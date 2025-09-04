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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static se.pitch.oss.fedpro.client_common.RTIambassadorClientGenericBase.extractAndRemoveClientSettings;

@RunWith(Parameterized.class)
public class TestExtractAndRemoveClientSettings {

   @Parameterized.Parameter
   public boolean _crcAddressIsFedProServerAddress;

   @Parameterized.Parameters
   public static Collection<Object[]> data()
   {
      // Every test will run as many times as the number of entries we provide in the list below
      return Arrays.asList(new Object[][] {
            {false}, // Run tests with _crcAddressIsFedProServerAddress set to false
            {true} // Run tests with _crcAddressIsFedProServerAddress set to true
      });
   }

   @Test
   public void clientSettingsAreExtractedAndRemovedCorrectly_When_NoSettingsAreProvided()
   {
      // Given
      ArrayList<String> inputList = new ArrayList<>();

      // When
      String settingsLine = extractAndRemoveClientSettings(inputList, _crcAddressIsFedProServerAddress);

      // Then
      assertEquals(settingsLine, "");
      assertEquals(inputList, new ArrayList<>());
   }

   @Test
   public void clientSettingsAreExtractedAndRemovedCorrectly_When_OnlyFedProSettingsAreProvided()
   {
      // Given
      ArrayList<String> inputList = new ArrayList<>(Arrays.asList("FedPro.connect.hostname=localhost",
            "FedPro.connect.timeout=10",
            "FedPro.connect.protocol=tcp"));

      // When
      String settingsLine = extractAndRemoveClientSettings(inputList, _crcAddressIsFedProServerAddress);

      // Then
      assertEquals(settingsLine, "connect.hostname=localhost,connect.timeout=10,connect.protocol=tcp");
      assertEquals(inputList, new ArrayList<>());
   }

   @Test
   public void clientSettingsAreExtractedAndRemovedCorrectly_When_OnlyLrcSettingsAreProvided()
   {
      // Given
      ArrayList<String> inputList =
            new ArrayList<>(Arrays.asList("crcHost=rti.test.local", "LRC.skipVersionCheck=false"));

      // When
      String settingsLine = extractAndRemoveClientSettings(inputList, _crcAddressIsFedProServerAddress);

      // Then
      assertEquals(settingsLine, "");
      assertEquals(inputList, Arrays.asList("crcHost=rti.test.local", "LRC.skipVersionCheck=false"));
   }

   @Test
   public void clientSettingsAreExtractedAndRemovedCorrectly_When_OnlyConfigNameIsProvided()
   {
      // Given
      ArrayList<String> inputList = new ArrayList<>(List.of("iosConfiguration"));

      // When
      String settingsLine = extractAndRemoveClientSettings(inputList, _crcAddressIsFedProServerAddress);

      // Then
      assertEquals(settingsLine, "");
      assertEquals(inputList, List.of("iosConfiguration"));
   }

   @Test
   public void clientSettingsAreExtractedAndRemovedCorrectly_When_ConfigNameAndLrcSettingsAreProvided()
   {
      // Given
      ArrayList<String> inputList = new ArrayList<>(Arrays.asList("iosConfiguration", "LRC.skipVersionCheck=false"));

      // When
      String settingsLine = extractAndRemoveClientSettings(inputList, _crcAddressIsFedProServerAddress);

      // Then
      assertEquals(settingsLine, "");
      assertEquals(inputList, Arrays.asList("iosConfiguration", "LRC.skipVersionCheck=false"));
   }

   @Test
   public void clientSettingsAreExtractedAndRemovedCorrectly_When_ConfigNameAndFedProSettingsAreProvided()
   {
      // Given
      ArrayList<String> inputList = new ArrayList<>(Arrays.asList("iosConfiguration",
            "FedPro.connect.timeout=10",
            "FedPro.connect.protocol=tcp",
            "FedPro.connect.hostname=localhost"));

      // When
      String settingsLine = extractAndRemoveClientSettings(inputList, _crcAddressIsFedProServerAddress);

      // Then
      assertEquals(settingsLine, "connect.timeout=10,connect.protocol=tcp,connect.hostname=localhost");
      assertEquals(inputList, List.of("iosConfiguration"));
   }

   @Test
   public void clientSettingsAreExtractedAndRemovedCorrectly_When_LrcAndFedProSettingsAreProvided()
   {
      // Given
      ArrayList<String> inputList = new ArrayList<>(Arrays.asList("FedPro.connect.hostname=localhost",
            "FedPro.connect.timeout=10",
            "FedPro.connect.protocol=tcp",
            "crcHost=rti.test.local",
            "LRC.skipVersionCheck=false",
            "FedPro.messageQueue.outgoing.limitedRate=false"));

      // When
      String settingsLine = extractAndRemoveClientSettings(inputList, _crcAddressIsFedProServerAddress);

      // Then
      assertEquals(
            settingsLine,
            "connect.hostname=localhost,connect.timeout=10,connect.protocol=tcp,messageQueue.outgoing.limitedRate=false");
      assertEquals(inputList, Arrays.asList("crcHost=rti.test.local", "LRC.skipVersionCheck=false"));
   }

}
