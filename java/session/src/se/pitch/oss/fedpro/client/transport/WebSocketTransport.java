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

import se.pitch.oss.fedpro.common.transport.FedProSocket;
import se.pitch.oss.fedpro.common.transport.websockets.WebSocketSocket;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class WebSocketTransport extends TransportBase {

   public WebSocketTransport(String host, int port)
   {
      super(host, port);
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
         impl.connectBlocking();
      } catch (InterruptedException ignore) {
      }
      return new WebSocketSocket(impl);
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
