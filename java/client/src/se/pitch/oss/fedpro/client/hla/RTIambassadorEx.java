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

import java.util.concurrent.CompletableFuture;

public interface RTIambassadorEx extends RTIambassador {
   AsyncRTIambassador async();

   interface AsyncRTIambassador {
      CompletableFuture<Void> listFederationExecutions();

      CompletableFuture<Void> listFederationExecutionMembers(java.lang.String federationExecutionName);

      CompletableFuture<Void> registerFederationSynchronizationPoint(java.lang.String synchronizationPointLabel,
                                                                            byte[] userSuppliedTag);

      CompletableFuture<Void> registerFederationSynchronizationPointWithSet(java.lang.String synchronizationPointLabel,
                                                                                   byte[] userSuppliedTag,
                                                                                   FederateHandleSet synchronizationSet);

      CompletableFuture<Void> synchronizationPointAchieved(java.lang.String synchronizationPointLabel,
                                                                  boolean successIndicator);

      CompletableFuture<Void> requestFederationSave(java.lang.String label);

      CompletableFuture<Void> requestFederationSaveWithTime(java.lang.String label,
                                                                   LogicalTime<?, ?> theTime);

      CompletableFuture<Void> federateSaveBegun();

      CompletableFuture<Void> federateSaveComplete();

      CompletableFuture<Void> federateSaveNotComplete();

      CompletableFuture<Void> abortFederationSave();

      CompletableFuture<Void> queryFederationSaveStatus();

      CompletableFuture<Void> requestFederationRestore(java.lang.String label);

      CompletableFuture<Void> federateRestoreComplete();

      CompletableFuture<Void> federateRestoreNotComplete();

      CompletableFuture<Void> abortFederationRestore();

      CompletableFuture<Void> queryFederationRestoreStatus();

      CompletableFuture<Void> publishObjectClassAttributes(ObjectClassHandle theClass,
                                                                  AttributeHandleSet attributeList);

      CompletableFuture<Void> unpublishObjectClass(ObjectClassHandle theClass);

      CompletableFuture<Void> unpublishObjectClassAttributes(ObjectClassHandle theClass,
                                                                    AttributeHandleSet attributeList);

      CompletableFuture<Void> publishInteractionClass(InteractionClassHandle theInteraction);

      CompletableFuture<Void> unpublishInteractionClass(InteractionClassHandle theInteraction);

      CompletableFuture<Void> subscribeObjectClassAttributes(ObjectClassHandle theClass,
                                                                    AttributeHandleSet attributeList);

      CompletableFuture<Void> subscribeObjectClassAttributesWithRate(ObjectClassHandle theClass,
                                                                            AttributeHandleSet attributeList,
                                                                            java.lang.String updateRateDesignator);

      CompletableFuture<Void> subscribeObjectClassAttributesPassively(ObjectClassHandle theClass,
                                                                             AttributeHandleSet attributeList);

      CompletableFuture<Void> subscribeObjectClassAttributesPassivelyWithRate(ObjectClassHandle theClass,
                                                                                     AttributeHandleSet attributeList,
                                                                                     java.lang.String updateRateDesignator);

      CompletableFuture<Void> unsubscribeObjectClass(ObjectClassHandle theClass);

      CompletableFuture<Void> unsubscribeObjectClassAttributes(ObjectClassHandle theClass,
                                                                      AttributeHandleSet attributeList);

      CompletableFuture<Void> subscribeInteractionClass(InteractionClassHandle theClass);

      CompletableFuture<Void> subscribeInteractionClassPassively(InteractionClassHandle theClass);

      CompletableFuture<Void> unsubscribeInteractionClass(InteractionClassHandle theClass);

      CompletableFuture<Void> reserveObjectInstanceName(java.lang.String theObjectName);

      CompletableFuture<Void> releaseObjectInstanceName(java.lang.String theObjectInstanceName);

      CompletableFuture<Void> reserveMultipleObjectInstanceName(java.util.Set<java.lang.String> theObjectNames);

      CompletableFuture<Void> releaseMultipleObjectInstanceName(java.util.Set<java.lang.String> theObjectNames);

      CompletableFuture<ObjectInstanceHandle> registerObjectInstance(ObjectClassHandle theClass);

      CompletableFuture<ObjectInstanceHandle> registerObjectInstanceWithName(ObjectClassHandle theClass,
                                                                                    java.lang.String theObjectName);

      CompletableFuture<Void> updateAttributeValues(ObjectInstanceHandle theObject,
                                                           AttributeHandleValueMap theAttributes,
                                                           byte[] userSuppliedTag);

