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

import se.pitch.oss.fedpro.client.ClientTransport;
import se.pitch.oss.fedpro.common.transport.SessionSocket;

import java.io.IOException;
import java.net.ConnectException;

public abstract class ClientTransportBase implements ClientTransport {
   private final int _defaultPort;

   protected ClientTransportBase(int defaultPort)
   {
      _defaultPort = defaultPort;
   }

   public SessionSocket connect(String host,
                                int port) throws IOException
   {
      try {
         SessionSocket socket = doConnect(host, port);
         socket.setTcpNoDelay(true);
         return socket;
      } catch (ConnectException e) {
         ConnectException connectException = new ConnectException(e + ", host " + host + ":" + port);
         connectException.initCause(e);
         throw connectException;
      }
   }

   protected abstract SessionSocket doConnect(String host,
                                              int port) throws IOException;

   @Override
   public int getDefaultPort()
   {
      return _defaultPort;
   }
}
