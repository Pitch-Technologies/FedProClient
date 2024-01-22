/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

//File: RTIambassador.java

package hla.rti1516_202X;

import hla.rti1516_202X.auth.Credentials;
import hla.rti1516_202X.exceptions.*;
import hla.rti1516_202X.time.LogicalTime;
import hla.rti1516_202X.time.LogicalTimeFactory;
import hla.rti1516_202X.time.LogicalTimeInterval;

import java.util.Set;

/*
 Memory Management Conventions for Parameters

 All Java parameters, including object references, are passed by value.
 Therefore, there is no need to specify further conventions for primitive types.

 Unless otherwise noted, reference parameters adhere to the following convention:
 The referenced object is created (or acquired) by the caller. The callee must
 copy during the call anything it wishes to save beyond the completion of the
 call.

 Unless otherwise noted, a reference returned from a method represents a new
 object created by the callee. The caller is free to modify the object whose
 reference is returned.

 */

/**
 * The RTI presents this interface to the federate.
 * RTI implementer must implement this.
 */

public interface RTIambassador {

////////////////////////////////////
// Federation Management Services //
////////////////////////////////////

   // 4.2
   ConfigurationResult connect(FederateAmbassador federateAmbassador,
                               CallbackModel callbackModel)
      throws
      Unauthorized,
      ConnectionFailed,
      UnsupportedCallbackModel,
      AlreadyConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError;

   // 4.2
   ConfigurationResult connect(FederateAmbassador federateAmbassador,
                               CallbackModel callbackModel,
                               RtiConfiguration configuration)
      throws
      Unauthorized,
      ConnectionFailed,
      UnsupportedCallbackModel,
      AlreadyConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError;

   // 4.2
   ConfigurationResult connect(FederateAmbassador federateAmbassador,
                               CallbackModel callbackModel,
                               Credentials credentials)
      throws
      Unauthorized,
      InvalidCredentials,
      ConnectionFailed,
      UnsupportedCallbackModel,
      AlreadyConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError;

   // 4.2
   ConfigurationResult connect(FederateAmbassador federateAmbassador,
                               CallbackModel callbackModel,
                               RtiConfiguration configuration,
                               Credentials credentials)
      throws
      Unauthorized,
      InvalidCredentials,
      ConnectionFailed,
      UnsupportedCallbackModel,
      AlreadyConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError;

   // 4.3
   void disconnect()
      throws
      FederateIsExecutionMember,
      CallNotAllowedFromWithinCallback,
      RTIinternalError;

   // 4.6
   void createFederationExecution(String federationName, 
                                  String fomModule)
      throws
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      FederationExecutionAlreadyExists,
      Unauthorized,
      NotConnected,
      RTIinternalError;

   // 4.6
   void createFederationExecution(String federationName,
                                  String fomModule,
                                  String logicalTimeImplementationName)
      throws
      CouldNotCreateLogicalTimeFactory,
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      FederationExecutionAlreadyExists,
      Unauthorized,
      NotConnected,
      RTIinternalError;

   // 4.6
   void createFederationExecution(String federationName, 
                                  String[] fomModules)
      throws
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      FederationExecutionAlreadyExists,
      Unauthorized,
      NotConnected,
      RTIinternalError;

   // 4.6
   void createFederationExecution(String federationName,
                                  String[] fomModules,
                                  String logicalTimeImplementationName)
      throws
      CouldNotCreateLogicalTimeFactory,
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      FederationExecutionAlreadyExists,
      Unauthorized,
      NotConnected,
      RTIinternalError;

   // 4.6
   void createFederationExecutionWithMIM(String federationName, 
                                         String[] fomModules, 
                                         String mimModule)
      throws
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      InvalidMIM,
      ErrorReadingMIM,
      CouldNotOpenMIM,
      DesignatorIsHLAstandardMIM,
      FederationExecutionAlreadyExists,
      Unauthorized,
      NotConnected,
      RTIinternalError;

