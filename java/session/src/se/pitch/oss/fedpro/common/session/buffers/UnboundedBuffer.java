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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class UnboundedBuffer<E> implements GenericBuffer<E> {

   private final BlockingQueue<E> _buffer = new LinkedBlockingQueue<>();
   private final Object _lock;

   public UnboundedBuffer()
   {
      this(new Object());
   }

   public UnboundedBuffer(Object lock)
   {
      _lock = lock;
   }

   @Override
   public E waitAndPoll()
   throws InterruptedException
   {
      throw new RuntimeException("Not implemented");
   }

   @Override
   public E waitAndPeek()
   throws InterruptedException
   {
      throw new RuntimeException("Not implemented");
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
   public E poll()
   {
      E retVal = _buffer.poll();
      synchronized (_lock) {
         _lock.notifyAll();
      }
      return retVal;
   }

   @Override
   public E peek()
   {
      return _buffer.peek();
   }

   @Override
   public int size()
   {
      return _buffer.size();
   }

   @Override
   public boolean isEmpty()
   {
      return _buffer.isEmpty();
   }

   @Override
   public int capacity()
   {
      return Integer.MAX_VALUE;
   }

   @Override
   public boolean insert(E element)
   {
      try {
         _buffer.put(element);
         synchronized (_lock) {
            _lock.notifyAll();
         }
         return true;
      } catch (InterruptedException e) {
         return false;
      }
   }
}
