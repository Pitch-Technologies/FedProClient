/*
 *  Copyright (C) 2023-2026 Pitch Technologies AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include "TransportFactoryImpl.h"
#include <fedpro/TransportFactory.h>

#include <fedpro/Properties.h>
#include <fedpro/Settings.h>
#include "TcpTransport.h"
#include "TlsTransport.h"
#include "WebsocketTransport.h"
#include "WebsocketSecureTransport.h"

#include <stdexcept>

namespace FedPro
{
   TransportFactory::~TransportFactory() = default;

   std::unique_ptr<Transport> TransportFactory::createTransport(const Properties & settings)
   {
      std::string protocol{settings.getString(SETTING_NAME_CONNECTION_PROTOCOL, TCP)};
      if (protocol == TLS) {
         return createTlsTransport(settings);
      } else if (protocol == WS) {
         return createWebSocketTransport(settings);
      } else if (protocol == WSS) {
         return createWebSocketSecureTransport(settings);
      } else if (protocol == TCP) {
         return createTcpTransport(settings);
      } else {
         throw std::runtime_error(
               "Invalid network protocol '" + protocol + "' in settings");
      }
   }

   std::unique_ptr<Transport> TransportFactory::createTcpTransport(const Properties & settings)
   {
      return std::make_unique<TcpTransport>(settings);
   }

   std::unique_ptr<Transport> TransportFactory::createTlsTransport(const Properties & settings)
   {
      return std::make_unique<TlsTransport>(settings);
   }

   std::unique_ptr<Transport> TransportFactory::createWebSocketTransport(const Properties & settings)
   {
      return std::make_unique<WebsocketTransport>(settings);
   }

   std::unique_ptr<Transport> TransportFactory::createWebSocketSecureTransport(const Properties & settings)
   {
      return std::make_unique<WebsocketSecureTransport>(settings);
   }

   namespace Internal
   {

      static std::shared_ptr<TransportFactory> _transportFactory = std::make_unique<TransportFactory>();

      FEDPRO_EXPORT void resetTransportFactory()
      {
         _transportFactory = std::make_unique<TransportFactory>();
      }

      FEDPRO_EXPORT void setTransportFactory(std::unique_ptr<TransportFactory> transportFactory)
      {
         _transportFactory = std::move(transportFactory);
      }

      FEDPRO_EXPORT std::unique_ptr<Transport> createTransport(const Properties & settings)
      {
         if (!_transportFactory) {
            throw std::runtime_error{"No TransportFactory available"};
         }
         return _transportFactory->createTransport(settings);
      }

   } // Internal

} // FedPro
