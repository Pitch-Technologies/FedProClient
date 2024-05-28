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
   public void reportFederationExecutions(FederationExecutionInformationSet federationExecutionInformationSet)
      throws
      FederateInternalError
   {
   }

   // 4.11
   public void reportFederationExecutionMembers(String federationName,
                                                FederationExecutionMemberInformationSet federationExecutionMemberInformationSet)
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
                                       FederateHandle federateHandle)
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
   public void startRegistrationForObjectClass(ObjectClassHandle theClass)
      throws FederateInternalError
   {
   }

   // 5.11
   public void stopRegistrationForObjectClass(ObjectClassHandle theClass)
      throws FederateInternalError
   {
   }

   // 5.12
   public void turnInteractionsOn(InteractionClassHandle theHandle)
      throws FederateInternalError
   {
   }

   // 5.13
   public void turnInteractionsOff(InteractionClassHandle theHandle)
      throws FederateInternalError
   {
   }

   // 6.3
   public void objectInstanceNameReservationSucceeded(String objectName)
      throws FederateInternalError
   {
   }

   // 6.3
   public void objectInstanceNameReservationFailed(String objectName)
      throws FederateInternalError
   {
   }

   // 6.6
   public void multipleObjectInstanceNameReservationSucceeded(Set<String> objectNames)
      throws FederateInternalError
   {
   }

   // 6.6
   public void multipleObjectInstanceNameReservationFailed(Set<String> objectNames)
      throws FederateInternalError
   {
   }

   // 6.9
   public void discoverObjectInstance(ObjectInstanceHandle theObject,
                                      ObjectClassHandle theObjectClass,
                                      String objectName,
                                      FederateHandle producingFederate)
      throws FederateInternalError
   {
   }

   // 6.11
   public void reflectAttributeValues(ObjectInstanceHandle theObject,
                                      AttributeHandleValueMap theAttributes,
                                      byte[] userSuppliedTag,
                                      OrderType sentOrdering,
                                      TransportationTypeHandle theTransport,
                                      SupplementalReflectInfo reflectInfo)
      throws FederateInternalError
   {
   }

   // 6.11
   public void reflectAttributeValues(ObjectInstanceHandle theObject,
                                      AttributeHandleValueMap theAttributes,
                                      byte[] userSuppliedTag,
                                      OrderType sentOrdering,
                                      TransportationTypeHandle theTransport,
                                      LogicalTime<?, ?> theTime,
                                      OrderType receivedOrdering,
                                      SupplementalReflectInfo reflectInfo)
      throws FederateInternalError
   {
      reflectAttributeValues(theObject, theAttributes, userSuppliedTag, sentOrdering, theTransport, reflectInfo);
   }

   // 6.11
   public void reflectAttributeValues(ObjectInstanceHandle theObject,
                                      AttributeHandleValueMap theAttributes,
                                      byte[] userSuppliedTag,
                                      OrderType sentOrdering,
                                      TransportationTypeHandle theTransport,
                                      LogicalTime<?, ?> theTime,
                                      OrderType receivedOrdering,
                                      MessageRetractionHandle retractionHandle,
                                      SupplementalReflectInfo reflectInfo)
      throws FederateInternalError
   {
      reflectAttributeValues(theObject, theAttributes, userSuppliedTag, sentOrdering, theTransport, theTime, receivedOrdering, reflectInfo);
   }

   // 6.13
   public void receiveInteraction(InteractionClassHandle interactionClass,
                                  ParameterHandleValueMap theParameters,
                                  byte[] userSuppliedTag,
                                  OrderType sentOrdering,
                                  TransportationTypeHandle theTransport,
                                  SupplementalReceiveInfo receiveInfo)
      throws FederateInternalError
   {
   }

   // 6.13
   public void receiveInteraction(InteractionClassHandle interactionClass,
                                  ParameterHandleValueMap theParameters,
                                  byte[] userSuppliedTag,
                                  OrderType sentOrdering,
                                  TransportationTypeHandle theTransport,
                                  LogicalTime<?, ?> theTime,
                                  OrderType receivedOrdering,
                                  SupplementalReceiveInfo receiveInfo)
      throws FederateInternalError
   {
      receiveInteraction(interactionClass, theParameters, userSuppliedTag, sentOrdering, theTransport, receiveInfo);
   }

   // 6.13
   public void receiveInteraction(InteractionClassHandle interactionClass,
                                  ParameterHandleValueMap theParameters,
                                  byte[] userSuppliedTag,
                                  OrderType sentOrdering,
                                  TransportationTypeHandle theTransport,
                                  LogicalTime<?, ?> theTime,
                                  OrderType receivedOrdering,
                                  MessageRetractionHandle retractionHandle,
                                  SupplementalReceiveInfo receiveInfo)
      throws FederateInternalError
   {
      receiveInteraction(interactionClass, theParameters, userSuppliedTag, sentOrdering, theTransport, theTime, receivedOrdering, receiveInfo);
   }

   @Override
   public void receiveDirectedInteraction(InteractionClassHandle interactionClass,
                                          ObjectInstanceHandle theObject,
                                          ParameterHandleValueMap theParameters,
                                          byte[] userSuppliedTag,
                                          OrderType sentOrdering,
                                          TransportationTypeHandle theTransport,
                                          SupplementalReceiveInfo receiveInfo)
      throws
      FederateInternalError
   {

   }

   @Override
   public void receiveDirectedInteraction(InteractionClassHandle interactionClass,
                                          ObjectInstanceHandle theObject,
                                          ParameterHandleValueMap theParameters,
                                          byte[] userSuppliedTag,
                                          OrderType sentOrdering,
                                          TransportationTypeHandle theTransport,
                                          LogicalTime<?, ?> theTime,
                                          OrderType receivedOrdering,
                                          SupplementalReceiveInfo receiveInfo)
      throws
      FederateInternalError
   {
      receiveDirectedInteraction(interactionClass, theObject, theParameters, userSuppliedTag, sentOrdering, theTransport, receiveInfo);
   }

   @Override
   public void receiveDirectedInteraction(InteractionClassHandle interactionClass,
                                          ObjectInstanceHandle theObject,
                                          ParameterHandleValueMap theParameters,
                                          byte[] userSuppliedTag,
                                          OrderType sentOrdering,
                                          TransportationTypeHandle theTransport,
                                          LogicalTime<?, ?> theTime,
                                          OrderType receivedOrdering,
                                          MessageRetractionHandle retractionHandle,
                                          SupplementalReceiveInfo receiveInfo)
      throws
      FederateInternalError
   {
      receiveDirectedInteraction(interactionClass, theObject, theParameters, userSuppliedTag, sentOrdering, theTransport, theTime, receivedOrdering, receiveInfo);
   }

   // 6.15
   public void removeObjectInstance(ObjectInstanceHandle theObject,
                                    byte[] userSuppliedTag,
                                    OrderType sentOrdering,
                                    FederateHandle producingFederate)
      throws FederateInternalError
   {
   }

   // 6.15
   public void removeObjectInstance(ObjectInstanceHandle theObject,
                                    byte[] userSuppliedTag,
                                    OrderType sentOrdering,
                                    LogicalTime<?, ?> theTime,
                                    OrderType receivedOrdering,
                                    FederateHandle producingFederate)
      throws FederateInternalError
   {
      removeObjectInstance(theObject, userSuppliedTag, sentOrdering, producingFederate);
   }

   // 6.15
   public void removeObjectInstance(ObjectInstanceHandle theObject,
                                    byte[] userSuppliedTag,
                                    OrderType sentOrdering,
                                    LogicalTime<?, ?> theTime,
                                    OrderType receivedOrdering,
                                    MessageRetractionHandle retractionHandle,
                                    FederateHandle producingFederate)
      throws FederateInternalError
   {
      removeObjectInstance(theObject, userSuppliedTag, sentOrdering, theTime, receivedOrdering, producingFederate);
   }

   // 6.17
   public void attributesInScope(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes)
      throws FederateInternalError
   {
   }

   // 6.18
   public void attributesOutOfScope(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes)
      throws FederateInternalError
   {
   }

   // 6.20
   public void provideAttributeValueUpdate(ObjectInstanceHandle theObject,
                                           AttributeHandleSet theAttributes,
                                           byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 6.21
   public void turnUpdatesOnForObjectInstance(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes)
      throws FederateInternalError
   {
   }

   // 6.21
   public void turnUpdatesOnForObjectInstance(ObjectInstanceHandle theObject,
                                              AttributeHandleSet theAttributes,
                                              String updateRateDesignator)
      throws FederateInternalError
   {
   }

   // 6.22
   public void turnUpdatesOffForObjectInstance(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes)
      throws FederateInternalError
   {
   }

   // 6.24
   public void confirmAttributeTransportationTypeChange(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes,
                                                        TransportationTypeHandle theTransportation)
      throws
      FederateInternalError
   {
   }

   // 6.27
   public void reportAttributeTransportationType(ObjectInstanceHandle theObject, AttributeHandle theAttribute,
                                                 TransportationTypeHandle theTransportation)
      throws
      FederateInternalError
   {
   }

   // 6.29
   public void confirmInteractionTransportationTypeChange(InteractionClassHandle theInteraction,
                                                          TransportationTypeHandle theTransportation)
      throws
      FederateInternalError
   {
   }

   // 6.31
   public void reportInteractionTransportationType(FederateHandle theFederate, InteractionClassHandle theInteraction,
                                                   TransportationTypeHandle theTransportation)
      throws
      FederateInternalError
   {
   }

   // 7.4
   public void requestAttributeOwnershipAssumption(ObjectInstanceHandle theObject,
                                                   AttributeHandleSet offeredAttributes,
                                                   byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.5
   public void requestDivestitureConfirmation(ObjectInstanceHandle theObject,
                                              AttributeHandleSet offeredAttributes,
                                              byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.7
   public void attributeOwnershipAcquisitionNotification(ObjectInstanceHandle theObject,
                                                         AttributeHandleSet securedAttributes,
                                                         byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.10
   public void attributeOwnershipUnavailable(ObjectInstanceHandle theObject,
                                             AttributeHandleSet theAttributes,
                                             byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.11
   public void requestAttributeOwnershipRelease(ObjectInstanceHandle theObject,
                                                AttributeHandleSet candidateAttributes,
                                                byte[] userSuppliedTag)
      throws FederateInternalError
   {
   }

   // 7.16
   public void confirmAttributeOwnershipAcquisitionCancellation(ObjectInstanceHandle theObject,
                                                                AttributeHandleSet theAttributes)
      throws FederateInternalError
   {
   }

   // 7.18
   public void informAttributeOwnership(ObjectInstanceHandle theObject,
                                        AttributeHandleSet theAttributes,
                                        FederateHandle theOwner)
      throws FederateInternalError
   {
   }

   // 7.18
   public void attributeIsNotOwned(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes)
      throws FederateInternalError
   {
   }

   // 7.18
   public void attributeIsOwnedByRTI(ObjectInstanceHandle theObject, AttributeHandleSet theAttributes)
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
   public void flushQueueGrant(LogicalTime<?, ?> theTime, LogicalTime<?, ?> optimisticTime)
      throws FederateInternalError
   {
   }

   // 8.14
   public void timeAdvanceGrant(LogicalTime<?, ?> theTime)
      throws FederateInternalError
   {
   }

   // 8.23
   public void requestRetraction(MessageRetractionHandle theHandle)
      throws FederateInternalError
   {
   }
}
