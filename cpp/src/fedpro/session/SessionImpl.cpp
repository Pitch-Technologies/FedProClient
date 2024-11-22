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

#include "SessionImpl.h"

#include "buffers/RoundRobinBuffer.h"
#include <fedpro/EofException.h>
#include <fedpro/FedProExceptions.h>
#include "flowcontrol/ExponentialRateLimiter.h"
#include "flowcontrol/NullRateLimiter.h"
#include "LogUtil.h"
#include "msg/HeartbeatResponseMessage.h"
#include "msg/HlaCallbackRequestMessage.h"
#include "msg/HlaCallResponseMessage.h"
#include "msg/MessageHeader.h"
#include "msg/NewSessionStatusMessage.h"
#include "msg/ResumeStatusMessage.h"
#include "SequenceNumber.h"
#include "TimeoutTimer.h"

#include "spdlog/spdlog.h"

#include <algorithm>
#include <cassert>
#include <chrono>
#include <mutex>

namespace FedPro
{
   static constexpr uint32_t NUM_OF_STATE_LISTENER_THREADS{1};

   SessionImpl::SessionImpl(
         std::unique_ptr<Transport> transportProtocol,
         const Properties & settings) noexcept
         : _sessionMutex{std::make_shared<std::mutex>()},
           _stateCondition{},
           _requestFutures{},
           _sessionTerminatedPromise{},
           _sessionTerminatedFuture{},
           _sessionTimeoutTimer{nullptr},
           _state{State::NEW},
           _transportProtocol{std::move(transportProtocol)},
           _socket{nullptr},
           _sessionId{MessageHeader::NO_SESSION_ID},
           _sessionIdString{LogUtil::formatSessionId(_sessionId)},
           _lastReceivedSequenceNumber{SequenceNumber::NO_SEQUENCE_NUMBER},
           _hlaCallbackRequestListener{[](
                 int32_t,
                 const ByteSequence &) {}},
           _stateListeners{},
           _onMessageSent{[]() { /* No-op */ }},
           _messageWriter{nullptr},
           _socketWriter{nullptr},
           _socketWriterThread{},
           _readerThread{},
           _stateListenerThreadPool{NUM_OF_STATE_LISTENER_THREADS},
           _sessionTimeout{settings.getDuration(SETTING_NAME_RESPONSE_TIMEOUT, DEFAULT_RESPONSE_TIMEOUT_MILLIS)},
           _rateLimitEnabled{settings.getBoolean(SETTING_NAME_RATE_LIMIT_ENABLED, DEFAULT_RATE_LIMIT_ENABLED)},
           _maxRetryConnectAttempts{settings.getUnsignedInt32(
                 SETTING_NAME_CONNECTION_MAX_RETRY_ATTEMPTS, DEFAULT_CONNECTION_MAX_RETRY_ATTEMPTS)},
           _connectionTimeout{settings.getDuration(SETTING_NAME_CONNECTION_TIMEOUT, DEFAULT_CONNECTION_TIMEOUT_MILLIS)},
           _queueSize{settings.getUnsignedInt32(SETTING_NAME_MESSAGE_QUEUE_SIZE, DEFAULT_MESSAGE_QUEUE_SIZE)}

   {
      _stateListenerThreadPool.start();
   }

   SessionImpl::~SessionImpl()
   {
      // If session is in a transitory state, wait until it is in a stable state. The force terminate if necessary.
      State oldState = waitForStableAndSetState(
            {{State::NEW,     State::TERMINATED},
             {State::RUNNING, State::TERMINATED},
             {State::DROPPED, State::TERMINATED}},
            "Terminated ungracefully. Terminate request failed or did not occur at all.");
      _stateCondition.notify_all();

      if (oldState != Session::State::TERMINATED) {
         // This means the destructor was the one to transition the session state to TERMINATED.
         stopAndWaitForWriterThread();
         waitForReaderThread();
      }

      // After terminating, stop pools and process remaining jobs to inform listeners about state changes.
      _stateListenerThreadPool.stop(true);
   }

   uint64_t SessionImpl::getId() const noexcept
   {
      return _sessionId;
   }

   SessionImpl::State SessionImpl::getState() const noexcept
   {
      std::lock_guard<std::mutex> lock(*_sessionMutex);
      return _state;
   }

   void SessionImpl::addStateListener(Session::StateListener onStateTransition)
   {
      if (!onStateTransition) {
         throw std::invalid_argument{logPrefix() + ": The passed StateListener is null."};
      }
      _stateListeners.push_back(onStateTransition);
   }

   void SessionImpl::setMessageSentListener(Session::MessageSentListener onMessageSent)
   {
      if (!onMessageSent) {
         throw std::invalid_argument{logPrefix() + ": The passed MessageSentListener is null."};
      }
      _onMessageSent = onMessageSent;
   }

   void SessionImpl::start(Session::HlaCallbackRequestListener onHlaCallbackRequest)
   {
      start(onHlaCallbackRequest, _connectionTimeout);
   }

