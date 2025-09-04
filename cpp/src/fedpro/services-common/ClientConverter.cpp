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

// Silence clang-tidy issues reported for standard HLA exception.
// NOLINTBEGIN(hicpp-exception-baseclass)

#include "ClientConverter.h"

#include "HandleImplementation.h"
#include "RegionHandleImplementation.h"
#include "utility/StringUtil.h"

#if (RTI_HLA_VERSION >= 2025)
#include <RTI/time/LogicalTime.h>
#include <RTI/time/LogicalTimeInterval.h>
#else
#include <RTI/LogicalTime.h>
#include <RTI/LogicalTimeInterval.h>
#endif

namespace FedPro
{
   ClientConverter::ClientConverter() = default;

   void ClientConverter::setTimeFactory(std::shared_ptr<RTI_NAMESPACE::LogicalTimeFactory> timeFactory)
   {
      _timeFactory = std::move(timeFactory);
   }

   //
   // Enumerations
   //

   RTI_NAMESPACE::AdditionalSettingsResultCode ClientConverter::convertToHla(rti1516_2025::fedpro::AdditionalSettingsResultCode fedproResultCode) const
   {
      switch (fedproResultCode) {
         case rti1516_2025::fedpro::SETTINGS_IGNORED:
            return RTI_NAMESPACE::SETTINGS_IGNORED;
         case rti1516_2025::fedpro::SETTINGS_FAILED_TO_PARSE:
            return RTI_NAMESPACE::SETTINGS_FAILED_TO_PARSE;
         case rti1516_2025::fedpro::SETTINGS_APPLIED:
            return RTI_NAMESPACE::SETTINGS_APPLIED;
         default:
            throw std::invalid_argument("Invalid AdditionalSettingsResultCode value");
      }
   }

   RTI_NAMESPACE::OrderType ClientConverter::convertToHla(const rti1516_2025::fedpro::OrderType fedproOrderType) const
   {
      switch (fedproOrderType) {
         case rti1516_2025::fedpro::RECEIVE:
            return RTI_NAMESPACE::RECEIVE;
         case rti1516_2025::fedpro::TIMESTAMP:
            return RTI_NAMESPACE::TIMESTAMP;
         default:
            throw std::invalid_argument("Invalid OrderType value");
      }
   }

   rti1516_2025::fedpro::OrderType ClientConverter::convertFromHla(const RTI_NAMESPACE::OrderType rtiOrderType) const
   {
      switch (rtiOrderType) {
         case RTI_NAMESPACE::RECEIVE:
            return rti1516_2025::fedpro::RECEIVE;
         case RTI_NAMESPACE::TIMESTAMP:
            return rti1516_2025::fedpro::TIMESTAMP;
         default:
            throw std::invalid_argument("Invalid OrderType value");
      }
   }

   RTI_NAMESPACE::ResignAction ClientConverter::convertToHla(const rti1516_2025::fedpro::ResignAction fedproResignAction) const
   {
      switch (fedproResignAction) {
         case rti1516_2025::fedpro::UNCONDITIONALLY_DIVEST_ATTRIBUTES:
            return RTI_NAMESPACE::UNCONDITIONALLY_DIVEST_ATTRIBUTES;
         case rti1516_2025::fedpro::DELETE_OBJECTS:
            return RTI_NAMESPACE::DELETE_OBJECTS;
         case rti1516_2025::fedpro::CANCEL_PENDING_OWNERSHIP_ACQUISITIONS:
            return RTI_NAMESPACE::CANCEL_PENDING_OWNERSHIP_ACQUISITIONS;
         case rti1516_2025::fedpro::DELETE_OBJECTS_THEN_DIVEST:
            return RTI_NAMESPACE::DELETE_OBJECTS_THEN_DIVEST;
         case rti1516_2025::fedpro::CANCEL_THEN_DELETE_THEN_DIVEST:
            return RTI_NAMESPACE::CANCEL_THEN_DELETE_THEN_DIVEST;
         case rti1516_2025::fedpro::NO_ACTION:
            return RTI_NAMESPACE::NO_ACTION;
         default:
            throw std::invalid_argument("Invalid ResignAction value");
      }
   }

   rti1516_2025::fedpro::ResignAction ClientConverter::convertFromHla(const RTI_NAMESPACE::ResignAction rtiResignAction) const
   {
      switch (rtiResignAction) {
         case RTI_NAMESPACE::UNCONDITIONALLY_DIVEST_ATTRIBUTES:
            return rti1516_2025::fedpro::UNCONDITIONALLY_DIVEST_ATTRIBUTES;
         case RTI_NAMESPACE::DELETE_OBJECTS:
            return rti1516_2025::fedpro::DELETE_OBJECTS;
         case RTI_NAMESPACE::CANCEL_PENDING_OWNERSHIP_ACQUISITIONS:
            return rti1516_2025::fedpro::CANCEL_PENDING_OWNERSHIP_ACQUISITIONS;
         case RTI_NAMESPACE::DELETE_OBJECTS_THEN_DIVEST:
            return rti1516_2025::fedpro::DELETE_OBJECTS_THEN_DIVEST;
         case RTI_NAMESPACE::CANCEL_THEN_DELETE_THEN_DIVEST:
            return rti1516_2025::fedpro::CANCEL_THEN_DELETE_THEN_DIVEST;
         case RTI_NAMESPACE::NO_ACTION:
            return rti1516_2025::fedpro::NO_ACTION;
         default:
            throw std::invalid_argument("Invalid ResignAction value");
      }
   }

