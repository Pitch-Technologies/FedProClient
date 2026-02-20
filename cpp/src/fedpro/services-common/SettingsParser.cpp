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

#include "SettingsParser.h"

#include "utility/LoggerInitializer.h"
#include "utility/StringUtil.h"

#include <algorithm>
#include <string>
#include <fstream>
#include <sstream>

namespace FedPro
{
   static constexpr const string_view TOKEN_TRUE{"true", 4};
   static constexpr const string_view TOKEN_FALSE{"false", 5};

   Properties SettingsParser::parse()
   {
      return parse(string_view{});
   }

   Properties SettingsParser::parse(string_view input)
   {
      Properties settings;
      // Setting values are parsed and overwritten in the following order.
      parseEnvironmentVariable(settings, FEDPRO_CLIENT_SETTINGS);
      parseSettingsLine(settings, input);
      parseEnvironmentVariable(settings, FEDPRO_CLIENT_OVERRIDES);
      return settings;
   }

   // Private methods

   void SettingsParser::parseEnvironmentVariable(
         Properties & settings,
         const char * envVarName)
   {
      const char * envVarPtr = std::getenv(envVarName);
      if (!envVarPtr) {
         // No settings provided through this environment variable.
         return;
      }
      try {
         parseSettingsLine(settings, envVarPtr);
      } catch (const std::invalid_argument & e) {
         throw std::invalid_argument{
               "Failed to parse environment variable '" + std::string{envVarPtr} + "': " + e.what()};
      }
   }

   void SettingsParser::parseSetting(
         Properties & settings,
         const std::string & key,
         const std::string & value)
   {
      if (key == SETTING_NAME_HEARTBEAT_INTERVAL || key == SETTING_NAME_RESPONSE_TIMEOUT ||
          key == SETTING_NAME_RESUME_RETRY_DELAY_MILLIS || key == SETTING_NAME_RECONNECT_LIMIT ||
          key == SETTING_NAME_CONNECTION_TIMEOUT || key == SETTING_NAME_PRINT_STATS_INTERVAL) {
         parseDuration(settings, key, value);
      } else if (key == SETTING_NAME_ASYNC_UPDATES || key == SETTING_NAME_RATE_LIMIT_ENABLED ||
                 key == SETTING_NAME_PRINT_STATS || key == SETTING_NAME_WARN_ON_LATE_STATE_LISTENER_SHUTDOWN) {
         parseBoolean(settings, key, value);
      } else if (key == SETTING_NAME_CONNECTION_HOST || key == SETTING_NAME_HLA_API_VERSION) {
         settings.setString(key, value);
      } else if (key == SETTING_NAME_CONNECTION_PORT) {
         parseUnsignedInt16(settings, key, value);
      } else if (key == SETTING_NAME_CONNECTION_PROTOCOL) {
         parseProtocol(settings, value);
      } else if (key == SETTING_NAME_CONSOLE_LOG_LEVEL || key == SETTING_NAME_ROTATING_FILE_LOG_LEVEL) {
         settings.setInt(key, LoggerInitializer::toLogSeverityLevel(value));
      } else if (key == SETTING_NAME_ROTATING_FILE_LOG_PATH) {
         return parsePath(settings, key, value);
      } else if (key == SETTING_NAME_CONNECTION_MAX_RETRY_ATTEMPTS || key == SETTING_NAME_MESSAGE_QUEUE_SIZE) {
         parseUnsignedInt32(settings, key, value);
      } else {
         throw std::invalid_argument{"'" + key + "' is not a valid setting name."};
      }
   }

   void SettingsParser::parseBoolean(
         Properties & settings,
         const std::string & settingName,
         const std::string & booleanAsString)
   {
      // It's ok to specify "true" or "false" case insensitively
      if (!equalInsensitive(TOKEN_TRUE, booleanAsString) && !equalInsensitive(TOKEN_FALSE, booleanAsString)) {
         throw std::invalid_argument{
               "'" + booleanAsString + "' is not a valid boolean. Must be either '" + TOKEN_TRUE + "' or '" +
               TOKEN_FALSE + "'."};
      }
      settings.setBoolean(settingName, equalInsensitive(TOKEN_TRUE, booleanAsString));
   }

   void SettingsParser::parseDuration(
         Properties & settings,
         const std::string & settingName,
         const std::string & durationAsString)
   {
      try {
         settings.setDuration(settingName, std::chrono::seconds(std::stoul(durationAsString)));
      } catch (const std::exception & e) {
         throw std::invalid_argument{"'" + durationAsString + "' is not a valid duration: " + e.what()};
      }
   }