   void SessionImpl::start(
         Session::HlaCallbackRequestListener onHlaCallbackRequest,
         FedProDuration connectionTimeout)
   {

      if (!onHlaCallbackRequest) {
         throw std::invalid_argument{logPrefix() + ": HlaCallbackRequestListener is null."};
      }

      State beforeState = compareAndSetState(State::NEW, State::STARTING);
      if (beforeState == State::TERMINATED) {
         throw SessionAlreadyTerminated{"Cannot start a terminated session"};
      } else if (beforeState != State::NEW) {
         throw SessionIllegalState{"Cannot start a session in state " + stateToString(beforeState)};
      }
      // Now that the session state is STARTING, this thread will have exclusive right to the session. This is
      // guaranteed since the public methods that attempt to alter the session will throw in the wrong state.

      // Initialize necessary components

      std::shared_ptr<RateLimiter> limiter;
      if (_rateLimitEnabled) {
         limiter = std::make_shared<ExponentialRateLimiter>(_queueSize);
      } else {
         limiter = std::make_shared<NullRateLimiter>();
      }

      std::shared_ptr<RoundRobinBuffer<QueueableMessage>>
            messageQueue = std::make_shared<RoundRobinBuffer<QueueableMessage>>(
            limiter,
            [](const QueueableMessage & message) { return message.isHlaResponse(); },
            _sessionMutex,
            _queueSize);

      _messageWriter = std::make_unique<MessageWriter>(_sessionId, messageQueue);

      _hlaCallbackRequestListener = onHlaCallbackRequest;

      std::unique_ptr<TimeoutTimer> connectionTimeoutTimer = TimeoutTimer::createLazyTimeoutTimer(connectionTimeout);
      try {
         _socket = connect();
         if (!_socket) {
            throw std::ios_base::failure{"Transport could not connect"};
         }

         int32_t firstSocketWriterSequenceNumber = FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER + 1;
         // The first sequence number that socket writer will expect is INITIAL_SEQUENCE_NUMBER + 1 as the first
         // session message (CTRL_NEW_SESSION) will be sent directly to the socket and not stored in the message history

         _socketWriter = SocketWriter::createSocketWriter(
               _sessionId,
               std::make_unique<SocketWriterListener>(this),
               std::make_unique<HistoryBuffer>(
                     _sessionMutex, messageQueue, firstSocketWriterSequenceNumber),
               _socket,
               firstSocketWriterSequenceNumber);

         messageQueue.reset();

         _socketWriter->writeDirectMessage(MessageWriter::createNewSessionMessage());

         connectionTimeoutTimer->start(
               [&connectionTimeoutTimer, this]() {
                  doWhenTimedOut(connectionTimeoutTimer->getTimeoutDuration());
               });
         _sessionId = readNewSessionStatus();
         _sessionIdString = LogUtil::formatSessionId(_sessionId);
         connectionTimeoutTimer->cancel();

         _messageWriter->setSessionId(_sessionId);
         _socketWriter->setSessionId(_sessionId);

      } catch (const std::exception & e) {
         connectionTimeoutTimer->cancel();
         closeSocket();
         compareAndSetState(State::STARTING, State::TERMINATED, e.what());
         throw SessionLost{e.what()};
      }

      _sessionTimeoutTimer = TimeoutTimer::createLazyTimeoutTimer(_sessionTimeout);
      _sessionTimeoutTimer->start(
            [this]() {
               doWhenTimedOut(_sessionTimeoutTimer->getTimeoutDuration());
            });

      // Starting the these threads must be done in a lock free state
      startWriterThread();
      startReaderThread();

      compareAndSetState(State::STARTING, State::RUNNING);
   }

