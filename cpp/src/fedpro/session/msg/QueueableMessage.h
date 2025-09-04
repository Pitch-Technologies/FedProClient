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

#include "../ConcurrentHashMap.h"
#include "EncodableMessage.h"
#include "EncodedMessage.h"
#include "../MessageType.h"

#include <cstdint>
#include <future>
#include <memory>
#include <string>

namespace FedPro
{
   class QueueableMessage
   {
   private:
      uint32_t _payloadSize;
      int32_t _lastReceivedSequenceNumber;
      uint64_t _sessionId;
      MessageType _messageType;
      std::unique_ptr<EncodableMessage> _message;
      std::unique_ptr<std::promise<ByteSequence>> _promise;

      // Caller is responsible for deallocate this resource.
      // The deallocating is done in SessionImpl, which owns this map.
      ConcurrentHashMap<int32_t, std::promise<ByteSequence>> * _futuresMap;

   public:

      QueueableMessage(
            uint32_t payloadSize,
            int32_t lastReceivedSequenceNumber,
            uint64_t sessionId,
            MessageType messageType,
            std::unique_ptr<EncodableMessage> message,
            std::unique_ptr<std::promise<ByteSequence>> promise,
            ConcurrentHashMap<int32_t, std::promise<ByteSequence>> * futuresMap) noexcept;

      ~QueueableMessage() = default;

      QueueableMessage(QueueableMessage && rhs) noexcept;

      QueueableMessage & operator=(QueueableMessage && rhs) noexcept;

      bool isHlaResponse() const noexcept;

      /**
       * @brief This function can only be called once for each QueueableMessage since its promise is moved to,
       * the map of session promises
       */
      EncodedMessage createEncodedMessage(int32_t nextSequenceNumber);

      std::string toString() const noexcept;

      void swap(QueueableMessage & rhs) noexcept;

      friend std::ostream & operator<<(
            std::ostream & os,
            const QueueableMessage & message);
   };

} // FedPro
