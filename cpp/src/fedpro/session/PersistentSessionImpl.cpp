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

#include "PersistentSessionImpl.h"

#include <fedpro/FedProExceptions.h>
#include "TimeoutTimer.h"

#include "spdlog/spdlog.h"

#include <functional>
#include <thread>

namespace FedPro
{
   FEDPRO_EXPORT Session * getSession(PersistentSession & persistentSession)
   {
      auto * persistentSessionImpl = dynamic_cast<PersistentSessionImpl *>(&persistentSession);
      if (persistentSessionImpl) {
         return persistentSessionImpl->getSession();
      } else {
         throw std::invalid_argument("Unexpected Session class type");
      }
   }

   PersistentSessionImpl::PersistentSessionImpl(
         std::unique_ptr<Transport> transportProtocol,
         ConnectionLostListener onConnectionLost,
         const Properties & settings,
         std::unique_ptr<ResumeStrategy> resumeStrategy)
         : _onConnectionLost{std::move(onConnectionLost)},
           _heartbeatInterval{settings.getDuration(SETTING_NAME_HEARTBEAT_INTERVAL, DEFAULT_HEARTBEAT_INTERVAL_MILLIS)},
           _resumeStrategy{std::move(resumeStrategy)},
           _session{std::move(transportProtocol), settings}
{
      if (!_resumeStrategy) {
         throw std::invalid_argument(logPrefix() + ": The passed resume strategy is null.");
      }

      if (!_onConnectionLost) {
         _onConnectionLost = [](const std::string & reason) { /* No-op */ };
      }
      _session.addStateListener(
            [this](
                  Session::State oldState,
                  Session::State newState,
                  const std::string & reason) {
               this->onSessionStateChange(oldState, newState, reason);
            });

      SPDLOG_INFO("Federate Protocol client session layer settings used:\n" + getSettings().toPrettyString());
   }

   PersistentSessionImpl::~PersistentSessionImpl()
   {
      if (_sendHeartbeatTimer) {
         _sendHeartbeatTimer->cancel();
      }
   }

   FedProDuration PersistentSessionImpl::getHeartbeatInterval() const noexcept
   {
      return _heartbeatInterval;
   }

   uint64_t PersistentSessionImpl::getId() const noexcept
   {
      return _session.getId();
   }

   void PersistentSessionImpl::start(Session::HlaCallbackRequestListener onHlaCallbackRequest)
   {
      initializeHeartBeatTimer();
      _session.start(onHlaCallbackRequest);
      startHeartBeatTimer();
   }

   void PersistentSessionImpl::start(
         Session::HlaCallbackRequestListener onHlaCallbackRequest,
         FedProDuration connectionTimeout)
   {
      initializeHeartBeatTimer();
      _session.start(onHlaCallbackRequest, connectionTimeout);
      startHeartBeatTimer();
   }

   std::future<ByteSequence> PersistentSessionImpl::sendHlaCallRequest(
         const ByteSequence & encodedHlaCall)
   {
      return _session.sendHlaCallRequest(encodedHlaCall);
   }

   void PersistentSessionImpl::sendHlaCallbackResponse(
         int32_t responseToSequenceNumber,
         const ByteSequence & hlaCallbackResponse)
   {
      _session.sendHlaCallbackResponse(responseToSequenceNumber, hlaCallbackResponse);
   }

   void PersistentSessionImpl::terminate()
   {
      if (_sendHeartbeatTimer) {
         _sendHeartbeatTimer->cancel();
      }
      _session.terminate();
   }

   void PersistentSessionImpl::terminate(FedProDuration responseTimeout)
   {
      if (_sendHeartbeatTimer) {
         _sendHeartbeatTimer->cancel();
      }
      _session.terminate(responseTimeout);
   }

   // Private methods

   void PersistentSessionImpl::initializeHeartBeatTimer() noexcept
   {
      // Avoid replacing the Heartbeat Timer if start() is called multiple times.
      if (!_sendHeartbeatTimer) {
         _sendHeartbeatTimer = TimeoutTimer::createEagerTimeoutTimer(_heartbeatInterval);
         _session.setMessageSentListener(
               [this]() {
                  _sendHeartbeatTimer->extend();
               });
      }
   }

   void PersistentSessionImpl::startHeartBeatTimer() noexcept
   {
      _sendHeartbeatTimer->start(
            [this]() {
               heartbeat();
            });
   }

