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
import se.pitch.oss.fedpro.common.transport.websockets.WebSocketSocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

import static se.pitch.oss.fedpro.client.Settings.SETTING_NAME_CONNECTION_HOST;
import static se.pitch.oss.fedpro.client.Settings.SETTING_NAME_CONNECTION_PORT;
import static se.pitch.oss.fedpro.client.transport.TransportSettings.*;
import static se.pitch.oss.fedpro.common.Ports.DEFAULT_PORT_WS;

public class WebSocketTransport extends TransportBase {

   private final String _protocolName;

   public WebSocketTransport(String host, int port, String protocolName)
   {
      super(host, port);
      _protocolName = protocolName;
   }

   public WebSocketTransport(TypedProperties settings, String protocolName)
   {
      super(
            settings == null ?
                  DEFAULT_CONNECTION_HOST :
                  settings.getString(SETTING_NAME_CONNECTION_HOST, DEFAULT_CONNECTION_HOST),
            settings == null ?
                  DEFAULT_PORT_WS :
                  settings.getInt(SETTING_NAME_CONNECTION_PORT, DEFAULT_PORT_WS));

      _protocolName = protocolName;
      TypedProperties allTransportSettingsUsed = new TypedProperties();
      allTransportSettingsUsed.setString(SETTING_NAME_CONNECTION_HOST, _host);
      allTransportSettingsUsed.setInt(SETTING_NAME_CONNECTION_PORT, _port);
      LOGGER.config(() -> String.format(
            "Federate Protocol client transport layer settings used:\n%s",
            allTransportSettingsUsed.toPrettyString()));
   }

   @Override
   protected FedProSocket doConnect(String host, int port)
   throws IOException
   {
      if (host.isEmpty()) {
         host = "localhost";
      }

      host = addHostPrefix(host);

      URI uri;
      try {
         uri = new URI(host + ":" + port);
      } catch (URISyntaxException e) {
         throw new UnknownHostException(String.format("Invalid address: '%s:%d'", host, port));
      }

      WebSocketClientImpl impl = createWebSocketClient(uri);

      // WebSockets requires TcpNoDelay to be either set or unset before connection is made.
      // See https://github.com/TooTallNate/Java-WebSocket/wiki/Enable-TCP_NODELAY
      impl.setTcpNoDelay(true);

      try {
         if (!impl.connectBlocking()) {
            throw new IOException("Could not connect to server at " + host + ":" + port);
         }
      } catch (InterruptedException ignore) {
      }
      return new WebSocketSocket(impl, _protocolName);
   }

   WebSocketClientImpl createWebSocketClient(URI uri)
   {
      return new WebSocketClientImpl(uri);
   }

   protected String addHostPrefix(String host)
   throws UnknownHostException
   {
      String[] splitHost = host.split("://", 2);

      boolean hasProtocol = splitHost.length == 2;
      if (hasProtocol) {
         String protocol = splitHost[0];
         if (!protocol.equals("ws")) {
            throw new UnknownHostException(String.format(
                  "Invalid protocol '%s' used together with WebSocket, expected 'ws'.",
                  protocol));
         }
      } else {
         host = "ws://" + host;
      }

      return host;
   }
}
