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

#include <services-common/FomModuleLoader.h>
#include <services-common/RTIambassadorClient.h>

#include <RTI/RTIambassador.h>

#include <memory>

namespace RTI_NAMESPACE
{
   class FederateAmbassador;

   class RTIambassadorClientAdapter : public RTIambassador
   {
   public:

      RTIambassadorClientAdapter();

      ~RTIambassadorClientAdapter() override;

      /**
       * 4.2
       * @throws Unauthorized
       * @throws ConnectionFailed
       * @throws UnsupportedCallbackModel
       * @throws AlreadyConnected
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      ConfigurationResult connect (
            FederateAmbassador & federateAmbassador,
            CallbackModel callbackModel) override;

      /**
       * 4.2
       * @throws Unauthorized
       * @throws ConnectionFailed
       * @throws UnsupportedCallbackModel
       * @throws AlreadyConnected
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      ConfigurationResult connect (
            FederateAmbassador & federateAmbassador,
            CallbackModel callbackModel,
            const RtiConfiguration & configuration) override;

      /**
       * 4.2
       * @throws Unauthorized
       * @throws InvalidCredentials
       * @throws ConnectionFailed
       * @throws UnsupportedCallbackModel
       * @throws AlreadyConnected
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      ConfigurationResult connect (
            FederateAmbassador & federateAmbassador,
            CallbackModel callbackModel,
            Credentials const & credentials) override;

      /**
       * 4.2
       * @throws Unauthorized
       * @throws InvalidCredentials
       * @throws ConnectionFailed
       * @throws UnsupportedCallbackModel
       * @throws AlreadyConnected
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      ConfigurationResult connect (
            FederateAmbassador & federateAmbassador,
            CallbackModel callbackModel,
            const RtiConfiguration & configuration,
            Credentials const & credentials) override;

      /**
       * 4.3
       * @throws FederateIsExecutionMember
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      void disconnect () override;

      /**
       * 4.5
       * @throws CouldNotCreateLogicalTimeFactory
       * @throws InconsistentFOM
       * @throws InvalidFOM
       * @throws ErrorReadingFOM
       * @throws CouldNotOpenFOM
       * @throws InvalidMIM
       * @throws ErrorReadingMIM
       * @throws CouldNotOpenMIM
       * @throws DesignatorIsHLAstandardMIM
       * @throws FederationExecutionAlreadyExists
       * @throws Unauthorized
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void createFederationExecution (
            std::wstring const & federationName,
            std::wstring const & fomModule,
            std::wstring const & logicalTimeImplementationName) override;

      /**
       * 4.5
       * @throws CouldNotCreateLogicalTimeFactory
       * @throws InconsistentFOM
       * @throws InvalidFOM
       * @throws ErrorReadingFOM
       * @throws CouldNotOpenFOM
       * @throws FederationExecutionAlreadyExists
       * @throws Unauthorized
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void createFederationExecution (
            std::wstring const & federationName,
            std::vector<std::wstring> const & fomModules,
            std::wstring const & logicalTimeImplementationName) override;

      /**
       * 4.5
       * @throws InconsistentFOM
       * @throws InvalidFOM
       * @throws ErrorReadingFOM
       * @throws CouldNotOpenFOM
       * @throws InvalidMIM
       * @throws ErrorReadingMIM
       * @throws CouldNotOpenMIM
       * @throws DesignatorIsHLAstandardMIM
       * @throws FederationExecutionAlreadyExists
       * @throws Unauthorized
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void createFederationExecutionWithMIM (
            std::wstring const & federationName,
            std::vector<std::wstring> const & fomModules,
            std::wstring const & mimModule,
            std::wstring const & logicalTimeImplementationName) override;

      /**
       * 4.6
       * @throws FederatesCurrentlyJoined
       * @throws FederationExecutionDoesNotExist
       * @throws Unauthorized
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void destroyFederationExecution (
            std::wstring const & federationName) override;

      /**
       * 4.7
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void listFederationExecutions () override;

      /**
       * 4.9
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void listFederationExecutionMembers (std::wstring const & federationName) override;

      /**
       * 4.11
       * @throws CouldNotCreateLogicalTimeFactory
       * @throws FederateNameAlreadyInUse
       * @throws FederationExecutionDoesNotExist
       * @throws InconsistentFOM
       * @throws InvalidFOM
       * @throws ErrorReadingFOM
       * @throws CouldNotOpenFOM
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateAlreadyExecutionMember
       * @throws Unauthorized
       * @throws NotConnected
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      FederateHandle joinFederationExecution (
            std::wstring const & federateType,
            std::wstring const & federationName,
            std::vector<std::wstring> const & additionalFomModules) override;

      /**
       * 4.11
       * @throws CouldNotCreateLogicalTimeFactory
       * @throws FederationExecutionDoesNotExist
       * @throws InconsistentFOM
       * @throws InvalidFOM
       * @throws ErrorReadingFOM
       * @throws CouldNotOpenFOM
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateAlreadyExecutionMember
       * @throws Unauthorized
       * @throws NotConnected
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      FederateHandle joinFederationExecution (
            std::wstring const & federateName,
            std::wstring const & federateType,
            std::wstring const & federationName,
            std::vector<std::wstring> const & additionalFomModules) override;

      /**
       * 4.12
       * @throws InvalidResignAction
       * @throws OwnershipAcquisitionPending
       * @throws FederateOwnsAttributes
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      void resignFederationExecution (
            ResignAction resignAction) override;

      /**
       * 4.14
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void registerFederationSynchronizationPoint (
            std::wstring const & synchronizationPointLabel,
            const VariableLengthData & userSuppliedTag) override;

      /**
       * 4.14
       * @throws InvalidFederateHandle
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void registerFederationSynchronizationPoint (
            std::wstring const & synchronizationPointLabel,
            const VariableLengthData & userSuppliedTag,
            FederateHandleSet const & synchronizationSet) override;

      /**
       * 4.17
       * @throws SynchronizationPointLabelNotAnnounced
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void synchronizationPointAchieved (
            std::wstring const & synchronizationPointLabel,
            bool successfully) override;

      /**
       * 4.19
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void requestFederationSave (
            std::wstring const & label) override;

      /**
       * 4.19
       * @throws LogicalTimeAlreadyPassed
       * @throws InvalidLogicalTime
       * @throws FederateUnableToUseTime
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void requestFederationSave (
            std::wstring const & label,
            const LogicalTime & time) override;

      /**
       * 4.21
       * @throws SaveNotInitiated
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void federateSaveBegun () override;

      /**
       * 4.22
       * @throws FederateHasNotBegunSave
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void federateSaveComplete () override;

      /**
       * 4.22
       * @throws FederateHasNotBegunSave
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void federateSaveNotComplete () override;

      /**
       * 4.24
       * @throws SaveNotInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void abortFederationSave () override;

      /**
       * 4.25
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void queryFederationSaveStatus () override;

      /**
       * 4.27
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void requestFederationRestore (
            std::wstring const & label) override;

      /**
       * 4.31
       * @throws RestoreNotRequested
       * @throws SaveInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void federateRestoreComplete () override;

      /**
       * 4.31
       * @throws RestoreNotRequested
       * @throws SaveInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void federateRestoreNotComplete () override;

      /**
       * 4.33
       * @throws RestoreNotInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void abortFederationRestore () override;

      /**
       * 4.34
       * @throws SaveInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void queryFederationRestoreStatus () override;

      //////////////////////////////////////
      //! Declaration Management Services //
      //////////////////////////////////////

      /**
       * 5.2
       * @throws AttributeNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void publishObjectClassAttributes (
            const ObjectClassHandle & objectClass,
            const AttributeHandleSet & attributes) override;

      /**
       * 5.3
       * @throws OwnershipAcquisitionPending
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void unpublishObjectClass (
            const ObjectClassHandle & objectClass) override;

      /**
       * 5.3
       * @throws OwnershipAcquisitionPending
       * @throws AttributeNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void unpublishObjectClassAttributes (
            const ObjectClassHandle & objectClass,
            const AttributeHandleSet & attributes) override;

      /**
       * 5.4
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void publishInteractionClass(
         InteractionClassHandle const & interactionClass) override;

      /**
       * 5.5
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void unpublishInteractionClass(
         InteractionClassHandle const & interactionClass) override;

      /**
       * 5.6
       * @throws InteractionClassNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void publishObjectClassDirectedInteractions(
         ObjectClassHandle const & objectClass,
            InteractionClassHandleSet const & interactionClasses) override;

      /**
       * 5.7
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void unpublishObjectClassDirectedInteractions(
         ObjectClassHandle const & objectClass) override;

      /**
       * 5.7
       * @throws InteractionClassNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void unpublishObjectClassDirectedInteractions(
         ObjectClassHandle const & objectClass,
         InteractionClassHandleSet const & interactionClasses) override;

      /**
       * 5.8
       * @throws AttributeNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void subscribeObjectClassAttributes (
            const ObjectClassHandle & objectClass,
            const AttributeHandleSet & attributes,
            bool active,
            std::wstring const & updateRateDesignator) override;

      /**
       * 5.9
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void unsubscribeObjectClass (
            const ObjectClassHandle & objectClass) override;

      /**
       * 5.9
       * @throws AttributeNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void unsubscribeObjectClassAttributes (
            const ObjectClassHandle & objectClass,
            const AttributeHandleSet & attributes) override;

      /**
       * 5.10
       * @throws FederateServiceInvocationsAreBeingReportedViaMOM
       * @throws InteractionClassNotDefined=
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void subscribeInteractionClass(
         InteractionClassHandle const & interactionClass,
         bool active) override;

      /**
       * 5.11
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void unsubscribeInteractionClass(
         InteractionClassHandle const & interactionClass) override;

      /**
       * 5.12
       * @throws InteractionClassNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void subscribeObjectClassDirectedInteractions(
         ObjectClassHandle const & objectClass,
         InteractionClassHandleSet const & interactionClasses,
         bool universally) override;

      /**
       * 5.13
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void unsubscribeObjectClassDirectedInteractions(
         ObjectClassHandle const & objectClass) override;

      /**
       * 5.13
       * @throws InteractionClassNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void unsubscribeObjectClassDirectedInteractions(
         ObjectClassHandle const & objectClass,
         InteractionClassHandleSet const & interactionClasses) override;

      /////////////////////////////////
      //! Object Management Services //
      /////////////////////////////////

      /**
       * 6.2
       * @throws IllegalName
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void reserveObjectInstanceName (
            std::wstring const & objectInstanceName) override;

      /**
       * 6.4
       * @throws ObjectInstanceNameNotReserved
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void releaseObjectInstanceName (
            std::wstring const & objectInstanceName) override;

      /**
       * 6.5
       * @throws IllegalName
       * @throws NameSetWasEmpty
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void reserveMultipleObjectInstanceNames (
            std::set<std::wstring> const & objectInstanceNames) override;

      /**
       * 6.7
       * @throws ObjectInstanceNameNotReserved
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void releaseMultipleObjectInstanceNames (
            std::set<std::wstring> const & objectInstanceNames) override;

      /**
       * 6.8
       * @throws ObjectClassNotPublished
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      ObjectInstanceHandle registerObjectInstance (
            const ObjectClassHandle & objectClass) override;

      /**
       * 6.8
       * @throws ObjectInstanceNameInUse
       * @throws ObjectInstanceNameNotReserved
       * @throws ObjectClassNotPublished
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      ObjectInstanceHandle registerObjectInstance (
            const ObjectClassHandle & objectClass,
            std::wstring const & objectInstanceName) override;

      /**
       * 6.10
       * @throws AttributeNotOwned
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void updateAttributeValues (
            const ObjectInstanceHandle & objectInstance,
            AttributeHandleValueMap const & attributeValues,
            const VariableLengthData & userSuppliedTag) override;

      /**
       * 6.10
       * @throws InvalidLogicalTime
       * @throws AttributeNotOwned
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      MessageRetractionHandle updateAttributeValues (
            const ObjectInstanceHandle & objectInstance,
            AttributeHandleValueMap const & attributeValues,
            const VariableLengthData & userSuppliedTag,
            const LogicalTime & time) override;

      /**
       * 6.12
       * @throws InteractionClassNotPublished
       * @throws InteractionParameterNotDefined
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void sendInteraction (
            const InteractionClassHandle & interactionClass,
            const ParameterHandleValueMap & parameterValues,
            const VariableLengthData & userSuppliedTag) override;

      /**
       * 6.12
       * @throws InvalidLogicalTime
       * @throws InteractionClassNotPublished
       * @throws InteractionParameterNotDefined
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      MessageRetractionHandle sendInteraction (
            const InteractionClassHandle & interactionClass,
            const ParameterHandleValueMap & parameterValues,
            const VariableLengthData & userSuppliedTag,
            const LogicalTime & time) override;

      /**
       * 6.12
       * @throws ObjectInstanceNotKnown
       * @throws InteractionClassNotPublished
       * @throws InteractionParameterNotDefined
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void sendDirectedInteraction (
            const InteractionClassHandle & interactionClass,
            const ObjectInstanceHandle & objectInstance,
            const ParameterHandleValueMap & parameterValues,
            const VariableLengthData & userSuppliedTag) override;

      /**
       * 6.14
       * @throws InvalidLogicalTime
       * @throws ObjectInstanceNotKnown
       * @throws InteractionClassNotPublished
       * @throws InteractionParameterNotDefined
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      MessageRetractionHandle sendDirectedInteraction (
            const InteractionClassHandle & interactionClass,
            const ObjectInstanceHandle & objectInstance,
            const ParameterHandleValueMap & parameterValues,
            const VariableLengthData & userSuppliedTag,
            const LogicalTime & time) override;

      /**
       * 6.16
       * @throws DeletePrivilegeNotHeld
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void deleteObjectInstance (
            const ObjectInstanceHandle & objectInstance,
            const VariableLengthData & userSuppliedTag) override;

      /**
       * 6.16
       * @throws InvalidLogicalTime
       * @throws DeletePrivilegeNotHeld
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      MessageRetractionHandle deleteObjectInstance (
            const ObjectInstanceHandle & objectInstance,
            const VariableLengthData & userSuppliedTag,
            LogicalTime  const & time) override;

      /**
       * 6.18
       * @throws OwnershipAcquisitionPending
       * @throws FederateOwnsAttributes
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void localDeleteObjectInstance (
            const ObjectInstanceHandle & objectInstance) override;

      /**
       * 6.21
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void requestAttributeValueUpdate (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSet & attributes,
            const VariableLengthData & userSuppliedTag) override;

      /**
       * 6.21
       * @throws AttributeNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void requestAttributeValueUpdate (
            const ObjectClassHandle & objectClass,
            const AttributeHandleSet & attributes,
            const VariableLengthData & userSuppliedTag) override;

      /**
       * 6.25
       * @throws AttributeAlreadyBeingChanged
       * @throws AttributeNotOwned
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws InvalidTransportationTypeHandle
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void requestAttributeTransportationTypeChange (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSet & attributes,
            const TransportationTypeHandle & transportationType) override;

      /**
       * 6.27
       * @throws AttributeAlreadyBeingChanged
       * @throws AttributeNotDefined
       * @throws ObjectClassNotDefined
       * @throws InvalidTransportationTypeHandle
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void changeDefaultAttributeTransportationType (
            const ObjectClassHandle & objectClass,
            const AttributeHandleSet & attributes,
            const TransportationTypeHandle & transportationType) override;

      /**
       * 6.28
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void queryAttributeTransportationType (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandle & attribute) override;

      /**
       * 6.30
       * @throws InteractionClassAlreadyBeingChanged
       * @throws InteractionClassNotPublished
       * @throws InteractionClassNotDefined
       * @throws InvalidTransportationTypeHandle
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void requestInteractionTransportationTypeChange (
            const InteractionClassHandle & interactionClass,
            const TransportationTypeHandle & transportationType) override;

      /**
       * 6.32
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void queryInteractionTransportationType (
            const FederateHandle & federate,
            const InteractionClassHandle & interactionClass) override;


      ////////////////////////////////////
      //! Ownership Management Services //
      ////////////////////////////////////

      /**
       * 7.2
       * @throws AttributeNotOwned
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void unconditionalAttributeOwnershipDivestiture (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSet & attributes,
            const VariableLengthData & userSuppliedTag) override;

      /**
       * 7.3
       * @throws AttributeAlreadyBeingDivested
       * @throws AttributeNotOwned
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void negotiatedAttributeOwnershipDivestiture (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSet & attributes,
            const VariableLengthData & userSuppliedTag) override;

      /**
       * 7.6
       * @throws NoAcquisitionPending
       * @throws AttributeDivestitureWasNotRequested
       * @throws AttributeNotOwned
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void confirmDivestiture (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSet & confirmedAttributes,
            const VariableLengthData & userSuppliedTag) override;

      /**
       * 7.8
       * @throws AttributeNotPublished
       * @throws ObjectClassNotPublished
       * @throws FederateOwnsAttributes
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void attributeOwnershipAcquisition (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSet & desiredAttributes,
            const VariableLengthData & userSuppliedTag) override;

      /**
       * 7.9
       * @throws AttributeAlreadyBeingAcquired
       * @throws AttributeNotPublished
       * @throws ObjectClassNotPublished
       * @throws FederateOwnsAttributes
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void attributeOwnershipAcquisitionIfAvailable (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSet & desiredAttributes,
            const VariableLengthData & userSuppliedTag) override;

      /**
       * 7.12
       * @throws AttributeNotOwned
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void attributeOwnershipReleaseDenied (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSet & attributes,
            const VariableLengthData & userSuppliedTag) override;

      /**
       * 7.13
       * @throws AttributeNotOwned
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void attributeOwnershipDivestitureIfWanted (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSet & attributes,
            const VariableLengthData & userSuppliedTag,
            AttributeHandleSet & divestedAttributes) override;

      /**
       * 7.14
       * @throws AttributeDivestitureWasNotRequested
       * @throws AttributeNotOwned
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void cancelNegotiatedAttributeOwnershipDivestiture (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSet & attributes) override;

      /**
       * 7.15
       * @throws AttributeAcquisitionWasNotRequested
       * @throws AttributeAlreadyOwned
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void cancelAttributeOwnershipAcquisition (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSet & attributes) override;

      /**
       * 7.17
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void queryAttributeOwnership (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSet & attributes) override;

      /**
       * 7.19
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool isAttributeOwnedByFederate (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandle & attribute) override;

      ///////////////////////////////
      //! Time Management Services //
      ///////////////////////////////

      /**
       * 8.2
       * @throws InvalidLookahead
       * @throws InTimeAdvancingState
       * @throws RequestForTimeRegulationPending
       * @throws TimeRegulationAlreadyEnabled
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void enableTimeRegulation (
            const LogicalTimeInterval & lookahead) override;

      /**
       * 8.4
       * @throws TimeRegulationIsNotEnabled
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void disableTimeRegulation () override;

      /**
       * 8.5
       * @throws InTimeAdvancingState
       * @throws RequestForTimeConstrainedPending
       * @throws TimeConstrainedAlreadyEnabled
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void enableTimeConstrained () override;

      /**
       * 8.7
       * @throws TimeConstrainedIsNotEnabled
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void disableTimeConstrained () override;

      /**
       * 8.8
       * @throws LogicalTimeAlreadyPassed
       * @throws InvalidLogicalTime
       * @throws InTimeAdvancingState
       * @throws RequestForTimeRegulationPending
       * @throws RequestForTimeConstrainedPending
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void timeAdvanceRequest (
            const LogicalTime & time) override;

      /**
       * 8.9
       * @throws LogicalTimeAlreadyPassed
       * @throws InvalidLogicalTime
       * @throws InTimeAdvancingState
       * @throws RequestForTimeRegulationPending
       * @throws RequestForTimeConstrainedPending
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void timeAdvanceRequestAvailable (
            const LogicalTime & time) override;

      /**
       * 8.10
       * @throws LogicalTimeAlreadyPassed
       * @throws InvalidLogicalTime
       * @throws InTimeAdvancingState
       * @throws RequestForTimeRegulationPending
       * @throws RequestForTimeConstrainedPending
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void nextMessageRequest (
            const LogicalTime & time) override;

      /**
       * 8.11
       * @throws LogicalTimeAlreadyPassed
       * @throws InvalidLogicalTime
       * @throws InTimeAdvancingState
       * @throws RequestForTimeRegulationPending
       * @throws RequestForTimeConstrainedPending
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void nextMessageRequestAvailable (
            const LogicalTime & time) override;

      /**
       * 8.12
       * @throws LogicalTimeAlreadyPassed
       * @throws InvalidLogicalTime
       * @throws InTimeAdvancingState
       * @throws RequestForTimeRegulationPending
       * @throws RequestForTimeConstrainedPending
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void flushQueueRequest (
            const LogicalTime & time) override;

      /**
       * 8.15
       * @throws AsynchronousDeliveryAlreadyEnabled
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void enableAsynchronousDelivery () override;

      /**
       * 8.16
       * @throws AsynchronousDeliveryAlreadyDisabled
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void disableAsynchronousDelivery () override;

      /**
       * 8.17
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool queryGALT (LogicalTime & time) override;

      /**
       * 8.18
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void queryLogicalTime (LogicalTime & time) override;

      /**
       * 8.19
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool queryLITS (LogicalTime & time) override;

      /**
       * 8.20
       * @throws InvalidLookahead
       * @throws InTimeAdvancingState
       * @throws TimeRegulationIsNotEnabled
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void modifyLookahead (
            const LogicalTimeInterval & lookahead) override;

      /**
       * 8.21
       * @throws TimeRegulationIsNotEnabled
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void queryLookahead (LogicalTimeInterval & interval) override;

      /**
       * 8.22
       * @throws MessageCanNoLongerBeRetracted
       * @throws InvalidMessageRetractionHandle
       * @throws TimeRegulationIsNotEnabled
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void retract (
            const MessageRetractionHandle & retraction) override;

      /**
       * 8.24
       * @throws AttributeNotOwned
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void changeAttributeOrderType (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSet & attributes,
            OrderType orderType) override;

      /**
       * 8.25
       * @throws AttributeNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void changeDefaultAttributeOrderType (
            const ObjectClassHandle & objectClass,
            const AttributeHandleSet & attributes,
            OrderType orderType) override;

      /**
       * 8.26
       * @throws InteractionClassNotPublished
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void changeInteractionOrderType (
            const InteractionClassHandle & interactionClass,
            OrderType orderType) override;

      ///////////////////////////////////
      //! Data Distribution Management //
      ///////////////////////////////////

      /**
       * 9.2
       * @throws InvalidDimensionHandle
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      RegionHandle createRegion (
            DimensionHandleSet const & dimensions) override;

      /**
       * 9.3
       * @throws RegionNotCreatedByThisFederate
       * @throws InvalidRegion
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void commitRegionModifications (
            const RegionHandleSet & regions) override;

      /**
       * 9.4
       * @throws RegionInUseForUpdateOrSubscription
       * @throws RegionNotCreatedByThisFederate
       * @throws InvalidRegion
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void deleteRegion (
            const RegionHandle & region) override;

      /**
       * 9.5
       * @throws InvalidRegionContext
       * @throws RegionNotCreatedByThisFederate
       * @throws InvalidRegion
       * @throws AttributeNotPublished
       * @throws ObjectClassNotPublished
       * @throws AttributeNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      ObjectInstanceHandle registerObjectInstanceWithRegions (
            const ObjectClassHandle & objectClass,
            const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions) override;

      /**
       * 9.5
       * @throws ObjectInstanceNameInUse
       * @throws ObjectInstanceNameNotReserved
       * @throws InvalidRegionContext
       * @throws RegionNotCreatedByThisFederate
       * @throws InvalidRegion
       * @throws AttributeNotPublished
       * @throws ObjectClassNotPublished
       * @throws AttributeNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      ObjectInstanceHandle registerObjectInstanceWithRegions (
            const ObjectClassHandle & objectClass,
            const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
            std::wstring const & objectInstanceName) override;

      /**
       * 9.6
       * @throws InvalidRegionContext
       * @throws RegionNotCreatedByThisFederate
       * @throws InvalidRegion
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void associateRegionsForUpdates (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions) override;

      /**
       * 9.7
       * @throws RegionNotCreatedByThisFederate
       * @throws InvalidRegion
       * @throws AttributeNotDefined
       * @throws ObjectInstanceNotKnown
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void unassociateRegionsForUpdates (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions) override;

      /**
       * 9.8
       * @throws InvalidRegionContext
       * @throws RegionNotCreatedByThisFederate
       * @throws InvalidRegion
       * @throws AttributeNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void subscribeObjectClassAttributesWithRegions (
            const ObjectClassHandle & objectClass,
            const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
            bool active,
            std::wstring const & updateRateDesignator) override;


      /**
       * 9.9
       * @throws RegionNotCreatedByThisFederate
       * @throws InvalidRegion
       * @throws AttributeNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void unsubscribeObjectClassAttributesWithRegions (
            const ObjectClassHandle & objectClass,
            const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions) override;

      /**
       * 9.10
       * @throws FederateServiceInvocationsAreBeingReportedViaMOM
       * @throws InvalidRegionContext
       * @throws RegionNotCreatedByThisFederate
       * @throws InvalidRegion
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void subscribeInteractionClassWithRegions (
            const InteractionClassHandle & interactionClass,
            const RegionHandleSet & regions,
            bool active) override;

      /**
       * 9.11
       * @throws FederateServiceInvocationsAreBeingReportedViaMOM
       * @throws InvalidRegionContext
       * @throws RegionNotCreatedByThisFederate
       * @throws InvalidRegion
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void unsubscribeInteractionClassWithRegions (
            const InteractionClassHandle & interactionClass,
            const RegionHandleSet & regions) override;

      /**
       * 9.12
       * @throws InvalidRegionContext
       * @throws RegionNotCreatedByThisFederate
       * @throws InvalidRegion
       * @throws InteractionClassNotPublished
       * @throws InteractionParameterNotDefined
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void sendInteractionWithRegions (
            const InteractionClassHandle & interactionClass,
            const ParameterHandleValueMap & parameterValues,
            const RegionHandleSet & regions,
            const VariableLengthData & userSuppliedTag) override;

      /**
       * 9.12
       * @throws InvalidLogicalTime
       * @throws InvalidRegionContext
       * @throws RegionNotCreatedByThisFederate
       * @throws InvalidRegion
       * @throws InteractionClassNotPublished
       * @throws InteractionParameterNotDefined
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      MessageRetractionHandle sendInteractionWithRegions (
            const InteractionClassHandle & interactionClass,
            const ParameterHandleValueMap & parameterValues,
            const RegionHandleSet & regions,
            const VariableLengthData & userSuppliedTag,
            const LogicalTime & time) override;

      /**
       * 9.13
       * @throws InvalidRegionContext
       * @throws RegionNotCreatedByThisFederate
       * @throws InvalidRegion
       * @throws AttributeNotDefined
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void requestAttributeValueUpdateWithRegions (
            const ObjectClassHandle & objectClass,
            const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
            const VariableLengthData & userSuppliedTag) override;

      ///////////////////////////
      //! RTI Support Services //
      ///////////////////////////

      /**
       * 10.2
       * @throws NameNotFound
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      FederateHandle getFederateHandle (
            std::wstring const & federateName) override;

      /**
       * 10.3
       * @throws InvalidFederateHandle
       * @throws FederateHandleNotKnown
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      std::wstring getFederateName (
            const FederateHandle & federate) override;

      /**
       * 10.4
       * @throws NameNotFound
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      ObjectClassHandle getObjectClassHandle (
            std::wstring const & objectClassName) override;

      /**
       * 10.5
       * @throws InvalidObjectClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      std::wstring getObjectClassName (
            const ObjectClassHandle & objectClass) override;

      /**
       * 10.6
       * @throws ObjectInstanceNotKnown
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      ObjectClassHandle getKnownObjectClassHandle (
            const ObjectInstanceHandle & objectInstance) override;

      /**
       * 10.7
       * @throws ObjectInstanceNotKnown
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      ObjectInstanceHandle getObjectInstanceHandle (
            std::wstring const & objectInstanceName) override;

      /**
       * 10.8
       * @throws ObjectInstanceNotKnown
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      std::wstring getObjectInstanceName (
            const ObjectInstanceHandle & objectInstance) override;

      /**
       * 10.9
       * @throws NameNotFound
       * @throws InvalidObjectClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      AttributeHandle getAttributeHandle (
            const ObjectClassHandle & objectClass,
            std::wstring const & attributeName) override;

      /**
       * 10.10
       * @throws AttributeNotDefined
       * @throws InvalidAttributeHandle
       * @throws InvalidObjectClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      std::wstring getAttributeName (
            const ObjectClassHandle & objectClass,
            const AttributeHandle & attribute) override;

      /**
       * 10.11
       * @throws InvalidUpdateRateDesignator
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      double getUpdateRateValue (
            std::wstring const & updateRateDesignator) override;

      /**
       * 10.12
       * @throws ObjectInstanceNotKnown
       * @throws AttributeNotDefined
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      double getUpdateRateValueForAttribute (
            const ObjectInstanceHandle & objectInstance,
            const AttributeHandle & attribute) override;

      /**
       * 10.13
       * @throws NameNotFound
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      InteractionClassHandle getInteractionClassHandle (
            std::wstring const & interactionClassName) override;

      /**
       * 10.14
       * @throws InvalidInteractionClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      std::wstring getInteractionClassName (
            const InteractionClassHandle & interactionClass) override;

      /**
       * 10.15
       * @throws NameNotFound
       * @throws InvalidInteractionClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      ParameterHandle getParameterHandle (
            const InteractionClassHandle & interactionClass,
            std::wstring const & parameterName) override;

      /**
       * 10.16
       * @throws InteractionParameterNotDefined
       * @throws InvalidParameterHandle
       * @throws InvalidInteractionClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      std::wstring getParameterName (
            const InteractionClassHandle & interactionClass,
            const ParameterHandle & parameter) override;

      /**
       * 10.17
       * @throws InvalidOrderName
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      OrderType getOrderType (
            std::wstring const & orderTypeName) override;

      /**
       * 10.18
       * @throws InvalidOrderType
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      std::wstring getOrderName (
            OrderType orderType) override;

      /**
       * 10.19
       * @throws InvalidTransportationName
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      TransportationTypeHandle getTransportationTypeHandle (
            std::wstring const & transportationTypeName) override;

      /**
       * 10.20
       * @throws InvalidTransportationTypeHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      std::wstring getTransportationTypeName (
            const TransportationTypeHandle & transportationType) override;

      /**
       * 10.21
       * @throws InvalidObjectClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      DimensionHandleSet getAvailableDimensionsForObjectClass(
       ObjectClassHandle const & objectClass) override;

      /**
       * 10.22
       * @throws InvalidInteractionClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      DimensionHandleSet getAvailableDimensionsForInteractionClass (
            const InteractionClassHandle & interactionClass) override;

      /**
       * 10.23
       * @throws NameNotFound
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      DimensionHandle getDimensionHandle (
            std::wstring const & dimensionName) override;

      /**
       * 10.24
       * @throws InvalidDimensionHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      std::wstring getDimensionName (
            const DimensionHandle & dimension) override;

      /**
       * 10.25
       * @throws InvalidDimensionHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      unsigned long getDimensionUpperBound (
            const DimensionHandle & dimension) override;

      /**
       * 10.26
       * @throws InvalidRegion
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      DimensionHandleSet getDimensionHandleSet (
            const RegionHandle & region) override;

      /**
       * 10.27
       * @throws RegionDoesNotContainSpecifiedDimension
       * @throws InvalidRegion
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      RangeBounds getRangeBounds (
            const RegionHandle & region,
            const DimensionHandle & dimension) override;

      /**
       * 10.28
       * @throws InvalidRangeBound
       * @throws RegionDoesNotContainSpecifiedDimension
       * @throws RegionNotCreatedByThisFederate
       * @throws InvalidRegion
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void setRangeBounds (
            const RegionHandle & region,
            const DimensionHandle & dimension,
            const RangeBounds & rangeBounds) override;

      /**
       * 10.29
       * @throws InvalidServiceGroup
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      unsigned long normalizeServiceGroup (
            ServiceGroup serviceGroup) override;

      /**
       * 10.30
       * @throws InvalidFederateHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      unsigned long normalizeFederateHandle (
            const FederateHandle & federate) override;

      /**
       * 10.31
       * @throws InvalidObjectClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      unsigned long normalizeObjectClassHandle (
            const ObjectClassHandle & objectClass) override;

      /**
       * 10.32
       * @throws InvalidInteractionClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      unsigned long normalizeInteractionClassHandle (
            const InteractionClassHandle & interactionClass) override;

      /**
       * 10.33
       * @throws InvalidObjectInstanceHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      unsigned long normalizeObjectInstanceHandle (
         ObjectInstanceHandle const & objectInstance) override;

      /**
       * 10.34
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool getObjectClassRelevanceAdvisorySwitch() const override;

      /**
       * 10.35
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void setObjectClassRelevanceAdvisorySwitch(bool switchValue) override;

      /**
       * 10.36
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool getAttributeRelevanceAdvisorySwitch() const override;

      /**
       * 10.37
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void setAttributeRelevanceAdvisorySwitch(bool switchValue) override;

      /**
       * 10.38
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool getAttributeScopeAdvisorySwitch() const override;

      /**
       * 10.39
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void setAttributeScopeAdvisorySwitch(bool switchValue) override;

      /**
       * 10.40
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool getInteractionRelevanceAdvisorySwitch() const override;

      /**
       * 10.41
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void setInteractionRelevanceAdvisorySwitch(bool switchValue) override;

      /**
       * 10.42
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool getConveyRegionDesignatorSetsSwitch() const override;

      /**
       * 10.43
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void setConveyRegionDesignatorSetsSwitch(bool switchValue) override;

      /**
       * 10.44
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      ResignAction getAutomaticResignDirective() override;

      /**
       * 10.45
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws InvalidResignAction
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void setAutomaticResignDirective(
         ResignAction resignAction) override;

      /**
       * 10.46
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool getServiceReportingSwitch() const override;

      /**
       * 10.47
       * @throws ReportServiceInvocationsAreSubscribed
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void setServiceReportingSwitch(bool switchValue) override;

      /**
       * 10.48
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool getExceptionReportingSwitch() const override;

      /**
       * 10.49
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void setExceptionReportingSwitch(bool switchValue) override;

      /**
       * 10.50
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool getSendServiceReportsToFileSwitch() const override;

      /**
       * 10.51
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      void setSendServiceReportsToFileSwitch(bool switchValue) override;

      /**
       * 10.52
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool getAutoProvideSwitch() const override;

      /**
       * 10.53
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool getDelaySubscriptionEvaluationSwitch() const override;

      /**
       * 10.54
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool getAdvisoriesUseKnownClassSwitch() const override;

      /**
       * 10.55
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool getAllowRelaxedDDMSwitch() const override;

      /**
       * 10.56
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      bool getNonRegulatedGrantSwitch() const override;

      /**
       * 10.57
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      bool evokeCallback (
            double approximateMinimumTimeInSeconds) override;

      /**
       * 10.58
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      bool evokeMultipleCallbacks (
            double approximateMinimumTimeInSeconds,
            double approximateMaximumTimeInSeconds) override;

      /**
       * 10.59
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws RTIinternalError
       */
      void enableCallbacks () override;

