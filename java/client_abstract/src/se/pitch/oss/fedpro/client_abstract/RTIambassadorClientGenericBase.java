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

package se.pitch.oss.fedpro.client_abstract;

import com.google.protobuf.InvalidProtocolBufferException;
import hla.rti1516_202X.fedpro.*;
import se.pitch.oss.fedpro.client.PersistentSession;
import se.pitch.oss.fedpro.client.SessionFactory;
import se.pitch.oss.fedpro.client.SimpleResumeStrategy;
import se.pitch.oss.fedpro.client.TransportFactory;
import se.pitch.oss.fedpro.client.transport.TransportBase;
import se.pitch.oss.fedpro.common.exceptions.SessionIllegalState;
import se.pitch.oss.fedpro.common.exceptions.SessionAlreadyTerminated;
import se.pitch.oss.fedpro.common.exceptions.SessionLost;
import net.jcip.annotations.GuardedBy;
import se.pitch.oss.fedpro.client_abstract.exceptions.*;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import static se.pitch.oss.fedpro.client.TimeoutConstants.*;

public abstract class RTIambassadorClientGenericBase {
   private static final Logger LOGGER = Logger.getLogger(RTIambassadorClientGenericBase.class.getName());

   @GuardedBy("_connectionStateLock")
   protected PersistentSession _persistentSession;
   private final BlockingQueue<QueuedCallback> _callbackQueue = new LinkedBlockingQueue<>();
   @GuardedBy("_connectionStateLock")
   private Thread _callbackThread;
   private final AtomicReference<Thread> _callbackInProgressThread = new AtomicReference<>();
   private final Object _callbackLock = new Object();
   @GuardedBy("_callbackLock")
   private boolean _callbackInProgress = false;
   @GuardedBy("_callbackLock")
   private boolean _callbacksEnabled = true;

   // We protect connect and disconnect calls here at the top level, to simplify some code. All other HLA calls are made
   //  thread-safe further down, in the Session classes.
   protected final Object _connectionStateLock = new Object();

   protected boolean _asyncUpdates = false;

   private static class QueuedCallback {
      final CallbackRequest callbackRequest;
      final int sequenceNumber;
      final boolean needsResponse;

      public QueuedCallback(
            CallbackRequest callbackRequest,
            int sequenceNumber,
            boolean needsResponse)
      {
         this.callbackRequest = callbackRequest;
         this.sequenceNumber = sequenceNumber;
         this.needsResponse = needsResponse;
      }
   }

   private final QueuedCallback POISON = new QueuedCallback(null, 0, false);
   private final QueuedCallback DUMMY = new QueuedCallback(null, 0, false);

   protected RTIambassadorClientGenericBase()
   {
   }

   private void callbackLoop()
   {
      while (true) {
         try {
            synchronized (_callbackLock) {
               // Wait for callbacks to be enabled
               while (!_callbacksEnabled) {
                  _callbackLock.wait();
               }
               // Callbacks are enabled. Let's go!
               _callbackInProgress = true;
            }
            try {
               QueuedCallback queuedCallback = _callbackQueue.take();
               if (queuedCallback == DUMMY) {
                  // Wakeup from disableCallbacks. Loop around.
                  continue;
               }
               if (queuedCallback == POISON) {
                  return;
               }
               dispatchCallback(queuedCallback);
            } finally {
               synchronized (_callbackLock) {
                  _callbackInProgress = false;
                  _callbackLock.notifyAll();
               }
            }

         } catch (InterruptedException e) {
            // Interrupted. Let's get out of here.
            return;
         }
      }
   }

   public boolean isInCurrentCallbackThread()
   {
      return _callbackInProgressThread.get() == Thread.currentThread();
   }

