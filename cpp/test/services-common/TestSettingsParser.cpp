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

#include <services-common/SettingsParser.h>
#include <utility/StringUtil.h>

#include <gtest/gtest.h>

#include <string>

using namespace FedPro;

class TestSettingsParser : public ::testing::Test
{
protected:

   const std::string randomString{"something_random"};
   const uint16_t randomUint16{7};

   void SetUp() override
   {
      unsetEnvironmentVariable(FEDPRO_CLIENT_SETTINGS);
      unsetEnvironmentVariable(FEDPRO_CLIENT_OVERRIDES);
   }

   void TearDown() override
   {
      unsetEnvironmentVariable(FEDPRO_CLIENT_SETTINGS);
      unsetEnvironmentVariable(FEDPRO_CLIENT_OVERRIDES);
   }

   static void setEnvironmentVariable(
         const std::string & name,
         const std::string & value)
   {
      int returnCode;
#ifdef _WIN32
      const std::string env_var = name + "=" + value;
      returnCode = _putenv(env_var.c_str());
#else
      returnCode = setenv(name.c_str(), value.c_str(), 1);
#endif
      if (returnCode != 0) {
         std::cerr << "Failed to set environment variable: " << name << std::endl;
      }
   }

   static void unsetEnvironmentVariable(const std::string & name)
   {
      int returnCode;
#ifdef _WIN32
      const std::string env_var = name + "=";
      returnCode = _putenv(env_var.c_str());
#else
      returnCode = unsetenv(name.c_str());
#endif
      if (returnCode != 0) {
         std::cerr << "Failed to unset environment variable: " << name << std::endl;
      }
   }

   static const char * getEnvironmentVariable(const char * variableName)
   {
      const char * value = std::getenv(variableName);
      if (!value) {
         throw std::invalid_argument{
               "Tried to fetch environment variable '" + std::string{variableName} + "' but failed"};
      }
      return value;
   }

};

TEST_F(TestSettingsParser, parse_Given_NoSettingsLine)
{
   // Given no input

   // When
   Properties clientSettings = SettingsParser::parse();

   // Then
   EXPECT_TRUE(clientSettings.empty());
}

TEST_F(TestSettingsParser, parseS_Given_EmptySettingsLine)
{
   // Given
   string_view settingsLine;

   // When
   Properties clientSettings = SettingsParser::parse(settingsLine);

   // Then
   EXPECT_TRUE(clientSettings.empty());
}

TEST_F(TestSettingsParser, parseSucceeds_When_ValidHostnameInSettingsLine)
{
   // Given
   const char * invalidHostnames[] = {"123.123.123", "::1", "2001:0000:130F:0000:0000:09C0:876A:130B", "server.local"};

   for (const char * validHostname : invalidHostnames) {
      std::string hostSetting{SETTING_NAME_CONNECTION_HOST};
      hostSetting += "=";
      hostSetting += validHostname;

      // When
      Properties settings = SettingsParser::parse(hostSetting);

      // Then
      EXPECT_EQ(validHostname, settings.getString(SETTING_NAME_CONNECTION_HOST, randomString));
      EXPECT_EQ(randomUint16, settings.getUnsignedInt16(SETTING_NAME_CONNECTION_PORT, randomUint16));
   }
}

TEST_F(TestSettingsParser, parseValidPortThroughSettingsLine)
{
   // Given
   std::string portSetting{SETTING_NAME_CONNECTION_PORT};
   portSetting += "=1337";
   // When
   Properties settings = SettingsParser::parse(portSetting);
   // Then
   EXPECT_EQ(randomString, settings.getString(SETTING_NAME_CONNECTION_HOST, randomString));
   EXPECT_EQ(1337, settings.getUnsignedInt16(SETTING_NAME_CONNECTION_PORT, randomUint16));
}

TEST_F(TestSettingsParser, parseThrows_When_InvalidPortInSettingsLine)
{
   // Given
   const char * invalidPorts[] = {"-1", "65536", "123 =( ", "one"};

   for (const char * invalidPort : invalidPorts) {
      std::string portSetting{SETTING_NAME_CONNECTION_PORT};
      portSetting += "=";
      portSetting += invalidPort;

      EXPECT_THROW(
      // When
            Properties settings = SettingsParser::parse(portSetting),
      // Then
            std::invalid_argument);
   }
}

