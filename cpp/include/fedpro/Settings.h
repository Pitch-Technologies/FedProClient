/***********************************************************************
  Copyright (C) 2023 Pitch Technologies AB

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 **********************************************************************/

#pragma once

#include <fedpro/Aliases.h>

#include <cstdint>
#include <initializer_list>
#include <string>

namespace FedPro
{
   /**
    * @brief Prefix for settings names used when providing settings through the Local Settings Designator.
    */
   static constexpr const char * SETTING_PREFIX{"FedPro."};
   static constexpr const wchar_t * SETTING_PREFIX_WIDE{L"FedPro."};
   static const std::wstring SETTING_PREFIX_WIDE_STRING{FedPro::SETTING_PREFIX_WIDE};

   /**
    * @brief The environment variable which value contains all possible Federate Protocol
    * client settings.
    */
   static constexpr const char * FEDPRO_CLIENT_SETTINGS{"FEDPRO_CLIENT_SETTINGS"};
   static constexpr const char * FEDPRO_CLIENT_OVERRIDES{"FEDPRO_CLIENT_OVERRIDES"};

   /**
    * @brief Names of available protocols.
    */
   static constexpr const char * TCP{"tcp"};
   static constexpr const char * TLS{"tls"};
   static constexpr const char * WS{"websocket"};
   static constexpr const char * WSS{"websocketsecure"};
   static const std::initializer_list<const char *> ALLOWED_PROTOCOLS = {TCP, TLS, WS, WSS};

   /**
    * @brief HLA standard settings.
    */
   static constexpr const char * SETTING_NAME_HEARTBEAT_INTERVAL{"FED_INT_HEART"}; // Session
   static constexpr const char * SETTING_NAME_RESPONSE_TIMEOUT{"FED_TIMEOUT_HEART"}; // Session
   static constexpr const char * SETTING_NAME_RECONNECT_LIMIT{"FED_TIMEOUT_RECONNECT"}; // Session

   /**
    * @brief Other settings.
    */
   static constexpr const char * SETTING_NAME_ASYNC_UPDATES{"asyncUpdates"};  // Service
   static constexpr const char * SETTING_NAME_CONNECTION_HOST{"connect.hostname"}; // Transport
   static constexpr const char * SETTING_NAME_CONNECTION_MAX_RETRY_ATTEMPTS{"connect.maxRetryAttempts"}; // Session
   static constexpr const char * SETTING_NAME_RESUME_RETRY_DELAY_MILLIS{"resume.retryDelayMillis"}; // Session
   static constexpr const char * SETTING_NAME_CONNECTION_PORT{"connect.port"}; // Transport
   static constexpr const char * SETTING_NAME_CONNECTION_PROTOCOL{"connect.protocol"}; // Service
   static constexpr const char * SETTING_NAME_CONNECTION_TIMEOUT{"connect.timeout"}; // Session
   static constexpr const char * SETTING_NAME_CONSOLE_LOG_LEVEL{"log.console.level"}; // Log
   static constexpr const char * SETTING_NAME_ROTATING_FILE_LOG_LEVEL{"log.rotatingFile.level"}; // Log
   static constexpr const char * SETTING_NAME_ROTATING_FILE_LOG_PATH{"log.rotatingFile.path"}; // Log
   static constexpr const char * SETTING_NAME_PRINT_STATS{"log.stats"}; // Session, Service
   static constexpr const char * SETTING_NAME_PRINT_STATS_INTERVAL{"log.stats.interval"}; // Session, Service
   static constexpr const char
         * SETTING_NAME_WARN_ON_LATE_STATE_LISTENER_SHUTDOWN{"log.warnOnLateStateListenerShutdown"};
   static constexpr const char * SETTING_NAME_MESSAGE_QUEUE_SIZE{"messageQueue.size"}; // Session
   static constexpr const char * SETTING_NAME_RATE_LIMIT_ENABLED{"messageQueue.outgoing.limitedRate"}; // Session
   static constexpr const char * SETTING_NAME_HLA_API_VERSION{"API.version"}; // Service

} // FedPro
