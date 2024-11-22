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

#include <RTI/FederateAmbassador.h>

#include <memory>

namespace FedPro
{
   class FederateAmbassadorAdapter;

   class RTIambassadorClient;

   class FederateAmbassadorDispatcher
   {
   public:
      explicit FederateAmbassadorDispatcher(
            RTI_NAMESPACE::FederateAmbassador * federateReference,
            std::shared_ptr<ClientConverter> clientConverter);

      ~FederateAmbassadorDispatcher();

      void prefetch(RTIambassadorClient * client);

      /**
       * Convert and dispatch a CallbackRequest to the FederateAmbassador.
       *
       * @param callback Callback to dispatch. This method consumes the callback and may leave it empty.
       */
      void dispatchCallback(std::unique_ptr<rti1516_202X::fedpro::CallbackRequest> callback);

   private:

#if (RTI_HLA_VERSION >= 2024)
      RTI_NAMESPACE::FederateAmbassador * _federateReference;
#else
      std::unique_ptr<FederateAmbassadorAdapter> _federateReference;
#endif

      std::shared_ptr<ClientConverter> _clientConverter;
   };
}
