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

import net.jcip.annotations.GuardedBy;
import se.pitch.oss.fedpro.client.TypedProperties;
import se.pitch.oss.fedpro.client.Session;
import se.pitch.oss.fedpro.client.Transport;
import se.pitch.oss.fedpro.client.session.msg.*;
import se.pitch.oss.fedpro.client.transport.TransportBase;
import se.pitch.oss.fedpro.common.exceptions.BadMessage;
import se.pitch.oss.fedpro.common.exceptions.SessionAlreadyTerminated;
import se.pitch.oss.fedpro.common.exceptions.SessionIllegalState;
import se.pitch.oss.fedpro.common.exceptions.SessionLost;
import se.pitch.oss.fedpro.common.session.*;
import se.pitch.oss.fedpro.common.session.buffers.RoundRobinBuffer;
import se.pitch.oss.fedpro.common.session.flowcontrol.ExponentialRateLimiter;
import se.pitch.oss.fedpro.common.session.flowcontrol.NullRateLimiter;
import se.pitch.oss.fedpro.common.session.flowcontrol.RateLimiter;
import se.pitch.oss.fedpro.common.transport.FedProSocket;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;

import static java.util.Map.entry;
import static se.pitch.oss.fedpro.client.Settings.*;
import static se.pitch.oss.fedpro.client.session.SessionSettings.*;
import static se.pitch.oss.fedpro.client.session.TimeoutTimer.createLazyTimeoutTimer;
import static se.pitch.oss.fedpro.common.session.MessageType.CTRL_NEW_SESSION_STATUS;
import static se.pitch.oss.fedpro.common.session.MessageType.CTRL_RESUME_STATUS;

public class SessionImpl implements Session {

   private static final Logger LOGGER = Logger.getLogger(SessionImpl.class.getName());

   // TODO: Consider turning this into a FedPro setting.
   private static final long STATE_LISTENER_TIMEOUT_MILLIS = 100;

   private final TransportBase _transport;
   private ClientMessageWriter _messageWriter;
   private SocketWriter _socketWriter;
   private Thread _socketWriterThread;
   private TimeoutTimer _sessionTimeoutTimer;

   // Session layer settings
   private final long _sessionTimeoutMillis;
   private final boolean _rateLimitEnabled;
   private final int _maxRetryConnectAttempts;
   private final long _connectionTimeoutMillis;
   private final int _queueSize;

   private final Object _sessionLock = new Object();
   @GuardedBy("_sessionLock")
   private State _state = State.NEW;
   private FedProSocket _socket;
   private long _sessionId = MessageHeader.NO_SESSION_ID;
   private String _sessionIdString = LogUtil.formatSessionId(_sessionId);
   private final AtomicSequenceNumber _lastReceivedSequenceNumber =
         new AtomicSequenceNumber(SequenceNumber.NO_SEQUENCE_NUMBER);

   // TODO: Save the original Exception that caused session to drop, and relay its message to federate
   //   in the connectionLost callback when and if we fail to resume the session.
   private final CompletableFuture<Void> _sessionTerminatedFuture = new CompletableFuture<>();
   private final Map<Integer, CompletableFuture<byte[]>> _requestFutures = new ConcurrentHashMap<>();

   private HlaCallbackRequestListener _hlaCallbackRequestListener;
   private final ExecutorService _stateListenerExecutor;
   private final List<StateListener> _stateListeners = new ArrayList<>();

   private MessageSentListener _messageSentListener;

   private RoundRobinBuffer<QueueableMessage> _roundRobinMessageQueue;
   private final MovingStats _hlaCallStats;
   private final MovingStats _hlaCallbackStats;
   private final MovingStats _resumeCount;

   private final boolean _warnOnLateStateListenerShutdown;

