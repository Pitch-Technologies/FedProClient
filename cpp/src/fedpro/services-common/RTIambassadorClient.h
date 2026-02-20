/*
 *  Copyright (C) 2023-2026 Pitch Technologies AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#pragma once

#include "ClientConverter.h"
#include "Handle.h"
#include <protobuf/generated/RTIambassador.pb.h>
#include "RTIambassadorClientGenericBase.h"
#include "RTIcompat.h"
#include "RTITypedefs.h"
#include "RTIcompat.h"
#include <utility/string_view.h>

#include <RTI/RTI1516.h>

#include <future>
#include <memory>
#include <string>

namespace RTI_NAMESPACE
{
   class RtiConfiguration;
   class Credentials;
} // RTI_NAMESPACE

namespace FedPro
{
   class Properties;

   class RTIambassadorClient : public RTIambassadorClientGenericBase
   {
   public:
      explicit RTIambassadorClient(std::shared_ptr<ClientConverter> clientConverter);

      template<typename Type>
      class GetResponseFunc
      {
      public:

         using DecodeFunction = Type (*)(
               std::future<ByteSequence> &,
               const ClientConverter &);

         GetResponseFunc(
               DecodeFunction decodeFunc,
               std::future<ByteSequence> && responseFuture,
               std::shared_ptr<ClientConverter> clientConverter)
               : _decodeFunc{decodeFunc},
                 _responseFuture{std::move(responseFuture)},
                 _clientConverter{std::move(clientConverter)}
         {
         }

         Type operator()()
         {
            return _decodeFunc(_responseFuture, *_clientConverter);
         }

      private:
         DecodeFunction _decodeFunc;
         std::future<ByteSequence> _responseFuture;
         std::shared_ptr<ClientConverter> _clientConverter;
      };

      // Connect to a Federate Protocol Server.
      // The caller is responsible for parsing clientSettings and rtiConfiguration.
      RTI_NAMESPACE::ConfigurationResult connect(
            const Properties & clientSettings,
            RTI_NAMESPACE::FederateAmbassador & federateAmbassador,
            RTI_NAMESPACE::CallbackModel callbackModel,
            const RTI_NAMESPACE::RtiConfiguration * rtiConfiguration = nullptr,
            const RTI_NAMESPACE::Credentials * credentials = nullptr);

      void disconnect();

      void prefetch();

      // REQUIRES(_connectionStateLock)
      void throwIfAlreadyConnected() const;

      void throwIfInCallback(const char *methodName) const;

      bool evokeCallback(double approximateMinimumTimeInSeconds);

      bool evokeMultipleCallbacks(
            double minimumTime,
            double maximumTime);






      void createFederationExecution(
         std::string const & federationName,
         const FedPro::FomModule & fomModule);

      void createFederationExecutionWithTime(
         std::string const & federationName,
         const FedPro::FomModule & fomModule,
         std::string const & logicalTimeImplementationName);

      void createFederationExecutionWithModules(
         std::string const & federationName,
         const FedPro::FomModuleSet & fomModules);

      void createFederationExecutionWithModulesAndTime(
         std::string const & federationName,
         const FedPro::FomModuleSet & fomModules,
         std::string const & logicalTimeImplementationName);

      void createFederationExecutionWithMIM(
         std::string const & federationName,
         const FedPro::FomModuleSet & fomModules,
         const FedPro::FomModule & mimModule);

      void createFederationExecutionWithMIMAndTime(
         std::string const & federationName,
         const FedPro::FomModuleSet & fomModules,
         const FedPro::FomModule & mimModule,
         std::string const & logicalTimeImplementationName);

      void destroyFederationExecution(
         std::string const & federationName);

      void listFederationExecutions(
);

      GetResponseFunc<void> asyncListFederationExecutions(
);

#if (RTI_HLA_VERSION >= 2025)
      void listFederationExecutionMembers(
         std::string const & federationName);

      GetResponseFunc<void> asyncListFederationExecutionMembers(
         std::string const & federationName);
#endif // RTI_HLA_VERSION

      FedPro::JoinResult joinFederationExecution(
         std::string const & federateType,
         std::string const & federationName);

      FedPro::JoinResult joinFederationExecutionWithModules(
         std::string const & federateType,
         std::string const & federationName,
         const FedPro::FomModuleSet & additionalFomModules);

      FedPro::JoinResult joinFederationExecutionWithName(
         std::string const & federateName,
         std::string const & federateType,
         std::string const & federationName);

      FedPro::JoinResult joinFederationExecutionWithNameAndModules(
         std::string const & federateName,
         std::string const & federateType,
         std::string const & federationName,
         const FedPro::FomModuleSet & additionalFomModules);

      void resignFederationExecution(
         RTI_NAMESPACE::ResignAction resignAction);

      void registerFederationSynchronizationPoint(
         std::string const & synchronizationPointLabel,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncRegisterFederationSynchronizationPoint(
         std::string const & synchronizationPointLabel,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      void registerFederationSynchronizationPoint(
         std::string const & synchronizationPointLabel,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
         const RTI_NAMESPACE::FederateHandleSet & synchronizationSet);

      GetResponseFunc<void> asyncRegisterFederationSynchronizationPoint(
         std::string const & synchronizationPointLabel,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
         const RTI_NAMESPACE::FederateHandleSet & synchronizationSet);

      void synchronizationPointAchieved(
         std::string const & synchronizationPointLabel,
         bool successfully);

      GetResponseFunc<void> asyncSynchronizationPointAchieved(
         std::string const & synchronizationPointLabel,
         bool successfully);

      void requestFederationSave(
         std::string const & label);

      GetResponseFunc<void> asyncRequestFederationSave(
         std::string const & label);

      void requestFederationSave(
         std::string const & label,
         const RTI_NAMESPACE::LogicalTime & time);

      GetResponseFunc<void> asyncRequestFederationSave(
         std::string const & label,
         const RTI_NAMESPACE::LogicalTime & time);

      void federateSaveBegun(
);

      GetResponseFunc<void> asyncFederateSaveBegun(
);

      void federateSaveComplete(
);

      GetResponseFunc<void> asyncFederateSaveComplete(
);

      void federateSaveNotComplete(
);

      GetResponseFunc<void> asyncFederateSaveNotComplete(
);

      void abortFederationSave(
);

      GetResponseFunc<void> asyncAbortFederationSave(
);

      void queryFederationSaveStatus(
);

      GetResponseFunc<void> asyncQueryFederationSaveStatus(
);

      void requestFederationRestore(
         std::string const & label);

      GetResponseFunc<void> asyncRequestFederationRestore(
         std::string const & label);

      void federateRestoreComplete(
);

      GetResponseFunc<void> asyncFederateRestoreComplete(
);

      void federateRestoreNotComplete(
);

      GetResponseFunc<void> asyncFederateRestoreNotComplete(
);

      void abortFederationRestore(
);

      GetResponseFunc<void> asyncAbortFederationRestore(
);

      void queryFederationRestoreStatus(
);

      GetResponseFunc<void> asyncQueryFederationRestoreStatus(
);

      void publishObjectClassAttributes(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      GetResponseFunc<void> asyncPublishObjectClassAttributes(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      void unpublishObjectClass(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);

      GetResponseFunc<void> asyncUnpublishObjectClass(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);

      void unpublishObjectClassAttributes(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      GetResponseFunc<void> asyncUnpublishObjectClassAttributes(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      void publishInteractionClass(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      GetResponseFunc<void> asyncPublishInteractionClass(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      void unpublishInteractionClass(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      GetResponseFunc<void> asyncUnpublishInteractionClass(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

#if (RTI_HLA_VERSION >= 2025)
      void publishObjectClassDirectedInteractions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses);

      GetResponseFunc<void> asyncPublishObjectClassDirectedInteractions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses);
#endif // RTI_HLA_VERSION

#if (RTI_HLA_VERSION >= 2025)
      void unpublishObjectClassDirectedInteractions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);

      GetResponseFunc<void> asyncUnpublishObjectClassDirectedInteractions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);
#endif // RTI_HLA_VERSION

#if (RTI_HLA_VERSION >= 2025)
      void unpublishObjectClassDirectedInteractions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses);

      GetResponseFunc<void> asyncUnpublishObjectClassDirectedInteractions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses);
#endif // RTI_HLA_VERSION

      void subscribeObjectClassAttributes(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      GetResponseFunc<void> asyncSubscribeObjectClassAttributes(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      void subscribeObjectClassAttributes(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         std::string const & updateRateDesignator);

      GetResponseFunc<void> asyncSubscribeObjectClassAttributes(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         std::string const & updateRateDesignator);

      void subscribeObjectClassAttributesPassively(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      GetResponseFunc<void> asyncSubscribeObjectClassAttributesPassively(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      void subscribeObjectClassAttributesPassively(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         std::string const & updateRateDesignator);

      GetResponseFunc<void> asyncSubscribeObjectClassAttributesPassively(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         std::string const & updateRateDesignator);

      void unsubscribeObjectClass(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);

      GetResponseFunc<void> asyncUnsubscribeObjectClass(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);

      void unsubscribeObjectClassAttributes(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      GetResponseFunc<void> asyncUnsubscribeObjectClassAttributes(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      void subscribeInteractionClass(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      GetResponseFunc<void> asyncSubscribeInteractionClass(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      void subscribeInteractionClassPassively(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      GetResponseFunc<void> asyncSubscribeInteractionClassPassively(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      void unsubscribeInteractionClass(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      GetResponseFunc<void> asyncUnsubscribeInteractionClass(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

#if (RTI_HLA_VERSION >= 2025)
      void subscribeObjectClassDirectedInteractions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses);

      GetResponseFunc<void> asyncSubscribeObjectClassDirectedInteractions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses);
#endif // RTI_HLA_VERSION

#if (RTI_HLA_VERSION >= 2025)
      void subscribeObjectClassDirectedInteractionsUniversally(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses);

      GetResponseFunc<void> asyncSubscribeObjectClassDirectedInteractionsUniversally(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses);
#endif // RTI_HLA_VERSION

#if (RTI_HLA_VERSION >= 2025)
      void unsubscribeObjectClassDirectedInteractions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);

      GetResponseFunc<void> asyncUnsubscribeObjectClassDirectedInteractions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);
#endif // RTI_HLA_VERSION

#if (RTI_HLA_VERSION >= 2025)
      void unsubscribeObjectClassDirectedInteractions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses);

      GetResponseFunc<void> asyncUnsubscribeObjectClassDirectedInteractions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses);
#endif // RTI_HLA_VERSION

      void reserveObjectInstanceName(
         std::string const & objectInstanceName);

      GetResponseFunc<void> asyncReserveObjectInstanceName(
         std::string const & objectInstanceName);

      void releaseObjectInstanceName(
         std::string const & objectInstanceName);

      GetResponseFunc<void> asyncReleaseObjectInstanceName(
         std::string const & objectInstanceName);

      void reserveMultipleObjectInstanceNames(
         std::set<std::wstring> const & objectInstanceNames);

      GetResponseFunc<void> asyncReserveMultipleObjectInstanceNames(
         std::set<std::wstring> const & objectInstanceNames);

      void releaseMultipleObjectInstanceNames(
         std::set<std::wstring> const & objectInstanceNames);

      GetResponseFunc<void> asyncReleaseMultipleObjectInstanceNames(
         std::set<std::wstring> const & objectInstanceNames);

      RTI_NAMESPACE::ObjectInstanceHandle registerObjectInstance(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);

      GetResponseFunc<RTI_NAMESPACE::ObjectInstanceHandle> asyncRegisterObjectInstance(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);

      RTI_NAMESPACE::ObjectInstanceHandle registerObjectInstanceWithName(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         std::string const & objectInstanceName);

      GetResponseFunc<RTI_NAMESPACE::ObjectInstanceHandle> asyncRegisterObjectInstanceWithName(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         std::string const & objectInstanceName);

      void updateAttributeValues(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleValueMap & attributeValues,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncUpdateAttributeValues(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleValueMap & attributeValues,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      RTI_NAMESPACE::MessageRetractionHandle updateAttributeValues(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleValueMap & attributeValues,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
         const RTI_NAMESPACE::LogicalTime & time);

      GetResponseFunc<RTI_NAMESPACE::MessageRetractionHandle> asyncUpdateAttributeValues(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleValueMap & attributeValues,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
         const RTI_NAMESPACE::LogicalTime & time);

      void sendInteraction(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncSendInteraction(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      RTI_NAMESPACE::MessageRetractionHandle sendInteraction(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
         const RTI_NAMESPACE::LogicalTime & time);

      GetResponseFunc<RTI_NAMESPACE::MessageRetractionHandle> asyncSendInteraction(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
         const RTI_NAMESPACE::LogicalTime & time);

      void sendDirectedInteraction(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncSendDirectedInteraction(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      RTI_NAMESPACE::MessageRetractionHandle sendDirectedInteraction(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
         const RTI_NAMESPACE::LogicalTime & time);

      GetResponseFunc<RTI_NAMESPACE::MessageRetractionHandle> asyncSendDirectedInteraction(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
         const RTI_NAMESPACE::LogicalTime & time);

      void deleteObjectInstance(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncDeleteObjectInstance(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      RTI_NAMESPACE::MessageRetractionHandle deleteObjectInstance(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
         const RTI_NAMESPACE::LogicalTime & time);

      GetResponseFunc<RTI_NAMESPACE::MessageRetractionHandle> asyncDeleteObjectInstance(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
         const RTI_NAMESPACE::LogicalTime & time);

      void localDeleteObjectInstance(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance);

      GetResponseFunc<void> asyncLocalDeleteObjectInstance(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance);

      void requestAttributeValueUpdate(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncRequestAttributeValueUpdate(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      void requestAttributeValueUpdate(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncRequestAttributeValueUpdate(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      void requestAttributeTransportationTypeChange(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::TransportationTypeHandle & transportationType);

      GetResponseFunc<void> asyncRequestAttributeTransportationTypeChange(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::TransportationTypeHandle & transportationType);

      void changeDefaultAttributeTransportationType(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::TransportationTypeHandle & transportationType);

      GetResponseFunc<void> asyncChangeDefaultAttributeTransportationType(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::TransportationTypeHandle & transportationType);

      void queryAttributeTransportationType(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandle & attribute);

      GetResponseFunc<void> asyncQueryAttributeTransportationType(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandle & attribute);

      void requestInteractionTransportationTypeChange(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::TransportationTypeHandle & transportationType);

      GetResponseFunc<void> asyncRequestInteractionTransportationTypeChange(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::TransportationTypeHandle & transportationType);

      void queryInteractionTransportationType(
         const RTI_NAMESPACE::FederateHandle & federate,
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      GetResponseFunc<void> asyncQueryInteractionTransportationType(
         const RTI_NAMESPACE::FederateHandle & federate,
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      void unconditionalAttributeOwnershipDivestiture(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncUnconditionalAttributeOwnershipDivestiture(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      void negotiatedAttributeOwnershipDivestiture(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncNegotiatedAttributeOwnershipDivestiture(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      void confirmDivestiture(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & confirmedAttributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncConfirmDivestiture(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & confirmedAttributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      void attributeOwnershipAcquisition(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & desiredAttributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncAttributeOwnershipAcquisition(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & desiredAttributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      void attributeOwnershipAcquisitionIfAvailable(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & desiredAttributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncAttributeOwnershipAcquisitionIfAvailable(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & desiredAttributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      void attributeOwnershipReleaseDenied(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncAttributeOwnershipReleaseDenied(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      RTI_NAMESPACE::AttributeHandleSet attributeOwnershipDivestitureIfWanted(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<RTI_NAMESPACE::AttributeHandleSet> asyncAttributeOwnershipDivestitureIfWanted(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      void cancelNegotiatedAttributeOwnershipDivestiture(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      GetResponseFunc<void> asyncCancelNegotiatedAttributeOwnershipDivestiture(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      void cancelAttributeOwnershipAcquisition(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      GetResponseFunc<void> asyncCancelAttributeOwnershipAcquisition(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      void queryAttributeOwnership(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      GetResponseFunc<void> asyncQueryAttributeOwnership(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes);

      bool isAttributeOwnedByFederate(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandle & attribute);

      GetResponseFunc<bool> asyncIsAttributeOwnedByFederate(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandle & attribute);

      void enableTimeRegulation(
         const RTI_NAMESPACE::LogicalTimeInterval & lookahead);

      GetResponseFunc<void> asyncEnableTimeRegulation(
         const RTI_NAMESPACE::LogicalTimeInterval & lookahead);

      void disableTimeRegulation(
);

      GetResponseFunc<void> asyncDisableTimeRegulation(
);

      void enableTimeConstrained(
);

      GetResponseFunc<void> asyncEnableTimeConstrained(
);

      void disableTimeConstrained(
);

      GetResponseFunc<void> asyncDisableTimeConstrained(
);

      void timeAdvanceRequest(
         const RTI_NAMESPACE::LogicalTime & time);

      GetResponseFunc<void> asyncTimeAdvanceRequest(
         const RTI_NAMESPACE::LogicalTime & time);

      void timeAdvanceRequestAvailable(
         const RTI_NAMESPACE::LogicalTime & time);

      GetResponseFunc<void> asyncTimeAdvanceRequestAvailable(
         const RTI_NAMESPACE::LogicalTime & time);

      void nextMessageRequest(
         const RTI_NAMESPACE::LogicalTime & time);

      GetResponseFunc<void> asyncNextMessageRequest(
         const RTI_NAMESPACE::LogicalTime & time);

      void nextMessageRequestAvailable(
         const RTI_NAMESPACE::LogicalTime & time);

      GetResponseFunc<void> asyncNextMessageRequestAvailable(
         const RTI_NAMESPACE::LogicalTime & time);

      void flushQueueRequest(
         const RTI_NAMESPACE::LogicalTime & time);

      GetResponseFunc<void> asyncFlushQueueRequest(
         const RTI_NAMESPACE::LogicalTime & time);

      void enableAsynchronousDelivery(
);

      GetResponseFunc<void> asyncEnableAsynchronousDelivery(
);

      void disableAsynchronousDelivery(
);

      GetResponseFunc<void> asyncDisableAsynchronousDelivery(
);

      std::pair< bool, std::unique_ptr<RTI_NAMESPACE::LogicalTime> > queryGALT(
);

      GetResponseFunc<std::pair< bool, std::unique_ptr<RTI_NAMESPACE::LogicalTime> >> asyncQueryGALT(
);

      RTI_UNIQUE_PTR<RTI_NAMESPACE::LogicalTime> queryLogicalTime(
);

      GetResponseFunc<RTI_UNIQUE_PTR<RTI_NAMESPACE::LogicalTime>> asyncQueryLogicalTime(
);

      std::pair< bool, std::unique_ptr<RTI_NAMESPACE::LogicalTime> > queryLITS(
);

      GetResponseFunc<std::pair< bool, std::unique_ptr<RTI_NAMESPACE::LogicalTime> >> asyncQueryLITS(
);

      void modifyLookahead(
         const RTI_NAMESPACE::LogicalTimeInterval & lookahead);

      GetResponseFunc<void> asyncModifyLookahead(
         const RTI_NAMESPACE::LogicalTimeInterval & lookahead);

      RTI_UNIQUE_PTR<RTI_NAMESPACE::LogicalTimeInterval> queryLookahead(
);

      GetResponseFunc<RTI_UNIQUE_PTR<RTI_NAMESPACE::LogicalTimeInterval>> asyncQueryLookahead(
);

      void retract(
         const RTI_NAMESPACE::MessageRetractionHandle & retraction);

      GetResponseFunc<void> asyncRetract(
         const RTI_NAMESPACE::MessageRetractionHandle & retraction);

      void changeAttributeOrderType(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         RTI_NAMESPACE::OrderType orderType);

      GetResponseFunc<void> asyncChangeAttributeOrderType(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         RTI_NAMESPACE::OrderType orderType);

      void changeDefaultAttributeOrderType(
         const RTI_NAMESPACE::ObjectClassHandle & theObjectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         RTI_NAMESPACE::OrderType orderType);

      GetResponseFunc<void> asyncChangeDefaultAttributeOrderType(
         const RTI_NAMESPACE::ObjectClassHandle & theObjectClass,
         const RTI_NAMESPACE::AttributeHandleSet & attributes,
         RTI_NAMESPACE::OrderType orderType);

      void changeInteractionOrderType(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         RTI_NAMESPACE::OrderType orderType);

      GetResponseFunc<void> asyncChangeInteractionOrderType(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         RTI_NAMESPACE::OrderType orderType);

      RTI_NAMESPACE::RegionHandle createRegion(
         const RTI_NAMESPACE::DimensionHandleSet & dimensions);

      GetResponseFunc<RTI_NAMESPACE::RegionHandle> asyncCreateRegion(
         const RTI_NAMESPACE::DimensionHandleSet & dimensions);

      void commitRegionModifications(
         const RTI_NAMESPACE::RegionHandleSet & regions);

      GetResponseFunc<void> asyncCommitRegionModifications(
         const RTI_NAMESPACE::RegionHandleSet & regions);

      void deleteRegion(
         const RTI_NAMESPACE::RegionHandle & region);

      GetResponseFunc<void> asyncDeleteRegion(
         const RTI_NAMESPACE::RegionHandle & region);

      RTI_NAMESPACE::ObjectInstanceHandle registerObjectInstanceWithRegions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions);

      GetResponseFunc<RTI_NAMESPACE::ObjectInstanceHandle> asyncRegisterObjectInstanceWithRegions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions);

      RTI_NAMESPACE::ObjectInstanceHandle registerObjectInstanceWithRegions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
         std::string const & objectInstanceName);

      GetResponseFunc<RTI_NAMESPACE::ObjectInstanceHandle> asyncRegisterObjectInstanceWithRegions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
         std::string const & objectInstanceName);

      void associateRegionsForUpdates(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions);

      GetResponseFunc<void> asyncAssociateRegionsForUpdates(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions);

      void unassociateRegionsForUpdates(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions);

      GetResponseFunc<void> asyncUnassociateRegionsForUpdates(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions);

      void subscribeObjectClassAttributesWithRegions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
         bool active);

      GetResponseFunc<void> asyncSubscribeObjectClassAttributesWithRegions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
         bool active);

      void subscribeObjectClassAttributesWithRegions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
         bool active,
         std::string const & updateRateDesignator);

      GetResponseFunc<void> asyncSubscribeObjectClassAttributesWithRegions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
         bool active,
         std::string const & updateRateDesignator);

      void unsubscribeObjectClassAttributesWithRegions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions);

      GetResponseFunc<void> asyncUnsubscribeObjectClassAttributesWithRegions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions);

      void subscribeInteractionClassWithRegions(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         bool active,
         const RTI_NAMESPACE::RegionHandleSet & regions);

      GetResponseFunc<void> asyncSubscribeInteractionClassWithRegions(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         bool active,
         const RTI_NAMESPACE::RegionHandleSet & regions);

      void unsubscribeInteractionClassWithRegions(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::RegionHandleSet & regions);

      GetResponseFunc<void> asyncUnsubscribeInteractionClassWithRegions(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::RegionHandleSet & regions);

      void sendInteractionWithRegions(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
         const RTI_NAMESPACE::RegionHandleSet & regions,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncSendInteractionWithRegions(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
         const RTI_NAMESPACE::RegionHandleSet & regions,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      RTI_NAMESPACE::MessageRetractionHandle sendInteractionWithRegions(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
         const RTI_NAMESPACE::RegionHandleSet & regions,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
         const RTI_NAMESPACE::LogicalTime & time);

      GetResponseFunc<RTI_NAMESPACE::MessageRetractionHandle> asyncSendInteractionWithRegions(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
         const RTI_NAMESPACE::RegionHandleSet & regions,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
         const RTI_NAMESPACE::LogicalTime & time);

      void requestAttributeValueUpdateWithRegions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      GetResponseFunc<void> asyncRequestAttributeValueUpdateWithRegions(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
         const RTI_NAMESPACE::VariableLengthData & userSuppliedTag);

      RTI_NAMESPACE::FederateHandle getFederateHandle(
         std::string const & federateName);

      GetResponseFunc<RTI_NAMESPACE::FederateHandle> asyncGetFederateHandle(
         std::string const & federateName);

      std::wstring getFederateName(
         const RTI_NAMESPACE::FederateHandle & federate);

      GetResponseFunc<std::wstring> asyncGetFederateName(
         const RTI_NAMESPACE::FederateHandle & federate);

      RTI_NAMESPACE::ObjectClassHandle getObjectClassHandle(
         std::string const & objectClassName);

      GetResponseFunc<RTI_NAMESPACE::ObjectClassHandle> asyncGetObjectClassHandle(
         std::string const & objectClassName);

      std::wstring getObjectClassName(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);

      GetResponseFunc<std::wstring> asyncGetObjectClassName(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);

      RTI_NAMESPACE::ObjectClassHandle getKnownObjectClassHandle(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance);

      GetResponseFunc<RTI_NAMESPACE::ObjectClassHandle> asyncGetKnownObjectClassHandle(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance);

      RTI_NAMESPACE::ObjectInstanceHandle getObjectInstanceHandle(
         std::string const & objectInstanceName);

      GetResponseFunc<RTI_NAMESPACE::ObjectInstanceHandle> asyncGetObjectInstanceHandle(
         std::string const & objectInstanceName);

      std::wstring getObjectInstanceName(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance);

      GetResponseFunc<std::wstring> asyncGetObjectInstanceName(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance);

      RTI_NAMESPACE::AttributeHandle getAttributeHandle(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         std::string const & attributeName);

      GetResponseFunc<RTI_NAMESPACE::AttributeHandle> asyncGetAttributeHandle(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         std::string const & attributeName);

      std::wstring getAttributeName(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandle & attribute);

      GetResponseFunc<std::wstring> asyncGetAttributeName(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass,
         const RTI_NAMESPACE::AttributeHandle & attribute);

      double getUpdateRateValue(
         std::string const & updateRateDesignator);

      GetResponseFunc<double> asyncGetUpdateRateValue(
         std::string const & updateRateDesignator);

      double getUpdateRateValueForAttribute(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandle & attribute);

      GetResponseFunc<double> asyncGetUpdateRateValueForAttribute(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
         const RTI_NAMESPACE::AttributeHandle & attribute);

      RTI_NAMESPACE::InteractionClassHandle getInteractionClassHandle(
         std::string const & interactionClassName);

      GetResponseFunc<RTI_NAMESPACE::InteractionClassHandle> asyncGetInteractionClassHandle(
         std::string const & interactionClassName);

      std::wstring getInteractionClassName(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      GetResponseFunc<std::wstring> asyncGetInteractionClassName(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      RTI_NAMESPACE::ParameterHandle getParameterHandle(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         std::string const & parameterName);

      GetResponseFunc<RTI_NAMESPACE::ParameterHandle> asyncGetParameterHandle(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         std::string const & parameterName);

      std::wstring getParameterName(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::ParameterHandle & parameter);

      GetResponseFunc<std::wstring> asyncGetParameterName(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
         const RTI_NAMESPACE::ParameterHandle & parameter);

      RTI_NAMESPACE::OrderType getOrderType(
         std::string const & orderTypeName);

      GetResponseFunc<RTI_NAMESPACE::OrderType> asyncGetOrderType(
         std::string const & orderTypeName);

      std::wstring getOrderName(
         RTI_NAMESPACE::OrderType orderType);

      GetResponseFunc<std::wstring> asyncGetOrderName(
         RTI_NAMESPACE::OrderType orderType);

      RTI_NAMESPACE::TransportationTypeHandle getTransportationTypeHandle(
         std::string const & transportationTypeName);

      GetResponseFunc<RTI_NAMESPACE::TransportationTypeHandle> asyncGetTransportationTypeHandle(
         std::string const & transportationTypeName);

      std::wstring getTransportationTypeName(
         const RTI_NAMESPACE::TransportationTypeHandle & transportationType);

      GetResponseFunc<std::wstring> asyncGetTransportationTypeName(
         const RTI_NAMESPACE::TransportationTypeHandle & transportationType);

      RTI_NAMESPACE::DimensionHandleSet getAvailableDimensionsForObjectClass(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);

      GetResponseFunc<RTI_NAMESPACE::DimensionHandleSet> asyncGetAvailableDimensionsForObjectClass(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);

      RTI_NAMESPACE::DimensionHandleSet getAvailableDimensionsForInteractionClass(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      GetResponseFunc<RTI_NAMESPACE::DimensionHandleSet> asyncGetAvailableDimensionsForInteractionClass(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      RTI_NAMESPACE::DimensionHandle getDimensionHandle(
         std::string const & dimensionName);

      GetResponseFunc<RTI_NAMESPACE::DimensionHandle> asyncGetDimensionHandle(
         std::string const & dimensionName);

      std::wstring getDimensionName(
         const RTI_NAMESPACE::DimensionHandle & dimension);

      GetResponseFunc<std::wstring> asyncGetDimensionName(
         const RTI_NAMESPACE::DimensionHandle & dimension);

      unsigned long getDimensionUpperBound(
         const RTI_NAMESPACE::DimensionHandle & dimension);

      GetResponseFunc<unsigned long> asyncGetDimensionUpperBound(
         const RTI_NAMESPACE::DimensionHandle & dimension);

      RTI_NAMESPACE::DimensionHandleSet getDimensionHandleSet(
         const RTI_NAMESPACE::RegionHandle & region);

      GetResponseFunc<RTI_NAMESPACE::DimensionHandleSet> asyncGetDimensionHandleSet(
         const RTI_NAMESPACE::RegionHandle & region);

      RTI_NAMESPACE::RangeBounds getRangeBounds(
         const RTI_NAMESPACE::RegionHandle & region,
         const RTI_NAMESPACE::DimensionHandle & dimension);

      GetResponseFunc<RTI_NAMESPACE::RangeBounds> asyncGetRangeBounds(
         const RTI_NAMESPACE::RegionHandle & region,
         const RTI_NAMESPACE::DimensionHandle & dimension);

      void setRangeBounds(
         const RTI_NAMESPACE::RegionHandle & region,
         const RTI_NAMESPACE::DimensionHandle & dimension,
         const RTI_NAMESPACE::RangeBounds & rangeBounds);

      GetResponseFunc<void> asyncSetRangeBounds(
         const RTI_NAMESPACE::RegionHandle & region,
         const RTI_NAMESPACE::DimensionHandle & dimension,
         const RTI_NAMESPACE::RangeBounds & rangeBounds);

      unsigned long normalizeServiceGroup(
         RTI_NAMESPACE::ServiceGroup serviceGroup);

      GetResponseFunc<unsigned long> asyncNormalizeServiceGroup(
         RTI_NAMESPACE::ServiceGroup serviceGroup);

      unsigned long normalizeFederateHandle(
         const RTI_NAMESPACE::FederateHandle & federate);

      GetResponseFunc<unsigned long> asyncNormalizeFederateHandle(
         const RTI_NAMESPACE::FederateHandle & federate);

      unsigned long normalizeObjectClassHandle(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);

      GetResponseFunc<unsigned long> asyncNormalizeObjectClassHandle(
         const RTI_NAMESPACE::ObjectClassHandle & objectClass);

      unsigned long normalizeInteractionClassHandle(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      GetResponseFunc<unsigned long> asyncNormalizeInteractionClassHandle(
         const RTI_NAMESPACE::InteractionClassHandle & interactionClass);

      unsigned long normalizeObjectInstanceHandle(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance);

      GetResponseFunc<unsigned long> asyncNormalizeObjectInstanceHandle(
         const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance);

      bool getObjectClassRelevanceAdvisorySwitch(
);

      GetResponseFunc<bool> asyncGetObjectClassRelevanceAdvisorySwitch(
);

      void setObjectClassRelevanceAdvisorySwitch(
         bool value);

      GetResponseFunc<void> asyncSetObjectClassRelevanceAdvisorySwitch(
         bool value);

      bool getAttributeRelevanceAdvisorySwitch(
);

      GetResponseFunc<bool> asyncGetAttributeRelevanceAdvisorySwitch(
);

      void setAttributeRelevanceAdvisorySwitch(
         bool value);

      GetResponseFunc<void> asyncSetAttributeRelevanceAdvisorySwitch(
         bool value);

      bool getAttributeScopeAdvisorySwitch(
);

      GetResponseFunc<bool> asyncGetAttributeScopeAdvisorySwitch(
);

      void setAttributeScopeAdvisorySwitch(
         bool value);

      GetResponseFunc<void> asyncSetAttributeScopeAdvisorySwitch(
         bool value);

      bool getInteractionRelevanceAdvisorySwitch(
);

      GetResponseFunc<bool> asyncGetInteractionRelevanceAdvisorySwitch(
);

      void setInteractionRelevanceAdvisorySwitch(
         bool value);

      GetResponseFunc<void> asyncSetInteractionRelevanceAdvisorySwitch(
         bool value);

      bool getConveyRegionDesignatorSetsSwitch(
);

      GetResponseFunc<bool> asyncGetConveyRegionDesignatorSetsSwitch(
);

      void setConveyRegionDesignatorSetsSwitch(
         bool value);

      GetResponseFunc<void> asyncSetConveyRegionDesignatorSetsSwitch(
         bool value);

      RTI_NAMESPACE::ResignAction getAutomaticResignDirective(
);

      GetResponseFunc<RTI_NAMESPACE::ResignAction> asyncGetAutomaticResignDirective(
);

      void setAutomaticResignDirective(
         RTI_NAMESPACE::ResignAction value);

      GetResponseFunc<void> asyncSetAutomaticResignDirective(
         RTI_NAMESPACE::ResignAction value);

      bool getServiceReportingSwitch(
);

      GetResponseFunc<bool> asyncGetServiceReportingSwitch(
);

      void setServiceReportingSwitch(
         bool value);

      GetResponseFunc<void> asyncSetServiceReportingSwitch(
         bool value);

      bool getExceptionReportingSwitch(
);

      GetResponseFunc<bool> asyncGetExceptionReportingSwitch(
);

      void setExceptionReportingSwitch(
         bool value);

      GetResponseFunc<void> asyncSetExceptionReportingSwitch(
         bool value);

      bool getSendServiceReportsToFileSwitch(
);

      GetResponseFunc<bool> asyncGetSendServiceReportsToFileSwitch(
);

      void setSendServiceReportsToFileSwitch(
         bool value);

      GetResponseFunc<void> asyncSetSendServiceReportsToFileSwitch(
         bool value);

      bool getAutoProvideSwitch(
);

      GetResponseFunc<bool> asyncGetAutoProvideSwitch(
);

      bool getDelaySubscriptionEvaluationSwitch(
);

      GetResponseFunc<bool> asyncGetDelaySubscriptionEvaluationSwitch(
);

      bool getAdvisoriesUseKnownClassSwitch(
);

      GetResponseFunc<bool> asyncGetAdvisoriesUseKnownClassSwitch(
);

      bool getAllowRelaxedDDMSwitch(
);

      GetResponseFunc<bool> asyncGetAllowRelaxedDDMSwitch(
);

      bool getNonRegulatedGrantSwitch(
);

      GetResponseFunc<bool> asyncGetNonRegulatedGrantSwitch(
);



   };

}
