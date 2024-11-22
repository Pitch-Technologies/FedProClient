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

#include "FomModuleSet.h"
#include "FomModule.h"
#include "Handle.h"
#include "JoinResult.h"
#include "protobuf_util.h"
#include "protobuf/generated/datatypes.pb.h"
#include "RTIcompat.h"
#include "RtiConfiguration.h"
#include "RTITypedefs.h"
#include "utility/string_view.h"

#if (RTI_HLA_VERSION >= 2024)
#include <RTI/auth/Credentials.h>
#include <RTI/time/LogicalTimeFactory.h>
#else
#include <RTI/LogicalTimeFactory.h>
#endif
#include <RTI/Enums.h>
#include <RTI/RangeBounds.h>
#include <RTI/Typedefs.h>

#include <cstdint>
#include <string>

namespace FedPro
{
   class ClientConverter
   {
   public:

      ClientConverter();

      void setTimeFactory(std::shared_ptr<RTI_NAMESPACE::LogicalTimeFactory> timeFactory);

      const std::shared_ptr<RTI_NAMESPACE::LogicalTimeFactory> & getTimeFactory() const
      {
         return _timeFactory;
      }

      // Primitives

      constexpr bool convertFromHla(bool b) const
      {
         return b;
      }

      constexpr bool convertToHla(bool b) const
      {
         return b;
      }

      constexpr double convertFromHla(double d) const
      {
         return d;
      }

      constexpr double convertToHla(double d) const
      {
         return d;
      }

      constexpr unsigned long convertToHla(uint32_t uintValue) const
      {
         return uintValue;
      }

      // Enumerations

#if RTI_HLA_VERSION >= 2024
      /**
       * @throw std::invalid_argument If an invalid enumeration value is provided.
       */
      RTI_NAMESPACE::AdditionalSettingsResultCode convertToHla(rti1516_202X::fedpro::AdditionalSettingsResultCode fedproResultCode) const;
#endif
      /**
       * @throw std::invalid_argument If an invalid enumeration value is provided.
       */
      RTI_NAMESPACE::OrderType convertToHla(const rti1516_202X::fedpro::OrderType fedproOrderType) const;

      /**
       * @throw std::invalid_argument If an invalid enumeration value is provided.
       */
      rti1516_202X::fedpro::OrderType convertFromHla(const RTI_NAMESPACE::OrderType rtiOrderType) const;

      /**
       * @throw std::invalid_argument If an invalid enumeration value is provided.
       */
      RTI_NAMESPACE::ResignAction convertToHla(const rti1516_202X::fedpro::ResignAction fedproResignAction) const;

      /**
       * @throw std::invalid_argument If an invalid enumeration value is provided.
       */
      rti1516_202X::fedpro::ResignAction convertFromHla(const RTI_NAMESPACE::ResignAction rtiResignAction) const;

      /**
       * @throw std::invalid_argument If an invalid enumeration value is provided.
       */
      RTI_NAMESPACE::RestoreFailureReason convertToHla(const rti1516_202X::fedpro::RestoreFailureReason fedproFailureReason) const;

      /**
       * @throw std::invalid_argument If an invalid enumeration value is provided.
       */
      rti1516_202X::fedpro::RestoreFailureReason convertFromHla(const RTI_NAMESPACE::RestoreFailureReason reason) const;

      /**
       * @throw std::invalid_argument If an invalid enumeration value is provided.
       */
      RTI_NAMESPACE::RestoreStatus convertToHla(rti1516_202X::fedpro::RestoreStatus fedproStatus) const;

      /**
       * @throw std::invalid_argument If an invalid enumeration value is provided.
       */
      RTI_NAMESPACE::SaveFailureReason convertToHla(const rti1516_202X::fedpro::SaveFailureReason fedproFailureReason) const;

      /**
       * @throw std::invalid_argument If an invalid enumeration value is provided.
       */
      rti1516_202X::fedpro::SaveFailureReason convertFromHla(const RTI_NAMESPACE::SaveFailureReason rtiFailureReason) const;

      /**
       * @throw std::invalid_argument If an invalid enumeration value is provided.
       */
      RTI_NAMESPACE::SaveStatus convertToHla(rti1516_202X::fedpro::SaveStatus fedproStatus) const;

