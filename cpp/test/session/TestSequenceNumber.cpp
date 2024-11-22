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

#include <session/SequenceNumber.h>

#include <gtest/gtest.h>

class TestSequenceNumber : public ::testing::Test
{
};

TEST_F(TestSequenceNumber, NO_SEQUENCE_NUMBER_hasCorrectValue)
{
   ASSERT_EQ(INT32_MIN, FedPro::SequenceNumber::NO_SEQUENCE_NUMBER);
}

TEST_F(TestSequenceNumber, MAX_SEQUENCE_NUMBER_hasCorrectValue)
{
   ASSERT_EQ(INT32_MAX, FedPro::SequenceNumber::MAX_SEQUENCE_NUMBER);
}

TEST_F(TestSequenceNumber, INITIAL_SEQUENCE_NUMBER_hasCorrectValue)
{
   ASSERT_EQ(0, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER);
}

TEST_F(TestSequenceNumber, doesNotThrow_When_initializingWithSmallestPossibleValue)
{
   FedPro::SequenceNumber{0};
}

TEST_F(TestSequenceNumber, doesNotThrow_When_initializingWithGreatestPossibleValue)
{
   FedPro::SequenceNumber{FedPro::SequenceNumber::MAX_SEQUENCE_NUMBER};
}

TEST_F(TestSequenceNumber, doesNotThrow_When_initializingWithInvalidSequenceNumber)
{
   FedPro::SequenceNumber{FedPro::SequenceNumber::NO_SEQUENCE_NUMBER};
}

TEST_F(TestSequenceNumber, doesNotThrow_When_initializingWithGreatestInvalidSequenceNumber)
{
   FedPro::SequenceNumber{-1};
}

TEST_F(TestSequenceNumber, sequenceNumberIsIncrementedBy1_When_IncrementIsCalled)
{
   // Given
   FedPro::SequenceNumber num{20};

   // When
   num.increment();

   // Then
   ASSERT_EQ(21, num.get());
}

TEST_F(TestSequenceNumber, sequenceNumberWrapsAround_When_GreatestPossibleValueIsIncremented)
{
   // Given
   FedPro::SequenceNumber num{FedPro::SequenceNumber::MAX_SEQUENCE_NUMBER};

   // When
   num.increment();

   // Then
   ASSERT_EQ(FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER, num.get());
}

TEST_F(TestSequenceNumber, sequenceNumberGetsCorrectValue_When_SetIsCalled)
{
   // Given
   FedPro::SequenceNumber num{0};

   // When
   num.set(50);

   // Then
   ASSERT_EQ(50, num.get());
}

TEST_F(TestSequenceNumber, setSets_When_InvalidSequenceNumberIsSpecified)
{
   // Given
   FedPro::SequenceNumber num{0};

   // When
   num.set(-1);

   // Then
   ASSERT_EQ(-1, num.get());
}

TEST_F(TestSequenceNumber, nextNumberIsObtained_When_NextAfterIsCalled)
{
   // Given
   int32_t num{20};

   // When
   int32_t nextNumber = FedPro::SequenceNumber::nextAfter(num);

   // Then
   ASSERT_EQ(21, nextNumber);
}

TEST_F(TestSequenceNumber, zeroIsObtained_When_NextAfterIsCalledAtMaxValue)
{
   // Given
   int32_t num{FedPro::SequenceNumber::MAX_SEQUENCE_NUMBER};

   // When
   int32_t nextNumber = FedPro::SequenceNumber::nextAfter(num);

   // Then
   ASSERT_EQ(0, nextNumber);
}

TEST_F(TestSequenceNumber, nextNumberIsObtained_When_NextAfterIsCalledAtInvalidSequenceNumber)
{
   // Given
   int32_t num{FedPro::SequenceNumber::NO_SEQUENCE_NUMBER};

   // When
   int32_t nextNumber = FedPro::SequenceNumber::nextAfter(num);

   // Then
   ASSERT_EQ(0, nextNumber);
}

TEST_F(TestSequenceNumber, maxSequenceNumber_is_ValidSequenceNumber)
{
   // Given
   FedPro::SequenceNumber num{FedPro::SequenceNumber::MAX_SEQUENCE_NUMBER};

   // Then
   ASSERT_EQ(FedPro::SequenceNumber::MAX_SEQUENCE_NUMBER, num.get());
}

TEST_F(TestSequenceNumber, maxSequenceNumber_is_MaxSequenceNumber)
{
   // Given
   FedPro::SequenceNumber num{FedPro::SequenceNumber::MAX_SEQUENCE_NUMBER};

   // When
   num.increment();

   // Then
   ASSERT_EQ(FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER, num.get());
}
