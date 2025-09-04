#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

// This interface is used to access the services of the RTI.

namespace rti1516_2025
{
   class RtiConfiguration;
   class FederateAmbassador;
   class LogicalTime;
   class LogicalTimeFactory;
   class LogicalTimeInterval;
   class RangeBounds;
}

#include <RTI/SpecificConfig.h>
#include <memory>
#include <string>
#include <vector>
#include <RTI/Typedefs.h>
#include <RTI/Exception.h>
#include <RTI/auth/Credentials.h>

namespace rti1516_2025
{
   class RTI_EXPORT RTIambassador
   {
   protected:
      RTIambassador ()
          noexcept;

   public:
      virtual ~RTIambassador ();

      /////////////////////////////////////
      //! Federation Management Services //
      /////////////////////////////////////

      /**
       * 4.2
       * @throws Unauthorized
       * @throws ConnectionFailed
       * @throws UnsupportedCallbackModel
       * @throws AlreadyConnected
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      virtual ConfigurationResult connect(
         FederateAmbassador & federateAmbassador,
         CallbackModel callbackModel) = 0;

      /**
       * 4.2
       * @throws Unauthorized
       * @throws ConnectionFailed
       * @throws UnsupportedCallbackModel
       * @throws AlreadyConnected
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      virtual ConfigurationResult connect(
         FederateAmbassador & federateAmbassador,
         CallbackModel callbackModel,
         RtiConfiguration const & configuration) = 0;

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
      virtual ConfigurationResult connect(
         FederateAmbassador & federateAmbassador,
         CallbackModel callbackModel,
         Credentials const & credentials) = 0;

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
      virtual ConfigurationResult connect(
         FederateAmbassador & federateAmbassador,
         CallbackModel callbackModel,
         RtiConfiguration const & configuration,
         Credentials const & credentials) = 0;

      /**
       * 4.3
       * @throws FederateIsExecutionMember
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      virtual void disconnect() = 0;

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
      virtual void createFederationExecution(
         std::wstring const & federationName,
         std::wstring const & fomModule,
         std::wstring const & logicalTimeImplementationName = L"") = 0;

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
      virtual void createFederationExecution(
         std::wstring const & federationName,
         std::vector<std::wstring> const & fomModules,
         std::wstring const & logicalTimeImplementationName = L"") = 0;

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
      virtual void createFederationExecutionWithMIM(
         std::wstring const & federationName,
         std::vector<std::wstring> const & fomModules,
         std::wstring const & mimModule,
         std::wstring const & logicalTimeImplementationName = L"") = 0;

      /**
       * 4.6
       * @throws FederatesCurrentlyJoined
       * @throws FederationExecutionDoesNotExist
       * @throws Unauthorized
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void destroyFederationExecution(
         std::wstring const & federationName) = 0;

      /**
       * 4.7
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void listFederationExecutions() = 0;

      /**
       * 4.9
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void listFederationExecutionMembers(std::wstring const & federationName) = 0;

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
      virtual FederateHandle joinFederationExecution(
         std::wstring const & federateType,
         std::wstring const & federationName,
         std::vector<std::wstring> const & additionalFomModules = std::vector<std::wstring>()) = 0;

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
      virtual FederateHandle joinFederationExecution(
         std::wstring const & federateName,
         std::wstring const & federateType,
         std::wstring const & federationName,
         std::vector<std::wstring> const & additionalFomModules = std::vector<std::wstring>()) = 0;

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
      virtual void resignFederationExecution(
         ResignAction resignAction) = 0;

      /**
       * 4.14
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void registerFederationSynchronizationPoint(
         std::wstring const & synchronizationPointLabel,
         VariableLengthData const & userSuppliedTag) = 0;

      /**
       * 4.14
       * @throws InvalidFederateHandle
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void registerFederationSynchronizationPoint(
         std::wstring const & synchronizationPointLabel,
         VariableLengthData const & userSuppliedTag,
         FederateHandleSet const & synchronizationSet) = 0;

      /**
       * 4.17
       * @throws SynchronizationPointLabelNotAnnounced
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void synchronizationPointAchieved(
         std::wstring const & synchronizationPointLabel,
         bool successfully = true) = 0;

      /**
       * 4.19
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void requestFederationSave(
         std::wstring const & label) = 0;

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
      virtual void requestFederationSave(
         std::wstring const & label,
         LogicalTime const & time) = 0;

      /**
       * 4.21
       * @throws SaveNotInitiated
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void federateSaveBegun() = 0;

      /**
       * 4.22
       * @throws FederateHasNotBegunSave
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void federateSaveComplete() = 0;

      /**
       * 4.22
       * @throws FederateHasNotBegunSave
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void federateSaveNotComplete () = 0;

      /**
       * 4.24
       * @throws SaveNotInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void abortFederationSave() = 0;

      /**
       * 4.25
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void queryFederationSaveStatus() = 0;

      /**
       * 4.27
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void requestFederationRestore(
         std::wstring const & label) = 0;

      /**
       * 4.31
       * @throws RestoreNotRequested
       * @throws SaveInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void federateRestoreComplete() = 0;

      /**
       * 4.31
       * @throws RestoreNotRequested
       * @throws SaveInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void federateRestoreNotComplete () = 0;

      /**
       * 4.33
       * @throws RestoreNotInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void abortFederationRestore() = 0;

      /**
       * 4.34
       * @throws SaveInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void queryFederationRestoreStatus() = 0;

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
      virtual void publishObjectClassAttributes(
         ObjectClassHandle const & objectClass,
         AttributeHandleSet const & attributes) = 0;

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
      virtual void unpublishObjectClass (
         ObjectClassHandle const & objectClass) = 0;

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
      virtual void unpublishObjectClassAttributes(
         ObjectClassHandle const & objectClass,
         AttributeHandleSet const & attributes) = 0;

      /**
       * 5.4
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void publishInteractionClass(
         InteractionClassHandle const & interactionClass) = 0;

      /**
       * 5.5
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void unpublishInteractionClass(
         InteractionClassHandle const & interactionClass) = 0;

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
      virtual void publishObjectClassDirectedInteractions(
         ObjectClassHandle const & objectClass,
         InteractionClassHandleSet const & interactionClasses) = 0;

      /**
       * 5.7
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void unpublishObjectClassDirectedInteractions(
         ObjectClassHandle const & objectClass) = 0;

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
      virtual void unpublishObjectClassDirectedInteractions(
         ObjectClassHandle const & objectClass,
         InteractionClassHandleSet const & interactionClasses) = 0;

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
      virtual void subscribeObjectClassAttributes(
         ObjectClassHandle const & objectClass,
         AttributeHandleSet const & attributes,
         bool active = true,
         std::wstring const & updateRateDesignator = L"") = 0;

      /**
       * 5.9
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void unsubscribeObjectClass(
         ObjectClassHandle const & objectClass) = 0;

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
      virtual void unsubscribeObjectClassAttributes(
         ObjectClassHandle const & objectClass,
         AttributeHandleSet const & attributes) = 0;

      /**
       * 5.10
       * @throws FederateServiceInvocationsAreBeingReportedViaMOM
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void subscribeInteractionClass(
         InteractionClassHandle const & interactionClass,
         bool active = true) = 0;

      /**
       * 5.11
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void unsubscribeInteractionClass(
         InteractionClassHandle const & interactionClass) = 0;

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
      virtual void subscribeObjectClassDirectedInteractions(
         ObjectClassHandle const & objectClass,
         InteractionClassHandleSet const & interactionClasses,
         bool universally = false) = 0;

      /**
       * 5.13
       * @throws ObjectClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void unsubscribeObjectClassDirectedInteractions(
         ObjectClassHandle const & objectClass) = 0;

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
      virtual void unsubscribeObjectClassDirectedInteractions(
         ObjectClassHandle const & objectClass,
         InteractionClassHandleSet const & interactionClasses) = 0;

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
      virtual void reserveObjectInstanceName(
         std::wstring const & objectInstanceName) = 0;

      /**
       * 6.4
       * @throws ObjectInstanceNameNotReserved
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void releaseObjectInstanceName(
         std::wstring const & objectInstanceName) = 0;

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
      virtual void reserveMultipleObjectInstanceNames(
         std::set<std::wstring> const & objectInstanceNames) = 0;

      /**
       * 6.7
       * @throws ObjectInstanceNameNotReserved
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void releaseMultipleObjectInstanceNames(
         std::set<std::wstring> const & objectInstanceNames) = 0;

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
      virtual ObjectInstanceHandle registerObjectInstance(
         ObjectClassHandle const & objectClass) = 0;

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
      virtual ObjectInstanceHandle registerObjectInstance(
         ObjectClassHandle const & objectClass,
         std::wstring const & objectInstanceName) = 0;

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
      virtual void updateAttributeValues(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleValueMap const & attributeValues,
         VariableLengthData const & userSuppliedTag) = 0;

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
      virtual MessageRetractionHandle updateAttributeValues(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleValueMap const & attributeValues,
         VariableLengthData const & userSuppliedTag,
         LogicalTime const & time) = 0;

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
      virtual void sendInteraction(
         InteractionClassHandle const & interactionClass,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag) = 0;

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
      virtual MessageRetractionHandle sendInteraction(
         InteractionClassHandle const & interactionClass,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         LogicalTime const & time) = 0;

      /**
       * 6.14
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
      virtual void sendDirectedInteraction(
         InteractionClassHandle const & interactionClass,
         ObjectInstanceHandle const & objectInstance,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag) = 0;

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
      virtual MessageRetractionHandle sendDirectedInteraction(
         InteractionClassHandle const & interactionClass,
         ObjectInstanceHandle const & objectInstance,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         LogicalTime const & time) = 0;

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
      virtual void deleteObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         VariableLengthData const & userSuppliedTag) = 0;

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
      virtual MessageRetractionHandle deleteObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         VariableLengthData const & userSuppliedTag,
         LogicalTime  const & time) = 0;

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
      virtual void localDeleteObjectInstance(
         ObjectInstanceHandle const & objectInstance) = 0;

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
      virtual void requestAttributeValueUpdate(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         VariableLengthData const & userSuppliedTag) = 0;

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
      virtual void requestAttributeValueUpdate(
         ObjectClassHandle const & objectClass,
         AttributeHandleSet const & attributes,
         VariableLengthData const & userSuppliedTag) = 0;

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
      virtual void requestAttributeTransportationTypeChange(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         TransportationTypeHandle const & transportationType) = 0;

      /**
       * 6.27
       * @throws AttributeNotDefined
       * @throws ObjectClassNotDefined
       * @throws InvalidTransportationTypeHandle
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void changeDefaultAttributeTransportationType(
         ObjectClassHandle const & objectClass,
         AttributeHandleSet const & attributes,
         TransportationTypeHandle const & transportationType) = 0;

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
      virtual void queryAttributeTransportationType(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandle const & attribute) = 0;

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
      virtual void requestInteractionTransportationTypeChange(
         InteractionClassHandle const & interactionClass,
         TransportationTypeHandle const & transportationType) = 0;

      /**
       * 6.32
       * @throws InteractionClassNotDefined
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void queryInteractionTransportationType(
         FederateHandle const & federate,
         InteractionClassHandle const & interactionClass) = 0;


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
      virtual void unconditionalAttributeOwnershipDivestiture(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         VariableLengthData const & userSuppliedTag) = 0;

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
      virtual void negotiatedAttributeOwnershipDivestiture(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         VariableLengthData const & userSuppliedTag) = 0;

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
      virtual void confirmDivestiture(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & confirmedAttributes,
         VariableLengthData const & userSuppliedTag) = 0;

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
      virtual void attributeOwnershipAcquisition(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & desiredAttributes,
         VariableLengthData const & userSuppliedTag) = 0;

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
      virtual void attributeOwnershipAcquisitionIfAvailable(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & desiredAttributes,
         VariableLengthData const & userSuppliedTag) = 0;

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
      virtual void attributeOwnershipReleaseDenied(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         VariableLengthData const & userSuppliedTag) = 0;

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
      virtual void attributeOwnershipDivestitureIfWanted(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         VariableLengthData const & userSuppliedTag,
         AttributeHandleSet & divestedAttributes) = 0; // filled by RTI

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
      virtual void cancelNegotiatedAttributeOwnershipDivestiture(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) = 0;

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
      virtual void cancelAttributeOwnershipAcquisition(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) = 0;

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
      virtual void queryAttributeOwnership(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes) = 0;

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
      virtual bool isAttributeOwnedByFederate(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandle const & attribute) = 0;

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
      virtual void enableTimeRegulation(
         LogicalTimeInterval const & lookahead) = 0;

      /**
       * 8.4
       * @throws TimeRegulationIsNotEnabled
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void disableTimeRegulation() = 0;

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
      virtual void enableTimeConstrained() = 0;

      /**
       * 8.7
       * @throws TimeConstrainedIsNotEnabled
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void disableTimeConstrained() = 0;

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
      virtual void timeAdvanceRequest(
         LogicalTime const & time) = 0;

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
      virtual void timeAdvanceRequestAvailable(
         LogicalTime const & time) = 0;

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
      virtual void nextMessageRequest(
         LogicalTime const & time) = 0;

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
      virtual void nextMessageRequestAvailable(
         LogicalTime const & time) = 0;

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
      virtual void flushQueueRequest(
         LogicalTime const & time) = 0;

      /**
       * 8.15
       * @throws AsynchronousDeliveryAlreadyEnabled
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void enableAsynchronousDelivery() = 0;

      /**
       * 8.16
       * @throws AsynchronousDeliveryAlreadyDisabled
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void disableAsynchronousDelivery() = 0;

      /**
       * 8.17
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool queryGALT(LogicalTime & time) = 0;

      /**
       * 8.18
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void queryLogicalTime(LogicalTime & time) = 0;

      /**
       * 8.19
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool queryLITS(LogicalTime & time) = 0;

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
      virtual void modifyLookahead(
         LogicalTimeInterval const & lookahead) = 0;

      /**
       * 8.21
       * @throws TimeRegulationIsNotEnabled
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void queryLookahead(LogicalTimeInterval & interval) = 0;

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
      virtual void retract(
         MessageRetractionHandle const & retraction) = 0;

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
      virtual void changeAttributeOrderType(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         OrderType orderType) = 0;

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
      virtual void changeDefaultAttributeOrderType(
         ObjectClassHandle const & objectClass,
         AttributeHandleSet const & attributes,
         OrderType orderType) = 0;

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
      virtual void changeInteractionOrderType(
         InteractionClassHandle const & interactionClass,
         OrderType orderType) = 0;

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
      virtual RegionHandle createRegion(
         DimensionHandleSet const & dimensions) = 0;

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
      virtual void commitRegionModifications(
         RegionHandleSet const & regions) = 0;

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
      virtual void deleteRegion(
         RegionHandle const & region) = 0;

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
      virtual ObjectInstanceHandle registerObjectInstanceWithRegions(
         ObjectClassHandle const & objectClass,
         AttributeHandleSetRegionHandleSetPairVector const & attributesAndRegions) = 0;

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
      virtual ObjectInstanceHandle registerObjectInstanceWithRegions(
         ObjectClassHandle const & objectClass,
         AttributeHandleSetRegionHandleSetPairVector const & attributesAndRegions,
         std::wstring const & objectInstanceName) = 0;

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
      virtual void associateRegionsForUpdates(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSetRegionHandleSetPairVector const & attributesAndRegions) = 0;

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
      virtual void unassociateRegionsForUpdates(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSetRegionHandleSetPairVector const & attributesAndRegions) = 0;

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
      virtual void subscribeObjectClassAttributesWithRegions(
         ObjectClassHandle const & objectClass,
         AttributeHandleSetRegionHandleSetPairVector const & attributesAndRegions,
         bool active = true,
         std::wstring const & updateRateDesignator = L"") = 0;


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
      virtual void unsubscribeObjectClassAttributesWithRegions(
         ObjectClassHandle const & objectClass,
         AttributeHandleSetRegionHandleSetPairVector const & attributesAndRegions) = 0;

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
      virtual void subscribeInteractionClassWithRegions(
         InteractionClassHandle const & interactionClass,
         RegionHandleSet const & regions,
         bool active = true) = 0;

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
      virtual void unsubscribeInteractionClassWithRegions(
         InteractionClassHandle const & interactionClass,
         RegionHandleSet const & regions) = 0;

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
      virtual void sendInteractionWithRegions(
         InteractionClassHandle const & interactionClass,
         ParameterHandleValueMap const & parameterValues,
         RegionHandleSet const & regions,
         VariableLengthData const & userSuppliedTag) = 0;

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
      virtual MessageRetractionHandle sendInteractionWithRegions(
         InteractionClassHandle const & interactionClass,
         ParameterHandleValueMap const & parameterValues,
         RegionHandleSet const & regions,
         VariableLengthData const & userSuppliedTag,
         LogicalTime const & time) = 0;

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
      virtual void requestAttributeValueUpdateWithRegions(
         ObjectClassHandle const & objectClass,
         AttributeHandleSetRegionHandleSetPairVector const & attributesAndRegions,
         VariableLengthData const & userSuppliedTag) = 0;

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
      virtual FederateHandle getFederateHandle(
         std::wstring const & federateName) = 0;

      /**
       * 10.3
       * @throws InvalidFederateHandle
       * @throws FederateHandleNotKnown
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual std::wstring getFederateName(
         FederateHandle const & federate) = 0;

      /**
       * 10.4
       * @throws NameNotFound
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual ObjectClassHandle getObjectClassHandle(
         std::wstring const & objectClassName) = 0;

      /**
       * 10.5
       * @throws InvalidObjectClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual std::wstring getObjectClassName(
         ObjectClassHandle const & objectClass) = 0;

      /**
       * 10.6
       * @throws ObjectInstanceNotKnown
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual ObjectClassHandle getKnownObjectClassHandle(
         ObjectInstanceHandle const & objectInstance) = 0;

      /**
       * 10.7
       * @throws ObjectInstanceNotKnown
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual ObjectInstanceHandle getObjectInstanceHandle(
         std::wstring const & objectInstanceName) = 0;

      /**
       * 10.8
       * @throws ObjectInstanceNotKnown
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual std::wstring getObjectInstanceName(
         ObjectInstanceHandle const & objectInstance) = 0;

      /**
       * 10.9
       * @throws NameNotFound
       * @throws InvalidObjectClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual AttributeHandle getAttributeHandle(
         ObjectClassHandle const & objectClass,
         std::wstring const & attributeName) = 0;

      /**
       * 10.10
       * @throws AttributeNotDefined
       * @throws InvalidAttributeHandle
       * @throws InvalidObjectClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual std::wstring getAttributeName(
         ObjectClassHandle const & objectClass,
         AttributeHandle const & attribute) = 0;

      /**
       * 10.11
       * @throws InvalidUpdateRateDesignator
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual double getUpdateRateValue(
         std::wstring const & updateRateDesignator) = 0;

      /**
       * 10.12
       * @throws ObjectInstanceNotKnown
       * @throws AttributeNotDefined
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual double getUpdateRateValueForAttribute(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandle const & attribute) = 0;

      /**
       * 10.13
       * @throws NameNotFound
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual InteractionClassHandle getInteractionClassHandle(
         std::wstring const & interactionClassName) = 0;

      /**
       * 10.14
       * @throws InvalidInteractionClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual std::wstring getInteractionClassName(
         InteractionClassHandle const & interactionClass) = 0;

      /**
       * 10.15
       * @throws NameNotFound
       * @throws InvalidInteractionClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual ParameterHandle getParameterHandle(
         InteractionClassHandle const & interactionClass,
         std::wstring const & parameterName) = 0;

      /**
       * 10.16
       * @throws InteractionParameterNotDefined
       * @throws InvalidParameterHandle
       * @throws InvalidInteractionClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual std::wstring getParameterName(
         InteractionClassHandle const & interactionClass,
         ParameterHandle const & parameter) = 0;

      /**
       * 10.17
       * @throws InvalidOrderName
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual OrderType getOrderType(
         std::wstring const & orderTypeName) = 0;

      /**
       * 10.18
       * @throws InvalidOrderType
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual std::wstring getOrderName(
         OrderType orderType) = 0;

      /**
       * 10.19
       * @throws InvalidTransportationName
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual TransportationTypeHandle getTransportationTypeHandle(
         std::wstring const & transportationTypeName) = 0;

      /**
       * 10.20
       * @throws InvalidTransportationTypeHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual std::wstring getTransportationTypeName(
         TransportationTypeHandle const & transportationType) = 0;

      /**
       * 10.21
       * @throws InvalidObjectClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual DimensionHandleSet getAvailableDimensionsForObjectClass(
       ObjectClassHandle const & objectClass) = 0;

      /**
       * 10.22
       * @throws InvalidInteractionClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual DimensionHandleSet getAvailableDimensionsForInteractionClass(
         InteractionClassHandle const & interactionClass) = 0;

      /**
       * 10.23
       * @throws NameNotFound
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual DimensionHandle getDimensionHandle(
         std::wstring const & dimensionName) = 0;

      /**
       * 10.24
       * @throws InvalidDimensionHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual std::wstring getDimensionName(
         DimensionHandle const & dimension) = 0;

      /**
       * 10.25
       * @throws InvalidDimensionHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual unsigned long getDimensionUpperBound(
         DimensionHandle const & dimension) = 0;

      /**
       * 10.26
       * @throws InvalidRegion
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual DimensionHandleSet getDimensionHandleSet(
         RegionHandle const & region) = 0;

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
      virtual RangeBounds getRangeBounds(
         RegionHandle const & region,
         DimensionHandle const & dimension) = 0;

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
      virtual void setRangeBounds(
         RegionHandle const & region,
         DimensionHandle const & dimension,
         RangeBounds const & rangeBounds) = 0;

      /**
       * 10.29
       * @throws InvalidServiceGroup
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual unsigned long normalizeServiceGroup(
         ServiceGroup serviceGroup) = 0;

      /**
       * 10.30
       * @throws InvalidFederateHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual unsigned long normalizeFederateHandle(
         FederateHandle const & federate) = 0;

      /**
       * 10.31
       * @throws InvalidObjectClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual unsigned long normalizeObjectClassHandle(
         ObjectClassHandle const & objectClass) = 0;

      /**
       * 10.32
       * @throws InvalidInteractionClassHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual unsigned long normalizeInteractionClassHandle(
         InteractionClassHandle const & interactionClass) = 0;

      /**
       * 10.33
       * @throws InvalidObjectInstanceHandle
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual unsigned long normalizeObjectInstanceHandle(
         ObjectInstanceHandle const & objectInstance) = 0;

      /**
       * 10.34
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool getObjectClassRelevanceAdvisorySwitch() const = 0;

      /**
       * 10.35
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void setObjectClassRelevanceAdvisorySwitch(bool switchValue) = 0;

      /**
       * 10.36
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool getAttributeRelevanceAdvisorySwitch() const = 0;

      /**
       * 10.37
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void setAttributeRelevanceAdvisorySwitch(bool switchValue) = 0;

      /**
       * 10.38
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool getAttributeScopeAdvisorySwitch() const = 0;

      /**
       * 10.39
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void setAttributeScopeAdvisorySwitch(bool switchValue) = 0;

      /**
       * 10.40
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool getInteractionRelevanceAdvisorySwitch() const = 0;

      /**
       * 10.41
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void setInteractionRelevanceAdvisorySwitch(bool switchValue) = 0;

      /**
       * 10.42
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool getConveyRegionDesignatorSetsSwitch() const = 0;

      /**
       * 10.43
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void setConveyRegionDesignatorSetsSwitch(bool switchValue) = 0;

      /**
       * 10.44
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual ResignAction getAutomaticResignDirective() = 0;

      /**
       * 10.45
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws InvalidResignAction
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void setAutomaticResignDirective(
         ResignAction resignAction) = 0;

      /**
       * 10.46
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool getServiceReportingSwitch() const = 0;

      /**
       * 10.47
       * @throws ReportServiceInvocationsAreSubscribed
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void setServiceReportingSwitch(bool switchValue) = 0;

      /**
       * 10.48
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool getExceptionReportingSwitch() const = 0;

      /**
       * 10.49
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void setExceptionReportingSwitch(bool switchValue) = 0;

      /**
       * 10.50
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool getSendServiceReportsToFileSwitch() const = 0;

      /**
       * 10.51
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual void setSendServiceReportsToFileSwitch(bool switchValue) = 0;

      /**
       * 10.52
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool getAutoProvideSwitch() const = 0;

      /**
       * 10.53
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool getDelaySubscriptionEvaluationSwitch() const = 0;

      /**
       * 10.54
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool getAdvisoriesUseKnownClassSwitch() const = 0;

      /**
       * 10.55
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool getAllowRelaxedDDMSwitch() const = 0;

      /**
       * 10.56
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual bool getNonRegulatedGrantSwitch() const = 0;

      /**
       * 10.57
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      virtual bool evokeCallback(
         double approximateMinimumTimeInSeconds) = 0;

      /**
       * 10.58
       * @throws CallNotAllowedFromWithinCallback
       * @throws RTIinternalError
       */
      virtual bool evokeMultipleCallbacks(
         double approximateMinimumTimeInSeconds,
         double approximateMaximumTimeInSeconds) = 0;