      /**
       * @throw std::invalid_argument If an invalid enumeration value is provided.
       */
      RTI_NAMESPACE::ServiceGroup convertToHla(const rti1516_202X::fedpro::ServiceGroup fedproServiceGroup) const;

      /**
       * @throw std::invalid_argument If an invalid enumeration value is provided.
       */
      rti1516_202X::fedpro::ServiceGroup convertFromHla(const RTI_NAMESPACE::ServiceGroup rtiServiceGroup) const;

      /**
       * @throw std::invalid_argument If an invalid enumeration value is provided.
       */
      RTI_NAMESPACE::SynchronizationPointFailureReason convertToHla(const rti1516_202X::fedpro::SynchronizationPointFailureReason fedproFailureReason) const;

      // Arrays and strings

      std::string * convertFromHla(std::string && s) const;

      std::string * convertFromHla(const std::string & s) const;

      std::string convertStringFromHla(const wstring_view s) const;

      std::string * convertFromHla(const wstring_view s) const;

      std::wstring convertToHla(const char * s) const;

      std::wstring convertToHla(const string_view s) const;

      RTI_NAMESPACE::VariableLengthData convertToHlaByteArray(const string_view s) const;

      std::string convertFromHla(const RTI_NAMESPACE::VariableLengthData & variableLengthData) const;

      // Handles
      // Ownership of FedPro handles allocated and returned below is assumed by protobuf code.

      RTI_NAMESPACE::FederateHandle convertToHla(rti1516_202X::fedpro::FederateHandle && federateHandle) const;

      rti1516_202X::fedpro::FederateHandle * convertFromHla(const RTI_NAMESPACE::FederateHandle & federateHandle) const;

      RTI_NAMESPACE::ObjectClassHandle convertToHla(rti1516_202X::fedpro::ObjectClassHandle && objectClassHandle) const;

      rti1516_202X::fedpro::ObjectClassHandle * convertFromHla(const RTI_NAMESPACE::ObjectClassHandle & objectClassHandle) const;

      RTI_NAMESPACE::InteractionClassHandle convertToHla(rti1516_202X::fedpro::InteractionClassHandle && interactionClassHandle) const;

      rti1516_202X::fedpro::InteractionClassHandle * convertFromHla(const RTI_NAMESPACE::InteractionClassHandle & interactionClassHandle) const;

      RTI_NAMESPACE::ObjectInstanceHandle convertToHla(rti1516_202X::fedpro::ObjectInstanceHandle && objectInstanceHandle) const;

      rti1516_202X::fedpro::ObjectInstanceHandle * convertFromHla(const RTI_NAMESPACE::ObjectInstanceHandle & objectInstanceHandle) const;

      RTI_NAMESPACE::AttributeHandle convertToHla(rti1516_202X::fedpro::AttributeHandle && attributeHandle) const;

      rti1516_202X::fedpro::AttributeHandle * convertFromHla(const RTI_NAMESPACE::AttributeHandle & attributeHandle) const;

      RTI_NAMESPACE::ParameterHandle convertToHla(rti1516_202X::fedpro::ParameterHandle && parameterHandle) const;

      rti1516_202X::fedpro::ParameterHandle * convertFromHla(const RTI_NAMESPACE::ParameterHandle & parameterHandle) const;

      RTI_NAMESPACE::DimensionHandle convertToHla(rti1516_202X::fedpro::DimensionHandle && dimensionHandle) const;

      rti1516_202X::fedpro::DimensionHandle * convertFromHla(const RTI_NAMESPACE::DimensionHandle & dimensionHandle) const;

      RTI_UNIQUE_PTR<RTI_NAMESPACE::MessageRetractionHandle> convertToHla(rti1516_202X::fedpro::MessageRetractionHandle && messageRetractionHandle) const;

      RTI_NAMESPACE::MessageRetractionHandle convertToHla(rti1516_202X::fedpro::MessageRetractionReturn && fedproRetractReturn) const;

      rti1516_202X::fedpro::MessageRetractionHandle * convertFromHla(const RTI_NAMESPACE::MessageRetractionHandle & messageRetractionHandle) const;

