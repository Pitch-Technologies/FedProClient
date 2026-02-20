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

#include "TcpSocket.h"

#include <fedpro/IOException.h>

#if defined(_WIN32)
#pragma comment(lib, "iphlpapi.lib")

#include <iphlpapi.h>

#else
#include <unistd.h>
#include <sys/socket.h>
#include <sys/time.h>
#include <netinet/in.h>
#include <netdb.h>
#include <sys/select.h>
#include <sys/ioctl.h>
#include <string.h>
#include <arpa/inet.h>
#include <errno.h>
#include <ifaddrs.h>
#endif

#include <algorithm>
#include <iostream>
#include <limits>
#include <sstream>
#include <algorithm>
#include <cstring>

namespace FedPro
{

#if defined(_WIN32)
#undef min
#undef max
#undef errno
#define errno WSAGetLastError()
   typedef u_long in_addr_t;
   typedef int socklen_t;
#else
#define SOCKET_ERROR (-1)
#define INVALID_SOCKET (-1)
#endif

   std::error_code TcpSocket::getLastErrorCode()
   {
#if defined(_WIN32)
      return std::error_code{WSAGetLastError(), std::system_category()};
#else
      return std::error_code{errno, std::system_category()};
#endif
   }

   const int TcpSocket::DEFAULT_BACKLOG{SOMAXCONN};

   TcpSocket::TcpSocket(
         std::string address,
         uint16_t port)
         : _port{port},
           _address{std::move(address)},
           _socket{INVALID_SOCKET}
   {
#if defined(_WIN32)
      WSADATA wsaData;
      if (WSAStartup(MAKEWORD(2, 0), &wsaData) != 0) {
         throw IOException("Could not create socket", getLastErrorCode());
      }
#endif

      // Try to convert hostnames to numerical IP addresses
      if (!_address.empty()) {
         getIPAddressFromHostname(_address, _address);
      }

      setupSocket();
   }

   TcpSocket::~TcpSocket()
   {
      TcpSocket::close();
#if defined(_WIN32)
      WSACleanup();
#endif
   }

   sockaddr_in TcpSocket::getRemoteSocketAddress()
   {
      return _remoteAddressC;
   }

