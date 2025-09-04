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

import se.pitch.oss.fedpro.common.exceptions.SessionAlreadyTerminated;
import se.pitch.oss.fedpro.common.exceptions.SessionIllegalState;
import se.pitch.oss.fedpro.common.exceptions.SessionLost;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * A representation of a client session which automatically sends heartbeats at a set interval when necessary, and
 * reconnects the session if the connection is broken.
 * <p>
 * Reconnection attempts will happen according to a supplied custom strategy, or, by default, every 5 seconds up to
 * a minute. If the connection cannot be reestablished, a {@link ConnectionLostListener} will be invoked.
 */
public interface PersistentSession {

   /**
    * Invoked when the session fails to reconnect to the server.
    */
   interface ConnectionLostListener {
      void onConnectionLost(String reason);
   }

   interface SessionTerminatedListener {
      void onSessionTerminated(long sessionId);
   }

   /**
    * Get the session ID.
    *
    * @return The session ID.
    */
   long getId();

   /**
    * Get pretty-printed session statistics that are expressed in quantity per second,
    * such as call count per second.
    */
   String getPrettyPrintedPerSecondStats();

   /**
    * Start a new Federate Protocol Persistent Session.
    *
    * @param hlaCallbackRequestListener The handler to be invoked for every callback that
    *                                   the RTI needs to deliver to the federate.
    * @throws SessionIllegalState  If the operation is not allowed in this state.
    * @throws SessionLost          If the session is in a faulty state, or cannot be
    *                              established for any reason.
    * @throws NullPointerException If hlaCallbackRequestListener is null.
    */
   void start(Session.HlaCallbackRequestListener hlaCallbackRequestListener)
   throws SessionLost, SessionIllegalState;

   /**
    * Start a new Federate Protocol Persistent Session.
    *
    * @param hlaCallbackRequestListener The handler to be invoked for every callback that
    *                                   the RTI needs to deliver to the federate.
    * @param connectionTimeout          Timeout for the server response to the initial
    *                                   connection.
    * @param connectionTimeoutUnit      Time unit for connectionTimeout.
    * @throws SessionIllegalState  If the operation is not allowed in this state.
    * @throws SessionLost          If the session is in a faulty state, or cannot be
    *                              established for any reason.
    * @throws NullPointerException If hlaCallbackRequestListener is null.
    */
   void start(
         Session.HlaCallbackRequestListener hlaCallbackRequestListener,
         long connectionTimeout,
         TimeUnit connectionTimeoutUnit)
   throws SessionLost, SessionIllegalState;

   /**
    * Send an HLA call to the server. If the connection to the server is currently broken,
    * or breaks before the call can be sent, the call will be sent after the session has
    * been reestablished.
    *
    * @param encodedHlaCall The encoded HLA call.
    * @return A future that will be completed with the response to the HLA call, or, if
    *       he connection is terminated before the call can be completed, with an exception.
    * @throws SessionAlreadyTerminated If the session is already terminated.
    * @throws SessionIllegalState      If the operation is not allowed in this state.
    */
   CompletableFuture<byte[]> sendHlaCallRequest(byte[] encodedHlaCall)
   throws SessionIllegalState;

   /**
    * Send a response to an HLA callback received from the server.
    *
    * @param responseToSequenceNumber The sequence number of the callback request message
    *                                 this message is in response to.
    * @param hlaCallbackResponse      The encoded callback response.
    * @throws SessionAlreadyTerminated If the session is already terminated.
    * @throws SessionIllegalState      If the operation is not allowed in this state.
    */
   void sendHlaCallbackResponse(
         int responseToSequenceNumber,
         byte[] hlaCallbackResponse)
   throws SessionIllegalState;

   /**
    * Terminate this session.
    *
    * @throws SessionAlreadyTerminated If the session is already terminated.
    * @throws SessionIllegalState      If the operation is not allowed in this state.
    * @throws SessionLost              If this method is interrupted for any reason,
    *                                  including timeout, while waiting. for the server to respond. The session is still
    *                                  considered terminated when this happens.
    */
   void terminate()
   throws SessionLost, SessionIllegalState;

   /**
    * Terminate this session.
    *
    * @param responseTimeout     The maximum time to wait for a response to the
    *                            termination request.
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
