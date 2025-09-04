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

#include "MessageHeader.h"

#include "ByteInfo.h"
#include "ByteReader.h"
#include "ByteWriter.h"
#include "fedpro/FedProExceptions.h"

#include <string>

namespace FedPro
{
   MessageHeader::MessageHeader(
         uint32_t packetSize,
         int32_t sequenceNumber,
         uint64_t sessionId,
         int32_t lastReceivedSequenceNumber,
         MessageType messageType) noexcept
         : packetSize{packetSize},
           sequenceNumber{sequenceNumber},
           sessionId{sessionId},
           lastReceivedSequenceNumber{lastReceivedSequenceNumber},
           messageType{messageType}
   {
   }

   MessageHeader MessageHeader::with(
         uint32_t payloadSize,
         int32_t sequenceNumber,
         uint64_t sessionId,
         int32_t lastReceivedSequenceNumber,
         MessageType messageType) noexcept
   {
      return {SIZE + payloadSize, sequenceNumber, sessionId, lastReceivedSequenceNumber, messageType};
   }

   uint32_t MessageHeader::getPayloadSize() const noexcept
   {
      return packetSize - SIZE;
   }

   ByteSequence MessageHeader::encode() const
   {
      ByteSequence encodedMessage;
      encodedMessage.resize(SIZE);
      int32_t messageTypeAsInt = MessageTypeHelper::asInt(this->messageType);
      int32_t writeHead{0};

      ByteWriter::write(encodedMessage, writeHead, packetSize);                                    // Starts at 0
      ByteWriter::write(encodedMessage, writeHead += INT32_SIZE, sequenceNumber);                  // Starts at 4
      ByteWriter::write(encodedMessage, writeHead += INT32_SIZE, sessionId);                       // Starts at 8
      ByteWriter::write(encodedMessage, writeHead += INT32_SIZE * 2, lastReceivedSequenceNumber);  // Starts at 16
      ByteWriter::write(encodedMessage, writeHead + INT32_SIZE, messageTypeAsInt);                 // Starts at 20

      return encodedMessage;
   }

   MessageHeader MessageHeader::decode(string_view headerBuffer)
   {
      ByteReader reader{headerBuffer};
      uint32_t packetSize = reader.readInt32();

      if (packetSize < MessageHeader::SIZE) {
         throw BadMessage("Invalid PacketSize " + std::to_string(packetSize));
      }

      int32_t sequenceNumber = reader.readInt32();
      uint64_t sessionId = reader.readUint64();
      int32_t lastReceivedSequenceNumber = reader.readInt32();
      uint32_t messageTypeUnsigned = reader.readInt32();

      MessageType messageType = MessageTypeHelper::fromUnsigned(messageTypeUnsigned);

      if (messageType == MessageType::UNKNOWN) {
         throw BadMessage("Unknown MessageType " + std::to_string(messageTypeUnsigned));
      }

      return MessageHeader{packetSize, sequenceNumber, sessionId, lastReceivedSequenceNumber, messageType};
   }

   std::string MessageHeader::getMessageTypeFromBytes(const ByteSequence & messageData)
   {
      if (SIZE <= messageData.size() && messageData.size() <= INT64_MAX) {
         uint32_t messageTypeUnsigned{0};

         // Decode the MessageType from bytes into an unsigned 32-bit integer.
         for (uint32_t i = MESSAGE_TYPE_FIRST_BYTE_POS; i < MESSAGE_TYPE_FIRST_BYTE_POS + INT32_SIZE; i++) {
            messageTypeUnsigned = (messageTypeUnsigned << 8) | static_cast<uint8_t>(messageData[i]);
         }

         MessageType messageType = MessageTypeHelper::fromUnsigned(messageTypeUnsigned);
         return MessageTypeHelper::asString(messageType);
      }
      return "<ENCODING ERROR>";
   }

} // FedPro
