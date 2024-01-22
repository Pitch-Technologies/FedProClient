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

package se.pitch.oss.fedpro.common.session.flowcontrol;

public class ExponentialRateLimiter implements RateLimiter {

   private final int _cutoff;

   public ExponentialRateLimiter(int queueSize)
   {
      _cutoff = queueSize / 20;
   }

   @Override
   public void preInsert(int size)
   {
      /*
       * If we have more than queue size / 20 calls in progress, sleep a bit
       * to try to reduce the number of calls.
       *
       * We sleep (no. of calls / 100)^2 ms.
       *
       * 0-99    -> nothing
       * 100-199 -> 1 ms
       * 200-299 -> 4 ms
       * 300-399 -> 9 ms
       *  ...
       * 900-999   -> 81 ms
       * 1000-1099 -> 100 ms
       * 1100-1199 -> 121 ms
       *  ...
       */

      if (size > _cutoff) {
         int hundreds = size / 100;
         try {
            Thread.sleep((long) hundreds * hundreds);
         } catch (InterruptedException ignore) {
         }
      }
   }

   @Override
   public void postInsert(int size)
   {
      // No-op
   }
}
