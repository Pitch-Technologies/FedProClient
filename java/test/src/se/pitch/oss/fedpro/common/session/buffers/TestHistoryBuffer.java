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

import se.pitch.oss.fedpro.client.session.msg.EncodedMessage;
import se.pitch.oss.fedpro.client.session.msg.QueueableMessage;
import se.pitch.oss.fedpro.common.session.MessageType;
import se.pitch.oss.fedpro.common.session.SequenceNumber;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.concurrent.CompletableFuture;

import static se.pitch.oss.fedpro.common.session.SequenceNumber.INITIAL_SEQUENCE_NUMBER;
import static se.pitch.oss.fedpro.common.session.SequenceNumber.NO_SEQUENCE_NUMBER;
import static org.junit.Assert.*;

public class TestHistoryBuffer {

   private final GenericBuffer<QueueableMessage> _messageQueue = new RateLimitedBuffer<>(3);
   private final HistoryBuffer _historyBuffer = new HistoryBuffer(_messageQueue, INITIAL_SEQUENCE_NUMBER);

   private final QueueableMessage _queueableMessage1 = dummyMessage(1);
   private final QueueableMessage _queueableMessage2 = dummyMessage(2);
   private final QueueableMessage _queueableMessage3 = dummyMessage(3);
   private final QueueableMessage _queueableMessage4 = dummyMessage(4);

   @Rule
   public Timeout globalTimeout = new Timeout(10000);

   @Test
   public void oldestAvailableMessageIsReturned_When_PeekIsCalled()
   {
      // Given
      _messageQueue.insert(_queueableMessage1);
      _messageQueue.insert(_queueableMessage2);
      // When
      EncodedMessage oldestAvailableMessage = _historyBuffer.peek();

      // Then
      assertSameMessage(_queueableMessage1, oldestAvailableMessage);
   }

   @Test
   public void peekReturnsNull_When_TryingToPeekPassedWriteIndex()
   {
      // Given
      _messageQueue.insert(_queueableMessage1);
      _historyBuffer.poll();

      // When / Then
      assertNull(_historyBuffer.peek());
   }

   @Test
   public void peekReturnsNull_When_HistoryBufferIsEmpty()
   {
      assertNull(_historyBuffer.peek());
   }

   @Test
   public void oldestAvailableMessageIsReturned_When_PollIsCalled()
   {
      // Given
      _messageQueue.insert(_queueableMessage1);
      _messageQueue.insert(_queueableMessage2);

      // When
      EncodedMessage oldestAvailableMessage = _historyBuffer.poll();

      // Then
      assertSameMessage(_queueableMessage1, oldestAvailableMessage);
   }

   @Test
   public void oldestMessageIsLost_When_HistoryIsOverwritten()
   {
      // Given
      _messageQueue.insert(_queueableMessage1);
      _historyBuffer.poll();
      _messageQueue.insert(_queueableMessage2);
      _historyBuffer.poll();
      _messageQueue.insert(_queueableMessage3);
      _historyBuffer.poll();

      // When
      _messageQueue.insert(_queueableMessage4);
      _historyBuffer.poll();

      // Then
      _historyBuffer.rewindTo(new SequenceNumber(3));
      assertSameMessage(_queueableMessage4, _historyBuffer.peek());

      _historyBuffer.rewindTo(new SequenceNumber(2));
      assertSameMessage(_queueableMessage3, _historyBuffer.peek());

      _historyBuffer.rewindTo(new SequenceNumber(1));
      assertSameMessage(_queueableMessage2, _historyBuffer.peek());

      assertEquals(3, _historyBuffer.size());
   }

   @Test
   public void PollReturnsNull_When_NothingIsLeftToPoll()
   {
      // Given
      _messageQueue.insert(_queueableMessage1);
      _historyBuffer.poll();

      // When / Then
      assertNull(_historyBuffer.poll());
   }

   @Test
   public void peekReturnsNextMessage_When_HistoryBufferIsPolled()
   {
      // Given
      _messageQueue.insert(_queueableMessage1);
      _messageQueue.insert(_queueableMessage2);

      // When
      _historyBuffer.poll();
      EncodedMessage message = _historyBuffer.peek();

      // Then
      assertSameMessage(_queueableMessage2, message);
   }

   @Test
   public void pollReturnsNull_When_HistoryBufferIsEmpty()
   {
      assertNull(_historyBuffer.poll());
   }

   @Test
   public void newestSequenceNumberIsReturned_When_GetNewestAddedSequenceNumberIsCalled()
   {
      // Given
      _messageQueue.insert(_queueableMessage1);
      _messageQueue.insert(_queueableMessage2);
      _historyBuffer.poll();
      _historyBuffer.poll();

      // When
      int newestNumber = _historyBuffer.getNewestAddedSequenceNumber();

      // Then
      assertEquals(INITIAL_SEQUENCE_NUMBER + 1, newestNumber);
   }

