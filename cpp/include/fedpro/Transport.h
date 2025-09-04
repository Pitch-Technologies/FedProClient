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

#include <memory>

namespace FedPro
{
   class Socket;

   /**
    * @brief Interface for representing a transport protocol.
    */
   class FEDPRO_EXPORT Transport
   {
      friend class Session;

   public:

      // Todo - move destructor to a cpp file
      virtual ~Transport() = default;

      /**
       * @brief Establishes a connection with a server through the host IP address and
       * port specified as arguments to the constructor of transport this instance.
       *
       * @throws std::ios_base::failure If an IO error occurs and no connection to the
       * server is successfully established.
       * @return A shared pointer to a socket instance.
       */
      virtual std::shared_ptr<Socket> connect() = 0;
   };

} // FedPro
