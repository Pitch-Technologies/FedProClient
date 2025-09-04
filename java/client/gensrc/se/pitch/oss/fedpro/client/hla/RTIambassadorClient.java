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

import hla.rti1516_2025.*;
import hla.rti1516_2025.time.*;
import hla.rti1516_2025.exceptions.*;
import hla.rti1516_2025.fedpro.CallRequest;
import hla.rti1516_2025.fedpro.CallResponse;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;


public class RTIambassadorClient extends RTIambassadorClientHla4Base
{
   public RTIambassadorClient(ClientConverter clientConverter) {
      super(clientConverter);
   }

   public void createFederationExecution(
      java.lang.String federationName,
      FomModule fomModule
   )
   throws
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      FederationExecutionAlreadyExists,
      Unauthorized,
      NotConnected,
      RTIinternalError
   {
      hla.rti1516_2025.fedpro.CreateFederationExecutionRequest request;
      hla.rti1516_2025.fedpro.CreateFederationExecutionRequest. Builder builder = hla.rti1516_2025.fedpro.CreateFederationExecutionRequest. newBuilder();

      builder.setFederationName(_clientConverter.convertFromHla(federationName));
      builder.setFomModule(_clientConverter.convertFromHla(fomModule));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCreateFederationExecutionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCreateFederationExecutionResponse();
      // No return value
   }

