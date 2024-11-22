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

#include "TransportImplBase.h"

#include <fedpro/Settings.h>

#include <spdlog/spdlog.h>

#include <iostream>
#include <memory>
#include <string>

namespace FedPro
{
   TransportImplBase::TransportImplBase(
         std::string host,
         uint16_t port)
         : _host{std::move(host)},
           _port{port}
   {
      SPDLOG_INFO("Federate Protocol client transport layer setting used:\n" + getSettings().toPrettyString());
   }

   std::shared_ptr<FedPro::Socket> TransportImplBase::connect()
   {
      try {
         std::shared_ptr<FedPro::Socket> socket = doConnect(_host, _port);
         if (socket) {
            socket->setTcpNoDelay(true);
         }
         return socket;
      } catch (const std::ios_base::failure & e) {
         throw e;
      } catch (...) {
         // If throwing any exception other than std::ios_base::failure,
         // catch it and re-throw with a generic error message.
         throw std::ios_base::failure("Could not connect");
      }
   }

   Properties TransportImplBase::getSettings() const
   {
      Properties settings{};
      settings.setString(SETTING_NAME_CONNECTION_HOST, _host);
      settings.setUnsignedInt16(SETTING_NAME_CONNECTION_PORT, _port);
      return settings;
   }

}
