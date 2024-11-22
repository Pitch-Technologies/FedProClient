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

package se.pitch.oss.fedpro.client_common;

import se.pitch.oss.fedpro.client.TypedProperties;
import se.pitch.oss.fedpro.client_common.exceptions.InvalidSetting;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.*;

import static se.pitch.oss.fedpro.client.Settings.*;

/**
 * Parses all Federate Protocol client settings provided by the user.
 * If the value for a certain setting is specified in more than one way,
 * the value which is set through the most prioritized way will be used.
 * The order of precedence among the ways is, from most prioritized way to least:
 * <ul>
 *    <li> System property with prefix `FedProOverrides.` </li>
 *    <li> Environment variable `FEDPRO_CLIENT_OVERRIDES` </li>
 *    <li> The settingsLine parameter </li>
 *    <li> System property with prefix `FedPro.` </li>
 *    <li> Environment variable `FEDPRO_CLIENT_SETTINGS` </li>
 * </ul>
 */
public class SettingsParser {

   private static final String TRUE = "true";
   private static final String FALSE = "false";

   /**
    * Parses all provided settings from Java system properties and environment variables
    * and then returns them.
    *
    * @return A FedProProperties instance that contains parsed values for all provided
    *       settings.
    * @throws InvalidSetting Thrown if any setting is provided through an invalid format.
    */
   public static TypedProperties parse()
   throws InvalidSetting
   {
      return SettingsParser.parse(null);
   }

   /**
    * Parses all provided settings from Java system properties, environment variables and
    * a settings line string, and then returns them.
    *
    * @param settingsLine A string containing settings seperated by the ',' character.
    *                     Each setting must be specified in the format <setting_name>=<setting_value>.
    *                     Example: "FED_INT_HEART=600,connect.port=tcp".
    * @return A FedProProperties instance that contains parsed values for all provided
    *       settings.
    * @throws InvalidSetting Thrown if any setting is provided through an invalid format.
    */
   public static TypedProperties parse(String settingsLine)
   throws InvalidSetting
   {
      TypedProperties parsedSettings = new TypedProperties();
      // Setting values are parsed and overwritten in the following order.
      parseEnvironmentVariable(parsedSettings, FEDPRO_CLIENT_SETTINGS);
      parseSystemPropertiesWithPrefix(parsedSettings, SETTING_PREFIX);
      parseSettingsLine(parsedSettings, settingsLine);
      parseEnvironmentVariable(parsedSettings, FEDPRO_CLIENT_OVERRIDES);
      parseSystemPropertiesWithPrefix(parsedSettings, SETTING_OVERRIDES_PREFIX);
      return parsedSettings;
   }

   // Private methods

   private static void parseEnvironmentVariable(
         TypedProperties settings,
         String envVar)
   throws InvalidSetting
   {
      try {
         parseSettingsLine(settings, System.getenv(envVar));
      } catch (InvalidSetting e) {
         throw new InvalidSetting("Failed to parse environment variable '" + envVar + "': " + e.getMessage());
      }
   }

   private static void parseSystemPropertiesWithPrefix(
         TypedProperties settings,
         String propertyKeyPrefix)
   throws InvalidSetting
   {
      Properties sysProp = System.getProperties();

      // The checks for the system property keys are done case sensitively.
      for (final String sysKey : sysProp.stringPropertyNames()) {
         if (sysKey.startsWith(propertyKeyPrefix)) {
            String value = sysProp.getProperty(sysKey);
            String actualKey = sysKey.substring(propertyKeyPrefix.length());

            try {
               parseSetting(settings, actualKey, value);
            } catch (InvalidSetting e) {
               throw new InvalidSetting("Failed to parse Java system property '" + sysKey + "': " + e.getMessage());
            }

         }
      }
   }

   private static void parseSetting(
         TypedProperties settings,
         String key,
         String value)
   throws InvalidSetting
   {
      switch (key) {
         case SETTING_NAME_HEARTBEAT_INTERVAL:
         case SETTING_NAME_RESPONSE_TIMEOUT:
         case SETTING_NAME_RECONNECT_LIMIT:
         case SETTING_NAME_CONNECTION_TIMEOUT:
            parseDuration(settings, key, value);
            break;
         case SETTING_NAME_ASYNC_UPDATES:
         case SETTING_NAME_KEYSTORE_USE_DEFAULT:
         case SETTING_NAME_RATE_LIMIT_ENABLED:
            parseBoolean(settings, key, value);
            break;
         case SETTING_NAME_CONNECTION_PORT:
            parseUnsignedInt16(settings, key, value);
            break;
         case SETTING_NAME_CONNECTION_PROTOCOL:
            parseProtocol(settings, value);
            break;
         case SETTING_NAME_KEYSTORE_PATH:
            parsePath(settings, key, value);
            break;
         case SETTING_NAME_CONNECTION_MAX_RETRY_ATTEMPTS:
         case SETTING_NAME_MESSAGE_QUEUE_SIZE:
            parseUnsignedInt32(settings, key, value);
            break;
         case SETTING_NAME_CONNECTION_HOST:
         case SETTING_NAME_KEYSTORE_ALGORITHM:
         case SETTING_NAME_KEYSTORE_PASSWORD_PATH:
         case SETTING_NAME_KEYSTORE_TYPE:
         case SETTING_NAME_TLS_MODE:
         case SETTING_NAME_TLS_SNI:
            // Allowed to be any string
            settings.setString(key, value);
            break;
         default:
            throw new InvalidSetting("'" + key + "' is not a valid setting name.");
      }
   }

