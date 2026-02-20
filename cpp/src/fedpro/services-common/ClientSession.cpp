/***********************************************************************
  Copyright (C) 2026 Pitch Technologies AB

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

#include "ClientSession.h"
#include "FederateAmbassadorDispatcher.h"
#include <utility/final.h>
#include <utility/FixedRateExecutor.h>
#include <utility/StringUtil.h>
#include <protobuf/generated/FederateAmbassador.pb.h>

#include <FedPro.h>

#include <spdlog/spdlog.h>

namespace FedPro
{
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

   thread_local bool ClientSession::_inCallbackThread{false};

   bool ClientSession::isInCallbackThread()
   {
      return _inCallbackThread;
   }

   ClientSession::ClientSession(
         std::unique_ptr<Transport> clientTransport,
         PersistentSession::SessionTerminatedListener sessionTerminatedListener,
         const Properties & settings,
         std::unique_ptr<ResumeStrategy> resumeStrategy,
         bool createCallbackThread,
         std::shared_ptr<FederateAmbassadorDispatcher> federateAmbassadorDispatcher)
         : _createCallbackThread{createCallbackThread},
           _federateAmbassadorDispatcher{std::move(federateAmbassadorDispatcher)}
   {
      _persistentSession = FedPro::createPersistentSession(
            std::move(clientTransport),
            [this](std::string reason) { lostConnection(std::move(reason)); },
            std::move(sessionTerminatedListener),
            settings,
            std::move(resumeStrategy));
   }

   ClientSession::~ClientSession() = default;

   void ClientSession::startPersistentSession()
   {
      _persistentSession->start(
            [this](
                  SequenceNumber seq,
                  const ByteSequence & hlaCallback) { hlaCallbackRequest(seq, hlaCallback); });
   }

   void ClientSession::startCallbackThread()
   {
      if (_callbackThread && _callbackThread->joinable()) {
         throw std::runtime_error("Callback thread is running or pending join");
      }

      if (_createCallbackThread) {
         _callbackThread = std::make_unique<std::thread>(&ClientSession::callbackLoop, this);
      }
   }

   void ClientSession::stopCallbackThread()
   {
      try {
         // Interrupts the thread waiting on the callback queue.
         // Should we drop all queued callbacks?
         _callbackQueue.interrupt();

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

      _callbackThread = nullptr;
   }

   void ClientSession::dispatchCallback(QueuedCallback queuedCallback)
   {
      if (!_federateAmbassadorDispatcher) {
         throw RTI_NAMESPACE::NotConnected(L"Cannot dispatch callback when FederateAmbassador is not connected");
      }
      try {
         _federateAmbassadorDispatcher->dispatchCallback(std::move(queuedCallback._callbackRequest));

         if (queuedCallback._needsResponse) {
            rti1516_2025::fedpro::CallbackResponse callbackResponse;
            callbackResponse.set_allocated_callbacksucceeded(new rti1516_2025::fedpro::CallbackSucceeded());
            _persistentSession->sendHlaCallbackResponse(queuedCallback._sequenceNumber, callbackResponse.SerializeAsString());
         }
      } catch (const RTI_NAMESPACE::FederateInternalError & e) {
         const std::string eAsString = toString(e.what());
         SPDLOG_WARN("Exception in federate callback handler: {}", eAsString);
         if (queuedCallback._needsResponse) {
            rti1516_2025::fedpro::CallbackResponse response{createCallbackResponse(typeid(e).name(), e.what())};
            sendHlaCallbackErrorResponse(queuedCallback._sequenceNumber, response.SerializeAsString());
         }
      } catch (const std::runtime_error &) {
         // TODO: do something more sensible with these exceptions
      } catch (const SessionIllegalState &) {
         // Terminated: A failed callback response is not even important enough to log in the context of session termination.
         // Not initialized: Can basically not happen.
      }
   }

   void ClientSession::callbackLoop()
   {
      _inCallbackThread = true;
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

            QueuedCallback queuedCallback = _callbackQueue.take();
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

   void ClientSession::lostConnection(std::string reason)
   {
      // TODO Anything more we should do?

      auto * connectionLost = new rti1516_2025::fedpro::ConnectionLost;
      connectionLost->set_faultdescription(std::move(reason));
      auto callback = std::make_unique<::rti1516_2025::fedpro::CallbackRequest>();
      callback->set_allocated_connectionlost(connectionLost);
      try {
         QueuedCallback queuedCallback{std::move(callback), 0, false};
         _callbackQueue.push(std::move(queuedCallback));
      } catch (const InterruptedException & e) {
         // Give up
         SPDLOG_CRITICAL(
               "Interrupted while trying to put a callback on the callback queue when the connection was lost: {}",
               e.what());
      }
   }

   void ClientSession::hlaCallbackRequest(
         SequenceNumber sequenceNumber,
         const ByteSequence & hlaCallback)
   {
      try {
         auto callback = std::make_unique<::rti1516_2025::fedpro::CallbackRequest>();
         if (!callback->ParseFromString(hlaCallback)) {
            throw std::invalid_argument("Failed to parse the encoded CallbackRequest");
         }

         QueuedCallback queuedCallback{std::move(callback), sequenceNumber, true};
         _callbackQueue.push(std::move(queuedCallback));
      } catch (const std::invalid_argument & e) {
         SPDLOG_WARN("Failed to parse callback request with sequence number {}: {}", sequenceNumber, e.what());
         // Report exception "InvalidProtocolBufferException" to match Java client behavior, even though
         // there is no such C++ exception.
         rti1516_2025::fedpro::CallbackResponse response{createCallbackResponse(
               "InvalidProtocolBufferException", L"Failed to parse the encoded CallbackRequest")};
         sendHlaCallbackErrorResponse(sequenceNumber, response.SerializeAsString());
      }
   }

   void ClientSession::sendHlaCallbackErrorResponse(
         SequenceNumber sequenceNumber,
         ByteSequence && encodedHlaResponse)
   {
      try {
         _persistentSession->sendHlaCallbackResponse(sequenceNumber, encodedHlaResponse);
      } catch (const SessionIllegalState &) {
         // Terminated: A callback error is not even important enough to log in the context of session termination.
         // Not initialized: Can basically not happen
      }
   }

   void ClientSession::startStatsPrinting(
         Runnable printStatsRunnable,
         FedProDuration printStatsInterval)
   {
      stopStatsPrinting();
      auto printStatsExecutor =
            makeFixedRateExecutor(std::move(printStatsRunnable), printStatsInterval, printStatsInterval);
      _printStatsInterrupt = printStatsExecutor.getState();
      _statsPrintingThread = std::thread(printStatsExecutor);
   }

   void ClientSession::stopStatsPrinting()
   {
      if (_printStatsInterrupt) {
         _printStatsInterrupt->interrupt();
         _printStatsInterrupt.reset();
         _statsPrintingThread.join();
      }
   }
} // FedPro

// NOLINTEND(hicpp-exception-baseclass)
