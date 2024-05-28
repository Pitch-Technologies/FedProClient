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

import se.pitch.oss.fedpro.common.session.buffers.GenericBuffer;
import se.pitch.oss.fedpro.common.session.buffers.HistoryBuffer;
import se.pitch.oss.fedpro.common.exceptions.BadMessage;
import se.pitch.oss.fedpro.client.session.msg.EncodedMessage;
import se.pitch.oss.fedpro.client.session.msg.MessageHeader;
import se.pitch.oss.fedpro.client.session.msg.QueueableMessage;
import se.pitch.oss.fedpro.common.transport.SessionSocket;
import net.jcip.annotations.GuardedBy;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.logging.Logger;

public class SocketWriter implements Runnable {
   // TODO: maybe change tactics here, and remove the thread object from this class. Instead, let the client class
   //  handle the thread, just like with _readerThread in Session.

   private static final Logger LOGGER = Logger.getLogger(SocketWriter.class.getName());
   private long _sessionId;
   private final String _clientOrServer;
   private final Listener _listener;
   // TODO: If possible, inherit from this class to create another SocketWriter based on BlockingQueue,
   //   to create back pressure in federate.
   private final HistoryBuffer _historyBuffer;
   private final Object _threadLock = new Object();
   private final Thread _thread;
   private SessionSocket _socket;
   private final SequenceNumber _expectedSequenceNumber;
   private static final boolean ALLOW_GAPS_IN_SEQUENCE_NUMBERS = Boolean.getBoolean("se.pitch.oss.prti1516e.fedpro.common.allowGapsInSequenceNumbers");

   @GuardedBy("_threadLock") private boolean _isTerminated = false;
   @GuardedBy("_threadLock") private boolean _isPaused = false;

   private boolean _run = false;

   public interface Listener {
      void messagesLost(String reason);

      void exceptionOnWrite(String message);

      void messageSent();
   }

   public static SocketWriter directSocketWriter(long sessionId,
                                                 SessionSocket socket,
                                                 boolean isClient)
   {
      return new SocketWriter(sessionId, null, null, socket, isClient, null, 1);
   }

   public SocketWriter(long sessionId,
                       Listener listener,
                       GenericBuffer<QueueableMessage> messageQueue,
                       SessionSocket socket,
                       boolean isClient,
                       int initialSequenceNumber)
   {
      _sessionId = sessionId;
      _listener = listener;
      _historyBuffer = new HistoryBuffer(messageQueue, initialSequenceNumber);
      _socket = socket;
      _thread = new Thread(this);
      _clientOrServer = isClient ? "Client" : "Server";
      _expectedSequenceNumber = new SequenceNumber(initialSequenceNumber);
   }

   private SocketWriter(long sessionId,
                        Listener listener,
                        HistoryBuffer historyBuffer,
                        SessionSocket socket,
                        boolean isClient,
                        Thread thread,
                        int initialSequenceNumber)
   {
      _sessionId = sessionId;
      _listener = listener;
      _historyBuffer = historyBuffer;
      _socket = socket;
      _thread = thread;
      _clientOrServer = isClient ? "Client" : "Server";
      _expectedSequenceNumber = new SequenceNumber(initialSequenceNumber);
   }

   public boolean isDirectOnly()
   {
      return _historyBuffer == null;
   }

   public Thread getThread()
   {
      return _thread;
   }

   @Override
   public void run()
   {
      assert !isDirectOnly();
      internalMessageWriterLoop();
   }

   private void internalMessageWriterLoop()
   {
      while (!isTerminated()) {
         try {

            while (!isPaused()) {
               writeNextMessage();
            }
            synchronized (_threadLock) {
               _threadLock.wait();
            }

         } catch (InterruptedException e) {
            // We just paused, unpaused or terminated. Keep going and let the while conditions handle it.
         } catch (RuntimeException e) {
            LOGGER.severe(() -> String.format("%s: Unexpected exception in socket writer thread: %s", logPrefix(), e));
         }
      }
   }