   @Test
   public void newestSequenceNumberIsNO_SEQUENCE_NUMBER_When_HistoryBufferIsEmpty()
   {
      assertEquals(NO_SEQUENCE_NUMBER, _historyBuffer.getNewestAddedSequenceNumber());
   }

   @Test
   public void oldestSequenceNumberIsReturned_When_GetOldestAddedSequenceNumberIsCalled()
   {
      // Given
      _messageQueue.insert(_queueableMessage1);
      _messageQueue.insert(_queueableMessage2);
      _historyBuffer.poll();
      _historyBuffer.poll();

      // When
      int oldestNumber = _historyBuffer.getOldestAddedSequenceNumber();

      // Then
      assertEquals(INITIAL_SEQUENCE_NUMBER, oldestNumber);
   }

   @Test
   public void oldestSequenceNumberIsNO_SEQUENCE_NUMBER_When_HistoryBufferIsEmpty()
   {
      assertEquals(NO_SEQUENCE_NUMBER, _historyBuffer.getOldestAddedSequenceNumber());
   }

   @Test
   public void sizeReturnsZero_When_MessageQueueIsEmpty()
   {
      assertEquals(0, _historyBuffer.size());
   }

   @Test
   public void sizeReturnsTwo_When_TwoMessagesHaveBeenInsertedIntoMessageQueue()
   {
      // When
      _messageQueue.insert(_queueableMessage1);
      _messageQueue.insert(_queueableMessage2);
      // Then
      assertEquals(2, _historyBuffer.size());
   }

   @Test
   public void sizeIsReducedByOne_When_HistoryBufferIsPolled()
   {
      // Given
      _messageQueue.insert(_queueableMessage1);
      _messageQueue.insert(_queueableMessage2);

      // When
      _historyBuffer.poll();

      // Then
      assertEquals(1, _historyBuffer.size());
   }

   @Test
   public void isEmpty_When_HistoryAndMessageQueuesAre()
   {
      assertTrue(_historyBuffer.isEmpty());
   }

   @Test
   public void isNotEmpty_When_MessageQueueIsNot()
   {
      _messageQueue.insert(_queueableMessage1);
      assertFalse(_historyBuffer.isEmpty());
   }

   @Test
   public void isEmpty_When_EverythingIsPolledOutOfHistoryBuffer()
   {
      // Given
      _messageQueue.insert(_queueableMessage1);

      // When
      _historyBuffer.poll();

      // Then
      assertTrue(_historyBuffer.isEmpty());
   }

   @Test
   public void oldestAvailableMessageIsReturned_When_waitAndPollIsCalled()
   throws Exception
   {
      // Given
      _messageQueue.insert(_queueableMessage1);
      _messageQueue.insert(_queueableMessage2);

      // When
      EncodedMessage oldestAvailableMessage = _historyBuffer.waitAndPoll();

      // Then
      assertSameMessage(_queueableMessage1, oldestAvailableMessage);
   }

   @Test
   public void waitAndPollWorks_When_InitiallyCalledOnAnEmptyHistoryBuffer()
   throws Exception
   {
      // Given
      new Thread(() -> {
         try {
            Thread.sleep(100);
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
         _messageQueue.insert(_queueableMessage1);
      }).start();

      // When // Then
      assertNotNull(_historyBuffer.waitAndPoll());
   }

   @Test
   public void oldestAvailableMessageIsReturned_When_waitAndPeekIsCalled()
   throws Exception
   {
      // Given
      _messageQueue.insert(_queueableMessage1);
      _messageQueue.insert(_queueableMessage2);

      // When
      EncodedMessage oldestAvailableMessage = _historyBuffer.waitAndPeek();

      // Then
      assertSameMessage(_queueableMessage1, oldestAvailableMessage);
   }

   @Test
   public void waitAndPeekWorks_When_InitiallyCalledOnAnEmptyHistoryBuffer()
   throws Exception
   {
      // Given
      new Thread(() -> {
         try {
            Thread.sleep(100);
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
         _messageQueue.insert(_queueableMessage1);
      }).start();

      // When
      assertNotNull(_historyBuffer.waitAndPeek());

      // Then do not throw.
   }

   @Test
   public void peeksCorrectMessage_When_Rewinding1Message()
   {
      // Given
      _messageQueue.insert(_queueableMessage1);
      _messageQueue.insert(_queueableMessage2);
      _historyBuffer.poll();
      _historyBuffer.poll();

      // When
      _historyBuffer.rewindTo(new SequenceNumber(1));

      // Then
      assertSameMessage(_queueableMessage2, _historyBuffer.peek());
   }

   private QueueableMessage dummyMessage(int mockSessionId)
   {
      return new QueueableMessage(
            0, 0, mockSessionId, MessageType.HLA_CALL_REQUEST, null, new CompletableFuture<>(), null
      );
   }

   public void assertSameMessage(QueueableMessage expectedMessage, EncodedMessage message)
   {
      EncodedMessage expected = expectedMessage.createEncodedMessage(message.sequenceNumber);
      assertArrayEquals(expected.data, message.data);
   }
}
