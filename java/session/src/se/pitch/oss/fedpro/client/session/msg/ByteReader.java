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

package se.pitch.oss.fedpro.client.session.msg;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static se.pitch.oss.fedpro.client.session.msg.ByteInfo.INT32_SIZE;

class ByteReader {

   public static byte[] readNBytes(InputStream input, int count)
   throws IOException
   {
      byte[] bytes = new byte[count];

      int readInTotal = 0;
      int bytesRead;

      // Blocks when there is nothing to read, unless there is an IOException or EOF is reached.
      while ((bytesRead = input.read(bytes, readInTotal, bytes.length - readInTotal)) > 0) {
         readInTotal += bytesRead;
      }

      if (readInTotal < count) {
         throw new EOFException("Could only read " + readInTotal + " of " + count + " bytes");
      }
      return bytes;
   }

   public static ByteBuffer wrap(InputStream input, int count)
   throws IOException
   {
      return ByteBuffer.wrap(readNBytes(input, count));
   }

   public static int getInt32(InputStream input)
   throws IOException
   {
      return wrap(input, INT32_SIZE).getInt();
   }
}
