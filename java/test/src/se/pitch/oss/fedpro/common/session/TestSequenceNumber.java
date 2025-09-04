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

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static se.pitch.oss.fedpro.common.session.SequenceNumber.*;

public class TestSequenceNumber {

   private SequenceNumber _num;

   @Test
   public void NO_SEQUENCE_NUMBER_hasCorrectValue()
   {
      assertEquals(Integer.MIN_VALUE, NO_SEQUENCE_NUMBER);
   }

   @Test
   public void MAX_SEQUENCE_NUMBER_hasCorrectValue()
   {
      assertEquals(Integer.MAX_VALUE, MAX_SEQUENCE_NUMBER);
   }

   @Test
   public void INITIAL_SEQUENCE_NUMBER_hasCorrectValue()
   {
      assertEquals(0, INITIAL_SEQUENCE_NUMBER);
   }

   @Test
   public void doesNotThrow_When_initializingWithSmallestPossibleValue()
   {
      _num = new SequenceNumber(0);
   }

   @Test
   public void doesNotThrow_When_initializingWithGreatestPossibleValue()
   {
      _num = new SequenceNumber(Integer.MAX_VALUE);
   }

   @Test
   public void doesNotThrow_When_initializingWithInvalidSequenceNumber()
   {
      _num = new SequenceNumber(NO_SEQUENCE_NUMBER);
   }

   @Test
   public void doesNotThrow_When_initializingWithGreatestNotAllowedValue()
   {
      _num = new SequenceNumber(-1);
   }

   @Test
   public void sequenceNumberIsIncrementedBy1_When_IncrementIsCalled()
   {
      // Given
      _num = new SequenceNumber(20);

      // When
      _num.increment();

      // Then
      assertEquals(21, _num.get());
   }

   @Test
   public void incrementingCorrectly_When_IncrementingFromInvalidSequenceNumber()
   {
      // Given
      _num = new SequenceNumber(-1000);

      // When
      _num.increment();

      // Then
      assertEquals(INITIAL_SEQUENCE_NUMBER, _num.get());
   }

   @Test
   public void incrementingCorrectly_When_IncrementingMultipleTimesStartingAtMaxValue()
   {
      // Given
      _num = new SequenceNumber(Integer.MAX_VALUE);

      // When
      _num.increment();
      _num.increment();

      // Then
      assertEquals(1, _num.get());
   }

   @Test
   public void sequenceNumberWrapsAround_When_GreatestPossibleValueIsIncremented()
   {
      // Given
      _num = new SequenceNumber(Integer.MAX_VALUE);

      // When
      _num.increment();

      // Then
      assertEquals(0, _num.get());
   }

   @Test
   public void sequenceNumberGetsCorrectValue_When_SetIsCalled()
   {
      // Given
      _num = new SequenceNumber(0);

      // When
      _num.set(50);

      // Then
      assertEquals(50, _num.get());
   }

   @Test
   public void sequenceNumberGetsCorrectValue_When_InvalidSequenceNumberIsSpecified()
   {
      // Given
      _num = new SequenceNumber(0);

      // When
      _num.set(-1);

      // Then
      assertEquals(-1, _num.get());
   }

   @Test
   public void maxSequenceNumber_is_ValidSequenceNumber()
   {
      // Given
      _num = new SequenceNumber(MAX_SEQUENCE_NUMBER);

      // Then
      assertEquals(MAX_SEQUENCE_NUMBER, _num.get());
   }

   @Test
   public void maxSequenceNumber_is_MaxSequenceNumber()
   {
      // Given
      _num = new SequenceNumber(MAX_SEQUENCE_NUMBER);

      // When
      _num.increment();

      // Then
      assertEquals(INITIAL_SEQUENCE_NUMBER, _num.get());
   }

   @Test
   public void sumWorks_When_CloseToWrap()
   {
      // Given
      int a = MAX_SEQUENCE_NUMBER - 1;
      int b = 10;

      // When
      int c = SequenceNumber.sum(a, b);

      // Then
      assertEquals(8, c);
   }

   @Test
   public void sumWorks_When_NotWrapping()
   {
      // Given
      int a = 50;
      int b = 5;

      // When
      int c = SequenceNumber.sum(a, b);

      // Then
      assertEquals(a + b, c);
   }
}
