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

public class Settings {

   /**
    * The environment variables which value may contain all possible Federate Protocol
    * client settings.
    */
   public static final String FEDPRO_CLIENT_SETTINGS = "FEDPRO_CLIENT_SETTINGS";
   public static final String FEDPRO_CLIENT_OVERRIDES = "FEDPRO_CLIENT_OVERRIDES";

   /**
    * Prefix for settings names used when providing settings through java system properties or through the Local Settings Designator.
    */
   public static final String SETTING_PREFIX = "FedPro.";

   /**
    * Prefix for settings names overrides provided as System property.
    */
   public static final String SETTING_OVERRIDES_PREFIX = "FedProOverrides.";

   /**
    * HLA standard settings.
    */
   public static final String SETTING_NAME_HEARTBEAT_INTERVAL = "FED_INT_HEART"; // Session
   public static final String SETTING_NAME_RESPONSE_TIMEOUT = "FED_TIMEOUT_HEART"; // Session
   public static final String SETTING_NAME_RECONNECT_LIMIT = "FED_TIMEOUT_RECONNECT"; // Session

   /**
    * Other settings.
    */
   public static final String SETTING_NAME_ASYNC_UPDATES = "asyncUpdates"; // Service
   public static final String SETTING_NAME_CONNECTION_HOST = "connect.hostname"; // Transport
   public static final String SETTING_NAME_CONNECTION_MAX_RETRY_ATTEMPTS = "connect.maxRetryAttempts"; // Session
   public static final String SETTING_NAME_RESUME_RETRY_DELAY_MILLIS = "resume.retryDelayMillis"; // Session
   public static final String SETTING_NAME_CONNECTION_PORT = "connect.port"; // Transport // Service
   public static final String SETTING_NAME_CONNECTION_PROTOCOL = "connect.protocol";
   public static final String SETTING_NAME_CONNECTION_TIMEOUT = "connect.timeout"; // Session
   public static final String SETTING_NAME_KEYSTORE_ALGORITHM = "keystore.algorithm"; // Transport
   public static final String SETTING_NAME_KEYSTORE_PASSWORD_PATH = "keystore.password.path"; // Transport
   public static final String SETTING_NAME_KEYSTORE_PATH = "keystore.path"; // Transport
   public static final String SETTING_NAME_KEYSTORE_TYPE = "keystore.type"; // Transport
   public static final String SETTING_NAME_MESSAGE_QUEUE_SIZE = "messageQueue.size"; // Session
   public static final String SETTING_NAME_RATE_LIMIT_ENABLED = "messageQueue.outgoing.limitedRate"; // Session
   public static final String SETTING_NAME_TLS_MODE = "tls.mode"; // Transport
   public static final String SETTING_NAME_TLS_SNI = "tls.sniHostname"; // Transport
   public static final String SETTING_NAME_PRINT_STATS = "log.stats"; // Session, Service
   public static final String SETTING_NAME_PRINT_STATS_INTERVAL = "log.stats.interval"; // Session, Service
   public static final String SETTING_NAME_HLA_API_VERSION = "API.version";

   /**
    * Undocumented settings
    */
   public static final String SETTING_NAME_WARN_ON_LATE_STATE_LISTENER_SHUTDOWN = "log.warnOnLateStateListenerShutdown";
}
