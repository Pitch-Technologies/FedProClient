/*
 *  Copyright (C) 2022 Pitch Technologies AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.pitch.oss.fedpro.client.hla;

import hla.rti1516_202X.*;
import hla.rti1516_202X.exceptions.FederateInternalError;

import java.util.Set;

/**
 *
 */
public class FederateAmbassadorClientAdapter {
   private final FederateAmbassador _federateAmbassador;

   public FederateAmbassadorClientAdapter(FederateAmbassador federateAmbassador)
   {
      _federateAmbassador = federateAmbassador;
   }

   public void federateResigned(String reasonForResignDescription) throws FederateInternalError
   {
      _federateAmbassador.federateResigned(reasonForResignDescription);
   }

   public void reportFederationExecutionMembers(String federationName,
                                                FederationExecutionMemberInformationSet federationExecutionMemberInformationSet)
      throws FederateInternalError
   {
      _federateAmbassador.reportFederationExecutionMembers(federationName, federationExecutionMemberInformationSet);
   }

   // 4.11
   public void reportFederationExecutionDoesNotExist(String federationName) throws FederateInternalError
   {
      _federateAmbassador.reportFederationExecutionDoesNotExist(federationName);
   }

   public void synchronizationPointRegistrationSucceeded(String synchronizationPointLabel) throws FederateInternalError
   {
      _federateAmbassador.synchronizationPointRegistrationSucceeded(synchronizationPointLabel);
   }

   public void synchronizationPointRegistrationFailed(String synchronizationPointLabel,
                                                      SynchronizationPointFailureReason reason)
      throws FederateInternalError
   {
      _federateAmbassador.synchronizationPointRegistrationFailed(synchronizationPointLabel, reason);
   }

   public void announceSynchronizationPoint(String synchronizationPointLabel,
                                            byte[] userSuppliedTag) throws FederateInternalError
   {
      _federateAmbassador.announceSynchronizationPoint(synchronizationPointLabel, userSuppliedTag);
   }

   public void federationSynchronized(String synchronizationPointLabel,
                                      FederateHandleSet failedToSyncSet) throws FederateInternalError
   {
      _federateAmbassador.federationSynchronized(synchronizationPointLabel, failedToSyncSet);
   }

   public void initiateFederateSave(String label) throws FederateInternalError
   {
      _federateAmbassador.initiateFederateSave(label);
   }

   public void initiateFederateSaveWithTime(String label,
                                            LogicalTime<?, ?> time) throws FederateInternalError
   {
      _federateAmbassador.initiateFederateSave(label, time);
   }

   public void federationSaved() throws FederateInternalError
   {
      _federateAmbassador.federationSaved();
   }

   public void federationNotSaved(SaveFailureReason reason) throws FederateInternalError
   {
      _federateAmbassador.federationNotSaved(reason);
   }

   public void federationSaveStatusResponse(FederateHandleSaveStatusPair[] response) throws FederateInternalError
   {
      _federateAmbassador.federationSaveStatusResponse(response);
   }

   public void requestFederationRestoreSucceeded(String label) throws FederateInternalError
   {
      _federateAmbassador.requestFederationRestoreSucceeded(label);
   }

   public void requestFederationRestoreFailed(String label) throws FederateInternalError
   {
      _federateAmbassador.requestFederationRestoreFailed(label);
   }

   public void federationRestoreBegun() throws FederateInternalError
   {
      _federateAmbassador.federationRestoreBegun();
   }

   public void initiateFederateRestore(String label,
                                       String federateName,
                                       FederateHandle federateHandle) throws FederateInternalError
   {
      _federateAmbassador.initiateFederateRestore(label, federateName, federateHandle);
   }

   public void federationRestored() throws FederateInternalError
   {
      _federateAmbassador.federationRestored();
   }

   public void federationNotRestored(RestoreFailureReason reason) throws FederateInternalError
   {
      _federateAmbassador.federationNotRestored(reason);
   }

