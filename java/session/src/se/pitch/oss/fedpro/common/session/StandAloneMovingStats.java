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

import net.jcip.annotations.GuardedBy;

import java.util.Arrays;

public class StandAloneMovingStats implements MovingStats {
   private static final int DEFAULT_MILLIS_PER_BUCKET = 1_000;

   private final Object _guard = new Object();
   @GuardedBy("_guard")
   private final int[] _maxPerBucket;
   @GuardedBy("_guard")
   private final int[] _minPerBucket;
   @GuardedBy("_guard")
   private final int[] _sumPerBucket;
   @GuardedBy("_guard")
   private final int[] _sampleCountPerBucket;

   private long _historicTotal = 0;
   private long _historicCount = 0;

   private final int _bucketCount;
   private final long _millisPerBucket;
   private long _startTime;

   public StandAloneMovingStats(
         long maxTimeWindowMillis) {
      this(maxTimeWindowMillis, MovingStats.validTimeMillis());
   }

   public StandAloneMovingStats(
         long maxTimeWindowMillis,
         long startTime) {
      this(maxTimeWindowMillis, DEFAULT_MILLIS_PER_BUCKET, startTime);
   }

   public StandAloneMovingStats(
         long maxTimeWindowMillis,
         long millisPerBucket,
         long startTime)
   {
      _millisPerBucket = millisPerBucket;
      _bucketCount = (int)(maxTimeWindowMillis / millisPerBucket) * 2;
      _maxPerBucket = new int[_bucketCount];
      _minPerBucket = new int[_bucketCount];
      _sumPerBucket = new int[_bucketCount];
      _sampleCountPerBucket = new int[_bucketCount];
      reset(startTime);
   }

   @Override
   public void sample(int value) {
      sample(value, MovingStats.validTimeMillis());
   }

   @Override
   public void sample(int value, long now)
   {
      long currentBucket = getBucket(now);
      synchronized (_guard) {
         resetBucketsBeforeUsing(currentBucket);
         int currentBucketIndex = (int) (currentBucket % _bucketCount);
         _maxPerBucket[currentBucketIndex] = Math.max(_maxPerBucket[currentBucketIndex], value);
         _minPerBucket[currentBucketIndex] = Math.min(_minPerBucket[currentBucketIndex], value);
         _sumPerBucket[currentBucketIndex] += value;
         _sampleCountPerBucket[currentBucketIndex]++;
         _historicTotal += value;
         _historicCount++;
      }
   }

   @Override
   public Stats getStats() {
      return getStatsForTime(MovingStats.validTimeMillis());
   }

   @Override
   public Stats getStats(int timeWindowMillis) {
      return getStatsForTime(timeWindowMillis, MovingStats.validTimeMillis());
   }

   public Stats getStatsForTime(long now) {
      return getStatsForTime((int) ((_bucketCount / 2) * _millisPerBucket), now);
   }

   @Override
   public Stats getStatsForTime(int timeWindowMillis, long now)
   {
      int bucketCountWindow = (int)(timeWindowMillis / _millisPerBucket);
      if (bucketCountWindow < 1) {
         throw new RuntimeException("Time window is too small.");
      }
      Stats stats = new Stats();
      int[] maxPerBucket;
      int[] minPerBucket;
      int[] sampleCountPerBucket;
      int[] sumPerBucket;
      long lastCompleteBucket = getBucket(now) - 1;
      synchronized (_guard) {
         resetBucketsBeforeUsing(lastCompleteBucket);
         // Copy buckets with current bucket at index n, oldest at index 0
         int lastBucketIndex = (int) (lastCompleteBucket % _bucketCount);
         int oldestBucketIndex = (lastBucketIndex + 1 - bucketCountWindow + _bucketCount) % _bucketCount;
         maxPerBucket = copyWrappedArray(_maxPerBucket, oldestBucketIndex, bucketCountWindow);
         minPerBucket = copyWrappedArray(_minPerBucket, oldestBucketIndex, bucketCountWindow);
         sampleCountPerBucket = copyWrappedArray(_sampleCountPerBucket, oldestBucketIndex, bucketCountWindow);
         sumPerBucket = copyWrappedArray(_sumPerBucket, oldestBucketIndex, bucketCountWindow);
         stats.historicTotal = _historicTotal;
         stats.historicCount = _historicCount;
      }
      int sampleCount = sum(sampleCountPerBucket);
      stats.sampleCount = sampleCount;
      stats.bucketCount = bucketCountWindow;

      stats.maxSample = max(maxPerBucket);
      stats.minSample = min(minPerBucket);
      stats.sum = sum(sumPerBucket);
      stats.maxBucket = max(sumPerBucket);
      stats.minBucket = min(sumPerBucket);
      stats.averageBucket = (float) stats.sum / bucketCountWindow;
      stats.averageSample = sampleCount != 0 ? (float) stats.sum / sampleCount : 0;
      return stats;
   }

   @GuardedBy("_guard")
   private long _nextBucket = 0;

   @GuardedBy("_guard")
   private void resetBucketsBeforeUsing(long bucketToUse) {
      while (bucketToUse >= _nextBucket) {
         _nextBucket = nextBucket(_nextBucket);
         int index = (int) (_nextBucket % _bucketCount);
         _maxPerBucket[index] = Integer.MIN_VALUE;
         _minPerBucket[index] = Integer.MAX_VALUE;
         _sumPerBucket[index] = 0;
         _sampleCountPerBucket[index] = 0;
      }
   }

   private int[] copyWrappedArray(int[] src, int firstElement, int count)
   {
      int[] dest = new int[count];
      int available = _bucketCount - firstElement;
      int firstRange = Math.min(count, available);
      System.arraycopy(src, firstElement, dest, 0, firstRange);

      int secondRange = count - firstRange;
      if (count != 0) {
         System.arraycopy(src, 0, dest, firstRange, secondRange);
      }
      return dest;
   }

   private static int sum(int... buckets)
   {
      int m = 0;
      for (int n : buckets) {
         m += n;
      }
      return m;
   }

   private static int max(int... buckets)
   {
      int m = Integer.MIN_VALUE;
      for (int n : buckets) {
         m = Math.max(m, n);
      }
      return m;
   }

   private static int min(int... buckets)
   {
      int m = Integer.MAX_VALUE;
      for (int n : buckets) {
         m = Math.min(m, n);
      }
      return m;
   }


   @GuardedBy("_guard")
   private void reset(long startTime)
   {
      Arrays.fill(_maxPerBucket, Integer.MIN_VALUE);
      Arrays.fill(_minPerBucket, Integer.MAX_VALUE);
      Arrays.fill(_sumPerBucket, 0);
      Arrays.fill(_sampleCountPerBucket, 0);
      _startTime = startTime;
   }

   public long getBucketCount()
   {
      return _bucketCount;
   }

   long getMaxBucketCount()
   {
      // Compute the largest possible bucket value that:
      // * Fit within int,
      // * allows correct rollover when wrapping, ie (maxBucket + 1) mod _bucketCount = 0
      long maxBucket = Long.MAX_VALUE - 1;
      // (a + b) % count = (a mod count + b mod count) % count
      maxBucket = maxBucket - (1 + (maxBucket % _bucketCount)) % _bucketCount;
      return maxBucket + 1;
   }

   long getBucket(long now)
   {
      long elapsed = now - _startTime;
      return (elapsed / _millisPerBucket) % getMaxBucketCount();
   }

   long nextBucket(long bucket)
   {
      long maxBucketCount = getMaxBucketCount();
      // Increment, and wraps to zero after maxBucket.
      return (1 + (bucket % maxBucketCount)) % maxBucketCount;
   }

}