   public SessionImpl(
         Transport transport,
         TypedProperties settings)
   {
      _transport = (TransportBase) transport;

      // Initialize settings
      if (settings != null) {
         _sessionTimeoutMillis = settings.getDuration(
               SETTING_NAME_RESPONSE_TIMEOUT,
               Duration.ofMillis(DEFAULT_RESPONSE_TIMEOUT_MILLIS)).toMillis();
         _rateLimitEnabled = settings.getBoolean(SETTING_NAME_RATE_LIMIT_ENABLED, DEFAULT_RATE_LIMIT_ENABLED);
         _maxRetryConnectAttempts = settings.getInt(
               SETTING_NAME_CONNECTION_MAX_RETRY_ATTEMPTS, DEFAULT_CONNECTION_MAX_RETRY_ATTEMPTS);
         _connectionTimeoutMillis = settings.getDuration(
               SETTING_NAME_CONNECTION_TIMEOUT, Duration.ofMillis(DEFAULT_CONNECTION_TIMEOUT_MILLIS)).toMillis();
         _queueSize = settings.getInt(SETTING_NAME_MESSAGE_QUEUE_SIZE, DEFAULT_MESSAGE_QUEUE_SIZE);
         boolean printStats = settings.getBoolean(SETTING_NAME_PRINT_STATS, false);
         _warnOnLateStateListenerShutdown = settings.getBoolean(SETTING_NAME_WARN_ON_LATE_STATE_LISTENER_SHUTDOWN, true);
         if (printStats) {
            int statsIntervalMillis = (int) settings.getDuration(
                  SETTING_NAME_PRINT_STATS_INTERVAL,
                  Duration.ofMillis(DEFAULT_PRINT_STATS_INTERVAL_MILLIS)).toMillis();
            _hlaCallStats = new StandAloneMovingStats(statsIntervalMillis);
            _hlaCallbackStats = new StandAloneMovingStats(statsIntervalMillis);
            _resumeCount = new StandAloneMovingStats(statsIntervalMillis);
         } else {
            _hlaCallbackStats = new MovingStatsNoOp();
            _hlaCallStats = new MovingStatsNoOp();
            _resumeCount = new MovingStatsNoOp();
         }
      } else {
         // Initialize settings
         _sessionTimeoutMillis = DEFAULT_RESPONSE_TIMEOUT_MILLIS;
         _rateLimitEnabled = DEFAULT_RATE_LIMIT_ENABLED;
         _maxRetryConnectAttempts = DEFAULT_CONNECTION_MAX_RETRY_ATTEMPTS;
         _connectionTimeoutMillis = DEFAULT_CONNECTION_TIMEOUT_MILLIS;
         _queueSize = DEFAULT_MESSAGE_QUEUE_SIZE;
         _hlaCallbackStats = new MovingStatsNoOp();
         _hlaCallStats = new MovingStatsNoOp();
         _resumeCount = new MovingStatsNoOp();
         _warnOnLateStateListenerShutdown = false;
      }

      // Make the stateListenerExecutor tasks be daemons.
      _stateListenerExecutor = Executors.newFixedThreadPool(1, r -> {
         // This shouldn't be executed for each new queued runnable
         // See ThreadFactory javadoc for more information
         Thread t = Executors.defaultThreadFactory().newThread(r);
         t.setDaemon(true);
         t.setName("FedPro Client State Listener Thread - " + t.getName());
         return t;
      });
   }

   void requestListenerExecutorShutdown()
   {
      // Add a poison task into the queue,
      // to request the executor to shut down itself after processing pending tasks.
      _stateListenerExecutor.execute(_stateListenerExecutor::shutdown);
      // Warn if executor does not complete within time limit
      // On separate thread so the state listener thread doesn't wait for itself.
      if (_warnOnLateStateListenerShutdown) {
         new Thread(() -> {
            try {
               if (!_stateListenerExecutor.awaitTermination(STATE_LISTENER_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)) {
                  LOGGER.warning(() -> String.format(
                        "%s: State Listener execution did not complete in %s ms (set log.warnOnLateStateListenerShutdown to false to disable this warning)",
                        logPrefix(),
                        STATE_LISTENER_TIMEOUT_MILLIS));
               }
            } catch (InterruptedException ignored) {}
         });
      }
   }


   @FunctionalInterface
   private interface ConnectOperation {
      void run()
      throws IOException, BadMessage, SessionLost;
   }

   private void tryConnectOperation(ConnectOperation connectOperation)
   throws IOException, BadMessage, SessionLost
   {
      int attempt = 0;
      while (true) {
         try {
            connectOperation.run();
            return;
         } catch (IOException lastException) {
            // Retry is allowed if the connection break or times out.
            // See IEEE 1516-2025 Federate Interface Specification, Figure 27—Federate Session Startup State Diagram.
            if (attempt < _maxRetryConnectAttempts) {
               try {
                  // Close the socket and wait before retrying
                  close(_socket);
                  Thread.sleep(300L);
               } catch (InterruptedException ex) {
                  // We were interrupted while waiting.
                  throw lastException;
               }
            } else {
               throw lastException;
            }
         }
         attempt++;
      }
   }

   @Override
   public long getId()
   {
      return _sessionId;
   }

   @Override
   public State getState()
   {
      State state;
      synchronized (_sessionLock) {
         state = _state;
      }
      return state;
   }

   @Override
   public void addStateListener(StateListener listener)
   {
      _stateListeners.add(listener);
   }

   @Override
   public void setMessageSentListener(MessageSentListener messageSentListener)
   {
      _messageSentListener = messageSentListener;
   }

   @Override
   public String getPrettyPrintedPerSecondStats() {
      MovingStats.Stats hlaCallStats = _hlaCallStats.getStats();
      MovingStats.Stats hlaCallBackStats = _hlaCallbackStats.getStats();
      MovingStats.Stats resumeStats = _resumeCount.getStats();

      // @formatter:off
      return "                                                      Average/s      Max/s      Min/s      Total   All time" + "\n" +
            "FedPro connection drop count:                       " + LogUtil.printStatFloat(resumeStats.averageBucket) + LogUtil.padStat(2) + LogUtil.printStatInt(resumeStats.sum) + LogUtil.printStatLong(resumeStats.historicTotal) + "\n" +
            "FedPro call request queue length:                   " + LogUtil.padStat(3) + LogUtil.printStatInt(_roundRobinMessageQueue.primarySize()) + "\n" +
            "FedPro callback response queue length:              " + LogUtil.padStat(3) + LogUtil.printStatInt(_roundRobinMessageQueue.alternateSize()) + "\n" +
            "FedPro call requests awaiting response:             " + LogUtil.padStat(3) + LogUtil.printStatInt(_requestFutures.size()) + "\n" +
            "HLA call count:                                     " + LogUtil.printStatFloat(hlaCallStats.averageBucket) + LogUtil.printStatInt(hlaCallStats.maxBucket) + LogUtil.printStatInt(hlaCallStats.minBucket) + LogUtil.printStatInt(hlaCallStats.sum) + LogUtil.printStatLong(hlaCallStats.historicTotal) + "\n" +
            "HLA callback count:                                 " + LogUtil.printStatFloat(hlaCallBackStats.averageBucket) + LogUtil.printStatInt(hlaCallBackStats.maxBucket) + LogUtil.printStatInt(hlaCallBackStats.minBucket) + LogUtil.printStatInt(hlaCallBackStats.sum) + LogUtil.printStatLong(hlaCallBackStats.historicTotal);
      // @formatter:on
   }

