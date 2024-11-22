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

#include <fedpro/Socket.h>

#include <fedpro/EofException.h>

#include <string>

namespace FedPro
{
   Socket::Socket()  = default;

   Socket::~Socket() = default;

   int Socket::recv(
         char * firstBytePos,
         uint32_t minByteCount,
         uint32_t maxByteCount) const
   {
      int readInTotal = 0;

      while (readInTotal < minByteCount) {
         // Blocks when there is nothing to read, unless there is a std::ios_base::failure or EofException.
         int bytesRead = recv(firstBytePos + readInTotal, maxByteCount - readInTotal);

         if (bytesRead < 1) {
            // EOF was reached or an unhandled error code was returned.
            break;
         }
         readInTotal += bytesRead;
      }

      if (readInTotal < minByteCount) {
         throw EofException{"Could only read " + std::to_string(readInTotal) + " of " +
                            std::to_string(minByteCount) + " bytes. "};
      }

      return readInTotal;
   }

   ByteSequence Socket::recvNBytes(uint32_t byteCount) const
   {
      ByteSequence bytes;
      bytes.resize(byteCount);
      recv(const_cast<char *>(bytes.data()), byteCount, byteCount);
      return bytes;
   }
}