      RTI_NAMESPACE::RegionHandle convertToHla(rti1516_202X::fedpro::RegionHandle && fedproRegionHandle) const;

      rti1516_202X::fedpro::RegionHandle * convertFromHla(const RTI_NAMESPACE::RegionHandle & regionHandle) const;

      RTI_NAMESPACE::TransportationTypeHandle convertToHla(rti1516_202X::fedpro::TransportationTypeHandle && fedproTransportHandle) const;

      rti1516_202X::fedpro::TransportationTypeHandle * convertFromHla(const RTI_NAMESPACE::TransportationTypeHandle & transportationTypeHandle) const;

      // Objects
#if (RTI_HLA_VERSION >= 2024)
      RTI_NAMESPACE::RtiConfiguration convertToHla(const rti1516_202X::fedpro::RtiConfiguration & configuration) const;

      rti1516_202X::fedpro::RtiConfiguration * convertFromHla(const RTI_NAMESPACE::RtiConfiguration & configuration) const;

      RTI_NAMESPACE::ConfigurationResult convertToHla(const rti1516_202X::fedpro::ConfigurationResult & decodedReponse) const;

      rti1516_202X::fedpro::Credentials * convertFromHla(const RTI_NAMESPACE::Credentials & credentials) const;
#endif

      RTI_NAMESPACE::FederationExecutionInformation convertToHla(const rti1516_202X::fedpro::FederationExecutionInformation & obj) const;

#if (RTI_HLA_VERSION >= 2024)
      RTI_NAMESPACE::FederationExecutionMemberInformation convertToHla(const rti1516_202X::fedpro::FederationExecutionMemberInformation & obj) const;
#endif

      RTI_NAMESPACE::FederateRestoreStatus convertToHla(rti1516_202X::fedpro::FederateRestoreStatus && obj) const;

      rti1516_202X::fedpro::FomModule * convertFromHla(const FedPro::FomModule & fomModule) const;

      FedPro::JoinResult convertToHla(rti1516_202X::fedpro::JoinResult && joinResult) const;

      RTI_UNIQUE_PTR<RTI_NAMESPACE::LogicalTime> convertToHla(const rti1516_202X::fedpro::LogicalTime & logicalTime) const;

      rti1516_202X::fedpro::LogicalTime * convertFromHla(const RTI_NAMESPACE::LogicalTime & logicalTime) const;

      RTI_UNIQUE_PTR<RTI_NAMESPACE::LogicalTimeInterval> convertToHla(const rti1516_202X::fedpro::LogicalTimeInterval & logicalTimeInterval) const;

      rti1516_202X::fedpro::LogicalTimeInterval * convertFromHla(const RTI_NAMESPACE::LogicalTimeInterval & logicalTimeInterval) const;

      RTI_NAMESPACE::RangeBounds convertToHla(const rti1516_202X::fedpro::RangeBounds & obj) const;

      rti1516_202X::fedpro::RangeBounds * convertFromHla(const RTI_NAMESPACE::RangeBounds & rangeBounds) const;

      std::pair<bool, std::unique_ptr<RTI_NAMESPACE::LogicalTime>> convertToHla(const rti1516_202X::fedpro::TimeQueryReturn & timeQueryReturn) const;

      // Vectors

      /**
       * Destructively convert a FedPro sequence to a std::vector of the corresponding RTI
       * Type.
       * @param fedproSequence Sequence to convert. Conversion may empty this.
       * @throw RTIinternalError if the Message is lacking data.
       */
      template<typename FedProSequenceT>
      auto convertToHlaVector(FedProSequenceT && fedproSequence) const
      {
         using RTIElement = decltype(convertToHla(std::move(std::declval<typename FedProSequenceT::value_type>())));
         using RTIVector = std::vector<RTIElement>;
         RTIVector rtiVector;
         for (auto & element : fedproSequence) {
            auto rtiElement = convertToHla(std::move(element));
            rtiVector.emplace_back(std::move(rtiElement));
         }

         return rtiVector;
      }