   public void socketWriterLoop()
   {
      _run = true;
      try {
         while (_run) {
            // This loop contains non-interruptible I/O, which means we have to close the socket to be sure the thread
            //   will exit. Since that's the case anyway, we make closing the socket the *only* way to exit.
            // This loop also contains locks, which we need to interrupt to not get stuck. Therefore, a
            //   combination of closing the socket and interrupting the thread is required.
            writeNextMessage();
         }
      } catch (InterruptedException e) {
         // End the thread
      } catch (RuntimeException e) {
         LOGGER.severe(() -> String.format("%s: Unexpected exception in thread " + Thread.currentThread().getName(),
            logPrefix()));
      }
   }

   public void writeNextMessage() throws InterruptedException
   {
      EncodedMessage message = _historyBuffer.waitAndPeek();

      // Todo - this will have to change if we are to allow gaps in the history:
      if (_expectedSequenceNumber.get() != message.sequenceNumber && !ALLOW_GAPS_IN_SEQUENCE_NUMBERS) {
         assert false;
         _listener.messagesLost("Message queue overflow. Messages being consumed too slowly?");
         terminateNow();
         return;
      }

      try {
         writeMessage(message);
      } catch (IOException e) {
         if (Thread.interrupted()) {
            throw new InterruptedException();
         } else {
            _listener.exceptionOnWrite(e.toString());
            _run = false;
            return;
         }
      }

      _expectedSequenceNumber.increment();
      _historyBuffer.poll();
      _listener.messageSent();
   }

   private void writeMessage(EncodedMessage message) throws IOException
   {
      // It's hard to find a definitive answer, but I think the write method may, if the outgoing buffer is full,
      //   block indefinitely (until the socket/stream is closed).
      _socket.getOutputStream().write(message.data);
      logSentMessage(message);
   }

   public void writeDirectMessage(EncodedMessage message) throws IOException
   {
      assert isDirectOnly();
      writeMessage(message);
   }

   public void pause()
   {
      synchronized (_threadLock) {
         _isPaused = true;
         _thread.interrupt();
      }
   }

   public void unpause()
   {
      synchronized (_threadLock) {
         if (!_isTerminated) {
            _isPaused = false;
            _thread.interrupt();
         }
      }
   }

   public void terminateNow()
   {
      synchronized (_threadLock) {
         _isTerminated = true;
         _isPaused = true;
         _thread.interrupt();
      }
   }

   public boolean isPaused()
   {
      synchronized (_threadLock) {
         return _isPaused;
      }
   }

   public boolean isTerminated()
   {
      synchronized (_threadLock) {
         return _isTerminated;
      }
   }

   public boolean waitForEmptyQueue(long timeoutMillis) throws InterruptedException
   {
      return _historyBuffer.waitUntilEmpty(timeoutMillis);
   }

   public void rewindToSequenceNumber(SequenceNumber sequenceNumber)
   {
      int numberOfMessages = _expectedSequenceNumber.getDistanceFrom(sequenceNumber);
      if (numberOfMessages == 0) {
         // The sequence number given to this function is the first sequence number that the other instance hasn't yet
         // seen. Therefore this can be an unsent sequence number. In that case we do not need to rewind.
         return;
      }
      _historyBuffer.rewindTo(sequenceNumber);
      _expectedSequenceNumber.set(sequenceNumber);
   }

   public void setNewSocket(SessionSocket socket)
   {
      _socket = socket;
   }

   private void logSentMessage(EncodedMessage message)
   {
      LOGGER.finer(() -> String.format("%s: Sent     seq.nr. %s, type %s",
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
      return _historyBuffer.getOldestAddedSequenceNumber();
   }

   public int getNewestAddedSequenceNumber()
   {
      return _historyBuffer.getNewestAddedSequenceNumber();
   }

   public void setSessionId(long sessionId)
   {
      _sessionId = sessionId;
   }

   public String logPrefix()
   {
      return LogUtil.logPrefix(_sessionId, _clientOrServer);
   }
}
