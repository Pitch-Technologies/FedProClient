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

package se.pitch.oss.fedpro.client.transport;

import se.pitch.oss.fedpro.client.TypedProperties;
import se.pitch.oss.fedpro.common.Protocol;
import se.pitch.oss.fedpro.common.transport.FedProSocket;
import se.pitch.oss.fedpro.common.transport.FedProSocketImpl;

import java.io.IOException;

import static se.pitch.oss.fedpro.client.Settings.*;
import static se.pitch.oss.fedpro.client.transport.TransportSettings.*;
import static se.pitch.oss.fedpro.common.Ports.DEFAULT_PORT_TCP;

public class TcpTransport extends TransportBase {

   public TcpTransport(TypedProperties settings)
   {
      super(
            settings == null ?
                  DEFAULT_CONNECTION_HOST :
                  settings.getString(SETTING_NAME_CONNECTION_HOST, DEFAULT_CONNECTION_HOST),
            settings == null ?
                  DEFAULT_PORT_TCP :
                  settings.getInt(SETTING_NAME_CONNECTION_PORT, DEFAULT_PORT_TCP));

      TypedProperties allTransportSettingsUsed = new TypedProperties();
      allTransportSettingsUsed.setString(SETTING_NAME_CONNECTION_HOST, _host);
      allTransportSettingsUsed.setInt(SETTING_NAME_CONNECTION_PORT, _port);
      LOGGER.config(() -> String.format(
            "Federate Protocol client transport layer settings used:\n%s",
            allTransportSettingsUsed.toPrettyString()));
   }

   protected FedProSocket doConnect(String host, int port)
   throws IOException
   {
      return new FedProSocketImpl(new java.net.Socket(host, port), Protocol.TCP);
   }

}
