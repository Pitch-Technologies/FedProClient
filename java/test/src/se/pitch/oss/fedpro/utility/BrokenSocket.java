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

import se.pitch.oss.fedpro.common.transport.FedProSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketAddress;
import java.net.SocketException;

public class BrokenSocket implements FedProSocket {
   @Override
   public InputStream getInputStream()
   throws IOException
   {
      throw new SocketException("Socket is closed");
   }

   @Override
   public OutputStream getOutputStream()
   throws IOException
   {
      throw new SocketException("Socket is closed");
   }

   @Override
   public void close()
   {
   }

   @Override
   public SocketAddress getRemoteSocketAddress()
   {
      return null;
   }

   @Override
   public void setTcpNoDelay(boolean flag)
   throws SocketException
   {
      throw new SocketException("Socket is closed");
   }

   @Override
   public void setSoTimeout(int socketTimeout)
   throws SocketException
   {
      throw new SocketException("Socket is closed");
   }

   @Override
   public int getSoTimeout()
   throws SocketException
   {
      throw new SocketException("Socket is closed");
   }

   @Override
   public String getProtocolName() {
      return "None";
   }
}
