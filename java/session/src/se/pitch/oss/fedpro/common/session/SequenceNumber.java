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

// Because of its wrap around behavior, the sequence number will never be negative unless explicitly set to invalid
// sequence number.
// Not thread-safe.

import java.util.Objects;

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
   */
   public static final int NO_SEQUENCE_NUMBER = Integer.MIN_VALUE;
   public static final int INITIAL_SEQUENCE_NUMBER = 0;
   public static final int MAX_SEQUENCE_NUMBER = Integer.MAX_VALUE;

   private int _value;

   public SequenceNumber(int value)
   {
      _value = value;
   }

   public SequenceNumber(SequenceNumber other)
   {
      this(other._value);
   }

   public SequenceNumber increment()
   {
      _value = resetInvalidToInitialValue(_value + 1);
      return this;
   }

   public static int nextAfter(int sequenceNumber) {
      return resetInvalidToInitialValue(sequenceNumber + 1);
   }

   public static int sum(int a, int b)
   {
      return wrapIntoValidRange(a + b);
   }

   public void set(SequenceNumber newNumber)
   {
      set(newNumber.get());
   }

   public SequenceNumber set(int newValue)
   {
      _value = newValue;
      return this;
   }

   public int get()
   {
      return _value;
   }

   private static int wrapIntoValidRange(int value)
   {
      // Sets the most significant bit to 0.
      return value & ~(1 << 31);
   }

   private static int resetInvalidToInitialValue(int value)
   {
      return Math.max(value, INITIAL_SEQUENCE_NUMBER);
   }

   protected static void validateArguments(int... values)
   {
      for (int value : values) {
         if (!isValidAsSequenceNumber(value)) {
            throw new IllegalArgumentException("Sequence number argument must be positive");
         }
      }
   }

   public static boolean isValidAsSequenceNumber(int value)
   {
      return value > -1;
   }

   public static boolean inInterval(
         int candidate,
         int oldest,
         int newest)
   {
      validateArguments(oldest, candidate, newest);
      if (oldest <= newest) {
         // When history is linear.
         return oldest <= candidate && candidate <= newest;
      } else {
         // When wrap around happens in history.
         return candidate <= newest || oldest <= candidate;
      }
   }

   public static boolean notInInterval(
         int candidate,
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

   @Override
   public boolean equals(Object o)
   {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }
      SequenceNumber that = (SequenceNumber) o;
      return _value == that._value;
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(_value);
   }
}
