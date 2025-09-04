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

import org.junit.*;

import se.pitch.oss.fedpro.client.TypedProperties;
import se.pitch.oss.fedpro.client_common.exceptions.InvalidSetting;
import se.pitch.oss.fedpro.common.Protocol;
import se.pitch.oss.fedpro.utility.SettingsHelper;

import java.time.Duration;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import static org.junit.Assert.*;
import static se.pitch.oss.fedpro.client.Settings.*;

public class TestSettingsParser {

   // This class tests providing settings through settingsLine string and system properties but not environment
   // variables due to the complexity of setting and clearing them.

   private static final String SYSPOP_FEDPRO_TEST_NAME = SETTING_PREFIX + "test_key";
   private static final String placeholderString = "some_placeholder_string";
   private static final int placeholderZeroInt = 0;
   private Properties _previousSysprops;

   @Before
   public void setUp() {
      SettingsHelper.assertNoOverrides();
      _previousSysprops = System.getProperties();
      SettingsHelper.clearFedProSysProps();
   }

   @After
   public void tearDown()
   {
      SettingsHelper.clearFedProOverrides();
      SettingsHelper.clearFedProSysProps();
      System.setProperties(_previousSysprops);
   }

   @Test
   public void parse_Given_NoSettingsLine()
   throws InvalidSetting
   {
      // Given no input

      // When
      TypedProperties clientSettings = SettingsParser.parse();

      // Then
      assertTrue(clientSettings.isEmpty());
   }

   @Test
   public void parseS_Given_EmptySettingsLine()
   throws InvalidSetting
   {
      // Given
      String settingsLine = "";

      // When
      TypedProperties clientSettings = SettingsParser.parse(settingsLine);

      // Then
      assertTrue(clientSettings.isEmpty());
   }

   // Settings Line Tests

   @Test
   public void parseSucceeds_When_ValidHostnameInSettingsLine()
   throws InvalidSetting
   {
      // Given
      String[] validHostnames = {
            "123.123.123",
            "::1",
            "2001:0000:130F:0000:0000:09C0:876A:130B",
            "server.local"
      };

      for (String validHostname : validHostnames) {

         // When
         TypedProperties settings = SettingsParser.parse(SETTING_NAME_CONNECTION_HOST + "=" + validHostname);

         // Then
         assertEquals(validHostname, settings.getString(SETTING_NAME_CONNECTION_HOST, placeholderString));
         assertEquals(placeholderZeroInt, settings.getInt(SETTING_NAME_CONNECTION_PORT, placeholderZeroInt));
      }
   }

   @Test
   public void parseValidPortThroughSettingsLine()
   throws InvalidSetting
   {
      // Given
      int port = 1337;
      String settingsLine = "connect.port=" + port;
      // When
      TypedProperties settings = SettingsParser.parse(settingsLine);
      // Then
      assertEquals(placeholderString, settings.getString(SETTING_NAME_CONNECTION_HOST, placeholderString));
      assertEquals(port, settings.getInt(SETTING_NAME_CONNECTION_PORT, placeholderZeroInt));
   }

   @Test
   public void parseThrows_When_InvalidPortInSettingsLine()
   {
      // Given
      String[] invalidPorts = {
            "-1",
            "65536",
            "123 =( ",
            "one"
      };

      for (String invalidPort : invalidPorts) {
         try {
            // When
            SettingsParser.parse(SETTING_NAME_CONNECTION_PORT + "=" + invalidPort);
            fail("Expected InvalidSetting exception");
         } catch (InvalidSetting ignored) {
            // Then
         }
      }
   }

