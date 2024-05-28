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
import hla.rti1516_202X.time.LogicalTime;

import java.util.Set;


public class FederateAmbassadorClientAdapter {
   private final FederateAmbassador _federateAmbassador;

   public void connectionLost(String faultDescription)
   throws FederateInternalError
   {
      _federateAmbassador.connectionLost(faultDescription);
   }

   public FederateAmbassadorClientAdapter(FederateAmbassador federateAmbassador)
   {
      _federateAmbassador = federateAmbassador;
   }

   public void federateResigned(String reasonForResignDescription)
   throws FederateInternalError
   {
      _federateAmbassador.federateResigned(reasonForResignDescription);
   }

   public void reportFederationExecutions(FederationExecutionInformationSet report)
   throws FederateInternalError
   {
      _federateAmbassador.reportFederationExecutions(report);
   }

   public void reportFederationExecutionMembers(
         String federationName,
         FederationExecutionMemberInformationSet report)
   throws FederateInternalError
   {
      _federateAmbassador.reportFederationExecutionMembers(federationName, report);
   }

   // 4.11
   public void reportFederationExecutionDoesNotExist(String federationName)
   throws FederateInternalError
   {
      _federateAmbassador.reportFederationExecutionDoesNotExist(federationName);
   }

   public void synchronizationPointRegistrationSucceeded(String synchronizationPointLabel)
   throws FederateInternalError
   {
      _federateAmbassador.synchronizationPointRegistrationSucceeded(synchronizationPointLabel);
   }

   public void synchronizationPointRegistrationFailed(
         String synchronizationPointLabel,
         SynchronizationPointFailureReason reason)
   throws FederateInternalError
   {
      _federateAmbassador.synchronizationPointRegistrationFailed(synchronizationPointLabel, reason);
   }

   public void announceSynchronizationPoint(
         String synchronizationPointLabel,
         byte[] userSuppliedTag)
   throws FederateInternalError
   {
      _federateAmbassador.announceSynchronizationPoint(synchronizationPointLabel, userSuppliedTag);
   }

   public void federationSynchronized(
         String synchronizationPointLabel,
         FederateHandleSet failedToSyncSet)
   throws FederateInternalError
   {
      _federateAmbassador.federationSynchronized(synchronizationPointLabel, failedToSyncSet);
   }

   public void initiateFederateSave(String label)
   throws FederateInternalError
   {
      _federateAmbassador.initiateFederateSave(label);
   }

   public void initiateFederateSaveWithTime(
         String label,
         LogicalTime<?, ?> time)
   throws FederateInternalError
   {
      _federateAmbassador.initiateFederateSave(label, time);
   }

   public void federationSaved()
   throws FederateInternalError
   {
      _federateAmbassador.federationSaved();
   }

   public void federationNotSaved(SaveFailureReason reason)
   throws FederateInternalError
   {
      _federateAmbassador.federationNotSaved(reason);
   }

   public void federationSaveStatusResponse(FederateHandleSaveStatusPair[] response)
   throws FederateInternalError
   {
      _federateAmbassador.federationSaveStatusResponse(response);
   }

   public void requestFederationRestoreSucceeded(String label)
   throws FederateInternalError
   {
      _federateAmbassador.requestFederationRestoreSucceeded(label);
   }

   public void requestFederationRestoreFailed(String label)
   throws FederateInternalError
   {
      _federateAmbassador.requestFederationRestoreFailed(label);
   }

   public void federationRestoreBegun()
   throws FederateInternalError
   {
      _federateAmbassador.federationRestoreBegun();
   }

   public void initiateFederateRestore(
         String label,
         String federateName,
         FederateHandle postRestoreFederateHandle)
   throws FederateInternalError
   {
      _federateAmbassador.initiateFederateRestore(label, federateName, postRestoreFederateHandle);
   }

   public void federationRestored()
   throws FederateInternalError
   {
      _federateAmbassador.federationRestored();
   }

