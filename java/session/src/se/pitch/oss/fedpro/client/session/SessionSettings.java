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

package se.pitch.oss.fedpro.client.session;

public class SessionSettings
{
   // Default values
   static final long DEFAULT_HEARTBEAT_INTERVAL_MILLIS = 60_000L;
   static final long DEFAULT_RESPONSE_TIMEOUT_MILLIS = 180_000L;
   static final long DEFAULT_RECONNECT_LIMIT_MILLIS = 600_000L;
   static final int DEFAULT_CONNECTION_MAX_RETRY_ATTEMPTS = 0;
   static final long DEFAULT_CONNECTION_TIMEOUT_MILLIS = DEFAULT_RESPONSE_TIMEOUT_MILLIS;
   static final int DEFAULT_MESSAGE_QUEUE_SIZE = 2000;
   static final boolean DEFAULT_RATE_LIMIT_ENABLED = false;
   public static final int DEFAULT_PRINT_STATS_INTERVAL_MILLIS =  60_000;

   // Non settings related default values.
   static final long DEFAULT_RECONNECT_DELAY_MILLIS = 5_000L;

   public static long getDefaultReconnectLimitMillis() {
      return DEFAULT_RECONNECT_LIMIT_MILLIS;
   }

   public static long getDefaultReconnectDelayMillis()
   {
      return DEFAULT_RECONNECT_DELAY_MILLIS;
   }

}
