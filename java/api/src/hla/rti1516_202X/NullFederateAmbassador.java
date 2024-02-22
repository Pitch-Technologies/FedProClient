/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_202X;

import hla.rti1516_202X.exceptions.FederateInternalError;
import hla.rti1516_202X.time.LogicalTime;

import java.util.Set;

public class NullFederateAmbassador implements FederateAmbassador {
   public void connectionLost(String faultDescription)
      throws FederateInternalError
   {
   }

   // 4.5
   public void federateResigned(String reasonForResignDescription)
      throws FederateInternalError
   {
   }

   // 4.9
   public void reportFederationExecutions(FederationExecutionInformationSet report)
      throws
      FederateInternalError
   {
   }

   // 4.11
   public void reportFederationExecutionMembers(String federationName,
                                                FederationExecutionMemberInformationSet report)
      throws
      FederateInternalError
   {
   }

   @Override
   // 4.11
   public void reportFederationExecutionDoesNotExist(String federationName)
      throws
      FederateInternalError
   {
   }

   // 4.15
   public void synchronizationPointRegistrationSucceeded(String synchronizationPointLabel)
      throws FederateInternalError
   {
   }

   // 4.15
   public void synchronizationPointRegistrationFailed(String synchronizationPointLabel,
                                                      SynchronizationPointFailureReason reason)
      throws FederateInternalError
   {
   }

