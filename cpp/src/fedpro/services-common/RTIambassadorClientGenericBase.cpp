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

// Silence clang-tidy issues reported for standard HLA exception.
// NOLINTBEGIN(hicpp-exception-baseclass)

#include "RTIambassadorClientGenericBase.h"

#include "FederateAmbassadorDispatcher.h"
#include <FedPro.h>
#include "SettingsParser.h"
#include <protobuf/generated/datatypes.pb.h>
#include <protobuf/generated/FederateAmbassador.pb.h>
#include <session/LogUtil.h>
#include <session/msg/MessageHeader.h>
#include <session/msg/HlaCallbackResponseMessage.h>
#include <session/SessionSettings.h>
#include <utility/final.h>
#include <utility/FixedRateExecutor.h>
#include <utility/LoggerInitializer.h>
#include <utility/MovingStatsNoOp.h>
#include <utility/StandAloneMovingStats.h>
#include <utility/StatsPrettyPrint.h>
#include <utility/StringUtil.h>

#include <RTI/Exception.h>

#include <cstdlib>
#include <exception>
#include <future>
#include <sstream>
#include <string>
#include <vector>

namespace FedPro
{
   RTIambassadorClientGenericBase::QueuedCallback::QueuedCallback(
         std::unique_ptr<CallbackRequest> callbackRequest,
         SequenceNumber sequenceNumber,
         bool needsResponse)
         : _callbackRequest{std::move(callbackRequest)},
           _sequenceNumber{sequenceNumber},
           _needsResponse{needsResponse}
   {
   }

   RTIambassadorClientGenericBase::QueuedCallback::QueuedCallback(Type type)
         : _type{type}
   {
   }

   RTIambassadorClientGenericBase::RTIambassadorClientGenericBase(std::shared_ptr<ClientConverter> clientConverter)
         : _clientConverter{std::move(clientConverter)}
   {
      if (!_clientConverter) {
         throw RTI_NAMESPACE::RTIinternalError(L"Missing ClientConverter instance");
      }
   }

   RTIambassadorClientGenericBase::~RTIambassadorClientGenericBase()
   {
      // Terminating session to destroy when this destructor returns.
      // _connectionStateLock MUST NOT be locked when this happens to prevent deadlocks.
      std::unique_ptr<PersistentSession> terminatingSession;
      {
         std::lock_guard<std::mutex> guard(_connectionStateLock);
         if (_persistentSession) {
            try {
               _persistentSession->terminate(FedProDuration{0});
            } catch (const FedPro::SessionLost &) {
            } catch (const FedPro::SessionAlreadyTerminated &) {
            }
            _persistentSession.swap(terminatingSession);
            _shutdownInProgress = true;
         }
      }

      // Wait for listeners to complete before destroying PersistentSession (cf terminatingSession),
      // since lostConnection() and sessionTerminated() need to access this client object,
      // and this the last opportunity to wait for the session's listeners.
      if (terminatingSession) {
         terminatingSession->waitListeners();
      }
      // Wait for threads to complete before destroying PersistentSession and this client object,
      // since printStats() and dispatchCallback() need to access PersistentSession and/or this client object,
      cleanUpTerminatingSession();
   }

   RTIambassadorClientGenericBase::CallResponse RTIambassadorClientGenericBase::doConnect(
         RTI_NAMESPACE::FederateAmbassador & federateReference,
         RTI_NAMESPACE::CallbackModel callbackModel,
         const CallRequest & callRequest,
         std::unique_ptr<PersistentSession> & failedSession) noexcept(false)
   {
      try {
         _persistentSession->start(
               [this](
                     SequenceNumber seq,
                     const ByteSequence & hlaCallback) { hlaCallbackRequest(seq, hlaCallback); });
         CallResponse callResponse = doHlaCall(callRequest);
         _federateAmbassadorDispatcher = std::make_unique<FederateAmbassadorDispatcher>(
               &federateReference, _clientConverter, [this]() -> std::unique_ptr<MovingStats> {
                  if (_printStats) {
                     return std::make_unique<StandAloneMovingStats>(_printStatsIntervalMillis);
                  } else {
                     return std::make_unique<MovingStatsNoOp>();
                  }
               });
         // Todo - I don't see how we need throwOnException() here? remove?
         throwOnException(callResponse);
         startStatsPrinting();
         if (callbackModel == RTI_NAMESPACE::HLA_IMMEDIATE) {
            startCallbackThread();
         }
         return callResponse;

      } catch (const SessionLost & e) {
         // There's no point keeping the session if it failed to initialize
         _persistentSession.swap(failedSession);
         throw RTI_NAMESPACE::ConnectionFailed(
               toWString("Failed to start FedPro session: ") + toWString(e.what()));
      } catch (const SessionIllegalState & e) {
         // There's no point keeping the session if it failed to initialize
         _persistentSession.swap(failedSession);
         throw RTI_NAMESPACE::ConnectionFailed(
               toWString("Failed to start FedPro session: ") + toWString(e.what()));
      } catch (const RTI_NAMESPACE::NotConnected & e) {
         // There's no point keeping the session if it failed to initialize
         _persistentSession.swap(failedSession);
         throw RTI_NAMESPACE::ConnectionFailed(toWString(L"Failed to connect: ") + e.what());
      }
   }

   void RTIambassadorClientGenericBase::createPersistentSession(const Properties & settings)
   {
      // No logging is done before this:
      LoggerInitializer::initialize(settings);

      // Always assign to honor changes in settings between successive connect/disconnect calls.
      _asyncUpdates = settings.getBoolean(SETTING_NAME_ASYNC_UPDATES, DEFAULT_ASYNC_UPDATES);

      _persistentSession = FedPro::createPersistentSession(
            createTransportConfiguration(settings),
            [this](std::string reason) { lostConnection(std::move(reason)); },
            [this](uint64_t sessionId) { sessionTerminated(sessionId); },
            settings,
            std::make_unique<SimpleResumeStrategy>(settings));

      Properties allServiceSettingsUsed;
      allServiceSettingsUsed.setBoolean(SETTING_NAME_ASYNC_UPDATES, _asyncUpdates);
      allServiceSettingsUsed.setString(SETTING_NAME_CONNECTION_PROTOCOL, _protocol);

      SPDLOG_INFO("Federate Protocol client service layer settings used:\n" + allServiceSettingsUsed.toPrettyString());

      _printStatsIntervalMillis = settings.getDuration(SETTING_NAME_PRINT_STATS_INTERVAL, DEFAULT_PRINT_STATS_INTERVAL_MILLIS);
      _printStats = settings.getBoolean(SETTING_NAME_PRINT_STATS, false);
      if (_printStats) {
         _hlaSyncUpdateStats = std::make_unique<StandAloneMovingStats>(_printStatsIntervalMillis);
         _hlaAsyncUpdateStats = std::make_unique<StandAloneMovingStats>(_printStatsIntervalMillis);
         _hlaSyncSentInteraction = std::make_unique<StandAloneMovingStats>(_printStatsIntervalMillis);
         _hlaAsyncSentInteraction = std::make_unique<StandAloneMovingStats>(_printStatsIntervalMillis);
         _hlaSyncSentDirectedInteraction = std::make_unique<StandAloneMovingStats>(_printStatsIntervalMillis);
         _hlaAsyncSentDirectedInteraction = std::make_unique<StandAloneMovingStats>(_printStatsIntervalMillis);
      } else {
         _hlaSyncUpdateStats = std::make_unique<MovingStatsNoOp>();
         _hlaAsyncUpdateStats = std::make_unique<MovingStatsNoOp>();
         _hlaSyncSentInteraction = std::make_unique<MovingStatsNoOp>();
         _hlaAsyncSentInteraction = std::make_unique<MovingStatsNoOp>();
         _hlaSyncSentDirectedInteraction = std::make_unique<MovingStatsNoOp>();
         _hlaAsyncSentDirectedInteraction = std::make_unique<MovingStatsNoOp>();
      }
   }

