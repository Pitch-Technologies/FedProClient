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

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCircularBuffer {

   private CircularBuffer<Integer> _buf;

   //@Rule public Timeout globalTimeout = new Timeout(3000);

   @Test
   public void oldestAvailableValueIsReturned_When_PeekIsCalled()
   {
      // Given
      _buf = new CircularBuffer<>(3);
      _buf.insert(0);
      _buf.insert(1);

      // When
      int returnValue = _buf.peek();

      // Then
      assertEquals(0, returnValue);
   }

   @Test
   public void peekReturnsOldestAvailableValue_When_OldestHasBeenOverwritten()
   {
      // Given
      _buf = new CircularBuffer<>(2);
      _buf.insert(0);
      _buf.insert(1);

      // When
      _buf.insert(2);

      // Then
      assertEquals(1, (int) _buf.peek());
   }

   @Test
   public void peekReturnsNull_When_BufferIsEmpty()
   {
      _buf = new CircularBuffer<>(1);
      assertNull(_buf.peek());
   }

   @Test
   public void oldestValueIsReturned_When_PeekOldestIsCalled()
   {
      // Given
      _buf = new CircularBuffer<>(3);
      _buf.insert(0);
      _buf.insert(1);

      // When
      int oldestValue = _buf.peekOldest();

      // Then
      assertEquals(0, oldestValue);
   }

   @Test
   public void peekOldestStillReturnsOldestValue_When_PollHasBeenCalled()
   {
      // Given
      _buf = new CircularBuffer<>(3);
      _buf.insert(0);
      _buf.insert(1);

      // When
      _buf.poll();

      // Then
      assertEquals(0, (int) _buf.peekOldest());
   }

   @Test
   public void peekOldestReturnsOldestValue_When_OldestHasBeenOverwritten()
   {
      // Given
      _buf = new CircularBuffer<>(2);
      _buf.insert(0);
      _buf.insert(1);

      // When
      _buf.insert(2);

      // Then
      assertEquals(1, (int) _buf.peekOldest());
   }

   @Test
   public void peekOldestReturnsNull_When_BufferIsEmpty()
   {
      _buf = new CircularBuffer<>(1);
      assertNull(_buf.peekOldest());
   }

   @Test
   public void oldestAvailableValueIsReturned_When_PollIsCalled()
   {
      // Given
      _buf = new CircularBuffer<>(3);
      _buf.insert(0);
      _buf.insert(1);

      // When
      int returnValue = _buf.poll();

      // Then
      assertEquals(0, returnValue);
   }

   @Test
   public void oldestAvailableValueGetsUnavailable_When_PollIsCalled()
   {
      // Given
      _buf = new CircularBuffer<>(3);
      _buf.insert(0);
      _buf.insert(1);

      // When
      _buf.poll();

      // Then
      assertEquals(1, _buf.size());
      assertEquals(1, (int) _buf.peek());
   }

   @Test
   public void pollReturnsOldestAvailableValue_When_OldestHasBeenOverwritten()
   {
      // Given
      _buf = new CircularBuffer<>(2);
      _buf.insert(0);
      _buf.insert(1);

      // When
      _buf.insert(2);

      // Then
      assertEquals(1, (int) _buf.poll());
   }

   @Test
   public void pollReturnsNull_When_BufferIsEmpty()
   {
      _buf = new CircularBuffer<>(1);
      assertNull(_buf.poll());
   }

   @Test
   public void newestValueIsReturned_When_PeekNewestIsCalled()
   {
      // Given
      _buf = new CircularBuffer<>(3);
      _buf.insert(0);
      _buf.insert(1);

      // When
      int newestValue = _buf.peekNewest();

      // Then
      assertEquals(1, newestValue);
   }

   @Test
   public void peekNewestReturnsNull_When_BufferIsEmpty()
   {
      _buf = new CircularBuffer<>(1);
      assertNull(_buf.peekNewest());
   }

   @Test
   public void oldestAvailableValueIsReturned_When_WaitAndPollIsCalled()
   throws Exception
   {
      // Given
      _buf = new CircularBuffer<>(3);
      _buf.insert(0);
      _buf.insert(1);

      // When
      int returnValue = _buf.waitAndPoll();

      // Then
      assertEquals(0, returnValue);
   }

   @Test
   public void oldestAvailableValueGetsUnavailable_When_WaitAndPollIsCalled()
   throws Exception
   {
      // Given
      _buf = new CircularBuffer<>(3);
      _buf.insert(0);
      _buf.insert(1);

      // When
      _buf.waitAndPoll();

      // Then
      assertEquals(1, _buf.size());
   }

   @Test
   public void waitAndPollReturnsOldestAvailableValue_When_OldestHasBeenOverwritten()
   throws Exception
   {
      // Given
      _buf = new CircularBuffer<>(2);
      _buf.insert(0);
      _buf.insert(1);

      // When
      _buf.insert(2);

      // Then
      assertEquals(1, (int) _buf.waitAndPoll());
   }

   @Test
   public void waitAndPollPolls_When_FirstValueIsInserted()
   throws Exception
   {
      // Given
      _buf = new CircularBuffer<>(2);

      // When
      new Thread(() -> {
         try {
            Thread.sleep(100);
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
         _buf.insert(5);
      }).start();

      // Then
      assertEquals(5, (int) _buf.waitAndPoll());
   }

   @Test
   public void oldestAvailableValueIsReturned_When_WaitAndPeekIsCalled()
   throws Exception
   {
      // Given
      _buf = new CircularBuffer<>(3);
      _buf.insert(0);
      _buf.insert(1);

      // When
      int returnValue = _buf.waitAndPeek();

      // Then
      assertEquals(0, returnValue);
   }

   @Test
   public void waitAndPeekReturnsOldestAvailableValue_When_OldestHasBeenOverwritten()
   throws Exception
   {
      // Given
      _buf = new CircularBuffer<>(2);
      _buf.insert(0);
      _buf.insert(1);

      // When
      _buf.insert(2);

      // Then
      assertEquals(1, (int) _buf.waitAndPeek());
   }

   @Test
   public void waitAndPeekPeeks_When_FirstValueIsInserted()
   throws Exception
   {
      // Given
      _buf = new CircularBuffer<>(2);

      // When
      new Thread(() -> {
         try {
            Thread.sleep(100);
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
         _buf.insert(5);
      }).start();

      // Then
      assertEquals(5, (int) _buf.waitAndPeek());
   }

   @Test
   public void sizeReturnsZero_When_BufferIsEmpty()
   {
      _buf = new CircularBuffer<>(4);
      assertEquals(0, _buf.size());
   }

   @Test
   public void sizeReturnsTwo_When_TwoElementsHaveBeenInserted()
   {
      // Given
      _buf = new CircularBuffer<>(4);

      // When
      _buf.insert(0);
      _buf.insert(1);

      // Then
      assertEquals(2, _buf.size());
   }

   @Test
   public void sizeIsReducedByOne_When_PollIsCalled()
   {
      // Given
      _buf = new CircularBuffer<>(4);
      _buf.insert(0);
      _buf.insert(1);

      // When
      _buf.poll();

      // Then
      assertEquals(1, _buf.size());
   }

   @Test
   public void sizeIsUnchanged_When_InsertingMoreElementsThanBufferCapacity()
   {
      // Given
      _buf = new CircularBuffer<>(2);
      _buf.insert(0);
      _buf.insert(1);

      // When
      _buf.insert(3);

      // Then
      assertEquals(2, _buf.size());
   }

   @Test
   public void isEmptyReturnsTrue_When_BufferIsEmpty()
   {
      _buf = new CircularBuffer<>(1);
      assertTrue(_buf.isEmpty());
   }

   @Test
   public void isEmptyReturnsFalse_When_BufferIsNotEmpty()
   {
      _buf = new CircularBuffer<>(2);
      _buf.insert(0);
      assertFalse(_buf.isEmpty());
   }

   @Test
   public void isEmptyReturnsFalse_When_BufferIsFull()
   {
      _buf = new CircularBuffer<>(1);
      _buf.insert(0);
      assertFalse(_buf.isEmpty());
   }

   @Test
   public void isFullReturnsTrue_When_BufferIsFull()
   {
      _buf = new CircularBuffer<>(1);
      _buf.insert(0);
      assertTrue(_buf.isFull());
   }

   @Test
   public void isFullReturnsFalse_When_BufferIsNotFull()
   {
      _buf = new CircularBuffer<>(2);
      _buf.insert(0);
      assertFalse(_buf.isFull());
   }

   @Test
   public void isFullReturnsFalse_When_BufferIsEmpty()
   {
      _buf = new CircularBuffer<>(1);
      assertFalse(_buf.isFull());
   }

   @Test
   public void stateIsCorrect_When_RewindingOneIndex()
   {
      // Given
      _buf = new CircularBuffer<>(4);
      _buf.insert(0);
      _buf.insert(1);
      _buf.insert(2);
      _buf.poll();

      // When
      _buf.rewindTo(getElementFinder(0));

      // Then
      assertEquals(3, _buf.size());
      assertEquals(0, (long) _buf.peek());
   }

   @Test
   public void stateIsCorrect_When_RewindingToFirstElement()
   {
      // Given
      _buf = new CircularBuffer<>(3);
      _buf.insert(0);
      _buf.insert(1);
      _buf.insert(2);
      _buf.poll();
      _buf.poll();
      _buf.poll();

      // When
      _buf.rewindTo(getElementFinder(0));

      // Then
      assertEquals(3, _buf.size());
      assertEquals(0, (long) _buf.peek());
   }

   @Test
   public void stateIsCorrect_When_RewindingByOneMultipleTimes()
   {
      // Given
      _buf = new CircularBuffer<>(3);
      _buf.insert(0);
      _buf.insert(1);
      _buf.insert(2);
      _buf.poll();
      _buf.poll();
      _buf.poll();
      _buf.insert(3);
      _buf.poll();
      _buf.insert(4);
      _buf.insert(5);
      _buf.insert(6);
      _buf.poll();
      _buf.poll();
      _buf.poll();

      // Future reads: []
      // Past reads: [4, 5, 6] (6 is the last read)


      _buf.rewindTo(getElementFinder(6));
      assertEquals(1, _buf.size());
      assertEquals(6, (long) _buf.peek());

      _buf.rewindTo(getElementFinder(5));
      assertEquals(2, _buf.size());
      assertEquals(5, (long) _buf.peek());

      _buf.rewindTo(getElementFinder(4));
      assertEquals(3, _buf.size());
      assertEquals(4, (long) _buf.peek());
   }

   @Test
   public void stateIsCorrect_When_RewindingTheEntireBufferMultipleTimes()
   {
      // Given
      _buf = new CircularBuffer<>(3);
      _buf.insert(0);
      _buf.insert(1);
      _buf.insert(2);
      _buf.poll();
      _buf.poll();
      _buf.poll();
      _buf.insert(3);
      _buf.poll();
      _buf.insert(4);
      _buf.insert(5);
      _buf.insert(6);
      _buf.poll();
      _buf.poll();
      _buf.poll();

      _buf.rewindTo(getElementFinder(4));
      _buf.poll();
      _buf.poll();
      _buf.poll();

      // When
      _buf.rewindTo(getElementFinder(4));

      // Then
      assertEquals(4, (int) _buf.poll());
      assertEquals(2, _buf.size());
      assertEquals(5, (int) _buf.peek());
   }

   @Test
   public void rewindsToFirstElement_When_RewindingToFirstElementBeforeWraparound()
   {
      // Given
      _buf = new CircularBuffer<>(3);
      _buf.insert(0);
      _buf.insert(1);
      _buf.poll();
      _buf.poll();

      // When
      _buf.rewindTo(getElementFinder(0));

      // Then
      assertEquals(0, (int) _buf.peek());
   }

   @Test
   public void rewindsToNonFirstElement_When_RewindingToNonFirstElementBeforeWraparound()
   {
      // Given
      _buf = new CircularBuffer<>(3);
      _buf.insert(0);
      _buf.insert(1);
      _buf.insert(2);
      _buf.poll();
      _buf.poll();

      // When
      _buf.rewindTo(getElementFinder(1));

      // Then
      assertEquals(1, (int) _buf.peek());
   }


   @Test(expected = IllegalArgumentException.class)
   public void rewindThrows_When_RewindingToToANonPresentValue()
   {
      // Given
      _buf = new CircularBuffer<>(4);

      // When
      _buf.rewindTo(getElementFinder(3));

      // Then throw.
   }

   @Test(expected = IllegalArgumentException.class)
   public void rewindThrows_When_RewindingToToPresentNonReadValueBeforeWraparound()
   {
      // Given
      _buf = new CircularBuffer<>(4);
      _buf.insert(3);

      // When
      _buf.rewindTo(getElementFinder(3));

      // Then throw.
   }

   @Test(expected = IllegalArgumentException.class)
   public void rewindThrows_When_RewindingToToPresentNonReadValueAfterWraparound()
   {
      // Given
      _buf = new CircularBuffer<>(2);
      _buf.insert(0);
      _buf.insert(1);
      _buf.poll();
      _buf.poll();
      _buf.insert(2);
      _buf.insert(3);
      _buf.poll();
      _buf.poll();

      // When
      _buf.rewindTo(getElementFinder(6));

      // Then throw.
   }

   @Test
   public void wrapAroundHappens_When_RewindingToPastZero()
   {
      // Given
      _buf = new CircularBuffer<>(2);
      _buf.insert(0);
      _buf.poll();
      _buf.insert(1);
      _buf.poll();
      _buf.insert(2);
      _buf.poll();

      // When
      _buf.rewindTo(getElementFinder(1));

      // Then
      assertEquals(1, (int) _buf.peek());
   }

   @Test
   public void waitUntilEmptyDoesNotThrow_When_PollHappensAfter100ms()
   throws Exception
   {
      // Given
      _buf = new CircularBuffer<>(2);
      _buf.insert(0);
      _buf.insert(1);
      new Thread(() -> {
         try {
            Thread.sleep(100);
         } catch (Exception e) {
            throw new RuntimeException(e);
         }
         _buf.poll();
         _buf.poll();
      }).start();

      // When
      _buf.waitUntilEmpty(0);

      // Then do not throw.
   }

   @Test
   public void waitUntilEmptyDoesNotWait_When_BufferIsEmpty()
   throws Exception
   {
      _buf = new CircularBuffer<>(2);
      _buf.waitUntilEmpty(0);
   }

   @Test
   public void waitUntilEmptyTimesOut_When_BufferDoesNotGetEmpty()
   throws Exception
   {
      // Given
      _buf = new CircularBuffer<>(2);
      _buf.insert(0);

      // When
      boolean result = _buf.waitUntilEmpty(100);

      // Then
      assertFalse(result);
   }

   @Test
   public void rewindToFirstDoesNotThrow_When_BufferIsEmpty()
   {
      // Given
      _buf = new CircularBuffer<>(2);

      // When
      _buf.rewindToFirst();

      // Then do not throw.
   }

   @Test
   public void rewindToFirstWorks_When_RewindingFullBufferToFirstMessage()
   {
      // Given
      _buf = new CircularBuffer<>(2);
      _buf.insert(3);
      _buf.insert(2);
      _buf.poll();
      _buf.poll();

      // When
      _buf.rewindToFirst();

      // Then
      assertEquals(3, (int) _buf.poll());
      assertEquals(2, (int) _buf.poll());
      assertEquals(0, _buf.size());
   }

   @Test
   public void rewindToFirstWorks_When_RewindingHalfFullBufferToFirstMessage()
   {
      // Given
      _buf = new CircularBuffer<>(4);
      _buf.insert(3);
      _buf.insert(2);
      _buf.poll();
      _buf.poll();

      // When
      _buf.rewindToFirst();

      // Then
      assertEquals(3, (int) _buf.poll());
      assertEquals(2, (int) _buf.poll());
      assertEquals(0, _buf.size());
   }

   @Test
   public void rewindToFirstWorks_When_UnreadMessagesExists()
   {
      // Given
      _buf = new CircularBuffer<>(2);
      _buf.insert(3);
      _buf.insert(2);
      _buf.poll();

      // When
      _buf.rewindToFirst();

      // Then
      assertEquals(3, (int) _buf.poll());
      assertEquals(2, (int) _buf.poll());
      assertEquals(0, _buf.size());
   }

   @Test(expected = IllegalArgumentException.class)
   public void rewindToFirstThrows_When_BufferHasWrappedCompletely()
   {
      // Given
      _buf = new CircularBuffer<>(2);
      _buf.insert(2);
      _buf.insert(2);
      _buf.poll();
      _buf.insert(2);

      // When
      _buf.rewindToFirst();

      // Then throw
   }

   private ElementFinder<Integer> getElementFinder(int sought)
   {
      return object -> object != null && object == sought;
   }

}