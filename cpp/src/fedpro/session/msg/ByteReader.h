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

#include "ByteInfo.h"
#include <fedpro/Aliases.h>
#include <fedpro/Socket.h>
#include "utility/string_view.h"

#include <cstdint>

namespace FedPro
{
   class ByteReader
   {
   public:

      // TODO remove static function, in favor of the non-static readInt32
      static inline int32_t getInt32(
            const Socket & inputSocket,
            const char * errorMessage = "Error reading from socket")
      {
         char readBuffer[INT32_SIZE];
         inputSocket.recv(readBuffer, sizeof(readBuffer), sizeof(readBuffer));
         ByteReader reader(string_view{readBuffer, sizeof(readBuffer)});
         return reader.readInt32();
      }

      constexpr explicit ByteReader(string_view bytes)
            : _bytes(bytes)
      {
      }

      string_view readBytes(size_t byteCount);

      int32_t readInt32();

      uint64_t readUint64();

   private:

      size_t _readOffset{0};

      string_view _bytes;
   };

} // FedPro
