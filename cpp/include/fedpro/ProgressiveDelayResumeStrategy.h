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

#include <fedpro/Config.h>
#include <fedpro/ResumeStrategy.h>

#include <map>

/**
 * @brief This resume strategy can be customized to attempt session resume at
 * progressively longer intervals. withLimit(FedProDuration, FedProDuration) may be called
 * any number of times. Each call adds a limit, from the point in time when the session
 * was dropped, before which a certain retry delay will be used.
 *
 * Example:
 * @code{.cpp}
 * progressiveDelayResumeStrategy.withLimit(std::chrono::seconds(1) std::chrono::milliseconds(10)).withLimit(std::chrono::seconds(5), std::chrono::seconds(6));
 * @endcode
 * In the example above, the resume attempts will first be made every second for the first
 * ten seconds, then every five  seconds until a minute has passed since disconnection.
 * After a minute, the session will be deemed lost. The order of the calls to
 * withLimit(FedProDuration, FedProDuration) can be changed to alter the resulting
 * strategy.
 */

namespace FedPro
{
   class FEDPRO_EXPORT ProgressiveDelayResumeStrategy : public ResumeStrategy
   {
   public:

      ProgressiveDelayResumeStrategy() = default;

      ProgressiveDelayResumeStrategy & withLimit(
            FedProDuration delayBeforeLimit,
            FedProDuration limit);

      bool shouldRetry(FedProDuration timeSinceDisconnect) noexcept override;

      FedProDuration getRetryDelay(FedProDuration timeSinceDisconnect) noexcept override;

      FedProDuration getRetryLimit() noexcept override;

   private:
      std::map<FedProDuration, FedProDuration> _delayBeforeEachLimit;
   };

} // FedPro
