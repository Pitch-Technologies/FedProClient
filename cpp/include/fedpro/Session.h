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

#include <fedpro/Aliases.h>
#include <fedpro/Config.h>

#include <cstdint>
#include <iosfwd>
#include <functional>
#include <future>
#include <string>

namespace FedPro
{
   using ByteSequence = std::string;

   /**
    * @brief Interface for managing client sessions.
    */
   class FEDPRO_EXPORT Session
   {
   public:

      /**
       * @brief Enum representing the possible states a session may be in.
       *
       * The states can be divided into two
       * subcategories, stable states (NEW, RUNNING, DROPPED, TERMINATED) and transitory
       * states (STARTING, RESUMING, TERMINATING). A session may be in a stable state for
       * a longer period of time, respectively in a transitory state for a shorter. When a
       * thread transition a session to a transitory state, that thread will have
       * exclusive access to that session for the entire period of time that the session
       * remains in the state it has transitioned to.
       * <p>
       * The different states of a session.
       * <ul>
       *    <li> NEW - Session instance is created but not yet started. </li>
       *    <li> STARTING - start(HlaCallbackRequestListener) has been called, but session
       *    is not yet established. </li>
       *    <li> RUNNING - Session is running normally. </li>
       *    <li> DROPPED - Session has experienced a connection loss. Resume is possible.
       *    </li>
       *    <li> RESUMING - Session is in the process of resuming after a connection loss.
       *    </li>
       *    <li> TERMINATING - terminate() has been called, and Session is still trying to
       *    shut down gracefully. </li>
       *    <li> TERMINATED - Session is terminated and can no longer be used. </li>
       * </ul>
       */
      enum class State
      {
         NEW, STARTING, RUNNING, DROPPED, RESUMING, TERMINATING, TERMINATED
      };

      /**
       * @brief Handler for callback request, HLA_CALLBACK_REQUEST, messages. Implement
       * this and pass it to start to handle incoming HLA callbacks to the federate.
       *
       * Called on a critical internal thread that should not be blocked unnecessarily.
       * Use this to prepare callbacks for the federate application and send an
       * appropriate response.
       * <ul>
       *     <li> oldState: sequenceNumber: Sequence number of the callback request. </li>
       *     <li> hlaCallback: Encoded callback request. </li>
       * </ul>
       */
      using HlaCallbackRequestListener = std::function<void(
            int32_t sequenceNumber,
            const ByteSequence & hlaCallback)>;

      /**
       * @brief Defines a type alias for a state transition listener. The listener takes
       * the following parameters:
       * <ul>
       *     <li> oldState: The previous state that the session transitioned from. </li>
       *     <li> newState: The new state that the session transitioned to. </li>
       *     <li> reason: Information about why the state transition occurred. </li>
       * </ul>
       */
      using StateListener = std::function<void(
            State oldState,
            State newState,
            const std::string & reason)>;

      /**
       * @brief Called on a critical internal thread that should not be blocked
       * unnecessarily.
       */
      using MessageSentListener = std::function<void()>;

      virtual ~Session() = default;

      /**
       * @brief Get the session ID.
       *
       * @return The session ID.
       */
      virtual uint64_t getId() const noexcept = 0;

      /**
       * @brief Get the current state of the session.
       *
       * @return The current state of the session.
       */
      virtual State getState() const noexcept = 0;

      /**
       * @brief Get notified when the session changes states. In particular, when the
       * state changes from RUNNING to DROPPED, it is the implementer's responsibility to
       * resume the session by calling resume(), and, if the session cannot be resumed,
       * make sure it terminates instead.
       *
       * The listener will always be called on the same dedicated thread. Note that this
       * means that once the listener is called, there is no guarantee that the session is
       * still in the new state. Due to concurrency, it may already have moved to a
       * different state.
       *
       * @param onStateTransition A listener callback to be invoked when the state
       * changes.
       * @throws std::invalid_argument If onStateTransition is a nullptr.
       */
      virtual void addStateListener(StateListener onStateTransition) = 0;

      /**
       * @brief Wait for asynchronous StateListener callbacks to complete after a call to terminate().
       * @throw SessionIllegalState if state is not TERMINATED.
       */
      virtual void waitStateListeners() noexcept(false) = 0;

      /**
       * @brief A callback that is invoked every time a message is sent to the server. Can
       * be used to detect periods of inactivity and send heartbeats to keep the
       * connection alive.
       *
       * @param onMessageSent A callback invoked when a message is sent.
       * @throws std::invalid_argument If onMessageSent is a nullptr.
       */
      virtual void setMessageSentListener(MessageSentListener onMessageSent) = 0;

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
       * @brief Start the session.
       *
       * @param onHlaCallbackRequest Handler for callback requests.
       *
       * @throws SessionIllegalState If the operation is not allowed in this state.
       * @throws SessionLost If the server is unable to create the session.
       * @throws std::invalid_argument If onHlaCallbackRequest is a nullptr.
       */
      virtual void start(HlaCallbackRequestListener onHlaCallbackRequest) = 0;

