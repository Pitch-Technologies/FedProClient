/*
 *  Copyright (C) 2022 Pitch Technologies AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.pitch.oss.fedpro.client;

import se.pitch.oss.fedpro.common.exceptions.SessionIllegalState;
import se.pitch.oss.fedpro.common.exceptions.SessionAlreadyTerminated;
import se.pitch.oss.fedpro.common.exceptions.SessionLost;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public interface Session {

   /**
    * The different states of a session.
    * <p>
    * {@link #NEW} - Newly created Session instance.
    * {@link #STARTING} - start() has been called, but session is not yet established.
    * {@link #RUNNING} - Session is running normally.
    * {@link #DROPPED} - Session has experienced a connection loss. Resume is possible.
    * {@link #RESUMING} - Session is in the process of resuming after a connection loss.
    * {@link #TERMINATED} - Session is terminated and can no longer be used.
    */
   enum State {
      NEW,
      STARTING,
      RUNNING,
      DROPPED,
      RESUMING,
      TERMINATED
   }

   /**
    * Get the session ID
    *
    * @return The session ID
    */
   long getId();

   /**
    * Get the current state of the session
    *
    * @return The current state of the session
    */
   State getState();

   void setSessionTimeout(
         long sessionTimeout,
         TimeUnit sessionTimeoutUnit)
   throws SessionIllegalState;

   /**
    * Get notified when the session changes states. In particular, when the state changes from RUNNING to DROPPED, it
    * is the implementer's responsibility to resume the session by calling resume(), and, if the session cannot be
    * resumed, make sure it terminates instead.
    * <p>
    * The listener will always be called on the same dedicated thread. Note that this means that once the listener is
    * called, there is no guarantee that the session is still in the newState; due to concurrency, it may already have
    * moved to a different state.
    *
    * @param listener a listener callback to be invoked when the session state changes
    */
   void addStateListener(StateListener listener);

   /**
    * A callback that is invoked every time a message is sent to the server. Can be used to detect periods of inactivity
    * and send heartbeats to keep the connection alive.
    *
    * @param messageSentListener a callback invoked when a message is sent
    */
   void setMessageSentListener(MessageSentListener messageSentListener);

   /**
    * Start the session.
    *
    * @param hlaCallbackRequestListener handler for callback requests
    * @throws SessionIllegalState if the operation is not allowed in this session state
    * @throws SessionLost         if server is unable to create session
    */
   void start(HlaCallbackRequestListener hlaCallbackRequestListener)
   throws SessionLost, SessionIllegalState;

   /**
    * Start the session.
    *
    * @param hlaCallbackRequestListener handler for callback requests
    * @param startSessionTimeout        the timeout for the initial response from the server
    * @param startSessionTimeoutUnit    the time unit for startSessionTimeout
    * @throws SessionIllegalState  if the operation is not allowed in this session state
    * @throws SessionLost          if the session cannot be established for any reason
    * @throws NullPointerException if hlaCallbackRequestListener is null
    */
   void start(
         HlaCallbackRequestListener hlaCallbackRequestListener,
         long startSessionTimeout,
         TimeUnit startSessionTimeoutUnit)
   throws SessionLost, SessionIllegalState;

   /**
    * Resume session on a new transport connection.
    * <p>
    * The session is terminated if the session can not be resumed.
    *
    * @return true if session is resumed, false otherwise.
    * @throws SessionLost         if the server refuses to resume the session or sends a bad response message
    * @throws SessionIllegalState if the operation is not allowed in this session state
    * @throws IOException         if an IO error occurs
    */
   boolean resume()
   throws SessionLost, IOException, SessionIllegalState;

   /**
    * Send a heartbeat, CTRL_HEARTBEAT, message.
    * <p>
    * The returned completable future will be completed when the
    * corresponding heartbeat response, CTRL_HEARTBEAT_RESPONSE,
    * message is received from the server.
    *
    * @return a completable future for the heartbeat response message
    */
   CompletableFuture<byte[]> sendHeartbeat()
   throws SessionIllegalState;

   /**
    * Send an HLA call message (HLA_CALL_REQUEST).
    * <p>
    * The returned completable future will be completed when the
    * corresponding response message, HLA_CALL_RESPONSE,
    * is received from the server.
    *
    * @return a completable future for the HLA call response message
    */
   CompletableFuture<byte[]> sendHlaCallRequest(byte[] encodedHlaCall)
   throws SessionIllegalState;

   /**
    * Send an HLA callback response message (HLA_CALLBACK_RESPONSE).
    *
    * @param responseToSequenceNumber sequence number of the HLA callback message this is a response to
    * @param hlaCallbackResponse      encoded result of the HLA callback
    */
   void sendHlaCallbackResponse(
         int responseToSequenceNumber,
         byte[] hlaCallbackResponse)
   throws SessionIllegalState;

   /**
    * Terminate the session.
    * <p>
    * A terminated session may not be restarted.
    * <p>
    * If transport layer communication is available, a CTRL_TERMINATE_SESSION message is sent; the corresponding
    * response, CTRL_SESSION_TERMINATED, is then awaited. Typically, all requests that are outstanding when this method
    * is called will be completed before termination. If there is a timeout or interruption, some outstanding requests
    * may be completed exceptionally. When this method returns, the session is always considered terminated. Any
    * requests sent after this method is called will be completed exceptionally once the session terminates.
    *
    * @throws SessionAlreadyTerminated if the session is already terminated
    * @throws SessionIllegalState      if the operation is not allowed in this session state
    * @throws SessionLost              if this method is interrupted for any reason, including timeout, while waiting for the
    *                                  server to respond. The session is still considered terminated when this happens.
    */
   void terminate()
   throws SessionLost, SessionIllegalState;

   /**
    * Terminate the session.
    * <p>
    * A terminated session may not be restarted.
    * <p>
    * If transport layer communication is available, a CTRL_TERMINATE_SESSION message is sent; the corresponding
    * response, CTRL_SESSION_TERMINATED, is then awaited. Typically, all requests that are outstanding when this method
    * is called will be completed before termination. If there is a timout or interruption, some outstanding requests
    * may be completed exceptionally. When this method returns, the session is always considered terminated. Any
    * requests sent after this method is called will be completed exceptionally once the session terminates.
    *
    * @param responseTimeout     the maximum time to wait for a response
    * @param responseTimeoutUnit the time unit of the responseTimeout argument
    * @throws SessionAlreadyTerminated if the session is already terminated
    * @throws SessionIllegalState      if the operation is not allowed in this session state
    * @throws SessionLost              if this method is interrupted for any reason, including timeout, while waiting for the
    *                                  server to respond. The session is still considered terminated when this happens.
    */
   void terminate(int responseTimeout, TimeUnit responseTimeoutUnit)
   throws SessionLost, SessionIllegalState;

   /**
    * Handler for callback request, HLA_CALLBACK_REQUEST, messages.
    * Implement this and pass it to start to handle incoming HLA callbacks to the federate.
    */
   @FunctionalInterface
   interface HlaCallbackRequestListener {
      /**
       * Handle a received callback request.
       * <p>
       * Called on a critical internal thread that should not be blocked unnecessarily. Use this to prepare callbacks for
       * the federate application and send an appropriate response.
       *
       * @param sequenceNumber sequence number of the callback request
       * @param hlaCallback    encoded callback request
       */
      void onHlaCallbackRequest(
            int sequenceNumber,
            byte[] hlaCallback);
   }

   @FunctionalInterface
   interface StateListener {
      void onStateTransition(
            State oldState,
            State newState,
            String reason);
   }

   @FunctionalInterface
   interface MessageSentListener {
      /**
       * Called on a critical internal thread that should not be blocked unnecessarily.
       */
      void onMessageSent();
   }
}