      /**
       * Convert a FedPro sequence to a std::vector of the corresponding RTI Type.
       * @param fedproSequence Sequence to convert.
       * @throw RTIinternalError if the Message is lacking data.
       */
      template<class FedProSequenceT>
      auto convertToHlaVector(const FedProSequenceT & fedproSequence) const
      {
         using RTIElement = decltype(convertToHla(std::declval<typename FedProSequenceT::value_type>()));
         using RTIVector = std::vector<RTIElement>;
         RTIVector rtiVector;
         for (const auto & element : fedproSequence) {
            rtiVector.emplace_back(std::move(convertToHla(element)));
         }

         return rtiVector;
      }

      RTI_NAMESPACE::FederateRestoreStatusVector convertToHla(rti1516_202X::fedpro::FederateRestoreStatusArray && federateRestoreStatusArray) const;

      RTI_NAMESPACE::FederationExecutionInformationVector convertToHla(rti1516_202X::fedpro::FederationExecutionInformationSet && federationExecutionInformationSet) const;

#if (RTI_HLA_VERSION >= 2024)
      RTI_NAMESPACE::FederationExecutionMemberInformationVector convertToHla(rti1516_202X::fedpro::FederationExecutionMemberInformationSet && federationExecutionMemberInformationSet) const;
#endif

      rti1516_202X::fedpro::AttributeSetRegionSetPairList * convertFromHla(const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributeHandleSetRegionHandleSetPairVector) const;

      // Maps

      RTI_NAMESPACE::AttributeHandleValueMap convertToHla(rti1516_202X::fedpro::AttributeHandleValueMap && attributeHandleValueMap) const;

      rti1516_202X::fedpro::AttributeHandleValueMap * convertFromHla(const RTI_NAMESPACE::AttributeHandleValueMap & attributeHandleValueMap) const;

      RTI_NAMESPACE::ParameterHandleValueMap convertToHla(rti1516_202X::fedpro::ParameterHandleValueMap && parameterHandleValueMap) const;

      rti1516_202X::fedpro::ParameterHandleValueMap * convertFromHla(const RTI_NAMESPACE::ParameterHandleValueMap & parameterHandleValueMap) const;

      // Sets

      /**
       * Destructively convert a FedPro sequence to a std::set of the corresponding RTI
       * Type.
       * @param fedproSequence Sequence to convert. Conversion may empty this.
       * @throw RTIinternalError if the Message is lacking data.
       */
      template<class FedProSequenceT>
      auto convertToHlaSet(FedProSequenceT && fedproSequence) const
      {
         using RTIElement = decltype(convertToHla(std::move(std::declval<typename FedProSequenceT::value_type>())));
         using RTISet = std::set<RTIElement>;
         RTISet rtiSet;
         for (auto & element : fedproSequence) {
            auto rtiElement = convertToHla(std::move(element));
            rtiSet.emplace(std::move(rtiElement));
         }

         return rtiSet;
      }

      /**
       * Convert a FedPro sequence to a std::set of the corresponding RTI Type.
       * @param fedproSequence Sequence to convert.
       * @throw RTIinternalError if the Message is lacking data.
       */
      template<class FedProSequenceT>
      auto convertToHlaSet(const FedProSequenceT & fedproSequence) const
      {
         using RTIElement = decltype(convertToHla(std::declval<typename FedProSequenceT::value_type>()));
         using RTISet = std::set<RTIElement>;
         RTISet rtiSet;
         for (const auto & element : fedproSequence) {
            rtiSet.emplace(std::move(convertToHla(element)));
         }

         return rtiSet;
      }

      RTI_NAMESPACE::AttributeHandleSet convertToHla(rti1516_202X::fedpro::AttributeHandleSet && attributeHandleSet) const;

      rti1516_202X::fedpro::AttributeHandleSet * convertFromHla(const RTI_NAMESPACE::AttributeHandleSet & attributeHandleSet) const;

      RTI_NAMESPACE::DimensionHandleSet convertToHla(rti1516_202X::fedpro::DimensionHandleSet && fedproHandleSet) const;

      rti1516_202X::fedpro::DimensionHandleSet * convertFromHla(const RTI_NAMESPACE::DimensionHandleSet & dimensionHandleSet) const;

      RTI_NAMESPACE::FederateHandleSaveStatusPairVector convertToHla(rti1516_202X::fedpro::FederateHandleSaveStatusPairArray && federateHandleSaveStatusPairArray) const;