   int TcpSocket::recv(
         char * buf,
         uint32_t numOfBytes) const
   {
      long n{0};
#if defined(_WIN32)
      // Avoid int overflow on Windows
      uint32_t maxReadBytes = std::min<uint32_t>(numOfBytes, std::numeric_limits<int>::max());
      n = ::recv(_socket, buf, static_cast<int>(maxReadBytes), 0);
#else
      size_t fulfilled{0};
      size_t maxReadBytes{numOfBytes};
      do {
         if (_socket == INVALID_SOCKET) {
            break;
         }
         errno = 0;
         n = ::recv(_socket, buf + fulfilled, maxReadBytes - fulfilled, 0);
         // If timeout, errno is set to either EAGAIN or EWOULDBLOCK, which means the loop should continue.

         if (n > 0) {
            fulfilled = n;
         }
      } while (errno == EAGAIN || errno == EWOULDBLOCK);
#endif

      if (n == SOCKET_ERROR || _socket == INVALID_SOCKET) {
         int errorCode = errno;
         // Socket timed out
#if defined(_WIN32)
         if (errorCode == WSAETIMEDOUT) {
#else
            if (errorCode == ETIMEDOUT) {
#endif
            throw IOException{"Socket timed out", getLastErrorCode()};
         }
         throw IOException{"Could not read from socket", getLastErrorCode()};
      }
      return n;
   }

   bool TcpSocket::send(
         const char * buf,
         uint32_t numOfBytes) const
   {
#if defined(_WIN32)
      // Avoid int overflow on Windows
      const uint32_t intMax = std::numeric_limits<int>::max();
      while (numOfBytes > intMax) {
         send(buf, intMax);
         buf += intMax;
         numOfBytes -= intMax;
      }
#endif

      // Send should be serialized by the OS, so this is thread-safe
#if defined(_WIN32)
      long n = ::send(_socket, buf, static_cast<int>(numOfBytes), 0);
#else
      long n = ::send(_socket, buf, numOfBytes, 0);
#endif
      if (n == SOCKET_ERROR) {
         throw IOException("Could not write to socket", getLastErrorCode());
      }
      if (n != numOfBytes) {
         throw IOException("Could not write all data to socket");
      }

      return true;
   }

   void TcpSocket::setTcpNoDelay(bool flag) const
   {
      // TODO implement
   }

   void TcpSocket::setTimeout(uint32_t timeoutMs)
   {
#if defined(_WIN32)
      DWORD tv = timeoutMs;
#else
      struct timeval tv;
      tv.tv_sec = timeoutMs / 1000;
      tv.tv_usec = (timeoutMs - tv.tv_sec * 1000) * 1000;  // unit conversion to us
#endif

      // https://learn.microsoft.com/en-us/windows/win32/winsock/sol-socket-socket-options
      // "The default for this option is zero, which indicates that a receive operation will not time out."
      if (setsockopt(_socket, SOL_SOCKET, SO_RCVTIMEO, (char *) &tv, sizeof(tv)) == SOCKET_ERROR) {
         throw IOException("Could not set sockopt SO_RCVTIMEO on socket", getLastErrorCode());
      }
   }

   int TcpSocket::getTimeout() const
   {
#if defined(_WIN32)
      return 0; // TODO: getTimeout in windows
#else
      struct timeval timeout{};
      size_t len = sizeof(timeout);
      getsockopt(
            _socket,
            SOL_SOCKET,
            SO_RCVTIMEO,
            reinterpret_cast<char *>(&timeout),
            reinterpret_cast<socklen_t *>(&len));
      return (timeout.tv_sec * 1000) + (timeout.tv_usec / 1000);
#endif
   }

   void TcpSocket::setupSocket()
   {
      // Create the socket
      _socket = socket(PF_INET, SOCK_STREAM, 0);
      if (_socket == SOCKET_ERROR) {
         throw IOException("Could not create socket", getLastErrorCode());
      }

#ifndef _WIN32
      // Set default timeout.
      // TODO: this shall be made configurable
      setTimeout(100);
#endif
   }

   bool TcpSocket::connect()
   {
      // Fill in local address for socket
      memset((char *) &_sendingLocalAddressC, 0, sizeof(_sendingLocalAddressC));
      _sendingLocalAddressC.sin_family = AF_INET;
      _sendingLocalAddressC.sin_port = htons(0); // 0 will select random port

      // TODO allow interface selection?
      // in_addr_t localInterfaceAddress = _localInterface != "" ? inet_addr(_localInterface.c_str()) : htonl(INADDR_ANY);
      // _sendingLocalAddressC.sin_addr.s_addr = localInterfaceAddress;

      // bind to socket
      int bindCode = bind(_socket, reinterpret_cast<const sockaddr *>(&_sendingLocalAddressC), sizeof(struct sockaddr));
      if (bindCode == SOCKET_ERROR) {
         throw IOException("Could not bind socket to ephemeral port", getLastErrorCode());
      }

      // Set up the remote address
      memset((char *) &_remoteAddressC, 0, sizeof(_remoteAddressC));
      _remoteAddressC.sin_family = AF_INET;
      _remoteAddressC.sin_addr.s_addr = inet_addr(_address.c_str()); // Address to send to
      _remoteAddressC.sin_port = htons(_port); // Remote port to send to

      int connectCode =
            ::connect(_socket, reinterpret_cast<const sockaddr *>(&_remoteAddressC), sizeof(_remoteAddressC));
      if (connectCode == SOCKET_ERROR) {
         throw IOException("Could not connect", getLastErrorCode());
      }
      return true;
   }


   bool TcpSocket::listen(int backlog)
   {
      memset((char *) &_remoteAddressC, 0, sizeof(_remoteAddressC));
      _sendingLocalAddressC.sin_family = AF_INET;
      _sendingLocalAddressC.sin_addr.s_addr = inet_addr(_address.c_str()); // Address to listen on
      _sendingLocalAddressC.sin_port = htons(_port); // Local port to listen on

      // Allow address:port reuse
      const int enableReuse{1};
      if (setsockopt(_socket, SOL_SOCKET, SO_REUSEADDR, (char *) &enableReuse, sizeof(enableReuse)) == SOCKET_ERROR) {
         throw IOException("Could not set sockopt SO_REUSEADDR on socket" , getLastErrorCode());
      }

      // bind to socket
      int bindCode = bind(_socket, reinterpret_cast<const sockaddr *>(&_sendingLocalAddressC), sizeof(struct sockaddr));
      if (bindCode == SOCKET_ERROR) {
         throw IOException(
               "Could not bind socket to " + _address + ":" + std::to_string(_port), getLastErrorCode());
      }

      auto listenCode = ::listen(_socket, backlog);
      if (listenCode == SOCKET_ERROR) {
         throw IOException("Could not listen on port", getLastErrorCode());
      }
      return true;
   }

   std::unique_ptr<TcpSocket> TcpSocket::accept()
   {
      socklen_t remoteAddressLen{sizeof(_remoteAddressC)};
      auto socket = std::make_unique<TcpSocket>(std::string(), 0);
      socket->_sendingLocalAddressC = _sendingLocalAddressC;
      socket->_socket = ::accept(_socket, reinterpret_cast<sockaddr *>(&_remoteAddressC), &remoteAddressLen);
      if (socket->_socket == INVALID_SOCKET) {
         throw IOException("Could not accept client", getLastErrorCode());
      }
      return socket;
   }

   void TcpSocket::close()
   {
      if (_socket != INVALID_SOCKET) {
#if defined(_WIN32)
         closesocket(_socket);
         _socket = INVALID_SOCKET;
#else
         ::close(_socket);
         _socket = INVALID_SOCKET;
#endif
      }
   }

   void TcpSocket::getIPAddressFromHostname(
         const std::string & hostname,
         std::string & address)
   {
       struct hostent *he = gethostbyname(hostname.c_str());
       if (he == nullptr) {
           throw IOException("Could resolve hostname: " + hostname, getLastErrorCode());
       }
       char *ipAddress = inet_ntoa(*(struct in_addr *) he->h_addr_list[0]);
       if (ipAddress == nullptr) {
           throw IOException("Could not convert IP address to number-dot notation for hostname: " + hostname,
                                        std::error_code{});
       }
       address = ipAddress;
   }

}
