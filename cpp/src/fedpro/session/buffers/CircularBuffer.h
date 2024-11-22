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

#include <chrono>
#include <condition_variable>
#include <cstdint>
#include <functional>
#include <memory>
#include <mutex>

namespace FedPro
{
   // A thread-safe, circular buffer of objects.
   // On overflow, new objects inserted into the buffer will silently overwrite old ones, even if they have not been read yet.

   template<typename E>
   class CircularBuffer : public GenericBuffer<E>
   {
   public:

      using size_type = uint32_t;

      using Predicate = std::function<bool(const E &)>;

      explicit CircularBuffer(size_type capacity)
            : _mutex{},
              _condition{},
              _interrupted{false},
              _capacity{capacity},
              _data{new E[capacity]},
              _writeIndex{-1},
              _count{0},
              _hasRotated{},
              _hasWrittenAfterRotate{}
      {
      }

      ~CircularBuffer()
      {
         delete[] _data;
      }

      bool insert(E && element) noexcept override
      {
         std::lock_guard<std::mutex> lock(_mutex);
         size_type nextWriteIndex = inc(_writeIndex);
         _data[nextWriteIndex] = std::move(element);
         _writeIndex = static_cast<int64_t>(nextWriteIndex);
         if (_hasRotated && !_hasWrittenAfterRotate) {
            _hasWrittenAfterRotate = true;
         }
         if (_writeIndex >= (static_cast<int64_t>(_capacity) - 1)) {
            _hasRotated = true;
         }
         if (_count < _capacity) {
            _count++;
         }
         _condition.notify_all();
         return true;
      }

      std::unique_ptr<E> peekOldest()
      {
         std::lock_guard<std::mutex> lock(_mutex);
         if (_writeIndex > -1) {
            return std::make_unique<E>(_data[oldestIndex()]);
         }
         return nullptr;
      }

      std::unique_ptr<E> peekNewest()
      {
         std::lock_guard<std::mutex> lock(_mutex);
         if (_writeIndex > -1) {
            return std::make_unique<E>(_data[_writeIndex]);
         }
         return nullptr;
      }

      std::unique_ptr<E> peek() override
      {
         std::unique_lock<std::mutex> lock(_mutex);
         return lockFreePeek();
      }

      std::unique_ptr<E> poll() override
      {
         std::lock_guard<std::mutex> lock(_mutex);
         return lockFreePoll();
      }

      std::unique_ptr<E> waitAndPoll() override
      {
         std::unique_lock<std::mutex> lock(_mutex);
         while (!_interrupted && lockFreeIsEmpty()) {
            _condition.wait(lock);
         }
         return lockFreePoll();
      }

      std::unique_ptr<E> waitAndPeek() override
      {
         std::unique_lock<std::mutex> lock(_mutex);
         while (!_interrupted && lockFreeIsEmpty()) {
            _condition.wait(lock);
         }
         return lockFreePeek();
      }

      void rewindToFirst()
      {
         std::lock_guard<std::mutex> lock(_mutex);
         if (_hasWrittenAfterRotate) {
            throw std::invalid_argument("Tried to rewind past the bounds of the buffer.");
         }
         if (_hasRotated) {
            _count = _capacity;
         } else {
            _count = _writeIndex + 1;
         }
      }

      void rewindTo(Predicate isCorrectElement)
      {
         std::lock_guard<std::mutex> lock(_mutex);
         size_type originalCount = _count;
         size_type highestValidIndex = _capacity;

         if (!_hasRotated) {
            highestValidIndex = readIndex();
         } else if (_count == 0) {
            _count++;
         }
         while (true) {
            size_type index = readIndex();

            if (index < highestValidIndex && isCorrectElement(_data[index])) {
               return;
            }
            _count++;
            if (_count > _capacity) {
               _count = originalCount;
               throw std::invalid_argument("Tried to rewind past the bounds of the buffer.");
            }
         }
      }