      RTI_NAMESPACE::FederateHandleSet convertToHla(rti1516_202X::fedpro::FederateHandleSet && fedproHandleSet) const;

      rti1516_202X::fedpro::FederateHandleSet * convertFromHla(const RTI_NAMESPACE::FederateHandleSet & federateHandleSet) const;

#if (RTI_HLA_VERSION >= 2024)
      RTI_NAMESPACE::InteractionClassHandleSet convertToHla(rti1516_202X::fedpro::InteractionClassHandleSet && fedproHandleSet) const;

      rti1516_202X::fedpro::InteractionClassHandleSet * convertFromHla(const RTI_NAMESPACE::InteractionClassHandleSet & interactionClassHandle) const;
#endif

      RTI_NAMESPACE::RegionHandleSet * convertToHla(rti1516_202X::fedpro::ConveyedRegionSet && conveyedRegionSet) const;

      rti1516_202X::fedpro::RegionHandleSet * convertFromHla(const RTI_NAMESPACE::RegionHandleSet & regionHandle) const;

      std::set<std::wstring> convertToHla(const google::protobuf::RepeatedPtrField<std::string> & strings) const;

      google::protobuf::RepeatedPtrField<std::string> convertFromHla(const std::set<std::wstring> & strings) const;

      rti1516_202X::fedpro::FomModuleSet * convertFromHla(const FedPro::FomModuleSet & fomModuleSet) const;

      //
      // Response messages
      //

      /**
       * Converts a FedPro response Message to its corresponding HLA type.
       * Note: This method applies to Response with an allocated result (eg Handle).
       *
       * @param fedproResponseMessage Instance to convert. This instance may be emptied
       * during conversion.
       * @throw RTIinternalError if the Message is lacking data.
       */
      template<typename FedProMessageT,
            typename std::enable_if_t<is_response_allocated<FedProMessageT>::value, bool> = true>
      auto convertToHla(FedProMessageT && fedproResponseMessage) const
      {
         auto * fedproResult = fedproResponseMessage.release_result();
         if (!fedproResult) {
            throw RTI_NAMESPACE::RTIinternalError(L"Missing result in Federate Protocol " + convertToHla(typeid(FedProMessageT).name()));
         }
         return convertToHlaAndDelete(fedproResult);
      }

      /**
       * Converts a FedPro response Message to its corresponding HLA type.
       * Note: This method applies to Response with a non-allocated result (eg enum,
       * bool).
       *
       * @param fedproResponseMessage Instance to convert.
       * @throw RTIinternalError if the Message is lacking data.
       */
      template<typename FedProMessageT,
            typename std::enable_if_t<is_response<FedProMessageT>::value, bool> = true,
            typename std::enable_if_t<!is_response_allocated<FedProMessageT>::value, bool> = true>
      auto convertToHla(const FedProMessageT & fedproResponseMessage) const
      {
         return convertToHla(fedproResponseMessage.result());
      }

      //
      // Helpers
      //

      /**
       * Convert a FedPro instance to its corresponding HLA type, and attempt to
       * efficiently move internal data to the HLA object.
       *
       * @throw RTIinternalError if conversion fails
       * @param fedproInstance Instance to convert. This instance may be emptied during
       * conversion.
       */
      template<typename FedProT>
      auto convertToHlaAndMove(FedProT * fedproInstance) const
      {
         using ReturnType = decltype(convertToHla(std::move(*fedproInstance)));
         if (fedproInstance) {
            return convertToHla(std::move(*fedproInstance));
         } else {
            return ReturnType{};
         }
      }

      /**
       * Convert a FedPro instance to its corresponding HLA type, and delete the original
       * FedPro instance.
       *
       * @throw RTIinternalError if fedproInstance is null
       * @param fedproInstance Instance to convert. This instance may be emptied during
       * conversion.
       */
      template<typename FedProT>
      auto convertToHlaAndDelete(FedProT * fedproInstancePointer) const
      {
         // Guarantee object deletion, even if convertToHlaAndMove throws.
         std::unique_ptr<FedProT> fedproInstance(fedproInstancePointer);
         return convertToHlaAndMove(fedproInstance.get());
      }

   private:

      std::shared_ptr<RTI_NAMESPACE::LogicalTimeFactory> _timeFactory;
   };
}
