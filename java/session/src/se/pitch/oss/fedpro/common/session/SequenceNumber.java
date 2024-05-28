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

// Because of its wrap around behavior, the sequence number will never be negative. (Not true until we revert the commit)
// Not thread safe.

public class SequenceNumber {

   /*
      NO_SEQUENCE_NUMBER:
      Binary:     10000000 0000000 00000000 00000000
      Hex:        80 00 00 00
      Decimal:    2 147 483 648
      java int:  -2 147 483 648     (=Integer.MIN_VALUE)

      Highest possible sequence number:
      Binary:     01111111 11111111 11111111 11111111
      Hex:        7F FF FF FF
      Decimal:    2 147 483 647
      java int:   2 147 483 647     (=Integer.MAX_VALUE)

      (Not true until we revert the commit)
   */
   public static final int NO_SEQUENCE_NUMBER = 0;
   public static final int MAX_SEQUENCE_NUMBER = -1;
   public static final int INITIAL_SEQUENCE_NUMBER = 1;

   private int _value;

   public SequenceNumber()
   {
      _value = INITIAL_SEQUENCE_NUMBER;
   }

   public SequenceNumber(int value)
   {
      validateArgument(value);
      _value = value;
   }

   public SequenceNumber increment()
   {
      return add(1);
   }

   public SequenceNumber decrement()
   {
      return subtract(1);
   }

   public SequenceNumber add(int valueToAdd)
   {
      if (valueToAdd < 0) {
         return subtract(-valueToAdd);
      }

      int preliminaryResult = _value + valueToAdd;
      if (inInterval(NO_SEQUENCE_NUMBER, _value, preliminaryResult)) {
         incrementAndSet(_value + valueToAdd + 1);
      } else {
         incrementAndSet(_value + valueToAdd);
      }

      return this;
   }

   public SequenceNumber subtract(int valueToSubtract)
   {
      if (valueToSubtract < 0) {
         return add(-valueToSubtract);
      }

      int preliminaryResult = _value - valueToSubtract;
      if (inInterval(NO_SEQUENCE_NUMBER, preliminaryResult, _value)) {
         decrementAndSet(_value - valueToSubtract - 1);
      } else {
         decrementAndSet(_value - valueToSubtract);
      }

      return this;
   }

   private void incrementAndSet(int x)
   {
      _value = incrementIf0(x);
   }

   private void decrementAndSet(int x)
   {
      _value = decrementIf0(x);
   }

   public void set(SequenceNumber newNumber)
   {
      set(newNumber.get());
   }

   public void set(int newValue)
   {
      validateArgument(newValue);
      _value = newValue;
   }

   public int get()
   {
      return _value;
   }

   public int getDistanceFrom(SequenceNumber otherNumber)
   {
      int other = otherNumber.get();
      if (other == _value) {
         return 0;
      }

      int preliminaryResult = _value - other;
      if (inInterval(NO_SEQUENCE_NUMBER, preliminaryResult, _value)) {
         return decrementIf0(_value - other - 1);
      } else {
         return decrementIf0(_value - other);
      }
   }

   public int getNext()
   {
      return incrementIf0(_value + 1);
   }

   public int getPrevious()
   {
      return decrementIf0(_value - 1);
   }

   private int incrementIf0(int value)
   {
      if (value == 0) {
         return value + 1;
      }
      return value;
   }

   private int decrementIf0(int value)
   {
      if (value == 0) {
         return value - 1;
      }
      return value;
   }

   protected void validateArgument(int value)
   {
      if (!isValidAsSequenceNumber(value)) {
         throw new IllegalArgumentException("Sequence number argument must be positive");
      }
   }

   public static boolean isValidAsSequenceNumber(int value)
   {
      return value != 0;
   }

   public static boolean inInterval(int candidate,
                                    int oldest,
                                    int newest)
   {
      if (oldest <= newest) {
         // When history is linear
         return oldest <= candidate && candidate <= newest;
      } else {
         // When wrap around happens in history
         return candidate <= newest || oldest <= candidate;
      }
   }

   public static boolean notInInterval(int candidate,
                                       int oldest,
                                       int newest)
   {
      return !inInterval(candidate, oldest, newest);
   }

   @Override
   public String toString()
   {
      return String.valueOf(_value);
   }
}
