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

import se.pitch.oss.fedpro.client.Transport;
import se.pitch.oss.fedpro.common.transport.FedProSocket;

import java.io.IOException;
import java.net.ConnectException;

public abstract class TransportBase implements Transport {

   private final String _host;
   private final int _port;

   protected TransportBase(String host, int port)
   {
      _host = host;
      _port = port;
   }

   public FedProSocket connect()
   throws IOException
   {
      try {
         FedProSocket socket = doConnect(_host, _port);
         socket.setTcpNoDelay(true);
         return socket;
      } catch (ConnectException e) {
         ConnectException connectException =
               new ConnectException("Failed to connect to FedPro server at " + _host + ":" + _port + " (" + e + ")");
         connectException.initCause(e);
         throw connectException;
      }
   }

   protected abstract FedProSocket doConnect(String host, int port)
   throws IOException;
}