   // 4.6
   void createFederationExecutionWithMIM(String federationName,
                                         String[] fomModules,
                                         String mimModule,
                                         String logicalTimeImplementationName)
      throws
      CouldNotCreateLogicalTimeFactory,
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      InvalidMIM,
      ErrorReadingMIM,
      CouldNotOpenMIM,
      DesignatorIsHLAstandardMIM,
      FederationExecutionAlreadyExists,
      Unauthorized,
      NotConnected,
      RTIinternalError;

   // 4.7
   void destroyFederationExecution(String federationName)
      throws
      FederatesCurrentlyJoined,
      FederationExecutionDoesNotExist,
      Unauthorized,
      NotConnected,
      RTIinternalError;

   // 4.8
   void listFederationExecutions()
      throws
      NotConnected,
      RTIinternalError;

   // 4.10
   void listFederationExecutionMembers(String federationName)
      throws
      NotConnected,
      RTIinternalError;

   // 4.12
   FederateHandle joinFederationExecution(String federateType, 
                                          String federationName)
      throws
      CouldNotCreateLogicalTimeFactory,
      FederationExecutionDoesNotExist,
      SaveInProgress,
      RestoreInProgress,
      FederateAlreadyExecutionMember,
      Unauthorized,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError;

   // 4.12
   FederateHandle joinFederationExecution(String federateType, 
                                          String federationName, 
                                          String[] additionalFomModules)
      throws
      CouldNotCreateLogicalTimeFactory,
      FederationExecutionDoesNotExist,
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      SaveInProgress,
      RestoreInProgress,
      FederateAlreadyExecutionMember,
      Unauthorized,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError;

   // 4.12
   FederateHandle joinFederationExecution(String federateName, 
                                          String federateType, 
                                          String federationName)
      throws
      CouldNotCreateLogicalTimeFactory,
      FederateNameAlreadyInUse,
      FederationExecutionDoesNotExist,
      SaveInProgress,
      RestoreInProgress,
      FederateAlreadyExecutionMember,
      Unauthorized,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError;

   // 4.12
   FederateHandle joinFederationExecution(String federateName,
                                          String federateType,
                                          String federationName,
                                          String[] additionalFomModules)
      throws
      CouldNotCreateLogicalTimeFactory,
      FederateNameAlreadyInUse,
      FederationExecutionDoesNotExist,
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      SaveInProgress,
      RestoreInProgress,
      FederateAlreadyExecutionMember,
      Unauthorized,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError;

   // 4.13
   void resignFederationExecution(ResignAction resignAction)
      throws
      InvalidResignAction,
      OwnershipAcquisitionPending,
      FederateOwnsAttributes,
      FederateNotExecutionMember,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError;

   // 4.14
   void registerFederationSynchronizationPoint(String synchronizationPointLabel,
                                               byte[] userSuppliedTag)
      throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.14
   void registerFederationSynchronizationPoint(String synchronizationPointLabel,
                                               byte[] userSuppliedTag,
                                               FederateHandleSet synchronizationSet)
      throws
      InvalidFederateHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.17
   void synchronizationPointAchieved(String synchronizationPointLabel)
      throws
      SynchronizationPointLabelNotAnnounced,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.17
   void synchronizationPointAchieved(String synchronizationPointLabel,
                                     boolean successfully)
      throws
      SynchronizationPointLabelNotAnnounced,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.19
   void requestFederationSave(String label)
      throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.19
   void requestFederationSave(String label,
                              LogicalTime<?, ?> time)
      throws
      LogicalTimeAlreadyPassed,
      InvalidLogicalTime,
      FederateUnableToUseTime,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.21
   void federateSaveBegun()
      throws
      SaveNotInitiated,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.22
   void federateSaveComplete()
      throws
      FederateHasNotBegunSave,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.22
   void federateSaveNotComplete()
      throws
      FederateHasNotBegunSave,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.24
   void abortFederationSave()
      throws
      SaveNotInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.25
   void queryFederationSaveStatus()
      throws
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.27
   void requestFederationRestore(String label)
      throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.31
   void federateRestoreComplete()
      throws
      RestoreNotRequested,
      SaveInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.31
   void federateRestoreNotComplete()
      throws
      RestoreNotRequested,
      SaveInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.33
   void abortFederationRestore()
      throws
      RestoreNotInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 4.34
   void queryFederationRestoreStatus()
      throws
      SaveInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;


/////////////////////////////////////
// Declaration Management Services //
/////////////////////////////////////