   @Test
   public void parse_Given_ValidStringSettingsInParameter()
   throws InvalidSetting
   {
      // Given
      ArrayList<Map.Entry<String, String>> validStringSettings = new ArrayList<>();
      validStringSettings.add(new AbstractMap.SimpleImmutableEntry<>(SETTING_NAME_CONNECTION_HOST, "localhost"));
      validStringSettings.add(new AbstractMap.SimpleImmutableEntry<>(SETTING_NAME_CONNECTION_HOST, "127.0.0.1"));
      validStringSettings.add(new AbstractMap.SimpleImmutableEntry<>(SETTING_NAME_HLA_API_VERSION, "IEEE 1516-2000"));
      validStringSettings.add(new AbstractMap.SimpleImmutableEntry<>(SETTING_NAME_HLA_API_VERSION, "IEEE 1516-2010"));

      for (Map.Entry<String, String> validStringSetting : validStringSettings) {
         String settingsLine = validStringSetting.getKey() + "=" + validStringSetting.getValue();

         // When
         TypedProperties clientSettings = SettingsParser.parse(settingsLine);

         // Then
         assertEquals(validStringSetting.getValue(), clientSettings.getString(validStringSetting.getKey(), ""));
      }
   }

   @Test
   public void parse_Given_Hostname127Dot1InSettingsLine()
   throws InvalidSetting
   {
      // Given
      String settingsLine = "connect.hostname=127.0.0.1";

      // When
      TypedProperties clientSettings = SettingsParser.parse(settingsLine);

      // Then
      assertEquals("127.0.0.1", clientSettings.getString("connect.hostname", ""));
   }

   @Test
   public void parse_Given_HostnameAndPortInSettingsLine()
   throws InvalidSetting
   {
      // Given
      int port = 1337;
      String hostname = "123.123";
      String settingsLine = "connect.hostname=" + hostname + ",connect.port=" + port;
      // When
      TypedProperties settings = SettingsParser.parse(settingsLine);
      // Then
      assertEquals(hostname, settings.getString(SETTING_NAME_CONNECTION_HOST, placeholderString));
      assertEquals(port, settings.getInt(SETTING_NAME_CONNECTION_PORT, placeholderZeroInt));
   }

   @Test
   public void parse_Given_HostnameAndPortAndCommaInSettingsLine()
   throws InvalidSetting
   {
      // Given
      String settingsLine = "connect.hostname=127.0.0.1,connect.port=1234,";

      // When
      TypedProperties clientSettings = SettingsParser.parse(settingsLine);

      // Then
      assertEquals(clientSettings.getString("connect.hostname", ""), "127.0.0.1");
      assertEquals(clientSettings.getInt("connect.port", 0), 1234);
   }

   @Test(expected = InvalidSetting.class)
   public void settingsThrows_When_HostnameAndColonWithoutPortProvidedThroughSettingsLine()
   throws InvalidSetting
   {
      // Given
      String settingsLine = "127.0.0.1:";
      // When
      SettingsParser.parse(settingsLine);
      // Then throw
   }

   @Test
   public void settingIsIgnored_Given_UnknownNameInSettingsLine()
   throws InvalidSetting
   {
      // Given
      String settingsLine = "unknownSetting=true";
      // When
      TypedProperties settings = SettingsParser.parse(settingsLine);
      // Then
      assertFalse(settings.getBoolean("unknownSetting", false));
   }

   @Test
   public void settingsParse_Given_IncorrectNameCaseInSettingsLine()
   throws InvalidSetting
   {
      // Given
      String settingsLine = "connect.proTOCOL=tcp";
      // When
      TypedProperties settings = SettingsParser.parse(settingsLine);
      // Then
      assertNull(settings.getString("connect.protocol", null));
   }

   @Test
   public void settingsParse_When_SettingsProvidedThroughSettingsLine()
   throws InvalidSetting
   {
      // Given
      String settingsLine = "asyncUpdates=true";
      // When
      TypedProperties settings = SettingsParser.parse(settingsLine);
      // Then
      assertEquals(placeholderString, settings.getString(SETTING_NAME_CONNECTION_HOST, placeholderString));
      assertTrue(settings.getBoolean(SETTING_NAME_ASYNC_UPDATES, false));
   }

