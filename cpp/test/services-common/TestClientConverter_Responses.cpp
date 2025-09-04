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

#include "TestClientConverter_Objects.h"

#include <protobuf/generated/RTIambassador.pb.h>
#include <services-common/protobuf_util.h>

#include <RTI/time/HLAfloat64Time.h>
#include <RTI/time/HLAfloat64TimeFactory.h>
#include <RTI/time/HLAinteger64TimeFactory.h>

using namespace FedPro;

class TestClientConverterResponses : public TestClientConverterObjects
{
protected:

   ~TestClientConverterResponses() override = default;

   // Test ClientConverter method convertToHla with an empty response.
   template<typename FedProResponseT>
   void test_convertTo_empty_response()
   {
      FedProResponseT emptyResponse{};
      if (is_response_allocated<FedProResponseT>::value) {
         // If the response is allocated, expect an exception because of default nullptr content.
         EXPECT_THROW(_clientConverter.convertToHla(std::move(emptyResponse)), RTI_NAMESPACE::RTIinternalError);
      } else {
         EXPECT_NO_THROW(_clientConverter.convertToHla(std::move(emptyResponse)));
      }
   }

   // Run all responses tests functions for a given FedProResponse type.
   template<typename FedProResponseT>
   void test_response()
   {
      test_convertTo_empty_response<FedProResponseT>();

      // Return type (minus const/ref/pointer) for method FedProResponseT::result()
      // Example:
      //    decltype(response.result()) -> const FederateHandle &
      //    remove_cvrefpointer_t<const FederateHandle &> -> FederateHandle
      using FedProResult = remove_cvrefpointer_t<decltype(FedProResponseT{}.result())>;
      // Return type for method ClientConverter::convertToHla(FedProResult)
      using RTIConvertType = decltype(_clientConverter.convertToHla(std::declval<FedProResult>()));
      // Return type (minus pointer/const/volatile) for ClientConverter::convertFromHla(RTIType)
      using FedProConvertType = remove_cvrefpointer_t<decltype(_clientConverter.convertFromHla(std::declval<RTIConvertType>()))>;

      // Test converting the result default value back-and-forth.
      test_convertFrom_convertTo<RTIConvertType, FedProConvertType>();
   }

};

// Run relevant responses test functions for rti1516_2025::fedpro::JoinFederationExecutionResponse.
template<>
void TestClientConverterResponses::test_response<rti1516_2025::fedpro::JoinFederationExecutionResponse>()
{
   test_convertTo_empty_response<rti1516_2025::fedpro::JoinFederationExecutionResponse>();

   // There is only a one-way conversion for JoinResult
   // So this specialization call test_convertToHla instead of test_convertFrom_convertTo.
   test_convertToHla<rti1516_2025::fedpro::JoinResult>();
}

// Run relevant responses test functions for rti1516_2025::fedpro::QueryGALTResponse.
template<>
void TestClientConverterResponses::test_response<rti1516_2025::fedpro::QueryGALTResponse>()
{
   test_convertTo_empty_response<rti1516_2025::fedpro::QueryGALTResponse>();

   // There is only a one-way conversion for rti1516_2025::fedpro::TimeQueryReturn
   // So this specialization call test_convertToHla instead of test_convertFrom_convertTo.
   _clientConverter.setTimeFactory(std::make_shared<RTI_NAMESPACE::HLAfloat64TimeFactory>());
   test_convertToHla<rti1516_2025::fedpro::TimeQueryReturn>();
}

// Run relevant responses test functions for rti1516_2025::fedpro::QueryLogicalTimeResponse.
template<>
void TestClientConverterResponses::test_response<rti1516_2025::fedpro::QueryLogicalTimeResponse>()
{
   test_convertTo_empty_response<rti1516_2025::fedpro::QueryLogicalTimeResponse>();

   // Test one-way conversion for rti1516_2025::fedpro::LogicalTime using float time.
   _clientConverter.setTimeFactory(std::make_shared<RTI_NAMESPACE::HLAfloat64TimeFactory>());
   test_convertToHla<rti1516_2025::fedpro::LogicalTime>();

   // Test one-way conversion for rti1516_2025::fedpro::LogicalTime using integer time.
   _clientConverter.setTimeFactory(std::make_shared<RTI_NAMESPACE::HLAinteger64TimeFactory>());
   test_convertToHla<rti1516_2025::fedpro::LogicalTime>();
}

// Run relevant responses test functions for rti1516_2025::fedpro::QueryLITSResponse.
template<>
void TestClientConverterResponses::test_response<rti1516_2025::fedpro::QueryLITSResponse>()
{
   test_convertTo_empty_response<rti1516_2025::fedpro::QueryLITSResponse>();

   // There is only a one-way conversion for rti1516_2025::fedpro::TimeQueryReturn
   // So this specialization call test_convertToHla instead of test_convertFrom_convertTo.
   _clientConverter.setTimeFactory(std::make_shared<RTI_NAMESPACE::HLAfloat64TimeFactory>());
   test_convertToHla<rti1516_2025::fedpro::TimeQueryReturn>();
}

