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

namespace FedPro
{
   class HlaCallResponseMessage : public EncodableMessage
   {
   public:
      const int32_t responseToSequenceNumber;
      const ByteSequence hlaServiceReturnValueOrException;

      HlaCallResponseMessage(
            int32_t responseToSequenceNumber,
            ByteSequence && hlaServiceReturnValueOrException) noexcept;

      ~HlaCallResponseMessage() override = default;

      ByteSequence encode() const override;

      static HlaCallResponseMessage decode(
            const Socket & inputSocket,
            uint32_t length);

      std::unique_ptr<EncodableMessage> clone() const override;
   };

} // FedPro