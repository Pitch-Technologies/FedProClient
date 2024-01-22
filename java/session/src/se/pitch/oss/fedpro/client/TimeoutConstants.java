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

package se.pitch.oss.fedpro.client;

public class TimeoutConstants {

   // HLA standard constants

   public static final String SETTING_NAME_HEARTBEAT_INTERVAL = "FED_INT_HEART";
   public static final String SETTING_NAME_RESPONSE_TIMEOUT = "FED_TIMEOUT_HEART";
   public static final String SETTING_NAME_RECONNECT_LIMIT = "FED_TIMEOUT_RECONNECT";

   public static final long DEFAULT_HEARTBEAT_INTERVAL_MILLIS = 60_000L;
   public static final long DEFAULT_RESPONSE_TIMEOUT_MILLIS = 180_000L;
   public static final long DEFAULT_RECONNECT_LIMIT_MILLIS = 600_000L;


   // Custom constants

   public static final String SETTING_NAME_CONNECTION_TIMEOUT = "ConnectionTimeout";

   public static final long DEFAULT_RECONNECT_DELAY_MILLIS = 5_000L;

}