   public void federationNotRestored(RestoreFailureReason reason)
   throws FederateInternalError
   {
      _federateAmbassador.federationNotRestored(reason);
   }

   public void federationRestoreStatusResponse(FederateRestoreStatus[] response)
   throws FederateInternalError
   {
      _federateAmbassador.federationRestoreStatusResponse(response);
   }

   public void startRegistrationForObjectClass(ObjectClassHandle objectClass)
   throws FederateInternalError
   {
      _federateAmbassador.startRegistrationForObjectClass(objectClass);
   }

   public void stopRegistrationForObjectClass(ObjectClassHandle objectClass)
   throws FederateInternalError
   {
      _federateAmbassador.stopRegistrationForObjectClass(objectClass);
   }

   public void turnInteractionsOn(InteractionClassHandle interactionClass)
   throws FederateInternalError
   {
      _federateAmbassador.turnInteractionsOn(interactionClass);
   }

   public void turnInteractionsOff(InteractionClassHandle interactionClass)
   throws FederateInternalError
   {
      _federateAmbassador.turnInteractionsOff(interactionClass);
   }

   public void objectInstanceNameReservationSucceeded(String objectInstanceName)
   throws FederateInternalError
   {
      _federateAmbassador.objectInstanceNameReservationSucceeded(objectInstanceName);
   }

   public void multipleObjectInstanceNameReservationSucceeded(Set<String> objectInstanceNames)
   throws FederateInternalError
   {
      _federateAmbassador.multipleObjectInstanceNameReservationSucceeded(objectInstanceNames);
   }

   public void objectInstanceNameReservationFailed(String objectInstanceName)
   throws FederateInternalError
   {
      _federateAmbassador.objectInstanceNameReservationFailed(objectInstanceName);
   }

   public void multipleObjectInstanceNameReservationFailed(Set<String> objectInstanceNames)
   throws FederateInternalError
   {
      _federateAmbassador.multipleObjectInstanceNameReservationFailed(objectInstanceNames);
   }

   public void discoverObjectInstance(
         ObjectInstanceHandle objectInstance,
         ObjectClassHandle objectClass,
         String objectInstanceName,
         FederateHandle producingFederate)
   throws FederateInternalError
   {
      _federateAmbassador.discoverObjectInstance(objectInstance, objectClass, objectInstanceName, producingFederate);
   }

   public void reflectAttributeValues(
         ObjectInstanceHandle objectInstance,
         AttributeHandleValueMap attributeValues,
         byte[] userSuppliedTag,
         TransportationTypeHandle transportationType,
         FederateHandle producingFederate,
         RegionHandleSet optionalSentRegions)
   throws FederateInternalError
   {
      _federateAmbassador.reflectAttributeValues(
            objectInstance,
            attributeValues,
            userSuppliedTag,
            transportationType,
            producingFederate,
            optionalSentRegions);
   }

   public void reflectAttributeValuesWithTime(
         ObjectInstanceHandle objectInstance,
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
      _federateAmbassador.reflectAttributeValues(
            objectInstance,
            attributeValues,
            userSuppliedTag,
            transportationType,
            producingFederate,
            optionalSentRegions,
            time,
            sentOrderType,
            receivedOrderType,
            optionalRetraction);
   }

   public void receiveInteraction(
         InteractionClassHandle interactionClass,
         ParameterHandleValueMap parameterValues,
         byte[] userSuppliedTag,
         TransportationTypeHandle transportationType,
         FederateHandle producingFederate,
         RegionHandleSet optionalSentRegions)
   throws FederateInternalError
   {
      _federateAmbassador.receiveInteraction(
            interactionClass,
            parameterValues,
            userSuppliedTag,
            transportationType,
            producingFederate,
            optionalSentRegions);
   }

