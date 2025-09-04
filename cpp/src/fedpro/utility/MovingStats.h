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

#include <fedpro/Aliases.h>

#include <chrono>

namespace FedPro
{
class MovingStats {
public:

   using SteadyTimePoint = std::chrono::time_point<std::chrono::steady_clock>;

   class Stats {
   public:

      // NOTE: When sampleCount is 0, maxSample and minSample are invalid
      //   When a bucket contains no samples, the min, max, sum and average of that bucket is 0.
      int _sampleCount{0};
      size_t _bucketCount{0};
      int _maxSample{0};
      int _maxBucket{0};
      int _minSample{0};
      int _minBucket{0};
      float _averageSample{0.0f};
      float _averageBucket{0.0f};
      int _sum{0};
      long _historicTotal{0};
      long _historicCount{0};

      int maxSampleWithDefault(int defaultValue) const
      {
         return _sampleCount > 0 ? _maxSample : defaultValue;
      }

      int minSampleWithDefault(int defaultValue) const
      {
         return _sampleCount > 0 ? _minSample : defaultValue;
      }

   };

   static SteadyTimePoint nowMillis();

   virtual ~MovingStats();

   virtual void sample(int value) = 0;

   virtual void sample(int value, SteadyTimePoint time) = 0;

   virtual Stats getStats() = 0;

   virtual Stats getStats(FedProDuration timeWindowMillis) = 0;

   virtual Stats getStatsForTime(FedProDuration timeWindowMillis, SteadyTimePoint time) = 0;

   virtual Stats getStatsForTime(SteadyTimePoint time) = 0;
};

} // FedPro