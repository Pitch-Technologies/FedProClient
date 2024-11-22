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

#include <cstdint>
#include <stdexcept>
#include <string>
#include <type_traits>
#include <typeinfo>
#include <utility>

#pragma once

namespace FedPro
{
   enum class endian
   {
      little = 0, big = 1,
#if defined BYTE_ORDER_IS_LITTLE
      native = little
#elif defined BYTE_ORDER_IS_BIG
      native = big
#endif
   };

   // Performs in-place byte-wise swapping of the provided buffer.
   // This effectively converts between little-endian and big-endian.
   inline void byteswap(uint8_t * dataBuffer, size_t dataSize) noexcept
   {
      for (size_t i = 0; i < dataSize/2; ++i) {
         size_t j = dataSize-i-1;
         std::swap(dataBuffer[i], dataBuffer[j]);
      }
   }

   template<typename T, typename std::enable_if_t<std::is_scalar<T>::value, bool> = true>
   T byteswap(T value) noexcept
   {
      T swappedValue{value};
      byteswap(reinterpret_cast<uint8_t *>(&swappedValue), sizeof(swappedValue));
      return swappedValue;
   }

   template<typename T, typename std::enable_if_t<std::is_scalar<T>::value, bool> = true>
   T nativeToBigEndian(T nativeValue)
   {
      if (endian::native == endian::big) {
         return nativeValue;
      } else {
         return byteswap(nativeValue);
      }
   }

   template<typename T, typename std::enable_if_t<std::is_scalar<T>::value, bool> = true>
   T nativeToBigEndian(const void * dataBuffer, size_t dataSize)
   {
      if (dataSize != sizeof(T)) {
         throw std::invalid_argument(std::string("Buffer size does not match size of ") + typeid(T).name());
      }

      const T & nativeValue = *reinterpret_cast<const T *>(dataBuffer);
      return nativeToBigEndian(nativeValue);
   }

   template<typename T, typename std::enable_if_t<std::is_scalar<T>::value, bool> = true>
   T bigEndianToNative(T bigValue)
   {
      if (endian::native == endian::big) {
         return bigValue;
      } else {
         return byteswap(bigValue);
      }
   }

   template<typename T, typename std::enable_if_t<std::is_scalar<T>::value, bool> = true>
   T bigEndianToNative(const void * dataBuffer, size_t dataSize)
   {
      if (dataSize != sizeof(T)) {
         throw std::invalid_argument(std::string("Buffer size does not match size of ") + typeid(T).name());
      }

      const T & bigValue = *reinterpret_cast<const T *>(dataBuffer);
      return bigEndianToNative(bigValue);
   }

} // FedPro