   std::unique_ptr<Transport> RTIambassadorClientGenericBase::createTransportConfiguration(const Properties & settings)
   {
      std::unique_ptr<Transport> transport;

      _protocol = settings.getString(SETTING_NAME_CONNECTION_PROTOCOL, TCP);
      if (_protocol == TLS) {
         // TODO: Create option to allow for TLS clients
         throw std::runtime_error("Option 'tls' not implemented.");
      } else if (_protocol == WS) {
         transport = createWebSocketTransport(settings);
      } else if (_protocol == WSS) {
         // TODO: Create option to allow for WebSocket Secure clients
         throw std::runtime_error("Option 'websocketsecure' not implemented.");
      } else if (_protocol == TCP) {
         transport = createTcpTransport(settings);
      } else {
         throw RTI_NAMESPACE::RTIinternalError(
               L"Invalid network protocol '" + make_wstring(_protocol) + L"' in settings");
      }
      return transport;
   }

   void RTIambassadorClientGenericBase::startCallbackThread()
   {
      if (_callbackThread && _callbackThread->joinable()) {
         throw std::runtime_error("Callback thread is running or pending join");
      }

      _callbackThread = std::make_unique<std::thread>(&RTIambassadorClientGenericBase::callbackLoop, this);
   }

   void RTIambassadorClientGenericBase::stopCallbackThread()
   {
      try {
         // Interrupts the thread waiting on the callback queue.
         // Should we drop all queued callbacks?
         {
            std::lock_guard<std::mutex> queueLock(_queueLock);
            _queueCondition.interruptOne();
         }

         {
            std::lock_guard<std::mutex> lock(_callbackLock);
            // If callbacks are disabled, then evoke may be stuck waiting
            // for notification and callbacks to be enabled.
            _callbacksEnabled = true;
            _callbackCondition.notifyAll();
         }

         // _connectionStateLock needs to be released at this point as _callbackThread may be stuck in a deadlock
         //   waiting for _connectionStateLock.

         //noinspection FieldAccessNotGuarded
         if (_callbackThread && _callbackThread->joinable()) {
            //noinspection FieldAccessNotGuarded
            _callbackThread->join();
         } else {
            // Evoked mode.
         }
      } catch (const InterruptedException &) {
      }

      {
         std::lock_guard<std::mutex> guard(_connectionStateLock);
         _callbackThread = nullptr;
      }
   }

   void RTIambassadorClientGenericBase::startStatsPrinting()
   {
      stopStatsPrinting();
      if (_printStats) {
         auto printStatsExecutor =
               makeFixedRateExecutor([this]() { printStats(); }, _printStatsIntervalMillis, _printStatsIntervalMillis);
         _printStatsInterrupt = printStatsExecutor.getState();
         _statPrintingExecutor = std::thread(printStatsExecutor);
      }
   }

   void RTIambassadorClientGenericBase::stopStatsPrinting()
   {
      if (_printStatsInterrupt) {
         _printStatsInterrupt->interrupt();
         _printStatsInterrupt.reset();
         _statPrintingExecutor.join();
      }
   }

   void RTIambassadorClientGenericBase::countSyncUpdateForStats()
   {
      _hlaSyncUpdateStats->sample(1);
   }

   void RTIambassadorClientGenericBase::countAsyncUpdateForStats()
   {
      _hlaAsyncUpdateStats->sample(1);
   }

   void RTIambassadorClientGenericBase::countSyncSentInteractionForStats()
   {
      _hlaSyncSentInteraction->sample(1);
   }

   void RTIambassadorClientGenericBase::countAsyncSentInteractionForStats()
   {
      _hlaAsyncSentInteraction->sample(1);
   }

   void RTIambassadorClientGenericBase::countSyncSentDirectedInteractionForStats()
   {
      _hlaSyncSentDirectedInteraction->sample(1);
   }

   void RTIambassadorClientGenericBase::countAsyncSentDirectedInteractionForStats()
   {
      _hlaAsyncSentDirectedInteraction->sample(1);
   }

   MovingStats::Stats RTIambassadorClientGenericBase::getReflectStats(MovingStats::SteadyTimePoint time)
   {
      return _federateAmbassadorDispatcher->getReflectStats(time);
   }

   MovingStats::Stats RTIambassadorClientGenericBase::getReceivedInteractionStats(MovingStats::SteadyTimePoint time)
   {
      return _federateAmbassadorDispatcher->getReceivedInteractionStats(time);
   }

   MovingStats::Stats RTIambassadorClientGenericBase::getReceivedDirectedInteractionStats(MovingStats::SteadyTimePoint time)
   {
      return _federateAmbassadorDispatcher->getReceivedDirectedInteractionStats(time);
   }

   MovingStats::Stats RTIambassadorClientGenericBase::getCallbackTimeStats(MovingStats::SteadyTimePoint time)
   {
      return _federateAmbassadorDispatcher->getCallbackTimeStats(time);
   }

   bool RTIambassadorClientGenericBase::evokeCallbackBase(double approximateMinimumTimeInSeconds)
   {
      {
         std::lock_guard<std::mutex> guard(_connectionStateLock);
         if (_callbackThread) {
            // Immediate mode
            return false;
         }
      }

      using clock_type = std::chrono::high_resolution_clock;

      // Converts a number of seconds encoded as floating point, to a number of clock period encoded as integer.
      constexpr const double clockPeriodsPerSec = static_cast<double>(clock_type::duration::period::den) /
                                                  static_cast<double>(clock_type::duration::period::num);
      clock_type::duration approximateMinimumTimeClockPeriods
            {static_cast<clock_type::rep>(approximateMinimumTimeInSeconds * clockPeriodsPerSec)};

      std::chrono::time_point<clock_type> start = clock_type::now();
      std::chrono::time_point<clock_type> minTime = start + approximateMinimumTimeClockPeriods;

      try {
         {
            std::unique_lock<std::mutex> lock(_callbackLock);
            while (!_callbacksEnabled) {
               if (_callbackCondition.waitUntil(lock, minTime) == std::cv_status::timeout) {
                  return false;
               }
            }
            // Callbacks are enabled. Let's go!
            _callbackInProgress = true;
         }

         // Function to execute at the end of the loop, or if returning early.
         auto final_guard = make_final(
               [this]() {
                  std::lock_guard<std::mutex> lock(_callbackLock);

                  _callbackInProgress = false;
                  _callbackCondition.notifyAll();
               });

         size_t remainingCallbacks{0};
         FedProDuration timeRemainingMillis = std::chrono::duration_cast<FedProDuration>(minTime - clock_type::now());
         std::unique_ptr<QueuedCallback> queuedCallback = pollCallback(remainingCallbacks, timeRemainingMillis);
         if (!queuedCallback) {
            return false;
         }
         if (queuedCallback->_type == QueuedCallback::Type::PLACEHOLDER) {
            // Wakeup from enableCallbacks. Why did we end up here?
            // If callbacks were disabled, we should be stuck in tryAcquire.
            return remainingCallbacks > 0;
         }

         dispatchCallback(std::move(*queuedCallback));
         return remainingCallbacks > 0;
      } catch (const InterruptedException &) {
         SPDLOG_DEBUG("Interrupted while waiting for callback.");
         return false;
      }
   }

