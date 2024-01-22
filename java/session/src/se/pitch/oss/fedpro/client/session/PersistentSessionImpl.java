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

package se.pitch.oss.fedpro.client.session;

import se.pitch.oss.fedpro.client.PersistentSession;
import se.pitch.oss.fedpro.client.ResumeStrategy;
import se.pitch.oss.fedpro.client.Session;
import se.pitch.oss.fedpro.client.Transport;
import se.pitch.oss.fedpro.common.exceptions.SessionIllegalState;
import se.pitch.oss.fedpro.common.exceptions.SessionLost;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static se.pitch.oss.fedpro.client.TimeoutConstants.DEFAULT_HEARTBEAT_INTERVAL_MILLIS;

public class PersistentSessionImpl implements PersistentSession {

   private static final Logger LOGGER = Logger.getLogger(PersistentSessionImpl.class.getName());

   private final SessionImpl _session;
   private final ResumeStrategy _resumeStrategy;
   private final ConnectionLostListener _connectionLostListener;

   private long _heartbeatInterval = DEFAULT_HEARTBEAT_INTERVAL_MILLIS;
   private TimeUnit _heartbeatIntervalUnit = TimeUnit.MILLISECONDS;
   private TimeoutTimer _sendHeartbeatTimer;

   /**
    * @param transportProtocol      The underlying transport layer
    * @param connectionLostListener The listener to be called when the session is terminally lost due to connection issues
    * @param resumeStrategy         A ResumeStrategy instance that will control when, how often and for how long reconnection
    *                               attempts will be made
    */
   public PersistentSessionImpl(
         Transport transportProtocol,
         ConnectionLostListener connectionLostListener,
         ResumeStrategy resumeStrategy)
   {
      _session = new SessionImpl(transportProtocol);
      _connectionLostListener = connectionLostListener;
      _resumeStrategy = resumeStrategy;
      _session.addStateListener(this::stateListener);
   }

   @Override
   public void setHeartbeatInterval(
         long heartbeatInterval,
         TimeUnit heartbeatIntervalUnit)
   {
      if (_sendHeartbeatTimer == null) {
         _heartbeatInterval = heartbeatInterval;
         _heartbeatIntervalUnit = heartbeatIntervalUnit;
      } else {
         LOGGER.warning(() -> String.format(
               "%s: Heartbeat interval can only be set before session start. ",
               logPrefix()));
      }
   }

   @Override
   public void setSessionTimeout(
         long sessionTimeout,
         TimeUnit sessionTimeoutUnit)
   throws SessionIllegalState
   {
      _session.setSessionTimeout(sessionTimeout, sessionTimeoutUnit);
   }

   @Override
   public long getId()
   {
      return _session.getId();
   }

   @Override
   public void start(SessionImpl.HlaCallbackRequestListener hlaCallbackRequestListener)
   throws SessionLost, SessionIllegalState
   {
      _session.start(hlaCallbackRequestListener);
      startHeartBeatTimer();
   }

   @Override
   public void start(
         SessionImpl.HlaCallbackRequestListener hlaCallbackRequestListener,
         long connectionTimeout,
         TimeUnit connectionTimeoutUnit)
   throws SessionLost, SessionIllegalState
   {
      _session.start(hlaCallbackRequestListener, connectionTimeout, connectionTimeoutUnit);
      startHeartBeatTimer();
   }

   private void startHeartBeatTimer()
   {
      _sendHeartbeatTimer = TimeoutTimer.createEagerTimeoutTimer(
            "Client Session Heartbeat Timer",
            _heartbeatIntervalUnit.toMillis(_heartbeatInterval));
      _session.setMessageSentListener(_sendHeartbeatTimer::extend);
      _sendHeartbeatTimer.start(this::heartbeat);
   }

   @Override
   public CompletableFuture<byte[]> sendHlaCallRequest(byte[] encodedHlaCall)
   throws SessionIllegalState
   {
      return _session.sendHlaCallRequest(encodedHlaCall);
   }