   RTI_NAMESPACE::RestoreFailureReason ClientConverter::convertToHla(const rti1516_2025::fedpro::RestoreFailureReason fedproFailureReason) const
   {
      switch (fedproFailureReason) {
         case rti1516_2025::fedpro::RTI_UNABLE_TO_RESTORE:
            return RTI_NAMESPACE::RTI_UNABLE_TO_RESTORE;
         case rti1516_2025::fedpro::FEDERATE_REPORTED_FAILURE_DURING_RESTORE:
            return RTI_NAMESPACE::FEDERATE_REPORTED_FAILURE_DURING_RESTORE;
         case rti1516_2025::fedpro::FEDERATE_RESIGNED_DURING_RESTORE:
            return RTI_NAMESPACE::FEDERATE_RESIGNED_DURING_RESTORE;
         case rti1516_2025::fedpro::RTI_DETECTED_FAILURE_DURING_RESTORE:
            return RTI_NAMESPACE::RTI_DETECTED_FAILURE_DURING_RESTORE;
         case rti1516_2025::fedpro::RESTORE_ABORTED:
            return RTI_NAMESPACE::RESTORE_ABORTED;
         default:
            throw std::invalid_argument("Invalid RestoreFailureReason value");
      }
   }

   rti1516_2025::fedpro::RestoreFailureReason ClientConverter::convertFromHla(const RTI_NAMESPACE::RestoreFailureReason rtiFailureReason) const
   {
      switch (rtiFailureReason) {
         case RTI_NAMESPACE::RTI_UNABLE_TO_RESTORE:
            return rti1516_2025::fedpro::RTI_UNABLE_TO_RESTORE;
         case RTI_NAMESPACE::FEDERATE_REPORTED_FAILURE_DURING_RESTORE:
            return rti1516_2025::fedpro::FEDERATE_REPORTED_FAILURE_DURING_RESTORE;
         case RTI_NAMESPACE::FEDERATE_RESIGNED_DURING_RESTORE:
            return rti1516_2025::fedpro::FEDERATE_RESIGNED_DURING_RESTORE;
         case RTI_NAMESPACE::RTI_DETECTED_FAILURE_DURING_RESTORE:
            return rti1516_2025::fedpro::RTI_DETECTED_FAILURE_DURING_RESTORE;
         case RTI_NAMESPACE::RESTORE_ABORTED:
            return rti1516_2025::fedpro::RESTORE_ABORTED;
         default:
            throw std::invalid_argument("Invalid RestoreFailureReason value");
      }
   }

   RTI_NAMESPACE::RestoreStatus ClientConverter::convertToHla(rti1516_2025::fedpro::RestoreStatus fedproStatus) const
   {
      switch (fedproStatus) {
         case rti1516_2025::fedpro::NO_RESTORE_IN_PROGRESS:
            return RTI_NAMESPACE::NO_RESTORE_IN_PROGRESS;
         case rti1516_2025::fedpro::FEDERATE_RESTORE_REQUEST_PENDING:
            return RTI_NAMESPACE::FEDERATE_RESTORE_REQUEST_PENDING;
         case rti1516_2025::fedpro::FEDERATE_WAITING_FOR_RESTORE_TO_BEGIN:
            return RTI_NAMESPACE::FEDERATE_WAITING_FOR_RESTORE_TO_BEGIN;
         case rti1516_2025::fedpro::FEDERATE_PREPARED_TO_RESTORE:
            return RTI_NAMESPACE::FEDERATE_PREPARED_TO_RESTORE;
         case rti1516_2025::fedpro::FEDERATE_RESTORING:
            return RTI_NAMESPACE::FEDERATE_RESTORING;
         case rti1516_2025::fedpro::FEDERATE_WAITING_FOR_FEDERATION_TO_RESTORE:
            return RTI_NAMESPACE::FEDERATE_WAITING_FOR_FEDERATION_TO_RESTORE;
         default:
            throw std::invalid_argument("Invalid RestoreStatus value");
      }
   }

   RTI_NAMESPACE::SaveFailureReason ClientConverter::convertToHla(const rti1516_2025::fedpro::SaveFailureReason fedproFailureReason) const
   {
      switch (fedproFailureReason) {
         case rti1516_2025::fedpro::RTI_UNABLE_TO_SAVE:
            return RTI_NAMESPACE::RTI_UNABLE_TO_SAVE;
         case rti1516_2025::fedpro::FEDERATE_REPORTED_FAILURE_DURING_SAVE:
            return RTI_NAMESPACE::FEDERATE_REPORTED_FAILURE_DURING_SAVE;
         case rti1516_2025::fedpro::FEDERATE_RESIGNED_DURING_SAVE:
            return RTI_NAMESPACE::FEDERATE_RESIGNED_DURING_SAVE;
         case rti1516_2025::fedpro::RTI_DETECTED_FAILURE_DURING_SAVE:
            return RTI_NAMESPACE::RTI_DETECTED_FAILURE_DURING_SAVE;
         case rti1516_2025::fedpro::SAVE_TIME_CANNOT_BE_HONORED:
            return RTI_NAMESPACE::SAVE_TIME_CANNOT_BE_HONORED;
         case rti1516_2025::fedpro::SAVE_ABORTED:
            return RTI_NAMESPACE::SAVE_ABORTED;
         default:
            throw std::invalid_argument("Invalid SaveFailureReason value");
      }
   }

   rti1516_2025::fedpro::SaveFailureReason ClientConverter::convertFromHla(const RTI_NAMESPACE::SaveFailureReason rtiFailureReason) const
   {

      switch (rtiFailureReason) {
         case RTI_NAMESPACE::RTI_UNABLE_TO_SAVE:
            return rti1516_2025::fedpro::RTI_UNABLE_TO_SAVE;
         case RTI_NAMESPACE::FEDERATE_REPORTED_FAILURE_DURING_SAVE:
            return rti1516_2025::fedpro::FEDERATE_REPORTED_FAILURE_DURING_SAVE;
         case RTI_NAMESPACE::FEDERATE_RESIGNED_DURING_SAVE:
            return rti1516_2025::fedpro::FEDERATE_RESIGNED_DURING_SAVE;
         case RTI_NAMESPACE::RTI_DETECTED_FAILURE_DURING_SAVE:
            return rti1516_2025::fedpro::RTI_DETECTED_FAILURE_DURING_SAVE;
         case RTI_NAMESPACE::SAVE_TIME_CANNOT_BE_HONORED:
            return rti1516_2025::fedpro::SAVE_TIME_CANNOT_BE_HONORED;
         case RTI_NAMESPACE::SAVE_ABORTED:
            return rti1516_2025::fedpro::SAVE_ABORTED;
         default:
            throw std::invalid_argument("Invalid SaveFailureReason value");
      }
   }

