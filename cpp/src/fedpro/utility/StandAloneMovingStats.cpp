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

#include "StandAloneMovingStats.h"

#include <algorithm>
#include <chrono>
#include <limits>
#include <stdexcept>

namespace FedPro
{
   constexpr const FedProDuration StandAloneMovingStats::DEFAULT_MILLIS_PER_BUCKET;

   StandAloneMovingStats::StandAloneMovingStats(
         FedProDuration maxTimeWindowMillis)
         : StandAloneMovingStats(maxTimeWindowMillis, nowMillis())
   {
   }

   StandAloneMovingStats::StandAloneMovingStats(
         FedProDuration maxTimeWindowMillis,
         SteadyTimePoint startTime)
         : StandAloneMovingStats(maxTimeWindowMillis, DEFAULT_MILLIS_PER_BUCKET, startTime)
   {
   }

   StandAloneMovingStats::StandAloneMovingStats(
         FedProDuration maxTimeWindowMillis,
         FedProDuration millisPerBucket,
         SteadyTimePoint startTime)
         : _bucketCount((maxTimeWindowMillis.count() / millisPerBucket.count()) * 2),
           _millisPerBucket{millisPerBucket},
           _maxPerBucket(_bucketCount, std::numeric_limits<Sample>::min()),
           _minPerBucket(_bucketCount, std::numeric_limits<Sample>::max()),
           _sumPerBucket(_bucketCount, 0),
           _sampleCountPerBucket(_bucketCount, 0),
           _startTime{startTime}
   {
      if (maxTimeWindowMillis.count() / millisPerBucket.count() > std::numeric_limits<size_t>::max() / 2) {
         throw std::invalid_argument{
               "maxTimeWindowMillis/millisPerBucket is too large, would overflow the number of buckets. "};
      }
   }

   void StandAloneMovingStats::sample(int value)
   {
      sample(value, nowMillis());
   }

   void StandAloneMovingStats::sample(
         int value,
         SteadyTimePoint time)
   {
      size_t currentBucket{getBucket(time)};
      {
         std::lock_guard<std::mutex> lock(_guard);
         resetBucketsBeforeUsing(currentBucket);
         size_t currentBucketIndex = currentBucket % _bucketCount;
         _maxPerBucket[currentBucketIndex] = std::max(_maxPerBucket[currentBucketIndex], value);
         _minPerBucket[currentBucketIndex] = std::min(_minPerBucket[currentBucketIndex], value);
         _sumPerBucket[currentBucketIndex] += value;
         _sampleCountPerBucket[currentBucketIndex]++;
         _historicTotal += value;
         _historicCount++;
      }
   }

   StandAloneMovingStats::Stats StandAloneMovingStats::getStats()
   {
      return getStatsForTime(nowMillis());
   }

   StandAloneMovingStats::Stats StandAloneMovingStats::getStats(FedProDuration timeWindowMillis)
   {
      return getStatsForTime(timeWindowMillis, nowMillis());
   }

   StandAloneMovingStats::Stats StandAloneMovingStats::getStatsForTime(SteadyTimePoint time)
   {
      return getStatsForTime((_bucketCount / 2) * _millisPerBucket, time);
   }

   StandAloneMovingStats::Stats StandAloneMovingStats::getStatsForTime(
         FedProDuration timeWindowMillis,
         SteadyTimePoint time)
   {
      size_t bucketCountWindow = timeWindowMillis / _millisPerBucket;
      if (bucketCountWindow < 1) {
         throw std::runtime_error{"Time window is too small."};
      }
      Stats stats{};
      size_t lastCompleteBucket = getBucket(time) - 1;
      {
         std::lock_guard<std::mutex> lock(_guard);
         resetBucketsBeforeUsing(lastCompleteBucket);
         // Copy buckets with current bucket at index n, oldest at index 0
         size_t lastBucketIndex = lastCompleteBucket % _bucketCount;
         size_t oldestBucketIndex = (lastBucketIndex + 1 - bucketCountWindow + _bucketCount) % _bucketCount;
         SampleRangePair maxPerBucket = wrappedRanges(_maxPerBucket, oldestBucketIndex, bucketCountWindow);
         stats._maxSample = max(maxPerBucket);
         SampleRangePair minPerBucket = wrappedRanges(_minPerBucket, oldestBucketIndex, bucketCountWindow);
         stats._minSample = min(minPerBucket);
         SampleRangePair sampleCountPerBucket = wrappedRanges(_sampleCountPerBucket, oldestBucketIndex, bucketCountWindow);
         stats._sampleCount = sum(sampleCountPerBucket);
         stats._bucketCount = bucketCountWindow;
         SampleRangePair sumPerBucket = wrappedRanges(_sumPerBucket, oldestBucketIndex, bucketCountWindow);
         stats._sum = sum(sumPerBucket);
         stats._maxBucket = max(sumPerBucket);
         stats._minBucket = min(sumPerBucket);
         stats._averageBucket = static_cast<float>(stats._sum) / static_cast<float>(bucketCountWindow);
         stats._averageSample =
               stats._sampleCount != 0 ? static_cast<float>(stats._sum) / static_cast<float>(stats._sampleCount) : 0;
         stats._historicTotal = _historicTotal;
         stats._historicCount = _historicCount;
      }
      return stats;
   }

