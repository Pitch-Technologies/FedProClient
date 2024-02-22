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
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import se.pitch.oss.fedpro.client.Transport;
import se.pitch.oss.fedpro.client.Session;
import se.pitch.oss.fedpro.client.session.msg.*;
import se.pitch.oss.fedpro.client.transport.TransportBase;
import se.pitch.oss.fedpro.common.exceptions.BadMessage;
import se.pitch.oss.fedpro.common.exceptions.SessionIllegalState;
import se.pitch.oss.fedpro.common.exceptions.SessionAlreadyTerminated;
import se.pitch.oss.fedpro.common.exceptions.SessionLost;
import se.pitch.oss.fedpro.common.session.*;
import se.pitch.oss.fedpro.common.session.buffers.GenericBuffer;
import se.pitch.oss.fedpro.common.session.buffers.RoundRobinBuffer;
import se.pitch.oss.fedpro.common.session.flowcontrol.ExponentialRateLimiter;
import se.pitch.oss.fedpro.common.session.flowcontrol.NullRateLimiter;
import se.pitch.oss.fedpro.common.session.flowcontrol.RateLimiter;
import se.pitch.oss.fedpro.common.transport.FedProSocket;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Logger;

import static se.pitch.oss.fedpro.client.TimeoutConstants.DEFAULT_RESPONSE_TIMEOUT_MILLIS;
import static se.pitch.oss.fedpro.client.session.TimeoutTimer.createLazyTimeoutTimer;
import static se.pitch.oss.fedpro.common.session.MessageType.CTRL_NEW_SESSION_STATUS;
import static se.pitch.oss.fedpro.common.session.MessageType.CTRL_RESUME_STATUS;

public class SessionImpl implements Session {

   private static final Logger LOGGER = Logger.getLogger(SessionImpl.class.getName());

   private static final int MESSAGE_QUEUE_SIZE =
         Integer.getInteger("se.pitch.oss.fedpro.client.message.queue", 2000);
   private static final boolean RATE_LIMIT_ENABLED = Boolean.getBoolean("se.pitch.oss.fedpro.client.rate.limit");
   private static final boolean ALLOW_GAPS_IN_SEQUENCE_NUMBERS =
         Boolean.getBoolean("se.pitch.oss.fedpro.common.allowGapsInSequenceNumbers");

   private final TransportBase _transport;
   private ClientMessageWriter _messageWriter;
   private SocketWriter _socketWriter;
   private Thread _socketWriterThread;
   private TimeoutTimer _sessionTimeoutTimer;
   private long _sessionTimeout = DEFAULT_RESPONSE_TIMEOUT_MILLIS;

   private final Object _sessionLock = new Object();
   @GuardedBy("_sessionLock")
   private State _state = State.NEW;
   private FedProSocket _socket;
   private long _sessionId = MessageHeader.NO_SESSION_ID;
   private final AtomicSequenceNumber _lastReceivedSequenceNumber = new AtomicSequenceNumber();

   // TODO: Save the original Exception that caused session to drop, and relay its message to federate
   //   in the connectionLost callback when and if we fail to resume the session.
   private final AtomicReference<CompletableFuture<Void>> _sessionTerminatedFuture = new AtomicReference<>();
   private final Map<Integer, CompletableFuture<byte[]>> _requestFutures = new ConcurrentHashMap<>();

   private HlaCallbackRequestListener _hlaCallbackRequestListener;
   private static final ExecutorService _stateListenerExecutor;
   private final List<StateListener> _stateListeners = new ArrayList<>();

   static {
      // Make the stateListenerExecutor tasks be daemons.
      _stateListenerExecutor = Executors.newFixedThreadPool(1, r -> {
         // This shouldn't be executed for each new queued runnable
         // See ThreadFactory javadoc for more information
         Thread t = Executors.defaultThreadFactory().newThread(r);
         t.setDaemon(true);
         t.setName("StateListenerThread-" + t.getName());
         return t;
      });
   }

   private MessageSentListener _messageSentListener;

