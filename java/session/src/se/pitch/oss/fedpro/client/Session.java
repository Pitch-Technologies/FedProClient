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

public interface Session extends AutoCloseable {

   /**
    * Enum representing the possible states a session may be in.
    * <p>
    * The states can be divided into two subcategories, stable states ({@link #NEW},
    * {@link #RUNNING}, {@link #DROPPED}, {@link #TERMINATED}) and transitory states
    * ({@link #STARTING}, {@link #RESUMING}, {@link #TERMINATING}). A session may be in a
    * stable state for a longer period of time, respectively, in a transitory state for a
    * shorter. When a thread transitions a session to a transitory state, that thread will
    * have exclusive access to that session for the entire period of time that the session
    * remains in the state it has transitioned to.
    * <p>
    * The different states of a session.
    * <ul>
    *    <li> {@link #NEW} - Session instance is created but not yet started. </li>
    *    <li> {@link #STARTING} - {@link #start(HlaCallbackRequestListener)} has been
    *    called, but session is not yet established. </li>
    *    <li> {@link #RUNNING} - Session is running normally. </li>
    *    <li> {@link #DROPPED} - Session has experienced a connection loss. Resume is
    *    possible. </li>
    *    <li> {@link #RESUMING} - Session is in the process of resuming after a connection
    *    loss. </li>
    *    <li> {@link #TERMINATING} - {@link #terminate()} has been called, and Session is
    *    still trying to shut down
    *    gracefully. </li>
    *    <li> {@link #TERMINATED} - Session is terminated and can no longer be used. </li>
    * </ul>
    */
   enum State {
      NEW,
      STARTING,
      RUNNING,
      DROPPED,
      RESUMING,
      TERMINATING,
      TERMINATED
   }

   @FunctionalInterface
   interface HlaCallbackRequestListener {
      /**
       * Handler for callback request, HLA_CALLBACK_REQUEST, messages. Implement this and
       * pass it to start to handle incoming HLA callbacks to the federate.
       * <p>
       * Called on a critical internal thread that should not be blocked unnecessarily.
       * Use this to prepare callbacks for the federate application and send an
       * appropriate response.
       *
       * @param sequenceNumber Sequence number of the callback request.
       * @param hlaCallback    Encoded callback request.
       */
      void onHlaCallbackRequest(
            int sequenceNumber,
            byte[] hlaCallback);
   }

   @FunctionalInterface
   interface StateListener {
      /**
       * Defines a type alias for a state transition listener. The listener takes the
       * following parameters:
       * <ul>
       *     <li> oldState: The previous state that the session transitioned from. </li>
       *     <li> newState: The new state that the session transitioned to. </li>
       *     <li> reason: Information about why the state transition occurred. </li>
       * </ul>
       */
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

   /**
    * Get the session ID.
    *
    * @return The session ID.
    */
   long getId();

   /**
    * Get the current state of the session.
    *
    * @return The current state of the session.
    */
   State getState();

   /**
    * Get notified when the session changes states. In particular, when the state changes
    * from RUNNING to DROPPED, it is the implementer's responsibility to resume the
    * session by calling {@link #resume()}, and, if the session cannot be resumed, make
    * sure it terminates instead.
    * <p>
    * The listener will always be called on the same dedicated thread. Note that this
    * means that once the listener is called, there is no guarantee that the session is
    * still in the new state, due to concurrency, it may already have moved to a
    * different state.
    *
    * @param listener A listener callback to be invoked when the state changes.
    * @throws NullPointerException If listener is null.
    */
   void addStateListener(StateListener listener);

   /**
    * A callback that is invoked every time a message is sent to the server. Can be used
    * to detect periods of inactivity and send heartbeats to keep the connection alive.
    * May only be set before invoking {@link #start(HlaCallbackRequestListener)}.
    *
    * @param messageSentListener A callback invoked when a message is sent.
    * @throws NullPointerException If messageSentListener is null.
    */
   void setMessageSentListener(MessageSentListener messageSentListener);

   /**
    * Start the session.
    *
    * @param hlaCallbackRequestListener Handler for callback requests.
    * @throws SessionIllegalState  If the operation is not allowed in this state.
    * @throws SessionLost          If the server is unable to create the session.
    * @throws NullPointerException If hlaCallbackRequestListener is null.
    */
   void start(HlaCallbackRequestListener hlaCallbackRequestListener)
   throws SessionLost, SessionIllegalState;

