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

#include <fedpro/Properties.h>
#include <fedpro/Settings.h>
#include <services-common/FomModuleLoader.h>
#include <services-common/RTIambassadorClient.h>
#include <utility/string_view.h>

#include <RTI/RTIambassador.h>
#include <RTI/Enums.h>

#include <memory>

namespace RTI_NAMESPACE
{
   class FederateAmbassador;

   class RTIambassadorClientAdapter : public RTIambassador
   {
   public:

      RTIambassadorClientAdapter();

      ~RTIambassadorClientAdapter() override;

      // 4.2
      void connect(
            FederateAmbassador & federateAmbassador,
            CallbackModel theCallbackModel,
            std::wstring const & localSettingsDesignator)
            RTI_THROW(ConnectionFailed, InvalidLocalSettingsDesignator, UnsupportedCallbackModel, AlreadyConnected, CallNotAllowedFromWithinCallback, RTIinternalError) override;

      // 4.3
      void disconnect()
            RTI_THROW(FederateIsExecutionMember, CallNotAllowedFromWithinCallback, RTIinternalError) override;

      // 4.5
      void createFederationExecution(
            std::wstring const & federationExecutionName,
            std::wstring const & fomModule,
            std::wstring const & logicalTimeImplementationName)
            RTI_THROW(CouldNotCreateLogicalTimeFactory, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, FederationExecutionAlreadyExists, NotConnected, RTIinternalError) override;

      void createFederationExecution(
            std::wstring const & federationExecutionName,
            std::vector<std::wstring> const & fomModules,
            std::wstring const & logicalTimeImplementationName)
            RTI_THROW(CouldNotCreateLogicalTimeFactory, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, FederationExecutionAlreadyExists, NotConnected, RTIinternalError) override;

      void createFederationExecutionWithMIM(
            std::wstring const & federationExecutionName,
            std::vector<std::wstring> const & fomModules,
            std::wstring const & mimModule,
            std::wstring const & logicalTimeImplementationName)
            RTI_THROW(CouldNotCreateLogicalTimeFactory, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, DesignatorIsHLAstandardMIM, ErrorReadingMIM, CouldNotOpenMIM, FederationExecutionAlreadyExists, NotConnected, RTIinternalError) override;

      // 4.6
      void destroyFederationExecution(
            std::wstring const & federationExecutionName)
            RTI_THROW(FederatesCurrentlyJoined, FederationExecutionDoesNotExist, NotConnected, RTIinternalError) override;

      // 4.7
      void listFederationExecutions()
            RTI_THROW(NotConnected, RTIinternalError) override;

      // 4.9
      FederateHandle joinFederationExecution(
            std::wstring const & federateType,
            std::wstring const & federationExecutionName,
            std::vector<std::wstring> const & additionalFomModules)
            RTI_THROW(CouldNotCreateLogicalTimeFactory, FederationExecutionDoesNotExist, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, SaveInProgress, RestoreInProgress, FederateAlreadyExecutionMember, NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError) override;

      FederateHandle joinFederationExecution(
            std::wstring const & federateName,
            std::wstring const & federateType,
            std::wstring const & federationExecutionName,
            std::vector<std::wstring> const & additionalFomModules)
            RTI_THROW(CouldNotCreateLogicalTimeFactory, FederateNameAlreadyInUse, FederationExecutionDoesNotExist, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, SaveInProgress, RestoreInProgress, FederateAlreadyExecutionMember, NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError) override;

      // 4.10
      void resignFederationExecution(
            ResignAction resignAction)
            RTI_THROW(InvalidResignAction, OwnershipAcquisitionPending, FederateOwnsAttributes, FederateNotExecutionMember, NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError) override;