   bool SessionImpl::resume()
   {
      State beforeState = compareAndSetState(State::DROPPED, State::RESUMING);
      if (beforeState == State::TERMINATED) {
         throw SessionAlreadyTerminated{"Cannot resume a terminated session"};
      } else if (beforeState != State::DROPPED) {
         throw SessionIllegalState{"Cannot resume a session in state " + stateToString(beforeState)};
      }
      // Now that the session state is RESUMING, this thread will have exclusive right to the session. This is
      // guaranteed since the public methods that attempt to alter the session will throw in the wrong state.

      stopAndWaitForWriterThread();
      waitForReaderThread();

      SPDLOG_DEBUG("{}: Trying to resume.", logPrefix());

      // In all cases a CTRL_NEW_SESSION_STATUS message will have always been received by the client at this point.
      // Therefore, it is risk-free that _lastReceivedSequenceNumber is initialized to INITIAL_SEQUENCE_NUMBER.
      // Oldest federate message always has a valid sequence number since CTRL_NEW_SESSION will have always been sent at this point.
      const int32_t lastReceivedRtiMessage = _lastReceivedSequenceNumber.get(); // LRR
      const int32_t oldestAvailableFederateMessage = _socketWriter->getOldestAddedSequenceNumber(); // OAF
      const int32_t newestAvailableFederateMessage = _socketWriter->getNewestAddedSequenceNumber();

      SPDLOG_DEBUG("{}: Oldest available Federate message {}. Last received RTI message is {}.",
                   logPrefix(),
                   oldestAvailableFederateMessage,
                   lastReceivedRtiMessage);

      std::shared_ptr<Socket> newSocket = nullptr;
      try {
         newSocket = connect();

         std::unique_ptr<SocketWriter> directSocketWriter = SocketWriter::createDirectSocketWriter(
               _sessionId, newSocket);
         EncodedMessage resumeSessionMessage = MessageWriter::createResumeSessionMessage(
               _sessionId, lastReceivedRtiMessage, oldestAvailableFederateMessage);
         directSocketWriter->writeDirectMessage(resumeSessionMessage);

         if (_sessionTimeoutTimer) {
            _sessionTimeoutTimer->resume();
         }
         ResumeStatusMessage resumeStatusMessage = readResumeStatus(newSocket);

         if (resumeStatusMessage.sessionStatus != ResumeStatusMessage::STATUS_OK_TO_RESUME) {
            throw SessionLost{"Server refused to resume session. Reason: " +
                              ResumeStatusMessage::toString(resumeStatusMessage.sessionStatus)};
         }

         SPDLOG_DEBUG("{}: Resuming Federate messages from sequence number {}.",
                      logPrefix(),
                      SequenceNumber::nextAfter(resumeStatusMessage.lastReceivedFederateSequenceNumber));

         if (!SequenceNumber::isValidAsSequenceNumber(resumeStatusMessage.lastReceivedFederateSequenceNumber)) {
            _socketWriter->rewindToFirstMessage();
         } else if (SequenceNumber::isValidAsSequenceNumber(newestAvailableFederateMessage) &&
                    newestAvailableFederateMessage != resumeStatusMessage.lastReceivedFederateSequenceNumber) {
            _socketWriter->rewindToSequenceNumberAfter(
                  SequenceNumber{resumeStatusMessage.lastReceivedFederateSequenceNumber});
         }

         _socket = newSocket;
         _socketWriter->setNewSocket(newSocket);

         // Starting the these threads must be done in a lock free state.
         startWriterThread();
         startReaderThread();

         compareAndSetState(State::RESUMING, State::RUNNING);
         _stateCondition.notify_all();
         return true;

      } catch (const BadMessage & e) {
         terminateResumingSession(newSocket, e.what());
         rethrowAsSessionException();
      } catch (const SessionLost & e) {
         terminateResumingSession(newSocket, e.what());
         rethrowAsSessionException();
      } catch (const std::ios_base::failure & e) {
         _socketWriter->terminateCurrentWriterThread();
         close(newSocket);
         compareAndSetState(State::RESUMING, State::DROPPED, e.what());
         _stateCondition.notify_all();
         throw;
      }
      return false;
   }

   std::future<ByteSequence> SessionImpl::sendHeartbeat()
   {
      return doAsyncSessionOperation(
            [this]() -> std::future<ByteSequence> {
               return _messageWriter->writeHeartbeatMessage(_lastReceivedSequenceNumber.get(), &_requestFutures);
            });
   }

   std::future<ByteSequence> SessionImpl::sendHlaCallRequest(const ByteSequence & encodedHlaCall)
   {
      return doAsyncSessionOperation(
            [this, encodedHlaCall]() -> std::future<ByteSequence> {
               return _messageWriter->writeHlaCallRequest(
                     encodedHlaCall, _lastReceivedSequenceNumber.get(), &_requestFutures);
            });
   }

   void SessionImpl::sendHlaCallbackResponse(
         int32_t responseToSequenceNumber,
         const ByteSequence & hlaCallbackResponse)
   {
      doSessionOperation(
            [this, responseToSequenceNumber, hlaCallbackResponse]() {
               _messageWriter->writeHlaCallbackResponse(
                     responseToSequenceNumber, hlaCallbackResponse, _lastReceivedSequenceNumber.get());
            });
   }

   void SessionImpl::terminate()
   {
      terminate(_sessionTimeout);
   }

   void SessionImpl::terminate(FedProDuration responseTimeout)
   {
      Session::State stateBeforeTerminating;
      {
         std::unique_lock<std::mutex> lock(*_sessionMutex);
         lockFreeDoAsyncSessionOperation(
               [this, &lock, &stateBeforeTerminating]() -> std::future<ByteSequence> {
                  while (_state == State::RESUMING) {
                     _stateCondition.wait(lock);
                  }
                  stateBeforeTerminating = _state;
                  lockFreeCompareAndSetState(State::RUNNING, State::TERMINATING);
                  lockFreeCompareAndSetState(State::DROPPED, State::TERMINATING);
                  return {};
               });
         // Henceforth, the session state is TERMINATING and so this thread has exclusive right to the session. This is
         // guaranteed since the public methods that attempt to alter the session will throw in the wrong state.
      }
      if (getState() == State::TERMINATED) {
         // This means that the session terminated non-gracefully. Server has not been notified that session terminated.
         throw SessionLost{logPrefix() + "Terminated while trying to resume session"};
      }

      if (stateBeforeTerminating == Session::State::DROPPED) {
         // Don't try to resume a session just to terminate it.
         stopThreadsInTransitoryState();
         compareAndSetState(State::TERMINATING, State::TERMINATED, "Terminated dropped session without resuming.");
         return;
      }

      _sessionTerminatedFuture = _sessionTerminatedPromise.get_future();
      _messageWriter->writeTerminateMessage(_lastReceivedSequenceNumber.get());
      std::future_status status = _sessionTerminatedFuture.wait_for(responseTimeout);

      stopThreadsInTransitoryState();
      compareAndSetState(State::TERMINATING, State::TERMINATED, "Terminated on request.");

      if (status == std::future_status::timeout) {
         throw SessionLost{logPrefix() + ": Timed out while waiting for response to termination request."};
      } else if (status != std::future_status::ready) {
         throw SessionLost{logPrefix() + ": Failed to complete termination request to server."};
      }
   }