   @Override
   public void sendHlaCallbackResponse(
         int responseToSequenceNumber,
         byte[] hlaCallbackResponse)
   throws SessionIllegalState
   {
      _session.sendHlaCallbackResponse(responseToSequenceNumber, hlaCallbackResponse);
   }

   @Override
   public void terminate()
   throws SessionLost, SessionIllegalState
   {
      if (_sendHeartbeatTimer != null) {
         _sendHeartbeatTimer.cancel();
      }
      _session.terminate();
   }

   @Override
   public void terminate(int responseTimeout, TimeUnit responseTimeoutUnit)
   throws SessionLost, SessionIllegalState
   {
      if (_sendHeartbeatTimer != null) {
         _sendHeartbeatTimer.cancel();
      }
      _session.terminate(responseTimeout, responseTimeoutUnit);
   }


   /**
    * Disrupt the current transport connection.
    * <p>
    * This function is intended for use in testing only.
    */
   public void forceCloseConnection()
   {
      _session.forceCloseConnection();
   }

   public Session getSession()
   {
      return _session;
   }

   private void heartbeat()
   {
      _sendHeartbeatTimer.resume();
      // TODO: Why throw away your future?
      try {
         _session.sendHeartbeat();
      } catch (SessionIllegalState e) {
         // session terminated or not initialized
      } catch (RuntimeException e) {
         LOGGER.log(Level.SEVERE, e,
               () -> String.format("%s: Unexpected exception in session heartbeat timer.", logPrefix()));
      }
   }

   private void stateListener(SessionImpl.State oldState, SessionImpl.State newState, String reason)
   {
      if (oldState == SessionImpl.State.RUNNING && newState == SessionImpl.State.DROPPED) {
         runResumeStrategy();
      }
   }

   private void runResumeStrategy()
   {
      _sendHeartbeatTimer.pause();

      final long disconnectedAtTime = System.currentTimeMillis();
      long timeSinceDisconnect = 0;
      Exception firstResumeFailedException = null;

      while (_resumeStrategy.shouldRetry(timeSinceDisconnect)) {
         try {
            _session.resume();
            _sendHeartbeatTimer.resume();
            LOGGER.info(() -> String.format("%s: Resumed session.", logPrefix()));
            return;
         } catch (SessionLost | SessionIllegalState e) {
            LOGGER.severe(() -> String.format("%s: Failed to resume: %s", logPrefix(), e));
            _connectionLostListener.onConnectionLost("Failed to resume Federate Protocol session: " + e);
            return;
         } catch (IOException e) {
            if (firstResumeFailedException == null) {
               firstResumeFailedException = e;
            }
            LOGGER.warning(() -> String.format("%s: Failed to resume: %s", logPrefix(), e));
            // Try again, given not in last for-loop iteration.
         }

         timeSinceDisconnect = System.currentTimeMillis() - disconnectedAtTime;
         try {
            sleepMillis(_resumeStrategy.getRetryDelay(timeSinceDisconnect));
         } catch (InterruptedException e) {
            return;
         }
      }

      LOGGER.severe(() -> String.format(
            "%s: Failed to resume session after %d seconds. Terminating session.",
            logPrefix(),
            _resumeStrategy.getRetryLimitMillis() / 1000));
      try {
         _session.terminate();
      } catch (SessionIllegalState | SessionLost e) {
         LOGGER.fine(() -> String.format("%s: Exception when terminating: %s", logPrefix(), e));
      }

      _connectionLostListener.onConnectionLost(
            "Failed to resume Federate Protocol session after trying for " + _resumeStrategy.getRetryLimitMillis() +
                  " seconds. Reason for first failure: " + firstResumeFailedException);
   }

   private String logPrefix()
   {
      return _session.logPrefix();
   }

   private void sleepMillis(long timeMillis)
   throws InterruptedException
   {
      if (timeMillis > 0) {
         Thread.sleep(timeMillis);
      }
   }
}