   protected boolean evokeCallbackBase(double approximateMinimumTimeInSeconds)
   throws FedProRtiInternalError
   {
      synchronized (_connectionStateLock) {
         if (_callbackThread != null) {
            // Immediate mode
            return false;
         }
      }
      // Evoke
      long start = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
      long minTime = (long) (start + approximateMinimumTimeInSeconds * 1000);
      try {
         synchronized (_callbackLock) {
            while (!_callbacksEnabled) {
               long now = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
               long timeRemainingMillis = minTime - now;
               if (timeRemainingMillis <= 0) {
                  // Callbacks did not become enabled within our allotted time
                  return false;
               }
               _callbackLock.wait(timeRemainingMillis);
            }
            // Callbacks are enabled. Let's go!
            _callbackInProgress = true;
         }
         long now = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
         long timeRemainingMillis = minTime - now;
         try {
            QueuedCallback queuedCallback;
            if (minTime == 0.0) {
               // If timeout is zero, check if there's a callback immediately available.
               // But don't wait.
               queuedCallback = _callbackQueue.poll();
            } else {
               queuedCallback = _callbackQueue.poll(timeRemainingMillis, TimeUnit.MILLISECONDS);
            }
            if (queuedCallback == null) {
               return false;
            }
            if (queuedCallback == DUMMY) {
               // Wakeup from enableCallbacks. Why did we end up here?
               // If callbacks were disabled, we should be stuck in tryAcquire.
               return !_callbackQueue.isEmpty();
            }
            if (queuedCallback == POISON) {
               return false;
            }
            dispatchCallback(queuedCallback);
         } finally {
            synchronized (_callbackLock) {
               _callbackInProgress = false;
               _callbackLock.notifyAll();
            }
         }
      } catch (InterruptedException e) {
         throw new FedProRtiInternalError("Interrupted while waiting for callbackLock", e);
      }
      return !_callbackQueue.isEmpty();
   }

   protected boolean evokeMultipleCallbacksBase(
         double minimumTime,
         double maximumTime)
   throws FedProRtiInternalError
   {
      synchronized (_connectionStateLock) {
         if (_callbackThread != null) {
            // Immediate mode
            return false;
         }
      }
      long startMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
      long waitLimitMillis = (long) (startMillis + minimumTime * 1000);
      long cutoffTimeMillis = (long) (startMillis + maximumTime * 1000);

      while (true) {
         try {
            synchronized (_callbackLock) {
               while (!_callbacksEnabled) {
                  long now = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
                  long timeRemainingMillis = waitLimitMillis - now;
                  if (timeRemainingMillis <= 0) {
                     // Callbacks did not become enabled within our allotted time
                     return false;
                  }
                  _callbackLock.wait(timeRemainingMillis);
               }
               _callbackInProgress = true;
            }
            long now = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
            try {
               QueuedCallback queuedCallback;
               if (now < waitLimitMillis) {
                  // If we haven't reached minimumTime, wait until callback becomes available but not longer than minimumTime
                  long timeoutMillis = waitLimitMillis - now;
                  queuedCallback = _callbackQueue.poll(timeoutMillis, TimeUnit.MILLISECONDS);
               } else {
                  // We are past minimumTime. Keep going until no more callbacks or we hit maximumTime
                  if (now < cutoffTimeMillis) {
                     // TODO pass an appropriate wait duration for this poll call
                     queuedCallback = _callbackQueue.poll();
                  } else {
                     queuedCallback = null;
                  }
               }
               if (queuedCallback == null) {
                  // No more callbacks or we ran out of time
                  return !_callbackQueue.isEmpty();
               }
               if (queuedCallback == DUMMY) {
                  // Wakeup from enableCallbacks/disableCallbacks. Loop around and start over
                  return !_callbackQueue.isEmpty();
               }
               if (queuedCallback == POISON) {
                  return false;
               }
               dispatchCallback(queuedCallback);
            } finally {
               synchronized (_callbackLock) {
                  _callbackInProgress = false;
                  _callbackLock.notifyAll();
               }
            }
         } catch (InterruptedException e) {
            throw new FedProRtiInternalError("Interrupted while waiting for callbackLock", e);
         }
      }
   }