   private void waitForState(State state)
   throws InterruptedException
   {
      synchronized (_sessionLock) {
         while (_state != state) {
            _sessionLock.wait();
         }
      }
   }

   private State waitForState(
         State state,
         long timeout,
         TimeUnit timeUnit)
   throws InterruptedException
   {
      final long endTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
      synchronized (_sessionLock) {
         long timeLeft = endTime - System.currentTimeMillis();
         while (_state != state && timeLeft > 0) {
            _sessionLock.wait(timeLeft);
            timeLeft = endTime - System.currentTimeMillis();
         }
         return _state;
      }
   }

   private State waitForStates(
         long timeout,
         TimeUnit timeUnit,
         State... anticipatedStates)
   throws InterruptedException
   {
      final long endTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
      synchronized (_sessionLock) {
         long timeLeft = endTime - System.currentTimeMillis();
         List<State> anticipatedStatesList = Arrays.asList(anticipatedStates);
         while (!anticipatedStatesList.contains(_state) && timeLeft > 0) {
            // The parameter of wait() is specified in milliseconds.
            _sessionLock.wait(timeLeft);
            timeLeft = endTime - System.currentTimeMillis();
         }
         return _state;
      }
   }

   private State waitForStates(State... anticipatedStates)
   throws InterruptedException
   {
      List<State> anticipatedStatesList = Arrays.asList(anticipatedStates);
      synchronized (_sessionLock) {
         while (!anticipatedStatesList.contains(_state)) {
            _sessionLock.wait();
         }
         return _state;
      }
   }

   public State compareAndSetState(
         Map<State, State> transitions,
         String reason)
   {
      synchronized (_sessionLock) {
         State oldState = _state;
         for (Map.Entry<State, State> entry : transitions.entrySet()) {
            compareAndSetState(entry.getKey(), entry.getValue(), reason);
            if (oldState == entry.getKey()) {
               return oldState;
            }
         }
         return oldState;
      }
   }

   public State compareAndSetState(Map<State, State> transitions)
   {
      return compareAndSetState(transitions, "");
   }

   private State setState(State newState)
   {
      synchronized (_sessionLock) {
         return compareAndSetState(_state, newState);
      }
   }

   private State compareAndSetState(
         State expectedState,
         State newState)
   {
      return compareAndSetState(expectedState, newState, "");
   }

   private State compareAndSetState(
         State expectedState,
         State newState,
         String reason)
   {
      synchronized (_sessionLock) {
         State oldState = _state;
         if (_state == expectedState) {
            LOGGER.finer(() -> String.format("%s: %s -> %s", logPrefix(), oldState, newState));
            _state = newState;
            if (oldState != newState) {
               handleStateTransition(oldState, newState);
               fireOnStateTransition(oldState, newState, reason);
               if (newState == State.TERMINATED) {
                  requestListenerExecutorShutdown();
               }
            }
            _sessionLock.notifyAll();
         }
         return oldState;
      }
   }

   @GuardedBy("_sessionLock")
   private void handleStateTransition(
         State oldState,
         State newState)
   {
      assert oldState != newState;

      switch (oldState) {
         case NEW: {
            if (newState == State.STARTING) {
               // Handled in start().
            } else {
               assert false : "Illegal state transition. " + oldState + " -> " + newState;
            }
            break;
         }
         case STARTING: {
            if (newState == State.RUNNING) {
               startWriterThread();
               startReaderThread();
            } else if (newState == State.TERMINATED) {
               // Handled in start().
            } else {
               assert false : "Illegal state transition. " + oldState + " -> " + newState;
            }
            break;
         }
         case RUNNING: {
            // TODO: Since read and write threads are using non-interruptible streams (InputStream/OutputStream),
            //   we have to close the socket in order to stop them. An alternative would be to look into using
            //   Java NIO InterruptibleChannel and Selector
            if (newState == State.DROPPED) {
               _sessionTimeoutTimer.pause();
               _socketWriterThread.interrupt();
               close(_socket);
               _socket = null;
            } else if (newState == State.TERMINATING) {
               // Handled in terminate()
            } else {
               assert false : "Illegal state transition. " + oldState + " -> " + newState;
            }
            break;
         }
         case DROPPED: {
            if (newState == State.RUNNING) {
               startWriterThread();
               startReaderThread();
            } else if (newState == State.RESUMING) {
               // Handled in resume().
            } else if (newState == State.TERMINATING) {
               // Handled in terminate()
            } else {
               assert false : "Illegal state transition. " + oldState + " -> " + newState;
            }
            break;
         }
         case RESUMING: {
            if (newState == State.RUNNING) {
               startWriterThread();
               startReaderThread();
            } else if (newState == State.TERMINATED) {
               transitionToTerminated();
            } else if (newState == State.DROPPED) {
               // Nothing needs to be done, as the thread to resume has already been started
               _sessionTimeoutTimer.pause();
            } else {
               assert false : "Illegal state transition. " + oldState + " -> " + newState;
            }
            break;
         }
         case TERMINATING: {
            if (newState == State.TERMINATED) {
               transitionToTerminated();
            } else {
               assert false : "Illegal state transition. " + oldState + " -> " + newState;
            }
            break;
         }
         case TERMINATED: {
            assert false : "Illegal state transition. " + oldState + " -> " + newState;
            break;
         }
      }
   }