      // 4.11
      void registerFederationSynchronizationPoint(
            std::wstring const & label,
            VariableLengthData const & theUserSuppliedTag)
            RTI_THROW(SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      void registerFederationSynchronizationPoint(
            std::wstring const & label,
            VariableLengthData const & theUserSuppliedTag,
            FederateHandleSet const & synchronizationSet)
            RTI_THROW(InvalidFederateHandle, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 4.14
      void synchronizationPointAchieved(
            std::wstring const & label,
            bool successfully)
            RTI_THROW(SynchronizationPointLabelNotAnnounced, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 4.16
      void requestFederationSave(
            std::wstring const & label)
            RTI_THROW(SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      void requestFederationSave(
            std::wstring const & label,
            LogicalTime const & theTime)
            RTI_THROW(LogicalTimeAlreadyPassed, InvalidLogicalTime, FederateUnableToUseTime, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 4.18
      void federateSaveBegun()
            RTI_THROW(SaveNotInitiated, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 4.19
      void federateSaveComplete()
            RTI_THROW(FederateHasNotBegunSave, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      void federateSaveNotComplete()

            RTI_THROW(FederateHasNotBegunSave, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 4.21
      void abortFederationSave()
            RTI_THROW(SaveNotInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 4.22
      void queryFederationSaveStatus()
            RTI_THROW(RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 4.24
      void requestFederationRestore(
            std::wstring const & label)
            RTI_THROW(SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 4.28
      void federateRestoreComplete()
            RTI_THROW(RestoreNotRequested, SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      void federateRestoreNotComplete()

            RTI_THROW(RestoreNotRequested, SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 4.30
      void abortFederationRestore()
            RTI_THROW(RestoreNotInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 4.31
      void queryFederationRestoreStatus()
            RTI_THROW(SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      /////////////////////////////////////
      // Declaration Management Services //
      /////////////////////////////////////

      // 5.2
      void publishObjectClassAttributes(
            ObjectClassHandle theClass,
            AttributeHandleSet const & attributeList)
            RTI_THROW(AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 5.3
      void unpublishObjectClass(
            ObjectClassHandle theClass)
            RTI_THROW(OwnershipAcquisitionPending, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      void unpublishObjectClassAttributes(
            ObjectClassHandle theClass,
            AttributeHandleSet const & attributeList)
            RTI_THROW(OwnershipAcquisitionPending, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 5.4
      void publishInteractionClass(
            InteractionClassHandle theInteraction)
            RTI_THROW(InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 5.5
      void unpublishInteractionClass(
            InteractionClassHandle theInteraction)
            RTI_THROW(InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 5.6
      void subscribeObjectClassAttributes(
            ObjectClassHandle theClass,
            AttributeHandleSet const & attributeList,
            bool active,
            std::wstring const & updateRateDesignator)
            RTI_THROW(AttributeNotDefined, ObjectClassNotDefined, InvalidUpdateRateDesignator, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 5.7
      void unsubscribeObjectClass(
            ObjectClassHandle theClass)
            RTI_THROW(ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      void unsubscribeObjectClassAttributes(
            ObjectClassHandle theClass,
            AttributeHandleSet const & attributeList)
            RTI_THROW(AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 5.8
      void subscribeInteractionClass(
            InteractionClassHandle theClass,
            bool active)
            RTI_THROW(FederateServiceInvocationsAreBeingReportedViaMOM, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 5.9
      void unsubscribeInteractionClass(
            InteractionClassHandle theClass)
            RTI_THROW(InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      ////////////////////////////////
      // Object Management Services //
      ////////////////////////////////

      // 6.2
      void reserveObjectInstanceName(
            std::wstring const & theObjectInstanceName)
            RTI_THROW(IllegalName, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 6.4
      void releaseObjectInstanceName(
            std::wstring const & theObjectInstanceName)
            RTI_THROW(ObjectInstanceNameNotReserved, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 6.5
      void reserveMultipleObjectInstanceName(
            std::set<std::wstring> const & theObjectInstanceNames)
            RTI_THROW(IllegalName, NameSetWasEmpty, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 6.7
      void releaseMultipleObjectInstanceName(
            std::set<std::wstring> const & theObjectInstanceNames)
            RTI_THROW(ObjectInstanceNameNotReserved, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 6.8
      ObjectInstanceHandle registerObjectInstance(
            ObjectClassHandle theClass)
            RTI_THROW(ObjectClassNotPublished, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      ObjectInstanceHandle registerObjectInstance(
            ObjectClassHandle theClass,
            std::wstring const & theObjectInstanceName)
            RTI_THROW(ObjectInstanceNameInUse, ObjectInstanceNameNotReserved, ObjectClassNotPublished, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 6.10
      void updateAttributeValues(
            ObjectInstanceHandle theObject,
            AttributeHandleValueMap const & theAttributeValues,
            VariableLengthData const & theUserSuppliedTag)
            RTI_THROW(AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      MessageRetractionHandle updateAttributeValues(
            ObjectInstanceHandle theObject,
            AttributeHandleValueMap const & theAttributeValues,
            VariableLengthData const & theUserSuppliedTag,
            LogicalTime const & theTime)
            RTI_THROW(InvalidLogicalTime, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 6.12
      void sendInteraction(
            InteractionClassHandle theInteraction,
            ParameterHandleValueMap const & theParameterValues,
            VariableLengthData const & theUserSuppliedTag)
            RTI_THROW(InteractionClassNotPublished, InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      MessageRetractionHandle sendInteraction(
            InteractionClassHandle theInteraction,
            ParameterHandleValueMap const & theParameterValues,
            VariableLengthData const & theUserSuppliedTag,
            LogicalTime const & theTime)
            RTI_THROW(InvalidLogicalTime, InteractionClassNotPublished, InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 6.14
      void deleteObjectInstance(
            ObjectInstanceHandle theObject,
            VariableLengthData const & theUserSuppliedTag)
            RTI_THROW(DeletePrivilegeNotHeld, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      MessageRetractionHandle deleteObjectInstance(
            ObjectInstanceHandle theObject,
            VariableLengthData const & theUserSuppliedTag,
            LogicalTime const & theTime)
            RTI_THROW(InvalidLogicalTime, DeletePrivilegeNotHeld, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 6.16
      void localDeleteObjectInstance(
            ObjectInstanceHandle theObject)
            RTI_THROW(OwnershipAcquisitionPending, FederateOwnsAttributes, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 6.19
      void requestAttributeValueUpdate(
            ObjectInstanceHandle theObject,
            AttributeHandleSet const & theAttributes,
            VariableLengthData const & theUserSuppliedTag)
            RTI_THROW(AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      void requestAttributeValueUpdate(
            ObjectClassHandle theClass,
            AttributeHandleSet const & theAttributes,
            VariableLengthData const & theUserSuppliedTag)
            RTI_THROW(AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 6.23
      void requestAttributeTransportationTypeChange(
            ObjectInstanceHandle theObject,
            AttributeHandleSet const & theAttributes,
            TransportationType theType)
            RTI_THROW(AttributeAlreadyBeingChanged, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, InvalidTransportationType, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 6.25
      void queryAttributeTransportationType(
            ObjectInstanceHandle theObject,
            AttributeHandle theAttribute)
            RTI_THROW(AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 6.27
      void requestInteractionTransportationTypeChange(
            InteractionClassHandle theClass,
            TransportationType theType)
            RTI_THROW(InteractionClassAlreadyBeingChanged, InteractionClassNotPublished, InteractionClassNotDefined, InvalidTransportationType, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 6.29
      void queryInteractionTransportationType(
            FederateHandle theFederate,
            InteractionClassHandle theInteraction)
            RTI_THROW(InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;


      ///////////////////////////////////
      // Ownership Management Services //
      ///////////////////////////////////

      // 7.2
      void unconditionalAttributeOwnershipDivestiture(
            ObjectInstanceHandle theObject,
            AttributeHandleSet const & theAttributes)
            RTI_THROW(AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 7.3
      void negotiatedAttributeOwnershipDivestiture(
            ObjectInstanceHandle theObject,
            AttributeHandleSet const & theAttributes,
            VariableLengthData const & theUserSuppliedTag)
            RTI_THROW(AttributeAlreadyBeingDivested, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 7.6
      void confirmDivestiture(
            ObjectInstanceHandle theObject,
            AttributeHandleSet const & confirmedAttributes,
            VariableLengthData const & theUserSuppliedTag)
            RTI_THROW(NoAcquisitionPending, AttributeDivestitureWasNotRequested, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 7.8
      void attributeOwnershipAcquisition(
            ObjectInstanceHandle theObject,
            AttributeHandleSet const & desiredAttributes,
            VariableLengthData const & theUserSuppliedTag)
            RTI_THROW(AttributeNotPublished, ObjectClassNotPublished, FederateOwnsAttributes, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 7.9
      void attributeOwnershipAcquisitionIfAvailable(
            ObjectInstanceHandle theObject,
            AttributeHandleSet const & desiredAttributes)
            RTI_THROW(AttributeAlreadyBeingAcquired, AttributeNotPublished, ObjectClassNotPublished, FederateOwnsAttributes, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 7.12
      void attributeOwnershipReleaseDenied(
            ObjectInstanceHandle theObject,
            AttributeHandleSet const & theAttributes)
            RTI_THROW(AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 7.13
      void attributeOwnershipDivestitureIfWanted(
            ObjectInstanceHandle theObject,
            AttributeHandleSet const & theAttributes,
            AttributeHandleSet & theDivestedAttributes) // filled by RTI
            RTI_THROW(AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 7.14
      void cancelNegotiatedAttributeOwnershipDivestiture(
            ObjectInstanceHandle theObject,
            AttributeHandleSet const & theAttributes)
            RTI_THROW(AttributeDivestitureWasNotRequested, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 7.15
      void cancelAttributeOwnershipAcquisition(
            ObjectInstanceHandle theObject,
            AttributeHandleSet const & theAttributes)
            RTI_THROW(AttributeAcquisitionWasNotRequested, AttributeAlreadyOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 7.17
      void queryAttributeOwnership(
            ObjectInstanceHandle theObject,
            AttributeHandle theAttribute)
            RTI_THROW(AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 7.19
      bool isAttributeOwnedByFederate(
            ObjectInstanceHandle theObject,
            AttributeHandle theAttribute)
            RTI_THROW(AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      //////////////////////////////
      // Time Management Services //
      //////////////////////////////

      // 8.2
      void enableTimeRegulation(
            LogicalTimeInterval const & theLookahead)
            RTI_THROW(InvalidLookahead, InTimeAdvancingState, RequestForTimeRegulationPending, TimeRegulationAlreadyEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.4
      void disableTimeRegulation()
            RTI_THROW(TimeRegulationIsNotEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.5
      void enableTimeConstrained()
            RTI_THROW(InTimeAdvancingState, RequestForTimeConstrainedPending, TimeConstrainedAlreadyEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.7
      void disableTimeConstrained()
            RTI_THROW(TimeConstrainedIsNotEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.8
      void timeAdvanceRequest(
            LogicalTime const & theTime)
            RTI_THROW(LogicalTimeAlreadyPassed, InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.9
      void timeAdvanceRequestAvailable(
            LogicalTime const & theTime)
            RTI_THROW(LogicalTimeAlreadyPassed, InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.10
      void nextMessageRequest(
            LogicalTime const & theTime)
            RTI_THROW(LogicalTimeAlreadyPassed, InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.11
      void nextMessageRequestAvailable(
            LogicalTime const & theTime)
            RTI_THROW(LogicalTimeAlreadyPassed, InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.12
      void flushQueueRequest(
            LogicalTime const & theTime)
            RTI_THROW(LogicalTimeAlreadyPassed, InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.14
      void enableAsynchronousDelivery()
            RTI_THROW(AsynchronousDeliveryAlreadyEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.15
      void disableAsynchronousDelivery()
            RTI_THROW(AsynchronousDeliveryAlreadyDisabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.16
      bool queryGALT(LogicalTime & theTime)
            RTI_THROW(SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.17
      void queryLogicalTime(LogicalTime & theTime)
            RTI_THROW(SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.18
      bool queryLITS(LogicalTime & theTime)
            RTI_THROW(SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.19
      void modifyLookahead(
            LogicalTimeInterval const & theLookahead)
            RTI_THROW(InvalidLookahead, InTimeAdvancingState, TimeRegulationIsNotEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.20
      void queryLookahead(LogicalTimeInterval & interval)
            RTI_THROW(TimeRegulationIsNotEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.21
      void retract(
            MessageRetractionHandle theHandle)
            RTI_THROW(MessageCanNoLongerBeRetracted, InvalidMessageRetractionHandle, TimeRegulationIsNotEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.23
      void changeAttributeOrderType(
            ObjectInstanceHandle theObject,
            AttributeHandleSet const & theAttributes,
            OrderType theType)
            RTI_THROW(AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 8.24
      void changeInteractionOrderType(
            InteractionClassHandle theClass,
            OrderType theType)
            RTI_THROW(InteractionClassNotPublished, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      //////////////////////////////////
      // Data Distribution Management //
      //////////////////////////////////

      // 9.2
      RegionHandle createRegion(
            DimensionHandleSet const & theDimensions)
            RTI_THROW(InvalidDimensionHandle, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 9.3
      void commitRegionModifications(
            RegionHandleSet const & theRegionHandleSet)
            RTI_THROW(RegionNotCreatedByThisFederate, InvalidRegion, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 9.4
      void deleteRegion(
            RegionHandle const & theRegion)
            RTI_THROW(RegionInUseForUpdateOrSubscription, RegionNotCreatedByThisFederate, InvalidRegion, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 9.5
      ObjectInstanceHandle registerObjectInstanceWithRegions(
            ObjectClassHandle theClass,
            AttributeHandleSetRegionHandleSetPairVector const & theAttributeHandleSetRegionHandleSetPairVector)
            RTI_THROW(InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotPublished, ObjectClassNotPublished, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      ObjectInstanceHandle registerObjectInstanceWithRegions(
            ObjectClassHandle theClass,
            AttributeHandleSetRegionHandleSetPairVector const & theAttributeHandleSetRegionHandleSetPairVector,
            std::wstring const & theObjectInstanceName)
            RTI_THROW(ObjectInstanceNameInUse, ObjectInstanceNameNotReserved, InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotPublished, ObjectClassNotPublished, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 9.6
      void associateRegionsForUpdates(
            ObjectInstanceHandle theObject,
            AttributeHandleSetRegionHandleSetPairVector const & theAttributeHandleSetRegionHandleSetPairVector)
            RTI_THROW(InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 9.7
      void unassociateRegionsForUpdates(
            ObjectInstanceHandle theObject,
            AttributeHandleSetRegionHandleSetPairVector const & theAttributeHandleSetRegionHandleSetPairVector)
            RTI_THROW(RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 9.8
      void subscribeObjectClassAttributesWithRegions(
            ObjectClassHandle theClass,
            AttributeHandleSetRegionHandleSetPairVector const & theAttributeHandleSetRegionHandleSetPairVector,
            bool active,
            std::wstring const & updateRateDesignator)
            RTI_THROW(InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectClassNotDefined, InvalidUpdateRateDesignator, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;


      // 9.9
      void unsubscribeObjectClassAttributesWithRegions(
            ObjectClassHandle theClass,
            AttributeHandleSetRegionHandleSetPairVector const & theAttributeHandleSetRegionHandleSetPairVector)
            RTI_THROW(RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 9.10
      void subscribeInteractionClassWithRegions(
            InteractionClassHandle theClass,
            RegionHandleSet const & theRegionHandleSet,
            bool active)
            RTI_THROW(FederateServiceInvocationsAreBeingReportedViaMOM, InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 9.11
      void unsubscribeInteractionClassWithRegions(
            InteractionClassHandle theClass,
            RegionHandleSet const & theRegionHandleSet)
            RTI_THROW(RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 9.12
      void sendInteractionWithRegions(
            InteractionClassHandle theInteraction,
            ParameterHandleValueMap const & theParameterValues,
            RegionHandleSet const & theRegionHandleSet,
            VariableLengthData const & theUserSuppliedTag)
            RTI_THROW(InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotPublished, InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      MessageRetractionHandle sendInteractionWithRegions(
            InteractionClassHandle theInteraction,
            ParameterHandleValueMap const & theParameterValues,
            RegionHandleSet const & theRegionHandleSet,
            VariableLengthData const & theUserSuppliedTag,
            LogicalTime const & theTime)
            RTI_THROW(InvalidLogicalTime, InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotPublished, InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 9.13
      void requestAttributeValueUpdateWithRegions(
            ObjectClassHandle theClass,
            AttributeHandleSetRegionHandleSetPairVector const & theSet,
            VariableLengthData const & theUserSuppliedTag)
            RTI_THROW(InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      //////////////////////////
      // RTI Support Services //
      //////////////////////////

      // 10.2
      ResignAction getAutomaticResignDirective()
            RTI_THROW(FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.3
      void setAutomaticResignDirective(
            ResignAction resignAction)
            RTI_THROW(InvalidResignAction, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.4
      FederateHandle getFederateHandle(
            std::wstring const & theName)
            RTI_THROW(NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.5
      std::wstring getFederateName(
            FederateHandle theHandle)
            RTI_THROW(InvalidFederateHandle, FederateHandleNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.6
      ObjectClassHandle getObjectClassHandle(
            std::wstring const & theName)
            RTI_THROW(NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.7
      std::wstring getObjectClassName(
            ObjectClassHandle theHandle)
            RTI_THROW(InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.8
      ObjectClassHandle getKnownObjectClassHandle(
            ObjectInstanceHandle theObject)
            RTI_THROW(ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.9
      ObjectInstanceHandle getObjectInstanceHandle(
            std::wstring const & theName)
            RTI_THROW(ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.10
      std::wstring getObjectInstanceName(
            ObjectInstanceHandle theHandle)
            RTI_THROW(ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.11
      AttributeHandle getAttributeHandle(
            ObjectClassHandle whichClass,
            std::wstring const & theAttributeName)
            RTI_THROW(NameNotFound, InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.12
      std::wstring getAttributeName(
            ObjectClassHandle whichClass,
            AttributeHandle theHandle)
            RTI_THROW(AttributeNotDefined, InvalidAttributeHandle, InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.13
      double getUpdateRateValue(
            std::wstring const & updateRateDesignator)
            RTI_THROW(InvalidUpdateRateDesignator, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.14
      double getUpdateRateValueForAttribute(
            ObjectInstanceHandle theObject,
            AttributeHandle theAttribute)
            RTI_THROW(ObjectInstanceNotKnown, AttributeNotDefined, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.15
      InteractionClassHandle getInteractionClassHandle(
            std::wstring const & theName)
            RTI_THROW(NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.16
      std::wstring getInteractionClassName(
            InteractionClassHandle theHandle)
            RTI_THROW(InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.17
      ParameterHandle getParameterHandle(
            InteractionClassHandle whichClass,
            std::wstring const & theName)
            RTI_THROW(NameNotFound, InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.18
      std::wstring getParameterName(
            InteractionClassHandle whichClass,
            ParameterHandle theHandle)
            RTI_THROW(InteractionParameterNotDefined, InvalidParameterHandle, InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.19
      OrderType getOrderType(
            std::wstring const & orderName)
            RTI_THROW(InvalidOrderName, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.20
      std::wstring getOrderName(
            OrderType orderType)
            RTI_THROW(InvalidOrderType, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.21
      TransportationType getTransportationType(
            std::wstring const & transportationName)
            RTI_THROW(InvalidTransportationName, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.22
      std::wstring getTransportationName(
            TransportationType transportationType)
            RTI_THROW(InvalidTransportationType, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.23
      DimensionHandleSet getAvailableDimensionsForClassAttribute(
            ObjectClassHandle theClass,
            AttributeHandle theHandle)
            RTI_THROW(AttributeNotDefined, InvalidAttributeHandle, InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.24
      DimensionHandleSet getAvailableDimensionsForInteractionClass(
            InteractionClassHandle theClass)
            RTI_THROW(InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.25
      DimensionHandle getDimensionHandle(
            std::wstring const & theName)
            RTI_THROW(NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.26
      std::wstring getDimensionName(
            DimensionHandle theHandle)
            RTI_THROW(InvalidDimensionHandle, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.27
      unsigned long getDimensionUpperBound(
            DimensionHandle theHandle)
            RTI_THROW(InvalidDimensionHandle, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.28
      DimensionHandleSet getDimensionHandleSet(
            RegionHandle theRegionHandle)
            RTI_THROW(InvalidRegion, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.29
      RangeBounds getRangeBounds(
            RegionHandle theRegionHandle,
            DimensionHandle theDimensionHandle)
            RTI_THROW(RegionDoesNotContainSpecifiedDimension, InvalidRegion, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.30
      void setRangeBounds(
            RegionHandle theRegionHandle,
            DimensionHandle theDimensionHandle,
            RangeBounds const & theRangeBounds)
            RTI_THROW(InvalidRangeBound, RegionDoesNotContainSpecifiedDimension, RegionNotCreatedByThisFederate, InvalidRegion, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.31
      unsigned long normalizeFederateHandle(
            FederateHandle theFederateHandle)
            RTI_THROW(InvalidFederateHandle, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.32
      unsigned long normalizeServiceGroup(
            ServiceGroup theServiceGroup)
            RTI_THROW(InvalidServiceGroup, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.33
      void enableObjectClassRelevanceAdvisorySwitch()
      RTI_THROW(ObjectClassRelevanceAdvisorySwitchIsOn, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.34
      void disableObjectClassRelevanceAdvisorySwitch()
      RTI_THROW(ObjectClassRelevanceAdvisorySwitchIsOff, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.35
      void enableAttributeRelevanceAdvisorySwitch()
      RTI_THROW(AttributeRelevanceAdvisorySwitchIsOn, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.36
      void disableAttributeRelevanceAdvisorySwitch()
      RTI_THROW(AttributeRelevanceAdvisorySwitchIsOff, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.37
      void enableAttributeScopeAdvisorySwitch()
      RTI_THROW(AttributeScopeAdvisorySwitchIsOn, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.38
      void disableAttributeScopeAdvisorySwitch()
      RTI_THROW(AttributeScopeAdvisorySwitchIsOff, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.39
      void enableInteractionRelevanceAdvisorySwitch()
      RTI_THROW(InteractionRelevanceAdvisorySwitchIsOn, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.40
      void disableInteractionRelevanceAdvisorySwitch()
      RTI_THROW(InteractionRelevanceAdvisorySwitchIsOff, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // 10.41
      bool evokeCallback(
            double approximateMinimumTimeInSeconds)
            RTI_THROW(CallNotAllowedFromWithinCallback, RTIinternalError) override;

      // 10.42
      bool evokeMultipleCallbacks(
            double approximateMinimumTimeInSeconds,
            double approximateMaximumTimeInSeconds)
            RTI_THROW(CallNotAllowedFromWithinCallback, RTIinternalError) override;

      // 10.43
      void enableCallbacks()
            RTI_THROW(SaveInProgress, RestoreInProgress, RTIinternalError) override;

      // 10.44
      void disableCallbacks()
            RTI_THROW(SaveInProgress, RestoreInProgress, RTIinternalError) override;

      // API-specific services

      // Return instance of time factory being used by the federation
      std::auto_ptr<LogicalTimeFactory> getTimeFactory() const
            RTI_THROW(FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      // Decode handles
      FederateHandle decodeFederateHandle(
            VariableLengthData const & encodedValue) const
            RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      ObjectClassHandle decodeObjectClassHandle(
            VariableLengthData const & encodedValue) const
            RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      InteractionClassHandle decodeInteractionClassHandle(
            VariableLengthData const & encodedValue) const
            RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      ObjectInstanceHandle decodeObjectInstanceHandle(
            VariableLengthData const & encodedValue) const
            RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      AttributeHandle decodeAttributeHandle(
            VariableLengthData const & encodedValue) const
            RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      ParameterHandle decodeParameterHandle(
            VariableLengthData const & encodedValue) const
            RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      DimensionHandle decodeDimensionHandle(
            VariableLengthData const & encodedValue) const
            RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      MessageRetractionHandle decodeMessageRetractionHandle(
            VariableLengthData const & encodedValue) const
            RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      RegionHandle decodeRegionHandle(
            VariableLengthData const & encodedValue) const
            RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError) override;

      static RTI_NAMESPACE::RtiConfiguration createRtiConfiguration(std::vector<std::wstring> & inputValueList);

   private:

      static const std::string & getTransportationTypeName(TransportationType transportationType);

      // May throw InvalidLookahead if there LogicalTimeInterval type is incorrect.
      void throwIfInvalidLookahead(const LogicalTimeInterval & lookahead);

      // May throw InvalidLogicalTime if there LogicalTime type is incorrect.
      void throwIfInvalidTime(const LogicalTime & time);

      std::shared_ptr<FedPro::ClientConverter> _clientConverter;

      FedPro::RTIambassadorClient _rtiAmbassadorClient;

      FedPro::FomModuleLoader _fomModuleLoader;

      std::mutex _connectionStateLock;

      std::shared_ptr<LogicalTimeFactory> _timeFactory;

   };

} // RTI_NAMESPACE