   private void dispatchCallback(QueuedCallback queuedCallback)
   {
      PersistentSession session = getSession();

      try {
         _callbackInProgressThread.set(Thread.currentThread());
         dispatchHlaVersionSpecificCallback(queuedCallback.callbackRequest);
         _callbackInProgressThread.set(null);

         if (session != null && queuedCallback.needsResponse) {
            CallbackResponse callbackResponse = CallbackResponse.newBuilder()
                  .setCallbackSucceeded(CallbackSucceeded.getDefaultInstance())
                  .build();
            session.sendHlaCallbackResponse(queuedCallback.sequenceNumber, callbackResponse.toByteArray());
         }
      } catch (FedProFederateInternalError e) {
         LOGGER.log(Level.WARNING, e, () -> "Exception in federate callback handler");
         if (queuedCallback.needsResponse) {
            sendHlaCallbackErrorResponse(session, queuedCallback.sequenceNumber, e.getCause());
         }
      } catch (FedProRtiInternalError | RuntimeException e) {
         // TODO: do something more sensible with these exceptions
         e.printStackTrace();
      } catch (SessionIllegalState e) {
         // Terminated: A failed callback response is not even important enough to log in the context of session termination.
         // Not initialized: Can basically not happen.
      }
   }

   protected abstract void dispatchHlaVersionSpecificCallback(CallbackRequest callbackRequest)
   throws FedProFederateInternalError, FedProRtiInternalError;

   protected void enableCallbacksBase()
   throws FedProRtiInternalError
   {
      try {
         synchronized (_callbackLock) {
            _callbacksEnabled = true;
            _callbackQueue.put(DUMMY);
            _callbackLock.notifyAll();
         }
      } catch (InterruptedException e) {
         throw new FedProRtiInternalError("Interrupted while waiting for callbackLock", e);
      }
   }

   protected void disableCallbacksBase()
   throws FedProRtiInternalError
   {
      try {
         synchronized (_callbackLock) {
            _callbacksEnabled = false;
            _callbackQueue.put(DUMMY);
            _callbackLock.notifyAll();
            if (isInCurrentCallbackThread()) {
               // This call comes from within a callback.
               // Obviously, if this is the callback thread then we cannot wait here
               // for the callback thread to terminate.
            } else {
               // COM_9-2 states that disableCallbacks shall not return until an
               // ongoing callback has finished
               while (_callbackInProgress) {
                  _callbackLock.wait();
               }
            }
         }
      } catch (InterruptedException e) {
         throw new FedProRtiInternalError("Interrupted while waiting for callbackLock", e);
      }
   }

   protected PersistentSession getSession()
   {
      synchronized (_connectionStateLock) {
         return _persistentSession;
      }
   }

   private CallResponse doHlaCallBase(CallRequest.Builder callRequestBuilder)
   throws FedProRtiInternalError, FedProNotConnected
   {
      return doHlaCallBase(callRequestBuilder.build());
   }

   protected CallResponse doHlaCallBase(CallRequest callRequest)
   throws FedProRtiInternalError, FedProNotConnected
   {
      try {
         PersistentSession session = getSession();
         if (session == null) {
            throw new FedProNotConnected("Federate is not connected");
         }
         CompletableFuture<byte[]> call = session.sendHlaCallRequest(callRequest.toByteArray());

         // TODO: Handle CancellationException and CompletionException
         byte[] response = call.join();

         CallResponse callResponse = CallResponse.parseFrom(response);

         throwOnException(callResponse);

         return callResponse;
      } catch (IOException e) {
         throw new FedProRtiInternalError("Failed to perform HLA call", e);
      } catch (SessionIllegalState e) {
         throw new FedProNotConnected("HLA call without a federate protocol session: " + e);
      }
   }

