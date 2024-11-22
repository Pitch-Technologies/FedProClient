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

#include <fedpro/Settings.h>

namespace FedPro
{
   // Defaults ports for each protocol
   static constexpr uint16_t DEFAULT_PORT_TCP = 15164;
   static constexpr uint16_t DEFAULT_PORT_TLS = 15165;
   static constexpr uint16_t DEFAULT_PORT_WS = 80;
   static constexpr uint16_t DEFAULT_PORT_WSS = 443;

   // Default values
   static constexpr const char * DEFAULT_CONNECTION_HOST{"localhost"};
   static constexpr const uint16_t DEFAULT_CONNECTION_PORT{DEFAULT_PORT_TCP};

}
