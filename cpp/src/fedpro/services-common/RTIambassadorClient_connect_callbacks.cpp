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

//
// Note:
//
//   This file contains RTIambassadorClient implementation for methods related to connection and callbacks.
//   See also RTIambassadorClient.cpp for the rest of the implementation.
//

#include "RTIambassadorClient.h"

#include "FederateAmbassadorDispatcher.h"
#include <fedpro/FedProExceptions.h>
#include "RtiConfiguration.h"
#include "utility/StringUtil.h"

namespace FedPro
{

   RTIambassadorClient::RTIambassadorClient(std::shared_ptr<ClientConverter> clientConverter)
         : RTIambassadorClientGenericBase(std::move(clientConverter))
   {
   }
   rti1516_202X::fedpro::ConfigurationResult RTIambassadorClient::connect(
         string_view inputSettings,
         RTI_NAMESPACE::FederateAmbassador & federateAmbassador,
         RTI_NAMESPACE::CallbackModel callbackModel,
         std::unique_ptr<rti1516_202X::fedpro::RtiConfiguration> rtiConfiguration,
         const RTI_NAMESPACE::Credentials * credentials)
   {
      throwIfInCallback(__func__);

      std::lock_guard<std::mutex> guard(_connectionStateLock);

      throwIfAlreadyConnected();
      try {
         createPersistentSession(inputSettings);
      } catch (const std::invalid_argument & e) {
         throw RTI_NAMESPACE::ConnectionFailed(toWString(e.what()));
      }

      rti1516_202X::fedpro::CallRequest callRequest;
#if (RTI_HLA_VERSION >= 2024)
      if (rtiConfiguration && credentials) {
         auto * request = new rti1516_202X::fedpro::ConnectWithConfigurationAndCredentialsRequest();
         request->set_allocated_rticonfiguration(rtiConfiguration.release());
         request->set_allocated_credentials(_clientConverter->convertFromHla(*credentials));

         callRequest.set_allocated_connectwithconfigurationandcredentialsrequest(request);

         rti1516_202X::fedpro::CallResponse callResponse = doConnect(federateAmbassador, callbackModel, callRequest);
         if (!callResponse.has_connectwithconfigurationandcredentialsresponse()) {
            throw RTI_NAMESPACE::RTIinternalError(L"Missing connect response in Federate Protocol Buffer");
         }

         const rti1516_202X::fedpro::ConnectWithConfigurationAndCredentialsResponse
               & response = callResponse.connectwithconfigurationandcredentialsresponse();
         return response.configurationresult();
      } else if (credentials) {
         auto * request = new rti1516_202X::fedpro::ConnectWithCredentialsRequest();
         request->set_allocated_credentials(_clientConverter->convertFromHla(*credentials));

         callRequest.set_allocated_connectwithcredentialsrequest(request);

         rti1516_202X::fedpro::CallResponse callResponse = doConnect(federateAmbassador, callbackModel, callRequest);
         if (!callResponse.has_connectwithcredentialsresponse()) {
            throw RTI_NAMESPACE::RTIinternalError(L"Missing connect response in Federate Protocol Buffer");
         }

         const rti1516_202X::fedpro::ConnectWithCredentialsResponse
               & response = callResponse.connectwithcredentialsresponse();
         return response.configurationresult();
#else
         if (credentials) {
            throw RTI_NAMESPACE::RTIinternalError(L"Unexpected credentials in pre-HLA4 federate");
#endif
      } else if (rtiConfiguration) {
         auto * request = new rti1516_202X::fedpro::ConnectWithConfigurationRequest();
         request->set_allocated_rticonfiguration(rtiConfiguration.release());

         callRequest.set_allocated_connectwithconfigurationrequest(request);

         rti1516_202X::fedpro::CallResponse callResponse = doConnect(federateAmbassador, callbackModel, callRequest);
         if (!callResponse.has_connectwithconfigurationresponse()) {
            throw RTI_NAMESPACE::RTIinternalError(L"Missing connect response in Federate Protocol Buffer");
         }

         const rti1516_202X::fedpro::ConnectWithConfigurationResponse
               & response = callResponse.connectwithconfigurationresponse();
         return response.configurationresult();
      } else {
         auto * request = new rti1516_202X::fedpro::ConnectRequest();

         callRequest.set_allocated_connectrequest(request);

         rti1516_202X::fedpro::CallResponse callResponse = doConnect(federateAmbassador, callbackModel, callRequest);
         if (!callResponse.has_connectresponse()) {
            throw RTI_NAMESPACE::RTIinternalError(L"Missing connect response in Federate Protocol Buffer");
         }

         const rti1516_202X::fedpro::ConnectResponse & response = callResponse.connectresponse();
         return response.configurationresult();
      }
   }

   void RTIambassadorClient::disconnect()
   {
      throwIfInCallback(__func__);

      try {
         disconnectBase();
      } catch (const RTI_NAMESPACE::NotConnected & e) {
         // TODO: What happens in the standard API when we call disconnect before the RTIambassador is connected?
         throw RTI_NAMESPACE::RTIinternalError(e.what());
      }
   }

   void RTIambassadorClient::prefetch()
   {
      _federateAmbassadorDispatcher->prefetch(this);
   }

   void RTIambassadorClient::throwIfAlreadyConnected() const
   {
      if (isConnected()) {
         throw RTI_NAMESPACE::AlreadyConnected(L"Already has a session");
      }
   }

   void RTIambassadorClient::throwIfInCallback(const char * methodName) const
   {
      if (isInCurrentCallbackThread()) {
         throw RTI_NAMESPACE::CallNotAllowedFromWithinCallback(
               std::wstring(L"Cannot call ") + _clientConverter->convertToHla(methodName) + L" from within a callback");
      }
   }

   bool RTIambassadorClient::evokeCallback(double approximateMinimumTimeInSeconds)
   {
      throwIfInCallback(__func__);
      return evokeCallbackBase(approximateMinimumTimeInSeconds);
   }

   bool RTIambassadorClient::evokeMultipleCallbacks(
         double minimumTime,
         double maximumTime)
   {
      throwIfInCallback(__func__);
      return evokeMultipleCallbacksBase(minimumTime, maximumTime);
   }

   void RTIambassadorClient::dispatchHlaCallback(std::unique_ptr<CallbackRequest> callbackRequest)
   {
      if (!_federateAmbassadorDispatcher) {
         throw RTI_NAMESPACE::NotConnected(L"Cannot dispatch callback when FederateAmbassador is not connected");
      }

      _federateAmbassadorDispatcher->dispatchCallback(std::move(callbackRequest));
   }

} // FedPro
