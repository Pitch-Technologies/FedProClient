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

   class NewSessionStatusMessage : public EncodableMessage
   {
   public:

      static const int32_t REASON_SUCCESS = 0;
      static const int32_t REASON_FAILURE_UNSUPPORTED_PROTOCOL_VERSION = 1;
      static const int32_t REASON_FAILURE_OUT_OF_RESOURCES = 2;

      // Suggestion for the HlA 4 standard:
      static const int32_t REASON_FAILURE_BAD_MESSAGE = 3;
      static const int32_t REASON_FAILURE_OTHER_ERROR = 99;

      const int32_t reason;

      explicit NewSessionStatusMessage(int32_t reason) noexcept;

      ~NewSessionStatusMessage() override = default;

      ByteSequence encode() const override;

      static NewSessionStatusMessage decode(const Socket & inputSocket);

      std::unique_ptr<EncodableMessage> clone() const override;

   };

} // FedPro
