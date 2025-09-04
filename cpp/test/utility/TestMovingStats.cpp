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

// Silence clang-tidy issues reported for gtest macros.
// NOLINTBEGIN(cppcoreguidelines-avoid-non-const-global-variables)

#include "utility/MovingStats.h"
#include "utility/StandAloneMovingStats.h"

#include <gtest/gtest.h>

using namespace FedPro;

class TestMovingStats : public ::testing::Test
{
public:

protected:

   using SteadyTimePoint = MovingStats::SteadyTimePoint;

   static SteadyTimePoint makeTime(FedProDuration timeMillis)
   {
      auto clockDuration = std::chrono::duration_cast<SteadyTimePoint::duration>(timeMillis);
      return SteadyTimePoint{clockDuration};
   }

   static SteadyTimePoint makeTime(uint64_t timeMillis)
   {
      return makeTime(FedProDuration{timeMillis});
   }
};

TEST_F(TestMovingStats, testMin)
{
   // Given
   StandAloneMovingStats ms(FedProDuration{60000}, FedProDuration{1000}, makeTime(0));
   ms.sample(2, makeTime(500));
   ms.sample(2, makeTime(500));
   ms.sample(0, makeTime(1500));
   ms.sample(5, makeTime(1500));
   // When
   MovingStats::Stats stats = ms.getStatsForTime(FedProDuration{2000}, makeTime(2000));
   // Then
   ASSERT_EQ(4, stats._minBucket);
   ASSERT_EQ(0, stats._minSample);
}

TEST_F(TestMovingStats, testMin_Given_rollover)
{
   // Given
   StandAloneMovingStats ms(FedProDuration{1000}, FedProDuration{1000}, makeTime(0));
   ms.sample(2, makeTime(500));
   ms.sample(0, makeTime(500));
   ms.sample(2, makeTime(1500));
   ms.sample(5, makeTime(1500));
   // When
   MovingStats::Stats stats = ms.getStatsForTime(FedProDuration{2000}, makeTime(2000));
   // Then
   ASSERT_EQ(4, stats._historicCount);
   ASSERT_EQ(2, stats._sampleCount);
   ASSERT_EQ(0, stats._minBucket);
   ASSERT_EQ(2, stats._minSample);
}

TEST_F(TestMovingStats, testMax)
{
   // Given
   StandAloneMovingStats ms(FedProDuration{60000}, FedProDuration{1000}, makeTime(0));
   ms.sample(3, makeTime(500));
   ms.sample(4, makeTime(500));
   ms.sample(0, makeTime(1500));
   ms.sample(5, makeTime(1500));
   // When
   MovingStats::Stats stats = ms.getStatsForTime(FedProDuration{2000}, makeTime(2000));
   // Then
   ASSERT_EQ(7, stats._maxBucket);
   ASSERT_EQ(5, stats._maxSample);
}

TEST_F(TestMovingStats, testNegativeMax)
{
   // Given
   StandAloneMovingStats ms(FedProDuration{60000}, FedProDuration{1000}, makeTime(0));
   ms.sample(-3, makeTime(500));
   ms.sample(-4, makeTime(500));
   ms.sample(-5, makeTime(1500));
   // When
   MovingStats::Stats stats = ms.getStatsForTime(FedProDuration{2000}, makeTime(2000));
   // Then
   ASSERT_EQ(-5, stats._maxBucket);
   ASSERT_EQ(-3, stats._maxSample);
}

TEST_F(TestMovingStats, testAverage)
{
   // Given
   StandAloneMovingStats ms(FedProDuration{60000}, FedProDuration{1000}, makeTime(0));
   ms.sample(3, makeTime(500));
   ms.sample(4, makeTime(500));
   ms.sample(0, makeTime(1500));
   ms.sample(5, makeTime(1500));
   // When
   MovingStats::Stats stats = ms.getStatsForTime(FedProDuration{2000}, makeTime(2000));
   // Then
   ASSERT_FLOAT_EQ(3, stats._averageSample);
   ASSERT_FLOAT_EQ(6, stats._averageBucket);
}

TEST_F(TestMovingStats, testSum)
{
   StandAloneMovingStats ms(FedProDuration{60000}, FedProDuration{1000}, makeTime(0));
   ms.sample(3, makeTime(500));
   ms.sample(4, makeTime(500));
   ms.sample(0, makeTime(1500));
   ms.sample(5, makeTime(1500));
   MovingStats::Stats stats = ms.getStatsForTime(FedProDuration{2000}, makeTime(2000));
   ASSERT_EQ(12, stats._sum);
}