   // 4.16
   public void announceSynchronizationPoint(String synchronizationPointLabel, byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 4.18
   public void federationSynchronized(String synchronizationPointLabel, FederateHandleSet failedToSyncSet)
      throws FederateInternalError
   {
   }

   // 4.20
   public void initiateFederateSave(String label)
      throws FederateInternalError
   {
   }

   // 4.20
   public void initiateFederateSave(String label, LogicalTime<?, ?> time)
      throws FederateInternalError
   {
   }

   // 4.23
   public void federationSaved()
      throws FederateInternalError
   {
   }

   // 4.23
   public void federationNotSaved(SaveFailureReason reason)
      throws FederateInternalError
   {
   }

   // 4.26
   public void federationSaveStatusResponse(FederateHandleSaveStatusPair[] response)
      throws FederateInternalError
   {
   }

   // 4.28
   public void requestFederationRestoreSucceeded(String label)
      throws FederateInternalError
   {
   }

   // 4.28
   public void requestFederationRestoreFailed(String label)
      throws FederateInternalError
   {
   }

   // 4.29
   public void federationRestoreBegun()
      throws FederateInternalError
   {
   }

   // 4.30
   public void initiateFederateRestore(String label, 
                                       String federateName, 
                                       FederateHandle postRestoreFederateHandle)
      throws FederateInternalError
   {
   }

   // 4.32
   public void federationRestored()
      throws FederateInternalError
   {
   }

   // 4.32
   public void federationNotRestored(RestoreFailureReason reason)
      throws FederateInternalError
   {
   }

   // 4.35
   public void federationRestoreStatusResponse(FederateRestoreStatus[] response)
      throws FederateInternalError
   {
   }

   // 5.10
   public void startRegistrationForObjectClass(ObjectClassHandle objectClass)
      throws FederateInternalError
   {
   }

   // 5.11
   public void stopRegistrationForObjectClass(ObjectClassHandle objectClass)
      throws FederateInternalError
   {
   }

   // 5.12
   public void turnInteractionsOn(InteractionClassHandle interactionClass)
      throws FederateInternalError
   {
   }

   // 5.13
   public void turnInteractionsOff(InteractionClassHandle interactionClass)
      throws FederateInternalError
   {
   }

   // 6.3
   public void objectInstanceNameReservationSucceeded(String objectInstanceName)
      throws FederateInternalError
   {
   }

   // 6.3
   public void objectInstanceNameReservationFailed(String objectInstanceName)
      throws FederateInternalError
   {
   }

   // 6.6
   public void multipleObjectInstanceNameReservationSucceeded(Set<String> objectInstanceNames)
      throws FederateInternalError
   {
   }

   // 6.6
   public void multipleObjectInstanceNameReservationFailed(Set<String> objectInstanceNames)
      throws FederateInternalError
   {
   }

   // 6.9
   public void discoverObjectInstance(ObjectInstanceHandle objectInstance,
                                      ObjectClassHandle objectClass,
                                      String objectInstanceName,
                                      FederateHandle producingFederate)
      throws FederateInternalError
   {
   }

   // 6.11
   public void reflectAttributeValues(ObjectInstanceHandle objectInstance,
                                      AttributeHandleValueMap attributeValues,
                                      byte[] userSuppliedTag,
                                      TransportationTypeHandle transportationType,
                                      FederateHandle producingFederate,
                                      RegionHandleSet optionalSentRegions)
      throws FederateInternalError
   {
   }

   // 6.11
   public void reflectAttributeValues(ObjectInstanceHandle objectInstance,
                                      AttributeHandleValueMap attributeValues,
                                      byte[] userSuppliedTag,
                                      TransportationTypeHandle transportationType,
                                      FederateHandle producingFederate,
                                      RegionHandleSet optionalSentRegions,
                                      LogicalTime<?, ?> time, 
                                      OrderType sentOrderType,
                                      OrderType receivedOrderType,
                                      MessageRetractionHandle optionalRetraction)
      throws FederateInternalError
   {
      reflectAttributeValues(
         objectInstance,
         attributeValues,
         userSuppliedTag,
         transportationType,
         producingFederate,
         optionalSentRegions);
   }

   // 6.13
   public void receiveInteraction(InteractionClassHandle interactionClass,
                                  ParameterHandleValueMap parameterValues,
                                  byte[] userSuppliedTag,
                                  TransportationTypeHandle transportationType,
                                  FederateHandle producingFederate,
                                  RegionHandleSet optionalSentRegions)
      throws FederateInternalError
   {
   }

   // 6.13
   public void receiveInteraction(InteractionClassHandle interactionClass,
                                  ParameterHandleValueMap parameterValues,
                                  byte[] userSuppliedTag,
                                  TransportationTypeHandle transportationType,
                                  FederateHandle producingFederate,
                                  RegionHandleSet optionalSentRegions,
                                  LogicalTime<?, ?> time,
                                  OrderType sentOrderType,
                                  OrderType receivedOrderType,
                                  MessageRetractionHandle optionalRetraction)
      throws FederateInternalError
   {
      receiveInteraction(
         interactionClass,
         parameterValues,
         userSuppliedTag,
         transportationType,
         producingFederate,
         optionalSentRegions);
   }

   @Override
   public void receiveDirectedInteraction(InteractionClassHandle interactionClass,
                                          ObjectInstanceHandle objectInstance,
                                          ParameterHandleValueMap parameterValues,
                                          byte[] userSuppliedTag,
                                          TransportationTypeHandle transportationType,
                                          FederateHandle producingFederate)
      throws
      FederateInternalError
   {

   }

   @Override
   public void receiveDirectedInteraction(InteractionClassHandle interactionClass,
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
      FederateInternalError
   {
      receiveDirectedInteraction(interactionClass,
         objectInstance,
         parameterValues,
         userSuppliedTag,
         transportationType,
         producingFederate);
   }

   // 6.15
   public void removeObjectInstance(ObjectInstanceHandle objectInstance,
                                    byte[] userSuppliedTag,
                                    FederateHandle producingFederate)
      throws FederateInternalError
   {
   }

   // 6.15
   public void removeObjectInstance(ObjectInstanceHandle objectInstance,
                                    byte[] userSuppliedTag,
                                    FederateHandle producingFederate,
                                    LogicalTime<?, ?> time,
                                    OrderType sentOrderType,
                                    OrderType receivedOrderType,
                                    MessageRetractionHandle optionalRetraction)
      throws FederateInternalError
   {
      removeObjectInstance(objectInstance, userSuppliedTag, producingFederate);
   }

   // 6.17
   public void attributesInScope(ObjectInstanceHandle objectInstance, AttributeHandleSet attributes)
      throws FederateInternalError
   {
   }

   // 6.18
   public void attributesOutOfScope(ObjectInstanceHandle objectInstance, AttributeHandleSet attributes)
      throws FederateInternalError
   {
   }

   // 6.20
   public void provideAttributeValueUpdate(ObjectInstanceHandle objectInstance,
                                           AttributeHandleSet attributes,
                                           byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 6.21
   public void turnUpdatesOnForObjectInstance(ObjectInstanceHandle objectInstance, AttributeHandleSet attributes)
      throws FederateInternalError
   {
   }

   // 6.21
   public void turnUpdatesOnForObjectInstance(ObjectInstanceHandle objectInstance,
                                              AttributeHandleSet attributes,
                                              String updateRateDesignator)
      throws FederateInternalError
   {
   }

   // 6.22
   public void turnUpdatesOffForObjectInstance(ObjectInstanceHandle objectInstance, AttributeHandleSet attributes)
      throws FederateInternalError
   {
   }

   // 6.24
   public void confirmAttributeTransportationTypeChange(ObjectInstanceHandle objectInstance, AttributeHandleSet attributes,
                                                        TransportationTypeHandle transportationType)
      throws
      FederateInternalError
   {
   }

   // 6.27
   public void reportAttributeTransportationType(ObjectInstanceHandle objectInstance, AttributeHandle attribute,
                                                 TransportationTypeHandle transportationType)
      throws
      FederateInternalError
   {
   }

   // 6.29
   public void confirmInteractionTransportationTypeChange(InteractionClassHandle interactionClass,
                                                          TransportationTypeHandle transportationType)
      throws
      FederateInternalError
   {
   }

   // 6.31
   public void reportInteractionTransportationType(FederateHandle federate, InteractionClassHandle interactionClass,
                                                   TransportationTypeHandle transportationType)
      throws
      FederateInternalError
   {
   }

   // 7.4
   public void requestAttributeOwnershipAssumption(ObjectInstanceHandle objectInstance,
                                                   AttributeHandleSet offeredAttributes,
                                                   byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.5
   public void requestDivestitureConfirmation(ObjectInstanceHandle objectInstance,
                                              AttributeHandleSet releasedAttributes,
                                              byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.7
   public void attributeOwnershipAcquisitionNotification(ObjectInstanceHandle objectInstance,
                                                         AttributeHandleSet securedAttributes,
                                                         byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.10
   public void attributeOwnershipUnavailable(ObjectInstanceHandle objectInstance,
                                             AttributeHandleSet attributes,
                                             byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.11
   public void requestAttributeOwnershipRelease(ObjectInstanceHandle objectInstance,
                                                AttributeHandleSet candidateAttributes,
                                                byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.16
   public void confirmAttributeOwnershipAcquisitionCancellation(ObjectInstanceHandle objectInstance,
                                                                AttributeHandleSet attributes)
      throws FederateInternalError
   {
   }

   // 7.18
   public void informAttributeOwnership(ObjectInstanceHandle objectInstance,
                                        AttributeHandleSet attributes,
                                        FederateHandle owner)
      throws FederateInternalError
   {
   }

   // 7.18
   public void attributeIsNotOwned(ObjectInstanceHandle objectInstance, AttributeHandleSet attributes)
      throws FederateInternalError
   {
   }

   // 7.18
   public void attributeIsOwnedByRTI(ObjectInstanceHandle objectInstance, AttributeHandleSet attributes)
      throws FederateInternalError
   {
   }

   // 8.3
   public void timeRegulationEnabled(LogicalTime<?, ?> time)
      throws FederateInternalError
   {
   }

   // 8.6
   public void timeConstrainedEnabled(LogicalTime<?, ?> time)
      throws FederateInternalError
   {
   }

   // 8.13
   public void flushQueueGrant(LogicalTime<?, ?> time, LogicalTime<?, ?> optimisticTime)
      throws FederateInternalError
   {
   }

   // 8.14
   public void timeAdvanceGrant(LogicalTime<?, ?> time)
      throws FederateInternalError
   {
   }

   // 8.23
   public void requestRetraction(MessageRetractionHandle retraction)
      throws FederateInternalError
   {
   }
}
