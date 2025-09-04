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

import se.pitch.oss.fedpro.common.session.flowcontrol.NullRateLimiter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class TestRoundRobinBuffer {

   @Rule
   public Timeout globalTimeout = new Timeout(1000);

   private static final int PRIMARY_VALUE = 3;
   private static final int ALTERNATE_VALUE = 1;

   @Test
   public void insertDoesNotBlock_When_BothQueuesAreUsed()
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();

      // When
      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(ALTERNATE_VALUE);
      roundRobinBuffer.insert(ALTERNATE_VALUE);
      roundRobinBuffer.insert(ALTERNATE_VALUE);

      // Then don't block
   }

   @Test
   public void pollAlternates_When_BothQueuesAreUsed()
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();

      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(ALTERNATE_VALUE);

      // When
      int[] firstThreePolls = new int[] {roundRobinBuffer.poll(), roundRobinBuffer.poll(), roundRobinBuffer.poll()};

      // Then
      int[] expectedPollSequence = new int[] {PRIMARY_VALUE, ALTERNATE_VALUE, PRIMARY_VALUE};
      assertArrayEquals(expectedPollSequence, firstThreePolls);
   }

   @Test
   public void pollPrioritizesPrimaryQueue_When_BothQueuesAreUsed()
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();

      roundRobinBuffer.insert(ALTERNATE_VALUE);
      roundRobinBuffer.insert(ALTERNATE_VALUE);
      roundRobinBuffer.insert(ALTERNATE_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);

      // When
      int[] firstThreePolls = new int[] {roundRobinBuffer.poll(), roundRobinBuffer.poll(), roundRobinBuffer.poll()};

      // Then
      int[] expectedPollSequence = new int[] {PRIMARY_VALUE, ALTERNATE_VALUE, PRIMARY_VALUE};
      assertArrayEquals(expectedPollSequence, firstThreePolls);
   }

   @Test
   public void peekDoesNotAlterPollOrder()
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();
      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(ALTERNATE_VALUE);
      roundRobinBuffer.insert(ALTERNATE_VALUE);

      // When
      roundRobinBuffer.peek();

      // Then
      int result = roundRobinBuffer.poll();
      assertEquals(PRIMARY_VALUE, result);
   }

   @Test
   public void pollReturnsElement_When_PrimaryQueueIsEmpty()
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();

      roundRobinBuffer.insert(ALTERNATE_VALUE);
      roundRobinBuffer.insert(ALTERNATE_VALUE);
      roundRobinBuffer.insert(ALTERNATE_VALUE);

      // When
      int[] firstThreePolls = new int[] {roundRobinBuffer.poll(), roundRobinBuffer.poll(), roundRobinBuffer.poll()};

      // Then
      int[] expectedPollSequence = new int[] {ALTERNATE_VALUE, ALTERNATE_VALUE, ALTERNATE_VALUE};
      assertArrayEquals(expectedPollSequence, firstThreePolls);
   }

   @Test
   public void pollReturnsElement_When_AlternateQueueIsEmpty()
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();

      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);

      // When
      int[] firstThreePolls = new int[] {roundRobinBuffer.poll(), roundRobinBuffer.poll(), roundRobinBuffer.poll()};

      // Then
      int[] expectedPollSequence = new int[] {PRIMARY_VALUE, PRIMARY_VALUE, PRIMARY_VALUE};
      assertArrayEquals(expectedPollSequence, firstThreePolls);
   }

   @Test
   public void sizeGivesMaxOfLongestQueue_When_PrimaryQueueIsLongest()
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();

      roundRobinBuffer.insert(ALTERNATE_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);

      // When
      int actualSize = roundRobinBuffer.size();

      // Then
      assertEquals(2, actualSize);
   }

   @Test
   public void sizeGivesMaxOfLongestQueue_When_AlternateQueueIsLongest()
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();

      roundRobinBuffer.insert(ALTERNATE_VALUE);
      roundRobinBuffer.insert(ALTERNATE_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);

      // When
      int actualSize = roundRobinBuffer.size();

      // Then
      assertEquals(2, actualSize);
   }

   @Test
   public void peekDoesNotModifyQueues_When_PeekingPrimaryQueue()
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();

      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);

      // When
      roundRobinBuffer.peek();

      // Then
      assertEquals(2, roundRobinBuffer.size());
   }

   @Test
   public void peekDoesNotModifyQueues_When_PeekingAlternateQueue()
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();

      roundRobinBuffer.insert(ALTERNATE_VALUE);
      roundRobinBuffer.insert(ALTERNATE_VALUE);

      // When
      roundRobinBuffer.peek();

      // Then
      assertEquals(2, roundRobinBuffer.size());
   }

   @Test
   public void waitAndPollReturns_When_InsertedIntoPrimaryQueue()
   throws InterruptedException
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();

      new Thread(() -> {
         try {
            Thread.sleep(500);
         } catch (InterruptedException ignore) {
         }
         roundRobinBuffer.insert(PRIMARY_VALUE);
      }).start();

      // When
      int returnedValue = roundRobinBuffer.waitAndPoll();

      // Then
      assertEquals(PRIMARY_VALUE, returnedValue);
   }

   @Test
   public void waitAndPeekReturns_When_InsertedIntoPrimaryQueue()
   throws InterruptedException
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();

      new Thread(() -> {
         try {
            Thread.sleep(500);
         } catch (InterruptedException ignore) {
         }
         roundRobinBuffer.insert(PRIMARY_VALUE);
      }).start();

      // When
      int returnedValue = roundRobinBuffer.waitAndPeek();

      // Then
      assertEquals(PRIMARY_VALUE, returnedValue);
   }

   @Test
   public void waitAndPollReturns_When_InsertedIntoAlternateQueue()
   throws InterruptedException
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();

      new Thread(() -> {
         try {
            Thread.sleep(500);
         } catch (InterruptedException ignore) {
         }
         roundRobinBuffer.insert(ALTERNATE_VALUE);
      }).start();

      // When
      int returnedValue = roundRobinBuffer.waitAndPoll();

      // Then
      assertEquals(ALTERNATE_VALUE, returnedValue);
   }

   @Test
   public void waitAndPeekReturns_When_InsertedIntoAlternateQueue()
   throws InterruptedException
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();

      new Thread(() -> {
         try {
            Thread.sleep(500);
         } catch (InterruptedException ignore) {
         }
         roundRobinBuffer.insert(ALTERNATE_VALUE);
      }).start();

      // When
      int returnedValue = roundRobinBuffer.waitAndPeek();

      // Then
      assertEquals(ALTERNATE_VALUE, returnedValue);
   }

   @Test
   public void insertWaits_When_InsertingMoreElementsThanCapacity()
   throws InterruptedException
   {
      // Given
      RoundRobinBuffer<Integer> roundRobinBuffer = createBuffer();

      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);
      roundRobinBuffer.insert(PRIMARY_VALUE);

      AtomicBoolean returnValue = new AtomicBoolean(false);

      Thread insertThread = new Thread(() -> returnValue.set(roundRobinBuffer.insert(PRIMARY_VALUE)));

      // When
      insertThread.start();
      // Ensure insert thread tries to insert and then waits
      while (!insertThread.getState().equals(Thread.State.WAITING)) {
         // If it's not yet in the correct state, yield this thread and let other threads run
         Thread.yield();
      }

      // When insertThread is waiting for buffer to be non-full, poll
      roundRobinBuffer.poll();

      // Wait for insert thread to finish
      insertThread.join();

      // Then
      assertTrue(returnValue.get());
      assertEquals(3, roundRobinBuffer.size());
   }

   private RoundRobinBuffer<Integer> createBuffer()
   {
      return new RoundRobinBuffer<>(
            new Object(), 3,
            new NullRateLimiter(),
            new IntegerAlternator(List.of(ALTERNATE_VALUE))
      );
   }

   private static class IntegerAlternator implements QueueAlternator<Integer> {
      private final List<Integer> _alternateIntegers;

      public IntegerAlternator(List<Integer> alternateIntegers)
      {
         _alternateIntegers = alternateIntegers;
      }

      @Override
      public boolean putInAlternateQueue(Integer element)
      {
         return _alternateIntegers.contains(element);
      }
   }
}
