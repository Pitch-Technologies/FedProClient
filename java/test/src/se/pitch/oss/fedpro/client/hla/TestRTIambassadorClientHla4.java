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

package se.pitch.oss.fedpro.client.hla;

import hla.rti1516_2025.RtiConfiguration;

import hla.rti1516_2025.fedpro.CallResponse;
import hla.rti1516_2025.fedpro.ExceptionData;
import hla.rti1516_2025.exceptions.RTIexception;
import org.junit.Test;
import se.pitch.oss.fedpro.client_common.exceptions.FedProRtiException;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static se.pitch.oss.fedpro.client.hla.RTIambassadorClientHla4Base.parseRtiConfiguration;
import static se.pitch.oss.fedpro.client_common.RTIambassadorClientGenericBase.extractAndRemoveClientSettings;

public class TestRTIambassadorClientHla4 {

   @Test
   public void clientSettingsAreExtractedAndRemovedCorrectly_When_crcAddressIsProvided()
   {
      // Given
      ArrayList<String> inputList = new ArrayList<>(Arrays.asList(
            "FedPro.connect.hostname=localhost",
            "crcAddress=123.456.789:1234",
            "crcHost=rti.test.local",
            "LRC.skipVersionCheck=false"));

      // When
      String settingsLine = extractAndRemoveClientSettings(inputList, false);

      // Then
      assertEquals(
            settingsLine,
            "connect.hostname=localhost");
      assertEquals(
            inputList,
            Arrays.asList("crcAddress=123.456.789:1234", "crcHost=rti.test.local", "LRC.skipVersionCheck=false"));
   }

   @Test
   public void rtiConfigurationParseCorrectly_When_NoSettingsAreProvided()
   {
      // Given
      RtiConfiguration providedConfiguration = RtiConfiguration.createConfiguration();
      ArrayList<String> inputList = new ArrayList<>();

      // When
      RtiConfiguration rtiConfiguration = parseRtiConfiguration(providedConfiguration, inputList);

      // Then
      assertEquals(rtiConfiguration.rtiAddress(), "");
      assertEquals(rtiConfiguration.configurationName(), "");
      assertEquals(rtiConfiguration.additionalSettings(), "");
   }

   @Test
   public void rtiConfigurationParseCorrectly_When_OnlySettingsAreProvided()
   {
      // Given
      RtiConfiguration providedConfiguration = RtiConfiguration.createConfiguration();
      ArrayList<String> inputList = new ArrayList<>(Arrays.asList(
            "FedPro.connect.timeout=10,",
            "LRC.skipVersionCheck=false"));

      // When
      RtiConfiguration rtiConfiguration = parseRtiConfiguration(providedConfiguration, inputList);

      // Then
      assertEquals(rtiConfiguration.rtiAddress(), "");
      assertEquals(rtiConfiguration.configurationName(), "");
      assertEquals(rtiConfiguration.additionalSettings(), String.join("\n", "FedPro.connect.timeout=10,",
            "LRC.skipVersionCheck=false"));
   }

   @Test
   public void rtiConfigurationParseCorrectly_When_OnlyConfigNameIsProvided()
   {
      // Given
      RtiConfiguration providedConfiguration =
            RtiConfiguration.createConfiguration().withConfigurationName("iosConfiguration");
      ArrayList<String> inputList = new ArrayList<>();

      // When
      RtiConfiguration rtiConfiguration = parseRtiConfiguration(providedConfiguration, inputList);

      // Then
      assertEquals(rtiConfiguration.rtiAddress(), "");
      assertEquals(rtiConfiguration.configurationName(), "iosConfiguration");
      assertEquals(rtiConfiguration.additionalSettings(), "");
   }

   @Test
   public void rtiConfigurationParseCorrectly_When_ConfigNameAndSettingsAreProvided()
   {
      // Given
      RtiConfiguration providedConfiguration =
            RtiConfiguration.createConfiguration().withConfigurationName("iosConfiguration");
      ArrayList<String> inputList = new ArrayList<>(Arrays.asList(
            "FedPro.connect.timeout=10,",
            "LRC.skipVersionCheck=false",
            "FedPro.connect.hostname=localhost"));

      // When
      RtiConfiguration rtiConfiguration = parseRtiConfiguration(providedConfiguration, inputList);

      // Then
      assertEquals(rtiConfiguration.rtiAddress(), "");
      assertEquals(rtiConfiguration.configurationName(), "iosConfiguration");
      assertEquals(rtiConfiguration.additionalSettings(), String.join("\n", "FedPro.connect.timeout=10,",
            "LRC.skipVersionCheck=false",
            "FedPro.connect.hostname=localhost"));
   }

   @Test(expected = RTIexception.class)
   public void throwOnException_throw_RTIexception()
   throws FedProRtiException
   {
      ClientConverter clientConverter = new ClientConverter();
      RTIambassadorClientHla4Base ambassadorClient = new RTIambassadorClientHla4Base(clientConverter);

      // Given
      String message = "Placeholder message";
      CallResponse exceptionCallResponse = CallResponse.newBuilder()
            .setExceptionData(ExceptionData.newBuilder().setExceptionName("RTIexception").setDetails(message))
            .build();

      // When
      ambassadorClient.throwOnException(exceptionCallResponse);

      // Then throw
   }

}