TEST_F(TestSettingsParser, parse_Given_ValidStringSettingsInParameter)
{
   // Given
   const std::initializer_list<std::pair<string_view, string_view>> validStringSettings =
         {{SETTING_NAME_CONNECTION_HOST, "localhost"},
          {SETTING_NAME_CONNECTION_HOST, "127.0.0.1"},
          {SETTING_NAME_HLA_API_VERSION, "IEEE 1516-2000"},
          {SETTING_NAME_HLA_API_VERSION, "IEEE 1516-2010"}
   };
   for (const auto & validSettingPair : validStringSettings) {
      const std::string settingName{toString(validSettingPair.first)};
      const std::string settingsLine{settingName + "=" + validSettingPair.second};
      // When
      Properties settings = SettingsParser::parse(settingsLine);
      // Then
      EXPECT_EQ(validSettingPair.second, settings.getString(settingName, ""));
   }
}

TEST_F(TestSettingsParser, parse_Given_Hostname127Dot1InSettingsLine)
{
   // Given
   string_view settingsLine{"connect.hostname=127.0.0.1"};
   // When
   Properties settings = SettingsParser::parse(settingsLine);
   // Then
   EXPECT_EQ("127.0.0.1", settings.getString(SETTING_NAME_CONNECTION_HOST, ""));
}

TEST_F(TestSettingsParser, parse_Given_HostnameAndPortInSettingsLine)
{
   // Given
   std::string settingsString{SETTING_NAME_CONNECTION_PORT};
   settingsString += "=1337,";
   settingsString += SETTING_NAME_CONNECTION_HOST;
   settingsString += "=123.123";
   // When
   Properties settings = SettingsParser::parse(settingsString);
   // Then
   EXPECT_EQ("123.123", settings.getString(SETTING_NAME_CONNECTION_HOST, randomString));
   EXPECT_EQ(1337, settings.getUnsignedInt16(SETTING_NAME_CONNECTION_PORT, randomUint16));
}

TEST_F(TestSettingsParser, parse_Given_HostnameAndPortAndCommaInSettingsLine)
{
   // Given
   std::string settingsString{SETTING_NAME_CONNECTION_PORT};
   settingsString += "=1337,";
   settingsString += SETTING_NAME_CONNECTION_HOST;
   settingsString += "=123.123,";
   // When
   Properties settings = SettingsParser::parse(settingsString);
   // Then
   EXPECT_EQ("123.123", settings.getString(SETTING_NAME_CONNECTION_HOST, randomString));
   EXPECT_EQ(1337, settings.getUnsignedInt16(SETTING_NAME_CONNECTION_PORT, randomUint16));
}

TEST_F(TestSettingsParser, SettingsParse_When_SettingsProvidedThroughSettingsLine)
{
   // Given
   const std::string settingsString{"asyncUpdates=true"};
   // When
   Properties settings = SettingsParser::parse(settingsString);
   // Then
   EXPECT_EQ(randomString, settings.getString(SETTING_NAME_CONNECTION_HOST, randomString));
   EXPECT_EQ(true, settings.getBoolean(SETTING_NAME_ASYNC_UPDATES, false));
}

TEST_F(TestSettingsParser, SettingsParse_When_CaseInsensitiveSettingsProvidedThroughSettingsLine)
{
   // Given
   const std::string hostname{"LOCALhost"}, protocol{"webSOCKet"};
   std::string settingsString{SETTING_NAME_CONNECTION_HOST};
   settingsString += "=" + hostname + ",asyncUpdates=trUE,connect.protocol=" + protocol;
   // When
   Properties settings = SettingsParser::parse(settingsString);
   // Then
   EXPECT_EQ(hostname, settings.getString(SETTING_NAME_CONNECTION_HOST, randomString));
   EXPECT_EQ(true, settings.getBoolean(SETTING_NAME_ASYNC_UPDATES, false));
   EXPECT_EQ(WS, settings.getString(SETTING_NAME_CONNECTION_PROTOCOL, randomString));
}

