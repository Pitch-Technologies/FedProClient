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

#include <fedpro/SimpleResumeStrategy.h>

#include "SessionSettings.h"

namespace FedPro
{

   SimpleResumeStrategy::SimpleResumeStrategy()
         : SimpleResumeStrategy(Properties{})
   {
   }

   SimpleResumeStrategy::SimpleResumeStrategy(const Properties & settings)
         : _reconnectDelay{settings.getDuration(SETTING_NAME_RESUME_RETRY_DELAY_MILLIS, DEFAULT_RECONNECT_DELAY_MILLIS)},
           _reconnectLimit{settings.getDuration(SETTING_NAME_RECONNECT_LIMIT, DEFAULT_RECONNECT_LIMIT_MILLIS)}
   {
   }

   bool SimpleResumeStrategy::shouldRetry(FedProDuration timeSinceDisconnect) noexcept
   {
      return timeSinceDisconnect < _reconnectLimit;
   }

   FedProDuration SimpleResumeStrategy::getRetryDelay(FedProDuration timeSinceDisconnect) noexcept
   {
      return _reconnectDelay;
   }

   FedProDuration SimpleResumeStrategy::getRetryLimit() noexcept
   {
      return _reconnectLimit;
   }


} // FedPro