   // 5.2
   void publishObjectClassAttributes(ObjectClassHandle objectClass,
                                     AttributeHandleSet attributes)
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.3
   void unpublishObjectClass(ObjectClassHandle objectClass)
      throws
      OwnershipAcquisitionPending,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.3
   void unpublishObjectClassAttributes(ObjectClassHandle objectClass,
                                       AttributeHandleSet attributes)
      throws
      OwnershipAcquisitionPending,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   void publishObjectClassDirectedInteractions(ObjectClassHandle objectClass,
                                               InteractionClassHandleSet interactionClasses)
      throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.3
   void unpublishObjectClassDirectedInteractions(ObjectClassHandle objectClass)
      throws
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.3
   void unpublishObjectClassDirectedInteractions(ObjectClassHandle objectClass,
                                                 InteractionClassHandleSet interactionClasses)
      throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.4
   void publishInteractionClass(InteractionClassHandle interactionClass)
      throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.5
   void unpublishInteractionClass(InteractionClassHandle interactionClass)
      throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.6
   void subscribeObjectClassAttributes(ObjectClassHandle objectClass,
                                       AttributeHandleSet attributes)
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.6
   void subscribeObjectClassAttributes(ObjectClassHandle objectClass,
                                       AttributeHandleSet attributes,
                                       String updateRateDesignator)
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      InvalidUpdateRateDesignator,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.6
   void subscribeObjectClassAttributesPassively(ObjectClassHandle objectClass,
                                                AttributeHandleSet attributes)
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.6
   void subscribeObjectClassAttributesPassively(ObjectClassHandle objectClass,
                                                AttributeHandleSet attributes,
                                                String updateRateDesignator)
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      InvalidUpdateRateDesignator,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.7
   void unsubscribeObjectClass(ObjectClassHandle objectClass)
      throws
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.7
   void unsubscribeObjectClassAttributes(ObjectClassHandle objectClass,
                                         AttributeHandleSet attributes)
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.6
   void subscribeObjectClassDirectedInteractions(ObjectClassHandle objectClass,
                                                 InteractionClassHandleSet interactionClasses)
      throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.7
   void unsubscribeObjectClassDirectedInteractions(ObjectClassHandle objectClass)
      throws
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.7
   void unsubscribeObjectClassDirectedInteractions(ObjectClassHandle objectClass,
                                                   InteractionClassHandleSet interactionClasses)
      throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.8
   void subscribeInteractionClass(InteractionClassHandle interactionClass)
      throws
      FederateServiceInvocationsAreBeingReportedViaMOM,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.8
   void subscribeInteractionClassPassively(InteractionClassHandle interactionClass)
      throws
      FederateServiceInvocationsAreBeingReportedViaMOM,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 5.9
   void unsubscribeInteractionClass(InteractionClassHandle interactionClass)
      throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

////////////////////////////////
// Object Management Services //
////////////////////////////////