   RTI_NAMESPACE::SaveStatus ClientConverter::convertToHla(rti1516_2025::fedpro::SaveStatus fedproStatus) const
   {
      switch (fedproStatus) {
         case rti1516_2025::fedpro::NO_SAVE_IN_PROGRESS:
            return RTI_NAMESPACE::NO_SAVE_IN_PROGRESS;
         case rti1516_2025::fedpro::FEDERATE_INSTRUCTED_TO_SAVE:
            return RTI_NAMESPACE::FEDERATE_INSTRUCTED_TO_SAVE;
         case rti1516_2025::fedpro::FEDERATE_SAVING:
            return RTI_NAMESPACE::FEDERATE_SAVING;
         case rti1516_2025::fedpro::FEDERATE_WAITING_FOR_FEDERATION_TO_SAVE:
            return RTI_NAMESPACE::FEDERATE_WAITING_FOR_FEDERATION_TO_SAVE;
         default:
            throw std::invalid_argument("Invalid SaveStatus value");
      }
   }

   RTI_NAMESPACE::ServiceGroup ClientConverter::convertToHla(const rti1516_2025::fedpro::ServiceGroup fedproServiceGroup) const
   {
      switch (fedproServiceGroup) {
         case rti1516_2025::fedpro::FEDERATION_MANAGEMENT:
            return RTI_NAMESPACE::FEDERATION_MANAGEMENT;
         case rti1516_2025::fedpro::DECLARATION_MANAGEMENT:
            return RTI_NAMESPACE::DECLARATION_MANAGEMENT;
         case rti1516_2025::fedpro::OBJECT_MANAGEMENT:
            return RTI_NAMESPACE::OBJECT_MANAGEMENT;
         case rti1516_2025::fedpro::OWNERSHIP_MANAGEMENT:
            return RTI_NAMESPACE::OWNERSHIP_MANAGEMENT;
         case rti1516_2025::fedpro::TIME_MANAGEMENT:
            return RTI_NAMESPACE::TIME_MANAGEMENT;
         case rti1516_2025::fedpro::DATA_DISTRIBUTION_MANAGEMENT:
            return RTI_NAMESPACE::DATA_DISTRIBUTION_MANAGEMENT;
         case rti1516_2025::fedpro::SUPPORT_SERVICES:
            return RTI_NAMESPACE::SUPPORT_SERVICES;
         default:
            throw std::invalid_argument("Invalid ServiceGroup value");
      }
   }

   rti1516_2025::fedpro::ServiceGroup ClientConverter::convertFromHla(const RTI_NAMESPACE::ServiceGroup rtiServiceGroup) const
   {
      switch (rtiServiceGroup) {
         case RTI_NAMESPACE::FEDERATION_MANAGEMENT:
            return rti1516_2025::fedpro::FEDERATION_MANAGEMENT;
         case RTI_NAMESPACE::DECLARATION_MANAGEMENT:
            return rti1516_2025::fedpro::DECLARATION_MANAGEMENT;
         case RTI_NAMESPACE::OBJECT_MANAGEMENT:
            return rti1516_2025::fedpro::OBJECT_MANAGEMENT;
         case RTI_NAMESPACE::OWNERSHIP_MANAGEMENT:
            return rti1516_2025::fedpro::OWNERSHIP_MANAGEMENT;
         case RTI_NAMESPACE::TIME_MANAGEMENT:
            return rti1516_2025::fedpro::TIME_MANAGEMENT;
         case RTI_NAMESPACE::DATA_DISTRIBUTION_MANAGEMENT:
            return rti1516_2025::fedpro::DATA_DISTRIBUTION_MANAGEMENT;
         case RTI_NAMESPACE::SUPPORT_SERVICES:
            return rti1516_2025::fedpro::SUPPORT_SERVICES;
         default:
            throw std::invalid_argument("Invalid ServiceGroup value");
      }
   }

   RTI_NAMESPACE::SynchronizationPointFailureReason ClientConverter::convertToHla(const rti1516_2025::fedpro::SynchronizationPointFailureReason fedproFailureReason) const
   {
      switch (fedproFailureReason) {
         case rti1516_2025::fedpro::SYNCHRONIZATION_POINT_LABEL_NOT_UNIQUE:
            return RTI_NAMESPACE::SYNCHRONIZATION_POINT_LABEL_NOT_UNIQUE;
         case rti1516_2025::fedpro::SYNCHRONIZATION_SET_MEMBER_NOT_JOINED:
            return RTI_NAMESPACE::SYNCHRONIZATION_SET_MEMBER_NOT_JOINED;
         default:
            throw std::invalid_argument("Invalid SynchronizationPointFailureReason value");
      }
   }

   //
   // Arrays and strings
   //

   std::string * ClientConverter::convertFromHla(std::string && s) const
   {
      return new std::string(std::move(s));
   }

   std::string * ClientConverter::convertFromHla(const std::string & s) const
   {
      return new std::string(s);
   }

   std::string ClientConverter::convertStringFromHla(const wstring_view s) const
   {
      return toString(s);
   }

