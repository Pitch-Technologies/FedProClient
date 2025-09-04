/*
 *  Copyright (C) 2022 Pitch Technologies AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.pitch.oss.fedpro.common.session;

import se.pitch.oss.fedpro.client.session.msg.EncodedMessage;
import se.pitch.oss.fedpro.client.session.msg.MessageHeader;
import se.pitch.oss.fedpro.client.session.msg.QueueableMessage;
import se.pitch.oss.fedpro.common.exceptions.BadMessage;
import se.pitch.oss.fedpro.common.session.buffers.BufferReader;
import se.pitch.oss.fedpro.common.session.buffers.HistoryBuffer;
import se.pitch.oss.fedpro.common.transport.FedProSocket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class SocketWriter {

   private static final Logger LOGGER = Logger.getLogger(SocketWriter.class.getName());

   private String _sessionIdString;
   private final String _clientOrServer;
   private final Listener _listener;
   private final HistoryBuffer _historyBuffer;
   private FedProSocket _socket;
   private final SequenceNumber _expectedNextSequenceNumber;
   private int _lastSequenceNumber = SequenceNumber.NO_SEQUENCE_NUMBER;

   private boolean _run = false;

   public interface Listener {
      void exceptionOnWrite(Exception e);

      void messageSent();
   }

   public static SocketWriter createDirectSocketWriter(
         long sessionId,
         FedProSocket socket,
         boolean isClient)
   {
      return new SocketWriter(sessionId, null, null, socket, isClient, null);
   }

   public static SocketWriter createSocketWriterForThread(
         long sessionId,
         Listener listener,
         BufferReader<QueueableMessage> messageQueue,
         FedProSocket socket,
         boolean isClient,
         int expectedNextSequenceNumber)
   {
      return new SocketWriter(
            sessionId,
            listener,
            new HistoryBuffer(messageQueue, expectedNextSequenceNumber),
            socket,
            isClient,
            new SequenceNumber(expectedNextSequenceNumber));
   }

   private SocketWriter(
         long sessionId,
         Listener listener,
         HistoryBuffer historyBuffer,
         FedProSocket socket,
         boolean isClient,
         SequenceNumber expectedNextSequenceNumber)
   {
      _sessionIdString = LogUtil.formatSessionId(sessionId);
      _listener = listener;
      _historyBuffer = historyBuffer;
      _socket = socket;
      _clientOrServer = isClient ? LogUtil.CLIENT_PREFIX : LogUtil.SERVER_PREFIX;
      _expectedNextSequenceNumber = expectedNextSequenceNumber;
   }

   public boolean isDirectOnly()
   {
      return _historyBuffer == null;
   }

   public void socketWriterLoop()
   {
      assert !isDirectOnly();
      try {
         while (_run) {
            // This loop sometimes waits in non-interruptible I/O, which will not stop blocking unless the socket is closed.
            //   It also sometimes waits on a lock, which we need to interrupt to stop blocking.
            //   Therefore, the loop should be ended by first interrupting the thread and then closing the socket.
            writeNextMessage();
         }
      } catch (InterruptedException e) {
         LOGGER.finer(() -> String.format(
               "%s: \"%s\" was interrupted and will end",
               logPrefix(),
               Thread.currentThread().getName()));
         // End the thread
      } catch (RuntimeException e) {
         LOGGER.severe(() -> String.format(
               "%s: Unexpected exception in thread " + Thread.currentThread().getName(),
               logPrefix()));
      }
   }

   public void writeNextMessage()
   throws InterruptedException
   {
      assert !isDirectOnly();
      EncodedMessage message = _historyBuffer.waitAndPeek();

      try {
         writeMessage(message);
      } catch (IOException e) {
         if (Thread.interrupted()) {
            throw new InterruptedException();
         } else {
            _listener.exceptionOnWrite(e);
            _run = false;
            return;
         }
      }

      if (_expectedNextSequenceNumber.get() != message.sequenceNumber) {
         LOGGER.fine(() -> String.format(
               "%s: Use of non-sequential sequence number in outgoing message: %d after previous sequence number %s",
               logPrefix(),
               message.sequenceNumber,
               _lastSequenceNumber));
      }

      _lastSequenceNumber = message.sequenceNumber;
      _expectedNextSequenceNumber.set(_lastSequenceNumber).increment();

      _historyBuffer.poll();
      _listener.messageSent();
   }

   private void writeMessage(EncodedMessage message)
   throws IOException
   {
      // It's hard to find a definitive answer, but I think the write method may, if the outgoing buffer is full,
      //   block indefinitely (until the socket/stream is closed).
      _socket.getOutputStream().write(message.data);
      logSentMessage(message);
   }

   public void writeDirectMessage(EncodedMessage message)
   throws IOException
   {
      writeMessage(message);
   }

   public boolean waitForEmptyQueue(long timeoutMillis)
   throws InterruptedException
   {
      assert !isDirectOnly();
      return _historyBuffer.waitUntilEmpty(timeoutMillis);
   }

   public void rewindToFirstMessage()
   {
      assert !isDirectOnly();
      _historyBuffer.rewindToFirst();
      if (!_historyBuffer.isEmpty()) {
         _expectedNextSequenceNumber.set(_historyBuffer.peek().sequenceNumber);
      }
   }

   public void rewindToSequenceNumberAfter(SequenceNumber sequenceNumber)
   {
      assert !isDirectOnly();
      _historyBuffer.rewindTo(sequenceNumber);
      // Consume one message to end up on the message after the given sequence number.
      _historyBuffer.poll();
      if (!_historyBuffer.isEmpty()) {
         _expectedNextSequenceNumber.set(_historyBuffer.peek().sequenceNumber);
      }
   }

   public void setNewSocket(FedProSocket socket)
   {
      assert !isDirectOnly();
      _socket = socket;
   }

   public void enableWriterLoop()
   {
      _run = true;
   }

   private void logSentMessage(EncodedMessage message)
   {
      LOGGER.finer(() -> String.format(
            "%s: Sent     seq.nr. %s, type %s",
            logPrefix(),
            LogUtil.padNumString(message.sequenceNumber),
            decodeMessageType(message)));
   }

   private String decodeMessageType(EncodedMessage message)
   {
      try {
         return MessageHeader.decode(new ByteArrayInputStream(message.data)).messageType.toString();
      } catch (BadMessage | IOException e) {
         return "<ENCODING ERROR>";
      }
   }

   public int getOldestAddedSequenceNumber()
   {
      assert !isDirectOnly();
      return _historyBuffer.getOldestAddedSequenceNumber();
   }

   public int getNewestAddedSequenceNumber()
   {
      assert !isDirectOnly();
      return _historyBuffer.getNewestAddedSequenceNumber();
   }

   public void setSessionId(long sessionId)
   {
      _sessionIdString = LogUtil.formatSessionId(sessionId);
   }

   private String logPrefix()
   {
      return LogUtil.logPrefix(_sessionIdString, _clientOrServer);
   }
}
