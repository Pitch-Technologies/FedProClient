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

#include <fedpro/Config.h>
#include <fedpro/Properties.h>
#include <fedpro/Session.h>

#include <chrono>
#include <cstdint>
#include <iosfwd>
#include <functional>
#include <memory>
#include <string>

namespace FedPro
{
   /**
    * @brief A representation of a client session which automatically sends heartbeats at
    * a set interval when necessary, and reconnects the session if the connection is
    * broken.
    *
    * Reconnection attempts will happen according to a supplied custom strategy, or, by
    * default, every 5 seconds up to a minute. If the connection cannot be reestablished,
    * a ConnectionLostListener will be invoked.
    */
   class FEDPRO_EXPORT PersistentSession
   {
   public:

      /**
       * @brief Invoked when the session fails to reconnect to the server.
       */
      using ConnectionLostListener = std::function<void(const std::string & reason)>;

      /**
       * @brief Invoked when the session is terminated.
       */
      using SessionTerminatedListener = std::function<void(uint64_t sessionId)>;

      virtual ~PersistentSession() = default;

      /**
       * @brief Wait for asynchronous listener callbacks to complete after a call to terminate().
       * @throw SessionIllegalState if state is not TERMINATED.
       */
      virtual void waitListeners() noexcept(false) = 0;

      /**
       * @brief Get the interval at which to send heartbeats
       *
       * @return The interval at which to send heartbeats, or zero if not sending heartbeats.
       */
      virtual FedProDuration getHeartbeatInterval() const noexcept = 0;

      /**
       * @brief Get the session ID.
       *
       * @return The session ID.
       */
      virtual uint64_t getId() const noexcept = 0;

      /**
       * @brief Pretty-print session statistics that are expressed in quantity per second,
       * such as call count per second.
       */
      virtual void prettyPrintPerSecondStats(std::ostream & stream) = 0;

      /**
       * @brief Pretty-print session statistics that are expressed in others quantities, such as call time.
       */
      virtual void prettyPrintStats(std::ostream & stream) = 0;

      /**
       * @brief Start a new Federate Protocol Persistent Session.
       *
       * @param onHlaCallbackRequest The handler to be invoked for every callback that the
       * RTI needs to deliver to the federate.
       *
       * @throws SessionIllegalState If the operation is not allowed in this state.
       * @throws SessionLost If the session is in a faulty state or cannot be established
       * for any reason.
       * @throws std::invalid_argument If onHlaCallbackRequest is a nullptr.
       */
      virtual void start(Session::HlaCallbackRequestListener onHlaCallbackRequest) = 0;

      /**
       * @brief Start a new Federate Protocol Persistent Session.
       *
       * @param onHlaCallbackRequest The handler to be invoked for every callback that the
       * RTI needs to deliver to the federate.
       * @param connectionTimeout Timeout for the server response to the initial
       * connection.
       *
       * @throws SessionIllegalState If the operation is not allowed in this state.
       * @throws SessionLost If the session is in a faulty state or cannot be established
       * for any reason.
       * @throws std::invalid_argument If onHlaCallbackRequest is a nullptr.
       */
      virtual void start(
            Session::HlaCallbackRequestListener onHlaCallbackRequest,
            FedProDuration connectionTimeout) = 0;

      /**
       * @brief Send an HLA call to the server. If the connection to the server is
       * currently broken, or breaks before
       * the call can be sent, the call will be sent after the session has been
       * reestablished.
       *
       * @param encodedHlaCall The encoded HLA call.
       *
       * @return A future that will be completed with the response to the HLA call, or,
       * if the connection is terminated before the call can be completed, with an
       * exception.
       *
       * @throws SessionAlreadyTerminated If the session is already terminated.
       * @throws SessionIllegalState If the operation is not allowed in this state.
       */
      virtual std::future<ByteSequence> sendHlaCallRequest(const ByteSequence & encodedHlaCall) = 0;

      /**
       * @brief Send a response to an HLA callback received from the server.
       *
       * @param responseToSequenceNumber The sequence number of the callback request
       * message this message is in response
       * to.
       * @param hlaCallbackResponse The encoded callback response.
       *
       * @throws SessionAlreadyTerminated If the session is already terminated.
       * @throws SessionIllegalState If the operation is not allowed in this state.
       */
      virtual void sendHlaCallbackResponse(
            int32_t responseToSequenceNumber,
            const ByteSequence & hlaCallbackResponse) = 0;

      /**
       * @brief Terminate this session.
       *
       * @throws SessionAlreadyTerminated If the session is already terminated.
       * @throws SessionIllegalState If the operation is not allowed in this state.
       * @throws SessionLost If this method is interrupted for any reason, including
       * timeout, while waiting for the
       * server to respond. The session is still considered terminated when this happens.
       */
      virtual void terminate() = 0;

      /**
       * @brief Terminate this session.
       *
       * @param responseTimeout The maximum time to wait for a response to the termination
       * request.
       *
       * @throws SessionAlreadyTerminated If the session is already terminated.
       * @throws SessionIllegalState If the operation is not allowed in this state.
       * @throws SessionLost If this method is interrupted for any reason, including
       * timeout, while waiting for the
       * server to respond. The session is still considered terminated when this happens.
       */
      virtual void terminate(FedProDuration responseTimeout) = 0;

   };

} // FedPro
