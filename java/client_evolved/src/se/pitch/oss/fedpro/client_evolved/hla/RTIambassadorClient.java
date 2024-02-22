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

import hla.rti1516_202X.fedpro.CallRequest;
import hla.rti1516_202X.fedpro.CallResponse;
import hla.rti1516e.exceptions.*;

import java.util.Collections;

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
      hla.rti1516_202X.fedpro.CreateFederationExecutionWithModulesAndTimeRequest request;
      hla.rti1516_202X.fedpro.CreateFederationExecutionWithModulesAndTimeRequest.Builder builder =
            hla.rti1516_202X.fedpro.CreateFederationExecutionWithModulesAndTimeRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.CreateFederationExecutionWithMIMAndTimeRequest request;
      hla.rti1516_202X.fedpro.CreateFederationExecutionWithMIMAndTimeRequest.Builder builder =
            hla.rti1516_202X.fedpro.CreateFederationExecutionWithMIMAndTimeRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.DestroyFederationExecutionRequest request;
      hla.rti1516_202X.fedpro.DestroyFederationExecutionRequest.Builder builder =
            hla.rti1516_202X.fedpro.DestroyFederationExecutionRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.ListFederationExecutionsRequest request;
      request = hla.rti1516_202X.fedpro.ListFederationExecutionsRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.JoinFederationExecutionRequest request;
      hla.rti1516_202X.fedpro.JoinFederationExecutionRequest.Builder builder =
            hla.rti1516_202X.fedpro.JoinFederationExecutionRequest.newBuilder();

      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationName(_clientConverter.convertFromHla(federationName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setJoinFederationExecutionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionResponse();
      hla.rti1516_202X.fedpro.JoinFederationExecutionResponse response =
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
      hla.rti1516_202X.fedpro.JoinFederationExecutionWithNameRequest request;
      hla.rti1516_202X.fedpro.JoinFederationExecutionWithNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.JoinFederationExecutionWithNameRequest.newBuilder();

      builder.setFederateName(_clientConverter.convertFromHla(federateName));
      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationName(_clientConverter.convertFromHla(federationName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setJoinFederationExecutionWithNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionWithNameResponse();
      hla.rti1516_202X.fedpro.JoinFederationExecutionWithNameResponse response =
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
      hla.rti1516_202X.fedpro.JoinFederationExecutionWithNameAndModulesRequest request;
      hla.rti1516_202X.fedpro.JoinFederationExecutionWithNameAndModulesRequest.Builder builder =
            hla.rti1516_202X.fedpro.JoinFederationExecutionWithNameAndModulesRequest.newBuilder();

      builder.setFederateName(_clientConverter.convertFromHla(federateName));
      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationName(_clientConverter.convertFromHla(federationName));
      builder.setAdditionalFomModules(_clientConverter.convertFromHla(additionalFomModules));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setJoinFederationExecutionWithNameAndModulesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionWithNameAndModulesResponse();
      hla.rti1516_202X.fedpro.JoinFederationExecutionWithNameAndModulesResponse response =
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
      hla.rti1516_202X.fedpro.JoinFederationExecutionWithModulesRequest request;
      hla.rti1516_202X.fedpro.JoinFederationExecutionWithModulesRequest.Builder builder =
            hla.rti1516_202X.fedpro.JoinFederationExecutionWithModulesRequest.newBuilder();

      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationName(_clientConverter.convertFromHla(federationName));
      builder.setAdditionalFomModules(_clientConverter.convertFromHla(additionalFomModules));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setJoinFederationExecutionWithModulesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionWithModulesResponse();
      hla.rti1516_202X.fedpro.JoinFederationExecutionWithModulesResponse response =
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
      hla.rti1516_202X.fedpro.ResignFederationExecutionRequest request;
      hla.rti1516_202X.fedpro.ResignFederationExecutionRequest.Builder builder =
            hla.rti1516_202X.fedpro.ResignFederationExecutionRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.RegisterFederationSynchronizationPointRequest request;
      hla.rti1516_202X.fedpro.RegisterFederationSynchronizationPointRequest.Builder builder =
            hla.rti1516_202X.fedpro.RegisterFederationSynchronizationPointRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.RegisterFederationSynchronizationPointWithSetRequest request;
      hla.rti1516_202X.fedpro.RegisterFederationSynchronizationPointWithSetRequest.Builder builder =
            hla.rti1516_202X.fedpro.RegisterFederationSynchronizationPointWithSetRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.SynchronizationPointAchievedRequest request;
      hla.rti1516_202X.fedpro.SynchronizationPointAchievedRequest.Builder builder =
            hla.rti1516_202X.fedpro.SynchronizationPointAchievedRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.RequestFederationSaveRequest request;
      hla.rti1516_202X.fedpro.RequestFederationSaveRequest.Builder builder =
            hla.rti1516_202X.fedpro.RequestFederationSaveRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.RequestFederationSaveWithTimeRequest request;
      hla.rti1516_202X.fedpro.RequestFederationSaveWithTimeRequest.Builder builder =
            hla.rti1516_202X.fedpro.RequestFederationSaveWithTimeRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.FederateSaveBegunRequest request;
      request = hla.rti1516_202X.fedpro.FederateSaveBegunRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.FederateSaveCompleteRequest request;
      request = hla.rti1516_202X.fedpro.FederateSaveCompleteRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.FederateSaveNotCompleteRequest request;
      request = hla.rti1516_202X.fedpro.FederateSaveNotCompleteRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.AbortFederationSaveRequest request;
      request = hla.rti1516_202X.fedpro.AbortFederationSaveRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.QueryFederationSaveStatusRequest request;
      request = hla.rti1516_202X.fedpro.QueryFederationSaveStatusRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.RequestFederationRestoreRequest request;
      hla.rti1516_202X.fedpro.RequestFederationRestoreRequest.Builder builder =
            hla.rti1516_202X.fedpro.RequestFederationRestoreRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.FederateRestoreCompleteRequest request;
      request = hla.rti1516_202X.fedpro.FederateRestoreCompleteRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.FederateRestoreNotCompleteRequest request;
      request = hla.rti1516_202X.fedpro.FederateRestoreNotCompleteRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.AbortFederationRestoreRequest request;
      request = hla.rti1516_202X.fedpro.AbortFederationRestoreRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.QueryFederationRestoreStatusRequest request;
      request = hla.rti1516_202X.fedpro.QueryFederationRestoreStatusRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.PublishObjectClassAttributesRequest request;
      hla.rti1516_202X.fedpro.PublishObjectClassAttributesRequest.Builder builder =
            hla.rti1516_202X.fedpro.PublishObjectClassAttributesRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.UnpublishObjectClassRequest request;
      hla.rti1516_202X.fedpro.UnpublishObjectClassRequest.Builder builder =
            hla.rti1516_202X.fedpro.UnpublishObjectClassRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.UnpublishObjectClassAttributesRequest request;
      hla.rti1516_202X.fedpro.UnpublishObjectClassAttributesRequest.Builder builder =
            hla.rti1516_202X.fedpro.UnpublishObjectClassAttributesRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.PublishInteractionClassRequest request;
      hla.rti1516_202X.fedpro.PublishInteractionClassRequest.Builder builder =
            hla.rti1516_202X.fedpro.PublishInteractionClassRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.UnpublishInteractionClassRequest request;
      hla.rti1516_202X.fedpro.UnpublishInteractionClassRequest.Builder builder =
            hla.rti1516_202X.fedpro.UnpublishInteractionClassRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesRequest request;
      hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesRequest.Builder builder =
            hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesWithRateRequest request;
      hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesWithRateRequest.Builder builder =
            hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesWithRateRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesPassivelyRequest request;
      hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesPassivelyRequest.Builder builder =
            hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesPassivelyRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesPassivelyWithRateRequest request;
      hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesPassivelyWithRateRequest.Builder builder =
            hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesPassivelyWithRateRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.UnsubscribeObjectClassRequest request;
      hla.rti1516_202X.fedpro.UnsubscribeObjectClassRequest.Builder builder =
            hla.rti1516_202X.fedpro.UnsubscribeObjectClassRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.UnsubscribeObjectClassAttributesRequest request;
      hla.rti1516_202X.fedpro.UnsubscribeObjectClassAttributesRequest.Builder builder =
            hla.rti1516_202X.fedpro.UnsubscribeObjectClassAttributesRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.SubscribeInteractionClassRequest request;
      hla.rti1516_202X.fedpro.SubscribeInteractionClassRequest.Builder builder =
            hla.rti1516_202X.fedpro.SubscribeInteractionClassRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.SubscribeInteractionClassPassivelyRequest request;
      hla.rti1516_202X.fedpro.SubscribeInteractionClassPassivelyRequest.Builder builder =
            hla.rti1516_202X.fedpro.SubscribeInteractionClassPassivelyRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.UnsubscribeInteractionClassRequest request;
      hla.rti1516_202X.fedpro.UnsubscribeInteractionClassRequest.Builder builder =
            hla.rti1516_202X.fedpro.UnsubscribeInteractionClassRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.ReserveObjectInstanceNameRequest request;
      hla.rti1516_202X.fedpro.ReserveObjectInstanceNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.ReserveObjectInstanceNameRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.ReleaseObjectInstanceNameRequest request;
      hla.rti1516_202X.fedpro.ReleaseObjectInstanceNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.ReleaseObjectInstanceNameRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.ReserveMultipleObjectInstanceNameRequest request;
      hla.rti1516_202X.fedpro.ReserveMultipleObjectInstanceNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.ReserveMultipleObjectInstanceNameRequest.newBuilder();

      builder.addAllObjectInstanceNames(_clientConverter.convertFromHla(theObjectNames));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReserveMultipleObjectInstanceNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasReserveMultipleObjectInstanceNameResponse();
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
      hla.rti1516_202X.fedpro.ReleaseMultipleObjectInstanceNameRequest request;
      hla.rti1516_202X.fedpro.ReleaseMultipleObjectInstanceNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.ReleaseMultipleObjectInstanceNameRequest.newBuilder();

      builder.addAllObjectInstanceNames(_clientConverter.convertFromHla(theObjectNames));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReleaseMultipleObjectInstanceNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasReleaseMultipleObjectInstanceNameResponse();
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
      hla.rti1516_202X.fedpro.RegisterObjectInstanceRequest request;
      hla.rti1516_202X.fedpro.RegisterObjectInstanceRequest.Builder builder =
            hla.rti1516_202X.fedpro.RegisterObjectInstanceRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterObjectInstanceRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterObjectInstanceResponse();
      hla.rti1516_202X.fedpro.RegisterObjectInstanceResponse response = callResponse.getRegisterObjectInstanceResponse();
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
      hla.rti1516_202X.fedpro.RegisterObjectInstanceWithNameRequest request;
      hla.rti1516_202X.fedpro.RegisterObjectInstanceWithNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.RegisterObjectInstanceWithNameRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setObjectInstanceName(_clientConverter.convertFromHla(theObjectName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterObjectInstanceWithNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterObjectInstanceWithNameResponse();
      hla.rti1516_202X.fedpro.RegisterObjectInstanceWithNameResponse response =
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
      hla.rti1516_202X.fedpro.UpdateAttributeValuesRequest request;
      hla.rti1516_202X.fedpro.UpdateAttributeValuesRequest.Builder builder =
            hla.rti1516_202X.fedpro.UpdateAttributeValuesRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributeValues(_clientConverter.convertFromHla(theAttributes));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUpdateAttributeValuesRequest(request).build();
      if (_asyncUpdates) {
         doAsyncHlaCall(callRequest);
         return;
      }
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
      hla.rti1516_202X.fedpro.UpdateAttributeValuesWithTimeRequest request;
      hla.rti1516_202X.fedpro.UpdateAttributeValuesWithTimeRequest.Builder builder =
            hla.rti1516_202X.fedpro.UpdateAttributeValuesWithTimeRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributeValues(_clientConverter.convertFromHla(theAttributes));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      builder.setTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUpdateAttributeValuesWithTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUpdateAttributeValuesWithTimeResponse();
      hla.rti1516_202X.fedpro.UpdateAttributeValuesWithTimeResponse response =
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
      hla.rti1516_202X.fedpro.SendInteractionRequest request;
      hla.rti1516_202X.fedpro.SendInteractionRequest.Builder builder =
            hla.rti1516_202X.fedpro.SendInteractionRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theInteraction));
      builder.setParameterValues(_clientConverter.convertFromHla(theParameters));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionRequest(request).build();
      if (_asyncUpdates) {
         doAsyncHlaCall(callRequest);
         return;
      }
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
      hla.rti1516_202X.fedpro.SendInteractionWithTimeRequest request;
      hla.rti1516_202X.fedpro.SendInteractionWithTimeRequest.Builder builder =
            hla.rti1516_202X.fedpro.SendInteractionWithTimeRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theInteraction));
      builder.setParameterValues(_clientConverter.convertFromHla(theParameters));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      builder.setTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionWithTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSendInteractionWithTimeResponse();
      hla.rti1516_202X.fedpro.SendInteractionWithTimeResponse response =
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
      hla.rti1516_202X.fedpro.DeleteObjectInstanceRequest request;
      hla.rti1516_202X.fedpro.DeleteObjectInstanceRequest.Builder builder =
            hla.rti1516_202X.fedpro.DeleteObjectInstanceRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.DeleteObjectInstanceWithTimeRequest request;
      hla.rti1516_202X.fedpro.DeleteObjectInstanceWithTimeRequest.Builder builder =
            hla.rti1516_202X.fedpro.DeleteObjectInstanceWithTimeRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(objectHandle));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      builder.setTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setDeleteObjectInstanceWithTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDeleteObjectInstanceWithTimeResponse();
      hla.rti1516_202X.fedpro.DeleteObjectInstanceWithTimeResponse response =
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
      hla.rti1516_202X.fedpro.LocalDeleteObjectInstanceRequest request;
      hla.rti1516_202X.fedpro.LocalDeleteObjectInstanceRequest.Builder builder =
            hla.rti1516_202X.fedpro.LocalDeleteObjectInstanceRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.RequestInstanceAttributeValueUpdateRequest request;
      hla.rti1516_202X.fedpro.RequestInstanceAttributeValueUpdateRequest.Builder builder =
            hla.rti1516_202X.fedpro.RequestInstanceAttributeValueUpdateRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.RequestClassAttributeValueUpdateRequest request;
      hla.rti1516_202X.fedpro.RequestClassAttributeValueUpdateRequest.Builder builder =
            hla.rti1516_202X.fedpro.RequestClassAttributeValueUpdateRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.RequestAttributeTransportationTypeChangeRequest request;
      hla.rti1516_202X.fedpro.RequestAttributeTransportationTypeChangeRequest.Builder builder =
            hla.rti1516_202X.fedpro.RequestAttributeTransportationTypeChangeRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.QueryAttributeTransportationTypeRequest request;
      hla.rti1516_202X.fedpro.QueryAttributeTransportationTypeRequest.Builder builder =
            hla.rti1516_202X.fedpro.QueryAttributeTransportationTypeRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.RequestInteractionTransportationTypeChangeRequest request;
      hla.rti1516_202X.fedpro.RequestInteractionTransportationTypeChangeRequest.Builder builder =
            hla.rti1516_202X.fedpro.RequestInteractionTransportationTypeChangeRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.QueryInteractionTransportationTypeRequest request;
      hla.rti1516_202X.fedpro.QueryInteractionTransportationTypeRequest.Builder builder =
            hla.rti1516_202X.fedpro.QueryInteractionTransportationTypeRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.UnconditionalAttributeOwnershipDivestitureRequest request;
      hla.rti1516_202X.fedpro.UnconditionalAttributeOwnershipDivestitureRequest.Builder builder =
            hla.rti1516_202X.fedpro.UnconditionalAttributeOwnershipDivestitureRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.NegotiatedAttributeOwnershipDivestitureRequest request;
      hla.rti1516_202X.fedpro.NegotiatedAttributeOwnershipDivestitureRequest.Builder builder =
            hla.rti1516_202X.fedpro.NegotiatedAttributeOwnershipDivestitureRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.ConfirmDivestitureRequest request;
      hla.rti1516_202X.fedpro.ConfirmDivestitureRequest.Builder builder =
            hla.rti1516_202X.fedpro.ConfirmDivestitureRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributes(_clientConverter.convertFromHla(theAttributes));
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
      hla.rti1516_202X.fedpro.AttributeOwnershipAcquisitionRequest request;
      hla.rti1516_202X.fedpro.AttributeOwnershipAcquisitionRequest.Builder builder =
            hla.rti1516_202X.fedpro.AttributeOwnershipAcquisitionRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.AttributeOwnershipAcquisitionIfAvailableRequest request;
      hla.rti1516_202X.fedpro.AttributeOwnershipAcquisitionIfAvailableRequest.Builder builder =
            hla.rti1516_202X.fedpro.AttributeOwnershipAcquisitionIfAvailableRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.AttributeOwnershipReleaseDeniedRequest request;
      hla.rti1516_202X.fedpro.AttributeOwnershipReleaseDeniedRequest.Builder builder =
            hla.rti1516_202X.fedpro.AttributeOwnershipReleaseDeniedRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.AttributeOwnershipDivestitureIfWantedRequest request;
      hla.rti1516_202X.fedpro.AttributeOwnershipDivestitureIfWantedRequest.Builder builder =
            hla.rti1516_202X.fedpro.AttributeOwnershipDivestitureIfWantedRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttributes(_clientConverter.convertFromHla(theAttributes));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setAttributeOwnershipDivestitureIfWantedRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasAttributeOwnershipDivestitureIfWantedResponse();
      hla.rti1516_202X.fedpro.AttributeOwnershipDivestitureIfWantedResponse response =
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
      hla.rti1516_202X.fedpro.CancelNegotiatedAttributeOwnershipDivestitureRequest request;
      hla.rti1516_202X.fedpro.CancelNegotiatedAttributeOwnershipDivestitureRequest.Builder builder =
            hla.rti1516_202X.fedpro.CancelNegotiatedAttributeOwnershipDivestitureRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.CancelAttributeOwnershipAcquisitionRequest request;
      hla.rti1516_202X.fedpro.CancelAttributeOwnershipAcquisitionRequest.Builder builder =
            hla.rti1516_202X.fedpro.CancelAttributeOwnershipAcquisitionRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.QueryAttributeOwnershipRequest request;
      hla.rti1516_202X.fedpro.QueryAttributeOwnershipRequest.Builder builder =
            hla.rti1516_202X.fedpro.QueryAttributeOwnershipRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.IsAttributeOwnedByFederateRequest request;
      hla.rti1516_202X.fedpro.IsAttributeOwnedByFederateRequest.Builder builder =
            hla.rti1516_202X.fedpro.IsAttributeOwnedByFederateRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttribute(_clientConverter.convertFromHla(theAttribute));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setIsAttributeOwnedByFederateRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasIsAttributeOwnedByFederateResponse();
      hla.rti1516_202X.fedpro.IsAttributeOwnedByFederateResponse response =
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
      hla.rti1516_202X.fedpro.EnableTimeRegulationRequest request;
      hla.rti1516_202X.fedpro.EnableTimeRegulationRequest.Builder builder =
            hla.rti1516_202X.fedpro.EnableTimeRegulationRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.DisableTimeRegulationRequest request;
      request = hla.rti1516_202X.fedpro.DisableTimeRegulationRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.EnableTimeConstrainedRequest request;
      request = hla.rti1516_202X.fedpro.EnableTimeConstrainedRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.DisableTimeConstrainedRequest request;
      request = hla.rti1516_202X.fedpro.DisableTimeConstrainedRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.TimeAdvanceRequestRequest request;
      hla.rti1516_202X.fedpro.TimeAdvanceRequestRequest.Builder builder =
            hla.rti1516_202X.fedpro.TimeAdvanceRequestRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.TimeAdvanceRequestAvailableRequest request;
      hla.rti1516_202X.fedpro.TimeAdvanceRequestAvailableRequest.Builder builder =
            hla.rti1516_202X.fedpro.TimeAdvanceRequestAvailableRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.NextMessageRequestRequest request;
      hla.rti1516_202X.fedpro.NextMessageRequestRequest.Builder builder =
            hla.rti1516_202X.fedpro.NextMessageRequestRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.NextMessageRequestAvailableRequest request;
      hla.rti1516_202X.fedpro.NextMessageRequestAvailableRequest.Builder builder =
            hla.rti1516_202X.fedpro.NextMessageRequestAvailableRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.FlushQueueRequestRequest request;
      hla.rti1516_202X.fedpro.FlushQueueRequestRequest.Builder builder =
            hla.rti1516_202X.fedpro.FlushQueueRequestRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.EnableAsynchronousDeliveryRequest request;
      request = hla.rti1516_202X.fedpro.EnableAsynchronousDeliveryRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.DisableAsynchronousDeliveryRequest request;
      request = hla.rti1516_202X.fedpro.DisableAsynchronousDeliveryRequest.getDefaultInstance();
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
      hla.rti1516_202X.fedpro.QueryGALTRequest request;
      request = hla.rti1516_202X.fedpro.QueryGALTRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryGALTRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryGALTResponse();
      hla.rti1516_202X.fedpro.QueryGALTResponse response = callResponse.getQueryGALTResponse();
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
      hla.rti1516_202X.fedpro.QueryLogicalTimeRequest request;
      request = hla.rti1516_202X.fedpro.QueryLogicalTimeRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLogicalTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryLogicalTimeResponse();
      hla.rti1516_202X.fedpro.QueryLogicalTimeResponse response = callResponse.getQueryLogicalTimeResponse();
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
      hla.rti1516_202X.fedpro.QueryLITSRequest request;
      request = hla.rti1516_202X.fedpro.QueryLITSRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLITSRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryLITSResponse();
      hla.rti1516_202X.fedpro.QueryLITSResponse response = callResponse.getQueryLITSResponse();
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
      hla.rti1516_202X.fedpro.ModifyLookaheadRequest request;
      hla.rti1516_202X.fedpro.ModifyLookaheadRequest.Builder builder =
            hla.rti1516_202X.fedpro.ModifyLookaheadRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.QueryLookaheadRequest request;
      request = hla.rti1516_202X.fedpro.QueryLookaheadRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLookaheadRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryLookaheadResponse();
      hla.rti1516_202X.fedpro.QueryLookaheadResponse response = callResponse.getQueryLookaheadResponse();
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
      hla.rti1516_202X.fedpro.RetractRequest request;
      hla.rti1516_202X.fedpro.RetractRequest.Builder builder = hla.rti1516_202X.fedpro.RetractRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.ChangeAttributeOrderTypeRequest request;
      hla.rti1516_202X.fedpro.ChangeAttributeOrderTypeRequest.Builder builder =
            hla.rti1516_202X.fedpro.ChangeAttributeOrderTypeRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.ChangeInteractionOrderTypeRequest request;
      hla.rti1516_202X.fedpro.ChangeInteractionOrderTypeRequest.Builder builder =
            hla.rti1516_202X.fedpro.ChangeInteractionOrderTypeRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.CreateRegionRequest request;
      hla.rti1516_202X.fedpro.CreateRegionRequest.Builder builder =
            hla.rti1516_202X.fedpro.CreateRegionRequest.newBuilder();

      builder.setDimensions(_clientConverter.convertFromHla(dimensions));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCreateRegionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCreateRegionResponse();
      hla.rti1516_202X.fedpro.CreateRegionResponse response = callResponse.getCreateRegionResponse();
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
      hla.rti1516_202X.fedpro.CommitRegionModificationsRequest request;
      hla.rti1516_202X.fedpro.CommitRegionModificationsRequest.Builder builder =
            hla.rti1516_202X.fedpro.CommitRegionModificationsRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.DeleteRegionRequest request;
      hla.rti1516_202X.fedpro.DeleteRegionRequest.Builder builder =
            hla.rti1516_202X.fedpro.DeleteRegionRequest.newBuilder();

      builder.setTheRegion(_clientConverter.convertFromHla(theRegion));

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
      hla.rti1516_202X.fedpro.RegisterObjectInstanceWithRegionsRequest request;
      hla.rti1516_202X.fedpro.RegisterObjectInstanceWithRegionsRequest.Builder builder =
            hla.rti1516_202X.fedpro.RegisterObjectInstanceWithRegionsRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterObjectInstanceWithRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterObjectInstanceWithRegionsResponse();
      hla.rti1516_202X.fedpro.RegisterObjectInstanceWithRegionsResponse response =
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
      hla.rti1516_202X.fedpro.RegisterObjectInstanceWithNameAndRegionsRequest request;
      hla.rti1516_202X.fedpro.RegisterObjectInstanceWithNameAndRegionsRequest.Builder builder =
            hla.rti1516_202X.fedpro.RegisterObjectInstanceWithNameAndRegionsRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      builder.setObjectInstanceName(_clientConverter.convertFromHla(theObject));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setRegisterObjectInstanceWithNameAndRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterObjectInstanceWithNameAndRegionsResponse();
      hla.rti1516_202X.fedpro.RegisterObjectInstanceWithNameAndRegionsResponse response =
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
      hla.rti1516_202X.fedpro.AssociateRegionsForUpdatesRequest request;
      hla.rti1516_202X.fedpro.AssociateRegionsForUpdatesRequest.Builder builder =
            hla.rti1516_202X.fedpro.AssociateRegionsForUpdatesRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.UnassociateRegionsForUpdatesRequest request;
      hla.rti1516_202X.fedpro.UnassociateRegionsForUpdatesRequest.Builder builder =
            hla.rti1516_202X.fedpro.UnassociateRegionsForUpdatesRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesWithRegionsRequest request;
      hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesWithRegionsRequest.Builder builder =
            hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesWithRegionsRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesWithRegionsAndRateRequest request;
      hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesWithRegionsAndRateRequest.Builder builder =
            hla.rti1516_202X.fedpro.SubscribeObjectClassAttributesWithRegionsAndRateRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.UnsubscribeObjectClassAttributesWithRegionsRequest request;
      hla.rti1516_202X.fedpro.UnsubscribeObjectClassAttributesWithRegionsRequest.Builder builder =
            hla.rti1516_202X.fedpro.UnsubscribeObjectClassAttributesWithRegionsRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.SubscribeInteractionClassWithRegionsRequest request;
      hla.rti1516_202X.fedpro.SubscribeInteractionClassWithRegionsRequest.Builder builder =
            hla.rti1516_202X.fedpro.SubscribeInteractionClassWithRegionsRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.UnsubscribeInteractionClassWithRegionsRequest request;
      hla.rti1516_202X.fedpro.UnsubscribeInteractionClassWithRegionsRequest.Builder builder =
            hla.rti1516_202X.fedpro.UnsubscribeInteractionClassWithRegionsRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.SendInteractionWithRegionsRequest request;
      hla.rti1516_202X.fedpro.SendInteractionWithRegionsRequest.Builder builder =
            hla.rti1516_202X.fedpro.SendInteractionWithRegionsRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.SendInteractionWithRegionsAndTimeRequest request;
      hla.rti1516_202X.fedpro.SendInteractionWithRegionsAndTimeRequest.Builder builder =
            hla.rti1516_202X.fedpro.SendInteractionWithRegionsAndTimeRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theInteraction));
      builder.setParameterValues(_clientConverter.convertFromHla(theParameters));
      builder.setRegions(_clientConverter.convertFromHla(regions));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      builder.setTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionWithRegionsAndTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSendInteractionWithRegionsAndTimeResponse();
      hla.rti1516_202X.fedpro.SendInteractionWithRegionsAndTimeResponse response =
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
      hla.rti1516_202X.fedpro.RequestAttributeValueUpdateWithRegionsRequest request;
      hla.rti1516_202X.fedpro.RequestAttributeValueUpdateWithRegionsRequest.Builder builder =
            hla.rti1516_202X.fedpro.RequestAttributeValueUpdateWithRegionsRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.GetAutomaticResignDirectiveRequest request;
      request = hla.rti1516_202X.fedpro.GetAutomaticResignDirectiveRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetAutomaticResignDirectiveRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAutomaticResignDirectiveResponse();
      hla.rti1516_202X.fedpro.GetAutomaticResignDirectiveResponse response =
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
      hla.rti1516_202X.fedpro.SetAutomaticResignDirectiveRequest request;
      hla.rti1516_202X.fedpro.SetAutomaticResignDirectiveRequest.Builder builder =
            hla.rti1516_202X.fedpro.SetAutomaticResignDirectiveRequest.newBuilder();

      builder.setResignAction(_clientConverter.convertFromHla(resignAction));

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
      hla.rti1516_202X.fedpro.GetFederateHandleRequest request;
      hla.rti1516_202X.fedpro.GetFederateHandleRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetFederateHandleRequest.newBuilder();

      builder.setFederateName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetFederateHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetFederateHandleResponse();
      hla.rti1516_202X.fedpro.GetFederateHandleResponse response = callResponse.getGetFederateHandleResponse();
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
      hla.rti1516_202X.fedpro.GetFederateNameRequest request;
      hla.rti1516_202X.fedpro.GetFederateNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetFederateNameRequest.newBuilder();

      builder.setFederate(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetFederateNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetFederateNameResponse();
      hla.rti1516_202X.fedpro.GetFederateNameResponse response = callResponse.getGetFederateNameResponse();
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
      hla.rti1516_202X.fedpro.GetObjectClassHandleRequest request;
      hla.rti1516_202X.fedpro.GetObjectClassHandleRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetObjectClassHandleRequest.newBuilder();

      builder.setObjectClassName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectClassHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetObjectClassHandleResponse();
      hla.rti1516_202X.fedpro.GetObjectClassHandleResponse response = callResponse.getGetObjectClassHandleResponse();
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
      hla.rti1516_202X.fedpro.GetObjectClassNameRequest request;
      hla.rti1516_202X.fedpro.GetObjectClassNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetObjectClassNameRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectClassNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetObjectClassNameResponse();
      hla.rti1516_202X.fedpro.GetObjectClassNameResponse response = callResponse.getGetObjectClassNameResponse();
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
      hla.rti1516_202X.fedpro.GetKnownObjectClassHandleRequest request;
      hla.rti1516_202X.fedpro.GetKnownObjectClassHandleRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetKnownObjectClassHandleRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetKnownObjectClassHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetKnownObjectClassHandleResponse();
      hla.rti1516_202X.fedpro.GetKnownObjectClassHandleResponse response =
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
      hla.rti1516_202X.fedpro.GetObjectInstanceHandleRequest request;
      hla.rti1516_202X.fedpro.GetObjectInstanceHandleRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetObjectInstanceHandleRequest.newBuilder();

      builder.setObjectInstanceName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectInstanceHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetObjectInstanceHandleResponse();
      hla.rti1516_202X.fedpro.GetObjectInstanceHandleResponse response =
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
      hla.rti1516_202X.fedpro.GetObjectInstanceNameRequest request;
      hla.rti1516_202X.fedpro.GetObjectInstanceNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetObjectInstanceNameRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectInstanceNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetObjectInstanceNameResponse();
      hla.rti1516_202X.fedpro.GetObjectInstanceNameResponse response = callResponse.getGetObjectInstanceNameResponse();
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
      hla.rti1516_202X.fedpro.GetAttributeHandleRequest request;
      hla.rti1516_202X.fedpro.GetAttributeHandleRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetAttributeHandleRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(whichClass));
      builder.setAttributeName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetAttributeHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAttributeHandleResponse();
      hla.rti1516_202X.fedpro.GetAttributeHandleResponse response = callResponse.getGetAttributeHandleResponse();
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
      hla.rti1516_202X.fedpro.GetAttributeNameRequest request;
      hla.rti1516_202X.fedpro.GetAttributeNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetAttributeNameRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(whichClass));
      builder.setAttribute(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetAttributeNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAttributeNameResponse();
      hla.rti1516_202X.fedpro.GetAttributeNameResponse response = callResponse.getGetAttributeNameResponse();
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
      hla.rti1516_202X.fedpro.GetUpdateRateValueRequest request;
      hla.rti1516_202X.fedpro.GetUpdateRateValueRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetUpdateRateValueRequest.newBuilder();

      builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetUpdateRateValueRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetUpdateRateValueResponse();
      hla.rti1516_202X.fedpro.GetUpdateRateValueResponse response = callResponse.getGetUpdateRateValueResponse();
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
      hla.rti1516_202X.fedpro.GetUpdateRateValueForAttributeRequest request;
      hla.rti1516_202X.fedpro.GetUpdateRateValueForAttributeRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetUpdateRateValueForAttributeRequest.newBuilder();

      builder.setObjectInstance(_clientConverter.convertFromHla(theObject));
      builder.setAttribute(_clientConverter.convertFromHla(theAttribute));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetUpdateRateValueForAttributeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetUpdateRateValueForAttributeResponse();
      hla.rti1516_202X.fedpro.GetUpdateRateValueForAttributeResponse response =
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
      hla.rti1516_202X.fedpro.GetInteractionClassHandleRequest request;
      hla.rti1516_202X.fedpro.GetInteractionClassHandleRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetInteractionClassHandleRequest.newBuilder();

      builder.setInteractionClassName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetInteractionClassHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetInteractionClassHandleResponse();
      hla.rti1516_202X.fedpro.GetInteractionClassHandleResponse response =
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
      hla.rti1516_202X.fedpro.GetInteractionClassNameRequest request;
      hla.rti1516_202X.fedpro.GetInteractionClassNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetInteractionClassNameRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetInteractionClassNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetInteractionClassNameResponse();
      hla.rti1516_202X.fedpro.GetInteractionClassNameResponse response =
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
      hla.rti1516_202X.fedpro.GetParameterHandleRequest request;
      hla.rti1516_202X.fedpro.GetParameterHandleRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetParameterHandleRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(whichClass));
      builder.setParameterName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetParameterHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetParameterHandleResponse();
      hla.rti1516_202X.fedpro.GetParameterHandleResponse response = callResponse.getGetParameterHandleResponse();
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
      hla.rti1516_202X.fedpro.GetParameterNameRequest request;
      hla.rti1516_202X.fedpro.GetParameterNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetParameterNameRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(whichClass));
      builder.setParameter(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetParameterNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetParameterNameResponse();
      hla.rti1516_202X.fedpro.GetParameterNameResponse response = callResponse.getGetParameterNameResponse();
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
      hla.rti1516_202X.fedpro.GetOrderTypeRequest request;
      hla.rti1516_202X.fedpro.GetOrderTypeRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetOrderTypeRequest.newBuilder();

      builder.setOrderTypeName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetOrderTypeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetOrderTypeResponse();
      hla.rti1516_202X.fedpro.GetOrderTypeResponse response = callResponse.getGetOrderTypeResponse();
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
      hla.rti1516_202X.fedpro.GetOrderNameRequest request;
      hla.rti1516_202X.fedpro.GetOrderNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetOrderNameRequest.newBuilder();

      builder.setOrderType(_clientConverter.convertFromHla(theType));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetOrderNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetOrderNameResponse();
      hla.rti1516_202X.fedpro.GetOrderNameResponse response = callResponse.getGetOrderNameResponse();
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
      hla.rti1516_202X.fedpro.GetTransportationTypeHandleRequest request;
      hla.rti1516_202X.fedpro.GetTransportationTypeHandleRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetTransportationTypeHandleRequest.newBuilder();

      builder.setTransportationTypeName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetTransportationTypeHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetTransportationTypeHandleResponse();
      hla.rti1516_202X.fedpro.GetTransportationTypeHandleResponse response =
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
      hla.rti1516_202X.fedpro.GetTransportationTypeNameRequest request;
      hla.rti1516_202X.fedpro.GetTransportationTypeNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetTransportationTypeNameRequest.newBuilder();

      builder.setTransportationType(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetTransportationTypeNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetTransportationTypeNameResponse();
      hla.rti1516_202X.fedpro.GetTransportationTypeNameResponse response =
            callResponse.getGetTransportationTypeNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.DimensionHandleSet
   getAvailableDimensionsForClassAttribute(
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
      hla.rti1516_202X.fedpro.GetAvailableDimensionsForClassAttributeRequest request;
      hla.rti1516_202X.fedpro.GetAvailableDimensionsForClassAttributeRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetAvailableDimensionsForClassAttributeRequest.newBuilder();

      builder.setObjectClass(_clientConverter.convertFromHla(whichClass));
      builder.setAttribute(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setGetAvailableDimensionsForClassAttributeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAvailableDimensionsForClassAttributeResponse();
      hla.rti1516_202X.fedpro.GetAvailableDimensionsForClassAttributeResponse response =
            callResponse.getGetAvailableDimensionsForClassAttributeResponse();
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
      hla.rti1516_202X.fedpro.GetAvailableDimensionsForInteractionClassRequest request;
      hla.rti1516_202X.fedpro.GetAvailableDimensionsForInteractionClassRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetAvailableDimensionsForInteractionClassRequest.newBuilder();

      builder.setInteractionClass(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest =
            CallRequest.newBuilder().setGetAvailableDimensionsForInteractionClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAvailableDimensionsForInteractionClassResponse();
      hla.rti1516_202X.fedpro.GetAvailableDimensionsForInteractionClassResponse response =
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
      hla.rti1516_202X.fedpro.GetDimensionHandleRequest request;
      hla.rti1516_202X.fedpro.GetDimensionHandleRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetDimensionHandleRequest.newBuilder();

      builder.setDimensionName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetDimensionHandleResponse();
      hla.rti1516_202X.fedpro.GetDimensionHandleResponse response = callResponse.getGetDimensionHandleResponse();
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
      hla.rti1516_202X.fedpro.GetDimensionNameRequest request;
      hla.rti1516_202X.fedpro.GetDimensionNameRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetDimensionNameRequest.newBuilder();

      builder.setDimension(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetDimensionNameResponse();
      hla.rti1516_202X.fedpro.GetDimensionNameResponse response = callResponse.getGetDimensionNameResponse();
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
      hla.rti1516_202X.fedpro.GetDimensionUpperBoundRequest request;
      hla.rti1516_202X.fedpro.GetDimensionUpperBoundRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetDimensionUpperBoundRequest.newBuilder();

      builder.setDimension(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionUpperBoundRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetDimensionUpperBoundResponse();
      hla.rti1516_202X.fedpro.GetDimensionUpperBoundResponse response = callResponse.getGetDimensionUpperBoundResponse();
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
      hla.rti1516_202X.fedpro.GetDimensionHandleSetRequest request;
      hla.rti1516_202X.fedpro.GetDimensionHandleSetRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetDimensionHandleSetRequest.newBuilder();

      builder.setRegion(_clientConverter.convertFromHla(region));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionHandleSetRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetDimensionHandleSetResponse();
      hla.rti1516_202X.fedpro.GetDimensionHandleSetResponse response = callResponse.getGetDimensionHandleSetResponse();
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
      hla.rti1516_202X.fedpro.GetRangeBoundsRequest request;
      hla.rti1516_202X.fedpro.GetRangeBoundsRequest.Builder builder =
            hla.rti1516_202X.fedpro.GetRangeBoundsRequest.newBuilder();

      builder.setRegion(_clientConverter.convertFromHla(region));
      builder.setDimension(_clientConverter.convertFromHla(dimension));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetRangeBoundsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetRangeBoundsResponse();
      hla.rti1516_202X.fedpro.GetRangeBoundsResponse response = callResponse.getGetRangeBoundsResponse();
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
      hla.rti1516_202X.fedpro.SetRangeBoundsRequest request;
      hla.rti1516_202X.fedpro.SetRangeBoundsRequest.Builder builder =
            hla.rti1516_202X.fedpro.SetRangeBoundsRequest.newBuilder();

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
      hla.rti1516_202X.fedpro.NormalizeFederateHandleRequest request;
      hla.rti1516_202X.fedpro.NormalizeFederateHandleRequest.Builder builder =
            hla.rti1516_202X.fedpro.NormalizeFederateHandleRequest.newBuilder();

      builder.setFederate(_clientConverter.convertFromHla(federateHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNormalizeFederateHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasNormalizeFederateHandleResponse();
      hla.rti1516_202X.fedpro.NormalizeFederateHandleResponse response =
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
      hla.rti1516_202X.fedpro.NormalizeServiceGroupRequest request;
      hla.rti1516_202X.fedpro.NormalizeServiceGroupRequest.Builder builder =
            hla.rti1516_202X.fedpro.NormalizeServiceGroupRequest.newBuilder();

      builder.setServiceGroup(_clientConverter.convertFromHla(group));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNormalizeServiceGroupRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasNormalizeServiceGroupResponse();
      hla.rti1516_202X.fedpro.NormalizeServiceGroupResponse response = callResponse.getNormalizeServiceGroupResponse();
      return _clientConverter.convertToHla(Integer.toUnsignedLong(response.getResult()));
   }

   public void
   enableObjectClassRelevanceAdvisorySwitch(
   )
   throws
         ObjectClassRelevanceAdvisorySwitchIsOn,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_202X.fedpro.EnableObjectClassRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.fedpro.EnableObjectClassRelevanceAdvisorySwitchRequest.getDefaultInstance();
      CallRequest callRequest =
            CallRequest.newBuilder().setEnableObjectClassRelevanceAdvisorySwitchRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasEnableObjectClassRelevanceAdvisorySwitchResponse();
      // No return value
   }

   public void
   disableObjectClassRelevanceAdvisorySwitch(
   )
   throws
         ObjectClassRelevanceAdvisorySwitchIsOff,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_202X.fedpro.DisableObjectClassRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.fedpro.DisableObjectClassRelevanceAdvisorySwitchRequest.getDefaultInstance();
      CallRequest callRequest =
            CallRequest.newBuilder().setDisableObjectClassRelevanceAdvisorySwitchRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDisableObjectClassRelevanceAdvisorySwitchResponse();
      // No return value
   }

   public void
   enableAttributeRelevanceAdvisorySwitch(
   )
   throws
         AttributeRelevanceAdvisorySwitchIsOn,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_202X.fedpro.EnableAttributeRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.fedpro.EnableAttributeRelevanceAdvisorySwitchRequest.getDefaultInstance();
      CallRequest callRequest =
            CallRequest.newBuilder().setEnableAttributeRelevanceAdvisorySwitchRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasEnableAttributeRelevanceAdvisorySwitchResponse();
      // No return value
   }

   public void
   disableAttributeRelevanceAdvisorySwitch(
   )
   throws
         AttributeRelevanceAdvisorySwitchIsOff,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_202X.fedpro.DisableAttributeRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.fedpro.DisableAttributeRelevanceAdvisorySwitchRequest.getDefaultInstance();
      CallRequest callRequest =
            CallRequest.newBuilder().setDisableAttributeRelevanceAdvisorySwitchRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDisableAttributeRelevanceAdvisorySwitchResponse();
      // No return value
   }

   public void
   enableAttributeScopeAdvisorySwitch(
   )
   throws
         AttributeScopeAdvisorySwitchIsOn,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_202X.fedpro.EnableAttributeScopeAdvisorySwitchRequest request;
      request = hla.rti1516_202X.fedpro.EnableAttributeScopeAdvisorySwitchRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setEnableAttributeScopeAdvisorySwitchRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasEnableAttributeScopeAdvisorySwitchResponse();
      // No return value
   }

   public void
   disableAttributeScopeAdvisorySwitch(
   )
   throws
         AttributeScopeAdvisorySwitchIsOff,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_202X.fedpro.DisableAttributeScopeAdvisorySwitchRequest request;
      request = hla.rti1516_202X.fedpro.DisableAttributeScopeAdvisorySwitchRequest.getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableAttributeScopeAdvisorySwitchRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDisableAttributeScopeAdvisorySwitchResponse();
      // No return value
   }

   public void
   enableInteractionRelevanceAdvisorySwitch(
   )
   throws
         InteractionRelevanceAdvisorySwitchIsOn,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_202X.fedpro.EnableInteractionRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.fedpro.EnableInteractionRelevanceAdvisorySwitchRequest.getDefaultInstance();
      CallRequest callRequest =
            CallRequest.newBuilder().setEnableInteractionRelevanceAdvisorySwitchRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasEnableInteractionRelevanceAdvisorySwitchResponse();
      // No return value
   }

   public void
   disableInteractionRelevanceAdvisorySwitch(
   )
   throws
         InteractionRelevanceAdvisorySwitchIsOff,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      hla.rti1516_202X.fedpro.DisableInteractionRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.fedpro.DisableInteractionRelevanceAdvisorySwitchRequest.getDefaultInstance();
      CallRequest callRequest =
            CallRequest.newBuilder().setDisableInteractionRelevanceAdvisorySwitchRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDisableInteractionRelevanceAdvisorySwitchResponse();
      // No return value
   }

}