      CompletableFuture<MessageRetractionReturn> updateAttributeValuesWithTime(ObjectInstanceHandle theObject,
                                                                                      AttributeHandleValueMap theAttributes,
                                                                                      byte[] userSuppliedTag,
                                                                                      LogicalTime<?, ?> theTime);

      CompletableFuture<Void> sendInteraction(InteractionClassHandle theInteraction,
                                                     ParameterHandleValueMap theParameters,
                                                     byte[] userSuppliedTag);

      CompletableFuture<MessageRetractionReturn> sendInteractionWithTime(InteractionClassHandle theInteraction,
                                                                                ParameterHandleValueMap theParameters,
                                                                                byte[] userSuppliedTag,
                                                                                LogicalTime<?, ?> theTime);

      CompletableFuture<Void> deleteObjectInstance(ObjectInstanceHandle objectHandle,
                                                          byte[] userSuppliedTag);

      CompletableFuture<MessageRetractionReturn> deleteObjectInstanceWithTime(ObjectInstanceHandle objectHandle,
                                                                                     byte[] userSuppliedTag,
                                                                                     LogicalTime<?, ?> theTime);

      CompletableFuture<Void> localDeleteObjectInstance(ObjectInstanceHandle objectHandle);

      CompletableFuture<Void> requestInstanceAttributeValueUpdate(ObjectInstanceHandle theObject,
                                                                         AttributeHandleSet theAttributes,
                                                                         byte[] userSuppliedTag);

      CompletableFuture<Void> requestClassAttributeValueUpdate(ObjectClassHandle theClass,
                                                                      AttributeHandleSet theAttributes,
                                                                      byte[] userSuppliedTag);

      CompletableFuture<Void> requestAttributeTransportationTypeChange(ObjectInstanceHandle theObject,
                                                                              AttributeHandleSet theAttributes,
                                                                              TransportationTypeHandle theType);

      CompletableFuture<Void> changeDefaultAttributeTransportationType(ObjectClassHandle theObjectClass,
                                                                              AttributeHandleSet theAttributes,
                                                                              TransportationTypeHandle theType);

      CompletableFuture<Void> queryAttributeTransportationType(ObjectInstanceHandle theObject,
                                                                      AttributeHandle theAttribute);

      CompletableFuture<Void> requestInteractionTransportationTypeChange(InteractionClassHandle theClass,
                                                                                TransportationTypeHandle theType);

      CompletableFuture<Void> queryInteractionTransportationType(FederateHandle theFederate,
                                                                        InteractionClassHandle theInteraction);

      CompletableFuture<Void> unconditionalAttributeOwnershipDivestiture(ObjectInstanceHandle theObject,
                                                                                AttributeHandleSet theAttributes,
                                                                                byte[] userSuppliedTag);

      CompletableFuture<Void> negotiatedAttributeOwnershipDivestiture(ObjectInstanceHandle theObject,
                                                                             AttributeHandleSet theAttributes,
                                                                             byte[] userSuppliedTag);

      CompletableFuture<Void> confirmDivestiture(ObjectInstanceHandle theObject,
                                                        AttributeHandleSet theAttributes,
                                                        byte[] userSuppliedTag);

      CompletableFuture<Void> attributeOwnershipAcquisition(ObjectInstanceHandle theObject,
                                                                   AttributeHandleSet desiredAttributes,
                                                                   byte[] userSuppliedTag);

      CompletableFuture<Void> attributeOwnershipAcquisitionIfAvailable(ObjectInstanceHandle theObject,
                                                                              AttributeHandleSet desiredAttributes,
                                                                              byte[] userSuppliedTag);

      CompletableFuture<Void> attributeOwnershipReleaseDenied(ObjectInstanceHandle theObject,
                                                                     AttributeHandleSet theAttributes,
                                                                     byte[] userSuppliedTag);

      CompletableFuture<AttributeHandleSet> attributeOwnershipDivestitureIfWanted(ObjectInstanceHandle theObject,
                                                                                         AttributeHandleSet theAttributes,
                                                                                         byte[] userSuppliedTag);

      CompletableFuture<Void> cancelNegotiatedAttributeOwnershipDivestiture(ObjectInstanceHandle theObject,
                                                                                   AttributeHandleSet theAttributes);

      CompletableFuture<Void> cancelAttributeOwnershipAcquisition(ObjectInstanceHandle theObject,
                                                                         AttributeHandleSet theAttributes);

      CompletableFuture<Void> queryAttributeOwnership(ObjectInstanceHandle theObject,
                                                             AttributeHandleSet theAttributes);

