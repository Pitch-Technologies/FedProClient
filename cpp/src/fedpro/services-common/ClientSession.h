/*
 *  Copyright (C) 2023-2026 Pitch Technologies AB
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

#pragma once

#include "QueuedCallback.h"
#include "utility/BlockingQueue.h"
#include "utility/InterruptibleCondition.h"

#include <fedpro/Aliases.h>
#include <fedpro/PersistentSession.h>
#include <fedpro/Properties.h>

#include <functional>

namespace FedPro
{
   class FederateAmbassadorDispatcher;
   class ResumeStrategy;
   class Transport;

   class ClientSession
   {
   public:

      using Runnable = std::function<void()>;

      using SequenceNumber = int32_t;

      static bool isInCallbackThread();

      ClientSession(
            std::unique_ptr<Transport> clientTransport,
            PersistentSession::SessionTerminatedListener sessionTerminatedListener,
            const Properties & settings,
            std::unique_ptr<ResumeStrategy> resumeStrategy,
            bool createCallbackThread,
            std::shared_ptr<FederateAmbassadorDispatcher> federateAmbassadorDispatcher);

      ~ClientSession();

      void startPersistentSession();

      PersistentSession & getPersistentSession() noexcept
      {
         return *_persistentSession;
      }

      /**
       * Start the callback thread to process responses.
       *
       * @throw std::runtime_error If the callback thread is already running.
       */
      void startCallbackThread();

      void stopCallbackThread();

      /**
       * @throw RTI_NAMESPACE::NotConnected
       * @throw RTI_NAMESPACE::FederateInternalError
       * @throw RTI_NAMESPACE::RTIinternalError
       */
      void dispatchCallback(QueuedCallback queuedCallback);

      void startStatsPrinting(
            Runnable printStatsRunnable,
            FedProDuration printStatsInterval);

      void stopStatsPrinting();

      std::unique_ptr<PersistentSession> _persistentSession;

      std::unique_ptr<std::thread> _callbackThread;

      BlockingQueue<QueuedCallback> _callbackQueue;

      std::mutex _callbackLock;

      InterruptibleCondition _callbackCondition; // GUARDED_BY(_callbackLock)

      bool _callbackInProgress{false}; // GUARDED_BY(_callbackLock)

      bool _callbacksEnabled{true}; // GUARDED_BY(_callbackLock)

      std::thread _statsPrintingThread;

      std::shared_ptr<InterruptibleConditionState> _printStatsInterrupt;

   private:

      void callbackLoop();

      void lostConnection(std::string reason);

      // Parse a callback and add it to the queue.
      void hlaCallbackRequest(
            SequenceNumber sequenceNumber,
            const ByteSequence & hlaCallback);

      void sendHlaCallbackErrorResponse(
            SequenceNumber sequenceNumber,
            ByteSequence && encodedHlaResponse);

      thread_local static bool _inCallbackThread;

      const bool _createCallbackThread;

      const std::shared_ptr<FederateAmbassadorDispatcher> _federateAmbassadorDispatcher;
   };

} // FedPro
