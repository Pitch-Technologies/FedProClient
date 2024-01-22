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

class WebSocketClientImpl extends WebSocketClient implements WebSocketSender {

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
         int i,
         String s,
         boolean b)
   {
      try {
         _webSocketInputStream.close();
      } catch (IOException ignore) {
      }
   }

   @Override
   public void onError(Exception e)
   {
      try {
         _webSocketInputStream.close();
      } catch (IOException ignored) {
      }
   }

   @Override
   public void close(WebSocket webSocket)
   {
      super.close();
   }
}