   public void createFederationExecutionWithTime(
      java.lang.String federationName,
      FomModule fomModule,
      java.lang.String logicalTimeImplementationName
   )
   throws
      CouldNotCreateLogicalTimeFactory,
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      FederationExecutionAlreadyExists,
      Unauthorized,
      NotConnected,
      RTIinternalError
   {
      hla.rti1516_2025.fedpro.CreateFederationExecutionWithTimeRequest request;
      hla.rti1516_2025.fedpro.CreateFederationExecutionWithTimeRequest. Builder builder = hla.rti1516_2025.fedpro.CreateFederationExecutionWithTimeRequest. newBuilder();

      builder.setFederationName(_clientConverter.convertFromHla(federationName));
      builder.setFomModule(_clientConverter.convertFromHla(fomModule));
      builder.setLogicalTimeImplementationName(_clientConverter.convertFromHla(logicalTimeImplementationName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCreateFederationExecutionWithTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCreateFederationExecutionWithTimeResponse();
      // No return value
   }

   public void createFederationExecutionWithModules(
      java.lang.String federationName,
      FomModuleSet fomModules
   )
   throws
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      FederationExecutionAlreadyExists,
      Unauthorized,
      NotConnected,
      RTIinternalError
   {
      hla.rti1516_2025.fedpro.CreateFederationExecutionWithModulesRequest request;
      hla.rti1516_2025.fedpro.CreateFederationExecutionWithModulesRequest. Builder builder = hla.rti1516_2025.fedpro.CreateFederationExecutionWithModulesRequest. newBuilder();

      builder.setFederationName(_clientConverter.convertFromHla(federationName));
      builder.setFomModules(_clientConverter.convertFromHla(fomModules));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCreateFederationExecutionWithModulesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCreateFederationExecutionWithModulesResponse();
      // No return value
   }

   public void createFederationExecutionWithModulesAndTime(
      java.lang.String federationName,
      FomModuleSet fomModules,
      java.lang.String logicalTimeImplementationName
   )
   throws
      CouldNotCreateLogicalTimeFactory,
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      FederationExecutionAlreadyExists,
      Unauthorized,
      NotConnected,
      RTIinternalError
   {
      hla.rti1516_2025.fedpro.CreateFederationExecutionWithModulesAndTimeRequest request;
      hla.rti1516_2025.fedpro.CreateFederationExecutionWithModulesAndTimeRequest. Builder builder = hla.rti1516_2025.fedpro.CreateFederationExecutionWithModulesAndTimeRequest. newBuilder();

      builder.setFederationName(_clientConverter.convertFromHla(federationName));
      builder.setFomModules(_clientConverter.convertFromHla(fomModules));
      builder.setLogicalTimeImplementationName(_clientConverter.convertFromHla(logicalTimeImplementationName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCreateFederationExecutionWithModulesAndTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCreateFederationExecutionWithModulesAndTimeResponse();
      // No return value
   }

   public void createFederationExecutionWithMIM(
      java.lang.String federationName,
      FomModuleSet fomModules,
      FomModule mimModule
   )
   throws
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      InvalidMIM,
      ErrorReadingMIM,
      CouldNotOpenMIM,
      DesignatorIsHLAstandardMIM,
      FederationExecutionAlreadyExists,
      Unauthorized,
      NotConnected,
      RTIinternalError
   {
      hla.rti1516_2025.fedpro.CreateFederationExecutionWithMIMRequest request;
      hla.rti1516_2025.fedpro.CreateFederationExecutionWithMIMRequest. Builder builder = hla.rti1516_2025.fedpro.CreateFederationExecutionWithMIMRequest. newBuilder();

      builder.setFederationName(_clientConverter.convertFromHla(federationName));
      builder.setFomModules(_clientConverter.convertFromHla(fomModules));
      builder.setMimModule(_clientConverter.convertFromHla(mimModule));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCreateFederationExecutionWithMIMRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCreateFederationExecutionWithMIMResponse();
      // No return value
   }

   public void createFederationExecutionWithMIMAndTime(
      java.lang.String federationName,
      FomModuleSet fomModules,
      FomModule mimModule,
      java.lang.String logicalTimeImplementationName
   )
   throws
      CouldNotCreateLogicalTimeFactory,
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      InvalidMIM,
      ErrorReadingMIM,
      CouldNotOpenMIM,
      DesignatorIsHLAstandardMIM,
      FederationExecutionAlreadyExists,
      Unauthorized,
      NotConnected,
      RTIinternalError
   {
      hla.rti1516_2025.fedpro.CreateFederationExecutionWithMIMAndTimeRequest request;
      hla.rti1516_2025.fedpro.CreateFederationExecutionWithMIMAndTimeRequest. Builder builder = hla.rti1516_2025.fedpro.CreateFederationExecutionWithMIMAndTimeRequest. newBuilder();

      builder.setFederationName(_clientConverter.convertFromHla(federationName));
      builder.setFomModules(_clientConverter.convertFromHla(fomModules));
      builder.setMimModule(_clientConverter.convertFromHla(mimModule));
      builder.setLogicalTimeImplementationName(_clientConverter.convertFromHla(logicalTimeImplementationName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCreateFederationExecutionWithMIMAndTimeRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasCreateFederationExecutionWithMIMAndTimeResponse();
      // No return value
   }

   public void destroyFederationExecution(
      java.lang.String federationName
   )
   throws
      FederatesCurrentlyJoined,
      FederationExecutionDoesNotExist,
      Unauthorized,
      NotConnected,
      RTIinternalError
   {
      hla.rti1516_2025.fedpro.DestroyFederationExecutionRequest request;
      hla.rti1516_2025.fedpro.DestroyFederationExecutionRequest. Builder builder = hla.rti1516_2025.fedpro.DestroyFederationExecutionRequest. newBuilder();

      builder.setFederationName(_clientConverter.convertFromHla(federationName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setDestroyFederationExecutionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasDestroyFederationExecutionResponse();
      // No return value
   }

   public void listFederationExecutions(

   )
   throws
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncListFederationExecutions(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncListFederationExecutions(

   )
   {
      hla.rti1516_2025.fedpro.ListFederationExecutionsRequest request;
      request = hla.rti1516_2025.fedpro.ListFederationExecutionsRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setListFederationExecutionsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasListFederationExecutionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.LISTFEDERATIONEXECUTIONSRESPONSE));
         }
         return null;
      });
   }

   public void listFederationExecutionMembers(
      java.lang.String federationName
   )
   throws
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncListFederationExecutionMembers(
               federationName
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncListFederationExecutionMembers(
      java.lang.String federationName
   )
   {
      hla.rti1516_2025.fedpro.ListFederationExecutionMembersRequest request;
      hla.rti1516_2025.fedpro.ListFederationExecutionMembersRequest. Builder builder = hla.rti1516_2025.fedpro.ListFederationExecutionMembersRequest. newBuilder();

      try {
         builder.setFederationName(_clientConverter.convertFromHla(federationName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setListFederationExecutionMembersRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasListFederationExecutionMembersResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.LISTFEDERATIONEXECUTIONMEMBERSRESPONSE));
         }
         return null;
      });
   }

   public JoinResult joinFederationExecution(
      java.lang.String federateType,
      java.lang.String federationName
   )
   throws
      CouldNotCreateLogicalTimeFactory,
      FederationExecutionDoesNotExist,
      SaveInProgress,
      RestoreInProgress,
      FederateAlreadyExecutionMember,
      Unauthorized,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError
   {
      throwIfInCallback("joinFederationExecution");
      hla.rti1516_2025.fedpro.JoinFederationExecutionRequest request;
      hla.rti1516_2025.fedpro.JoinFederationExecutionRequest. Builder builder = hla.rti1516_2025.fedpro.JoinFederationExecutionRequest. newBuilder();

      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationName(_clientConverter.convertFromHla(federationName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setJoinFederationExecutionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionResponse();
      hla.rti1516_2025.fedpro.JoinFederationExecutionResponse response = callResponse.getJoinFederationExecutionResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public JoinResult joinFederationExecutionWithModules(
      java.lang.String federateType,
      java.lang.String federationName,
      FomModuleSet additionalFomModules
   )
   throws
      CouldNotCreateLogicalTimeFactory,
      FederationExecutionDoesNotExist,
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      SaveInProgress,
      RestoreInProgress,
      FederateAlreadyExecutionMember,
      Unauthorized,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError
   {
      throwIfInCallback("joinFederationExecutionWithModules");
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithModulesRequest request;
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithModulesRequest. Builder builder = hla.rti1516_2025.fedpro.JoinFederationExecutionWithModulesRequest. newBuilder();

      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationName(_clientConverter.convertFromHla(federationName));
      builder.setAdditionalFomModules(_clientConverter.convertFromHla(additionalFomModules));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setJoinFederationExecutionWithModulesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionWithModulesResponse();
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithModulesResponse response = callResponse.getJoinFederationExecutionWithModulesResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public JoinResult joinFederationExecutionWithName(
      java.lang.String federateName,
      java.lang.String federateType,
      java.lang.String federationName
   )
   throws
      CouldNotCreateLogicalTimeFactory,
      FederateNameAlreadyInUse,
      FederationExecutionDoesNotExist,
      SaveInProgress,
      RestoreInProgress,
      FederateAlreadyExecutionMember,
      Unauthorized,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError
   {
      throwIfInCallback("joinFederationExecutionWithName");
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameRequest request;
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameRequest. Builder builder = hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameRequest. newBuilder();

      builder.setFederateName(_clientConverter.convertFromHla(federateName));
      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationName(_clientConverter.convertFromHla(federationName));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setJoinFederationExecutionWithNameRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionWithNameResponse();
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameResponse response = callResponse.getJoinFederationExecutionWithNameResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public JoinResult joinFederationExecutionWithNameAndModules(
      java.lang.String federateName,
      java.lang.String federateType,
      java.lang.String federationName,
      FomModuleSet additionalFomModules
   )
   throws
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
      Unauthorized,
      NotConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError
   {
      throwIfInCallback("joinFederationExecutionWithNameAndModules");
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameAndModulesRequest request;
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameAndModulesRequest. Builder builder = hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameAndModulesRequest. newBuilder();

      builder.setFederateName(_clientConverter.convertFromHla(federateName));
      builder.setFederateType(_clientConverter.convertFromHla(federateType));
      builder.setFederationName(_clientConverter.convertFromHla(federationName));
      builder.setAdditionalFomModules(_clientConverter.convertFromHla(additionalFomModules));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setJoinFederationExecutionWithNameAndModulesRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasJoinFederationExecutionWithNameAndModulesResponse();
      hla.rti1516_2025.fedpro.JoinFederationExecutionWithNameAndModulesResponse response = callResponse.getJoinFederationExecutionWithNameAndModulesResponse();
      return _clientConverter.convertToHla(response.getResult());
   }

   public void resignFederationExecution(
      hla.rti1516_2025.ResignAction resignAction
   )
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
      hla.rti1516_2025.fedpro.ResignFederationExecutionRequest. Builder builder = hla.rti1516_2025.fedpro.ResignFederationExecutionRequest. newBuilder();

      builder.setResignAction(_clientConverter.convertFromHla(resignAction));

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setResignFederationExecutionRequest(request).build();
      CallResponse callResponse = doHlaCall(callRequest);

      assert callResponse.hasResignFederationExecutionResponse();
      // No return value
   }

   public void registerFederationSynchronizationPoint(
      java.lang.String synchronizationPointLabel,
      byte[] userSuppliedTag
   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncRegisterFederationSynchronizationPoint(
               synchronizationPointLabel,
               userSuppliedTag
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncRegisterFederationSynchronizationPoint(
      java.lang.String synchronizationPointLabel,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.RegisterFederationSynchronizationPointRequest request;
      hla.rti1516_2025.fedpro.RegisterFederationSynchronizationPointRequest. Builder builder = hla.rti1516_2025.fedpro.RegisterFederationSynchronizationPointRequest. newBuilder();

      try {
         builder.setSynchronizationPointLabel(_clientConverter.convertFromHla(synchronizationPointLabel));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterFederationSynchronizationPointRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRegisterFederationSynchronizationPointResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REGISTERFEDERATIONSYNCHRONIZATIONPOINTRESPONSE));
         }
         return null;
      });
   }

   public void registerFederationSynchronizationPoint(
      java.lang.String synchronizationPointLabel,
      byte[] userSuppliedTag,
      hla.rti1516_2025.FederateHandleSet synchronizationSet
   )
   throws
      InvalidFederateHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncRegisterFederationSynchronizationPoint(
               synchronizationPointLabel,
               userSuppliedTag,
               synchronizationSet
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncRegisterFederationSynchronizationPoint(
      java.lang.String synchronizationPointLabel,
      byte[] userSuppliedTag,
      hla.rti1516_2025.FederateHandleSet synchronizationSet
   )
   {
      hla.rti1516_2025.fedpro.RegisterFederationSynchronizationPointWithSetRequest request;
      hla.rti1516_2025.fedpro.RegisterFederationSynchronizationPointWithSetRequest. Builder builder = hla.rti1516_2025.fedpro.RegisterFederationSynchronizationPointWithSetRequest. newBuilder();

      try {
         builder.setSynchronizationPointLabel(_clientConverter.convertFromHla(synchronizationPointLabel));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
         builder.setSynchronizationSet(_clientConverter.convertFromHla(synchronizationSet));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterFederationSynchronizationPointWithSetRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRegisterFederationSynchronizationPointWithSetResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REGISTERFEDERATIONSYNCHRONIZATIONPOINTWITHSETRESPONSE));
         }
         return null;
      });
   }

   public void synchronizationPointAchieved(
      java.lang.String synchronizationPointLabel,
      boolean successfully
   )
   throws
      SynchronizationPointLabelNotAnnounced,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSynchronizationPointAchieved(
               synchronizationPointLabel,
               successfully
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSynchronizationPointAchieved(
      java.lang.String synchronizationPointLabel,
      boolean successfully
   )
   {
      hla.rti1516_2025.fedpro.SynchronizationPointAchievedRequest request;
      hla.rti1516_2025.fedpro.SynchronizationPointAchievedRequest. Builder builder = hla.rti1516_2025.fedpro.SynchronizationPointAchievedRequest. newBuilder();

      try {
         builder.setSynchronizationPointLabel(_clientConverter.convertFromHla(synchronizationPointLabel));
         builder.setSuccessfully(_clientConverter.convertFromHla(successfully));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSynchronizationPointAchievedRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSynchronizationPointAchievedResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SYNCHRONIZATIONPOINTACHIEVEDRESPONSE));
         }
         return null;
      });
   }

   public void requestFederationSave(
      java.lang.String label
   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncRequestFederationSave(
               label
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncRequestFederationSave(
      java.lang.String label
   )
   {
      hla.rti1516_2025.fedpro.RequestFederationSaveRequest request;
      hla.rti1516_2025.fedpro.RequestFederationSaveRequest. Builder builder = hla.rti1516_2025.fedpro.RequestFederationSaveRequest. newBuilder();

      try {
         builder.setLabel(_clientConverter.convertFromHla(label));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestFederationSaveRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestFederationSaveResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTFEDERATIONSAVERESPONSE));
         }
         return null;
      });
   }

   public void requestFederationSave(
      java.lang.String label,
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
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
      try {
         CompletableFuture<Void> future =
            asyncRequestFederationSave(
               label,
               time
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncRequestFederationSave(
      java.lang.String label,
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
   {
      hla.rti1516_2025.fedpro.RequestFederationSaveWithTimeRequest request;
      hla.rti1516_2025.fedpro.RequestFederationSaveWithTimeRequest. Builder builder = hla.rti1516_2025.fedpro.RequestFederationSaveWithTimeRequest. newBuilder();

      try {
         builder.setLabel(_clientConverter.convertFromHla(label));
         builder.setTime(_clientConverter.convertFromHla(time));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestFederationSaveWithTimeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestFederationSaveWithTimeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTFEDERATIONSAVEWITHTIMERESPONSE));
         }
         return null;
      });
   }

   public void federateSaveBegun(

   )
   throws
      SaveNotInitiated,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncFederateSaveBegun(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncFederateSaveBegun(

   )
   {
      hla.rti1516_2025.fedpro.FederateSaveBegunRequest request;
      request = hla.rti1516_2025.fedpro.FederateSaveBegunRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateSaveBegunRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasFederateSaveBegunResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.FEDERATESAVEBEGUNRESPONSE));
         }
         return null;
      });
   }

   public void federateSaveComplete(

   )
   throws
      FederateHasNotBegunSave,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncFederateSaveComplete(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncFederateSaveComplete(

   )
   {
      hla.rti1516_2025.fedpro.FederateSaveCompleteRequest request;
      request = hla.rti1516_2025.fedpro.FederateSaveCompleteRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateSaveCompleteRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasFederateSaveCompleteResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.FEDERATESAVECOMPLETERESPONSE));
         }
         return null;
      });
   }

   public void federateSaveNotComplete(

   )
   throws
      FederateHasNotBegunSave,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncFederateSaveNotComplete(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncFederateSaveNotComplete(

   )
   {
      hla.rti1516_2025.fedpro.FederateSaveNotCompleteRequest request;
      request = hla.rti1516_2025.fedpro.FederateSaveNotCompleteRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateSaveNotCompleteRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasFederateSaveNotCompleteResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.FEDERATESAVENOTCOMPLETERESPONSE));
         }
         return null;
      });
   }

   public void abortFederationSave(

   )
   throws
      SaveNotInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncAbortFederationSave(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncAbortFederationSave(

   )
   {
      hla.rti1516_2025.fedpro.AbortFederationSaveRequest request;
      request = hla.rti1516_2025.fedpro.AbortFederationSaveRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setAbortFederationSaveRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasAbortFederationSaveResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ABORTFEDERATIONSAVERESPONSE));
         }
         return null;
      });
   }

   public void queryFederationSaveStatus(

   )
   throws
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncQueryFederationSaveStatus(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncQueryFederationSaveStatus(

   )
   {
      hla.rti1516_2025.fedpro.QueryFederationSaveStatusRequest request;
      request = hla.rti1516_2025.fedpro.QueryFederationSaveStatusRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryFederationSaveStatusRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasQueryFederationSaveStatusResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.QUERYFEDERATIONSAVESTATUSRESPONSE));
         }
         return null;
      });
   }

   public void requestFederationRestore(
      java.lang.String label
   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncRequestFederationRestore(
               label
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncRequestFederationRestore(
      java.lang.String label
   )
   {
      hla.rti1516_2025.fedpro.RequestFederationRestoreRequest request;
      hla.rti1516_2025.fedpro.RequestFederationRestoreRequest. Builder builder = hla.rti1516_2025.fedpro.RequestFederationRestoreRequest. newBuilder();

      try {
         builder.setLabel(_clientConverter.convertFromHla(label));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestFederationRestoreRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestFederationRestoreResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTFEDERATIONRESTORERESPONSE));
         }
         return null;
      });
   }

   public void federateRestoreComplete(

   )
   throws
      RestoreNotRequested,
      SaveInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncFederateRestoreComplete(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncFederateRestoreComplete(

   )
   {
      hla.rti1516_2025.fedpro.FederateRestoreCompleteRequest request;
      request = hla.rti1516_2025.fedpro.FederateRestoreCompleteRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateRestoreCompleteRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasFederateRestoreCompleteResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.FEDERATERESTORECOMPLETERESPONSE));
         }
         return null;
      });
   }

   public void federateRestoreNotComplete(

   )
   throws
      RestoreNotRequested,
      SaveInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncFederateRestoreNotComplete(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncFederateRestoreNotComplete(

   )
   {
      hla.rti1516_2025.fedpro.FederateRestoreNotCompleteRequest request;
      request = hla.rti1516_2025.fedpro.FederateRestoreNotCompleteRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateRestoreNotCompleteRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasFederateRestoreNotCompleteResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.FEDERATERESTORENOTCOMPLETERESPONSE));
         }
         return null;
      });
   }

   public void abortFederationRestore(

   )
   throws
      RestoreNotInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncAbortFederationRestore(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncAbortFederationRestore(

   )
   {
      hla.rti1516_2025.fedpro.AbortFederationRestoreRequest request;
      request = hla.rti1516_2025.fedpro.AbortFederationRestoreRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setAbortFederationRestoreRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasAbortFederationRestoreResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ABORTFEDERATIONRESTORERESPONSE));
         }
         return null;
      });
   }

   public void queryFederationRestoreStatus(

   )
   throws
      SaveInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncQueryFederationRestoreStatus(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncQueryFederationRestoreStatus(

   )
   {
      hla.rti1516_2025.fedpro.QueryFederationRestoreStatusRequest request;
      request = hla.rti1516_2025.fedpro.QueryFederationRestoreStatusRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryFederationRestoreStatusRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasQueryFederationRestoreStatusResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.QUERYFEDERATIONRESTORESTATUSRESPONSE));
         }
         return null;
      });
   }

   public void publishObjectClassAttributes(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
   throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncPublishObjectClassAttributes(
               objectClass,
               attributes
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncPublishObjectClassAttributes(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
   {
      hla.rti1516_2025.fedpro.PublishObjectClassAttributesRequest request;
      hla.rti1516_2025.fedpro.PublishObjectClassAttributesRequest. Builder builder = hla.rti1516_2025.fedpro.PublishObjectClassAttributesRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setPublishObjectClassAttributesRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasPublishObjectClassAttributesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.PUBLISHOBJECTCLASSATTRIBUTESRESPONSE));
         }
         return null;
      });
   }

   public void unpublishObjectClass(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   throws
      OwnershipAcquisitionPending,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncUnpublishObjectClass(
               objectClass
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUnpublishObjectClass(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   {
      hla.rti1516_2025.fedpro.UnpublishObjectClassRequest request;
      hla.rti1516_2025.fedpro.UnpublishObjectClassRequest. Builder builder = hla.rti1516_2025.fedpro.UnpublishObjectClassRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishObjectClassRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnpublishObjectClassResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNPUBLISHOBJECTCLASSRESPONSE));
         }
         return null;
      });
   }

   public void unpublishObjectClassAttributes(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
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
      try {
         CompletableFuture<Void> future =
            asyncUnpublishObjectClassAttributes(
               objectClass,
               attributes
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUnpublishObjectClassAttributes(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
   {
      hla.rti1516_2025.fedpro.UnpublishObjectClassAttributesRequest request;
      hla.rti1516_2025.fedpro.UnpublishObjectClassAttributesRequest. Builder builder = hla.rti1516_2025.fedpro.UnpublishObjectClassAttributesRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishObjectClassAttributesRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnpublishObjectClassAttributesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNPUBLISHOBJECTCLASSATTRIBUTESRESPONSE));
         }
         return null;
      });
   }

   public void publishInteractionClass(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncPublishInteractionClass(
               interactionClass
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncPublishInteractionClass(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   {
      hla.rti1516_2025.fedpro.PublishInteractionClassRequest request;
      hla.rti1516_2025.fedpro.PublishInteractionClassRequest. Builder builder = hla.rti1516_2025.fedpro.PublishInteractionClassRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setPublishInteractionClassRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasPublishInteractionClassResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.PUBLISHINTERACTIONCLASSRESPONSE));
         }
         return null;
      });
   }

   public void unpublishInteractionClass(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncUnpublishInteractionClass(
               interactionClass
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUnpublishInteractionClass(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   {
      hla.rti1516_2025.fedpro.UnpublishInteractionClassRequest request;
      hla.rti1516_2025.fedpro.UnpublishInteractionClassRequest. Builder builder = hla.rti1516_2025.fedpro.UnpublishInteractionClassRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishInteractionClassRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnpublishInteractionClassResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNPUBLISHINTERACTIONCLASSRESPONSE));
         }
         return null;
      });
   }

   public void publishObjectClassDirectedInteractions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.InteractionClassHandleSet interactionClasses
   )
   throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncPublishObjectClassDirectedInteractions(
               objectClass,
               interactionClasses
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncPublishObjectClassDirectedInteractions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.InteractionClassHandleSet interactionClasses
   )
   {
      hla.rti1516_2025.fedpro.PublishObjectClassDirectedInteractionsRequest request;
      hla.rti1516_2025.fedpro.PublishObjectClassDirectedInteractionsRequest. Builder builder = hla.rti1516_2025.fedpro.PublishObjectClassDirectedInteractionsRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setInteractionClasses(_clientConverter.convertFromHla(interactionClasses));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setPublishObjectClassDirectedInteractionsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasPublishObjectClassDirectedInteractionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.PUBLISHOBJECTCLASSDIRECTEDINTERACTIONSRESPONSE));
         }
         return null;
      });
   }

   public void unpublishObjectClassDirectedInteractions(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   throws
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncUnpublishObjectClassDirectedInteractions(
               objectClass
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUnpublishObjectClassDirectedInteractions(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   {
      hla.rti1516_2025.fedpro.UnpublishObjectClassDirectedInteractionsRequest request;
      hla.rti1516_2025.fedpro.UnpublishObjectClassDirectedInteractionsRequest. Builder builder = hla.rti1516_2025.fedpro.UnpublishObjectClassDirectedInteractionsRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishObjectClassDirectedInteractionsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnpublishObjectClassDirectedInteractionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNPUBLISHOBJECTCLASSDIRECTEDINTERACTIONSRESPONSE));
         }
         return null;
      });
   }

   public void unpublishObjectClassDirectedInteractions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.InteractionClassHandleSet interactionClasses
   )
   throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncUnpublishObjectClassDirectedInteractions(
               objectClass,
               interactionClasses
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUnpublishObjectClassDirectedInteractions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.InteractionClassHandleSet interactionClasses
   )
   {
      hla.rti1516_2025.fedpro.UnpublishObjectClassDirectedInteractionsWithSetRequest request;
      hla.rti1516_2025.fedpro.UnpublishObjectClassDirectedInteractionsWithSetRequest. Builder builder = hla.rti1516_2025.fedpro.UnpublishObjectClassDirectedInteractionsWithSetRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setInteractionClasses(_clientConverter.convertFromHla(interactionClasses));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishObjectClassDirectedInteractionsWithSetRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnpublishObjectClassDirectedInteractionsWithSetResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNPUBLISHOBJECTCLASSDIRECTEDINTERACTIONSWITHSETRESPONSE));
         }
         return null;
      });
   }

   public void subscribeObjectClassAttributes(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
   throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassAttributes(
               objectClass,
               attributes
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSubscribeObjectClassAttributes(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
   {
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesRequest request;
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesRequest. Builder builder = hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassAttributesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSATTRIBUTESRESPONSE));
         }
         return null;
      });
   }

   public void subscribeObjectClassAttributes(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes,
      java.lang.String updateRateDesignator
   )
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
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassAttributes(
               objectClass,
               attributes,
               updateRateDesignator
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSubscribeObjectClassAttributes(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes,
      java.lang.String updateRateDesignator
   )
   {
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRateRequest request;
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRateRequest. Builder builder = hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRateRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
         builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesWithRateRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassAttributesWithRateResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSATTRIBUTESWITHRATERESPONSE));
         }
         return null;
      });
   }

   public void subscribeObjectClassAttributesPassively(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
   throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassAttributesPassively(
               objectClass,
               attributes
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSubscribeObjectClassAttributesPassively(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
   {
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesPassivelyRequest request;
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesPassivelyRequest. Builder builder = hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesPassivelyRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesPassivelyRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassAttributesPassivelyResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSATTRIBUTESPASSIVELYRESPONSE));
         }
         return null;
      });
   }

   public void subscribeObjectClassAttributesPassively(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes,
      java.lang.String updateRateDesignator
   )
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
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassAttributesPassively(
               objectClass,
               attributes,
               updateRateDesignator
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSubscribeObjectClassAttributesPassively(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes,
      java.lang.String updateRateDesignator
   )
   {
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesPassivelyWithRateRequest request;
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesPassivelyWithRateRequest. Builder builder = hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesPassivelyWithRateRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
         builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesPassivelyWithRateRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassAttributesPassivelyWithRateResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSATTRIBUTESPASSIVELYWITHRATERESPONSE));
         }
         return null;
      });
   }

   public void unsubscribeObjectClass(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   throws
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncUnsubscribeObjectClass(
               objectClass
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUnsubscribeObjectClass(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   {
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassRequest request;
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassRequest. Builder builder = hla.rti1516_2025.fedpro.UnsubscribeObjectClassRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnsubscribeObjectClassResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNSUBSCRIBEOBJECTCLASSRESPONSE));
         }
         return null;
      });
   }

   public void unsubscribeObjectClassAttributes(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
   throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncUnsubscribeObjectClassAttributes(
               objectClass,
               attributes
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUnsubscribeObjectClassAttributes(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
   {
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassAttributesRequest request;
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassAttributesRequest. Builder builder = hla.rti1516_2025.fedpro.UnsubscribeObjectClassAttributesRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassAttributesRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnsubscribeObjectClassAttributesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNSUBSCRIBEOBJECTCLASSATTRIBUTESRESPONSE));
         }
         return null;
      });
   }

   public void subscribeInteractionClass(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   throws
      FederateServiceInvocationsAreBeingReportedViaMOM,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSubscribeInteractionClass(
               interactionClass
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSubscribeInteractionClass(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   {
      hla.rti1516_2025.fedpro.SubscribeInteractionClassRequest request;
      hla.rti1516_2025.fedpro.SubscribeInteractionClassRequest. Builder builder = hla.rti1516_2025.fedpro.SubscribeInteractionClassRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeInteractionClassRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeInteractionClassResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEINTERACTIONCLASSRESPONSE));
         }
         return null;
      });
   }

   public void subscribeInteractionClassPassively(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   throws
      FederateServiceInvocationsAreBeingReportedViaMOM,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSubscribeInteractionClassPassively(
               interactionClass
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSubscribeInteractionClassPassively(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   {
      hla.rti1516_2025.fedpro.SubscribeInteractionClassPassivelyRequest request;
      hla.rti1516_2025.fedpro.SubscribeInteractionClassPassivelyRequest. Builder builder = hla.rti1516_2025.fedpro.SubscribeInteractionClassPassivelyRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeInteractionClassPassivelyRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeInteractionClassPassivelyResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEINTERACTIONCLASSPASSIVELYRESPONSE));
         }
         return null;
      });
   }

   public void unsubscribeInteractionClass(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncUnsubscribeInteractionClass(
               interactionClass
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUnsubscribeInteractionClass(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   {
      hla.rti1516_2025.fedpro.UnsubscribeInteractionClassRequest request;
      hla.rti1516_2025.fedpro.UnsubscribeInteractionClassRequest. Builder builder = hla.rti1516_2025.fedpro.UnsubscribeInteractionClassRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeInteractionClassRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnsubscribeInteractionClassResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNSUBSCRIBEINTERACTIONCLASSRESPONSE));
         }
         return null;
      });
   }

   public void subscribeObjectClassDirectedInteractions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.InteractionClassHandleSet interactionClasses
   )
   throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassDirectedInteractions(
               objectClass,
               interactionClasses
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSubscribeObjectClassDirectedInteractions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.InteractionClassHandleSet interactionClasses
   )
   {
      hla.rti1516_2025.fedpro.SubscribeObjectClassDirectedInteractionsRequest request;
      hla.rti1516_2025.fedpro.SubscribeObjectClassDirectedInteractionsRequest. Builder builder = hla.rti1516_2025.fedpro.SubscribeObjectClassDirectedInteractionsRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setInteractionClasses(_clientConverter.convertFromHla(interactionClasses));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassDirectedInteractionsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassDirectedInteractionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSDIRECTEDINTERACTIONSRESPONSE));
         }
         return null;
      });
   }

   public void subscribeObjectClassDirectedInteractionsUniversally(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.InteractionClassHandleSet interactionClasses
   )
   throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassDirectedInteractionsUniversally(
               objectClass,
               interactionClasses
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSubscribeObjectClassDirectedInteractionsUniversally(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.InteractionClassHandleSet interactionClasses
   )
   {
      hla.rti1516_2025.fedpro.SubscribeObjectClassDirectedInteractionsUniversallyRequest request;
      hla.rti1516_2025.fedpro.SubscribeObjectClassDirectedInteractionsUniversallyRequest. Builder builder = hla.rti1516_2025.fedpro.SubscribeObjectClassDirectedInteractionsUniversallyRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setInteractionClasses(_clientConverter.convertFromHla(interactionClasses));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassDirectedInteractionsUniversallyRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassDirectedInteractionsUniversallyResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSDIRECTEDINTERACTIONSUNIVERSALLYRESPONSE));
         }
         return null;
      });
   }

   public void unsubscribeObjectClassDirectedInteractions(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   throws
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncUnsubscribeObjectClassDirectedInteractions(
               objectClass
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUnsubscribeObjectClassDirectedInteractions(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   {
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassDirectedInteractionsRequest request;
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassDirectedInteractionsRequest. Builder builder = hla.rti1516_2025.fedpro.UnsubscribeObjectClassDirectedInteractionsRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassDirectedInteractionsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnsubscribeObjectClassDirectedInteractionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNSUBSCRIBEOBJECTCLASSDIRECTEDINTERACTIONSRESPONSE));
         }
         return null;
      });
   }

   public void unsubscribeObjectClassDirectedInteractions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.InteractionClassHandleSet interactionClasses
   )
   throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncUnsubscribeObjectClassDirectedInteractions(
               objectClass,
               interactionClasses
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUnsubscribeObjectClassDirectedInteractions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.InteractionClassHandleSet interactionClasses
   )
   {
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassDirectedInteractionsWithSetRequest request;
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassDirectedInteractionsWithSetRequest. Builder builder = hla.rti1516_2025.fedpro.UnsubscribeObjectClassDirectedInteractionsWithSetRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setInteractionClasses(_clientConverter.convertFromHla(interactionClasses));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassDirectedInteractionsWithSetRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnsubscribeObjectClassDirectedInteractionsWithSetResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNSUBSCRIBEOBJECTCLASSDIRECTEDINTERACTIONSWITHSETRESPONSE));
         }
         return null;
      });
   }

   public void reserveObjectInstanceName(
      java.lang.String objectInstanceName
   )
   throws
      IllegalName,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncReserveObjectInstanceName(
               objectInstanceName
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncReserveObjectInstanceName(
      java.lang.String objectInstanceName
   )
   {
      hla.rti1516_2025.fedpro.ReserveObjectInstanceNameRequest request;
      hla.rti1516_2025.fedpro.ReserveObjectInstanceNameRequest. Builder builder = hla.rti1516_2025.fedpro.ReserveObjectInstanceNameRequest. newBuilder();

      try {
         builder.setObjectInstanceName(_clientConverter.convertFromHla(objectInstanceName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReserveObjectInstanceNameRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasReserveObjectInstanceNameResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.RESERVEOBJECTINSTANCENAMERESPONSE));
         }
         return null;
      });
   }

   public void releaseObjectInstanceName(
      java.lang.String objectInstanceName
   )
   throws
      ObjectInstanceNameNotReserved,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncReleaseObjectInstanceName(
               objectInstanceName
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncReleaseObjectInstanceName(
      java.lang.String objectInstanceName
   )
   {
      hla.rti1516_2025.fedpro.ReleaseObjectInstanceNameRequest request;
      hla.rti1516_2025.fedpro.ReleaseObjectInstanceNameRequest. Builder builder = hla.rti1516_2025.fedpro.ReleaseObjectInstanceNameRequest. newBuilder();

      try {
         builder.setObjectInstanceName(_clientConverter.convertFromHla(objectInstanceName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReleaseObjectInstanceNameRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasReleaseObjectInstanceNameResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.RELEASEOBJECTINSTANCENAMERESPONSE));
         }
         return null;
      });
   }

   public void reserveMultipleObjectInstanceNames(
      java.util.Set<java.lang.String> objectInstanceNames
   )
   throws
      IllegalName,
      NameSetWasEmpty,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncReserveMultipleObjectInstanceNames(
               objectInstanceNames
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncReserveMultipleObjectInstanceNames(
      java.util.Set<java.lang.String> objectInstanceNames
   )
   {
      hla.rti1516_2025.fedpro.ReserveMultipleObjectInstanceNamesRequest request;
      hla.rti1516_2025.fedpro.ReserveMultipleObjectInstanceNamesRequest. Builder builder = hla.rti1516_2025.fedpro.ReserveMultipleObjectInstanceNamesRequest. newBuilder();

      try {
         builder.addAllObjectInstanceNames(_clientConverter.convertFromHla(objectInstanceNames));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReserveMultipleObjectInstanceNamesRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasReserveMultipleObjectInstanceNamesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.RESERVEMULTIPLEOBJECTINSTANCENAMESRESPONSE));
         }
         return null;
      });
   }

   public void releaseMultipleObjectInstanceNames(
      java.util.Set<java.lang.String> objectInstanceNames
   )
   throws
      ObjectInstanceNameNotReserved,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncReleaseMultipleObjectInstanceNames(
               objectInstanceNames
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncReleaseMultipleObjectInstanceNames(
      java.util.Set<java.lang.String> objectInstanceNames
   )
   {
      hla.rti1516_2025.fedpro.ReleaseMultipleObjectInstanceNamesRequest request;
      hla.rti1516_2025.fedpro.ReleaseMultipleObjectInstanceNamesRequest. Builder builder = hla.rti1516_2025.fedpro.ReleaseMultipleObjectInstanceNamesRequest. newBuilder();

      try {
         builder.addAllObjectInstanceNames(_clientConverter.convertFromHla(objectInstanceNames));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReleaseMultipleObjectInstanceNamesRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasReleaseMultipleObjectInstanceNamesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.RELEASEMULTIPLEOBJECTINSTANCENAMESRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_2025.ObjectInstanceHandle registerObjectInstance(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   throws
      ObjectClassNotPublished,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<ObjectInstanceHandle> future =
            asyncRegisterObjectInstance(
               objectClass
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<ObjectInstanceHandle> asyncRegisterObjectInstance(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   {
      hla.rti1516_2025.fedpro.RegisterObjectInstanceRequest request;
      hla.rti1516_2025.fedpro.RegisterObjectInstanceRequest. Builder builder = hla.rti1516_2025.fedpro.RegisterObjectInstanceRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterObjectInstanceRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasRegisterObjectInstanceResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.REGISTEROBJECTINSTANCERESPONSE);
            }
            hla.rti1516_2025.fedpro.RegisterObjectInstanceResponse response = callResponse.getRegisterObjectInstanceResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.ObjectInstanceHandle registerObjectInstanceWithName(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      java.lang.String objectInstanceName
   )
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
      try {
         CompletableFuture<ObjectInstanceHandle> future =
            asyncRegisterObjectInstanceWithName(
               objectClass,
               objectInstanceName
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<ObjectInstanceHandle> asyncRegisterObjectInstanceWithName(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      java.lang.String objectInstanceName
   )
   {
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameRequest request;
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameRequest. Builder builder = hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setObjectInstanceName(_clientConverter.convertFromHla(objectInstanceName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterObjectInstanceWithNameRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasRegisterObjectInstanceWithNameResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.REGISTEROBJECTINSTANCEWITHNAMERESPONSE);
            }
            hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameResponse response = callResponse.getRegisterObjectInstanceWithNameResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void updateAttributeValues(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleValueMap attributeValues,
      byte[] userSuppliedTag
   )
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
      try {
         CompletableFuture<Void> future =
            asyncUpdateAttributeValues(
               objectInstance,
               attributeValues,
               userSuppliedTag
            );
         if (_asyncUpdates) {
            countAsyncUpdateForStats();
            // Ignore future result
            return;
         } else {
            countSyncUpdateForStats();
         }
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUpdateAttributeValues(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleValueMap attributeValues,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.UpdateAttributeValuesRequest request;
      hla.rti1516_2025.fedpro.UpdateAttributeValuesRequest. Builder builder = hla.rti1516_2025.fedpro.UpdateAttributeValuesRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttributeValues(_clientConverter.convertFromHla(attributeValues));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUpdateAttributeValuesRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUpdateAttributeValuesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UPDATEATTRIBUTEVALUESRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_2025.MessageRetractionReturn updateAttributeValues(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleValueMap attributeValues,
      byte[] userSuppliedTag,
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
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
      try {
         CompletableFuture<MessageRetractionReturn> future =
            asyncUpdateAttributeValues(
               objectInstance,
               attributeValues,
               userSuppliedTag,
               time
            );
         countSyncUpdateForStats();
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<MessageRetractionReturn> asyncUpdateAttributeValues(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleValueMap attributeValues,
      byte[] userSuppliedTag,
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
   {
      hla.rti1516_2025.fedpro.UpdateAttributeValuesWithTimeRequest request;
      hla.rti1516_2025.fedpro.UpdateAttributeValuesWithTimeRequest. Builder builder = hla.rti1516_2025.fedpro.UpdateAttributeValuesWithTimeRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttributeValues(_clientConverter.convertFromHla(attributeValues));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
         builder.setTime(_clientConverter.convertFromHla(time));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUpdateAttributeValuesWithTimeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasUpdateAttributeValuesWithTimeResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.UPDATEATTRIBUTEVALUESWITHTIMERESPONSE);
            }
            hla.rti1516_2025.fedpro.UpdateAttributeValuesWithTimeResponse response = callResponse.getUpdateAttributeValuesWithTimeResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void sendInteraction(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.ParameterHandleValueMap parameterValues,
      byte[] userSuppliedTag
   )
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
      try {
         CompletableFuture<Void> future =
            asyncSendInteraction(
               interactionClass,
               parameterValues,
               userSuppliedTag
            );
         if (_asyncUpdates) {
            countAsyncSentInteractionForStats();
            // Ignore future result
            return;
         } else {
            countSyncSentInteractionForStats();
         }
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSendInteraction(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.ParameterHandleValueMap parameterValues,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.SendInteractionRequest request;
      hla.rti1516_2025.fedpro.SendInteractionRequest. Builder builder = hla.rti1516_2025.fedpro.SendInteractionRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
         builder.setParameterValues(_clientConverter.convertFromHla(parameterValues));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSendInteractionResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SENDINTERACTIONRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_2025.MessageRetractionReturn sendInteraction(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.ParameterHandleValueMap parameterValues,
      byte[] userSuppliedTag,
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
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
      try {
         CompletableFuture<MessageRetractionReturn> future =
            asyncSendInteraction(
               interactionClass,
               parameterValues,
               userSuppliedTag,
               time
            );
         countSyncSentInteractionForStats();
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<MessageRetractionReturn> asyncSendInteraction(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.ParameterHandleValueMap parameterValues,
      byte[] userSuppliedTag,
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
   {
      hla.rti1516_2025.fedpro.SendInteractionWithTimeRequest request;
      hla.rti1516_2025.fedpro.SendInteractionWithTimeRequest. Builder builder = hla.rti1516_2025.fedpro.SendInteractionWithTimeRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
         builder.setParameterValues(_clientConverter.convertFromHla(parameterValues));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
         builder.setTime(_clientConverter.convertFromHla(time));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionWithTimeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasSendInteractionWithTimeResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.SENDINTERACTIONWITHTIMERESPONSE);
            }
            hla.rti1516_2025.fedpro.SendInteractionWithTimeResponse response = callResponse.getSendInteractionWithTimeResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void sendDirectedInteraction(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.ParameterHandleValueMap parameterValues,
      byte[] userSuppliedTag
   )
   throws
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
      try {
         CompletableFuture<Void> future =
            asyncSendDirectedInteraction(
               interactionClass,
               objectInstance,
               parameterValues,
               userSuppliedTag
            );
         if (_asyncUpdates) {
            countAsyncSentDirectedInteractionForStats();
            // Ignore future result
            return;
         } else {
            countSyncSentDirectedInteractionForStats();
         }
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSendDirectedInteraction(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.ParameterHandleValueMap parameterValues,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.SendDirectedInteractionRequest request;
      hla.rti1516_2025.fedpro.SendDirectedInteractionRequest. Builder builder = hla.rti1516_2025.fedpro.SendDirectedInteractionRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setParameterValues(_clientConverter.convertFromHla(parameterValues));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendDirectedInteractionRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSendDirectedInteractionResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SENDDIRECTEDINTERACTIONRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_2025.MessageRetractionReturn sendDirectedInteraction(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.ParameterHandleValueMap parameterValues,
      byte[] userSuppliedTag,
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
   throws
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
      try {
         CompletableFuture<MessageRetractionReturn> future =
            asyncSendDirectedInteraction(
               interactionClass,
               objectInstance,
               parameterValues,
               userSuppliedTag,
               time
            );
         countSyncSentDirectedInteractionForStats();
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<MessageRetractionReturn> asyncSendDirectedInteraction(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.ParameterHandleValueMap parameterValues,
      byte[] userSuppliedTag,
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
   {
      hla.rti1516_2025.fedpro.SendDirectedInteractionWithTimeRequest request;
      hla.rti1516_2025.fedpro.SendDirectedInteractionWithTimeRequest. Builder builder = hla.rti1516_2025.fedpro.SendDirectedInteractionWithTimeRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setParameterValues(_clientConverter.convertFromHla(parameterValues));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
         builder.setTime(_clientConverter.convertFromHla(time));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendDirectedInteractionWithTimeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasSendDirectedInteractionWithTimeResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.SENDDIRECTEDINTERACTIONWITHTIMERESPONSE);
            }
            hla.rti1516_2025.fedpro.SendDirectedInteractionWithTimeResponse response = callResponse.getSendDirectedInteractionWithTimeResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void deleteObjectInstance(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      byte[] userSuppliedTag
   )
   throws
      DeletePrivilegeNotHeld,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncDeleteObjectInstance(
               objectInstance,
               userSuppliedTag
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncDeleteObjectInstance(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.DeleteObjectInstanceRequest request;
      hla.rti1516_2025.fedpro.DeleteObjectInstanceRequest. Builder builder = hla.rti1516_2025.fedpro.DeleteObjectInstanceRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setDeleteObjectInstanceRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasDeleteObjectInstanceResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.DELETEOBJECTINSTANCERESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_2025.MessageRetractionReturn deleteObjectInstance(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      byte[] userSuppliedTag,
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
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
      try {
         CompletableFuture<MessageRetractionReturn> future =
            asyncDeleteObjectInstance(
               objectInstance,
               userSuppliedTag,
               time
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<MessageRetractionReturn> asyncDeleteObjectInstance(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      byte[] userSuppliedTag,
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
   {
      hla.rti1516_2025.fedpro.DeleteObjectInstanceWithTimeRequest request;
      hla.rti1516_2025.fedpro.DeleteObjectInstanceWithTimeRequest. Builder builder = hla.rti1516_2025.fedpro.DeleteObjectInstanceWithTimeRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
         builder.setTime(_clientConverter.convertFromHla(time));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setDeleteObjectInstanceWithTimeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasDeleteObjectInstanceWithTimeResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.DELETEOBJECTINSTANCEWITHTIMERESPONSE);
            }
            hla.rti1516_2025.fedpro.DeleteObjectInstanceWithTimeResponse response = callResponse.getDeleteObjectInstanceWithTimeResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void localDeleteObjectInstance(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance
   )
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
      try {
         CompletableFuture<Void> future =
            asyncLocalDeleteObjectInstance(
               objectInstance
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncLocalDeleteObjectInstance(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance
   )
   {
      hla.rti1516_2025.fedpro.LocalDeleteObjectInstanceRequest request;
      hla.rti1516_2025.fedpro.LocalDeleteObjectInstanceRequest. Builder builder = hla.rti1516_2025.fedpro.LocalDeleteObjectInstanceRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setLocalDeleteObjectInstanceRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasLocalDeleteObjectInstanceResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.LOCALDELETEOBJECTINSTANCERESPONSE));
         }
         return null;
      });
   }

   public void requestAttributeValueUpdate(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes,
      byte[] userSuppliedTag
   )
   throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncRequestAttributeValueUpdate(
               objectInstance,
               attributes,
               userSuppliedTag
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncRequestAttributeValueUpdate(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.RequestInstanceAttributeValueUpdateRequest request;
      hla.rti1516_2025.fedpro.RequestInstanceAttributeValueUpdateRequest. Builder builder = hla.rti1516_2025.fedpro.RequestInstanceAttributeValueUpdateRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestInstanceAttributeValueUpdateRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestInstanceAttributeValueUpdateResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTINSTANCEATTRIBUTEVALUEUPDATERESPONSE));
         }
         return null;
      });
   }

   public void requestAttributeValueUpdate(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes,
      byte[] userSuppliedTag
   )
   throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncRequestAttributeValueUpdate(
               objectClass,
               attributes,
               userSuppliedTag
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncRequestAttributeValueUpdate(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.RequestClassAttributeValueUpdateRequest request;
      hla.rti1516_2025.fedpro.RequestClassAttributeValueUpdateRequest. Builder builder = hla.rti1516_2025.fedpro.RequestClassAttributeValueUpdateRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestClassAttributeValueUpdateRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestClassAttributeValueUpdateResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTCLASSATTRIBUTEVALUEUPDATERESPONSE));
         }
         return null;
      });
   }

   public void requestAttributeTransportationTypeChange(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes,
      hla.rti1516_2025.TransportationTypeHandle transportationType
   )
   throws
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
      try {
         CompletableFuture<Void> future =
            asyncRequestAttributeTransportationTypeChange(
               objectInstance,
               attributes,
               transportationType
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncRequestAttributeTransportationTypeChange(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes,
      hla.rti1516_2025.TransportationTypeHandle transportationType
   )
   {
      hla.rti1516_2025.fedpro.RequestAttributeTransportationTypeChangeRequest request;
      hla.rti1516_2025.fedpro.RequestAttributeTransportationTypeChangeRequest. Builder builder = hla.rti1516_2025.fedpro.RequestAttributeTransportationTypeChangeRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
         builder.setTransportationType(_clientConverter.convertFromHla(transportationType));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestAttributeTransportationTypeChangeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestAttributeTransportationTypeChangeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTATTRIBUTETRANSPORTATIONTYPECHANGERESPONSE));
         }
         return null;
      });
   }

   public void changeDefaultAttributeTransportationType(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes,
      hla.rti1516_2025.TransportationTypeHandle transportationType
   )
   throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      InvalidTransportationTypeHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncChangeDefaultAttributeTransportationType(
               objectClass,
               attributes,
               transportationType
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncChangeDefaultAttributeTransportationType(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandleSet attributes,
      hla.rti1516_2025.TransportationTypeHandle transportationType
   )
   {
      hla.rti1516_2025.fedpro.ChangeDefaultAttributeTransportationTypeRequest request;
      hla.rti1516_2025.fedpro.ChangeDefaultAttributeTransportationTypeRequest. Builder builder = hla.rti1516_2025.fedpro.ChangeDefaultAttributeTransportationTypeRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
         builder.setTransportationType(_clientConverter.convertFromHla(transportationType));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setChangeDefaultAttributeTransportationTypeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasChangeDefaultAttributeTransportationTypeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.CHANGEDEFAULTATTRIBUTETRANSPORTATIONTYPERESPONSE));
         }
         return null;
      });
   }

   public void queryAttributeTransportationType(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandle attribute
   )
   throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncQueryAttributeTransportationType(
               objectInstance,
               attribute
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncQueryAttributeTransportationType(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandle attribute
   )
   {
      hla.rti1516_2025.fedpro.QueryAttributeTransportationTypeRequest request;
      hla.rti1516_2025.fedpro.QueryAttributeTransportationTypeRequest. Builder builder = hla.rti1516_2025.fedpro.QueryAttributeTransportationTypeRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttribute(_clientConverter.convertFromHla(attribute));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setQueryAttributeTransportationTypeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasQueryAttributeTransportationTypeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.QUERYATTRIBUTETRANSPORTATIONTYPERESPONSE));
         }
         return null;
      });
   }

   public void requestInteractionTransportationTypeChange(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.TransportationTypeHandle transportationType
   )
   throws
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
      try {
         CompletableFuture<Void> future =
            asyncRequestInteractionTransportationTypeChange(
               interactionClass,
               transportationType
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncRequestInteractionTransportationTypeChange(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.TransportationTypeHandle transportationType
   )
   {
      hla.rti1516_2025.fedpro.RequestInteractionTransportationTypeChangeRequest request;
      hla.rti1516_2025.fedpro.RequestInteractionTransportationTypeChangeRequest. Builder builder = hla.rti1516_2025.fedpro.RequestInteractionTransportationTypeChangeRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
         builder.setTransportationType(_clientConverter.convertFromHla(transportationType));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestInteractionTransportationTypeChangeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestInteractionTransportationTypeChangeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTINTERACTIONTRANSPORTATIONTYPECHANGERESPONSE));
         }
         return null;
      });
   }

   public void queryInteractionTransportationType(
      hla.rti1516_2025.FederateHandle federate,
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncQueryInteractionTransportationType(
               federate,
               interactionClass
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncQueryInteractionTransportationType(
      hla.rti1516_2025.FederateHandle federate,
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   {
      hla.rti1516_2025.fedpro.QueryInteractionTransportationTypeRequest request;
      hla.rti1516_2025.fedpro.QueryInteractionTransportationTypeRequest. Builder builder = hla.rti1516_2025.fedpro.QueryInteractionTransportationTypeRequest. newBuilder();

      try {
         builder.setFederate(_clientConverter.convertFromHla(federate));
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setQueryInteractionTransportationTypeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasQueryInteractionTransportationTypeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.QUERYINTERACTIONTRANSPORTATIONTYPERESPONSE));
         }
         return null;
      });
   }

   public void unconditionalAttributeOwnershipDivestiture(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes,
      byte[] userSuppliedTag
   )
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
      try {
         CompletableFuture<Void> future =
            asyncUnconditionalAttributeOwnershipDivestiture(
               objectInstance,
               attributes,
               userSuppliedTag
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUnconditionalAttributeOwnershipDivestiture(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.UnconditionalAttributeOwnershipDivestitureRequest request;
      hla.rti1516_2025.fedpro.UnconditionalAttributeOwnershipDivestitureRequest. Builder builder = hla.rti1516_2025.fedpro.UnconditionalAttributeOwnershipDivestitureRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnconditionalAttributeOwnershipDivestitureRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnconditionalAttributeOwnershipDivestitureResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNCONDITIONALATTRIBUTEOWNERSHIPDIVESTITURERESPONSE));
         }
         return null;
      });
   }

   public void negotiatedAttributeOwnershipDivestiture(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes,
      byte[] userSuppliedTag
   )
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
      try {
         CompletableFuture<Void> future =
            asyncNegotiatedAttributeOwnershipDivestiture(
               objectInstance,
               attributes,
               userSuppliedTag
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncNegotiatedAttributeOwnershipDivestiture(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.NegotiatedAttributeOwnershipDivestitureRequest request;
      hla.rti1516_2025.fedpro.NegotiatedAttributeOwnershipDivestitureRequest. Builder builder = hla.rti1516_2025.fedpro.NegotiatedAttributeOwnershipDivestitureRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNegotiatedAttributeOwnershipDivestitureRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasNegotiatedAttributeOwnershipDivestitureResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.NEGOTIATEDATTRIBUTEOWNERSHIPDIVESTITURERESPONSE));
         }
         return null;
      });
   }

   public void confirmDivestiture(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet confirmedAttributes,
      byte[] userSuppliedTag
   )
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
      try {
         CompletableFuture<Void> future =
            asyncConfirmDivestiture(
               objectInstance,
               confirmedAttributes,
               userSuppliedTag
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncConfirmDivestiture(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet confirmedAttributes,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.ConfirmDivestitureRequest request;
      hla.rti1516_2025.fedpro.ConfirmDivestitureRequest. Builder builder = hla.rti1516_2025.fedpro.ConfirmDivestitureRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setConfirmedAttributes(_clientConverter.convertFromHla(confirmedAttributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setConfirmDivestitureRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasConfirmDivestitureResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.CONFIRMDIVESTITURERESPONSE));
         }
         return null;
      });
   }

   public void attributeOwnershipAcquisition(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet desiredAttributes,
      byte[] userSuppliedTag
   )
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
      try {
         CompletableFuture<Void> future =
            asyncAttributeOwnershipAcquisition(
               objectInstance,
               desiredAttributes,
               userSuppliedTag
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncAttributeOwnershipAcquisition(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet desiredAttributes,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.AttributeOwnershipAcquisitionRequest request;
      hla.rti1516_2025.fedpro.AttributeOwnershipAcquisitionRequest. Builder builder = hla.rti1516_2025.fedpro.AttributeOwnershipAcquisitionRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setDesiredAttributes(_clientConverter.convertFromHla(desiredAttributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAttributeOwnershipAcquisitionRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasAttributeOwnershipAcquisitionResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ATTRIBUTEOWNERSHIPACQUISITIONRESPONSE));
         }
         return null;
      });
   }

   public void attributeOwnershipAcquisitionIfAvailable(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet desiredAttributes,
      byte[] userSuppliedTag
   )
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
      try {
         CompletableFuture<Void> future =
            asyncAttributeOwnershipAcquisitionIfAvailable(
               objectInstance,
               desiredAttributes,
               userSuppliedTag
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncAttributeOwnershipAcquisitionIfAvailable(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet desiredAttributes,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.AttributeOwnershipAcquisitionIfAvailableRequest request;
      hla.rti1516_2025.fedpro.AttributeOwnershipAcquisitionIfAvailableRequest. Builder builder = hla.rti1516_2025.fedpro.AttributeOwnershipAcquisitionIfAvailableRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setDesiredAttributes(_clientConverter.convertFromHla(desiredAttributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAttributeOwnershipAcquisitionIfAvailableRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasAttributeOwnershipAcquisitionIfAvailableResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ATTRIBUTEOWNERSHIPACQUISITIONIFAVAILABLERESPONSE));
         }
         return null;
      });
   }

   public void attributeOwnershipReleaseDenied(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes,
      byte[] userSuppliedTag
   )
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
      try {
         CompletableFuture<Void> future =
            asyncAttributeOwnershipReleaseDenied(
               objectInstance,
               attributes,
               userSuppliedTag
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncAttributeOwnershipReleaseDenied(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.AttributeOwnershipReleaseDeniedRequest request;
      hla.rti1516_2025.fedpro.AttributeOwnershipReleaseDeniedRequest. Builder builder = hla.rti1516_2025.fedpro.AttributeOwnershipReleaseDeniedRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAttributeOwnershipReleaseDeniedRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasAttributeOwnershipReleaseDeniedResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ATTRIBUTEOWNERSHIPRELEASEDENIEDRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_2025.AttributeHandleSet attributeOwnershipDivestitureIfWanted(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes,
      byte[] userSuppliedTag
   )
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
      try {
         CompletableFuture<AttributeHandleSet> future =
            asyncAttributeOwnershipDivestitureIfWanted(
               objectInstance,
               attributes,
               userSuppliedTag
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<AttributeHandleSet> asyncAttributeOwnershipDivestitureIfWanted(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.AttributeOwnershipDivestitureIfWantedRequest request;
      hla.rti1516_2025.fedpro.AttributeOwnershipDivestitureIfWantedRequest. Builder builder = hla.rti1516_2025.fedpro.AttributeOwnershipDivestitureIfWantedRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAttributeOwnershipDivestitureIfWantedRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasAttributeOwnershipDivestitureIfWantedResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.ATTRIBUTEOWNERSHIPDIVESTITUREIFWANTEDRESPONSE);
            }
            hla.rti1516_2025.fedpro.AttributeOwnershipDivestitureIfWantedResponse response = callResponse.getAttributeOwnershipDivestitureIfWantedResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void cancelNegotiatedAttributeOwnershipDivestiture(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
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
      try {
         CompletableFuture<Void> future =
            asyncCancelNegotiatedAttributeOwnershipDivestiture(
               objectInstance,
               attributes
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncCancelNegotiatedAttributeOwnershipDivestiture(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
   {
      hla.rti1516_2025.fedpro.CancelNegotiatedAttributeOwnershipDivestitureRequest request;
      hla.rti1516_2025.fedpro.CancelNegotiatedAttributeOwnershipDivestitureRequest. Builder builder = hla.rti1516_2025.fedpro.CancelNegotiatedAttributeOwnershipDivestitureRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCancelNegotiatedAttributeOwnershipDivestitureRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasCancelNegotiatedAttributeOwnershipDivestitureResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.CANCELNEGOTIATEDATTRIBUTEOWNERSHIPDIVESTITURERESPONSE));
         }
         return null;
      });
   }

   public void cancelAttributeOwnershipAcquisition(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
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
      try {
         CompletableFuture<Void> future =
            asyncCancelAttributeOwnershipAcquisition(
               objectInstance,
               attributes
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncCancelAttributeOwnershipAcquisition(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
   {
      hla.rti1516_2025.fedpro.CancelAttributeOwnershipAcquisitionRequest request;
      hla.rti1516_2025.fedpro.CancelAttributeOwnershipAcquisitionRequest. Builder builder = hla.rti1516_2025.fedpro.CancelAttributeOwnershipAcquisitionRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCancelAttributeOwnershipAcquisitionRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasCancelAttributeOwnershipAcquisitionResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.CANCELATTRIBUTEOWNERSHIPACQUISITIONRESPONSE));
         }
         return null;
      });
   }

   public void queryAttributeOwnership(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
   throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncQueryAttributeOwnership(
               objectInstance,
               attributes
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncQueryAttributeOwnership(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes
   )
   {
      hla.rti1516_2025.fedpro.QueryAttributeOwnershipRequest request;
      hla.rti1516_2025.fedpro.QueryAttributeOwnershipRequest. Builder builder = hla.rti1516_2025.fedpro.QueryAttributeOwnershipRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setQueryAttributeOwnershipRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasQueryAttributeOwnershipResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.QUERYATTRIBUTEOWNERSHIPRESPONSE));
         }
         return null;
      });
   }

   public boolean isAttributeOwnedByFederate(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandle attribute
   )
   throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Boolean> future =
            asyncIsAttributeOwnedByFederate(
               objectInstance,
               attribute
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Boolean> asyncIsAttributeOwnedByFederate(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandle attribute
   )
   {
      hla.rti1516_2025.fedpro.IsAttributeOwnedByFederateRequest request;
      hla.rti1516_2025.fedpro.IsAttributeOwnedByFederateRequest. Builder builder = hla.rti1516_2025.fedpro.IsAttributeOwnedByFederateRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttribute(_clientConverter.convertFromHla(attribute));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setIsAttributeOwnedByFederateRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasIsAttributeOwnedByFederateResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.ISATTRIBUTEOWNEDBYFEDERATERESPONSE);
            }
            hla.rti1516_2025.fedpro.IsAttributeOwnedByFederateResponse response = callResponse.getIsAttributeOwnedByFederateResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void enableTimeRegulation(
      hla.rti1516_2025.time.LogicalTimeInterval<?> lookahead
   )
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
      try {
         CompletableFuture<Void> future =
            asyncEnableTimeRegulation(
               lookahead
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncEnableTimeRegulation(
      hla.rti1516_2025.time.LogicalTimeInterval<?> lookahead
   )
   {
      hla.rti1516_2025.fedpro.EnableTimeRegulationRequest request;
      hla.rti1516_2025.fedpro.EnableTimeRegulationRequest. Builder builder = hla.rti1516_2025.fedpro.EnableTimeRegulationRequest. newBuilder();

      try {
         builder.setLookahead(_clientConverter.convertFromHla(lookahead));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setEnableTimeRegulationRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasEnableTimeRegulationResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ENABLETIMEREGULATIONRESPONSE));
         }
         return null;
      });
   }

   public void disableTimeRegulation(

   )
   throws
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncDisableTimeRegulation(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncDisableTimeRegulation(

   )
   {
      hla.rti1516_2025.fedpro.DisableTimeRegulationRequest request;
      request = hla.rti1516_2025.fedpro.DisableTimeRegulationRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableTimeRegulationRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasDisableTimeRegulationResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.DISABLETIMEREGULATIONRESPONSE));
         }
         return null;
      });
   }

   public void enableTimeConstrained(

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
      try {
         CompletableFuture<Void> future =
            asyncEnableTimeConstrained(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncEnableTimeConstrained(

   )
   {
      hla.rti1516_2025.fedpro.EnableTimeConstrainedRequest request;
      request = hla.rti1516_2025.fedpro.EnableTimeConstrainedRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setEnableTimeConstrainedRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasEnableTimeConstrainedResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ENABLETIMECONSTRAINEDRESPONSE));
         }
         return null;
      });
   }

   public void disableTimeConstrained(

   )
   throws
      TimeConstrainedIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncDisableTimeConstrained(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncDisableTimeConstrained(

   )
   {
      hla.rti1516_2025.fedpro.DisableTimeConstrainedRequest request;
      request = hla.rti1516_2025.fedpro.DisableTimeConstrainedRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableTimeConstrainedRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasDisableTimeConstrainedResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.DISABLETIMECONSTRAINEDRESPONSE));
         }
         return null;
      });
   }

   public void timeAdvanceRequest(
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
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
      try {
         CompletableFuture<Void> future =
            asyncTimeAdvanceRequest(
               time
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncTimeAdvanceRequest(
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
   {
      hla.rti1516_2025.fedpro.TimeAdvanceRequestRequest request;
      hla.rti1516_2025.fedpro.TimeAdvanceRequestRequest. Builder builder = hla.rti1516_2025.fedpro.TimeAdvanceRequestRequest. newBuilder();

      try {
         builder.setTime(_clientConverter.convertFromHla(time));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setTimeAdvanceRequestRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasTimeAdvanceRequestResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.TIMEADVANCEREQUESTRESPONSE));
         }
         return null;
      });
   }

   public void timeAdvanceRequestAvailable(
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
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
      try {
         CompletableFuture<Void> future =
            asyncTimeAdvanceRequestAvailable(
               time
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncTimeAdvanceRequestAvailable(
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
   {
      hla.rti1516_2025.fedpro.TimeAdvanceRequestAvailableRequest request;
      hla.rti1516_2025.fedpro.TimeAdvanceRequestAvailableRequest. Builder builder = hla.rti1516_2025.fedpro.TimeAdvanceRequestAvailableRequest. newBuilder();

      try {
         builder.setTime(_clientConverter.convertFromHla(time));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setTimeAdvanceRequestAvailableRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasTimeAdvanceRequestAvailableResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.TIMEADVANCEREQUESTAVAILABLERESPONSE));
         }
         return null;
      });
   }

   public void nextMessageRequest(
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
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
      try {
         CompletableFuture<Void> future =
            asyncNextMessageRequest(
               time
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncNextMessageRequest(
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
   {
      hla.rti1516_2025.fedpro.NextMessageRequestRequest request;
      hla.rti1516_2025.fedpro.NextMessageRequestRequest. Builder builder = hla.rti1516_2025.fedpro.NextMessageRequestRequest. newBuilder();

      try {
         builder.setTime(_clientConverter.convertFromHla(time));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNextMessageRequestRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasNextMessageRequestResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.NEXTMESSAGEREQUESTRESPONSE));
         }
         return null;
      });
   }

   public void nextMessageRequestAvailable(
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
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
      try {
         CompletableFuture<Void> future =
            asyncNextMessageRequestAvailable(
               time
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncNextMessageRequestAvailable(
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
   {
      hla.rti1516_2025.fedpro.NextMessageRequestAvailableRequest request;
      hla.rti1516_2025.fedpro.NextMessageRequestAvailableRequest. Builder builder = hla.rti1516_2025.fedpro.NextMessageRequestAvailableRequest. newBuilder();

      try {
         builder.setTime(_clientConverter.convertFromHla(time));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNextMessageRequestAvailableRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasNextMessageRequestAvailableResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.NEXTMESSAGEREQUESTAVAILABLERESPONSE));
         }
         return null;
      });
   }

   public void flushQueueRequest(
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
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
      try {
         CompletableFuture<Void> future =
            asyncFlushQueueRequest(
               time
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncFlushQueueRequest(
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
   {
      hla.rti1516_2025.fedpro.FlushQueueRequestRequest request;
      hla.rti1516_2025.fedpro.FlushQueueRequestRequest. Builder builder = hla.rti1516_2025.fedpro.FlushQueueRequestRequest. newBuilder();

      try {
         builder.setTime(_clientConverter.convertFromHla(time));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setFlushQueueRequestRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasFlushQueueRequestResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.FLUSHQUEUEREQUESTRESPONSE));
         }
         return null;
      });
   }

   public void enableAsynchronousDelivery(

   )
   throws
      AsynchronousDeliveryAlreadyEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncEnableAsynchronousDelivery(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncEnableAsynchronousDelivery(

   )
   {
      hla.rti1516_2025.fedpro.EnableAsynchronousDeliveryRequest request;
      request = hla.rti1516_2025.fedpro.EnableAsynchronousDeliveryRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setEnableAsynchronousDeliveryRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasEnableAsynchronousDeliveryResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ENABLEASYNCHRONOUSDELIVERYRESPONSE));
         }
         return null;
      });
   }

   public void disableAsynchronousDelivery(

   )
   throws
      AsynchronousDeliveryAlreadyDisabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncDisableAsynchronousDelivery(

            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncDisableAsynchronousDelivery(

   )
   {
      hla.rti1516_2025.fedpro.DisableAsynchronousDeliveryRequest request;
      request = hla.rti1516_2025.fedpro.DisableAsynchronousDeliveryRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableAsynchronousDeliveryRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasDisableAsynchronousDeliveryResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.DISABLEASYNCHRONOUSDELIVERYRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_2025.TimeQueryReturn queryGALT(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<TimeQueryReturn> future =
            asyncQueryGALT(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<TimeQueryReturn> asyncQueryGALT(

   )
   {
      hla.rti1516_2025.fedpro.QueryGALTRequest request;
      request = hla.rti1516_2025.fedpro.QueryGALTRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryGALTRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasQueryGALTResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.QUERYGALTRESPONSE);
            }
            hla.rti1516_2025.fedpro.QueryGALTResponse response = callResponse.getQueryGALTResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.time.LogicalTime<?, ?> queryLogicalTime(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<LogicalTime> future =
            asyncQueryLogicalTime(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<LogicalTime> asyncQueryLogicalTime(

   )
   {
      hla.rti1516_2025.fedpro.QueryLogicalTimeRequest request;
      request = hla.rti1516_2025.fedpro.QueryLogicalTimeRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLogicalTimeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasQueryLogicalTimeResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.QUERYLOGICALTIMERESPONSE);
            }
            hla.rti1516_2025.fedpro.QueryLogicalTimeResponse response = callResponse.getQueryLogicalTimeResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.TimeQueryReturn queryLITS(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<TimeQueryReturn> future =
            asyncQueryLITS(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<TimeQueryReturn> asyncQueryLITS(

   )
   {
      hla.rti1516_2025.fedpro.QueryLITSRequest request;
      request = hla.rti1516_2025.fedpro.QueryLITSRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLITSRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasQueryLITSResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.QUERYLITSRESPONSE);
            }
            hla.rti1516_2025.fedpro.QueryLITSResponse response = callResponse.getQueryLITSResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void modifyLookahead(
      hla.rti1516_2025.time.LogicalTimeInterval<?> lookahead
   )
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
      try {
         CompletableFuture<Void> future =
            asyncModifyLookahead(
               lookahead
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncModifyLookahead(
      hla.rti1516_2025.time.LogicalTimeInterval<?> lookahead
   )
   {
      hla.rti1516_2025.fedpro.ModifyLookaheadRequest request;
      hla.rti1516_2025.fedpro.ModifyLookaheadRequest. Builder builder = hla.rti1516_2025.fedpro.ModifyLookaheadRequest. newBuilder();

      try {
         builder.setLookahead(_clientConverter.convertFromHla(lookahead));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setModifyLookaheadRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasModifyLookaheadResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.MODIFYLOOKAHEADRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_2025.time.LogicalTimeInterval<?> queryLookahead(

   )
   throws
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<LogicalTimeInterval> future =
            asyncQueryLookahead(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<LogicalTimeInterval> asyncQueryLookahead(

   )
   {
      hla.rti1516_2025.fedpro.QueryLookaheadRequest request;
      request = hla.rti1516_2025.fedpro.QueryLookaheadRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLookaheadRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasQueryLookaheadResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.QUERYLOOKAHEADRESPONSE);
            }
            hla.rti1516_2025.fedpro.QueryLookaheadResponse response = callResponse.getQueryLookaheadResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void retract(
      hla.rti1516_2025.MessageRetractionHandle retraction
   )
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
      try {
         CompletableFuture<Void> future =
            asyncRetract(
               retraction
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncRetract(
      hla.rti1516_2025.MessageRetractionHandle retraction
   )
   {
      hla.rti1516_2025.fedpro.RetractRequest request;
      hla.rti1516_2025.fedpro.RetractRequest. Builder builder = hla.rti1516_2025.fedpro.RetractRequest. newBuilder();

      try {
         builder.setRetraction(_clientConverter.convertFromHla(retraction));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRetractRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRetractResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.RETRACTRESPONSE));
         }
         return null;
      });
   }

   public void changeAttributeOrderType(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes,
      hla.rti1516_2025.OrderType orderType
   )
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
      try {
         CompletableFuture<Void> future =
            asyncChangeAttributeOrderType(
               objectInstance,
               attributes,
               orderType
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncChangeAttributeOrderType(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandleSet attributes,
      hla.rti1516_2025.OrderType orderType
   )
   {
      hla.rti1516_2025.fedpro.ChangeAttributeOrderTypeRequest request;
      hla.rti1516_2025.fedpro.ChangeAttributeOrderTypeRequest. Builder builder = hla.rti1516_2025.fedpro.ChangeAttributeOrderTypeRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
         builder.setOrderType(_clientConverter.convertFromHla(orderType));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setChangeAttributeOrderTypeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasChangeAttributeOrderTypeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.CHANGEATTRIBUTEORDERTYPERESPONSE));
         }
         return null;
      });
   }

   public void changeDefaultAttributeOrderType(
      hla.rti1516_2025.ObjectClassHandle theObjectClass,
      hla.rti1516_2025.AttributeHandleSet attributes,
      hla.rti1516_2025.OrderType orderType
   )
   throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncChangeDefaultAttributeOrderType(
               theObjectClass,
               attributes,
               orderType
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncChangeDefaultAttributeOrderType(
      hla.rti1516_2025.ObjectClassHandle theObjectClass,
      hla.rti1516_2025.AttributeHandleSet attributes,
      hla.rti1516_2025.OrderType orderType
   )
   {
      hla.rti1516_2025.fedpro.ChangeDefaultAttributeOrderTypeRequest request;
      hla.rti1516_2025.fedpro.ChangeDefaultAttributeOrderTypeRequest. Builder builder = hla.rti1516_2025.fedpro.ChangeDefaultAttributeOrderTypeRequest. newBuilder();

      try {
         builder.setTheObjectClass(_clientConverter.convertFromHla(theObjectClass));
         builder.setAttributes(_clientConverter.convertFromHla(attributes));
         builder.setOrderType(_clientConverter.convertFromHla(orderType));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setChangeDefaultAttributeOrderTypeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasChangeDefaultAttributeOrderTypeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.CHANGEDEFAULTATTRIBUTEORDERTYPERESPONSE));
         }
         return null;
      });
   }

   public void changeInteractionOrderType(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.OrderType orderType
   )
   throws
      InteractionClassNotPublished,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncChangeInteractionOrderType(
               interactionClass,
               orderType
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncChangeInteractionOrderType(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.OrderType orderType
   )
   {
      hla.rti1516_2025.fedpro.ChangeInteractionOrderTypeRequest request;
      hla.rti1516_2025.fedpro.ChangeInteractionOrderTypeRequest. Builder builder = hla.rti1516_2025.fedpro.ChangeInteractionOrderTypeRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
         builder.setOrderType(_clientConverter.convertFromHla(orderType));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setChangeInteractionOrderTypeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasChangeInteractionOrderTypeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.CHANGEINTERACTIONORDERTYPERESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_2025.RegionHandle createRegion(
      hla.rti1516_2025.DimensionHandleSet dimensions
   )
   throws
      InvalidDimensionHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<RegionHandle> future =
            asyncCreateRegion(
               dimensions
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<RegionHandle> asyncCreateRegion(
      hla.rti1516_2025.DimensionHandleSet dimensions
   )
   {
      hla.rti1516_2025.fedpro.CreateRegionRequest request;
      hla.rti1516_2025.fedpro.CreateRegionRequest. Builder builder = hla.rti1516_2025.fedpro.CreateRegionRequest. newBuilder();

      try {
         builder.setDimensions(_clientConverter.convertFromHla(dimensions));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCreateRegionRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasCreateRegionResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.CREATEREGIONRESPONSE);
            }
            hla.rti1516_2025.fedpro.CreateRegionResponse response = callResponse.getCreateRegionResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void commitRegionModifications(
      hla.rti1516_2025.RegionHandleSet regions
   )
   throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncCommitRegionModifications(
               regions
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncCommitRegionModifications(
      hla.rti1516_2025.RegionHandleSet regions
   )
   {
      hla.rti1516_2025.fedpro.CommitRegionModificationsRequest request;
      hla.rti1516_2025.fedpro.CommitRegionModificationsRequest. Builder builder = hla.rti1516_2025.fedpro.CommitRegionModificationsRequest. newBuilder();

      try {
         builder.setRegions(_clientConverter.convertFromHla(regions));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCommitRegionModificationsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasCommitRegionModificationsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.COMMITREGIONMODIFICATIONSRESPONSE));
         }
         return null;
      });
   }

   public void deleteRegion(
      hla.rti1516_2025.RegionHandle region
   )
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
      try {
         CompletableFuture<Void> future =
            asyncDeleteRegion(
               region
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncDeleteRegion(
      hla.rti1516_2025.RegionHandle region
   )
   {
      hla.rti1516_2025.fedpro.DeleteRegionRequest request;
      hla.rti1516_2025.fedpro.DeleteRegionRequest. Builder builder = hla.rti1516_2025.fedpro.DeleteRegionRequest. newBuilder();

      try {
         builder.setRegion(_clientConverter.convertFromHla(region));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setDeleteRegionRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasDeleteRegionResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.DELETEREGIONRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_2025.ObjectInstanceHandle registerObjectInstanceWithRegions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions
   )
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
      try {
         CompletableFuture<ObjectInstanceHandle> future =
            asyncRegisterObjectInstanceWithRegions(
               objectClass,
               attributesAndRegions
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<ObjectInstanceHandle> asyncRegisterObjectInstanceWithRegions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions
   )
   {
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithRegionsRequest request;
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithRegionsRequest. Builder builder = hla.rti1516_2025.fedpro.RegisterObjectInstanceWithRegionsRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterObjectInstanceWithRegionsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasRegisterObjectInstanceWithRegionsResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.REGISTEROBJECTINSTANCEWITHREGIONSRESPONSE);
            }
            hla.rti1516_2025.fedpro.RegisterObjectInstanceWithRegionsResponse response = callResponse.getRegisterObjectInstanceWithRegionsResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.ObjectInstanceHandle registerObjectInstanceWithRegions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions,
      java.lang.String objectInstanceName
   )
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
      try {
         CompletableFuture<ObjectInstanceHandle> future =
            asyncRegisterObjectInstanceWithRegions(
               objectClass,
               attributesAndRegions,
               objectInstanceName
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<ObjectInstanceHandle> asyncRegisterObjectInstanceWithRegions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions,
      java.lang.String objectInstanceName
   )
   {
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameAndRegionsRequest request;
      hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameAndRegionsRequest. Builder builder = hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameAndRegionsRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
         builder.setObjectInstanceName(_clientConverter.convertFromHla(objectInstanceName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterObjectInstanceWithNameAndRegionsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasRegisterObjectInstanceWithNameAndRegionsResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.REGISTEROBJECTINSTANCEWITHNAMEANDREGIONSRESPONSE);
            }
            hla.rti1516_2025.fedpro.RegisterObjectInstanceWithNameAndRegionsResponse response = callResponse.getRegisterObjectInstanceWithNameAndRegionsResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void associateRegionsForUpdates(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions
   )
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
      try {
         CompletableFuture<Void> future =
            asyncAssociateRegionsForUpdates(
               objectInstance,
               attributesAndRegions
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncAssociateRegionsForUpdates(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions
   )
   {
      hla.rti1516_2025.fedpro.AssociateRegionsForUpdatesRequest request;
      hla.rti1516_2025.fedpro.AssociateRegionsForUpdatesRequest. Builder builder = hla.rti1516_2025.fedpro.AssociateRegionsForUpdatesRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAssociateRegionsForUpdatesRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasAssociateRegionsForUpdatesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ASSOCIATEREGIONSFORUPDATESRESPONSE));
         }
         return null;
      });
   }

   public void unassociateRegionsForUpdates(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions
   )
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
      try {
         CompletableFuture<Void> future =
            asyncUnassociateRegionsForUpdates(
               objectInstance,
               attributesAndRegions
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUnassociateRegionsForUpdates(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions
   )
   {
      hla.rti1516_2025.fedpro.UnassociateRegionsForUpdatesRequest request;
      hla.rti1516_2025.fedpro.UnassociateRegionsForUpdatesRequest. Builder builder = hla.rti1516_2025.fedpro.UnassociateRegionsForUpdatesRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnassociateRegionsForUpdatesRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnassociateRegionsForUpdatesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNASSOCIATEREGIONSFORUPDATESRESPONSE));
         }
         return null;
      });
   }

   public void subscribeObjectClassAttributesWithRegions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions,
      boolean active
   )
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
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassAttributesWithRegions(
               objectClass,
               attributesAndRegions,
               active
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSubscribeObjectClassAttributesWithRegions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions,
      boolean active
   )
   {
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRegionsRequest request;
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRegionsRequest. Builder builder = hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRegionsRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
         builder.setActive(_clientConverter.convertFromHla(active));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesWithRegionsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassAttributesWithRegionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSATTRIBUTESWITHREGIONSRESPONSE));
         }
         return null;
      });
   }

   public void subscribeObjectClassAttributesWithRegions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions,
      boolean active,
      java.lang.String updateRateDesignator
   )
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
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassAttributesWithRegions(
               objectClass,
               attributesAndRegions,
               active,
               updateRateDesignator
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSubscribeObjectClassAttributesWithRegions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions,
      boolean active,
      java.lang.String updateRateDesignator
   )
   {
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRegionsAndRateRequest request;
      hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRegionsAndRateRequest. Builder builder = hla.rti1516_2025.fedpro.SubscribeObjectClassAttributesWithRegionsAndRateRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
         builder.setActive(_clientConverter.convertFromHla(active));
         builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesWithRegionsAndRateRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassAttributesWithRegionsAndRateResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSATTRIBUTESWITHREGIONSANDRATERESPONSE));
         }
         return null;
      });
   }

   public void unsubscribeObjectClassAttributesWithRegions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions
   )
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
      try {
         CompletableFuture<Void> future =
            asyncUnsubscribeObjectClassAttributesWithRegions(
               objectClass,
               attributesAndRegions
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUnsubscribeObjectClassAttributesWithRegions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions
   )
   {
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassAttributesWithRegionsRequest request;
      hla.rti1516_2025.fedpro.UnsubscribeObjectClassAttributesWithRegionsRequest. Builder builder = hla.rti1516_2025.fedpro.UnsubscribeObjectClassAttributesWithRegionsRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassAttributesWithRegionsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnsubscribeObjectClassAttributesWithRegionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNSUBSCRIBEOBJECTCLASSATTRIBUTESWITHREGIONSRESPONSE));
         }
         return null;
      });
   }

   public void subscribeInteractionClassWithRegions(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      boolean active,
      hla.rti1516_2025.RegionHandleSet regions
   )
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
      try {
         CompletableFuture<Void> future =
            asyncSubscribeInteractionClassWithRegions(
               interactionClass,
               active,
               regions
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSubscribeInteractionClassWithRegions(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      boolean active,
      hla.rti1516_2025.RegionHandleSet regions
   )
   {
      hla.rti1516_2025.fedpro.SubscribeInteractionClassWithRegionsRequest request;
      hla.rti1516_2025.fedpro.SubscribeInteractionClassWithRegionsRequest. Builder builder = hla.rti1516_2025.fedpro.SubscribeInteractionClassWithRegionsRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
         builder.setActive(_clientConverter.convertFromHla(active));
         builder.setRegions(_clientConverter.convertFromHla(regions));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeInteractionClassWithRegionsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeInteractionClassWithRegionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEINTERACTIONCLASSWITHREGIONSRESPONSE));
         }
         return null;
      });
   }

   public void unsubscribeInteractionClassWithRegions(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.RegionHandleSet regions
   )
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
      try {
         CompletableFuture<Void> future =
            asyncUnsubscribeInteractionClassWithRegions(
               interactionClass,
               regions
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncUnsubscribeInteractionClassWithRegions(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.RegionHandleSet regions
   )
   {
      hla.rti1516_2025.fedpro.UnsubscribeInteractionClassWithRegionsRequest request;
      hla.rti1516_2025.fedpro.UnsubscribeInteractionClassWithRegionsRequest. Builder builder = hla.rti1516_2025.fedpro.UnsubscribeInteractionClassWithRegionsRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
         builder.setRegions(_clientConverter.convertFromHla(regions));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeInteractionClassWithRegionsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnsubscribeInteractionClassWithRegionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNSUBSCRIBEINTERACTIONCLASSWITHREGIONSRESPONSE));
         }
         return null;
      });
   }

   public void sendInteractionWithRegions(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.ParameterHandleValueMap parameterValues,
      hla.rti1516_2025.RegionHandleSet regions,
      byte[] userSuppliedTag
   )
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
      try {
         CompletableFuture<Void> future =
            asyncSendInteractionWithRegions(
               interactionClass,
               parameterValues,
               regions,
               userSuppliedTag
            );
         countSyncSentInteractionForStats();
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSendInteractionWithRegions(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.ParameterHandleValueMap parameterValues,
      hla.rti1516_2025.RegionHandleSet regions,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.SendInteractionWithRegionsRequest request;
      hla.rti1516_2025.fedpro.SendInteractionWithRegionsRequest. Builder builder = hla.rti1516_2025.fedpro.SendInteractionWithRegionsRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
         builder.setParameterValues(_clientConverter.convertFromHla(parameterValues));
         builder.setRegions(_clientConverter.convertFromHla(regions));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionWithRegionsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSendInteractionWithRegionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SENDINTERACTIONWITHREGIONSRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_2025.MessageRetractionReturn sendInteractionWithRegions(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.ParameterHandleValueMap parameterValues,
      hla.rti1516_2025.RegionHandleSet regions,
      byte[] userSuppliedTag,
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
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
      try {
         CompletableFuture<MessageRetractionReturn> future =
            asyncSendInteractionWithRegions(
               interactionClass,
               parameterValues,
               regions,
               userSuppliedTag,
               time
            );
         countSyncSentInteractionForStats();
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<MessageRetractionReturn> asyncSendInteractionWithRegions(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.ParameterHandleValueMap parameterValues,
      hla.rti1516_2025.RegionHandleSet regions,
      byte[] userSuppliedTag,
      hla.rti1516_2025.time.LogicalTime<?, ?> time
   )
   {
      hla.rti1516_2025.fedpro.SendInteractionWithRegionsAndTimeRequest request;
      hla.rti1516_2025.fedpro.SendInteractionWithRegionsAndTimeRequest. Builder builder = hla.rti1516_2025.fedpro.SendInteractionWithRegionsAndTimeRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
         builder.setParameterValues(_clientConverter.convertFromHla(parameterValues));
         builder.setRegions(_clientConverter.convertFromHla(regions));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
         builder.setTime(_clientConverter.convertFromHla(time));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionWithRegionsAndTimeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasSendInteractionWithRegionsAndTimeResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.SENDINTERACTIONWITHREGIONSANDTIMERESPONSE);
            }
            hla.rti1516_2025.fedpro.SendInteractionWithRegionsAndTimeResponse response = callResponse.getSendInteractionWithRegionsAndTimeResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void requestAttributeValueUpdateWithRegions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions,
      byte[] userSuppliedTag
   )
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
      try {
         CompletableFuture<Void> future =
            asyncRequestAttributeValueUpdateWithRegions(
               objectClass,
               attributesAndRegions,
               userSuppliedTag
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncRequestAttributeValueUpdateWithRegions(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeSetRegionSetPairList attributesAndRegions,
      byte[] userSuppliedTag
   )
   {
      hla.rti1516_2025.fedpro.RequestAttributeValueUpdateWithRegionsRequest request;
      hla.rti1516_2025.fedpro.RequestAttributeValueUpdateWithRegionsRequest. Builder builder = hla.rti1516_2025.fedpro.RequestAttributeValueUpdateWithRegionsRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestAttributeValueUpdateWithRegionsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestAttributeValueUpdateWithRegionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTATTRIBUTEVALUEUPDATEWITHREGIONSRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_2025.FederateHandle getFederateHandle(
      java.lang.String federateName
   )
   throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<FederateHandle> future =
            asyncGetFederateHandle(
               federateName
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<FederateHandle> asyncGetFederateHandle(
      java.lang.String federateName
   )
   {
      hla.rti1516_2025.fedpro.GetFederateHandleRequest request;
      hla.rti1516_2025.fedpro.GetFederateHandleRequest. Builder builder = hla.rti1516_2025.fedpro.GetFederateHandleRequest. newBuilder();

      try {
         builder.setFederateName(_clientConverter.convertFromHla(federateName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetFederateHandleRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetFederateHandleResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETFEDERATEHANDLERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetFederateHandleResponse response = callResponse.getGetFederateHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String getFederateName(
      hla.rti1516_2025.FederateHandle federate
   )
   throws
      InvalidFederateHandle,
      FederateHandleNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<String> future =
            asyncGetFederateName(
               federate
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<String> asyncGetFederateName(
      hla.rti1516_2025.FederateHandle federate
   )
   {
      hla.rti1516_2025.fedpro.GetFederateNameRequest request;
      hla.rti1516_2025.fedpro.GetFederateNameRequest. Builder builder = hla.rti1516_2025.fedpro.GetFederateNameRequest. newBuilder();

      try {
         builder.setFederate(_clientConverter.convertFromHla(federate));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetFederateNameRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetFederateNameResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETFEDERATENAMERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetFederateNameResponse response = callResponse.getGetFederateNameResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.ObjectClassHandle getObjectClassHandle(
      java.lang.String objectClassName
   )
   throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<ObjectClassHandle> future =
            asyncGetObjectClassHandle(
               objectClassName
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<ObjectClassHandle> asyncGetObjectClassHandle(
      java.lang.String objectClassName
   )
   {
      hla.rti1516_2025.fedpro.GetObjectClassHandleRequest request;
      hla.rti1516_2025.fedpro.GetObjectClassHandleRequest. Builder builder = hla.rti1516_2025.fedpro.GetObjectClassHandleRequest. newBuilder();

      try {
         builder.setObjectClassName(_clientConverter.convertFromHla(objectClassName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectClassHandleRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetObjectClassHandleResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETOBJECTCLASSHANDLERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetObjectClassHandleResponse response = callResponse.getGetObjectClassHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String getObjectClassName(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   throws
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<String> future =
            asyncGetObjectClassName(
               objectClass
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<String> asyncGetObjectClassName(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   {
      hla.rti1516_2025.fedpro.GetObjectClassNameRequest request;
      hla.rti1516_2025.fedpro.GetObjectClassNameRequest. Builder builder = hla.rti1516_2025.fedpro.GetObjectClassNameRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectClassNameRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetObjectClassNameResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETOBJECTCLASSNAMERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetObjectClassNameResponse response = callResponse.getGetObjectClassNameResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.ObjectClassHandle getKnownObjectClassHandle(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance
   )
   throws
      ObjectInstanceNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<ObjectClassHandle> future =
            asyncGetKnownObjectClassHandle(
               objectInstance
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<ObjectClassHandle> asyncGetKnownObjectClassHandle(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance
   )
   {
      hla.rti1516_2025.fedpro.GetKnownObjectClassHandleRequest request;
      hla.rti1516_2025.fedpro.GetKnownObjectClassHandleRequest. Builder builder = hla.rti1516_2025.fedpro.GetKnownObjectClassHandleRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetKnownObjectClassHandleRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetKnownObjectClassHandleResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETKNOWNOBJECTCLASSHANDLERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetKnownObjectClassHandleResponse response = callResponse.getGetKnownObjectClassHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.ObjectInstanceHandle getObjectInstanceHandle(
      java.lang.String objectInstanceName
   )
   throws
      ObjectInstanceNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<ObjectInstanceHandle> future =
            asyncGetObjectInstanceHandle(
               objectInstanceName
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<ObjectInstanceHandle> asyncGetObjectInstanceHandle(
      java.lang.String objectInstanceName
   )
   {
      hla.rti1516_2025.fedpro.GetObjectInstanceHandleRequest request;
      hla.rti1516_2025.fedpro.GetObjectInstanceHandleRequest. Builder builder = hla.rti1516_2025.fedpro.GetObjectInstanceHandleRequest. newBuilder();

      try {
         builder.setObjectInstanceName(_clientConverter.convertFromHla(objectInstanceName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectInstanceHandleRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetObjectInstanceHandleResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETOBJECTINSTANCEHANDLERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetObjectInstanceHandleResponse response = callResponse.getGetObjectInstanceHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String getObjectInstanceName(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance
   )
   throws
      ObjectInstanceNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<String> future =
            asyncGetObjectInstanceName(
               objectInstance
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<String> asyncGetObjectInstanceName(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance
   )
   {
      hla.rti1516_2025.fedpro.GetObjectInstanceNameRequest request;
      hla.rti1516_2025.fedpro.GetObjectInstanceNameRequest. Builder builder = hla.rti1516_2025.fedpro.GetObjectInstanceNameRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectInstanceNameRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetObjectInstanceNameResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETOBJECTINSTANCENAMERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetObjectInstanceNameResponse response = callResponse.getGetObjectInstanceNameResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.AttributeHandle getAttributeHandle(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      java.lang.String attributeName
   )
   throws
      NameNotFound,
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<AttributeHandle> future =
            asyncGetAttributeHandle(
               objectClass,
               attributeName
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<AttributeHandle> asyncGetAttributeHandle(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      java.lang.String attributeName
   )
   {
      hla.rti1516_2025.fedpro.GetAttributeHandleRequest request;
      hla.rti1516_2025.fedpro.GetAttributeHandleRequest. Builder builder = hla.rti1516_2025.fedpro.GetAttributeHandleRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttributeName(_clientConverter.convertFromHla(attributeName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetAttributeHandleRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetAttributeHandleResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETATTRIBUTEHANDLERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetAttributeHandleResponse response = callResponse.getGetAttributeHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String getAttributeName(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandle attribute
   )
   throws
      AttributeNotDefined,
      InvalidAttributeHandle,
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<String> future =
            asyncGetAttributeName(
               objectClass,
               attribute
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<String> asyncGetAttributeName(
      hla.rti1516_2025.ObjectClassHandle objectClass,
      hla.rti1516_2025.AttributeHandle attribute
   )
   {
      hla.rti1516_2025.fedpro.GetAttributeNameRequest request;
      hla.rti1516_2025.fedpro.GetAttributeNameRequest. Builder builder = hla.rti1516_2025.fedpro.GetAttributeNameRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
         builder.setAttribute(_clientConverter.convertFromHla(attribute));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetAttributeNameRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetAttributeNameResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETATTRIBUTENAMERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetAttributeNameResponse response = callResponse.getGetAttributeNameResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public double getUpdateRateValue(
      java.lang.String updateRateDesignator
   )
   throws
      InvalidUpdateRateDesignator,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Double> future =
            asyncGetUpdateRateValue(
               updateRateDesignator
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Double> asyncGetUpdateRateValue(
      java.lang.String updateRateDesignator
   )
   {
      hla.rti1516_2025.fedpro.GetUpdateRateValueRequest request;
      hla.rti1516_2025.fedpro.GetUpdateRateValueRequest. Builder builder = hla.rti1516_2025.fedpro.GetUpdateRateValueRequest. newBuilder();

      try {
         builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetUpdateRateValueRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetUpdateRateValueResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETUPDATERATEVALUERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetUpdateRateValueResponse response = callResponse.getGetUpdateRateValueResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public double getUpdateRateValueForAttribute(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandle attribute
   )
   throws
      ObjectInstanceNotKnown,
      AttributeNotDefined,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Double> future =
            asyncGetUpdateRateValueForAttribute(
               objectInstance,
               attribute
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Double> asyncGetUpdateRateValueForAttribute(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance,
      hla.rti1516_2025.AttributeHandle attribute
   )
   {
      hla.rti1516_2025.fedpro.GetUpdateRateValueForAttributeRequest request;
      hla.rti1516_2025.fedpro.GetUpdateRateValueForAttributeRequest. Builder builder = hla.rti1516_2025.fedpro.GetUpdateRateValueForAttributeRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
         builder.setAttribute(_clientConverter.convertFromHla(attribute));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetUpdateRateValueForAttributeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetUpdateRateValueForAttributeResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETUPDATERATEVALUEFORATTRIBUTERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetUpdateRateValueForAttributeResponse response = callResponse.getGetUpdateRateValueForAttributeResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.InteractionClassHandle getInteractionClassHandle(
      java.lang.String interactionClassName
   )
   throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<InteractionClassHandle> future =
            asyncGetInteractionClassHandle(
               interactionClassName
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<InteractionClassHandle> asyncGetInteractionClassHandle(
      java.lang.String interactionClassName
   )
   {
      hla.rti1516_2025.fedpro.GetInteractionClassHandleRequest request;
      hla.rti1516_2025.fedpro.GetInteractionClassHandleRequest. Builder builder = hla.rti1516_2025.fedpro.GetInteractionClassHandleRequest. newBuilder();

      try {
         builder.setInteractionClassName(_clientConverter.convertFromHla(interactionClassName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetInteractionClassHandleRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetInteractionClassHandleResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETINTERACTIONCLASSHANDLERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetInteractionClassHandleResponse response = callResponse.getGetInteractionClassHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String getInteractionClassName(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   throws
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<String> future =
            asyncGetInteractionClassName(
               interactionClass
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<String> asyncGetInteractionClassName(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   {
      hla.rti1516_2025.fedpro.GetInteractionClassNameRequest request;
      hla.rti1516_2025.fedpro.GetInteractionClassNameRequest. Builder builder = hla.rti1516_2025.fedpro.GetInteractionClassNameRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetInteractionClassNameRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetInteractionClassNameResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETINTERACTIONCLASSNAMERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetInteractionClassNameResponse response = callResponse.getGetInteractionClassNameResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.ParameterHandle getParameterHandle(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      java.lang.String parameterName
   )
   throws
      NameNotFound,
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<ParameterHandle> future =
            asyncGetParameterHandle(
               interactionClass,
               parameterName
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<ParameterHandle> asyncGetParameterHandle(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      java.lang.String parameterName
   )
   {
      hla.rti1516_2025.fedpro.GetParameterHandleRequest request;
      hla.rti1516_2025.fedpro.GetParameterHandleRequest. Builder builder = hla.rti1516_2025.fedpro.GetParameterHandleRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
         builder.setParameterName(_clientConverter.convertFromHla(parameterName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetParameterHandleRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetParameterHandleResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETPARAMETERHANDLERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetParameterHandleResponse response = callResponse.getGetParameterHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String getParameterName(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.ParameterHandle parameter
   )
   throws
      InteractionParameterNotDefined,
      InvalidParameterHandle,
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<String> future =
            asyncGetParameterName(
               interactionClass,
               parameter
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<String> asyncGetParameterName(
      hla.rti1516_2025.InteractionClassHandle interactionClass,
      hla.rti1516_2025.ParameterHandle parameter
   )
   {
      hla.rti1516_2025.fedpro.GetParameterNameRequest request;
      hla.rti1516_2025.fedpro.GetParameterNameRequest. Builder builder = hla.rti1516_2025.fedpro.GetParameterNameRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
         builder.setParameter(_clientConverter.convertFromHla(parameter));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetParameterNameRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetParameterNameResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETPARAMETERNAMERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetParameterNameResponse response = callResponse.getGetParameterNameResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.OrderType getOrderType(
      java.lang.String orderTypeName
   )
   throws
      InvalidOrderName,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<OrderType> future =
            asyncGetOrderType(
               orderTypeName
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<OrderType> asyncGetOrderType(
      java.lang.String orderTypeName
   )
   {
      hla.rti1516_2025.fedpro.GetOrderTypeRequest request;
      hla.rti1516_2025.fedpro.GetOrderTypeRequest. Builder builder = hla.rti1516_2025.fedpro.GetOrderTypeRequest. newBuilder();

      try {
         builder.setOrderTypeName(_clientConverter.convertFromHla(orderTypeName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetOrderTypeRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetOrderTypeResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETORDERTYPERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetOrderTypeResponse response = callResponse.getGetOrderTypeResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String getOrderName(
      hla.rti1516_2025.OrderType orderType
   )
   throws
      InvalidOrderType,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<String> future =
            asyncGetOrderName(
               orderType
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<String> asyncGetOrderName(
      hla.rti1516_2025.OrderType orderType
   )
   {
      hla.rti1516_2025.fedpro.GetOrderNameRequest request;
      hla.rti1516_2025.fedpro.GetOrderNameRequest. Builder builder = hla.rti1516_2025.fedpro.GetOrderNameRequest. newBuilder();

      try {
         builder.setOrderType(_clientConverter.convertFromHla(orderType));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetOrderNameRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetOrderNameResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETORDERNAMERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetOrderNameResponse response = callResponse.getGetOrderNameResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.TransportationTypeHandle getTransportationTypeHandle(
      java.lang.String transportationTypeName
   )
   throws
      InvalidTransportationName,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<TransportationTypeHandle> future =
            asyncGetTransportationTypeHandle(
               transportationTypeName
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<TransportationTypeHandle> asyncGetTransportationTypeHandle(
      java.lang.String transportationTypeName
   )
   {
      hla.rti1516_2025.fedpro.GetTransportationTypeHandleRequest request;
      hla.rti1516_2025.fedpro.GetTransportationTypeHandleRequest. Builder builder = hla.rti1516_2025.fedpro.GetTransportationTypeHandleRequest. newBuilder();

      try {
         builder.setTransportationTypeName(_clientConverter.convertFromHla(transportationTypeName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetTransportationTypeHandleRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetTransportationTypeHandleResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETTRANSPORTATIONTYPEHANDLERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetTransportationTypeHandleResponse response = callResponse.getGetTransportationTypeHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String getTransportationTypeName(
      hla.rti1516_2025.TransportationTypeHandle transportationType
   )
   throws
      InvalidTransportationTypeHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<String> future =
            asyncGetTransportationTypeName(
               transportationType
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<String> asyncGetTransportationTypeName(
      hla.rti1516_2025.TransportationTypeHandle transportationType
   )
   {
      hla.rti1516_2025.fedpro.GetTransportationTypeNameRequest request;
      hla.rti1516_2025.fedpro.GetTransportationTypeNameRequest. Builder builder = hla.rti1516_2025.fedpro.GetTransportationTypeNameRequest. newBuilder();

      try {
         builder.setTransportationType(_clientConverter.convertFromHla(transportationType));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetTransportationTypeNameRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetTransportationTypeNameResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETTRANSPORTATIONTYPENAMERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetTransportationTypeNameResponse response = callResponse.getGetTransportationTypeNameResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.DimensionHandleSet getAvailableDimensionsForObjectClass(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   throws
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<DimensionHandleSet> future =
            asyncGetAvailableDimensionsForObjectClass(
               objectClass
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<DimensionHandleSet> asyncGetAvailableDimensionsForObjectClass(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   {
      hla.rti1516_2025.fedpro.GetAvailableDimensionsForObjectClassRequest request;
      hla.rti1516_2025.fedpro.GetAvailableDimensionsForObjectClassRequest. Builder builder = hla.rti1516_2025.fedpro.GetAvailableDimensionsForObjectClassRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetAvailableDimensionsForObjectClassRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetAvailableDimensionsForObjectClassResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETAVAILABLEDIMENSIONSFOROBJECTCLASSRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetAvailableDimensionsForObjectClassResponse response = callResponse.getGetAvailableDimensionsForObjectClassResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.DimensionHandleSet getAvailableDimensionsForInteractionClass(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   throws
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<DimensionHandleSet> future =
            asyncGetAvailableDimensionsForInteractionClass(
               interactionClass
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<DimensionHandleSet> asyncGetAvailableDimensionsForInteractionClass(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   {
      hla.rti1516_2025.fedpro.GetAvailableDimensionsForInteractionClassRequest request;
      hla.rti1516_2025.fedpro.GetAvailableDimensionsForInteractionClassRequest. Builder builder = hla.rti1516_2025.fedpro.GetAvailableDimensionsForInteractionClassRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetAvailableDimensionsForInteractionClassRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetAvailableDimensionsForInteractionClassResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETAVAILABLEDIMENSIONSFORINTERACTIONCLASSRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetAvailableDimensionsForInteractionClassResponse response = callResponse.getGetAvailableDimensionsForInteractionClassResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.DimensionHandle getDimensionHandle(
      java.lang.String dimensionName
   )
   throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<DimensionHandle> future =
            asyncGetDimensionHandle(
               dimensionName
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<DimensionHandle> asyncGetDimensionHandle(
      java.lang.String dimensionName
   )
   {
      hla.rti1516_2025.fedpro.GetDimensionHandleRequest request;
      hla.rti1516_2025.fedpro.GetDimensionHandleRequest. Builder builder = hla.rti1516_2025.fedpro.GetDimensionHandleRequest. newBuilder();

      try {
         builder.setDimensionName(_clientConverter.convertFromHla(dimensionName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionHandleRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetDimensionHandleResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETDIMENSIONHANDLERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetDimensionHandleResponse response = callResponse.getGetDimensionHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String getDimensionName(
      hla.rti1516_2025.DimensionHandle dimension
   )
   throws
      InvalidDimensionHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<String> future =
            asyncGetDimensionName(
               dimension
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<String> asyncGetDimensionName(
      hla.rti1516_2025.DimensionHandle dimension
   )
   {
      hla.rti1516_2025.fedpro.GetDimensionNameRequest request;
      hla.rti1516_2025.fedpro.GetDimensionNameRequest. Builder builder = hla.rti1516_2025.fedpro.GetDimensionNameRequest. newBuilder();

      try {
         builder.setDimension(_clientConverter.convertFromHla(dimension));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionNameRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetDimensionNameResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETDIMENSIONNAMERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetDimensionNameResponse response = callResponse.getGetDimensionNameResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public long getDimensionUpperBound(
      hla.rti1516_2025.DimensionHandle dimension
   )
   throws
      InvalidDimensionHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Long> future =
            asyncGetDimensionUpperBound(
               dimension
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Long> asyncGetDimensionUpperBound(
      hla.rti1516_2025.DimensionHandle dimension
   )
   {
      hla.rti1516_2025.fedpro.GetDimensionUpperBoundRequest request;
      hla.rti1516_2025.fedpro.GetDimensionUpperBoundRequest. Builder builder = hla.rti1516_2025.fedpro.GetDimensionUpperBoundRequest. newBuilder();

      try {
         builder.setDimension(_clientConverter.convertFromHla(dimension));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionUpperBoundRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetDimensionUpperBoundResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETDIMENSIONUPPERBOUNDRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetDimensionUpperBoundResponse response = callResponse.getGetDimensionUpperBoundResponse();
            return _clientConverter.convertToHla(Integer.toUnsignedLong(response.getResult()));
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.DimensionHandleSet getDimensionHandleSet(
      hla.rti1516_2025.RegionHandle region
   )
   throws
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<DimensionHandleSet> future =
            asyncGetDimensionHandleSet(
               region
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<DimensionHandleSet> asyncGetDimensionHandleSet(
      hla.rti1516_2025.RegionHandle region
   )
   {
      hla.rti1516_2025.fedpro.GetDimensionHandleSetRequest request;
      hla.rti1516_2025.fedpro.GetDimensionHandleSetRequest. Builder builder = hla.rti1516_2025.fedpro.GetDimensionHandleSetRequest. newBuilder();

      try {
         builder.setRegion(_clientConverter.convertFromHla(region));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetDimensionHandleSetRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetDimensionHandleSetResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETDIMENSIONHANDLESETRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetDimensionHandleSetResponse response = callResponse.getGetDimensionHandleSetResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_2025.RangeBounds getRangeBounds(
      hla.rti1516_2025.RegionHandle region,
      hla.rti1516_2025.DimensionHandle dimension
   )
   throws
      RegionDoesNotContainSpecifiedDimension,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<RangeBounds> future =
            asyncGetRangeBounds(
               region,
               dimension
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<RangeBounds> asyncGetRangeBounds(
      hla.rti1516_2025.RegionHandle region,
      hla.rti1516_2025.DimensionHandle dimension
   )
   {
      hla.rti1516_2025.fedpro.GetRangeBoundsRequest request;
      hla.rti1516_2025.fedpro.GetRangeBoundsRequest. Builder builder = hla.rti1516_2025.fedpro.GetRangeBoundsRequest. newBuilder();

      try {
         builder.setRegion(_clientConverter.convertFromHla(region));
         builder.setDimension(_clientConverter.convertFromHla(dimension));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetRangeBoundsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetRangeBoundsResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETRANGEBOUNDSRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetRangeBoundsResponse response = callResponse.getGetRangeBoundsResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void setRangeBounds(
      hla.rti1516_2025.RegionHandle region,
      hla.rti1516_2025.DimensionHandle dimension,
      hla.rti1516_2025.RangeBounds rangeBounds
   )
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
      try {
         CompletableFuture<Void> future =
            asyncSetRangeBounds(
               region,
               dimension,
               rangeBounds
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSetRangeBounds(
      hla.rti1516_2025.RegionHandle region,
      hla.rti1516_2025.DimensionHandle dimension,
      hla.rti1516_2025.RangeBounds rangeBounds
   )
   {
      hla.rti1516_2025.fedpro.SetRangeBoundsRequest request;
      hla.rti1516_2025.fedpro.SetRangeBoundsRequest. Builder builder = hla.rti1516_2025.fedpro.SetRangeBoundsRequest. newBuilder();

      try {
         builder.setRegion(_clientConverter.convertFromHla(region));
         builder.setDimension(_clientConverter.convertFromHla(dimension));
         builder.setRangeBounds(_clientConverter.convertFromHla(rangeBounds));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetRangeBoundsRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSetRangeBoundsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SETRANGEBOUNDSRESPONSE));
         }
         return null;
      });
   }

   public long normalizeServiceGroup(
      hla.rti1516_2025.ServiceGroup serviceGroup
   )
   throws
      InvalidServiceGroup,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Long> future =
            asyncNormalizeServiceGroup(
               serviceGroup
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Long> asyncNormalizeServiceGroup(
      hla.rti1516_2025.ServiceGroup serviceGroup
   )
   {
      hla.rti1516_2025.fedpro.NormalizeServiceGroupRequest request;
      hla.rti1516_2025.fedpro.NormalizeServiceGroupRequest. Builder builder = hla.rti1516_2025.fedpro.NormalizeServiceGroupRequest. newBuilder();

      try {
         builder.setServiceGroup(_clientConverter.convertFromHla(serviceGroup));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNormalizeServiceGroupRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasNormalizeServiceGroupResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.NORMALIZESERVICEGROUPRESPONSE);
            }
            hla.rti1516_2025.fedpro.NormalizeServiceGroupResponse response = callResponse.getNormalizeServiceGroupResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public long normalizeFederateHandle(
      hla.rti1516_2025.FederateHandle federate
   )
   throws
      InvalidFederateHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Long> future =
            asyncNormalizeFederateHandle(
               federate
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Long> asyncNormalizeFederateHandle(
      hla.rti1516_2025.FederateHandle federate
   )
   {
      hla.rti1516_2025.fedpro.NormalizeFederateHandleRequest request;
      hla.rti1516_2025.fedpro.NormalizeFederateHandleRequest. Builder builder = hla.rti1516_2025.fedpro.NormalizeFederateHandleRequest. newBuilder();

      try {
         builder.setFederate(_clientConverter.convertFromHla(federate));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNormalizeFederateHandleRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasNormalizeFederateHandleResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.NORMALIZEFEDERATEHANDLERESPONSE);
            }
            hla.rti1516_2025.fedpro.NormalizeFederateHandleResponse response = callResponse.getNormalizeFederateHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public long normalizeObjectClassHandle(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   throws
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Long> future =
            asyncNormalizeObjectClassHandle(
               objectClass
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Long> asyncNormalizeObjectClassHandle(
      hla.rti1516_2025.ObjectClassHandle objectClass
   )
   {
      hla.rti1516_2025.fedpro.NormalizeObjectClassHandleRequest request;
      hla.rti1516_2025.fedpro.NormalizeObjectClassHandleRequest. Builder builder = hla.rti1516_2025.fedpro.NormalizeObjectClassHandleRequest. newBuilder();

      try {
         builder.setObjectClass(_clientConverter.convertFromHla(objectClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNormalizeObjectClassHandleRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasNormalizeObjectClassHandleResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.NORMALIZEOBJECTCLASSHANDLERESPONSE);
            }
            hla.rti1516_2025.fedpro.NormalizeObjectClassHandleResponse response = callResponse.getNormalizeObjectClassHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public long normalizeInteractionClassHandle(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   throws
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Long> future =
            asyncNormalizeInteractionClassHandle(
               interactionClass
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Long> asyncNormalizeInteractionClassHandle(
      hla.rti1516_2025.InteractionClassHandle interactionClass
   )
   {
      hla.rti1516_2025.fedpro.NormalizeInteractionClassHandleRequest request;
      hla.rti1516_2025.fedpro.NormalizeInteractionClassHandleRequest. Builder builder = hla.rti1516_2025.fedpro.NormalizeInteractionClassHandleRequest. newBuilder();

      try {
         builder.setInteractionClass(_clientConverter.convertFromHla(interactionClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNormalizeInteractionClassHandleRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasNormalizeInteractionClassHandleResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.NORMALIZEINTERACTIONCLASSHANDLERESPONSE);
            }
            hla.rti1516_2025.fedpro.NormalizeInteractionClassHandleResponse response = callResponse.getNormalizeInteractionClassHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public long normalizeObjectInstanceHandle(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance
   )
   throws
      InvalidObjectInstanceHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Long> future =
            asyncNormalizeObjectInstanceHandle(
               objectInstance
            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Long> asyncNormalizeObjectInstanceHandle(
      hla.rti1516_2025.ObjectInstanceHandle objectInstance
   )
   {
      hla.rti1516_2025.fedpro.NormalizeObjectInstanceHandleRequest request;
      hla.rti1516_2025.fedpro.NormalizeObjectInstanceHandleRequest. Builder builder = hla.rti1516_2025.fedpro.NormalizeObjectInstanceHandleRequest. newBuilder();

      try {
         builder.setObjectInstance(_clientConverter.convertFromHla(objectInstance));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNormalizeObjectInstanceHandleRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasNormalizeObjectInstanceHandleResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.NORMALIZEOBJECTINSTANCEHANDLERESPONSE);
            }
            hla.rti1516_2025.fedpro.NormalizeObjectInstanceHandleResponse response = callResponse.getNormalizeObjectInstanceHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public boolean getObjectClassRelevanceAdvisorySwitch(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Boolean> future =
            asyncGetObjectClassRelevanceAdvisorySwitch(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Boolean> asyncGetObjectClassRelevanceAdvisorySwitch(

   )
   {
      hla.rti1516_2025.fedpro.GetObjectClassRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_2025.fedpro.GetObjectClassRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetObjectClassRelevanceAdvisorySwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetObjectClassRelevanceAdvisorySwitchResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETOBJECTCLASSRELEVANCEADVISORYSWITCHRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetObjectClassRelevanceAdvisorySwitchResponse response = callResponse.getGetObjectClassRelevanceAdvisorySwitchResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void setObjectClassRelevanceAdvisorySwitch(
      boolean value
   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSetObjectClassRelevanceAdvisorySwitch(
               value
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSetObjectClassRelevanceAdvisorySwitch(
      boolean value
   )
   {
      hla.rti1516_2025.fedpro.SetObjectClassRelevanceAdvisorySwitchRequest request;
      hla.rti1516_2025.fedpro.SetObjectClassRelevanceAdvisorySwitchRequest. Builder builder = hla.rti1516_2025.fedpro.SetObjectClassRelevanceAdvisorySwitchRequest. newBuilder();

      try {
         builder.setValue(_clientConverter.convertFromHla(value));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetObjectClassRelevanceAdvisorySwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSetObjectClassRelevanceAdvisorySwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SETOBJECTCLASSRELEVANCEADVISORYSWITCHRESPONSE));
         }
         return null;
      });
   }

   public boolean getAttributeRelevanceAdvisorySwitch(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Boolean> future =
            asyncGetAttributeRelevanceAdvisorySwitch(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Boolean> asyncGetAttributeRelevanceAdvisorySwitch(

   )
   {
      hla.rti1516_2025.fedpro.GetAttributeRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_2025.fedpro.GetAttributeRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetAttributeRelevanceAdvisorySwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetAttributeRelevanceAdvisorySwitchResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETATTRIBUTERELEVANCEADVISORYSWITCHRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetAttributeRelevanceAdvisorySwitchResponse response = callResponse.getGetAttributeRelevanceAdvisorySwitchResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void setAttributeRelevanceAdvisorySwitch(
      boolean value
   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSetAttributeRelevanceAdvisorySwitch(
               value
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSetAttributeRelevanceAdvisorySwitch(
      boolean value
   )
   {
      hla.rti1516_2025.fedpro.SetAttributeRelevanceAdvisorySwitchRequest request;
      hla.rti1516_2025.fedpro.SetAttributeRelevanceAdvisorySwitchRequest. Builder builder = hla.rti1516_2025.fedpro.SetAttributeRelevanceAdvisorySwitchRequest. newBuilder();

      try {
         builder.setValue(_clientConverter.convertFromHla(value));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetAttributeRelevanceAdvisorySwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSetAttributeRelevanceAdvisorySwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SETATTRIBUTERELEVANCEADVISORYSWITCHRESPONSE));
         }
         return null;
      });
   }

   public boolean getAttributeScopeAdvisorySwitch(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Boolean> future =
            asyncGetAttributeScopeAdvisorySwitch(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Boolean> asyncGetAttributeScopeAdvisorySwitch(

   )
   {
      hla.rti1516_2025.fedpro.GetAttributeScopeAdvisorySwitchRequest request;
      request = hla.rti1516_2025.fedpro.GetAttributeScopeAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetAttributeScopeAdvisorySwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetAttributeScopeAdvisorySwitchResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETATTRIBUTESCOPEADVISORYSWITCHRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetAttributeScopeAdvisorySwitchResponse response = callResponse.getGetAttributeScopeAdvisorySwitchResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void setAttributeScopeAdvisorySwitch(
      boolean value
   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSetAttributeScopeAdvisorySwitch(
               value
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSetAttributeScopeAdvisorySwitch(
      boolean value
   )
   {
      hla.rti1516_2025.fedpro.SetAttributeScopeAdvisorySwitchRequest request;
      hla.rti1516_2025.fedpro.SetAttributeScopeAdvisorySwitchRequest. Builder builder = hla.rti1516_2025.fedpro.SetAttributeScopeAdvisorySwitchRequest. newBuilder();

      try {
         builder.setValue(_clientConverter.convertFromHla(value));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetAttributeScopeAdvisorySwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSetAttributeScopeAdvisorySwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SETATTRIBUTESCOPEADVISORYSWITCHRESPONSE));
         }
         return null;
      });
   }

   public boolean getInteractionRelevanceAdvisorySwitch(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Boolean> future =
            asyncGetInteractionRelevanceAdvisorySwitch(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Boolean> asyncGetInteractionRelevanceAdvisorySwitch(

   )
   {
      hla.rti1516_2025.fedpro.GetInteractionRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_2025.fedpro.GetInteractionRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetInteractionRelevanceAdvisorySwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetInteractionRelevanceAdvisorySwitchResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETINTERACTIONRELEVANCEADVISORYSWITCHRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetInteractionRelevanceAdvisorySwitchResponse response = callResponse.getGetInteractionRelevanceAdvisorySwitchResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void setInteractionRelevanceAdvisorySwitch(
      boolean value
   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSetInteractionRelevanceAdvisorySwitch(
               value
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSetInteractionRelevanceAdvisorySwitch(
      boolean value
   )
   {
      hla.rti1516_2025.fedpro.SetInteractionRelevanceAdvisorySwitchRequest request;
      hla.rti1516_2025.fedpro.SetInteractionRelevanceAdvisorySwitchRequest. Builder builder = hla.rti1516_2025.fedpro.SetInteractionRelevanceAdvisorySwitchRequest. newBuilder();

      try {
         builder.setValue(_clientConverter.convertFromHla(value));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetInteractionRelevanceAdvisorySwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSetInteractionRelevanceAdvisorySwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SETINTERACTIONRELEVANCEADVISORYSWITCHRESPONSE));
         }
         return null;
      });
   }

   public boolean getConveyRegionDesignatorSetsSwitch(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Boolean> future =
            asyncGetConveyRegionDesignatorSetsSwitch(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Boolean> asyncGetConveyRegionDesignatorSetsSwitch(

   )
   {
      hla.rti1516_2025.fedpro.GetConveyRegionDesignatorSetsSwitchRequest request;
      request = hla.rti1516_2025.fedpro.GetConveyRegionDesignatorSetsSwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetConveyRegionDesignatorSetsSwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetConveyRegionDesignatorSetsSwitchResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETCONVEYREGIONDESIGNATORSETSSWITCHRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetConveyRegionDesignatorSetsSwitchResponse response = callResponse.getGetConveyRegionDesignatorSetsSwitchResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void setConveyRegionDesignatorSetsSwitch(
      boolean value
   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSetConveyRegionDesignatorSetsSwitch(
               value
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSetConveyRegionDesignatorSetsSwitch(
      boolean value
   )
   {
      hla.rti1516_2025.fedpro.SetConveyRegionDesignatorSetsSwitchRequest request;
      hla.rti1516_2025.fedpro.SetConveyRegionDesignatorSetsSwitchRequest. Builder builder = hla.rti1516_2025.fedpro.SetConveyRegionDesignatorSetsSwitchRequest. newBuilder();

      try {
         builder.setValue(_clientConverter.convertFromHla(value));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetConveyRegionDesignatorSetsSwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSetConveyRegionDesignatorSetsSwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SETCONVEYREGIONDESIGNATORSETSSWITCHRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_2025.ResignAction getAutomaticResignDirective(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<ResignAction> future =
            asyncGetAutomaticResignDirective(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<ResignAction> asyncGetAutomaticResignDirective(

   )
   {
      hla.rti1516_2025.fedpro.GetAutomaticResignDirectiveRequest request;
      request = hla.rti1516_2025.fedpro.GetAutomaticResignDirectiveRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetAutomaticResignDirectiveRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetAutomaticResignDirectiveResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETAUTOMATICRESIGNDIRECTIVERESPONSE);
            }
            hla.rti1516_2025.fedpro.GetAutomaticResignDirectiveResponse response = callResponse.getGetAutomaticResignDirectiveResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void setAutomaticResignDirective(
      hla.rti1516_2025.ResignAction value
   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSetAutomaticResignDirective(
               value
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSetAutomaticResignDirective(
      hla.rti1516_2025.ResignAction value
   )
   {
      hla.rti1516_2025.fedpro.SetAutomaticResignDirectiveRequest request;
      hla.rti1516_2025.fedpro.SetAutomaticResignDirectiveRequest. Builder builder = hla.rti1516_2025.fedpro.SetAutomaticResignDirectiveRequest. newBuilder();

      try {
         builder.setValue(_clientConverter.convertFromHla(value));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetAutomaticResignDirectiveRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSetAutomaticResignDirectiveResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SETAUTOMATICRESIGNDIRECTIVERESPONSE));
         }
         return null;
      });
   }

   public boolean getServiceReportingSwitch(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Boolean> future =
            asyncGetServiceReportingSwitch(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Boolean> asyncGetServiceReportingSwitch(

   )
   {
      hla.rti1516_2025.fedpro.GetServiceReportingSwitchRequest request;
      request = hla.rti1516_2025.fedpro.GetServiceReportingSwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetServiceReportingSwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetServiceReportingSwitchResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETSERVICEREPORTINGSWITCHRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetServiceReportingSwitchResponse response = callResponse.getGetServiceReportingSwitchResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void setServiceReportingSwitch(
      boolean value
   )
   throws
      ReportServiceInvocationsAreSubscribed,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSetServiceReportingSwitch(
               value
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSetServiceReportingSwitch(
      boolean value
   )
   {
      hla.rti1516_2025.fedpro.SetServiceReportingSwitchRequest request;
      hla.rti1516_2025.fedpro.SetServiceReportingSwitchRequest. Builder builder = hla.rti1516_2025.fedpro.SetServiceReportingSwitchRequest. newBuilder();

      try {
         builder.setValue(_clientConverter.convertFromHla(value));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetServiceReportingSwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSetServiceReportingSwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SETSERVICEREPORTINGSWITCHRESPONSE));
         }
         return null;
      });
   }

   public boolean getExceptionReportingSwitch(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Boolean> future =
            asyncGetExceptionReportingSwitch(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Boolean> asyncGetExceptionReportingSwitch(

   )
   {
      hla.rti1516_2025.fedpro.GetExceptionReportingSwitchRequest request;
      request = hla.rti1516_2025.fedpro.GetExceptionReportingSwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetExceptionReportingSwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetExceptionReportingSwitchResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETEXCEPTIONREPORTINGSWITCHRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetExceptionReportingSwitchResponse response = callResponse.getGetExceptionReportingSwitchResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void setExceptionReportingSwitch(
      boolean value
   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSetExceptionReportingSwitch(
               value
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSetExceptionReportingSwitch(
      boolean value
   )
   {
      hla.rti1516_2025.fedpro.SetExceptionReportingSwitchRequest request;
      hla.rti1516_2025.fedpro.SetExceptionReportingSwitchRequest. Builder builder = hla.rti1516_2025.fedpro.SetExceptionReportingSwitchRequest. newBuilder();

      try {
         builder.setValue(_clientConverter.convertFromHla(value));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetExceptionReportingSwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSetExceptionReportingSwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SETEXCEPTIONREPORTINGSWITCHRESPONSE));
         }
         return null;
      });
   }

   public boolean getSendServiceReportsToFileSwitch(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Boolean> future =
            asyncGetSendServiceReportsToFileSwitch(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Boolean> asyncGetSendServiceReportsToFileSwitch(

   )
   {
      hla.rti1516_2025.fedpro.GetSendServiceReportsToFileSwitchRequest request;
      request = hla.rti1516_2025.fedpro.GetSendServiceReportsToFileSwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetSendServiceReportsToFileSwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetSendServiceReportsToFileSwitchResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETSENDSERVICEREPORTSTOFILESWITCHRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetSendServiceReportsToFileSwitchResponse response = callResponse.getGetSendServiceReportsToFileSwitchResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void setSendServiceReportsToFileSwitch(
      boolean value
   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Void> future =
            asyncSetSendServiceReportsToFileSwitch(
               value
            );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Void> asyncSetSendServiceReportsToFileSwitch(
      boolean value
   )
   {
      hla.rti1516_2025.fedpro.SetSendServiceReportsToFileSwitchRequest request;
      hla.rti1516_2025.fedpro.SetSendServiceReportsToFileSwitchRequest. Builder builder = hla.rti1516_2025.fedpro.SetSendServiceReportsToFileSwitchRequest. newBuilder();

      try {
         builder.setValue(_clientConverter.convertFromHla(value));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetSendServiceReportsToFileSwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSetSendServiceReportsToFileSwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SETSENDSERVICEREPORTSTOFILESWITCHRESPONSE));
         }
         return null;
      });
   }

   public boolean getAutoProvideSwitch(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Boolean> future =
            asyncGetAutoProvideSwitch(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Boolean> asyncGetAutoProvideSwitch(

   )
   {
      hla.rti1516_2025.fedpro.GetAutoProvideSwitchRequest request;
      request = hla.rti1516_2025.fedpro.GetAutoProvideSwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetAutoProvideSwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetAutoProvideSwitchResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETAUTOPROVIDESWITCHRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetAutoProvideSwitchResponse response = callResponse.getGetAutoProvideSwitchResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public boolean getDelaySubscriptionEvaluationSwitch(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Boolean> future =
            asyncGetDelaySubscriptionEvaluationSwitch(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Boolean> asyncGetDelaySubscriptionEvaluationSwitch(

   )
   {
      hla.rti1516_2025.fedpro.GetDelaySubscriptionEvaluationSwitchRequest request;
      request = hla.rti1516_2025.fedpro.GetDelaySubscriptionEvaluationSwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetDelaySubscriptionEvaluationSwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetDelaySubscriptionEvaluationSwitchResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETDELAYSUBSCRIPTIONEVALUATIONSWITCHRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetDelaySubscriptionEvaluationSwitchResponse response = callResponse.getGetDelaySubscriptionEvaluationSwitchResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public boolean getAdvisoriesUseKnownClassSwitch(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Boolean> future =
            asyncGetAdvisoriesUseKnownClassSwitch(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Boolean> asyncGetAdvisoriesUseKnownClassSwitch(

   )
   {
      hla.rti1516_2025.fedpro.GetAdvisoriesUseKnownClassSwitchRequest request;
      request = hla.rti1516_2025.fedpro.GetAdvisoriesUseKnownClassSwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetAdvisoriesUseKnownClassSwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetAdvisoriesUseKnownClassSwitchResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETADVISORIESUSEKNOWNCLASSSWITCHRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetAdvisoriesUseKnownClassSwitchResponse response = callResponse.getGetAdvisoriesUseKnownClassSwitchResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public boolean getAllowRelaxedDDMSwitch(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Boolean> future =
            asyncGetAllowRelaxedDDMSwitch(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Boolean> asyncGetAllowRelaxedDDMSwitch(

   )
   {
      hla.rti1516_2025.fedpro.GetAllowRelaxedDDMSwitchRequest request;
      request = hla.rti1516_2025.fedpro.GetAllowRelaxedDDMSwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetAllowRelaxedDDMSwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetAllowRelaxedDDMSwitchResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETALLOWRELAXEDDDMSWITCHRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetAllowRelaxedDDMSwitchResponse response = callResponse.getGetAllowRelaxedDDMSwitchResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public boolean getNonRegulatedGrantSwitch(

   )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError
   {
      try {
         CompletableFuture<Boolean> future =
            asyncGetNonRegulatedGrantSwitch(

            );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

   public CompletableFuture<Boolean> asyncGetNonRegulatedGrantSwitch(

   )
   {
      hla.rti1516_2025.fedpro.GetNonRegulatedGrantSwitchRequest request;
      request = hla.rti1516_2025.fedpro.GetNonRegulatedGrantSwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetNonRegulatedGrantSwitchRequest(request).build();

      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetNonRegulatedGrantSwitchResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETNONREGULATEDGRANTSWITCHRESPONSE);
            }
            hla.rti1516_2025.fedpro.GetNonRegulatedGrantSwitchResponse response = callResponse.getGetNonRegulatedGrantSwitchResponse();
            return _clientConverter.convertToHla(response.getResult());
        } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }


}
