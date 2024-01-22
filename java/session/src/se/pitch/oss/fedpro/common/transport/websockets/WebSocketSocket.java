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

package se.pitch.oss.fedpro.common.transport.websockets;

import se.pitch.oss.fedpro.common.transport.FedProSocket;
import org.java_websocket.WebSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class WebSocketSocket implements FedProSocket {

   protected final WebSocketSender _webSocketSender;
   private final WebSocket _webSocket;
   private final InputStream _inputStream;
   private final OutputStream _outputStream;
   private InetSocketAddress _remoteSocketAddress;

   public WebSocketSocket(WebSocketSender webSocketSender)
   {
      this(webSocketSender, null);
   }

   public WebSocketSocket(
         WebSocketSender webSocketSender,
         WebSocket webSocket)
   {
      _webSocketSender = webSocketSender;
      _webSocket = webSocket;
      _inputStream = _webSocketSender.getInputStream(webSocket);
      _outputStream = new WebSocketOutputStream(_webSocketSender, webSocket);
   }

   @Override
   public InputStream getInputStream()
   throws IOException
   {
      return _inputStream;
   }

   @Override
   public OutputStream getOutputStream()
   throws IOException
   {
      return _outputStream;
   }

   @Override
   public SocketAddress getRemoteSocketAddress()
   {
      if (_remoteSocketAddress == null) {
         _remoteSocketAddress = _webSocket.getRemoteSocketAddress();
      }
      return _remoteSocketAddress;
   }

   @Override
   public synchronized void close()
   throws IOException
   {
      _webSocketSender.close(_webSocket);
      _inputStream.close();
      _outputStream.close();
   }

   @Override
   public void setTcpNoDelay(boolean flag)
   {
      // No-op. Cannot be set after connection has been established
      // Workaround is in place to set TcpNoDelay to true for WebSocket connections before connection has been
      // established. See WebSocketTransport::doConnect.
   }

   @Override
   public void setSoTimeout(int socketTimeout)
   {
      // Socket timeout is not used by the client side.
      // Server-side socket overrides this method to implement timeout.
   }

   @Override
   public int getSoTimeout()
   {
      // Socket timeout is not used by the client side.
      // Server-side socket overrides this method to implement timeout.
      return 0;
   }
}
