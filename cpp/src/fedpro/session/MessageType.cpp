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

#include "MessageType.h"

#include <string>

namespace FedPro
{

   int32_t MessageTypeHelper::asInt(MessageType type) noexcept
   {
      return static_cast<int32_t>(type);
   }

   uint32_t MessageTypeHelper::asUnsigned(MessageType type) noexcept
   {
      return static_cast<uint32_t>(type);
   }

   bool MessageTypeHelper::isControl(MessageType type) noexcept
   {
      return type < MessageType::HLA_CALL_REQUEST;
   }

   bool MessageTypeHelper::isHlaResponse(MessageType type) noexcept
   {
      return type == MessageType::HLA_CALL_RESPONSE || type == MessageType::HLA_CALLBACK_RESPONSE;
   }

   MessageType MessageTypeHelper::fromUnsigned(uint32_t type) noexcept
   {
      auto it = MESSAGE_TYPE_MAP.find(type);
      if (it == MESSAGE_TYPE_MAP.end()) {
         return MessageType::UNKNOWN;
      }
      return MessageType{it->second.first};
   }

   std::string MessageTypeHelper::asString(MessageType type) noexcept
   {
      return MESSAGE_TYPE_MAP.at(asUnsigned(type)).second;
   }

   bool MessageTypeHelper::isValid(uint32_t candidate) noexcept
   {
      return MESSAGE_TYPE_MAP.find(candidate) != MESSAGE_TYPE_MAP.end();
   }

} // FedPro
