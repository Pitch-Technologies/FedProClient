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

import net.jcip.annotations.GuardedBy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A thread-safe, circular buffer of objects.
 * <p>
 * On overflow, new objects inserted into the buffer will silently overwrite old ones, even if they have not been
 * read yet.
 *
 * @param <E> The type of objects in the buffer
 */
public class CircularBuffer<E> implements GenericBuffer<E> {

   private final Object _lock = new Object();
   private final int _capacity;
   @GuardedBy("_lock")
   private final E[] _data;
   @GuardedBy("_lock")
   private int _writeIndex;
   @GuardedBy("_lock")
   private int _count;
   private boolean _hasRotated = false;

   @SuppressWarnings("unchecked")
   public CircularBuffer(int capacity)
   {
      _capacity = capacity;
      _data = (E[]) new Object[_capacity];
      _count = 0;
      _writeIndex = -1;
   }

   private int inc(int index)
   {
      return (index + 1) % _capacity;
   }

   @GuardedBy("_lock")
   private int readIndex()
   {
      int readIndex = _writeIndex - (_count - 1);
      if (readIndex < 0) {
         readIndex += _capacity;
      }
      return readIndex % _capacity;
   }

   /**
    * Insert an element at the end of the buffer.
    * <p>
    * Note that this may overwrite old elements, even if they have not been read from the buffer yet.
    *
    * @param element The element to insert
    */
   @Override
   public boolean insert(E element)
   {
      synchronized (_lock) {
         int nextWriteIndex = inc(_writeIndex);
         _data[nextWriteIndex] = element;
         _writeIndex = nextWriteIndex;
         if (_writeIndex >= (_capacity - 1)) {
            _hasRotated = true;
         }
         if (_count < _capacity) {
            _count++;
         }
         _lock.notifyAll();
      }
      return true;
   }

   public E peekOldest()
   {
      // Returns null if buffer has always been empty
      synchronized (_lock) {
         return _data[oldestIndex()];
      }
   }

   @GuardedBy("_lock")
   private int oldestIndex()
   {
      return _hasRotated ? inc(_writeIndex) : 0;
   }

   // Todo - Delete this method or do we need it in the future?
   public int availableHistoric()
   {
      synchronized (_lock) {
         int historySize = readIndex() - oldestIndex();
         return historySize < 0 ? historySize + _capacity : historySize;
      }
   }

   public E peekNewest()
   {
      synchronized (_lock) {
         if (_writeIndex > -1) {
            return _data[_writeIndex];
         }
      }
      return null;
   }

   @Override
   public E peek()
   {
      synchronized (_lock) {
         if (_count > 0) {
            return _data[readIndex()];
         }
      }
      return null;
   }

   @Override
   public E poll()
   {
      synchronized (_lock) {
         if (_count > 0) {
            E element = _data[readIndex()];
            _count--;
            _lock.notifyAll();
            return element;
         }
      }
      return null;
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
      // TODO: Find out why waitAndPeek sometimes returns null
      synchronized (_lock) {
         while (isEmpty()) {
            _lock.wait();
         }
         return peek();
      }
   }

   public void rewindBy(int numSteps)
   {
      synchronized (_lock) {
         if (numSteps + _count > _capacity) {
            throw new IllegalArgumentException("Tried to rewind past the bounds of the buffer.");
         }
         if (!_hasRotated && numSteps > readIndex()) {
            throw new IllegalArgumentException(
                  "Tried to rewind " + numSteps + " elements but buffer only had " + (_writeIndex + 1) + ".");
         }
         _count += numSteps;
      }
   }

   public void rewindTo(ElementFinder<E> elementFinder)
   {
      // This functions supports gap in the rewind order.
      // It still requires sequence numbers to be incrementing.
      // There could be a case made to use a binary search to find the correct element.
      // But as this function will seldom be used, no such optimization has been done.
      synchronized (_lock) {
         int originalCount = _count;
         int highestValidIndex = _capacity;

         if (!_hasRotated) {
            // Special case where we have not rotated yet.
            // The only valid indexes are previously read indexes, these are all lower than the initial readIndex()
            highestValidIndex = readIndex();
         } else if (_count == 0) {
            // If count == 0, readIndex will return the oldest recorded element instead of the oldest unread element
            // This is only relevant after wraparound has occurred
            _count++;
         }
         while (true) {
            int index = readIndex();

            if (index < highestValidIndex && elementFinder.isCorrectElement(_data[index])) {
               // _count is now the correct value, next read will give the element indicated by elementFinder
               return;
            }
            _count++;
            if (_count > _capacity) {
               _count = originalCount;
               throw new IllegalArgumentException("Tried to rewind past the bounds of the buffer.");
            }
         }
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
   public boolean isEmpty()
   {
      synchronized (_lock) {
         return _count == 0;
      }
   }

   public boolean isFull()
   {
      synchronized (_lock) {
         return _count >= _capacity;
      }
   }

   public List<E> copy()
   {
      synchronized (_lock) {
         List<E> list = new ArrayList<>(_count);
         int readIndex = readIndex();
         for (int i = 0; i < _count; i++) {
            list.add(_data[readIndex]);
            readIndex = inc(readIndex);
         }
         return list;
      }
   }

   @Override
   public String toString()
   {
      synchronized (_lock) {
         return "CircularBuffer {\n" + "\tCapacity = " + _capacity + ",\n\tData = " + Arrays.toString(_data) +
               ",\n\tWrite index = " + _writeIndex + ",\n\tCount = " + _count + ",\n\tRotated = " + _hasRotated + "\n}";
      }
   }
}