   void PersistentSessionImpl::heartbeat() noexcept
   {
      _sendHeartbeatTimer->resume();
      try {
         _session.sendHeartbeat();
      } catch (const SessionIllegalState & e) {
         // session terminated or not initialized
      } catch (const std::runtime_error & e) {
         SPDLOG_CRITICAL("{}: Unexpected exception in session heartbeat timer. {}", logPrefix(), e.what());
      }
   }

   void PersistentSessionImpl::onSessionStateChange(
         Session::State oldState,
         Session::State newState,
         const std::string & reason) noexcept
   {
      // Todo why would we want to pass reason here, if we not use it?
      if (oldState == Session::State::RUNNING && newState == Session::State::DROPPED) {
         runResumeStrategy();
      }
   }

   void PersistentSessionImpl::runResumeStrategy() noexcept
   {
      _sendHeartbeatTimer->pause();
      const std::chrono::steady_clock::time_point disconnectedAtTime = std::chrono::steady_clock::now();
      FedProDuration timeSinceDisconnect = FedProDuration::zero();
      std::exception_ptr firstResumeFailedException = nullptr;

      while (_resumeStrategy->shouldRetry(timeSinceDisconnect)) { // checks if argument still is less than limit
         try {
            _session.resume();
            _sendHeartbeatTimer->resume();
            SPDLOG_INFO("{}: Resumed session.", logPrefix());
            return;
         } catch (const SessionLost & e) {
            SPDLOG_ERROR("{}: Failed to resume: {}", logPrefix(), e.what());
            _onConnectionLost("Failed to resume Federate Protocol session: " + std::string(e.what()));
            return;
         } catch (const SessionIllegalState & e) {
            SPDLOG_ERROR("{}: Failed to resume: {}", logPrefix(), e.what());
            _onConnectionLost("Failed to resume Federate Protocol session: " + std::string(e.what()));
            return;
         } catch (const std::exception & e) {
            if (!firstResumeFailedException) {
               firstResumeFailedException = std::make_exception_ptr(e);
            }
            SPDLOG_WARN("{}: Failed to resume: {}", logPrefix(), e.what());

            // Try again, given not in last for-loop iteration.
         }

         timeSinceDisconnect = std::chrono::duration_cast<FedProDuration>(
               std::chrono::steady_clock::now() - disconnectedAtTime);

         std::this_thread::sleep_for(_resumeStrategy->getRetryDelay(timeSinceDisconnect));
      }

      auto seconds = std::chrono::duration_cast<std::chrono::seconds>(
            _resumeStrategy->getRetryDelay(timeSinceDisconnect)).count();
      SPDLOG_ERROR("{}: Failed to resume session after {} seconds. Terminating session.", logPrefix(), seconds);

      try {
         _session.terminate();
      } catch (const SessionIllegalState & e) {
         SPDLOG_DEBUG("{}: Exception when terminating: {}", logPrefix(), e.what());
      } catch (const SessionLost & e) {
         SPDLOG_DEBUG("{}: Exception when terminating: {}", logPrefix(), e.what());
      }

      std::chrono::seconds
            retryLimitSeconds = std::chrono::duration_cast<std::chrono::seconds>(_resumeStrategy->getRetryLimit());
      std::string retryLimit = std::to_string(retryLimitSeconds.count());

      std::string firstResumeFailedMessage = "No reason found";
      if (firstResumeFailedException) {
         try {
            std::rethrow_exception(firstResumeFailedException);
         } catch (const std::exception & e) {
            firstResumeFailedMessage = e.what();
         }
      }

      _onConnectionLost(
            "Failed to resume Federate Protocol session after trying for " + retryLimit +
            " seconds. Reason for first failure: " + firstResumeFailedMessage);
   }

   Properties PersistentSessionImpl::getSettings() const noexcept
   {
      Properties settings = _session.getSettings();
      settings.setDuration(SETTING_NAME_HEARTBEAT_INTERVAL, _heartbeatInterval);
      settings.setDuration(SETTING_NAME_RECONNECT_LIMIT, _resumeStrategy->getRetryLimit());
      return settings;
   }

   std::string PersistentSessionImpl::logPrefix() noexcept
   {
      return _session.logPrefix();
   }

   Session * PersistentSessionImpl::getSession()
   {
      return &_session;
   }

} // FedPro
