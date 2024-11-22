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
#include <fedpro/Properties.h>
#include <fedpro/ResumeStrategy.h>

namespace FedPro
{
   /**
    * @brief This resume strategy keeps trying to resume a dropped session at a fixed
    * interval, up to a specified limit.
    */
   class FEDPRO_EXPORT SimpleResumeStrategy : public ResumeStrategy
   {
   public:

      explicit SimpleResumeStrategy();

      explicit SimpleResumeStrategy(const Properties & settings);

      bool shouldRetry(FedProDuration timeSinceDisconnect) noexcept override;

      FedProDuration getRetryDelay(FedProDuration timeSinceDisconnect) noexcept override;

      FedProDuration getRetryLimit() noexcept override;

   private:
      const FedProDuration _reconnectDelay;
      const FedProDuration _reconnectLimit;
   };

} // FedPro
