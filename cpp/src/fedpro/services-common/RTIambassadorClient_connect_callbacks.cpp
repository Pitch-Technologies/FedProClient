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

// Silence clang-tidy issues reported for standard HLA exception.
// NOLINTBEGIN(hicpp-exception-baseclass)

#include "RTIambassadorClient.h"

#include "ClientSession.h"
#include "FederateAmbassadorDispatcher.h"
#include <fedpro/FedProExceptions.h>
#include "RtiConfiguration.h"
#include "utility/StringUtil.h"
#include "utility/final.h"

namespace FedPro
{

   RTIambassadorClient::RTIambassadorClient(std::shared_ptr<ClientConverter> clientConverter)
         : RTIambassadorClientGenericBase(std::move(clientConverter))
   {
   }

   RTI_NAMESPACE::ConfigurationResult RTIambassadorClient::connect(
         const Properties & clientSettings,
         RTI_NAMESPACE::FederateAmbassador & federateAmbassador,
         RTI_NAMESPACE::CallbackModel callbackModel,
         const RTI_NAMESPACE::RtiConfiguration * rtiConfiguration,
         const RTI_NAMESPACE::Credentials * credentials)
   {
      throwIfInCallback(__func__);
      // This holds the session resulting from a failed connection attempt. doConnect may assign a pointer to this.
      std::shared_ptr<ClientSession> failedSession;
      // When the method returns or throws, this executes.
      auto sessionCleanup = make_final([this, &failedSession]() {
         if (failedSession) {
            // Wait for listeners to complete before destroying PersistentSession (cf terminatingSession),
            // since lostConnection() and sessionTerminated() need to access this client object,
            // and this the last opportunity to wait for the session's listeners.
            failedSession->_persistentSession->waitListeners();

            // cleanUpTerminatingSession waits for thread, and destroy the PersistentSession being terminated.
            // _connectionStateLock MUST NOT be locked when this happens to prevent deadlocks.
            cleanUpTerminatingSession(*failedSession);
         }
      });

      std::lock_guard<std::mutex> guard(_connectionStateLock);

      throwIfAlreadyConnected();
      try {
         createClientSession(clientSettings, federateAmbassador, callbackModel);
      } catch (const std::invalid_argument & e) {
         throw RTI_NAMESPACE::ConnectionFailed(toWString(e.what()));
      }

      rti1516_2025::fedpro::CallRequest callRequest;
#if (RTI_HLA_VERSION >= 2025)
      if (rtiConfiguration && credentials) {
         auto * request = new rti1516_2025::fedpro::ConnectWithConfigurationAndCredentialsRequest();
         request->set_allocated_rticonfiguration(_clientConverter->convertFromHla(*rtiConfiguration));
         request->set_allocated_credentials(_clientConverter->convertFromHla(*credentials));

         callRequest.set_allocated_connectwithconfigurationandcredentialsrequest(request);

         rti1516_2025::fedpro::CallResponse callResponse =
               doConnect(callRequest, failedSession);
         if (!callResponse.has_connectwithconfigurationandcredentialsresponse()) {
            throw RTI_NAMESPACE::RTIinternalError(L"Missing connect response in Federate Protocol Buffer");
         }

         const rti1516_2025::fedpro::ConnectWithConfigurationAndCredentialsResponse
               & response = callResponse.connectwithconfigurationandcredentialsresponse();
         return _clientConverter->convertToHla(response.configurationresult());
      } else if (credentials) {
         auto * request = new rti1516_2025::fedpro::ConnectWithCredentialsRequest();
         request->set_allocated_credentials(_clientConverter->convertFromHla(*credentials));

         callRequest.set_allocated_connectwithcredentialsrequest(request);

         rti1516_2025::fedpro::CallResponse callResponse =
               doConnect(callRequest, failedSession);
         if (!callResponse.has_connectwithcredentialsresponse()) {
            throw RTI_NAMESPACE::RTIinternalError(L"Missing connect response in Federate Protocol Buffer");
         }

         const rti1516_2025::fedpro::ConnectWithCredentialsResponse
               & response = callResponse.connectwithcredentialsresponse();
         return _clientConverter->convertToHla(response.configurationresult());
#else
         if (credentials) {
            throw RTI_NAMESPACE::RTIinternalError(L"Unexpected credentials in pre-HLA4 federate");
#endif
      } else if (rtiConfiguration) {
         auto * request = new rti1516_2025::fedpro::ConnectWithConfigurationRequest();
         request->set_allocated_rticonfiguration(_clientConverter->convertFromHla(*rtiConfiguration));

         callRequest.set_allocated_connectwithconfigurationrequest(request);

         rti1516_2025::fedpro::CallResponse callResponse =
               doConnect(callRequest, failedSession);
         if (!callResponse.has_connectwithconfigurationresponse()) {
            throw RTI_NAMESPACE::RTIinternalError(L"Missing connect response in Federate Protocol Buffer");
         }

         const rti1516_2025::fedpro::ConnectWithConfigurationResponse
               & response = callResponse.connectwithconfigurationresponse();
         return _clientConverter->convertToHla(response.configurationresult());
      } else {
         auto * request = new rti1516_2025::fedpro::ConnectRequest();

         callRequest.set_allocated_connectrequest(request);

         rti1516_2025::fedpro::CallResponse callResponse =
               doConnect(callRequest, failedSession);
         if (!callResponse.has_connectresponse()) {
            throw RTI_NAMESPACE::RTIinternalError(L"Missing connect response in Federate Protocol Buffer");
         }

         const rti1516_2025::fedpro::ConnectResponse & response = callResponse.connectresponse();
         return _clientConverter->convertToHla(response.configurationresult());
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
      if (_clientSession) {
         throw RTI_NAMESPACE::AlreadyConnected(L"Already has a session");
      }
   }

   void RTIambassadorClient::throwIfInCallback(const char * methodName) const
   {
      if (isInCallbackThread()) {
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

} // FedPro

// NOLINTEND(hicpp-exception-baseclass)
