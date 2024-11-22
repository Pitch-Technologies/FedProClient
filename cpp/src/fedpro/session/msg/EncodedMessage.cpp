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

#include "EncodedMessage.h"

#include "../SessionUtils.h"

#include <chrono>

namespace FedPro
{
   EncodedMessage::EncodedMessage(
         int32_t sequenceNumber,
         bool isControl,
         ByteSequence data) noexcept
         : sequenceNumber{sequenceNumber},
           isControl{isControl},
           data{std::move(data)}
   {
   }

   EncodedMessage EncodedMessage::create(
         const MessageHeader & header,
         EncodableMessage * message)
   {
      ByteSequence buffer;
      buffer.resize(header.packetSize);

      buffer = header.encode();
      // Ownership for the EncodableMessage remains with the caller
      if (message) {
         const ByteSequence messageData = message->encode();
         buffer.insert(buffer.end(), messageData.begin(), messageData.end());
      }
      bool isControlMessage = MessageTypeHelper::isControl(header.messageType);
      return {header.sequenceNumber, isControlMessage, std::move(buffer)};
   }

   std::string EncodedMessage::toString() const noexcept
   {
      std::string result = "EncodedMessage {\n";
      result += "\tSequence Number = " + std::to_string(sequenceNumber) + ",\n";
      result += "\tIs Control message = " + std::to_string(isControl) + ",\n";
      result += "\tData = " + sequenceToString(data.c_str(), data.size()) + ",\n";
      result += "}";
      return result;
   }

   std::ostream & operator<<(
         std::ostream & os,
         const EncodedMessage & message)
   {
      return os << message.toString();
   }

} // FedPro
