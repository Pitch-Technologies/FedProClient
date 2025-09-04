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

import java.util.concurrent.TimeUnit;

public interface MovingStats {
   static long validTimeMillis() {
      return TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
   }
   void sample(int value);

   void sample(int value, long now);

   Stats getStats();
   Stats getStats(int timeWindowMillis);

   Stats getStatsForTime(int timeWindowMillis, long now);
   Stats getStatsForTime(long now);

   class Stats {
      // NOTE: When sampleCount is 0, maxSample and minSample are invalid
      //   When a bucket contains no samples, the min, max, sum and average of that bucket is 0.
      public int sampleCount;
      public int bucketCount;
      public int maxSample;
      public int maxBucket;
      public int minSample;
      public int minBucket;
      public float averageSample;
      public float averageBucket;
      public int sum;
      public long historicTotal;
      public long historicCount;

      public int maxSampleWithDefault(int defaultValue) {
         return sampleCount > 0 ? maxSample : defaultValue;
      }

      public int minSampleWithDefault(int defaultValue) {
         return sampleCount > 0 ? minSample : defaultValue;
      }
   }
}
