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

#include "HlaCallResponseMessage.h"

#include "ByteInfo.h"
#include "ByteReader.h"
#include "ByteWriter.h"

namespace FedPro
{
   HlaCallResponseMessage::HlaCallResponseMessage(
         int32_t responseToSequenceNumber,
         ByteSequence && hlaServiceReturnValueOrException) noexcept
         : responseToSequenceNumber{responseToSequenceNumber},
           hlaServiceReturnValueOrException{std::move(hlaServiceReturnValueOrException)}
   {
   }

   ByteSequence HlaCallResponseMessage::encode() const
   {
      ByteSequence encodedMessage;
      encodedMessage.resize(INT32_SIZE + hlaServiceReturnValueOrException.size());
      ByteWriter::write(encodedMessage, 0, responseToSequenceNumber);
      std::copy(
            hlaServiceReturnValueOrException.begin(),
            hlaServiceReturnValueOrException.end(),
            encodedMessage.begin() + INT32_SIZE);
      return encodedMessage;
   }

   HlaCallResponseMessage HlaCallResponseMessage::decode(
         const Socket & inputSocket,
         uint32_t length)
   {
      int32_t responseToSequenceNumber = ByteReader::getInt32(
            inputSocket, "Error reading responseToSequenceNumber in HlaCallResponse in message");

      ByteSequence buffer{inputSocket.recvNBytes(length - INT32_SIZE)};
      return {responseToSequenceNumber, std::move(buffer)};
   }

   std::unique_ptr<EncodableMessage> HlaCallResponseMessage::clone() const
   {
      return std::make_unique<HlaCallResponseMessage>(*this);
   }

} // FedPro