   @GuardedBy("_sessionLock")
   private void transitionToTerminated()
   {
      LOGGER.finer(() -> String.format("%s: Completing: %s", logPrefix(), _sessionTerminatedFuture));
      _sessionTimeoutTimer.cancel();
      _socketWriterThread.interrupt();
      close(_socket);
      // We can't wait for the socketWriterThread to terminate since it is using the same
      // sessionLock that we are guarded by here.
      failAllFuturesWhenTerminating();
   }

   @GuardedBy("_sessionLock")
   private void failAllFuturesWhenTerminating()
   {
      // The session State being terminated guarantees that no further doSessionOperation() call is possible, which
      // means new messages cannot be added to _roundRobinMessageQueue via _messageWriter.write...Message() calls.
      QueueableMessage queuedMessage;
      while ((queuedMessage = _roundRobinMessageQueue.poll()) != null) {
         CompletableFuture<byte[]> responseFuture = queuedMessage.removeResponseFuture();
         if (responseFuture != null) {
            failFutureWhenTerminating(responseFuture);
         }
      }

      _requestFutures.forEach((seqNum, future) -> failFutureWhenTerminating(future));
      _requestFutures.clear();

      failFutureWhenTerminating(_sessionTerminatedFuture);
   }

   @GuardedBy("_sessionLock")
   private void failFutureWhenTerminating(CompletableFuture<?> future)
   {
      try {
         future.completeExceptionally(new SessionLost("Session terminated before request could complete."));
      } catch (RuntimeException e) {
         LOGGER.fine(() -> String.format("%s: Unexpected exception in HLA call completion: %s", logPrefix(), e));
      }
   }

   @GuardedBy("_sessionLock")
   private void fireOnStateTransition(
         State oldState,
         State newState,
         String reason)
   {
      for (StateListener listener : _stateListeners) {
         _stateListenerExecutor.execute(() -> listener.onStateTransition(oldState, newState, reason));
      }
   }

   @Override
   public void start(HlaCallbackRequestListener hlaCallbackRequestListener)
   throws SessionLost, SessionIllegalState
   {
      start(hlaCallbackRequestListener, _connectionTimeoutMillis, TimeUnit.MILLISECONDS);
   }

   @Override
   public void start(
         HlaCallbackRequestListener hlaCallbackRequestListener,
         long connectionTimeout,
         TimeUnit connectionTimeoutUnit)
   throws SessionLost, SessionIllegalState
   {
      Objects.requireNonNull(hlaCallbackRequestListener);

      State beforeState = compareAndSetState(State.NEW, State.STARTING);
      if (beforeState == State.TERMINATED) {
         throw new SessionAlreadyTerminated("Cannot start a session that has terminated");
      } else if (beforeState != State.NEW) {
         throw new SessionIllegalState("Cannot start a session that is in state " + beforeState);
      }
      // Now that the session state is STARTING, this thread will have exclusive right to the session. This is
      // guaranteed since the public methods that attempt to alter the session will throw in the wrong state.

      RateLimiter limiter;
      if (_rateLimitEnabled) {
         limiter = new ExponentialRateLimiter(_queueSize);
      } else {
         limiter = new NullRateLimiter();
      }

      _roundRobinMessageQueue = new RoundRobinBuffer<>(
            _sessionLock,
            _queueSize,
            limiter,
            QueueableMessage::isHlaResponse);

      _hlaCallbackRequestListener = hlaCallbackRequestListener;

      try {
         tryConnectOperation(() -> {
            _socket = _transport.connect();

            _socketWriter = SocketWriter.createSocketWriterForThread(
                  MessageHeader.NO_SESSION_ID,
                  getSocketWriterListener(),
                  _roundRobinMessageQueue,
                  _socket,
                  true,
                  SequenceNumber.INITIAL_SEQUENCE_NUMBER + 1);
            // The first sequence number that socket writer will expect is not INITIAL_SEQUENCE_NUMBER, since the first
            // session message (CTRL_NEW_SESSION) will be sent directly to the socket and not stored in the message-sent
            // history.

            _socketWriter.writeDirectMessage(ClientMessageWriter.createNewSessionMessage());

            final long connectionTimeoutMillis = TimeUnit.MILLISECONDS.convert(connectionTimeout, connectionTimeoutUnit);
            final TimeoutTimer connectionTimeoutTimer = createLazyTimeoutTimer(
                  "FedPro Client Connection Timeout Timer",
                  connectionTimeoutMillis);
            connectionTimeoutTimer.start(() -> doWhenTimedOut(connectionTimeoutTimer.getTimeoutDurationMillis()));
            try {
               _sessionId = readNewSessionStatus(_socket.getInputStream());
               _sessionIdString = LogUtil.formatSessionId(_sessionId);
            } finally {
               connectionTimeoutTimer.cancel();
            }
         });

         assert _messageWriter == null;
         _messageWriter = new ClientMessageWriter(MessageHeader.NO_SESSION_ID, _roundRobinMessageQueue);
         _messageWriter.setSessionId(_sessionId);
         _socketWriter.setSessionId(_sessionId);

      } catch (IOException | BadMessage e) {
         compareAndSetState(State.STARTING, State.TERMINATED, e.toString());
         throw new SessionLost(e.getMessage(), e);
      } catch (SessionLost e) {
         compareAndSetState(State.STARTING, State.TERMINATED, e.toString());
         throw e;
      }

      _sessionTimeoutTimer = createLazyTimeoutTimer("FedPro Client Session Timeout Timer", _sessionTimeoutMillis);
      _sessionTimeoutTimer.start(() -> doWhenTimedOut(_sessionTimeoutTimer.getTimeoutDurationMillis()));

      compareAndSetState(State.STARTING, State.RUNNING);
   }

