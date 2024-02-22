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

import org.java_websocket.WebSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class WebSocketOutputStream extends OutputStream {

   private final WebSocketSender _webSocket;
   private final List<WebSocket> _clients = new ArrayList<>();
   private final AtomicBoolean _isClosed = new AtomicBoolean();

   public WebSocketOutputStream(
         WebSocketSender webSocket,
         WebSocket client)
   {
      _webSocket = webSocket;
      _clients.add(client);
   }

   @Override
   public void write(int b)
   throws IOException
   {
      assert false : "Non-supported write";
   }

   @Override
   public void write(byte[] b)
   throws IOException
   {
      if (_isClosed.get()) {
         throw new IOException("closed");
      } else {
         _webSocket.send(b, _clients);
      }
   }

   @Override
   public void close()
   throws IOException
   {
      _isClosed.set(true);
   }
}
