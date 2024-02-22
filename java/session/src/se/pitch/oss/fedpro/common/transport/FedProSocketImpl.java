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

package se.pitch.oss.fedpro.common.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketAddress;
import java.net.SocketException;

public class FedProSocketImpl implements FedProSocket {

   private final java.net.Socket _socket;

   public FedProSocketImpl(java.net.Socket socket)
   {
      _socket = socket;
   }

   @Override
   public InputStream getInputStream()
   throws IOException
   {
      return _socket.getInputStream();
   }

   @Override
   public OutputStream getOutputStream()
   throws IOException
   {
      return _socket.getOutputStream();
   }

   @Override
   public void close()
   throws IOException
   {
      _socket.close();
   }

   @Override
   public SocketAddress getRemoteSocketAddress()
   {
      return _socket.getRemoteSocketAddress();
   }

   @Override
   public void setTcpNoDelay(boolean flag)
   throws SocketException
   {
      _socket.setTcpNoDelay(flag);
   }

   @Override
   public void setSoTimeout(int socketTimeout)
   throws SocketException
   {
      _socket.setSoTimeout(socketTimeout);
   }

   @Override
   public int getSoTimeout()
   throws SocketException
   {
      return _socket.getSoTimeout();
   }

   @Override
   public String toString()
   {
      return _socket.toString();
   }
}