      /**
       * 10.59
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws RTIinternalError
       */
      virtual void enableCallbacks() = 0;

      /**
       * 10.60
       * @throws SaveInProgress
       * @throws RestoreInProgress
       * @throws RTIinternalError
       */
      virtual void disableCallbacks() = 0;

      ////////////////////////////
      //! API-specific services //
      ////////////////////////////

      //! Return instance of time factory being used by the federation
      /**
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       */
      virtual std::unique_ptr<LogicalTimeFactory> getTimeFactory () const = 0;

      //! Decode handles
      //@{

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual FederateHandle decodeFederateHandle (
         VariableLengthData const & encodedValue) const = 0;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual ObjectClassHandle decodeObjectClassHandle (
         VariableLengthData const & encodedValue) const = 0;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual InteractionClassHandle decodeInteractionClassHandle (
         VariableLengthData const & encodedValue) const = 0;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual ObjectInstanceHandle decodeObjectInstanceHandle (
         VariableLengthData const & encodedValue) const = 0;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual AttributeHandle decodeAttributeHandle (
         VariableLengthData const & encodedValue) const = 0;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual ParameterHandle decodeParameterHandle (
         VariableLengthData const & encodedValue) const = 0;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual DimensionHandle decodeDimensionHandle (
         VariableLengthData const & encodedValue) const = 0;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual MessageRetractionHandle decodeMessageRetractionHandle (
         VariableLengthData const & encodedValue) const = 0;

      /**
       * @throws CouldNotDecode
       * @throws FederateNotExecutionMember
       * @throws NotConnected
       * @throws RTIinternalError
       */
      virtual RegionHandle decodeRegionHandle (
         VariableLengthData const & encodedValue) const = 0;

      //@}
   };
}

