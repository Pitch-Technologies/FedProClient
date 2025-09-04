#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

#if defined(_WIN32)
#pragma warning(disable : 4100)
#endif

#include <RTI/Exception.h>
#include <RTI/FederateAmbassador.h>

namespace rti1516_2025
{
   class RTI_EXPORT NullFederateAmbassador : public FederateAmbassador
   {
   public:
      NullFederateAmbassador ()
          = default;

      ~NullFederateAmbassador ()
         noexcept override = default;

      /**
       * @throws FederateInternalError */
      void connectionLost (
         std::wstring const & faultDescription) override
          {}

      /**
       * @throws FederateInternalError */
      void reportFederationExecutions (
         FederationExecutionInformationVector const & report) override
          {}

      /**
       * @throws FederateInternalError */
      void reportFederationExecutionMembers(
         std::wstring const & federationName,
         FederationExecutionMemberInformationVector const & report) override
          {}

      /**
       * @throws FederateInternalError */
      void reportFederationExecutionDoesNotExist(
         std::wstring const & federationName) override
          {}

      /**
       * @throws FederateInternalError */
      void federateResigned (
         std::wstring const & reasonForResignDescription) override
          {}

      /**
       * @throws FederateInternalError */
      void synchronizationPointRegistrationSucceeded (
         std::wstring const & synchronizationPointLabel) override
          {}

      /**
       * @throws FederateInternalError */
      void synchronizationPointRegistrationFailed (
         std::wstring const & synchronizationPointLabel,
         SynchronizationPointFailureReason reason) override
          {}

      /**
       * @throws FederateInternalError */
      void announceSynchronizationPoint (
         std::wstring  const & synchronizationPointLabel,
         VariableLengthData const & userSuppliedTag) override
          {}

      /**
       * @throws FederateInternalError */
      void federationSynchronized (
         std::wstring const & synchronizationPointLabel,
         FederateHandleSet const & failedToSyncSet) override
          {}

      /**
       * @throws FederateInternalError */
      void initiateFederateSave (
         std::wstring const & label) override
          {}

      /**
       * @throws FederateInternalError */
      void initiateFederateSave (
         std::wstring const & label,
         LogicalTime const & time) override
          {}

      /**
       * @throws FederateInternalError */
      void federationSaved () override
          {}

      /**
       * @throws FederateInternalError */
      void federationNotSaved (
         SaveFailureReason reason) override
          {}


      /**
       * @throws FederateInternalError */
      void federationSaveStatusResponse (
         FederateHandleSaveStatusPairVector const & response) override
          {}

      /**
       * @throws FederateInternalError */
      void requestFederationRestoreSucceeded (
         std::wstring const & label) override
          {}

      /**
       * @throws FederateInternalError */
      void requestFederationRestoreFailed (
         std::wstring const & label) override
          {}

      /**
       * @throws FederateInternalError */
      void federationRestoreBegun () override
          {}

      /**
       * @throws FederateInternalError */
      void initiateFederateRestore (
         std::wstring const & label,
         std::wstring const & federateName,
         FederateHandle const & postRestoreFederateHandle) override
          {}

      /**
       * @throws FederateInternalError */
      void federationRestored () override
          {}

      /**
       * @throws FederateInternalError */
      void federationNotRestored (
         RestoreFailureReason reason) override
          {}

      /**
       * @throws FederateInternalError */
      void federationRestoreStatusResponse (
         FederateRestoreStatusVector const & response) override
          {}

      /**
       * @throws FederateInternalError */
      void startRegistrationForObjectClass (
         ObjectClassHandle const & objectClass) override
          {}

      /**
       * @throws FederateInternalError */
      void stopRegistrationForObjectClass (
         ObjectClassHandle const & objectClass) override
          {}

      /**
       * @throws FederateInternalError */
      void turnInteractionsOn (
         InteractionClassHandle const & interactionClass) override
          {}

      /**
       * @throws FederateInternalError */
      void turnInteractionsOff (
         InteractionClassHandle const & interactionClass) override
          {}

      /**
       * @throws FederateInternalError */
      void objectInstanceNameReservationSucceeded (
         std::wstring const & objectInstanceName) override
          {}

      /**
       * @throws FederateInternalError */
      void objectInstanceNameReservationFailed (
         std::wstring const & objectInstanceName) override
          {}

      /**
       * @throws FederateInternalError */
      void multipleObjectInstanceNameReservationSucceeded (
         std::set<std::wstring> const & objectInstanceNames) override
          {}

      /**
       * @throws FederateInternalError */
      void multipleObjectInstanceNameReservationFailed (
         std::set<std::wstring> const & objectInstanceNames) override
          {}

      /**
       * @throws FederateInternalError */
      void discoverObjectInstance (
         ObjectInstanceHandle const & objectInstance,
         ObjectClassHandle const & objectClass,
         std::wstring const & objectInstanceName,
         FederateHandle const & producingFederate) override
          {}

      /**
       * @throws FederateInternalError */
      void reflectAttributeValues(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleValueMap const & attributeValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate,
         RegionHandleSet const * optionalSentRegions) override
          {}

      /**
       * @throws FederateInternalError */
      void reflectAttributeValues(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleValueMap const & attributeValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate,
         RegionHandleSet const * optionalSentRegions,
         LogicalTime const & time,
         OrderType sentOrderType,
         OrderType receivedOrderType,
         MessageRetractionHandle const * optionalRetraction) override
         {
         reflectAttributeValues(
            objectInstance, attributeValues, userSuppliedTag, transportationType, producingFederate,
            optionalSentRegions);
         }

