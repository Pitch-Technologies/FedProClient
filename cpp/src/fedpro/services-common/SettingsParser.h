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
#include <fedpro/Config.h>
#include <fedpro/Properties.h>
#include <fedpro/Settings.h>

#include <utility/string_view.h>

#include <cstdint>
#include <map>
#include <string>
#include <vector>

namespace FedPro
{
   /**
    * @brief Parses all Federate Protocol client settings provided by the user.
    * If the value for a certain setting is specified in more than one way, the value
    * which is set through the most prioritized way will be used. The order of precedence
    * among the ways is, from most prioritized way to least:
    * <ul>
    *    <li> Environment variable `FEDPRO_CLIENT_OVERRIDES` </li>
    *    <li> The settingsLine parameter </li>
    *    <li> Environment variable `FEDPRO_CLIENT_SETTINGS` </li>
    * </ul>
    */
   class SettingsParser
   {
   public:

      /**
       * @brief Parses all provided settings from environment variables and returns them.
       *
       * @return A Properties instance that contains parsed values for all provided settings.
       *
       * @throws std::invalid_argument Thrown if any setting is provided through an
       * invalid format.
       */
      FEDPRO_EXPORT static Properties parse();

      /**
       * @brief Parses all provided settings from environment variables and a settings
       * line string, and returns them.
       *
       * @param settingsLine A line containing settings seperated by the ',' character.
       * Each setting must be specified in the format <setting_name>=<setting_value>.
       * Example: "FED_INT_HEART=600,connect.port=tcp".
       *
       * @return A Properties instance that contains parsed values for all provided settings.
       *
       * @throws std::invalid_argument Thrown if any setting is provided through an
       * invalid format.
       */
      FEDPRO_EXPORT static Properties parse(string_view settingsLine);

   private:

      static void parseEnvironmentVariable(
            Properties & settings,
            const char * envVarName);

      static void parseSetting(
            Properties & settings,
            const std::string & key,
            const std::string & value);

      static void parseBoolean(
            Properties & settings,
            const std::string & settingName,
            const std::string & booleanAsString);

      static void parseDuration(
            Properties & settings,
            const std::string & settingName,
            const std::string & durationAsString);

      static void parseInt(
            Properties & settings,
            const std::string & settingName,
            const std::string & intAsString);

      static void parsePath(
            Properties & settings,
            const std::string & settingName,
            const std::string & path);

      static void parseProtocol(
            Properties & settings,
            const std::string & protocol);

      static void parseUnsignedInt16(
            Properties & settings,
            const std::string & settingName,
            const std::string & numberAsString);

      static void parseUnsignedInt32(
            Properties & settings,
            const std::string & settingName,
            const std::string & numberAsString);

      static void parseSettingsLine(
            Properties & settings,
            string_view settingsLine);

   };

} // FedPro
