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
import hla.rti1516_202X.auth.Credentials;
import hla.rti1516_202X.exceptions.*;
import hla.rti1516_202X.time.HLAfloat64Interval;
import hla.rti1516_202X.time.HLAfloat64Time;
import hla.rti1516_202X.time.HLAinteger64Interval;
import hla.rti1516_202X.time.HLAinteger64Time;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class RTIambassadorClientAdapter implements RTIambassadorEx {

   private final RTIambassadorClient _rtiAmbassadorClient;
   private LogicalTimeFactory<?, ?> _timeFactory;
   private final ClientConverter _clientConverter;

   private final AsyncRTIambassador _asyncRTIambassador = new AsyncRTIambassador() {
      @Override
      public CompletableFuture<Void> listFederationExecutions()
      {
         return _rtiAmbassadorClient.asyncListFederationExecutions();
      }

      @Override
      public CompletableFuture<Void> listFederationExecutionMembers(java.lang.String federationExecutionName)
      {
         return _rtiAmbassadorClient.asyncListFederationExecutionMembers(federationExecutionName);
      }

      @Override
      public CompletableFuture<Void> registerFederationSynchronizationPoint(java.lang.String synchronizationPointLabel,
                                                                            byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncRegisterFederationSynchronizationPoint(synchronizationPointLabel,
            userSuppliedTag);
      }

      @Override
      public CompletableFuture<Void> registerFederationSynchronizationPointWithSet(java.lang.String synchronizationPointLabel,
                                                                                   byte[] userSuppliedTag,
                                                                                   FederateHandleSet synchronizationSet)
      {
         return _rtiAmbassadorClient.asyncRegisterFederationSynchronizationPointWithSet(synchronizationPointLabel,
            userSuppliedTag,
            synchronizationSet);
      }

      @Override
      public CompletableFuture<Void> synchronizationPointAchieved(java.lang.String synchronizationPointLabel,
                                                                  boolean successIndicator)
      {
         return _rtiAmbassadorClient.asyncSynchronizationPointAchieved(synchronizationPointLabel, successIndicator);
      }

      @Override
      public CompletableFuture<Void> requestFederationSave(java.lang.String label)
      {
         return _rtiAmbassadorClient.asyncRequestFederationSave(label);
      }

      @Override
      public CompletableFuture<Void> requestFederationSaveWithTime(java.lang.String label,
                                                                   LogicalTime<?, ?> theTime)
      {
         return _rtiAmbassadorClient.asyncRequestFederationSaveWithTime(label, theTime);
      }

      @Override
      public CompletableFuture<Void> federateSaveBegun()
      {
         return _rtiAmbassadorClient.asyncFederateSaveBegun();
      }

      @Override
      public CompletableFuture<Void> federateSaveComplete()
      {
         return _rtiAmbassadorClient.asyncFederateSaveComplete();
      }

      @Override
      public CompletableFuture<Void> federateSaveNotComplete()
      {
         return _rtiAmbassadorClient.asyncFederateSaveNotComplete();
      }

      @Override
      public CompletableFuture<Void> abortFederationSave()
      {
         return _rtiAmbassadorClient.asyncAbortFederationSave();
      }

      @Override
      public CompletableFuture<Void> queryFederationSaveStatus()
      {
         return _rtiAmbassadorClient.asyncQueryFederationSaveStatus();
      }

      @Override
      public CompletableFuture<Void> requestFederationRestore(java.lang.String label)
      {
         return _rtiAmbassadorClient.asyncRequestFederationRestore(label);
      }

      @Override
      public CompletableFuture<Void> federateRestoreComplete()
      {
         return _rtiAmbassadorClient.asyncFederateRestoreComplete();
      }

      @Override
      public CompletableFuture<Void> federateRestoreNotComplete()
      {
         return _rtiAmbassadorClient.asyncFederateRestoreNotComplete();
      }

      @Override
      public CompletableFuture<Void> abortFederationRestore()
      {
         return _rtiAmbassadorClient.asyncAbortFederationRestore();
      }

      @Override
      public CompletableFuture<Void> queryFederationRestoreStatus()
      {
         return _rtiAmbassadorClient.asyncQueryFederationRestoreStatus();
      }

      @Override
      public CompletableFuture<Void> publishObjectClassAttributes(ObjectClassHandle theClass,
                                                                  AttributeHandleSet attributeList)
      {
         return _rtiAmbassadorClient.asyncPublishObjectClassAttributes(theClass, attributeList);
      }

      @Override
      public CompletableFuture<Void> unpublishObjectClass(ObjectClassHandle theClass)
      {
         return _rtiAmbassadorClient.asyncUnpublishObjectClass(theClass);
      }

      @Override
      public CompletableFuture<Void> unpublishObjectClassAttributes(ObjectClassHandle theClass,
                                                                    AttributeHandleSet attributeList)
      {
         return _rtiAmbassadorClient.asyncUnpublishObjectClassAttributes(theClass, attributeList);
      }

      @Override
      public CompletableFuture<Void> publishInteractionClass(InteractionClassHandle theInteraction)
      {
         return _rtiAmbassadorClient.asyncPublishInteractionClass(theInteraction);
      }

      @Override
      public CompletableFuture<Void> unpublishInteractionClass(InteractionClassHandle theInteraction)
      {
         return _rtiAmbassadorClient.asyncUnpublishInteractionClass(theInteraction);
      }

      @Override
      public CompletableFuture<Void> subscribeObjectClassAttributes(ObjectClassHandle theClass,
                                                                    AttributeHandleSet attributeList)
      {
         return _rtiAmbassadorClient.asyncSubscribeObjectClassAttributes(theClass, attributeList);
      }

      @Override
      public CompletableFuture<Void> subscribeObjectClassAttributesWithRate(ObjectClassHandle theClass,
                                                                            AttributeHandleSet attributeList,
                                                                            java.lang.String updateRateDesignator)
      {
         return _rtiAmbassadorClient.asyncSubscribeObjectClassAttributesWithRate(theClass,
            attributeList,
            updateRateDesignator);
      }

      @Override
      public CompletableFuture<Void> subscribeObjectClassAttributesPassively(ObjectClassHandle theClass,
                                                                             AttributeHandleSet attributeList)
      {
         return _rtiAmbassadorClient.asyncSubscribeObjectClassAttributesPassively(theClass, attributeList);
      }

      @Override
      public CompletableFuture<Void> subscribeObjectClassAttributesPassivelyWithRate(ObjectClassHandle theClass,
                                                                                     AttributeHandleSet attributeList,
                                                                                     java.lang.String updateRateDesignator)
      {
         return _rtiAmbassadorClient.asyncSubscribeObjectClassAttributesPassivelyWithRate(theClass,
            attributeList,
            updateRateDesignator);
      }

      @Override
      public CompletableFuture<Void> unsubscribeObjectClass(ObjectClassHandle theClass)
      {
         return _rtiAmbassadorClient.asyncUnsubscribeObjectClass(theClass);
      }

      @Override
      public CompletableFuture<Void> unsubscribeObjectClassAttributes(ObjectClassHandle theClass,
                                                                      AttributeHandleSet attributeList)
      {
         return _rtiAmbassadorClient.asyncUnsubscribeObjectClassAttributes(theClass, attributeList);
      }

      @Override
      public CompletableFuture<Void> subscribeInteractionClass(InteractionClassHandle theClass)
      {
         return _rtiAmbassadorClient.asyncSubscribeInteractionClass(theClass);
      }

      @Override
      public CompletableFuture<Void> subscribeInteractionClassPassively(InteractionClassHandle theClass)
      {
         return _rtiAmbassadorClient.asyncSubscribeInteractionClassPassively(theClass);
      }

      @Override
      public CompletableFuture<Void> unsubscribeInteractionClass(InteractionClassHandle theClass)
      {
         return _rtiAmbassadorClient.asyncUnsubscribeInteractionClass(theClass);
      }

      @Override
      public CompletableFuture<Void> reserveObjectInstanceName(java.lang.String theObjectName)
      {
         return _rtiAmbassadorClient.asyncReserveObjectInstanceName(theObjectName);
      }

      @Override
      public CompletableFuture<Void> releaseObjectInstanceName(java.lang.String theObjectInstanceName)
      {
         return _rtiAmbassadorClient.asyncReleaseObjectInstanceName(theObjectInstanceName);
      }

      @Override
      public CompletableFuture<Void> reserveMultipleObjectInstanceName(java.util.Set<java.lang.String> theObjectNames)
      {
         return _rtiAmbassadorClient.asyncReserveMultipleObjectInstanceName(theObjectNames);
      }

      @Override
      public CompletableFuture<Void> releaseMultipleObjectInstanceName(java.util.Set<java.lang.String> theObjectNames)
      {
         return _rtiAmbassadorClient.asyncReleaseMultipleObjectInstanceName(theObjectNames);
      }

      @Override
      public CompletableFuture<ObjectInstanceHandle> registerObjectInstance(ObjectClassHandle theClass)
      {
         return _rtiAmbassadorClient.asyncRegisterObjectInstance(theClass);
      }

      @Override
      public CompletableFuture<ObjectInstanceHandle> registerObjectInstanceWithName(ObjectClassHandle theClass,
                                                                                    java.lang.String theObjectName)
      {
         return _rtiAmbassadorClient.asyncRegisterObjectInstanceWithName(theClass, theObjectName);
      }

      @Override
      public CompletableFuture<Void> updateAttributeValues(ObjectInstanceHandle theObject,
                                                           AttributeHandleValueMap theAttributes,
                                                           byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncUpdateAttributeValues(theObject, theAttributes, userSuppliedTag);
      }

      @Override
      public CompletableFuture<MessageRetractionReturn> updateAttributeValuesWithTime(ObjectInstanceHandle theObject,
                                                                                      AttributeHandleValueMap theAttributes,
                                                                                      byte[] userSuppliedTag,
                                                                                      LogicalTime<?, ?> theTime)
      {
         return _rtiAmbassadorClient.asyncUpdateAttributeValuesWithTime(theObject,
            theAttributes,
            userSuppliedTag,
            theTime);
      }

      @Override
      public CompletableFuture<Void> sendInteraction(InteractionClassHandle theInteraction,
                                                     ParameterHandleValueMap theParameters,
                                                     byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncSendInteraction(theInteraction, theParameters, userSuppliedTag);
      }

      @Override
      public CompletableFuture<MessageRetractionReturn> sendInteractionWithTime(InteractionClassHandle theInteraction,
                                                                                ParameterHandleValueMap theParameters,
                                                                                byte[] userSuppliedTag,
                                                                                LogicalTime<?, ?> theTime)
      {
         return _rtiAmbassadorClient.asyncSendInteractionWithTime(theInteraction,
            theParameters,
            userSuppliedTag,
            theTime);
      }

      @Override
      public CompletableFuture<Void> deleteObjectInstance(ObjectInstanceHandle objectHandle,
                                                          byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncDeleteObjectInstance(objectHandle, userSuppliedTag);
      }

      @Override
      public CompletableFuture<MessageRetractionReturn> deleteObjectInstanceWithTime(ObjectInstanceHandle objectHandle,
                                                                                     byte[] userSuppliedTag,
                                                                                     LogicalTime<?, ?> theTime)
      {
         return _rtiAmbassadorClient.asyncDeleteObjectInstanceWithTime(objectHandle, userSuppliedTag, theTime);
      }

      @Override
      public CompletableFuture<Void> localDeleteObjectInstance(ObjectInstanceHandle objectHandle)
      {
         return _rtiAmbassadorClient.asyncLocalDeleteObjectInstance(objectHandle);
      }

      @Override
      public CompletableFuture<Void> requestInstanceAttributeValueUpdate(ObjectInstanceHandle theObject,
                                                                         AttributeHandleSet theAttributes,
                                                                         byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncRequestInstanceAttributeValueUpdate(theObject,
            theAttributes,
            userSuppliedTag);
      }

      @Override
      public CompletableFuture<Void> requestClassAttributeValueUpdate(ObjectClassHandle theClass,
                                                                      AttributeHandleSet theAttributes,
                                                                      byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncRequestClassAttributeValueUpdate(theClass, theAttributes, userSuppliedTag);
      }

      @Override
      public CompletableFuture<Void> requestAttributeTransportationTypeChange(ObjectInstanceHandle theObject,
                                                                              AttributeHandleSet theAttributes,
                                                                              TransportationTypeHandle theType)
      {
         return _rtiAmbassadorClient.asyncRequestAttributeTransportationTypeChange(theObject, theAttributes, theType);
      }

      @Override
      public CompletableFuture<Void> changeDefaultAttributeTransportationType(ObjectClassHandle theObjectClass,
                                                                              AttributeHandleSet theAttributes,
                                                                              TransportationTypeHandle theType)
      {
         return _rtiAmbassadorClient.asyncChangeDefaultAttributeTransportationType(theObjectClass,
            theAttributes,
            theType);
      }

      @Override
      public CompletableFuture<Void> queryAttributeTransportationType(ObjectInstanceHandle theObject,
                                                                      AttributeHandle theAttribute)
      {
         return _rtiAmbassadorClient.asyncQueryAttributeTransportationType(theObject, theAttribute);
      }

      @Override
      public CompletableFuture<Void> requestInteractionTransportationTypeChange(InteractionClassHandle theClass,
                                                                                TransportationTypeHandle theType)
      {
         return _rtiAmbassadorClient.asyncRequestInteractionTransportationTypeChange(theClass, theType);
      }

      @Override
      public CompletableFuture<Void> queryInteractionTransportationType(FederateHandle theFederate,
                                                                        InteractionClassHandle theInteraction)
      {
         return _rtiAmbassadorClient.asyncQueryInteractionTransportationType(theFederate, theInteraction);
      }

      @Override
      public CompletableFuture<Void> unconditionalAttributeOwnershipDivestiture(ObjectInstanceHandle theObject,
                                                                                AttributeHandleSet theAttributes,
                                                                                byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncUnconditionalAttributeOwnershipDivestiture(theObject,
            theAttributes,
            userSuppliedTag);
      }

      @Override
      public CompletableFuture<Void> negotiatedAttributeOwnershipDivestiture(ObjectInstanceHandle theObject,
                                                                             AttributeHandleSet theAttributes,
                                                                             byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncNegotiatedAttributeOwnershipDivestiture(theObject,
            theAttributes,
            userSuppliedTag);
      }

      @Override
      public CompletableFuture<Void> confirmDivestiture(ObjectInstanceHandle theObject,
                                                        AttributeHandleSet theAttributes,
                                                        byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncConfirmDivestiture(theObject, theAttributes, userSuppliedTag);
      }

      @Override
      public CompletableFuture<Void> attributeOwnershipAcquisition(ObjectInstanceHandle theObject,
                                                                   AttributeHandleSet desiredAttributes,
                                                                   byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncAttributeOwnershipAcquisition(theObject, desiredAttributes, userSuppliedTag);
      }

      @Override
      public CompletableFuture<Void> attributeOwnershipAcquisitionIfAvailable(ObjectInstanceHandle theObject,
                                                                              AttributeHandleSet desiredAttributes,
                                                                              byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncAttributeOwnershipAcquisitionIfAvailable(theObject,
            desiredAttributes,
            userSuppliedTag);
      }

      @Override
      public CompletableFuture<Void> attributeOwnershipReleaseDenied(ObjectInstanceHandle theObject,
                                                                     AttributeHandleSet theAttributes,
                                                                     byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncAttributeOwnershipReleaseDenied(theObject, theAttributes, userSuppliedTag);
      }

      @Override
      public CompletableFuture<AttributeHandleSet> attributeOwnershipDivestitureIfWanted(ObjectInstanceHandle theObject,
                                                                                         AttributeHandleSet theAttributes,
                                                                                         byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncAttributeOwnershipDivestitureIfWanted(theObject,
            theAttributes,
            userSuppliedTag);
      }

      @Override
      public CompletableFuture<Void> cancelNegotiatedAttributeOwnershipDivestiture(ObjectInstanceHandle theObject,
                                                                                   AttributeHandleSet theAttributes)
      {
         return _rtiAmbassadorClient.asyncCancelNegotiatedAttributeOwnershipDivestiture(theObject, theAttributes);
      }

      @Override
      public CompletableFuture<Void> cancelAttributeOwnershipAcquisition(ObjectInstanceHandle theObject,
                                                                         AttributeHandleSet theAttributes)
      {
         return _rtiAmbassadorClient.asyncCancelAttributeOwnershipAcquisition(theObject, theAttributes);
      }

      @Override
      public CompletableFuture<Void> queryAttributeOwnership(ObjectInstanceHandle theObject,
                                                             AttributeHandleSet theAttributes)
      {
         return _rtiAmbassadorClient.asyncQueryAttributeOwnership(theObject, theAttributes);
      }

      @Override
      public CompletableFuture<Boolean> isAttributeOwnedByFederate(ObjectInstanceHandle theObject,
                                                                   AttributeHandle theAttribute)
      {
         return _rtiAmbassadorClient.asyncIsAttributeOwnedByFederate(theObject, theAttribute);
      }

      @Override
      public CompletableFuture<Void> enableTimeRegulation(LogicalTimeInterval<?> theLookahead)
      {
         return _rtiAmbassadorClient.asyncEnableTimeRegulation(theLookahead);
      }

      @Override
      public CompletableFuture<Void> disableTimeRegulation()
      {
         return _rtiAmbassadorClient.asyncDisableTimeRegulation();
      }

      @Override
      public CompletableFuture<Void> enableTimeConstrained()
      {
         return _rtiAmbassadorClient.asyncEnableTimeConstrained();
      }

      @Override
      public CompletableFuture<Void> disableTimeConstrained()
      {
         return _rtiAmbassadorClient.asyncDisableTimeConstrained();
      }

      @Override
      public CompletableFuture<Void> timeAdvanceRequest(LogicalTime<?, ?> theTime)
      {
         return _rtiAmbassadorClient.asyncTimeAdvanceRequest(theTime);
      }

      @Override
      public CompletableFuture<Void> timeAdvanceRequestAvailable(LogicalTime<?, ?> theTime)
      {
         return _rtiAmbassadorClient.asyncTimeAdvanceRequestAvailable(theTime);
      }

      @Override
      public CompletableFuture<Void> nextMessageRequest(LogicalTime<?, ?> theTime)
      {
         return _rtiAmbassadorClient.asyncNextMessageRequest(theTime);
      }

      @Override
      public CompletableFuture<Void> nextMessageRequestAvailable(LogicalTime<?, ?> theTime)
      {
         return _rtiAmbassadorClient.asyncNextMessageRequestAvailable(theTime);
      }

      @Override
      public CompletableFuture<Void> flushQueueRequest(LogicalTime<?, ?> theTime)
      {
         return _rtiAmbassadorClient.asyncFlushQueueRequest(theTime);
      }

      @Override
      public CompletableFuture<Void> enableAsynchronousDelivery()
      {
         return _rtiAmbassadorClient.asyncEnableAsynchronousDelivery();
      }

      @Override
      public CompletableFuture<Void> disableAsynchronousDelivery()
      {
         return _rtiAmbassadorClient.asyncDisableAsynchronousDelivery();
      }

      @Override
      public CompletableFuture<TimeQueryReturn> queryGALT()
      {
         return _rtiAmbassadorClient.asyncQueryGALT();
      }

      @Override
      public CompletableFuture<LogicalTime> queryLogicalTime()
      {
         return _rtiAmbassadorClient.asyncQueryLogicalTime();
      }

      @Override
      public CompletableFuture<TimeQueryReturn> queryLITS()
      {
         return _rtiAmbassadorClient.asyncQueryLITS();
      }

      @Override
      public CompletableFuture<Void> modifyLookahead(LogicalTimeInterval<?> theLookahead)
      {
         return _rtiAmbassadorClient.asyncModifyLookahead(theLookahead);
      }

      @Override
      public CompletableFuture<LogicalTimeInterval> queryLookahead()
      {
         return _rtiAmbassadorClient.asyncQueryLookahead();
      }

      @Override
      public CompletableFuture<Void> retract(MessageRetractionHandle theHandle)
      {
         return _rtiAmbassadorClient.asyncRetract(theHandle);
      }

      @Override
      public CompletableFuture<Void> changeAttributeOrderType(ObjectInstanceHandle theObject,
                                                              AttributeHandleSet theAttributes,
                                                              OrderType theType)
      {
         return _rtiAmbassadorClient.asyncChangeAttributeOrderType(theObject, theAttributes, theType);
      }

      @Override
      public CompletableFuture<Void> changeDefaultAttributeOrderType(ObjectClassHandle theObjectClass,
                                                                     AttributeHandleSet theAttributes,
                                                                     OrderType theType)
      {
         return _rtiAmbassadorClient.asyncChangeDefaultAttributeOrderType(theObjectClass, theAttributes, theType);
      }

      @Override
      public CompletableFuture<Void> changeInteractionOrderType(InteractionClassHandle theClass,
                                                                OrderType theType)
      {
         return _rtiAmbassadorClient.asyncChangeInteractionOrderType(theClass, theType);
      }

      @Override
      public CompletableFuture<RegionHandle> createRegion(DimensionHandleSet dimensions)
      {
         return _rtiAmbassadorClient.asyncCreateRegion(dimensions);
      }

      @Override
      public CompletableFuture<Void> commitRegionModifications(RegionHandleSet regions)
      {
         return _rtiAmbassadorClient.asyncCommitRegionModifications(regions);
      }

      @Override
      public CompletableFuture<Void> deleteRegion(RegionHandle theRegion)
      {
         return _rtiAmbassadorClient.asyncDeleteRegion(theRegion);
      }

      @Override
      public CompletableFuture<ObjectInstanceHandle> registerObjectInstanceWithRegions(ObjectClassHandle theClass,
                                                                                       AttributeSetRegionSetPairList attributesAndRegions)
      {
         return _rtiAmbassadorClient.asyncRegisterObjectInstanceWithRegions(theClass, attributesAndRegions);
      }

      @Override
      public CompletableFuture<ObjectInstanceHandle> registerObjectInstanceWithNameAndRegions(ObjectClassHandle theClass,
                                                                                              AttributeSetRegionSetPairList attributesAndRegions,
                                                                                              java.lang.String theObject)
      {
         return _rtiAmbassadorClient.asyncRegisterObjectInstanceWithNameAndRegions(theClass,
            attributesAndRegions,
            theObject);
      }

      @Override
      public CompletableFuture<Void> associateRegionsForUpdates(ObjectInstanceHandle theObject,
                                                                AttributeSetRegionSetPairList attributesAndRegions)
      {
         return _rtiAmbassadorClient.asyncAssociateRegionsForUpdates(theObject, attributesAndRegions);
      }

      @Override
      public CompletableFuture<Void> unassociateRegionsForUpdates(ObjectInstanceHandle theObject,
                                                                  AttributeSetRegionSetPairList attributesAndRegions)
      {
         return _rtiAmbassadorClient.asyncUnassociateRegionsForUpdates(theObject, attributesAndRegions);
      }

      @Override
      public CompletableFuture<Void> subscribeObjectClassAttributesWithRegions(ObjectClassHandle theClass,
                                                                               AttributeSetRegionSetPairList attributesAndRegions,
                                                                               boolean active)
      {
         return _rtiAmbassadorClient.asyncSubscribeObjectClassAttributesWithRegions(theClass,
            attributesAndRegions,
            active);
      }

      @Override
      public CompletableFuture<Void> subscribeObjectClassAttributesWithRegionsAndRate(ObjectClassHandle theClass,
                                                                                      AttributeSetRegionSetPairList attributesAndRegions,
                                                                                      boolean active,
                                                                                      java.lang.String updateRateDesignator)
      {
         return _rtiAmbassadorClient.asyncSubscribeObjectClassAttributesWithRegionsAndRate(theClass,
            attributesAndRegions,
            active,
            updateRateDesignator);
      }

      @Override
      public CompletableFuture<Void> unsubscribeObjectClassAttributesWithRegions(ObjectClassHandle theClass,
                                                                                 AttributeSetRegionSetPairList attributesAndRegions)
      {
         return _rtiAmbassadorClient.asyncUnsubscribeObjectClassAttributesWithRegions(theClass, attributesAndRegions);
      }

      @Override
      public CompletableFuture<Void> subscribeInteractionClassWithRegions(InteractionClassHandle theClass,
                                                                          boolean active,
                                                                          RegionHandleSet regions)
      {
         return _rtiAmbassadorClient.asyncSubscribeInteractionClassWithRegions(theClass, active, regions);
      }

      @Override
      public CompletableFuture<Void> unsubscribeInteractionClassWithRegions(InteractionClassHandle theClass,
                                                                            RegionHandleSet regions)
      {
         return _rtiAmbassadorClient.asyncUnsubscribeInteractionClassWithRegions(theClass, regions);
      }

      @Override
      public CompletableFuture<Void> sendInteractionWithRegions(InteractionClassHandle theInteraction,
                                                                ParameterHandleValueMap theParameters,
                                                                RegionHandleSet regions,
                                                                byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncSendInteractionWithRegions(theInteraction,
            theParameters,
            regions,
            userSuppliedTag);
      }

      @Override
      public CompletableFuture<MessageRetractionReturn> sendInteractionWithRegionsAndTime(InteractionClassHandle theInteraction,
                                                                                          ParameterHandleValueMap theParameters,
                                                                                          RegionHandleSet regions,
                                                                                          byte[] userSuppliedTag,
                                                                                          LogicalTime<?, ?> theTime)
      {
         return _rtiAmbassadorClient.asyncSendInteractionWithRegionsAndTime(theInteraction,
            theParameters,
            regions,
            userSuppliedTag,
            theTime);
      }

      @Override
      public CompletableFuture<Void> requestAttributeValueUpdateWithRegions(ObjectClassHandle theClass,
                                                                            AttributeSetRegionSetPairList attributesAndRegions,
                                                                            byte[] userSuppliedTag)
      {
         return _rtiAmbassadorClient.asyncRequestAttributeValueUpdateWithRegions(theClass,
            attributesAndRegions,
            userSuppliedTag);
      }

      @Override
      public CompletableFuture<ResignAction> getAutomaticResignDirective()
      {
         return _rtiAmbassadorClient.asyncGetAutomaticResignDirective();
      }

      @Override
      public CompletableFuture<Void> setAutomaticResignDirective(ResignAction resignAction)
      {
         return _rtiAmbassadorClient.asyncSetAutomaticResignDirective(resignAction);
      }

      @Override
      public CompletableFuture<FederateHandle> getFederateHandle(java.lang.String theName)
      {
         return _rtiAmbassadorClient.asyncGetFederateHandle(theName);
      }

      @Override
      public CompletableFuture<String> getFederateName(FederateHandle theHandle)
      {
         return _rtiAmbassadorClient.asyncGetFederateName(theHandle);
      }

      @Override
      public CompletableFuture<ObjectClassHandle> getObjectClassHandle(java.lang.String theName)
      {
         return _rtiAmbassadorClient.asyncGetObjectClassHandle(theName);
      }

      @Override
      public CompletableFuture<String> getObjectClassName(ObjectClassHandle theHandle)
      {
         return _rtiAmbassadorClient.asyncGetObjectClassName(theHandle);
      }

      @Override
      public CompletableFuture<ObjectClassHandle> getKnownObjectClassHandle(ObjectInstanceHandle theObject)
      {
         return _rtiAmbassadorClient.asyncGetKnownObjectClassHandle(theObject);
      }

      @Override
      public CompletableFuture<ObjectInstanceHandle> getObjectInstanceHandle(java.lang.String theName)
      {
         return _rtiAmbassadorClient.asyncGetObjectInstanceHandle(theName);
      }

      @Override
      public CompletableFuture<String> getObjectInstanceName(ObjectInstanceHandle theHandle)
      {
         return _rtiAmbassadorClient.asyncGetObjectInstanceName(theHandle);
      }

      @Override
      public CompletableFuture<AttributeHandle> getAttributeHandle(ObjectClassHandle whichClass,
                                                                   java.lang.String theName)
      {
         return _rtiAmbassadorClient.asyncGetAttributeHandle(whichClass, theName);
      }

      @Override
      public CompletableFuture<String> getAttributeName(ObjectClassHandle whichClass,
                                                        AttributeHandle theHandle)
      {
         return _rtiAmbassadorClient.asyncGetAttributeName(whichClass, theHandle);
      }

      @Override
      public CompletableFuture<Double> getUpdateRateValue(java.lang.String updateRateDesignator)
      {
         return _rtiAmbassadorClient.asyncGetUpdateRateValue(updateRateDesignator);
      }

      @Override
      public CompletableFuture<Double> getUpdateRateValueForAttribute(ObjectInstanceHandle theObject,
                                                                      AttributeHandle theAttribute)
      {
         return _rtiAmbassadorClient.asyncGetUpdateRateValueForAttribute(theObject, theAttribute);
      }

      @Override
      public CompletableFuture<InteractionClassHandle> getInteractionClassHandle(java.lang.String theName)
      {
         return _rtiAmbassadorClient.asyncGetInteractionClassHandle(theName);
      }

      @Override
      public CompletableFuture<String> getInteractionClassName(InteractionClassHandle theHandle)
      {
         return _rtiAmbassadorClient.asyncGetInteractionClassName(theHandle);
      }

      @Override
      public CompletableFuture<ParameterHandle> getParameterHandle(InteractionClassHandle whichClass,
                                                                   java.lang.String theName)
      {
         return _rtiAmbassadorClient.asyncGetParameterHandle(whichClass, theName);
      }

      @Override
      public CompletableFuture<String> getParameterName(InteractionClassHandle whichClass,
                                                        ParameterHandle theHandle)
      {
         return _rtiAmbassadorClient.asyncGetParameterName(whichClass, theHandle);
      }

      @Override
      public CompletableFuture<OrderType> getOrderType(java.lang.String theName)
      {
         return _rtiAmbassadorClient.asyncGetOrderType(theName);
      }

      @Override
      public CompletableFuture<String> getOrderName(OrderType theType)
      {
         return _rtiAmbassadorClient.asyncGetOrderName(theType);
      }

      @Override
      public CompletableFuture<TransportationTypeHandle> getTransportationTypeHandle(java.lang.String theName)
      {
         return _rtiAmbassadorClient.asyncGetTransportationTypeHandle(theName);
      }

      @Override
      public CompletableFuture<String> getTransportationTypeName(TransportationTypeHandle theHandle)
      {
         return _rtiAmbassadorClient.asyncGetTransportationTypeName(theHandle);
      }

      @Override
      public CompletableFuture<DimensionHandleSet> getAvailableDimensionsForClassAttribute(ObjectClassHandle whichClass,
                                                                                           AttributeHandle theHandle)
      {
         return _rtiAmbassadorClient.asyncGetAvailableDimensionsForClassAttribute(whichClass, theHandle);
      }

      @Override
      public CompletableFuture<DimensionHandleSet> getAvailableDimensionsForInteractionClass(InteractionClassHandle theHandle)
      {
         return _rtiAmbassadorClient.asyncGetAvailableDimensionsForInteractionClass(theHandle);
      }

      @Override
      public CompletableFuture<DimensionHandle> getDimensionHandle(java.lang.String theName)
      {
         return _rtiAmbassadorClient.asyncGetDimensionHandle(theName);
      }

      @Override
      public CompletableFuture<String> getDimensionName(DimensionHandle theHandle)
      {
         return _rtiAmbassadorClient.asyncGetDimensionName(theHandle);
      }

      @Override
      public CompletableFuture<Long> getDimensionUpperBound(DimensionHandle theHandle)
      {
         return _rtiAmbassadorClient.asyncGetDimensionUpperBound(theHandle);
      }

      @Override
      public CompletableFuture<DimensionHandleSet> getDimensionHandleSet(RegionHandle region)
      {
         return _rtiAmbassadorClient.asyncGetDimensionHandleSet(region);
      }

      @Override
      public CompletableFuture<RangeBounds> getRangeBounds(RegionHandle region,
                                                           DimensionHandle dimension)
      {
         return _rtiAmbassadorClient.asyncGetRangeBounds(region, dimension);
      }

      @Override
      public CompletableFuture<Void> setRangeBounds(RegionHandle region,
                                                    DimensionHandle dimension,
                                                    RangeBounds bounds)
      {
         return _rtiAmbassadorClient.asyncSetRangeBounds(region, dimension, bounds);
      }

      @Override
      public CompletableFuture<Long> normalizeServiceGroup(ServiceGroup group)
      {
         return _rtiAmbassadorClient.asyncNormalizeServiceGroup(group);
      }

      @Override
      public CompletableFuture<Long> normalizeFederateHandle(FederateHandle federateHandle)
      {
         return _rtiAmbassadorClient.asyncNormalizeFederateHandle(federateHandle);
      }

      @Override
      public CompletableFuture<Long> normalizeObjectClassHandle(ObjectClassHandle objectClassHandle)
      {
         return _rtiAmbassadorClient.asyncNormalizeObjectClassHandle(objectClassHandle);
      }

      @Override
      public CompletableFuture<Long> normalizeInteractionClassHandle(InteractionClassHandle interactionClassHandle)
      {
         return _rtiAmbassadorClient.asyncNormalizeInteractionClassHandle(interactionClassHandle);
      }

      @Override
      public CompletableFuture<Long> normalizeObjectInstanceHandle(ObjectInstanceHandle objectInstanceHandle)
      {
         return _rtiAmbassadorClient.asyncNormalizeObjectInstanceHandle(objectInstanceHandle);
      }

      @Override
      public CompletableFuture<Void> enableObjectClassRelevanceAdvisorySwitch()
      {
         return _rtiAmbassadorClient.asyncEnableObjectClassRelevanceAdvisorySwitch();
      }

      @Override
      public CompletableFuture<Void> disableObjectClassRelevanceAdvisorySwitch()
      {
         return _rtiAmbassadorClient.asyncDisableObjectClassRelevanceAdvisorySwitch();
      }

      @Override
      public CompletableFuture<Void> enableAttributeRelevanceAdvisorySwitch()
      {
         return _rtiAmbassadorClient.asyncEnableAttributeRelevanceAdvisorySwitch();
      }

      @Override
      public CompletableFuture<Void> disableAttributeRelevanceAdvisorySwitch()
      {
         return _rtiAmbassadorClient.asyncDisableAttributeRelevanceAdvisorySwitch();
      }

      @Override
      public CompletableFuture<Void> enableAttributeScopeAdvisorySwitch()
      {
         return _rtiAmbassadorClient.asyncEnableAttributeScopeAdvisorySwitch();
      }

      @Override
      public CompletableFuture<Void> disableAttributeScopeAdvisorySwitch()
      {
         return _rtiAmbassadorClient.asyncDisableAttributeScopeAdvisorySwitch();
      }

      @Override
      public CompletableFuture<Void> enableInteractionRelevanceAdvisorySwitch()
      {
         return _rtiAmbassadorClient.asyncEnableInteractionRelevanceAdvisorySwitch();
      }

      @Override
      public CompletableFuture<Void> disableInteractionRelevanceAdvisorySwitch()
      {
         return _rtiAmbassadorClient.asyncDisableInteractionRelevanceAdvisorySwitch();
      }

   };

   public RTIambassadorClientAdapter()
   {
      _clientConverter = new ClientConverter();
      _rtiAmbassadorClient = new RTIambassadorClient(_clientConverter);
   }

   @Override
   public AsyncRTIambassador async()
   {
      return _asyncRTIambassador;
   }

   @Override
   public ConfigurationResult connect(FederateAmbassador federateReference,
                                      CallbackModel callbackModel,
                                      RtiConfiguration configuration) throws
      ConnectionFailed,
      UnsupportedCallbackModel,
      AlreadyConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError
   {
      return _rtiAmbassadorClient.connect(federateReference, callbackModel, configuration);
   }

   @Override
   public ConfigurationResult connect(FederateAmbassador federateReference,
                                      CallbackModel callbackModel,
                                      Credentials credentials) throws
      Unauthorized,
      InvalidCredentials,
      ConnectionFailed,
      UnsupportedCallbackModel,
      AlreadyConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError
   {
      return _rtiAmbassadorClient.connect(federateReference, callbackModel, credentials);
   }

   @Override
   public ConfigurationResult connect(FederateAmbassador federateReference,
                                      CallbackModel callbackModel,
                                      RtiConfiguration configuration,
                                      Credentials credentials) throws
      Unauthorized,
      InvalidCredentials,
      ConnectionFailed,
      UnsupportedCallbackModel,
      AlreadyConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError
   {
      return _rtiAmbassadorClient.connect(federateReference, callbackModel, configuration, credentials);
   }

   public ConfigurationResult connect(FederateAmbassador federateReference,
                                      CallbackModel callbackModel) throws
      ConnectionFailed,
      UnsupportedCallbackModel,
      AlreadyConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError
   {
      return _rtiAmbassadorClient.connect(federateReference, callbackModel);
   }

   public void disconnect() throws FederateIsExecutionMember, CallNotAllowedFromWithinCallback, RTIinternalError
   {
      _rtiAmbassadorClient.disconnect();
   }

   public void createFederationExecution(String federationExecutionName,
                                         URL[] fomModules,
                                         URL mimModule,
                                         String logicalTimeImplementationName) throws
      CouldNotCreateLogicalTimeFactory,
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      ErrorReadingMIM,
      InvalidMIM,
      CouldNotOpenMIM,
      DesignatorIsHLAstandardMIM,
      FederationExecutionAlreadyExists,
      NotConnected,
      RTIinternalError,
      Unauthorized
   {
      FomModuleSet fomModuleSet = getFomModuleSet(fomModules);
      _rtiAmbassadorClient.createFederationExecutionWithMIMAndTime(federationExecutionName,
         fomModuleSet,
         getFomModule(mimModule),
         logicalTimeImplementationName);
   }

   public void createFederationExecution(String federationExecutionName,
                                         URL[] fomModules,
                                         String logicalTimeImplementationName) throws
      CouldNotCreateLogicalTimeFactory,
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      FederationExecutionAlreadyExists,
      NotConnected,
      Unauthorized,
      RTIinternalError
   {
      FomModuleSet fomModuleSet = getFomModuleSet(fomModules);
      _rtiAmbassadorClient.createFederationExecutionWithModulesAndTime(federationExecutionName,
         fomModuleSet,
         logicalTimeImplementationName);
   }

   private FomModuleSet getFomModuleSet(URL[] fomModules)
   {
      FomModuleSet result = new FomModuleSet();
      for (URL fomModule : fomModules) {
         result.add(getFomModule(fomModule));
      }
      return result;
   }

   private FomModule getFomModule(URL fomModule)
   {
      if ("file".equalsIgnoreCase(fomModule.getProtocol())) {
         try {
            File file = new File(fomModule.toURI());
            if (file.length() > 1024) {
               return new FomModule(zip(file));
            } else {
               return new FomModule(file.getName(), Files.readAllBytes(file.toPath()));
            }
         } catch (IllegalArgumentException | URISyntaxException | IOException ignore) {
            // fallback to url
         }
      }
      return new FomModule(fomModule.toString());
   }

   private static byte[] zip(File file) throws IOException
   {
      ByteArrayOutputStream result = new ByteArrayOutputStream();

      ZipOutputStream zip = new ZipOutputStream(result);

      zip.putNextEntry(new ZipEntry(file.getName()));
      Files.copy(file.toPath(), zip);
      zip.closeEntry();

      zip.close();

      return result.toByteArray();
   }

   public void createFederationExecution(String federationExecutionName,
                                         URL[] fomModules,
                                         URL mimModule) throws
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      ErrorReadingMIM,
      InvalidMIM,
      CouldNotOpenMIM,
      DesignatorIsHLAstandardMIM,
      FederationExecutionAlreadyExists,
      NotConnected,
      Unauthorized,
      RTIinternalError
   {
      try {
         FomModuleSet fomModuleSet = getFomModuleSet(fomModules);
         _rtiAmbassadorClient.createFederationExecutionWithMIMAndTime(federationExecutionName,
            fomModuleSet,
            getFomModule(mimModule),
            "HLAfloat64Time");
      } catch (CouldNotCreateLogicalTimeFactory e) {
         throw new RTIinternalError("Failed to create default time HLAfloat64Time");
      }
   }

   public void createFederationExecution(String federationExecutionName,
                                         URL[] fomModules) throws
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      FederationExecutionAlreadyExists,
      NotConnected,
      Unauthorized,
      RTIinternalError
   {
      FomModuleSet fomModuleSet = getFomModuleSet(fomModules);
      _rtiAmbassadorClient.createFederationExecutionWithModules(federationExecutionName, fomModuleSet);
   }

   public void createFederationExecution(String federationExecutionName,
                                         URL fomModule) throws
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      FederationExecutionAlreadyExists,
      NotConnected,
      Unauthorized,
      RTIinternalError
   {
      _rtiAmbassadorClient.createFederationExecution(federationExecutionName, getFomModule(fomModule));
   }

   public void destroyFederationExecution(String federationExecutionName)
      throws FederatesCurrentlyJoined, FederationExecutionDoesNotExist, NotConnected, Unauthorized, RTIinternalError
   {
      _rtiAmbassadorClient.destroyFederationExecution(federationExecutionName);
   }

   public void listFederationExecutions() throws NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.listFederationExecutions();
   }

   @Override
   public void listFederationExecutionMembers(String federationExecutionName) throws NotConnected, RTIinternalError
   {
      if (federationExecutionName == null) {
         throw new NullPointerException("String");
      }
      _rtiAmbassadorClient.listFederationExecutionMembers(federationExecutionName);
   }

   public FederateHandle joinFederationExecution(String federateName,
                                                 String federateType,
                                                 String federationExecutionName,
                                                 URL[] additionalFomModules) throws
      CouldNotCreateLogicalTimeFactory,
      FederateNameAlreadyInUse,
      FederationExecutionDoesNotExist,
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      SaveInProgress,
      RestoreInProgress,
      FederateAlreadyExecutionMember,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      Unauthorized,
      RTIinternalError
   {
      FomModuleSet fomModuleSet = getFomModuleSet(additionalFomModules);
      JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecutionWithNameAndModules(federateName,
         federateType,
         federationExecutionName,
         fomModuleSet);
      FederateHandle federateHandle = joinResult.getFederateHandle();
      String timeFactoryName = joinResult.getLogicalTimeImplementationName();
      _timeFactory = LogicalTimeFactoryFactory.getLogicalTimeFactory(timeFactoryName);
      _clientConverter.setTimeFactory(_timeFactory);
      return federateHandle;
   }

   public FederateHandle joinFederationExecution(String federateType,
                                                 String federationExecutionName,
                                                 URL[] additionalFomModules) throws
      CouldNotCreateLogicalTimeFactory,
      FederationExecutionDoesNotExist,
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      SaveInProgress,
      RestoreInProgress,
      FederateAlreadyExecutionMember,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      Unauthorized,
      RTIinternalError
   {
      FomModuleSet fomModuleSet = getFomModuleSet(additionalFomModules);
      JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecutionWithModules(federateType,
         federationExecutionName,
         fomModuleSet);
      FederateHandle federateHandle = joinResult.getFederateHandle();
      String timeFactoryName = joinResult.getLogicalTimeImplementationName();
      _timeFactory = LogicalTimeFactoryFactory.getLogicalTimeFactory(timeFactoryName);
      _clientConverter.setTimeFactory(_timeFactory);
      return federateHandle;
   }

   public FederateHandle joinFederationExecution(String federateName,
                                                 String federateType,
                                                 String federationExecutionName) throws
      CouldNotCreateLogicalTimeFactory,
      FederateNameAlreadyInUse,
      FederationExecutionDoesNotExist,
      SaveInProgress,
      RestoreInProgress,
      FederateAlreadyExecutionMember,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      Unauthorized,
      RTIinternalError
   {
      // FIXME join throws incorrect exceptions
      JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecutionWithName(federateName,
         federateType,
         federationExecutionName);
      FederateHandle federateHandle = joinResult.getFederateHandle();
      String timeFactoryName = joinResult.getLogicalTimeImplementationName();
      _timeFactory = LogicalTimeFactoryFactory.getLogicalTimeFactory(timeFactoryName);
      _clientConverter.setTimeFactory(_timeFactory);
      return federateHandle;
   }

   public FederateHandle joinFederationExecution(String federateType,
                                                 String federationExecutionName) throws
      CouldNotCreateLogicalTimeFactory,
      FederationExecutionDoesNotExist,
      SaveInProgress,
      RestoreInProgress,
      FederateAlreadyExecutionMember,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      Unauthorized,
      RTIinternalError
   {
      // FIXME join throws incorrect exceptions
      JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecution(federateType, federationExecutionName);
      FederateHandle federateHandle = joinResult.getFederateHandle();
      String timeFactoryName = joinResult.getLogicalTimeImplementationName();
      _timeFactory = LogicalTimeFactoryFactory.getLogicalTimeFactory(timeFactoryName);
      _clientConverter.setTimeFactory(_timeFactory);
      return federateHandle;
   }

   public void resignFederationExecution(ResignAction resignAction) throws
      InvalidResignAction,
      OwnershipAcquisitionPending,
      FederateOwnsAttributes,
      FederateNotExecutionMember,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError
   {
      _rtiAmbassadorClient.resignFederationExecution(resignAction);
      // Let's leave the time factories assigned since we can get late callbacks.
      //_timeFactory = null;
      //_clientConverter.setTimeFactory(null);
   }

   public void registerFederationSynchronizationPoint(String synchronizationPointLabel,
                                                      byte[] userSuppliedTag)
      throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.registerFederationSynchronizationPoint(synchronizationPointLabel, userSuppliedTag);
   }

   public void registerFederationSynchronizationPoint(String synchronizationPointLabel,
                                                      byte[] userSuppliedTag,
                                                      FederateHandleSet synchronizationSet) throws
      InvalidFederateHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.registerFederationSynchronizationPointWithSet(synchronizationPointLabel,
         userSuppliedTag,
         synchronizationSet);
   }

   public void synchronizationPointAchieved(String synchronizationPointLabel) throws
      SynchronizationPointLabelNotAnnounced,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.synchronizationPointAchieved(synchronizationPointLabel, true);
   }

   public void synchronizationPointAchieved(String synchronizationPointLabel,
                                            boolean successIndicator) throws
      SynchronizationPointLabelNotAnnounced,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.synchronizationPointAchieved(synchronizationPointLabel, successIndicator);
   }

   public void requestFederationSave(String label)
      throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.requestFederationSave(label);
   }

   public void requestFederationSave(String label,
                                     LogicalTime<?, ?> theTime) throws
      LogicalTimeAlreadyPassed,
      InvalidLogicalTime,
      FederateUnableToUseTime,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.requestFederationSaveWithTime(label, theTime);
   }

   public void federateSaveBegun()
      throws SaveNotInitiated, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.federateSaveBegun();
   }

   public void federateSaveComplete()
      throws FederateHasNotBegunSave, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.federateSaveComplete();
   }

   public void federateSaveNotComplete()
      throws FederateHasNotBegunSave, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.federateSaveNotComplete();
   }

   public void abortFederationSave()
      throws SaveNotInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.abortFederationSave();
   }

   public void queryFederationSaveStatus()
      throws RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.queryFederationSaveStatus();
   }

   public void requestFederationRestore(String label)
      throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.requestFederationRestore(label);
   }

   public void federateRestoreComplete()
      throws RestoreNotRequested, SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.federateRestoreComplete();
   }

   public void federateRestoreNotComplete()
      throws RestoreNotRequested, SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.federateRestoreNotComplete();
   }

   public void abortFederationRestore()
      throws RestoreNotInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.abortFederationRestore();
   }

   public void queryFederationRestoreStatus()
      throws SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.queryFederationRestoreStatus();
   }

   public void publishObjectClassAttributes(ObjectClassHandle theClass,
                                            AttributeHandleSet attributeList) throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.publishObjectClassAttributes(theClass, attributeList);
   }

   public void unpublishObjectClass(ObjectClassHandle theClass) throws
      OwnershipAcquisitionPending,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unpublishObjectClass(theClass);
   }

   public void unpublishObjectClassAttributes(ObjectClassHandle theClass,
                                              AttributeHandleSet attributeList) throws
      OwnershipAcquisitionPending,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unpublishObjectClassAttributes(theClass, attributeList);
   }

   @Override
   public void publishObjectClassDirectedInteractions(ObjectClassHandle theClass,
                                                      InteractionClassHandleSet interactionList) throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {

   }

   @Override
   public void unpublishObjectClassDirectedInteractions(ObjectClassHandle theClass) throws
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {

   }

   @Override
   public void unpublishObjectClassDirectedInteractions(ObjectClassHandle theClass,
                                                        InteractionClassHandleSet interactionList) throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {

   }

   public void publishInteractionClass(InteractionClassHandle theInteraction) throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theInteraction == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.publishInteractionClass(theInteraction);
   }

   public void unpublishInteractionClass(InteractionClassHandle theInteraction) throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theInteraction == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.unpublishInteractionClass(theInteraction);
   }

   public void subscribeObjectClassAttributes(ObjectClassHandle theClass,
                                              AttributeHandleSet attributeList) throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributes(theClass, attributeList);
   }

   public void subscribeObjectClassAttributes(ObjectClassHandle theClass,
                                              AttributeHandleSet attributeList,
                                              String updateRateDesignator) throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      InvalidUpdateRateDesignator,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRate(theClass, attributeList, updateRateDesignator);
   }

   public void subscribeObjectClassAttributesPassively(ObjectClassHandle theClass,
                                                       AttributeHandleSet attributeList) throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesPassively(theClass, attributeList);
   }

   public void subscribeObjectClassAttributesPassively(ObjectClassHandle theClass,
                                                       AttributeHandleSet attributeList,
                                                       String updateRateDesignator) throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      InvalidUpdateRateDesignator,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesPassivelyWithRate(theClass,
         attributeList,
         updateRateDesignator);
   }

   public void unsubscribeObjectClass(ObjectClassHandle theClass) throws
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeObjectClass(theClass);
   }

   public void unsubscribeObjectClassAttributes(ObjectClassHandle theClass,
                                                AttributeHandleSet attributeList) throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeObjectClassAttributes(theClass, attributeList);
   }

   @Override
   public void subscribeObjectClassDirectedInteractions(ObjectClassHandle theClass,
                                                        InteractionClassHandleSet interactionList) throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {

   }

   @Override
   public void unsubscribeObjectClassDirectedInteractions(ObjectClassHandle theClass) throws
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {

   }

   @Override
   public void unsubscribeObjectClassDirectedInteractions(ObjectClassHandle theClass,
                                                          InteractionClassHandleSet interactionList) throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {

   }

   public void subscribeInteractionClass(InteractionClassHandle theClass) throws
      FederateServiceInvocationsAreBeingReportedViaMOM,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeInteractionClass(theClass);
   }

   public void subscribeInteractionClassPassively(InteractionClassHandle theClass) throws
      FederateServiceInvocationsAreBeingReportedViaMOM,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeInteractionClassPassively(theClass);
   }

   public void unsubscribeInteractionClass(InteractionClassHandle theClass) throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeInteractionClass(theClass);
   }

   public void reserveObjectInstanceName(String theObjectName)
      throws IllegalName, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.reserveObjectInstanceName(theObjectName);
   }

   public void releaseObjectInstanceName(String theObjectInstanceName) throws
      ObjectInstanceNameNotReserved,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.releaseObjectInstanceName(theObjectInstanceName);
   }

   public void reserveMultipleObjectInstanceName(Set<String> theObjectNames) throws
      IllegalName,
      NameSetWasEmpty,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.reserveMultipleObjectInstanceName(theObjectNames);
   }

   public void releaseMultipleObjectInstanceName(Set<String> theObjectNames) throws
      ObjectInstanceNameNotReserved,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.releaseMultipleObjectInstanceName(theObjectNames);
   }

   public ObjectInstanceHandle registerObjectInstance(ObjectClassHandle theClass) throws
      ObjectClassNotPublished,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      return _rtiAmbassadorClient.registerObjectInstance(theClass);
   }

   public ObjectInstanceHandle registerObjectInstance(ObjectClassHandle theClass,
                                                      String theObjectName) throws
      ObjectInstanceNameInUse,
      ObjectInstanceNameNotReserved,
      ObjectClassNotPublished,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithName(theClass, theObjectName);
   }

   public void updateAttributeValues(ObjectInstanceHandle theObject,
                                     AttributeHandleValueMap theAttributes,
                                     byte[] userSuppliedTag) throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.updateAttributeValues(theObject, theAttributes, userSuppliedTag);
   }

   public MessageRetractionReturn updateAttributeValues(ObjectInstanceHandle theObject,
                                                        AttributeHandleValueMap theAttributes,
                                                        byte[] userSuppliedTag,
                                                        LogicalTime<?, ?> theTime) throws
      InvalidLogicalTime,
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      return _rtiAmbassadorClient.updateAttributeValuesWithTime(theObject, theAttributes, userSuppliedTag, theTime);
   }

   public void sendInteraction(InteractionClassHandle theInteraction,
                               ParameterHandleValueMap theParameters,
                               byte[] userSuppliedTag) throws
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theInteraction == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.sendInteraction(theInteraction, theParameters, userSuppliedTag);
   }

   public MessageRetractionReturn sendInteraction(InteractionClassHandle theInteraction,
                                                  ParameterHandleValueMap theParameters,
                                                  byte[] userSuppliedTag,
                                                  LogicalTime<?, ?> theTime) throws
      InvalidLogicalTime,
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theInteraction == null) {
         throw new InteractionClassNotDefined("null");
      }
      return _rtiAmbassadorClient.sendInteractionWithTime(theInteraction, theParameters, userSuppliedTag, theTime);
   }

   @Override
   public void sendDirectedInteraction(InteractionClassHandle theInteraction,
                                       ObjectInstanceHandle theObject,
                                       ParameterHandleValueMap theParameters,
                                       byte[] userSuppliedTag) throws
      ObjectInstanceNotKnown,
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {

   }

   @Override
   public MessageRetractionReturn sendDirectedInteraction(InteractionClassHandle theInteraction,
                                                          ObjectInstanceHandle theObject,
                                                          ParameterHandleValueMap theParameters,
                                                          byte[] userSuppliedTag,
                                                          LogicalTime<?, ?> theTime) throws
      InvalidLogicalTime,
      ObjectInstanceNotKnown,
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      return null;
   }

   public void deleteObjectInstance(ObjectInstanceHandle objectHandle,
                                    byte[] userSuppliedTag) throws
      DeletePrivilegeNotHeld,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.deleteObjectInstance(objectHandle, userSuppliedTag);
   }

   public MessageRetractionReturn deleteObjectInstance(ObjectInstanceHandle objectHandle,
                                                       byte[] userSuppliedTag,
                                                       LogicalTime<?, ?> theTime) throws
      InvalidLogicalTime,
      DeletePrivilegeNotHeld,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      return _rtiAmbassadorClient.deleteObjectInstanceWithTime(objectHandle, userSuppliedTag, theTime);
   }

   public void localDeleteObjectInstance(ObjectInstanceHandle objectHandle) throws
      OwnershipAcquisitionPending,
      FederateOwnsAttributes,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.localDeleteObjectInstance(objectHandle);
   }

   public void requestAttributeValueUpdate(ObjectInstanceHandle theObject,
                                           AttributeHandleSet theAttributes,
                                           byte[] userSuppliedTag) throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.requestInstanceAttributeValueUpdate(theObject, theAttributes, userSuppliedTag);
   }

   public void requestAttributeValueUpdate(ObjectClassHandle theClass,
                                           AttributeHandleSet theAttributes,
                                           byte[] userSuppliedTag) throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.requestClassAttributeValueUpdate(theClass, theAttributes, userSuppliedTag);
   }

   public void requestAttributeTransportationTypeChange(ObjectInstanceHandle theObject,
                                                        AttributeHandleSet theAttributes,
                                                        TransportationTypeHandle theType) throws
      AttributeAlreadyBeingChanged,
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      InvalidTransportationTypeHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.requestAttributeTransportationTypeChange(theObject, theAttributes, theType);
   }

   @Override
   public void changeDefaultAttributeTransportationType(ObjectClassHandle theObjectClass,
                                                        AttributeHandleSet theAttributes,
                                                        TransportationTypeHandle theType) throws
      AttributeAlreadyBeingChanged,
      AttributeNotDefined,
      ObjectClassNotDefined,
      InvalidTransportationTypeHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.changeDefaultAttributeTransportationType(theObjectClass, theAttributes, theType);
   }

   public void queryAttributeTransportationType(ObjectInstanceHandle theObject,
                                                AttributeHandle theAttribute) throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.queryAttributeTransportationType(theObject, theAttribute);
   }

   public void requestInteractionTransportationTypeChange(InteractionClassHandle theClass,
                                                          TransportationTypeHandle theType) throws
      InteractionClassAlreadyBeingChanged,
      InteractionClassNotPublished,
      InteractionClassNotDefined,
      InvalidTransportationTypeHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.requestInteractionTransportationTypeChange(theClass, theType);
   }

   public void queryInteractionTransportationType(FederateHandle theFederate,
                                                  InteractionClassHandle theInteraction) throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.queryInteractionTransportationType(theFederate, theInteraction);
   }

   public void unconditionalAttributeOwnershipDivestiture(ObjectInstanceHandle theObject,
                                                          AttributeHandleSet theAttributes,
                                                          byte[] userSuppliedTag) throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.unconditionalAttributeOwnershipDivestiture(theObject, theAttributes, userSuppliedTag);
   }

   public void negotiatedAttributeOwnershipDivestiture(ObjectInstanceHandle theObject,
                                                       AttributeHandleSet theAttributes,
                                                       byte[] userSuppliedTag) throws
      AttributeAlreadyBeingDivested,
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.negotiatedAttributeOwnershipDivestiture(theObject, theAttributes, userSuppliedTag);
   }

   public void confirmDivestiture(ObjectInstanceHandle theObject,
                                  AttributeHandleSet theAttributes,
                                  byte[] userSuppliedTag) throws
      NoAcquisitionPending,
      AttributeDivestitureWasNotRequested,
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.confirmDivestiture(theObject, theAttributes, userSuppliedTag);
   }

   public void attributeOwnershipAcquisition(ObjectInstanceHandle theObject,
                                             AttributeHandleSet desiredAttributes,
                                             byte[] userSuppliedTag) throws
      AttributeNotPublished,
      ObjectClassNotPublished,
      FederateOwnsAttributes,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (desiredAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.attributeOwnershipAcquisition(theObject, desiredAttributes, userSuppliedTag);
   }

   public void attributeOwnershipAcquisitionIfAvailable(ObjectInstanceHandle theObject,
                                                        AttributeHandleSet desiredAttributes,
                                                        byte[] userSuppliedTag) throws
      AttributeAlreadyBeingAcquired,
      AttributeNotPublished,
      ObjectClassNotPublished,
      FederateOwnsAttributes,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (desiredAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.attributeOwnershipAcquisitionIfAvailable(theObject, desiredAttributes, userSuppliedTag);
   }

   public void attributeOwnershipReleaseDenied(ObjectInstanceHandle theObject,
                                               AttributeHandleSet theAttributes,
                                               byte[] userSuppliedTag) throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.attributeOwnershipReleaseDenied(theObject, theAttributes, userSuppliedTag);
   }

   public AttributeHandleSet attributeOwnershipDivestitureIfWanted(ObjectInstanceHandle theObject,
                                                                   AttributeHandleSet theAttributes,
                                                                   byte[] userSuppliedTag) throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      return _rtiAmbassadorClient.attributeOwnershipDivestitureIfWanted(theObject, theAttributes, userSuppliedTag);
   }

   public void cancelNegotiatedAttributeOwnershipDivestiture(ObjectInstanceHandle theObject,
                                                             AttributeHandleSet theAttributes) throws
      AttributeDivestitureWasNotRequested,
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.cancelNegotiatedAttributeOwnershipDivestiture(theObject, theAttributes);
   }

   public void cancelAttributeOwnershipAcquisition(ObjectInstanceHandle theObject,
                                                   AttributeHandleSet theAttributes) throws
      AttributeAcquisitionWasNotRequested,
      AttributeAlreadyOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.cancelAttributeOwnershipAcquisition(theObject, theAttributes);
   }

   public void queryAttributeOwnership(ObjectInstanceHandle theObject,
                                       AttributeHandleSet theAttributes) throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.queryAttributeOwnership(theObject, theAttributes);
   }

   public boolean isAttributeOwnedByFederate(ObjectInstanceHandle theObject,
                                             AttributeHandle theAttribute) throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      return _rtiAmbassadorClient.isAttributeOwnedByFederate(theObject, theAttribute);
   }

   public void enableTimeRegulation(LogicalTimeInterval<?> theLookahead) throws
      InvalidLookahead,
      InTimeAdvancingState,
      RequestForTimeRegulationPending,
      TimeRegulationAlreadyEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      checkIntervalType(theLookahead);
      _rtiAmbassadorClient.enableTimeRegulation(theLookahead);
   }

   private void checkIntervalType(LogicalTimeInterval<?> theLookahead) throws InvalidLookahead
   {
      // Types match if they implement the same basic time type, e.g. HLAinteger64Time.
      LogicalTimeInterval<?> logicalTimeInterval = _timeFactory.makeEpsilon();
      if (theLookahead instanceof HLAfloat64Interval && logicalTimeInterval instanceof HLAfloat64Interval) {
         return;
      }
      if (theLookahead instanceof HLAinteger64Interval && logicalTimeInterval instanceof HLAinteger64Interval) {
         return;
      }
      boolean sameClass = logicalTimeInterval.getClass().equals(theLookahead.getClass());
      if (!sameClass) {
         throw new InvalidLookahead("wrong time type");
      }
   }

   private void checkTimeType(LogicalTime<?, ?> theTime) throws InvalidLogicalTime
   {
      LogicalTime<?, ?> logicalTime = _timeFactory.makeInitial();
      if (theTime instanceof HLAfloat64Time && logicalTime instanceof HLAfloat64Time) {
         return;
      }
      if (theTime instanceof HLAinteger64Time && logicalTime instanceof HLAinteger64Time) {
         return;
      }
      boolean sameClass = logicalTime.getClass().equals(theTime.getClass());
      if (!sameClass) {
         throw new InvalidLogicalTime("wrong time type");
      }
   }

   public void disableTimeRegulation() throws
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.disableTimeRegulation();
   }

   public void enableTimeConstrained() throws
      InTimeAdvancingState,
      RequestForTimeConstrainedPending,
      TimeConstrainedAlreadyEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.enableTimeConstrained();
   }

   public void disableTimeConstrained() throws
      TimeConstrainedIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.disableTimeConstrained();
   }

   public void timeAdvanceRequest(LogicalTime<?, ?> theTime) throws
      LogicalTimeAlreadyPassed,
      InvalidLogicalTime,
      InTimeAdvancingState,
      RequestForTimeRegulationPending,
      RequestForTimeConstrainedPending,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      checkTimeType(theTime);
      _rtiAmbassadorClient.timeAdvanceRequest(theTime);
   }

   public void timeAdvanceRequestAvailable(LogicalTime<?, ?> theTime) throws
      LogicalTimeAlreadyPassed,
      InvalidLogicalTime,
      InTimeAdvancingState,
      RequestForTimeRegulationPending,
      RequestForTimeConstrainedPending,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      checkTimeType(theTime);
      _rtiAmbassadorClient.timeAdvanceRequestAvailable(theTime);
   }

   public void nextMessageRequest(LogicalTime<?, ?> theTime) throws
      LogicalTimeAlreadyPassed,
      InvalidLogicalTime,
      InTimeAdvancingState,
      RequestForTimeRegulationPending,
      RequestForTimeConstrainedPending,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      checkTimeType(theTime);
      _rtiAmbassadorClient.nextMessageRequest(theTime);
   }

   public void nextMessageRequestAvailable(LogicalTime<?, ?> theTime) throws
      LogicalTimeAlreadyPassed,
      InvalidLogicalTime,
      InTimeAdvancingState,
      RequestForTimeRegulationPending,
      RequestForTimeConstrainedPending,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      checkTimeType(theTime);
      _rtiAmbassadorClient.nextMessageRequestAvailable(theTime);
   }

   public void flushQueueRequest(LogicalTime<?, ?> theTime) throws
      LogicalTimeAlreadyPassed,
      InvalidLogicalTime,
      InTimeAdvancingState,
      RequestForTimeRegulationPending,
      RequestForTimeConstrainedPending,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      checkTimeType(theTime);
      _rtiAmbassadorClient.flushQueueRequest(theTime);
   }

   public void enableAsynchronousDelivery() throws
      AsynchronousDeliveryAlreadyEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.enableAsynchronousDelivery();
   }

   public void disableAsynchronousDelivery() throws
      AsynchronousDeliveryAlreadyDisabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.disableAsynchronousDelivery();
   }

   public TimeQueryReturn queryGALT()
      throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.queryGALT();
   }

   public LogicalTime<?, ?> queryLogicalTime()
      throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.queryLogicalTime();
   }

   public TimeQueryReturn queryLITS()
      throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.queryLITS();
   }

   public void modifyLookahead(LogicalTimeInterval<?> theLookahead) throws
      InvalidLookahead,
      InTimeAdvancingState,
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.modifyLookahead(theLookahead);
   }

   public LogicalTimeInterval<?> queryLookahead() throws
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      return _rtiAmbassadorClient.queryLookahead();
   }

   public void retract(MessageRetractionHandle theHandle) throws
      MessageCanNoLongerBeRetracted,
      InvalidMessageRetractionHandle,
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.retract(theHandle);
   }

   public void changeAttributeOrderType(ObjectInstanceHandle theObject,
                                        AttributeHandleSet theAttributes,
                                        OrderType theType) throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.changeAttributeOrderType(theObject, theAttributes, theType);
   }

   @Override
   public void changeDefaultAttributeOrderType(ObjectClassHandle theObjectClass,
                                               AttributeHandleSet theAttributes,
                                               OrderType theType) throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.changeDefaultAttributeOrderType(theObjectClass, theAttributes, theType);
   }

   public void changeInteractionOrderType(InteractionClassHandle theClass,
                                          OrderType theType) throws
      InteractionClassNotPublished,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.changeInteractionOrderType(theClass, theType);
   }

   public RegionHandle createRegion(DimensionHandleSet dimensions) throws
      InvalidDimensionHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      return _rtiAmbassadorClient.createRegion(dimensions);
   }

   public void commitRegionModifications(RegionHandleSet regions) throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.commitRegionModifications(regions);
   }

   public void deleteRegion(RegionHandle theRegion) throws
      RegionInUseForUpdateOrSubscription,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theRegion == null) {
         throw new InvalidRegion("null");
      }
      if (theRegion instanceof ClientConverter.ConveyedRegionHandleImpl) {
         throw new RegionNotCreatedByThisFederate("conveyed region");
      }
      _rtiAmbassadorClient.deleteRegion(theRegion);
   }

   public ObjectInstanceHandle registerObjectInstanceWithRegions(ObjectClassHandle theClass,
                                                                 AttributeSetRegionSetPairList attributesAndRegions)
      throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotPublished,
      ObjectClassNotPublished,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithRegions(theClass, attributesAndRegions);
   }

   public ObjectInstanceHandle registerObjectInstanceWithRegions(ObjectClassHandle theClass,
                                                                 AttributeSetRegionSetPairList attributesAndRegions,
                                                                 String theObject) throws
      ObjectInstanceNameInUse,
      ObjectInstanceNameNotReserved,
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotPublished,
      ObjectClassNotPublished,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithNameAndRegions(theClass, attributesAndRegions, theObject);
   }

   public void associateRegionsForUpdates(ObjectInstanceHandle theObject,
                                          AttributeSetRegionSetPairList attributesAndRegions) throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theObject == null) {
         throw new ObjectInstanceNotKnown("null");
      }
      _rtiAmbassadorClient.associateRegionsForUpdates(theObject, attributesAndRegions);
   }

   public void unassociateRegionsForUpdates(ObjectInstanceHandle theObject,
                                            AttributeSetRegionSetPairList attributesAndRegions) throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theObject == null) {
         throw new ObjectInstanceNotKnown("null");
      }
      _rtiAmbassadorClient.unassociateRegionsForUpdates(theObject, attributesAndRegions);
   }

   public void subscribeObjectClassAttributesWithRegions(ObjectClassHandle theClass,
                                                         AttributeSetRegionSetPairList attributesAndRegions) throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegions(theClass, attributesAndRegions, true);
   }

   public void subscribeObjectClassAttributesWithRegions(ObjectClassHandle theClass,
                                                         AttributeSetRegionSetPairList attributesAndRegions,
                                                         String updateRateDesignator) throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectClassNotDefined,
      InvalidUpdateRateDesignator,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegionsAndRate(theClass,
         attributesAndRegions,
         true,
         updateRateDesignator);
   }

   public void subscribeObjectClassAttributesPassivelyWithRegions(ObjectClassHandle theClass,
                                                                  AttributeSetRegionSetPairList attributesAndRegions)
      throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegions(theClass, attributesAndRegions, false);
   }

   public void subscribeObjectClassAttributesPassivelyWithRegions(ObjectClassHandle theClass,
                                                                  AttributeSetRegionSetPairList attributesAndRegions,
                                                                  String updateRateDesignator) throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectClassNotDefined,
      InvalidUpdateRateDesignator,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegionsAndRate(theClass,
         attributesAndRegions,
         false,
         updateRateDesignator);
   }

   public void unsubscribeObjectClassAttributesWithRegions(ObjectClassHandle theClass,
                                                           AttributeSetRegionSetPairList attributesAndRegions) throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeObjectClassAttributesWithRegions(theClass, attributesAndRegions);
   }

   public void subscribeInteractionClassWithRegions(InteractionClassHandle theClass,
                                                    RegionHandleSet regions) throws
      FederateServiceInvocationsAreBeingReportedViaMOM,
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeInteractionClassWithRegions(theClass, true, regions);
   }

   public void subscribeInteractionClassPassivelyWithRegions(InteractionClassHandle theClass,
                                                             RegionHandleSet regions) throws
      FederateServiceInvocationsAreBeingReportedViaMOM,
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeInteractionClassWithRegions(theClass, false, regions);
   }

   public void unsubscribeInteractionClassWithRegions(InteractionClassHandle theClass,
                                                      RegionHandleSet regions) throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeInteractionClassWithRegions(theClass, regions);
   }

   public void sendInteractionWithRegions(InteractionClassHandle theInteraction,
                                          ParameterHandleValueMap theParameters,
                                          RegionHandleSet regions,
                                          byte[] userSuppliedTag) throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theInteraction == null) {
         throw new NullPointerException("null");
      }
      _rtiAmbassadorClient.sendInteractionWithRegions(theInteraction, theParameters, regions, userSuppliedTag);
   }

   public MessageRetractionReturn sendInteractionWithRegions(InteractionClassHandle theInteraction,
                                                             ParameterHandleValueMap theParameters,
                                                             RegionHandleSet regions,
                                                             byte[] userSuppliedTag,
                                                             LogicalTime<?, ?> theTime) throws
      InvalidLogicalTime,
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theInteraction == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.sendInteractionWithRegionsAndTime(theInteraction,
         theParameters,
         regions,
         userSuppliedTag,
         theTime);
   }

   public void requestAttributeValueUpdateWithRegions(ObjectClassHandle theClass,
                                                      AttributeSetRegionSetPairList attributesAndRegions,
                                                      byte[] userSuppliedTag) throws
      InvalidRegionContext,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.requestAttributeValueUpdateWithRegions(theClass, attributesAndRegions, userSuppliedTag);
   }

   public ResignAction getAutomaticResignDirective() throws FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.getAutomaticResignDirective();
   }

   public void setAutomaticResignDirective(ResignAction resignAction)
      throws InvalidResignAction, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.setAutomaticResignDirective(resignAction);
   }

   public FederateHandle getFederateHandle(String theName)
      throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (theName == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.getFederateHandle(theName);
   }

   public String getFederateName(FederateHandle theHandle)
      throws InvalidFederateHandle, FederateHandleNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (theHandle == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.getFederateName(theHandle);
   }

   public ObjectClassHandle getObjectClassHandle(String theName)
      throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.getObjectClassHandle(theName);
   }

   public String getObjectClassName(ObjectClassHandle theHandle)
      throws InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (theHandle == null) {
         throw new InvalidObjectClassHandle("null");
      }
      return _rtiAmbassadorClient.getObjectClassName(theHandle);
   }

   public ObjectClassHandle getKnownObjectClassHandle(ObjectInstanceHandle theObject)
      throws ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (theObject == null) {
         throw new ObjectInstanceNotKnown("null");
      }
      return _rtiAmbassadorClient.getKnownObjectClassHandle(theObject);
   }

   public ObjectInstanceHandle getObjectInstanceHandle(String theName)
      throws ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.getObjectInstanceHandle(theName);
   }

   public String getObjectInstanceName(ObjectInstanceHandle theHandle)
      throws ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (theHandle == null) {
         throw new ObjectInstanceNotKnown("null");
      }
      return _rtiAmbassadorClient.getObjectInstanceName(theHandle);
   }

   public AttributeHandle getAttributeHandle(ObjectClassHandle whichClass,
                                             String theName)
      throws NameNotFound, InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (whichClass == null) {
         throw new InvalidObjectClassHandle("null");
      }
      return _rtiAmbassadorClient.getAttributeHandle(whichClass, theName);
   }

   public String getAttributeName(ObjectClassHandle whichClass,
                                  AttributeHandle theHandle) throws
      AttributeNotDefined,
      InvalidAttributeHandle,
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (whichClass == null) {
         throw new InvalidObjectClassHandle("null");
      }
      if (theHandle == null) {
         throw new InvalidAttributeHandle("null");
      }
      return _rtiAmbassadorClient.getAttributeName(whichClass, theHandle);
   }

   public double getUpdateRateValue(String updateRateDesignator)
      throws InvalidUpdateRateDesignator, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.getUpdateRateValue(updateRateDesignator);
   }

   public double getUpdateRateValueForAttribute(ObjectInstanceHandle theObject,
                                                AttributeHandle theAttribute)
      throws ObjectInstanceNotKnown, AttributeNotDefined, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.getUpdateRateValueForAttribute(theObject, theAttribute);
   }

   public InteractionClassHandle getInteractionClassHandle(String theName)
      throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.getInteractionClassHandle(theName);
   }

   public CompletableFuture<InteractionClassHandle> asyncGetInteractionClassHandle(String theName)
      throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.asyncGetInteractionClassHandle(theName);
   }

   public String getInteractionClassName(InteractionClassHandle theHandle)
      throws InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (theHandle == null) {
         throw new InvalidInteractionClassHandle("null");
      }
      return _rtiAmbassadorClient.getInteractionClassName(theHandle);
   }

   public ParameterHandle getParameterHandle(InteractionClassHandle whichClass,
                                             String theName)
      throws NameNotFound, InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (whichClass == null) {
         throw new InvalidInteractionClassHandle("null");
      }
      return _rtiAmbassadorClient.getParameterHandle(whichClass, theName);
   }

   public String getParameterName(InteractionClassHandle whichClass,
                                  ParameterHandle theHandle) throws
      InteractionParameterNotDefined,
      InvalidParameterHandle,
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (whichClass == null) {
         throw new InvalidInteractionClassHandle("null");
      }
      if (theHandle == null) {
         throw new InvalidParameterHandle("null");
      }
      return _rtiAmbassadorClient.getParameterName(whichClass, theHandle);
   }

   public OrderType getOrderType(String theName)
      throws InvalidOrderName, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (theName == null) {
         throw new InvalidOrderName("null");
      }
      return _rtiAmbassadorClient.getOrderType(theName);
   }

   public String getOrderName(OrderType theType)
      throws InvalidOrderType, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (theType == null) {
         throw new InvalidOrderType("null");
      }
      return _rtiAmbassadorClient.getOrderName(theType);
   }

   public TransportationTypeHandle getTransportationTypeHandle(String theName)
      throws InvalidTransportationName, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (theName == null) {
         throw new InvalidTransportationName("null");
      }
      return _rtiAmbassadorClient.getTransportationTypeHandle(theName);
   }

   public String getTransportationTypeName(TransportationTypeHandle theHandle)
      throws InvalidTransportationTypeHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (theHandle == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.getTransportationTypeName(theHandle);
   }

   public DimensionHandleSet getAvailableDimensionsForClassAttribute(ObjectClassHandle whichClass,
                                                                     AttributeHandle theHandle) throws
      AttributeNotDefined,
      InvalidAttributeHandle,
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (whichClass == null) {
         throw new InvalidObjectClassHandle("null");
      }
      if (theHandle == null) {
         throw new InvalidAttributeHandle("null");
      }
      return _rtiAmbassadorClient.getAvailableDimensionsForClassAttribute(whichClass, theHandle);
   }

   public DimensionHandleSet getAvailableDimensionsForInteractionClass(InteractionClassHandle theHandle)
      throws InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (theHandle == null) {
         throw new InvalidInteractionClassHandle("null");
      }
      return _rtiAmbassadorClient.getAvailableDimensionsForInteractionClass(theHandle);
   }

   public DimensionHandle getDimensionHandle(String theName)
      throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.getDimensionHandle(theName);
   }

   public String getDimensionName(DimensionHandle theHandle)
      throws InvalidDimensionHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (theHandle == null) {
         throw new InvalidDimensionHandle("null");
      }
      return _rtiAmbassadorClient.getDimensionName(theHandle);
   }

   public long getDimensionUpperBound(DimensionHandle theHandle)
      throws InvalidDimensionHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (theHandle == null) {
         throw new InvalidDimensionHandle("null");
      }
      return _rtiAmbassadorClient.getDimensionUpperBound(theHandle);
   }

   public DimensionHandleSet getDimensionHandleSet(RegionHandle region) throws
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (region == null) {
         throw new InvalidRegion("null");
      }
      if (region instanceof ClientConverter.ConveyedRegionHandleImpl) {
         ClientConverter.ConveyedRegionHandleImpl conveyedRegionHandle = (ClientConverter.ConveyedRegionHandleImpl) region;
         return conveyedRegionHandle.getDimensionHandleSet();
      }
      return _rtiAmbassadorClient.getDimensionHandleSet(region);
   }

   public RangeBounds getRangeBounds(RegionHandle region,
                                     DimensionHandle dimension) throws
      RegionDoesNotContainSpecifiedDimension,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (region == null) {
         throw new InvalidRegion("null");
      }
      if (region instanceof ClientConverter.ConveyedRegionHandleImpl) {
         ClientConverter.ConveyedRegionHandleImpl conveyedRegionHandle = (ClientConverter.ConveyedRegionHandleImpl) region;
         return conveyedRegionHandle.getRangeBounds(dimension);
      }
      return _rtiAmbassadorClient.getRangeBounds(region, dimension);
   }

   public void setRangeBounds(RegionHandle region,
                              DimensionHandle dimension,
                              RangeBounds bounds) throws
      InvalidRangeBound,
      RegionDoesNotContainSpecifiedDimension,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      if (region == null) {
         throw new InvalidRegion("null");
      }
      if (region instanceof ClientConverter.ConveyedRegionHandleImpl) {
         throw new RegionNotCreatedByThisFederate("conveyed region");
      }
      _rtiAmbassadorClient.setRangeBounds(region, dimension, bounds);
   }

   public long normalizeFederateHandle(FederateHandle federateHandle)
      throws InvalidFederateHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (federateHandle == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.normalizeFederateHandle(federateHandle);
   }

   @Override
   public long normalizeObjectClassHandle(ObjectClassHandle objectClassHandle)
      throws InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.normalizeObjectClassHandle(objectClassHandle);
   }

   @Override
   public long normalizeInteractionClassHandle(InteractionClassHandle interactionClassHandle)
      throws InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.normalizeInteractionClassHandle(interactionClassHandle);
   }

   @Override
   public long normalizeObjectInstanceHandle(ObjectInstanceHandle objectInstanceHandle)
      throws InvalidObjectInstanceHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.normalizeObjectInstanceHandle(objectInstanceHandle);
   }

   public long normalizeServiceGroup(ServiceGroup group)
      throws InvalidServiceGroup, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.normalizeServiceGroup(group);
   }

   public void enableObjectClassRelevanceAdvisorySwitch() throws
      ObjectClassRelevanceAdvisorySwitchIsOn,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.enableObjectClassRelevanceAdvisorySwitch();
   }

   public void disableObjectClassRelevanceAdvisorySwitch() throws
      ObjectClassRelevanceAdvisorySwitchIsOff,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.disableObjectClassRelevanceAdvisorySwitch();
   }

   public void enableAttributeRelevanceAdvisorySwitch() throws
      AttributeRelevanceAdvisorySwitchIsOn,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.enableAttributeRelevanceAdvisorySwitch();
   }

   public void disableAttributeRelevanceAdvisorySwitch() throws
      AttributeRelevanceAdvisorySwitchIsOff,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.disableAttributeRelevanceAdvisorySwitch();
   }

   public void enableAttributeScopeAdvisorySwitch() throws
      AttributeScopeAdvisorySwitchIsOn,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.enableAttributeScopeAdvisorySwitch();
   }

   public void disableAttributeScopeAdvisorySwitch() throws
      AttributeScopeAdvisorySwitchIsOff,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.disableAttributeScopeAdvisorySwitch();
   }

   public void enableInteractionRelevanceAdvisorySwitch() throws
      InteractionRelevanceAdvisorySwitchIsOn,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.enableInteractionRelevanceAdvisorySwitch();
   }

   public void disableInteractionRelevanceAdvisorySwitch() throws
      InteractionRelevanceAdvisorySwitchIsOff,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      _rtiAmbassadorClient.disableInteractionRelevanceAdvisorySwitch();
   }

   public boolean evokeCallback(double approximateMinimumTimeInSeconds)
      throws CallNotAllowedFromWithinCallback, RTIinternalError
   {
      return _rtiAmbassadorClient.evokeCallback(approximateMinimumTimeInSeconds);
   }

   public boolean evokeMultipleCallbacks(double approximateMinimumTimeInSeconds,
                                         double approximateMaximumTimeInSeconds)
      throws CallNotAllowedFromWithinCallback, RTIinternalError
   {
      return _rtiAmbassadorClient.evokeMultipleCallbacks(approximateMinimumTimeInSeconds,
         approximateMaximumTimeInSeconds);
   }

   public void enableCallbacks() throws SaveInProgress, RestoreInProgress, RTIinternalError
   {
      _rtiAmbassadorClient.enableCallbacks();
   }

   public void disableCallbacks() throws SaveInProgress, RestoreInProgress, RTIinternalError
   {
      _rtiAmbassadorClient.disableCallbacks();
   }

   public AttributeHandleFactory getAttributeHandleFactory() throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getAttributeHandleFactory();
   }

   public AttributeHandleSetFactory getAttributeHandleSetFactory() throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getAttributeHandleSetFactory();
   }

   public AttributeHandleValueMapFactory getAttributeHandleValueMapFactory()
      throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getAttributeHandleValueMapFactory();
   }

   public AttributeSetRegionSetPairListFactory getAttributeSetRegionSetPairListFactory()
      throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getAttributeSetRegionSetPairListFactory();
   }

   public DimensionHandleFactory getDimensionHandleFactory() throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getDimensionHandleFactory();
   }

   public DimensionHandleSetFactory getDimensionHandleSetFactory() throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getDimensionHandleSetFactory();
   }

   public FederateHandleFactory getFederateHandleFactory() throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getFederateHandleFactory();
   }

   public FederateHandleSetFactory getFederateHandleSetFactory() throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getFederateHandleSetFactory();
   }

   public InteractionClassHandleFactory getInteractionClassHandleFactory()
      throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getInteractionClassHandleFactory();
   }

   @Override
   public InteractionClassHandleSetFactory getInteractionClassHandleSetFactory()
      throws FederateNotExecutionMember, NotConnected
   {
      return null;
   }

   public ObjectClassHandleFactory getObjectClassHandleFactory() throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getObjectClassHandleFactory();
   }

   public ObjectInstanceHandleFactory getObjectInstanceHandleFactory() throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getObjectInstanceHandleFactory();
   }

   public ParameterHandleFactory getParameterHandleFactory() throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getParameterHandleFactory();
   }

   public ParameterHandleValueMapFactory getParameterHandleValueMapFactory()
      throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getParameterHandleValueMapFactory();
   }

   public RegionHandleSetFactory getRegionHandleSetFactory() throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getRegionHandleSetFactory();
   }

   public TransportationTypeHandleFactory getTransportationTypeHandleFactory()
      throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getTransportationTypeHandleFactory();
   }

   @Override
   public RegionHandleFactory getRegionHandleFactory() throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getRegionHandleFactory();
   }

   @Override
   public MessageRetractionHandleFactory getMessageRetractionHandleFactory()
      throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getMessageRetractionHandleFactory();
   }

   public String getHLAversion()
   {
      return "IEEE 1516-2010";
   }

   public LogicalTimeFactory<?, ?> getTimeFactory() throws FederateNotExecutionMember, NotConnected
   {
      if (_timeFactory != null) {
         return _timeFactory;
      } else {
         throw new FederateNotExecutionMember("No time factory available");
      }
   }

}