   @Test
   public void settingsParse_When_HostnameAndPortAndSettingsProvidedThroughSettingsLine()
   throws InvalidSetting
   {
      // Given
      String hostname = "123";
      int port = 7;
      String settingsLine =
            "connect.hostname=" + hostname + ",connect.port=" + port + ",asyncUpdates=true,connect.protocol=websocket";
      // When
      TypedProperties settings = SettingsParser.parse(settingsLine);
      // Then
      assertEquals(hostname, settings.getString(SETTING_NAME_CONNECTION_HOST, placeholderString));
      assertEquals(port, settings.getInt(SETTING_NAME_CONNECTION_PORT, placeholderZeroInt));
      assertTrue(settings.getBoolean(SETTING_NAME_ASYNC_UPDATES, false));
      assertEquals(Protocol.WS, settings.getString(SETTING_NAME_CONNECTION_PROTOCOL, placeholderString));
   }

   @Test
   public void settingsParse_When_CaseInsensitiveSettingsProvidedThroughSettingsLine()
   throws InvalidSetting
   {
      // Given
      String hostname = "LOCALhost";
      String protocol = "webSOCKet";
      String settingsLine = "connect.hostname=" + hostname + ",asyncUpdates=trUE,connect.protocol=" + protocol;
      // When
      TypedProperties settings = SettingsParser.parse(settingsLine);
      // Then
      assertEquals(hostname, settings.getString(SETTING_NAME_CONNECTION_HOST, placeholderString));
      assertTrue(settings.getBoolean(SETTING_NAME_ASYNC_UPDATES, false));
      assertEquals(Protocol.WS, settings.getString(SETTING_NAME_CONNECTION_PROTOCOL, placeholderString));
   }

   @Test(expected = InvalidSetting.class)
   public void parseThrows_Given_UnknownProtocolInSettingsLine()
   throws Exception
   {
      // Given
      String settingsLine = "connect.protocol=unknownProto";

      // When
      SettingsParser.parse(settingsLine);

      // Then throw
   }

   // Java System Properties Tests

   @Test
   public void sysPropsAreSet_When_TheyAreSet()
   {
      // Given
      String value1 = "value1";
      // When
      System.setProperty(SYSPOP_FEDPRO_TEST_NAME, value1);
      // Then
      assertEquals(value1, System.getProperty(SYSPOP_FEDPRO_TEST_NAME));
   }

   @Test
   public void sysPropsAreNotSet_When_TheyAreNotSet()
   {
      assertNull(System.getProperty(SYSPOP_FEDPRO_TEST_NAME));
   }

   @Test
   public void sysPropIsSet_When_ItIsSetWithoutValue()
   {
      // Given
      String value = "";
      // When
      System.setProperty(SYSPOP_FEDPRO_TEST_NAME, value);
      // Then
      assertEquals(value, System.getProperty(SYSPOP_FEDPRO_TEST_NAME));
   }

   @Test
   public void settingsParse_When_SettingsAreFetchedFromSysProps()
   throws InvalidSetting
   {
      // Given
      System.setProperty("FedPro.connect.timeout", "90");
      System.setProperty("FedProOverrides.connect.maxRetryAttempts", "14");
      // When
      TypedProperties settings = SettingsParser.parse();
      // Then
      assertEquals(
            Duration.ofSeconds(90),
            settings.getDuration(SETTING_NAME_CONNECTION_TIMEOUT, Duration.ofSeconds(1)));
      assertEquals(14, settings.getInt(SETTING_NAME_CONNECTION_MAX_RETRY_ATTEMPTS, placeholderZeroInt));
   }

