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

import static se.pitch.oss.fedpro.common.session.SequenceNumber.NO_SEQUENCE_NUMBER;
import static org.junit.Assert.assertEquals;

public class TestAtomicSequenceNumber {

   private AtomicSequenceNumber _num;

   @Test
   public void oldValueIsReturned_When_GetAndSetIsCalled()
   {
      // Given
      _num = new AtomicSequenceNumber(40);

      // When
      SequenceNumber oldNumber = _num.getAndSet(60);

      // Then
      assertEquals(40, oldNumber.get());
   }

   @Test
   public void sequenceNumberGetsNewValue_When_GetAndSetIsCalled()
   {
      // Given
      _num = new AtomicSequenceNumber(40);

      // When
      _num.getAndSet(60);

      // Then
      assertEquals(60, _num.get());
   }

   @Test
   public void getAndSetSets_When_InvalidSequenceNumberIsSpecified()
   {
      // Given
      _num = new AtomicSequenceNumber(40);

      // When
      _num.getAndSet(NO_SEQUENCE_NUMBER);

      // Then
      assertEquals(NO_SEQUENCE_NUMBER, _num.get());
   }

}
