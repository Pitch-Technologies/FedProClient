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

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class HlaCallResponseMessage implements EncodableMessage {

   public final int responseToSequenceNumber; //unsigned int
   public final byte[] hlaServiceReturnValueOrException;

   public HlaCallResponseMessage(int responseToSequenceNumber, byte[] hlaServiceReturnValueOrException)
   {
      this.responseToSequenceNumber = responseToSequenceNumber;
      this.hlaServiceReturnValueOrException = hlaServiceReturnValueOrException;
   }

   @Override
   public byte[] encode()
   {
      return ByteBuffer.allocate(4 + hlaServiceReturnValueOrException.length)
            .putInt(responseToSequenceNumber)
            .put(hlaServiceReturnValueOrException)
            .array();
   }

   public static HlaCallResponseMessage decode(InputStream inputStream, int length)
   throws IOException
   {
      ByteBuffer buffer = ByteReader.wrap(inputStream, 4);
      int responseToSequenceNumber = buffer.getInt();
      byte[] hlaServiceReturnValueOrException = ByteReader.readNBytes(inputStream, length - 4);
      return new HlaCallResponseMessage(responseToSequenceNumber, hlaServiceReturnValueOrException);
   }
}
