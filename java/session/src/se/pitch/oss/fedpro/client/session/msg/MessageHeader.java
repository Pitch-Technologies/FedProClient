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

import se.pitch.oss.fedpro.common.session.MessageType;
import se.pitch.oss.fedpro.common.exceptions.BadMessage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class MessageHeader {

   // Header size in bytes
   public static final int SIZE = 4 + 4 + 8 + 4 + 4;
   public static final long NO_SESSION_ID = 0;

   public final long packetSize; //unsigned int
   public final int sequenceNumber; //unsigned int
   public final long sessionId;
   public final int lastReceivedSequenceNumber; //unsigned int
   public final MessageType messageType;

   private MessageHeader(
         long packetSize,
         int sequenceNumber,
         long sessionId,
         int lastReceivedSequenceNumber,
         MessageType messageType)
   {
      this.packetSize = packetSize;
      this.sequenceNumber = sequenceNumber;
      this.sessionId = sessionId;
      this.lastReceivedSequenceNumber = lastReceivedSequenceNumber;
      this.messageType = messageType;
   }

   public static MessageHeader with(
         long payloadSize,
         int sequenceNumber,
         long sessionId,
         int lastReceivedSequenceNumber,
         MessageType messageType)
   {
      return new MessageHeader(SIZE + payloadSize, sequenceNumber, sessionId, lastReceivedSequenceNumber, messageType);
   }

   public int getPayloadSize()
   {
      return (int) (packetSize - SIZE);
   }

   public byte[] encode()
   {
      return ByteBuffer.allocate(MessageHeader.SIZE)
            .putInt((int) packetSize)
            .putInt(sequenceNumber)
            .putLong(sessionId)
            .putInt(lastReceivedSequenceNumber)
            .putInt(messageType.asInt())
            .array();
   }

   public static MessageHeader decode(InputStream inputStream)
   throws BadMessage, IOException
   {
      long packetSize = Integer.toUnsignedLong(ByteReader.getInt(inputStream));
      if (packetSize < SIZE) {
         throw new BadMessage("Invalid PacketSize " + packetSize);
      }

      long remaining = packetSize - 4;
      if (remaining >= Integer.MAX_VALUE) {
         throw new BadMessage("Invalid PacketSize " + packetSize);
      }
      ByteBuffer buffer = ByteReader.wrap(inputStream, SIZE - 4);

      int sequenceNumber = buffer.getInt();
      long sessionId = buffer.getLong();
      int lastReceivedSequenceNumber = buffer.getInt();
      int messageTypeInt = buffer.getInt();

      MessageType messageType = MessageType.fromInt(messageTypeInt);
      if (messageType == null) {
         throw new BadMessage("Unknown MessageType " + messageTypeInt);
      }

      return new MessageHeader(packetSize, sequenceNumber, sessionId, lastReceivedSequenceNumber, messageType);
   }

}
