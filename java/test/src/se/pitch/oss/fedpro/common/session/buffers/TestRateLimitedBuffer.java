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

import se.pitch.oss.fedpro.common.session.flowcontrol.RateLimiter;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

import static org.junit.Assert.*;

public class TestRateLimitedBuffer {

   @Rule
   public Timeout globalTimeout = new Timeout(1000);

   private final ControllableRateLimiter _rateLimiter = new ControllableRateLimiter();

   @Test
   public void bufferUnmodified_When_Peeked()
   {
      // Given
      RateLimitedBuffer<Integer> rateLimitedBuffer = new RateLimitedBuffer<>(1);
      rateLimitedBuffer.insert(1);

      // When
      int returnValue = rateLimitedBuffer.peek();

      // Then
      assertEquals(1, returnValue);
      assertEquals(1, rateLimitedBuffer.size());
   }

   @Test
   public void bufferModified_When_Polled()
   {
      // Given
      RateLimitedBuffer<Integer> rateLimitedBuffer = new RateLimitedBuffer<>(1);
      rateLimitedBuffer.insert(1);

      // When
      int returnValue = rateLimitedBuffer.poll();

      // Then
      assertEquals(1, returnValue);
      assertEquals(0, rateLimitedBuffer.size());
   }

   @Test
   public void peekReturnsNull_When_Empty()
   {
      // Given
      RateLimitedBuffer<Integer> rateLimitedBuffer = new RateLimitedBuffer<>(1);

      // When
      Integer returnValue = rateLimitedBuffer.peek();

      // Then
      assertNull(returnValue);
   }

   @Test
   public void pollReturnsNull_When_Empty()
   {
      // Given
      RateLimitedBuffer<Integer> rateLimitedBuffer = new RateLimitedBuffer<>(1);

      // When
      Integer returnValue = rateLimitedBuffer.poll();

      // Then
      assertNull(returnValue);
   }

   @Test
   public void insertFails_When_BufferIsFullAndNonBlocking()
   {
      // Given
      RateLimitedBuffer<Integer> rateLimitedBuffer = new RateLimitedBuffer<>(new Object(), 1, _rateLimiter, false);

      // When
      rateLimitedBuffer.insert(1);

      // Then
      assertFalse(rateLimitedBuffer.insert(1));
      assertEquals(1, rateLimitedBuffer.size());
   }

   @Test
   public void insertBlocks_When_BufferIsFullAndBlocking()
   {
      // Given
      RateLimitedBuffer<Integer> rateLimitedBuffer = new RateLimitedBuffer<>(new Object(), 1, _rateLimiter, true);

      rateLimitedBuffer.insert(1);

      new Thread(() -> {
         try {
            Thread.sleep(300);
         } catch (InterruptedException ignore) {
         }
         rateLimitedBuffer.poll();
      }).start();

      // When
      long preInsert = System.currentTimeMillis();
      boolean returnValue = rateLimitedBuffer.insert(1);
      long postInsert = System.currentTimeMillis();

      // Then
      assertTrue(returnValue);
      assertTrue(postInsert - preInsert > 100);
   }

   @Test
   public void insertBlocks_When_BufferIsNonBlockingAndRateLimitedOnPreInsert()
   throws InterruptedException
   {
      // Given a non-blocking rate limited buffer
      RateLimitedBuffer<Integer> rateLimitedBuffer = new RateLimitedBuffer<>(new Object(), 1, _rateLimiter, false);
      CountDownLatch lockHasBeenAcquired = new CountDownLatch(1);

      // Where the pre-insert function is blocking for 300 ms
      new Thread(() -> {
         try {
            _rateLimiter.preInsertLock.acquire();
            lockHasBeenAcquired.countDown();
            Thread.sleep(300);
         } catch (InterruptedException ignore) {
         }
         _rateLimiter.preInsertLock.release();
      }).start();

      lockHasBeenAcquired.await();

      // When inserting
      long preInsert = System.currentTimeMillis();
      boolean returnValue = rateLimitedBuffer.insert(1);
      long postInsert = System.currentTimeMillis();


      // Then the time taken for insert should be non-instant
      assertTrue(returnValue);
      assertTrue(postInsert - preInsert > 100);
   }