      /**
       * 10.60
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws RTIinternalError
       */
      void disableCallbacks () override;

      ////////////////////////////
      //! API-specific services //
      ////////////////////////////

      //! Return instance of time factory being used by the federation
      /**
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       */
      std::unique_ptr<LogicalTimeFactory> getTimeFactory () const override;

      //! Decode handles
      //@{

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      FederateHandle decodeFederateHandle (
            const VariableLengthData & encodedValue) const override;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      ObjectClassHandle decodeObjectClassHandle (
            const VariableLengthData & encodedValue) const override;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      InteractionClassHandle decodeInteractionClassHandle (
            const VariableLengthData & encodedValue) const override;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      ObjectInstanceHandle decodeObjectInstanceHandle (
            const VariableLengthData & encodedValue) const override;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      AttributeHandle decodeAttributeHandle (
            const VariableLengthData & encodedValue) const override;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      ParameterHandle decodeParameterHandle (
            const VariableLengthData & encodedValue) const override;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      DimensionHandle decodeDimensionHandle (
            const VariableLengthData & encodedValue) const override;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      MessageRetractionHandle decodeMessageRetractionHandle (
            const VariableLengthData & encodedValue) const override;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      RegionHandle decodeRegionHandle (
            const VariableLengthData & encodedValue) const override;

      static RTI_NAMESPACE::RtiConfiguration parseRtiConfiguration(const RTI_NAMESPACE::RtiConfiguration & rtiConfiguration, const std::vector<std::wstring> & inputValueList);

   private:

      static void addServerAddressToList(
            std::vector<std::wstring> & settingsList,
            const RTI_NAMESPACE::RtiConfiguration & rtiConfiguration);

      static void addToSettingsLine(std::string & settingsLine, const std::string & toAdd);

      // May throw InvalidLookahead if there LogicalTimeInterval type is incorrect.
      void throwIfInvalidLookahead(const LogicalTimeInterval & lookahead);

      // May throw InvalidLogicalTime if there LogicalTime type is incorrect.
      void throwIfInvalidTime(const LogicalTime & time);

      std::shared_ptr<FedPro::ClientConverter> _clientConverter;

      // Client object is mutable because RTIambassador has const methods, and our adapter
      // require adding requests to a queue, which requires a non-const client object.
      mutable FedPro::RTIambassadorClient _rtiAmbassadorClient;

      FedPro::FomModuleLoader _fomModuleLoader;

      std::mutex _connectionStateLock;

      std::shared_ptr<LogicalTimeFactory> _timeFactory;

   };

} // RTI_NAMESPACE
