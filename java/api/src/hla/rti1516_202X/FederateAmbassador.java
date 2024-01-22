/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

//File: FederateAmbassador.java

package hla.rti1516_202X;

import hla.rti1516_202X.exceptions.FederateInternalError;
import hla.rti1516_202X.time.LogicalTime;

import java.util.Set;


/**
 * Federate must implement this interface.
 */

public interface FederateAmbassador {

////////////////////////////////////
// Federation Management Services //
////////////////////////////////////

   // 4.4
   void connectionLost(String faultDescription)
      throws
      FederateInternalError;

   // 4.5
   void federateResigned(String reasonForResignDescription)
      throws
      FederateInternalError;

   // 4.9
   void reportFederationExecutions(FederationExecutionInformationSet report)
      throws
      FederateInternalError;

   // 4.11
   void reportFederationExecutionMembers(String federationName,
                                         FederationExecutionMemberInformationSet report)
      throws
      FederateInternalError;

   // 4.11
   void reportFederationExecutionDoesNotExist(String federationName)
      throws
      FederateInternalError;

   // 4.15
   void synchronizationPointRegistrationSucceeded(String synchronizationPointLabel)
      throws
      FederateInternalError;

   // 4.15
   void synchronizationPointRegistrationFailed(String synchronizationPointLabel,
                                               SynchronizationPointFailureReason reason)
      throws
      FederateInternalError;

   // 4.16
   void announceSynchronizationPoint(String synchronizationPointLabel,
                                     byte[] userSuppliedTag)
      throws
      FederateInternalError;

   // 4.18
   void federationSynchronized(String synchronizationPointLabel,
                               FederateHandleSet failedToSyncSet)
      throws
      FederateInternalError;

   // 4.20
   void initiateFederateSave(String label)
      throws
      FederateInternalError;

   // 4.20
   void initiateFederateSave(String label,
                             LogicalTime<?, ?> time)
      throws
      FederateInternalError;

   // 4.23
   void federationSaved()
      throws
      FederateInternalError;

   // 4.23
   void federationNotSaved(SaveFailureReason reason)
      throws
      FederateInternalError;

   // 4.26
   void federationSaveStatusResponse(FederateHandleSaveStatusPair[] response)
      throws
      FederateInternalError;

   // 4.28
   void requestFederationRestoreSucceeded(String label)
      throws
      FederateInternalError;

   // 4.28
   void requestFederationRestoreFailed(String label)
      throws
      FederateInternalError;

   // 4.29
   void federationRestoreBegun()
      throws
      FederateInternalError;

   // 4.30
   void initiateFederateRestore(String label,
                                String federateName,
                                FederateHandle postRestoreFederateHandle)
      throws
      FederateInternalError;

   // 4.32
   void federationRestored()
      throws
      FederateInternalError;

   // 4.29
   void federationNotRestored(RestoreFailureReason reason)
      throws
      FederateInternalError;

   // 4.35
   void federationRestoreStatusResponse(FederateRestoreStatus[] response)
      throws
      FederateInternalError;

/////////////////////////////////////
// Declaration Management Services //
/////////////////////////////////////

   // 5.10
   void startRegistrationForObjectClass(ObjectClassHandle objectClass)
      throws
      FederateInternalError;

   // 5.11
   void stopRegistrationForObjectClass(ObjectClassHandle objectClass)
      throws
      FederateInternalError;

   // 5.12
   void turnInteractionsOn(InteractionClassHandle interactionClass)
      throws
      FederateInternalError;

   // 5.13
   void turnInteractionsOff(InteractionClassHandle interactionClass)
      throws
      FederateInternalError;

////////////////////////////////
// Object Management Services //
////////////////////////////////

   // 6.3
   void objectInstanceNameReservationSucceeded(String objectInstanceName)
      throws
      FederateInternalError;

   // 6.3
   void objectInstanceNameReservationFailed(String objectInstanceName)
      throws
      FederateInternalError;

   // 6.6
   void multipleObjectInstanceNameReservationSucceeded(Set<String> objectInstanceNames)
      throws
      FederateInternalError;

