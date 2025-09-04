 /*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_2025;

import hla.rti1516_2025.exceptions.FederateInternalError;
import hla.rti1516_2025.time.LogicalTime;

import java.util.Set;

public class NullFederateAmbassador implements FederateAmbassador {
   // 4.4
   @Override
   public void connectionLost(String faultDescription)
      throws FederateInternalError
   {
   }

   // 4.8
   @Override
   public void reportFederationExecutions(FederationExecutionInformationSet report)
      throws
      FederateInternalError
   {
   }

   // 4.10
   @Override
   public void reportFederationExecutionMembers(String federationName,
                                                FederationExecutionMemberInformationSet report)
      throws
      FederateInternalError
   {
   }

   // 4.10
   @Override
   public void reportFederationExecutionDoesNotExist(String federationName)
      throws
      FederateInternalError
   {
   }

   // 4.13
   @Override
   public void federateResigned(String reasonForResignDescription)
      throws FederateInternalError
   {
   }

   // 4.15
   @Override
   public void synchronizationPointRegistrationSucceeded(String synchronizationPointLabel)
      throws FederateInternalError
   {
   }

   // 4.15
   @Override
   public void synchronizationPointRegistrationFailed(String synchronizationPointLabel,
                                                      SynchronizationPointFailureReason reason)
      throws FederateInternalError
   {
   }

   // 4.16
   @Override
   public void announceSynchronizationPoint(String synchronizationPointLabel, byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 4.18
   @Override
   public void federationSynchronized(String synchronizationPointLabel, FederateHandleSet failedToSyncSet)
      throws FederateInternalError
   {
   }

   // 4.20
   @Override
   public void initiateFederateSave(String label)
      throws FederateInternalError
   {
   }

   // 4.20
   @Override
   public void initiateFederateSave(String label, LogicalTime<?, ?> time)
      throws FederateInternalError
   {
   }

   // 4.23
   @Override
   public void federationSaved()
      throws FederateInternalError
   {
   }

   // 4.23
   @Override
   public void federationNotSaved(SaveFailureReason reason)
      throws FederateInternalError
   {
   }

   // 4.26
   @Override
   public void federationSaveStatusResponse(FederateHandleSaveStatusPair[] response)
      throws FederateInternalError
   {
   }

   // 4.28
   @Override
   public void requestFederationRestoreSucceeded(String label)
      throws FederateInternalError
   {
   }

   // 4.28
   @Override
   public void requestFederationRestoreFailed(String label)
      throws FederateInternalError
   {
   }

   // 4.29
   @Override
   public void federationRestoreBegun()
      throws FederateInternalError
   {
   }

   // 4.30
   @Override
   public void initiateFederateRestore(String label, 
                                       String federateName, 
                                       FederateHandle postRestoreFederateHandle)
      throws FederateInternalError
   {
   }

   // 4.32
   @Override
   public void federationRestored()
      throws FederateInternalError
   {
   }

   // 4.32
   @Override
   public void federationNotRestored(RestoreFailureReason reason)
      throws FederateInternalError
   {
   }

   // 4.35
   @Override
   public void federationRestoreStatusResponse(FederateRestoreStatus[] response)
      throws FederateInternalError
   {
   }

   // 5.14
   @Override
   public void startRegistrationForObjectClass(ObjectClassHandle objectClass)
      throws FederateInternalError
   {
   }

   // 5.15
   @Override
   public void stopRegistrationForObjectClass(ObjectClassHandle objectClass)
      throws FederateInternalError
   {
   }

   // 5.16
   @Override
   public void turnInteractionsOn(InteractionClassHandle interactionClass)
      throws FederateInternalError
   {
   }

   // 5.17
   @Override
   public void turnInteractionsOff(InteractionClassHandle interactionClass)
      throws FederateInternalError
   {
   }

   // 6.3
   @Override
   public void objectInstanceNameReservationSucceeded(String objectInstanceName)
      throws FederateInternalError
   {
   }

   // 6.3
   @Override
   public void objectInstanceNameReservationFailed(String objectInstanceName)
      throws FederateInternalError
   {
   }

   // 6.6
   @Override
   public void multipleObjectInstanceNameReservationSucceeded(Set<String> objectInstanceNames)
      throws FederateInternalError
   {
   }

   // 6.6
   @Override
   public void multipleObjectInstanceNameReservationFailed(Set<String> objectInstanceNames)
      throws FederateInternalError
   {
   }

   // 6.9
   @Override
   public void discoverObjectInstance(ObjectInstanceHandle objectInstance,
                                      ObjectClassHandle objectClass,
                                      String objectInstanceName,
                                      FederateHandle producingFederate)
      throws FederateInternalError
   {
   }

   // 6.11
   @Override
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
   @Override
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
   @Override
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
   @Override
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

   // 6.15
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

   // 6.15
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

   // 6.17
   @Override
   public void removeObjectInstance(ObjectInstanceHandle objectInstance,
                                    byte[] userSuppliedTag,
                                    FederateHandle producingFederate)
      throws FederateInternalError
   {
   }

   // 6.17
   @Override
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

   // 6.19
   @Override
   public void attributesInScope(ObjectInstanceHandle objectInstance, AttributeHandleSet attributes)
      throws FederateInternalError
   {
   }

   // 6.20
   @Override
   public void attributesOutOfScope(ObjectInstanceHandle objectInstance, AttributeHandleSet attributes)
      throws FederateInternalError
   {
   }

   // 6.22
   @Override
   public void provideAttributeValueUpdate(ObjectInstanceHandle objectInstance,
                                           AttributeHandleSet attributes,
                                           byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 6.23
   @Override
   public void turnUpdatesOnForObjectInstance(ObjectInstanceHandle objectInstance, AttributeHandleSet attributes)
      throws FederateInternalError
   {
   }

   // 6.23
   @Override
   public void turnUpdatesOnForObjectInstance(ObjectInstanceHandle objectInstance,
                                              AttributeHandleSet attributes,
                                              String updateRateDesignator)
      throws FederateInternalError
   {
   }

   // 6.24
   @Override
   public void turnUpdatesOffForObjectInstance(ObjectInstanceHandle objectInstance, AttributeHandleSet attributes)
      throws FederateInternalError
   {
   }

   // 6.26
   @Override
   public void confirmAttributeTransportationTypeChange(ObjectInstanceHandle objectInstance, AttributeHandleSet attributes,
                                                        TransportationTypeHandle transportationType)
      throws
      FederateInternalError
   {
   }

   // 6.29
   @Override
   public void reportAttributeTransportationType(ObjectInstanceHandle objectInstance, AttributeHandle attribute,
                                                 TransportationTypeHandle transportationType)
      throws
      FederateInternalError
   {
   }

   // 6.31
   @Override
   public void confirmInteractionTransportationTypeChange(InteractionClassHandle interactionClass,
                                                          TransportationTypeHandle transportationType)
      throws
      FederateInternalError
   {
   }

   // 6.33
   @Override
   public void reportInteractionTransportationType(FederateHandle federate, InteractionClassHandle interactionClass,
                                                   TransportationTypeHandle transportationType)
      throws
      FederateInternalError
   {
   }

   // 7.4
   @Override
   public void requestAttributeOwnershipAssumption(ObjectInstanceHandle objectInstance,
                                                   AttributeHandleSet offeredAttributes,
                                                   byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.5
   @Override
   public void requestDivestitureConfirmation(ObjectInstanceHandle objectInstance,
                                              AttributeHandleSet releasedAttributes,
                                              byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.7
   @Override
   public void attributeOwnershipAcquisitionNotification(ObjectInstanceHandle objectInstance,
                                                         AttributeHandleSet securedAttributes,
                                                         byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.10
   @Override
   public void attributeOwnershipUnavailable(ObjectInstanceHandle objectInstance,
                                             AttributeHandleSet attributes,
                                             byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.11
   @Override
   public void requestAttributeOwnershipRelease(ObjectInstanceHandle objectInstance,
                                                AttributeHandleSet candidateAttributes,
                                                byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.16
   @Override
   public void confirmAttributeOwnershipAcquisitionCancellation(ObjectInstanceHandle objectInstance,
                                                                AttributeHandleSet attributes)
      throws FederateInternalError
   {
   }

   // 7.18
   @Override
   public void informAttributeOwnership(ObjectInstanceHandle objectInstance,
                                        AttributeHandleSet attributes,
                                        FederateHandle owner)
      throws FederateInternalError
   {
   }

   // 7.18
   @Override
   public void attributeIsNotOwned(ObjectInstanceHandle objectInstance, AttributeHandleSet attributes)
      throws FederateInternalError
   {
   }

   // 7.18
   @Override
   public void attributeIsOwnedByRTI(ObjectInstanceHandle objectInstance, AttributeHandleSet attributes)
      throws FederateInternalError
   {
   }

   // 8.3
   @Override
   public void timeRegulationEnabled(LogicalTime<?, ?> time)
      throws FederateInternalError
   {
   }

   // 8.6
   @Override
   public void timeConstrainedEnabled(LogicalTime<?, ?> time)
      throws FederateInternalError
   {
   }

   // 8.13
   @Override
   public void flushQueueGrant(LogicalTime<?, ?> time, LogicalTime<?, ?> optimisticTime)
      throws FederateInternalError
   {
   }

   // 8.14
   @Override
   public void timeAdvanceGrant(LogicalTime<?, ?> time)
      throws FederateInternalError
   {
   }

   // 8.23
   @Override
   public void requestRetraction(MessageRetractionHandle retraction)
      throws FederateInternalError
   {
   }
}
