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

#include "ClientConverter.h"
#include "protobuf/generated/FederateAmbassador.pb.h"
#include <utility/MovingStats.h>

#include <RTI/FederateAmbassador.h>

#include <functional>
#include <memory>

namespace FedPro
{
   class FederateAmbassadorAdapter;

   class RTIambassadorClient;

   class FederateAmbassadorDispatcher
   {
   public:
      using MovingStatsSupplier = std::function<std::unique_ptr<MovingStats>()>;

      explicit FederateAmbassadorDispatcher(
            RTI_NAMESPACE::FederateAmbassador * federateReference,
            std::shared_ptr<ClientConverter> clientConverter,
            const MovingStatsSupplier & movingStatsSupplier);

      ~FederateAmbassadorDispatcher();

      void prefetch(RTIambassadorClient * client);

      /**
       * Convert and dispatch a CallbackRequest to the FederateAmbassador.
       *
       * @param callback Callback to dispatch. This method consumes the callback and may leave it empty.
       */
      void dispatchCallback(std::unique_ptr<rti1516_2025::fedpro::CallbackRequest> callback);

      MovingStats::Stats getReflectStats(MovingStats::SteadyTimePoint time) {
         return _reflectStats->getStatsForTime(time);
      }

      MovingStats::Stats getReceivedInteractionStats(MovingStats::SteadyTimePoint time) {
         return _receivedInteractionStats->getStatsForTime(time);
      }

      MovingStats::Stats getReceivedDirectedInteractionStats(MovingStats::SteadyTimePoint time) {
         return _receivedDirectedInteractionStats->getStatsForTime(time);
      }

      MovingStats::Stats getCallbackTimeStats(MovingStats::SteadyTimePoint time) {
         return _callbackTimeStats->getStatsForTime(time);
      }

   private:

#if (RTI_HLA_VERSION >= 2025)
      RTI_NAMESPACE::FederateAmbassador * _federateReference;
#else
      std::unique_ptr<FederateAmbassadorAdapter> _federateReference;
#endif

      std::shared_ptr<ClientConverter> _clientConverter;

      std::unique_ptr<MovingStats> _reflectStats;
      std::unique_ptr<MovingStats> _receivedInteractionStats;
      std::unique_ptr<MovingStats> _receivedDirectedInteractionStats;
      std::unique_ptr<MovingStats> _callbackTimeStats;

   };
}
