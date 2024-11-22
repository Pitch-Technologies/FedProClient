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

#include <cstdint>
#include <string>

// Because of its wrap around behavior, the sequence number will never be negative unless explicitly set to invalid
// sequence number.
// Not thread-safe.

/*
   NO_SEQUENCE_NUMBER:
   Binary:     10000000 0000000 00000000 00000000
   Hex:        80 00 00 00
   Decimal:    2 147 483 648
   int32_t:    INT32_MIN

   Highest possible sequence number:
   Binary:     01111111 11111111 11111111 11111111
   Hex:        7F FF FF FF
   Decimal:    2 147 483 647
   int32_t:    INT32_MAX
*/

namespace FedPro
{
   class SequenceNumber
   {
   public:
      static constexpr int32_t INITIAL_SEQUENCE_NUMBER = 0;
      static constexpr int32_t MAX_SEQUENCE_NUMBER = INT32_MAX;
      static constexpr int32_t NO_SEQUENCE_NUMBER = INT32_MIN;

      explicit SequenceNumber(int32_t value);

      virtual ~SequenceNumber() = default;

      virtual SequenceNumber & increment() noexcept;

      virtual void set(const SequenceNumber & newNumber);

      virtual void set(int32_t newValue);

      virtual int32_t get() const noexcept;

      static bool isValidAsSequenceNumber(int32_t value) noexcept;

      static bool inInterval(
            int32_t candidate,
            int32_t oldest,
            int32_t newest) noexcept;

      static bool notInInterval(
            int32_t candidate,
            int32_t oldest,
            int32_t newest) noexcept;

      std::string toString() const noexcept;

      static int32_t nextAfter(int32_t number);

   private:
      int32_t _value;

      static void validateArguments(std::initializer_list<int32_t> values);

   };

} // FedPro
