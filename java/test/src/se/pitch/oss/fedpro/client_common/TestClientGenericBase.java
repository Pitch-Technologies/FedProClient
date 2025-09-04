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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static se.pitch.oss.fedpro.client_common.RTIambassadorClientGenericBase.splitFederateConnectSettings;

public class TestClientGenericBase {

   @Test
   public void connectSettingsAreSplitCorrectly_When_ThereAreNoEntries()
   {
      // Given
      String inputValueList = "";

      // When
      ArrayList<String> inputList = splitFederateConnectSettings(inputValueList);

      // Then
      assertEquals(inputList, List.of(""));
   }

   @Test
   public void emptyElementsArePreserved_When_EmptyEntriesExists()
   {
      // Given
      String inputValueList = "\n\na\n\nb\n";

      // When
      ArrayList<String> inputList = splitFederateConnectSettings(inputValueList);

      // Then
      assertEquals(inputList, Arrays.asList("", "", "a", "", "b", ""));
   }

   @Test
   public void connectSettingsAreSplitCorrectly_When_EntriesAreSeperatedByLineBreaks()
   {
      // Given
      String inputValueList = String.join("\n", "crcHost=rti.test.local", "crcHost=rti.test.local");

      // When
      ArrayList<String> inputList = splitFederateConnectSettings(inputValueList);

      // Then
      assertEquals(inputList, Arrays.asList("crcHost=rti.test.local", "crcHost=rti.test.local"));
   }

   @Test
   public void connectSettingsAreSplitCorrectly_When_EntriesAreSeperatedByComma()
   {
      // Given
      String inputValueList = String.join("\n", "crcHost=rti.test.local,crcHost=rti.test.local");

      // When
      ArrayList<String> inputList = splitFederateConnectSettings(inputValueList);

      // Then
      assertEquals(inputList, Arrays.asList("crcHost=rti.test.local", "crcHost=rti.test.local"));
   }

   @Test
   public void connectSettingsAreSplitCorrectly_When_EntriesAreSeperatedByCommaAndLineBreaks()
   {
      // Given
      String inputValueList = String.join(
            "\n",
            "LRC.skipVersionCheck=false",
            "FedPro.connect.hostname=localhost",
            "FedPro.connect.timeout=10,FedPro.FED_TIMEOUT_HEART=100",
            "crcHost=rti.test.local",
            "FedPro.messageQueue.outgoing.limitedRate=false");

      // When
      ArrayList<String> inputList = splitFederateConnectSettings(inputValueList);

      // Then
      assertEquals(inputList, Arrays.asList(
            "LRC.skipVersionCheck=false",
            "FedPro.connect.hostname=localhost",
            "FedPro.connect.timeout=10",
            "FedPro.FED_TIMEOUT_HEART=100",
            "crcHost=rti.test.local",
            "FedPro.messageQueue.outgoing.limitedRate=false"));
   }

}