   void SessionImpl::forceCloseConnection() noexcept
   {
      closeSocket();
      SPDLOG_TRACE("{}: Socket forcefully closed by client.", logPrefix());
   }

   Properties SessionImpl::getSettings() const noexcept
   {
      Properties settings;
      settings.setDuration(SETTING_NAME_RESPONSE_TIMEOUT, _sessionTimeout);
      settings.setUnsignedInt32(SETTING_NAME_CONNECTION_MAX_RETRY_ATTEMPTS, _maxRetryConnectAttempts);
      settings.setDuration(SETTING_NAME_CONNECTION_TIMEOUT, _connectionTimeout);
      settings.setBoolean(SETTING_NAME_RATE_LIMIT_ENABLED, _rateLimitEnabled);
      settings.setUnsignedInt32(SETTING_NAME_MESSAGE_QUEUE_SIZE, _queueSize);
      return settings;
   }

   std::string SessionImpl::logPrefix() const noexcept
   {
      return LogUtil::logPrefix(_sessionIdString, LogUtil::CLIENT_PREFIX);
   }

   // Private methods

   std::string SessionImpl::stateToString(State state) noexcept
   {
      switch (state) {
         case State::NEW:
            return "NEW";
         case State::STARTING:
            return "STARTING";
         case State::RUNNING:
            return "RUNNING";
         case State::DROPPED:
            return "DROPPED";
         case State::RESUMING:
            return "RESUMING";
         case State::TERMINATING:
            return "TERMINATING";
         case State::TERMINATED:
            return "TERMINATED";
      }
      return "UNKNOWN_STATE";
   }

   void SessionImpl::waitForState(State state) noexcept
   {
      std::unique_lock<std::mutex> sessionLock(*_sessionMutex);
      lockFreeWaitForState(state, sessionLock);
   }

   void SessionImpl::lockFreeWaitForState(
         State state,
         std::unique_lock<std::mutex> & sessionLock) noexcept
   {
      while (_state != state) {
         _stateCondition.wait(sessionLock);
      }
   }

   Session::State SessionImpl::waitForState(
         State state,
         FedProDuration timeout) noexcept
   {
      Clock::time_point endTime = Clock::now() + timeout;
      std::unique_lock<std::mutex> sessionLock(*_sessionMutex);

      while (_state != state && Clock::now() < endTime) {
         Clock::time_point now = Clock::now();
         FedProDuration timeLeft = std::chrono::duration_cast<FedProDuration>(endTime - now);
         _stateCondition.wait_for(sessionLock, timeLeft);
      }
      return _state;
   }

   Session::State SessionImpl::waitForStates(
         std::initializer_list<State> anticipatedStates) noexcept
   {
      std::unique_lock<std::mutex> lock(*_sessionMutex);
      return lockFreeWaitForStates(anticipatedStates, lock);
   }

   Session::State SessionImpl::lockFreeWaitForStates(
         std::initializer_list<State> anticipatedStates,
         std::unique_lock<std::mutex> & lock) noexcept
   {
      while (std::find(anticipatedStates.begin(), anticipatedStates.end(), _state) == anticipatedStates.end()) {
         _stateCondition.wait(lock);
      }
      return _state;
   }

   Session::State SessionImpl::waitForStates(
         FedProDuration timeout,
         std::initializer_list<State> anticipatedStates) noexcept
   {
      std::unique_lock<std::mutex> lock(*_sessionMutex);

      Clock::time_point endTime = Clock::now() + timeout;

      while (std::find(anticipatedStates.begin(), anticipatedStates.end(), _state) == anticipatedStates.end() &&
             Clock::now() < endTime) {
         Clock::time_point now = Clock::now();
         FedProDuration timeLeft = std::chrono::duration_cast<FedProDuration>(endTime - now);
         _stateCondition.wait_for(lock, timeLeft);
      }
      return _state;
   }

   void SessionImpl::stopThreadsInTransitoryState() noexcept
   {
      if (inStableState()) {
         SPDLOG_ERROR("{}: Failed to stop reader and writer threads. Can only be stopped in transitory states.",
                      logPrefix());
         return;
      }
      stopAndWaitForWriterThread();
      waitForReaderThread();
   }

