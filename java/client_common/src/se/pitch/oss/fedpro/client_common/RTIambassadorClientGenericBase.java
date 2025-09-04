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

package se.pitch.oss.fedpro.client_common;

import com.google.protobuf.InvalidProtocolBufferException;
import hla.rti1516_2025.fedpro.*;
import net.jcip.annotations.GuardedBy;
import se.pitch.oss.fedpro.client.*;
import se.pitch.oss.fedpro.client.SimpleResumeStrategy;
import se.pitch.oss.fedpro.client.session.SessionSettings;
import se.pitch.oss.fedpro.common.Protocol;
import se.pitch.oss.fedpro.common.exceptions.SessionIllegalState;
import se.pitch.oss.fedpro.common.exceptions.SessionAlreadyTerminated;
import se.pitch.oss.fedpro.common.exceptions.SessionLost;
import se.pitch.oss.fedpro.client_common.exceptions.*;
import se.pitch.oss.fedpro.common.session.LogUtil;
import se.pitch.oss.fedpro.common.session.MovingStats;
import se.pitch.oss.fedpro.common.session.StandAloneMovingStats;
import se.pitch.oss.fedpro.common.session.MovingStatsNoOp;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import static se.pitch.oss.fedpro.client.Settings.*;
import static se.pitch.oss.fedpro.client_common.ServiceSettings.DEFAULT_ASYNC_UPDATES;
import static se.pitch.oss.fedpro.client_common.ServiceSettings.DEFAULT_CONNECTION_PROTOCOL;

public abstract class RTIambassadorClientGenericBase {
   private static final Logger LOGGER = Logger.getLogger(RTIambassadorClientGenericBase.class.getName());

   protected static final String crcAddressPrefix = "crcAddress=";

