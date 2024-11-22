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

import static se.pitch.oss.fedpro.client.session.msg.ByteInfo.INT32_SIZE;

public class HeartbeatResponseMessage implements EncodableMessage {

   public final int responseToSequenceNumber; // unsigned int

   public HeartbeatResponseMessage(int responseToSequenceNumber)
   {
      this.responseToSequenceNumber = responseToSequenceNumber;
   }

   @Override
   public byte[] encode()
   {
      return ByteBuffer.allocate(INT32_SIZE).putInt(responseToSequenceNumber).array();
   }

   public static HeartbeatResponseMessage decode(InputStream inputStream)
   throws IOException
   {
      int responseToSequenceNumber = ByteReader.getInt32(inputStream);
      return new HeartbeatResponseMessage(responseToSequenceNumber);
   }
}