      CompletableFuture<Boolean> isAttributeOwnedByFederate(ObjectInstanceHandle theObject,
                                                                   AttributeHandle theAttribute);

      CompletableFuture<Void> enableTimeRegulation(LogicalTimeInterval<?> theLookahead);

      CompletableFuture<Void> disableTimeRegulation();

      CompletableFuture<Void> enableTimeConstrained();

      CompletableFuture<Void> disableTimeConstrained();

      CompletableFuture<Void> timeAdvanceRequest(LogicalTime<?, ?> theTime);

      CompletableFuture<Void> timeAdvanceRequestAvailable(LogicalTime<?, ?> theTime);

      CompletableFuture<Void> nextMessageRequest(LogicalTime<?, ?> theTime);

      CompletableFuture<Void> nextMessageRequestAvailable(LogicalTime<?, ?> theTime);

      CompletableFuture<Void> flushQueueRequest(LogicalTime<?, ?> theTime);

      CompletableFuture<Void> enableAsynchronousDelivery();

      CompletableFuture<Void> disableAsynchronousDelivery();

      CompletableFuture<TimeQueryReturn> queryGALT();

      CompletableFuture<LogicalTime> queryLogicalTime();

      CompletableFuture<TimeQueryReturn> queryLITS();

      CompletableFuture<Void> modifyLookahead(LogicalTimeInterval<?> theLookahead);

      CompletableFuture<LogicalTimeInterval> queryLookahead();

      CompletableFuture<Void> retract(MessageRetractionHandle theHandle);

      CompletableFuture<Void> changeAttributeOrderType(ObjectInstanceHandle theObject,
                                                              AttributeHandleSet theAttributes,
                                                              OrderType theType);

      CompletableFuture<Void> changeDefaultAttributeOrderType(ObjectClassHandle theObjectClass,
                                                                     AttributeHandleSet theAttributes,
                                                                     OrderType theType);

      CompletableFuture<Void> changeInteractionOrderType(InteractionClassHandle theClass,
                                                                OrderType theType);

      CompletableFuture<RegionHandle> createRegion(DimensionHandleSet dimensions);

      CompletableFuture<Void> commitRegionModifications(RegionHandleSet regions);

      CompletableFuture<Void> deleteRegion(RegionHandle theRegion);

      CompletableFuture<ObjectInstanceHandle> registerObjectInstanceWithRegions(ObjectClassHandle theClass,
                                                                                       AttributeSetRegionSetPairList attributesAndRegions);

      CompletableFuture<ObjectInstanceHandle> registerObjectInstanceWithNameAndRegions(ObjectClassHandle theClass,
                                                                                              AttributeSetRegionSetPairList attributesAndRegions,
                                                                                              java.lang.String theObject);

      CompletableFuture<Void> associateRegionsForUpdates(ObjectInstanceHandle theObject,
                                                                AttributeSetRegionSetPairList attributesAndRegions);

      CompletableFuture<Void> unassociateRegionsForUpdates(ObjectInstanceHandle theObject,
                                                                  AttributeSetRegionSetPairList attributesAndRegions);

      CompletableFuture<Void> subscribeObjectClassAttributesWithRegions(ObjectClassHandle theClass,
                                                                               AttributeSetRegionSetPairList attributesAndRegions,
                                                                               boolean active);

      CompletableFuture<Void> subscribeObjectClassAttributesWithRegionsAndRate(ObjectClassHandle theClass,
                                                                                      AttributeSetRegionSetPairList attributesAndRegions,
                                                                                      boolean active,
                                                                                      java.lang.String updateRateDesignator);

      CompletableFuture<Void> unsubscribeObjectClassAttributesWithRegions(ObjectClassHandle theClass,
                                                                                 AttributeSetRegionSetPairList attributesAndRegions);

      CompletableFuture<Void> subscribeInteractionClassWithRegions(InteractionClassHandle theClass,
                                                                          boolean active,
                                                                          RegionHandleSet regions);

      CompletableFuture<Void> unsubscribeInteractionClassWithRegions(InteractionClassHandle theClass,
                                                                            RegionHandleSet regions);

      CompletableFuture<Void> sendInteractionWithRegions(InteractionClassHandle theInteraction,
                                                                ParameterHandleValueMap theParameters,
                                                                RegionHandleSet regions,
                                                                byte[] userSuppliedTag);

      CompletableFuture<MessageRetractionReturn> sendInteractionWithRegionsAndTime(InteractionClassHandle theInteraction,
                                                                                          ParameterHandleValueMap theParameters,
                                                                                          RegionHandleSet regions,
                                                                                          byte[] userSuppliedTag,
                                                                                          LogicalTime<?, ?> theTime);

