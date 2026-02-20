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

#include "MemorySocket.h"

#include <fedpro/EofException.h>

#include <algorithm>
#include <cstring>
#include <limits>

using namespace FedPro;

MemorySocket::MemorySocket() = default;

void MemorySocket::addInboundPacket(
      FedPro::string_view packetBytes)
{
   std::unique_lock<std::mutex> lock(_mutex);
   _inboundMemoryBuffer.append(packetBytes.data(), packetBytes.size());
   _condition.notify_all();
}

bool MemorySocket::send(
      const char * firstBytePos,
      uint32_t numOfBytes) const
{
   _outboundMemoryBuffer.append(firstBytePos, firstBytePos + numOfBytes);
   return true;
}

int MemorySocket::recv(
      char * firstBytePos,
      uint32_t numOfBytes) const
{
   std::unique_lock<std::mutex> lock(_mutex);
   while (_open && _inboundMemoryBuffer.empty()) {
      _condition.wait(lock);
   }

   if (!_open) {
      throw EofException("Cannot receive with a closed socket");
   }

   size_t bytesRead = std::min({
         static_cast<size_t>(numOfBytes),
          _inboundMemoryBuffer.size(),
          static_cast<size_t>(std::numeric_limits<int>::max())});

   std::memcpy(firstBytePos, _inboundMemoryBuffer.data(), bytesRead);
   _inboundMemoryBuffer.erase(0, bytesRead);

   return static_cast<int>(bytesRead);
}

bool MemorySocket::connect()
{
   _open = true;
   std::lock_guard<std::mutex> lock(_mutex);
   _condition.notify_all();
   return true;
}

void MemorySocket::close()
{
   _open = false;
   std::lock_guard<std::mutex> lock(_mutex);
   _condition.notify_all();
}