   bool RTIambassadorClientGenericBase::evokeMultipleCallbacksBase(
         double minimumTime,
         double maximumTime)
   {
      {
         std::lock_guard<std::mutex> guard(_connectionStateLock);
         if (_callbackThread) {
            // Immediate mode
            return false;
         }
      }

      using clock_type = std::chrono::high_resolution_clock;

      // Converts a number of seconds encoded as floating point, to a number of clock period encoded as integer.
      constexpr const double clockPeriodsPerSec = static_cast<double>(clock_type::duration::period::den) /
                                                  static_cast<double>(clock_type::duration::period::num);
      clock_type::duration minimumTimeClockPeriods{static_cast<clock_type::rep>(minimumTime * clockPeriodsPerSec)};
      clock_type::duration maximumTimeClockPeriods{static_cast<clock_type::rep>(maximumTime * clockPeriodsPerSec)};

      std::chrono::time_point<clock_type> start = clock_type::now();
      std::chrono::time_point<clock_type> waitLimitPoint = start + minimumTimeClockPeriods;
      std::chrono::time_point<clock_type> cutoffTimePoint = start + maximumTimeClockPeriods;

      while (true) {
         try {
            {
               std::unique_lock<std::mutex> lock(_callbackLock);
               while (!_callbacksEnabled) {
                  if (_callbackCondition.waitUntil(lock, waitLimitPoint) == std::cv_status::timeout) {
                     return false;
                  }
               }
               // Callbacks are enabled. Let's go!
               _callbackInProgress = true;
            }

            // Function to execute at the end of the loop, or if returning early.
            auto final_guard = make_final(
                  [this]() {
                     std::lock_guard<std::mutex> scopedLock(_callbackLock);

                     _callbackInProgress = false;
                     _callbackCondition.notifyAll();
                  });

            std::unique_ptr<QueuedCallback> queuedCallback;
            size_t remainingCallbacks{0};

            clock_type::time_point now = clock_type::now();
            if (now < waitLimitPoint) {
               // If we haven't reached minimumTime, wait until callback becomes available but not longer than minimumTime
               FedProDuration timeout = std::chrono::duration_cast<FedProDuration>(waitLimitPoint - now);
               queuedCallback = pollCallback(remainingCallbacks, timeout);
            } else if (now < cutoffTimePoint) {
               // We are past minimumTime. Keep going until no more callbacks or we hit maximumTime
               FedProDuration timeout = std::chrono::duration_cast<FedProDuration>(cutoffTimePoint - now);
               queuedCallback = pollCallback(remainingCallbacks, timeout);
            } else {
               std::unique_lock<std::mutex> queueGuard(_queueLock);
               remainingCallbacks = _callbackQueue.size();
            }

            if (!queuedCallback) {
               // No more callbacks or we ran out of time
               return remainingCallbacks > 0;
            }
            if (queuedCallback->_type == QueuedCallback::Type::PLACEHOLDER) {
               // Wakeup from enableCallbacks/disableCallbacks. Loop around and start over
               return remainingCallbacks > 0;
            }
            dispatchCallback(std::move(*queuedCallback));
         } catch (const InterruptedException &) {
            SPDLOG_DEBUG("Interrupted while waiting for callback.");
            return false;
         }
      }
   }

   void RTIambassadorClientGenericBase::disconnectBase()
   {
      // Terminating session to destroy when this function returns.
      // _connectionStateLock MUST NOT be locked when this happens to prevent deadlocks.
      std::unique_ptr<PersistentSession> terminatingSession;
      {
         std::lock_guard<std::mutex> guard(_connectionStateLock);
         if (_shutdownInProgress) {
            return;
         }

         try {
            rti1516_2025::fedpro::CallRequest callRequest;
            callRequest.set_allocated_disconnectrequest(new rti1516_2025::fedpro::DisconnectRequest);
            CallResponse callResponse = doHlaCall(callRequest);
            // Todo - I don't see how we need throwOnException() here? remove?
            throwOnException(callResponse);
         } catch (const RTI_NAMESPACE::NotConnected & e) {
            const std::string eAsString = toString(e.what());
            SPDLOG_DEBUG("Call to disconnect while not connected: {}", eAsString);
            // Already disconnected. Just ignore.
            return;
         }

         terminatePersistentSession();
         _persistentSession.swap(terminatingSession);
         _shutdownInProgress = true;
      }

      // Wait for listeners to complete before destroying PersistentSession (cf terminatingSession),
      // since lostConnection() and sessionTerminated() need to access this client object,
      // and this the last opportunity to wait for the session's listeners.
      terminatingSession->waitListeners();
      // Wait for threads to complete before destroying PersistentSession (cf terminatingSession),
      // since dispatchCallback() need to access PersistentSession,
      cleanUpTerminatingSession();
      {
         std::lock_guard<std::mutex> guard(_connectionStateLock);
         _shutdownInProgress = false;
      }
   }

   void RTIambassadorClientGenericBase::terminatePersistentSession()
   {
      try {
         if (_persistentSession) {
            _persistentSession->terminate();
         }
      } catch (const SessionLost & e) {
         SPDLOG_DEBUG("Exception while trying to disconnect: {}", e.what());
         // Termination request did not complete.
      } catch (const SessionAlreadyTerminated & e) {
         SPDLOG_DEBUG("Call to disconnect while not connected: {}", e.what());
         // Already disconnected. Just ignore.
      } catch (const SessionIllegalState & e) {
         // TODO: What happens in the standard API when we call disconnect before the RTIambassador is connected?
         throw RTI_NAMESPACE::RTIinternalError(_clientConverter->convertToHla(e.what()));
      }
   }

   static ::rti1516_2025::fedpro::CallbackResponse createCallbackResponse(
         const char * exceptionName,
         const wstring_view exceptionDetails)
   {
      auto * exceptionData = new ::rti1516_2025::fedpro::ExceptionData();
      exceptionData->set_exceptionname(exceptionName);
      exceptionData->set_details(toString(exceptionDetails));

      ::rti1516_2025::fedpro::CallbackResponse callbackResponse;
      callbackResponse.set_allocated_callbackfailed(exceptionData);
      return callbackResponse;
   }

   bool RTIambassadorClientGenericBase::isInCurrentCallbackThread() const
   {
      return (_callbackThread && std::this_thread::get_id() == _callbackThread->get_id());
   }

   std::vector<std::wstring> RTIambassadorClientGenericBase::splitFederateConnectSettings(const std::wstring & inputString)
   {
      // Regex to match line breaks or commas
      std::wregex regex(L"(\\r?\\n|,)");
      std::wsregex_token_iterator it(inputString.begin(), inputString.end(), regex, -1); // -1 indicates split mode
      std::wsregex_token_iterator end;

      std::vector<std::wstring> result;
      for (; it != end; ++it) {
         result.emplace_back(*it);
      }

      // Check if the input string ends with a delimiter
      if (!inputString.empty() && std::regex_match(inputString.substr(inputString.size() - 1), regex)) {
         result.emplace_back(L"");
      }

      return result;
   }

   void RTIambassadorClientGenericBase::addToSettingsLine(
         std::string & settingsLine,
         string_view toAdd)
   {
      if (!settingsLine.empty()) {
         settingsLine += ',';
      }
      settingsLine.append(toString(toAdd));
   }

