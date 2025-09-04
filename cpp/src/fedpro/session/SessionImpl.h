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

#pragma once

#include <fedpro/Config.h>
#include <fedpro/Properties.h>
#include <fedpro/Session.h>
#include <fedpro/Socket.h>
#include <fedpro/Transport.h>

#include "AtomicSequenceNumber.h"
#include "buffers/RoundRobinBuffer.h"
#include "ConcurrentHashMap.h"
#include "MessageWriter.h"
#include "msg/ResumeStatusMessage.h"
#include "SessionSettings.h"
#include "SocketWriter.h"
#include "ThreadPool.h"
#include "TimeoutTimer.h"
#include <utility/MovingStats.h>

#include <atomic>
#include <cstdint>
#include <future>
#include <memory>
#include <vector>

namespace FedPro
{
   class SessionImpl : public Session
   {
   public:

      friend class SocketWriterListener;

      using Clock = std::chrono::steady_clock;

      explicit SessionImpl(
            std::unique_ptr<Transport> transportProtocol,
            const Properties & settings) noexcept;

      ~SessionImpl() override;

      void requestListenerExecutorShutdown();

      uint64_t getId() const noexcept override;

      State getState() const noexcept override;

      void addStateListener(StateListener onStateTransition) override;

      void waitStateListeners() noexcept(false) override;

      void setMessageSentListener(MessageSentListener onMessageSent) override;

      void prettyPrintPerSecondStats(std::ostream & stream) override;

      void prettyPrintStats(std::ostream & stream) override;

      void start(HlaCallbackRequestListener onHlaCallbackRequest) override;

      void start(
            HlaCallbackRequestListener onHlaCallbackRequest,
            FedProDuration connectionTimeout) override;

      bool resume() override;

      std::future<ByteSequence> sendHeartbeat() override;

      std::future<ByteSequence> sendHlaCallRequest(const ByteSequence & encodedHlaCall) override;

      void sendHlaCallbackResponse(
            int32_t responseToSequenceNumber,
            const ByteSequence & hlaCallbackResponse) override;

      void terminate() override;

      void terminate(FedProDuration responseTimeout) override;

      /**
        * Disrupt the current transport connection.
        * <p>
        * This function is intended for use in testing only.
        */
      void forceCloseConnection() noexcept;

      Properties getSettings() const noexcept;

      std::string logPrefix() const noexcept;

   private:

      void trackMessageSent(int32_t sequenceNumber, bool isControl);

      void trackHlaMessageReceived(int32_t sequenceNumber) noexcept;

      // The sole mutex for the session, protects the session state transitions
      // and read and write operations on the message buffers
      std::shared_ptr<std::mutex> _sessionMutex;
      std::condition_variable _stateCondition;

      // RequestsFuture has to be declared before message and socket writer to assure that the hash map isn't destroyed
      // before the writers. Class members are initialized in declaration order and destroyed in reverse order.
      ConcurrentHashMap<int32_t, std::promise<ByteSequence>> _requestFutures;
      std::promise<ByteSequence> _sessionTerminatedPromise;
      std::future<ByteSequence> _sessionTerminatedFuture;
      std::unique_ptr<FedPro::TimeoutTimer> _sessionTimeoutTimer;

      // GuardedBy _sessionMutex
      State _state;
      std::unique_ptr<Transport> _transportProtocol;
      std::shared_ptr<Socket> _socket;
      uint64_t _sessionId;
      std::string _sessionIdString;
      AtomicSequenceNumber _lastReceivedSequenceNumber;
      HlaCallbackRequestListener _hlaCallbackRequestListener;
      std::vector<StateListener> _stateListeners;
      MessageSentListener _onMessageSent;
      ConcurrentHashMap<int32_t, MovingStats::SteadyTimePoint> _requestTimes;

      // GuardedBy _sessionMutex
      std::unique_ptr<MessageWriter> _messageWriter;
      std::unique_ptr<SocketWriter> _socketWriter;
      // These 2 threads need to be declared after message writer and socket writer, because of the de-allocation order.
      std::thread _socketWriterThread;
      std::thread _readerThread;

      ThreadPool _stateListenerThreadPool;

      // Session layer settings
      FedProDuration _sessionTimeout;
      bool _rateLimitEnabled;
      uint32_t _maxRetryConnectAttempts;
      FedProDuration _connectionTimeout;
      uint32_t _queueSize;

      using SessionOperation = std::function<void()>;

      using AsyncSessionOperation = std::function<std::future<ByteSequence>()>;

      using ConnectOperation = SessionOperation;

      // Private methods

      static std::string stateToString(State state) noexcept;

      void waitForState(State state) noexcept;

