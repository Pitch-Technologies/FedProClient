#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

// This is a pure abstract interface that must be implemented by the
// federate to receive callbacks from the RTI.

namespace rti1516_2025
{
   class LogicalTime;
}

#include <RTI/SpecificConfig.h>
#include <RTI/Exception.h>
#include <RTI/Typedefs.h>
#include <RTI/Enums.h>

namespace rti1516_2025
{
   class RTI_EXPORT FederateAmbassador
   {
   protected:
      FederateAmbassador ();

   public:
      virtual ~FederateAmbassador ()
         noexcept = 0;

      ////////////////////////////////////
      // Federation Management Services //
      ////////////////////////////////////

      /**
       * 4.4
       * @throws FederateInternalError */
      virtual void connectionLost(
         std::wstring const & faultDescription) = 0;

      /**
       * 4.8
       * @throws FederateInternalError */
      virtual void reportFederationExecutions(
         FederationExecutionInformationVector const & report) = 0;

      /**
       * 4.10
       * @throws FederateInternalError */
      virtual void reportFederationExecutionMembers(
         std::wstring const & federationName,
         FederationExecutionMemberInformationVector const & report) = 0;

      /**
       * 4.11
       * @throws FederateInternalError */
      virtual void reportFederationExecutionDoesNotExist(
         std::wstring const & federationName) = 0;

      /**
       * 4.13
       * @throws FederateInternalError */
      virtual void federateResigned(
         std::wstring const & reasonForResignDescription) = 0;

      /**
       * 4.15
       * @throws FederateInternalError */
      virtual void synchronizationPointRegistrationSucceeded(
         std::wstring const & synchronizationPointLabel) = 0;

      /**
       * 4.15
       * @throws FederateInternalError */
      virtual void synchronizationPointRegistrationFailed(
         std::wstring const & synchronizationPointLabel,
         SynchronizationPointFailureReason reason) = 0;

      /**
       * 4.16
       * @throws FederateInternalError */
      virtual void announceSynchronizationPoint(
         std::wstring  const & synchronizationPointLabel,
         VariableLengthData const & userSuppliedTag) = 0;

      /**
       * 4.18
       * @throws FederateInternalError */
      virtual void federationSynchronized(
         std::wstring const & synchronizationPointLabel,
         FederateHandleSet const & failedToSyncSet) = 0;

      /**
       * 4.20
       * @throws FederateInternalError */
      virtual void initiateFederateSave(
         std::wstring const & label) = 0;

      /**
       * 4.20
       * @throws FederateInternalError */
      virtual void initiateFederateSave(
         std::wstring const & label,
         LogicalTime const & time) = 0;

      /**
       * 4.23
       * @throws FederateInternalError */
      virtual void federationSaved() = 0;

      /**
       * 4.23
       * @throws FederateInternalError */
      virtual void federationNotSaved (
         SaveFailureReason reason) = 0;

      /**
       * 4.26
       * @throws FederateInternalError */
      virtual void federationSaveStatusResponse(
         FederateHandleSaveStatusPairVector const & response) = 0;

      /**
       * 4.28
       * @throws FederateInternalError */
      virtual void requestFederationRestoreSucceeded(
         std::wstring const & label) = 0;

      /**
       * 4.28
       * @throws FederateInternalError */
      virtual void requestFederationRestoreFailed(
         std::wstring const & label) = 0;

      /**
       * 4.29
       * @throws FederateInternalError */
      virtual void federationRestoreBegun() = 0;

      /**
       * 4.30
       * @throws FederateInternalError */
      virtual void initiateFederateRestore(
         std::wstring const & label,
         std::wstring const & federateName,
         FederateHandle const & postRestoreFederateHandle) = 0;

      /**
       * 4.32
       * @throws FederateInternalError */
      virtual void federationRestored() = 0;

      /**
       * 4.32
       * @throws FederateInternalError */
      virtual void federationNotRestored (
         RestoreFailureReason reason) = 0;

      /**
       * 4.35
       * @throws FederateInternalError */
      virtual void federationRestoreStatusResponse(
         FederateRestoreStatusVector const & response) = 0;

