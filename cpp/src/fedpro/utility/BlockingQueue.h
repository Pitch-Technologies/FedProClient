/***********************************************************************
  Copyright (C) 2026 Pitch Technologies AB

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

#include "InterruptibleCondition.h"
#include <fedpro/Aliases.h>

#include <memory>
#include <mutex>
#include <queue>

namespace FedPro
{
   template<typename Type>
   class BlockingQueue
   {
   public:

      /**
       * Interrupt a call to take() or poll() that is awaiting an object from the queue.
       */
      void interrupt()
      {
         std::lock_guard<std::mutex> queueLock(_queueLock);
         _queueCondition.interruptOne();
      }

      /**
       * Push an object into the queue
       */
      void push(Type object)
      {
         std::lock_guard<std::mutex> queueLock(_queueLock);
         _queue.emplace(std::move(object));
         _queueCondition.notifyAll();
      }

      /**
       * Take an object from the queue. May block until an object is available.
       */
      Type take()
      {
         std::unique_lock<std::mutex> queueLock(_queueLock);
         _queueCondition.wait(queueLock, [this]() { return !_queue.empty(); });
         Type object = std::move(_queue.front());
         _queue.pop();
         return object;
      }

      /**
       * Poll an object from the queue.
       * Wait at least for the provided duration for an object to become available.
       */
      std::unique_ptr<Type> poll(
            size_t & remainingCallbacks,
            FedProDuration timeout = FedProDuration{0})
      {
         std::unique_lock<std::mutex> queueLock(_queueLock);
         while (_queue.empty()) {
            if (timeout == FedProDuration::zero() ||
                _queueCondition.waitFor(queueLock, timeout) == std::cv_status::timeout) {
               remainingCallbacks = _queue.size();
               return {};
            }
         }

         auto queuedCallback = std::make_unique<Type>(std::move(_queue.front()));
         _queue.pop();

         remainingCallbacks = _queue.size();
         return queuedCallback;
      }

      size_t size() const
      {
         std::lock_guard<std::mutex> guard(_queueLock);
         return _queue.size();
      }

   private:

      mutable std::mutex _queueLock;

      InterruptibleCondition _queueCondition; // GUARDED_BY(_queueLock)

      std::queue<Type> _queue; // GUARDED_BY(_queueLock)

   };

} // FedPro
