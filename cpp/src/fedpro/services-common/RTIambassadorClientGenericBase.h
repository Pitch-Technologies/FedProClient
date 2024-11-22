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
#include <fedpro/Transport.h>
#include "protobuf_util.h"
#include "ServiceSettings.h"
#include "protobuf/generated/RTIambassador.pb.h"
#include "utility/InterruptibleCondition.h"
#include "utility/string_view.h"

#include <condition_variable>
#include <future>
#include <memory>
#include <mutex>
#include <queue>
#include <thread>

namespace RTI_NAMESPACE
{
   class FederateAmbassador;
}

namespace rti1516_202X
{
   namespace fedpro
   {
      class CallbackRequest;
   }
}

namespace FedPro
{

   class FederateAmbassadorDispatcher;

   class RTIambassadorClientGenericBase
   {
   public:

      using CallRequest = ::rti1516_202X::fedpro::CallRequest;
      using CallResponse = ::rti1516_202X::fedpro::CallResponse;
      using CallbackRequest = ::rti1516_202X::fedpro::CallbackRequest;

      using SequenceNumber = int32_t;

      explicit RTIambassadorClientGenericBase(std::shared_ptr<ClientConverter> clientConverter);

      virtual ~RTIambassadorClientGenericBase();

      CallResponse doConnect(
            RTI_NAMESPACE::FederateAmbassador & federateReference,
            RTI_NAMESPACE::CallbackModel callbackModel,
            const CallRequest & callRequest);

      bool isConnected() const
      {
         return _persistentSession != nullptr;
      }

      /**
       * Create the PersistentSession instance using the provided settings.
       *
       * @param settingsLine A line containing settings seperated by the ',' character.
       * Each setting must be specified in the format <setting_name>=<setting_value>.
       * Example: "FED_INT_HEART=600,connect.port=tcp".
       *
       * @throw std::invalid_argument If any setting is provided through an invalid format
       * @throw std::runtime_error
       */
      void createPersistentSession(string_view settingsLine);

      /**
       * Start the session using the provided timeout settings and callback listener.
       *
       * @throw FedPro::ConnectionFailed
       */
      void startPersistentSession(const Session::HlaCallbackRequestListener & hlaCallbackListener);

      virtual void enableCallbacks();

      virtual void disableCallbacks();

      bool isInCurrentCallbackThread() const;

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
       * Submit an HLA call, wait for the response, and decode the response.
       *
       * @throw RTI_NAMESPACE::NotConnected
       */
      CallResponse doHlaCall(const CallRequest & request);

      /**
       * Submit an HLA call.
       *
       * @throw RTI_NAMESPACE::NotConnected
       */
      std::future<ByteSequence> doAsyncHlaCall(const CallRequest & callRequest);

      /**
       * Start the callback thread to process responses.
       *
       * @throw std::runtime_error If the callback thread is already running.
       */
      void startCallbackThread();

      void stopCallbackThread();

      bool evokeCallbackBase(double approximateMinimumTimeInSeconds);

      bool evokeMultipleCallbacksBase(
            double minimumTime,
            double maximumTime);

      void disconnectBase();

      virtual void dispatchHlaCallback(std::unique_ptr<CallbackRequest> queuedCallback) = 0;

      /**
       * @throw FedPro::NotConnectedException
       */
      std::future<ByteSequence> sendCallRequest(ByteSequence && encodedHlaCall);

      PersistentSession * getSession();

      std::shared_ptr<ClientConverter> _clientConverter;

      std::unique_ptr<FederateAmbassadorDispatcher> _federateAmbassadorDispatcher;

      std::mutex _connectionStateLock;

      // Service layer settings
      // We may want to query property "client.asyncUpdates".
      bool _asyncUpdates{DEFAULT_ASYNC_UPDATES};
      std::string _protocol{};

   private:

      class QueuedCallback
      {
      public:
         enum class Type : uint8_t
         {
            REQUEST, PLACEHOLDER
         };

         std::unique_ptr<CallbackRequest> _callbackRequest;
         SequenceNumber _sequenceNumber{0};
         bool _needsResponse{false};
         Type _type{Type::REQUEST};

         /**
          * Create a callback queue entry with a request.
          */
         QueuedCallback(
               std::unique_ptr<CallbackRequest> callbackRequest,
               SequenceNumber sequenceNumber,
               bool needsResponse);

         /**
          * Create an empty callback queue entry.
          */
         explicit QueuedCallback(Type type);

         QueuedCallback(QueuedCallback &&) noexcept = default;

         QueuedCallback & operator=(QueuedCallback &&) noexcept = default;
      };

      // Put a callback into the queue
      void putCallback(QueuedCallback callback);

      // Take a callback from the queue. May block until a callback is available.
      QueuedCallback takeCallback();

      // Poll a callback from the queue.
      // Wait at least for the provided duration for a callback to become available.
      std::unique_ptr<QueuedCallback> pollCallback(
            size_t & remainingCallbacks,
            FedProDuration duration = FedProDuration{0});

      void terminatePersistentSession(); // REQUIRES(_connectionStateLock)

      void callbackLoop();

      void dispatchCallback(QueuedCallback queuedCallback);

      void sendHlaCallbackErrorResponse(
            PersistentSession * resumingClient,
            SequenceNumber sequenceNumber,
            ByteSequence && encodedHlaResponse);

      // Parse a callback and add it to the queue.
      void hlaCallbackRequest(
            SequenceNumber sequenceNumber,
            const ByteSequence & hlaCallback);

      void lostConnection(std::string reason);

      std::unique_ptr<Transport> createTransportConfiguration(const Properties & settings);

      std::mutex _queueLock;

      InterruptibleCondition _queueCondition; // GUARDED_BY(_queueLock)

      std::queue<QueuedCallback> _callbackQueue; // GUARDED_BY(_queueLock)

      std::mutex _callbackLock;

      InterruptibleCondition _callbackCondition; // GUARDED_BY(_callbackLock)

      bool _callbackInProgress{false}; // GUARDED_BY(_callbackLock)

      bool _callbacksEnabled{true}; // GUARDED_BY(_callbackLock)

      std::unique_ptr<PersistentSession> _persistentSession;

      std::unique_ptr<std::thread> _callbackThread; // GUARDED_BY(_connectionStateLock)
   };

}
