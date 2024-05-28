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

public class NewSessionStatusMessage implements EncodableMessage {

   public static final int REASON_SUCCESS = 0;
   public static final int REASON_FAILURE_UNSUPPORTED_PROTOCOL_VERSION = 1;
   public static final int REASON_FAILURE_OUT_OF_RESOURCES = 2;

   // Suggestion for the HlA 4 standard:
   public static final int REASON_FAILURE_BAD_MESSAGE = 3;
   public static final int REASON_FAILURE_OTHER_ERROR = 99;

   public final int reason;

   public NewSessionStatusMessage(int reason)
   {
      this.reason = reason;
   }

   @Override
   public byte[] encode()
   {
      return ByteBuffer.allocate(4).putInt(reason).array();
   }

   public static NewSessionStatusMessage decode(InputStream inputStream)
   throws IOException, BadMessage
   {
      int reason = ByteReader.getInt(inputStream);

      if (!(reason == REASON_SUCCESS || reason == REASON_FAILURE_UNSUPPORTED_PROTOCOL_VERSION ||
            reason == REASON_FAILURE_OUT_OF_RESOURCES || reason == REASON_FAILURE_OTHER_ERROR)) {
         throw new BadMessage("Invalid reason in NewSessionStatus message, got " + reason);
      }

      return new NewSessionStatusMessage(reason);
   }
}
