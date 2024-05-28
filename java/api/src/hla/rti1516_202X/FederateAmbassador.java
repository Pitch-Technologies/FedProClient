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

import java.io.Serializable;
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
   void reportFederationExecutions(FederationExecutionInformationSet federationExecutionInformationSet)
      throws
      FederateInternalError;

   // 4.11
   void reportFederationExecutionMembers(String federationName,
                                         FederationExecutionMemberInformationSet federationExecutionMemberInformationSet)
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
                                FederateHandle federateHandle)
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
   void startRegistrationForObjectClass(ObjectClassHandle theClass)
      throws
      FederateInternalError;

   // 5.11
   void stopRegistrationForObjectClass(ObjectClassHandle theClass)
      throws
      FederateInternalError;

   // 5.12
   void turnInteractionsOn(InteractionClassHandle theHandle)
      throws
      FederateInternalError;

   // 5.13
   void turnInteractionsOff(InteractionClassHandle theHandle)
      throws
      FederateInternalError;

////////////////////////////////
// Object Management Services //
////////////////////////////////

   // 6.3
   void objectInstanceNameReservationSucceeded(String objectName)
      throws
      FederateInternalError;

   // 6.3
   void objectInstanceNameReservationFailed(String objectName)
      throws
      FederateInternalError;

   // 6.6
   void multipleObjectInstanceNameReservationSucceeded(Set<String> objectNames)
      throws
      FederateInternalError;

   // 6.6
   void multipleObjectInstanceNameReservationFailed(Set<String> objectNames)
      throws
      FederateInternalError;

   // 6.9
   void discoverObjectInstance(ObjectInstanceHandle theObject,
                               ObjectClassHandle theObjectClass,
                               String objectName,
                               FederateHandle producingFederate)
      throws
      FederateInternalError;

   interface SupplementalReflectInfo extends Serializable {
      boolean hasSentRegions();

      FederateHandle getProducingFederate();

      RegionHandleSet getSentRegions();
   }

   // 6.11
   void reflectAttributeValues(ObjectInstanceHandle theObject,
                               AttributeHandleValueMap theAttributes,
                               byte[] userSuppliedTag,
                               OrderType sentOrdering,
                               TransportationTypeHandle theTransport,
                               SupplementalReflectInfo reflectInfo)
      throws
      FederateInternalError;

   // 6.11
   void reflectAttributeValues(ObjectInstanceHandle theObject,
                               AttributeHandleValueMap theAttributes,
                               byte[] userSuppliedTag,
                               OrderType sentOrdering,
                               TransportationTypeHandle theTransport,
                               LogicalTime<?, ?> theTime,
                               OrderType receivedOrdering,
                               SupplementalReflectInfo reflectInfo)
      throws
      FederateInternalError;

   // 6.11
   void reflectAttributeValues(ObjectInstanceHandle theObject,
                               AttributeHandleValueMap theAttributes,
                               byte[] userSuppliedTag,
                               OrderType sentOrdering,
                               TransportationTypeHandle theTransport,
                               LogicalTime<?, ?> theTime,
                               OrderType receivedOrdering,
                               MessageRetractionHandle retractionHandle,
                               SupplementalReflectInfo reflectInfo)
      throws
      FederateInternalError;

   interface SupplementalReceiveInfo extends Serializable {
      boolean hasSentRegions();

      FederateHandle getProducingFederate();

      RegionHandleSet getSentRegions();
   }

   // 6.13
   void receiveInteraction(InteractionClassHandle interactionClass,
                           ParameterHandleValueMap theParameters,
                           byte[] userSuppliedTag,
                           OrderType sentOrdering,
                           TransportationTypeHandle theTransport,
                           SupplementalReceiveInfo receiveInfo)
      throws
      FederateInternalError;

   // 6.13
   void receiveInteraction(InteractionClassHandle interactionClass,
                           ParameterHandleValueMap theParameters,
                           byte[] userSuppliedTag,
                           OrderType sentOrdering,
                           TransportationTypeHandle theTransport,
                           LogicalTime<?, ?> theTime,
                           OrderType receivedOrdering,
                           SupplementalReceiveInfo receiveInfo)
      throws
      FederateInternalError;

   // 6.13
   void receiveInteraction(InteractionClassHandle interactionClass,
                           ParameterHandleValueMap theParameters,
                           byte[] userSuppliedTag,
                           OrderType sentOrdering,
                           TransportationTypeHandle theTransport,
                           LogicalTime<?, ?> theTime,
                           OrderType receivedOrdering,
                           MessageRetractionHandle retractionHandle,
                           SupplementalReceiveInfo receiveInfo)
      throws
      FederateInternalError;

   void receiveDirectedInteraction(InteractionClassHandle interactionClass,
                                   ObjectInstanceHandle theObject,
                                   ParameterHandleValueMap theParameters,
                                   byte[] userSuppliedTag,
                                   OrderType sentOrdering,
                                   TransportationTypeHandle theTransport,
                                   SupplementalReceiveInfo receiveInfo)
      throws
      FederateInternalError;

   // 6.13
   void receiveDirectedInteraction(InteractionClassHandle interactionClass,
                                   ObjectInstanceHandle theObject,
                                   ParameterHandleValueMap theParameters,
                                   byte[] userSuppliedTag,
                                   OrderType sentOrdering,
                                   TransportationTypeHandle theTransport,
                                   LogicalTime<?, ?> theTime,
                                   OrderType receivedOrdering,
                                   SupplementalReceiveInfo receiveInfo)
      throws
      FederateInternalError;

   // 6.13
   void receiveDirectedInteraction(InteractionClassHandle interactionClass,
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
      FederateInternalError;

   // 6.15
   void removeObjectInstance(ObjectInstanceHandle theObject,
                             byte[] userSuppliedTag,
                             OrderType sentOrdering,
                             FederateHandle producingFederate)
      throws
      FederateInternalError;

   // 6.15
   void removeObjectInstance(ObjectInstanceHandle theObject,
                             byte[] userSuppliedTag,
                             OrderType sentOrdering,
                             LogicalTime<?, ?> theTime,
                             OrderType receivedOrdering,
                             FederateHandle producingFederate)
      throws
      FederateInternalError;

   // 6.15
   void removeObjectInstance(ObjectInstanceHandle theObject,
                             byte[] userSuppliedTag,
                             OrderType sentOrdering,
                             LogicalTime<?, ?> theTime,
                             OrderType receivedOrdering,
                             MessageRetractionHandle retractionHandle,
                             FederateHandle producingFederate)
      throws
      FederateInternalError;

   // 6.17
   void attributesInScope(ObjectInstanceHandle theObject,
                          AttributeHandleSet theAttributes)
      throws
      FederateInternalError;

   // 6.18
   void attributesOutOfScope(ObjectInstanceHandle theObject,
                             AttributeHandleSet theAttributes)
      throws
      FederateInternalError;

   // 6.20
   void provideAttributeValueUpdate(ObjectInstanceHandle theObject,
                                    AttributeHandleSet theAttributes,
                                    byte[] userSuppliedTag)
      throws
      FederateInternalError;

   // 6.21
   void turnUpdatesOnForObjectInstance(ObjectInstanceHandle theObject,
                                       AttributeHandleSet theAttributes)
      throws
      FederateInternalError;

   // 6.21
   void turnUpdatesOnForObjectInstance(ObjectInstanceHandle theObject,
                                       AttributeHandleSet theAttributes,
                                       String updateRateDesignator)
      throws
      FederateInternalError;

   // 6.22
   void turnUpdatesOffForObjectInstance(ObjectInstanceHandle theObject,
                                        AttributeHandleSet theAttributes)
      throws
      FederateInternalError;

   // 6.24
   void confirmAttributeTransportationTypeChange(ObjectInstanceHandle theObject,
                                                 AttributeHandleSet theAttributes,
                                                 TransportationTypeHandle theTransportation)
      throws
      FederateInternalError;

   // 6.27
   void reportAttributeTransportationType(ObjectInstanceHandle theObject,
                                          AttributeHandle theAttribute,
                                          TransportationTypeHandle theTransportation)
      throws
      FederateInternalError;

   // 6.29
   void confirmInteractionTransportationTypeChange(InteractionClassHandle theInteraction,
                                                   TransportationTypeHandle theTransportation)
      throws
      FederateInternalError;

   // 6.31
   void reportInteractionTransportationType(FederateHandle theFederate,
                                            InteractionClassHandle theInteraction,
                                            TransportationTypeHandle theTransportation)
      throws
      FederateInternalError;

///////////////////////////////////
// Ownership Management Services //
///////////////////////////////////

   // 7.4
   void requestAttributeOwnershipAssumption(ObjectInstanceHandle theObject,
                                            AttributeHandleSet offeredAttributes,
                                            byte[] userSuppliedTag)
      throws
      FederateInternalError;

   // 7.5
   void requestDivestitureConfirmation(ObjectInstanceHandle theObject,
                                       AttributeHandleSet offeredAttributes,
                                       byte[] userSuppliedTag)
      throws
      FederateInternalError;

   // 7.7
   void attributeOwnershipAcquisitionNotification(ObjectInstanceHandle theObject,
                                                  AttributeHandleSet securedAttributes,
                                                  byte[] userSuppliedTag)
      throws
      FederateInternalError;

   // 7.10
   void attributeOwnershipUnavailable(ObjectInstanceHandle theObject,
                                      AttributeHandleSet theAttributes,
                                      byte[] userSuppliedTag)
      throws
      FederateInternalError;

   // 7.11
   void requestAttributeOwnershipRelease(ObjectInstanceHandle theObject,
                                         AttributeHandleSet candidateAttributes,
                                         byte[] userSuppliedTag)
      throws
      FederateInternalError;

   // 7.16
   void confirmAttributeOwnershipAcquisitionCancellation(ObjectInstanceHandle theObject,
                                                         AttributeHandleSet theAttributes)
      throws
      FederateInternalError;

   // 7.18
   void informAttributeOwnership(ObjectInstanceHandle theObject,
                                 AttributeHandleSet theAttributes,
                                 FederateHandle theOwner)
      throws
      FederateInternalError;

   // 7.18
   void attributeIsNotOwned(ObjectInstanceHandle theObject,
                            AttributeHandleSet theAttributes)
      throws
      FederateInternalError;

   // 7.18
   void attributeIsOwnedByRTI(ObjectInstanceHandle theObject,
                              AttributeHandleSet theAttributes)
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
   void flushQueueGrant(LogicalTime<?, ?> theTime, LogicalTime<?, ?> optimisticTime)
      throws
      FederateInternalError;

   // 8.14
   void timeAdvanceGrant(LogicalTime<?, ?> theTime)
      throws
      FederateInternalError;

   // 8.23
   void requestRetraction(MessageRetractionHandle theHandle)
      throws
      FederateInternalError;
}

//end FederateAmbassador

