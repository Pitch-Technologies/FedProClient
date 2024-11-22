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

#include "services-common/RTIambassadorClientGenericBase.h"

#include <gtest/gtest.h>

using namespace FedPro;

class TestExtractAndRemoveClientSettings : public ::testing::TestWithParam<bool>
{
};

// This makes each tests in this suite run twice,
// once in which GetParam() returns false, and once in which it returns true
INSTANTIATE_TEST_SUITE_P(ClientSettingsTests,                   // Name of the test suite
                         TestExtractAndRemoveClientSettings,    // Test fixture class
                         ::testing::Values(false, true)         // Test with both true and false
);

TEST_P(TestExtractAndRemoveClientSettings, clientSettingsAreExtractedAndRemovedCorrectly_When_NoSettingsAreProvided)
{
   // Given
   std::vector<std::wstring> inputList;

   // When
   std::string settingsLine = RTIambassadorClientGenericBase::extractAndRemoveClientSettings(inputList, GetParam());

   // Then
   EXPECT_EQ(settingsLine, "");
   EXPECT_TRUE(inputList.empty());
}

TEST_P(TestExtractAndRemoveClientSettings,
       clientSettingsAreExtractedAndRemovedCorrectly_When_OnlyFedProSettingsAreProvided)
{
   // Given
   std::vector<std::wstring>
         inputList{L"FedPro.connect.hostname=localhost", L"FedPro.connect.timeout=10", L"FedPro.connect.protocol=tcp"};

   // When
   std::string settingsLine = RTIambassadorClientGenericBase::extractAndRemoveClientSettings(inputList, GetParam());

   // Then
   EXPECT_EQ(settingsLine, "connect.hostname=localhost,connect.timeout=10,connect.protocol=tcp");
   EXPECT_TRUE(inputList.empty());
}

TEST_P(TestExtractAndRemoveClientSettings,
       clientSettingsAreExtractedAndRemovedCorrectly_When_OnlyLrcSettingsAreProvided)
{
   // Given
   std::vector<std::wstring> inputList{L"crcHost=rti.test.local", L"LRC.skipVersionCheck=false"};

   // When
   std::string settingsLine = RTIambassadorClientGenericBase::extractAndRemoveClientSettings(inputList, GetParam());

   // Then
   EXPECT_EQ(settingsLine, "");
   EXPECT_EQ(inputList, (std::vector<std::wstring>{L"crcHost=rti.test.local", L"LRC.skipVersionCheck=false"}));
}

TEST_P(TestExtractAndRemoveClientSettings, clientSettingsAreExtractedAndRemovedCorrectly_When_OnlyConfigNameIsProvided)
{
   // Given
   std::vector<std::wstring> inputList{L"iosConfiguration"};

   // When
   std::string settingsLine = RTIambassadorClientGenericBase::extractAndRemoveClientSettings(inputList, GetParam());

   // Then
   EXPECT_EQ(settingsLine, "");
   EXPECT_EQ(inputList, std::vector<std::wstring>{L"iosConfiguration"});
}

TEST_P(TestExtractAndRemoveClientSettings,
       clientSettingsAreExtractedAndRemovedCorrectly_When_ConfigNameAndLrcSettingsAreProvided)
{
   // Given
   std::vector<std::wstring> inputList{L"iosConfiguration", L"LRC.skipVersionCheck=false"};

   // When
   std::string settingsLine = RTIambassadorClientGenericBase::extractAndRemoveClientSettings(inputList, GetParam());

   // Then
   EXPECT_EQ(settingsLine, "");
   EXPECT_EQ(inputList, (std::vector<std::wstring>{L"iosConfiguration", L"LRC.skipVersionCheck=false"}));
}

TEST_P(TestExtractAndRemoveClientSettings,
       clientSettingsAreExtractedAndRemovedCorrectly_When_ConfigNameAndFedProSettingsAreProvided)
{
   // Given
   std::vector<std::wstring> inputList
         {L"iosConfiguration", L"FedPro.connect.timeout=10", L"FedPro.connect.protocol=tcp",
          L"FedPro.connect.hostname=localhost"};

   // When
   std::string settingsLine = RTIambassadorClientGenericBase::extractAndRemoveClientSettings(inputList, GetParam());

   // Then
   EXPECT_EQ(settingsLine, "connect.timeout=10,connect.protocol=tcp,connect.hostname=localhost");
   EXPECT_EQ(inputList, std::vector<std::wstring>{L"iosConfiguration"});
}

TEST_P(TestExtractAndRemoveClientSettings,
       clientSettingsAreExtractedAndRemovedCorrectly_When_LrcAndFedProSettingsAreProvided)
{
   // Given
   std::vector<std::wstring> inputList
         {L"FedPro.connect.hostname=localhost", L"FedPro.connect.timeout=10", L"FedPro.connect.protocol=tcp",
          L"crcHost=rti.test.local", L"LRC.skipVersionCheck=false", L"FedPro.messageQueue.outgoing.limitedRate=false"};

   // When
   std::string settingsLine = RTIambassadorClientGenericBase::extractAndRemoveClientSettings(inputList, GetParam());

   // Then
   EXPECT_EQ(settingsLine,
             "connect.hostname=localhost,connect.timeout=10,connect.protocol=tcp,messageQueue.outgoing.limitedRate=false");
   EXPECT_EQ(inputList, (std::vector<std::wstring>{L"crcHost=rti.test.local", L"LRC.skipVersionCheck=false"}));
}
