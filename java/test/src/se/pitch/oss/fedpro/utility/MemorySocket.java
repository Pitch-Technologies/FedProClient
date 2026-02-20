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

import java.io.*;
import java.net.SocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

public class MemorySocket implements FedProSocket {

   private final AtomicBoolean _open = new AtomicBoolean(false);
   private final PipedOutputStream _inboundOutputStream = new PipedOutputStream();
   private final PipedInputStream _inboundInputStream = new PipedInputStream(_inboundOutputStream);
   private final PipedInputStream _outboundInputStream = new PipedInputStream();
   private final PipedOutputStream _outboundOutputStream = new PipedOutputStream(_outboundInputStream);

   public MemorySocket()
   throws IOException
   {
      // Java's convention is to connect the socket on creation.
      if (!connect()) {
         throw new IOException("Cannot connect");
      }
   }

   public void addInboundPacket(byte[] packet)
   throws IOException
   {
      _inboundOutputStream.write(packet);
   }

   boolean connect()
   {
      synchronized (_open) {
         _open.set(true);
         _open.notifyAll();
      }
      return true;
   }

   @Override
   public InputStream getInputStream()
   {
      return _inboundInputStream;
   }

   @Override
   public OutputStream getOutputStream()
   {
      return _outboundOutputStream;
   }

   @Override
   public void close()
   throws IOException
   {
      synchronized (_open) {
         _open.set(false);
         _open.notifyAll();
      }
      _inboundInputStream.close();
      _outboundOutputStream.close();
      // To make inboundInputStream reach end, we must close the outputStream that feeds the inboundInputStream
      _inboundOutputStream.close();
   }

   @Override
   public SocketAddress getRemoteSocketAddress()
   {
      // No address
      return null;
   }

   @Override
   public void setTcpNoDelay(boolean flag)
   {
   }

   @Override
   public void setSoTimeout(int socketTimeout)
   {
   }

   @Override
   public int getSoTimeout()
   {
      return 0;
   }

   @Override
   public String getProtocolName()
   {
      return "None";
   }
}
