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

#pragma once

#include "MovingStats.h"

#include <mutex>
#include <vector>

namespace FedPro
{
   class StandAloneMovingStats : public MovingStats
   {
   public:

      using Sample = int;

      static constexpr const FedProDuration DEFAULT_MILLIS_PER_BUCKET{1000};

      explicit StandAloneMovingStats(
            FedProDuration maxTimeWindowMillis);

      StandAloneMovingStats(
            FedProDuration maxTimeWindowMillis,
            SteadyTimePoint startTime);

      StandAloneMovingStats(
            FedProDuration maxTimeWindowMillis,
            FedProDuration millisPerBucket,
            SteadyTimePoint startTime);

      void sample(int value) override;

      void sample(
            int value,
            SteadyTimePoint time) override;

      constexpr size_t getBucketCount() const
      {
         return _bucketCount;
      }

      Stats getStats() override;

      Stats getStats(FedProDuration timeWindowMillis) override;

      Stats getStatsForTime(
            FedProDuration timeWindowMillis,
            SteadyTimePoint time) override;

      Stats getStatsForTime(SteadyTimePoint time) override;

   private:

      using SampleConstIterator = std::vector<StandAloneMovingStats::Sample>::const_iterator;
      using SampleRange = std::pair<SampleConstIterator, SampleConstIterator>;
      using SampleRangePair = std::pair<SampleRange, SampleRange>;

      void resetBucketsBeforeUsing(size_t bucketToUse); // REQUIRES(_guard)

      size_t getMaxBucketCount() const;

      size_t getBucket(SteadyTimePoint time) const;

      size_t nextBucket(size_t bucket) const;

      SampleRangePair wrappedRanges(
            const std::vector<Sample> & src,
            size_t firstElement,
            size_t count) const;

      static Sample sum(SampleRangePair ranges);

      static Sample max(SampleRangePair ranges);

      static Sample min(SampleRangePair ranges);

      const size_t _bucketCount;
      const FedProDuration _millisPerBucket;

      mutable std::mutex _guard;

      std::vector<Sample> _maxPerBucket; // GUARDED_BY(_guard)
      std::vector<Sample> _minPerBucket; // GUARDED_BY(_guard)
      std::vector<Sample> _sumPerBucket; // GUARDED_BY(_guard)
      std::vector<int> _sampleCountPerBucket; // GUARDED_BY(_guard)

      long _historicTotal{0};
      long _historicCount{0};

      SteadyTimePoint _startTime;
      size_t _nextBucket{0}; // GUARDED_BY(_guard)

   };

} // FedPro