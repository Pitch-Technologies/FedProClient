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

package se.pitch.oss.fedpro.utility;

import se.pitch.oss.fedpro.client.transport.TransportBase;
import se.pitch.oss.fedpro.common.transport.FedProSocket;

import java.io.IOException;

public class MemoryTransport extends TransportBase {

   private final MemorySocket _socket = new MemorySocket();

   public MemoryTransport()
   throws IOException
   {
      super("", 0);
   }

   public void addInboundPacket(byte[] packet)
   throws IOException
   {
      _socket.addInboundPacket(packet);
   }

   @Override
   protected FedProSocket doConnect(String host, int port)
   {
      return _socket;
   }
}