   protected CompletableFuture<CallResponse> doAsyncHlaCallBase(CallRequest callRequest)
   throws FedProNotConnected
   {
      try {
         PersistentSession session = getSession();
         if (session == null) {
            throw new FedProNotConnected("Adapter not connected");
         }

         CompletableFuture<byte[]> futureResponse = session.sendHlaCallRequest(callRequest.toByteArray());

         // TODO Make sure the returned future, if completed exceptionally, always has an RTIexception
         //  as the cause for the CompletionException. May use handle() or exceptionally().
         return futureResponse.thenApply(response -> {
            try {
               CallResponse callResponse = CallResponse.parseFrom(response);

               // This throws the appropriate RTIexception. This is caught below
               // and passed on as a CompletionException. This completionException
               // is thrown by get/join. The sync variants catch this completionException,
               // extracts the cause and rethrows that.
               throwOnException(callResponse);

               return callResponse;
            } catch (InvalidProtocolBufferException | FedProRtiException e) {
               // Will this result in nested CompletionExceptions, or not?
               throw new CompletionException(e);
            }
         });

      } catch (SessionIllegalState e) {
         throw new FedProNotConnected("HLA call without a federate protocol session.", e);
      }
   }

   protected abstract void throwOnException(CallResponse callResponse)
   throws FedProRtiInternalError;

   private static void sendHlaCallbackErrorResponse(
         PersistentSession resumingClient,
         int sequenceNumber,
         Throwable throwable)
   {
      if (resumingClient != null) {
         try {
            CallbackResponse callbackResponse = CallbackResponse.newBuilder()
                  .setCallbackFailed(ExceptionData.newBuilder()
                        .setExceptionName(throwable.getClass().getSimpleName())
                        .setDetails(throwable.getMessage()))
                  .build();

            resumingClient.sendHlaCallbackResponse(sequenceNumber, callbackResponse.toByteArray());
         } catch (SessionIllegalState e) {
            // Terminated: A callback error is not even important enough to log in the context of session termination.
            // Not initialized: Can basically not happen
         }
      }
   }

   private void hlaCallbackRequest(
         int sequenceNumber,
         byte[] hlaCallback)
   {
      try {
         CallbackRequest callback = CallbackRequest.parseFrom(hlaCallback);
         _callbackQueue.put(new QueuedCallback(callback, sequenceNumber, true));
      } catch (InvalidProtocolBufferException e) {
         LOGGER.warning(() -> String.format(
               "Failed to parse callback request with sequence number %d: %s",
               sequenceNumber,
               e));
         sendHlaCallbackErrorResponse(getSession(), sequenceNumber, e);
      } catch (InterruptedException e) {
         // Time to leave
         sendHlaCallbackErrorResponse(getSession(), sequenceNumber, e);
      }
   }

   private void lostConnection(String reason)
   {
      // TODO Anything more we should do?

      CallbackRequest connectionLostRequest = CallbackRequest.newBuilder()
            .setConnectionLost(ConnectionLost.newBuilder().setFaultDescription("Unable to resume session").build())
            .build();
      try {
         _callbackQueue.put(new QueuedCallback(connectionLostRequest, 0, false));
      } catch (InterruptedException e) {
         // Give up
         // TODO LOGGER. We should probably log something here
      }
   }

   private static final String SETTING_NAME_ASYNC = "ASYNC";
   private static final String SETTING_NAME_PROTOCOL = "protocol";
   private static final String SYSPROP_NAME_PROTOCOL = "FedPro.default.protocol";

   private static final String PROTOCOL_SETTING_TCP = "tcp";
   private static final String PROTOCOL_SETTING_TLS = "tls";
   private static final String PROTOCOL_SETTING_WS = "websocket";
   private static final String PROTOCOL_SETTING_WSS = "websocketsecure";