   // 6.6
   void multipleObjectInstanceNameReservationFailed(Set<String> objectInstanceNames)
      throws
      FederateInternalError;

   // 6.9
   void discoverObjectInstance(ObjectInstanceHandle objectInstance,
                               ObjectClassHandle objectClass,
                               String objectInstanceName,
                               FederateHandle producingFederate)
      throws
      FederateInternalError;

   // 6.11
   void reflectAttributeValues(ObjectInstanceHandle objectInstance,
                               AttributeHandleValueMap attributeValues,
                               byte[] userSuppliedTag,
                               TransportationTypeHandle transportationType,
                               FederateHandle producingFederate,
                               RegionHandleSet optionalSentRegions)
      throws
      FederateInternalError;

   // 6.11
   void reflectAttributeValues(ObjectInstanceHandle objectInstance,
                               AttributeHandleValueMap attributeValues,
                               byte[] userSuppliedTag,
                               TransportationTypeHandle transportationType,
                               FederateHandle producingFederate,
                               RegionHandleSet optionalSentRegions,
                               LogicalTime<?, ?> time,
                               OrderType sentOrderType,
                               OrderType receivedOrderType,
                               MessageRetractionHandle optionalRetraction)
      throws
      FederateInternalError;

   // 6.13
   void receiveInteraction(InteractionClassHandle interactionClass,
                           ParameterHandleValueMap parameterValues,
                           byte[] userSuppliedTag,
                           TransportationTypeHandle transportationType,
                           FederateHandle producingFederate,
                           RegionHandleSet optionalSentRegions)
      throws
      FederateInternalError;

   // 6.13
   void receiveInteraction(InteractionClassHandle interactionClass,
                           ParameterHandleValueMap parameterValues,
                           byte[] userSuppliedTag,
                           TransportationTypeHandle transportationType,
                           FederateHandle producingFederate,
                           RegionHandleSet optionalSentRegions,
                           LogicalTime<?, ?> time,
                           OrderType sentOrderType,
                           OrderType receivedOrderType,
                           MessageRetractionHandle optionalRetraction)
      throws
      FederateInternalError;

   void receiveDirectedInteraction(InteractionClassHandle interactionClass,
                                   ObjectInstanceHandle objectInstance,
                                   ParameterHandleValueMap parameterValues,
                                   byte[] userSuppliedTag,
                                   TransportationTypeHandle transportationType,
                                   FederateHandle producingFederate)
      throws
      FederateInternalError;

   // 6.13
   void receiveDirectedInteraction(InteractionClassHandle interactionClass,
                                   ObjectInstanceHandle objectInstance,
                                   ParameterHandleValueMap parameterValues,
                                   byte[] userSuppliedTag,
                                   TransportationTypeHandle transportationType,
                                   FederateHandle producingFederate,
                                   LogicalTime<?, ?> time,
                                   OrderType sentOrderType,
                                   OrderType receivedOrderType,
                                   MessageRetractionHandle optionalRetraction)
      throws
      FederateInternalError;

   // 6.15
   void removeObjectInstance(ObjectInstanceHandle objectInstance,
                             byte[] userSuppliedTag,
                             FederateHandle producingFederate)
      throws
      FederateInternalError;

   // 6.15
   void removeObjectInstance(ObjectInstanceHandle objectInstance,
                             byte[] userSuppliedTag,
                             FederateHandle producingFederate,
                             LogicalTime<?, ?> time,
                             OrderType sentOrderType,
                             OrderType receivedOrderType,
                             MessageRetractionHandle optionalRetraction)
      throws
      FederateInternalError;

   // 6.17
   void attributesInScope(ObjectInstanceHandle objectInstance,
                          AttributeHandleSet attributes)
      throws
      FederateInternalError;

   // 6.18
   void attributesOutOfScope(ObjectInstanceHandle objectInstance,
                             AttributeHandleSet attributes)
      throws
      FederateInternalError;

   // 6.20
   void provideAttributeValueUpdate(ObjectInstanceHandle objectInstance,
                                    AttributeHandleSet attributes,
                                    byte[] userSuppliedTag)
      throws
      FederateInternalError;

