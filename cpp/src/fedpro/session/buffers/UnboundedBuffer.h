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

#include <mutex>
#include <queue>
#include <cstddef>

namespace FedPro
{
   // Not thread-safe, relies on caller for locking.
   template<typename E>
   class UnboundedBuffer : public GenericBuffer<E>
   {
   public:

      using peek_type = typename GenericBuffer<E>::peek_type;

      using size_type = size_t;

      /**
      * @param mutex Used to signal the instance of this class to stop waiting for insertion or poll.
      */
      explicit UnboundedBuffer(
            const std::shared_ptr<std::mutex> & mutex)
            : _mutex{mutex}
      {
      }

      std::unique_ptr<E> waitAndPoll() override
      {
         throw std::runtime_error("Not implemented");
      }

      peek_type waitAndPeek() override
      {
         throw std::runtime_error("Not implemented");
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

      std::unique_ptr<E> poll() override
      {
         if (_data.empty()) {
            return nullptr;
         }
         std::unique_ptr<E> element = std::make_unique<E>(std::move(_data.front()));
         _data.pop();
         _condition.notify_all();
         return element;
      }

      peek_type peek() override
      {
         if (_data.empty()) {
            return BufferPeeker<E>::createPeek();
         }
         return BufferPeeker<E>::createPeek(_data.front());
      }

      size_type size() noexcept override
      {
         // Todo - if size would be larger than size_type, then we would be lying
         return capacity() < _data.size() ? capacity() : _data.size();
      }

      bool isEmpty() noexcept override
      {
         return _data.empty();
      }

      size_type capacity() const noexcept override
      {
         return UINT32_MAX;
      }

      bool insert(E && element) noexcept override
      {
         _data.emplace(std::move(element));
         _condition.notify_all();
         return true;
      }

      void interrupt(bool interrupt) noexcept override
      {
         _interrupted = interrupt;
         _condition.notify_all();
      }

      std::string toString() noexcept override
      {
         std::string result = "UnboundedBuffer {\n";
         result += "\tInterrupted = " + std::to_string(_interrupted) + ",\n";
         result += "\tCapacity = " + std::to_string(capacity()) + "\n";
         result += "\tData = " + queueToString(_data) + "\n";
         result += "\tCount = " + std::to_string(_data.size()) + "\n";
         result += "}";
         return result;
      }

   private:
      std::shared_ptr<std::mutex> _mutex;
      std::condition_variable _condition;
      bool _interrupted{false};

      std::queue<E> _data;
   };

} // FedPro