   void StandAloneMovingStats::resetBucketsBeforeUsing(size_t bucketToUse)
   {
      while (bucketToUse >= _nextBucket) {
         _nextBucket = nextBucket(_nextBucket);
         size_t index = _nextBucket % _bucketCount;
         _maxPerBucket[index] = std::numeric_limits<Sample>::min();
         _minPerBucket[index] = std::numeric_limits<Sample>::max();
         _sumPerBucket[index] = 0;
         _sampleCountPerBucket[index] = 0;
      }
   }

   StandAloneMovingStats::SampleRangePair StandAloneMovingStats::wrappedRanges(
         const std::vector<Sample> & src,
         size_t firstElement,
         size_t count) const
   {
      size_t available = _bucketCount - firstElement;
      size_t firstRangeSize = std::min(count, available);
      SampleConstIterator firstRangeBegin{src.begin()};
      std::advance(firstRangeBegin, firstElement);
      SampleConstIterator firstRangeEnd{firstRangeBegin};
      std::advance(firstRangeEnd, firstRangeSize);

      size_t secondRangeSize = count - firstRangeSize;
      SampleConstIterator secondRangeBegin{src.begin()};
      SampleConstIterator secondRangeEnd{secondRangeBegin};
      std::advance(secondRangeEnd, secondRangeSize);

      SampleRange firstRange{firstRangeBegin, firstRangeEnd};
      SampleRange secondRange{secondRangeBegin, secondRangeEnd};
      return {firstRange, secondRange};
   }

   StandAloneMovingStats::Sample StandAloneMovingStats::sum(SampleRangePair ranges)
   {
      Sample m{0};
      for (SampleConstIterator sampleIt{ranges.first.first}; sampleIt != ranges.first.second; ++sampleIt) {
         m += *sampleIt;
      }
      for (SampleConstIterator sampleIt{ranges.second.first}; sampleIt != ranges.second.second; ++sampleIt) {
         m += *sampleIt;
      }
      return m;
   }

   StandAloneMovingStats::Sample StandAloneMovingStats::max(SampleRangePair ranges)
   {
      Sample m{std::numeric_limits<Sample>::min()};
      for (SampleConstIterator sampleIt{ranges.first.first}; sampleIt != ranges.first.second; ++sampleIt) {
         m = std::max(m, *sampleIt);
      }
      for (SampleConstIterator sampleIt{ranges.second.first}; sampleIt != ranges.second.second; ++sampleIt) {
         m = std::max(m, *sampleIt);
      }
      return m;
   }

   StandAloneMovingStats::Sample StandAloneMovingStats::min(SampleRangePair ranges)
   {
      Sample m{std::numeric_limits<Sample>::max()};
      for (SampleConstIterator sampleIt{ranges.first.first}; sampleIt != ranges.first.second; ++sampleIt) {
         m = std::min(m, *sampleIt);
      }
      for (SampleConstIterator sampleIt{ranges.second.first}; sampleIt != ranges.second.second; ++sampleIt) {
         m = std::min(m, *sampleIt);
      }
      return m;
   }

   size_t StandAloneMovingStats::getMaxBucketCount() const
   {
      // The largest possible bucket value that:
      // * Fit within size_t,
      // * allows correct rollover when wrapping, ie (maxBucket + 1) mod _bucketCount = 0
      size_t maxBucket = std::numeric_limits<size_t>::max() - 1;
      // (a + b) % count = (a mod count + b mod count) % count
      maxBucket = maxBucket - ((1 % _bucketCount) + (maxBucket % _bucketCount)) % _bucketCount;
      // Increment, and wraps to zero after maxBucket.
      return maxBucket + 1;
   }

   size_t StandAloneMovingStats::getBucket(SteadyTimePoint time) const
   {
      FedProDuration elapsed = std::chrono::duration_cast<FedProDuration>(time - _startTime);
      return elapsed / _millisPerBucket;
   }

   size_t StandAloneMovingStats::nextBucket(size_t bucket) const
   {
      size_t maxBucketCount = getMaxBucketCount();
      // Increment, and wraps to zero after maxBucket.
      return (1 + (bucket % maxBucketCount)) % maxBucketCount;
   }
}