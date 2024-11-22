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

#include "RTI_util.h"
#include <services-common/FomModule.h>

#include <RTI/time/HLAfloat64Interval.h>
#include <RTI/time/HLAfloat64TimeFactory.h>
#include <RTI/time/HLAinteger64TimeFactory.h>

TEST_F(TestClientConverterObjects, RtiConfiguration)
{
   test_convertFrom_convertTo<RTI_NAMESPACE::RtiConfiguration>();
}

TEST_F(TestClientConverterObjects, ConfigurationResult)
{
   test_convertToHla<rti1516_202X::fedpro::ConfigurationResult>();
}

TEST_F(TestClientConverterObjects, FederationExecutionInformation)
{
   test_convertToHla<rti1516_202X::fedpro::FederationExecutionInformation>();
}

TEST_F(TestClientConverterObjects, FederationExecutionMemberInformation)
{
   test_convertToHla<rti1516_202X::fedpro::FederationExecutionMemberInformation>();
}

TEST_F(TestClientConverterObjects, FederateRestoreStatus)
{
   test_convertToHla<rti1516_202X::fedpro::FederateRestoreStatus>();
}

TEST_F(TestClientConverterObjects, FomModule)
{
   test_convertFromHla<FedPro::FomModule>();
}

TEST_F(TestClientConverterObjects, JoinResult)
{
   test_convertToHla<rti1516_202X::fedpro::JoinResult>();
}

TEST_F(TestClientConverterObjects, LogicalTime)
{
   _clientConverter.setTimeFactory(std::make_shared<RTI_NAMESPACE::HLAfloat64TimeFactory>());
   test_convertFrom_convertTo<RTI_NAMESPACE::HLAfloat64Time>();

   _clientConverter.setTimeFactory(std::make_shared<RTI_NAMESPACE::HLAinteger64TimeFactory>());
   test_convertFrom_convertTo<RTI_NAMESPACE::HLAinteger64Time>();
}

TEST_F(TestClientConverterObjects, LogicalTimeInterval)
{
   _clientConverter.setTimeFactory(std::make_shared<RTI_NAMESPACE::HLAfloat64TimeFactory>());
   test_convertFrom_convertTo<RTI_NAMESPACE::HLAfloat64Interval>();

   _clientConverter.setTimeFactory(std::make_shared<RTI_NAMESPACE::HLAinteger64TimeFactory>());
   test_convertFrom_convertTo<RTI_NAMESPACE::HLAinteger64Interval>();
}

TEST_F(TestClientConverterObjects, RangeBounds)
{
   test_convertFrom_convertTo<RTI_NAMESPACE::RangeBounds>();
}

TEST_F(TestClientConverterObjects, TimeQueryReturn)
{
   _clientConverter.setTimeFactory(std::make_shared<RTI_NAMESPACE::HLAfloat64TimeFactory>());
   test_convertToHla<rti1516_202X::fedpro::TimeQueryReturn>();
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)