   std::string * ClientConverter::convertFromHla(const wstring_view s) const
   {
      return new std::string(convertStringFromHla(s));
   }

   std::wstring ClientConverter::convertToHla(const char * s) const
   {
      return convertToHla(string_view(s));
   }

   std::wstring ClientConverter::convertToHla(const string_view s) const
   {
      return toWString(s);
   }

   RTI_NAMESPACE::VariableLengthData ClientConverter::convertToHlaByteArray(const string_view s) const
   {
      return {s.data(), s.size()};
   }

   std::string ClientConverter::convertFromHla(const RTI_NAMESPACE::VariableLengthData & variableLengthData) const
   {
      return {reinterpret_cast<const char *>(variableLengthData.data()), variableLengthData.size()};
   }

   // Handles

   // Create a FedPro handle from a RTI API Handle.
   // Ownership of the handle allocated and returned is assumed by protobuf code, which will delete the object.
   template<typename FedProHandleT, typename RTIHandleT>
   static FedProHandleT * convertFromHlaHandle(const RTIHandleT & rtiHandle)
   {
      auto * handle = new FedProHandleT;
      handle->set_data(std::move(RTI_NAMESPACE::serialize(rtiHandle)));
      return handle;
   }

   // Create a HLA RTI handle from encoded data.
   // @param fedproHandle Federate Protocol handle to be converted. This handle's data will be moved, and may be left empty.
   template<typename RTIHandleT, typename FedProHandleT>
   static RTIHandleT convertToHlaHandle(FedProHandleT && handle)
   {
      auto * mutableData = handle.mutable_data();
      if (mutableData) {
         return RTI_NAMESPACE::make_handle<RTIHandleT>(std::move(*mutableData));
      } else {
         return {};
      }
   }

   RTI_NAMESPACE::FederateHandle ClientConverter::convertToHla(rti1516_2025::fedpro::FederateHandle && fedproFederateHandle) const
   {
      return convertToHlaHandle<RTI_NAMESPACE::FederateHandle>(fedproFederateHandle);
   }

   rti1516_2025::fedpro::FederateHandle * ClientConverter::convertFromHla(const RTI_NAMESPACE::FederateHandle & federateHandle) const
   {
      return convertFromHlaHandle<rti1516_2025::fedpro::FederateHandle>(federateHandle);
   }

   RTI_NAMESPACE::ObjectClassHandle ClientConverter::convertToHla(rti1516_2025::fedpro::ObjectClassHandle && fedproClassHandle) const
   {
      return convertToHlaHandle<RTI_NAMESPACE::ObjectClassHandle>(fedproClassHandle);
   }

   rti1516_2025::fedpro::ObjectClassHandle * ClientConverter::convertFromHla(const RTI_NAMESPACE::ObjectClassHandle & handle) const
   {
      return convertFromHlaHandle<rti1516_2025::fedpro::ObjectClassHandle>(handle);
   }

   RTI_NAMESPACE::InteractionClassHandle ClientConverter::convertToHla(rti1516_2025::fedpro::InteractionClassHandle && fedproInteractionHandle) const
   {
      return convertToHlaHandle<RTI_NAMESPACE::InteractionClassHandle>(fedproInteractionHandle);
   }

   rti1516_2025::fedpro::InteractionClassHandle * ClientConverter::convertFromHla(const RTI_NAMESPACE::InteractionClassHandle & handle) const
   {
      return convertFromHlaHandle<rti1516_2025::fedpro::InteractionClassHandle>(handle);
   }

   RTI_NAMESPACE::ObjectInstanceHandle ClientConverter::convertToHla(rti1516_2025::fedpro::ObjectInstanceHandle && fedproObjectHandle) const
   {
      return convertToHlaHandle<RTI_NAMESPACE::ObjectInstanceHandle>(fedproObjectHandle);
   }

   rti1516_2025::fedpro::ObjectInstanceHandle * ClientConverter::convertFromHla(const RTI_NAMESPACE::ObjectInstanceHandle & handle) const
   {
      return convertFromHlaHandle<rti1516_2025::fedpro::ObjectInstanceHandle>(handle);
   }

   RTI_NAMESPACE::AttributeHandle ClientConverter::convertToHla(rti1516_2025::fedpro::AttributeHandle && fedproAttribHandle) const
   {
      return convertToHlaHandle<RTI_NAMESPACE::AttributeHandle>(fedproAttribHandle);
   }

   rti1516_2025::fedpro::AttributeHandle * ClientConverter::convertFromHla(const RTI_NAMESPACE::AttributeHandle & attributeHandle) const
   {
      return convertFromHlaHandle<rti1516_2025::fedpro::AttributeHandle>(attributeHandle);
   }

   RTI_NAMESPACE::ParameterHandle ClientConverter::convertToHla(rti1516_2025::fedpro::ParameterHandle && fedproParamHandle) const
   {
      return convertToHlaHandle<RTI_NAMESPACE::ParameterHandle>(fedproParamHandle);
   }

   rti1516_2025::fedpro::ParameterHandle * ClientConverter::convertFromHla(const RTI_NAMESPACE::ParameterHandle & handle) const
   {
      return convertFromHlaHandle<rti1516_2025::fedpro::ParameterHandle>(handle);
   }

   RTI_NAMESPACE::DimensionHandle ClientConverter::convertToHla(rti1516_2025::fedpro::DimensionHandle && fedproDimensionHandle) const
   {
      return convertToHlaHandle<RTI_NAMESPACE::DimensionHandle>(fedproDimensionHandle);
   }

   rti1516_2025::fedpro::DimensionHandle * ClientConverter::convertFromHla(const RTI_NAMESPACE::DimensionHandle & dimensionHandle) const
   {
      return convertFromHlaHandle<rti1516_2025::fedpro::DimensionHandle>(dimensionHandle);
   }