   // 6.2
   void reserveObjectInstanceName(String objectInstanceName)
      throws
      IllegalName,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.4
   void releaseObjectInstanceName(String objectInstanceName)
      throws
      ObjectInstanceNameNotReserved,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.5
   void reserveMultipleObjectInstanceName(Set<String> objectInstanceNames)
      throws
      IllegalName,
      NameSetWasEmpty,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.7
   void releaseMultipleObjectInstanceName(Set<String> objectInstanceNames)
      throws
      ObjectInstanceNameNotReserved,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.8
   ObjectInstanceHandle registerObjectInstance(ObjectClassHandle objectClass)
      throws
      ObjectClassNotPublished,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.8
   ObjectInstanceHandle registerObjectInstance(ObjectClassHandle objectClass,
                                               String objectInstanceName)
      throws
      ObjectInstanceNameInUse,
      ObjectInstanceNameNotReserved,
      ObjectClassNotPublished,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.10
   void updateAttributeValues(ObjectInstanceHandle objectInstance,
                              AttributeHandleValueMap attributeValues,
                              byte[] userSuppliedTag)
      throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.10
   MessageRetractionReturn updateAttributeValues(ObjectInstanceHandle objectInstance,
                                                 AttributeHandleValueMap attributeValues,
                                                 byte[] userSuppliedTag,
                                                 LogicalTime<?, ?> time)
      throws
      InvalidLogicalTime,
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.12
   void sendInteraction(InteractionClassHandle interactionClass,
                        ParameterHandleValueMap parameterValues,
                        byte[] userSuppliedTag)
      throws
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.12
   MessageRetractionReturn sendInteraction(InteractionClassHandle interactionClass,
                                           ParameterHandleValueMap parameterValues,
                                           byte[] userSuppliedTag,
                                           LogicalTime<?, ?> time)
      throws
      InvalidLogicalTime,
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   void sendDirectedInteraction(InteractionClassHandle interactionClass,
                                ObjectInstanceHandle objectInstance,
                                ParameterHandleValueMap parameterValues,
                                byte[] userSuppliedTag)
      throws
      ObjectInstanceNotKnown,
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.12
   MessageRetractionReturn sendDirectedInteraction(InteractionClassHandle interactionClass,
                                                   ObjectInstanceHandle objectInstance,
                                                   ParameterHandleValueMap parameterValues,
                                                   byte[] userSuppliedTag,
                                                   LogicalTime<?, ?> time)
      throws
      InvalidLogicalTime,
      ObjectInstanceNotKnown,
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.14
   void deleteObjectInstance(ObjectInstanceHandle objectInstance,
                             byte[] userSuppliedTag)
      throws
      DeletePrivilegeNotHeld,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.14
   MessageRetractionReturn deleteObjectInstance(ObjectInstanceHandle objectInstance,
                                                byte[] userSuppliedTag,
                                                LogicalTime<?, ?> time)
      throws
      InvalidLogicalTime,
      DeletePrivilegeNotHeld,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.16
   void localDeleteObjectInstance(ObjectInstanceHandle objectInstance)
      throws
      OwnershipAcquisitionPending,
      FederateOwnsAttributes,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.19
   void requestAttributeValueUpdate(ObjectInstanceHandle objectInstance,
                                    AttributeHandleSet attributes,
                                    byte[] userSuppliedTag)
      throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.19
   void requestAttributeValueUpdate(ObjectClassHandle objectClass,
                                    AttributeHandleSet attributes,
                                    byte[] userSuppliedTag)
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.23
   void requestAttributeTransportationTypeChange(ObjectInstanceHandle objectInstance,
                                                 AttributeHandleSet attributes,
                                                 TransportationTypeHandle transportationType)
      throws
      AttributeAlreadyBeingChanged,
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      InvalidTransportationTypeHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.25
   void changeDefaultAttributeTransportationType(ObjectClassHandle objectClass,
                                                 AttributeHandleSet attributes,
                                                 TransportationTypeHandle transportationType)
      throws
      AttributeAlreadyBeingChanged,
      AttributeNotDefined,
      ObjectClassNotDefined,
      InvalidTransportationTypeHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.26
   void queryAttributeTransportationType(ObjectInstanceHandle objectInstance,
                                         AttributeHandle attribute)
      throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.28
   void requestInteractionTransportationTypeChange(InteractionClassHandle interactionClass,
                                                   TransportationTypeHandle transportationType)
      throws
      InteractionClassAlreadyBeingChanged,
      InteractionClassNotPublished,
      InteractionClassNotDefined,
      InvalidTransportationTypeHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 6.30
   void queryInteractionTransportationType(FederateHandle federate,
                                           InteractionClassHandle interactionClass)
      throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

///////////////////////////////////
// Ownership Management Services //
///////////////////////////////////