   std::string RTIambassadorClientGenericBase::extractAndRemoveClientSettings(
         std::vector<std::wstring> & inputValueList,
         bool crcAddressIsFedProServerAddress)
   {
      /*
       * This method moves all settings that are prefixed with 'FedPro.'from the input parameter to client setting
       * string and return it.
       * Exception: If the boolean parameter is set to true, the setting 'crcAddress' is interpreted as the address of
       * the FedPro server, not as an LRC setting.
       */
      const std::wstring crcAddressPrefix{L"crcAddress="};
      std::string clientSettings;

      auto iterator = inputValueList.begin();
      while (iterator != inputValueList.end()) {
         std::wstring settingsLine = *iterator;

         if (settingsLine.find(crcAddressPrefix) == 0 && crcAddressIsFedProServerAddress) {
            // Handle the case where crcAddress is interpreted as the FedPro server address

            std::wstring serverAddress = settingsLine.substr(crcAddressPrefix.length());
            std::vector<wstring_view> serverHostAndPort = splitString(serverAddress, L':', 2);
            if (!serverHostAndPort[0].empty()) {
               std::string serverHost = toString(serverHostAndPort[0]);
               addToSettingsLine(clientSettings, std::string{SETTING_NAME_CONNECTION_HOST} + "=" + serverHost);
            }
            if (serverHostAndPort.size() > 1 && !serverHostAndPort[1].empty()) {
               std::string serverPort = toString(serverHostAndPort[1]);
               addToSettingsLine(clientSettings, std::string{SETTING_NAME_CONNECTION_PORT} + "=" + serverPort);
            }

            iterator = inputValueList.erase(iterator); // Remove the processed element

         } else if (settingsLine.find(SETTING_PREFIX_WIDE_STRING) == 0) {
            // Handle settings prefixed with 'FedPro.'

            std::wstring setting = settingsLine.substr(SETTING_PREFIX_WIDE_STRING.length());
            addToSettingsLine(clientSettings, toString(setting));

            iterator = inputValueList.erase(iterator); // Remove the processed element

         } else {
            ++iterator; // Move to the next element
         }
      }
      return clientSettings;
   }

   std::wstring RTIambassadorClientGenericBase::toAdditionalSettingsString(const std::vector<std::wstring> & inputValueList)
   {
      std::wostringstream additionalSettingsStream;
      for (size_t i = 0; i < inputValueList.size(); ++i) {
         additionalSettingsStream << inputValueList[i];
         if (i < inputValueList.size() - 1) {
            // Add newline except after the last element
            additionalSettingsStream << '\n';
         }
      }
      return additionalSettingsStream.str();
   }

   std::future<ByteSequence> RTIambassadorClientGenericBase::sendCallRequest(ByteSequence && encodedHlaCall)
   {
      if (!_persistentSession) {
         throw RTI_NAMESPACE::NotConnected(L"HLA call without a federate protocol session");
      }

      return _persistentSession->sendHlaCallRequest(encodedHlaCall);
   }

   void RTIambassadorClientGenericBase::enableCallbacks()
   {
      try {
         // Push placeholder callback
         QueuedCallback placeholder{QueuedCallback::Type::PLACEHOLDER};
         putCallback(std::move(placeholder));

         std::lock_guard<std::mutex> lock(_callbackLock);
         _callbacksEnabled = true;
         _callbackCondition.notifyAll();
      } catch (const InterruptedException &) {
         throw RTI_NAMESPACE::RTIinternalError(L"Interrupted while waiting for callback lock");
      }
   }

   void RTIambassadorClientGenericBase::disableCallbacks()
   {
      try {
         // Push placeholder callback
         QueuedCallback placeholder{QueuedCallback::Type::PLACEHOLDER};
         putCallback(std::move(placeholder));

         std::unique_lock<std::mutex> lock(_callbackLock);
         _callbacksEnabled = false;
         _callbackCondition.notifyAll();

         // Do not wait for the callback thread if you are the callback thread
         if (!isInCurrentCallbackThread()) {
            // COM_9-2 states that disableCallbacks shall not return until an
            // ongoing callback has finished
            _callbackCondition.wait(lock, [this]() { return !_callbackInProgress; });
         }
      } catch (const InterruptedException &) {
         throw RTI_NAMESPACE::RTIinternalError(L"Interrupted while waiting for callback lock");
      }
   }

   PersistentSession * RTIambassadorClientGenericBase::getSession()
   {
      std::lock_guard<std::mutex> guard(_connectionStateLock);
      return _persistentSession.get();
   }

   RTIambassadorClientGenericBase::CallResponse RTIambassadorClientGenericBase::doHlaCall(const CallRequest & callRequest)
   {
      try {
         ByteSequence encodedRequest;
         if (!callRequest.SerializeToString(&encodedRequest)) {
            throw RTI_NAMESPACE::RTIinternalError(
                  L"Failed to serialize the CallRequest as Protocol Buffer, possibly due to missing fields. ");
         }

         std::future<ByteSequence> call = sendCallRequest(std::move(encodedRequest));

         // Todo: Probably a bug, this call to get() will rethrow any exception instead of storing it in encodedResponse.
         //   Try / catch block instead?
         //   Possible exceptions are: MessageQueueFull and SessionLost.
         ByteSequence encodedResponse = call.get();

         return decodeHlaCallResponse(encodedResponse);

      } catch (const std::ios_base::failure & e) {
         throw RTI_NAMESPACE::RTIinternalError(L"Failed to perform HLA call. " + make_wstring(e.what()));
      } catch (const SessionIllegalState & e) {
         throw RTI_NAMESPACE::NotConnected(L"Failed to perform HLA call. " + make_wstring(e.what()));
      }
   }

   std::future<ByteSequence> RTIambassadorClientGenericBase::doAsyncHlaCall(const CallRequest & callRequest)
   {
      ByteSequence encodedRequest;
      if (!callRequest.SerializeToString(&encodedRequest)) {
         throw RTI_NAMESPACE::RTIinternalError(
               L"Failed to serialize the CallRequest as Protocol Buffer, possibly due to missing fields. ");
      }

      try {
         return sendCallRequest(std::move(encodedRequest));
      } catch (const SessionIllegalState & e) {
         throw RTI_NAMESPACE::NotConnected(L"Failed to perform HLA call. " + make_wstring(e.what()));
      }
   }

   RTIambassadorClientGenericBase::CallResponse RTIambassadorClientGenericBase::decodeHlaCallResponse(const ByteSequence & encodedResponse)
   {
      CallResponse callResponse;
      if (!callResponse.ParseFromString(encodedResponse)) {
         throw RTI_NAMESPACE::RTIinternalError(L"Failed to parse the CallbackRequest Protocol Buffer");
      }

      throwOnException(callResponse);

      return callResponse;
   }

