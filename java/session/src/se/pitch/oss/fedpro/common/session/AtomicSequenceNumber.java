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

// Thread-safe.

public class AtomicSequenceNumber extends SequenceNumber {

   public AtomicSequenceNumber(int value)
   {
      super(value);
   }

   public synchronized AtomicSequenceNumber increment()
   {
      super.increment();
      return this;
   }

   public synchronized void set(SequenceNumber newNumber)
   {
      super.set(newNumber);
   }

   public synchronized SequenceNumber set(int newValue)
   {
      return super.set(newValue);
   }

   public synchronized int get()
   {
      return super.get();
   }

   public synchronized SequenceNumber getAndSet(int newValue)
   {
      int oldValue = get();
      set(newValue);
      return new SequenceNumber(oldValue);
   }

}
