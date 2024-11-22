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

#include <fedpro/Aliases.h>
#include <fedpro/Config.h>
#include <fedpro/FedProExceptions.h>
#include <fedpro/PersistentSession.h>
#include <fedpro/ProgressiveDelayResumeStrategy.h>
#include <fedpro/Properties.h>
#include <fedpro/ResumeStrategy.h>
#include <fedpro/Session.h>
#include <fedpro/SimpleResumeStrategy.h>
#include <fedpro/Settings.h>
#include <fedpro/Transport.h>

namespace FedPro
{
   /**
    * @brief Create a new client session with default settings.
    *
    * @param transportProtocol The unique pointer to the transport to be used for the new
    * session. The ownership of the pointer is transferred to the created session.
    *
    * @return An unique pointer to a new Session instance.
    */
   FEDPRO_EXPORT std::unique_ptr<Session> createSession(std::unique_ptr<Transport> transportProtocol);

   /**
    * @brief Create a new client session.
    *
    * @param transportProtocol The unique pointer to the transport to be used for the new
    * session. The ownership of the pointer is transferred to the created session.
    * @param settings The Properties object instance which session layer settings
    * will be loaded from. Unprovided settings will get default values and non-session
    * settings will be ignored.
    *
    * @return An unique pointer to a new Session instance.
    */
   FEDPRO_EXPORT std::unique_ptr<Session> createSession(
         std::unique_ptr<Transport> transportProtocol,
         const Properties & settings);

   /**
    * @brief Create a new persistent client session with default resume strategy and
    * default settings.
    *
    * @param transportProtocol The unique pointer to the transport protocol to be used for
    * the new persistent session. The ownership of the pointer is transferred to the
    * created session.
    * @param connectionLostListener The listener to be invoked when the session is
    * terminally lost due to connection issues.
    *
    * @return An unique pointer to a new PersistentSession instance.
    */
   FEDPRO_EXPORT std::unique_ptr<PersistentSession> createPersistentSession(
         std::unique_ptr<Transport> transportProtocol,
         const PersistentSession::ConnectionLostListener & connectionLostListener);

   /**
    * @brief Create a new persistent client session with a custom resume strategy.
    *
    * @param transportProtocol The unique pointer to the transport protocol to be used for
    * the new persistent session. The ownership of the pointer is transferred to the
    * created session.
    * @param connectionLostListener The listener to be invoked when the session is
    * terminally lost due to connection issues.
    * @param settings The Properties object instance which session layer settings will be
    * loaded from. Unprovided settings will get default values and non-session settings
    * will be ignored.
    *
    * @throws std::invalid_argument If resumeStrategy is a nullptr.
    *
    * @return An unique pointer to a new PersistentSession instance.
    */
   FEDPRO_EXPORT std::unique_ptr<PersistentSession> createPersistentSession(
         std::unique_ptr<Transport> transportProtocol,
         const PersistentSession::ConnectionLostListener & connectionLostListener,
         const Properties & settings);

   /**
    * @brief Create a new persistent client session with a custom resume strategy.
    *
    * @param transportProtocol The unique pointer to the transport protocol to be used for
    * the new persistent session. The ownership of the pointer is transferred to the
    * created session.
    * @param connectionLostListener The listener to be invoked when the session is
    * terminally lost due to connection issues.
    * @param settings The Properties object instance which session layer settings will be
    * loaded from. Unprovided settings will get default values and non-session settings
    * will be ignored.
    * @param resumeStrategy The unique pointer to the resume strategy instance that will
    * control when, how often, and for how long reconnection attempts will be made in case
    * of a lost connection. The ownership of the pointer is transferred to the created
    * session.
    *
    * @throws std::invalid_argument If resumeStrategy is a nullptr.
    *
    * @return An unique pointer to a new PersistentSession instance.
    */
   FEDPRO_EXPORT std::unique_ptr<PersistentSession> createPersistentSession(
         std::unique_ptr<Transport> transportProtocol,
         const PersistentSession::ConnectionLostListener & connectionLostListener,
         const Properties & settings,
         std::unique_ptr<ResumeStrategy> resumeStrategy);

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
   FEDPRO_EXPORT std::unique_ptr<Transport> createTcpTransport(const Properties & settings);
   FEDPRO_EXPORT std::unique_ptr<Transport> createTcpTransport();

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
   FEDPRO_EXPORT std::unique_ptr<Transport> createTlsTransport(const Properties & settings);
   FEDPRO_EXPORT std::unique_ptr<Transport> createTlsTransport();

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
   FEDPRO_EXPORT std::unique_ptr<Transport> createWebSocketTransport(const Properties & settings);
   FEDPRO_EXPORT std::unique_ptr<Transport> createWebSocketTransport();

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
   FEDPRO_EXPORT std::unique_ptr<Transport> createWebSocketSecureTransport(const Properties & settings);
   FEDPRO_EXPORT std::unique_ptr<Transport> createWebSocketSecureTransport();

   /**
    * @brief initializes the static logger for the Federate Protocol client library.
    *
    * @param settings The Properties object instance which log settings will be loaded
    * from. Unprovided settings will get default values and non-transport settings will be
    * ignored.
    */
   FEDPRO_EXPORT void initializeLogger(const Properties & settings);


} // FedPro