   void SettingsParser::parseInt(
         Properties & settings,
         const std::string & settingName,
         const std::string & intAsString)
   {
      if (!std::all_of(intAsString.begin(), intAsString.end(), ::isdigit)) {
         throw std::invalid_argument{"'" + intAsString + "' is not a valid integer, must only contain numbers."};
      }

      int candidate, maxAllowed{std::numeric_limits<int>::max()}, minAllowed{std::numeric_limits<int>::min()};
      try {
         candidate = std::stoi(intAsString);
      } catch (const std::invalid_argument & e) {
         throw std::invalid_argument{"'" + intAsString + "' is not a valid integer: " + std::string{e.what()}};
      } catch (const std::out_of_range & ) {
         throw std::invalid_argument{
               "'" + intAsString + "' is not a valid integer: must be in range " + std::to_string(minAllowed) + "-" +
               std::to_string(maxAllowed) + "."};
      }

      settings.setInt(settingName, candidate);
   }

   void SettingsParser::parsePath(
         Properties & settings,
         const std::string & settingName,
         const std::string & path)
   {
      settings.setString(settingName, path);
   }

   void SettingsParser::parseProtocol(
         Properties & settings,
         const std::string & protocol)
   {
      // It's ok to specify the protocol case insensitively (tcp, TCT, TcP e.g.)
      auto it = std::find_if(
            ALLOWED_PROTOCOLS.begin(), ALLOWED_PROTOCOLS.end(), [&protocol](const std::string & allowedProtocol) {
               return equalInsensitive(protocol, allowedProtocol);
            });

      if (it == std::end(ALLOWED_PROTOCOLS)) {
         throw std::invalid_argument{"'" + protocol + "' is not a supported transport protocol."};
      }
      std::string toInsert{protocol};
      toLower(toInsert);
      settings.setString(SETTING_NAME_CONNECTION_PROTOCOL, toInsert);
   }

   void SettingsParser::parseUnsignedInt16(
         Properties & settings,
         const std::string & settingName,
         const std::string & numberAsString)
   {
      if (!std::all_of(numberAsString.begin(), numberAsString.end(), ::isdigit)) {
         throw std::invalid_argument{
               "'" + numberAsString + "' is not a valid unsigned 16 bit integer, must only contain positive integers."};
      }

      unsigned int candidate;
      try {
         candidate = std::stoul(numberAsString);
      } catch (const std::exception & e) {
         throw std::invalid_argument{
               "'" + numberAsString + "' is not a valid unsigned 16 bit integer: " + std::string{e.what()}};
      }

      uint16_t maxAllowed{std::numeric_limits<uint16_t>::max()};
      if (candidate > maxAllowed) {
         throw std::invalid_argument{
               "'" + numberAsString + "' is not a valid unsigned 16 bit integer, can not exceed " +
               std::to_string(maxAllowed) + "."};
      }
      settings.setUnsignedInt16(settingName, candidate);
   }

   void SettingsParser::parseUnsignedInt32(
         Properties & settings,
         const std::string & settingName,
         const std::string & numberAsString)
   {
      if (!std::all_of(numberAsString.begin(), numberAsString.end(), ::isdigit)) {
         throw std::invalid_argument{
               "'" + numberAsString + "' is not a valid unsigned 32 bit integer, must only contain positive integers."};
      }

      unsigned long candidate;
      try {
         candidate = std::stoul(numberAsString);
      } catch (const std::exception & e) {
         throw std::invalid_argument{
               "'" + numberAsString + "' is not a valid unsigned 32 bit integer: " + std::string{e.what()}};
      }

      uint32_t maxAllowed{std::numeric_limits<uint32_t>::max()};
      if (candidate > maxAllowed) {
         throw std::out_of_range{"'" + numberAsString + "' is not a valid unsigned 32 bit integer, can not exceed " +
                                 std::to_string(maxAllowed) + "."};
      }
      settings.setUnsignedInt32(settingName, candidate);
   }

   void SettingsParser::parseSettingsLine(
         Properties & settings,
         string_view settingsLine)
   {
      for (string_view setting : splitString(settingsLine, ',')) {
         if (!setting.empty()) {
            std::pair<string_view, string_view> settingKeyValuePair = splitIntoPair(setting, '=');
            try {
               parseSetting(
                     settings, toString(settingKeyValuePair.first), toString(settingKeyValuePair.second));
            } catch (const std::invalid_argument & e) {
               throw std::invalid_argument{
                     std::string{"Setting '"} + setting + "' is invalid: " + std::string{e.what()}};
            }
         }
      }
   }

} // FedPro
