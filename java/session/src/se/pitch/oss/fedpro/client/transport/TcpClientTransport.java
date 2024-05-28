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

import se.pitch.oss.fedpro.common.Ports;
import se.pitch.oss.fedpro.common.transport.SessionSocket;
import se.pitch.oss.fedpro.common.transport.SessionSocketImpl;

import java.io.IOException;
import java.net.Socket;

public class TcpClientTransport extends ClientTransportBase {
   public TcpClientTransport()
   {
      super(Ports.DEFAULT_PORT_TCP);
   }

   @Override
   protected SessionSocket doConnect(String host,
                                     int port) throws IOException
   {
      return new SessionSocketImpl(new Socket(host, port));
   }
}