      /////////////////////////////////////
      // Declaration Management Services //
      /////////////////////////////////////

      /**
       * 5.14
       * @throws FederateInternalError */
      virtual void startRegistrationForObjectClass(
         ObjectClassHandle const & objectClass) = 0;

      /**
       * 5.15
       * @throws FederateInternalError */
      virtual void stopRegistrationForObjectClass(
         ObjectClassHandle const & objectClass) = 0;

      /**
       * 5.16
       * @throws FederateInternalError */
      virtual void turnInteractionsOn(
         InteractionClassHandle const & interactionClass) = 0;

      /**
       * 5.17
       * @throws FederateInternalError */
      virtual void turnInteractionsOff(
         InteractionClassHandle const & interactionClass) = 0;

      ////////////////////////////////
      // Object Management Services //
      ////////////////////////////////

      /**
       * 6.3
       * @throws FederateInternalError */
      virtual void objectInstanceNameReservationSucceeded(
         std::wstring const & objectInstanceName) = 0;

      /**
       * 6.3
       * @throws FederateInternalError */
      virtual void objectInstanceNameReservationFailed(
         std::wstring const & objectInstanceName) = 0;

      /**
       * 6.6
       * @throws FederateInternalError */
      virtual void multipleObjectInstanceNameReservationSucceeded(
         std::set<std::wstring> const & objectInstanceNames) = 0;

      /**
       * 6.6
       * @throws FederateInternalError */
      virtual void multipleObjectInstanceNameReservationFailed(
         std::set<std::wstring> const & objectInstanceNames) = 0;


      /**
       * 6.9
       * @throws FederateInternalError */
      virtual void discoverObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         ObjectClassHandle const & objectClass,
         std::wstring const & objectInstanceName,
         FederateHandle const & producingFederate) = 0;

      /**
       * 6.11
       * @throws FederateInternalError */
      virtual void reflectAttributeValues(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleValueMap const & attributeValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate,
         RegionHandleSet const * optionalSentRegions) = 0;

      /**
       * 6.11
       * @throws FederateInternalError */
      virtual void reflectAttributeValues(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleValueMap const & attributeValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate,
         RegionHandleSet const * optionalSentRegions,
         LogicalTime const & time,
         OrderType sentOrderType,
         OrderType receivedOrderType,
         MessageRetractionHandle const * optionalRetraction) = 0;

      /**
       * 6.13
       * @throws FederateInternalError */
      virtual void receiveInteraction(
         InteractionClassHandle const & interactionClass,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate,
         const RegionHandleSet * optionalSentRegions) = 0;

      /**
       * 6.13
       * @throws FederateInternalError */
      virtual void receiveInteraction(
         InteractionClassHandle const & interactionClass,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate,
         RegionHandleSet const * optionalSentRegions,
         LogicalTime const & time,
         OrderType sentOrderType,
         OrderType receivedOrderType,
         MessageRetractionHandle const * optionalRetraction) = 0;

      /**
       * 6.15
       * @throws FederateInternalError */
      virtual void receiveDirectedInteraction(
         InteractionClassHandle const & interactionClass,
         ObjectInstanceHandle const & objectInstance,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate) = 0;

      /**
       * 6.15
       * @throws FederateInternalError */
      virtual void receiveDirectedInteraction(
         InteractionClassHandle const & interactionClass,
         ObjectInstanceHandle const & objectInstance,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate,
         LogicalTime const & time,
         OrderType sentOrderType,
         OrderType receivedOrderType,
         MessageRetractionHandle const * optionalRetraction) = 0;