      /**
       * @throws FederateInternalError */
      void receiveInteraction(
         InteractionClassHandle const & interactionClass,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate,
         const RegionHandleSet * optionalSentRegions) override
          {}

      /**
       * @throws FederateInternalError */
      void receiveInteraction(
         InteractionClassHandle const & interactionClass,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate,
         RegionHandleSet const * optionalSentRegions,
         LogicalTime const & time,
         OrderType sentOrderType,
         OrderType receivedOrderType,
         MessageRetractionHandle const * optionalRetraction) override
         {
         receiveInteraction(interactionClass, parameterValues, userSuppliedTag, transportationType, producingFederate,
                            optionalSentRegions);
         }

      /**
       * @throws FederateInternalError */
      void receiveDirectedInteraction(
         InteractionClassHandle const & interactionClass,
         ObjectInstanceHandle const & objectInstance,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate) override
          {}

      /**
       * @throws FederateInternalError */
      void receiveDirectedInteraction(
         InteractionClassHandle const & interactionClass,
         ObjectInstanceHandle const & objectInstance,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate,
         LogicalTime const & time,
         OrderType sentOrderType,
         OrderType receivedOrderType,
         MessageRetractionHandle const * optionalRetraction) override
         {
            receiveDirectedInteraction(interactionClass, objectInstance, parameterValues, userSuppliedTag, transportationType,
                                       producingFederate);
         }

      /**
       * @throws FederateInternalError */
      void removeObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         VariableLengthData const & userSuppliedTag,
         FederateHandle const & producingFederate) override
          {}

      /**
       * @throws FederateInternalError */
      void removeObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         VariableLengthData const & userSuppliedTag,
         FederateHandle const & producingFederate,
         LogicalTime const & time,
         OrderType sentOrderType,
         OrderType receivedOrderType,
         MessageRetractionHandle const * optionalRetraction) override
         {
         removeObjectInstance(objectInstance, userSuppliedTag, producingFederate);
         }

      /**
       * @throws FederateInternalError */
      void attributesInScope (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) override
          {}

      /**
       * @throws FederateInternalError */
      void attributesOutOfScope (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) override
          {}

      /**
       * @throws FederateInternalError */
      void provideAttributeValueUpdate (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         VariableLengthData const & userSuppliedTag) override
          {}

      /**
       * @throws FederateInternalError */
      void turnUpdatesOnForObjectInstance (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) override
          {}

      /**
       * @throws FederateInternalError */
      void turnUpdatesOnForObjectInstance (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         std::wstring const & updateRateDesignator) override
          {}

      /**
       * @throws FederateInternalError */
      void turnUpdatesOffForObjectInstance (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) override
          {}

      /**
       * @throws FederateInternalError */
      void confirmAttributeTransportationTypeChange (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         TransportationTypeHandle const & transportationType) override
          {}

      /**
       * @throws FederateInternalError */
      void reportAttributeTransportationType (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandle const & attribute,
         TransportationTypeHandle const & transportationType) override
          {}

      /**
       * @throws FederateInternalError */
      void confirmInteractionTransportationTypeChange (
         InteractionClassHandle const & interactionClass,
         TransportationTypeHandle const & transportationType) override
          {}

      /**
       * @throws FederateInternalError */
      void reportInteractionTransportationType (
         FederateHandle const & federate,
         InteractionClassHandle const & interactionClass,
         TransportationTypeHandle const & transportationType) override
          {}


      /**
       * @throws FederateInternalError */
      void requestAttributeOwnershipAssumption (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & offeredAttributes,
         VariableLengthData const & userSuppliedTag) override
          {}

      /**
       * @throws FederateInternalError */
      void requestDivestitureConfirmation (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & releasedAttributes,
         VariableLengthData const & userSuppliedTag) override
          {}

      /**
       * @throws FederateInternalError */
      void attributeOwnershipAcquisitionNotification (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & securedAttributes,
         VariableLengthData const & userSuppliedTag) override
          {}

      /**
       * @throws FederateInternalError */
      void attributeOwnershipUnavailable (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         VariableLengthData const & userSuppliedTag) override
          {}

      /**
       * @throws FederateInternalError */
      void requestAttributeOwnershipRelease (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & candidateAttributes,
         VariableLengthData const & userSuppliedTag) override
          {}

      /**
       * @throws FederateInternalError */
      void confirmAttributeOwnershipAcquisitionCancellation (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) override
          {}

      /**
       * @throws FederateInternalError */
      void informAttributeOwnership (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         FederateHandle const & owner) override
          {}

      /**
       * @throws FederateInternalError */
      void attributeIsNotOwned (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) override
          {}

      /**
       * @throws FederateInternalError */
      void attributeIsOwnedByRTI (
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) override
          {}

      /**
       * @throws FederateInternalError */
      void timeRegulationEnabled (
         LogicalTime const & time) override
          {}

      /**
       * @throws FederateInternalError */
      void timeConstrainedEnabled (
         LogicalTime const & time) override
          {}

      /**
       * @throws FederateInternalError */
      void flushQueueGrant(
         LogicalTime const & time, LogicalTime const & optimisticTime) override
         {}

      /**
       * @throws FederateInternalError */
      void timeAdvanceGrant (
         LogicalTime const & time) override
         {}

      /**
       * @throws FederateInternalError */
      void requestRetraction (
         MessageRetractionHandle const & retraction) override
          {}
   };
}


