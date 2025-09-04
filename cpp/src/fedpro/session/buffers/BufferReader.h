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

#include "BufferPeeker.h"
#include <fedpro/Aliases.h>
#include "../SessionUtils.h"

#include <atomic>
#include <cstddef>
#include <cstdint>
#include <memory>
#include <string>

namespace FedPro
{

   template<typename E>
   class BufferReader : public BufferPeeker<E>
   {
   public:

      using size_type = size_t;

      using typename BufferPeeker<E>::peek_type;

      virtual ~BufferReader() = default;

      virtual peek_type peek() = 0;

      virtual peek_type waitAndPeek() = 0;

      virtual std::unique_ptr<E> waitAndPoll() = 0;

      virtual bool waitUntilEmpty(FedProDuration timeout) = 0;

      virtual std::unique_ptr<E> poll() = 0;

      virtual size_type size() noexcept = 0;

      virtual bool isEmpty() noexcept = 0;

      virtual size_type capacity() const noexcept = 0;

      virtual void interrupt(bool interrupt) noexcept = 0;

      virtual std::string toString() noexcept = 0;

   };

} // FedPro