      /**
       * 6.17
       * @throws FederateInternalError */
      virtual void removeObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         VariableLengthData const & userSuppliedTag,
         FederateHandle const & producingFederate) = 0;

      /**
       * 6.17
       * @throws FederateInternalError */
      virtual void removeObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         VariableLengthData const & userSuppliedTag,
         FederateHandle const & producingFederate,
         LogicalTime const & time,
         OrderType sentOrderType,
         OrderType receivedOrderType,
         MessageRetractionHandle const * optionalRetraction) = 0;

      /**
       * 6.19
       * @throws FederateInternalError */
      virtual void attributesInScope(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) = 0;

      /**
       * 6.20
       * @throws FederateInternalError */
      virtual void attributesOutOfScope(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) = 0;

      /**
       * 6.22
       * @throws FederateInternalError */
      virtual void provideAttributeValueUpdate(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         VariableLengthData const & userSuppliedTag) = 0;

      /**
       * 6.23
       * @throws FederateInternalError */
      virtual void turnUpdatesOnForObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) = 0;

      /**
       * 6.23
       * @throws FederateInternalError */
      virtual void turnUpdatesOnForObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         std::wstring const & updateRateDesignator) = 0;

      /**
       * 6.24
       * @throws FederateInternalError */
      virtual void turnUpdatesOffForObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) = 0;

      /**
       * 6.26
       * @throws FederateInternalError */
      virtual void confirmAttributeTransportationTypeChange(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         TransportationTypeHandle const & transportationType) = 0;

      /**
       * 6.29
       * @throws FederateInternalError */
      virtual void reportAttributeTransportationType(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandle const & attribute,
         TransportationTypeHandle const & transportationType) = 0;

      /**
       * 6.31
       * @throws FederateInternalError */
      virtual void confirmInteractionTransportationTypeChange(
         InteractionClassHandle const & interactionClass,
         TransportationTypeHandle const & transportationType) = 0;

      /**
       * 6.33
       * @throws FederateInternalError */
      virtual void reportInteractionTransportationType(
         FederateHandle const & federate,
         InteractionClassHandle const & interactionClass,
         TransportationTypeHandle const & transportationType) = 0;


      ///////////////////////////////////
      // Ownership Management Services //
      ///////////////////////////////////

      /**
       * 7.4
       * @throws FederateInternalError */
      virtual void requestAttributeOwnershipAssumption(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & offeredAttributes,
         VariableLengthData const & userSuppliedTag) = 0;

      /**
       * 7.5
       * @throws FederateInternalError */
      virtual void requestDivestitureConfirmation(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & releasedAttributes,
         VariableLengthData const & userSuppliedTag) = 0;

      /**
       * 7.7
       * @throws FederateInternalError */
      virtual void attributeOwnershipAcquisitionNotification(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & securedAttributes,
         VariableLengthData const & userSuppliedTag) = 0;

      /**
       * 7.10
       * @throws FederateInternalError */
      virtual void attributeOwnershipUnavailable(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         VariableLengthData const & userSuppliedTag) = 0;

      /**
       * 7.11
       * @throws FederateInternalError */
      virtual void requestAttributeOwnershipRelease(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & candidateAttributes,
         VariableLengthData const & userSuppliedTag) = 0;

      /**
       * 7.16
       * @throws FederateInternalError */
      virtual void confirmAttributeOwnershipAcquisitionCancellation(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) = 0;

      /**
       * 7.18
       * @throws FederateInternalError */
      virtual void informAttributeOwnership(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         FederateHandle const & owner) = 0;

      /**
       * 7.18
       * @throws FederateInternalError */
      virtual void attributeIsNotOwned (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) = 0;

      /**
       * 7.18
       * @throws FederateInternalError */
      virtual void attributeIsOwnedByRTI (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) = 0;

      //////////////////////////////
      // Time Management Services //
      //////////////////////////////

      /**
       * 8.3
       * @throws FederateInternalError */
      virtual void timeRegulationEnabled(
         LogicalTime const & time) = 0;

      /**
       * 8.6
       * @throws FederateInternalError */
      virtual void timeConstrainedEnabled(
         LogicalTime const & time) = 0;

      /**
       * 8.13
       * @throws FederateInternalError */
      virtual void flushQueueGrant(
         LogicalTime const & time, LogicalTime const & optimisticTime) = 0;

      /**
       * 8.14
       * @throws FederateInternalError */
      virtual void timeAdvanceGrant(
         LogicalTime const & time) = 0;

      /**
       * 8.23
       * @throws FederateInternalError */
      virtual void requestRetraction(
         MessageRetractionHandle const & retraction) = 0;
   };
}


