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

public class AsyncHelper {
   static AsyncRTIambassador getAsyncRTIambassador(
         RTIambassadorClient rtiAmbassadorClient)
   {
      return new AsyncRTIambassador() {
         @Override
         public CompletableFuture<Void> listFederationExecutions()
         {
            return rtiAmbassadorClient.asyncListFederationExecutions();
         }

         @Override
         public CompletableFuture<Void> listFederationExecutionMembers(
               String federationName)
         {
            return rtiAmbassadorClient.asyncListFederationExecutionMembers(
                  federationName);
         }

         @Override
         public CompletableFuture<Void> registerFederationSynchronizationPoint(
               String synchronizationPointLabel, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncRegisterFederationSynchronizationPoint(
                  synchronizationPointLabel, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<Void> registerFederationSynchronizationPointWithSet(
               String synchronizationPointLabel, 
               byte[] userSuppliedTag, 
               FederateHandleSet synchronizationSet)
         {
            return rtiAmbassadorClient.asyncRegisterFederationSynchronizationPointWithSet(
                  synchronizationPointLabel, 
                  userSuppliedTag, 
                  synchronizationSet);
         }

         @Override
         public CompletableFuture<Void> synchronizationPointAchieved(
               String synchronizationPointLabel, 
               boolean successfully)
         {
            return rtiAmbassadorClient.asyncSynchronizationPointAchieved(
                  synchronizationPointLabel, 
                  successfully);
         }

         @Override
         public CompletableFuture<Void> requestFederationSave(
               String label)
         {
            return rtiAmbassadorClient.asyncRequestFederationSave(
                  label);
         }

         @Override
         public CompletableFuture<Void> requestFederationSaveWithTime(
               String label, 
               LogicalTime<?, ?> time)
         {
            return rtiAmbassadorClient.asyncRequestFederationSaveWithTime(
                  label, 
                  time);
         }

         @Override
         public CompletableFuture<Void> federateSaveBegun()
         {
            return rtiAmbassadorClient.asyncFederateSaveBegun();
         }

         @Override
         public CompletableFuture<Void> federateSaveComplete()
         {
            return rtiAmbassadorClient.asyncFederateSaveComplete();
         }

         @Override
         public CompletableFuture<Void> federateSaveNotComplete()
         {
            return rtiAmbassadorClient.asyncFederateSaveNotComplete();
         }

         @Override
         public CompletableFuture<Void> abortFederationSave()
         {
            return rtiAmbassadorClient.asyncAbortFederationSave();
         }

         @Override
         public CompletableFuture<Void> queryFederationSaveStatus()
         {
            return rtiAmbassadorClient.asyncQueryFederationSaveStatus();
         }

         @Override
         public CompletableFuture<Void> requestFederationRestore(
               String label)
         {
            return rtiAmbassadorClient.asyncRequestFederationRestore(
                  label);
         }

         @Override
         public CompletableFuture<Void> federateRestoreComplete()
         {
            return rtiAmbassadorClient.asyncFederateRestoreComplete();
         }

         @Override
         public CompletableFuture<Void> federateRestoreNotComplete()
         {
            return rtiAmbassadorClient.asyncFederateRestoreNotComplete();
         }

         @Override
         public CompletableFuture<Void> abortFederationRestore()
         {
            return rtiAmbassadorClient.asyncAbortFederationRestore();
         }

         @Override
         public CompletableFuture<Void> queryFederationRestoreStatus()
         {
            return rtiAmbassadorClient.asyncQueryFederationRestoreStatus();
         }

         @Override
         public CompletableFuture<Void> publishObjectClassAttributes(
               ObjectClassHandle objectClass, 
               AttributeHandleSet attributes)
         {
            return rtiAmbassadorClient.asyncPublishObjectClassAttributes(
                  objectClass, 
                  attributes);
         }

         @Override
         public CompletableFuture<Void> unpublishObjectClass(
               ObjectClassHandle objectClass)
         {
            return rtiAmbassadorClient.asyncUnpublishObjectClass(
                  objectClass);
         }

         @Override
         public CompletableFuture<Void> unpublishObjectClassAttributes(
               ObjectClassHandle objectClass, 
               AttributeHandleSet attributes)
         {
            return rtiAmbassadorClient.asyncUnpublishObjectClassAttributes(
                  objectClass, 
                  attributes);
         }

         @Override
         public CompletableFuture<Void> publishObjectClassDirectedInteractions(
               ObjectClassHandle objectClass, 
               InteractionClassHandleSet interactionClasses)
         {
            return rtiAmbassadorClient.asyncPublishObjectClassDirectedInteractions(
                  objectClass, 
                  interactionClasses);
         }

         @Override
         public CompletableFuture<Void> unpublishObjectClassDirectedInteractions(
               ObjectClassHandle objectClass)
         {
            return rtiAmbassadorClient.asyncUnpublishObjectClassDirectedInteractions(
                  objectClass);
         }

         @Override
         public CompletableFuture<Void> unpublishObjectClassDirectedInteractionsWithSet(
               ObjectClassHandle objectClass, 
               InteractionClassHandleSet interactionClasses)
         {
            return rtiAmbassadorClient.asyncUnpublishObjectClassDirectedInteractionsWithSet(
                  objectClass, 
                  interactionClasses);
         }

         @Override
         public CompletableFuture<Void> publishInteractionClass(
               InteractionClassHandle interactionClass)
         {
            return rtiAmbassadorClient.asyncPublishInteractionClass(
                  interactionClass);
         }

         @Override
         public CompletableFuture<Void> unpublishInteractionClass(
               InteractionClassHandle interactionClass)
         {
            return rtiAmbassadorClient.asyncUnpublishInteractionClass(
                  interactionClass);
         }

         @Override
         public CompletableFuture<Void> subscribeObjectClassAttributes(
               ObjectClassHandle objectClass, 
               AttributeHandleSet attributes)
         {
            return rtiAmbassadorClient.asyncSubscribeObjectClassAttributes(
                  objectClass, 
                  attributes);
         }

         @Override
         public CompletableFuture<Void> subscribeObjectClassAttributesWithRate(
               ObjectClassHandle objectClass, 
               AttributeHandleSet attributes, 
               String updateRateDesignator)
         {
            return rtiAmbassadorClient.asyncSubscribeObjectClassAttributesWithRate(
                  objectClass, 
                  attributes, 
                  updateRateDesignator);
         }

         @Override
         public CompletableFuture<Void> subscribeObjectClassAttributesPassively(
               ObjectClassHandle objectClass, 
               AttributeHandleSet attributes)
         {
            return rtiAmbassadorClient.asyncSubscribeObjectClassAttributesPassively(
                  objectClass, 
                  attributes);
         }

         @Override
         public CompletableFuture<Void> subscribeObjectClassAttributesPassivelyWithRate(
               ObjectClassHandle objectClass, 
               AttributeHandleSet attributes, 
               String updateRateDesignator)
         {
            return rtiAmbassadorClient.asyncSubscribeObjectClassAttributesPassivelyWithRate(
                  objectClass, 
                  attributes, 
                  updateRateDesignator);
         }

         @Override
         public CompletableFuture<Void> unsubscribeObjectClass(
               ObjectClassHandle objectClass)
         {
            return rtiAmbassadorClient.asyncUnsubscribeObjectClass(
                  objectClass);
         }

         @Override
         public CompletableFuture<Void> unsubscribeObjectClassAttributes(
               ObjectClassHandle objectClass, 
               AttributeHandleSet attributes)
         {
            return rtiAmbassadorClient.asyncUnsubscribeObjectClassAttributes(
                  objectClass, 
                  attributes);
         }

         @Override
         public CompletableFuture<Void> subscribeObjectClassDirectedInteractions(
               ObjectClassHandle objectClass, 
               InteractionClassHandleSet interactionClasses)
         {
            return rtiAmbassadorClient.asyncSubscribeObjectClassDirectedInteractions(
                  objectClass, 
                  interactionClasses);
         }

         @Override
         public CompletableFuture<Void> unsubscribeObjectClassDirectedInteractions(
               ObjectClassHandle objectClass)
         {
            return rtiAmbassadorClient.asyncUnsubscribeObjectClassDirectedInteractions(
                  objectClass);
         }

         @Override
         public CompletableFuture<Void> unsubscribeObjectClassDirectedInteractionsWithSet(
               ObjectClassHandle objectClass, 
               InteractionClassHandleSet interactionClasses)
         {
            return rtiAmbassadorClient.asyncUnsubscribeObjectClassDirectedInteractionsWithSet(
                  objectClass, 
                  interactionClasses);
         }

         @Override
         public CompletableFuture<Void> subscribeInteractionClass(
               InteractionClassHandle interactionClass)
         {
            return rtiAmbassadorClient.asyncSubscribeInteractionClass(
                  interactionClass);
         }

         @Override
         public CompletableFuture<Void> subscribeInteractionClassPassively(
               InteractionClassHandle interactionClass)
         {
            return rtiAmbassadorClient.asyncSubscribeInteractionClassPassively(
                  interactionClass);
         }

         @Override
         public CompletableFuture<Void> unsubscribeInteractionClass(
               InteractionClassHandle interactionClass)
         {
            return rtiAmbassadorClient.asyncUnsubscribeInteractionClass(
                  interactionClass);
         }

         @Override
         public CompletableFuture<Void> reserveObjectInstanceName(
               String objectInstanceName)
         {
            return rtiAmbassadorClient.asyncReserveObjectInstanceName(
                  objectInstanceName);
         }

         @Override
         public CompletableFuture<Void> releaseObjectInstanceName(
               String objectInstanceName)
         {
            return rtiAmbassadorClient.asyncReleaseObjectInstanceName(
                  objectInstanceName);
         }

         @Override
         public CompletableFuture<Void> reserveMultipleObjectInstanceName(
               Set<String> objectInstanceNames)
         {
            return rtiAmbassadorClient.asyncReserveMultipleObjectInstanceName(
                  objectInstanceNames);
         }

         @Override
         public CompletableFuture<Void> releaseMultipleObjectInstanceName(
               Set<String> objectInstanceNames)
         {
            return rtiAmbassadorClient.asyncReleaseMultipleObjectInstanceName(
                  objectInstanceNames);
         }

         @Override
         public CompletableFuture<ObjectInstanceHandle> registerObjectInstance(
               ObjectClassHandle objectClass)
         {
            return rtiAmbassadorClient.asyncRegisterObjectInstance(
                  objectClass);
         }

         @Override
         public CompletableFuture<ObjectInstanceHandle> registerObjectInstanceWithName(
               ObjectClassHandle objectClass, 
               String objectInstanceName)
         {
            return rtiAmbassadorClient.asyncRegisterObjectInstanceWithName(
                  objectClass, 
                  objectInstanceName);
         }

         @Override
         public CompletableFuture<Void> updateAttributeValues(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleValueMap attributeValues, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncUpdateAttributeValues(
                  objectInstance, 
                  attributeValues, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<MessageRetractionReturn> updateAttributeValuesWithTime(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleValueMap attributeValues, 
               byte[] userSuppliedTag, 
               LogicalTime<?, ?> time)
         {
            return rtiAmbassadorClient.asyncUpdateAttributeValuesWithTime(
                  objectInstance, 
                  attributeValues, 
                  userSuppliedTag, 
                  time);
         }

         @Override
         public CompletableFuture<Void> sendInteraction(
               InteractionClassHandle interactionClass, 
               ParameterHandleValueMap parameterValues, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncSendInteraction(
                  interactionClass, 
                  parameterValues, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<MessageRetractionReturn> sendInteractionWithTime(
               InteractionClassHandle interactionClass, 
               ParameterHandleValueMap parameterValues, 
               byte[] userSuppliedTag, 
               LogicalTime<?, ?> time)
         {
            return rtiAmbassadorClient.asyncSendInteractionWithTime(
                  interactionClass, 
                  parameterValues, 
                  userSuppliedTag, 
                  time);
         }

         @Override
         public CompletableFuture<Void> sendDirectedInteraction(
               InteractionClassHandle interactionClass, 
               ObjectInstanceHandle objectInstance, 
               ParameterHandleValueMap parameterValues, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncSendDirectedInteraction(
                  interactionClass, 
                  objectInstance, 
                  parameterValues, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<MessageRetractionReturn> sendDirectedInteractionWithTime(
               InteractionClassHandle interactionClass, 
               ObjectInstanceHandle objectInstance, 
               ParameterHandleValueMap parameterValues, 
               byte[] userSuppliedTag, 
               LogicalTime<?, ?> time)
         {
            return rtiAmbassadorClient.asyncSendDirectedInteractionWithTime(
                  interactionClass, 
                  objectInstance, 
                  parameterValues, 
                  userSuppliedTag, 
                  time);
         }

         @Override
         public CompletableFuture<Void> deleteObjectInstance(
               ObjectInstanceHandle objectInstance, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncDeleteObjectInstance(
                  objectInstance, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<MessageRetractionReturn> deleteObjectInstanceWithTime(
               ObjectInstanceHandle objectInstance, 
               byte[] userSuppliedTag, 
               LogicalTime<?, ?> time)
         {
            return rtiAmbassadorClient.asyncDeleteObjectInstanceWithTime(
                  objectInstance, 
                  userSuppliedTag, 
                  time);
         }

         @Override
         public CompletableFuture<Void> localDeleteObjectInstance(
               ObjectInstanceHandle objectInstance)
         {
            return rtiAmbassadorClient.asyncLocalDeleteObjectInstance(
                  objectInstance);
         }

         @Override
         public CompletableFuture<Void> requestInstanceAttributeValueUpdate(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleSet attributes, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncRequestInstanceAttributeValueUpdate(
                  objectInstance, 
                  attributes, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<Void> requestClassAttributeValueUpdate(
               ObjectClassHandle objectClass, 
               AttributeHandleSet attributes, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncRequestClassAttributeValueUpdate(
                  objectClass, 
                  attributes, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<Void> requestAttributeTransportationTypeChange(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleSet attributes, 
               TransportationTypeHandle transportationType)
         {
            return rtiAmbassadorClient.asyncRequestAttributeTransportationTypeChange(
                  objectInstance, 
                  attributes, 
                  transportationType);
         }

         @Override
         public CompletableFuture<Void> changeDefaultAttributeTransportationType(
               ObjectClassHandle theObjectClass, 
               AttributeHandleSet attributes, 
               TransportationTypeHandle transportationType)
         {
            return rtiAmbassadorClient.asyncChangeDefaultAttributeTransportationType(
                  theObjectClass, 
                  attributes, 
                  transportationType);
         }

         @Override
         public CompletableFuture<Void> queryAttributeTransportationType(
               ObjectInstanceHandle objectInstance, 
               AttributeHandle attribute)
         {
            return rtiAmbassadorClient.asyncQueryAttributeTransportationType(
                  objectInstance, 
                  attribute);
         }

         @Override
         public CompletableFuture<Void> requestInteractionTransportationTypeChange(
               InteractionClassHandle interactionClass, 
               TransportationTypeHandle transportationType)
         {
            return rtiAmbassadorClient.asyncRequestInteractionTransportationTypeChange(
                  interactionClass, 
                  transportationType);
         }

         @Override
         public CompletableFuture<Void> queryInteractionTransportationType(
               FederateHandle federate, 
               InteractionClassHandle interactionClass)
         {
            return rtiAmbassadorClient.asyncQueryInteractionTransportationType(
                  federate, 
                  interactionClass);
         }

         @Override
         public CompletableFuture<Void> unconditionalAttributeOwnershipDivestiture(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleSet attributes, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncUnconditionalAttributeOwnershipDivestiture(
                  objectInstance, 
                  attributes, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<Void> negotiatedAttributeOwnershipDivestiture(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleSet attributes, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncNegotiatedAttributeOwnershipDivestiture(
                  objectInstance, 
                  attributes, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<Void> confirmDivestiture(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleSet attributes, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncConfirmDivestiture(
                  objectInstance, 
                  attributes, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<Void> attributeOwnershipAcquisition(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleSet desiredAttributes, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncAttributeOwnershipAcquisition(
                  objectInstance, 
                  desiredAttributes, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<Void> attributeOwnershipAcquisitionIfAvailable(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleSet desiredAttributes, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncAttributeOwnershipAcquisitionIfAvailable(
                  objectInstance, 
                  desiredAttributes, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<Void> attributeOwnershipReleaseDenied(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleSet attributes, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncAttributeOwnershipReleaseDenied(
                  objectInstance, 
                  attributes, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<AttributeHandleSet> attributeOwnershipDivestitureIfWanted(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleSet attributes, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncAttributeOwnershipDivestitureIfWanted(
                  objectInstance, 
                  attributes, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<Void> cancelNegotiatedAttributeOwnershipDivestiture(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleSet attributes)
         {
            return rtiAmbassadorClient.asyncCancelNegotiatedAttributeOwnershipDivestiture(
                  objectInstance, 
                  attributes);
         }

         @Override
         public CompletableFuture<Void> cancelAttributeOwnershipAcquisition(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleSet attributes)
         {
            return rtiAmbassadorClient.asyncCancelAttributeOwnershipAcquisition(
                  objectInstance, 
                  attributes);
         }

         @Override
         public CompletableFuture<Void> queryAttributeOwnership(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleSet attributes)
         {
            return rtiAmbassadorClient.asyncQueryAttributeOwnership(
                  objectInstance, 
                  attributes);
         }

         @Override
         public CompletableFuture<Boolean> isAttributeOwnedByFederate(
               ObjectInstanceHandle objectInstance, 
               AttributeHandle attribute)
         {
            return rtiAmbassadorClient.asyncIsAttributeOwnedByFederate(
                  objectInstance, 
                  attribute);
         }

         @Override
         public CompletableFuture<Void> enableTimeRegulation(
               LogicalTimeInterval<?> lookahead)
         {
            return rtiAmbassadorClient.asyncEnableTimeRegulation(
                  lookahead);
         }

         @Override
         public CompletableFuture<Void> disableTimeRegulation()
         {
            return rtiAmbassadorClient.asyncDisableTimeRegulation();
         }

         @Override
         public CompletableFuture<Void> enableTimeConstrained()
         {
            return rtiAmbassadorClient.asyncEnableTimeConstrained();
         }

         @Override
         public CompletableFuture<Void> disableTimeConstrained()
         {
            return rtiAmbassadorClient.asyncDisableTimeConstrained();
         }

         @Override
         public CompletableFuture<Void> timeAdvanceRequest(
               LogicalTime<?, ?> time)
         {
            return rtiAmbassadorClient.asyncTimeAdvanceRequest(
                  time);
         }

         @Override
         public CompletableFuture<Void> timeAdvanceRequestAvailable(
               LogicalTime<?, ?> time)
         {
            return rtiAmbassadorClient.asyncTimeAdvanceRequestAvailable(
                  time);
         }

         @Override
         public CompletableFuture<Void> nextMessageRequest(
               LogicalTime<?, ?> time)
         {
            return rtiAmbassadorClient.asyncNextMessageRequest(
                  time);
         }

         @Override
         public CompletableFuture<Void> nextMessageRequestAvailable(
               LogicalTime<?, ?> time)
         {
            return rtiAmbassadorClient.asyncNextMessageRequestAvailable(
                  time);
         }

         @Override
         public CompletableFuture<Void> flushQueueRequest(
               LogicalTime<?, ?> time)
         {
            return rtiAmbassadorClient.asyncFlushQueueRequest(
                  time);
         }

         @Override
         public CompletableFuture<Void> enableAsynchronousDelivery()
         {
            return rtiAmbassadorClient.asyncEnableAsynchronousDelivery();
         }

         @Override
         public CompletableFuture<Void> disableAsynchronousDelivery()
         {
            return rtiAmbassadorClient.asyncDisableAsynchronousDelivery();
         }

         @Override
         public CompletableFuture<TimeQueryReturn> queryGALT()
         {
            return rtiAmbassadorClient.asyncQueryGALT();
         }

         @Override
         public CompletableFuture<LogicalTime> queryLogicalTime()
         {
            return rtiAmbassadorClient.asyncQueryLogicalTime();
         }

         @Override
         public CompletableFuture<TimeQueryReturn> queryLITS()
         {
            return rtiAmbassadorClient.asyncQueryLITS();
         }

         @Override
         public CompletableFuture<Void> modifyLookahead(
               LogicalTimeInterval<?> lookahead)
         {
            return rtiAmbassadorClient.asyncModifyLookahead(
                  lookahead);
         }

         @Override
         public CompletableFuture<LogicalTimeInterval> queryLookahead()
         {
            return rtiAmbassadorClient.asyncQueryLookahead();
         }

         @Override
         public CompletableFuture<Void> retract(
               MessageRetractionHandle retraction)
         {
            return rtiAmbassadorClient.asyncRetract(
                  retraction);
         }

         @Override
         public CompletableFuture<Void> changeAttributeOrderType(
               ObjectInstanceHandle objectInstance, 
               AttributeHandleSet attributes, 
               OrderType orderType)
         {
            return rtiAmbassadorClient.asyncChangeAttributeOrderType(
                  objectInstance, 
                  attributes, 
                  orderType);
         }

         @Override
         public CompletableFuture<Void> changeDefaultAttributeOrderType(
               ObjectClassHandle theObjectClass, 
               AttributeHandleSet attributes, 
               OrderType orderType)
         {
            return rtiAmbassadorClient.asyncChangeDefaultAttributeOrderType(
                  theObjectClass, 
                  attributes, 
                  orderType);
         }

         @Override
         public CompletableFuture<Void> changeInteractionOrderType(
               InteractionClassHandle interactionClass, 
               OrderType orderType)
         {
            return rtiAmbassadorClient.asyncChangeInteractionOrderType(
                  interactionClass, 
                  orderType);
         }

         @Override
         public CompletableFuture<RegionHandle> createRegion(
               DimensionHandleSet dimensions)
         {
            return rtiAmbassadorClient.asyncCreateRegion(
                  dimensions);
         }

         @Override
         public CompletableFuture<Void> commitRegionModifications(
               RegionHandleSet regions)
         {
            return rtiAmbassadorClient.asyncCommitRegionModifications(
                  regions);
         }

         @Override
         public CompletableFuture<Void> deleteRegion(
               RegionHandle theRegion)
         {
            return rtiAmbassadorClient.asyncDeleteRegion(
                  theRegion);
         }

         @Override
         public CompletableFuture<ObjectInstanceHandle> registerObjectInstanceWithRegions(
               ObjectClassHandle objectClass, 
               AttributeSetRegionSetPairList attributesAndRegions)
         {
            return rtiAmbassadorClient.asyncRegisterObjectInstanceWithRegions(
                  objectClass, 
                  attributesAndRegions);
         }

         @Override
         public CompletableFuture<ObjectInstanceHandle> registerObjectInstanceWithNameAndRegions(
               ObjectClassHandle objectClass, 
               AttributeSetRegionSetPairList attributesAndRegions, 
               String objectInstanceName)
         {
            return rtiAmbassadorClient.asyncRegisterObjectInstanceWithNameAndRegions(
                  objectClass, 
                  attributesAndRegions, 
                  objectInstanceName);
         }

         @Override
         public CompletableFuture<Void> associateRegionsForUpdates(
               ObjectInstanceHandle objectInstance, 
               AttributeSetRegionSetPairList attributesAndRegions)
         {
            return rtiAmbassadorClient.asyncAssociateRegionsForUpdates(
                  objectInstance, 
                  attributesAndRegions);
         }

         @Override
         public CompletableFuture<Void> unassociateRegionsForUpdates(
               ObjectInstanceHandle objectInstance, 
               AttributeSetRegionSetPairList attributesAndRegions)
         {
            return rtiAmbassadorClient.asyncUnassociateRegionsForUpdates(
                  objectInstance, 
                  attributesAndRegions);
         }

         @Override
         public CompletableFuture<Void> subscribeObjectClassAttributesWithRegions(
               ObjectClassHandle objectClass, 
               AttributeSetRegionSetPairList attributesAndRegions, 
               boolean active)
         {
            return rtiAmbassadorClient.asyncSubscribeObjectClassAttributesWithRegions(
                  objectClass, 
                  attributesAndRegions, 
                  active);
         }

         @Override
         public CompletableFuture<Void> subscribeObjectClassAttributesWithRegionsAndRate(
               ObjectClassHandle objectClass, 
               AttributeSetRegionSetPairList attributesAndRegions, 
               boolean active, 
               String updateRateDesignator)
         {
            return rtiAmbassadorClient.asyncSubscribeObjectClassAttributesWithRegionsAndRate(
                  objectClass, 
                  attributesAndRegions, 
                  active, 
                  updateRateDesignator);
         }

         @Override
         public CompletableFuture<Void> unsubscribeObjectClassAttributesWithRegions(
               ObjectClassHandle objectClass, 
               AttributeSetRegionSetPairList attributesAndRegions)
         {
            return rtiAmbassadorClient.asyncUnsubscribeObjectClassAttributesWithRegions(
                  objectClass, 
                  attributesAndRegions);
         }

         @Override
         public CompletableFuture<Void> subscribeInteractionClassWithRegions(
               InteractionClassHandle interactionClass, 
               boolean active, 
               RegionHandleSet regions)
         {
            return rtiAmbassadorClient.asyncSubscribeInteractionClassWithRegions(
                  interactionClass, 
                  active, 
                  regions);
         }

         @Override
         public CompletableFuture<Void> unsubscribeInteractionClassWithRegions(
               InteractionClassHandle interactionClass, 
               RegionHandleSet regions)
         {
            return rtiAmbassadorClient.asyncUnsubscribeInteractionClassWithRegions(
                  interactionClass, 
                  regions);
         }

         @Override
         public CompletableFuture<Void> sendInteractionWithRegions(
               InteractionClassHandle interactionClass, 
               ParameterHandleValueMap parameterValues, 
               RegionHandleSet regions, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncSendInteractionWithRegions(
                  interactionClass, 
                  parameterValues, 
                  regions, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<MessageRetractionReturn> sendInteractionWithRegionsAndTime(
               InteractionClassHandle interactionClass, 
               ParameterHandleValueMap parameterValues, 
               RegionHandleSet regions, 
               byte[] userSuppliedTag, 
               LogicalTime<?, ?> time)
         {
            return rtiAmbassadorClient.asyncSendInteractionWithRegionsAndTime(
                  interactionClass, 
                  parameterValues, 
                  regions, 
                  userSuppliedTag, 
                  time);
         }

         @Override
         public CompletableFuture<Void> requestAttributeValueUpdateWithRegions(
               ObjectClassHandle objectClass, 
               AttributeSetRegionSetPairList attributesAndRegions, 
               byte[] userSuppliedTag)
         {
            return rtiAmbassadorClient.asyncRequestAttributeValueUpdateWithRegions(
                  objectClass, 
                  attributesAndRegions, 
                  userSuppliedTag);
         }

         @Override
         public CompletableFuture<ResignAction> getAutomaticResignDirective()
         {
            return rtiAmbassadorClient.asyncGetAutomaticResignDirective();
         }

         @Override
         public CompletableFuture<Void> setAutomaticResignDirective(
               ResignAction resignAction)
         {
            return rtiAmbassadorClient.asyncSetAutomaticResignDirective(
                  resignAction);
         }

         @Override
         public CompletableFuture<FederateHandle> getFederateHandle(
               String federateName)
         {
            return rtiAmbassadorClient.asyncGetFederateHandle(
                  federateName);
         }

         @Override
         public CompletableFuture<String> getFederateName(
               FederateHandle federate)
         {
            return rtiAmbassadorClient.asyncGetFederateName(
                  federate);
         }

         @Override
         public CompletableFuture<ObjectClassHandle> getObjectClassHandle(
               String objectClassName)
         {
            return rtiAmbassadorClient.asyncGetObjectClassHandle(
                  objectClassName);
         }

         @Override
         public CompletableFuture<String> getObjectClassName(
               ObjectClassHandle objectClass)
         {
            return rtiAmbassadorClient.asyncGetObjectClassName(
                  objectClass);
         }

         @Override
         public CompletableFuture<ObjectClassHandle> getKnownObjectClassHandle(
               ObjectInstanceHandle objectInstance)
         {
            return rtiAmbassadorClient.asyncGetKnownObjectClassHandle(
                  objectInstance);
         }

         @Override
         public CompletableFuture<ObjectInstanceHandle> getObjectInstanceHandle(
               String objectInstanceName)
         {
            return rtiAmbassadorClient.asyncGetObjectInstanceHandle(
                  objectInstanceName);
         }

         @Override
         public CompletableFuture<String> getObjectInstanceName(
               ObjectInstanceHandle objectInstance)
         {
            return rtiAmbassadorClient.asyncGetObjectInstanceName(
                  objectInstance);
         }

         @Override
         public CompletableFuture<AttributeHandle> getAttributeHandle(
               ObjectClassHandle objectClass, 
               String attributeName)
         {
            return rtiAmbassadorClient.asyncGetAttributeHandle(
                  objectClass, 
                  attributeName);
         }

         @Override
         public CompletableFuture<String> getAttributeName(
               ObjectClassHandle objectClass, 
               AttributeHandle attribute)
         {
            return rtiAmbassadorClient.asyncGetAttributeName(
                  objectClass, 
                  attribute);
         }

         @Override
         public CompletableFuture<Double> getUpdateRateValue(
               String updateRateDesignator)
         {
            return rtiAmbassadorClient.asyncGetUpdateRateValue(
                  updateRateDesignator);
         }

         @Override
         public CompletableFuture<Double> getUpdateRateValueForAttribute(
               ObjectInstanceHandle objectInstance, 
               AttributeHandle attribute)
         {
            return rtiAmbassadorClient.asyncGetUpdateRateValueForAttribute(
                  objectInstance, 
                  attribute);
         }

         @Override
         public CompletableFuture<InteractionClassHandle> getInteractionClassHandle(
               String interactionClassName)
         {
            return rtiAmbassadorClient.asyncGetInteractionClassHandle(
                  interactionClassName);
         }

         @Override
         public CompletableFuture<String> getInteractionClassName(
               InteractionClassHandle interactionClass)
         {
            return rtiAmbassadorClient.asyncGetInteractionClassName(
                  interactionClass);
         }

         @Override
         public CompletableFuture<ParameterHandle> getParameterHandle(
               InteractionClassHandle interactionClass, 
               String parameterName)
         {
            return rtiAmbassadorClient.asyncGetParameterHandle(
                  interactionClass, 
                  parameterName);
         }

         @Override
         public CompletableFuture<String> getParameterName(
               InteractionClassHandle interactionClass, 
               ParameterHandle parameter)
         {
            return rtiAmbassadorClient.asyncGetParameterName(
                  interactionClass, 
                  parameter);
         }

         @Override
         public CompletableFuture<OrderType> getOrderType(
               String orderTypeName)
         {
            return rtiAmbassadorClient.asyncGetOrderType(
                  orderTypeName);
         }

         @Override
         public CompletableFuture<String> getOrderName(
               OrderType orderType)
         {
            return rtiAmbassadorClient.asyncGetOrderName(
                  orderType);
         }

         @Override
         public CompletableFuture<TransportationTypeHandle> getTransportationTypeHandle(
               String transportationTypeName)
         {
            return rtiAmbassadorClient.asyncGetTransportationTypeHandle(
                  transportationTypeName);
         }

         @Override
         public CompletableFuture<String> getTransportationTypeName(
               TransportationTypeHandle transportationType)
         {
            return rtiAmbassadorClient.asyncGetTransportationTypeName(
                  transportationType);
         }

         @Override
         public CompletableFuture<DimensionHandleSet> getAvailableDimensionsForClassAttribute(
               ObjectClassHandle objectClass, 
               AttributeHandle attribute)
         {
            return rtiAmbassadorClient.asyncGetAvailableDimensionsForClassAttribute(
                  objectClass, 
                  attribute);
         }

         @Override
         public CompletableFuture<DimensionHandleSet> getAvailableDimensionsForInteractionClass(
               InteractionClassHandle interactionClass)
         {
            return rtiAmbassadorClient.asyncGetAvailableDimensionsForInteractionClass(
                  interactionClass);
         }

         @Override
         public CompletableFuture<DimensionHandle> getDimensionHandle(
               String dimensionName)
         {
            return rtiAmbassadorClient.asyncGetDimensionHandle(
                  dimensionName);
         }

         @Override
         public CompletableFuture<String> getDimensionName(
               DimensionHandle dimension)
         {
            return rtiAmbassadorClient.asyncGetDimensionName(
                  dimension);
         }

         @Override
         public CompletableFuture<Long> getDimensionUpperBound(
               DimensionHandle dimension)
         {
            return rtiAmbassadorClient.asyncGetDimensionUpperBound(
                  dimension);
         }

         @Override
         public CompletableFuture<DimensionHandleSet> getDimensionHandleSet(
               RegionHandle region)
         {
            return rtiAmbassadorClient.asyncGetDimensionHandleSet(
                  region);
         }

         @Override
         public CompletableFuture<RangeBounds> getRangeBounds(
               RegionHandle region, 
               DimensionHandle dimension)
         {
            return rtiAmbassadorClient.asyncGetRangeBounds(
                  region, 
                  dimension);
         }

         @Override
         public CompletableFuture<Void> setRangeBounds(
               RegionHandle region, 
               DimensionHandle dimension, 
               RangeBounds rangeBounds)
         {
            return rtiAmbassadorClient.asyncSetRangeBounds(
                  region, 
                  dimension, 
                  rangeBounds);
         }

         @Override
         public CompletableFuture<Long> normalizeServiceGroup(
               ServiceGroup serviceGroup)
         {
            return rtiAmbassadorClient.asyncNormalizeServiceGroup(
                  serviceGroup);
         }

         @Override
         public CompletableFuture<Long> normalizeFederateHandle(
               FederateHandle federate)
         {
            return rtiAmbassadorClient.asyncNormalizeFederateHandle(
                  federate);
         }

         @Override
         public CompletableFuture<Long> normalizeObjectClassHandle(
               ObjectClassHandle objectClass)
         {
            return rtiAmbassadorClient.asyncNormalizeObjectClassHandle(
                  objectClass);
         }

         @Override
         public CompletableFuture<Long> normalizeInteractionClassHandle(
               InteractionClassHandle interactionClass)
         {
            return rtiAmbassadorClient.asyncNormalizeInteractionClassHandle(
                  interactionClass);
         }

         @Override
         public CompletableFuture<Long> normalizeObjectInstanceHandle(
               ObjectInstanceHandle objectInstance)
         {
            return rtiAmbassadorClient.asyncNormalizeObjectInstanceHandle(
                  objectInstance);
         }

         @Override
         public CompletableFuture<Void> enableObjectClassRelevanceAdvisorySwitch()
         {
            return rtiAmbassadorClient.asyncEnableObjectClassRelevanceAdvisorySwitch();
         }

         @Override
         public CompletableFuture<Void> disableObjectClassRelevanceAdvisorySwitch()
         {
            return rtiAmbassadorClient.asyncDisableObjectClassRelevanceAdvisorySwitch();
         }

         @Override
         public CompletableFuture<Void> enableAttributeRelevanceAdvisorySwitch()
         {
            return rtiAmbassadorClient.asyncEnableAttributeRelevanceAdvisorySwitch();
         }

         @Override
         public CompletableFuture<Void> disableAttributeRelevanceAdvisorySwitch()
         {
            return rtiAmbassadorClient.asyncDisableAttributeRelevanceAdvisorySwitch();
         }

         @Override
         public CompletableFuture<Void> enableAttributeScopeAdvisorySwitch()
         {
            return rtiAmbassadorClient.asyncEnableAttributeScopeAdvisorySwitch();
         }

         @Override
         public CompletableFuture<Void> disableAttributeScopeAdvisorySwitch()
         {
            return rtiAmbassadorClient.asyncDisableAttributeScopeAdvisorySwitch();
         }

         @Override
         public CompletableFuture<Void> enableInteractionRelevanceAdvisorySwitch()
         {
            return rtiAmbassadorClient.asyncEnableInteractionRelevanceAdvisorySwitch();
         }

         @Override
         public CompletableFuture<Void> disableInteractionRelevanceAdvisorySwitch()
         {
            return rtiAmbassadorClient.asyncDisableInteractionRelevanceAdvisorySwitch();
         }

      };
   }
}
