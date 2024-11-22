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

#include "BufferReader.h"

#include "CircularBuffer.h"
#include "RoundRobinBuffer.h"
#include "../msg/EncodedMessage.h"
#include "../msg/QueueableMessage.h"
#include "../SequenceNumber.h"

#include <mutex>
#include <cstdint>

namespace FedPro
{
   class HistoryBuffer : public BufferReader<EncodedMessage>
   {
   public:

      HistoryBuffer(
            std::shared_ptr<std::mutex> mutex,
            std::shared_ptr<RoundRobinBuffer<QueueableMessage>> messageQueue,
            int32_t initialSequenceNumber) noexcept;

      std::unique_ptr<EncodedMessage> waitAndPoll() override;

      std::unique_ptr<EncodedMessage> waitAndPeek() override;

      std::unique_ptr<EncodedMessage> poll() override;

      std::unique_ptr<EncodedMessage> peek() override;

      size_type size() noexcept override;

      bool isEmpty() noexcept override;

      size_type capacity() const noexcept override;

      bool waitUntilEmpty(FedProDuration timeout) override;

      void rewindToFirst();

      void rewindTo(const SequenceNumber & sequenceNumber);

      int32_t getOldestAddedSequenceNumber();

      int32_t getNewestAddedSequenceNumber();

      void interrupt(bool interrupt) noexcept override;

      std::string toString() noexcept override;

   private:
      std::shared_ptr<std::mutex> _mutex;
      SequenceNumber _sequenceNumberAllocator;
      std::shared_ptr<RoundRobinBuffer<QueueableMessage>> _messageQueue;
      CircularBuffer<EncodedMessage> _history;
      std::unique_ptr<EncodedMessage> _currentControlMessage;

      int32_t nextSequenceNumber() noexcept;

      void waitAndPollIntoHistory();

      void pollIntoHistory();

      std::unique_ptr<EncodedMessage> internalPoll();

      std::unique_ptr<EncodedMessage> internalPeek();

      void internalInsertIntoHistory(EncodedMessage encodedMessage);
   };

} // FedPro
