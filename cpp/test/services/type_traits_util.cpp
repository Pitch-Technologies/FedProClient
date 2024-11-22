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

#include <protobuf/generated/RTIambassador.pb.h>
#include <protobuf/generated/datatypes.pb.h>
#include <services-common/protobuf_util.h>

#include <gtest/gtest.h>

using namespace FedPro;

class TestFedPro_util : public ::testing::Test
{
};

TEST_F(TestFedPro_util, is_response)
{
   EXPECT_TRUE(is_response<rti1516_202X::fedpro::GetFederateHandleResponse>::value);
   EXPECT_TRUE(is_response<rti1516_202X::fedpro::RegisterObjectInstanceResponse>::value);
   EXPECT_TRUE(is_response<rti1516_202X::fedpro::IsAttributeOwnedByFederateResponse>::value);
   EXPECT_FALSE(is_response<std::string>::value);
}

TEST_F(TestFedPro_util, is_response_allocated)
{
   EXPECT_TRUE(is_response_allocated<rti1516_202X::fedpro::GetFederateHandleResponse>::value);
   EXPECT_TRUE(is_response_allocated<rti1516_202X::fedpro::RegisterObjectInstanceResponse>::value);
   EXPECT_FALSE(is_response_allocated<std::string>::value);
   EXPECT_FALSE(is_response_allocated<rti1516_202X::fedpro::IsAttributeOwnedByFederateResponse>::value);
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)