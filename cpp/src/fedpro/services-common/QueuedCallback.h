/***********************************************************************
  Copyright (C) 2026 Pitch Technologies AB

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

#include <cstdint>
#include <memory>

namespace rti1516_2025
{
   namespace fedpro
   {
      class CallbackRequest;
   }
}

namespace FedPro
{

   class QueuedCallback
   {
   public:
      using CallbackRequest = ::rti1516_2025::fedpro::CallbackRequest;

      using SequenceNumber = int32_t;

      enum class Type : uint8_t
      {
         REQUEST, PLACEHOLDER
      };

      std::unique_ptr<CallbackRequest> _callbackRequest;
      SequenceNumber _sequenceNumber{0};
      bool _needsResponse{false};
      Type _type{Type::REQUEST};

      /**
       * Create a callback queue entry with a request.
       */
      QueuedCallback(
            std::unique_ptr<CallbackRequest> callbackRequest,
            SequenceNumber sequenceNumber,
            bool needsResponse);

      /**
       * Create an empty callback queue entry.
       */
      explicit QueuedCallback(Type type);

      QueuedCallback(QueuedCallback &&) noexcept = default;

      QueuedCallback & operator=(QueuedCallback &&) noexcept = default;
   };

} // FedPro
