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

#include <RTI/Exception.h>
#include <RTI/NullFederateAmbassador.h>
#include <RTI/RTIambassador.h>
#include <RTI/RTIambassadorFactory.h>
#include <RTI/RtiConfiguration.h>

#include <gtest/gtest.h>

#include <cstdlib>

// TestRTIAmbassador acts as RTIambassador test fixture for HLA 4.
class TestRTIAmbassador : public ::testing::Test
{
public:

   void SetUp() override
   {
      // Reset environment options
      char optionsEnv[]{"FEDPRO_CLIENT_OPTIONS="};
      putEnvironment(optionsEnv);
   }
protected:
   static std::unique_ptr< RTI_NAMESPACE::RTIambassador > createRTIambassador()
   {
      RTI_NAMESPACE::RTIambassadorFactory factory;
      return factory.createRTIambassador();
   }

   static void putEnvironment(char *environmentVariable)
   {
#ifdef _WIN32
      _putenv(environmentVariable);
#else
      putenv(environmentVariable);
#endif
   }
};


TEST_F(TestRTIAmbassador, connectWebsocketUnknownHost)
{
   // Given
   RTI_NAMESPACE::RtiConfiguration rtiConfig;
   rtiConfig.withRtiAddress(L"connect.hostname=unknown.host.invalid,connect.protocol=websocket");
   auto rtiAmbassador = createRTIambassador();
   auto federateAmbassador = std::make_unique<RTI_NAMESPACE::NullFederateAmbassador>();

   EXPECT_THROW(
         // When
         rtiAmbassador->connect(*federateAmbassador, RTI_NAMESPACE::HLA_IMMEDIATE, rtiConfig),
         // Then
         RTI_NAMESPACE::ConnectionFailed);
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)