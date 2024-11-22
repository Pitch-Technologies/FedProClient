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

#include "MemorySocket.h"

#include <fedpro/Transport.h>
#include <utility/string_view.h>

#include <string>

class MemoryTransport : public FedPro::Transport
{
public:

   MemoryTransport();

   void addInboundPacket(
         FedPro::string_view packetBytes);

   std::shared_ptr<FedPro::Socket> connect() override;

private:

   std::shared_ptr<MemorySocket> _socket;

};
