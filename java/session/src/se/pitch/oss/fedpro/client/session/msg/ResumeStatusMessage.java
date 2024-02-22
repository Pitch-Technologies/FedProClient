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

import se.pitch.oss.fedpro.common.exceptions.BadMessage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class ResumeStatusMessage implements EncodableMessage {

   public static final int STATUS_OK_TO_RESUME = 0;
   public static final int STATUS_FAILURE_INVALID_SESSION = 1;
   public static final int STATUS_FAILURE_NOT_ENOUGH_BUFFERED_MESSAGES = 2;
   public static final int STATUS_FAILURE_OTHER_ERROR = 99;

   public final int sessionStatus;
   public final int lastReceivedFederateSequenceNumber; //LRF, unsigned int

   public ResumeStatusMessage(int sessionStatus, int lastReceivedFederateSequenceNumber)
   {
      this.sessionStatus = sessionStatus;
      this.lastReceivedFederateSequenceNumber = lastReceivedFederateSequenceNumber;
   }

   @Override
   public byte[] encode()
   {
      return ByteBuffer.allocate(8).putInt(sessionStatus).putInt(lastReceivedFederateSequenceNumber).array();
   }

   public static ResumeStatusMessage decode(InputStream inputStream)
   throws BadMessage, IOException
   {
      ByteBuffer buffer = ByteReader.wrap(inputStream, 8);
      int sessionStatus = buffer.getInt();
      int lastReceivedFederateSequenceNumber = buffer.getInt();

      if (!(sessionStatus == STATUS_OK_TO_RESUME || sessionStatus == STATUS_FAILURE_INVALID_SESSION ||
            sessionStatus == STATUS_FAILURE_NOT_ENOUGH_BUFFERED_MESSAGES ||
            sessionStatus == STATUS_FAILURE_OTHER_ERROR)) {
         throw new BadMessage("Invalid session status in ResumeStatus message, got " + sessionStatus);
      }

      return new ResumeStatusMessage(sessionStatus, lastReceivedFederateSequenceNumber);
   }
}