      size_type capacity() const noexcept override
      {
         return _capacity;
      }

      size_type size() noexcept override
      {
         std::lock_guard<std::mutex> lock(_mutex);
         return _count;
      }

      bool waitUntilEmpty(FedProDuration timeout) override
      {
         if (timeout == FedProDuration::zero()) {
            std::unique_lock<std::mutex> lock(_mutex);
            while (!_interrupted && !lockFreeIsEmpty()) {
               _condition.wait(lock);
            }
            return lockFreeIsEmpty();
         } else {
            auto waitEnd = std::chrono::system_clock::now() + timeout;
            std::unique_lock<std::mutex> lock(_mutex);
            auto timeLeft = waitEnd - std::chrono::system_clock::now();
            while (!_interrupted && !lockFreeIsEmpty() && timeLeft > FedProDuration::zero()) {
               if (_condition.wait_for(lock, timeLeft) == std::cv_status::timeout) {
                  break;
               }
               timeLeft = waitEnd - std::chrono::system_clock::now();
            }
            return lockFreeIsEmpty();
         }
      }

      bool isEmpty() noexcept override
      {
         std::lock_guard<std::mutex> lock(_mutex);
         return lockFreeIsEmpty();
      }

      bool isFull() noexcept
      {
         std::lock_guard<std::mutex> lock(_mutex);
         return _count >= _capacity;
      }

      void interrupt(bool interrupt) noexcept override
      {
         std::lock_guard<std::mutex> lock(_mutex);
         _interrupted = interrupt;
         _condition.notify_all();
      }

      std::string toString() noexcept override
      {
         std::lock_guard<std::mutex> lock(_mutex);
         std::string result = "CircularBuffer {\n";

         result += "\tInterrupted = " + std::to_string(_interrupted) + ",\n";
         result += "\tCapacity = " + std::to_string(_capacity) + ",\n";
         result += "\tData = " + sequenceToString(_data, _count) + "\n";
         result += "\tWrite index = " + std::to_string(_writeIndex) + ",\n";
         result += "\tCount = " + std::to_string(_count) + ",\n";
         std::string hasRotated = _hasRotated ? "true" : "false";
         result += "\tRotated = " + hasRotated + "\n";

         result += "}";
         return result;
      }

   private:
      std::mutex _mutex;
      std::condition_variable _condition;
      bool _interrupted;

      const size_type _capacity;
      E * _data;
      int64_t _writeIndex;
      size_type _count;
      bool _hasRotated;
      bool _hasWrittenAfterRotate;

      // Requires mutex to be held when called.
      size_type inc(int64_t index) const noexcept
      {
         return static_cast<size_type>((index + 1) % _capacity);
      }

      // Requires mutex to be held when called.
      bool lockFreeIsEmpty() const noexcept
      {
         return _count == 0;
      }

      // Requires mutex to be held when called.
      std::unique_ptr<E> lockFreePoll()
      {
         if (_count > 0) {
            std::unique_ptr<E> element = std::make_unique<E>(_data[readIndex()]);
            _count--;
            _condition.notify_all();
            return element;
         }
         return nullptr;
      }

      // Requires mutex to be held when called.
      std::unique_ptr<E> lockFreePeek() const
      {
         if (_count > 0) {
            return std::make_unique<E>(_data[readIndex()]);
         }
         return nullptr;
      }

      // Requires mutex to be held when called.
      size_type oldestIndex() const noexcept
      {
         return _hasRotated ? inc(_writeIndex) : 0;
      }

      // Requires mutex to be held when called.
      size_type readIndex() const noexcept
      {
         int64_t readIndex = _writeIndex - (static_cast<int64_t>(_count) - 1);
         if (readIndex < 0) {
            readIndex += static_cast<int64_t>(_capacity);
         }
         return static_cast<size_type>(readIndex % _capacity);
      }
   };

} // FedPro
