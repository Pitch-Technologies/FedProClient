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

#include "../../utility/string_view.h"
#include "../MessageType.h"
#include "ByteInfo.h"
#include <fedpro/Aliases.h>
#include <fedpro/Socket.h>

#include <cstdint>
#include <memory>

namespace FedPro
{
   class Socket;

   class MessageHeader
   {
   public:
      // Header size in bytes, may be abstracted as 4 + 4 + 8 + 4 + 4 bytes.
      static const uint32_t SIZE = INT32_SIZE * 6;
      static const uint64_t NO_SESSION_ID = 0;
      static constexpr uint32_t MESSAGE_TYPE_FIRST_BYTE_POS = 20;

      const uint32_t packetSize;
      const int32_t sequenceNumber;
      const uint64_t sessionId;
      const int32_t lastReceivedSequenceNumber;
      const MessageType messageType;

      MessageHeader(
            uint32_t packetSize,
            int32_t sequenceNumber,
            uint64_t sessionId,
            int32_t lastReceivedSequenceNumber,
            MessageType messageType) noexcept;

      static MessageHeader with(
            uint32_t payloadSize,
            int32_t sequenceNumber,
            uint64_t sessionId,
            int32_t lastReceivedSequenceNumber,
            MessageType messageType) noexcept;

      uint64_t getPayloadSize() const noexcept;

      ByteSequence encode() const;

      static MessageHeader decode(string_view headerBuffer);

      static std::string getMessageTypeFromBytes(const ByteSequence & messageData);

   };

} // FedPro
