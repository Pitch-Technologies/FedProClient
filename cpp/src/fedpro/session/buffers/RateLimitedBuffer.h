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
#include "../flowcontrol/NullRateLimiter.h"
#include "../flowcontrol/RateLimiter.h"

#include <condition_variable>
#include <cstddef>
#include <mutex>
#include <queue>
#include <utility>

namespace FedPro
{

   // Not thread-safe, relies on caller for locking.
   template<typename E>
   class RateLimitedBuffer : public GenericBuffer<E>
   {
   public:

      using peek_type = typename GenericBuffer<E>::peek_type;

      using size_type = size_t;

      /**
      * @param mutex Used to signal the instance of this class to stop waiting for insertion or poll.
      */
      explicit RateLimitedBuffer(
            std::shared_ptr<std::mutex> mutex,
            size_type capacity,
            std::shared_ptr<RateLimiter> limiter = std::make_shared<NullRateLimiter>(),
            bool blockWhenFull = true)
            : _mutex{std::move(mutex)},
              _condition{},
              _interrupted{false},
              _capacity{capacity},
              _data{},
              _limiter{std::move(limiter)},
              _blockWhenFull{blockWhenFull},
              _count{0}
      {
         if (!_limiter) {
            throw std::invalid_argument("Missing limiter for RateLimitedBuffer construction");
         }
      }

      bool insert(E && element) noexcept override
      {
         NannyLock lock(*_mutex);
         _limiter->preInsert(_count);
         if (_blockWhenFull) {
            while (_count >= _capacity) {
               if (_interrupted) {
                  return false;
               }
               _condition.wait(lock);
            }
         } else {
            if (_count >= _capacity) {
               return false;
            }
         }
         _data.emplace(std::move(element));
         _count++;
         _condition.notify_all();
         _limiter->postInsert(_count);
         return true;
      }

      std::unique_ptr<E> waitAndPoll() override
      {
         NannyLock lock(*_mutex);
         while (!_interrupted && isEmpty()) {
            _condition.wait(lock);
         }
         return poll();
      }

      peek_type waitAndPeek() override
      {
         NannyLock lock(*_mutex);
         while (!_interrupted && isEmpty()) {
            _condition.wait(lock);
         }
         return peek();
      }

      bool isEmpty() noexcept override
      {
         return _count == 0;
      }

      size_type capacity() const noexcept override
      {
         return _capacity;
      }

      size_type size() noexcept override
      {
         return _count;
      }

      std::unique_ptr<E> poll() override
      {
         if (_count > 0) {
            _count--;
            std::unique_ptr<E> element = std::make_unique<E>(std::move(_data.front()));
            _data.pop();
            _condition.notify_all();
            return element;
         }
         return nullptr;
      }

      peek_type peek() override
      {
         if (_data.empty()) {
            return BufferPeeker<E>::createPeek();
         }
         return BufferPeeker<E>::createPeek(_data.front());
      }

      bool waitUntilEmpty(FedProDuration timeout) override
      {
         NannyLock lock(*_mutex);
         if (timeout == FedProDuration::zero()) {
            while (!_interrupted && !isEmpty()) {
               _condition.wait(lock);
            }
            return isEmpty();
         } else {
            auto waitEnd = std::chrono::steady_clock::now() + timeout;
            auto timeLeft = waitEnd - std::chrono::steady_clock::now();
            while (!_interrupted && !isEmpty() && timeLeft > FedProDuration::zero()) {
               if (_condition.wait_for(lock, timeLeft) == std::cv_status::timeout) {
                  break;
               }
               timeLeft = waitEnd - std::chrono::steady_clock::now();
            }
            return isEmpty();
         }
      }

      void interrupt(bool interrupt) noexcept override
      {
         _interrupted = interrupt;
         _condition.notify_all();
      }

      std::string toString() noexcept override
      {
         std::string result = "RateLimitedBuffer {\n";
         result += "\tInterrupted = " + std::to_string(_interrupted) + ",\n";
         result += "\tCapacity = " + std::to_string(_capacity) + "\n";
         result += "\tData = " + queueToString(_data) + "\n";
         result += "\tLimiter = " + _limiter->toString() + "\n";
         std::string blockWhenFull = _blockWhenFull ? "true" : "false";
         result += "\tBlock when full = " + blockWhenFull + "\n";
         result += "\tCount = " + std::to_string(_data.size()) + "\n";
         result += "}";
         return result;
      }

   private:
      // Used for waiting for elements to be inserted or polled.
      std::shared_ptr<std::mutex> _mutex;
      std::condition_variable _condition;
      bool _interrupted;

      const size_type _capacity;
      std::queue<E> _data;
      const std::shared_ptr<RateLimiter> _limiter;
      bool _blockWhenFull;
      size_type _count;

   };

} // FedPro
