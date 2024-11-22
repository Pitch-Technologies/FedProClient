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

#include "ResumeRequestMessage.h"

#include "ByteInfo.h"
#include "ByteReader.h"
#include "ByteWriter.h"

namespace FedPro
{
   ResumeRequestMessage::ResumeRequestMessage(
         int32_t lastReceivedRtiSequenceNumber,
         int32_t oldestAvailableFederateSequenceNumber) noexcept
         : lastReceivedRtiSequenceNumber{lastReceivedRtiSequenceNumber},
           oldestAvailableFederateSequenceNumber{oldestAvailableFederateSequenceNumber}
   {
   }

   ByteSequence ResumeRequestMessage::encode() const
   {
      ByteSequence encodedMessage;
      encodedMessage.resize(INT32_SIZE * 2);
      ByteWriter::write(encodedMessage, 0, lastReceivedRtiSequenceNumber);
      ByteWriter::write(encodedMessage, sizeof(lastReceivedRtiSequenceNumber), oldestAvailableFederateSequenceNumber);
      return encodedMessage;
   }

   ResumeRequestMessage ResumeRequestMessage::decode(const Socket & inputSocket)
   {
      int32_t lastReceivedRtiSequenceNumber = ByteReader::getInt32(
            inputSocket, "Error reading lastReceivedRtiSequenceNumber in ResumeRequest message");
      int32_t oldestAvailableFederateSequenceNumber = ByteReader::getInt32(
            inputSocket, "Error reading oldestAvailableFederateSequenceNumber in ResumeRequest message");

      return {lastReceivedRtiSequenceNumber, oldestAvailableFederateSequenceNumber};
   }

   std::unique_ptr<EncodableMessage> ResumeRequestMessage::clone() const
   {
      return std::make_unique<ResumeRequestMessage>(*this);
   }

} // FedPro