   private static void parseBoolean(
         TypedProperties settings,
         String settingName,
         String booleanAsString)
   throws InvalidSetting
   {
      // It's ok to specify "true" or "false" case insensitively
      if (!booleanAsString.equalsIgnoreCase(TRUE) && !booleanAsString.equalsIgnoreCase(FALSE)) {
         throw new InvalidSetting(
               "'" + booleanAsString + "' is not a valid boolean. Must be either '" + TRUE + "' or '" + FALSE + "'.");
      }
      settings.setBoolean(settingName, booleanAsString.equalsIgnoreCase(TRUE));
   }

   private static void parseDuration(
         TypedProperties settings,
         String settingName,
         String durationAsString)
   throws InvalidSetting
   {
      try {
         settings.setDuration(settingName, Duration.ofSeconds(Long.parseLong(durationAsString)));
      } catch (NumberFormatException e) {
         throw new InvalidSetting("'" + durationAsString + "' is not a valid duration: " + e.getMessage());
      }
   }

   private static void parsePath(
         TypedProperties settings,
         String settingName,
         String path)
   throws InvalidSetting
   {
      try {
         Paths.get(path);
      } catch (InvalidPathException e) {
         throw new InvalidSetting("'" + path + "' is not a valid path: " + e.getMessage());
      }
      settings.setString(settingName, path);
   }

   public static void parseProtocol(
         TypedProperties settings,
         String protocol)
   throws InvalidSetting
   {
      // It's ok to specify the protocol case insensitively (tcp, TCP, TcP e.g.)
      if (Arrays.stream(ALLOWED_PROTOCOLS).noneMatch(protocol::equalsIgnoreCase)) {
         throw new InvalidSetting("'" + protocol + "' is not a supported transport protocol.");
      }
      settings.setString(SETTING_NAME_CONNECTION_PROTOCOL, protocol.toLowerCase());
   }

   private static void parseUnsignedInt16(
         TypedProperties settings,
         String settingName,
         String numberAsString)
   throws InvalidSetting
   {
      if (!numberAsString.chars().allMatch(Character::isDigit)) {
         throw new InvalidSetting(
               "'" + numberAsString + "' is not a valid unsigned 16 bit integer, must only contain positive integers.");
      }

      int candidate;
      try {
         candidate = Integer.parseInt(numberAsString);
      } catch (NumberFormatException e) {
         throw new InvalidSetting("'" + numberAsString + "' is not a valid unsigned 16 bit integer: " + e.getMessage());
      }

      int min = 0;
      int max = 65535;
      if (candidate < min || max < candidate) {
         throw new InvalidSetting(
               "'" + numberAsString + "' is not a valid unsigned 16 bit integer, valid range is " + min + "-" + max +
                     ".");
      }

      settings.setInt(settingName, candidate);
   }

   private static void parseUnsignedInt32(
         TypedProperties settings,
         String settingName,
         String numberAsString)
   throws InvalidSetting
   {
      if (!numberAsString.chars().allMatch(Character::isDigit)) {
         throw new InvalidSetting(
               "'" + numberAsString + "' is not a valid unsigned 32 bit integer, must only contain positive integers.");
      }

      int candidate;
      try {
         candidate = Integer.parseInt(numberAsString);
      } catch (NumberFormatException e) {
         throw new InvalidSetting("'" + numberAsString + "' is not a valid unsigned 32 bit integer: " + e.getMessage());
      }

      if (candidate < 0) {
         throw new InvalidSetting("'" + numberAsString + "' is not a valid unsigned 32 bit integer, must be positive.");
      }

      settings.setInt(settingName, candidate);
   }

   public static void parseSettingsLine(
         TypedProperties settings,
         String settingString)
   throws InvalidSetting
   {
      if (settingString == null) {
         // No settings provided through this variable, so do nothing here.
         return;
      }
      for (String setting : settingString.split(",")) {
         if (!setting.isEmpty()) {
            String[] settingKeyValuePair = getKeyValuePair(setting);
            try {
               parseSetting(settings, settingKeyValuePair[0], settingKeyValuePair[1]);
            } catch (InvalidSetting e) {
               throw new InvalidSetting("Setting '" + settingString + "' is invalid: " + e.getMessage());
            }
         }
      }
   }

   private static String[] getKeyValuePair(String toSplit)
   throws InvalidSetting
   {
      String[] keyValuePair = toSplit.split("=");
      // Verify that the setting contains exactly one '=' character
      if (keyValuePair.length != 2) {
         throw new InvalidSetting("'" + toSplit + "' must have the format '<key>=<value>'.");
      }
      return keyValuePair;
   }

}
