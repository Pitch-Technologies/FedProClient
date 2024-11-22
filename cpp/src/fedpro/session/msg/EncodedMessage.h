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

#include "EncodableMessage.h"
#include "MessageHeader.h"
#include "../SequenceNumber.h"

#include <cstdint>
#include <sstream>

namespace FedPro
{
   class EncodedMessage
   {
   public:

      int32_t sequenceNumber{};
      bool isControl{};
      ByteSequence data;

      EncodedMessage() = default;

      EncodedMessage(
            int32_t sequenceNumber,
            bool isControl,
            ByteSequence data) noexcept;

      static EncodedMessage create(
            const MessageHeader & header,
            EncodableMessage * message);

      std::string toString() const noexcept;

      friend std::ostream & operator<<(
            std::ostream & os,
            const EncodedMessage & message);

   };

} // FedPro