   void RTIambassadorClientGenericBase::throwOnException(const CallResponse & callResponse)
   {
      if (callResponse.has_exceptiondata()) {
         const rti1516_2025::fedpro::ExceptionData & exceptionData = callResponse.exceptiondata();
         const std::string & exceptionName = exceptionData.exceptionname();
         const std::string & details = exceptionData.details();
         if (exceptionName == "AlreadyConnected") {
            throw RTI_NAMESPACE::AlreadyConnected(toWString(details));
         } else if (exceptionName == "AsynchronousDeliveryAlreadyDisabled") {
            throw RTI_NAMESPACE::AsynchronousDeliveryAlreadyDisabled(toWString(details));
         } else if (exceptionName == "AsynchronousDeliveryAlreadyEnabled") {
            throw RTI_NAMESPACE::AsynchronousDeliveryAlreadyEnabled(toWString(details));
         } else if (exceptionName == "AttributeAcquisitionWasNotRequested") {
            throw RTI_NAMESPACE::AttributeAcquisitionWasNotRequested(toWString(details));
         } else if (exceptionName == "AttributeAlreadyBeingAcquired") {
            throw RTI_NAMESPACE::AttributeAlreadyBeingAcquired(toWString(details));
         } else if (exceptionName == "AttributeAlreadyBeingChanged") {
            throw RTI_NAMESPACE::AttributeAlreadyBeingChanged(toWString(details));
         } else if (exceptionName == "AttributeAlreadyBeingDivested") {
            throw RTI_NAMESPACE::AttributeAlreadyBeingDivested(toWString(details));
         } else if (exceptionName == "AttributeAlreadyOwned") {
            throw RTI_NAMESPACE::AttributeAlreadyOwned(toWString(details));
         } else if (exceptionName == "AttributeDivestitureWasNotRequested") {
            throw RTI_NAMESPACE::AttributeDivestitureWasNotRequested(toWString(details));
         } else if (exceptionName == "AttributeNotDefined") {
            throw RTI_NAMESPACE::AttributeNotDefined(toWString(details));
         } else if (exceptionName == "AttributeNotOwned") {
            throw RTI_NAMESPACE::AttributeNotOwned(toWString(details));
         } else if (exceptionName == "AttributeNotPublished") {
            throw RTI_NAMESPACE::AttributeNotPublished(toWString(details));
         } else if (exceptionName == "AttributeRelevanceAdvisorySwitchIsOff") {
            throw RTI_NAMESPACE::AttributeRelevanceAdvisorySwitchIsOff(toWString(details));
         } else if (exceptionName == "AttributeRelevanceAdvisorySwitchIsOn") {
            throw RTI_NAMESPACE::AttributeRelevanceAdvisorySwitchIsOn(toWString(details));
         } else if (exceptionName == "AttributeScopeAdvisorySwitchIsOff") {
            throw RTI_NAMESPACE::AttributeScopeAdvisorySwitchIsOff(toWString(details));
         } else if (exceptionName == "AttributeScopeAdvisorySwitchIsOn") {
            throw RTI_NAMESPACE::AttributeScopeAdvisorySwitchIsOn(toWString(details));
         } else if (exceptionName == "CallNotAllowedFromWithinCallback") {
            throw RTI_NAMESPACE::CallNotAllowedFromWithinCallback(toWString(details));
         } else if (exceptionName == "ConnectionFailed") {
            throw RTI_NAMESPACE::ConnectionFailed(toWString(details));
         } else if (exceptionName == "CouldNotCreateLogicalTimeFactory") {
            throw RTI_NAMESPACE::CouldNotCreateLogicalTimeFactory(toWString(details));
         } else if (exceptionName == "CouldNotDecode") {
            throw RTI_NAMESPACE::CouldNotDecode(toWString(details));
         } else if (exceptionName == "CouldNotEncode") {
            throw RTI_NAMESPACE::CouldNotEncode(toWString(details));
         } else if (exceptionName == "CouldNotOpenMIM") {
            throw RTI_NAMESPACE::CouldNotOpenMIM(toWString(details));
         } else if (exceptionName == "DeletePrivilegeNotHeld") {
            throw RTI_NAMESPACE::DeletePrivilegeNotHeld(toWString(details));
         } else if (exceptionName == "DesignatorIsHLAstandardMIM") {
            throw RTI_NAMESPACE::DesignatorIsHLAstandardMIM(toWString(details));
         } else if (exceptionName == "RequestForTimeConstrainedPending") {
            throw RTI_NAMESPACE::RequestForTimeConstrainedPending(toWString(details));
         } else if (exceptionName == "RequestForTimeRegulationPending") {
            throw RTI_NAMESPACE::RequestForTimeRegulationPending(toWString(details));
         } else if (exceptionName == "ErrorReadingMIM") {
            throw RTI_NAMESPACE::ErrorReadingMIM(toWString(details));
         } else if (exceptionName == "FederateAlreadyExecutionMember") {
            throw RTI_NAMESPACE::FederateAlreadyExecutionMember(toWString(details));
         } else if (exceptionName == "FederateHandleNotKnown") {
            throw RTI_NAMESPACE::FederateHandleNotKnown(toWString(details));
         } else if (exceptionName == "FederateHasNotBegunSave") {
            throw RTI_NAMESPACE::FederateHasNotBegunSave(toWString(details));
         } else if (exceptionName == "FederateInternalError") {
            throw RTI_NAMESPACE::FederateInternalError(toWString(details));
         } else if (exceptionName == "FederateIsExecutionMember") {
            throw RTI_NAMESPACE::FederateIsExecutionMember(toWString(details));
         } else if (exceptionName == "FederateNameAlreadyInUse") {
            throw RTI_NAMESPACE::FederateNameAlreadyInUse(toWString(details));
         } else if (exceptionName == "FederateNotExecutionMember") {
            throw RTI_NAMESPACE::FederateNotExecutionMember(toWString(details));
         } else if (exceptionName == "FederateOwnsAttributes") {
            throw RTI_NAMESPACE::FederateOwnsAttributes(toWString(details));
         } else if (exceptionName == "FederateServiceInvocationsAreBeingReportedViaMOM") {
            throw RTI_NAMESPACE::FederateServiceInvocationsAreBeingReportedViaMOM(toWString(details));
         } else if (exceptionName == "FederateUnableToUseTime") {
            throw RTI_NAMESPACE::FederateUnableToUseTime(toWString(details));
         } else if (exceptionName == "FederatesCurrentlyJoined") {
            throw RTI_NAMESPACE::FederatesCurrentlyJoined(toWString(details));
         } else if (exceptionName == "FederationExecutionAlreadyExists") {
            throw RTI_NAMESPACE::FederationExecutionAlreadyExists(toWString(details));
         } else if (exceptionName == "FederationExecutionDoesNotExist") {
            throw RTI_NAMESPACE::FederationExecutionDoesNotExist(toWString(details));
         } else if (exceptionName == "IllegalName") {
            throw RTI_NAMESPACE::IllegalName(toWString(details));
         } else if (exceptionName == "IllegalTimeArithmetic") {
            throw RTI_NAMESPACE::IllegalTimeArithmetic(toWString(details));
         } else if (exceptionName == "InteractionClassAlreadyBeingChanged") {
            throw RTI_NAMESPACE::InteractionClassAlreadyBeingChanged(toWString(details));
         } else if (exceptionName == "InteractionClassNotDefined") {
            throw RTI_NAMESPACE::InteractionClassNotDefined(toWString(details));
         } else if (exceptionName == "InteractionClassNotPublished") {
            throw RTI_NAMESPACE::InteractionClassNotPublished(toWString(details));
         } else if (exceptionName == "InteractionParameterNotDefined") {
            throw RTI_NAMESPACE::InteractionParameterNotDefined(toWString(details));
         } else if (exceptionName == "InteractionRelevanceAdvisorySwitchIsOff") {
            throw RTI_NAMESPACE::InteractionRelevanceAdvisorySwitchIsOff(toWString(details));
         } else if (exceptionName == "InteractionRelevanceAdvisorySwitchIsOn") {
            throw RTI_NAMESPACE::InteractionRelevanceAdvisorySwitchIsOn(toWString(details));
         } else if (exceptionName == "InTimeAdvancingState") {
            throw RTI_NAMESPACE::InTimeAdvancingState(toWString(details));
         } else if (exceptionName == "InvalidAttributeHandle") {
            throw RTI_NAMESPACE::InvalidAttributeHandle(toWString(details));
         } else if (exceptionName == "InvalidDimensionHandle") {
            throw RTI_NAMESPACE::InvalidDimensionHandle(toWString(details));
         } else if (exceptionName == "InvalidFederateHandle") {
            throw RTI_NAMESPACE::InvalidFederateHandle(toWString(details));
         } else if (exceptionName == "InvalidInteractionClassHandle") {
            throw RTI_NAMESPACE::InvalidInteractionClassHandle(toWString(details));
         } else if (exceptionName == "InvalidLogicalTime") {
            throw RTI_NAMESPACE::InvalidLogicalTime(toWString(details));
         } else if (exceptionName == "InvalidLogicalTimeInterval") {
            throw RTI_NAMESPACE::InvalidLogicalTimeInterval(toWString(details));
         } else if (exceptionName == "InvalidLookahead") {
            throw RTI_NAMESPACE::InvalidLookahead(toWString(details));
         } else if (exceptionName == "InvalidObjectClassHandle") {
            throw RTI_NAMESPACE::InvalidObjectClassHandle(toWString(details));
         } else if (exceptionName == "InvalidOrderName") {
            throw RTI_NAMESPACE::InvalidOrderName(toWString(details));
         } else if (exceptionName == "InvalidOrderType") {
            throw RTI_NAMESPACE::InvalidOrderType(toWString(details));
         } else if (exceptionName == "InvalidParameterHandle") {
            throw RTI_NAMESPACE::InvalidParameterHandle(toWString(details));
         } else if (exceptionName == "InvalidRangeBound") {
            throw RTI_NAMESPACE::InvalidRangeBound(toWString(details));
         } else if (exceptionName == "InvalidRegion") {
            throw RTI_NAMESPACE::InvalidRegion(toWString(details));
         } else if (exceptionName == "InvalidResignAction") {
            throw RTI_NAMESPACE::InvalidResignAction(toWString(details));
         } else if (exceptionName == "InvalidRegionContext") {
            throw RTI_NAMESPACE::InvalidRegionContext(toWString(details));
         } else if (exceptionName == "InvalidMessageRetractionHandle") {
            throw RTI_NAMESPACE::InvalidMessageRetractionHandle(toWString(details));
         } else if (exceptionName == "InvalidServiceGroup") {
            throw RTI_NAMESPACE::InvalidServiceGroup(toWString(details));
         } else if (exceptionName == "InvalidTransportationName") {
            throw RTI_NAMESPACE::InvalidTransportationName(toWString(details));
         } else if (exceptionName == "InvalidUpdateRateDesignator") {
            throw RTI_NAMESPACE::InvalidUpdateRateDesignator(toWString(details));
         } else if (exceptionName == "LogicalTimeAlreadyPassed") {
            throw RTI_NAMESPACE::LogicalTimeAlreadyPassed(toWString(details));
         } else if (exceptionName == "MessageCanNoLongerBeRetracted") {
            throw RTI_NAMESPACE::MessageCanNoLongerBeRetracted(toWString(details));
         } else if (exceptionName == "NameNotFound") {
            throw RTI_NAMESPACE::NameNotFound(toWString(details));
         } else if (exceptionName == "NameSetWasEmpty") {
            throw RTI_NAMESPACE::NameSetWasEmpty(toWString(details));
         } else if (exceptionName == "NoAcquisitionPending") {
            throw RTI_NAMESPACE::NoAcquisitionPending(toWString(details));
         } else if (exceptionName == "NotConnected") {
            throw RTI_NAMESPACE::NotConnected(toWString(details));
         } else if (exceptionName == "ObjectClassNotDefined") {
            throw RTI_NAMESPACE::ObjectClassNotDefined(toWString(details));
         } else if (exceptionName == "ObjectClassNotPublished") {
            throw RTI_NAMESPACE::ObjectClassNotPublished(toWString(details));
         } else if (exceptionName == "ObjectClassRelevanceAdvisorySwitchIsOff") {
            throw RTI_NAMESPACE::ObjectClassRelevanceAdvisorySwitchIsOff(toWString(details));
         } else if (exceptionName == "ObjectClassRelevanceAdvisorySwitchIsOn") {
            throw RTI_NAMESPACE::ObjectClassRelevanceAdvisorySwitchIsOn(toWString(details));
         } else if (exceptionName == "ObjectInstanceNameInUse") {
            throw RTI_NAMESPACE::ObjectInstanceNameInUse(toWString(details));
         } else if (exceptionName == "ObjectInstanceNameNotReserved") {
            throw RTI_NAMESPACE::ObjectInstanceNameNotReserved(toWString(details));
         } else if (exceptionName == "ObjectInstanceNotKnown") {
            throw RTI_NAMESPACE::ObjectInstanceNotKnown(toWString(details));
         } else if (exceptionName == "OwnershipAcquisitionPending") {
            throw RTI_NAMESPACE::OwnershipAcquisitionPending(toWString(details));
         } else if (exceptionName == "RTIinternalError") {
            throw RTI_NAMESPACE::RTIinternalError(toWString(details));
         } else if (exceptionName == "RegionDoesNotContainSpecifiedDimension") {
            throw RTI_NAMESPACE::RegionDoesNotContainSpecifiedDimension(toWString(details));
         } else if (exceptionName == "RegionInUseForUpdateOrSubscription") {
            throw RTI_NAMESPACE::RegionInUseForUpdateOrSubscription(toWString(details));
         } else if (exceptionName == "RegionNotCreatedByThisFederate") {
            throw RTI_NAMESPACE::RegionNotCreatedByThisFederate(toWString(details));
         } else if (exceptionName == "RestoreInProgress") {
            throw RTI_NAMESPACE::RestoreInProgress(toWString(details));
         } else if (exceptionName == "RestoreNotInProgress") {
            throw RTI_NAMESPACE::RestoreNotInProgress(toWString(details));
         } else if (exceptionName == "RestoreNotRequested") {
            throw RTI_NAMESPACE::RestoreNotRequested(toWString(details));
         } else if (exceptionName == "SaveInProgress") {
            throw RTI_NAMESPACE::SaveInProgress(toWString(details));
         } else if (exceptionName == "SaveNotInProgress") {
            throw RTI_NAMESPACE::SaveNotInProgress(toWString(details));
         } else if (exceptionName == "SaveNotInitiated") {
            throw RTI_NAMESPACE::SaveNotInitiated(toWString(details));
         } else if (exceptionName == "SynchronizationPointLabelNotAnnounced") {
            throw RTI_NAMESPACE::SynchronizationPointLabelNotAnnounced(toWString(details));
         } else if (exceptionName == "TimeConstrainedAlreadyEnabled") {
            throw RTI_NAMESPACE::TimeConstrainedAlreadyEnabled(toWString(details));
         } else if (exceptionName == "TimeConstrainedIsNotEnabled") {
            throw RTI_NAMESPACE::TimeConstrainedIsNotEnabled(toWString(details));
         } else if (exceptionName == "TimeRegulationAlreadyEnabled") {
            throw RTI_NAMESPACE::TimeRegulationAlreadyEnabled(toWString(details));
         } else if (exceptionName == "TimeRegulationIsNotEnabled") {
            throw RTI_NAMESPACE::TimeRegulationIsNotEnabled(toWString(details));
         } else if (exceptionName == "UnsupportedCallbackModel") {
            throw RTI_NAMESPACE::UnsupportedCallbackModel(toWString(details));
         } else if (exceptionName == "InternalError") {
            throw RTI_NAMESPACE::InternalError(toWString(details));
#if (RTI_HLA_VERSION < 2025)
            } else if (exceptionName == "AttributeAcquisitionWasNotCanceled") {
               throw RTI_NAMESPACE::AttributeAcquisitionWasNotCanceled(toWString(details));
            } else if (exceptionName == "AttributeNotRecognized") {
               throw RTI_NAMESPACE::AttributeNotRecognized(toWString(details));
            } else if (exceptionName == "AttributeNotSubscribed") {
               throw RTI_NAMESPACE::AttributeNotSubscribed(toWString(details));
            } else if (exceptionName == "BadInitializationParameter") {
               throw RTI_NAMESPACE::BadInitializationParameter(toWString(details));
            } else if (exceptionName == "CouldNotDiscover") {
               throw RTI_NAMESPACE::CouldNotDiscover(toWString(details));
            } else if (exceptionName == "CouldNotInitiateRestore") {
               throw RTI_NAMESPACE::CouldNotInitiateRestore(toWString(details));
            } else if (exceptionName == "CouldNotOpenFDD") {
               throw RTI_NAMESPACE::CouldNotOpenFDD(toWString(details));
            } else if (exceptionName == "ErrorReadingFDD") {
               throw RTI_NAMESPACE::ErrorReadingFDD(toWString(details));
            } else if (exceptionName == "InconsistentFDD") {
               throw RTI_NAMESPACE::InconsistentFDD(toWString(details));
            } else if (exceptionName == "InteractionClassNotRecognized") {
               throw RTI_NAMESPACE::InteractionClassNotRecognized(toWString(details));
            } else if (exceptionName == "InteractionClassNotSubscribed") {
               throw RTI_NAMESPACE::InteractionClassNotSubscribed(toWString(details));
            } else if (exceptionName == "InteractionParameterNotRecognized") {
               throw RTI_NAMESPACE::InteractionParameterNotRecognized(toWString(details));
            } else if (exceptionName == "InvalidLocalSettingsDesignator") {
               throw RTI_NAMESPACE::InvalidLocalSettingsDesignator(toWString(details));
            } else if (exceptionName == "InvalidTransportationType") {
               throw RTI_NAMESPACE::InvalidTransportationType(toWString(details));
            } else if (exceptionName == "JoinedFederateIsNotInTimeAdvancingState") {
               throw RTI_NAMESPACE::JoinedFederateIsNotInTimeAdvancingState(toWString(details));
            } else if (exceptionName == "NoRequestToEnableTimeConstrainedWasPending") {
               throw RTI_NAMESPACE::NoRequestToEnableTimeConstrainedWasPending(toWString(details));
            } else if (exceptionName == "NoRequestToEnableTimeRegulationWasPending") {
               throw RTI_NAMESPACE::NoRequestToEnableTimeRegulationWasPending(toWString(details));
            } else if (exceptionName == "NoFederateWillingToAcquireAttribute") {
               throw RTI_NAMESPACE::NoFederateWillingToAcquireAttribute(toWString(details));
            } else if (exceptionName == "ObjectClassNotKnown") {
               throw RTI_NAMESPACE::ObjectClassNotKnown(toWString(details));
            } else if (exceptionName == "SpecifiedSaveLabelDoesNotExist") {
               throw RTI_NAMESPACE::SpecifiedSaveLabelDoesNotExist(toWString(details));
            } else if (exceptionName == "UnableToPerformSave") {
               throw RTI_NAMESPACE::UnableToPerformSave(toWString(details));
            } else if (exceptionName == "UnknownName") {
               throw RTI_NAMESPACE::UnknownName(toWString(details));
#else
         } else if (exceptionName == "CouldNotOpenFOM") {
            throw RTI_NAMESPACE::CouldNotOpenFOM(toWString(details));
         } else if (exceptionName == "ErrorReadingFOM") {
            throw RTI_NAMESPACE::ErrorReadingFOM(toWString(details));
         } else if (exceptionName == "InconsistentFOM") {
            throw RTI_NAMESPACE::InconsistentFOM(toWString(details));
         } else if (exceptionName == "InvalidCredentials") {
            throw RTI_NAMESPACE::InvalidCredentials(toWString(details));
         } else if (exceptionName == "InvalidMIM") {
            throw RTI_NAMESPACE::InvalidMIM(toWString(details));
         } else if (exceptionName == "InvalidFOM") {
            throw RTI_NAMESPACE::InvalidFOM(toWString(details));
         } else if (exceptionName == "InvalidObjectInstanceHandle") {
            throw RTI_NAMESPACE::InvalidObjectInstanceHandle(toWString(details));
         } else if (exceptionName == "InvalidTransportationTypeHandle") {
            throw RTI_NAMESPACE::InvalidTransportationTypeHandle(toWString(details));
         } else if (exceptionName == "ReportServiceInvocationsAreSubscribed") {
            throw RTI_NAMESPACE::ReportServiceInvocationsAreSubscribed(toWString(details));
         } else if (exceptionName == "Unauthorized") {
            throw RTI_NAMESPACE::Unauthorized(toWString(details));
#endif
         } else {
            throw RTI_NAMESPACE::RTIinternalError(
                  L"Unexpected exception '" + toWString(exceptionName) + L"': " + toWString(details));
         }
      }
   }

