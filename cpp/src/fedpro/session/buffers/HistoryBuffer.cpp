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

#include "HistoryBuffer.h"

#include <utility>

namespace FedPro
{
   HistoryBuffer::HistoryBuffer(
         std::shared_ptr<std::mutex> mutex,
         std::shared_ptr<RoundRobinBuffer<QueueableMessage>> messageQueue,
         int32_t initialSequenceNumber) noexcept
         : _mutex{std::move(mutex)},
           _sequenceNumberAllocator{initialSequenceNumber},
           _messageQueue{std::move(messageQueue)},
           _history{_messageQueue->capacity()}
   {
   }

   std::unique_ptr<EncodedMessage> HistoryBuffer::waitAndPoll()
   {
      std::lock_guard<std::mutex> lock(*_mutex);
      waitAndPollIntoHistory();
      return internalPoll();
   }

   std::unique_ptr<EncodedMessage> HistoryBuffer::waitAndPeek()
   {
      std::lock_guard<std::mutex> lock(*_mutex);
      waitAndPollIntoHistory();
      return internalPeek();
   }

   std::unique_ptr<EncodedMessage> HistoryBuffer::poll()
   {
      std::lock_guard<std::mutex> lock(*_mutex);
      pollIntoHistory();
      return internalPoll();
   }

   std::unique_ptr<EncodedMessage> HistoryBuffer::peek()
   {
      std::lock_guard<std::mutex> lock(*_mutex);
      pollIntoHistory();
      return internalPeek();
   }

   HistoryBuffer::size_type HistoryBuffer::size() noexcept
   {
      std::lock_guard<std::mutex> lock(*_mutex);
      return _history.size() + _messageQueue->size() + (_currentControlMessage ? 1 : 0);
   }

   bool HistoryBuffer::isEmpty() noexcept
   {
      return size() == 0 && !_currentControlMessage;
   }

   HistoryBuffer::size_type HistoryBuffer::capacity() const noexcept
   {
      return _history.capacity();
   }

   bool HistoryBuffer::waitUntilEmpty(FedProDuration timeout)
   {
      std::lock_guard<std::mutex> lock(*_mutex);
      return _messageQueue->waitUntilEmpty(timeout);
   }

   void HistoryBuffer::rewindTo(const SequenceNumber & sequenceNumber)
   {
      _history.rewindTo(
            [&sequenceNumber](const EncodedMessage & object) {
               // There are no null element in _history so no need to check if "object != nullptr"
               return sequenceNumber.get() == object.sequenceNumber;
            });
      _currentControlMessage = nullptr;
   }

   void HistoryBuffer::rewindToFirst()
   {
      _history.rewindToFirst();
      _currentControlMessage = nullptr;
   }

   int32_t HistoryBuffer::getOldestAddedSequenceNumber()
   {
      // Intentionally skipping control messages
      std::lock_guard<std::mutex> lock(*_mutex);
      std::unique_ptr<EncodedMessage> message = _history.peekOldest();
      return message ? message->sequenceNumber : SequenceNumber::NO_SEQUENCE_NUMBER;
   }

   int32_t HistoryBuffer::getNewestAddedSequenceNumber()
   {
      // Intentionally skipping control messages
      std::lock_guard<std::mutex> lock(*_mutex);
      std::unique_ptr<EncodedMessage> message = _history.peekNewest();
      return message ? message->sequenceNumber : SequenceNumber::NO_SEQUENCE_NUMBER;
   }

   void HistoryBuffer::interrupt(bool interrupt) noexcept
   {
      std::lock_guard<std::mutex> lock(*_mutex);
      _messageQueue->interrupt(interrupt);
   }

   std::string HistoryBuffer::toString() noexcept
   {
      std::lock_guard<std::mutex> lock(*_mutex);
      return "HistoryBuffer -> " + _history.toString();
   }

   // Private Methods

   // Requires mutex to be held when called.
   int32_t HistoryBuffer::nextSequenceNumber() noexcept
   {
      int32_t oldNumber = _sequenceNumberAllocator.get();
      _sequenceNumberAllocator.increment();
      return oldNumber;
   }

   // Requires mutex to be held when called.
   void HistoryBuffer::waitAndPollIntoHistory()
   {
      if (_history.isEmpty() && !_currentControlMessage) {
         std::unique_ptr<QueueableMessage> message = _messageQueue->waitAndPoll();
         if (message) {
            internalInsertIntoHistory(message->createEncodedMessage(nextSequenceNumber()));
         }
      }
   }

   // Requires mutex to be held when called.
   void HistoryBuffer::pollIntoHistory()
   {
      if (_history.isEmpty() && !_currentControlMessage) {
         std::unique_ptr<QueueableMessage> message = _messageQueue->poll();
         if (message) {
            internalInsertIntoHistory(message->createEncodedMessage(nextSequenceNumber()));
         }
      }
   }

   // Requires mutex to be held when called.
   std::unique_ptr<EncodedMessage> HistoryBuffer::internalPoll()
   {
      if (!_currentControlMessage) {
         return _history.poll();
      } else {
         return std::move(_currentControlMessage);
      }
   }

   // Requires mutex to be held when called.
   std::unique_ptr<EncodedMessage> HistoryBuffer::internalPeek()
   {
      if (!_currentControlMessage) {
         return _history.peek();
      } else {
         return std::make_unique<EncodedMessage>(*_currentControlMessage);
      }
   }

   // Requires mutex to be held when called.
   void HistoryBuffer::internalInsertIntoHistory(EncodedMessage encodedMessage)
   {
      if (!encodedMessage.isControl) {
         _history.insert(std::move(encodedMessage));
      } else {
         _currentControlMessage = std::make_unique<EncodedMessage>(encodedMessage);
      }
   }

} // FedPro
