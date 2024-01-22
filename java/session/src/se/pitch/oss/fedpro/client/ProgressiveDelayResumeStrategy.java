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

package se.pitch.oss.fedpro.client;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * This resume strategy can be customized to attempt session resume at progressively longer intervals.
 * {@link #withLimit(long, long)} may be called any number of times; each call adds a limit - from the time the session
 * was dropped - before which a certain retry delay will be used.
 * <p>
 * Example:
 * {@code progressiveDelayResumeStrategy.withLimit(1_000, 10_000).withLimit(5_000, 60_000)}
 * In this example, the resume attempts will first be made every second for the first ten seconds, then every five
 * seconds until a minute has passed since disconnect, after which the session will be deemed lost. The order of the
 * calls to {@link #withLimit(long, long)} can be changed with changing the resulting strategy.
 */
public class ProgressiveDelayResumeStrategy implements ResumeStrategy {

   private final SortedMap<Long, Long> _delayBeforeEachLimit = new TreeMap<>();

   public ProgressiveDelayResumeStrategy()
   {
   }

   public ProgressiveDelayResumeStrategy withLimit(
         long delayBeforeLimitMillis,
         long limitMillis)
   {
      _delayBeforeEachLimit.put(limitMillis, delayBeforeLimitMillis);
      return this;
   }

   @Override
   public boolean shouldRetry(long timeSinceDisconnectMillis)
   {
      return timeSinceDisconnectMillis < _delayBeforeEachLimit.lastKey();
   }

   @Override
   public long getRetryDelay(long timeSinceDisconnectMillis)
   {
      return _delayBeforeEachLimit.entrySet()
            .stream()
            .filter(entry -> timeSinceDisconnectMillis < entry.getKey())
            .findFirst()
            .map(Map.Entry::getValue)
            .orElse(0L);
   }

   @Override
   public long getRetryLimitMillis()
   {
      return _delayBeforeEachLimit.lastKey();
   }
}