   private void doWhenTimedOut(long timeoutMillis)
   {
      LOGGER.severe(() -> String.format(
            "%s: No response from server in %d milliseconds, session timing out.",
            logPrefix(),
            timeoutMillis));
      close(_socket);
   }

   private SocketWriter.Listener getSocketWriterListener()
   {
      return new SocketWriter.Listener() {
         @Override
         public void exceptionOnWrite(Exception e)
         {
            LOGGER.info(() -> String.format("%s: Failed to write to socket: %s", logPrefix(), e.toString()));
            // Socket was probably closed. Socket reader thread will drop the session.
         }

         @Override
         public void messageSent()
         {
            if (_messageSentListener != null) {
               _messageSentListener.onMessageSent();
            }
         }
      };
   }

   /**
    * Disrupt the current transport connection.
    * <p>
    * This can be used for testing resume functionality.
    */
   public void forceCloseConnection()
   {
      LOGGER.fine(() -> String.format("%s: Forcefully closing the socket.", logPrefix()));

      close(_socket);
   }

   @Override
   public boolean resume()
   throws SessionLost, IOException, SessionIllegalState
   {
      State beforeState = compareAndSetState(State.DROPPED, State.RESUMING);
      if (beforeState == State.TERMINATED) {
         throw new SessionAlreadyTerminated("Cannot resume a session that has terminated");
      } else if (beforeState != State.DROPPED) {
         throw new SessionIllegalState("Cannot resume a session that is in state " + beforeState);
      }
      // Now that the session state is RESUMING, this thread will have exclusive right to the session. This is
      // guaranteed since the public methods that attempt to alter the session will throw in the wrong state.

      LOGGER.fine(() -> String.format("%s: Trying to resume.", logPrefix()));

      // In all cases a CTRL_NEW_SESSION_STATUS message will have always been received by the client at this point.
      // Therefore, it is risk-free that _lastReceivedSequenceNumber is initialized to INITIAL_SEQUENCE_NUMBER.
      // Oldest federate message always have a valid sequence number, since CTRL_NEW_SESSION will have always been sent at this point.
      final int lastReceivedRtiMessage = _lastReceivedSequenceNumber.get(); //LRR
      final int oldestAvailableFederateMessage = _socketWriter.getOldestAddedSequenceNumber(); //OAF
      final int newestAvailableFederateMessage = _socketWriter.getNewestAddedSequenceNumber();

      LOGGER.fine(() -> String.format(
            "%s: Oldest available Federate message %d. Last received RTI message is %d.",
            logPrefix(),
            oldestAvailableFederateMessage,
            lastReceivedRtiMessage));

      FedProSocket newSocket = null;
      try {

         newSocket = _transport.connect();

         SocketWriter directSocketWriter = SocketWriter.createDirectSocketWriter(_sessionId, newSocket, true);
         EncodedMessage resumeSessionMessage = ClientMessageWriter.createResumeSessionMessage(
               _sessionId,
               lastReceivedRtiMessage,
               oldestAvailableFederateMessage);
         directSocketWriter.writeDirectMessage(resumeSessionMessage);

         // Initialize _socket and resume timer before reading to enable read timeout.
         _socket = newSocket;
         _sessionTimeoutTimer.resume();
         ResumeStatusMessage resumeStatusMessage = readResumeStatus(newSocket.getInputStream());

         if (resumeStatusMessage.sessionStatus != ResumeStatusMessage.STATUS_OK_TO_RESUME) {
            throw new SessionLost("Server refused to resume session. Reason: " +
                  ResumeStatusMessage.toString(resumeStatusMessage.sessionStatus));
         }

         final int resumeFromNumber = SequenceNumber.nextAfter(resumeStatusMessage.lastReceivedFederateSequenceNumber);

         LOGGER.fine(() -> String.format(
               "%s: Resuming Federate messages from sequence number %d.",
               logPrefix(),
               resumeFromNumber));

         if (!SequenceNumber.isValidAsSequenceNumber(resumeStatusMessage.lastReceivedFederateSequenceNumber)) {
            _socketWriter.rewindToFirstMessage();
         } else if (SequenceNumber.isValidAsSequenceNumber(newestAvailableFederateMessage) &&
               newestAvailableFederateMessage != resumeStatusMessage.lastReceivedFederateSequenceNumber) {
            _socketWriter.rewindToSequenceNumberAfter(new SequenceNumber(resumeStatusMessage.lastReceivedFederateSequenceNumber));
         }

         _socketWriter.setNewSocket(newSocket);

         _resumeCount.sample(1);
         compareAndSetState(State.RESUMING, State.RUNNING);

         return true;

      } catch (BadMessage | SessionLost e) {
         close(newSocket);
         compareAndSetState(State.RESUMING, State.TERMINATED, e.toString());
         throwAsSessionException(e);
         // Will not be reached.
         return false;
      } catch (IOException e) {
         close(newSocket);
         compareAndSetState(State.RESUMING, State.DROPPED, e.toString());
         throw e;
      }
   }