   // 7.2
   void unconditionalAttributeOwnershipDivestiture(ObjectInstanceHandle objectInstance,
                                                   AttributeHandleSet attributes,
                                                   byte[] userSuppliedTag)
      throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 7.3
   void negotiatedAttributeOwnershipDivestiture(ObjectInstanceHandle objectInstance,
                                                AttributeHandleSet attributes,
                                                byte[] userSuppliedTag)
      throws
      AttributeAlreadyBeingDivested,
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 7.6
   void confirmDivestiture(ObjectInstanceHandle objectInstance,
                           AttributeHandleSet confirmedAttributes,
                           byte[] userSuppliedTag)
      throws
      NoAcquisitionPending,
      AttributeDivestitureWasNotRequested,
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 7.8
   void attributeOwnershipAcquisition(ObjectInstanceHandle objectInstance,
                                      AttributeHandleSet desiredAttributes,
                                      byte[] userSuppliedTag)
      throws
      AttributeNotPublished,
      ObjectClassNotPublished,
      FederateOwnsAttributes,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 7.9
   void attributeOwnershipAcquisitionIfAvailable(ObjectInstanceHandle objectInstance,
                                                 AttributeHandleSet desiredAttributes,
                                                 byte[] userSuppliedTag)
      throws
      AttributeAlreadyBeingAcquired,
      AttributeNotPublished,
      ObjectClassNotPublished,
      FederateOwnsAttributes,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 7.12
   void attributeOwnershipReleaseDenied(ObjectInstanceHandle objectInstance,
                                        AttributeHandleSet attributes,
                                        byte[] userSuppliedTag)
      throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 7.13
   AttributeHandleSet attributeOwnershipDivestitureIfWanted(ObjectInstanceHandle objectInstance,
                                                            AttributeHandleSet attributes,
                                                            byte[] userSuppliedTag)
      throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 7.14
   void cancelNegotiatedAttributeOwnershipDivestiture(ObjectInstanceHandle objectInstance,
                                                      AttributeHandleSet attributes)
      throws
      AttributeDivestitureWasNotRequested,
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 7.15
   void cancelAttributeOwnershipAcquisition(ObjectInstanceHandle objectInstance,
                                            AttributeHandleSet attributes)
      throws
      AttributeAcquisitionWasNotRequested,
      AttributeAlreadyOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 7.17
   void queryAttributeOwnership(ObjectInstanceHandle objectInstance,
                                AttributeHandleSet attributes)
      throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 7.19
   boolean isAttributeOwnedByFederate(ObjectInstanceHandle objectInstance,
                                      AttributeHandle attribute)
      throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

//////////////////////////////
// Time Management Services //
//////////////////////////////

   // 8.2
   void enableTimeRegulation(LogicalTimeInterval<?> lookahead)
      throws
      InvalidLookahead,
      InTimeAdvancingState,
      RequestForTimeRegulationPending,
      TimeRegulationAlreadyEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.4
   void disableTimeRegulation()
      throws
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.5
   void enableTimeConstrained()
      throws
      InTimeAdvancingState,
      RequestForTimeConstrainedPending,
      TimeConstrainedAlreadyEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.7
   void disableTimeConstrained()
      throws
      TimeConstrainedIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.8
   void timeAdvanceRequest(LogicalTime<?, ?> time)
      throws
      LogicalTimeAlreadyPassed,
      InvalidLogicalTime,
      InTimeAdvancingState,
      RequestForTimeRegulationPending,
      RequestForTimeConstrainedPending,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.9
   void timeAdvanceRequestAvailable(LogicalTime<?, ?> time)
      throws
      LogicalTimeAlreadyPassed,
      InvalidLogicalTime,
      InTimeAdvancingState,
      RequestForTimeRegulationPending,
      RequestForTimeConstrainedPending,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.10
   void nextMessageRequest(LogicalTime<?, ?> time)
      throws
      LogicalTimeAlreadyPassed,
      InvalidLogicalTime,
      InTimeAdvancingState,
      RequestForTimeRegulationPending,
      RequestForTimeConstrainedPending,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.11
   void nextMessageRequestAvailable(LogicalTime<?, ?> time)
      throws
      LogicalTimeAlreadyPassed,
      InvalidLogicalTime,
      InTimeAdvancingState,
      RequestForTimeRegulationPending,
      RequestForTimeConstrainedPending,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.12
   void flushQueueRequest(LogicalTime<?, ?> time)
      throws
      LogicalTimeAlreadyPassed,
      InvalidLogicalTime,
      InTimeAdvancingState,
      RequestForTimeRegulationPending,
      RequestForTimeConstrainedPending,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.15
   void enableAsynchronousDelivery()
      throws
      AsynchronousDeliveryAlreadyEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.16
   void disableAsynchronousDelivery()
      throws
      AsynchronousDeliveryAlreadyDisabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.17
   TimeQueryReturn queryGALT()
      throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.18
   LogicalTime<?, ?> queryLogicalTime()
      throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.19
   TimeQueryReturn queryLITS()
      throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.20
   void modifyLookahead(LogicalTimeInterval<?> lookahead)
      throws
      InvalidLookahead,
      InTimeAdvancingState,
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.21
   LogicalTimeInterval<?> queryLookahead()
      throws
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.22
   void retract(MessageRetractionHandle retraction)
      throws
      MessageCanNoLongerBeRetracted,
      InvalidMessageRetractionHandle,
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.24
   void changeAttributeOrderType(ObjectInstanceHandle objectInstance,
                                 AttributeHandleSet attributes,
                                 OrderType orderType)
      throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.25
   void changeDefaultAttributeOrderType(ObjectClassHandle objectClass,
                                        AttributeHandleSet attributes,
                                        OrderType orderType)
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 8.26
   void changeInteractionOrderType(InteractionClassHandle interactionClass,
                                   OrderType orderType)
      throws
      InteractionClassNotPublished,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

//////////////////////////////////
// Data Distribution Management //
//////////////////////////////////

