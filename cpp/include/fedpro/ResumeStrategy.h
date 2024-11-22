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
#include <fedpro/Config.h>

#include <chrono>
#include <cstdint>

namespace FedPro
{
   /**
    * @brief Interface for defining reconnection strategies.
    */
   class FEDPRO_EXPORT ResumeStrategy
   {
   public:
      /**
       * @brief Specifies whether a new reconnection attempt should be made, after the
       * specified time has passed since disconnect.
       *
       * @param timeSinceDisconnect The time since the disconnect happened.
       * @return True if another reconnect attempt should be made, false otherwise.
       */
      virtual bool shouldRetry(FedProDuration timeSinceDisconnect) noexcept = 0;

      /**
       * @brief Specifies the time to wait between each reconnect attempt, given that the
       * specified time has passed since disconnect.
       *
       * @param timeSinceDisconnect The time since the disconnect happened.
       * @return The delay between each new reconnect attempt.
       */
      virtual FedProDuration getRetryDelay(FedProDuration timeSinceDisconnect) noexcept = 0;

      /**
       * @brief The time limit after which to stop attempting to reconnect.
       *
       * @return The limit.
       */
      virtual FedProDuration getRetryLimit() noexcept = 0;

      virtual ~ResumeStrategy() = default;
   };

} // FedPro