   public void federationRestoreStatusResponse(FederateRestoreStatus[] response) throws FederateInternalError
   {
      _federateAmbassador.federationRestoreStatusResponse(response);
   }

   public void connectionLost(String faultDescription) throws FederateInternalError
   {
      _federateAmbassador.connectionLost(faultDescription);
   }

   public void reportFederationExecutions(FederationExecutionInformationSet theFederationExecutionInformationSet)
      throws FederateInternalError
   {
      _federateAmbassador.reportFederationExecutions(theFederationExecutionInformationSet);
   }

   public void startRegistrationForObjectClass(ObjectClassHandle theClass) throws FederateInternalError
   {
      _federateAmbassador.startRegistrationForObjectClass(theClass);
   }

   public void stopRegistrationForObjectClass(ObjectClassHandle theClass) throws FederateInternalError
   {
      _federateAmbassador.stopRegistrationForObjectClass(theClass);
   }

   public void turnInteractionsOn(InteractionClassHandle theHandle) throws FederateInternalError
   {
      _federateAmbassador.turnInteractionsOn(theHandle);
   }

   public void turnInteractionsOff(InteractionClassHandle theHandle) throws FederateInternalError
   {
      _federateAmbassador.turnInteractionsOff(theHandle);
   }

   public void objectInstanceNameReservationSucceeded(String objectName) throws FederateInternalError
   {
      _federateAmbassador.objectInstanceNameReservationSucceeded(objectName);
   }

   public void multipleObjectInstanceNameReservationSucceeded(Set<String> objectNames) throws FederateInternalError
   {
      _federateAmbassador.multipleObjectInstanceNameReservationSucceeded(objectNames);
   }

   public void objectInstanceNameReservationFailed(String objectName) throws FederateInternalError
   {
      _federateAmbassador.objectInstanceNameReservationFailed(objectName);
   }

   public void multipleObjectInstanceNameReservationFailed(Set<String> objectNames) throws FederateInternalError
   {
      _federateAmbassador.multipleObjectInstanceNameReservationFailed(objectNames);
   }

   public void discoverObjectInstance(ObjectInstanceHandle theObject,
                                      ObjectClassHandle theObjectClass,
                                      String objectName,
                                      FederateHandle producingFederate) throws FederateInternalError
   {
      _federateAmbassador.discoverObjectInstance(theObject, theObjectClass, objectName, producingFederate);
   }

   public void reflectAttributeValues(ObjectInstanceHandle theObject,
                                      AttributeHandleValueMap theAttributes,
                                      byte[] userSuppliedTag,
                                      OrderType sentOrdering,
                                      TransportationTypeHandle theTransport,
                                      FederateAmbassador.SupplementalReflectInfo reflectInfo)
      throws FederateInternalError
   {
      _federateAmbassador.reflectAttributeValues(theObject,
         theAttributes,
         userSuppliedTag,
         sentOrdering,
         theTransport,
         reflectInfo);
   }

   public void reflectAttributeValuesWithTime(ObjectInstanceHandle theObject,
                                              AttributeHandleValueMap theAttributes,
                                              byte[] userSuppliedTag,
                                              OrderType sentOrdering,
                                              TransportationTypeHandle theTransport,
                                              LogicalTime<?, ?> theTime,
                                              OrderType receivedOrdering,
                                              FederateAmbassador.SupplementalReflectInfo reflectInfo)
      throws FederateInternalError
   {
      _federateAmbassador.reflectAttributeValues(theObject,
         theAttributes,
         userSuppliedTag,
         sentOrdering,
         theTransport,
         theTime,
         receivedOrdering,
         reflectInfo);
   }

   public void reflectAttributeValuesWithRetraction(ObjectInstanceHandle theObject,
                                                    AttributeHandleValueMap theAttributes,
                                                    byte[] userSuppliedTag,
                                                    OrderType sentOrdering,
                                                    TransportationTypeHandle theTransport,
                                                    LogicalTime<?, ?> theTime,
                                                    OrderType receivedOrdering,
                                                    MessageRetractionHandle retractionHandle,
                                                    FederateAmbassador.SupplementalReflectInfo reflectInfo)
      throws FederateInternalError
   {
      _federateAmbassador.reflectAttributeValues(theObject,
         theAttributes,
         userSuppliedTag,
         sentOrdering,
         theTransport,
         theTime,
         receivedOrdering,
         retractionHandle,
         reflectInfo);
   }