   // 9.2
   RegionHandle createRegion(DimensionHandleSet dimensions)
      throws
      InvalidDimensionHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.3
   void commitRegionModifications(RegionHandleSet regions)
      throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.4
   void deleteRegion(RegionHandle region)
      throws
      RegionInUseForUpdateOrSubscription,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.5
   ObjectInstanceHandle registerObjectInstanceWithRegions(ObjectClassHandle objectClass,
                                                          AttributeSetRegionSetPairList attributesAndRegions)
      throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotPublished,
      ObjectClassNotPublished,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.5
   ObjectInstanceHandle registerObjectInstanceWithRegions(ObjectClassHandle objectClass,
                                                          AttributeSetRegionSetPairList attributesAndRegions,
                                                          String objectInstanceName)
      throws
      ObjectInstanceNameInUse,
      ObjectInstanceNameNotReserved,
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotPublished,
      ObjectClassNotPublished,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.6
   void associateRegionsForUpdates(ObjectInstanceHandle objectInstance,
                                   AttributeSetRegionSetPairList attributesAndRegions)
      throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.7
   void unassociateRegionsForUpdates(ObjectInstanceHandle objectInstance,
                                     AttributeSetRegionSetPairList attributesAndRegions)
      throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.8
   void subscribeObjectClassAttributesWithRegions(ObjectClassHandle objectClass,
                                                  AttributeSetRegionSetPairList attributesAndRegions)
      throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.8
   void subscribeObjectClassAttributesWithRegions(ObjectClassHandle objectClass,
                                                  AttributeSetRegionSetPairList attributesAndRegions,
                                                  String updateRateDesignator)
      throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectClassNotDefined,
      InvalidUpdateRateDesignator,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.8
   void subscribeObjectClassAttributesPassivelyWithRegions(ObjectClassHandle objectClass,
                                                           AttributeSetRegionSetPairList attributesAndRegions)
      throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.8
   void subscribeObjectClassAttributesPassivelyWithRegions(ObjectClassHandle objectClass,
                                                           AttributeSetRegionSetPairList attributesAndRegions,
                                                           String updateRateDesignator)
      throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectClassNotDefined,
      InvalidUpdateRateDesignator,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.9
   void unsubscribeObjectClassAttributesWithRegions(ObjectClassHandle objectClass,
                                                    AttributeSetRegionSetPairList attributesAndRegions)
      throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.10
   void subscribeInteractionClassWithRegions(InteractionClassHandle interactionClass,
                                             RegionHandleSet regions)
      throws
      FederateServiceInvocationsAreBeingReportedViaMOM,
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.10
   void subscribeInteractionClassPassivelyWithRegions(InteractionClassHandle interactionClass,
                                                      RegionHandleSet regions)
      throws
      FederateServiceInvocationsAreBeingReportedViaMOM,
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.11
   void unsubscribeInteractionClassWithRegions(InteractionClassHandle interactionClass,
                                               RegionHandleSet regions)
      throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.12
   void sendInteractionWithRegions(InteractionClassHandle interactionClass,
                                   ParameterHandleValueMap parameterValues,
                                   RegionHandleSet regions,
                                   byte[] userSuppliedTag)
      throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.12
   MessageRetractionReturn sendInteractionWithRegions(InteractionClassHandle interactionClass,
                                                      ParameterHandleValueMap parameterValues,
                                                      RegionHandleSet regions,
                                                      byte[] userSuppliedTag,
                                                      LogicalTime<?, ?> time)
      throws
      InvalidLogicalTime,
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 9.13
   void requestAttributeValueUpdateWithRegions(ObjectClassHandle objectClass,
                                               AttributeSetRegionSetPairList attributesAndRegions,
                                               byte[] userSuppliedTag)
      throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

//////////////////////////
// RTI Support Services //
//////////////////////////