   public SessionImpl(Transport transport)
   {
      _transport = (TransportBase) transport;
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
   public void setSessionTimeout(
         long sessionTimeout,
         TimeUnit sessionTimeoutUnit)
   throws SessionIllegalState
   {
      synchronized (_sessionLock) {
         if (_state.equals(State.NEW)) {
            _sessionTimeout = sessionTimeoutUnit.toMillis(sessionTimeout);
         } else {
            throw new SessionIllegalState("Session timeout can only be set before session start.");
         }
      }
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
         while (!Arrays.asList(anticipatedStates).contains(_state) && timeLeft > 0) {
            // The parameter of wait() is specified in milliseconds.
            _sessionLock.wait(timeLeft);
            timeLeft = endTime - System.currentTimeMillis();
         }
         return _state;
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
            LOGGER.finer(() -> String.format("%s: %s -> %s", logPrefix(), _state, newState));
            _state = newState;
            if (oldState != newState) {
               transitionSessionState(oldState, newState);
               fireOnStateTransition(oldState, newState, reason);
            }
            _sessionLock.notifyAll();
         }
         return oldState;
      }
   }

   @GuardedBy("_sessionLock")
   private void transitionSessionState(
         State oldState,
         State newState)
   {
      assert oldState != newState;

      if (oldState == State.NEW) {
         if (newState == State.STARTING) {
            // Handled in start().
         } else {
            assert false : "Illegal state transition. " + oldState + " -> " + newState;
         }

      } else if (oldState == State.STARTING) {
         if (newState == State.RUNNING) {
            startWriterThread();
            startReaderThread();
         } else if (newState == State.TERMINATED) {
            // Handled in start().
         } else {
            assert false : "Illegal state transition. " + oldState + " -> " + newState;
         }

      } else if (oldState == State.RUNNING) {
         // TODO: Since read and write threads are using non-interruptible streams (InputStream/OutputStream),
         //   we have to close the socket in order to stop them. An alternative would be to look into using
         //   Java NIO InterruptibleChannel and Selector
         if (newState == State.DROPPED) {
            _sessionTimeoutTimer.pause();
            _socketWriterThread.interrupt();
            close(_socket);
            _socket = null;
         } else if (newState == State.TERMINATED) {
            transitionToTerminated();
         } else {
            assert false : "Illegal state transition. " + oldState + " -> " + newState;
         }

      } else if (oldState == State.DROPPED) {
         if (newState == State.RUNNING) {
            startWriterThread();
            startReaderThread();
         } else if (newState == State.RESUMING) {
            // Handled in resume().
         } else if (newState == State.TERMINATED) {
            transitionToTerminated();
         } else {
            assert false : "Illegal state transition. " + oldState + " -> " + newState;
         }

      } else if (oldState == State.RESUMING) {
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

      } else if (oldState == State.TERMINATED) {
         assert false : "Illegal state transition. " + oldState + " -> " + newState;
      }
   }

   private void transitionToTerminated()
   {
      _sessionTimeoutTimer.cancel();
      close(_socket);
      failAllFuturesWhenTerminating();
      _socketWriterThread.interrupt();
   }

   private void failAllFuturesWhenTerminating()
   {
      // TODO: Make sure it's OK to call this when synced on _stateLock.

      _requestFutures.forEach((seqNum, future) -> failFutureWhenTerminating(future));
      _requestFutures.clear();

      CompletableFuture<Void> future = _sessionTerminatedFuture.getAndSet(null);
      if (future != null) {
         failFutureWhenTerminating(future);
      }
   }

   private void failFutureWhenTerminating(CompletableFuture<?> future)
   {
      try {
         future.completeExceptionally(new SessionLost("Session terminated before request could complete."));
      } catch (RuntimeException e) {
         LOGGER.fine(() -> String.format("%s: Unexpected exception in HLA call completion: %s", logPrefix(), e));
      }
   }

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
      start(hlaCallbackRequestListener, DEFAULT_RESPONSE_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS);
   }

   @Override
   public void start(
         HlaCallbackRequestListener hlaCallbackRequestListener,
         long connectionTimeout,
         TimeUnit connectionTimeoutUnit)
   throws SessionLost, SessionIllegalState
   {
      Objects.requireNonNull(hlaCallbackRequestListener);
      synchronized (_sessionLock) {
         State beforeState = compareAndSetState(State.NEW, State.STARTING);
         switch (beforeState) {
            case NEW:
               // Correct state, continue starting.
               break;
            case STARTING:
               throw new SessionIllegalState("Session already starting.");
            case TERMINATED:
               throw new SessionIllegalState("Tried to start terminated session.");
            default:
               throw new SessionIllegalState("Tried to start session that was in state " + beforeState);
         }
      }

      RateLimiter limiter;
      if (RATE_LIMIT_ENABLED) {
         limiter = new ExponentialRateLimiter(MESSAGE_QUEUE_SIZE);
      } else {
         limiter = new NullRateLimiter();
      }

      GenericBuffer<QueueableMessage> messageQueue = new RoundRobinBuffer<>(
            MESSAGE_QUEUE_SIZE,
            limiter,
            QueueableMessage::isHlaResponse,
            _sessionLock);
      assert _messageWriter == null;
      _messageWriter = new ClientMessageWriter(MessageHeader.NO_SESSION_ID, messageQueue, _sessionLock);

      _hlaCallbackRequestListener = hlaCallbackRequestListener;

      try {
         _socket = _transport.connect();

         _socketWriter = SocketWriter.createSocketWriterForThread(
               MessageHeader.NO_SESSION_ID,
               getSocketWriterListener(),
               messageQueue,
               _socket,
               true,
               SequenceNumber.INITIAL_SEQUENCE_NUMBER);

         _messageWriter.writeNewSessionMessage(NewSessionMessage.FEDERATE_PROTOCOL_VERSION);
         _socketWriter.writeNextMessage();


         final long connectionTimeoutMillis = TimeUnit.MILLISECONDS.convert(connectionTimeout, connectionTimeoutUnit);
         final TimeoutTimer connectionTimeoutTimer =
               createLazyTimeoutTimer("Client Connection Timeout Timer", connectionTimeoutMillis);
         connectionTimeoutTimer.start(() -> doWhenTimedOut(connectionTimeoutTimer.getTimeoutDurationMillis()));
         try {
            _sessionId = readNewSessionStatus(_socket.getInputStream());
         } finally {
            connectionTimeoutTimer.cancel();
         }

         _messageWriter.setSessionId(_sessionId);
         _socketWriter.setSessionId(_sessionId);

      } catch (IOException | BadMessage | InterruptedException | WebsocketNotConnectedException e) {
         // TODO: According to 12.17.4.7.1, Figure 27 in IEEE1516.2-202x_Feb2023.pdf, an IOException here means we should
         //   retry sending the new session request. Does that make sense...?

         // For resuming we have ResumeStrategy, could we use something similar for connecting a new session?
         compareAndSetState(State.STARTING, State.TERMINATED, e.toString());
         throw new SessionLost(e.getMessage(), e);
      } catch (SessionLost e) {
         compareAndSetState(State.STARTING, State.TERMINATED, e.toString());
         throw e;
      }

      _sessionTimeoutTimer = createLazyTimeoutTimer("Client Session Timeout Timer", _sessionTimeout);
      _sessionTimeoutTimer.start(() -> doWhenTimedOut(_sessionTimeoutTimer.getTimeoutDurationMillis()));

      compareAndSetState(State.STARTING, State.RUNNING);
   }

   private void doWhenTimedOut(long timeoutMillis)
   {
      LOGGER.severe(() -> String.format(
            "%s: No response from server in %d milliseconds, session timing out.",
            LogUtil.logPrefix(_sessionId, "Client"),
            timeoutMillis));
      close(_socket);
   }

   private SocketWriter.Listener getSocketWriterListener()
   {
      return new SocketWriter.Listener() {
         @Override
         public void exceptionOnWrite(String message)
         {
            LOGGER.warning(() -> String.format("%s: Failed to write to socket: %s", logPrefix(), message));
            compareAndSetState(State.RUNNING, State.DROPPED, "Failed to write to socket: " + message);
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
      close(_socket);
   }

   @Override
   public boolean resume()
   throws SessionLost, IOException, SessionIllegalState
   {
      State beforeState = compareAndSetState(State.DROPPED, State.RESUMING);
      if (beforeState != State.DROPPED) {
         throw new SessionIllegalState("Cannot resume a session in state " + beforeState);
      }

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

         _sessionTimeoutTimer.resume();
         ResumeStatusMessage resumeStatusMessage = readResumeStatus(newSocket.getInputStream());

         if (resumeStatusMessage.sessionStatus != ResumeStatusMessage.STATUS_OK_TO_RESUME) {
            throw new SessionLost(
                  "Server refused to resume session. Reason: " +
                        resumeStatusToString(resumeStatusMessage.sessionStatus));
         }

         SequenceNumber resumeFromNumber = new SequenceNumber(resumeStatusMessage.lastReceivedFederateSequenceNumber)
               .increment();

         LOGGER.fine(() -> String.format(
               "%s: Resuming Federate messages from sequence number %d.",
               logPrefix(),
               resumeFromNumber.get()));

         if (newestAvailableFederateMessage != SequenceNumber.NO_SEQUENCE_NUMBER &&
               newestAvailableFederateMessage != resumeStatusMessage.lastReceivedFederateSequenceNumber) {
            _socketWriter.rewindToSequenceNumber(resumeFromNumber);
         }

         _socketWriter.setNewSocket(newSocket);
         _socket = newSocket;

         compareAndSetState(State.RESUMING, State.RUNNING);

         return true;

      } catch (BadMessage | SessionLost | EOFException e) {
         close(newSocket);
         compareAndSetState(State.RESUMING, State.TERMINATED, e.toString());
         throwAsSessionException(e);
         return false; // Will not be reached
      } catch (IOException e) {
         close(newSocket);
         compareAndSetState(State.RESUMING, State.DROPPED, e.toString());
         throw e;
      }
   }

   private String resumeStatusToString(int resumeStatus)
   {
      switch (resumeStatus) {
         case ResumeStatusMessage.STATUS_OK_TO_RESUME:
            return "OK";
         case ResumeStatusMessage.STATUS_FAILURE_INVALID_SESSION:
            return "Invalid Session";
         case ResumeStatusMessage.STATUS_FAILURE_NOT_ENOUGH_BUFFERED_MESSAGES:
            return "Not enough buffered messages";
         default:
         case ResumeStatusMessage.STATUS_FAILURE_OTHER_ERROR:
            return "Unknown error";
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
      return doSessionOperation(() -> _messageWriter.writeHlaCallRequest(
            encodedHlaCall,
            _lastReceivedSequenceNumber.get(),
            _requestFutures));
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
      terminate(((int) _sessionTimeout), TimeUnit.MILLISECONDS);
   }

   @Override
   public void terminate(int responseTimeout, TimeUnit responseTimeoutUnit)
   throws SessionLost, SessionIllegalState
   {
      CompletableFuture<Void> futureResponse = new CompletableFuture<>();
      doSessionOperation(() -> {
         _sessionTerminatedFuture.set(futureResponse);
         try {
            _messageWriter.writeTerminateMessage(_lastReceivedSequenceNumber.get());
         } catch (InterruptedException e) {
            throw new RuntimeException("Client application thread was interrupted while trying to terminate session.");
         }
      });

      try {
         futureResponse.get(responseTimeout, responseTimeoutUnit);
      } catch (InterruptedException e) {
         throw new SessionLost(
               logPrefix() + ": Interrupted while waiting for response to termination request: " + e,
               e);
      } catch (TimeoutException e) {
         throw new SessionLost(
               logPrefix() + ": Timed out while waiting for response to termination request: " + e);
      } catch (CancellationException | ExecutionException e) {
         throw new SessionLost(logPrefix() + ": Failed to complete termination request to server: " + e);
      } finally {
         synchronized (_sessionLock) {
            if (_state == State.RESUMING) {
               try {
                  waitForStates(10, TimeUnit.SECONDS, State.RUNNING, State.DROPPED, State.TERMINATED);
               } catch (InterruptedException ignore) {
               }
               // This covers an ultra-edge case
               compareAndSetState(State.RESUMING, State.TERMINATED);
            }
            String message = "Terminated on request.";
            compareAndSetState(State.RUNNING, State.TERMINATED, message);
            compareAndSetState(State.DROPPED, State.TERMINATED, message);
         }
      }
   }

   private CompletableFuture<byte[]> doSessionOperation(AsyncSessionOperation operation)
   throws SessionIllegalState
   {
      synchronized (_sessionLock) {
         if (_state == State.RUNNING || _state == State.DROPPED || _state == State.RESUMING) {
            return operation.run();
         } else if (_state == State.TERMINATED) {
            throw new SessionAlreadyTerminated(logPrefix() + ": Terminated.");
         } else if (_state == State.NEW || _state == State.STARTING) {
            throw new SessionIllegalState(logPrefix() + ": Not yet established.");
         } else {
            throw new SessionIllegalState(logPrefix() + ": State " + _state + "is unknown.");
         }
      }
   }

   private void doSessionOperation(Runnable operation)
   throws SessionIllegalState
   {
      doSessionOperation(() -> {
         operation.run();
         return new CompletableFuture<>();
      });
   }

   @FunctionalInterface
   private interface AsyncSessionOperation {
      CompletableFuture<byte[]> run();
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
      _socketWriterThread = new Thread(
            _socketWriter::socketWriterLoop,
            "Client Session " + _sessionId + " Writer Thread.");
      _socketWriterThread.setDaemon(true);
      _socketWriterThread.start();
   }

   private void startReaderThread()
   {
      Thread readerThread = new Thread(this::runMessageReaderLoop, "Client Session " + _sessionId + " Reader Thread.");
      readerThread.setDaemon(true);
      readerThread.start();
   }

   private void runMessageReaderLoop()
   {
      try {
         if (_socket == null) {
            // We're still in DROPPED
            throw new EOFException();
         }
         InputStream inputStream = _socket.getInputStream();

         while (true) {
            MessageHeader messageHeader = MessageHeader.decode(inputStream);
            logReceivedMessage(messageHeader.messageType, messageHeader.sequenceNumber);

            if (messageHeader.sessionId != _sessionId) {
               throw new BadMessage(logPrefix() + ": Received wrong session ID " + messageHeader.sessionId);
            }

            switch (messageHeader.messageType) {
               case CTRL_HEARTBEAT_RESPONSE: {
                  HeartbeatResponseMessage message = HeartbeatResponseMessage.decode(inputStream);

                  trackMessageReceived(messageHeader.sequenceNumber);
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
                  trackMessageReceived(messageHeader.sequenceNumber);

                  CompletableFuture<Void> future = _sessionTerminatedFuture.get();
                  if (future != null) {
                     future.completeAsync(() -> null);
                  } else {
                     LOGGER.warning(() -> String.format(
                           "%s: Received unexpected CTRL_SESSION_TERMINATED.",
                           logPrefix()));
                  }

                  // This is the last message we can receive, there's no point on listening for further messages
                  return;
               }

               case HLA_CALL_RESPONSE: {
                  HlaCallResponseMessage message = HlaCallResponseMessage.decode(
                        inputStream,
                        messageHeader.getPayloadSize());

                  trackMessageReceived(messageHeader.sequenceNumber);
                  CompletableFuture<byte[]> future = _requestFutures.remove(message.responseToSequenceNumber);
                  if (future != null) {
                     future.completeAsync(() -> message.hlaServiceReturnValueOrException);
                  } else {
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

                  _hlaCallbackRequestListener.onHlaCallbackRequest(
                        messageHeader.sequenceNumber,
                        message.hlaServiceCallbackWithParams);
                  trackMessageReceived(messageHeader.sequenceNumber);
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
         // Handles the case where server closes the socket while we're trying to terminate the session
         // TODO: This case handles an inconsistency in the server implementation from the standard
         //       The server should not shut down the socket, but leave that to the client.
         //       We should consider removing this when the inconsistency is removed.
         CompletableFuture<Void> future = _sessionTerminatedFuture.get();
         if (future != null) {
            future.completeAsync(() -> null);
            return;
         }
         compareAndSetState(State.RUNNING, State.DROPPED, e.toString());
         LOGGER.info(() -> String.format("%s: Session dropped: %s", logPrefix(), e));
      } catch (IOException e) {
         State oldState = compareAndSetState(State.RUNNING, State.DROPPED, e.toString());
         if (oldState == State.RUNNING) {
            LOGGER.warning(() -> String.format("%s: Failed to read from socket: %s", logPrefix(), e));
         }
      } catch (BadMessage e) {
         // TODO: According to standard, we should terminate the session here.
         compareAndSetState(State.RUNNING, State.DROPPED, e.toString());
         // Should not happen with a correct server
         LOGGER.severe(() -> String.format("%s: Received invalid message! %s", logPrefix(), e));
      } catch (RuntimeException e) {
         compareAndSetState(State.RUNNING, State.DROPPED, e.toString());
         LOGGER.severe(() -> String.format(
               "%s: Got unexpected exception in message reader thread: %s",
               logPrefix(),
               e));
      }
   }

   private void trackMessageReceived(int sequenceNumber)
   {
      if (_sessionTimeoutTimer != null) {
         // _dropSessionTimer may be null during the call to start
         _sessionTimeoutTimer.extend();
      }
      if (SequenceNumber.isValidAsSequenceNumber(sequenceNumber)) {
         SequenceNumber previousReceived = _lastReceivedSequenceNumber.getAndSet(sequenceNumber);
         if (!ALLOW_GAPS_IN_SEQUENCE_NUMBERS && sequenceNumber != previousReceived.getNext()) {
            LOGGER.warning(() -> String.format(
                  "%s: Got unexpected sequence number %d. Last received was %d.",
                  logPrefix(),
                  sequenceNumber,
                  previousReceived.get()));
         }
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

      trackMessageReceived(messageHeader.sequenceNumber);

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
         throw new BadMessage(
               logPrefix() + ": Expected ResumeStatus message, got " + messageHeader.messageType);
      }

      return ResumeStatusMessage.decode(inputStream);
   }

   private void throwAsSessionException(Exception e)
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

   String logPrefix()
   {
      return LogUtil.logPrefix(_sessionId, "Client");
   }
}
