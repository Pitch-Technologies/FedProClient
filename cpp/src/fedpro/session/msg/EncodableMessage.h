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

#include <fedpro/Aliases.h>
#include <fedpro/Socket.h>

#include <memory>
#include <ostream>

namespace FedPro
{
   class EncodableMessage
   {
   public:

      EncodableMessage() = default;

      virtual ~EncodableMessage() = default;

      EncodableMessage(const EncodableMessage &) = default;

      EncodableMessage & operator=(const EncodableMessage &) = default;

      EncodableMessage(EncodableMessage &&) noexcept = default;

      EncodableMessage & operator=(EncodableMessage &&) noexcept = default;

      virtual ByteSequence encode() const = 0;

      virtual std::unique_ptr<EncodableMessage> clone() const = 0;

      friend std::ostream & operator<<(
            std::ostream & os,
            const EncodableMessage & message);

   };

} // FedPro
