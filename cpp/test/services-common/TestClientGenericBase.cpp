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

#include <services-common/RTIambassadorClientGenericBase.h>
#include <services-common/SettingsParser.h>

#include <gtest/gtest.h>

using namespace FedPro;

class TestClientGenericBase : public ::testing::Test
{
};

TEST_F(TestClientGenericBase, connectSettingsAreSplitCorrectly_When_ThereAreNoEntries)
{
   // Given
   std::wstring inputValueList;

   // When
   std::vector<std::wstring> inputList = RTIambassadorClientGenericBase::splitFederateConnectSettings(inputValueList);

   // Then
   EXPECT_EQ(inputList, std::vector<std::wstring>{L""});
}

TEST_F(TestClientGenericBase, emptyElementsArePreserved_When_EmptyEntriesExists)
{
   // Given
   std::wstring inputValueList{L"\n\na\n\nb\n"};

   // When
   std::vector<std::wstring> inputList = RTIambassadorClientGenericBase::splitFederateConnectSettings(inputValueList);

   // Then
   EXPECT_EQ(inputList, (std::vector<std::wstring>{L"", L"", L"a", L"", L"b", L""}));
}

TEST_F(TestClientGenericBase, connectSettingsAreSplitCorrectly_When_EntriesAreSeperatedByLineBreaks)
{
   // Given
   std::wstring inputValueList{L"crcHost=rti.test.local\ncrcHost=rti.test.local"};

   // When
   std::vector<std::wstring> inputList = RTIambassadorClientGenericBase::splitFederateConnectSettings(inputValueList);

   // Then
   EXPECT_EQ(inputList, (std::vector<std::wstring>{L"crcHost=rti.test.local", L"crcHost=rti.test.local"}));
}

TEST_F(TestClientGenericBase, connectSettingsAreSplitCorrectly_When_EntriesAreSeperatedByComma)
{
   // Given
   std::wstring inputValueList{L"crcHost=rti.test.local,crcHost=rti.test.local"};

   // When
   std::vector<std::wstring> inputList = RTIambassadorClientGenericBase::splitFederateConnectSettings(inputValueList);

   // Then
   EXPECT_EQ(inputList, (std::vector<std::wstring>{L"crcHost=rti.test.local", L"crcHost=rti.test.local"}));
}

TEST_F(TestClientGenericBase, connectSettingsAreSplitCorrectly_When_EntriesAreSeperatedByCommaAndLineBreaks)
{
   // Given
   std::wstring inputValueList{L"LRC.skipVersionCheck=false\n"
                               L"FedPro.connect.hostname=localhost\n"
                               L"FedPro.connect.timeout=10,FedPro.FED_TIMEOUT_HEART=100\n"
                               L"crcHost=rti.test.local\n"
                               L"FedPro.messageQueue.outgoing.limitedRate=false"};

   // When
   std::vector<std::wstring> inputList = RTIambassadorClientGenericBase::splitFederateConnectSettings(inputValueList);

   // Then
   EXPECT_EQ(inputList,
             (std::vector<std::wstring>{L"LRC.skipVersionCheck=false", L"FedPro.connect.hostname=localhost",
                                        L"FedPro.connect.timeout=10", L"FedPro.FED_TIMEOUT_HEART=100",
                                        L"crcHost=rti.test.local", L"FedPro.messageQueue.outgoing.limitedRate=false"}));
}

TEST_F(TestClientGenericBase, extractAndRemoveClientSettings_Given_StandardSetting)
{
   // Given
   const std::wstring additionalSettings{L"FED_INT_HEART=5"};
   std::vector<std::wstring> inputList =
         RTIambassadorClientGenericBase::splitFederateConnectSettings(additionalSettings);

   // When
   const std::string clientSettingsLine = RTIambassadorClientGenericBase::extractAndRemoveClientSettings(
         inputList,
         false);

   // Then
   const Properties clientSettings{SettingsParser::parse(clientSettingsLine)};
   ASSERT_TRUE(inputList.empty());
   ASSERT_EQ(clientSettings.getDuration("FED_INT_HEART", FedProDuration{0}), FedProDuration{5000});
}