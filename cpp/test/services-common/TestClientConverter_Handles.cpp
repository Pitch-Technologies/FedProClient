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

#include "TestClientConverter.h"

#include <services-common/ClientConverter.h>
#include <services-common/HandleImplementation.h>

class TestClientConverterHandles : public TestClientConverter
{
protected:

   // Test a valid handle's properties.
   template<typename HandleT>
   void test_handle_valid(const HandleT& rtiHandle)
   {
      EXPECT_TRUE(rtiHandle.isValid());
   }

   // Test an invalid handle's properties.
   template<typename HandleT>
   void test_handle_invalid()
   {
      HandleT invalidHandle;
      EXPECT_FALSE(invalidHandle.isValid());

      HandleT otherInvalidHandle;
      EXPECT_FALSE(otherInvalidHandle.isValid());
      EXPECT_EQ(otherInvalidHandle, invalidHandle);
   }

   // Test Handle class comparison operators.
   // @param rtiHandle A valid RTI Handle.
   template<typename HandleT>
   void test_handle_comparison(const HandleT& rtiHandle)
   {
      // Comparison with an invalid Handle

      HandleT invalidHandle;
      EXPECT_FALSE(rtiHandle == invalidHandle);
      EXPECT_TRUE(rtiHandle != invalidHandle);
      EXPECT_TRUE((rtiHandle < invalidHandle) || (invalidHandle < rtiHandle));

      // Comparison with itself

      EXPECT_TRUE(rtiHandle == rtiHandle);
      EXPECT_FALSE(rtiHandle != rtiHandle);
      EXPECT_FALSE(rtiHandle < rtiHandle);
   }

   // Test ClientConverter handle conversion methods.
   // @param validHandle A valid RTI handle.
   template<typename HandleT>
   void test_handle_conversion(const HandleT& rtiHandle)
   {
      auto fedproHandle = _clientConverter.convertFromHla(rtiHandle);
      auto rtiHandleBis = _clientConverter.convertToHla(std::move(*fedproHandle));
      EXPECT_EQ(rtiHandle.isValid(), rtiHandleBis.isValid());
      EXPECT_EQ(rtiHandle, rtiHandleBis);
      delete fedproHandle;
   }

   // Run all handle tests functions for a given Handle type.
   template<typename HandleT>
   void test_handle()
   {
      test_handle_invalid<HandleT>();

      HandleT rtiHandle32bit = RTI_NAMESPACE::make_handle<HandleT>("0001");
      test_handle_valid(rtiHandle32bit);
      test_handle_comparison(rtiHandle32bit);
      test_handle_conversion(rtiHandle32bit);

      HandleT rtiHandle64bit = RTI_NAMESPACE::make_handle<HandleT>("0001d\a\t ");
      test_handle_valid(rtiHandle64bit);
      test_handle_comparison(rtiHandle64bit);
      test_handle_conversion(rtiHandle64bit);
   }
};

// Test ClientConverter MessageRetractionHandle conversion methods.
// Note this template-specialization exists because of the different convertToHla return type.
// @param validHandle A valid RTI handle.
template<>
void TestClientConverterHandles::test_handle_conversion(const RTI_NAMESPACE::MessageRetractionHandle& rtiHandle)
{
   auto fedproHandle = _clientConverter.convertFromHla(rtiHandle);
   auto rtiHandleBis = _clientConverter.convertToHla(std::move(*fedproHandle));
   EXPECT_EQ(rtiHandle.isValid(), rtiHandleBis->isValid());
   EXPECT_EQ(rtiHandle, *rtiHandleBis);
   delete fedproHandle;
}

TEST_F(TestClientConverterHandles, FederateHandle)
{
   test_handle<RTI_NAMESPACE::FederateHandle>();
}

TEST_F(TestClientConverterHandles, ObjectClassHandle)
{
   test_handle<RTI_NAMESPACE::ObjectClassHandle>();
}

TEST_F(TestClientConverterHandles, InteractionClassHandle)
{
   test_handle<RTI_NAMESPACE::InteractionClassHandle>();
}

TEST_F(TestClientConverterHandles, ObjectInstanceHandle)
{
   test_handle<RTI_NAMESPACE::ObjectInstanceHandle>();
}

TEST_F(TestClientConverterHandles, AttributeHandle)
{
   test_handle<RTI_NAMESPACE::AttributeHandle>();
}

TEST_F(TestClientConverterHandles, ParameterHandle)
{
   test_handle<RTI_NAMESPACE::ParameterHandle>();
}

TEST_F(TestClientConverterHandles, DimensionHandle)
{
   test_handle<RTI_NAMESPACE::DimensionHandle>();
}

TEST_F(TestClientConverterHandles, MessageRetractionHandle)
{
   test_handle<RTI_NAMESPACE::MessageRetractionHandle>();
}

TEST_F(TestClientConverterHandles, RegionHandle)
{
   test_handle<RTI_NAMESPACE::RegionHandle>();
}

TEST_F(TestClientConverterHandles, TransportationTypeHandle)
{
   test_handle<RTI_NAMESPACE::TransportationTypeHandle>();
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)