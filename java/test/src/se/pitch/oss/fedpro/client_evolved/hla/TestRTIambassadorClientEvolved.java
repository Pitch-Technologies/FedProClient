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

package se.pitch.oss.fedpro.client_evolved.hla;

import hla.rti1516_2025.fedpro.CallResponse;
import hla.rti1516_2025.fedpro.ExceptionData;
import hla.rti1516_2025.fedpro.RtiConfiguration;
import hla.rti1516e.exceptions.RTIexception;
import org.junit.Test;
import se.pitch.oss.fedpro.client_common.exceptions.FedProRtiException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static se.pitch.oss.fedpro.client_evolved.hla.RTIambassadorClientEvolvedBase.*;

public class TestRTIambassadorClientEvolved {

   @Test
   public void clientSettingsAreExtractedAndRemovedCorrectly_When_FedProServerHostnameIsProvidedAsCrcAddress()
   {
      // Given
      ArrayList<String> inputList = new ArrayList<>(Collections.singletonList("crcAddress=123.456.789"));

      // When
      String settingsLine = extractAndRemoveClientSettings(inputList, true);

      // Then
      assertEquals(
            settingsLine,
            "connect.hostname=123.456.789");
      assertEquals(inputList, new ArrayList<>());
   }

   @Test
   public void clientSettingsAreExtractedAndRemovedCorrectly_When_FedProServerPortIsProvidedAsCrcAddress()
   {
      // Given
      ArrayList<String> inputList = new ArrayList<>(Collections.singletonList("crcAddress=:1234"));

      // When
      String settingsLine = extractAndRemoveClientSettings(inputList, true);

      // Then
      assertEquals(
            settingsLine,
            "connect.port=1234");
      assertEquals(inputList, new ArrayList<>());
   }

   @Test
   public void clientSettingsAreExtractedAndRemovedCorrectly_When_crcAddressIsProvidedAsFedProServerAddress()
   {
      // Given
      ArrayList<String> inputList = new ArrayList<>(Arrays.asList(
            "FedPro.connect.hostname=localhost",
            "crcAddress=123.456.789:1234",
            "crcHost=rti.test.local",
            "LRC.skipVersionCheck=false"));

      // When
      String settingsLine = extractAndRemoveClientSettings(inputList, true);

      // Then
      assertEquals(
            settingsLine,
            "connect.hostname=localhost,connect.hostname=123.456.789,connect.port=1234");
      assertEquals(inputList, Arrays.asList("crcHost=rti.test.local", "LRC.skipVersionCheck=false"));
   }

   @Test
   public void rtiConfigurationParseCorrectly_When_NoSettingsAreProvided()
   {
      // Given
      ArrayList<String> inputList = new ArrayList<>();

      // When
      RtiConfiguration rtiConfiguration = createRtiConfiguration(inputList).build();

      // Then
      assertEquals(rtiConfiguration.getRtiAddress(), "");
      assertEquals(rtiConfiguration.getConfigurationName(), "");
      assertEquals(rtiConfiguration.getAdditionalSettings(), "");
   }

   @Test
   public void rtiConfigurationParseCorrectly_When_OnlySettingsAreProvided()
   {
      // Given
      ArrayList<String> inputList =
            new ArrayList<>(Arrays.asList("crcHost=rti.test.local", "LRC.skipVersionCheck=false"));

      // When
      RtiConfiguration rtiConfiguration = createRtiConfiguration(inputList).build();

      // Then
      assertEquals(rtiConfiguration.getRtiAddress(), "");
      assertEquals(rtiConfiguration.getConfigurationName(), "");
      assertEquals(
            rtiConfiguration.getAdditionalSettings(),
            String.join("\n", "crcHost=rti.test.local", "LRC.skipVersionCheck=false"));
   }

   @Test
   public void rtiConfigurationParseCorrectly_When_OnlyConfigNameIsProvided()
   {
      // Given
      ArrayList<String> inputList = new ArrayList<>(List.of("iosConfiguration"));

      // When
      RtiConfiguration rtiConfiguration = createRtiConfiguration(inputList).build();

      // Then
      assertEquals(rtiConfiguration.getRtiAddress(), "");
      assertEquals(rtiConfiguration.getConfigurationName(), "iosConfiguration");
      assertEquals(rtiConfiguration.getAdditionalSettings(), "");
   }

   @Test
   public void rtiConfigurationParseCorrectly_When_ConfigNameAndSettingsAreProvided()
   {
      // Given
      ArrayList<String> inputList = new ArrayList<>(Arrays.asList(
            "iosConfiguration",
            "FedPro.connect.timeout=10,",
            "LRC.skipVersionCheck=false",
            "FedPro.connect.hostname=localhost"));

      // When
      RtiConfiguration rtiConfiguration = createRtiConfiguration(inputList).build();

      // Then
      assertEquals(rtiConfiguration.getRtiAddress(), "");
      assertEquals(rtiConfiguration.getConfigurationName(), "iosConfiguration");
      assertEquals(rtiConfiguration.getAdditionalSettings(), String.join("\n", "FedPro.connect.timeout=10,",
            "LRC.skipVersionCheck=false",
            "FedPro.connect.hostname=localhost"));
   }

   @Test(expected = RTIexception.class)
   public void throwOnException_throw_RTIexception()
   throws FedProRtiException
   {
      ClientConverter clientConverter = new ClientConverter();
      RTIambassadorClientEvolvedBase ambassadorClient = new RTIambassadorClientEvolvedBase(clientConverter);

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
