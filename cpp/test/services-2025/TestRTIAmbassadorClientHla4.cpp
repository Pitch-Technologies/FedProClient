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

// Silence clang-tidy issues reported for gtest macros.
// NOLINTBEGIN(cppcoreguidelines-avoid-non-const-global-variables)

#include "services-hla4/RTIambassadorClientAdapter.h"
#include "services-common/RTIambassadorClientGenericBase.h"
#include "services-common/RtiConfiguration.h"
#include "services-common/SettingsParser.h"

#include <gtest/gtest.h>

using namespace FedPro;
using namespace RTI_NAMESPACE;

// TestAmbassador acts as RTIambassador test fixture for HLA 4.
class TestRTIambassadorClientHla4 : public ::testing::Test, public RTIambassadorClientAdapter
{
public:
   TestRTIambassadorClientHla4() = default;
};

TEST(TestRTIambassadorClientHla4, clientSettingsAreExtractedAndRemovedCorrectly_When_crcAddressIsProvided)
{
   // Given
   std::vector<std::wstring> inputList
         {L"FedPro.connect.hostname=localhost", L"crcAddress=123.456.789:1234", L"crcHost=rti.test.local",
          L"LRC.skipVersionCheck=false"};

   // When
   std::string settingsLine = RTIambassadorClientGenericBase::extractAndRemoveClientSettings(inputList, false);

   // Then
   EXPECT_EQ(settingsLine, "connect.hostname=localhost");
   EXPECT_EQ(inputList,
             (std::vector<std::wstring>{L"crcAddress=123.456.789:1234", L"crcHost=rti.test.local",
                                        L"LRC.skipVersionCheck=false"}));
}

TEST(TestRTIambassadorClientHla4, rtiConfigurationParseCorrectly_When_NoSettingsAreProvided)
{
   // Given
   RtiConfiguration providedConfiguration = RtiConfiguration::createConfiguration();
   std::vector<std::wstring> inputList;

   // When
   RtiConfiguration
         rtiConfiguration = RTIambassadorClientAdapter::parseRtiConfiguration(providedConfiguration, inputList);

   // Then
   EXPECT_EQ(rtiConfiguration.rtiAddress(), L"");
   EXPECT_EQ(rtiConfiguration.configurationName(), L"");
   EXPECT_EQ(rtiConfiguration.additionalSettings(), L"");
}

TEST(TestRTIambassadorClientHla4, rtiConfigurationParseCorrectly_When_OnlySettingsAreProvided)
{
   // Given
   RtiConfiguration providedConfiguration = RtiConfiguration::createConfiguration();
   std::vector<std::wstring> inputList = {L"FedPro.connect.timeout=10,", L"LRC.skipVersionCheck=false"};

   // When
   RtiConfiguration
         rtiConfiguration = RTIambassadorClientAdapter::parseRtiConfiguration(providedConfiguration, inputList);

   // Then
   EXPECT_EQ(rtiConfiguration.rtiAddress(), L"");
   EXPECT_EQ(rtiConfiguration.configurationName(), L"");
   EXPECT_EQ(rtiConfiguration.additionalSettings(), L"FedPro.connect.timeout=10,\nLRC.skipVersionCheck=false");
}

TEST(TestRTIambassadorClientHla4, rtiConfigurationParseCorrectly_When_OnlyConfigNameIsProvided)
{
   // Given
   RtiConfiguration
         providedConfiguration = RtiConfiguration::createConfiguration().withConfigurationName(L"iosConfiguration");
   std::vector<std::wstring> inputList;

   // When
   RtiConfiguration
         rtiConfiguration = RTIambassadorClientAdapter::parseRtiConfiguration(providedConfiguration, inputList);

   // Then
   EXPECT_EQ(rtiConfiguration.rtiAddress(), L"");
   EXPECT_EQ(rtiConfiguration.configurationName(), L"iosConfiguration");
   EXPECT_EQ(rtiConfiguration.additionalSettings(), L"");
}

TEST(TestRTIambassadorClientHla4, rtiConfigurationParseCorrectly_When_ConfigNameAndSettingsAreProvided)
{
   // Given
   RtiConfiguration
         providedConfiguration = RtiConfiguration::createConfiguration().withConfigurationName(L"iosConfiguration");
   std::vector<std::wstring> inputList =
         {L"FedPro.connect.timeout=10,", L"LRC.skipVersionCheck=false", L"FedPro.connect.hostname=localhost"};

   // When
   RtiConfiguration
         rtiConfiguration = RTIambassadorClientAdapter::parseRtiConfiguration(providedConfiguration, inputList);

   // Then
   EXPECT_EQ(rtiConfiguration.rtiAddress(), L"");
   EXPECT_EQ(rtiConfiguration.configurationName(), L"iosConfiguration");
   EXPECT_EQ(rtiConfiguration.additionalSettings(),
             L"FedPro.connect.timeout=10,\nLRC.skipVersionCheck=false\nFedPro.connect.hostname=localhost");
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)
