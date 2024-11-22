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

#include "RTITypedefs.h"

namespace RTI_NAMESPACE
{
#if (RTI_HLA_VERSION >= 2024)
   ConfigurationResult::ConfigurationResult()
         : configurationUsed{false},
           addressUsed{false},
           additionalSettingsResult{AdditionalSettingsResultCode::SETTINGS_IGNORED},
           message{}
   {
   }

   ConfigurationResult::ConfigurationResult(
         bool configurationUsedP,
         bool addressUsedP,
         AdditionalSettingsResultCode settingsResultCodeP,
         std::wstring const & messageP)
         : configurationUsed{configurationUsedP},
           addressUsed{addressUsedP},
           additionalSettingsResult{settingsResultCodeP},
           message{messageP}
   {
   }

   FederateRestoreStatus::FederateRestoreStatus() :
      preRestoreHandle{},
      postRestoreHandle{},
      status{NO_RESTORE_IN_PROGRESS}
   {
   }
#endif

   FederateRestoreStatus::FederateRestoreStatus(
            FederateHandle const & preHandleP,
            FederateHandle const & postHandleP,
            RestoreStatus statusP) :
         preRestoreHandle{preHandleP},
         postRestoreHandle{postHandleP},
         status{statusP}
   {
   }

#if (RTI_HLA_VERSION >= 2024)
   FederationExecutionInformation::FederationExecutionInformation() = default;
#endif

   FederationExecutionInformation::FederationExecutionInformation(
         std::wstring const & federationName,
         std::wstring const & logicalTimeImplementationNameP) :
      federationExecutionName{federationName},
      logicalTimeImplementationName{logicalTimeImplementationNameP}
   {
   }

#if (RTI_HLA_VERSION < 2024)
   SupplementalReflectInfo::SupplementalReflectInfo()
         : hasProducingFederate{false},
           hasSentRegions{false}
   {
   }

   SupplementalReflectInfo::SupplementalReflectInfo(FederateHandle const & theFederateHandle)
         : hasProducingFederate{true},
           hasSentRegions{false},
           producingFederate(theFederateHandle)
   {
   }

   SupplementalReflectInfo::SupplementalReflectInfo(RegionHandleSet const & theRegionHandleSet)
         : hasProducingFederate{false},
           hasSentRegions{true},
           sentRegions{theRegionHandleSet}
   {
   }

   SupplementalReflectInfo::SupplementalReflectInfo(
         FederateHandle const & theFederateHandle,
         RegionHandleSet const & theRegionHandleSet)
         : hasProducingFederate{true},
           hasSentRegions{true},
           producingFederate{theFederateHandle},
           sentRegions{theRegionHandleSet}
   {
   }

   SupplementalReceiveInfo::SupplementalReceiveInfo()
         : hasProducingFederate{false},
           hasSentRegions{false}
   {
   }

   SupplementalReceiveInfo::SupplementalReceiveInfo(FederateHandle const & theFederateHandle)
         : hasProducingFederate{true},
           hasSentRegions{false},
           producingFederate(theFederateHandle)
   {
   }

   SupplementalReceiveInfo::SupplementalReceiveInfo(RegionHandleSet const & theRegionHandleSet)
         : hasProducingFederate{false},
           hasSentRegions{true},
           sentRegions{theRegionHandleSet}
   {
   }

   SupplementalReceiveInfo::SupplementalReceiveInfo(
         FederateHandle const & theFederateHandle,
         RegionHandleSet const & theRegionHandleSet)
         : hasProducingFederate{true},
           hasSentRegions{true},
           producingFederate{theFederateHandle},
           sentRegions{theRegionHandleSet}
   {
   }

   SupplementalRemoveInfo::SupplementalRemoveInfo()
         : hasProducingFederate{false}
   {
   }

   SupplementalRemoveInfo::SupplementalRemoveInfo(FederateHandle const & theFederateHandle)
         : hasProducingFederate{true},
           producingFederate(theFederateHandle)
   {
   }
#endif

#if (RTI_HLA_VERSION >= 2024)
   FederationExecutionMemberInformation::FederationExecutionMemberInformation() = default;

   FederationExecutionMemberInformation::FederationExecutionMemberInformation(
            std::wstring const & federateNameP,
            std::wstring const & federateTypeP) :
      federateName{federateNameP},
      federateType{federateTypeP}
   {
   }
#endif
}