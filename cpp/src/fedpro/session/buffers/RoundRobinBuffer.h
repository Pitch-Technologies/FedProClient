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

#include "GenericBuffer.h"

#include "../../utility/NannyLock.h"
#include "../flowcontrol/RateLimiter.h"
#include "RateLimitedBuffer.h"
#include "UnboundedBuffer.h"

#include <cstddef>
#include <functional>
#include <mutex>
#include <utility>

namespace FedPro
{

   // Not thread-safe, relies on caller for locking.
   template<typename E>
   class RoundRobinBuffer : public GenericBuffer<E>
   {
   public:

      using peek_type = typename GenericBuffer<E>::peek_type;

      using size_type = size_t;

      using QueueAlternator = std::function<bool(const E &)>;

      /**
      * @param mutex Used to signal the instance of this class to stop waiting for insertion or poll
       * as well as signaling _primaryQueue and _alternateQueue to stop waiting.
      */
      RoundRobinBuffer(
            std::shared_ptr<RateLimiter> limiter,
            const QueueAlternator & alternator,
            std::shared_ptr<std::mutex> mutex,
            size_type capacity,
            int primaryFactor = 1,
            int alternateFactor = 1)
            : _mutex{std::move(mutex)},
              _primaryQueue{_mutex, capacity, std::move(limiter)},
              _alternateQueue{_mutex},
              _addToAlternateQueue{alternator},
              _primaryFactor{primaryFactor},
              _alternateFactor{alternateFactor},
              _smallerFactorDoubled{_primaryFactor < _alternateFactor ? _primaryFactor * 2 : _alternateFactor * 2},
              _alternatorIndex{1}
      {
      }

      bool insert(E && element) noexcept override
      {
         bool inserted;
         if (!_addToAlternateQueue(element)) {
            inserted = _primaryQueue.insert(std::move(element));
         } else {
            inserted = _alternateQueue.insert(std::move(element));
         }
         _condition.notify_all();
         return inserted;
      }

      std::unique_ptr<E> waitAndPoll() override
      {
         NannyLock lock(*_mutex);
         while (!_interrupted && isEmpty()) {
            _condition.wait(lock);
         }
         // Do this regardless of whether the socket is working or not
         return poll();
      }

      peek_type waitAndPeek() override
      {
         NannyLock lock(*_mutex);
         while (!_interrupted && isEmpty()) {
            _condition.wait(lock);
         }
         // Do this regardless of whether the socket is working or not
         return peek();
      }

      bool waitUntilEmpty(FedProDuration timeout) override
      {
         _primaryQueue.waitUntilEmpty(timeout / 2);
         _alternateQueue.waitUntilEmpty(timeout / 2);
         return isEmpty();
      }

      std::unique_ptr<E> poll() override
      {
         std::unique_ptr<E> element = selectReadBuffer()->poll();
         incrementAlternator();
         _condition.notify_all();
         return element;
      }

      peek_type peek() override
      {
         return selectReadBuffer()->peek();
      }

      size_type primarySize() noexcept
      {
         return _primaryQueue.size();
      }

      size_type alternateSize() noexcept
      {
         return _alternateQueue.size();
      }

      size_type size() noexcept override
      {
         return _primaryQueue.size() > _alternateQueue.size() ? _primaryQueue.size() : _alternateQueue.size();
      }

      bool isEmpty() noexcept override
      {
         return _primaryQueue.isEmpty() && _alternateQueue.isEmpty();
      }

      size_type capacity() const noexcept override
      {
         return _primaryQueue.capacity();
      }

      void interruptPoller(bool interrupt) noexcept
      {
         _interrupted = interrupt;
         _condition.notify_all();
      }

      void interrupt(bool interrupt) noexcept override
      {
         _interrupted = interrupt;
         _primaryQueue.interrupt(interrupt);
         _alternateQueue.interrupt(interrupt);
         _condition.notify_all();
      }

      std::string toString() noexcept override
      {
         std::string result = "RoundRobinBuffer {\n";
         result += "\tInterrupted = " + std::to_string(_interrupted) + ",\n";
         result += "Primary Queue = " + _primaryQueue.toString() + "\n";
         result += "Alternate Queue = " + _alternateQueue.toString() + "\n";
         result += "Primary Factor = " + std::to_string(_primaryFactor) + "\n";
         result += "Alternate Factor = " + std::to_string(_alternateFactor) + "\n";
         result += "Alternator Index = " + std::to_string(_alternatorIndex) + "\n";
         return result;
      }

   private:
      // The mutex most be owned by the caller and not deallocated before the instance of this class is.
      std::shared_ptr<std::mutex> _mutex;
      std::condition_variable _condition;
      bool _interrupted{false};
      RateLimitedBuffer<E> _primaryQueue;
      UnboundedBuffer<E> _alternateQueue;
      const QueueAlternator _addToAlternateQueue;

      const int _primaryFactor;
      const int _alternateFactor;
      const int _smallerFactorDoubled;
      int _alternatorIndex;

      // Requires mutex to be held when called.
      void incrementAlternator() noexcept
      {
         _alternatorIndex++;
         if (_alternatorIndex > _primaryFactor + _alternateFactor) {
            _alternatorIndex = 1;
         }
      }

      // Requires mutex to be held when called.
      GenericBuffer<E> * selectReadBuffer() noexcept
      {
         if (_alternatorIndex <= _smallerFactorDoubled) {
            if (_alternatorIndex % 2 == 0) {
               return alternateQueueUnlessEmpty();
            } else {
               return primaryQueueUnlessEmpty();
            }
         } else if (_primaryFactor > _alternateFactor) {
            return primaryQueueUnlessEmpty();
         } else {
            return alternateQueueUnlessEmpty();
         }
      }

      // Requires mutex to be held when called.
      GenericBuffer<E> * primaryQueueUnlessEmpty() noexcept
      {
         if (_primaryQueue.isEmpty()) {
            return &_alternateQueue;
         }
         return &_primaryQueue;
      }

      // Requires mutex to be held when called.
      GenericBuffer<E> * alternateQueueUnlessEmpty() noexcept
      {
         if (_alternateQueue.isEmpty()) {
            return &_primaryQueue;
         }
         return &_alternateQueue;
      }

   };

} // FedPro