      /**
       * @brief Start the session.
       *
       * @param onHlaCallbackRequest Handler for callback requests.
       * @param connectionTimeout The timeout for the initial response from the server.
       *
       * @throws SessionIllegalState If the operation is not allowed in this state.
       * @throws SessionLost If the session cannot be established for any reason.
       * @throws std::invalid_argument If onHlaCallbackRequest is a nullptr.
       */
      virtual void start(
            HlaCallbackRequestListener onHlaCallbackRequest,
            FedProDuration connectionTimeout) = 0;

      /**
       * @brief Resume session on with new socket.
       *
       * The session is terminated if the session can not be resumed.
       *
       * @throws SessionLost If the server refuses to resume the session or sends a bad
       * response message.
       * @throws SessionIllegalState If the operation is not allowed in this state.
       * @throws IOException If an IO error occurs.
       *
       * @return True if session is resumed, false otherwise.
       */
      virtual bool resume() = 0;

      /**
       * @brief Send a heartbeat, CTRL_HEARTBEAT, message.
       *
       * The returned future will be set when the corresponding heartbeat response,
       * CTRL_HEARTBEAT_RESPONSE, message is received from the server.
       *
       * @throws SessionAlreadyTerminated If the session is already terminated.
       * @throws SessionIllegalState If the operation is not allowed in this state.
       *
       * @return A future for the heartbeat response message.
       */
      virtual std::future<ByteSequence> sendHeartbeat() = 0;

      /**
       * @brief Send an HLA call message (HLA_CALL_REQUEST).
       *
       * The returned future will be set when the corresponding response message,
       * HLA_CALL_RESPONSE, is received from the server.
       *
       * @throws SessionAlreadyTerminated If the session is already terminated.
       * @throws SessionIllegalState If the operation is not allowed in this state.
       *
       * @return A future for the HLA call response message.
       */
      virtual std::future<ByteSequence> sendHlaCallRequest(const ByteSequence & encodedHlaCall) = 0;

      /**
       * @brief Send an HLA callback response message (HLA_CALLBACK_RESPONSE).
       *
       * @param responseToSequenceNumber The sequence number of the HLA callback message
       * which this message is a response to.
       *
       * @throws SessionAlreadyTerminated If the session is already terminated.
       * @throws SessionIllegalState If the operation is not allowed in this state.
       *
       * @param hlaCallbackResponse Encoded result of the HLA callback.
       */
      virtual void sendHlaCallbackResponse(
            int32_t responseToSequenceNumber,
            const ByteSequence & hlaCallbackResponse) = 0;

      /**
       * @brief Terminate the session.
       *
       * A terminated session may not be restarted.
       * <p>
       * If transport layer communication is available, a CTRL_TERMINATE_SESSION message
       * is sent; the corresponding response, CTRL_SESSION_TERMINATED, is then awaited.
       * Typically, all requests that are outstanding when this method is called will be
       * completed before termination. If there is a timeout or interruption, some
       * outstanding requests may be completed exceptionally. When this method returns,
       * the session is always considered terminated. Any requests sent after this method
       * is called will be completed exceptionally once the session terminates.
       *
       * @throws SessionAlreadyTerminated If the session is already terminated.
       * @throws SessionIllegalState If the operation is not allowed in this state.
       * @throws SessionLost If this method is interrupted for any reason, including
       * timeout, while waiting for the server to respond. The session is still considered
       * terminated when this happens.
       */
      virtual void terminate() = 0;

      /**
       * @brief Terminate the session.
       *
       * A terminated session may not be restarted.
       * <p>
       * If transport layer communication is available, a CTRL_TERMINATE_SESSION message
       * is sent; the corresponding response, CTRL_SESSION_TERMINATED, is then awaited.
       * Typically, all requests that are outstanding when this method is called will be
       * completed before termination. If there is a timeout or interruption, some
       * outstanding requests may be completed exceptionally. When this method returns,
       * the session is always considered terminated. Any requests sent after this method
       * is called will be completed exceptionally once the session terminates.
       *
       * @param responseTimeout The maximum time to wait for a response.
       *
       * @throws SessionAlreadyTerminated If the session is already terminated.
       * @throws SessionIllegalState If the operation is not allowed in this state.
       * @throws SessionLost If this method is interrupted for any reason, including
       * timeout, while waiting for the server to respond. The session is still considered
       * terminated when this happens.
       */
      virtual void terminate(FedProDuration responseTimeout) = 0;

   };

} // FedPro
