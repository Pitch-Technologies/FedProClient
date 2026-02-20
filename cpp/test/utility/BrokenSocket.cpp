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

#include "BrokenSocket.h"

#include <fedpro/IOException.h>

bool BrokenSocket::send(
      const char * firstBytePos,
      uint32_t numOfBytes) const
{
   throw FedPro::IOException("Socket is broken");
}

int BrokenSocket::recv(
      char * firstBytePos,
      uint32_t numOfBytes) const
{
   throw FedPro::IOException("Socket is broken");
}

void BrokenSocket::close()
{
}

bool BrokenSocket::connect()
{
   return false;
}

void BrokenSocket::setTcpNoDelay(bool flag) const
{
}

void BrokenSocket::setTimeout(uint32_t timeoutMs)
{
}

int BrokenSocket::getTimeout() const
{
   return 0;
}