   bool SessionImpl::inStableState() noexcept
   {
      std::lock_guard<std::mutex> lock(*_sessionMutex);
      return _state == State::NEW || _state == State::RUNNING || _state == State::DROPPED ||
             _state == State::TERMINATED;
   }

   Session::State SessionImpl::waitForStableAndSetState(
         const std::unordered_map<State, State> & transitions,
         const std::string & reason) noexcept
   {
      std::unique_lock<std::mutex> lock(*_sessionMutex);
      lockFreeWaitForStates({State::NEW, State::RUNNING, State::DROPPED, State::TERMINATED}, lock);
      return lockFreeCompareAndSetState(transitions, reason);
   }

   Session::State SessionImpl::compareAndSetState(
         const std::unordered_map<State, State> & transitions,
         const std::string & reason) noexcept
   {
      std::unique_lock<std::mutex> lock(*_sessionMutex);
      return lockFreeCompareAndSetState(transitions, reason);
   }

   Session::State SessionImpl::lockFreeCompareAndSetState(
         const std::unordered_map<State, State> & transitions,
         const std::string & reason) noexcept
   {
      State oldState = _state;
      for (const auto & entry : transitions) {
         lockFreeCompareAndSetState(entry.first, entry.second, reason);
         if (oldState == entry.first) {
            return oldState;
         }
      }
      return oldState;
   }

   Session::State SessionImpl::compareAndSetState(
         State expectedState,
         State newState) noexcept
   {
      std::lock_guard<std::mutex> lock(*_sessionMutex);
      return lockFreeCompareAndSetState(expectedState, newState, "");
   }

   Session::State SessionImpl::lockFreeCompareAndSetState(
         State expectedState,
         State newState) noexcept
   {
      return lockFreeCompareAndSetState(expectedState, newState, "");
   }

   Session::State SessionImpl::compareAndSetState(
         State expectedState,
         State newState,
         const std::string & reason) noexcept
   {
      std::lock_guard<std::mutex> lock(*_sessionMutex);
      return lockFreeCompareAndSetState(expectedState, newState, reason);
   }

   Session::State SessionImpl::lockFreeCompareAndSetState(
         State expectedState,
         State newState,
         const std::string & reason) noexcept
   {
      State oldState = _state;
      if (_state == expectedState) {
         SPDLOG_DEBUG("{}: {} -> {}.", logPrefix(), stateToString(oldState), stateToString(newState));
         _state = newState;
         if (oldState != newState) {
            transitionSessionState(oldState, newState);

            // Could be that the resume strategy is executed, so the session mutex must be locked here.
            fireOnStateTransition(oldState, newState, reason);
         }
         _stateCondition.notify_all();
      }
      return oldState;
   }

   // GuardedBy _sessionMutex
   void SessionImpl::transitionSessionState(
         State oldState,
         State newState) noexcept
   {
      assert(oldState != newState);

      switch (oldState) {
         case State::NEW: {
            if (newState == State::STARTING) {
               // Handled in start().
            } else if (newState == State::TERMINATED) {
               transitionToTerminated();
            } else {
               assert(false && "Illegal state transition.");
            }
            break;
         }
         case State::STARTING: {
            if (newState == State::RUNNING) {
               // Handled in start().
            } else if (newState == State::TERMINATED) {
               transitionToTerminated();
            } else {
               assert(false && "Illegal state transition.");
            }
            break;
         }
         case State::RUNNING: {
            if (newState == State::DROPPED) {
               if (_sessionTimeoutTimer) {
                  _sessionTimeoutTimer->pause();
               }
            } else if (newState == State::TERMINATING) {
               // handled in terminate().
            } else if (newState == State::TERMINATED) {
               transitionToTerminated();
            } else {
               assert(false && "Illegal state transition.");
            }
            break;
         }
         case State::DROPPED: {
            if (newState == State::RESUMING) {
               // Handled in resume().
            } else if (newState == State::TERMINATING) {
               // handled in terminate().
            } else if (newState == State::TERMINATED) {
               transitionToTerminated();
            } else {
               assert(false && "Illegal state transition.");
            }
            break;
         }
         case State::RESUMING: {
            if (newState == State::RUNNING) {
               // Handled in start() or resume().
            } else if (newState == State::TERMINATED) {
               transitionToTerminated();
            } else if (newState == State::DROPPED) {
               if (_sessionTimeoutTimer) {
                  _sessionTimeoutTimer->pause();
               }
            } else {
               assert(false && "Illegal state transition.");
            }
            break;
         }
         case State::TERMINATING: {
            if (newState == State::TERMINATED) {
               transitionToTerminated();
            } else {
               assert(false && "Illegal state transition.");
            }
            break;
         }
         case State::TERMINATED: {
            assert(false && "Illegal state transition.");
         }
      }
   }

   void SessionImpl::terminateResumingSession(
         std::shared_ptr<Socket> & newSocket,
         const std::string & reason)
   {
      close(newSocket);
      stopThreadsInTransitoryState();
      compareAndSetState(State::RESUMING, State::TERMINATED, reason);
      _stateCondition.notify_all();
   }

