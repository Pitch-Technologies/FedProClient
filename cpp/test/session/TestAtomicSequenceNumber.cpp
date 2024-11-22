/***********************************************************************
  Copyright (C) 2023 Pitch Technologies AB

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 **********************************************************************/

#include <session/AtomicSequenceNumber.h>

#include <gtest/gtest.h>

class TestAtomicSequenceNumber : public ::testing::Test
{
};

TEST_F(TestAtomicSequenceNumber, oldValueIsReturned_When_GetAndSetIsCalled)
{
   // Given
   FedPro::AtomicSequenceNumber num{40};

   // When
   FedPro::SequenceNumber oldNumber = num.getAndSet(60);

   // Then
   ASSERT_EQ(40, oldNumber.get());
}

TEST_F(TestAtomicSequenceNumber, sequenceNumberGetsNewValue_When_GetAndSetIsCalled)
{
   // Given
   FedPro::AtomicSequenceNumber num{40};

   // When
   num.getAndSet(60);

   // Then
   ASSERT_EQ(60, num.get());
}

TEST_F(TestAtomicSequenceNumber, getAndSetSets_When_InvalidSequenceNumberIsSpecified)
{
   // Given
   FedPro::AtomicSequenceNumber num{40};

   // When
   num.getAndSet(FedPro::SequenceNumber::NO_SEQUENCE_NUMBER);

   // Then
   ASSERT_EQ(FedPro::SequenceNumber::NO_SEQUENCE_NUMBER, num.get());
}