   // 10.2
   ResignAction getAutomaticResignDirective()
      throws
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.3
   void setAutomaticResignDirective(ResignAction resignAction)
      throws
      InvalidResignAction,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.4
   FederateHandle getFederateHandle(String federateName)
      throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.5
   String getFederateName(FederateHandle federate)
      throws
      InvalidFederateHandle,
      FederateHandleNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.6
   ObjectClassHandle getObjectClassHandle(String objectClassName)
      throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.7
   String getObjectClassName(ObjectClassHandle objectClass)
      throws
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.8
   ObjectClassHandle getKnownObjectClassHandle(ObjectInstanceHandle objectInstance)
      throws
      ObjectInstanceNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.9
   ObjectInstanceHandle getObjectInstanceHandle(String objectInstanceName)
      throws
      ObjectInstanceNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.10
   String getObjectInstanceName(ObjectInstanceHandle objectInstance)
      throws
      ObjectInstanceNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.11
   AttributeHandle getAttributeHandle(ObjectClassHandle objectClass,
                                      String attributeName)
      throws
      NameNotFound,
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.12
   String getAttributeName(ObjectClassHandle objectClass,
                           AttributeHandle attribute)
      throws
      AttributeNotDefined,
      InvalidAttributeHandle,
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.13
   double getUpdateRateValue(String updateRateDesignator)
      throws
      InvalidUpdateRateDesignator,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.14
   double getUpdateRateValueForAttribute(ObjectInstanceHandle objectInstance,
                                         AttributeHandle attribute)
      throws
      ObjectInstanceNotKnown,
      AttributeNotDefined,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.15
   InteractionClassHandle getInteractionClassHandle(String interactionClassName)
      throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.16
   String getInteractionClassName(InteractionClassHandle interactionClass)
      throws
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.17
   ParameterHandle getParameterHandle(InteractionClassHandle interactionClass,
                                      String parameterName)
      throws
      NameNotFound,
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.18
   String getParameterName(InteractionClassHandle interactionClass,
                           ParameterHandle parameter)
      throws
      InteractionParameterNotDefined,
      InvalidParameterHandle,
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.19
   OrderType getOrderType(String orderTypeName)
      throws
      InvalidOrderName,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.20
   String getOrderName(OrderType orderType)
      throws
      InvalidOrderType,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.21
   TransportationTypeHandle getTransportationTypeHandle(String transportationTypeName)
      throws
      InvalidTransportationName,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.22
   String getTransportationTypeName(TransportationTypeHandle transportationType)
      throws
      InvalidTransportationTypeHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.23
   DimensionHandleSet getAvailableDimensionsForClassAttribute(ObjectClassHandle objectClass,
                                                              AttributeHandle attribute)
      throws
      AttributeNotDefined,
      InvalidAttributeHandle,
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.24
   DimensionHandleSet getAvailableDimensionsForInteractionClass(InteractionClassHandle interactionClass)
      throws
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.25
   DimensionHandle getDimensionHandle(String dimensionName)
      throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.26
   String getDimensionName(DimensionHandle dimension)
      throws
      InvalidDimensionHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.27
   long getDimensionUpperBound(DimensionHandle dimension)
      throws
      InvalidDimensionHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.28
   DimensionHandleSet getDimensionHandleSet(RegionHandle region)
      throws
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.29
   RangeBounds getRangeBounds(RegionHandle region,
                              DimensionHandle dimension)
      throws
      RegionDoesNotContainSpecifiedDimension,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.30
   void setRangeBounds(RegionHandle region,
                       DimensionHandle dimension,
                       RangeBounds rangeBounds)
      throws
      InvalidRangeBound,
      RegionDoesNotContainSpecifiedDimension,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.31
   long normalizeServiceGroup(ServiceGroup serviceGroup)
      throws
      InvalidServiceGroup,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.32
   long normalizeFederateHandle(FederateHandle federate)
      throws
      InvalidFederateHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.33
   long normalizeObjectClassHandle(ObjectClassHandle objectClass)
      throws
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.34
   long normalizeInteractionClassHandle(InteractionClassHandle interactionClass)
      throws
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.35
   long normalizeObjectInstanceHandle(ObjectInstanceHandle objectInstance)
      throws
      InvalidObjectInstanceHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.36
   void enableObjectClassRelevanceAdvisorySwitch()
      throws
      ObjectClassRelevanceAdvisorySwitchIsOn,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.37
   void disableObjectClassRelevanceAdvisorySwitch()
      throws
      ObjectClassRelevanceAdvisorySwitchIsOff,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.38
   void enableAttributeRelevanceAdvisorySwitch()
      throws
      AttributeRelevanceAdvisorySwitchIsOn,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.39
   void disableAttributeRelevanceAdvisorySwitch()
      throws
      AttributeRelevanceAdvisorySwitchIsOff,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.40
   void enableAttributeScopeAdvisorySwitch()
      throws
      AttributeScopeAdvisorySwitchIsOn,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.41
   void disableAttributeScopeAdvisorySwitch()
      throws
      AttributeScopeAdvisorySwitchIsOff,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.42
   void enableInteractionRelevanceAdvisorySwitch()
      throws
      InteractionRelevanceAdvisorySwitchIsOn,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.43
   void disableInteractionRelevanceAdvisorySwitch()
      throws
      InteractionRelevanceAdvisorySwitchIsOff,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError;

