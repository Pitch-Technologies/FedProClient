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

import net.jcip.annotations.GuardedBy;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;

public class WebSocketInputStream extends InputStream {

   private final Queue<ReadableByteBuffer> _inputStreams = new LinkedList<>();

   @GuardedBy("_inputStreams")
   private boolean _isClosed = false;

   public void add(ByteBuffer byteBuffer)
   {
      synchronized (_inputStreams) {
         _inputStreams.add(new ReadableByteBuffer(byteBuffer));
         _inputStreams.notifyAll();
      }
   }

   @GuardedBy("_inputStreams")
   private ReadableByteBuffer peekNextStream()
   throws IOException
   {
      if (_isClosed) {
         throw new EOFException("Closed");
      }
      ReadableByteBuffer stream;
      while ((stream = _inputStreams.peek()) == null) {
         try {
            _inputStreams.wait();
         } catch (InterruptedException ignore) {
         }
         if (_isClosed) {
            throw new EOFException("Closed");
         }
      }
      return stream;
   }

   @Override
   public int read()
   throws IOException
   {
      synchronized (_inputStreams) {
         int returnValue = peekNextStream().read();
         if (returnValue == -1) {
            _inputStreams.remove();
         }
         return returnValue;
      }
   }

   @Override
   public int read(
         byte[] b,
         int off,
         int len)
   throws IOException
   {
      synchronized (_inputStreams) {
         int returnValue = peekNextStream().read(b, off, len);
         if (returnValue == -1) {
            _inputStreams.remove();
         }
         return returnValue;
      }
   }

   @Override
   public void close()
   throws IOException
   {
      super.close();
      synchronized (_inputStreams) {
         _isClosed = true;
         _inputStreams.notifyAll();
      }
   }

   private static class ReadableByteBuffer {

      private final ByteBuffer _buf;

      public ReadableByteBuffer(ByteBuffer buf)
      {
         _buf = buf;
      }

      public int read()
      {
         if (!_buf.hasRemaining()) {
            return -1;
         }
         return _buf.get() & 0xFF;
      }

      public int read(
            byte[] bytes,
            int off,
            int len)
      {
         if (!_buf.hasRemaining()) {
            return -1;
         }

         len = Math.min(len, _buf.remaining());
         _buf.get(bytes, off, len);
         return len;
      }
   }
}
