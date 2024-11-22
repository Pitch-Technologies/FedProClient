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

#include <fedpro/Settings.h>

namespace FedPro
{
   // Default values
   static constexpr const FedProDuration DEFAULT_HEARTBEAT_INTERVAL_MILLIS{60000ULL};
   static constexpr const FedProDuration DEFAULT_RESPONSE_TIMEOUT_MILLIS{180000ULL};
   static constexpr const FedProDuration DEFAULT_RECONNECT_LIMIT_MILLIS{600000ULL};
   static constexpr const uint32_t DEFAULT_CONNECTION_MAX_RETRY_ATTEMPTS{0};
   static constexpr const FedProDuration DEFAULT_CONNECTION_TIMEOUT_MILLIS{DEFAULT_RESPONSE_TIMEOUT_MILLIS};
   static constexpr const uint32_t DEFAULT_MESSAGE_QUEUE_SIZE{2000};
   static constexpr const bool DEFAULT_RATE_LIMIT_ENABLED{false};

   // Non settings related default values.
   static constexpr const FedProDuration DEFAULT_RECONNECT_DELAY_MILLIS{5000ULL};

}
