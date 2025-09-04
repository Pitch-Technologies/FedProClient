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

#include "WebsocketTransport.h"

#include <fedpro/Socket.h>
#include "TransportSettings.h"

#include <memory>
#include <string>

namespace FedPro
{
   WebsocketTransport::WebsocketTransport(const Properties & settings)
         : TransportImplBase{settings.getString(SETTING_NAME_CONNECTION_HOST, DEFAULT_CONNECTION_HOST),
                             settings.getUnsignedInt16(SETTING_NAME_CONNECTION_PORT, DEFAULT_PORT_WS)}
   {
   }

   std::unique_ptr<FedPro::Socket> WebsocketTransport::doConnect(
         const std::string & host,
         uint16_t port)
   {
      // TODO
      return {};
   }
}