   RTI_UNIQUE_PTR<RTI_NAMESPACE::MessageRetractionHandle> ClientConverter::convertToHla(rti1516_2025::fedpro::MessageRetractionHandle && fedproRetractHandle) const
   {
      auto rtiRetractHandle = convertToHlaHandle<RTI_NAMESPACE::MessageRetractionHandle>(fedproRetractHandle);
      return RTI_NAMESPACE::make_unique<RTI_NAMESPACE::MessageRetractionHandle>(std::move(rtiRetractHandle));
   }

   RTI_NAMESPACE::MessageRetractionHandle ClientConverter::convertToHla(rti1516_2025::fedpro::MessageRetractionReturn && fedproRetractReturn) const
   {
      auto fedproRetractHandle = fedproRetractReturn.mutable_messageretractionhandle();
      if (fedproRetractHandle) {
         return convertToHlaHandle<RTI_NAMESPACE::MessageRetractionHandle>(std::move(*fedproRetractHandle));
      } else {
         return {};
      }
   }

   rti1516_2025::fedpro::MessageRetractionHandle * ClientConverter::convertFromHla(const RTI_NAMESPACE::MessageRetractionHandle & rtiRetractHandle) const
   {
      return convertFromHlaHandle<rti1516_2025::fedpro::MessageRetractionHandle>(rtiRetractHandle);
   }

   RTI_NAMESPACE::RegionHandle ClientConverter::convertToHla(rti1516_2025::fedpro::RegionHandle && fedproRegionHandle) const
   {
      return convertToHlaHandle<RTI_NAMESPACE::RegionHandle>(fedproRegionHandle);
   }

   rti1516_2025::fedpro::RegionHandle * ClientConverter::convertFromHla(const RTI_NAMESPACE::RegionHandle & handle) const
   {
      return convertFromHlaHandle<rti1516_2025::fedpro::RegionHandle>(handle);
   }

   RTI_NAMESPACE::TransportationTypeHandle ClientConverter::convertToHla(rti1516_2025::fedpro::TransportationTypeHandle && fedproTransportHandle) const
   {
      return convertToHlaHandle<RTI_NAMESPACE::TransportationTypeHandle>(fedproTransportHandle);
   }

   rti1516_2025::fedpro::TransportationTypeHandle * ClientConverter::convertFromHla(const RTI_NAMESPACE::TransportationTypeHandle & handle) const
   {
      return convertFromHlaHandle<rti1516_2025::fedpro::TransportationTypeHandle>(handle);
   }

   // Objects

   RTI_NAMESPACE::RtiConfiguration ClientConverter::convertToHla(const rti1516_2025::fedpro::RtiConfiguration & fedProConfig) const
   {
      RTI_NAMESPACE::RtiConfiguration rtiConfig;
      rtiConfig.withConfigurationName(convertToHla(fedProConfig.configurationname()));
      rtiConfig.withRtiAddress(convertToHla(fedProConfig.rtiaddress()));
      rtiConfig.withAdditionalSettings(convertToHla(fedProConfig.additionalsettings()));
      return rtiConfig;
   }

   rti1516_2025::fedpro::RtiConfiguration * ClientConverter::convertFromHla(const RTI_NAMESPACE::RtiConfiguration & configuration) const
   {
      auto * rtiConfig = new rti1516_2025::fedpro::RtiConfiguration;
      rtiConfig->set_allocated_configurationname(convertFromHla(configuration.configurationName()));
      rtiConfig->set_allocated_rtiaddress(convertFromHla(configuration.rtiAddress()));
      rtiConfig->set_allocated_additionalsettings(convertFromHla(configuration.additionalSettings()));
      return rtiConfig;
   }

   RTI_NAMESPACE::ConfigurationResult ClientConverter::convertToHla(const rti1516_2025::fedpro::ConfigurationResult & decodedResponse) const
   {
      bool configurationUsed = decodedResponse.configurationused();
      bool addressUsed = decodedResponse.addressused();
      auto additionalSettingsResultCode = convertToHla(decodedResponse.additionalsettingsresultcode());
      std::wstring message = make_wstring(decodedResponse.message());
      return {configurationUsed, addressUsed, additionalSettingsResultCode, message};
   }

#if (RTI_HLA_VERSION >= 2025)
   rti1516_2025::fedpro::Credentials * ClientConverter::convertFromHla(const RTI_NAMESPACE::Credentials & credentials) const
   {
      auto * fedproCredentials = new rti1516_2025::fedpro::Credentials;
      fedproCredentials->set_type(toString(credentials.getType()));
      fedproCredentials->set_data(convertFromHla(credentials.getData()));
      return fedproCredentials;
   }
#endif

   RTI_NAMESPACE::FederationExecutionInformation ClientConverter::convertToHla(const rti1516_2025::fedpro::FederationExecutionInformation & obj) const
   {
      return RTI_NAMESPACE::FederationExecutionInformation{convertToHla(obj.federationexecutionname()),
                                                          convertToHla(obj.logicaltimeimplementationname())};
   }

#if (RTI_HLA_VERSION >= 2025)
   RTI_NAMESPACE::FederationExecutionMemberInformation ClientConverter::convertToHla(const rti1516_2025::fedpro::FederationExecutionMemberInformation & obj) const
   {
      return RTI_NAMESPACE::FederationExecutionMemberInformation{convertToHla(obj.federatename()),
                                                                convertToHla(obj.federatetype())};
   }
#endif

   RTI_NAMESPACE::FederateRestoreStatus ClientConverter::convertToHla(rti1516_2025::fedpro::FederateRestoreStatus && fedproRestoreStatus) const
   {
      return RTI_NAMESPACE::FederateRestoreStatus{convertToHlaAndDelete(fedproRestoreStatus.release_prerestorehandle()),
                                                 convertToHlaAndDelete(fedproRestoreStatus.release_postrestorehandle()),
                                                 convertToHla(fedproRestoreStatus.restorestatus())};
   }