   void RTIambassadorClientGenericBase::putCallback(QueuedCallback callback)
   {
      std::lock_guard<std::mutex> queueLock(_queueLock);
      _callbackQueue.emplace(std::move(callback));
      _queueCondition.notifyAll();
   }

   RTIambassadorClientGenericBase::QueuedCallback RTIambassadorClientGenericBase::takeCallback()
   {
      std::unique_lock<std::mutex> queueLock(_queueLock);
      _queueCondition.wait(queueLock, [this]() { return !_callbackQueue.empty(); });
      QueuedCallback queuedCallback = std::move(_callbackQueue.front());
      _callbackQueue.pop();
      return queuedCallback;
   }

   std::unique_ptr<RTIambassadorClientGenericBase::QueuedCallback> RTIambassadorClientGenericBase::pollCallback(
         size_t & remainingCallbacks,
         FedProDuration timeout)
   {
      std::unique_lock<std::mutex> queueLock(_queueLock);
      while (_callbackQueue.empty()) {
         if (timeout == FedProDuration::zero() ||
             _queueCondition.waitFor(queueLock, timeout) == std::cv_status::timeout) {
            remainingCallbacks = _callbackQueue.size();
            return {};
         }
      }

      auto queuedCallback = std::make_unique<QueuedCallback>(std::move(_callbackQueue.front()));
      _callbackQueue.pop();

      remainingCallbacks = _callbackQueue.size();
      return queuedCallback;
   }

