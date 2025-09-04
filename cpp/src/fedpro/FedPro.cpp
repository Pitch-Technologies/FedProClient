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

#include <FedPro.h>

#include "session/PersistentSessionImpl.h"
#include "session/SessionImpl.h"
#include "transport/TcpTransport.h"
#include "transport/TlsTransport.h"
#include "transport/WebsocketTransport.h"
#include "transport/WebsocketSecureTransport.h"
#include "utility/LoggerInitializer.h"

#include <memory>

namespace FedPro
{
   FEDPRO_EXPORT std::unique_ptr<Session> createSession(
         std::unique_ptr<Transport> transportProtocol)
   {
      return createSession(std::move(transportProtocol), Properties{});
   }

   FEDPRO_EXPORT std::unique_ptr<Session> createSession(
         std::unique_ptr<Transport> transportProtocol,
         const Properties & settings)
   {
      return std::make_unique<SessionImpl>(std::move(transportProtocol), settings);
   }

   FEDPRO_EXPORT std::unique_ptr<PersistentSession> createPersistentSession(
         std::unique_ptr<Transport> transportProtocol,
         PersistentSession::ConnectionLostListener connectionLostListener,
         PersistentSession::SessionTerminatedListener sessionTerminatedListener)
   {
      return createPersistentSession(
            std::move(transportProtocol),
            std::move(connectionLostListener),
            std::move(sessionTerminatedListener),
            Properties{});
   }

   FEDPRO_EXPORT std::unique_ptr<PersistentSession> createPersistentSession(
         std::unique_ptr<Transport> transportProtocol,
         PersistentSession::ConnectionLostListener connectionLostListener,
         PersistentSession::SessionTerminatedListener sessionTerminatedListener,
         const Properties & settings)
   {
      std::unique_ptr<ResumeStrategy> strategy = std::make_unique<SimpleResumeStrategy>();
      return createPersistentSession(
            std::move(transportProtocol),
            std::move(connectionLostListener),
            std::move(sessionTerminatedListener),
            settings,
            std::move(strategy));
   }

   FEDPRO_EXPORT std::unique_ptr<PersistentSession> createPersistentSession(
         std::unique_ptr<Transport> transportProtocol,
         PersistentSession::ConnectionLostListener connectionLostListener,
         PersistentSession::SessionTerminatedListener sessionTerminatedListener,
         const Properties & settings,
         std::unique_ptr<ResumeStrategy> resumeStrategy)
   {
      if (!transportProtocol) {
         throw std::invalid_argument("Missing Transport object");
      }
      return std::make_unique<PersistentSessionImpl>(
            std::move(transportProtocol),
            std::move(connectionLostListener),
            std::move(sessionTerminatedListener),
            settings,
            std::move(resumeStrategy));
   }

   FEDPRO_EXPORT std::unique_ptr<Transport> createTcpTransport(const Properties & settings)
   {
      return std::make_unique<TcpTransport>(settings);
   }

   FEDPRO_EXPORT std::unique_ptr<Transport> createTcpTransport()
   {
      return createTcpTransport(Properties{});
   }

   FEDPRO_EXPORT std::unique_ptr<Transport> createTlsTransport(const Properties & settings)
   {
      return std::make_unique<TlsTransport>(settings);
   }

   FEDPRO_EXPORT std::unique_ptr<Transport> createTlsTransport()
   {
      return createTlsTransport(Properties{});
   }

   FEDPRO_EXPORT std::unique_ptr<Transport> createWebSocketTransport(const Properties & settings)
   {
      return std::make_unique<WebsocketTransport>(settings);
   }

   FEDPRO_EXPORT std::unique_ptr<Transport> createWebSocketTransport()
   {
      return createWebSocketTransport(Properties{});
   }

   FEDPRO_EXPORT std::unique_ptr<Transport> createWebSocketSecureTransport(const Properties & settings)
   {
      return std::make_unique<WebsocketSecureTransport>(settings);
   }

   FEDPRO_EXPORT std::unique_ptr<Transport> createWebSocketSecureTransport()
   {
      return createWebSocketSecureTransport(Properties{});
   }

   FEDPRO_EXPORT void initializeLogger(const Properties & settings)
   {
      LoggerInitializer::initialize(settings);
   }
}

