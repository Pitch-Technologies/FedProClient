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

package se.pitch.oss.fedpro.client_evolved.hla;

import hla.rti1516_2025.fedpro.*;
import hla.rti1516e.exceptions.*;

import java.util.Collections;

// Exceptions are thrown using reflection so we get false warnings
@SuppressWarnings({"RedundantThrows", "rawtypes"})
public class RTIambassadorClient extends RTIambassadorClientEvolvedBase {

   public RTIambassadorClient(ClientConverter clientConverter)
   {
      super(clientConverter);
   }

   public void
   createFederationExecution(
         java.lang.String federationName,
         FomModule fomModule,
         java.lang.String logicalTimeImplementationName)
   throws
         CouldNotCreateLogicalTimeFactory,
         InconsistentFDD,
         ErrorReadingFDD,
         CouldNotOpenFDD,
         FederationExecutionAlreadyExists,
         NotConnected,
         RTIinternalError
   {
      createFederationExecutionWithModules(
            federationName,
            new FomModuleSet(Collections.singleton(fomModule)),
            logicalTimeImplementationName);
   }

   public void
   createFederationExecutionWithModules(
         java.lang.String federationName,
         FomModuleSet fomModules,
         java.lang.String logicalTimeImplementationName)
   throws
         CouldNotCreateLogicalTimeFactory,
         InconsistentFDD,
         ErrorReadingFDD,
         CouldNotOpenFDD,
         FederationExecutionAlreadyExists,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.CreateFederationExecutionWithModulesAndTimeRequest request;
      hla.rti1516_2025.fedpro.CreateFederationExecutionWithModulesAndTimeRequest.Builder builder =
            hla.rti1516_2025.fedpro.CreateFederationExecutionWithModulesAndTimeRequest.newBuilder();