   /**
    * Start the session.
    *
    * @param hlaCallbackRequestListener Handler for callback requests.
    * @param connectionTimeout          The timeout for the initial response from the
    *                                   server.
    * @param connectionTimeoutUnit      The time unit for connectionTimeout.
    * @throws SessionIllegalState  If the operation is not allowed in this state.
    * @throws SessionLost          If the session cannot be established for any reason.
    * @throws NullPointerException If hlaCallbackRequestListener is null.
    */
   void start(
         HlaCallbackRequestListener hlaCallbackRequestListener,
         long connectionTimeout,
         TimeUnit connectionTimeoutUnit)
   throws SessionLost, SessionIllegalState;

   /**
    * Resume session on with new socket.
    * <p>
    * The session is terminated if the session can not be resumed.
    *
    * @return True if session is resumed, false otherwise.
    * @throws SessionLost         If the server refuses to resume the session or sends a
    *                             bad response message.
    * @throws SessionIllegalState If the operation is not allowed in this state.
    * @throws IOException         If an IO error occurs.
    */
   boolean resume()
   throws SessionLost, IOException, SessionIllegalState;

   /**
    * Send a heartbeat, CTRL_HEARTBEAT, message.
    * <p>
    * The returned completable future will be completed when the corresponding heartbeat
    * response, CTRL_HEARTBEAT_RESPONSE, message is received from the server.
    *
    * @return A completable future for the heartbeat response message.
    * @throws SessionAlreadyTerminated If the session is already terminated.
    * @throws SessionIllegalState      If the operation is not allowed in this state.
    */
   CompletableFuture<byte[]> sendHeartbeat()
   throws SessionIllegalState;

   /**
    * Send an HLA call message (HLA_CALL_REQUEST).
    * <p>
    * The returned completable future will be completed when the corresponding response
    * message, HLA_CALL_RESPONSE, is received from the server.
    *
    * @return A completable future for the HLA call response message.
    * @throws SessionAlreadyTerminated If the session is already terminated.
    * @throws SessionIllegalState      If the operation is not allowed in this state.
    */
   CompletableFuture<byte[]> sendHlaCallRequest(byte[] encodedHlaCall)
   throws SessionIllegalState;

   /**
    * Send an HLA callback response message (HLA_CALLBACK_RESPONSE).
    *
    * @param responseToSequenceNumber The sequence number of the HLA callback message
    *                                 which this message is a response to.
    * @param hlaCallbackResponse      Encoded result of the HLA callback.
    * @throws SessionAlreadyTerminated If the session is already terminated.
    * @throws SessionIllegalState      If the operation is not allowed in this state.
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
    * If transport layer communication is available, a CTRL_TERMINATE_SESSION message is
    * sent; the corresponding response, CTRL_SESSION_TERMINATED, is then awaited.
    * Typically, all requests that are outstanding when this method is called will be
    * completed before termination. If there is a timeout or interruption, some
    * outstanding requests may be completed exceptionally. When this method returns, the
    * session is always considered terminated. Any requests sent after this method is
    * called will be completed exceptionally once the session terminates.
    *
    * @throws SessionAlreadyTerminated If the session is already terminated.
    * @throws SessionIllegalState      If the operation is not allowed in this state.
    * @throws SessionLost              If this method is interrupted for any reason,
    *                                  including timeout, while waiting for the  server to respond. The session is still
    *                                  considered terminated when this happens.
    */
   void terminate()
   throws SessionLost, SessionIllegalState;

   /**
    * Terminate the session.
    * <p>
    * A terminated session may not be restarted.
    * <p>
    * If transport layer communication is available, a CTRL_TERMINATE_SESSION message is
    * sent; the corresponding response, CTRL_SESSION_TERMINATED, is then awaited.
    * Typically, all requests that are outstanding when this method is called will be
    * completed before termination. If there is a timout or interruption, some outstanding
    * requests may be completed exceptionally. When this method returns, the session is
    * always considered terminated. Any requests sent after this method is called will be
    * completed exceptionally once the session terminates.
    *
    * @param responseTimeout     The maximum time to wait for a response.
    * @param responseTimeoutUnit The time unit of the responseTimeout argument.
    * @throws SessionAlreadyTerminated If the session is already terminated.
    * @throws SessionIllegalState      If the operation is not allowed in this state.
    * @throws SessionLost              If this method is interrupted for any reason,
    *                                  including timeout, while waiting for the server to respond. The session is still
    *                                  considered terminated when this happens.
    */
   void terminate(int responseTimeout, TimeUnit responseTimeoutUnit)
   throws SessionLost, SessionIllegalState;

}
