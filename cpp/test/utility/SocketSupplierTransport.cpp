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

#include "SocketSupplierTransport.h"

#include <fedpro/Socket.h>

SocketSupplierTransport::SocketSupplierTransport(SocketSupplier socketSupplier)
      : _socketSupplier{std::move(socketSupplier)}
{
}

SocketSupplierTransport::SocketSupplierTransport(std::shared_ptr<FedPro::Socket> socket)
{
   _socketSupplier = [socket = std::move(socket)]() {
      return socket;
   };
}

SocketSupplierTransport::SocketSupplierTransport(const SocketSupplierTransport & transport) noexcept
      : _socketSupplier{transport._socketSupplier}
{
}

SocketSupplierTransport::~SocketSupplierTransport() = default;

std::shared_ptr<FedPro::Socket> SocketSupplierTransport::connect()
{
   return _socketSupplier();
}