   @Test
   public void insertBlocks_When_BufferIsNonBlockingAndRateLimitedOnPostInsert()
   throws InterruptedException
   {
      // Given a non-blocking rate limited buffer
      RateLimitedBuffer<Integer> rateLimitedBuffer = new RateLimitedBuffer<>(new Object(), 1, _rateLimiter, false);
      CountDownLatch lockHasBeenAcquired = new CountDownLatch(1);

      // Where the post-insert function is blocking for 300 ms
      new Thread(() -> {
         try {
            _rateLimiter.postInsertLock.acquire();
            lockHasBeenAcquired.countDown();
            Thread.sleep(300);
         } catch (InterruptedException ignore) {
         }
         _rateLimiter.postInsertLock.release();
      }).start();

      lockHasBeenAcquired.await();

      // When inserting
      long preInsert = System.currentTimeMillis();
      boolean returnValue = rateLimitedBuffer.insert(1);
      long postInsert = System.currentTimeMillis();

      // Then the time taken for insert should be non-instant
      assertTrue(returnValue);
      assertTrue(postInsert - preInsert > 100);
   }

   @Test
   public void insertBlocks_When_BufferIsBlockingAndEmptyAndRateLimitedOnPreInsert()
   throws InterruptedException
   {
      // Given a blocking, empty, rate limited buffer
      RateLimitedBuffer<Integer> rateLimitedBuffer = new RateLimitedBuffer<>(new Object(), 1, _rateLimiter, true);
      CountDownLatch lockHasBeenAcquired = new CountDownLatch(1);

      // Where the pre-insert function is blocking for 300 ms
      new Thread(() -> {
         try {
            _rateLimiter.preInsertLock.acquire();
            lockHasBeenAcquired.countDown();
            Thread.sleep(300);
         } catch (InterruptedException ignore) {
         }
         _rateLimiter.preInsertLock.release();
      }).start();

      lockHasBeenAcquired.await();

      // When inserting
      long preInsert = System.currentTimeMillis();
      boolean returnValue = rateLimitedBuffer.insert(1);
      long postInsert = System.currentTimeMillis();

      // Then the time taken for insert should be non-instant
      assertTrue(returnValue);
      assertTrue(postInsert - preInsert > 100);
   }

   @Test
   public void insertBlocks_When_BufferIsBlockingAndEmptyAndRateLimitedOnPostInsert()
   throws InterruptedException
   {
      // Given a blocking, empty, rate limited buffer
      RateLimitedBuffer<Integer> rateLimitedBuffer = new RateLimitedBuffer<>(new Object(), 1, _rateLimiter, true);
      CountDownLatch lockHasBeenAcquired = new CountDownLatch(1);

      // Where the post-insert function is blocking for 300 ms
      new Thread(() -> {
         try {
            _rateLimiter.postInsertLock.acquire();
            lockHasBeenAcquired.countDown();
            Thread.sleep(300);
         } catch (InterruptedException ignore) {
         }
         _rateLimiter.postInsertLock.release();
      }).start();

      lockHasBeenAcquired.await();

      // When inserting
      long preInsert = System.currentTimeMillis();
      boolean returnValue = rateLimitedBuffer.insert(1);
      long postInsert = System.currentTimeMillis();

      // Then the time taken for insert should be non-instant
      assertTrue(returnValue);
      assertTrue(postInsert - preInsert > 100);
   }

   private static class ControllableRateLimiter implements RateLimiter {

      public final Semaphore preInsertLock = new Semaphore(1);
      public final Semaphore postInsertLock = new Semaphore(1);

      @Override
      public void preInsert(int size)
      {
         try {
            preInsertLock.acquire();
         } catch (InterruptedException ignore) {
         }
         preInsertLock.release();
      }

      @Override
      public void postInsert(int size)
      {
         try {
            postInsertLock.acquire();
         } catch (InterruptedException ignore) {
         }
         postInsertLock.release();
      }
   }
}
