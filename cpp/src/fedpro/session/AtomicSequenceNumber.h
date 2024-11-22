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

#include "SequenceNumber.h"

#include <cstdint>
#include <mutex>

namespace FedPro
{
   class AtomicSequenceNumber : public SequenceNumber
   {
   public:
      explicit AtomicSequenceNumber(int32_t value);

      AtomicSequenceNumber & increment() noexcept override;

      void set(const SequenceNumber & newNumber) override;

      void set(int32_t newValue) override;

      int32_t get() const noexcept override;

      SequenceNumber getAndSet(int32_t newValue);

   private:
      mutable std::mutex _mutex;
   };

} // FedPro
