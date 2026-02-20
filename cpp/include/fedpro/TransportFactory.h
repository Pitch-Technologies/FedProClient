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

#pragma once

#include "Config.h"

#include <memory>

namespace FedPro
{
   class Properties;
   class Transport;

   class TransportFactory
   {
   public:

      /**
       * @brief Create a new TCP specific transport instance with specified server settings,
       * or their default values if not specified.
       *
       * @param settings The Properties object instance which transport layer settings will
       * be loaded from. Unprovided settings will get default values and non-transport
       * settings will be ignored.
       *
       * @return An unique pointer to a new Transport instance.
       */
      FEDPRO_EXPORT static std::unique_ptr<Transport> createTcpTransport(const Properties & settings);

      /**
       * @brief Create a new TLS specific transport instance with specified server settings,
       * or their default values if not specified.
       *
       * @param settings The Properties object instance which transport layer settings will
       * be loaded from. Unprovided settings will get default values and non-transport
       * settings will be ignored.
       *
       * @return An unique pointer to a new Transport instance.
       */
      FEDPRO_EXPORT static std::unique_ptr<Transport> createTlsTransport(const Properties & settings);

      /**
       * @brief Create a new Web Socket specific transport instance with specified settings,
       * or their default values if not specified.
       *
       * @param settings The Properties object instance which transport layer settings will
       * be loaded from. Unprovided settings will get default values and non-transport
       * settings will be ignored.
       *
       * @return An unique pointer to a new Transport instance.
       */
      FEDPRO_EXPORT static std::unique_ptr<Transport> createWebSocketTransport(const Properties & settings);

      /**
       * @brief Create a new Secure Web Socket specific transport instance with specified
       * settings, or their default values if not specified.
       *
       * @param settings The Properties object instance which transport layer settings will
       * be loaded from. Unprovided settings will get default values and non-transport
       * settings will be ignored.
       *
       * @return An unique pointer to a new Transport instance.
       */
      FEDPRO_EXPORT static std::unique_ptr<Transport> createWebSocketSecureTransport(const Properties & settings);

      FEDPRO_EXPORT virtual ~TransportFactory();

      /**
       * Create a transport given the provided connection settings.
       *
       * @return An unique pointer to a new Transport instance.
       *
       * @throws std::runtime_error if the transport protocol is not supported.
       */
      FEDPRO_EXPORT virtual std::unique_ptr<Transport> createTransport(const Properties & settings);
   };

} // FedPro