   @Override
   public CompletableFuture<byte[]> sendHeartbeat()
   throws SessionIllegalState
   {
      return doSessionOperation(() -> _messageWriter.writeHeartbeatMessage(
            _lastReceivedSequenceNumber.get(),
            _requestFutures));
   }

   @Override
   public CompletableFuture<byte[]> sendHlaCallRequest(byte[] encodedHlaCall)
   throws SessionIllegalState
   {
      return doSessionOperation(() -> {
         _hlaCallStats.sample(1);
         return _messageWriter.writeHlaCallRequest(
               encodedHlaCall,
               _lastReceivedSequenceNumber.get(),
               _requestFutures);
      });
   }

   @Override
   public void sendHlaCallbackResponse(
         int responseToSequenceNumber,
         byte[] hlaCallbackResponse)
   throws SessionIllegalState
   {
      // This is called on the callback thread, which can theoretically be interrupted by application code.
      doSessionOperation(() -> {
         try {
            _messageWriter.writeHlaCallbackResponse(
                  responseToSequenceNumber,
                  hlaCallbackResponse,
                  _lastReceivedSequenceNumber.get());
         } catch (InterruptedException e) {
            throw new RuntimeException("Thread was interrupted by client application when sending a callback response.");
         }
      });
   }

   @Override
   public void terminate()
   throws SessionLost, SessionIllegalState
   {
      terminate(((int) _sessionTimeoutMillis), TimeUnit.MILLISECONDS);
   }

   @Override
   public void terminate(
         int responseTimeout,
         TimeUnit responseTimeoutUnit)
   throws SessionLost, SessionIllegalState
   {
      State[] stateBeforeTerminating = new State[1];
      synchronized (_sessionLock) {
         while (_state == State.RESUMING || _state == State.TERMINATING) {
            try {
               _sessionLock.wait();
            } catch (InterruptedException ignored) {
               break;
            }
         }
         doSessionOperation(() -> {
            stateBeforeTerminating[0] = compareAndSetState(Map.ofEntries(
                  entry(State.RUNNING, State.TERMINATING),
                  entry(State.DROPPED, State.TERMINATING)));
            // Henceforth, the session state is TERMINATING and so this thread has exclusive right to the session. This is
            // guaranteed since the public methods that attempt to alter the session will throw in the wrong state.
         });
      }

      if (getState() == State.TERMINATED) {
         // If runResumeStrategy fails to resume, it calls terminate (this method) to clean up
         // the RESUMING session. If the session is TERMINATED, it has terminated while we were
         // trying to resume.
         LOGGER.finer(() -> String.format("%s: Session terminated while trying to resume", logPrefix()));
         throw new SessionLost("Session terminated while trying to resume.");
      }

      if (stateBeforeTerminating[0] == State.DROPPED) {
         // The session was DROPPED. We tried to resume but failed, so we call terminate (this method)
         // to clean up.
         LOGGER.finer(() -> String.format("%s: Session terminated without resuming", logPrefix()));
         compareAndSetState(State.TERMINATING, State.TERMINATED, "Terminated dropped session without resuming.");
         return;
      }

      try {
         _messageWriter.writeTerminateMessage(_lastReceivedSequenceNumber.get());
      } catch (InterruptedException e) {
         LOGGER.finer(() -> String.format("%s: Session failed to write termination message", logPrefix()));
         throw new RuntimeException("Client application thread was interrupted while trying to terminate session.");
      }

      try {
         _sessionTerminatedFuture.get(responseTimeout, responseTimeoutUnit);
      } catch (InterruptedException e) {
         throw new SessionLost(
               logPrefix() + ": Interrupted while waiting for response to termination request: " + e,
               e);
      } catch (TimeoutException e) {
         throw new SessionLost(logPrefix() + ": Timed out after " + responseTimeoutUnit.toMillis(responseTimeout) +
                               " ms while waiting for response to termination request: " + e);
      } catch (CancellationException | ExecutionException e) {
         throw new SessionLost(logPrefix() + ": Failed to complete termination request to server: " + e);
      } finally {
         compareAndSetState(State.TERMINATING, State.TERMINATED, "Terminated on request.");
      }
   }

   @FunctionalInterface
   private interface AsyncSessionOperation {
      CompletableFuture<byte[]> run();
   }

   private void doSessionOperation(Runnable operation)
   throws SessionIllegalState
   {
      doSessionOperation(() -> {
         operation.run();
         return new CompletableFuture<>();
      });
   }

   // All public methods that results in writing outgoing messages are thread-safe since they pass through this method,
   // which only executes the passed operation in state RUNNING, DROPPED or RESUMING. This helps ensure correct ordering
   // of messages.
   private CompletableFuture<byte[]> doSessionOperation(AsyncSessionOperation operation)
   throws SessionIllegalState
   {
      synchronized (_sessionLock) {
         if (_state == State.RUNNING || _state == State.DROPPED || _state == State.RESUMING) {
            return operation.run();
         } else if (_state == State.TERMINATED) {
            throw new SessionAlreadyTerminated(logPrefix() + ": Session has terminated.");
         } else {
            throw new SessionIllegalState(logPrefix() + ": Session state is " + _state);
         }
      }
   }

