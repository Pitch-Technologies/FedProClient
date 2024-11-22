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

#include <fedpro/FedProExceptions.h>
#include <fedpro/Socket.h>

#ifdef _WIN32
#define _WINSOCK_DEPRECATED_NO_WARNINGS
#pragma warning (disable : 4290) //C4290 is just informing you that other exceptions may still be thrown from these functions.
#pragma comment(lib, "Ws2_32.lib")
#include <WinSock2.h>
#else
#include <netinet/in.h>
#endif

#include <memory>
#include <string>
#include <system_error>

namespace FedPro
{
   /**
    * Low level socket communication.
    */
   class TcpSocket : public Socket
   {
   public:
      TcpSocket(
            std::string address,
            uint16_t port);

      // Todo - move buffer responsibility to session layer?
      static const size_t MAX_BUFFER_SIZE = 65507; // TODO: Figure out a good buffer size

      // Default pending connection queue size for listen().
      static const int DEFAULT_BACKLOG;

      ~TcpSocket() override;

      sockaddr_in getRemoteSocketAddress();

      bool send(
            const char * firstBytePos,
            uint32_t numOfBytes) const override;

      int recv(
            char * firstBytePos,
            uint32_t numOfBytes) const override;

      void close() override;

      void setTcpNoDelay(bool flag) const override;

      void setTimeout(uint32_t timeoutMs) override;

      int getTimeout() const override;

      bool connect() override;

      bool listen(int backlog = DEFAULT_BACKLOG);

      // Accept a TCP connection
      std::unique_ptr<TcpSocket> accept();

   private:

      static std::error_code getLastErrorCode();

#if defined(_WIN32)
      using SocketDescriptor = SOCKET;
#else
      using SocketDescriptor = int;
#endif

      uint16_t _port{0};

      std::string _address;

      SocketDescriptor _socket;

      struct sockaddr_in _remoteAddressC{};

      struct sockaddr_in _sendingLocalAddressC{};

      void setupSocket();

      static void getIPAddressFromHostname(const std::string &hostname, std::string &address);
   };

}
