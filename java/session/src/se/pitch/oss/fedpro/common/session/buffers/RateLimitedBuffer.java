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
import se.pitch.oss.fedpro.common.session.flowcontrol.RateLimiter;
import net.jcip.annotations.GuardedBy;

import java.util.LinkedList;
import java.util.Queue;

public class RateLimitedBuffer<E> implements GenericBuffer<E> {

   private final Object _lock;
   private final int _capacity;
   @GuardedBy("_lock")
   private final Queue<E> _data;
   private final RateLimiter _limiter;
   private final boolean _blockWhenFull;
   private int _count;

   public RateLimitedBuffer(int capacity)
   {
      this(capacity, new NullRateLimiter());
   }

   public RateLimitedBuffer(
         int capacity,
         RateLimiter rateLimiter)
   {
      this(new Object(), capacity, rateLimiter, true);
   }

   public RateLimitedBuffer(
         Object lock,
         int capacity,
         RateLimiter limiter,
         boolean blockWhenFull)
   {
      _lock = lock;
      _capacity = capacity;
      _data = new LinkedList<>();
      _limiter = limiter;
      _count = 0;
      _blockWhenFull = blockWhenFull;
   }

   @Override
   public boolean insert(E element)
   {
      _limiter.preInsert(_count);
      synchronized (_lock) {
         if (_blockWhenFull) {
            while (_count >= _capacity) {
               try {
                  _lock.wait();
               } catch (InterruptedException ignore) {
               }
            }
         } else {
            if (_count >= _capacity) {
               return false;
            }
         }
         _data.add(element);
         _count++;
         _lock.notifyAll();
      }
      _limiter.postInsert(_count);
      return true;
   }

   @Override
   public E waitAndPoll()
   throws InterruptedException
   {
      synchronized (_lock) {
         while (isEmpty()) {
            _lock.wait();
         }
         return poll();
      }
   }

   @Override
   public E waitAndPeek()
   throws InterruptedException
   {
      synchronized (_lock) {
         while (isEmpty()) {
            _lock.wait();
         }
         return peek();
      }
   }

   @Override
   public boolean isEmpty()
   {
      synchronized (_lock) {
         return _count == 0;
      }
   }

   @Override
   public int capacity()
   {
      return _capacity;
   }

   @Override
   public int size()
   {
      synchronized (_lock) {
         return _count;
      }
   }

   @Override
   public E poll()
   {
      synchronized (_lock) {
         if (_count > 0) {
            _count--;
            _lock.notifyAll();
            return _data.poll();
         }
      }
      return null;
   }

   @Override
   public E peek()
   {
      synchronized (_lock) {
         if (_count > 0) {
            return _data.peek();
         }
      }
      return null;
   }

   @Override
   public boolean waitUntilEmpty(long timeoutMillis)
   throws InterruptedException
   {
      if (timeoutMillis == 0) {
         synchronized (_lock) {
            while (!isEmpty()) {
               _lock.wait();
            }
            return isEmpty();
         }
      } else {
         long waitEnd = System.currentTimeMillis() + timeoutMillis;
         synchronized (_lock) {
            long timeLeft = waitEnd - System.currentTimeMillis();
            while (!isEmpty() && timeLeft > 0) {
               _lock.wait(timeLeft);
               timeLeft = waitEnd - System.currentTimeMillis();
            }
            return isEmpty();
         }
      }
   }

   @Override
   public String toString()
   {
      synchronized (_lock) {
         return "RateLimitedBuffer {\n" + "\tCapacity = " + _capacity + ",\n\tData = " + _data + ",\n\tLimiter = " +
               _limiter + ",\n\tBlock when full = " + _blockWhenFull + ",\n\tCount = " + _count + "\n}";
      }
   }
}
