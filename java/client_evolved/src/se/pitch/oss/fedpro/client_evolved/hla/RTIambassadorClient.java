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

import hla.rti1516_202X.thin.CallRequest;
import hla.rti1516_202X.thin.CallResponse;
import hla.rti1516e.exceptions.*;

import java.util.Collections;

public class RTIambassadorClient extends RTIambassadorClientEvolvedBase
{

   public RTIambassadorClient(ClientConverter clientConverter)
   {
      super(clientConverter);
   }

      public void
createFederationExecution(
   java.lang.String federationExecutionName,
   FomModule fomModule,
   java.lang.String logicalTimeImplementationName )
   throws
      CouldNotCreateLogicalTimeFactory,
      InconsistentFDD,
      ErrorReadingFDD,
      CouldNotOpenFDD,
      FederationExecutionAlreadyExists,
      NotConnected,
      RTIinternalError    {
     createFederationExecutionWithModules(
             federationExecutionName,
             new FomModuleSet(Collections.singleton(fomModule)),
             logicalTimeImplementationName);
   }

 public void
createFederationExecutionWithModules(
   java.lang.String federationExecutionName,
   FomModuleSet fomModules,
   java.lang.String logicalTimeImplementationName )
   throws
      CouldNotCreateLogicalTimeFactory,
      InconsistentFDD,
      ErrorReadingFDD,
      CouldNotOpenFDD,
      FederationExecutionAlreadyExists,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.CreateFederationExecutionWithModulesAndTimeRequest request;
      hla.rti1516_202X.thin.CreateFederationExecutionWithModulesAndTimeRequest. Builder builder = hla.rti1516_202X.thin.CreateFederationExecutionWithModulesAndTimeRequest. newBuilder();

