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

import java.nio.ByteBuffer;

public class EncodedMessage {

   public final int sequenceNumber; //unsigned int
   public final boolean isControl;
   public final byte[] data;

   private EncodedMessage(int sequenceNumber, boolean isControl, byte[] data)
   {
      this.sequenceNumber = sequenceNumber;
      this.isControl = isControl;
      this.data = data;
   }

   public static EncodedMessage create(MessageHeader header, EncodableMessage message)
   {
      ByteBuffer buffer = ByteBuffer.allocate((int) header.packetSize);
      buffer.put(header.encode());
      if (message != null) {
         buffer.put(message.encode());
      }

      return new EncodedMessage(header.sequenceNumber, header.messageType.isControl(), buffer.array());
   }
}
