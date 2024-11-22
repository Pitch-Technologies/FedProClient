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

#include <fedpro/ProgressiveDelayResumeStrategy.h>

namespace FedPro
{
   ProgressiveDelayResumeStrategy & ProgressiveDelayResumeStrategy::withLimit(
         FedProDuration delayBeforeLimit,
         FedProDuration limit)
   {
      _delayBeforeEachLimit[limit] = delayBeforeLimit;
      return *this;
   }

   bool ProgressiveDelayResumeStrategy::shouldRetry(FedProDuration timeSinceDisconnect) noexcept
   {
      return timeSinceDisconnect < _delayBeforeEachLimit.rbegin()->first;
   }

   FedProDuration ProgressiveDelayResumeStrategy::getRetryDelay(
         FedProDuration timeSinceDisconnect) noexcept
   {
      for (const auto & entry : _delayBeforeEachLimit) {
         if (timeSinceDisconnect < entry.first) {
            return entry.second;
         }
      }
      return FedProDuration::zero();
   }

   FedProDuration ProgressiveDelayResumeStrategy::getRetryLimit() noexcept
   {
      return _delayBeforeEachLimit.rbegin()->first;
   }

} // FedPro
