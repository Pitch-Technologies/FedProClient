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
import net.jcip.annotations.GuardedBy;

public class RoundRobinBuffer<E> implements GenericBuffer<E> {

   private final Object _sessionLock;
   private final GenericBuffer<E> _primaryQueue;
   private final GenericBuffer<E> _alternateQueue;
   private final QueueAlternator<E> _alternator;

   private final int _primaryFactor;
   private final int _alternateFactor;
   private final int _smallerFactorDoubled;
   private int _alternatorIndex;

   public RoundRobinBuffer(
         int capacity,
         RateLimiter limiter,
         QueueAlternator<E> alternator)
   {
      this(capacity, limiter, alternator, new Object(), 1, 1);
   }

   public RoundRobinBuffer(
         int capacity,
         RateLimiter limiter,
         QueueAlternator<E> alternator,
         Object sessionLock)
   {
      this(capacity, limiter, alternator, sessionLock, 1, 1);
   }

   public RoundRobinBuffer(
         int capacity,
         RateLimiter limiter,
         QueueAlternator<E> alternator,
         Object sessionLock,
         int primaryFactor,
         int alternateFactor)
   {
      _sessionLock = sessionLock;
      _primaryQueue = new RateLimitedBuffer<>(capacity, limiter, sessionLock, true);
      _alternateQueue = new UnboundedBuffer<>(sessionLock);
      _alternator = alternator;
      _primaryFactor = primaryFactor;
      _alternateFactor = alternateFactor;
      _smallerFactorDoubled = Math.min(_primaryFactor, _alternateFactor) * 2;
      _alternatorIndex = 1;
   }

   @Override
   public boolean insert(E element)
   {
      if (!_alternator.putInAlternateQueue(element)) {
         return _primaryQueue.insert(element);
      } else {
         return _alternateQueue.insert(element);
      }
   }

   @Override
   public E waitAndPoll()
   throws InterruptedException
   {
      synchronized (_sessionLock) {
         while (isEmpty()) {
            _sessionLock.wait();
         }
         return poll();
      }
   }

   @Override
   public E waitAndPeek()
   throws InterruptedException
   {
      synchronized (_sessionLock) {
         while (isEmpty()) {
            _sessionLock.wait();
         }
         return peek();
      }
   }

   @Override
   public E poll()
   {
      synchronized (_sessionLock) {
         E result = selectReadBuffer().poll();
         incrementAlternator();
         return result;
      }
   }

   @Override
   public E peek()
   {
      synchronized (_sessionLock) {
         return selectReadBuffer().peek();
      }
   }

   private void incrementAlternator()
   {
      _alternatorIndex++;
      if (_alternatorIndex > _primaryFactor + _alternateFactor) {
         _alternatorIndex = 1;
      }
   }

   @GuardedBy("_sessionLock")
   private GenericBuffer<E> selectReadBuffer()
   {
      if (_alternatorIndex <= _smallerFactorDoubled) {
         if (_alternatorIndex % 2 == 0) {
            return alternateQueueUnlessEmpty();
         } else {
            return primaryQueueUnlessEmpty();
         }
      } else if (_primaryFactor > _alternateFactor) {
         return primaryQueueUnlessEmpty();
      } else {
         return alternateQueueUnlessEmpty();
      }
   }

   private GenericBuffer<E> primaryQueueUnlessEmpty()
   {
      return _primaryQueue.isEmpty() ? _alternateQueue : _primaryQueue;
   }

   private GenericBuffer<E> alternateQueueUnlessEmpty()
   {
      return _alternateQueue.isEmpty() ? _primaryQueue : _alternateQueue;
   }

   @Override
   public int size()
   {
      return Math.max(_primaryQueue.size(), _alternateQueue.size());
   }

   @Override
   public boolean isEmpty()
   {
      return _primaryQueue.isEmpty() && _alternateQueue.isEmpty();
   }

   @Override
   public int capacity()
   {
      // Primary queue and alternate queue have the same capacity, and it does not make
      // sense to provide the sum here, even though we can, theoretically,
      // have twice this number of elements in the queue.
      return _primaryQueue.capacity();
   }

   @Override
   public boolean waitUntilEmpty(long timeoutMillis)
   throws InterruptedException
   {
      synchronized (_sessionLock) {
         _primaryQueue.waitUntilEmpty(timeoutMillis / 2);
         _alternateQueue.waitUntilEmpty(timeoutMillis / 2);
         return isEmpty();
      }
   }

   @Override
   public String toString()
   {
      return "RoundRobinBuffer{\n" + "_primaryQueue=" + _primaryQueue + "\n_alternateQueue=" + _alternateQueue +
            "\n_alternator=" + _alternator + "\n_primaryFactor=" + _primaryFactor + "\n_alternateFactor=" +
            _alternateFactor + "\n_alternatorIndex=" + _alternatorIndex + "\n}";
   }
}