      builder.setFederationName(_clientConverter.convertFromHla(federationName));
      builder.setFomModules(_clientConverter.convertFromHla(fomModules));
      builder.setLogicalTimeImplementationName(_clientConverter.convertFromHla(logicalTimeImplementationName));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setCreateFederationExecutionWithModulesAndTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCreateFederationExecutionWithModulesAndTimeResponse();
      // No return value
   }

   public void
   createFederationExecutionWithMIM(
         java.lang.String federationName,
         FomModuleSet fomModules,
         FomModule mimModule,
         java.lang.String logicalTimeImplementationName)
   throws
         CouldNotCreateLogicalTimeFactory,
         InconsistentFDD,
         ErrorReadingFDD,
         CouldNotOpenFDD,
         ErrorReadingMIM,
         CouldNotOpenMIM,
         DesignatorIsHLAstandardMIM,
         FederationExecutionAlreadyExists,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.CreateFederationExecutionWithMIMAndTimeRequest request;
      hla.rti1516_2025.fedpro.CreateFederationExecutionWithMIMAndTimeRequest.Builder builder =
            hla.rti1516_2025.fedpro.CreateFederationExecutionWithMIMAndTimeRequest.newBuilder();

      builder.setFederationName(_clientConverter.convertFromHla(federationName));
      builder.setFomModules(_clientConverter.convertFromHla(fomModules));
      builder.setMimModule(_clientConverter.convertFromHla(mimModule));
      builder.setLogicalTimeImplementationName(_clientConverter.convertFromHla(logicalTimeImplementationName));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setCreateFederationExecutionWithMIMAndTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCreateFederationExecutionWithMIMAndTimeResponse();
      // No return value
   }

   public void
   destroyFederationExecution(
         java.lang.String federationName)
   throws
         FederatesCurrentlyJoined,
         FederationExecutionDoesNotExist,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.DestroyFederationExecutionRequest request;
      hla.rti1516_2025.fedpro.DestroyFederationExecutionRequest.Builder builder =
            hla.rti1516_2025.fedpro.DestroyFederationExecutionRequest.newBuilder();

      builder.setFederationName(_clientConverter.convertFromHla(federationName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setDestroyFederationExecutionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDestroyFederationExecutionResponse();
      // No return value
   }

   public void
   listFederationExecutions(
   )
   throws
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.ListFederationExecutionsRequest request;
      request = hla.rti1516_2025.fedpro.ListFederationExecutionsRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setListFederationExecutionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasListFederationExecutionsResponse();
      // No return value
   }

   public JoinResult
   joinFederationExecution(
         java.lang.String federateType,
         java.lang.String federationName)
   throws
         CouldNotCreateLogicalTimeFactory,
         FederationExecutionDoesNotExist,
         SaveInProgress,
         RestoreInProgress,
         FederateAlreadyExecutionMember,
         NotConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      throwIfInCallback("joinFederationExecution");
      hla.rti1516_2025.fedpro.JoinFederationExecutionRequest request;
      hla.rti1516_2025.fedpro.JoinFederationExecutionRequest.Builder builder =
            hla.rti1516_2025.fedpro.JoinFederationExecutionRequest.newBuilder();

      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationName(_clientConverter.convertFromHla(federationName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setJoinFederationExecutionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionResponse();
      hla.rti1516_2025.fedpro.JoinFederationExecutionResponse response =
            callResponse.getJoinFederationExecutionResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public JoinResult
   joinFederationExecutionWithName(
         java.lang.String federateName,
         java.lang.String federateType,
         java.lang.String federationName)
   throws
         CouldNotCreateLogicalTimeFactory,
         FederateNameAlreadyInUse,
         FederationExecutionDoesNotExist,
         SaveInProgress,
         RestoreInProgress,
         FederateAlreadyExecutionMember,
         NotConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      throwIfInCallback("joinFederationExecutionWithName");
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameRequest request;
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameRequest.Builder builder =
            hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameRequest.newBuilder();

      builder.setFederateName(_clientConverter.convertFromHla(federateName));
      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationName(_clientConverter.convertFromHla(federationName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setJoinFederationExecutionWithNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionWithNameResponse();
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameResponse response =
            callResponse.getJoinFederationExecutionWithNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public JoinResult
   joinFederationExecutionWithNameAndModules(
         java.lang.String federateName,
         java.lang.String federateType,
         java.lang.String federationName,
         FomModuleSet additionalFomModules)
   throws
         CouldNotCreateLogicalTimeFactory,
         FederateNameAlreadyInUse,
         FederationExecutionDoesNotExist,
         InconsistentFDD,
         ErrorReadingFDD,
         CouldNotOpenFDD,
         SaveInProgress,
         RestoreInProgress,
         FederateAlreadyExecutionMember,
         NotConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      throwIfInCallback("joinFederationExecutionWithNameAndModules");
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameAndModulesRequest request;
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameAndModulesRequest.Builder builder =
            hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameAndModulesRequest.newBuilder();

      builder.setFederateName(_clientConverter.convertFromHla(federateName));
      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationName(_clientConverter.convertFromHla(federationName));
      builder.setAdditionalFomModules(_clientConverter.convertFromHla(additionalFomModules));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setJoinFederationExecutionWithNameAndModulesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionWithNameAndModulesResponse();
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameAndModulesResponse response =
            callResponse.getJoinFederationExecutionWithNameAndModulesResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public JoinResult
   joinFederationExecutionWithModules(
         java.lang.String federateType,
         java.lang.String federationName,
         FomModuleSet additionalFomModules)
   throws
         CouldNotCreateLogicalTimeFactory,
         FederationExecutionDoesNotExist,
         InconsistentFDD,
         ErrorReadingFDD,
         CouldNotOpenFDD,
         SaveInProgress,
         RestoreInProgress,
         FederateAlreadyExecutionMember,
         NotConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      throwIfInCallback("joinFederationExecutionWithModules");
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithModulesRequest request;
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithModulesRequest.Builder builder =
            hla.rti1516_2025.fedpro.JoinFederationExecutionWithModulesRequest.newBuilder();

      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationName(_clientConverter.convertFromHla(federationName));
      builder.setAdditionalFomModules(_clientConverter.convertFromHla(additionalFomModules));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setJoinFederationExecutionWithModulesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionWithModulesResponse();
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithModulesResponse response =
            callResponse.getJoinFederationExecutionWithModulesResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   resignFederationExecution(
         hla.rti1516e.ResignAction resignAction)
   throws
         InvalidResignAction,
         OwnershipAcquisitionPending,
         FederateOwnsAttributes,
         FederateNotExecutionMember,
         NotConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      throwIfInCallback("resignFederationExecution");
      hla.rti1516_2025.fedpro.ResignFederationExecutionRequest request;
      hla.rti1516_2025.fedpro.ResignFederationExecutionRequest.Builder builder =
            hla.rti1516_2025.fedpro.ResignFederationExecutionRequest.newBuilder();

      builder.setResignAction(_clientConverter.convertFromHla(resignAction));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setResignFederationExecutionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasResignFederationExecutionResponse();
      // No return value
   }

   public void
   registerFederationSynchronizationPoint(
         java.lang.String synchronizationPointLabel,
         byte[] userSuppliedTag)
   throws
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.RegisterFederationSynchronizationPointRequest request;
      hla.rti1516_2025.fedpro.RegisterFederationSynchronizationPointRequest.Builder builder =
            hla.rti1516_2025.fedpro.RegisterFederationSynchronizationPointRequest.newBuilder();

      builder.setSynchronizationPointLabel(_clientConverter.convertFromHla(synchronizationPointLabel));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setRegisterFederationSynchronizationPointRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterFederationSynchronizationPointResponse();
      // No return value
   }

   public void
   registerFederationSynchronizationPointWithSet(
         java.lang.String synchronizationPointLabel,
         byte[] userSuppliedTag,
         hla.rti1516e.FederateHandleSet synchronizationSet)
   throws
         InvalidFederateHandle,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.RegisterFederationSynchronizationPointWithSetRequest request;
      hla.rti1516_2025.fedpro.RegisterFederationSynchronizationPointWithSetRequest.Builder builder =
            hla.rti1516_2025.fedpro.RegisterFederationSynchronizationPointWithSetRequest.newBuilder();

      builder.setSynchronizationPointLabel(_clientConverter.convertFromHla(synchronizationPointLabel));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      builder.setSynchronizationSet(_clientConverter.convertFromHla(synchronizationSet));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setRegisterFederationSynchronizationPointWithSetRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterFederationSynchronizationPointWithSetResponse();
      // No return value
   }

   public void
   synchronizationPointAchieved(
         java.lang.String synchronizationPointLabel,
         boolean successIndicator)
   throws
         SynchronizationPointLabelNotAnnounced,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.SynchronizationPointAchievedRequest request;
      hla.rti1516_2025.fedpro.SynchronizationPointAchievedRequest.Builder builder =
            hla.rti1516_2025.fedpro.SynchronizationPointAchievedRequest.newBuilder();

      builder.setSynchronizationPointLabel(_clientConverter.convertFromHla(synchronizationPointLabel));
      builder.setSuccessfully(_clientConverter.convertFromHla(successIndicator));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSynchronizationPointAchievedRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSynchronizationPointAchievedResponse();
      // No return value
   }

   public void
   requestFederationSave(
         java.lang.String label)
   throws
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.RequestFederationSaveRequest request;
      hla.rti1516_2025.fedpro.RequestFederationSaveRequest.Builder builder =
            hla.rti1516_2025.fedpro.RequestFederationSaveRequest.newBuilder();

      builder.setLabel(_clientConverter.convertFromHla(label));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestFederationSaveRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRequestFederationSaveResponse();
      // No return value
   }

   public void
   requestFederationSaveWithTime(
         java.lang.String label,
         hla.rti1516e.LogicalTime theTime)
   throws
         LogicalTimeAlreadyPassed,
         InvalidLogicalTime,
         FederateUnableToUseTime,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.RequestFederationSaveWithTimeRequest request;
      hla.rti1516_2025.fedpro.RequestFederationSaveWithTimeRequest.Builder builder =
            hla.rti1516_2025.fedpro.RequestFederationSaveWithTimeRequest.newBuilder();

      builder.setLabel(_clientConverter.convertFromHla(label));
      builder.setTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestFederationSaveWithTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRequestFederationSaveWithTimeResponse();
      // No return value
   }

   public void
   federateSaveBegun(
   )
   throws
         SaveNotInitiated,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.FederateSaveBegunRequest request;
      request = hla.rti1516_2025.fedpro.FederateSaveBegunRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateSaveBegunRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasFederateSaveBegunResponse();
      // No return value
   }

   public void
   federateSaveComplete(
   )
   throws
         FederateHasNotBegunSave,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.FederateSaveCompleteRequest request;
      request = hla.rti1516_2025.fedpro.FederateSaveCompleteRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateSaveCompleteRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasFederateSaveCompleteResponse();
      // No return value
   }

   public void
   federateSaveNotComplete(
   )
   throws
         FederateHasNotBegunSave,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.FederateSaveNotCompleteRequest request;
      request = hla.rti1516_2025.fedpro.FederateSaveNotCompleteRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateSaveNotCompleteRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasFederateSaveNotCompleteResponse();
      // No return value
   }

   public void
   abortFederationSave(
   )
   throws
         SaveNotInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.AbortFederationSaveRequest request;
      request = hla.rti1516_2025.fedpro.AbortFederationSaveRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setAbortFederationSaveRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasAbortFederationSaveResponse();
      // No return value
   }

   public void
   queryFederationSaveStatus(
   )
   throws
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.QueryFederationSaveStatusRequest request;
      request = hla.rti1516_2025.fedpro.QueryFederationSaveStatusRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryFederationSaveStatusRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryFederationSaveStatusResponse();
      // No return value
   }

   public void
   requestFederationRestore(
         java.lang.String label)
   throws
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.RequestFederationRestoreRequest request;
      hla.rti1516_2025.fedpro.RequestFederationRestoreRequest.Builder builder =
            hla.rti1516_2025.fedpro.RequestFederationRestoreRequest.newBuilder();

      builder.setLabel(_clientConverter.convertFromHla(label));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestFederationRestoreRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRequestFederationRestoreResponse();
      // No return value
   }

   public void
   federateRestoreComplete(
   )
   throws
         RestoreNotRequested,
         SaveInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.FederateRestoreCompleteRequest request;
      request = hla.rti1516_2025.fedpro.FederateRestoreCompleteRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateRestoreCompleteRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasFederateRestoreCompleteResponse();
      // No return value
   }

   public void
   federateRestoreNotComplete(
   )
   throws
         RestoreNotRequested,
         SaveInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.FederateRestoreNotCompleteRequest request;
      request = hla.rti1516_2025.fedpro.FederateRestoreNotCompleteRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateRestoreNotCompleteRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasFederateRestoreNotCompleteResponse();
      // No return value
   }

   public void
   abortFederationRestore(
   )
   throws
         RestoreNotInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.AbortFederationRestoreRequest request;
      request = hla.rti1516_2025.fedpro.AbortFederationRestoreRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setAbortFederationRestoreRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasAbortFederationRestoreResponse();
      // No return value
   }

   public void
   queryFederationRestoreStatus(
   )
   throws
         SaveInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.QueryFederationRestoreStatusRequest request;
      request = hla.rti1516_2025.fedpro.QueryFederationRestoreStatusRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryFederationRestoreStatusRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryFederationRestoreStatusResponse();
      // No return value
   }

   public void
   publishObjectClassAttributes(
         hla.rti1516e.ObjectClassHandle theClass,
         hla.rti1516e.AttributeHandleSet attributeList)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.PublishObjectClassAttributesRequest request;
      hla.rti1516_2025.fedpro.PublishObjectClassAttributesRequest.Builder builder =
            hla.rti1516_2025.fedpro.PublishObjectClassAttributesRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributes(_clientConverter.convertFromHla(attributeList));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setPublishObjectClassAttributesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasPublishObjectClassAttributesResponse();
      // No return value
   }

   public void
   unpublishObjectClass(
         hla.rti1516e.ObjectClassHandle theClass)
   throws
         OwnershipAcquisitionPending,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.UnpublishObjectClassRequest request;
      hla.rti1516_2025.fedpro.UnpublishObjectClassRequest.Builder builder =
            hla.rti1516_2025.fedpro.UnpublishObjectClassRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishObjectClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnpublishObjectClassResponse();
      // No return value
   }

   public void
   unpublishObjectClassAttributes(
         hla.rti1516e.ObjectClassHandle theClass,
         hla.rti1516e.AttributeHandleSet attributeList)
   throws
         OwnershipAcquisitionPending,
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.UnpublishObjectClassAttributesRequest request;
      hla.rti1516_2025.fedpro.UnpublishObjectClassAttributesRequest.Builder builder =
            hla.rti1516_2025.fedpro.UnpublishObjectClassAttributesRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributes(_clientConverter.convertFromHla(attributeList));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishObjectClassAttributesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnpublishObjectClassAttributesResponse();
      // No return value
   }

   public void
   publishInteractionClass(
         hla.rti1516e.InteractionClassHandle theInteraction)
   throws
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.PublishInteractionClassRequest request;
      hla.rti1516_2025.fedpro.PublishInteractionClassRequest.Builder builder =
            hla.rti1516_2025.fedpro.PublishInteractionClassRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theInteraction));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setPublishInteractionClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasPublishInteractionClassResponse();
      // No return value
   }

   public void
   unpublishInteractionClass(
         hla.rti1516e.InteractionClassHandle theInteraction)
   throws
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.UnpublishInteractionClassRequest request;
      hla.rti1516_2025.fedpro.UnpublishInteractionClassRequest.Builder builder =
            hla.rti1516_2025.fedpro.UnpublishInteractionClassRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theInteraction));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishInteractionClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnpublishInteractionClassResponse();
      // No return value
   }

   public void
   subscribeObjectClassAttributes(
         hla.rti1516e.ObjectClassHandle theClass,
         hla.rti1516e.AttributeHandleSet attributeList)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesRequest request;
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesRequest.Builder builder =
            hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributes(_clientConverter.convertFromHla(attributeList));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeObjectClassAttributesResponse();
      // No return value
   }

   public void
   subscribeObjectClassAttributesWithRate(
         hla.rti1516e.ObjectClassHandle theClass,
         hla.rti1516e.AttributeHandleSet attributeList,
         java.lang.String updateRateDesignator)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         InvalidUpdateRateDesignator,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRateRequest request;
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRateRequest.Builder builder =
            hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRateRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributes(_clientConverter.convertFromHla(attributeList));
      builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setSubscribeObjectClassAttributesWithRateRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeObjectClassAttributesWithRateResponse();
      // No return value
   }

   public void
   subscribeObjectClassAttributesPassively(
         hla.rti1516e.ObjectClassHandle theClass,
         hla.rti1516e.AttributeHandleSet attributeList)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesPassivelyRequest request;
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesPassivelyRequest.Builder builder =
            hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesPassivelyRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributes(_clientConverter.convertFromHla(attributeList));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setSubscribeObjectClassAttributesPassivelyRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeObjectClassAttributesPassivelyResponse();
      // No return value
   }

   public void
   subscribeObjectClassAttributesPassivelyWithRate(
         hla.rti1516e.ObjectClassHandle theClass,
         hla.rti1516e.AttributeHandleSet attributeList,
         java.lang.String updateRateDesignator)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         InvalidUpdateRateDesignator,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesPassivelyWithRateRequest request;
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesPassivelyWithRateRequest.Builder builder =
            hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesPassivelyWithRateRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributes(_clientConverter.convertFromHla(attributeList));
      builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setSubscribeObjectClassAttributesPassivelyWithRateRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeObjectClassAttributesPassivelyWithRateResponse();
      // No return value
   }

   public void
   unsubscribeObjectClass(
         hla.rti1516e.ObjectClassHandle theClass)
   throws
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassRequest request;
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassRequest.Builder builder =
            hla.rti1516_2025.fedpro.UnsubscribeObjectClassRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnsubscribeObjectClassResponse();
      // No return value
   }

   public void
   unsubscribeObjectClassAttributes(
         hla.rti1516e.ObjectClassHandle theClass,
         hla.rti1516e.AttributeHandleSet attributeList)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassAttributesRequest request;
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassAttributesRequest.Builder builder =
            hla.rti1516_2025.fedpro.UnsubscribeObjectClassAttributesRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributes(_clientConverter.convertFromHla(attributeList));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassAttributesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnsubscribeObjectClassAttributesResponse();
      // No return value
   }

   public void
   subscribeInteractionClass(
         hla.rti1516e.InteractionClassHandle theClass)
   throws
         FederateServiceInvocationsAreBeingReportedViaMOM,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.SubscribeInteractionClassRequest request;
      hla.rti1516_2025.fedpro.SubscribeInteractionClassRequest.Builder builder =
            hla.rti1516_2025.fedpro.SubscribeInteractionClassRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theClass));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeInteractionClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeInteractionClassResponse();
      // No return value
   }

   public void
   subscribeInteractionClassPassively(
         hla.rti1516e.InteractionClassHandle theClass)
   throws
         FederateServiceInvocationsAreBeingReportedViaMOM,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.SubscribeInteractionClassPassivelyRequest request;
      hla.rti1516_2025.fedpro.SubscribeInteractionClassPassivelyRequest.Builder builder =
            hla.rti1516_2025.fedpro.SubscribeInteractionClassPassivelyRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theClass));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeInteractionClassPassivelyRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeInteractionClassPassivelyResponse();
      // No return value
   }

   public void
   unsubscribeInteractionClass(
         hla.rti1516e.InteractionClassHandle theClass)
   throws
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.UnsubscribeInteractionClassRequest request;
      hla.rti1516_2025.fedpro.UnsubscribeInteractionClassRequest.Builder builder =
            hla.rti1516_2025.fedpro.UnsubscribeInteractionClassRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theClass));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeInteractionClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnsubscribeInteractionClassResponse();
      // No return value
   }

   public void
   reserveObjectInstanceName(
         java.lang.String theObjectName)
   throws
         IllegalName,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.ReserveObjectInstanceNameRequest request;
      hla.rti1516_2025.fedpro.ReserveObjectInstanceNameRequest.Builder builder =
            hla.rti1516_2025.fedpro.ReserveObjectInstanceNameRequest.newBuilder();

      builder.setObjectInstanceName(_clientConverter.convertFromHla(theObjectName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReserveObjectInstanceNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasReserveObjectInstanceNameResponse();
      // No return value
   }

   public void
   releaseObjectInstanceName(
         java.lang.String theObjectInstanceName)
   throws
         ObjectInstanceNameNotReserved,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.ReleaseObjectInstanceNameRequest request;
      hla.rti1516_2025.fedpro.ReleaseObjectInstanceNameRequest.Builder builder =
            hla.rti1516_2025.fedpro.ReleaseObjectInstanceNameRequest.newBuilder();

      builder.setObjectInstanceName(_clientConverter.convertFromHla(theObjectInstanceName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReleaseObjectInstanceNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasReleaseObjectInstanceNameResponse();
      // No return value
   }

   public void
   reserveMultipleObjectInstanceName(
         java.util.Set<java.lang.String> theObjectNames)
   throws
         IllegalName,
         NameSetWasEmpty,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.ReserveMultipleObjectInstanceNamesRequest request;
      hla.rti1516_2025.fedpro.ReserveMultipleObjectInstanceNamesRequest.Builder builder =
            hla.rti1516_2025.fedpro.ReserveMultipleObjectInstanceNamesRequest.newBuilder();

      builder.addAllObjectInstanceNames(_clientConverter.convertFromHla(theObjectNames));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReserveMultipleObjectInstanceNamesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasReserveMultipleObjectInstanceNamesResponse();
      // No return value
   }

   public void
   releaseMultipleObjectInstanceName(
         java.util.Set<java.lang.String> theObjectNames)
   throws
         ObjectInstanceNameNotReserved,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.ReleaseMultipleObjectInstanceNamesRequest request;
      hla.rti1516_2025.fedpro.ReleaseMultipleObjectInstanceNamesRequest.Builder builder =
            hla.rti1516_2025.fedpro.ReleaseMultipleObjectInstanceNamesRequest.newBuilder();

      builder.addAllObjectInstanceNames(_clientConverter.convertFromHla(theObjectNames));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReleaseMultipleObjectInstanceNamesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasReleaseMultipleObjectInstanceNamesResponse();
      // No return value
   }

   public hla.rti1516e.ObjectInstanceHandle
   registerObjectInstance(
         hla.rti1516e.ObjectClassHandle theClass)
   throws
         ObjectClassNotPublished,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.RegisterObjectInstanceRequest request;
      hla.rti1516_2025.fedpro.RegisterObjectInstanceRequest.Builder builder =
            hla.rti1516_2025.fedpro.RegisterObjectInstanceRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterObjectInstanceRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterObjectInstanceResponse();
      hla.rti1516_2025.fedpro.RegisterObjectInstanceResponse response = callResponse.getRegisterObjectInstanceResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.ObjectInstanceHandle
   registerObjectInstanceWithName(
         hla.rti1516e.ObjectClassHandle theClass,
         java.lang.String theObjectName)
   throws
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
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameRequest request;
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameRequest.Builder builder =
            hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setObjectInstanceName(_clientConverter.convertFromHla(theObjectName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterObjectInstanceWithNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterObjectInstanceWithNameResponse();
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameResponse response =
            callResponse.getRegisterObjectInstanceWithNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   updateAttributeValues(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandleValueMap theAttributes,
         byte[] userSuppliedTag)
   throws
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.UpdateAttributeValuesRequest request;
      hla.rti1516_2025.fedpro.UpdateAttributeValuesRequest.Builder builder =
            hla.rti1516_2025.fedpro.UpdateAttributeValuesRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributeValues(_clientConverter.convertFromHla(theAttributes));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUpdateAttributeValuesRequest(request).build();
      if (_asyncUpdates) {
         countAsyncUpdateForStats();
         doAsyncHlaCall(callRequest);
         return;
      }
      countSyncUpdateForStats();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUpdateAttributeValuesResponse();
      // No return value
   }

   public hla.rti1516e.MessageRetractionReturn
   updateAttributeValuesWithTime(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandleValueMap theAttributes,
         byte[] userSuppliedTag,
         hla.rti1516e.LogicalTime theTime)
   throws
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
      hla.rti1516_2025.fedpro.UpdateAttributeValuesWithTimeRequest request;
      hla.rti1516_2025.fedpro.UpdateAttributeValuesWithTimeRequest.Builder builder =
            hla.rti1516_2025.fedpro.UpdateAttributeValuesWithTimeRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributeValues(_clientConverter.convertFromHla(theAttributes));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      builder.setTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUpdateAttributeValuesWithTimeRequest(request).build();
      countSyncUpdateForStats();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUpdateAttributeValuesWithTimeResponse();
      hla.rti1516_2025.fedpro.UpdateAttributeValuesWithTimeResponse response =
            callResponse.getUpdateAttributeValuesWithTimeResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   sendInteraction(
         hla.rti1516e.InteractionClassHandle theInteraction,
         hla.rti1516e.ParameterHandleValueMap theParameters,
         byte[] userSuppliedTag)
   throws
         InteractionClassNotPublished,
         InteractionParameterNotDefined,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.SendInteractionRequest request;
      hla.rti1516_2025.fedpro.SendInteractionRequest.Builder builder =
            hla.rti1516_2025.fedpro.SendInteractionRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theInteraction));
      builder.setParameterValues(_clientConverter.convertFromHla(theParameters));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionRequest(request).build();
      if (_asyncUpdates) {
         countAsyncSentInteractionForStats();
         doAsyncHlaCall(callRequest);
         return;
      }
      countSyncSentInteractionForStats();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSendInteractionResponse();
      // No return value
   }

   public hla.rti1516e.MessageRetractionReturn
   sendInteractionWithTime(
         hla.rti1516e.InteractionClassHandle theInteraction,
         hla.rti1516e.ParameterHandleValueMap theParameters,
         byte[] userSuppliedTag,
         hla.rti1516e.LogicalTime theTime)
   throws
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
      hla.rti1516_2025.fedpro.SendInteractionWithTimeRequest request;
      hla.rti1516_2025.fedpro.SendInteractionWithTimeRequest.Builder builder =
            hla.rti1516_2025.fedpro.SendInteractionWithTimeRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theInteraction));
      builder.setParameterValues(_clientConverter.convertFromHla(theParameters));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      builder.setTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionWithTimeRequest(request).build();
      countSyncSentInteractionForStats();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSendInteractionWithTimeResponse();
      hla.rti1516_2025.fedpro.SendInteractionWithTimeResponse response =
            callResponse.getSendInteractionWithTimeResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   deleteObjectInstance(
         hla.rti1516e.ObjectInstanceHandle objectHandle,
         byte[] userSuppliedTag)
   throws
         DeletePrivilegeNotHeld,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.DeleteObjectInstanceRequest request;
      hla.rti1516_2025.fedpro.DeleteObjectInstanceRequest.Builder builder =
            hla.rti1516_2025.fedpro.DeleteObjectInstanceRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(objectHandle));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setDeleteObjectInstanceRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDeleteObjectInstanceResponse();
      // No return value
   }

   public hla.rti1516e.MessageRetractionReturn
   deleteObjectInstanceWithTime(
         hla.rti1516e.ObjectInstanceHandle objectHandle,
         byte[] userSuppliedTag,
         hla.rti1516e.LogicalTime theTime)
   throws
         InvalidLogicalTime,
         DeletePrivilegeNotHeld,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.DeleteObjectInstanceWithTimeRequest request;
      hla.rti1516_2025.fedpro.DeleteObjectInstanceWithTimeRequest.Builder builder =
            hla.rti1516_2025.fedpro.DeleteObjectInstanceWithTimeRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(objectHandle));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      builder.setTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setDeleteObjectInstanceWithTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDeleteObjectInstanceWithTimeResponse();
      hla.rti1516_2025.fedpro.DeleteObjectInstanceWithTimeResponse response =
            callResponse.getDeleteObjectInstanceWithTimeResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   localDeleteObjectInstance(
         hla.rti1516e.ObjectInstanceHandle objectHandle)
   throws
         OwnershipAcquisitionPending,
         FederateOwnsAttributes,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.LocalDeleteObjectInstanceRequest request;
      hla.rti1516_2025.fedpro.LocalDeleteObjectInstanceRequest.Builder builder =
            hla.rti1516_2025.fedpro.LocalDeleteObjectInstanceRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(objectHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setLocalDeleteObjectInstanceRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasLocalDeleteObjectInstanceResponse();
      // No return value
   }

   public void
   requestInstanceAttributeValueUpdate(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandleSet theAttributes,
         byte[] userSuppliedTag)
   throws
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.RequestInstanceAttributeValueUpdateRequest request;
      hla.rti1516_2025.fedpro.RequestInstanceAttributeValueUpdateRequest.Builder builder =
            hla.rti1516_2025.fedpro.RequestInstanceAttributeValueUpdateRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributes(_clientConverter.convertFromHla(theAttributes));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestInstanceAttributeValueUpdateRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRequestInstanceAttributeValueUpdateResponse();
      // No return value
   }

   public void
   requestClassAttributeValueUpdate(
         hla.rti1516e.ObjectClassHandle theClass,
         hla.rti1516e.AttributeHandleSet theAttributes,
         byte[] userSuppliedTag)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.RequestClassAttributeValueUpdateRequest request;
      hla.rti1516_2025.fedpro.RequestClassAttributeValueUpdateRequest.Builder builder =
            hla.rti1516_2025.fedpro.RequestClassAttributeValueUpdateRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributes(_clientConverter.convertFromHla(theAttributes));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestClassAttributeValueUpdateRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRequestClassAttributeValueUpdateResponse();
      // No return value
   }

   public void
   requestAttributeTransportationTypeChange(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandleSet theAttributes,
         hla.rti1516e.TransportationTypeHandle theType)
   throws
         AttributeAlreadyBeingChanged,
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         InvalidTransportationType,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.RequestAttributeTransportationTypeChangeRequest request;
      hla.rti1516_2025.fedpro.RequestAttributeTransportationTypeChangeRequest.Builder builder =
            hla.rti1516_2025.fedpro.RequestAttributeTransportationTypeChangeRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributes(_clientConverter.convertFromHla(theAttributes));
      builder.setTransportationType(_clientConverter.convertFromHla(theType));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setRequestAttributeTransportationTypeChangeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRequestAttributeTransportationTypeChangeResponse();
      // No return value
   }

   public void
   queryAttributeTransportationType(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandle theAttribute)
   throws
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.QueryAttributeTransportationTypeRequest request;
      hla.rti1516_2025.fedpro.QueryAttributeTransportationTypeRequest.Builder builder =
            hla.rti1516_2025.fedpro.QueryAttributeTransportationTypeRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttribute(_clientConverter.convertFromHla(theAttribute));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setQueryAttributeTransportationTypeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryAttributeTransportationTypeResponse();
      // No return value
   }

   public void
   requestInteractionTransportationTypeChange(
         hla.rti1516e.InteractionClassHandle theClass,
         hla.rti1516e.TransportationTypeHandle theType)
   throws
         InteractionClassAlreadyBeingChanged,
         InteractionClassNotPublished,
         InteractionClassNotDefined,
         InvalidTransportationType,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.RequestInteractionTransportationTypeChangeRequest request;
      hla.rti1516_2025.fedpro.RequestInteractionTransportationTypeChangeRequest.Builder builder =
            hla.rti1516_2025.fedpro.RequestInteractionTransportationTypeChangeRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theClass));
      builder.setTransportationType(_clientConverter.convertFromHla(theType));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setRequestInteractionTransportationTypeChangeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRequestInteractionTransportationTypeChangeResponse();
      // No return value
   }

   public void
   queryInteractionTransportationType(
         hla.rti1516e.FederateHandle theFederate,
         hla.rti1516e.InteractionClassHandle theInteraction)
   throws
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.QueryInteractionTransportationTypeRequest request;
      hla.rti1516_2025.fedpro.QueryInteractionTransportationTypeRequest.Builder builder =
            hla.rti1516_2025.fedpro.QueryInteractionTransportationTypeRequest.newBuilder();

      builder.setFederate(_clientConverter.convertFromHla(theFederate));
      builder.setInteractionClass(_clientConverter.convertFromHla(theInteraction));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setQueryInteractionTransportationTypeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryInteractionTransportationTypeResponse();
      // No return value
   }

   public void
   unconditionalAttributeOwnershipDivestiture(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandleSet theAttributes)
   throws
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.UnconditionalAttributeOwnershipDivestitureRequest request;
      hla.rti1516_2025.fedpro.UnconditionalAttributeOwnershipDivestitureRequest.Builder builder =
            hla.rti1516_2025.fedpro.UnconditionalAttributeOwnershipDivestitureRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributes(_clientConverter.convertFromHla(theAttributes));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setUnconditionalAttributeOwnershipDivestitureRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnconditionalAttributeOwnershipDivestitureResponse();
      // No return value
   }

   public void
   negotiatedAttributeOwnershipDivestiture(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandleSet theAttributes,
         byte[] userSuppliedTag)
   throws
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
      hla.rti1516_2025.fedpro.NegotiatedAttributeOwnershipDivestitureRequest request;
      hla.rti1516_2025.fedpro.NegotiatedAttributeOwnershipDivestitureRequest.Builder builder =
            hla.rti1516_2025.fedpro.NegotiatedAttributeOwnershipDivestitureRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributes(_clientConverter.convertFromHla(theAttributes));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setNegotiatedAttributeOwnershipDivestitureRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasNegotiatedAttributeOwnershipDivestitureResponse();
      // No return value
   }

   public void
   confirmDivestiture(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandleSet theAttributes,
         byte[] userSuppliedTag)
   throws
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
      hla.rti1516_2025.fedpro.ConfirmDivestitureRequest request;
      hla.rti1516_2025.fedpro.ConfirmDivestitureRequest.Builder builder =
            hla.rti1516_2025.fedpro.ConfirmDivestitureRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setConfirmedAttributes(_clientConverter.convertFromHla(theAttributes));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setConfirmDivestitureRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasConfirmDivestitureResponse();
      // No return value
   }

   public void
   attributeOwnershipAcquisition(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandleSet desiredAttributes,
         byte[] userSuppliedTag)
   throws
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
      hla.rti1516_2025.fedpro.AttributeOwnershipAcquisitionRequest request;
      hla.rti1516_2025.fedpro.AttributeOwnershipAcquisitionRequest.Builder builder =
            hla.rti1516_2025.fedpro.AttributeOwnershipAcquisitionRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setDesiredAttributes(_clientConverter.convertFromHla(desiredAttributes));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAttributeOwnershipAcquisitionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasAttributeOwnershipAcquisitionResponse();
      // No return value
   }

   public void
   attributeOwnershipAcquisitionIfAvailable(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandleSet desiredAttributes)
   throws
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
      hla.rti1516_2025.fedpro.AttributeOwnershipAcquisitionIfAvailableRequest request;
      hla.rti1516_2025.fedpro.AttributeOwnershipAcquisitionIfAvailableRequest.Builder builder =
            hla.rti1516_2025.fedpro.AttributeOwnershipAcquisitionIfAvailableRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setDesiredAttributes(_clientConverter.convertFromHla(desiredAttributes));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setAttributeOwnershipAcquisitionIfAvailableRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasAttributeOwnershipAcquisitionIfAvailableResponse();
      // No return value
   }

   public void
   attributeOwnershipReleaseDenied(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandleSet theAttributes)
   throws
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.AttributeOwnershipReleaseDeniedRequest request;
      hla.rti1516_2025.fedpro.AttributeOwnershipReleaseDeniedRequest.Builder builder =
            hla.rti1516_2025.fedpro.AttributeOwnershipReleaseDeniedRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributes(_clientConverter.convertFromHla(theAttributes));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAttributeOwnershipReleaseDeniedRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasAttributeOwnershipReleaseDeniedResponse();
      // No return value
   }

   public hla.rti1516e.AttributeHandleSet
   attributeOwnershipDivestitureIfWanted(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandleSet theAttributes)
   throws
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.AttributeOwnershipDivestitureIfWantedRequest request;
      hla.rti1516_2025.fedpro.AttributeOwnershipDivestitureIfWantedRequest.Builder builder =
            hla.rti1516_2025.fedpro.AttributeOwnershipDivestitureIfWantedRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributes(_clientConverter.convertFromHla(theAttributes));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setAttributeOwnershipDivestitureIfWantedRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasAttributeOwnershipDivestitureIfWantedResponse();
      hla.rti1516_2025.fedpro.AttributeOwnershipDivestitureIfWantedResponse response =
            callResponse.getAttributeOwnershipDivestitureIfWantedResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   cancelNegotiatedAttributeOwnershipDivestiture(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandleSet theAttributes)
   throws
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
      hla.rti1516_2025.fedpro.CancelNegotiatedAttributeOwnershipDivestitureRequest request;
      hla.rti1516_2025.fedpro.CancelNegotiatedAttributeOwnershipDivestitureRequest.Builder builder =
            hla.rti1516_2025.fedpro.CancelNegotiatedAttributeOwnershipDivestitureRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributes(_clientConverter.convertFromHla(theAttributes));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setCancelNegotiatedAttributeOwnershipDivestitureRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCancelNegotiatedAttributeOwnershipDivestitureResponse();
      // No return value
   }

   public void
   cancelAttributeOwnershipAcquisition(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandleSet theAttributes)
   throws
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
      hla.rti1516_2025.fedpro.CancelAttributeOwnershipAcquisitionRequest request;
      hla.rti1516_2025.fedpro.CancelAttributeOwnershipAcquisitionRequest.Builder builder =
            hla.rti1516_2025.fedpro.CancelAttributeOwnershipAcquisitionRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributes(_clientConverter.convertFromHla(theAttributes));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCancelAttributeOwnershipAcquisitionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCancelAttributeOwnershipAcquisitionResponse();
      // No return value
   }

   public void
   queryAttributeOwnership(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandle theAttribute)
   throws
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.QueryAttributeOwnershipRequest request;
      hla.rti1516_2025.fedpro.QueryAttributeOwnershipRequest.Builder builder =
            hla.rti1516_2025.fedpro.QueryAttributeOwnershipRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributes(_clientConverter.convertFromHlaToSet(theAttribute));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setQueryAttributeOwnershipRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryAttributeOwnershipResponse();
      // No return value
   }

   public boolean
   isAttributeOwnedByFederate(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandle theAttribute)
   throws
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.IsAttributeOwnedByFederateRequest request;
      hla.rti1516_2025.fedpro.IsAttributeOwnedByFederateRequest.Builder builder =
            hla.rti1516_2025.fedpro.IsAttributeOwnedByFederateRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttribute(_clientConverter.convertFromHla(theAttribute));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setIsAttributeOwnedByFederateRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasIsAttributeOwnedByFederateResponse();
      hla.rti1516_2025.fedpro.IsAttributeOwnedByFederateResponse response =
            callResponse.getIsAttributeOwnedByFederateResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   enableTimeRegulation(
         hla.rti1516e.LogicalTimeInterval theLookahead)
   throws
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
      hla.rti1516_2025.fedpro.EnableTimeRegulationRequest request;
      hla.rti1516_2025.fedpro.EnableTimeRegulationRequest.Builder builder =
            hla.rti1516_2025.fedpro.EnableTimeRegulationRequest.newBuilder();

      builder.setLookahead(_clientConverter.convertFromHla(theLookahead));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setEnableTimeRegulationRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasEnableTimeRegulationResponse();
      // No return value
   }

   public void
   disableTimeRegulation(
   )
   throws
         TimeRegulationIsNotEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.DisableTimeRegulationRequest request;
      request = hla.rti1516_2025.fedpro.DisableTimeRegulationRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableTimeRegulationRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDisableTimeRegulationResponse();
      // No return value
   }

   public void
   enableTimeConstrained(
   )
   throws
         InTimeAdvancingState,
         RequestForTimeConstrainedPending,
         TimeConstrainedAlreadyEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.EnableTimeConstrainedRequest request;
      request = hla.rti1516_2025.fedpro.EnableTimeConstrainedRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setEnableTimeConstrainedRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasEnableTimeConstrainedResponse();
      // No return value
   }

   public void
   disableTimeConstrained(
   )
   throws
         TimeConstrainedIsNotEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.DisableTimeConstrainedRequest request;
      request = hla.rti1516_2025.fedpro.DisableTimeConstrainedRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableTimeConstrainedRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDisableTimeConstrainedResponse();
      // No return value
   }

   public void
   timeAdvanceRequest(
         hla.rti1516e.LogicalTime theTime)
   throws
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
      hla.rti1516_2025.fedpro.TimeAdvanceRequestRequest request;
      hla.rti1516_2025.fedpro.TimeAdvanceRequestRequest.Builder builder =
            hla.rti1516_2025.fedpro.TimeAdvanceRequestRequest.newBuilder();

      builder.setTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setTimeAdvanceRequestRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasTimeAdvanceRequestResponse();
      // No return value
   }

   public void
   timeAdvanceRequestAvailable(
         hla.rti1516e.LogicalTime theTime)
   throws
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
      hla.rti1516_2025.fedpro.TimeAdvanceRequestAvailableRequest request;
      hla.rti1516_2025.fedpro.TimeAdvanceRequestAvailableRequest.Builder builder =
            hla.rti1516_2025.fedpro.TimeAdvanceRequestAvailableRequest.newBuilder();

      builder.setTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setTimeAdvanceRequestAvailableRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasTimeAdvanceRequestAvailableResponse();
      // No return value
   }

   public void
   nextMessageRequest(
         hla.rti1516e.LogicalTime theTime)
   throws
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
      hla.rti1516_2025.fedpro.NextMessageRequestRequest request;
      hla.rti1516_2025.fedpro.NextMessageRequestRequest.Builder builder =
            hla.rti1516_2025.fedpro.NextMessageRequestRequest.newBuilder();

      builder.setTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNextMessageRequestRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasNextMessageRequestResponse();
      // No return value
   }

   public void
   nextMessageRequestAvailable(
         hla.rti1516e.LogicalTime theTime)
   throws
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
      hla.rti1516_2025.fedpro.NextMessageRequestAvailableRequest request;
      hla.rti1516_2025.fedpro.NextMessageRequestAvailableRequest.Builder builder =
            hla.rti1516_2025.fedpro.NextMessageRequestAvailableRequest.newBuilder();

      builder.setTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNextMessageRequestAvailableRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasNextMessageRequestAvailableResponse();
      // No return value
   }

   public void
   flushQueueRequest(
         hla.rti1516e.LogicalTime theTime)
   throws
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
      hla.rti1516_2025.fedpro.FlushQueueRequestRequest request;
      hla.rti1516_2025.fedpro.FlushQueueRequestRequest.Builder builder =
            hla.rti1516_2025.fedpro.FlushQueueRequestRequest.newBuilder();

      builder.setTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setFlushQueueRequestRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasFlushQueueRequestResponse();
      // No return value
   }

   public void
   enableAsynchronousDelivery(
   )
   throws
         AsynchronousDeliveryAlreadyEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.EnableAsynchronousDeliveryRequest request;
      request = hla.rti1516_2025.fedpro.EnableAsynchronousDeliveryRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setEnableAsynchronousDeliveryRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasEnableAsynchronousDeliveryResponse();
      // No return value
   }

   public void
   disableAsynchronousDelivery(
   )
   throws
         AsynchronousDeliveryAlreadyDisabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.DisableAsynchronousDeliveryRequest request;
      request = hla.rti1516_2025.fedpro.DisableAsynchronousDeliveryRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableAsynchronousDeliveryRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDisableAsynchronousDeliveryResponse();
      // No return value
   }

   public hla.rti1516e.TimeQueryReturn
   queryGALT(
   )
   throws
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.QueryGALTRequest request;
      request = hla.rti1516_2025.fedpro.QueryGALTRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryGALTRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryGALTResponse();
      hla.rti1516_2025.fedpro.QueryGALTResponse response = callResponse.getQueryGALTResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.LogicalTime
   queryLogicalTime(
   )
   throws
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.QueryLogicalTimeRequest request;
      request = hla.rti1516_2025.fedpro.QueryLogicalTimeRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLogicalTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryLogicalTimeResponse();
      hla.rti1516_2025.fedpro.QueryLogicalTimeResponse response = callResponse.getQueryLogicalTimeResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.TimeQueryReturn
   queryLITS(
   )
   throws
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.QueryLITSRequest request;
      request = hla.rti1516_2025.fedpro.QueryLITSRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLITSRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryLITSResponse();
      hla.rti1516_2025.fedpro.QueryLITSResponse response = callResponse.getQueryLITSResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   modifyLookahead(
         hla.rti1516e.LogicalTimeInterval theLookahead)
   throws
         InvalidLookahead,
         InTimeAdvancingState,
         TimeRegulationIsNotEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.ModifyLookaheadRequest request;
      hla.rti1516_2025.fedpro.ModifyLookaheadRequest.Builder builder =
            hla.rti1516_2025.fedpro.ModifyLookaheadRequest.newBuilder();

      builder.setLookahead(_clientConverter.convertFromHla(theLookahead));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setModifyLookaheadRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasModifyLookaheadResponse();
      // No return value
   }

   public hla.rti1516e.LogicalTimeInterval
   queryLookahead(
   )
   throws
         TimeRegulationIsNotEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.QueryLookaheadRequest request;
      request = hla.rti1516_2025.fedpro.QueryLookaheadRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLookaheadRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryLookaheadResponse();
      hla.rti1516_2025.fedpro.QueryLookaheadResponse response = callResponse.getQueryLookaheadResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   retract(
         hla.rti1516e.MessageRetractionHandle theHandle)
   throws
         MessageCanNoLongerBeRetracted,
         InvalidMessageRetractionHandle,
         TimeRegulationIsNotEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.RetractRequest request;
      hla.rti1516_2025.fedpro.RetractRequest.Builder builder = hla.rti1516_2025.fedpro.RetractRequest.newBuilder();

      builder.setRetraction(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRetractRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRetractResponse();
      // No return value
   }

   public void
   changeAttributeOrderType(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandleSet theAttributes,
         hla.rti1516e.OrderType theType)
   throws
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.ChangeAttributeOrderTypeRequest request;
      hla.rti1516_2025.fedpro.ChangeAttributeOrderTypeRequest.Builder builder =
            hla.rti1516_2025.fedpro.ChangeAttributeOrderTypeRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributes(_clientConverter.convertFromHla(theAttributes));
      builder.setOrderType(_clientConverter.convertFromHla(theType));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setChangeAttributeOrderTypeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasChangeAttributeOrderTypeResponse();
      // No return value
   }

   public void
   changeInteractionOrderType(
         hla.rti1516e.InteractionClassHandle theClass,
         hla.rti1516e.OrderType theType)
   throws
         InteractionClassNotPublished,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.ChangeInteractionOrderTypeRequest request;
      hla.rti1516_2025.fedpro.ChangeInteractionOrderTypeRequest.Builder builder =
            hla.rti1516_2025.fedpro.ChangeInteractionOrderTypeRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theClass));
      builder.setOrderType(_clientConverter.convertFromHla(theType));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setChangeInteractionOrderTypeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasChangeInteractionOrderTypeResponse();
      // No return value
   }

   public hla.rti1516e.RegionHandle
   createRegion(
         hla.rti1516e.DimensionHandleSet dimensions)
   throws
         InvalidDimensionHandle,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.CreateRegionRequest request;
      hla.rti1516_2025.fedpro.CreateRegionRequest.Builder builder =
            hla.rti1516_2025.fedpro.CreateRegionRequest.newBuilder();

      builder.setDimensions(_clientConverter.convertFromHla(dimensions));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCreateRegionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCreateRegionResponse();
      hla.rti1516_2025.fedpro.CreateRegionResponse response = callResponse.getCreateRegionResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   commitRegionModifications(
         hla.rti1516e.RegionHandleSet regions)
   throws
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.CommitRegionModificationsRequest request;
      hla.rti1516_2025.fedpro.CommitRegionModificationsRequest.Builder builder =
            hla.rti1516_2025.fedpro.CommitRegionModificationsRequest.newBuilder();

      builder.setRegions(_clientConverter.convertFromHla(regions));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCommitRegionModificationsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCommitRegionModificationsResponse();
      // No return value
   }

   public void
   deleteRegion(
         hla.rti1516e.RegionHandle theRegion)
   throws
         RegionInUseForUpdateOrSubscription,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.DeleteRegionRequest request;
      hla.rti1516_2025.fedpro.DeleteRegionRequest.Builder builder =
            hla.rti1516_2025.fedpro.DeleteRegionRequest.newBuilder();

      builder.setRegion(_clientConverter.convertFromHla(theRegion));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setDeleteRegionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDeleteRegionResponse();
      // No return value
   }

   public hla.rti1516e.ObjectInstanceHandle
   registerObjectInstanceWithRegions(
         hla.rti1516e.ObjectClassHandle theClass,
         hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions)
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
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithRegionsRequest request;
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithRegionsRequest.Builder builder =
            hla.rti1516_2025.fedpro.RegisterObjectInstanceWithRegionsRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterObjectInstanceWithRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterObjectInstanceWithRegionsResponse();
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithRegionsResponse response =
            callResponse.getRegisterObjectInstanceWithRegionsResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.ObjectInstanceHandle
   registerObjectInstanceWithNameAndRegions(
         hla.rti1516e.ObjectClassHandle theClass,
         hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions,
         java.lang.String theObject)
   throws
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
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameAndRegionsRequest request;
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameAndRegionsRequest.Builder builder =
            hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameAndRegionsRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      builder.setObjectInstanceName(_clientConverter.convertFromHla(theObject));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setRegisterObjectInstanceWithNameAndRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterObjectInstanceWithNameAndRegionsResponse();
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameAndRegionsResponse response =
            callResponse.getRegisterObjectInstanceWithNameAndRegionsResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   associateRegionsForUpdates(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions)
   throws
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
      hla.rti1516_2025.fedpro.AssociateRegionsForUpdatesRequest request;
      hla.rti1516_2025.fedpro.AssociateRegionsForUpdatesRequest.Builder builder =
            hla.rti1516_2025.fedpro.AssociateRegionsForUpdatesRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAssociateRegionsForUpdatesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasAssociateRegionsForUpdatesResponse();
      // No return value
   }

   public void
   unassociateRegionsForUpdates(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions)
   throws
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
      hla.rti1516_2025.fedpro.UnassociateRegionsForUpdatesRequest request;
      hla.rti1516_2025.fedpro.UnassociateRegionsForUpdatesRequest.Builder builder =
            hla.rti1516_2025.fedpro.UnassociateRegionsForUpdatesRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnassociateRegionsForUpdatesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnassociateRegionsForUpdatesResponse();
      // No return value
   }

   public void
   subscribeObjectClassAttributesWithRegions(
         hla.rti1516e.ObjectClassHandle theClass,
         hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions,
         boolean active)
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
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRegionsRequest request;
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRegionsRequest.Builder builder =
            hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRegionsRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      builder.setActive(_clientConverter.convertFromHla(active));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setSubscribeObjectClassAttributesWithRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeObjectClassAttributesWithRegionsResponse();
      // No return value
   }

   public void
   subscribeObjectClassAttributesWithRegionsAndRate(
         hla.rti1516e.ObjectClassHandle theClass,
         hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions,
         boolean active,
         java.lang.String updateRateDesignator)
   throws
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
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRegionsAndRateRequest request;
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRegionsAndRateRequest.Builder builder =
            hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRegionsAndRateRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      builder.setActive(_clientConverter.convertFromHla(active));
      builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setSubscribeObjectClassAttributesWithRegionsAndRateRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeObjectClassAttributesWithRegionsAndRateResponse();
      // No return value
   }

   public void
   unsubscribeObjectClassAttributesWithRegions(
         hla.rti1516e.ObjectClassHandle theClass,
         hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions)
   throws
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
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassAttributesWithRegionsRequest request;
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassAttributesWithRegionsRequest.Builder builder =
            hla.rti1516_2025.fedpro.UnsubscribeObjectClassAttributesWithRegionsRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setUnsubscribeObjectClassAttributesWithRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnsubscribeObjectClassAttributesWithRegionsResponse();
      // No return value
   }

   public void
   subscribeInteractionClassWithRegions(
         hla.rti1516e.InteractionClassHandle theClass,
         boolean active,
         hla.rti1516e.RegionHandleSet regions)
   throws
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
      hla.rti1516_2025.fedpro.SubscribeInteractionClassWithRegionsRequest request;
      hla.rti1516_2025.fedpro.SubscribeInteractionClassWithRegionsRequest.Builder builder =
            hla.rti1516_2025.fedpro.SubscribeInteractionClassWithRegionsRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theClass));
      builder.setActive(_clientConverter.convertFromHla(active));
      builder.setRegions(_clientConverter.convertFromHla(regions));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setSubscribeInteractionClassWithRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeInteractionClassWithRegionsResponse();
      // No return value
   }

   public void
   unsubscribeInteractionClassWithRegions(
         hla.rti1516e.InteractionClassHandle theClass,
         hla.rti1516e.RegionHandleSet regions)
   throws
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.UnsubscribeInteractionClassWithRegionsRequest request;
      hla.rti1516_2025.fedpro.UnsubscribeInteractionClassWithRegionsRequest.Builder builder =
            hla.rti1516_2025.fedpro.UnsubscribeInteractionClassWithRegionsRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theClass));
      builder.setRegions(_clientConverter.convertFromHla(regions));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setUnsubscribeInteractionClassWithRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnsubscribeInteractionClassWithRegionsResponse();
      // No return value
   }

   public void
   sendInteractionWithRegions(
         hla.rti1516e.InteractionClassHandle theInteraction,
         hla.rti1516e.ParameterHandleValueMap theParameters,
         hla.rti1516e.RegionHandleSet regions,
         byte[] userSuppliedTag)
   throws
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
      hla.rti1516_2025.fedpro.SendInteractionWithRegionsRequest request;
      hla.rti1516_2025.fedpro.SendInteractionWithRegionsRequest.Builder builder =
            hla.rti1516_2025.fedpro.SendInteractionWithRegionsRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theInteraction));
      builder.setParameterValues(_clientConverter.convertFromHla(theParameters));
      builder.setRegions(_clientConverter.convertFromHla(regions));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionWithRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSendInteractionWithRegionsResponse();
      // No return value
   }

   public hla.rti1516e.MessageRetractionReturn
   sendInteractionWithRegionsAndTime(
         hla.rti1516e.InteractionClassHandle theInteraction,
         hla.rti1516e.ParameterHandleValueMap theParameters,
         hla.rti1516e.RegionHandleSet regions,
         byte[] userSuppliedTag,
         hla.rti1516e.LogicalTime theTime)
   throws
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
      hla.rti1516_2025.fedpro.SendInteractionWithRegionsAndTimeRequest request;
      hla.rti1516_2025.fedpro.SendInteractionWithRegionsAndTimeRequest.Builder builder =
            hla.rti1516_2025.fedpro.SendInteractionWithRegionsAndTimeRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theInteraction));
      builder.setParameterValues(_clientConverter.convertFromHla(theParameters));
      builder.setRegions(_clientConverter.convertFromHla(regions));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      builder.setTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionWithRegionsAndTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSendInteractionWithRegionsAndTimeResponse();
      hla.rti1516_2025.fedpro.SendInteractionWithRegionsAndTimeResponse response =
            callResponse.getSendInteractionWithRegionsAndTimeResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   requestAttributeValueUpdateWithRegions(
         hla.rti1516e.ObjectClassHandle theClass,
         hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions,
         byte[] userSuppliedTag)
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
      hla.rti1516_2025.fedpro.RequestAttributeValueUpdateWithRegionsRequest request;
      hla.rti1516_2025.fedpro.RequestAttributeValueUpdateWithRegionsRequest.Builder builder =
            hla.rti1516_2025.fedpro.RequestAttributeValueUpdateWithRegionsRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setRequestAttributeValueUpdateWithRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRequestAttributeValueUpdateWithRegionsResponse();
      // No return value
   }

   public hla.rti1516e.ResignAction
   getAutomaticResignDirective(
   )
   throws
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetAutomaticResignDirectiveRequest request;
      request = hla.rti1516_2025.fedpro.GetAutomaticResignDirectiveRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetAutomaticResignDirectiveRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAutomaticResignDirectiveResponse();
      hla.rti1516_2025.fedpro.GetAutomaticResignDirectiveResponse response =
            callResponse.getGetAutomaticResignDirectiveResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   setAutomaticResignDirective(
         hla.rti1516e.ResignAction resignAction)
   throws
         InvalidResignAction,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.SetAutomaticResignDirectiveRequest request;
      hla.rti1516_2025.fedpro.SetAutomaticResignDirectiveRequest.Builder builder =
            hla.rti1516_2025.fedpro.SetAutomaticResignDirectiveRequest.newBuilder();

      builder.setValue(_clientConverter.convertFromHla(resignAction));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetAutomaticResignDirectiveRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSetAutomaticResignDirectiveResponse();
      // No return value
   }

   public hla.rti1516e.FederateHandle
   getFederateHandle(
         java.lang.String theName)
   throws
         NameNotFound,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetFederateHandleRequest request;
      hla.rti1516_2025.fedpro.GetFederateHandleRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetFederateHandleRequest.newBuilder();

      builder.setFederateName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetFederateHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetFederateHandleResponse();
      hla.rti1516_2025.fedpro.GetFederateHandleResponse response = callResponse.getGetFederateHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getFederateName(
         hla.rti1516e.FederateHandle theHandle)
   throws
         InvalidFederateHandle,
         FederateHandleNotKnown,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetFederateNameRequest request;
      hla.rti1516_2025.fedpro.GetFederateNameRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetFederateNameRequest.newBuilder();

      builder.setFederate(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetFederateNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetFederateNameResponse();
      hla.rti1516_2025.fedpro.GetFederateNameResponse response = callResponse.getGetFederateNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.ObjectClassHandle
   getObjectClassHandle(
         java.lang.String theName)
   throws
         NameNotFound,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetObjectClassHandleRequest request;
      hla.rti1516_2025.fedpro.GetObjectClassHandleRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetObjectClassHandleRequest.newBuilder();

      builder.setObjectClassName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectClassHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetObjectClassHandleResponse();
      hla.rti1516_2025.fedpro.GetObjectClassHandleResponse response = callResponse.getGetObjectClassHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getObjectClassName(
         hla.rti1516e.ObjectClassHandle theHandle)
   throws
         InvalidObjectClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetObjectClassNameRequest request;
      hla.rti1516_2025.fedpro.GetObjectClassNameRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetObjectClassNameRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectClassNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetObjectClassNameResponse();
      hla.rti1516_2025.fedpro.GetObjectClassNameResponse response = callResponse.getGetObjectClassNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.ObjectClassHandle
   getKnownObjectClassHandle(
         hla.rti1516e.ObjectInstanceHandle theObject)
   throws
         ObjectInstanceNotKnown,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetKnownObjectClassHandleRequest request;
      hla.rti1516_2025.fedpro.GetKnownObjectClassHandleRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetKnownObjectClassHandleRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetKnownObjectClassHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetKnownObjectClassHandleResponse();
      hla.rti1516_2025.fedpro.GetKnownObjectClassHandleResponse response =
            callResponse.getGetKnownObjectClassHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.ObjectInstanceHandle
   getObjectInstanceHandle(
         java.lang.String theName)
   throws
         ObjectInstanceNotKnown,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetObjectInstanceHandleRequest request;
      hla.rti1516_2025.fedpro.GetObjectInstanceHandleRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetObjectInstanceHandleRequest.newBuilder();

      builder.setObjectInstanceName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectInstanceHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetObjectInstanceHandleResponse();
      hla.rti1516_2025.fedpro.GetObjectInstanceHandleResponse response =
            callResponse.getGetObjectInstanceHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getObjectInstanceName(
         hla.rti1516e.ObjectInstanceHandle theHandle)
   throws
         ObjectInstanceNotKnown,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetObjectInstanceNameRequest request;
      hla.rti1516_2025.fedpro.GetObjectInstanceNameRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetObjectInstanceNameRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectInstanceNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetObjectInstanceNameResponse();
      hla.rti1516_2025.fedpro.GetObjectInstanceNameResponse response = callResponse.getGetObjectInstanceNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.AttributeHandle
   getAttributeHandle(
         hla.rti1516e.ObjectClassHandle whichClass,
         java.lang.String theName)
   throws
         NameNotFound,
         InvalidObjectClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetAttributeHandleRequest request;
      hla.rti1516_2025.fedpro.GetAttributeHandleRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetAttributeHandleRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(whichClass));
      builder.setAttributeName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetAttributeHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAttributeHandleResponse();
      hla.rti1516_2025.fedpro.GetAttributeHandleResponse response = callResponse.getGetAttributeHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getAttributeName(
         hla.rti1516e.ObjectClassHandle whichClass,
         hla.rti1516e.AttributeHandle theHandle)
   throws
         AttributeNotDefined,
         InvalidAttributeHandle,
         InvalidObjectClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetAttributeNameRequest request;
      hla.rti1516_2025.fedpro.GetAttributeNameRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetAttributeNameRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(whichClass));
      builder.setAttribute(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetAttributeNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAttributeNameResponse();
      hla.rti1516_2025.fedpro.GetAttributeNameResponse response = callResponse.getGetAttributeNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public double
   getUpdateRateValue(
         java.lang.String updateRateDesignator)
   throws
         InvalidUpdateRateDesignator,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetUpdateRateValueRequest request;
      hla.rti1516_2025.fedpro.GetUpdateRateValueRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetUpdateRateValueRequest.newBuilder();

      builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetUpdateRateValueRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetUpdateRateValueResponse();
      hla.rti1516_2025.fedpro.GetUpdateRateValueResponse response = callResponse.getGetUpdateRateValueResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public double
   getUpdateRateValueForAttribute(
         hla.rti1516e.ObjectInstanceHandle theObject,
         hla.rti1516e.AttributeHandle theAttribute)
   throws
         ObjectInstanceNotKnown,
         AttributeNotDefined,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetUpdateRateValueForAttributeRequest request;
      hla.rti1516_2025.fedpro.GetUpdateRateValueForAttributeRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetUpdateRateValueForAttributeRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttribute(_clientConverter.convertFromHla(theAttribute));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetUpdateRateValueForAttributeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetUpdateRateValueForAttributeResponse();
      hla.rti1516_2025.fedpro.GetUpdateRateValueForAttributeResponse response =
            callResponse.getGetUpdateRateValueForAttributeResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.InteractionClassHandle
   getInteractionClassHandle(
         java.lang.String theName)
   throws
         NameNotFound,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetInteractionClassHandleRequest request;
      hla.rti1516_2025.fedpro.GetInteractionClassHandleRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetInteractionClassHandleRequest.newBuilder();

      builder.setInteractionClassName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetInteractionClassHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetInteractionClassHandleResponse();
      hla.rti1516_2025.fedpro.GetInteractionClassHandleResponse response =
            callResponse.getGetInteractionClassHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getInteractionClassName(
         hla.rti1516e.InteractionClassHandle theHandle)
   throws
         InvalidInteractionClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetInteractionClassNameRequest request;
      hla.rti1516_2025.fedpro.GetInteractionClassNameRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetInteractionClassNameRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetInteractionClassNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetInteractionClassNameResponse();
      hla.rti1516_2025.fedpro.GetInteractionClassNameResponse response =
            callResponse.getGetInteractionClassNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.ParameterHandle
   getParameterHandle(
         hla.rti1516e.InteractionClassHandle whichClass,
         java.lang.String theName)
   throws
         NameNotFound,
         InvalidInteractionClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetParameterHandleRequest request;
      hla.rti1516_2025.fedpro.GetParameterHandleRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetParameterHandleRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(whichClass));
      builder.setParameterName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetParameterHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetParameterHandleResponse();
      hla.rti1516_2025.fedpro.GetParameterHandleResponse response = callResponse.getGetParameterHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getParameterName(
         hla.rti1516e.InteractionClassHandle whichClass,
         hla.rti1516e.ParameterHandle theHandle)
   throws
         InteractionParameterNotDefined,
         InvalidParameterHandle,
         InvalidInteractionClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetParameterNameRequest request;
      hla.rti1516_2025.fedpro.GetParameterNameRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetParameterNameRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(whichClass));
      builder.setParameter(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetParameterNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetParameterNameResponse();
      hla.rti1516_2025.fedpro.GetParameterNameResponse response = callResponse.getGetParameterNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.OrderType
   getOrderType(
         java.lang.String theName)
   throws
         InvalidOrderName,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetOrderTypeRequest request;
      hla.rti1516_2025.fedpro.GetOrderTypeRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetOrderTypeRequest.newBuilder();

      builder.setOrderTypeName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetOrderTypeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetOrderTypeResponse();
      hla.rti1516_2025.fedpro.GetOrderTypeResponse response = callResponse.getGetOrderTypeResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getOrderName(
         hla.rti1516e.OrderType theType)
   throws
         InvalidOrderType,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetOrderNameRequest request;
      hla.rti1516_2025.fedpro.GetOrderNameRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetOrderNameRequest.newBuilder();

      builder.setOrderType(_clientConverter.convertFromHla(theType));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetOrderNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetOrderNameResponse();
      hla.rti1516_2025.fedpro.GetOrderNameResponse response = callResponse.getGetOrderNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.TransportationTypeHandle
   getTransportationTypeHandle(
         java.lang.String theName)
   throws
         InvalidTransportationName,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetTransportationTypeHandleRequest request;
      hla.rti1516_2025.fedpro.GetTransportationTypeHandleRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetTransportationTypeHandleRequest.newBuilder();

      builder.setTransportationTypeName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetTransportationTypeHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetTransportationTypeHandleResponse();
      hla.rti1516_2025.fedpro.GetTransportationTypeHandleResponse response =
            callResponse.getGetTransportationTypeHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getTransportationTypeName(
         hla.rti1516e.TransportationTypeHandle theHandle)
   throws
         InvalidTransportationType,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetTransportationTypeNameRequest request;
      hla.rti1516_2025.fedpro.GetTransportationTypeNameRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetTransportationTypeNameRequest.newBuilder();

      builder.setTransportationType(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetTransportationTypeNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetTransportationTypeNameResponse();
      hla.rti1516_2025.fedpro.GetTransportationTypeNameResponse response =
            callResponse.getGetTransportationTypeNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.DimensionHandleSet
   getAvailableDimensionsForClassAttribute(
         hla.rti1516e.ObjectClassHandle whichClass,
         hla.rti1516e.AttributeHandle ignoredTheHandle)
   throws
         AttributeNotDefined,
         InvalidAttributeHandle,
         InvalidObjectClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetAvailableDimensionsForObjectClassRequest request;
      hla.rti1516_2025.fedpro.GetAvailableDimensionsForObjectClassRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetAvailableDimensionsForObjectClassRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(whichClass));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setGetAvailableDimensionsForObjectClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAvailableDimensionsForObjectClassResponse();
      hla.rti1516_2025.fedpro.GetAvailableDimensionsForObjectClassResponse response =
            callResponse.getGetAvailableDimensionsForObjectClassResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.DimensionHandleSet
   getAvailableDimensionsForInteractionClass(
         hla.rti1516e.InteractionClassHandle theHandle)
   throws
         InvalidInteractionClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetAvailableDimensionsForInteractionClassRequest request;
      hla.rti1516_2025.fedpro.GetAvailableDimensionsForInteractionClassRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetAvailableDimensionsForInteractionClassRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setGetAvailableDimensionsForInteractionClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAvailableDimensionsForInteractionClassResponse();
      hla.rti1516_2025.fedpro.GetAvailableDimensionsForInteractionClassResponse response =
            callResponse.getGetAvailableDimensionsForInteractionClassResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.DimensionHandle
   getDimensionHandle(
         java.lang.String theName)
   throws
         NameNotFound,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetDimensionHandleRequest request;
      hla.rti1516_2025.fedpro.GetDimensionHandleRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetDimensionHandleRequest.newBuilder();

      builder.setDimensionName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetDimensionHandleResponse();
      hla.rti1516_2025.fedpro.GetDimensionHandleResponse response = callResponse.getGetDimensionHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getDimensionName(
         hla.rti1516e.DimensionHandle theHandle)
   throws
         InvalidDimensionHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetDimensionNameRequest request;
      hla.rti1516_2025.fedpro.GetDimensionNameRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetDimensionNameRequest.newBuilder();

      builder.setDimension(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetDimensionNameResponse();
      hla.rti1516_2025.fedpro.GetDimensionNameResponse response = callResponse.getGetDimensionNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public long
   getDimensionUpperBound(
         hla.rti1516e.DimensionHandle theHandle)
   throws
         InvalidDimensionHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetDimensionUpperBoundRequest request;
      hla.rti1516_2025.fedpro.GetDimensionUpperBoundRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetDimensionUpperBoundRequest.newBuilder();

      builder.setDimension(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionUpperBoundRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetDimensionUpperBoundResponse();
      hla.rti1516_2025.fedpro.GetDimensionUpperBoundResponse response = callResponse.getGetDimensionUpperBoundResponse();
      return _clientConverter.convertToHla(Integer.toUnsignedLong(response.getResult()));
   }

   public hla.rti1516e.DimensionHandleSet
   getDimensionHandleSet(
         hla.rti1516e.RegionHandle region)
   throws
         InvalidRegion,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetDimensionHandleSetRequest request;
      hla.rti1516_2025.fedpro.GetDimensionHandleSetRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetDimensionHandleSetRequest.newBuilder();

      builder.setRegion(_clientConverter.convertFromHla(region));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionHandleSetRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetDimensionHandleSetResponse();
      hla.rti1516_2025.fedpro.GetDimensionHandleSetResponse response = callResponse.getGetDimensionHandleSetResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.RangeBounds
   getRangeBounds(
         hla.rti1516e.RegionHandle region,
         hla.rti1516e.DimensionHandle dimension)
   throws
         RegionDoesNotContainSpecifiedDimension,
         InvalidRegion,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetRangeBoundsRequest request;
      hla.rti1516_2025.fedpro.GetRangeBoundsRequest.Builder builder =
            hla.rti1516_2025.fedpro.GetRangeBoundsRequest.newBuilder();

      builder.setRegion(_clientConverter.convertFromHla(region));
      builder.setDimension(_clientConverter.convertFromHla(dimension));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetRangeBoundsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetRangeBoundsResponse();
      hla.rti1516_2025.fedpro.GetRangeBoundsResponse response = callResponse.getGetRangeBoundsResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   setRangeBounds(
         hla.rti1516e.RegionHandle region,
         hla.rti1516e.DimensionHandle dimension,
         hla.rti1516e.RangeBounds bounds)
   throws
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
      hla.rti1516_2025.fedpro.SetRangeBoundsRequest request;
      hla.rti1516_2025.fedpro.SetRangeBoundsRequest.Builder builder =
            hla.rti1516_2025.fedpro.SetRangeBoundsRequest.newBuilder();

      builder.setRegion(_clientConverter.convertFromHla(region));
      builder.setDimension(_clientConverter.convertFromHla(dimension));
      builder.setRangeBounds(_clientConverter.convertFromHla(bounds));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetRangeBoundsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSetRangeBoundsResponse();
      // No return value
   }

   public long
   normalizeFederateHandle(
         hla.rti1516e.FederateHandle federateHandle)
   throws
         InvalidFederateHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.NormalizeFederateHandleRequest request;
      hla.rti1516_2025.fedpro.NormalizeFederateHandleRequest.Builder builder =
            hla.rti1516_2025.fedpro.NormalizeFederateHandleRequest.newBuilder();

      builder.setFederate(_clientConverter.convertFromHla(federateHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNormalizeFederateHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasNormalizeFederateHandleResponse();
      hla.rti1516_2025.fedpro.NormalizeFederateHandleResponse response =
            callResponse.getNormalizeFederateHandleResponse();
      return _clientConverter.convertToHla(Integer.toUnsignedLong(response.getResult()));
   }

   public long
   normalizeServiceGroup(
         hla.rti1516e.ServiceGroup group)
   throws
         InvalidServiceGroup,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_2025.fedpro.NormalizeServiceGroupRequest request;
      hla.rti1516_2025.fedpro.NormalizeServiceGroupRequest.Builder builder =
            hla.rti1516_2025.fedpro.NormalizeServiceGroupRequest.newBuilder();

      builder.setServiceGroup(_clientConverter.convertFromHla(group));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNormalizeServiceGroupRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasNormalizeServiceGroupResponse();
      hla.rti1516_2025.fedpro.NormalizeServiceGroupResponse response = callResponse.getNormalizeServiceGroupResponse();
      return _clientConverter.convertToHla(Integer.toUnsignedLong(response.getResult()));
   }

   public boolean getObjectClassRelevanceAdvisorySwitch()
   throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetObjectClassRelevanceAdvisorySwitchRequest request;
      GetObjectClassRelevanceAdvisorySwitchRequest.Builder builder =
            GetObjectClassRelevanceAdvisorySwitchRequest.newBuilder();

      request = builder.build();

      CallRequest callRequest =
            CallRequest.newBuilder().setGetObjectClassRelevanceAdvisorySwitchRequest(request)
                  .build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetObjectClassRelevanceAdvisorySwitchResponse();
      hla.rti1516_2025.fedpro.GetObjectClassRelevanceAdvisorySwitchResponse response = callResponse.getGetObjectClassRelevanceAdvisorySwitchResponse();
      return response.getResult();
   }

   public void setObjectClassRelevanceAdvisorySwitch(boolean value)
   throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      hla.rti1516_2025.fedpro.SetObjectClassRelevanceAdvisorySwitchRequest request;
      SetObjectClassRelevanceAdvisorySwitchRequest.Builder builder =
            SetObjectClassRelevanceAdvisorySwitchRequest.newBuilder();

      builder.setValue(value);
      request = builder.build();

      CallRequest callRequest =
            CallRequest.newBuilder().setSetObjectClassRelevanceAdvisorySwitchRequest(request)
                  .build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSetObjectClassRelevanceAdvisorySwitchResponse();
      // No return value
   }

   public boolean getAttributeRelevanceAdvisorySwitch()
   throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetAttributeRelevanceAdvisorySwitchRequest request;
      GetAttributeRelevanceAdvisorySwitchRequest.Builder builder =
            GetAttributeRelevanceAdvisorySwitchRequest.newBuilder();

      request = builder.build();

      CallRequest callRequest =
            CallRequest.newBuilder().setGetAttributeRelevanceAdvisorySwitchRequest(request)
                  .build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAttributeRelevanceAdvisorySwitchResponse();
      hla.rti1516_2025.fedpro.GetAttributeRelevanceAdvisorySwitchResponse response = callResponse.getGetAttributeRelevanceAdvisorySwitchResponse();
      return response.getResult();
   }

   public void setAttributeRelevanceAdvisorySwitch(boolean value)
   throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      hla.rti1516_2025.fedpro.SetAttributeRelevanceAdvisorySwitchRequest request;
      SetAttributeRelevanceAdvisorySwitchRequest.Builder builder =
            SetAttributeRelevanceAdvisorySwitchRequest.newBuilder();

      builder.setValue(value);
      request = builder.build();

      CallRequest callRequest =
            CallRequest.newBuilder().setSetAttributeRelevanceAdvisorySwitchRequest(request)
                  .build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSetAttributeRelevanceAdvisorySwitchResponse();
      // No return value
   }

   public boolean getAttributeScopeAdvisorySwitch()
   throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetAttributeScopeAdvisorySwitchRequest request;
      GetAttributeScopeAdvisorySwitchRequest.Builder builder =
            GetAttributeScopeAdvisorySwitchRequest.newBuilder();

      request = builder.build();

      CallRequest callRequest =
            CallRequest.newBuilder().setGetAttributeScopeAdvisorySwitchRequest(request)
                  .build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAttributeScopeAdvisorySwitchResponse();
      hla.rti1516_2025.fedpro.GetAttributeScopeAdvisorySwitchResponse response = callResponse.getGetAttributeScopeAdvisorySwitchResponse();
      return response.getResult();
   }

   public void setAttributeScopeAdvisorySwitch(boolean value)
   throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      hla.rti1516_2025.fedpro.SetAttributeScopeAdvisorySwitchRequest request;
      SetAttributeScopeAdvisorySwitchRequest.Builder builder =
            SetAttributeScopeAdvisorySwitchRequest.newBuilder();

      builder.setValue(value);
      request = builder.build();

      CallRequest callRequest =
            CallRequest.newBuilder().setSetAttributeScopeAdvisorySwitchRequest(request)
                  .build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSetAttributeScopeAdvisorySwitchResponse();
      // No return value
   }

   public boolean getInteractionRelevanceAdvisorySwitch()
   throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      hla.rti1516_2025.fedpro.GetInteractionRelevanceAdvisorySwitchRequest request;
      GetInteractionRelevanceAdvisorySwitchRequest.Builder builder =
            GetInteractionRelevanceAdvisorySwitchRequest.newBuilder();

      request = builder.build();

      CallRequest callRequest =
            CallRequest.newBuilder().setGetInteractionRelevanceAdvisorySwitchRequest(request)
                  .build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetInteractionRelevanceAdvisorySwitchResponse();
      hla.rti1516_2025.fedpro.GetInteractionRelevanceAdvisorySwitchResponse response = callResponse.getGetInteractionRelevanceAdvisorySwitchResponse();
      return response.getResult();
   }

   public void setInteractionRelevanceAdvisorySwitch(boolean value)
   throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      hla.rti1516_2025.fedpro.SetInteractionRelevanceAdvisorySwitchRequest request;
      SetInteractionRelevanceAdvisorySwitchRequest.Builder builder =
            SetInteractionRelevanceAdvisorySwitchRequest.newBuilder();

      builder.setValue(value);
      request = builder.build();

      CallRequest callRequest =
            CallRequest.newBuilder().setSetInteractionRelevanceAdvisorySwitchRequest(request)
                  .build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSetInteractionRelevanceAdvisorySwitchResponse();
      // No return value
   }
}
