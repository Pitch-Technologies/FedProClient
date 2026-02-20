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
#include <fedpro/PersistentSession.h>
#include <fedpro/Properties.h>
#include <fedpro/Session.h>
#include "protobuf_util.h"
#include "protobuf/generated/RTIambassador.pb.h"
#include "ServiceSettings.h"
#include <utility/MovingStats.h>
#include <utility/string_view.h>

#include <condition_variable>
#include <future>
#include <memory>
#include <mutex>
#include <thread>

namespace RTI_NAMESPACE
{
   class FederateAmbassador;
}

namespace FedPro
{
   class ClientSession;
   class FederateAmbassadorDispatcher;
   class ResumeStrategy;
   class Transport;

   class RTIambassadorClientGenericBase
   {
   public:

      using CallRequest = ::rti1516_2025::fedpro::CallRequest;
      using CallResponse = ::rti1516_2025::fedpro::CallResponse;

      explicit RTIambassadorClientGenericBase(std::shared_ptr<ClientConverter> clientConverter);

      virtual ~RTIambassadorClientGenericBase();

      void createClientSession(
            const Properties & settings,
            RTI_NAMESPACE::FederateAmbassador & federateReference,
            RTI_NAMESPACE::CallbackModel callbackModel);

      /**
       * Create the ClientSession instance, and the corresponding PersistentSession, using the provided settings.
       *
       * @param settings A Properties object produced by SettingsParser::parse().
       * Example: "FED_INT_HEART=600,connect.port=tcp".
       * @param createCallbackThread Whether to create an thread for immediate callback dispatch, or not.
       *
       * @throw std::invalid_argument If any setting is provided through an invalid format
       * @throw std::runtime_error
       */
      void createClientSession(
            const Properties & settings,
            std::shared_ptr<FederateAmbassadorDispatcher> federateAmbassadorDispatcher,
            bool createCallbackThread);

      virtual void enableCallbacks();

      virtual void disableCallbacks();

      bool isInCallbackThread() const;

      static std::vector<std::wstring> splitFederateConnectSettings(const std::wstring & inputString);

      static void addToSettingsLine(
            std::string & settingsLine,
            string_view toAdd);

      static std::string extractAndRemoveClientSettings(
            std::vector<std::wstring> & inputValueList,
            bool crcAddressIsFedProServerAddress);

      static std::wstring toAdditionalSettingsString(const std::vector<std::wstring> & inputValueList);

   protected:

      static void throwOnException(const CallResponse & callResponse);

      static CallResponse decodeHlaCallResponse(const ByteSequence & encodedResponse);

      /**
       * @brief Start the session, and send the provided connect request.
       * @param callRequest A connect request
       * @param failedSession If the call fails, the method assign the failed session instance to this output parameter.
       */
      CallResponse doConnect(
            const CallRequest & callRequest,
            std::shared_ptr<ClientSession> & failedSession) noexcept(false); // REQUIRES(_connectionStateLock)

      /**
       * Submit an HLA call, wait for the response, and decode the response.
       *
       * @throw RTI_NAMESPACE::NotConnected
       */
      CallResponse doHlaCall(const CallRequest & callRequest);

      CallResponse doHlaCall(const CallRequest & callRequest, ClientSession & session);

      /**
       * Submit an HLA call.
       *
       * @throw RTI_NAMESPACE::NotConnected
       */
      std::future<ByteSequence> doAsyncHlaCall(const CallRequest & callRequest);

      void countSyncUpdateForStats();

      void countAsyncUpdateForStats();

      void countSyncSentInteractionForStats();

      void countAsyncSentInteractionForStats();

      void countSyncSentDirectedInteractionForStats();

      void countAsyncSentDirectedInteractionForStats();

      MovingStats::Stats getReflectStats(MovingStats::SteadyTimePoint time);

      MovingStats::Stats getReceivedInteractionStats(MovingStats::SteadyTimePoint time);

      MovingStats::Stats getReceivedDirectedInteractionStats(MovingStats::SteadyTimePoint time);

      MovingStats::Stats getCallbackTimeStats(MovingStats::SteadyTimePoint time);

      bool evokeCallbackBase(double approximateMinimumTimeInSeconds);

      bool evokeMultipleCallbacksBase(
            double minimumTime,
            double maximumTime);

      void disconnectBase();

      std::shared_ptr<ClientSession> getClientSession();

      void startSessionThreads(ClientSession & clientSession);

      void cleanUpTerminatingSession(ClientSession & clientSession);

      std::shared_ptr<ClientConverter> _clientConverter;

      std::shared_ptr<FederateAmbassadorDispatcher> _federateAmbassadorDispatcher;

      std::shared_ptr<ClientSession> _clientSession; // GUARDED_BY(_connectionStateLock)

      // Protect connect and disconnect calls here at the top level, to simplify some code.
      // All other HLA calls are made thread-safe further down, in the Session classes.
      // Lock safety: This shall not be locked nor synchronized while executing federate callbacks/functors,
      // since a federate may call disconnect() which is synchronized with _disconnectLock.
      std::mutex _connectionStateLock;

      // disconnect has an additional lock to prevent concurrent disconnect calls.
      // Lock safety: _disconnectLock shall not be locked nor synchronized after _connectionStateLock
      std::mutex _disconnectLock;

      // Service layer settings
      // We may want to query property "client.asyncUpdates".
      bool _asyncUpdates{DEFAULT_ASYNC_UPDATES};
      std::string _protocol{};

   private:

      void terminatePersistentSession(ClientSession & clientSession);

      void printStats() noexcept;

      void sessionTerminated(uint64_t sessionId);

      std::unique_ptr<Transport> createTransportConfiguration(const Properties & settings);

      std::unique_ptr<MovingStats> _hlaSyncUpdateStats;
      std::unique_ptr<MovingStats> _hlaAsyncUpdateStats;
      std::unique_ptr<MovingStats> _hlaSyncSentInteraction;
      std::unique_ptr<MovingStats> _hlaAsyncSentInteraction;
      std::unique_ptr<MovingStats> _hlaSyncSentDirectedInteraction;
      std::unique_ptr<MovingStats> _hlaAsyncSentDirectedInteraction;

      bool _printStats{false};

      FedProDuration _printStatsIntervalMillis{};
   };

}