   void RTIambassadorClientGenericBase::callbackLoop()
   {
      while (true) {
         try {
            {
               std::unique_lock<std::mutex> lock{_callbackLock};

               // Wait for callbacks to be enabled
               _callbackCondition.wait(lock, [this]() { return _callbacksEnabled; });

               // Callbacks are enabled. Let's go!
               _callbackInProgress = true;
            }

            // Function to execute at the end of the loop, or if returning early.
            auto final_guard = make_final(
                  [this]() {
                     std::lock_guard<std::mutex> lock(_callbackLock);

                     _callbackInProgress = false;
                     _callbackCondition.notifyAll();
                  });

            QueuedCallback queuedCallback = takeCallback();
            if (queuedCallback._type == QueuedCallback::Type::PLACEHOLDER) {
               // Wakeup from disableCallbacks. Loop around.
               continue;
            }

            dispatchCallback(std::move(queuedCallback));
         } catch (const InterruptedException &) {
            SPDLOG_DEBUG("Interrupted while waiting for callback");
            return;
         }
      }
   }

   void RTIambassadorClientGenericBase::dispatchCallback(QueuedCallback queuedCallback)
   {
      auto * session = _persistentSession.get();

      try {
         dispatchHlaCallback(std::move(queuedCallback._callbackRequest));

         if (session && queuedCallback._needsResponse) {
            rti1516_2025::fedpro::CallbackResponse callbackResponse;
            callbackResponse.set_allocated_callbacksucceeded(new rti1516_2025::fedpro::CallbackSucceeded());
            session->sendHlaCallbackResponse(queuedCallback._sequenceNumber, callbackResponse.SerializeAsString());
         }
      } catch (const RTI_NAMESPACE::FederateInternalError & e) {
         const std::string eAsString = toString(e.what());
         SPDLOG_WARN("Exception in federate callback handler: {}", eAsString);
         if (queuedCallback._needsResponse) {
            rti1516_2025::fedpro::CallbackResponse response{createCallbackResponse(typeid(e).name(), e.what())};
            sendHlaCallbackErrorResponse(
                  session, queuedCallback._sequenceNumber, response.SerializeAsString());
         }
      } catch (const std::runtime_error &) {
         // TODO: do something more sensible with these exceptions
      } catch (const SessionIllegalState &) {
         // Terminated: A failed callback response is not even important enough to log in the context of session termination.
         // Not initialized: Can basically not happen.
      }
   }