   private void close(FedProSocket socket)
   {
      if (socket != null) {
         try {
            socket.close();
         } catch (IOException ignored) {
            LOGGER.fine(() -> String.format("%s: Failed to close socket.", logPrefix()));
         }
      }
   }

   @GuardedBy("_sessionLock")
   private void startWriterThread()
   {
      _socketWriter.enableWriterLoop();
      _socketWriterThread = new Thread(
            _socketWriter::socketWriterLoop,
            "FedPro Client Session " + _sessionIdString + " Writer Thread.");
      _socketWriterThread.setDaemon(true);
      _socketWriterThread.start();
   }

   @GuardedBy("_sessionLock")
   private void startReaderThread()
   {
      Thread readerThread = new Thread(
            this::runMessageReaderLoop,
            "FedPro Client Session " + _sessionIdString + " Reader Thread.");
      readerThread.setDaemon(true);
      readerThread.start();
   }

   private void runMessageReaderLoop()
   {
      try {
         FedProSocket socket = _socket;
         if (socket == null) {
            // We're still in DROPPED
            throw new EOFException();
         }
         InputStream inputStream = socket.getInputStream();

         while (true) {
            MessageHeader messageHeader = MessageHeader.decode(inputStream);
            logReceivedMessage(messageHeader.messageType, messageHeader.sequenceNumber);

            if (messageHeader.sessionId != _sessionId) {
               throw new BadMessage(
                     logPrefix() + ": Received wrong session ID " + LogUtil.formatSessionId(messageHeader.sessionId));
            } else if (!SequenceNumber.isValidAsSequenceNumber(messageHeader.sequenceNumber)) {
               throw new BadMessage(
                     logPrefix() + ": Received invalid sequence number " + messageHeader.sequenceNumber);
            }

            switch (messageHeader.messageType) {
               case CTRL_HEARTBEAT_RESPONSE: {
                  HeartbeatResponseMessage message = HeartbeatResponseMessage.decode(inputStream);

                  extendSessionTimer();
                  CompletableFuture<byte[]> future = _requestFutures.remove(message.responseToSequenceNumber);
                  if (future != null) {
                     future.completeAsync(() -> null);
                  } else {
                     LOGGER.warning(() -> String.format(
                           "%s: Received unexpected heartbeat response to sequence number %d.",
                           logPrefix(),
                           message.responseToSequenceNumber));
                  }
                  break;
               }

               case CTRL_SESSION_TERMINATED: {
                  extendSessionTimer();
                  LOGGER.finer(() -> String.format("%s: Received CTRL_SESSION_TERMINATED. Completing: %s", logPrefix(), _sessionTerminatedFuture));
                  _sessionTerminatedFuture.completeAsync(() -> null);

                  // This is the last message we can receive, there's no point on listening for further messages
                  return;
               }

               case HLA_CALL_RESPONSE: {
                  HlaCallResponseMessage message = HlaCallResponseMessage.decode(
                        inputStream,
                        messageHeader.getPayloadSize());

                  trackHlaMessageReceived(messageHeader.sequenceNumber);
                  CompletableFuture<byte[]> future = _requestFutures.remove(message.responseToSequenceNumber);
                  if (future != null) {
                     future.completeAsync(() -> message.hlaServiceReturnValueOrException);
                  } else {
                     // This can happen during shutdown. failAllFuturesWhenTerminating has already
                     // called completeExceptionally on all futures.
                     LOGGER.warning(() -> String.format(
                           "%s: Received unexpected HLA call response to sequence number %d.",
                           logPrefix(),
                           message.responseToSequenceNumber));
                  }
                  break;
               }

               case HLA_CALLBACK_REQUEST: {
                  HlaCallbackRequestMessage message = HlaCallbackRequestMessage.decode(
                        inputStream,
                        messageHeader.getPayloadSize());
                  // The _hlaCallbackRequestListener may call back into `this` to send a CallbackResponse
                  // The _hlaCallbackRequestListener may block if the callback buffer becomes full

                  _hlaCallbackStats.sample(1);
                  _hlaCallbackRequestListener.onHlaCallbackRequest(
                        messageHeader.sequenceNumber,
                        message.hlaServiceCallbackWithParams);
                  trackHlaMessageReceived(messageHeader.sequenceNumber);
                  break;
               }

               case CTRL_RESUME_REQUEST:
               case CTRL_RESUME_STATUS:
               case HLA_CALL_REQUEST:
               case CTRL_NEW_SESSION:
               case CTRL_NEW_SESSION_STATUS:
               case CTRL_HEARTBEAT:
               case CTRL_TERMINATE_SESSION:
               case HLA_CALLBACK_RESPONSE:
                  throw new BadMessage("Invalid MessageType received " + messageHeader.messageType);
            }
         }
      } catch (EOFException e) {
         // Socket was closed by server.
         handleReadException(e);
      } catch (IOException e) {
         // Socket was closed locally.
         handleReadException(e);
      } catch (BadMessage e) {
         // 12.13.4.4 requires entering a fatal state when receiving a bad message, so schedule termination.
         // Scheduling to better match the C++ client behavior.
         scheduleBestEffortTerminate();
         LOGGER.severe(() -> String.format("%s: Received invalid message! %s", logPrefix(), e));
      } catch (RuntimeException e) {
         handleReadException(e);
         LOGGER.severe(() -> String.format(
               "%s: Got unexpected exception in message reader thread: %s",
               logPrefix(),
               e));
      }
   }

