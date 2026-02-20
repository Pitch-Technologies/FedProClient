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

#include <fedpro/Socket.h>
#include <utility/string_view.h>

#include <atomic>
#include <condition_variable>
#include <limits>
#include <mutex>
#include <string>

class MemorySocket : public FedPro::Socket
{
public:

   MemorySocket();

   void addInboundPacket(
         FedPro::string_view packetBytes);

   bool send(
         const char * firstBytePos,
         uint32_t numOfBytes) const override;

   int recv(
         char * firstBytePos,
         uint32_t numOfBytes) const override;

   bool connect() override;

   void close() override;

   void setTcpNoDelay(bool) const override
   {
   }

   void setTimeout(uint32_t) override
   {
   }

   int getTimeout() const override
   {
      return std::numeric_limits<int>::max();
   }

private:

   mutable std::mutex _mutex;

   std::atomic<bool> _open{false};

   // Signals arrival of packets, opening and closing of socket.
   mutable std::condition_variable _condition; // GUARDED_BY(_mutex)

   mutable std::string _inboundMemoryBuffer; // GUARDED_BY(_mutex)

   mutable std::string _outboundMemoryBuffer;

};
