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

#include "NewSessionStatusMessage.h"

#include "ByteInfo.h"
#include "ByteReader.h"
#include "ByteWriter.h"
#include "fedpro/FedProExceptions.h"

namespace FedPro
{
   NewSessionStatusMessage::NewSessionStatusMessage(int32_t reason) noexcept
         : reason{reason}
   {
   }

   ByteSequence NewSessionStatusMessage::encode() const
   {
      ByteSequence encodedMessage;
      encodedMessage.resize(INT32_SIZE);
      ByteWriter::write(encodedMessage, 0, reason);
      return encodedMessage;
   }

   NewSessionStatusMessage NewSessionStatusMessage::decode(const Socket & inputSocket)
   {
      int32_t reason = ByteReader::getInt32(inputSocket, "Error reading reason of NewSessionStatus message");

      if (!(reason == REASON_SUCCESS || reason == REASON_FAILURE_UNSUPPORTED_PROTOCOL_VERSION ||
            reason == REASON_FAILURE_OUT_OF_RESOURCES || reason == REASON_FAILURE_OTHER_ERROR)) {
         throw BadMessage("Invalid reason in NewSessionStatus message, got " + std::to_string(reason));
      }
      return NewSessionStatusMessage{reason};
   }

   std::unique_ptr<EncodableMessage> NewSessionStatusMessage::clone() const
   {
      return std::make_unique<NewSessionStatusMessage>(*this);
   }

} // FedPro
