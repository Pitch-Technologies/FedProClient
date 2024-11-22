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

#include "AtomicSequenceNumber.h"

namespace FedPro
{
   AtomicSequenceNumber::AtomicSequenceNumber(int32_t value)
         : SequenceNumber{value},
           _mutex{}
   {
   }

   AtomicSequenceNumber & AtomicSequenceNumber::increment() noexcept
   {
      std::lock_guard<std::mutex> lock(_mutex);
      SequenceNumber::increment();
      return *this;
   }

   void AtomicSequenceNumber::set(const SequenceNumber & newNumber)
   {
      std::lock_guard<std::mutex> lock(_mutex);
      SequenceNumber::set(newNumber);
   }

   void AtomicSequenceNumber::set(int32_t newValue)
   {
      std::lock_guard<std::mutex> lock(_mutex);
      SequenceNumber::set(newValue);
   }

   int32_t AtomicSequenceNumber::get() const noexcept
   {
      std::lock_guard<std::mutex> lock(_mutex);
      return SequenceNumber::get();
   }

   SequenceNumber AtomicSequenceNumber::getAndSet(int32_t newValue)
   {
      int32_t oldValue;
      {
         std::lock_guard<std::mutex> lock(_mutex);
         oldValue = SequenceNumber::get();
         SequenceNumber::set(newValue);
      }
      return SequenceNumber{oldValue};
   }

} // FedPro
