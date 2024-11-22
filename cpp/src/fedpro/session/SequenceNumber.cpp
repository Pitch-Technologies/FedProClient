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

#include "SequenceNumber.h"

#include <iostream>
#include <stdexcept>

namespace FedPro
{
   const int32_t SequenceNumber::NO_SEQUENCE_NUMBER;
   const int32_t SequenceNumber::INITIAL_SEQUENCE_NUMBER;
   const int32_t SequenceNumber::MAX_SEQUENCE_NUMBER;

   SequenceNumber::SequenceNumber(int32_t value)
   {
      _value = value;
   }

   SequenceNumber & SequenceNumber::increment() noexcept
   {
      if (_value == MAX_SEQUENCE_NUMBER || !isValidAsSequenceNumber(_value)) {
         _value = INITIAL_SEQUENCE_NUMBER;
      } else {
         _value++;
      }
      return *this;
   }

   void SequenceNumber::set(const SequenceNumber & newNumber)
   {
      set(newNumber.get());
   }

   void SequenceNumber::set(int32_t newValue)
   {
      _value = newValue;
   }

   int32_t SequenceNumber::get() const noexcept
   {
      return _value;
   }

   bool SequenceNumber::isValidAsSequenceNumber(int32_t value) noexcept
   {
      return value > -1;
   }

   bool SequenceNumber::inInterval(
         int32_t candidate,
         int32_t oldest,
         int32_t newest) noexcept
   {
      validateArguments({candidate, oldest, newest});
      if (oldest <= newest) {
         // When history is linear.
         return oldest <= candidate && candidate <= newest;
      } else {
         // When wrap around happens in history.
         return candidate <= newest || oldest <= candidate;
      }
   }

   bool SequenceNumber::notInInterval(
         int32_t candidate,
         int32_t oldest,
         int32_t newest) noexcept
   {
      return !inInterval(candidate, oldest, newest);
   }

   std::string SequenceNumber::toString() const noexcept
   {
      return std::to_string(_value);
   }

   int32_t SequenceNumber::nextAfter(int32_t number)
   {
      if (number == MAX_SEQUENCE_NUMBER || !isValidAsSequenceNumber(number)) {
         return INITIAL_SEQUENCE_NUMBER;
      }
      return number + 1;
   }

   // Private methods

   void SequenceNumber::validateArguments(std::initializer_list<int32_t> values)
   {
      for (int32_t value : values) {
         if (!isValidAsSequenceNumber(value)) {
            throw std::invalid_argument(
                  "Sequence number argument " + std::to_string(value) + " is invalid, must be in range " +
                  std::to_string(INITIAL_SEQUENCE_NUMBER) + " to " + std::to_string(MAX_SEQUENCE_NUMBER));
         }
      }
   }

} // FedPro