TEST_F(TestSettingsParser, parseThrows_Given_UnknownProtocolInSettingsLine)
{
   // Given
   string_view settingsLine{"connect.protocol=unknownProto"};
   EXPECT_THROW(
   // When
         SettingsParser::parse(settingsLine),
   // Then
         std::invalid_argument);
}

TEST_F(TestSettingsParser, parseThrowsOnInvalidInput)
{
   EXPECT_THROW(
   // Given, When
         SettingsParser::parse("noAssignment"),
   // Then
         std::invalid_argument);
}

TEST_F(TestSettingsParser, parseThrows_Given_InvalidNameInSettingsLine)
{
   // Given
   for (const auto & invalidName : {"", ".", " ", "invalidSetting"}) {
      std::string invalidSetting{invalidName};
      invalidSetting += "=true";
      EXPECT_THROW(
      //  When
            SettingsParser::parse(invalidSetting),
      // Then
            std::invalid_argument);
   }
}


TEST_F(TestSettingsParser, parseThrows_Given_IncorrectNameCaseInSettingsLine)
{
   // Given
   string_view settingsLine{"connect.proTOCOL=tcp"};
   EXPECT_THROW(
   // When
         SettingsParser::parse(settingsLine),
   // Then
         std::invalid_argument);
}

TEST_F(TestSettingsParser, parseWithStringValue)
{
   // Given
   for (const auto & validValue : {"-", "=", " ", "\t"}) {
      std::string validSetting{SETTING_NAME_ROTATING_FILE_LOG_PATH};
      validSetting += "=";
      validSetting += validValue;
      //  When
      Properties settings = SettingsParser::parse(validSetting);
      // Then
      EXPECT_EQ(settings.getString(SETTING_NAME_ROTATING_FILE_LOG_PATH, ""), validValue);
   }
}

// Environment Variables Tests

TEST_F(TestSettingsParser, EnvVarsAreSet_When_TheyAreSet)
{
   // Given
   const std::string value1{"value1"}, value2{"value2"};
   // When
   setEnvironmentVariable(FEDPRO_CLIENT_SETTINGS, value1);
   setEnvironmentVariable(FEDPRO_CLIENT_OVERRIDES, value2);
   // Then
   EXPECT_EQ(getEnvironmentVariable(FEDPRO_CLIENT_SETTINGS), value1);
   EXPECT_EQ(getEnvironmentVariable(FEDPRO_CLIENT_OVERRIDES), value2);
}

TEST_F(TestSettingsParser, EnvVarsAreNotSet_When_TheyAreNotSet)
{
   EXPECT_EQ(std::getenv(FEDPRO_CLIENT_SETTINGS), nullptr);
   EXPECT_EQ(std::getenv(FEDPRO_CLIENT_OVERRIDES), nullptr);
}

TEST_F(TestSettingsParser, SettingsParseDefaults_When_EnvVarsHaveNoValues)
{
   setEnvironmentVariable(FEDPRO_CLIENT_SETTINGS, "");
   setEnvironmentVariable(FEDPRO_CLIENT_OVERRIDES, "");
   EXPECT_TRUE(SettingsParser::parse().empty());
}

TEST_F(TestSettingsParser, SettingsParse_When_SettingsAreFetchedFromEnvVars)
{
   // Given
   setEnvironmentVariable(FEDPRO_CLIENT_SETTINGS, "connect.timeout=90");
   setEnvironmentVariable(FEDPRO_CLIENT_OVERRIDES, "connect.maxRetryAttempts=14");
   // When
   const Properties settings = SettingsParser::parse();
   // Then
   EXPECT_EQ(std::chrono::seconds(90), settings.getDuration(SETTING_NAME_CONNECTION_TIMEOUT, std::chrono::seconds(1)));
   EXPECT_EQ(14, settings.getUnsignedInt32(SETTING_NAME_CONNECTION_MAX_RETRY_ATTEMPTS, 0));
}