   rti1516_2025::fedpro::FomModule * ClientConverter::convertFromHla(const FedPro::FomModule & fomModule) const
   {
      auto fedProObj = new rti1516_2025::fedpro::FomModule();
      switch (fomModule.getType()) {
         case FedPro::FomModule::Type::FILE:
         {
            auto fomModuleFile = new rti1516_2025::fedpro::FileFomModule;
            fomModuleFile->set_allocated_name(convertFromHla(fomModule.getFileName()));
            fomModuleFile->set_content(fomModule.getFileContent());
            fedProObj->set_allocated_file(fomModuleFile);
            break;
         }
         case FedPro::FomModule::Type::COMPRESSED:
            fedProObj->set_compressedmodule(fomModule.getCompressedModule());
            break;
         case FedPro::FomModule::Type::URL:
            fedProObj->set_allocated_url(convertFromHla(fomModule.getUrl()));
            break;
      }

      return fedProObj;
   }

   FedPro::JoinResult ClientConverter::convertToHla(rti1516_2025::fedpro::JoinResult && joinResult) const
   {
      if (!joinResult.has_federatehandle()) {
         throw RTI_NAMESPACE::RTIinternalError(L"Missing FederateHandle in Federate Protocol JoinResult");
      }
      return {convertToHlaAndDelete(joinResult.release_federatehandle()),
              convertToHla(joinResult.logicaltimeimplementationname())};
   }

   RTI_UNIQUE_PTR<RTI_NAMESPACE::LogicalTime> ClientConverter::convertToHla(const rti1516_2025::fedpro::LogicalTime & logicalTime) const
   {
      if (!_timeFactory)
         throw RTI_NAMESPACE::RTIinternalError(L"Error in LogicalTime conversion due to missing factory");

      try {
         return _timeFactory->decodeLogicalTime(const_cast<char *>(logicalTime.data().data()), logicalTime.data().size());
      } catch (...) {
         throw RTI_NAMESPACE::RTIinternalError(L"Error in LogicalTime conversion");
      }
   }

   rti1516_2025::fedpro::LogicalTime * ClientConverter::convertFromHla(const RTI_NAMESPACE::LogicalTime & rtiLogicalTime) const
   {
      std::string data;
      data.resize(rtiLogicalTime.encodedLength());
      try {
         rtiLogicalTime.encode(&data.front(), data.size());
      } catch (const RTI_NAMESPACE::CouldNotEncode & e) {
         throw RTI_NAMESPACE::RTIinternalError(L"Error in LogicalTime conversion: "  + e.what());
      }

      auto logicalTime = new rti1516_2025::fedpro::LogicalTime;
      logicalTime->set_data(std::move(data));
      return logicalTime;
   }

   RTI_UNIQUE_PTR<RTI_NAMESPACE::LogicalTimeInterval> ClientConverter::convertToHla(const rti1516_2025::fedpro::LogicalTimeInterval & logicalTimeInterval) const
   {
      if (!_timeFactory)
         throw RTI_NAMESPACE::RTIinternalError(L"Error in LogicalTimeInterval conversion due to missing factory");

      try {
         return _timeFactory->decodeLogicalTimeInterval(
               const_cast<char *>(logicalTimeInterval.data().data()),
               logicalTimeInterval.data().size());
      } catch (...) {
         throw RTI_NAMESPACE::RTIinternalError(L"Error in LogicalTimeInterval conversion");
      }
   }

   rti1516_2025::fedpro::LogicalTimeInterval * ClientConverter::convertFromHla(const RTI_NAMESPACE::LogicalTimeInterval & rtiLogicalTimeInterval) const
   {
      std::string data;
      data.resize(rtiLogicalTimeInterval.encodedLength());
      try {
         rtiLogicalTimeInterval.encode(&data.front(), data.size());
      } catch (const RTI_NAMESPACE::CouldNotEncode & e) {
         throw RTI_NAMESPACE::RTIinternalError(L"Error in LogicalTimeInterval conversion: " + e.what());
      }

      auto logicalTimeInterval = new rti1516_2025::fedpro::LogicalTimeInterval;
      logicalTimeInterval->set_data(std::move(data));
      return logicalTimeInterval;
   }

   RTI_NAMESPACE::RangeBounds ClientConverter::convertToHla(const rti1516_2025::fedpro::RangeBounds & obj) const
   {
      return RTI_NAMESPACE::RangeBounds{obj.lower(), obj.upper()};
   }

   rti1516_2025::fedpro::RangeBounds * ClientConverter::convertFromHla(const RTI_NAMESPACE::RangeBounds & rangeBounds) const
   {
      auto obj = new rti1516_2025::fedpro::RangeBounds;
      obj->set_lower(rangeBounds.getLowerBound());
      obj->set_upper(rangeBounds.getUpperBound());
      return obj;
   }

   std::pair<bool, std::unique_ptr<RTI_NAMESPACE::LogicalTime>> ClientConverter::convertToHla(const rti1516_2025::fedpro::TimeQueryReturn & timeQueryReturn) const
   {
      // Return type is pair<bool, std::unique_ptr> instead of pair<bool, RTI_UNIQUE_PTR>
      // because RTI_UNIQUE_PTR may be std::auto_ptr, which is incompatible with std::pair.
      return {timeQueryReturn.logicaltimeisvalid(), convertToHla(timeQueryReturn.logicaltime())};
   }

   // Vectors

   RTI_NAMESPACE::FederateRestoreStatusVector ClientConverter::convertToHla(rti1516_2025::fedpro::FederateRestoreStatusArray && federateRestoreStatusArray) const
   {
      auto * fedproSequence = federateRestoreStatusArray.mutable_federaterestorestatus();
      return convertToHlaVector(std::move(*fedproSequence));
   }