      CompletableFuture<Void> requestAttributeValueUpdateWithRegions(ObjectClassHandle theClass,
                                                                            AttributeSetRegionSetPairList attributesAndRegions,
                                                                            byte[] userSuppliedTag);

      CompletableFuture<ResignAction> getAutomaticResignDirective();

      CompletableFuture<Void> setAutomaticResignDirective(ResignAction resignAction);

      CompletableFuture<FederateHandle> getFederateHandle(java.lang.String theName);

      CompletableFuture<String> getFederateName(FederateHandle theHandle);

      CompletableFuture<ObjectClassHandle> getObjectClassHandle(java.lang.String theName);

      CompletableFuture<String> getObjectClassName(ObjectClassHandle theHandle);

      CompletableFuture<ObjectClassHandle> getKnownObjectClassHandle(ObjectInstanceHandle theObject);

      CompletableFuture<ObjectInstanceHandle> getObjectInstanceHandle(java.lang.String theName);

      CompletableFuture<String> getObjectInstanceName(ObjectInstanceHandle theHandle);

      CompletableFuture<AttributeHandle> getAttributeHandle(ObjectClassHandle whichClass,
                                                                   java.lang.String theName);

      CompletableFuture<String> getAttributeName(ObjectClassHandle whichClass,
                                                        AttributeHandle theHandle);

      CompletableFuture<Double> getUpdateRateValue(java.lang.String updateRateDesignator);

      CompletableFuture<Double> getUpdateRateValueForAttribute(ObjectInstanceHandle theObject,
                                                                      AttributeHandle theAttribute);

      CompletableFuture<InteractionClassHandle> getInteractionClassHandle(java.lang.String theName);

      CompletableFuture<String> getInteractionClassName(InteractionClassHandle theHandle);

      CompletableFuture<ParameterHandle> getParameterHandle(InteractionClassHandle whichClass,
                                                                   java.lang.String theName);

      CompletableFuture<String> getParameterName(InteractionClassHandle whichClass,
                                                        ParameterHandle theHandle);

      CompletableFuture<OrderType> getOrderType(java.lang.String theName);

      CompletableFuture<String> getOrderName(OrderType theType);

      CompletableFuture<TransportationTypeHandle> getTransportationTypeHandle(java.lang.String theName);

      CompletableFuture<String> getTransportationTypeName(TransportationTypeHandle theHandle);

      CompletableFuture<DimensionHandleSet> getAvailableDimensionsForClassAttribute(ObjectClassHandle whichClass,
                                                                                           AttributeHandle theHandle);

      CompletableFuture<DimensionHandleSet> getAvailableDimensionsForInteractionClass(InteractionClassHandle theHandle);

      CompletableFuture<DimensionHandle> getDimensionHandle(java.lang.String theName);

      CompletableFuture<String> getDimensionName(DimensionHandle theHandle);

      CompletableFuture<Long> getDimensionUpperBound(DimensionHandle theHandle);

      CompletableFuture<DimensionHandleSet> getDimensionHandleSet(RegionHandle region);

      CompletableFuture<RangeBounds> getRangeBounds(RegionHandle region,
                                                           DimensionHandle dimension);

      CompletableFuture<Void> setRangeBounds(RegionHandle region,
                                                    DimensionHandle dimension,
                                                    RangeBounds bounds);

      CompletableFuture<Long> normalizeServiceGroup(ServiceGroup group);

      CompletableFuture<Long> normalizeFederateHandle(FederateHandle federateHandle);

      CompletableFuture<Long> normalizeObjectClassHandle(ObjectClassHandle objectClassHandle);

      CompletableFuture<Long> normalizeInteractionClassHandle(InteractionClassHandle interactionClassHandle);

      CompletableFuture<Long> normalizeObjectInstanceHandle(ObjectInstanceHandle objectInstanceHandle);

      CompletableFuture<Void> enableObjectClassRelevanceAdvisorySwitch();

      CompletableFuture<Void> disableObjectClassRelevanceAdvisorySwitch();

      CompletableFuture<Void> enableAttributeRelevanceAdvisorySwitch();

      CompletableFuture<Void> disableAttributeRelevanceAdvisorySwitch();

      CompletableFuture<Void> enableAttributeScopeAdvisorySwitch();

      CompletableFuture<Void> disableAttributeScopeAdvisorySwitch();

      CompletableFuture<Void> enableInteractionRelevanceAdvisorySwitch();

      CompletableFuture<Void> disableInteractionRelevanceAdvisorySwitch();

   }
}
