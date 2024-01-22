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

public class NewSessionMessage implements EncodableMessage {

   public static final int FEDERATE_PROTOCOL_VERSION = 1;

   public final int protocolVersion;

   public NewSessionMessage(int protocolVersion)
   {
      this.protocolVersion = protocolVersion;
   }

   @Override
   public byte[] encode()
   {
      return ByteBuffer.allocate(4).putInt(protocolVersion).array();
   }

   public static NewSessionMessage decode(InputStream inputStream)
   throws IOException
   {
      int protocolVersion = ByteReader.getInt(inputStream);

      return new NewSessionMessage(protocolVersion);
   }
}
