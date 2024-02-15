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
import hla.rti1516_202X.time.LogicalTime;
import hla.rti1516_202X.time.LogicalTimeInterval;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface AsyncRTIambassador {
   CompletableFuture<Void> listFederationExecutions();

   CompletableFuture<Void> listFederationExecutionMembers(
         String federationName);

   CompletableFuture<Void> registerFederationSynchronizationPoint(
         String synchronizationPointLabel, 
         byte[] userSuppliedTag);

   CompletableFuture<Void> registerFederationSynchronizationPointWithSet(
         String synchronizationPointLabel, 
         byte[] userSuppliedTag, 
         FederateHandleSet synchronizationSet);

   CompletableFuture<Void> synchronizationPointAchieved(
         String synchronizationPointLabel, 
         boolean successfully);

   CompletableFuture<Void> requestFederationSave(
         String label);

   CompletableFuture<Void> requestFederationSaveWithTime(
         String label, 
         LogicalTime<?, ?> time);

   CompletableFuture<Void> federateSaveBegun();

   CompletableFuture<Void> federateSaveComplete();

   CompletableFuture<Void> federateSaveNotComplete();

   CompletableFuture<Void> abortFederationSave();

   CompletableFuture<Void> queryFederationSaveStatus();

   CompletableFuture<Void> requestFederationRestore(
         String label);

   CompletableFuture<Void> federateRestoreComplete();

   CompletableFuture<Void> federateRestoreNotComplete();

   CompletableFuture<Void> abortFederationRestore();

   CompletableFuture<Void> queryFederationRestoreStatus();

   CompletableFuture<Void> publishObjectClassAttributes(
         ObjectClassHandle objectClass, 
         AttributeHandleSet attributes);

   CompletableFuture<Void> unpublishObjectClass(
         ObjectClassHandle objectClass);

   CompletableFuture<Void> unpublishObjectClassAttributes(
         ObjectClassHandle objectClass, 
         AttributeHandleSet attributes);

   CompletableFuture<Void> publishObjectClassDirectedInteractions(
         ObjectClassHandle objectClass, 
         InteractionClassHandleSet interactionClasses);

   CompletableFuture<Void> unpublishObjectClassDirectedInteractions(
         ObjectClassHandle objectClass);

   CompletableFuture<Void> unpublishObjectClassDirectedInteractionsWithSet(
         ObjectClassHandle objectClass, 
         InteractionClassHandleSet interactionClasses);

   CompletableFuture<Void> publishInteractionClass(
         InteractionClassHandle interactionClass);

   CompletableFuture<Void> unpublishInteractionClass(
         InteractionClassHandle interactionClass);

   CompletableFuture<Void> subscribeObjectClassAttributes(
         ObjectClassHandle objectClass, 
         AttributeHandleSet attributes);

   CompletableFuture<Void> subscribeObjectClassAttributesWithRate(
         ObjectClassHandle objectClass, 
         AttributeHandleSet attributes, 
         String updateRateDesignator);

   CompletableFuture<Void> subscribeObjectClassAttributesPassively(
         ObjectClassHandle objectClass, 
         AttributeHandleSet attributes);

   CompletableFuture<Void> subscribeObjectClassAttributesPassivelyWithRate(
         ObjectClassHandle objectClass, 
         AttributeHandleSet attributes, 
         String updateRateDesignator);

   CompletableFuture<Void> unsubscribeObjectClass(
         ObjectClassHandle objectClass);

   CompletableFuture<Void> unsubscribeObjectClassAttributes(
         ObjectClassHandle objectClass, 
         AttributeHandleSet attributes);

   CompletableFuture<Void> subscribeObjectClassDirectedInteractions(
         ObjectClassHandle objectClass, 
         InteractionClassHandleSet interactionClasses);

   CompletableFuture<Void> unsubscribeObjectClassDirectedInteractions(
         ObjectClassHandle objectClass);

   CompletableFuture<Void> unsubscribeObjectClassDirectedInteractionsWithSet(
         ObjectClassHandle objectClass, 
         InteractionClassHandleSet interactionClasses);

   CompletableFuture<Void> subscribeInteractionClass(
         InteractionClassHandle interactionClass);

   CompletableFuture<Void> subscribeInteractionClassPassively(
         InteractionClassHandle interactionClass);

   CompletableFuture<Void> unsubscribeInteractionClass(
         InteractionClassHandle interactionClass);

   CompletableFuture<Void> reserveObjectInstanceName(
         String objectInstanceName);

   CompletableFuture<Void> releaseObjectInstanceName(
         String objectInstanceName);

   CompletableFuture<Void> reserveMultipleObjectInstanceName(
         Set<String> objectInstanceNames);

   CompletableFuture<Void> releaseMultipleObjectInstanceName(
         Set<String> objectInstanceNames);

   CompletableFuture<ObjectInstanceHandle> registerObjectInstance(
         ObjectClassHandle objectClass);

   CompletableFuture<ObjectInstanceHandle> registerObjectInstanceWithName(
         ObjectClassHandle objectClass, 
         String objectInstanceName);

   CompletableFuture<Void> updateAttributeValues(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleValueMap attributeValues, 
         byte[] userSuppliedTag);

   CompletableFuture<MessageRetractionReturn> updateAttributeValuesWithTime(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleValueMap attributeValues, 
         byte[] userSuppliedTag, 
         LogicalTime<?, ?> time);

   CompletableFuture<Void> sendInteraction(
         InteractionClassHandle interactionClass, 
         ParameterHandleValueMap parameterValues, 
         byte[] userSuppliedTag);

   CompletableFuture<MessageRetractionReturn> sendInteractionWithTime(
         InteractionClassHandle interactionClass, 
         ParameterHandleValueMap parameterValues, 
         byte[] userSuppliedTag, 
         LogicalTime<?, ?> time);

   CompletableFuture<Void> sendDirectedInteraction(
         InteractionClassHandle interactionClass, 
         ObjectInstanceHandle objectInstance, 
         ParameterHandleValueMap parameterValues, 
         byte[] userSuppliedTag);

   CompletableFuture<MessageRetractionReturn> sendDirectedInteractionWithTime(
         InteractionClassHandle interactionClass, 
         ObjectInstanceHandle objectInstance, 
         ParameterHandleValueMap parameterValues, 
         byte[] userSuppliedTag, 
         LogicalTime<?, ?> time);

   CompletableFuture<Void> deleteObjectInstance(
         ObjectInstanceHandle objectInstance, 
         byte[] userSuppliedTag);

   CompletableFuture<MessageRetractionReturn> deleteObjectInstanceWithTime(
         ObjectInstanceHandle objectInstance, 
         byte[] userSuppliedTag, 
         LogicalTime<?, ?> time);

   CompletableFuture<Void> localDeleteObjectInstance(
         ObjectInstanceHandle objectInstance);

   CompletableFuture<Void> requestInstanceAttributeValueUpdate(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleSet attributes, 
         byte[] userSuppliedTag);

   CompletableFuture<Void> requestClassAttributeValueUpdate(
         ObjectClassHandle objectClass, 
         AttributeHandleSet attributes, 
         byte[] userSuppliedTag);

   CompletableFuture<Void> requestAttributeTransportationTypeChange(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleSet attributes, 
         TransportationTypeHandle transportationType);

   CompletableFuture<Void> changeDefaultAttributeTransportationType(
         ObjectClassHandle theObjectClass, 
         AttributeHandleSet attributes, 
         TransportationTypeHandle transportationType);

   CompletableFuture<Void> queryAttributeTransportationType(
         ObjectInstanceHandle objectInstance, 
         AttributeHandle attribute);

   CompletableFuture<Void> requestInteractionTransportationTypeChange(
         InteractionClassHandle interactionClass, 
         TransportationTypeHandle transportationType);

   CompletableFuture<Void> queryInteractionTransportationType(
         FederateHandle federate, 
         InteractionClassHandle interactionClass);

   CompletableFuture<Void> unconditionalAttributeOwnershipDivestiture(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleSet attributes, 
         byte[] userSuppliedTag);

   CompletableFuture<Void> negotiatedAttributeOwnershipDivestiture(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleSet attributes, 
         byte[] userSuppliedTag);

   CompletableFuture<Void> confirmDivestiture(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleSet attributes, 
         byte[] userSuppliedTag);

   CompletableFuture<Void> attributeOwnershipAcquisition(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleSet desiredAttributes, 
         byte[] userSuppliedTag);

   CompletableFuture<Void> attributeOwnershipAcquisitionIfAvailable(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleSet desiredAttributes, 
         byte[] userSuppliedTag);

   CompletableFuture<Void> attributeOwnershipReleaseDenied(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleSet attributes, 
         byte[] userSuppliedTag);

   CompletableFuture<AttributeHandleSet> attributeOwnershipDivestitureIfWanted(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleSet attributes, 
         byte[] userSuppliedTag);

   CompletableFuture<Void> cancelNegotiatedAttributeOwnershipDivestiture(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleSet attributes);

   CompletableFuture<Void> cancelAttributeOwnershipAcquisition(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleSet attributes);

   CompletableFuture<Void> queryAttributeOwnership(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleSet attributes);

   CompletableFuture<Boolean> isAttributeOwnedByFederate(
         ObjectInstanceHandle objectInstance, 
         AttributeHandle attribute);

   CompletableFuture<Void> enableTimeRegulation(
         LogicalTimeInterval<?> lookahead);

   CompletableFuture<Void> disableTimeRegulation();

   CompletableFuture<Void> enableTimeConstrained();

   CompletableFuture<Void> disableTimeConstrained();

   CompletableFuture<Void> timeAdvanceRequest(
         LogicalTime<?, ?> time);

   CompletableFuture<Void> timeAdvanceRequestAvailable(
         LogicalTime<?, ?> time);

   CompletableFuture<Void> nextMessageRequest(
         LogicalTime<?, ?> time);

   CompletableFuture<Void> nextMessageRequestAvailable(
         LogicalTime<?, ?> time);

   CompletableFuture<Void> flushQueueRequest(
         LogicalTime<?, ?> time);

   CompletableFuture<Void> enableAsynchronousDelivery();

   CompletableFuture<Void> disableAsynchronousDelivery();

   CompletableFuture<TimeQueryReturn> queryGALT();

   CompletableFuture<LogicalTime> queryLogicalTime();

   CompletableFuture<TimeQueryReturn> queryLITS();

   CompletableFuture<Void> modifyLookahead(
         LogicalTimeInterval<?> lookahead);

   CompletableFuture<LogicalTimeInterval> queryLookahead();

   CompletableFuture<Void> retract(
         MessageRetractionHandle retraction);

   CompletableFuture<Void> changeAttributeOrderType(
         ObjectInstanceHandle objectInstance, 
         AttributeHandleSet attributes, 
         OrderType orderType);

   CompletableFuture<Void> changeDefaultAttributeOrderType(
         ObjectClassHandle theObjectClass, 
         AttributeHandleSet attributes, 
         OrderType orderType);

   CompletableFuture<Void> changeInteractionOrderType(
         InteractionClassHandle interactionClass, 
         OrderType orderType);

   CompletableFuture<RegionHandle> createRegion(
         DimensionHandleSet dimensions);

   CompletableFuture<Void> commitRegionModifications(
         RegionHandleSet regions);

   CompletableFuture<Void> deleteRegion(
         RegionHandle theRegion);

   CompletableFuture<ObjectInstanceHandle> registerObjectInstanceWithRegions(
         ObjectClassHandle objectClass, 
         AttributeSetRegionSetPairList attributesAndRegions);

   CompletableFuture<ObjectInstanceHandle> registerObjectInstanceWithNameAndRegions(
         ObjectClassHandle objectClass, 
         AttributeSetRegionSetPairList attributesAndRegions, 
         String objectInstanceName);

   CompletableFuture<Void> associateRegionsForUpdates(
         ObjectInstanceHandle objectInstance, 
         AttributeSetRegionSetPairList attributesAndRegions);

   CompletableFuture<Void> unassociateRegionsForUpdates(
         ObjectInstanceHandle objectInstance, 
         AttributeSetRegionSetPairList attributesAndRegions);

   CompletableFuture<Void> subscribeObjectClassAttributesWithRegions(
         ObjectClassHandle objectClass, 
         AttributeSetRegionSetPairList attributesAndRegions, 
         boolean active);

   CompletableFuture<Void> subscribeObjectClassAttributesWithRegionsAndRate(
         ObjectClassHandle objectClass, 
         AttributeSetRegionSetPairList attributesAndRegions, 
         boolean active, 
         String updateRateDesignator);

   CompletableFuture<Void> unsubscribeObjectClassAttributesWithRegions(
         ObjectClassHandle objectClass, 
         AttributeSetRegionSetPairList attributesAndRegions);

   CompletableFuture<Void> subscribeInteractionClassWithRegions(
         InteractionClassHandle interactionClass, 
         boolean active, 
         RegionHandleSet regions);

   CompletableFuture<Void> unsubscribeInteractionClassWithRegions(
         InteractionClassHandle interactionClass, 
         RegionHandleSet regions);

   CompletableFuture<Void> sendInteractionWithRegions(
         InteractionClassHandle interactionClass, 
         ParameterHandleValueMap parameterValues, 
         RegionHandleSet regions, 
         byte[] userSuppliedTag);

   CompletableFuture<MessageRetractionReturn> sendInteractionWithRegionsAndTime(
         InteractionClassHandle interactionClass, 
         ParameterHandleValueMap parameterValues, 
         RegionHandleSet regions, 
         byte[] userSuppliedTag, 
         LogicalTime<?, ?> time);

   CompletableFuture<Void> requestAttributeValueUpdateWithRegions(
         ObjectClassHandle objectClass, 
         AttributeSetRegionSetPairList attributesAndRegions, 
         byte[] userSuppliedTag);

   CompletableFuture<ResignAction> getAutomaticResignDirective();

   CompletableFuture<Void> setAutomaticResignDirective(
         ResignAction resignAction);

   CompletableFuture<FederateHandle> getFederateHandle(
         String federateName);

   CompletableFuture<String> getFederateName(
         FederateHandle federate);

   CompletableFuture<ObjectClassHandle> getObjectClassHandle(
         String objectClassName);

   CompletableFuture<String> getObjectClassName(
         ObjectClassHandle objectClass);

   CompletableFuture<ObjectClassHandle> getKnownObjectClassHandle(
         ObjectInstanceHandle objectInstance);

   CompletableFuture<ObjectInstanceHandle> getObjectInstanceHandle(
         String objectInstanceName);

   CompletableFuture<String> getObjectInstanceName(
         ObjectInstanceHandle objectInstance);

   CompletableFuture<AttributeHandle> getAttributeHandle(
         ObjectClassHandle objectClass, 
         String attributeName);

   CompletableFuture<String> getAttributeName(
         ObjectClassHandle objectClass, 
         AttributeHandle attribute);

   CompletableFuture<Double> getUpdateRateValue(
         String updateRateDesignator);

   CompletableFuture<Double> getUpdateRateValueForAttribute(
         ObjectInstanceHandle objectInstance, 
         AttributeHandle attribute);

   CompletableFuture<InteractionClassHandle> getInteractionClassHandle(
         String interactionClassName);

   CompletableFuture<String> getInteractionClassName(
         InteractionClassHandle interactionClass);

   CompletableFuture<ParameterHandle> getParameterHandle(
         InteractionClassHandle interactionClass, 
         String parameterName);

   CompletableFuture<String> getParameterName(
         InteractionClassHandle interactionClass, 
         ParameterHandle parameter);

   CompletableFuture<OrderType> getOrderType(
         String orderTypeName);

   CompletableFuture<String> getOrderName(
         OrderType orderType);

   CompletableFuture<TransportationTypeHandle> getTransportationTypeHandle(
         String transportationTypeName);

   CompletableFuture<String> getTransportationTypeName(
         TransportationTypeHandle transportationType);

   CompletableFuture<DimensionHandleSet> getAvailableDimensionsForClassAttribute(
         ObjectClassHandle objectClass, 
         AttributeHandle attribute);

   CompletableFuture<DimensionHandleSet> getAvailableDimensionsForInteractionClass(
         InteractionClassHandle interactionClass);

   CompletableFuture<DimensionHandle> getDimensionHandle(
         String dimensionName);

   CompletableFuture<String> getDimensionName(
         DimensionHandle dimension);

   CompletableFuture<Long> getDimensionUpperBound(
         DimensionHandle dimension);

   CompletableFuture<DimensionHandleSet> getDimensionHandleSet(
         RegionHandle region);

   CompletableFuture<RangeBounds> getRangeBounds(
         RegionHandle region, 
         DimensionHandle dimension);

   CompletableFuture<Void> setRangeBounds(
         RegionHandle region, 
         DimensionHandle dimension, 
         RangeBounds rangeBounds);

   CompletableFuture<Long> normalizeServiceGroup(
         ServiceGroup serviceGroup);

   CompletableFuture<Long> normalizeFederateHandle(
         FederateHandle federate);

   CompletableFuture<Long> normalizeObjectClassHandle(
         ObjectClassHandle objectClass);

   CompletableFuture<Long> normalizeInteractionClassHandle(
         InteractionClassHandle interactionClass);

   CompletableFuture<Long> normalizeObjectInstanceHandle(
         ObjectInstanceHandle objectInstance);

   CompletableFuture<Void> enableObjectClassRelevanceAdvisorySwitch();

   CompletableFuture<Void> disableObjectClassRelevanceAdvisorySwitch();

   CompletableFuture<Void> enableAttributeRelevanceAdvisorySwitch();

   CompletableFuture<Void> disableAttributeRelevanceAdvisorySwitch();

   CompletableFuture<Void> enableAttributeScopeAdvisorySwitch();

   CompletableFuture<Void> disableAttributeScopeAdvisorySwitch();

   CompletableFuture<Void> enableInteractionRelevanceAdvisorySwitch();

   CompletableFuture<Void> disableInteractionRelevanceAdvisorySwitch();

}