   // 6.21
   void turnUpdatesOnForObjectInstance(ObjectInstanceHandle objectInstance,
                                       AttributeHandleSet attributes)
      throws
      FederateInternalError;

   // 6.21
   void turnUpdatesOnForObjectInstance(ObjectInstanceHandle objectInstance,
                                       AttributeHandleSet attributes,
                                       String updateRateDesignator)
      throws
      FederateInternalError;

   // 6.22
   void turnUpdatesOffForObjectInstance(ObjectInstanceHandle objectInstance,
                                        AttributeHandleSet attributes)
      throws
      FederateInternalError;

   // 6.24
   void confirmAttributeTransportationTypeChange(ObjectInstanceHandle objectInstance,
                                                 AttributeHandleSet attributes,
                                                 TransportationTypeHandle transportationType)
      throws
      FederateInternalError;

   // 6.27
   void reportAttributeTransportationType(ObjectInstanceHandle objectInstance,
                                          AttributeHandle attribute,
                                          TransportationTypeHandle transportationType)
      throws
      FederateInternalError;

   // 6.29
   void confirmInteractionTransportationTypeChange(InteractionClassHandle interactionClass,
                                                   TransportationTypeHandle transportationType)
      throws
      FederateInternalError;

   // 6.31
   void reportInteractionTransportationType(FederateHandle federate,
                                            InteractionClassHandle interactionClass,
                                            TransportationTypeHandle transportationType)
      throws
      FederateInternalError;

///////////////////////////////////
// Ownership Management Services //
///////////////////////////////////

   // 7.4
   void requestAttributeOwnershipAssumption(ObjectInstanceHandle objectInstance,
                                            AttributeHandleSet offeredAttributes,
                                            byte[] userSuppliedTag)
      throws
      FederateInternalError;

   // 7.5
   void requestDivestitureConfirmation(ObjectInstanceHandle objectInstance,
                                       AttributeHandleSet releasedAttributes,
                                       byte[] userSuppliedTag)
      throws
      FederateInternalError;

   // 7.7
   void attributeOwnershipAcquisitionNotification(ObjectInstanceHandle objectInstance,
                                                  AttributeHandleSet securedAttributes,
                                                  byte[] userSuppliedTag)
      throws
      FederateInternalError;

   // 7.10
   void attributeOwnershipUnavailable(ObjectInstanceHandle objectInstance,
                                      AttributeHandleSet attributes,
                                      byte[] userSuppliedTag)
      throws
      FederateInternalError;

   // 7.11
   void requestAttributeOwnershipRelease(ObjectInstanceHandle objectInstance,
                                         AttributeHandleSet candidateAttributes,
                                         byte[] userSuppliedTag)
      throws
      FederateInternalError;

   // 7.16
   void confirmAttributeOwnershipAcquisitionCancellation(ObjectInstanceHandle objectInstance,
                                                         AttributeHandleSet attributes)
      throws
      FederateInternalError;

   // 7.18
   void informAttributeOwnership(ObjectInstanceHandle objectInstance,
                                 AttributeHandleSet attributes,
                                 FederateHandle owner)
      throws
      FederateInternalError;

   // 7.18
   void attributeIsNotOwned(ObjectInstanceHandle objectInstance,
                            AttributeHandleSet attributes)
      throws
      FederateInternalError;

   // 7.18
   void attributeIsOwnedByRTI(ObjectInstanceHandle objectInstance,
                              AttributeHandleSet attributes)
      throws
      FederateInternalError;

//////////////////////////////
// Time Management Services //
//////////////////////////////

   // 8.3
   void timeRegulationEnabled(LogicalTime<?, ?> time)
      throws
      FederateInternalError;

   // 8.6
   void timeConstrainedEnabled(LogicalTime<?, ?> time)
      throws
      FederateInternalError;

   // 8.13
   void flushQueueGrant(LogicalTime<?, ?> time, LogicalTime<?, ?> optimisticTime)
      throws
      FederateInternalError;

   // 8.14
   void timeAdvanceGrant(LogicalTime<?, ?> time)
      throws
      FederateInternalError;

   // 8.23
   void requestRetraction(MessageRetractionHandle retraction)
      throws
      FederateInternalError;
}

//end FederateAmbassador