   void SessionImpl::transitionToTerminated() noexcept
   {
      if (_sessionTimeoutTimer) {
         _sessionTimeoutTimer->cancel();
      }
      closeSocket();
      if (_messageWriter) {
         _messageWriter->disableMessageQueueInsertion();
      }
      failAllFuturesWhenTerminating();
   }

   void SessionImpl::failAllFuturesWhenTerminating() noexcept
   {
      std::unordered_map<int32_t, std::promise<ByteSequence>> futuresToFail{};
      _requestFutures.swap(futuresToFail);
      for (auto & pair : futuresToFail) {
         failFutureWhenTerminating(pair.second);
      }

      // If not, then the terminate-future is already completed and there is no point to complete it exceptionally.
      if (!_sessionTerminatedFuture.valid()) {
         failFutureWhenTerminating(_sessionTerminatedPromise);
      }
   }

   void SessionImpl::failFutureWhenTerminating(std::promise<ByteSequence> & promise) const noexcept
   {
      try {
         promise.set_exception(
               std::make_exception_ptr(
                     SessionLost{"Session terminated before request could complete."}));
      } catch (const std::exception & e) {
         SPDLOG_DEBUG("{}: Unexpected exception in HLA call completion: {}", logPrefix(), e.what());
      }
   }

   void SessionImpl::fireOnStateTransition(
         State oldState,
         State newState,
         const std::string & reason) noexcept
   {
      for (const StateListener & listener : _stateListeners) {
         _stateListenerThreadPool.queueJob(
               [listener, oldState, newState, reason]() {
                  listener(oldState, newState, reason);
               });
      }
   }

   void SessionImpl::doWhenTimedOut(FedProDuration timeout) noexcept
   {
      SPDLOG_ERROR("{}: No response from server in {} milliseconds, session timing out.", logPrefix(), timeout.count());
      closeSocket();
   }

