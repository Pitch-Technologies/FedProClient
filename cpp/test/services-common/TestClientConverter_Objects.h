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

#pragma once

#include "TestClientConverter.h"

#include <protobuf/generated/datatypes.pb.h>
#include <services-common/FomModule.h>
#include <services-common/FomModuleLoader.h>

#include <RTI/RtiConfiguration.h>
#include <RTI/time/HLAfloat64Time.h>
#include <RTI/time/HLAinteger64Time.h>

#include <array>

// Create valid instances of RTI_NAMESPACE::RtiConfiguration.
template<>
inline auto TestClientConverter::createValidInstances<RTI_NAMESPACE::RtiConfiguration>() const
{
   RTI_NAMESPACE::RtiConfiguration emptyConfig;
   RTI_NAMESPACE::RtiConfiguration nonEmptyConfig;
   nonEmptyConfig.withRtiAddress(L"remotehost").withConfigurationName(L"myconfig").withAdditionalSettings(L"debug");
   return std::array<RTI_NAMESPACE::RtiConfiguration, 2>{{emptyConfig, nonEmptyConfig}};
}

// Create valid instances of FedPro::FomModule.
template<>
inline auto TestClientConverter::createValidInstances<FedPro::FomModule>() const
{
   FedPro::FomModuleLoader moduleLoader;
   FedPro::FomModule urlModule(L"https://example.invalid/foms/RPR-Base_v2.0.xml");
   FedPro::FomModule fileModule = moduleLoader.getFomModule(L"file://./MockModule.xml");
   return std::array<FedPro::FomModule, 2>{{urlModule, fileModule}};
}

// Create valid a instance of rti1516_2025::fedpro::FederateRestoreStatus.
template<>
inline auto TestClientConverter::createValidInstances<rti1516_2025::fedpro::FederateRestoreStatus>() const
{
   rti1516_2025::fedpro::FederateRestoreStatus federateRestoreStatus;
   federateRestoreStatus.set_allocated_prerestorehandle(new rti1516_2025::fedpro::FederateHandle);
   federateRestoreStatus.set_allocated_postrestorehandle(new rti1516_2025::fedpro::FederateHandle);
   federateRestoreStatus.set_restorestatus(rti1516_2025::fedpro::FEDERATE_RESTORING);
   return std::array<rti1516_2025::fedpro::FederateRestoreStatus, 1>{federateRestoreStatus};
}

// Create a valid instance of rti1516_2025::fedpro::JoinResult.
template<>
inline auto TestClientConverter::createValidInstances<rti1516_2025::fedpro::JoinResult>() const
{
   rti1516_2025::fedpro::JoinResult fedproJoinResult;
   fedproJoinResult.set_allocated_federatehandle(new rti1516_2025::fedpro::FederateHandle);
   return std::array<rti1516_2025::fedpro::JoinResult, 1>{fedproJoinResult};
}

// Create an invalid instance of rti1516_2025::fedpro::JoinResult.
template<>
inline auto TestClientConverter::createInvalidInstances<rti1516_2025::fedpro::JoinResult>() const
{
   rti1516_2025::fedpro::JoinResult fedproJoinResultMissingField;
   return std::array<rti1516_2025::fedpro::JoinResult, 1>{fedproJoinResultMissingField};
}

// Create valid a instance of RTI_NAMESPACE::HLAfloat64Time.
template<>
inline auto TestClientConverter::createValidInstances<RTI_NAMESPACE::HLAfloat64Time>() const
{
   RTI_NAMESPACE::HLAfloat64Time initialTime;
   initialTime.setInitial();
   RTI_NAMESPACE::HLAfloat64Time finalTime;
   initialTime.setFinal();
   RTI_NAMESPACE::HLAfloat64Time midTime((initialTime + finalTime) / 2);
   return std::array<RTI_NAMESPACE::HLAfloat64Time, 3>{initialTime, finalTime, midTime};
}

// Create valid a instance of RTI_NAMESPACE::HLAinteger64Time.
template<>
inline auto TestClientConverter::createValidInstances<RTI_NAMESPACE::HLAinteger64Time>() const
{
   RTI_NAMESPACE::HLAinteger64Time initialTime;
   initialTime.setInitial();
   RTI_NAMESPACE::HLAinteger64Time finalTime;
   initialTime.setFinal();
   RTI_NAMESPACE::HLAinteger64Time midTime((initialTime + finalTime) / 2);
   return std::array<RTI_NAMESPACE::HLAinteger64Time, 3>{initialTime, finalTime, midTime};
}

// Create valid instances of rti1516_2025::fedpro::TimeQueryReturn.
template<>
inline auto TestClientConverter::createValidInstances<rti1516_2025::fedpro::TimeQueryReturn>() const
{
   std::vector<rti1516_2025::fedpro::TimeQueryReturn> timeQueryReturnVector;
   auto rtiValidTimes = createValidInstances<RTI_NAMESPACE::HLAfloat64Time>();
   for (const auto & rtiValidTime : rtiValidTimes) {
      rti1516_2025::fedpro::TimeQueryReturn timeQueryReturn;
      timeQueryReturn.set_allocated_logicaltime(_clientConverter.convertFromHla(rtiValidTime));
      timeQueryReturn.set_logicaltimeisvalid(true);
      timeQueryReturnVector.emplace_back(std::move(timeQueryReturn));
   }

   return timeQueryReturnVector;
}

// Create valid instances of rti1516_2025::fedpro::LogicalTime.
template<>
inline auto TestClientConverter::createValidInstances<rti1516_2025::fedpro::LogicalTime>() const
{
   auto timeFactory = _clientConverter.getTimeFactory();
   auto initialTime = timeFactory->makeInitial();
   std::unique_ptr<rti1516_2025::fedpro::LogicalTime> fedproInitialTime{ _clientConverter.convertFromHla(*initialTime)};
   auto finalTime = timeFactory->makeFinal();
   std::unique_ptr<rti1516_2025::fedpro::LogicalTime> fedproFinalTime{ _clientConverter.convertFromHla(*finalTime)};
   return std::array<rti1516_2025::fedpro::LogicalTime, 2>{*fedproInitialTime, *fedproFinalTime};
}

// Create invalid instances of rti1516_2025::fedpro::LogicalTime.
template<>
inline auto TestClientConverter::createInvalidInstances<rti1516_2025::fedpro::LogicalTime>() const
{
   rti1516_2025::fedpro::LogicalTime timeWithoutData;
   rti1516_2025::fedpro::LogicalTime timeTooLittleData;
   return std::array<rti1516_2025::fedpro::LogicalTime, 2>{timeWithoutData, timeTooLittleData};
}

class TestClientConverterObjects : public TestClientConverter
{
protected:

   ~TestClientConverterObjects() override = default;

};