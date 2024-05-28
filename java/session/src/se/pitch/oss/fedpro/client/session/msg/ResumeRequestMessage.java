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

public class ResumeRequestMessage implements EncodableMessage {

   public final int lastReceivedRtiSequenceNumber; //LRR, unsigned int
   public final int oldestAvailableFederateSequenceNumber; //OAF, unsigned int

   public ResumeRequestMessage(int lastReceivedRtiSequenceNumber, int oldestAvailableFederateSequenceNumber)
   {
      this.lastReceivedRtiSequenceNumber = lastReceivedRtiSequenceNumber;
      this.oldestAvailableFederateSequenceNumber = oldestAvailableFederateSequenceNumber;
   }

   @Override
   public byte[] encode()
   {
      return ByteBuffer.allocate(8)
            .putInt(lastReceivedRtiSequenceNumber)
            .putInt(oldestAvailableFederateSequenceNumber)
            .array();
   }

   public static ResumeRequestMessage decode(InputStream inputStream)
   throws IOException
   {
      ByteBuffer buffer = ByteReader.wrap(inputStream, 8);
      int lastReceivedRtiSequenceNumber = buffer.getInt();
      int oldestAvailableFederateSequenceNumber = buffer.getInt();

      return new ResumeRequestMessage(lastReceivedRtiSequenceNumber, oldestAvailableFederateSequenceNumber);
   }
}
