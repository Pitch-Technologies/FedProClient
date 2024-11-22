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

public class LogUtil {

   private static final int NUMBER_LENGTH = 3;
   public static final String SERVER_PREFIX = "Server";
   public static final String CLIENT_PREFIX = "Client";

   public static String logPrefix(
         long sessionId,
         String caller)
   {
      return logPrefix(formatSessionId(sessionId), caller);
   }

   public static String logPrefix(
         String sessionIdString,
         String caller)
   {
      return caller + " Session " + sessionIdString;
   }

   public static String padNumString(int nr)
   {
      return padNumString(String.valueOf(nr));
   }

   public static String padNumString(String nr)
   {
      return " ".repeat(Math.max(0, NUMBER_LENGTH - nr.length())) + nr;
   }

   public static String formatSessionId(long sessionId)
   {
      String format = String.format("%016x", sessionId);
      return format.substring(0, 4) + "-" + format.substring(4, 8) + "-" + format.substring(8, 12) + "-" + format.substring(12, 16);
   }
}