TEST_F(TestMovingStats, testSum_Given_rollover)
{
   // Given
   StandAloneMovingStats ms(FedProDuration{1000}, FedProDuration{1000}, makeTime(0));
   ms.sample(2, makeTime(500));
   ms.sample(0, makeTime(500));
   ms.sample(2, makeTime(1500));
   ms.sample(5, makeTime(1500));
   // When
   MovingStats::Stats stats = ms.getStatsForTime(FedProDuration{2000}, makeTime(2000));
   // Then
   ASSERT_EQ(4, stats._historicCount);
   ASSERT_EQ(2, stats._sampleCount);
   ASSERT_EQ(7, stats._sum);
   ASSERT_EQ(9, stats._historicTotal);

}

TEST_F(TestMovingStats, testCount)
{
   StandAloneMovingStats ms(FedProDuration{60000}, FedProDuration{1000}, makeTime(0));
   ms.sample(3, makeTime(500));
   ms.sample(4, makeTime(500));
   ms.sample(0, makeTime(1500));
   ms.sample(5, makeTime(1500));
   MovingStats::Stats stats = ms.getStatsForTime(FedProDuration{2000}, makeTime(2000));
   ASSERT_EQ(2, stats._bucketCount);
   ASSERT_EQ(4, stats._sampleCount);
}

TEST_F(TestMovingStats, testMinWithGap)
{
   StandAloneMovingStats ms(FedProDuration{60000}, FedProDuration{1000}, makeTime(0));
   ms.sample(3, makeTime(500));
   ms.sample(5, makeTime(2500));
   MovingStats::Stats stats = ms.getStatsForTime(FedProDuration{3000}, makeTime(3000));
   ASSERT_EQ(0, stats._minBucket);
   ASSERT_EQ(3, stats._minSample);
}

TEST_F(TestMovingStats, testNegativeMinWithGap)
{
   StandAloneMovingStats ms(FedProDuration{60000}, FedProDuration{1000}, makeTime(0));
   ms.sample(-3, makeTime(500));
   ms.sample(-5, makeTime(2500));
   MovingStats::Stats stats = ms.getStatsForTime(FedProDuration{3000}, makeTime(3000));
   ASSERT_EQ(-5, stats._minBucket);
   ASSERT_EQ(-5, stats._minSample);
}

TEST_F(TestMovingStats, testCountWithGap)
{
   StandAloneMovingStats ms(FedProDuration{60000}, FedProDuration{1000}, makeTime(0));
   ms.sample(1, makeTime(500));
   ms.sample(1, makeTime(2500));
   MovingStats::Stats stats = ms.getStatsForTime(FedProDuration{3000}, makeTime(3000));
   ASSERT_EQ(3, stats._bucketCount);
   ASSERT_EQ(2, stats._sampleCount);
}

TEST_F(TestMovingStats, testRollover)
{
   StandAloneMovingStats ms(FedProDuration{60000}, FedProDuration{1000}, makeTime(0));
   ms.sample(10, makeTime(500));
   ms.sample(-5, makeTime(500));
   MovingStats::Stats stats = ms.getStatsForTime(FedProDuration{60000}, makeTime(120000));
   ASSERT_EQ(60, stats._bucketCount);
   ASSERT_EQ(0, stats._sampleCount);
   ASSERT_EQ(0, stats._minBucket);
   ASSERT_EQ(0, stats._maxBucket);
   ASSERT_FLOAT_EQ(0, stats._averageSample);
   ASSERT_FLOAT_EQ(0, stats._averageBucket);
   ASSERT_EQ(0, stats._sum);
}

TEST_F(TestMovingStats, testOldReportDoesNotAffectNewReport)
{
   // Given
   StandAloneMovingStats ms(FedProDuration{5000}, FedProDuration{1000}, makeTime(0));
   ms.sample(1, makeTime(500));
   ms.sample(2, makeTime(1500));
   ms.sample(3, makeTime(2500));
   ms.sample(4, makeTime(3500));
   ms.sample(5, makeTime(4500));

   MovingStats::Stats oldstats = ms.getStatsForTime(FedProDuration{2000}, makeTime(3000));
   ASSERT_EQ(2, oldstats._bucketCount);
   ASSERT_EQ(2, oldstats._sampleCount);
   ASSERT_EQ(3, oldstats._maxBucket);
   ASSERT_FLOAT_EQ(2.5, oldstats._averageBucket);

   // When
   MovingStats::Stats newstats = ms.getStatsForTime(FedProDuration{5000}, makeTime(5000));

   // Then
   ASSERT_EQ(5, newstats._bucketCount);
   ASSERT_EQ(5, newstats._sampleCount);
   ASSERT_EQ(5, newstats._maxBucket);
   ASSERT_EQ(15, newstats._sum);
   ASSERT_FLOAT_EQ(3, newstats._averageBucket);
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)