   RTI_NAMESPACE::FederationExecutionInformationVector ClientConverter::convertToHla(rti1516_2025::fedpro::FederationExecutionInformationSet && federationExecutionInformationSet) const
   {
      return convertToHlaVector(federationExecutionInformationSet.federationexecutioninformation());
   }

#if (RTI_HLA_VERSION >= 2025)
   RTI_NAMESPACE::FederationExecutionMemberInformationVector ClientConverter::convertToHla(rti1516_2025::fedpro::FederationExecutionMemberInformationSet && federationExecutionMemberInformationSet) const
   {
      return convertToHlaVector(federationExecutionMemberInformationSet.federationexecutionmemberinformation());
   }
#endif

   rti1516_2025::fedpro::AttributeSetRegionSetPairList * ClientConverter::convertFromHla(const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributeHandleSetRegionHandleSetPairVector) const
   {
      auto fedproVector = new rti1516_2025::fedpro::AttributeSetRegionSetPairList;
      for (const auto & element : attributeHandleSetRegionHandleSetPairVector) {
         rti1516_2025::fedpro::AttributeSetRegionSetPair * fedproPair = fedproVector->add_attributesetregionsetpair();
         fedproPair->set_allocated_attributeset(convertFromHla(element.first));
         fedproPair->set_allocated_regionset(convertFromHla(element.second));
      }

      return fedproVector;
   }

   // Maps

   RTI_NAMESPACE::AttributeHandleValueMap ClientConverter::convertToHla(rti1516_2025::fedpro::AttributeHandleValueMap && attributeHandleValueMap) const
   {
      RTI_NAMESPACE::AttributeHandleValueMap handleMap;
      for (auto & entry : *attributeHandleValueMap.mutable_attributehandlevalue()) {
         handleMap.emplace(
               convertToHlaAndDelete(entry.release_attributehandle()), convertToHlaByteArray(entry.value()));
      }

      return handleMap;
   }

   rti1516_2025::fedpro::AttributeHandleValueMap * ClientConverter::convertFromHla(const RTI_NAMESPACE::AttributeHandleValueMap & attributeHandleValueMap) const
   {
      auto handleValueMap = new rti1516_2025::fedpro::AttributeHandleValueMap;
      for (const auto & handleValuePair : attributeHandleValueMap) {
         rti1516_2025::fedpro::AttributeHandle * handle = convertFromHla(handleValuePair.first);
         std::string encodedValue{convertFromHla(handleValuePair.second)};
         rti1516_2025::fedpro::AttributeHandleValue * handleValue = handleValueMap->add_attributehandlevalue();
         handleValue->set_allocated_attributehandle(handle);
         handleValue->set_value(std::move(encodedValue));
      }

      return handleValueMap;
   }

   RTI_NAMESPACE::ParameterHandleValueMap ClientConverter::convertToHla(rti1516_2025::fedpro::ParameterHandleValueMap && parameterHandleValueMap) const
   {
      RTI_NAMESPACE::ParameterHandleValueMap handleMap;
      for (auto & entry : *parameterHandleValueMap.mutable_parameterhandlevalue()) {
         handleMap.emplace(
               convertToHlaAndDelete(entry.release_parameterhandle()), convertToHlaByteArray(entry.value()));
      }

      return handleMap;
   }

   rti1516_2025::fedpro::ParameterHandleValueMap * ClientConverter::convertFromHla(const RTI_NAMESPACE::ParameterHandleValueMap & parameterHandleValueMap) const
   {
      auto handleValueMap = new rti1516_2025::fedpro::ParameterHandleValueMap;
      for (const auto & handleValuePair : parameterHandleValueMap) {
         rti1516_2025::fedpro::ParameterHandle * handle = convertFromHla(handleValuePair.first);
         std::string encodedValue{convertFromHla(handleValuePair.second)};
         rti1516_2025::fedpro::ParameterHandleValue * handleValue = handleValueMap->add_parameterhandlevalue();
         handleValue->set_allocated_parameterhandle(handle);
         handleValue->set_value(std::move(encodedValue));
      }

      return handleValueMap;
   }

   // Sets

   RTI_NAMESPACE::AttributeHandleSet ClientConverter::convertToHla(rti1516_2025::fedpro::AttributeHandleSet && attributeHandleSet) const
   {
      auto* fedproHandleSequence = attributeHandleSet.mutable_attributehandle();
      return convertToHlaSet(std::move(*fedproHandleSequence));
   }

   rti1516_2025::fedpro::AttributeHandleSet * ClientConverter::convertFromHla(const RTI_NAMESPACE::AttributeHandleSet & hlaHandleSet) const
   {
      auto handleSet = new rti1516_2025::fedpro::AttributeHandleSet;
      for (const auto & hlaHandle : hlaHandleSet) {
         std::string encodedHandle{RTI_NAMESPACE::serialize(hlaHandle)};
         rti1516_2025::fedpro::AttributeHandle * handle = handleSet->add_attributehandle();
         handle->set_data(std::move(encodedHandle));
      }

      return handleSet;
   }

   RTI_NAMESPACE::DimensionHandleSet ClientConverter::convertToHla(rti1516_2025::fedpro::DimensionHandleSet && fedproHandleSet) const
   {
      auto * fedproHandleSequence = fedproHandleSet.mutable_dimensionhandle();
      return convertToHlaSet(std::move(*fedproHandleSequence));
   }

   rti1516_2025::fedpro::DimensionHandleSet * ClientConverter::convertFromHla(const RTI_NAMESPACE::DimensionHandleSet & hlaHandleSet) const
   {
      auto handleSet = new rti1516_2025::fedpro::DimensionHandleSet;
      for (const auto & hlaHandle : hlaHandleSet) {
         std::string encodedHandle{RTI_NAMESPACE::serialize(hlaHandle)};
         rti1516_2025::fedpro::DimensionHandle * handle = handleSet->add_dimensionhandle();
         handle->set_data(std::move(encodedHandle));
      }

      return handleSet;
   }