   @GuardedBy("_connectionStateLock")
   protected PersistentSession createPersistentSession(ConnectSettings connectSettings)
   throws FedProRtiInternalError
   {
      final long heartBeatIntervalMillis = getTimeoutSettingMillis(
            SETTING_NAME_HEARTBEAT_INTERVAL,
            DEFAULT_HEARTBEAT_INTERVAL_MILLIS);
      final long responseTimeoutMillis = getTimeoutSettingMillis(
            SETTING_NAME_RESPONSE_TIMEOUT,
            DEFAULT_RESPONSE_TIMEOUT_MILLIS);
      final long reconnectTimeoutMillis = getTimeoutSettingMillis(
            SETTING_NAME_RECONNECT_LIMIT,
            DEFAULT_RECONNECT_LIMIT_MILLIS);

      // Always assign to honor changes in settings between successive connect/disconnect calls.
      _asyncUpdates = connectSettings.hasSetting(SETTING_NAME_ASYNC) || Boolean.getBoolean("fedpro.asyncUpdates");

      LOGGER.config(() -> String.format("Asynchronous updates are %s.", _asyncUpdates ? "enabled" : "disabled"));

      String setting = connectSettings.getSetting(SETTING_NAME_PROTOCOL);
      if (setting == null) {
         setting = System.getProperties().getProperty(SYSPROP_NAME_PROTOCOL);
         if (setting == null) {
            setting = PROTOCOL_SETTING_TCP;
         }
      }

      String finalSetting = setting;
      LOGGER.config(() -> String.format("Protocol is set to %s.", finalSetting));

      String host = connectSettings.getServer();
      int port = connectSettings.getPort();
      TransportBase transport;
      switch (setting.toLowerCase()) {
         case PROTOCOL_SETTING_TLS:
            // TODO: Create option to allow for TLS clients
            throw new RuntimeException("Option 'tls' not implemented.");
         case PROTOCOL_SETTING_WS:
            if (port > 0) {
               transport = (TransportBase) TransportFactory.createWebSocketTransport(host, port);
            } else {
               transport = (TransportBase) TransportFactory.createWebSocketTransport(host);
            }
            break;
         case PROTOCOL_SETTING_WSS:
            // TODO: Create option to allow for WebSocket Secure clients
            throw new RuntimeException("Option 'websocketsecure' not implemented.");
         case PROTOCOL_SETTING_TCP:
            if (port > 0) {
               transport = (TransportBase) TransportFactory.createTcpTransport(host, port);
            } else {
               transport = (TransportBase) TransportFactory.createTcpTransport(host);
            }
            break;
         default:
            throw new FedProRtiInternalError(String.format("Invalid transport type '%s' in settings", setting));
      }

      PersistentSession persistentSession = SessionFactory.createPersistentSession(
            transport,
            this::lostConnection,
            new SimpleResumeStrategy(DEFAULT_RECONNECT_DELAY_MILLIS, reconnectTimeoutMillis));
      try {
         persistentSession.setSessionTimeout(responseTimeoutMillis, TimeUnit.MILLISECONDS);
      } catch (SessionIllegalState ignore) {
         // Will not happen
      }
      persistentSession.setHeartbeatInterval(heartBeatIntervalMillis, TimeUnit.MILLISECONDS);


      connectSettings.logSettings(LOGGER, SETTING_NAME_PROTOCOL, SETTING_NAME_ASYNC);

      return persistentSession;
   }

   private long getTimeoutSettingMillis(
         String settingName,
         long defaultMillis)
   throws FedProRtiInternalError
   {
      // The federate timeout settings are defined in seconds in the standard.
      try {
         String propertyValue = System.getProperty(settingName, System.getenv(settingName));
         return Optional.ofNullable(propertyValue).map(Long::parseLong).map(i -> i * 1000L).orElse(defaultMillis);
      } catch (NumberFormatException e) {
         throw new FedProRtiInternalError(
               "Could not parse federate protocol setting " + settingName + " for federate",
               e);
      }
   }