      builder.setFederationExecutionName(_clientConverter.convertFromHla(federationExecutionName));
      builder.setFomModules(_clientConverter.convertFromHla(fomModules));
      builder.setLogicalTimeImplementationName(_clientConverter.convertFromHla(logicalTimeImplementationName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCreateFederationExecutionWithModulesAndTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCreateFederationExecutionWithModulesAndTimeResponse();
      // No return value
   }

 public void
createFederationExecutionWithMIM(
   java.lang.String federationExecutionName,
   FomModuleSet fomModules,
   FomModule mimModule,
   java.lang.String logicalTimeImplementationName )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.CreateFederationExecutionWithMIMAndTimeRequest request;
      hla.rti1516_202X.thin.CreateFederationExecutionWithMIMAndTimeRequest. Builder builder = hla.rti1516_202X.thin.CreateFederationExecutionWithMIMAndTimeRequest. newBuilder();

      builder.setFederationExecutionName(_clientConverter.convertFromHla(federationExecutionName));
      builder.setFomModules(_clientConverter.convertFromHla(fomModules));
      builder.setMimModule(_clientConverter.convertFromHla(mimModule));
      builder.setLogicalTimeImplementationName(_clientConverter.convertFromHla(logicalTimeImplementationName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCreateFederationExecutionWithMIMAndTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCreateFederationExecutionWithMIMAndTimeResponse();
      // No return value
   }

   public void
   destroyFederationExecution(
      java.lang.String federationExecutionName )
      throws
      FederatesCurrentlyJoined,
      FederationExecutionDoesNotExist,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.DestroyFederationExecutionRequest request;
      hla.rti1516_202X.thin.DestroyFederationExecutionRequest. Builder builder = hla.rti1516_202X.thin.DestroyFederationExecutionRequest. newBuilder();

      builder.setFederationExecutionName(_clientConverter.convertFromHla(federationExecutionName));

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
      RTIinternalError    {
      hla.rti1516_202X.thin.ListFederationExecutionsRequest request;
      request = hla.rti1516_202X.thin.ListFederationExecutionsRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setListFederationExecutionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasListFederationExecutionsResponse();
      // No return value
   }

 public JoinResult
joinFederationExecution(
   java.lang.String federateType,
   java.lang.String federationExecutionName )
   throws
      CouldNotCreateLogicalTimeFactory,
      FederationExecutionDoesNotExist,
      SaveInProgress,
      RestoreInProgress,
      FederateAlreadyExecutionMember,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError    {
      throwIfInCallback("joinFederationExecution");
      hla.rti1516_202X.thin.JoinFederationExecutionRequest request;
      hla.rti1516_202X.thin.JoinFederationExecutionRequest. Builder builder = hla.rti1516_202X.thin.JoinFederationExecutionRequest. newBuilder();

      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationExecutionName(_clientConverter.convertFromHla(federationExecutionName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setJoinFederationExecutionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionResponse();
      hla.rti1516_202X.thin.JoinFederationExecutionResponse response = callResponse.getJoinFederationExecutionResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

 public JoinResult
joinFederationExecutionWithName(
   java.lang.String federateName,
   java.lang.String federateType,
   java.lang.String federationExecutionName )
   throws
      CouldNotCreateLogicalTimeFactory,
      FederateNameAlreadyInUse,
      FederationExecutionDoesNotExist,
      SaveInProgress,
      RestoreInProgress,
      FederateAlreadyExecutionMember,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError    {
      throwIfInCallback("joinFederationExecutionWithName");
      hla.rti1516_202X.thin.JoinFederationExecutionWithNameRequest request;
      hla.rti1516_202X.thin.JoinFederationExecutionWithNameRequest. Builder builder = hla.rti1516_202X.thin.JoinFederationExecutionWithNameRequest. newBuilder();

      builder.setFederateName(_clientConverter.convertFromHla(federateName));
      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationExecutionName(_clientConverter.convertFromHla(federationExecutionName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setJoinFederationExecutionWithNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionWithNameResponse();
      hla.rti1516_202X.thin.JoinFederationExecutionWithNameResponse response = callResponse.getJoinFederationExecutionWithNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

 public JoinResult
joinFederationExecutionWithNameAndModules(
   java.lang.String federateName,
   java.lang.String federateType,
   java.lang.String federationExecutionName,
   FomModuleSet additionalFomModules )
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
      RTIinternalError    {
      throwIfInCallback("joinFederationExecutionWithNameAndModules");
      hla.rti1516_202X.thin.JoinFederationExecutionWithNameAndModulesRequest request;
      hla.rti1516_202X.thin.JoinFederationExecutionWithNameAndModulesRequest. Builder builder = hla.rti1516_202X.thin.JoinFederationExecutionWithNameAndModulesRequest. newBuilder();

      builder.setFederateName(_clientConverter.convertFromHla(federateName));
      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationExecutionName(_clientConverter.convertFromHla(federationExecutionName));
      builder.setAdditionalFomModules(_clientConverter.convertFromHla(additionalFomModules));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setJoinFederationExecutionWithNameAndModulesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionWithNameAndModulesResponse();
      hla.rti1516_202X.thin.JoinFederationExecutionWithNameAndModulesResponse response = callResponse.getJoinFederationExecutionWithNameAndModulesResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

 public JoinResult
joinFederationExecutionWithModules(
   java.lang.String federateType,
   java.lang.String federationExecutionName,
   FomModuleSet additionalFomModules )
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
      RTIinternalError    {
      throwIfInCallback("joinFederationExecutionWithModules");
      hla.rti1516_202X.thin.JoinFederationExecutionWithModulesRequest request;
      hla.rti1516_202X.thin.JoinFederationExecutionWithModulesRequest. Builder builder = hla.rti1516_202X.thin.JoinFederationExecutionWithModulesRequest. newBuilder();

      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationExecutionName(_clientConverter.convertFromHla(federationExecutionName));
      builder.setAdditionalFomModules(_clientConverter.convertFromHla(additionalFomModules));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setJoinFederationExecutionWithModulesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionWithModulesResponse();
      hla.rti1516_202X.thin.JoinFederationExecutionWithModulesResponse response = callResponse.getJoinFederationExecutionWithModulesResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   resignFederationExecution(
      hla.rti1516e.ResignAction resignAction )
      throws
      InvalidResignAction,
      OwnershipAcquisitionPending,
      FederateOwnsAttributes,
      FederateNotExecutionMember,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError    {
      throwIfInCallback("resignFederationExecution");
      hla.rti1516_202X.thin.ResignFederationExecutionRequest request;
      hla.rti1516_202X.thin.ResignFederationExecutionRequest. Builder builder = hla.rti1516_202X.thin.ResignFederationExecutionRequest. newBuilder();

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
      byte[] userSuppliedTag )
      throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.RegisterFederationSynchronizationPointRequest request;
      hla.rti1516_202X.thin.RegisterFederationSynchronizationPointRequest. Builder builder = hla.rti1516_202X.thin.RegisterFederationSynchronizationPointRequest. newBuilder();

      builder.setSynchronizationPointLabel(_clientConverter.convertFromHla(synchronizationPointLabel));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterFederationSynchronizationPointRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterFederationSynchronizationPointResponse();
      // No return value
   }

   public void
   registerFederationSynchronizationPointWithSet(
      java.lang.String synchronizationPointLabel,
      byte[] userSuppliedTag,
      hla.rti1516e.FederateHandleSet synchronizationSet )
      throws
      InvalidFederateHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.RegisterFederationSynchronizationPointWithSetRequest request;
      hla.rti1516_202X.thin.RegisterFederationSynchronizationPointWithSetRequest. Builder builder = hla.rti1516_202X.thin.RegisterFederationSynchronizationPointWithSetRequest. newBuilder();

      builder.setSynchronizationPointLabel(_clientConverter.convertFromHla(synchronizationPointLabel));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      builder.setSynchronizationSet(_clientConverter.convertFromHla(synchronizationSet));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterFederationSynchronizationPointWithSetRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterFederationSynchronizationPointWithSetResponse();
      // No return value
   }

   public void
   synchronizationPointAchieved(
      java.lang.String synchronizationPointLabel,
      boolean successIndicator )
      throws
      SynchronizationPointLabelNotAnnounced,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.SynchronizationPointAchievedRequest request;
      hla.rti1516_202X.thin.SynchronizationPointAchievedRequest. Builder builder = hla.rti1516_202X.thin.SynchronizationPointAchievedRequest. newBuilder();

      builder.setSynchronizationPointLabel(_clientConverter.convertFromHla(synchronizationPointLabel));
      builder.setSuccessIndicator(_clientConverter.convertFromHla(successIndicator));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSynchronizationPointAchievedRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSynchronizationPointAchievedResponse();
      // No return value
   }

   public void
   requestFederationSave(
      java.lang.String label )
      throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.RequestFederationSaveRequest request;
      hla.rti1516_202X.thin.RequestFederationSaveRequest. Builder builder = hla.rti1516_202X.thin.RequestFederationSaveRequest. newBuilder();

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
      hla.rti1516e.LogicalTime theTime )
      throws
      LogicalTimeAlreadyPassed,
      InvalidLogicalTime,
      FederateUnableToUseTime,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.RequestFederationSaveWithTimeRequest request;
      hla.rti1516_202X.thin.RequestFederationSaveWithTimeRequest. Builder builder = hla.rti1516_202X.thin.RequestFederationSaveWithTimeRequest. newBuilder();

      builder.setLabel(_clientConverter.convertFromHla(label));
      builder.setTheTime(_clientConverter.convertFromHla(theTime));

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
      RTIinternalError    {
      hla.rti1516_202X.thin.FederateSaveBegunRequest request;
      request = hla.rti1516_202X.thin.FederateSaveBegunRequest. getDefaultInstance();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.FederateSaveCompleteRequest request;
      request = hla.rti1516_202X.thin.FederateSaveCompleteRequest. getDefaultInstance();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.FederateSaveNotCompleteRequest request;
      request = hla.rti1516_202X.thin.FederateSaveNotCompleteRequest. getDefaultInstance();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.AbortFederationSaveRequest request;
      request = hla.rti1516_202X.thin.AbortFederationSaveRequest. getDefaultInstance();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.QueryFederationSaveStatusRequest request;
      request = hla.rti1516_202X.thin.QueryFederationSaveStatusRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryFederationSaveStatusRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryFederationSaveStatusResponse();
      // No return value
   }

   public void
   requestFederationRestore(
      java.lang.String label )
      throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.RequestFederationRestoreRequest request;
      hla.rti1516_202X.thin.RequestFederationRestoreRequest. Builder builder = hla.rti1516_202X.thin.RequestFederationRestoreRequest. newBuilder();

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
      RTIinternalError    {
      hla.rti1516_202X.thin.FederateRestoreCompleteRequest request;
      request = hla.rti1516_202X.thin.FederateRestoreCompleteRequest. getDefaultInstance();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.FederateRestoreNotCompleteRequest request;
      request = hla.rti1516_202X.thin.FederateRestoreNotCompleteRequest. getDefaultInstance();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.AbortFederationRestoreRequest request;
      request = hla.rti1516_202X.thin.AbortFederationRestoreRequest. getDefaultInstance();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.QueryFederationRestoreStatusRequest request;
      request = hla.rti1516_202X.thin.QueryFederationRestoreStatusRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryFederationRestoreStatusRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryFederationRestoreStatusResponse();
      // No return value
   }

   public void
   publishObjectClassAttributes(
      hla.rti1516e.ObjectClassHandle theClass,
      hla.rti1516e.AttributeHandleSet attributeList )
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.PublishObjectClassAttributesRequest request;
      hla.rti1516_202X.thin.PublishObjectClassAttributesRequest. Builder builder = hla.rti1516_202X.thin.PublishObjectClassAttributesRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributeList(_clientConverter.convertFromHla(attributeList));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setPublishObjectClassAttributesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasPublishObjectClassAttributesResponse();
      // No return value
   }

   public void
   unpublishObjectClass(
      hla.rti1516e.ObjectClassHandle theClass )
      throws
      OwnershipAcquisitionPending,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.UnpublishObjectClassRequest request;
      hla.rti1516_202X.thin.UnpublishObjectClassRequest. Builder builder = hla.rti1516_202X.thin.UnpublishObjectClassRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishObjectClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnpublishObjectClassResponse();
      // No return value
   }

   public void
   unpublishObjectClassAttributes(
      hla.rti1516e.ObjectClassHandle theClass,
      hla.rti1516e.AttributeHandleSet attributeList )
      throws
      OwnershipAcquisitionPending,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.UnpublishObjectClassAttributesRequest request;
      hla.rti1516_202X.thin.UnpublishObjectClassAttributesRequest. Builder builder = hla.rti1516_202X.thin.UnpublishObjectClassAttributesRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributeList(_clientConverter.convertFromHla(attributeList));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishObjectClassAttributesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnpublishObjectClassAttributesResponse();
      // No return value
   }

   public void
   publishInteractionClass(
      hla.rti1516e.InteractionClassHandle theInteraction )
      throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.PublishInteractionClassRequest request;
      hla.rti1516_202X.thin.PublishInteractionClassRequest. Builder builder = hla.rti1516_202X.thin.PublishInteractionClassRequest. newBuilder();

      builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setPublishInteractionClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasPublishInteractionClassResponse();
      // No return value
   }

   public void
   unpublishInteractionClass(
      hla.rti1516e.InteractionClassHandle theInteraction )
      throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.UnpublishInteractionClassRequest request;
      hla.rti1516_202X.thin.UnpublishInteractionClassRequest. Builder builder = hla.rti1516_202X.thin.UnpublishInteractionClassRequest. newBuilder();

      builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishInteractionClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnpublishInteractionClassResponse();
      // No return value
   }

   public void
   subscribeObjectClassAttributes(
      hla.rti1516e.ObjectClassHandle theClass,
      hla.rti1516e.AttributeHandleSet attributeList )
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesRequest request;
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesRequest. Builder builder = hla.rti1516_202X.thin.SubscribeObjectClassAttributesRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributeList(_clientConverter.convertFromHla(attributeList));

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
      java.lang.String updateRateDesignator )
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      InvalidUpdateRateDesignator,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRateRequest request;
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRateRequest. Builder builder = hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRateRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributeList(_clientConverter.convertFromHla(attributeList));
      builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesWithRateRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeObjectClassAttributesWithRateResponse();
      // No return value
   }

   public void
   subscribeObjectClassAttributesPassively(
      hla.rti1516e.ObjectClassHandle theClass,
      hla.rti1516e.AttributeHandleSet attributeList )
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesPassivelyRequest request;
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesPassivelyRequest. Builder builder = hla.rti1516_202X.thin.SubscribeObjectClassAttributesPassivelyRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributeList(_clientConverter.convertFromHla(attributeList));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesPassivelyRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeObjectClassAttributesPassivelyResponse();
      // No return value
   }

   public void
   subscribeObjectClassAttributesPassivelyWithRate(
      hla.rti1516e.ObjectClassHandle theClass,
      hla.rti1516e.AttributeHandleSet attributeList,
      java.lang.String updateRateDesignator )
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      InvalidUpdateRateDesignator,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesPassivelyWithRateRequest request;
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesPassivelyWithRateRequest. Builder builder = hla.rti1516_202X.thin.SubscribeObjectClassAttributesPassivelyWithRateRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributeList(_clientConverter.convertFromHla(attributeList));
      builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesPassivelyWithRateRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeObjectClassAttributesPassivelyWithRateResponse();
      // No return value
   }

   public void
   unsubscribeObjectClass(
      hla.rti1516e.ObjectClassHandle theClass )
      throws
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.UnsubscribeObjectClassRequest request;
      hla.rti1516_202X.thin.UnsubscribeObjectClassRequest. Builder builder = hla.rti1516_202X.thin.UnsubscribeObjectClassRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnsubscribeObjectClassResponse();
      // No return value
   }

   public void
   unsubscribeObjectClassAttributes(
      hla.rti1516e.ObjectClassHandle theClass,
      hla.rti1516e.AttributeHandleSet attributeList )
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.UnsubscribeObjectClassAttributesRequest request;
      hla.rti1516_202X.thin.UnsubscribeObjectClassAttributesRequest. Builder builder = hla.rti1516_202X.thin.UnsubscribeObjectClassAttributesRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributeList(_clientConverter.convertFromHla(attributeList));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassAttributesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnsubscribeObjectClassAttributesResponse();
      // No return value
   }

   public void
   subscribeInteractionClass(
      hla.rti1516e.InteractionClassHandle theClass )
      throws
      FederateServiceInvocationsAreBeingReportedViaMOM,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.SubscribeInteractionClassRequest request;
      hla.rti1516_202X.thin.SubscribeInteractionClassRequest. Builder builder = hla.rti1516_202X.thin.SubscribeInteractionClassRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeInteractionClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeInteractionClassResponse();
      // No return value
   }

   public void
   subscribeInteractionClassPassively(
      hla.rti1516e.InteractionClassHandle theClass )
      throws
      FederateServiceInvocationsAreBeingReportedViaMOM,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.SubscribeInteractionClassPassivelyRequest request;
      hla.rti1516_202X.thin.SubscribeInteractionClassPassivelyRequest. Builder builder = hla.rti1516_202X.thin.SubscribeInteractionClassPassivelyRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeInteractionClassPassivelyRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeInteractionClassPassivelyResponse();
      // No return value
   }

   public void
   unsubscribeInteractionClass(
      hla.rti1516e.InteractionClassHandle theClass )
      throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.UnsubscribeInteractionClassRequest request;
      hla.rti1516_202X.thin.UnsubscribeInteractionClassRequest. Builder builder = hla.rti1516_202X.thin.UnsubscribeInteractionClassRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeInteractionClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnsubscribeInteractionClassResponse();
      // No return value
   }

   public void
   reserveObjectInstanceName(
      java.lang.String theObjectName )
      throws
      IllegalName,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.ReserveObjectInstanceNameRequest request;
      hla.rti1516_202X.thin.ReserveObjectInstanceNameRequest. Builder builder = hla.rti1516_202X.thin.ReserveObjectInstanceNameRequest. newBuilder();

      builder.setTheObjectName(_clientConverter.convertFromHla(theObjectName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReserveObjectInstanceNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasReserveObjectInstanceNameResponse();
      // No return value
   }

   public void
   releaseObjectInstanceName(
      java.lang.String theObjectInstanceName )
      throws
      ObjectInstanceNameNotReserved,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.ReleaseObjectInstanceNameRequest request;
      hla.rti1516_202X.thin.ReleaseObjectInstanceNameRequest. Builder builder = hla.rti1516_202X.thin.ReleaseObjectInstanceNameRequest. newBuilder();

      builder.setTheObjectInstanceName(_clientConverter.convertFromHla(theObjectInstanceName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReleaseObjectInstanceNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasReleaseObjectInstanceNameResponse();
      // No return value
   }

   public void
   reserveMultipleObjectInstanceName(
      java.util.Set<java.lang.String> theObjectNames )
      throws
      IllegalName,
      NameSetWasEmpty,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.ReserveMultipleObjectInstanceNameRequest request;
      hla.rti1516_202X.thin.ReserveMultipleObjectInstanceNameRequest. Builder builder = hla.rti1516_202X.thin.ReserveMultipleObjectInstanceNameRequest. newBuilder();

      builder.addAllTheObjectNames(_clientConverter.convertFromHla(theObjectNames));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReserveMultipleObjectInstanceNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasReserveMultipleObjectInstanceNameResponse();
      // No return value
   }

   public void
   releaseMultipleObjectInstanceName(
      java.util.Set<java.lang.String> theObjectNames )
      throws
      ObjectInstanceNameNotReserved,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.ReleaseMultipleObjectInstanceNameRequest request;
      hla.rti1516_202X.thin.ReleaseMultipleObjectInstanceNameRequest. Builder builder = hla.rti1516_202X.thin.ReleaseMultipleObjectInstanceNameRequest. newBuilder();

      builder.addAllTheObjectNames(_clientConverter.convertFromHla(theObjectNames));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReleaseMultipleObjectInstanceNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasReleaseMultipleObjectInstanceNameResponse();
      // No return value
   }

   public hla.rti1516e.ObjectInstanceHandle
   registerObjectInstance(
      hla.rti1516e.ObjectClassHandle theClass )
      throws
      ObjectClassNotPublished,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.RegisterObjectInstanceRequest request;
      hla.rti1516_202X.thin.RegisterObjectInstanceRequest. Builder builder = hla.rti1516_202X.thin.RegisterObjectInstanceRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterObjectInstanceRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterObjectInstanceResponse();
      hla.rti1516_202X.thin.RegisterObjectInstanceResponse response = callResponse.getRegisterObjectInstanceResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.ObjectInstanceHandle
   registerObjectInstanceWithName(
      hla.rti1516e.ObjectClassHandle theClass,
      java.lang.String theObjectName )
      throws
      ObjectInstanceNameInUse,
      ObjectInstanceNameNotReserved,
      ObjectClassNotPublished,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.RegisterObjectInstanceWithNameRequest request;
      hla.rti1516_202X.thin.RegisterObjectInstanceWithNameRequest. Builder builder = hla.rti1516_202X.thin.RegisterObjectInstanceWithNameRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setTheObjectName(_clientConverter.convertFromHla(theObjectName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterObjectInstanceWithNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterObjectInstanceWithNameResponse();
      hla.rti1516_202X.thin.RegisterObjectInstanceWithNameResponse response = callResponse.getRegisterObjectInstanceWithNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   updateAttributeValues(
      hla.rti1516e.ObjectInstanceHandle theObject,
      hla.rti1516e.AttributeHandleValueMap theAttributes,
      byte[] userSuppliedTag )
      throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.UpdateAttributeValuesRequest request;
      hla.rti1516_202X.thin.UpdateAttributeValuesRequest. Builder builder = hla.rti1516_202X.thin.UpdateAttributeValuesRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
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
      hla.rti1516e.LogicalTime theTime )
      throws
      InvalidLogicalTime,
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.UpdateAttributeValuesWithTimeRequest request;
      hla.rti1516_202X.thin.UpdateAttributeValuesWithTimeRequest. Builder builder = hla.rti1516_202X.thin.UpdateAttributeValuesWithTimeRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      builder.setTheTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUpdateAttributeValuesWithTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUpdateAttributeValuesWithTimeResponse();
      hla.rti1516_202X.thin.UpdateAttributeValuesWithTimeResponse response = callResponse.getUpdateAttributeValuesWithTimeResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   sendInteraction(
      hla.rti1516e.InteractionClassHandle theInteraction,
      hla.rti1516e.ParameterHandleValueMap theParameters,
      byte[] userSuppliedTag )
      throws
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.SendInteractionRequest request;
      hla.rti1516_202X.thin.SendInteractionRequest. Builder builder = hla.rti1516_202X.thin.SendInteractionRequest. newBuilder();

      builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));
      builder.setTheParameters(_clientConverter.convertFromHla(theParameters));
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
      hla.rti1516e.LogicalTime theTime )
      throws
      InvalidLogicalTime,
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.SendInteractionWithTimeRequest request;
      hla.rti1516_202X.thin.SendInteractionWithTimeRequest. Builder builder = hla.rti1516_202X.thin.SendInteractionWithTimeRequest. newBuilder();

      builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));
      builder.setTheParameters(_clientConverter.convertFromHla(theParameters));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      builder.setTheTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionWithTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSendInteractionWithTimeResponse();
      hla.rti1516_202X.thin.SendInteractionWithTimeResponse response = callResponse.getSendInteractionWithTimeResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   deleteObjectInstance(
      hla.rti1516e.ObjectInstanceHandle objectHandle,
      byte[] userSuppliedTag )
      throws
      DeletePrivilegeNotHeld,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.DeleteObjectInstanceRequest request;
      hla.rti1516_202X.thin.DeleteObjectInstanceRequest. Builder builder = hla.rti1516_202X.thin.DeleteObjectInstanceRequest. newBuilder();

      builder.setObjectHandle(_clientConverter.convertFromHla(objectHandle));
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
      hla.rti1516e.LogicalTime theTime )
      throws
      InvalidLogicalTime,
      DeletePrivilegeNotHeld,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.DeleteObjectInstanceWithTimeRequest request;
      hla.rti1516_202X.thin.DeleteObjectInstanceWithTimeRequest. Builder builder = hla.rti1516_202X.thin.DeleteObjectInstanceWithTimeRequest. newBuilder();

      builder.setObjectHandle(_clientConverter.convertFromHla(objectHandle));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      builder.setTheTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setDeleteObjectInstanceWithTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDeleteObjectInstanceWithTimeResponse();
      hla.rti1516_202X.thin.DeleteObjectInstanceWithTimeResponse response = callResponse.getDeleteObjectInstanceWithTimeResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   localDeleteObjectInstance(
      hla.rti1516e.ObjectInstanceHandle objectHandle )
      throws
      OwnershipAcquisitionPending,
      FederateOwnsAttributes,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.LocalDeleteObjectInstanceRequest request;
      hla.rti1516_202X.thin.LocalDeleteObjectInstanceRequest. Builder builder = hla.rti1516_202X.thin.LocalDeleteObjectInstanceRequest. newBuilder();

      builder.setObjectHandle(_clientConverter.convertFromHla(objectHandle));

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
      byte[] userSuppliedTag )
      throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.RequestInstanceAttributeValueUpdateRequest request;
      hla.rti1516_202X.thin.RequestInstanceAttributeValueUpdateRequest. Builder builder = hla.rti1516_202X.thin.RequestInstanceAttributeValueUpdateRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
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
      byte[] userSuppliedTag )
      throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.RequestClassAttributeValueUpdateRequest request;
      hla.rti1516_202X.thin.RequestClassAttributeValueUpdateRequest. Builder builder = hla.rti1516_202X.thin.RequestClassAttributeValueUpdateRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
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
      hla.rti1516e.TransportationTypeHandle theType )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.RequestAttributeTransportationTypeChangeRequest request;
      hla.rti1516_202X.thin.RequestAttributeTransportationTypeChangeRequest. Builder builder = hla.rti1516_202X.thin.RequestAttributeTransportationTypeChangeRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
      builder.setTheType(_clientConverter.convertFromHla(theType));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestAttributeTransportationTypeChangeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRequestAttributeTransportationTypeChangeResponse();
      // No return value
   }

   public void
   queryAttributeTransportationType(
      hla.rti1516e.ObjectInstanceHandle theObject,
      hla.rti1516e.AttributeHandle theAttribute )
      throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.QueryAttributeTransportationTypeRequest request;
      hla.rti1516_202X.thin.QueryAttributeTransportationTypeRequest. Builder builder = hla.rti1516_202X.thin.QueryAttributeTransportationTypeRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttribute(_clientConverter.convertFromHla(theAttribute));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setQueryAttributeTransportationTypeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryAttributeTransportationTypeResponse();
      // No return value
   }

   public void
   requestInteractionTransportationTypeChange(
      hla.rti1516e.InteractionClassHandle theClass,
      hla.rti1516e.TransportationTypeHandle theType )
      throws
      InteractionClassAlreadyBeingChanged,
      InteractionClassNotPublished,
      InteractionClassNotDefined,
      InvalidTransportationType,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.RequestInteractionTransportationTypeChangeRequest request;
      hla.rti1516_202X.thin.RequestInteractionTransportationTypeChangeRequest. Builder builder = hla.rti1516_202X.thin.RequestInteractionTransportationTypeChangeRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setTheType(_clientConverter.convertFromHla(theType));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestInteractionTransportationTypeChangeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRequestInteractionTransportationTypeChangeResponse();
      // No return value
   }

   public void
   queryInteractionTransportationType(
      hla.rti1516e.FederateHandle theFederate,
      hla.rti1516e.InteractionClassHandle theInteraction )
      throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.QueryInteractionTransportationTypeRequest request;
      hla.rti1516_202X.thin.QueryInteractionTransportationTypeRequest. Builder builder = hla.rti1516_202X.thin.QueryInteractionTransportationTypeRequest. newBuilder();

      builder.setTheFederate(_clientConverter.convertFromHla(theFederate));
      builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setQueryInteractionTransportationTypeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryInteractionTransportationTypeResponse();
      // No return value
   }

   public void
   unconditionalAttributeOwnershipDivestiture(
      hla.rti1516e.ObjectInstanceHandle theObject,
      hla.rti1516e.AttributeHandleSet theAttributes )
      throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.UnconditionalAttributeOwnershipDivestitureRequest request;
      hla.rti1516_202X.thin.UnconditionalAttributeOwnershipDivestitureRequest. Builder builder = hla.rti1516_202X.thin.UnconditionalAttributeOwnershipDivestitureRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnconditionalAttributeOwnershipDivestitureRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnconditionalAttributeOwnershipDivestitureResponse();
      // No return value
   }

   public void
   negotiatedAttributeOwnershipDivestiture(
      hla.rti1516e.ObjectInstanceHandle theObject,
      hla.rti1516e.AttributeHandleSet theAttributes,
      byte[] userSuppliedTag )
      throws
      AttributeAlreadyBeingDivested,
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.NegotiatedAttributeOwnershipDivestitureRequest request;
      hla.rti1516_202X.thin.NegotiatedAttributeOwnershipDivestitureRequest. Builder builder = hla.rti1516_202X.thin.NegotiatedAttributeOwnershipDivestitureRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNegotiatedAttributeOwnershipDivestitureRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasNegotiatedAttributeOwnershipDivestitureResponse();
      // No return value
   }

   public void
   confirmDivestiture(
      hla.rti1516e.ObjectInstanceHandle theObject,
      hla.rti1516e.AttributeHandleSet theAttributes,
      byte[] userSuppliedTag )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.ConfirmDivestitureRequest request;
      hla.rti1516_202X.thin.ConfirmDivestitureRequest. Builder builder = hla.rti1516_202X.thin.ConfirmDivestitureRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
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
      byte[] userSuppliedTag )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.AttributeOwnershipAcquisitionRequest request;
      hla.rti1516_202X.thin.AttributeOwnershipAcquisitionRequest. Builder builder = hla.rti1516_202X.thin.AttributeOwnershipAcquisitionRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
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
      hla.rti1516e.AttributeHandleSet desiredAttributes )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.AttributeOwnershipAcquisitionIfAvailableRequest request;
      hla.rti1516_202X.thin.AttributeOwnershipAcquisitionIfAvailableRequest. Builder builder = hla.rti1516_202X.thin.AttributeOwnershipAcquisitionIfAvailableRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setDesiredAttributes(_clientConverter.convertFromHla(desiredAttributes));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAttributeOwnershipAcquisitionIfAvailableRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasAttributeOwnershipAcquisitionIfAvailableResponse();
      // No return value
   }

   public void
   attributeOwnershipReleaseDenied(
      hla.rti1516e.ObjectInstanceHandle theObject,
      hla.rti1516e.AttributeHandleSet theAttributes )
      throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.AttributeOwnershipReleaseDeniedRequest request;
      hla.rti1516_202X.thin.AttributeOwnershipReleaseDeniedRequest. Builder builder = hla.rti1516_202X.thin.AttributeOwnershipReleaseDeniedRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAttributeOwnershipReleaseDeniedRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasAttributeOwnershipReleaseDeniedResponse();
      // No return value
   }

   public hla.rti1516e.AttributeHandleSet
   attributeOwnershipDivestitureIfWanted(
      hla.rti1516e.ObjectInstanceHandle theObject,
      hla.rti1516e.AttributeHandleSet theAttributes )
      throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.AttributeOwnershipDivestitureIfWantedRequest request;
      hla.rti1516_202X.thin.AttributeOwnershipDivestitureIfWantedRequest. Builder builder = hla.rti1516_202X.thin.AttributeOwnershipDivestitureIfWantedRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAttributeOwnershipDivestitureIfWantedRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasAttributeOwnershipDivestitureIfWantedResponse();
      hla.rti1516_202X.thin.AttributeOwnershipDivestitureIfWantedResponse response = callResponse.getAttributeOwnershipDivestitureIfWantedResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   cancelNegotiatedAttributeOwnershipDivestiture(
      hla.rti1516e.ObjectInstanceHandle theObject,
      hla.rti1516e.AttributeHandleSet theAttributes )
      throws
      AttributeDivestitureWasNotRequested,
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.CancelNegotiatedAttributeOwnershipDivestitureRequest request;
      hla.rti1516_202X.thin.CancelNegotiatedAttributeOwnershipDivestitureRequest. Builder builder = hla.rti1516_202X.thin.CancelNegotiatedAttributeOwnershipDivestitureRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCancelNegotiatedAttributeOwnershipDivestitureRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCancelNegotiatedAttributeOwnershipDivestitureResponse();
      // No return value
   }

   public void
   cancelAttributeOwnershipAcquisition(
      hla.rti1516e.ObjectInstanceHandle theObject,
      hla.rti1516e.AttributeHandleSet theAttributes )
      throws
      AttributeAcquisitionWasNotRequested,
      AttributeAlreadyOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.CancelAttributeOwnershipAcquisitionRequest request;
      hla.rti1516_202X.thin.CancelAttributeOwnershipAcquisitionRequest. Builder builder = hla.rti1516_202X.thin.CancelAttributeOwnershipAcquisitionRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCancelAttributeOwnershipAcquisitionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCancelAttributeOwnershipAcquisitionResponse();
      // No return value
   }

   public void
   queryAttributeOwnership(
      hla.rti1516e.ObjectInstanceHandle theObject,
      hla.rti1516e.AttributeHandle theAttribute )
      throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.QueryAttributeOwnershipRequest request;
      hla.rti1516_202X.thin.QueryAttributeOwnershipRequest. Builder builder = hla.rti1516_202X.thin.QueryAttributeOwnershipRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttributes(_clientConverter.convertFromHlaToSet(theAttribute));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setQueryAttributeOwnershipRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryAttributeOwnershipResponse();
      // No return value
   }

   public boolean
   isAttributeOwnedByFederate(
      hla.rti1516e.ObjectInstanceHandle theObject,
      hla.rti1516e.AttributeHandle theAttribute )
      throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.IsAttributeOwnedByFederateRequest request;
      hla.rti1516_202X.thin.IsAttributeOwnedByFederateRequest. Builder builder = hla.rti1516_202X.thin.IsAttributeOwnedByFederateRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttribute(_clientConverter.convertFromHla(theAttribute));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setIsAttributeOwnedByFederateRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasIsAttributeOwnedByFederateResponse();
      hla.rti1516_202X.thin.IsAttributeOwnedByFederateResponse response = callResponse.getIsAttributeOwnedByFederateResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   enableTimeRegulation(
      hla.rti1516e.LogicalTimeInterval theLookahead )
      throws
      InvalidLookahead,
      InTimeAdvancingState,
      RequestForTimeRegulationPending,
      TimeRegulationAlreadyEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.EnableTimeRegulationRequest request;
      hla.rti1516_202X.thin.EnableTimeRegulationRequest. Builder builder = hla.rti1516_202X.thin.EnableTimeRegulationRequest. newBuilder();

      builder.setTheLookahead(_clientConverter.convertFromHla(theLookahead));

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
      RTIinternalError    {
      hla.rti1516_202X.thin.DisableTimeRegulationRequest request;
      request = hla.rti1516_202X.thin.DisableTimeRegulationRequest. getDefaultInstance();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.EnableTimeConstrainedRequest request;
      request = hla.rti1516_202X.thin.EnableTimeConstrainedRequest. getDefaultInstance();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.DisableTimeConstrainedRequest request;
      request = hla.rti1516_202X.thin.DisableTimeConstrainedRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableTimeConstrainedRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDisableTimeConstrainedResponse();
      // No return value
   }

   public void
   timeAdvanceRequest(
      hla.rti1516e.LogicalTime theTime )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.TimeAdvanceRequestRequest request;
      hla.rti1516_202X.thin.TimeAdvanceRequestRequest. Builder builder = hla.rti1516_202X.thin.TimeAdvanceRequestRequest. newBuilder();

      builder.setTheTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setTimeAdvanceRequestRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasTimeAdvanceRequestResponse();
      // No return value
   }

   public void
   timeAdvanceRequestAvailable(
      hla.rti1516e.LogicalTime theTime )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.TimeAdvanceRequestAvailableRequest request;
      hla.rti1516_202X.thin.TimeAdvanceRequestAvailableRequest. Builder builder = hla.rti1516_202X.thin.TimeAdvanceRequestAvailableRequest. newBuilder();

      builder.setTheTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setTimeAdvanceRequestAvailableRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasTimeAdvanceRequestAvailableResponse();
      // No return value
   }

   public void
   nextMessageRequest(
      hla.rti1516e.LogicalTime theTime )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.NextMessageRequestRequest request;
      hla.rti1516_202X.thin.NextMessageRequestRequest. Builder builder = hla.rti1516_202X.thin.NextMessageRequestRequest. newBuilder();

      builder.setTheTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNextMessageRequestRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasNextMessageRequestResponse();
      // No return value
   }

   public void
   nextMessageRequestAvailable(
      hla.rti1516e.LogicalTime theTime )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.NextMessageRequestAvailableRequest request;
      hla.rti1516_202X.thin.NextMessageRequestAvailableRequest. Builder builder = hla.rti1516_202X.thin.NextMessageRequestAvailableRequest. newBuilder();

      builder.setTheTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNextMessageRequestAvailableRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasNextMessageRequestAvailableResponse();
      // No return value
   }

   public void
   flushQueueRequest(
      hla.rti1516e.LogicalTime theTime )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.FlushQueueRequestRequest request;
      hla.rti1516_202X.thin.FlushQueueRequestRequest. Builder builder = hla.rti1516_202X.thin.FlushQueueRequestRequest. newBuilder();

      builder.setTheTime(_clientConverter.convertFromHla(theTime));

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
      RTIinternalError    {
      hla.rti1516_202X.thin.EnableAsynchronousDeliveryRequest request;
      request = hla.rti1516_202X.thin.EnableAsynchronousDeliveryRequest. getDefaultInstance();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.DisableAsynchronousDeliveryRequest request;
      request = hla.rti1516_202X.thin.DisableAsynchronousDeliveryRequest. getDefaultInstance();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.QueryGALTRequest request;
      request = hla.rti1516_202X.thin.QueryGALTRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryGALTRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryGALTResponse();
      hla.rti1516_202X.thin.QueryGALTResponse response = callResponse.getQueryGALTResponse();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.QueryLogicalTimeRequest request;
      request = hla.rti1516_202X.thin.QueryLogicalTimeRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLogicalTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryLogicalTimeResponse();
      hla.rti1516_202X.thin.QueryLogicalTimeResponse response = callResponse.getQueryLogicalTimeResponse();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.QueryLITSRequest request;
      request = hla.rti1516_202X.thin.QueryLITSRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLITSRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryLITSResponse();
      hla.rti1516_202X.thin.QueryLITSResponse response = callResponse.getQueryLITSResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   modifyLookahead(
      hla.rti1516e.LogicalTimeInterval theLookahead )
      throws
      InvalidLookahead,
      InTimeAdvancingState,
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.ModifyLookaheadRequest request;
      hla.rti1516_202X.thin.ModifyLookaheadRequest. Builder builder = hla.rti1516_202X.thin.ModifyLookaheadRequest. newBuilder();

      builder.setTheLookahead(_clientConverter.convertFromHla(theLookahead));

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
      RTIinternalError    {
      hla.rti1516_202X.thin.QueryLookaheadRequest request;
      request = hla.rti1516_202X.thin.QueryLookaheadRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLookaheadRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasQueryLookaheadResponse();
      hla.rti1516_202X.thin.QueryLookaheadResponse response = callResponse.getQueryLookaheadResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   retract(
      hla.rti1516e.MessageRetractionHandle theHandle )
      throws
      MessageCanNoLongerBeRetracted,
      InvalidMessageRetractionHandle,
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.RetractRequest request;
      hla.rti1516_202X.thin.RetractRequest. Builder builder = hla.rti1516_202X.thin.RetractRequest. newBuilder();

      builder.setTheHandle(_clientConverter.convertFromHla(theHandle));

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
      hla.rti1516e.OrderType theType )
      throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.ChangeAttributeOrderTypeRequest request;
      hla.rti1516_202X.thin.ChangeAttributeOrderTypeRequest. Builder builder = hla.rti1516_202X.thin.ChangeAttributeOrderTypeRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
      builder.setTheType(_clientConverter.convertFromHla(theType));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setChangeAttributeOrderTypeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasChangeAttributeOrderTypeResponse();
      // No return value
   }

   public void
   changeInteractionOrderType(
      hla.rti1516e.InteractionClassHandle theClass,
      hla.rti1516e.OrderType theType )
      throws
      InteractionClassNotPublished,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.ChangeInteractionOrderTypeRequest request;
      hla.rti1516_202X.thin.ChangeInteractionOrderTypeRequest. Builder builder = hla.rti1516_202X.thin.ChangeInteractionOrderTypeRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setTheType(_clientConverter.convertFromHla(theType));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setChangeInteractionOrderTypeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasChangeInteractionOrderTypeResponse();
      // No return value
   }

   public hla.rti1516e.RegionHandle
   createRegion(
      hla.rti1516e.DimensionHandleSet dimensions )
      throws
      InvalidDimensionHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.CreateRegionRequest request;
      hla.rti1516_202X.thin.CreateRegionRequest. Builder builder = hla.rti1516_202X.thin.CreateRegionRequest. newBuilder();

      builder.setDimensions(_clientConverter.convertFromHla(dimensions));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCreateRegionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCreateRegionResponse();
      hla.rti1516_202X.thin.CreateRegionResponse response = callResponse.getCreateRegionResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   commitRegionModifications(
      hla.rti1516e.RegionHandleSet regions )
      throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.CommitRegionModificationsRequest request;
      hla.rti1516_202X.thin.CommitRegionModificationsRequest. Builder builder = hla.rti1516_202X.thin.CommitRegionModificationsRequest. newBuilder();

      builder.setRegions(_clientConverter.convertFromHla(regions));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCommitRegionModificationsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCommitRegionModificationsResponse();
      // No return value
   }

   public void
   deleteRegion(
      hla.rti1516e.RegionHandle theRegion )
      throws
      RegionInUseForUpdateOrSubscription,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.DeleteRegionRequest request;
      hla.rti1516_202X.thin.DeleteRegionRequest. Builder builder = hla.rti1516_202X.thin.DeleteRegionRequest. newBuilder();

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
      hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.RegisterObjectInstanceWithRegionsRequest request;
      hla.rti1516_202X.thin.RegisterObjectInstanceWithRegionsRequest. Builder builder = hla.rti1516_202X.thin.RegisterObjectInstanceWithRegionsRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterObjectInstanceWithRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterObjectInstanceWithRegionsResponse();
      hla.rti1516_202X.thin.RegisterObjectInstanceWithRegionsResponse response = callResponse.getRegisterObjectInstanceWithRegionsResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.ObjectInstanceHandle
   registerObjectInstanceWithNameAndRegions(
      hla.rti1516e.ObjectClassHandle theClass,
      hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions,
      java.lang.String theObject )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.RegisterObjectInstanceWithNameAndRegionsRequest request;
      hla.rti1516_202X.thin.RegisterObjectInstanceWithNameAndRegionsRequest. Builder builder = hla.rti1516_202X.thin.RegisterObjectInstanceWithNameAndRegionsRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      builder.setTheObject(_clientConverter.convertFromHla(theObject));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterObjectInstanceWithNameAndRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasRegisterObjectInstanceWithNameAndRegionsResponse();
      hla.rti1516_202X.thin.RegisterObjectInstanceWithNameAndRegionsResponse response = callResponse.getRegisterObjectInstanceWithNameAndRegionsResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   associateRegionsForUpdates(
      hla.rti1516e.ObjectInstanceHandle theObject,
      hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.AssociateRegionsForUpdatesRequest request;
      hla.rti1516_202X.thin.AssociateRegionsForUpdatesRequest. Builder builder = hla.rti1516_202X.thin.AssociateRegionsForUpdatesRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
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
      hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions )
      throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.UnassociateRegionsForUpdatesRequest request;
      hla.rti1516_202X.thin.UnassociateRegionsForUpdatesRequest. Builder builder = hla.rti1516_202X.thin.UnassociateRegionsForUpdatesRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
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
      boolean active )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRegionsRequest request;
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRegionsRequest. Builder builder = hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRegionsRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      builder.setActive(_clientConverter.convertFromHla(active));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesWithRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeObjectClassAttributesWithRegionsResponse();
      // No return value
   }

   public void
   subscribeObjectClassAttributesWithRegionsAndRate(
      hla.rti1516e.ObjectClassHandle theClass,
      hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions,
      boolean active,
      java.lang.String updateRateDesignator )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRegionsAndRateRequest request;
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRegionsAndRateRequest. Builder builder = hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRegionsAndRateRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      builder.setActive(_clientConverter.convertFromHla(active));
      builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesWithRegionsAndRateRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeObjectClassAttributesWithRegionsAndRateResponse();
      // No return value
   }

   public void
   unsubscribeObjectClassAttributesWithRegions(
      hla.rti1516e.ObjectClassHandle theClass,
      hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions )
      throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.UnsubscribeObjectClassAttributesWithRegionsRequest request;
      hla.rti1516_202X.thin.UnsubscribeObjectClassAttributesWithRegionsRequest. Builder builder = hla.rti1516_202X.thin.UnsubscribeObjectClassAttributesWithRegionsRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassAttributesWithRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnsubscribeObjectClassAttributesWithRegionsResponse();
      // No return value
   }

   public void
   subscribeInteractionClassWithRegions(
      hla.rti1516e.InteractionClassHandle theClass,
      boolean active,
      hla.rti1516e.RegionHandleSet regions )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.SubscribeInteractionClassWithRegionsRequest request;
      hla.rti1516_202X.thin.SubscribeInteractionClassWithRegionsRequest. Builder builder = hla.rti1516_202X.thin.SubscribeInteractionClassWithRegionsRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setActive(_clientConverter.convertFromHla(active));
      builder.setRegions(_clientConverter.convertFromHla(regions));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeInteractionClassWithRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSubscribeInteractionClassWithRegionsResponse();
      // No return value
   }

   public void
   unsubscribeInteractionClassWithRegions(
      hla.rti1516e.InteractionClassHandle theClass,
      hla.rti1516e.RegionHandleSet regions )
      throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.UnsubscribeInteractionClassWithRegionsRequest request;
      hla.rti1516_202X.thin.UnsubscribeInteractionClassWithRegionsRequest. Builder builder = hla.rti1516_202X.thin.UnsubscribeInteractionClassWithRegionsRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setRegions(_clientConverter.convertFromHla(regions));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeInteractionClassWithRegionsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasUnsubscribeInteractionClassWithRegionsResponse();
      // No return value
   }

   public void
   sendInteractionWithRegions(
      hla.rti1516e.InteractionClassHandle theInteraction,
      hla.rti1516e.ParameterHandleValueMap theParameters,
      hla.rti1516e.RegionHandleSet regions,
      byte[] userSuppliedTag )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.SendInteractionWithRegionsRequest request;
      hla.rti1516_202X.thin.SendInteractionWithRegionsRequest. Builder builder = hla.rti1516_202X.thin.SendInteractionWithRegionsRequest. newBuilder();

      builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));
      builder.setTheParameters(_clientConverter.convertFromHla(theParameters));
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
      hla.rti1516e.LogicalTime theTime )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.SendInteractionWithRegionsAndTimeRequest request;
      hla.rti1516_202X.thin.SendInteractionWithRegionsAndTimeRequest. Builder builder = hla.rti1516_202X.thin.SendInteractionWithRegionsAndTimeRequest. newBuilder();

      builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));
      builder.setTheParameters(_clientConverter.convertFromHla(theParameters));
      builder.setRegions(_clientConverter.convertFromHla(regions));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      builder.setTheTime(_clientConverter.convertFromHla(theTime));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionWithRegionsAndTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSendInteractionWithRegionsAndTimeResponse();
      hla.rti1516_202X.thin.SendInteractionWithRegionsAndTimeResponse response = callResponse.getSendInteractionWithRegionsAndTimeResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   requestAttributeValueUpdateWithRegions(
      hla.rti1516e.ObjectClassHandle theClass,
      hla.rti1516e.AttributeSetRegionSetPairList attributesAndRegions,
      byte[] userSuppliedTag )
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
      RTIinternalError    {
      hla.rti1516_202X.thin.RequestAttributeValueUpdateWithRegionsRequest request;
      hla.rti1516_202X.thin.RequestAttributeValueUpdateWithRegionsRequest. Builder builder = hla.rti1516_202X.thin.RequestAttributeValueUpdateWithRegionsRequest. newBuilder();

      builder.setTheClass(_clientConverter.convertFromHla(theClass));
      builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestAttributeValueUpdateWithRegionsRequest(request).build();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.GetAutomaticResignDirectiveRequest request;
      request = hla.rti1516_202X.thin.GetAutomaticResignDirectiveRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetAutomaticResignDirectiveRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAutomaticResignDirectiveResponse();
      hla.rti1516_202X.thin.GetAutomaticResignDirectiveResponse response = callResponse.getGetAutomaticResignDirectiveResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   setAutomaticResignDirective(
      hla.rti1516e.ResignAction resignAction )
      throws
      InvalidResignAction,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.SetAutomaticResignDirectiveRequest request;
      hla.rti1516_202X.thin.SetAutomaticResignDirectiveRequest. Builder builder = hla.rti1516_202X.thin.SetAutomaticResignDirectiveRequest. newBuilder();

      builder.setResignAction(_clientConverter.convertFromHla(resignAction));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetAutomaticResignDirectiveRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSetAutomaticResignDirectiveResponse();
      // No return value
   }

   public hla.rti1516e.FederateHandle
   getFederateHandle(
      java.lang.String theName )
      throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetFederateHandleRequest request;
      hla.rti1516_202X.thin.GetFederateHandleRequest. Builder builder = hla.rti1516_202X.thin.GetFederateHandleRequest. newBuilder();

      builder.setTheName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetFederateHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetFederateHandleResponse();
      hla.rti1516_202X.thin.GetFederateHandleResponse response = callResponse.getGetFederateHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getFederateName(
      hla.rti1516e.FederateHandle theHandle )
      throws
      InvalidFederateHandle,
      FederateHandleNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetFederateNameRequest request;
      hla.rti1516_202X.thin.GetFederateNameRequest. Builder builder = hla.rti1516_202X.thin.GetFederateNameRequest. newBuilder();

      builder.setTheHandle(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetFederateNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetFederateNameResponse();
      hla.rti1516_202X.thin.GetFederateNameResponse response = callResponse.getGetFederateNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.ObjectClassHandle
   getObjectClassHandle(
      java.lang.String theName )
      throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetObjectClassHandleRequest request;
      hla.rti1516_202X.thin.GetObjectClassHandleRequest. Builder builder = hla.rti1516_202X.thin.GetObjectClassHandleRequest. newBuilder();

      builder.setTheName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectClassHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetObjectClassHandleResponse();
      hla.rti1516_202X.thin.GetObjectClassHandleResponse response = callResponse.getGetObjectClassHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getObjectClassName(
      hla.rti1516e.ObjectClassHandle theHandle )
      throws
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetObjectClassNameRequest request;
      hla.rti1516_202X.thin.GetObjectClassNameRequest. Builder builder = hla.rti1516_202X.thin.GetObjectClassNameRequest. newBuilder();

      builder.setTheHandle(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectClassNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetObjectClassNameResponse();
      hla.rti1516_202X.thin.GetObjectClassNameResponse response = callResponse.getGetObjectClassNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.ObjectClassHandle
   getKnownObjectClassHandle(
      hla.rti1516e.ObjectInstanceHandle theObject )
      throws
      ObjectInstanceNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetKnownObjectClassHandleRequest request;
      hla.rti1516_202X.thin.GetKnownObjectClassHandleRequest. Builder builder = hla.rti1516_202X.thin.GetKnownObjectClassHandleRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetKnownObjectClassHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetKnownObjectClassHandleResponse();
      hla.rti1516_202X.thin.GetKnownObjectClassHandleResponse response = callResponse.getGetKnownObjectClassHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.ObjectInstanceHandle
   getObjectInstanceHandle(
      java.lang.String theName )
      throws
      ObjectInstanceNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetObjectInstanceHandleRequest request;
      hla.rti1516_202X.thin.GetObjectInstanceHandleRequest. Builder builder = hla.rti1516_202X.thin.GetObjectInstanceHandleRequest. newBuilder();

      builder.setTheName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectInstanceHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetObjectInstanceHandleResponse();
      hla.rti1516_202X.thin.GetObjectInstanceHandleResponse response = callResponse.getGetObjectInstanceHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getObjectInstanceName(
      hla.rti1516e.ObjectInstanceHandle theHandle )
      throws
      ObjectInstanceNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetObjectInstanceNameRequest request;
      hla.rti1516_202X.thin.GetObjectInstanceNameRequest. Builder builder = hla.rti1516_202X.thin.GetObjectInstanceNameRequest. newBuilder();

      builder.setTheHandle(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectInstanceNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetObjectInstanceNameResponse();
      hla.rti1516_202X.thin.GetObjectInstanceNameResponse response = callResponse.getGetObjectInstanceNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.AttributeHandle
   getAttributeHandle(
      hla.rti1516e.ObjectClassHandle whichClass,
      java.lang.String theName )
      throws
      NameNotFound,
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetAttributeHandleRequest request;
      hla.rti1516_202X.thin.GetAttributeHandleRequest. Builder builder = hla.rti1516_202X.thin.GetAttributeHandleRequest. newBuilder();

      builder.setWhichClass(_clientConverter.convertFromHla(whichClass));
      builder.setTheName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetAttributeHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAttributeHandleResponse();
      hla.rti1516_202X.thin.GetAttributeHandleResponse response = callResponse.getGetAttributeHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getAttributeName(
      hla.rti1516e.ObjectClassHandle whichClass,
      hla.rti1516e.AttributeHandle theHandle )
      throws
      AttributeNotDefined,
      InvalidAttributeHandle,
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetAttributeNameRequest request;
      hla.rti1516_202X.thin.GetAttributeNameRequest. Builder builder = hla.rti1516_202X.thin.GetAttributeNameRequest. newBuilder();

      builder.setWhichClass(_clientConverter.convertFromHla(whichClass));
      builder.setTheHandle(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetAttributeNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAttributeNameResponse();
      hla.rti1516_202X.thin.GetAttributeNameResponse response = callResponse.getGetAttributeNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public double
   getUpdateRateValue(
      java.lang.String updateRateDesignator )
      throws
      InvalidUpdateRateDesignator,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetUpdateRateValueRequest request;
      hla.rti1516_202X.thin.GetUpdateRateValueRequest. Builder builder = hla.rti1516_202X.thin.GetUpdateRateValueRequest. newBuilder();

      builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetUpdateRateValueRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetUpdateRateValueResponse();
      hla.rti1516_202X.thin.GetUpdateRateValueResponse response = callResponse.getGetUpdateRateValueResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public double
   getUpdateRateValueForAttribute(
      hla.rti1516e.ObjectInstanceHandle theObject,
      hla.rti1516e.AttributeHandle theAttribute )
      throws
      ObjectInstanceNotKnown,
      AttributeNotDefined,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetUpdateRateValueForAttributeRequest request;
      hla.rti1516_202X.thin.GetUpdateRateValueForAttributeRequest. Builder builder = hla.rti1516_202X.thin.GetUpdateRateValueForAttributeRequest. newBuilder();

      builder.setTheObject(_clientConverter.convertFromHla(theObject));
      builder.setTheAttribute(_clientConverter.convertFromHla(theAttribute));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetUpdateRateValueForAttributeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetUpdateRateValueForAttributeResponse();
      hla.rti1516_202X.thin.GetUpdateRateValueForAttributeResponse response = callResponse.getGetUpdateRateValueForAttributeResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.InteractionClassHandle
   getInteractionClassHandle(
      java.lang.String theName )
      throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetInteractionClassHandleRequest request;
      hla.rti1516_202X.thin.GetInteractionClassHandleRequest. Builder builder = hla.rti1516_202X.thin.GetInteractionClassHandleRequest. newBuilder();

      builder.setTheName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetInteractionClassHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetInteractionClassHandleResponse();
      hla.rti1516_202X.thin.GetInteractionClassHandleResponse response = callResponse.getGetInteractionClassHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getInteractionClassName(
      hla.rti1516e.InteractionClassHandle theHandle )
      throws
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetInteractionClassNameRequest request;
      hla.rti1516_202X.thin.GetInteractionClassNameRequest. Builder builder = hla.rti1516_202X.thin.GetInteractionClassNameRequest. newBuilder();

      builder.setTheHandle(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetInteractionClassNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetInteractionClassNameResponse();
      hla.rti1516_202X.thin.GetInteractionClassNameResponse response = callResponse.getGetInteractionClassNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.ParameterHandle
   getParameterHandle(
      hla.rti1516e.InteractionClassHandle whichClass,
      java.lang.String theName )
      throws
      NameNotFound,
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetParameterHandleRequest request;
      hla.rti1516_202X.thin.GetParameterHandleRequest. Builder builder = hla.rti1516_202X.thin.GetParameterHandleRequest. newBuilder();

      builder.setWhichClass(_clientConverter.convertFromHla(whichClass));
      builder.setTheName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetParameterHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetParameterHandleResponse();
      hla.rti1516_202X.thin.GetParameterHandleResponse response = callResponse.getGetParameterHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getParameterName(
      hla.rti1516e.InteractionClassHandle whichClass,
      hla.rti1516e.ParameterHandle theHandle )
      throws
      InteractionParameterNotDefined,
      InvalidParameterHandle,
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetParameterNameRequest request;
      hla.rti1516_202X.thin.GetParameterNameRequest. Builder builder = hla.rti1516_202X.thin.GetParameterNameRequest. newBuilder();

      builder.setWhichClass(_clientConverter.convertFromHla(whichClass));
      builder.setTheHandle(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetParameterNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetParameterNameResponse();
      hla.rti1516_202X.thin.GetParameterNameResponse response = callResponse.getGetParameterNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.OrderType
   getOrderType(
      java.lang.String theName )
      throws
      InvalidOrderName,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetOrderTypeRequest request;
      hla.rti1516_202X.thin.GetOrderTypeRequest. Builder builder = hla.rti1516_202X.thin.GetOrderTypeRequest. newBuilder();

      builder.setTheName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetOrderTypeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetOrderTypeResponse();
      hla.rti1516_202X.thin.GetOrderTypeResponse response = callResponse.getGetOrderTypeResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getOrderName(
      hla.rti1516e.OrderType theType )
      throws
      InvalidOrderType,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetOrderNameRequest request;
      hla.rti1516_202X.thin.GetOrderNameRequest. Builder builder = hla.rti1516_202X.thin.GetOrderNameRequest. newBuilder();

      builder.setTheType(_clientConverter.convertFromHla(theType));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetOrderNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetOrderNameResponse();
      hla.rti1516_202X.thin.GetOrderNameResponse response = callResponse.getGetOrderNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.TransportationTypeHandle
   getTransportationTypeHandle(
      java.lang.String theName )
      throws
      InvalidTransportationName,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetTransportationTypeHandleRequest request;
      hla.rti1516_202X.thin.GetTransportationTypeHandleRequest. Builder builder = hla.rti1516_202X.thin.GetTransportationTypeHandleRequest. newBuilder();

      builder.setTheName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetTransportationTypeHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetTransportationTypeHandleResponse();
      hla.rti1516_202X.thin.GetTransportationTypeHandleResponse response = callResponse.getGetTransportationTypeHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getTransportationTypeName(
      hla.rti1516e.TransportationTypeHandle theHandle )
      throws
      InvalidTransportationType,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetTransportationTypeNameRequest request;
      hla.rti1516_202X.thin.GetTransportationTypeNameRequest. Builder builder = hla.rti1516_202X.thin.GetTransportationTypeNameRequest. newBuilder();

      builder.setTheHandle(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetTransportationTypeNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetTransportationTypeNameResponse();
      hla.rti1516_202X.thin.GetTransportationTypeNameResponse response = callResponse.getGetTransportationTypeNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.DimensionHandleSet
   getAvailableDimensionsForClassAttribute(
      hla.rti1516e.ObjectClassHandle whichClass,
      hla.rti1516e.AttributeHandle theHandle )
      throws
      AttributeNotDefined,
      InvalidAttributeHandle,
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetAvailableDimensionsForClassAttributeRequest request;
      hla.rti1516_202X.thin.GetAvailableDimensionsForClassAttributeRequest. Builder builder = hla.rti1516_202X.thin.GetAvailableDimensionsForClassAttributeRequest. newBuilder();

      builder.setWhichClass(_clientConverter.convertFromHla(whichClass));
      builder.setTheHandle(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetAvailableDimensionsForClassAttributeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAvailableDimensionsForClassAttributeResponse();
      hla.rti1516_202X.thin.GetAvailableDimensionsForClassAttributeResponse response = callResponse.getGetAvailableDimensionsForClassAttributeResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.DimensionHandleSet
   getAvailableDimensionsForInteractionClass(
      hla.rti1516e.InteractionClassHandle theHandle )
      throws
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetAvailableDimensionsForInteractionClassRequest request;
      hla.rti1516_202X.thin.GetAvailableDimensionsForInteractionClassRequest. Builder builder = hla.rti1516_202X.thin.GetAvailableDimensionsForInteractionClassRequest. newBuilder();

      builder.setTheHandle(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetAvailableDimensionsForInteractionClassRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetAvailableDimensionsForInteractionClassResponse();
      hla.rti1516_202X.thin.GetAvailableDimensionsForInteractionClassResponse response = callResponse.getGetAvailableDimensionsForInteractionClassResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.DimensionHandle
   getDimensionHandle(
      java.lang.String theName )
      throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetDimensionHandleRequest request;
      hla.rti1516_202X.thin.GetDimensionHandleRequest. Builder builder = hla.rti1516_202X.thin.GetDimensionHandleRequest. newBuilder();

      builder.setTheName(_clientConverter.convertFromHla(theName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetDimensionHandleResponse();
      hla.rti1516_202X.thin.GetDimensionHandleResponse response = callResponse.getGetDimensionHandleResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public java.lang.String
   getDimensionName(
      hla.rti1516e.DimensionHandle theHandle )
      throws
      InvalidDimensionHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetDimensionNameRequest request;
      hla.rti1516_202X.thin.GetDimensionNameRequest. Builder builder = hla.rti1516_202X.thin.GetDimensionNameRequest. newBuilder();

      builder.setTheHandle(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetDimensionNameResponse();
      hla.rti1516_202X.thin.GetDimensionNameResponse response = callResponse.getGetDimensionNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public long
   getDimensionUpperBound(
      hla.rti1516e.DimensionHandle theHandle )
      throws
      InvalidDimensionHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetDimensionUpperBoundRequest request;
      hla.rti1516_202X.thin.GetDimensionUpperBoundRequest. Builder builder = hla.rti1516_202X.thin.GetDimensionUpperBoundRequest. newBuilder();

      builder.setTheHandle(_clientConverter.convertFromHla(theHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionUpperBoundRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetDimensionUpperBoundResponse();
      hla.rti1516_202X.thin.GetDimensionUpperBoundResponse response = callResponse.getGetDimensionUpperBoundResponse();
      return _clientConverter.convertToHla(Integer.toUnsignedLong(response.getResult()));
   }

   public hla.rti1516e.DimensionHandleSet
   getDimensionHandleSet(
      hla.rti1516e.RegionHandle region )
      throws
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetDimensionHandleSetRequest request;
      hla.rti1516_202X.thin.GetDimensionHandleSetRequest. Builder builder = hla.rti1516_202X.thin.GetDimensionHandleSetRequest. newBuilder();

      builder.setRegion(_clientConverter.convertFromHla(region));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionHandleSetRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetDimensionHandleSetResponse();
      hla.rti1516_202X.thin.GetDimensionHandleSetResponse response = callResponse.getGetDimensionHandleSetResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public hla.rti1516e.RangeBounds
   getRangeBounds(
      hla.rti1516e.RegionHandle region,
      hla.rti1516e.DimensionHandle dimension )
      throws
      RegionDoesNotContainSpecifiedDimension,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.GetRangeBoundsRequest request;
      hla.rti1516_202X.thin.GetRangeBoundsRequest. Builder builder = hla.rti1516_202X.thin.GetRangeBoundsRequest. newBuilder();

      builder.setRegion(_clientConverter.convertFromHla(region));
      builder.setDimension(_clientConverter.convertFromHla(dimension));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetRangeBoundsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasGetRangeBoundsResponse();
      hla.rti1516_202X.thin.GetRangeBoundsResponse response = callResponse.getGetRangeBoundsResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void
   setRangeBounds(
      hla.rti1516e.RegionHandle region,
      hla.rti1516e.DimensionHandle dimension,
      hla.rti1516e.RangeBounds bounds )
      throws
      InvalidRangeBound,
      RegionDoesNotContainSpecifiedDimension,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.SetRangeBoundsRequest request;
      hla.rti1516_202X.thin.SetRangeBoundsRequest. Builder builder = hla.rti1516_202X.thin.SetRangeBoundsRequest. newBuilder();

      builder.setRegion(_clientConverter.convertFromHla(region));
      builder.setDimension(_clientConverter.convertFromHla(dimension));
      builder.setBounds(_clientConverter.convertFromHla(bounds));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetRangeBoundsRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasSetRangeBoundsResponse();
      // No return value
   }

   public long
   normalizeFederateHandle(
      hla.rti1516e.FederateHandle federateHandle )
      throws
      InvalidFederateHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.NormalizeFederateHandleRequest request;
      hla.rti1516_202X.thin.NormalizeFederateHandleRequest. Builder builder = hla.rti1516_202X.thin.NormalizeFederateHandleRequest. newBuilder();

      builder.setFederateHandle(_clientConverter.convertFromHla(federateHandle));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNormalizeFederateHandleRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasNormalizeFederateHandleResponse();
      hla.rti1516_202X.thin.NormalizeFederateHandleResponse response = callResponse.getNormalizeFederateHandleResponse();
      return _clientConverter.convertToHla(Integer.toUnsignedLong(response.getResult()));
   }

   public long
   normalizeServiceGroup(
      hla.rti1516e.ServiceGroup group )
      throws
      InvalidServiceGroup,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      hla.rti1516_202X.thin.NormalizeServiceGroupRequest request;
      hla.rti1516_202X.thin.NormalizeServiceGroupRequest. Builder builder = hla.rti1516_202X.thin.NormalizeServiceGroupRequest. newBuilder();

      builder.setGroup(_clientConverter.convertFromHla(group));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNormalizeServiceGroupRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasNormalizeServiceGroupResponse();
      hla.rti1516_202X.thin.NormalizeServiceGroupResponse response = callResponse.getNormalizeServiceGroupResponse();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.EnableObjectClassRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.EnableObjectClassRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setEnableObjectClassRelevanceAdvisorySwitchRequest(request).build();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.DisableObjectClassRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.DisableObjectClassRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableObjectClassRelevanceAdvisorySwitchRequest(request).build();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.EnableAttributeRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.EnableAttributeRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setEnableAttributeRelevanceAdvisorySwitchRequest(request).build();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.DisableAttributeRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.DisableAttributeRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableAttributeRelevanceAdvisorySwitchRequest(request).build();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.EnableAttributeScopeAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.EnableAttributeScopeAdvisorySwitchRequest. getDefaultInstance();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.DisableAttributeScopeAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.DisableAttributeScopeAdvisorySwitchRequest. getDefaultInstance();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.EnableInteractionRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.EnableInteractionRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setEnableInteractionRelevanceAdvisorySwitchRequest(request).build();
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
      RTIinternalError    {
      hla.rti1516_202X.thin.DisableInteractionRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.DisableInteractionRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableInteractionRelevanceAdvisorySwitchRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDisableInteractionRelevanceAdvisorySwitchResponse();
      // No return value
   }

}
