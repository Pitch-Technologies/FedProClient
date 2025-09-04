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

#include <fedpro/PersistentSession.h>
#include <fedpro/Properties.h>
#include <fedpro/ResumeStrategy.h>
#include <fedpro/Transport.h>
#include "SessionImpl.h"

namespace FedPro
{
   // For testing purpose, export function but do not expose it in public headers.
   FEDPRO_EXPORT Session * getSession(PersistentSession & persistentSession);

   class PersistentSessionImpl : public PersistentSession
   {
   public:

      /**
       * @param transportProtocol      An unique pointer to the underlying transport
       *                               layer.
       * @param connectionLostListener The listener to be called when the session is
       *                               terminally lost due to connection issues.
       * @param sessionTerminatedListener The listener to be called when the session terminates
       *                                  for any reason.
       * @param resumeStrategy         An unique pointer to the ResumeStrategy that will
       *                               control when, how often and for how long
       *                               reconnection attempts will be made.
       * @throws std::invalid_argument If resumeStrategy is a nullptr, or if a value for
       *                               an environment variable is specified incorrectly.
       */
      explicit PersistentSessionImpl(
            std::unique_ptr<Transport> transportProtocol,
            ConnectionLostListener onConnectionLost,
            SessionTerminatedListener sessionTerminatedListener,
            const Properties & settings,
            std::unique_ptr<ResumeStrategy> resumeStrategy);

      ~PersistentSessionImpl() override;

      void waitListeners() noexcept(false) override;

      FedProDuration getHeartbeatInterval() const noexcept override;

      uint64_t getId() const noexcept override;

      void prettyPrintPerSecondStats(std::ostream & stream) override;

      void prettyPrintStats(std::ostream & stream) override;

      void start(Session::HlaCallbackRequestListener onHlaCallbackRequest) override;

      void start(
            Session::HlaCallbackRequestListener onHlaCallbackRequest,
            FedProDuration connectionTimeout) override;

      std::future<ByteSequence> sendHlaCallRequest(const ByteSequence & encodedHlaCall) override;

      void sendHlaCallbackResponse(
            int32_t responseToSequenceNumber,
            const ByteSequence & hlaCallbackResponse) override;

      void terminate() override;

      void terminate(FedProDuration responseTimeout) override;

      Session * getSession();

   private:
      ConnectionLostListener _onConnectionLost;

      SessionTerminatedListener _sessionTerminatedListener;

      // Session layer settings
      FedProDuration _heartbeatInterval;

      std::unique_ptr<ResumeStrategy> _resumeStrategy;

      std::unique_ptr<FedPro::TimeoutTimer> _sendHeartbeatTimer;

      // Session must be destructed before other data members
      SessionImpl _session;

      void initializeHeartBeatTimer() noexcept;

      void startHeartBeatTimer() noexcept;

      void heartbeat() noexcept;

      void onSessionStateChange(
            Session::State oldState,
            Session::State newState,
            const std::string & reason) noexcept;

      void runResumeStrategy() noexcept;

      Properties getSettings() const noexcept;

      std::string logPrefix() noexcept;

   };

} // FedPro