   public void receiveInteractionWithTime(
         InteractionClassHandle interactionClass,
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
      _federateAmbassador.receiveInteraction(
            interactionClass,
            parameterValues,
            userSuppliedTag,
            transportationType,
            producingFederate,
            optionalSentRegions,
            time,
            sentOrderType,
            receivedOrderType,
            optionalRetraction);
   }

   public void receiveDirectedInteraction(
         InteractionClassHandle interactionClass,
         ObjectInstanceHandle objectInstance,
         ParameterHandleValueMap parameterValues,
         byte[] userSuppliedTag,
         TransportationTypeHandle transportationType,
         FederateHandle producingFederate)
   throws FederateInternalError
   {
      _federateAmbassador.receiveDirectedInteraction(
            interactionClass,
            objectInstance,
            parameterValues,
            userSuppliedTag,
            transportationType,
            producingFederate);
   }

   public void receiveDirectedInteractionWithTime(
         InteractionClassHandle interactionClass,
         ObjectInstanceHandle objectInstance,
         ParameterHandleValueMap parameterValues,
         byte[] userSuppliedTag,
         TransportationTypeHandle transportationType,
         FederateHandle producingFederate,
         LogicalTime<?, ?> time,
         OrderType sentOrderType,
         OrderType receivedOrderType,
         MessageRetractionHandle optionalRetraction)
   throws FederateInternalError
   {
      _federateAmbassador.receiveDirectedInteraction(
            interactionClass,
            objectInstance,
            parameterValues,
            userSuppliedTag,
            transportationType,
            producingFederate,
            time,
            sentOrderType,
            receivedOrderType,
            optionalRetraction);
   }

   public void removeObjectInstance(
         ObjectInstanceHandle theObject,
         byte[] userSuppliedTag,
         FederateHandle producingFederate)
   throws FederateInternalError
   {
      _federateAmbassador.removeObjectInstance(theObject, userSuppliedTag, producingFederate);
   }

   public void removeObjectInstanceWithTime(
         ObjectInstanceHandle theObject,
         byte[] userSuppliedTag,
         FederateHandle producingFederate,
         LogicalTime<?, ?> time,
         OrderType sentOrderType,
         OrderType receivedOrderType,
         MessageRetractionHandle optionalRetraction)
   throws FederateInternalError
   {
      _federateAmbassador.removeObjectInstance(
            theObject,
            userSuppliedTag,
            producingFederate,
            time,
            sentOrderType,
            receivedOrderType,
            optionalRetraction);
   }

   public void attributesInScope(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes)
   throws FederateInternalError
   {
      _federateAmbassador.attributesInScope(objectInstance, attributes);
   }

   public void attributesOutOfScope(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes)
   throws FederateInternalError
   {
      _federateAmbassador.attributesOutOfScope(objectInstance, attributes);
   }

   public void provideAttributeValueUpdate(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes,
         byte[] userSuppliedTag)
   throws FederateInternalError
   {
      _federateAmbassador.provideAttributeValueUpdate(objectInstance, attributes, userSuppliedTag);
   }

   public void turnUpdatesOnForObjectInstance(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes)
   throws FederateInternalError
   {
      _federateAmbassador.turnUpdatesOnForObjectInstance(objectInstance, attributes);
   }

   public void turnUpdatesOnForObjectInstanceWithRate(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes,
         String updateRateDesignator)
   throws FederateInternalError
   {
      _federateAmbassador.turnUpdatesOnForObjectInstance(objectInstance, attributes, updateRateDesignator);
   }

   public void turnUpdatesOffForObjectInstance(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes)
   throws FederateInternalError
   {
      _federateAmbassador.turnUpdatesOffForObjectInstance(objectInstance, attributes);
   }

   public void confirmAttributeTransportationTypeChange(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes,
         TransportationTypeHandle transportationType)
   throws FederateInternalError
   {
      _federateAmbassador.confirmAttributeTransportationTypeChange(objectInstance, attributes, transportationType);
   }

