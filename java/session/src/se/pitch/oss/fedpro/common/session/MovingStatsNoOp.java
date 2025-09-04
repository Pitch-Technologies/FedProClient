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

public class MovingStatsNoOp implements MovingStats {
   @Override
   public void sample(int value)
   {}

   @Override
   public void sample(int value, long now)
   {}

   @Override
   public MovingStats.Stats getStats()
   {
      return new MovingStats.Stats();
   }

   @Override
   public MovingStats.Stats getStats(int timeWindowMillis)
   {
      return new MovingStats.Stats();
   }

   @Override
   public MovingStats.Stats getStatsForTime(int timeWindowMillis, long now)
   {
      return new MovingStats.Stats();
   }

   @Override
   public Stats getStatsForTime(long now)
   {
      return new MovingStats.Stats();
   }
}
