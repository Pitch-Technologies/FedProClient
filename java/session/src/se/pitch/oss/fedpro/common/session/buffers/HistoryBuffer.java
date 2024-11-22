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

package se.pitch.oss.fedpro.common.session.buffers;

import se.pitch.oss.fedpro.common.session.SequenceNumber;
import se.pitch.oss.fedpro.client.session.msg.EncodedMessage;
import se.pitch.oss.fedpro.client.session.msg.QueueableMessage;
import net.jcip.annotations.GuardedBy;

public class HistoryBuffer implements BufferReader<EncodedMessage> {

   private final Object _lock = new Object();
   @GuardedBy("_lock")
   private final SequenceNumber _sequenceNumberAllocator;
   private final BufferReader<QueueableMessage> _messageQueue;
   private final CircularBuffer<EncodedMessage> _history;
   private EncodedMessage _currentControlMessage;

   public HistoryBuffer(
         BufferReader<QueueableMessage> messageQueue,
         int initialSequenceNumber)
   {
      _sequenceNumberAllocator = new SequenceNumber(initialSequenceNumber);
      _messageQueue = messageQueue;
      _history = new CircularBuffer<>(_messageQueue.capacity());
   }

   @GuardedBy("_lock")
   private int nextSequenceNumber()
   {
      int oldNumber = _sequenceNumberAllocator.get();
      _sequenceNumberAllocator.increment();
      return oldNumber;
   }

   @Override
   public EncodedMessage waitAndPoll()
   throws InterruptedException
   {
      synchronized (_lock) {
         waitAndPollIntoHistory();
         return internalPoll();
      }
   }

   @Override
   public EncodedMessage waitAndPeek()
   throws InterruptedException
   {
      synchronized (_lock) {
         waitAndPollIntoHistory();
         return internalPeek();
      }
   }

   @Override
   public EncodedMessage poll()
   {
      synchronized (_lock) {
         pollIntoHistory();
         return internalPoll();
      }
   }

   @Override
   public EncodedMessage peek()
   {
      synchronized (_lock) {
         pollIntoHistory();
         return internalPeek();
      }
   }

   @GuardedBy("_lock")
   private EncodedMessage internalPeek()
   {
      if (_currentControlMessage == null) {
         return _history.peek();
      } else {
         return _currentControlMessage;
      }
   }

   @GuardedBy("_lock")
   private EncodedMessage internalPoll()
   {
      if (_currentControlMessage == null) {
         return _history.poll();
      } else {
         EncodedMessage message = _currentControlMessage;
         _currentControlMessage = null;
         return message;
      }
   }

   @GuardedBy("_lock")
   private void waitAndPollIntoHistory()
   throws InterruptedException
   {
      if (_history.isEmpty() && _currentControlMessage == null) {
         QueueableMessage message = _messageQueue.waitAndPoll();
         internalInsertIntoHistory(message.createEncodedMessage(nextSequenceNumber()));
      }
   }

   @GuardedBy("_lock")
   private void pollIntoHistory()
   {
      if (_history.isEmpty() && _currentControlMessage == null) {
         QueueableMessage message = _messageQueue.poll();
         if (message != null) {
            internalInsertIntoHistory(message.createEncodedMessage(nextSequenceNumber()));
         }
      }
   }

   @GuardedBy("_lock")
   private void internalInsertIntoHistory(EncodedMessage encodedMessage)
   {
      if (!encodedMessage.isControl) {
         _history.insert(encodedMessage);
      } else {
         _currentControlMessage = encodedMessage;
      }
   }

   @Override
   public int size()
   {
      return _history.size() + _messageQueue.size() + (_currentControlMessage == null ? 0 : 1);
   }

   @Override
   public boolean isEmpty()
   {
      return size() == 0;
   }

   @Override
   public int capacity()
   {
      return _history.capacity();
   }

   @Override
   public boolean waitUntilEmpty(long timeoutMillis)
   throws InterruptedException
   {
      return _messageQueue.waitUntilEmpty(timeoutMillis);
   }

   public void rewindTo(SequenceNumber sequenceNumber)
   {
      _history.rewindTo(object -> object != null && sequenceNumber.get() == object.sequenceNumber);
      _currentControlMessage = null;
   }

   public void rewindToFirst()
   {
      _history.rewindToFirst();
      _currentControlMessage = null;
   }

   public int getOldestAddedSequenceNumber()
   {
      // Intentionally skipping control messages
      synchronized (_lock) {
         EncodedMessage message = _history.peekOldest();
         return message != null ? message.sequenceNumber : SequenceNumber.NO_SEQUENCE_NUMBER;
      }
   }

   public int getNewestAddedSequenceNumber()
   {
      // Intentionally skipping control messages
      synchronized (_lock) {
         EncodedMessage message = _history.peekNewest();
         return message != null ? message.sequenceNumber : SequenceNumber.NO_SEQUENCE_NUMBER;
      }
   }

   @Override
   public String toString()
   {
      synchronized (_lock) {
         return "HistoryBuffer -> " + _history.toString();
      }
   }
}