      // Guarded by _sessionMutex
      void lockFreeWaitForState(
            State state,
            std::unique_lock<std::mutex> & sessionLock) noexcept;

      State waitForState(
            State _state,
            FedProDuration timeout) noexcept;

      State waitForStates(std::initializer_list<State> anticipatedStates) noexcept;

      // Guarded by _sessionMutex
      Session::State lockFreeWaitForStates(
            std::initializer_list<State> anticipatedStates,
            std::unique_lock<std::mutex> & lock) noexcept;

      State waitForStates(
            FedProDuration timeout,
            std::initializer_list<State> anticipatedStates) noexcept;

      void stopThreadsInTransitoryState() noexcept;

      bool inStableState() noexcept;

      State waitForStableAndSetState(
            const std::unordered_map<State, State> & transitions,
            const std::string & reason) noexcept;

      State compareAndSetState(
            const std::unordered_map<State, State> & transitions,
            const std::string & reason) noexcept;

      // Guarded by _sessionMutex
      State lockFreeCompareAndSetState(
            const std::unordered_map<State, State> & transitions,
            const std::string & reason) noexcept;

      State compareAndSetState(
            State expectedState,
            State newState) noexcept;

      // Guarded by _sessionMutex
      State lockFreeCompareAndSetState(
            State expectedState,
            State newState) noexcept;

      State compareAndSetState(
            State expectedState,
            State newState,
            const std::string & reason) noexcept;

      // Guarded by _sessionMutex
      State lockFreeCompareAndSetState(
            State expectedState,
            State newState,
            const std::string & reason) noexcept;

      void transitionSessionState(
            State oldState,
            State newState) noexcept;

      void terminateResumingSession(
            std::shared_ptr<Socket> & newSocket,
            const std::string & reason);

      void transitionToTerminated() noexcept;

      void failAllFuturesWhenTerminating() noexcept;

      void failFutureWhenTerminating(std::promise<ByteSequence> & promise) const noexcept;

      void fireOnStateTransition(
            State oldState,
            State newState,
            const std::string & reason) noexcept;

      void doWhenTimedOut(FedProDuration timeout) noexcept;

      void tryConnectOperation(const ConnectOperation & connectOperation) noexcept(false);

      void doSessionOperation(const SessionOperation & operation);

      std::future<ByteSequence> doAsyncSessionOperation(const AsyncSessionOperation & operation);

      // All public methods that results in writing outgoing messages are thread-safe since they pass through
      // doAsyncSessionOperation(), which only executes the passed operation in state RUNNING, DROPPED or RESUMING. This
      // helps ensure correct ordering of messages.
      // Guarded by _sessionMutex
      std::future<ByteSequence> lockFreeDoAsyncSessionOperation(const AsyncSessionOperation & operation);

      void closeSocket() noexcept;

      void close(std::shared_ptr<Socket> & socket) const noexcept;

      void startWriterThread() noexcept;

      void startReaderThread() noexcept;

      // Session mutex must never be acquired while this method is called.
      // Guarded by the session state machine.
      void stopAndWaitForWriterThread() noexcept;

      // Session mutex must never be acquired while this method is called.
      // Guarded by the session state machine.
      void waitForReaderThread() noexcept;

      void runMessageReaderLoop() noexcept;

      void scheduleBestEffortTerminate() noexcept;

      Session::State dropSession(const std::exception & e) noexcept;

      void extendSessionTimer() noexcept;

      uint64_t readNewSessionStatus();

      ResumeStatusMessage readResumeStatus(const std::shared_ptr<Socket> & newSocket);

      static void rethrowAsSessionException();

      void logReceivedMessage(
            MessageType messageType,
            int32_t sequenceNumber) const noexcept;

      std::shared_ptr<RoundRobinBuffer<QueueableMessage>>_roundRobinMessageQueue;

      bool _printStats{false};
      std::unique_ptr<MovingStats> _hlaCallStats;
      std::unique_ptr<MovingStats> _hlaCallTimeStats;
      std::unique_ptr<MovingStats> _hlaCallbackStats;
      std::unique_ptr<MovingStats> _resumeCount;

      bool _warnOnLateStateListenerShutdown{true};

   };

   class SocketWriterListener : public SocketWriter::Listener
   {
   public:

      explicit SocketWriterListener(SessionImpl * session);

      void exceptionOnWrite(const std::exception & e) override;

      void messageSent(int32_t sequenceNumber, bool isControl) override;

   private:
      // Safe, given that the listener instance get deallocated before SessionImpl.
      SessionImpl * _session;
   };

   // For testing purpose, export forceCloseConnection but do not expose it in public headers.
   FEDPRO_EXPORT void forceCloseConnection(Session & session);

} // FedPro
