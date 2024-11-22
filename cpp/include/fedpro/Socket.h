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

#include <fedpro/Config.h>

#include <cstdint>
#include <string>

namespace FedPro
{
   using ByteSequence = std::string;

   class FEDPRO_EXPORT Socket
   {
   public:
      Socket();

      virtual ~Socket();

      virtual bool send(
            const char * buf,
            uint32_t numOfBytes) const = 0;

      /**
       * @brief Receive data from the socket. Note a socket may receive as little as 1
       * byte.
       *
       * @param buf A buffer large enough to store maxByteCount bytes.
       * @param maxByteCount Maximum number of bytes to read.
       */
      virtual int recv(
            char * buf,
            uint32_t maxByteCount) const = 0;

      /**
       * @brief Receive data from the socket.
       * Internally, there may be multiple `recv(...)` calls to ensure reception of at
       * least minByteCount bytes.
       *
       * @param buf A buffer large enough to store maxByteCount bytes.
       * @param minByteCount Minimum number of bytes to read.
       * @param maxByteCount Maximum number of bytes to read.
       */
      int recv(
            char * buf,
            uint32_t minByteCount,
            uint32_t maxByteCount) const;

      /**
       * @brief Read from the socket exactly byteCount bytes.
       * Internally, there may be multiple `recv(...)` calls to ensure reception of
       * byteCount bytes.
       *
       * @param byteCount Number of bytes to read.
       */
      ByteSequence recvNBytes(uint32_t byteCount) const;

      virtual bool connect() = 0;

      virtual void close() = 0;

      virtual void setTcpNoDelay(bool flag) const = 0;

      /**
       * @brief Sets the interval between checks for socket status. Function calls to
       * read from the socket will unblock and exit if the socket is closed when such
       * a check occurs.
       *
       * @param timeoutMs The time in milliseconds that a thread will spend waiting
       * before checking the socket status.
       */
      virtual void setTimeout(uint32_t timeoutMs) = 0;

      virtual int getTimeout() const = 0;
   };
}
