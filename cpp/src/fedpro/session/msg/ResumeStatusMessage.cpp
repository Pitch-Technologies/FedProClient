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

#include "ResumeStatusMessage.h"

#include "ByteInfo.h"
#include "ByteReader.h"
#include "ByteWriter.h"
#include "fedpro/FedProExceptions.h"

namespace FedPro
{
   ResumeStatusMessage::ResumeStatusMessage(
         int32_t sessionStatus,
         int32_t lastReceivedFederateSequenceNumber) noexcept
         : sessionStatus{sessionStatus},
           lastReceivedFederateSequenceNumber{lastReceivedFederateSequenceNumber}
   {
   }

   ByteSequence ResumeStatusMessage::encode() const
   {
      ByteSequence encodedMessage;
      encodedMessage.resize(INT32_SIZE * 2);
      ByteWriter::write(encodedMessage, 0, sessionStatus);
      ByteWriter::write(encodedMessage, sizeof(sessionStatus), lastReceivedFederateSequenceNumber);
      return encodedMessage;
   }

   ResumeStatusMessage ResumeStatusMessage::decode(const Socket & inputSocket)
   {
      int32_t sessionStatus = ByteReader::getInt32(
            inputSocket, "Error reading sessionStatus in ResumeStatus message");
      int32_t lastReceivedFederateSequenceNumber = ByteReader::getInt32(
            inputSocket, "Error reading lastReceivedFederateSequenceNumber in ResumeStatus message");

      if (!(sessionStatus == STATUS_OK_TO_RESUME || sessionStatus == STATUS_FAILURE_INVALID_SESSION ||
            sessionStatus == STATUS_FAILURE_NOT_ENOUGH_BUFFERED_MESSAGES ||
            sessionStatus == STATUS_FAILURE_OTHER_ERROR)) {
         throw BadMessage("Invalid session status in ResumeStatus message, got " + std::to_string(sessionStatus));
      }
      return {sessionStatus, lastReceivedFederateSequenceNumber};
   }

   std::unique_ptr<EncodableMessage> ResumeStatusMessage::clone() const
   {
      return std::make_unique<ResumeStatusMessage>(*this);
   }

   std::string ResumeStatusMessage::toString(int32_t resumeStatus) noexcept
   {
      switch (resumeStatus) {
         case ResumeStatusMessage::STATUS_OK_TO_RESUME:
            return "OK";
         case ResumeStatusMessage::STATUS_FAILURE_INVALID_SESSION:
            return "Invalid Session";
         case ResumeStatusMessage::STATUS_FAILURE_NOT_ENOUGH_BUFFERED_MESSAGES:
            return "Not enough buffered messages";
         case ResumeStatusMessage::STATUS_FAILURE_OTHER_ERROR:
            return "Other error";
         default:
            return "Unknown error";
      }
   }

} // FedPro