   @Test
   public void settingsParse_When_MultipleSettingsAreFetchedFromSysProps()
   throws InvalidSetting
   {
      // Given
      System.setProperty("FedPro.connect.timeout", "91");
      System.setProperty("FedPro.asyncUpdates", "true");
      System.setProperty("FedProOverrides.connect.maxRetryAttempts", "15");
      System.setProperty("FedProOverrides.connect.hostname", "1337.0.0");
      // When
      TypedProperties settings = SettingsParser.parse();
      // Then
      assertEquals(
            Duration.ofSeconds(91),
            settings.getDuration(SETTING_NAME_CONNECTION_TIMEOUT, Duration.ofSeconds(91)));
      assertTrue(settings.getBoolean(SETTING_NAME_ASYNC_UPDATES, false));
      assertEquals(15, settings.getInt(SETTING_NAME_CONNECTION_MAX_RETRY_ATTEMPTS, placeholderZeroInt));
      assertEquals("1337.0.0", settings.getString(SETTING_NAME_CONNECTION_HOST, placeholderString));
   }

   @Test
   public void settingIsIgnored_Given_UnknownSettingNameInSysProp()
   throws InvalidSetting
   {
      // Given
      System.setProperty("FedPro.random_unknown_setting", "90");
      // When
      TypedProperties settings = SettingsParser.parse();
      // Then
      assertEquals(0, settings.getInt("random_unknown_setting", 0));
   }

   @Test(expected = InvalidSetting.class)
   public void settingsThrows_When_InvalidSettingValueFetchedFromSysProp()
   throws InvalidSetting
   {
      // Given
      System.setProperty("FedPro.connect.port", "Mr_Im-not-an-integer");
      // When
      SettingsParser.parse();
      // Then throw
   }

   // Order of precedence Tests

   @Test
   public void settingsPrioritizeSettingsLine_When_SettingProvidedThroughSysPropAndSettingsLine()
   throws InvalidSetting
   {
      // Given
      System.setProperty("FedPro.connect.port", "1");
      // When
      TypedProperties settings = SettingsParser.parse("connect.port=2");
      // Then
      assertEquals(2, settings.getInt(SETTING_NAME_CONNECTION_PORT, placeholderZeroInt));
   }

   @Test
   public void settingsPrioritizeOverridesSysProp_When_SettingProvidedThroughOverridesSysPropAndSettingsLine()
   throws InvalidSetting
   {
      // Given
      System.setProperty("FedProOverrides.connect.port", "1");
      // When
      TypedProperties settings = SettingsParser.parse("connect.port=2");
      // Then
      assertEquals(1, settings.getInt(SETTING_NAME_CONNECTION_PORT, placeholderZeroInt));
   }

   @Test
   public void settingsPrioritizeOverridesSysProp_When_SettingProvidedThroughBothSysProps()
   throws InvalidSetting
   {
      // Given
      System.setProperty("FedPro.connect.port", "1");
      System.setProperty("FedProOverrides.connect.port", "2");
      // When
      TypedProperties settings = SettingsParser.parse();
      // Then
      assertEquals(2, settings.getInt(SETTING_NAME_CONNECTION_PORT, placeholderZeroInt));
   }

   @Test
   public void settingsPrioritizeOverridesSysProp_When_SettingIsProvidedInEveryWay()
   throws InvalidSetting
   {
      // Given
      System.setProperty("FedPro.connect.port", "1");
      System.setProperty("FedProOverrides.connect.port", "2");
      // When
      TypedProperties settings = SettingsParser.parse("connect.port=3");
      // Then
      assertEquals(2, settings.getInt(SETTING_NAME_CONNECTION_PORT, placeholderZeroInt));
   }

   @Test
   public void parseSystemProperty_GivenValidSetting()
   throws InvalidSetting
   {
      // Given
      SettingsHelper.setFedProOverride(SETTING_NAME_MESSAGE_QUEUE_SIZE, "200");

      // When
      TypedProperties clientSettings = SettingsParser.parse();

      // Then
      assertEquals(clientSettings.getInt(SETTING_NAME_MESSAGE_QUEUE_SIZE, 0), 200);
   }

   @Test(expected = InvalidSetting.class)
   public void parseSystemProperty_GivenInvalidSetting()
   throws InvalidSetting
   {
      // Given
      SettingsHelper.setFedProOverride(SETTING_NAME_MESSAGE_QUEUE_SIZE, "invalidValue");

      // When
      SettingsParser.parse();

      // Then
   }

}