   @GuardedBy("_connectionStateLock")
   protected void startPersistentSession()
   throws FedProConnectionFailed, FedProRtiInternalError, FedProNotConnected
   {
      /*
       * It's better to provide this information in the RtiConfiguration, but we still allow this as an option.
       *
       * LSD format looks like this:
       *    [[<Federate Protocol Server IP or Host Name>][:<Server Port>][;[<Flag[=value]>[,<Flag[=value]>[,...]]][;[<HLA LSD for Server to connect to>]]]]
       *
       * For example "192.168.1.1:15164", "fedpro.cloud.pitch.se;;CRC@cloudbooster", ";DEBUG", ";;prtievolved.lkpg.pitch.se"
       *
       * Default server host name is "localhost"
       * Default port is 15164, or 15165 if using TLS
       * Default HLA LSD is "", empty string
       */

      final long startSessionTimeoutMillis =
            getTimeoutSettingMillis(SETTING_NAME_CONNECTION_TIMEOUT, DEFAULT_RESPONSE_TIMEOUT_MILLIS);

      try {
         _persistentSession.start(this::hlaCallbackRequest, startSessionTimeoutMillis, TimeUnit.MILLISECONDS);
      } catch (SessionLost | SessionIllegalState e) {
         // There's no point keeping the session if it failed to initialize
         _persistentSession = null;
         throw new FedProConnectionFailed("Failed to start FedPro session: " + e.getMessage(), e);
      }
   }

   @GuardedBy("_connectionStateLock")
   protected void startCallbackThread()
   {
      _callbackThread = new Thread(this::callbackLoop);
      _callbackThread.setName("Callback Thread " + _persistentSession.getId());
      _callbackThread.setDaemon(true);
      _callbackThread.start();
   }

   protected void disconnectBase()
   throws FedProRtiInternalError
   {
      synchronized (_connectionStateLock) {
         try {
            CallResponse callResponse = doHlaCallBase(CallRequest.newBuilder()
                  .setDisconnectRequest(DisconnectRequest.newBuilder()));
            // Todo - I don't see how we need throwOnException() here? remove?
            throwOnException(callResponse);
         } catch (FedProNotConnected e) {
            LOGGER.fine(() -> String.format("Call to disconnect while not connected: %s", e));
            // Already disconnected. Just ignore.
            return;
         }

         terminatePersistentSession();

      }

      stopCallbackThread();
   }

   @GuardedBy("_connectionStateLock")
   private void terminatePersistentSession()
   throws FedProRtiInternalError
   {
      try {
         _persistentSession.terminate();
      } catch (SessionLost e) {
         LOGGER.fine(() -> String.format("Exception while trying to disconnect: %s", e));
         // Termination request did not complete. Still need cleanup below.
      } catch (SessionAlreadyTerminated e) {
         LOGGER.fine(() -> String.format("Call to disconnect while not connected: %s", e));
         // Already disconnected. Just ignore.
      } catch (SessionIllegalState | RuntimeException e) {
         // TODO: What happens in the standard API when we call disconnect before the RTIambassador is connected?
         throw new FedProRtiInternalError("" + e, e);
      }
   }

   private void stopCallbackThread()
   {
      try {
         // Poison pill ends up last in callback queue.
         // Should we drop all queued callbacks?
         _callbackQueue.put(POISON);
         synchronized (_callbackLock) {
            // If callbacks are disabled, then evoke may be stuck waiting
            // for notification and callbacks to be enabled.
            _callbacksEnabled = true;
            _callbackLock.notifyAll();
         }

         // _connectionStateLock needs to be released at this point as _callbackThread may be stuck in a deadlock
         //   waiting for _connectionStateLock.

         //noinspection FieldAccessNotGuarded
         if (_callbackThread != null) {
            //noinspection FieldAccessNotGuarded
            _callbackThread.join();
         } else {
            // Evoked mode.
         }
      } catch (InterruptedException e) {
      }
      synchronized (_connectionStateLock) {
         _persistentSession = null;
         _callbackThread = null;
      }
   }

}