   public void confirmInteractionTransportationTypeChange(
         InteractionClassHandle interactionClass,
         TransportationTypeHandle transportationType)
   throws FederateInternalError
   {
      _federateAmbassador.confirmInteractionTransportationTypeChange(interactionClass, transportationType);
   }

   public void reportAttributeTransportationType(
         ObjectInstanceHandle objectInstance,
         AttributeHandle attribute,
         TransportationTypeHandle transportationType)
   throws FederateInternalError
   {
      _federateAmbassador.reportAttributeTransportationType(objectInstance, attribute, transportationType);
   }

   public void reportInteractionTransportationType(
         FederateHandle federate,
         InteractionClassHandle interactionClass,
         TransportationTypeHandle transportationType)
   throws FederateInternalError
   {
      _federateAmbassador.reportInteractionTransportationType(federate, interactionClass, transportationType);
   }

   public void requestAttributeOwnershipAssumption(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet offeredAttributes,
         byte[] userSuppliedTag)
   throws FederateInternalError
   {
      _federateAmbassador.requestAttributeOwnershipAssumption(objectInstance, offeredAttributes, userSuppliedTag);
   }

   public void requestDivestitureConfirmation(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet releasedAttributes,
         byte[] userSuppliedTag)
   throws FederateInternalError
   {
      _federateAmbassador.requestDivestitureConfirmation(objectInstance, releasedAttributes, userSuppliedTag);
   }

   public void attributeOwnershipAcquisitionNotification(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet securedAttributes,
         byte[] userSuppliedTag)
   throws FederateInternalError
   {
      _federateAmbassador.attributeOwnershipAcquisitionNotification(objectInstance, securedAttributes, userSuppliedTag);
   }

   public void attributeOwnershipUnavailable(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes,
         byte[] userSuppliedTag)
   throws FederateInternalError
   {
      _federateAmbassador.attributeOwnershipUnavailable(objectInstance, attributes, userSuppliedTag);
   }

   public void requestAttributeOwnershipRelease(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet candidateAttributes,
         byte[] userSuppliedTag)
   throws FederateInternalError
   {
      _federateAmbassador.requestAttributeOwnershipRelease(objectInstance, candidateAttributes, userSuppliedTag);
   }

   public void confirmAttributeOwnershipAcquisitionCancellation(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes)
   throws FederateInternalError
   {
      _federateAmbassador.confirmAttributeOwnershipAcquisitionCancellation(objectInstance, attributes);
   }

   public void informAttributeOwnership(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes,
         FederateHandle owner)
   throws FederateInternalError
   {
      _federateAmbassador.informAttributeOwnership(objectInstance, attributes, owner);
   }

   public void attributeIsNotOwned(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes)
   throws FederateInternalError
   {
      _federateAmbassador.attributeIsNotOwned(objectInstance, attributes);
   }

   public void attributeIsOwnedByRTI(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes)
   throws FederateInternalError
   {
      _federateAmbassador.attributeIsOwnedByRTI(objectInstance, attributes);
   }

   public void timeRegulationEnabled(LogicalTime<?, ?> time)
   throws FederateInternalError
   {
      _federateAmbassador.timeRegulationEnabled(time);
   }

   public void timeConstrainedEnabled(LogicalTime<?, ?> time)
   throws FederateInternalError
   {
      _federateAmbassador.timeConstrainedEnabled(time);
   }

   public void timeAdvanceGrant(LogicalTime<?, ?> time)
   throws FederateInternalError
   {
      _federateAmbassador.timeAdvanceGrant(time);
   }

   public void flushQueueGrant(
         LogicalTime<?, ?> time,
         LogicalTime<?, ?> optimisticTime)
   throws FederateInternalError
   {
      _federateAmbassador.flushQueueGrant(time, optimisticTime);
   }

   public void requestRetraction(MessageRetractionHandle retraction)
   throws FederateInternalError
   {
      _federateAmbassador.requestRetraction(retraction);
   }
}
