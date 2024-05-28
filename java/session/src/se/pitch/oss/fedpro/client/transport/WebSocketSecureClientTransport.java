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

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.net.UnknownHostException;

public class WebSocketSecureClientTransport extends WebSocketClientTransport {
   private final SSLContext _context;

   public WebSocketSecureClientTransport(int port,
                                         SSLContext context)
   {
      super(port);
      _context = context;
   }

   @Override
   protected WebSocketClientImpl createWebSocketClient(URI uri)
   {
      WebSocketClientImpl impl = super.createWebSocketClient(uri);
      impl.setSocketFactory(_context.getSocketFactory());
      return impl;
   }

   @Override
   protected String addHostPrefix(String host) throws UnknownHostException
   {
      String[] splitHost = host.split("://", 2);

      boolean hasProtocol = splitHost.length == 2;
      if (hasProtocol) {
         String protocol = splitHost[0];
         if (!protocol.equals("wss")) {
            throw new UnknownHostException(String.format("Invalid protocol '%s' used together with WebSocketSecure, expected 'wss'.",
               protocol));
         }
      } else {
         host = "wss://" + host;
      }

      return host;
   }
}
