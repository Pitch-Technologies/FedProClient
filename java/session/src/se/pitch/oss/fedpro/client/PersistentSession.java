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
    * Set the interval at which to send heartbeats when no other messages are being sent, in order to keep the
    * connection alive. May only be set before invoking {@link #start(Session.HlaCallbackRequestListener)}.
    *
    * @param heartbeatInterval     The interval at which to send heartbeats
    * @param heartbeatIntervalUnit The time unit of heartbeatInterval
    */
   void setHeartbeatInterval(
         long heartbeatInterval,
         TimeUnit heartbeatIntervalUnit);

   /**
    * Set the timeout for each request to the server, after which the session will be deemed dropped and attempt
    * reconnection.
    *
    * @param sessionTimeout     The timeout for each request
    * @param sessionTimeoutUnit The unit of sessionTimeout
    * @throws SessionIllegalState If the session has already started.
    */
   void setSessionTimeout(
         long sessionTimeout,
         TimeUnit sessionTimeoutUnit)
   throws SessionIllegalState;

   long getId();

   void start(Session.HlaCallbackRequestListener hlaCallbackRequestListener)
   throws SessionLost, SessionIllegalState;

   /**
    * Start a new Federate Protocol session
    *
    * @param hlaCallbackRequestListener The handler to be invoked for every callback the RTI needs to deliver to the federate
    * @param connectionTimeout          timeout for the server response to the initial connection
    * @param connectionTimeoutUnit      time unit for connectionTimeout
    * @throws SessionIllegalState if the operation is not allowed in this session state
    * @throws SessionLost         if the session is in a faulty state, or cannot be established for any reason
    */
   void start(
         Session.HlaCallbackRequestListener hlaCallbackRequestListener,
         long connectionTimeout,
         TimeUnit connectionTimeoutUnit)
   throws SessionLost, SessionIllegalState;

   /**
    * Send an HLA call to the Federate Protocol server. If the connection to the server is currently broken, or breaks
    * before the call can be sent, the call will be sent after the session has been reestablished.
    *
    * @param encodedHlaCall the encoded HLA call
    * @return a future that will be completed with the response to the HLA call, or, if the connection is terminated
    *       before the call can be completed, with an exception.
    * @throws SessionIllegalState if the operation is not allowed in this session state
    */
   CompletableFuture<byte[]> sendHlaCallRequest(byte[] encodedHlaCall)
   throws SessionIllegalState;

   /**
    * Send a response to an HLA callback received from the server.
    *
    * @param responseToSequenceNumber the sequence number of the callback request message this message is in response to
    * @param hlaCallbackResponse      the encoded callback response
    * @throws SessionIllegalState if the operation is not allowed in this session state
    */
   void sendHlaCallbackResponse(
         int responseToSequenceNumber,
         byte[] hlaCallbackResponse)
   throws SessionIllegalState;

   /**
    * Terminate this session.
    *
    * @throws SessionIllegalState if the operation is not allowed in this session state
    * @throws SessionLost         if this method is interrupted for any reason, including timeout, while waiting
    *                             for the server to respond. The session is still considered terminated when this happens.
    */
   void terminate()
   throws SessionLost, SessionIllegalState;

   /**
    * Terminate this session.
    *
    * @param responseTimeout     the maximum time to wait for a response to the termination request
    * @param responseTimeoutUnit the time unit of the responseTimeout argument
    * @throws SessionIllegalState if the operation is not allowed in this session state
    * @throws SessionLost         if this method is interrupted for any reason, including timeout, while waiting for the
    *                             server to respond. The session is still considered terminated when this happens.
    */
   void terminate(int responseTimeout, TimeUnit responseTimeoutUnit)
   throws SessionLost, SessionIllegalState;

   interface ConnectionLostListener {
      void onConnectionLost(String reason);
   }
}