   RTI_NAMESPACE::FederateHandleSaveStatusPairVector ClientConverter::convertToHla(rti1516_2025::fedpro::FederateHandleSaveStatusPairArray && federateHandleSaveStatusPairArray) const
   {
      RTI_NAMESPACE::FederateHandleSaveStatusPairVector statusPairVector;
      statusPairVector.reserve(federateHandleSaveStatusPairArray.federatehandlesavestatuspair_size());
      for (auto & statusPair : *federateHandleSaveStatusPairArray.mutable_federatehandlesavestatuspair()) {
         statusPairVector.emplace_back(
               convertToHlaAndDelete(statusPair.release_federatehandle()), convertToHla(statusPair.savestatus()));
      }

      return statusPairVector;
   }

   RTI_NAMESPACE::FederateHandleSet ClientConverter::convertToHla(rti1516_2025::fedpro::FederateHandleSet && fedproHandleSet) const
   {
      auto * fedproHandleSequence = fedproHandleSet.mutable_federatehandle();
      return convertToHlaSet(std::move(*fedproHandleSequence));
   }

   rti1516_2025::fedpro::FederateHandleSet * ClientConverter::convertFromHla(const RTI_NAMESPACE::FederateHandleSet & hlaHandleSet) const
   {
      auto handleSet = new rti1516_2025::fedpro::FederateHandleSet;
      for (const auto & hlaHandle : hlaHandleSet) {
         std::string encodedHandle{RTI_NAMESPACE::serialize(hlaHandle)};
         rti1516_2025::fedpro::FederateHandle * handle = handleSet->add_federatehandle();
         handle->set_data(std::move(encodedHandle));
      }

      return handleSet;
   }

#if (RTI_HLA_VERSION >= 2025)
   RTI_NAMESPACE::InteractionClassHandleSet ClientConverter::convertToHla(rti1516_2025::fedpro::InteractionClassHandleSet && fedproHandleSet) const
   {
      auto * fedproHandleSequence = fedproHandleSet.mutable_interactionclasshandle();
      return convertToHlaSet(std::move(*fedproHandleSequence));
   }

   rti1516_2025::fedpro::InteractionClassHandleSet * ClientConverter::convertFromHla(const RTI_NAMESPACE::InteractionClassHandleSet & hlaHandleSet) const
   {
      auto handleSet = new rti1516_2025::fedpro::InteractionClassHandleSet;
      for (const auto & hlaHandle : hlaHandleSet) {
         std::string encodedHandle{RTI_NAMESPACE::serialize(hlaHandle)};
         rti1516_2025::fedpro::InteractionClassHandle * handle = handleSet->add_interactionclasshandle();
         handle->set_data(std::move(encodedHandle));
      }

      return handleSet;
   }
#endif

   RTI_NAMESPACE::RegionHandleSet * ClientConverter::convertToHla(rti1516_2025::fedpro::ConveyedRegionSet && conveyedRegionSet) const
   {
      auto * hlaSet = new RTI_NAMESPACE::RegionHandleSet;
      for (auto & conveyedRegion : *conveyedRegionSet.mutable_conveyedregions()) {
         RTI_NAMESPACE::RegionHandle regionHandle;
         for (auto & dimensionAndRange : *conveyedRegion.mutable_dimensionandrange()) {
            auto regionHandleImpl = static_cast<RTI_NAMESPACE::RegionHandleImplementation * >(getImplementation(regionHandle));
            regionHandleImpl->addRange(
                  convertToHlaAndDelete(dimensionAndRange.release_dimensionhandle()),
                  convertToHla(dimensionAndRange.rangebounds()));
         }

         hlaSet->emplace(regionHandle);
      }

      return hlaSet;
   }

   rti1516_2025::fedpro::RegionHandleSet * ClientConverter::convertFromHla(const RTI_NAMESPACE::RegionHandleSet & rtiHandleSet) const
   {
      auto regionHandleSet = new rti1516_2025::fedpro::RegionHandleSet;
      for (const auto & rtiHandle : rtiHandleSet) {
         rti1516_2025::fedpro::RegionHandle * regionHandle = regionHandleSet->add_regionhandle();
         regionHandle->set_data(RTI_NAMESPACE::serialize(rtiHandle));
      }

      return regionHandleSet;
   }

   std::set<std::wstring> ClientConverter::convertToHla(const google::protobuf::RepeatedPtrField<std::string> & strings) const
   {
      return convertToHlaSet(strings);
   }

   google::protobuf::RepeatedPtrField<std::string> ClientConverter::convertFromHla(const std::set<std::wstring> & rtiSet) const
   {
      google::protobuf::RepeatedPtrField<std::string> stringSequence;
      for (const auto & rtiElement : rtiSet) {
         stringSequence.Add(convertStringFromHla(rtiElement));
      }

      return stringSequence;
   }

   rti1516_2025::fedpro::FomModuleSet * ClientConverter::convertFromHla(const FedPro::FomModuleSet & rtiFomModuleSet) const
   {
      auto fomModuleSet = new rti1516_2025::fedpro::FomModuleSet;
      for (const auto & rtiFomModule : rtiFomModuleSet) {
         rti1516_2025::fedpro::FomModule * fomModule = fomModuleSet->add_fommodule();
         rti1516_2025::fedpro::FomModule * tmpFomModule = convertFromHla(rtiFomModule);
         if (tmpFomModule)
            *fomModule = std::move(*tmpFomModule);
         delete tmpFomModule;
      }

      return fomModuleSet;
   }

}

// NOLINTEND(hicpp-exception-baseclass)
