#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

// Purpose: This file contains the standard RTI types that are defined in
// namespace rti1516_202X.  These definitions/declarations are standard for all RTI
// implementations.
//
// The types declared here require the use of some RTI Specific types.

// The following type definitions use standard C++ classes for containers
// that are used in the RTI API.

#include <RTI/SpecificConfig.h>
#include <set>
#include <map>
#include <vector>
#include <RTI/Enums.h>
#include <RTI/Handle.h>

namespace rti1516_202X
{
   typedef std::set< AttributeHandle > AttributeHandleSet;
   typedef std::set< InteractionClassHandle > InteractionClassHandleSet;
   typedef std::set< ParameterHandle > ParameterHandleSet;
   typedef std::set< FederateHandle  > FederateHandleSet;
   typedef std::set< DimensionHandle > DimensionHandleSet;
   typedef std::set< RegionHandle    > RegionHandleSet;

   // RTI::AttributeHandleValueMap implements a constrained set of
   // (attribute handle and value) pairs
   typedef std::map< AttributeHandle, VariableLengthData >
   AttributeHandleValueMap;

   // RTI::ParameterHandleValueMap implements a constrained set of
   // (parameter handle and value) pairs
   typedef std::map< ParameterHandle, VariableLengthData >
   ParameterHandleValueMap;

   // RTI::AttributeHandleSetRegionHandleSetPairVector implements a collection of
   // (attribute handle set and region set) pairs
   typedef std::pair< AttributeHandleSet, RegionHandleSet >
   AttributeHandleSetRegionHandleSetPair;

   typedef std::vector< AttributeHandleSetRegionHandleSetPair >
   AttributeHandleSetRegionHandleSetPairVector;

   // RTI::FederateHandleSaveStatusPairVector implements a collection of
   // (federate handle and save status) pairs
   typedef std::pair< FederateHandle, SaveStatus >
   FederateHandleSaveStatusPair;

   typedef std::vector< FederateHandleSaveStatusPair >
   FederateHandleSaveStatusPairVector;

   class RTI_EXPORT ConfigurationResult
   {
   public:
      ConfigurationResult ();
      ConfigurationResult (
         bool configurationUsed,
         bool addressUsed,
         AdditionalSettingsResultCode settingsResultCode,
         std::wstring const & message = L"");

      bool configurationUsed;
      bool addressUsed;
      AdditionalSettingsResultCode additionalSettingsResult;
      std::wstring message;
   };

   // RTI::FederateRestoreStatusVector implements a collection of
   // FederateRestoreStatus objects, each of which contains a pre-restore handle,
   // a post-restore handle, and a restore status.
   class RTI_EXPORT FederateRestoreStatus
   {
   public:
      FederateRestoreStatus ();
      FederateRestoreStatus (
         FederateHandle const & preHandle,
         FederateHandle const & postHandle,
         RestoreStatus status);

      FederateHandle preRestoreHandle;
      FederateHandle postRestoreHandle;
      RestoreStatus status;
   };

   typedef std::vector< FederateRestoreStatus > FederateRestoreStatusVector;

   // RTI::FederationExecutionInformationSet implements a collection of
   // FederationExecutionInformation, each of which contains
   // a federation execution name and a logical time implementation name.
   class RTI_EXPORT FederationExecutionInformation
   {
   public:
      FederationExecutionInformation ();
      FederationExecutionInformation (
         std::wstring const & federationName,
         std::wstring const & logicalTimeImplementationName);

      std::wstring federationExecutionName;
      std::wstring logicalTimeImplementationName;
   };

   typedef std::vector<FederationExecutionInformation> FederationExecutionInformationVector;

   // RTI::FederationExecutionMemberInformationVector implements a collection of
   // FederationExecutionMemberInformation, each of which contains
   // a federate name and a federate type.
   class RTI_EXPORT FederationExecutionMemberInformation
   {
   public:
      FederationExecutionMemberInformation();
      FederationExecutionMemberInformation(
         std::wstring const & federateName,
         std::wstring const & federateType);

      std::wstring federateName;
      std::wstring federateType;
   };

   typedef std::vector<FederationExecutionMemberInformation> FederationExecutionMemberInformationVector;
}


