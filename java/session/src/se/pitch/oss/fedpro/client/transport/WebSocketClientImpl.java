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

import se.pitch.oss.fedpro.common.transport.websockets.WebSocketInputStream;
import se.pitch.oss.fedpro.common.transport.websockets.WebSocketSender;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

class WebSocketClientImpl extends WebSocketClient implements WebSocketSender {

   private static final Logger LOGGER = Logger.getLogger(WebSocketClientImpl.class.getName());

   private final WebSocketInputStream _webSocketInputStream = new WebSocketInputStream();

   public WebSocketClientImpl(URI serverUri)
   {
      super(serverUri);
   }

   @Override
   public void onMessage(ByteBuffer bytes)
   {
      _webSocketInputStream.add(bytes);
   }

   @Override
   public void send(
         byte[] data,
         Collection<WebSocket> clients)
   {
      send(data);
   }

   @Override
   public InputStream getInputStream(WebSocket socket)
   {
      return _webSocketInputStream;
   }

   @Override
   public void onOpen(ServerHandshake serverHandshake)
   {
      // Noop
   }

   @Override
   public void onMessage(String s)
   {
      // Noop
   }

   @Override
   public void onClose(
         int statusCode,
         String reason,
         boolean remote)
   {
      try {
         _webSocketInputStream.close();
         // Report positive status codes only. Negative values are not valid status codes,
         // and Java-WebSocket internally use such invalid values that have no meaning to the outside world.
         if (statusCode >= 0) {
            // 1000 is CLOSE_NORMAL as per RFE 6455 https://www.rfc-editor.org/rfc/rfc6455.html#section-7.4.1
            Level logLevel = (statusCode == 1000) ? Level.INFO : Level.WARNING;
            LOGGER.log(logLevel, String.format("WebSocket closed with status code %d: %s", statusCode, reason));
         }
      } catch (IOException ignore) {
      }
   }

   @Override
   public void onError(Exception e)
   {
      try {
         _webSocketInputStream.close();
         LOGGER.warning("WebSocket error: " + e);
      } catch (IOException ignored) {
      }
   }

   @Override
   public void close(WebSocket webSocket)
   {
      super.close();
   }
}
