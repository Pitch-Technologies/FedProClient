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

package se.pitch.oss.fedpro.common.session;

import se.pitch.oss.fedpro.client.session.msg.MessageHeader;

public class LogUtil {

   private static final int NUMBER_LENGTH = 3;

   public static String logPrefix(String caller)
   {
      StringBuilder prefix = new StringBuilder(caller);
      while (prefix.length() < "Server session ".length() + NUMBER_LENGTH) {
         prefix.append(" ");
      }
      return prefix.toString();
   }

   public static String logPrefix(
         long sessionId,
         String caller)
   {
      return caller + " Session " +
            padNumString(sessionId == MessageHeader.NO_SESSION_ID ? "-" : String.valueOf(sessionId));
   }

   public static String padNumString(int nr)
   {
      return padNumString(String.valueOf(nr));
   }

   public static String padNumString(String nr)
   {
      return " ".repeat(Math.max(0, NUMBER_LENGTH - nr.length())) + nr;
   }
}
