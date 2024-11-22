/*
 *  Copyright (C) 2022 Pitch Technologies AB
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

package se.pitch.oss.fedpro.client;

import se.pitch.oss.fedpro.client.session.PersistentSessionImpl;
import se.pitch.oss.fedpro.client.session.SessionImpl;

public class SessionFactory {

   /**
    * Create a new client session.
    *
    * @param transportProtocol Client transport to be used for the new session.
    * @return A new Session instance.
    */
   public static Session createSession(Transport transportProtocol)
   {
      return createSession(transportProtocol, null);
   }

   /**
    * Create a new client session.
    *
    * @param transportProtocol Client transport to be used for the new session.
    * @param settings          The FedProProperties object instance which session
    *                          layer settings will be loaded from.
    *                          Unprovided settings will get default values and
    *                          non-session settings will be ignored.
    * @return A new Session instance.
    */
   public static Session createSession(
         Transport transportProtocol, TypedProperties settings)
   {
      return new SessionImpl(transportProtocol, settings);
   }

   /**
    * Create a new persistent client session with default resume strategy.
    *
    * @param transportProtocol      The underlying transport protocol.
    * @param connectionLostListener The listener to be invoked when the session is
    *                               terminally lost due to connection issues.
    * @return A new PersistentSession instance.
    */
   public static PersistentSession createPersistentSession(
         Transport transportProtocol, PersistentSession.ConnectionLostListener connectionLostListener)
   {
      return createPersistentSession(transportProtocol, connectionLostListener, null);
   }

   /**
    * Create a new persistent client session with default resume strategy.
    *
    * @param transportProtocol      The underlying transport protocol.
    * @param connectionLostListener The listener to be invoked when the session is
    *                               terminally lost due to connection issues.
    * @param settings               The FedProProperties object instance which session
    *                               layer settings will be loaded from.
    *                               Unprovided settings will get default values and
    *                               non-session settings will be ignored.
    * @return A new PersistentSession instance.
    */
   public static PersistentSession createPersistentSession(
         Transport transportProtocol,
         PersistentSession.ConnectionLostListener connectionLostListener,
         TypedProperties settings)
   {
      return createPersistentSession(transportProtocol, connectionLostListener, settings, new SimpleResumeStrategy());
   }

   /**
    * Create a new persistent client session with a custom resume strategy.
    *
    * @param transportProtocol      The underlying transport protocol.
    * @param connectionLostListener The listener to be invoked when the session is
    *                               terminally lost due to connection issues.
    * @param settings               The FedProProperties object instance which session
    *                               layer settings will be loaded from.
    *                               Unprovided settings will get default values and
    *                               non-session settings will be ignored.
    * @param resumeStrategy         A ResumeStrategy instance that will control when, how
    *                               often and for how long reconnection attempts are made
    *                               in case of a lost connection.
    * @return A new PersistentSession instance.
    * @throws NullPointerException If resumeStrategy is null.
    */
   public static PersistentSession createPersistentSession(
         Transport transportProtocol,
         PersistentSession.ConnectionLostListener connectionLostListener,
         TypedProperties settings,
         ResumeStrategy resumeStrategy)
   {
      return new PersistentSessionImpl(transportProtocol, connectionLostListener, settings, resumeStrategy);
   }

}