TEST_F(TestSettingsParser, SettingsParse_When_MultipleSettingsAreFetchedFromEnvVars)
{
   // Given
   setEnvironmentVariable(FEDPRO_CLIENT_SETTINGS, "connect.timeout=91,asyncUpdates=true");
   setEnvironmentVariable(FEDPRO_CLIENT_OVERRIDES, "connect.maxRetryAttempts=15,connect.hostname=1337.0.0");
   // When
   const Properties settings = SettingsParser::parse();
   // Then
   EXPECT_EQ(std::chrono::seconds(91), settings.getDuration(SETTING_NAME_CONNECTION_TIMEOUT, std::chrono::seconds(1)));
   EXPECT_EQ(true, settings.getBoolean(SETTING_NAME_ASYNC_UPDATES, false));
   EXPECT_EQ(15, settings.getUnsignedInt32(SETTING_NAME_CONNECTION_MAX_RETRY_ATTEMPTS, 0));
   EXPECT_EQ("1337.0.0", settings.getString(SETTING_NAME_CONNECTION_HOST, randomString));
}

TEST_F(TestSettingsParser, SettingsThrows_When_InvalidSettingNameFetchedFromEnvVar)
{
   // Given
   setEnvironmentVariable(FEDPRO_CLIENT_SETTINGS, "random_invalid_setting=90");
   EXPECT_THROW(
   // When
         SettingsParser::parse(),
   // Then
         std::invalid_argument);
}

TEST_F(TestSettingsParser, SettingsThrows_When_InvalidSettingValueFetchedFromEnvVar)
{
   // Given
   setEnvironmentVariable(FEDPRO_CLIENT_SETTINGS, "connect.port=Mr_Im-not-an-integer");
   EXPECT_THROW(
   // When
         SettingsParser::parse(),
   // Then
         std::invalid_argument);
}

// Order of precedence Tests

TEST_F(TestSettingsParser, SettingsPrioritizeSettingsLine_When_SettingProvidedThroughEnvVarAndSettingsLine)
{
   // Given
   setEnvironmentVariable(FEDPRO_CLIENT_SETTINGS, "connect.port=1");
   // When
   const Properties settings = SettingsParser::parse("connect.port=2");
   // Then
   EXPECT_EQ(2, settings.getUnsignedInt16(SETTING_NAME_CONNECTION_PORT, 0));
}

TEST_F(TestSettingsParser, SettingsPrioritizeOverridesEnVar_When_SettingProvidedThroughOverridesEnvVarAndSettingsLine)
{
   // Given
   setEnvironmentVariable(FEDPRO_CLIENT_OVERRIDES, "connect.port=1");
   // When
   const Properties settings = SettingsParser::parse("connect.port=2");
   // Then
   EXPECT_EQ(1, settings.getUnsignedInt16(SETTING_NAME_CONNECTION_PORT, 0));
}

TEST_F(TestSettingsParser, SettingsPrioritizeOverridesEnVar_When_SettingProvidedThroughBothEnvVars)
{
   // Given
   setEnvironmentVariable(FEDPRO_CLIENT_SETTINGS, "connect.port=1");
   setEnvironmentVariable(FEDPRO_CLIENT_OVERRIDES, "connect.port=2");
   // When
   const Properties settings = SettingsParser::parse();
   // Then
   EXPECT_EQ(2, settings.getUnsignedInt16(SETTING_NAME_CONNECTION_PORT, 0));
}

TEST_F(TestSettingsParser, SettingsPrioritizeOverridesEnVar_When_SettingIsProvidedInEveryWay)
{
   // Given
   setEnvironmentVariable(FEDPRO_CLIENT_SETTINGS, "connect.port=1");
   setEnvironmentVariable(FEDPRO_CLIENT_OVERRIDES, "connect.port=2");
   // When
   const Properties settings = SettingsParser::parse("connect.port=3");
   // Then
   EXPECT_EQ(2, settings.getUnsignedInt16(SETTING_NAME_CONNECTION_PORT, 0));
}