   public void receiveInteraction(InteractionClassHandle interactionClass,
                                  ParameterHandleValueMap theParameters,
                                  byte[] userSuppliedTag,
                                  OrderType sentOrdering,
                                  TransportationTypeHandle theTransport,
                                  FederateAmbassador.SupplementalReceiveInfo receiveInfo) throws FederateInternalError
   {
      _federateAmbassador.receiveInteraction(interactionClass,
         theParameters,
         userSuppliedTag,
         sentOrdering,
         theTransport,
         receiveInfo);
   }

   public void receiveInteractionWithTime(InteractionClassHandle interactionClass,
                                          ParameterHandleValueMap theParameters,
                                          byte[] userSuppliedTag,
                                          OrderType sentOrdering,
                                          TransportationTypeHandle theTransport,
                                          LogicalTime<?, ?> theTime,
                                          OrderType receivedOrdering,
                                          FederateAmbassador.SupplementalReceiveInfo receiveInfo)
      throws FederateInternalError
   {
      _federateAmbassador.receiveInteraction(interactionClass,
         theParameters,
         userSuppliedTag,
         sentOrdering,
         theTransport,
         theTime,
         receivedOrdering,
         receiveInfo);
   }

   public void receiveInteractionWithRetraction(InteractionClassHandle interactionClass,
                                                ParameterHandleValueMap theParameters,
                                                byte[] userSuppliedTag,
                                                OrderType sentOrdering,
                                                TransportationTypeHandle theTransport,
                                                LogicalTime<?, ?> theTime,
                                                OrderType receivedOrdering,
                                                MessageRetractionHandle retractionHandle,
                                                FederateAmbassador.SupplementalReceiveInfo receiveInfo)
      throws FederateInternalError
   {
      _federateAmbassador.receiveInteraction(interactionClass,
         theParameters,
         userSuppliedTag,
         sentOrdering,
         theTransport,
         theTime,
         receivedOrdering,
         retractionHandle,
         receiveInfo);
   }


   public void receiveDirectedInteraction(InteractionClassHandle interactionClass,
                                          ObjectInstanceHandle objectInstanceHandle,
                                          ParameterHandleValueMap theParameters,
                                          byte[] userSuppliedTag,
                                          OrderType sentOrdering,
                                          TransportationTypeHandle theTransport,
                                          FederateAmbassador.SupplementalReceiveInfo receiveInfo)
   {

   }

   public void receiveDirectedInteractionWithTime(InteractionClassHandle interactionClass,
                                                  ObjectInstanceHandle objectInstanceHandle,
                                                  ParameterHandleValueMap theParameters,
                                                  byte[] userSuppliedTag,
                                                  OrderType sentOrdering,
                                                  TransportationTypeHandle theTransport,
                                                  LogicalTime<?, ?> theTime,
                                                  OrderType receivedOrdering,
                                                  FederateAmbassador.SupplementalReceiveInfo receiveInfo)
   {

   }

   public void receiveDirectedInteractionWithRetraction(InteractionClassHandle interactionClass,
                                                        ObjectInstanceHandle objectInstanceHandle,
                                                        ParameterHandleValueMap theParameters,
                                                        byte[] userSuppliedTag,
                                                        OrderType sentOrdering,
                                                        TransportationTypeHandle theTransport,
                                                        LogicalTime<?, ?> theTime,
                                                        OrderType receivedOrdering,
                                                        MessageRetractionHandle retractionHandle,
                                                        FederateAmbassador.SupplementalReceiveInfo receiveInfo)
   {

   }