// Run relevant responses test functions for rti1516_2025::fedpro::QueryLookaheadResponse.
// There is already a test for LogicalTimeInterval in TestClientConverter_Objects,
// So this specialization call test_convertTo_empty_response() only.
template<>
void TestClientConverterResponses::test_response<rti1516_2025::fedpro::QueryLookaheadResponse>()
{
   test_convertTo_empty_response<rti1516_2025::fedpro::QueryLookaheadResponse>();
}

TEST_F(TestClientConverterResponses, JoinFederationExecutionResponse)
{
   test_response<rti1516_2025::fedpro::JoinFederationExecutionResponse>();
}

TEST_F(TestClientConverterResponses, IsAttributeOwnedByFederateResponse)
{
   test_response<rti1516_2025::fedpro::IsAttributeOwnedByFederateResponse>();
}

TEST_F(TestClientConverterResponses, RegisterObjectInstanceWithNameResponse)
{
   test_response<rti1516_2025::fedpro::RegisterObjectInstanceWithNameResponse>();
}

TEST_F(TestClientConverterResponses, UpdateAttributeValuesWithTimeResponse)
{
   test_response<rti1516_2025::fedpro::UpdateAttributeValuesWithTimeResponse>();
}

TEST_F(TestClientConverterResponses, RegisterObjectInstanceResponse)
{
   test_response<rti1516_2025::fedpro::RegisterObjectInstanceResponse>();
}

TEST_F(TestClientConverterResponses, SendInteractionWithTimeResponse)
{
   test_response<rti1516_2025::fedpro::SendInteractionWithTimeResponse>();
}

TEST_F(TestClientConverterResponses, SendDirectedInteractionWithTimeResponse)
{
   test_response<rti1516_2025::fedpro::SendDirectedInteractionWithTimeResponse>();
}

TEST_F(TestClientConverterResponses, DeleteObjectInstanceWithTimeResponse)
{
   test_response<rti1516_2025::fedpro::DeleteObjectInstanceWithTimeResponse>();
}

TEST_F(TestClientConverterResponses, AttributeOwnershipDivestitureIfWantedResponse)
{
   test_response<rti1516_2025::fedpro::AttributeOwnershipDivestitureIfWantedResponse>();
}

TEST_F(TestClientConverterResponses, QueryGALTResponse)
{
   test_response<rti1516_2025::fedpro::QueryGALTResponse>();
}

TEST_F(TestClientConverterResponses, QueryLogicalTimeResponse)
{
   test_response<rti1516_2025::fedpro::QueryLogicalTimeResponse>();
}

TEST_F(TestClientConverterResponses, QueryLITSResponse)
{
   test_response<rti1516_2025::fedpro::QueryLITSResponse>();
}

TEST_F(TestClientConverterResponses, QueryLookaheadResponse)
{
   test_response<rti1516_2025::fedpro::QueryLookaheadResponse>();
}

TEST_F(TestClientConverterResponses, CreateRegionResponse)
{
   test_response<rti1516_2025::fedpro::CreateRegionResponse>();
}

TEST_F(TestClientConverterResponses, RegisterObjectInstanceWithRegionsResponse)
{
   test_response<rti1516_2025::fedpro::RegisterObjectInstanceWithRegionsResponse>();
}

TEST_F(TestClientConverterResponses, RegisterObjectInstanceWithNameAndRegionsResponse)
{
   test_response<rti1516_2025::fedpro::RegisterObjectInstanceWithNameAndRegionsResponse>();
}

TEST_F(TestClientConverterResponses, SendInteractionWithRegionsAndTimeResponse)
{
   test_response<rti1516_2025::fedpro::SendInteractionWithRegionsAndTimeResponse>();
}

TEST_F(TestClientConverterResponses, GetAutomaticResignDirectiveResponse)
{
   test_response<rti1516_2025::fedpro::GetAutomaticResignDirectiveResponse>();
}

TEST_F(TestClientConverterResponses, GetAvailableDimensionsForObjectClassResponse)
{
   test_response<rti1516_2025::fedpro::GetAvailableDimensionsForObjectClassResponse>();
}

TEST_F(TestClientConverterResponses, GetFederateHandleResponse)
{
   test_response<rti1516_2025::fedpro::GetFederateHandleResponse>();
}

TEST_F(TestClientConverterResponses, GetFederateNameResponse)
{
   test_response<rti1516_2025::fedpro::GetFederateNameResponse>();
}

TEST_F(TestClientConverterResponses, GetObjectClassHandleResponse)
{
   test_response<rti1516_2025::fedpro::GetObjectClassHandleResponse>();
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)