   std::shared_ptr<Socket> SessionImpl::connect()
   {
      uint32_t attempt{0};
      while (true) {
         try {
            return _transportProtocol->connect();
         } catch (const std::ios_base::failure & lastException) {
            // Probably caused by port starvation. Wait a bit and try again.
            if (attempt < _maxRetryConnectAttempts) {
               try {
                  std::this_thread::sleep_for(std::chrono::milliseconds(300));
               } catch (const std::exception &) {
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

   void SessionImpl::doSessionOperation(const SessionOperation & operation) // throws SessionIllegalState
   {
      doAsyncSessionOperation(
            [operation]() -> std::future<ByteSequence> {
               operation();
               return {};
            });
   }

   std::future<ByteSequence> SessionImpl::doAsyncSessionOperation(const AsyncSessionOperation & operation)
   // throws SessionIllegalState
   {
      std::unique_lock<std::mutex> sessionLock(*_sessionMutex);
      return lockFreeDoAsyncSessionOperation(operation);
   }

   std::future<ByteSequence> SessionImpl::lockFreeDoAsyncSessionOperation(const AsyncSessionOperation & operation)
   // throws SessionIllegalState
   {
      if (_state == State::RUNNING || _state == State::DROPPED || _state == State::RESUMING) {
         return operation();
      } else if (_state == State::TERMINATING) {
         throw SessionIllegalState{logPrefix() + ": Terminate call in progress."};
      } else if (_state == State::TERMINATED) {
         throw SessionAlreadyTerminated{logPrefix() + ": Terminated."};
      } else if (_state == State::NEW || _state == State::STARTING) {
         throw SessionIllegalState{logPrefix() + ": Not yet established."};
      } else {
         throw SessionIllegalState{logPrefix() + ": State " + stateToString(_state) + "is unknown."};
      }
   }

   void SessionImpl::closeSocket() noexcept
   {
      close(_socket);
   }

   void SessionImpl::close(std::shared_ptr<Socket> & socket) const noexcept
   {
      if (socket) {
         try {
            socket->close();
         } catch (...) {
            SPDLOG_DEBUG("{}: Failed to close socket.", logPrefix());
         }
      }
      socket.reset();
   }

   void SessionImpl::startWriterThread() noexcept
   {
      if (inStableState()) {
         SPDLOG_ERROR("{}: Only allowed to start the socket writer thread in state STARTING or RESUMING", logPrefix());
         return;
      }
      _socketWriterThread = std::thread{[this]() { _socketWriter->socketWriterLoop(); }};
   }

   void SessionImpl::startReaderThread() noexcept
   {
      if (inStableState()) {
         SPDLOG_ERROR("{}: Only allowed to start the socket reader thread in state STARTING or RESUMING", logPrefix());
         return;
      }
      _readerThread = std::thread{[this]() {
         runMessageReaderLoop();
      }};
   }

   void SessionImpl::stopAndWaitForWriterThread() noexcept
   {
      if (_socketWriter) {
         _socketWriter->terminateCurrentWriterThread();
      }
      if (_socketWriterThread.joinable()) {
         try {
            _socketWriterThread.join();
         } catch (const std::system_error &) {
         }
      }
   }

   void SessionImpl::waitForReaderThread() noexcept
   {
      if (_readerThread.joinable()) {
         try {
            _readerThread.join();
         } catch (const std::system_error &) {
         }
      }
   }

   void SessionImpl::runMessageReaderLoop() noexcept
   {
      try {
         if (!_socket) {
            throw EofException{logPrefix() + "Failed to run message reader for session " + _sessionIdString +
                               " since it is dropped"};
         }

         while (true) {

            ByteSequence headerBuffer{_socket->recvNBytes(MessageHeader::SIZE)};
            MessageHeader messageHeader = MessageHeader::decode(headerBuffer);

            logReceivedMessage(messageHeader.messageType, messageHeader.sequenceNumber);

            if (messageHeader.sessionId != _sessionId) {
               throw BadMessage{
                     logPrefix() + ": Received wrong session ID " + LogUtil::formatSessionId(messageHeader.sessionId)};
            }
            switch (messageHeader.messageType) {
               case MessageType::CTRL_HEARTBEAT_RESPONSE: {
                  HeartbeatResponseMessage message = HeartbeatResponseMessage::decode(*_socket);

                  extendSessionTimer();
                  std::unique_ptr<std::promise<ByteSequence>>
                        promise = _requestFutures.remove(message.responseToSequenceNumber);
                  if (promise) {
                     promise->set_value(ByteSequence{});
                     // It is ok for the promise to be deallocated here, the corresponding future will be able to
                     // get the value as long as set_value() is called before de-allocation.
                  } else {
                     SPDLOG_WARN("{}: Received unexpected heartbeat response to sequence number {}.",
                                 logPrefix(),
                                 message.responseToSequenceNumber);
                  }
                  break;
               }

               case MessageType::CTRL_SESSION_TERMINATED: {
                  extendSessionTimer();
                  State currentState = getState();
                  if (currentState != State::TERMINATING && currentState != State::TERMINATED) {
                     SPDLOG_WARN("{}: Received unexpected CTRL_SESSION_TERMINATED.", logPrefix());
                  }
                  _sessionTerminatedPromise.set_value("Server acknowledged session termination gracefully");
                  // This is the last message we can receive, there's no point in listening for further messages.
                  return;
               }

               case MessageType::HLA_CALL_RESPONSE: {
                  HlaCallResponseMessage message = HlaCallResponseMessage::decode(
                        *_socket, messageHeader.getPayloadSize());

                  trackHlaMessageReceived(messageHeader.sequenceNumber);
                  std::unique_ptr<std::promise<ByteSequence>>
                        promise = _requestFutures.remove(message.responseToSequenceNumber);
                  if (promise) {
                     promise->set_value(message.hlaServiceReturnValueOrException);
                     // It is ok for the promise to be deallocated here, the corresponding future will be able to
                     // get the value as long as set_value() is called before de-allocation.
                  } else {
                     SPDLOG_WARN("{}: Received unexpected HLA call response to sequence number {}.",
                                 logPrefix(),
                                 message.responseToSequenceNumber);
                     return;
                  }
                  break;
               }

               case MessageType::HLA_CALLBACK_REQUEST: {
                  HlaCallbackRequestMessage message = HlaCallbackRequestMessage::decode(
                        *_socket, messageHeader.getPayloadSize());
                  // The _hlaCallbackRequestListener may call back into `this` to send a CallbackResponse
                  // The _hlaCallbackRequestListener may block if the callback buffer becomes full

                  _hlaCallbackRequestListener(
                        messageHeader.sequenceNumber, message.hlaServiceCallbackWithParams);
                  trackHlaMessageReceived(messageHeader.sequenceNumber);
                  break;
               }

               case MessageType::CTRL_RESUME_REQUEST:
               case MessageType::CTRL_RESUME_STATUS:
               case MessageType::HLA_CALL_REQUEST:
               case MessageType::CTRL_NEW_SESSION:
               case MessageType::CTRL_NEW_SESSION_STATUS:
               case MessageType::CTRL_HEARTBEAT:
               case MessageType::CTRL_TERMINATE_SESSION:
               case MessageType::HLA_CALLBACK_RESPONSE:
               case MessageType::UNKNOWN:
                  throw BadMessage{logPrefix() + ": Invalid MessageType received " +
                                   MessageTypeHelper::asString(messageHeader.messageType)};
            }
         }
      } catch (const EofException & e) {
         // Handles the case where the server closes the socket while we're trying to terminate the session
         // TODO (from the java code): This case handles an inconsistency in the server implementation from the standard
         //  The server should not shut down the socket, but leave that to the client.
         //  We should consider removing this when the inconsistency is removed.
         if (getState() == State::TERMINATING) {
            _sessionTerminatedPromise.set_value("Session socket closed");
            return;
         }
         dropSession(e);
         SPDLOG_INFO("{}: Failed to read from socket: {}", logPrefix(), e.what());
      } catch (const std::ios_base::failure & e) {
         State oldState = dropSession(e);
         if (oldState == State::RUNNING || oldState == State::TERMINATING) {
            SPDLOG_WARN("{}: Failed to read from socket: {}", logPrefix(), e.what());
         }
      } catch (const BadMessage & e) {
         // TODO (from the java code): According to standard, we should terminate the session here.
         dropSession(e);
         // Should not happen with a correct server
         SPDLOG_ERROR("{}: Received an invalid message! {}", logPrefix(), e.what());
      } catch (const std::runtime_error & e) {
         dropSession(e);
         SPDLOG_ERROR("{}: Got an unexpected exception in the message reader thread: {}", logPrefix(), e.what());
      }
   }

   Session::State SessionImpl::dropSession(const std::exception & e) noexcept
   {
      if (_socketWriter) {
         _socketWriter->terminateCurrentWriterThread();
      }
      closeSocket();
      return compareAndSetState(State::RUNNING, State::DROPPED, e.what());
   }

   void SessionImpl::trackHlaMessageReceived(int32_t sequenceNumber) noexcept
   {
      extendSessionTimer();
      if (SequenceNumber::isValidAsSequenceNumber(sequenceNumber)) {
         _lastReceivedSequenceNumber.getAndSet(sequenceNumber);
      } else {
         SPDLOG_WARN("{}: Received an invalid seq.nr. {}.", logPrefix(), LogUtil::padNumString(sequenceNumber));
      }
   }

   void SessionImpl::extendSessionTimer() noexcept
   {
      if (_sessionTimeoutTimer) {
         // This timer may be null during the call to start.
         _sessionTimeoutTimer->extend();
      }
   }

   uint64_t SessionImpl::readNewSessionStatus() // throws std::ios_base::failure, BadMessage, SessionLost
   {
      ByteSequence headerBuffer{_socket->recvNBytes(MessageHeader::SIZE)};
      MessageHeader messageHeader = MessageHeader::decode(headerBuffer);
      logReceivedMessage(messageHeader.messageType, messageHeader.sequenceNumber);

      if (messageHeader.messageType != MessageType::CTRL_NEW_SESSION_STATUS) {
         throw BadMessage(
               "Expected NewSessionStatus message, got " + MessageTypeHelper::asString(messageHeader.messageType) +
               ".");
      }

      NewSessionStatusMessage message = NewSessionStatusMessage::decode(*_socket);
      extendSessionTimer();

      if (message.reason != NewSessionStatusMessage::REASON_SUCCESS) {
         throw SessionLost("Server unable to create session, reason " + std::to_string(message.reason));
      }

      return messageHeader.sessionId;
   }

   ResumeStatusMessage SessionImpl::readResumeStatus(const std::shared_ptr<Socket> & newSocket)
   // throws std::ios_base::failure, BadMessage
   {
      ByteSequence headerBuffer{newSocket->recvNBytes(MessageHeader::SIZE)};
      MessageHeader messageHeader = MessageHeader::decode(headerBuffer);

      if (_sessionTimeoutTimer) {
         _sessionTimeoutTimer->extend();
      }
      logReceivedMessage(messageHeader.messageType, messageHeader.sequenceNumber);

      if (messageHeader.messageType != MessageType::CTRL_RESUME_STATUS) {
         throw BadMessage{logPrefix() + ": Expected ResumeStatus message, got " +
                          MessageTypeHelper::asString(messageHeader.messageType) + "."};
      }
      return ResumeStatusMessage::decode(*newSocket);
   }

   void SessionImpl::rethrowAsSessionException() // throws SessionLost
   {
      try {
         // Rethrow the exception being handled by the caller.
         throw;
      } catch (const SessionLost & ) {
         throw;
      } catch (const std::exception & otherException) {
         throw SessionLost{otherException.what()};
      } catch (...) {
         // Should not get here.
         throw SessionLost{"Unknown exception"};
      }
   }

   void SessionImpl::logReceivedMessage(
         MessageType messageType,
         int32_t sequenceNumber) const noexcept
   {
      SPDLOG_DEBUG("{}: Received seq.nr. {}, type {}.",
                   logPrefix(),
                   LogUtil::padNumString(sequenceNumber),
                   MessageTypeHelper::asString(messageType));
   }

   SocketWriterListener::SocketWriterListener(SessionImpl * session)
         : _session{session}
   {
   }

   void SocketWriterListener::exceptionOnWrite(const std::exception & e)
   {
      SPDLOG_WARN("{}: Failed to write to socket: {}", _session->logPrefix(), e.what());
      _session->compareAndSetState(
            Session::State::RUNNING, Session::State::DROPPED, "Failed to write to socket: " + std::string(e.what()));
   }

   void SocketWriterListener::messageSent()
   {
      _session->_onMessageSent();
   }

   // Export forceCloseConnection for tests, but do not expose in public headers.
   FEDPRO_EXPORT void forceCloseConnection(Session & session)
   {
      auto * sessionImpl = dynamic_cast<SessionImpl *>(&session);
      if (sessionImpl) {
         sessionImpl->forceCloseConnection();
      } else {
         throw std::invalid_argument{"Unexpected Session class type"};
      }
   }

} // FedPro