   private void handleReadException(Exception e)
   {
      // Unexpected loss of connection.
      // If we were in RUNNING state, move to DROPPED.
      State oldState = compareAndSetState(State.RUNNING, State.DROPPED, e.toString());
      switch (oldState) {
         case RUNNING:
            // Moved to DROPPED. Will try to resume.
            LOGGER.info(() -> String.format("%s: While running, exception in reader thread: %s", logPrefix(), e));
            break;
         case TERMINATING:
            // Handles the case where socket was closed locally or by server while
            // we're trying to terminate the session.
            LOGGER.info(() -> String.format("%s: While terminating, exception in reader thread: %s", logPrefix(), e));
            _sessionTerminatedFuture.completeAsync(() -> null);
            break;
         case DROPPED:
            // Socket exception while we're trying to resume the session.
            // Just ignore and let resume deal with it.
            LOGGER.info(() -> String.format("%s: While dropped, exception in reader thread: %s", logPrefix(), e));
            break;
         default:
            // TODO Figure out what to do here
            LOGGER.warning(() -> String.format("%s: In %s, exception in reader thread: %s", logPrefix(), oldState, e));
            break;
      }
   }

   private void scheduleBestEffortTerminate()
   {
      _stateListenerExecutor.execute(() -> {
         try {
            // Best effort terminate. Do not wait for a response.
            final int responseTimeout = 0;
            terminate(responseTimeout, TimeUnit.MILLISECONDS);
         } catch (SessionLost ignored) {
            // SessionLost is likely, as terminate() do not wait for a response.
         } catch (SessionAlreadyTerminated ignored) {
            // Desired state.
         } catch (SessionIllegalState e) {
            LOGGER.warning(() -> String.format(
                  "%s: Unexpected stat during best-effort terminate. %s.",
                  logPrefix(),
                  e.getMessage()));
         }
      });
   }

   private void trackHlaMessageReceived(int sequenceNumber)
   {
      extendSessionTimer();
      if (SequenceNumber.isValidAsSequenceNumber(sequenceNumber)) {
         _lastReceivedSequenceNumber.set(sequenceNumber);
      } else {
         LOGGER.warning(() -> String.format(
               "%s: Received an invalid seq.nr. %s.",
               logPrefix(),
               LogUtil.padNumString(sequenceNumber)));
      }
   }

   private void extendSessionTimer()
   {
      if (_sessionTimeoutTimer != null) {
         // This timer may be null during the call to start.
         _sessionTimeoutTimer.extend();
      }
   }

   private long readNewSessionStatus(InputStream inputStream)
   throws IOException, BadMessage, SessionLost
   {
      MessageHeader messageHeader = MessageHeader.decode(inputStream);
      logReceivedMessage(messageHeader.messageType, messageHeader.sequenceNumber);

      if (messageHeader.messageType != CTRL_NEW_SESSION_STATUS) {
         throw new BadMessage(
               logPrefix() + ": Expected NewSessionStatus message, got " + messageHeader.messageType + ".");
      }

      NewSessionStatusMessage message = NewSessionStatusMessage.decode(inputStream);

      extendSessionTimer();

      if (message.reason != NewSessionStatusMessage.REASON_SUCCESS) {
         throw new SessionLost(logPrefix() + ": Server unable to create session, reason " + message.reason);
      }

      return messageHeader.sessionId;
   }

   private ResumeStatusMessage readResumeStatus(InputStream inputStream)
   throws IOException, BadMessage
   {
      MessageHeader messageHeader = MessageHeader.decode(inputStream);
      _sessionTimeoutTimer.extend();
      logReceivedMessage(messageHeader.messageType, messageHeader.sequenceNumber);

      if (messageHeader.messageType != CTRL_RESUME_STATUS) {
         throw new BadMessage(logPrefix() + ": Expected ResumeStatus message, got " + messageHeader.messageType);
      }

      return ResumeStatusMessage.decode(inputStream);
   }

   private static void throwAsSessionException(Exception e)
   throws SessionLost
   {
      if (e instanceof SessionLost) {
         throw (SessionLost) e;
      } else {
         throw new SessionLost(e);
      }
   }

   private void logReceivedMessage(
         MessageType messageType,
         int sequenceNumber)
   {
      LOGGER.finer(() -> String.format(
            "%s: Received seq.nr. %s, type %s",
            logPrefix(),
            LogUtil.padNumString(sequenceNumber),
            messageType));
   }

   TypedProperties getSettings()
   {
      TypedProperties settings = new TypedProperties();
      settings.setDuration(SETTING_NAME_RESPONSE_TIMEOUT, Duration.ofMillis(_sessionTimeoutMillis));
      settings.setInt(SETTING_NAME_CONNECTION_MAX_RETRY_ATTEMPTS, _maxRetryConnectAttempts);
      settings.setDuration(SETTING_NAME_CONNECTION_TIMEOUT, Duration.ofMillis(_connectionTimeoutMillis));
      settings.setBoolean(SETTING_NAME_RATE_LIMIT_ENABLED, _rateLimitEnabled);
      settings.setInt(SETTING_NAME_MESSAGE_QUEUE_SIZE, _queueSize);
      return settings;
   }

   String logPrefix()
   {
      return LogUtil.logPrefix(_sessionIdString, LogUtil.CLIENT_PREFIX);
   }
}