   public void removeObjectInstance(ObjectInstanceHandle theObject,
                                    byte[] userSuppliedTag,
                                    OrderType sentOrdering,
                                    FederateHandle producingFederate) throws FederateInternalError
   {
      _federateAmbassador.removeObjectInstance(theObject, userSuppliedTag, sentOrdering, producingFederate);
   }

   public void removeObjectInstanceWithTime(ObjectInstanceHandle theObject,
                                            byte[] userSuppliedTag,
                                            OrderType sentOrdering,
                                            LogicalTime<?, ?> theTime,
                                            OrderType receivedOrdering,
                                            FederateHandle producingFederate) throws FederateInternalError
   {
      _federateAmbassador.removeObjectInstance(theObject,
         userSuppliedTag,
         sentOrdering,
         theTime,
         receivedOrdering,
         producingFederate);
   }

   public void removeObjectInstanceWithRetraction(ObjectInstanceHandle theObject,
                                                  byte[] userSuppliedTag,
                                                  OrderType sentOrdering,
                                                  LogicalTime<?, ?> theTime,
                                                  OrderType receivedOrdering,
                                                  MessageRetractionHandle retractionHandle,
                                                  FederateHandle producingFederate) throws FederateInternalError
   {
      _federateAmbassador.removeObjectInstance(theObject,
         userSuppliedTag,
         sentOrdering,
         theTime,
         receivedOrdering,
         retractionHandle,
         producingFederate);
   }

   public void attributesInScope(ObjectInstanceHandle theObject,
                                 AttributeHandleSet theAttributes) throws FederateInternalError
   {
      _federateAmbassador.attributesInScope(theObject, theAttributes);
   }

   public void attributesOutOfScope(ObjectInstanceHandle theObject,
                                    AttributeHandleSet theAttributes) throws FederateInternalError
   {
      _federateAmbassador.attributesOutOfScope(theObject, theAttributes);
   }

   public void provideAttributeValueUpdate(ObjectInstanceHandle theObject,
                                           AttributeHandleSet theAttributes,
                                           byte[] userSuppliedTag) throws FederateInternalError
   {
      _federateAmbassador.provideAttributeValueUpdate(theObject, theAttributes, userSuppliedTag);
   }

   public void turnUpdatesOnForObjectInstance(ObjectInstanceHandle theObject,
                                              AttributeHandleSet theAttributes) throws FederateInternalError
   {
      _federateAmbassador.turnUpdatesOnForObjectInstance(theObject, theAttributes);
   }

   public void turnUpdatesOnForObjectInstanceWithRate(ObjectInstanceHandle theObject,
                                                      AttributeHandleSet theAttributes,
                                                      String updateRateDesignator) throws FederateInternalError
   {
      _federateAmbassador.turnUpdatesOnForObjectInstance(theObject, theAttributes, updateRateDesignator);
   }

   public void turnUpdatesOffForObjectInstance(ObjectInstanceHandle theObject,
                                               AttributeHandleSet theAttributes) throws FederateInternalError
   {
      _federateAmbassador.turnUpdatesOffForObjectInstance(theObject, theAttributes);
   }

   public void confirmAttributeTransportationTypeChange(ObjectInstanceHandle theObject,
                                                        AttributeHandleSet theAttributes,
                                                        TransportationTypeHandle theTransportation)
      throws FederateInternalError
   {
      _federateAmbassador.confirmAttributeTransportationTypeChange(theObject, theAttributes, theTransportation);
   }

   public void confirmInteractionTransportationTypeChange(InteractionClassHandle theInteraction,
                                                          TransportationTypeHandle theTransportation)
      throws FederateInternalError
   {
      _federateAmbassador.confirmInteractionTransportationTypeChange(theInteraction, theTransportation);
   }

   public void reportAttributeTransportationType(ObjectInstanceHandle theObject,
                                                 AttributeHandle theAttribute,
                                                 TransportationTypeHandle theTransportation)
      throws FederateInternalError
   {
      _federateAmbassador.reportAttributeTransportationType(theObject, theAttribute, theTransportation);
   }

