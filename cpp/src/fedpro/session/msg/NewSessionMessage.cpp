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

#include "NewSessionMessage.h"

#include "ByteInfo.h"
#include "ByteReader.h"
#include "ByteWriter.h"

namespace FedPro
{
   NewSessionMessage::NewSessionMessage(int32_t protocolVersion) noexcept
         : protocolVersion{protocolVersion}
   {
   }

   ByteSequence NewSessionMessage::encode() const
   {
      ByteSequence encodedMessage;
      encodedMessage.resize(INT32_SIZE);
      ByteWriter::write(encodedMessage, 0, protocolVersion);
      return encodedMessage;
   }

   NewSessionMessage NewSessionMessage::decode(const Socket & inputSocket)
   {
      int32_t protocolVersion = ByteReader::getInt32(
            inputSocket, "Error reading protocolVersion in NewSession message");
      return NewSessionMessage{protocolVersion};
   }

   std::unique_ptr<EncodableMessage> NewSessionMessage::clone() const
   {
      return std::make_unique<NewSessionMessage>(*this);
   }

} // FedPro
