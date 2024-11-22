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

#include "ByteReader.h"

#include <fedpro/EofException.h>
#include "utility/bit.h"

#include <iostream>

namespace FedPro
{
   string_view ByteReader::readBytes(size_t byteCount)
   {
      if (_readOffset + byteCount > _bytes.size()) {
         throw EofException{
               "Read failure. Unable to read " + std::to_string(byteCount) + " bytes at position " + std::to_string(_readOffset) +
               " with only " + std::to_string(_bytes.size() - _readOffset ) + " bytes available. "};
      }

      string_view nextBytes = _bytes.substr(_readOffset, byteCount);
      _readOffset += byteCount;
      return nextBytes;
   }

   int32_t ByteReader::readInt32()
   {
      string_view nextBytes = readBytes(INT32_SIZE);
      const auto * resultAddress = reinterpret_cast<const int32_t *>(nextBytes.data());
      int32_t result = *resultAddress;
      if (FedPro::endian::native == FedPro::endian::little) {
         return byteswap(result);
      }
      return result;
   }

   uint64_t ByteReader::readUint64()
   {
      string_view nextBytes = readBytes(sizeof(uint64_t));
      const auto * resultAddress = reinterpret_cast<const uint64_t *>(nextBytes.data());
      uint64_t result = *resultAddress;
      if (FedPro::endian::native == FedPro::endian::little) {
         return byteswap(result);
      }
      return result;
   }

} // FedPro
