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

#include "HlaCallbackRequestMessage.h"

#include "ByteReader.h"

namespace FedPro
{
   HlaCallbackRequestMessage::HlaCallbackRequestMessage(ByteSequence hlaServiceCallbackWithParams) noexcept
         : hlaServiceCallbackWithParams{std::move(hlaServiceCallbackWithParams)}
   {
   }

   ByteSequence HlaCallbackRequestMessage::encode() const
   {
      return hlaServiceCallbackWithParams;
   }

   HlaCallbackRequestMessage HlaCallbackRequestMessage::decode(
         const Socket & inputSocket,
         uint32_t length)
   {
      ByteSequence hlaServiceCallWithParams{inputSocket.recvNBytes(length)};
      return HlaCallbackRequestMessage{hlaServiceCallWithParams};
   }

   std::unique_ptr<EncodableMessage> HlaCallbackRequestMessage::clone() const
   {
      return std::make_unique<HlaCallbackRequestMessage>(*this);
   }

} // FedPro