   // 10.44
   boolean evokeCallback(double approximateMinimumTimeInSeconds)
      throws
      CallNotAllowedFromWithinCallback,
      RTIinternalError;

   // 10.45
   boolean evokeMultipleCallbacks(double approximateMinimumTimeInSeconds,
                                  double approximateMaximumTimeInSeconds)
      throws
      CallNotAllowedFromWithinCallback,
      RTIinternalError;

   // 10.46
   void enableCallbacks()
      throws
      SaveInProgress,
      RestoreInProgress,
      RTIinternalError;

   // 10.47
   void disableCallbacks()
      throws
      SaveInProgress,
      RestoreInProgress,
      RTIinternalError;

   //API-specific services
   AttributeHandleFactory getAttributeHandleFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   AttributeHandleSetFactory getAttributeHandleSetFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   AttributeHandleValueMapFactory getAttributeHandleValueMapFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   AttributeSetRegionSetPairListFactory getAttributeSetRegionSetPairListFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   DimensionHandleFactory getDimensionHandleFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   DimensionHandleSetFactory getDimensionHandleSetFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   FederateHandleFactory getFederateHandleFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   FederateHandleSetFactory getFederateHandleSetFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   InteractionClassHandleFactory getInteractionClassHandleFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   InteractionClassHandleSetFactory getInteractionClassHandleSetFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   ObjectClassHandleFactory getObjectClassHandleFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   ObjectInstanceHandleFactory getObjectInstanceHandleFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   ParameterHandleFactory getParameterHandleFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   ParameterHandleValueMapFactory getParameterHandleValueMapFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   RegionHandleSetFactory getRegionHandleSetFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   TransportationTypeHandleFactory getTransportationTypeHandleFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   RegionHandleFactory getRegionHandleFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   MessageRetractionHandleFactory getMessageRetractionHandleFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;

   String getHLAversion();

   LogicalTimeFactory<?, ?> getTimeFactory()
      throws
      FederateNotExecutionMember,
      NotConnected;
}

//end RTIambassador