   public void reportInteractionTransportationType(FederateHandle theFederate,
                                                   InteractionClassHandle theInteraction,
                                                   TransportationTypeHandle theTransportation)
      throws FederateInternalError
   {
      _federateAmbassador.reportInteractionTransportationType(theFederate, theInteraction, theTransportation);
   }

   public void requestAttributeOwnershipAssumption(ObjectInstanceHandle theObject,
                                                   AttributeHandleSet offeredAttributes,
                                                   byte[] userSuppliedTag) throws FederateInternalError
   {
      _federateAmbassador.requestAttributeOwnershipAssumption(theObject, offeredAttributes, userSuppliedTag);
   }

   public void requestDivestitureConfirmation(ObjectInstanceHandle theObject,
                                              AttributeHandleSet offeredAttributes,
                                              byte[] userSuppliedTag) throws FederateInternalError
   {
      _federateAmbassador.requestDivestitureConfirmation(theObject, offeredAttributes, userSuppliedTag);
   }

   public void attributeOwnershipAcquisitionNotification(ObjectInstanceHandle theObject,
                                                         AttributeHandleSet securedAttributes,
                                                         byte[] userSuppliedTag) throws FederateInternalError
   {
      _federateAmbassador.attributeOwnershipAcquisitionNotification(theObject, securedAttributes, userSuppliedTag);
   }

   public void attributeOwnershipUnavailable(ObjectInstanceHandle theObject,
                                             AttributeHandleSet theAttributes,
                                             byte[] userSuppliedTag) throws FederateInternalError
   {
      _federateAmbassador.attributeOwnershipUnavailable(theObject, theAttributes, userSuppliedTag);
   }

   public void requestAttributeOwnershipRelease(ObjectInstanceHandle theObject,
                                                AttributeHandleSet candidateAttributes,
                                                byte[] userSuppliedTag) throws FederateInternalError
   {
      _federateAmbassador.requestAttributeOwnershipRelease(theObject, candidateAttributes, userSuppliedTag);
   }

   public void confirmAttributeOwnershipAcquisitionCancellation(ObjectInstanceHandle theObject,
                                                                AttributeHandleSet theAttributes)
      throws FederateInternalError
   {
      _federateAmbassador.confirmAttributeOwnershipAcquisitionCancellation(theObject, theAttributes);
   }

   public void informAttributeOwnership(ObjectInstanceHandle theObject,
                                        AttributeHandleSet theAttributes,
                                        FederateHandle theOwner) throws FederateInternalError
   {
      _federateAmbassador.informAttributeOwnership(theObject, theAttributes, theOwner);
   }

   public void attributeIsNotOwned(ObjectInstanceHandle theObject,
                                   AttributeHandleSet theAttributes) throws FederateInternalError
   {
      _federateAmbassador.attributeIsNotOwned(theObject, theAttributes);
   }

   public void attributeIsOwnedByRTI(ObjectInstanceHandle theObject,
                                     AttributeHandleSet theAttributes) throws FederateInternalError
   {
      _federateAmbassador.attributeIsOwnedByRTI(theObject, theAttributes);
   }

   public void timeRegulationEnabled(LogicalTime<?, ?> time) throws FederateInternalError
   {
      _federateAmbassador.timeRegulationEnabled(time);
   }

   public void timeConstrainedEnabled(LogicalTime<?, ?> time) throws FederateInternalError
   {
      _federateAmbassador.timeConstrainedEnabled(time);
   }

   public void timeAdvanceGrant(LogicalTime<?, ?> theTime) throws FederateInternalError
   {
      _federateAmbassador.timeAdvanceGrant(theTime);
   }

   public void flushQueueGrant(LogicalTime<?, ?> theTime,
                               LogicalTime<?, ?> optimisticTime) throws FederateInternalError
   {
      _federateAmbassador.flushQueueGrant(theTime, optimisticTime);
   }

   public void requestRetraction(MessageRetractionHandle theHandle) throws FederateInternalError
   {
      _federateAmbassador.requestRetraction(theHandle);
   }
}
