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

import static se.pitch.oss.fedpro.client.TimeoutConstants.DEFAULT_RECONNECT_DELAY_MILLIS;
import static se.pitch.oss.fedpro.client.TimeoutConstants.DEFAULT_RECONNECT_LIMIT_MILLIS;

public class SessionFactory {

   /**
    * Create a new client session.
    *
    * @param transport client transport to use
    */
   public static Session createSession(Transport transport)
   {
      return new SessionImpl(transport);
   }

   /**
    * @param transportProtocol      The underlying transport layer
    * @param connectionLostListener The listener to be called when the session is terminally lost due to connection issues
    */
   public static PersistentSession createPersistentSession(
         Transport transportProtocol,
         PersistentSession.ConnectionLostListener connectionLostListener)
   {
      ResumeStrategy strategy =
            new SimpleResumeStrategy(DEFAULT_RECONNECT_DELAY_MILLIS, DEFAULT_RECONNECT_LIMIT_MILLIS);
      return createPersistentSession(transportProtocol, connectionLostListener, strategy);
   }

   /**
    * @param transportProtocol      The underlying transport layer
    * @param connectionLostListener The listener to be called when the session is terminally lost due to connection issues
    * @param resumeStrategy         A ResumeStrategy instance that will control when, how often and for how long reconnection
    *                               attempts will be made
    */
   public static PersistentSession createPersistentSession(
         Transport transportProtocol,
         PersistentSession.ConnectionLostListener connectionLostListener,
         ResumeStrategy resumeStrategy)
   {
      return new PersistentSessionImpl(transportProtocol, connectionLostListener, resumeStrategy);
   }

}