   private final ScheduledExecutorService _statPrintingExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
      Thread thread = Executors.defaultThreadFactory().newThread(r);
      thread.setName("FedPro Client Stat printing");
      thread.setDaemon(true);
      return thread;
   });

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

   @GuardedBy("_connectionStateLock")
   private boolean _shutdownInProgress = false;

   // Service layer settings
   protected boolean _asyncUpdates = DEFAULT_ASYNC_UPDATES;
   private String _protocol;

   private MovingStats _hlaSyncUpdateStats;
   private MovingStats _hlaAsyncUpdateStats;
   private MovingStats _hlaSyncSentInteraction;
   private MovingStats _hlaAsyncSentInteraction;
   private MovingStats _hlaSyncSentDirectedInteraction;
   private MovingStats _hlaAsyncSentDirectedInteraction;
   private MovingStats _hlaCallTimeStats;

   protected boolean _printStats;
   protected int _printStatsIntervalMillis;

   private static class QueuedCallback {
      final CallbackRequest callbackRequest;
      final int sequenceNumber;
      final boolean needsResponse;

      public QueuedCallback(
            CallbackRequest callbackRequest, int sequenceNumber, boolean needsResponse)
      {
         this.callbackRequest = callbackRequest;
         this.sequenceNumber = sequenceNumber;
         this.needsResponse = needsResponse;
      }
   }

   private final QueuedCallback POISON = new QueuedCallback(null, 0, false);
   private final QueuedCallback PLACEHOLDER = new QueuedCallback(null, 0, false);

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
               if (queuedCallback == PLACEHOLDER) {
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
            if (queuedCallback == PLACEHOLDER) {
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
         double minimumTime, double maximumTime)
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
               if (queuedCallback == PLACEHOLDER) {
                  // Wakeup from enableCallbacks/disableCallbacks. Loop around and start over
                  continue;
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
      PersistentSession session = _persistentSession;

      try {
         _callbackInProgressThread.set(Thread.currentThread());
         dispatchHlaVersionSpecificCallback(queuedCallback.callbackRequest);
         _callbackInProgressThread.set(null);

         if (session != null && queuedCallback.needsResponse) {
            CallbackResponse callbackResponse =
                  CallbackResponse.newBuilder().setCallbackSucceeded(CallbackSucceeded.getDefaultInstance()).build();
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
            _callbackQueue.put(PLACEHOLDER);
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
            _callbackQueue.put(PLACEHOLDER);
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

   protected CallResponse decodeHlaCallResponse(byte[] encodedResponse)
   throws IOException, FedProRtiInternalError
   {
      CallResponse callResponse = CallResponse.parseFrom(encodedResponse);

      throwOnException(callResponse);

      return callResponse;
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
         long sendtime = MovingStats.validTimeMillis();
         CompletableFuture<byte[]> call = session.sendHlaCallRequest(callRequest.toByteArray());

         // TODO: Handle CancellationException and CompletionException
         byte[] response = call.join();
         _hlaCallTimeStats.sample((int) (MovingStats.validTimeMillis() - sendtime));

         return decodeHlaCallResponse(response);
      } catch (IOException e) {
         throw new FedProRtiInternalError("Failed to perform HLA call", e);
      } catch (SessionIllegalState e) {
         throw new FedProNotConnected("HLA call without a federate protocol session: " + e);
      }
   }

   protected CompletableFuture<byte[]> doAsyncHlaCallBase(CallRequest callRequest)
   throws FedProNotConnected
   {
      try {
         PersistentSession session = getSession();
         if (session == null) {
            throw new FedProNotConnected("Adapter not connected");
         }

         long sendtime = MovingStats.validTimeMillis();
         return session.sendHlaCallRequest(callRequest.toByteArray()).thenApply(bytes -> {
            _hlaCallTimeStats.sample((int) (MovingStats.validTimeMillis() - sendtime));
            return bytes;
         });
      } catch (SessionIllegalState e) {
         throw new FedProNotConnected("HLA call without a federate protocol session.", e);
      }
   }

   // Derived classes sneakily throw API-specific RTIexception
   protected abstract void throwOnException(CallResponse callResponse)
   throws FedProRtiInternalError;

   private static void sendHlaCallbackErrorResponse(
         PersistentSession resumingClient, int sequenceNumber, Throwable throwable)
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
         int sequenceNumber, byte[] hlaCallback)
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


      // TODO: When we get a connectionLost callback from the LRC, should we then also close the FedPro connection?
      //   Otherwise, we may keep creating new FedPro session and leave them idle, i.e. leak them.
      CallbackRequest connectionLostRequest = CallbackRequest.newBuilder()
            .setConnectionLost(ConnectionLost.newBuilder().setFaultDescription("Unable to resume session").build())
            .build();
      try {
         _callbackQueue.put(new QueuedCallback(connectionLostRequest, 0, false));
      } catch (InterruptedException e) {
         // Give up
         LOGGER.severe(() -> String.format(
               "Interrupted while trying to put a callback on the callback queue when the connection was lost: %s",
               e));
      }
   }

   @GuardedBy("_connectionStateLock")
   protected PersistentSession createPersistentSession(TypedProperties settings)
   throws FedProRtiInternalError, InvalidSetting
   {
      // Always assign to honor changes in settings between successive connect/disconnect calls.
      _asyncUpdates = settings.getBoolean(SETTING_NAME_ASYNC_UPDATES, DEFAULT_ASYNC_UPDATES);

      PersistentSession persistentSession = SessionFactory.createPersistentSession(
            createTransportConfiguration(settings),
            this::lostConnection,
            this::sessionTerminated,
            settings,
            new SimpleResumeStrategy(settings));

      TypedProperties allServiceSettingsUsed = new TypedProperties();
      allServiceSettingsUsed.setBoolean(SETTING_NAME_ASYNC_UPDATES, _asyncUpdates);
      allServiceSettingsUsed.setString(SETTING_NAME_CONNECTION_PROTOCOL, _protocol);
      LOGGER.config(() -> String.format(
            "Federate Protocol client service layer settings used:\n%s",
            allServiceSettingsUsed.toPrettyString()));
      _printStatsIntervalMillis = (int) settings.getDuration(SETTING_NAME_PRINT_STATS_INTERVAL, Duration.ofMillis(SessionSettings.DEFAULT_PRINT_STATS_INTERVAL_MILLIS)).toMillis();
      _printStats = settings.getBoolean(SETTING_NAME_PRINT_STATS, false);
      if (_printStats) {
         _hlaSyncUpdateStats = new StandAloneMovingStats(_printStatsIntervalMillis);
         _hlaAsyncUpdateStats = new StandAloneMovingStats(_printStatsIntervalMillis);
         _hlaSyncSentInteraction = new StandAloneMovingStats(_printStatsIntervalMillis);
         _hlaAsyncSentInteraction = new StandAloneMovingStats(_printStatsIntervalMillis);
         _hlaSyncSentDirectedInteraction = new StandAloneMovingStats(_printStatsIntervalMillis);
         _hlaAsyncSentDirectedInteraction = new StandAloneMovingStats(_printStatsIntervalMillis);
         _hlaCallTimeStats = new StandAloneMovingStats(_printStatsIntervalMillis);
      } else {
         _hlaSyncUpdateStats = new MovingStatsNoOp();
         _hlaAsyncUpdateStats = new MovingStatsNoOp();
         _hlaSyncSentInteraction = new MovingStatsNoOp();
         _hlaAsyncSentInteraction = new MovingStatsNoOp();
         _hlaSyncSentDirectedInteraction = new MovingStatsNoOp();
         _hlaAsyncSentDirectedInteraction = new MovingStatsNoOp();
         _hlaCallTimeStats = new MovingStatsNoOp();
      }
      return persistentSession;
   }

   private Transport createTransportConfiguration(TypedProperties settings)
   throws FedProRtiInternalError
   {
      _protocol = settings.getString(SETTING_NAME_CONNECTION_PROTOCOL, DEFAULT_CONNECTION_PROTOCOL);
      switch (_protocol.toLowerCase()) {
         case Protocol.TLS:
            return TransportFactory.createTlsTransport(settings);
         case Protocol.WS:
            return TransportFactory.createWebSocketTransport(settings);
         case Protocol.WSS:
            return TransportFactory.createWebSocketSecureTransport(settings);
         case Protocol.TCP:
            return TransportFactory.createTcpTransport(settings);
         default:
            throw new FedProRtiInternalError(String.format("Invalid network protocol '%s' in settings", _protocol));
      }
   }

   @GuardedBy("_connectionStateLock")
   protected void startPersistentSession()
   throws FedProConnectionFailed, FedProRtiInternalError, FedProNotConnected
   {
      try {
         _persistentSession.start(this::hlaCallbackRequest);
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
      _callbackThread.setName("FedPro Client Session " + LogUtil.formatSessionId(_persistentSession.getId()) + " Callback Thread");
      _callbackThread.setDaemon(true);
      _callbackThread.start();
   }

   protected void disconnectBase()
   throws FedProRtiInternalError
   {
      synchronized (_connectionStateLock) {
         if (_persistentSession == null || _shutdownInProgress) {
            return;
         }
         try {
            CallResponse callResponse =
                  doHlaCallBase(CallRequest.newBuilder().setDisconnectRequest(DisconnectRequest.newBuilder()));
            // Todo - I don't see how we need throwOnException() here? remove?
            throwOnException(callResponse);
         } catch (FedProNotConnected e) {
            LOGGER.fine(() -> String.format("Call to disconnect while not connected: %s", e));
            // Already disconnected. Just ignore.
            return;
         }

         terminatePersistentSession();
         _shutdownInProgress = true;
      }
      cleanUpTerminatingSession();
      synchronized (_connectionStateLock) {
         _shutdownInProgress = false;
         _persistentSession = null;
      }
   }

   @GuardedBy("_connectionStateLock")
   private void terminatePersistentSession()
   throws FedProRtiInternalError
   {
      try {
         _persistentSession.terminate();
      } catch (SessionLost e) {
         LOGGER.fine(() -> String.format("Exception while trying to disconnect: %s", e));
         // Termination request did not complete.
      } catch (SessionAlreadyTerminated e) {
         LOGGER.fine(() -> String.format("Call to disconnect while not connected: %s", e));
         // Already disconnected. Just ignore.
      } catch (SessionIllegalState | RuntimeException e) {
         throw new FedProRtiInternalError("" + e, e);
      }
   }

   private void sessionTerminated(long sessionId) {
      synchronized (_connectionStateLock) {
         if (_persistentSession == null || _persistentSession.getId() != sessionId || _shutdownInProgress) {
            return;
         }
         _shutdownInProgress = true;
      }
      cleanUpTerminatingSession();
      synchronized (_connectionStateLock) {
         _shutdownInProgress = false;
         _persistentSession = null;
      }
   }

   private void cleanUpTerminatingSession() {
      stopStatPrinting();
      stopCallbackThread();
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
      } catch (InterruptedException ignored) {
      }
      synchronized (_connectionStateLock) {
         _callbackThread = null;
      }
   }

   private ScheduledFuture<?> _statPrintingFuture;

   protected void startStatPrinting() {
      if (_printStats) {
         stopStatPrinting();
         _statPrintingFuture = _statPrintingExecutor.scheduleAtFixedRate(this::printStats, _printStatsIntervalMillis, _printStatsIntervalMillis, TimeUnit.MILLISECONDS);
      }
   }

   private void stopStatPrinting() {
      if (_statPrintingFuture != null) {
         _statPrintingFuture.cancel(false);
         _statPrintingFuture = null;
      }
   }

   protected void countSyncUpdateForStats() {
      _hlaSyncUpdateStats.sample(1);
   }

   public void countAsyncUpdateForStats() {
      _hlaAsyncUpdateStats.sample(1);
   }

   protected void countSyncSentInteractionForStats() {
      _hlaSyncSentInteraction.sample(1);
   }

   public void countAsyncSentInteractionForStats() {
      _hlaAsyncSentInteraction.sample(1);
   }

   protected void countSyncSentDirectedInteractionForStats() {
      _hlaSyncSentDirectedInteraction.sample(1);
   }

   public void countAsyncSentDirectedInteractionForStats() {
      _hlaAsyncSentDirectedInteraction.sample(1);
   }

   private void printStats() {
      try {

         long time = MovingStats.validTimeMillis();

         MovingStats.Stats syncUpdates = _hlaSyncUpdateStats.getStatsForTime(time);
         MovingStats.Stats asyncUpdates = _hlaAsyncUpdateStats.getStatsForTime(time);
         MovingStats.Stats syncInteractions = _hlaSyncSentInteraction.getStatsForTime(time);
         MovingStats.Stats asyncInteractions = _hlaAsyncSentInteraction.getStatsForTime(time);
         MovingStats.Stats syncDirInteractions = _hlaSyncSentDirectedInteraction.getStatsForTime(time);
         MovingStats.Stats asyncDirInteractions = _hlaAsyncSentDirectedInteraction.getStatsForTime(time);
         MovingStats.Stats reflectStats = getReflectStats(time);
         MovingStats.Stats receivedInteractionStats = getReceivedInteractionStats(time);
         MovingStats.Stats receivedDirInteractionStats = getReceivedDirectedInteractionStats(time);
         MovingStats.Stats callbackTimeStats = getCallbackTimeStats(time);
         MovingStats.Stats callTimeStats = _hlaCallTimeStats.getStatsForTime(time);

         // The stats we are accessing have their own lock, and have no bearing on the session state.
         // @formatter:off
         // noinspection FieldAccessNotGuarded
         LOGGER.info("FedPro client stats for the last " + (int)(_printStatsIntervalMillis / 1000.0) + " seconds.\n" +
               "Session " + LogUtil.formatSessionId(_persistentSession.getId()) + "\n" +
               _persistentSession.getPrettyPrintedPerSecondStats() + "\n" +
               "HLA callbacks in queue:                             " + LogUtil.padStat(3) + LogUtil.printStatInt(_callbackQueue.size()) + "\n" +
               "\n" +
               "Updates sent (sync):                                " + LogUtil.printStatFloat(syncUpdates.averageBucket) + LogUtil.printStatInt(syncUpdates.maxBucket) + LogUtil.printStatInt(syncUpdates.minBucket) + LogUtil.printStatInt(syncUpdates.sum) + LogUtil.printStatLong(syncUpdates.historicTotal) + "\n" +
               "Updates sent (async):                               " + LogUtil.printStatFloat(asyncUpdates.averageBucket) + LogUtil.printStatInt(asyncUpdates.maxBucket) + LogUtil.printStatInt(asyncUpdates.minBucket) + LogUtil.printStatInt(asyncUpdates.sum) + LogUtil.printStatLong(asyncUpdates.historicTotal) + "\n" +
               "Updates received:                                   " + LogUtil.printStatFloat(reflectStats.averageBucket) + LogUtil.printStatInt(reflectStats.maxBucket) + LogUtil.printStatInt(reflectStats.minBucket) + LogUtil.printStatInt(reflectStats.sum) + LogUtil.printStatLong(reflectStats.historicTotal) + "\n" +
               "\n" +
               "Interactions sent (sync):                           " + LogUtil.printStatFloat(syncInteractions.averageBucket) + LogUtil.printStatInt(syncInteractions.maxBucket) + LogUtil.printStatInt(syncInteractions.minBucket) + LogUtil.printStatInt(syncInteractions.sum) + LogUtil.printStatLong(syncInteractions.historicTotal) + "\n" +
               "Interactions sent (async):                          " + LogUtil.printStatFloat(asyncInteractions.averageBucket) + LogUtil.printStatInt(asyncInteractions.maxBucket) + LogUtil.printStatInt(asyncInteractions.minBucket) + LogUtil.printStatInt(asyncInteractions.sum) + LogUtil.printStatLong(asyncInteractions.historicTotal) + "\n" +
               "Interactions received:                              " + LogUtil.printStatFloat(receivedInteractionStats.averageBucket) + LogUtil.printStatInt(receivedInteractionStats.maxBucket) + LogUtil.printStatInt(receivedInteractionStats.minBucket) + LogUtil.printStatInt(receivedInteractionStats.sum) + LogUtil.printStatLong(receivedInteractionStats.historicTotal) + "\n" +
               "\n" +
               "Directed Interactions sent (sync):                  " + LogUtil.printStatFloat(syncDirInteractions.averageBucket) + LogUtil.printStatInt(syncDirInteractions.maxBucket) + LogUtil.printStatInt(syncDirInteractions.minBucket) + LogUtil.printStatInt(syncDirInteractions.sum) + LogUtil.printStatLong(syncDirInteractions.historicTotal) + "\n" +
               "Directed Interactions sent (async):                 " + LogUtil.printStatFloat(asyncDirInteractions.averageBucket) + LogUtil.printStatInt(asyncDirInteractions.maxBucket) + LogUtil.printStatInt(asyncDirInteractions.minBucket) + LogUtil.printStatInt(asyncDirInteractions.sum) + LogUtil.printStatLong(asyncDirInteractions.historicTotal) + "\n" +
               "Directed Interactions received:                     " + LogUtil.printStatFloat(receivedDirInteractionStats.averageBucket) + LogUtil.printStatInt(receivedDirInteractionStats.maxBucket) + LogUtil.printStatInt(receivedDirInteractionStats.minBucket) + LogUtil.printStatInt(receivedDirInteractionStats.sum) + LogUtil.printStatLong(receivedDirInteractionStats.historicTotal) + "\n" +
               "\n" +
               "                                                        Average        Max        Min      Total   All time" + "\n" +
               // Call time, from the client's POV, is round-trip time to server plus time in queue in server plus actual HLA call time to the RTI
               "HLA call times (ms):                                " + LogUtil.printStatFloat(callTimeStats.averageSample) + LogUtil.printStatInt(callTimeStats.maxSampleWithDefault(0)) + LogUtil.printStatInt(callTimeStats.minSampleWithDefault(0)) + LogUtil.printStatInt(callTimeStats.sum) + LogUtil.printStatLong(callTimeStats.historicTotal) + "\n" +
               // Callback time, from the client's POV, is just time spent in federate code during callbacks
               "HLA callback times (ms):                            " + LogUtil.printStatFloat(callbackTimeStats.averageSample) + LogUtil.printStatInt(callbackTimeStats.maxSampleWithDefault(0)) + LogUtil.printStatInt(callbackTimeStats.minSampleWithDefault(0)) + LogUtil.printStatInt(callbackTimeStats.sum) + LogUtil.printStatLong(callbackTimeStats.historicTotal)
         );
         // @formatter:on

      } catch (RuntimeException e) {
         LOGGER.warning("Unexpected exception in stat printing thread: " + e);
      }
   }

   protected abstract MovingStats.Stats getReflectStats(long time);
   protected abstract MovingStats.Stats getReceivedInteractionStats(long time);
   protected abstract MovingStats.Stats getReceivedDirectedInteractionStats(long time);
   protected abstract MovingStats.Stats getCallbackTimeStats(long time);

   public static ArrayList<String> splitFederateConnectSettings(String inputString)
   {
      return new ArrayList<>(Arrays.asList(inputString.split("(\r?\n|,)", -1)));
   }

   protected static void addToSettingsLine(StringBuilder settingsLine, String toAdd)
   {
      if (settingsLine.length() > 0) {
         settingsLine.append(",");
      }
      settingsLine.append(toAdd);
   }

   public static String extractAndRemoveClientSettings(
         ArrayList<String> inputValueList,
         boolean crcAddressIsFedProServerAddress)
   {
      /*
       * This method moves all settings that are prefixed with 'FedPro.'from the input parameter to client setting
       * string and return it.
       * Exception: If the boolean parameter is set to true, the setting 'crcAddress' is interpreted as the address of
       * the FedPro server, not as an LRC setting.
       */
      StringBuilder clientSettings = new StringBuilder();

      Iterator<String> iterator = inputValueList.iterator();
      while (iterator.hasNext()) {
         String settingsLine = iterator.next();

         if (settingsLine.startsWith(crcAddressPrefix) && crcAddressIsFedProServerAddress) {

            String serverAddress = settingsLine.substring(crcAddressPrefix.length());
            String[] serverHostAndPort = serverAddress.split(":", 2);
            if (!serverHostAndPort[0].isEmpty()) {
               addToSettingsLine(clientSettings, SETTING_NAME_CONNECTION_HOST + "=" + serverHostAndPort[0]);
            }
            if (serverHostAndPort.length > 1 && !serverHostAndPort[1].isEmpty()) {
               addToSettingsLine(clientSettings, SETTING_NAME_CONNECTION_PORT + "=" + serverHostAndPort[1]);
            }
            iterator.remove();

         } else if (settingsLine.startsWith(SETTING_PREFIX)) {

            String setting = settingsLine.substring(SETTING_PREFIX.length());
            addToSettingsLine(clientSettings, setting);
            iterator.remove();

         }

      }
      return clientSettings.toString();
   }

}
