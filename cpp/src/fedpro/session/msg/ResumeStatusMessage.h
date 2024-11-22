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
   class ResumeStatusMessage : public EncodableMessage
   {
   public:

      static const int32_t STATUS_OK_TO_RESUME = 0;
      static const int32_t STATUS_FAILURE_INVALID_SESSION = 1;
      static const int32_t STATUS_FAILURE_NOT_ENOUGH_BUFFERED_MESSAGES = 2;
      static const int32_t STATUS_FAILURE_OTHER_ERROR = 99;

      int32_t sessionStatus;
      int32_t lastReceivedFederateSequenceNumber; // LRF

      ResumeStatusMessage(
            int32_t sessionStatus,
            int32_t lastReceivedFederateSequenceNumber) noexcept;

      ~ResumeStatusMessage() override = default;

      ByteSequence encode() const override;

      static ResumeStatusMessage decode(const Socket & inputSocket);

      std::unique_ptr<EncodableMessage> clone() const override;

      static std::string toString(int32_t resumeStatus) noexcept;
   };

} // FedPro