   void RTIambassadorClientGenericBase::sendHlaCallbackErrorResponse(
         PersistentSession * resumingClient,
         SequenceNumber sequenceNumber,
         ByteSequence && encodedHlaResponse)
   {
      if (resumingClient) {
         try {
            resumingClient->sendHlaCallbackResponse(sequenceNumber, encodedHlaResponse);
         } catch (const SessionIllegalState &) {
            // Terminated: A callback error is not even important enough to log in the context of session termination.
            // Not initialized: Can basically not happen
         }
      }

   }

   void RTIambassadorClientGenericBase::hlaCallbackRequest(
         SequenceNumber sequenceNumber,
         const ByteSequence & hlaCallback)
   {
      try {
         auto callback = std::make_unique<CallbackRequest>();
         if (!callback->ParseFromString(hlaCallback)) {
            throw std::invalid_argument("Failed to parse the encoded CallbackRequest");
         }

         QueuedCallback queuedCallback{std::move(callback), sequenceNumber, true};
         putCallback(std::move(queuedCallback));
      } catch (const std::invalid_argument & e) {
         SPDLOG_WARN("Failed to parse callback request with sequence number {}: {}", sequenceNumber, e.what());
         // Report exception "InvalidProtocolBufferException" to match Java client behavior, even though
         // there is no such C++ exception.
         rti1516_2025::fedpro::CallbackResponse response{createCallbackResponse(
               "InvalidProtocolBufferException", L"Failed to parse the encoded CallbackRequest")};
         sendHlaCallbackErrorResponse(getSession(), sequenceNumber, response.SerializeAsString());
      }
   }

   void RTIambassadorClientGenericBase::lostConnection(std::string reason)
   {
      // TODO Anything more we should do?

      auto * connectionLost = new rti1516_2025::fedpro::ConnectionLost;
      connectionLost->set_faultdescription(std::move(reason));
      auto callback = std::make_unique<CallbackRequest>();
      callback->set_allocated_connectionlost(connectionLost);
      try {
         QueuedCallback queuedCallback{std::move(callback), 0, false};
         putCallback(std::move(queuedCallback));
      } catch (const InterruptedException & e) {
         // Give up
         SPDLOG_CRITICAL(
               "Interrupted while trying to put a callback on the callback queue when the connection was lost: {}",
               e.what());
      }
   }

   void RTIambassadorClientGenericBase::printStats() noexcept
   {
      using namespace StatsPrettyPrint;

      std::stringstream stream;
      std::stringstream stream2;
      std::string moreSessionStats;
      stream << "FedPro client stats for the last " << (static_cast<double>(_printStatsIntervalMillis.count()) / 1000.0) << " seconds.\n";
      {
         std::lock_guard<std::mutex> guard(_connectionStateLock);
         if (_persistentSession) {
            stream << "Session: " << LogUtil::formatSessionId(_persistentSession->getId()) << "\n"
            << "                                                    " << titlesPerSecond << "\n";
            _persistentSession->prettyPrintPerSecondStats(stream);
            stream2 << "                                                    " << titles << "\n";
            _persistentSession->prettyPrintStats(stream2);
         } else {
            stream << "Session: no active session\n"
            << "                                                    " << titlesPerSecond << "\n";
            stream2 << "                                                    " << titles << "\n";
         }
         stream << "\n";
      }

      MovingStats::SteadyTimePoint time = MovingStats::nowMillis();
      MovingStats::Stats syncUpdates = _hlaSyncUpdateStats->getStatsForTime(time);
      MovingStats::Stats asyncUpdates = _hlaAsyncUpdateStats->getStatsForTime(time);
      MovingStats::Stats syncInteractions = _hlaSyncSentInteraction->getStatsForTime(time);
      MovingStats::Stats asyncInteractions = _hlaAsyncSentInteraction->getStatsForTime(time);
      MovingStats::Stats syncDirInteractions = _hlaSyncSentDirectedInteraction->getStatsForTime(time);
      MovingStats::Stats asyncDirInteractions = _hlaAsyncSentDirectedInteraction->getStatsForTime(time);
      MovingStats::Stats reflectStats = getReflectStats(time);
      MovingStats::Stats receivedInteractionStats = getReceivedInteractionStats(time);
      MovingStats::Stats receivedDirInteractionStats = getReceivedDirectedInteractionStats(time);
      MovingStats::Stats callbackTimeStats = getCallbackTimeStats(time);

      stream << "HLA callbacks in queue:                             " << padStat << padStat << padStat << setFormat << _callbackQueue.size() << "\n"
             << "\n"
             << "Updates sent (sync):                                " << syncUpdates << "\n"
             << "Updates sent (async):                               " << asyncUpdates << "\n"
             << "Updates received:                                   " << reflectStats << "\n"
             << "\n"
             << "Interactions sent (sync):                           " << syncInteractions << "\n"
             << "Interactions sent (async):                          " << asyncInteractions << "\n"
             << "Interactions received:                              " << receivedInteractionStats << "\n"
             << "\n"
             << "Directed Interactions sent (sync):                  " << syncDirInteractions << "\n"
             << "Directed Interactions sent (async):                 " << asyncDirInteractions << "\n"
             << "Directed Interactions received:                     " << receivedDirInteractionStats << "\n"
             << "\n";

      stream2 << "HLA callback time (ms):                             " << callbackTimeStats << "\n";

      SPDLOG_INFO(stream.str() + stream2.str());
   }

   void RTIambassadorClientGenericBase::sessionTerminated(uint64_t sessionId)
   {
      // Terminating session to destroy when this function returns.
      // _connectionStateLock MUST NOT be locked when this happens to prevent deadlocks.
      std::unique_ptr<PersistentSession> terminatingSession;
      {
        std::lock_guard<std::mutex> guard(_connectionStateLock);
         if (!_persistentSession || (_persistentSession->getId() != sessionId) || _shutdownInProgress) {
            return;
         }
         _persistentSession.swap(terminatingSession);
         _shutdownInProgress = true;
      }

      // Wait for threads to complete before destroying PersistentSession (cf terminatingSession),
      // since dispatchCallback() need to access PersistentSession,
      cleanUpTerminatingSession();
      {
         std::lock_guard<std::mutex> guard(_connectionStateLock);
         _shutdownInProgress = false;
      }
   }

   void RTIambassadorClientGenericBase::cleanUpTerminatingSession()
   {
      stopStatsPrinting();
      if (_callbackThread) {
         stopCallbackThread();
      }
   }

} // FedPro

// NOLINTEND(hicpp-exception-baseclass)
