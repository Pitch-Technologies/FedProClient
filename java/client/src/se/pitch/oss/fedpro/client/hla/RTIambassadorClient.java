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
import hla.rti1516_202X.exceptions.*;
import hla.rti1516_202X.thin.CallRequest;
import hla.rti1516_202X.thin.CallResponse;

import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class RTIambassadorClient extends RTIambassadorClientHla4Base
{
   public RTIambassadorClient(ClientConverter clientConverter) {
      super(clientConverter);
   }

         public void
createFederationExecutionWithMIMAndTime(
   java.lang.String federationExecutionName,
   FomModuleSet fomModules,
   FomModule mimModule,
   java.lang.String logicalTimeImplementationName )
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
createFederationExecutionWithModulesAndTime(
   java.lang.String federationExecutionName,
   FomModuleSet fomModules,
   java.lang.String logicalTimeImplementationName )
   throws
      CouldNotCreateLogicalTimeFactory,
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      FederationExecutionAlreadyExists,
      Unauthorized,
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
   FomModule mimModule )
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
      RTIinternalError    {
     hla.rti1516_202X.thin.CreateFederationExecutionWithMIMRequest request;
     hla.rti1516_202X.thin.CreateFederationExecutionWithMIMRequest. Builder builder = hla.rti1516_202X.thin.CreateFederationExecutionWithMIMRequest. newBuilder();

     builder.setFederationExecutionName(_clientConverter.convertFromHla(federationExecutionName));
     builder.setFomModules(_clientConverter.convertFromHla(fomModules));
     builder.setMimModule(_clientConverter.convertFromHla(mimModule));

     request = builder.build();
     CallRequest callRequest = CallRequest.newBuilder().setCreateFederationExecutionWithMIMRequest(request).build();
     CallResponse callResponse = doHlaCall(callRequest);

    assert callResponse.hasCreateFederationExecutionWithMIMResponse();
    // No return value
   }

  public void
createFederationExecutionWithModules(
   java.lang.String federationExecutionName,
   FomModuleSet fomModules )
   throws
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      FederationExecutionAlreadyExists,
      Unauthorized,
      NotConnected,
      RTIinternalError    {
     hla.rti1516_202X.thin.CreateFederationExecutionWithModulesRequest request;
     hla.rti1516_202X.thin.CreateFederationExecutionWithModulesRequest. Builder builder = hla.rti1516_202X.thin.CreateFederationExecutionWithModulesRequest. newBuilder();

     builder.setFederationExecutionName(_clientConverter.convertFromHla(federationExecutionName));
     builder.setFomModules(_clientConverter.convertFromHla(fomModules));

     request = builder.build();
     CallRequest callRequest = CallRequest.newBuilder().setCreateFederationExecutionWithModulesRequest(request).build();
     CallResponse callResponse = doHlaCall(callRequest);

    assert callResponse.hasCreateFederationExecutionWithModulesResponse();
    // No return value
   }

  public void
createFederationExecution(
   java.lang.String federationExecutionName,
   FomModule fomModule )
   throws
      InconsistentFOM,
      InvalidFOM,
      ErrorReadingFOM,
      CouldNotOpenFOM,
      FederationExecutionAlreadyExists,
      Unauthorized,
      NotConnected,
      RTIinternalError    {
     hla.rti1516_202X.thin.CreateFederationExecutionRequest request;
     hla.rti1516_202X.thin.CreateFederationExecutionRequest. Builder builder = hla.rti1516_202X.thin.CreateFederationExecutionRequest. newBuilder();

     builder.setFederationExecutionName(_clientConverter.convertFromHla(federationExecutionName));
     builder.setFomModule(_clientConverter.convertFromHla(fomModule));

     request = builder.build();
     CallRequest callRequest = CallRequest.newBuilder().setCreateFederationExecutionRequest(request).build();
     CallResponse callResponse = doHlaCall(callRequest);

    assert callResponse.hasCreateFederationExecutionResponse();
    // No return value
   }

  public void
destroyFederationExecution(
   java.lang.String federationExecutionName )
   throws
      FederatesCurrentlyJoined,
      FederationExecutionDoesNotExist,
      Unauthorized,
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

      public CompletableFuture<Void>
asyncListFederationExecutions(
 )
   {
      hla.rti1516_202X.thin.ListFederationExecutionsRequest request;
      request = hla.rti1516_202X.thin.ListFederationExecutionsRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setListFederationExecutionsRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasListFederationExecutionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.LISTFEDERATIONEXECUTIONSRESPONSE));
         }
         return null;
      });
   }

   public void
listFederationExecutionMembers(
   java.lang.String federationExecutionName )
   throws
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncListFederationExecutionMembers(
               federationExecutionName );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncListFederationExecutionMembers(
   java.lang.String federationExecutionName )
   {
      hla.rti1516_202X.thin.ListFederationExecutionMembersRequest request;
      hla.rti1516_202X.thin.ListFederationExecutionMembersRequest. Builder builder = hla.rti1516_202X.thin.ListFederationExecutionMembersRequest. newBuilder();

      try {
         builder.setFederationExecutionName(_clientConverter.convertFromHla(federationExecutionName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setListFederationExecutionMembersRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasListFederationExecutionMembersResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.LISTFEDERATIONEXECUTIONMEMBERSRESPONSE));
         }
         return null;
      });
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
      Unauthorized,
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
joinFederationExecution(
   java.lang.String federateType,
   java.lang.String federationExecutionName )
   throws
      CouldNotCreateLogicalTimeFactory,
      FederationExecutionDoesNotExist,
      SaveInProgress,
      RestoreInProgress,
      FederateAlreadyExecutionMember,
      Unauthorized,
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

  public void
resignFederationExecution(
   hla.rti1516_202X.ResignAction resignAction )
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
      try {
         CompletableFuture<Void> future =
            asyncRegisterFederationSynchronizationPoint(
               synchronizationPointLabel,
               userSuppliedTag );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncRegisterFederationSynchronizationPoint(
   java.lang.String synchronizationPointLabel,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.RegisterFederationSynchronizationPointRequest request;
      hla.rti1516_202X.thin.RegisterFederationSynchronizationPointRequest. Builder builder = hla.rti1516_202X.thin.RegisterFederationSynchronizationPointRequest. newBuilder();

      try {
         builder.setSynchronizationPointLabel(_clientConverter.convertFromHla(synchronizationPointLabel));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterFederationSynchronizationPointRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRegisterFederationSynchronizationPointResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REGISTERFEDERATIONSYNCHRONIZATIONPOINTRESPONSE));
         }
         return null;
      });
   }

   public void
registerFederationSynchronizationPointWithSet(
   java.lang.String synchronizationPointLabel,
   byte[] userSuppliedTag,
   hla.rti1516_202X.FederateHandleSet synchronizationSet )
   throws
      InvalidFederateHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncRegisterFederationSynchronizationPointWithSet(
               synchronizationPointLabel,
               userSuppliedTag,
               synchronizationSet );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncRegisterFederationSynchronizationPointWithSet(
   java.lang.String synchronizationPointLabel,
   byte[] userSuppliedTag,
   hla.rti1516_202X.FederateHandleSet synchronizationSet )
   {
      hla.rti1516_202X.thin.RegisterFederationSynchronizationPointWithSetRequest request;
      hla.rti1516_202X.thin.RegisterFederationSynchronizationPointWithSetRequest. Builder builder = hla.rti1516_202X.thin.RegisterFederationSynchronizationPointWithSetRequest. newBuilder();

      try {
         builder.setSynchronizationPointLabel(_clientConverter.convertFromHla(synchronizationPointLabel));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
         builder.setSynchronizationSet(_clientConverter.convertFromHla(synchronizationSet));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRegisterFederationSynchronizationPointWithSetRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRegisterFederationSynchronizationPointWithSetResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REGISTERFEDERATIONSYNCHRONIZATIONPOINTWITHSETRESPONSE));
         }
         return null;
      });
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
      try {
         CompletableFuture<Void> future =
            asyncSynchronizationPointAchieved(
               synchronizationPointLabel,
               successIndicator );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSynchronizationPointAchieved(
   java.lang.String synchronizationPointLabel,
   boolean successIndicator )
   {
      hla.rti1516_202X.thin.SynchronizationPointAchievedRequest request;
      hla.rti1516_202X.thin.SynchronizationPointAchievedRequest. Builder builder = hla.rti1516_202X.thin.SynchronizationPointAchievedRequest. newBuilder();

      try {
         builder.setSynchronizationPointLabel(_clientConverter.convertFromHla(synchronizationPointLabel));
         builder.setSuccessIndicator(_clientConverter.convertFromHla(successIndicator));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSynchronizationPointAchievedRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSynchronizationPointAchievedResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SYNCHRONIZATIONPOINTACHIEVEDRESPONSE));
         }
         return null;
      });
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
      try {
         CompletableFuture<Void> future =
            asyncRequestFederationSave(
               label );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncRequestFederationSave(
   java.lang.String label )
   {
      hla.rti1516_202X.thin.RequestFederationSaveRequest request;
      hla.rti1516_202X.thin.RequestFederationSaveRequest. Builder builder = hla.rti1516_202X.thin.RequestFederationSaveRequest. newBuilder();

      try {
         builder.setLabel(_clientConverter.convertFromHla(label));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestFederationSaveRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestFederationSaveResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTFEDERATIONSAVERESPONSE));
         }
         return null;
      });
   }

   public void
requestFederationSaveWithTime(
   java.lang.String label,
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
   throws
      LogicalTimeAlreadyPassed,
      InvalidLogicalTime,
      FederateUnableToUseTime,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncRequestFederationSaveWithTime(
               label,
               theTime );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncRequestFederationSaveWithTime(
   java.lang.String label,
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
   {
      hla.rti1516_202X.thin.RequestFederationSaveWithTimeRequest request;
      hla.rti1516_202X.thin.RequestFederationSaveWithTimeRequest. Builder builder = hla.rti1516_202X.thin.RequestFederationSaveWithTimeRequest. newBuilder();

      try {
         builder.setLabel(_clientConverter.convertFromHla(label));
         builder.setTheTime(_clientConverter.convertFromHla(theTime));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestFederationSaveWithTimeRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestFederationSaveWithTimeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTFEDERATIONSAVEWITHTIMERESPONSE));
         }
         return null;
      });
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

      public CompletableFuture<Void>
asyncFederateSaveBegun(
 )
   {
      hla.rti1516_202X.thin.FederateSaveBegunRequest request;
      request = hla.rti1516_202X.thin.FederateSaveBegunRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateSaveBegunRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasFederateSaveBegunResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.FEDERATESAVEBEGUNRESPONSE));
         }
         return null;
      });
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

      public CompletableFuture<Void>
asyncFederateSaveComplete(
 )
   {
      hla.rti1516_202X.thin.FederateSaveCompleteRequest request;
      request = hla.rti1516_202X.thin.FederateSaveCompleteRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateSaveCompleteRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasFederateSaveCompleteResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.FEDERATESAVECOMPLETERESPONSE));
         }
         return null;
      });
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

      public CompletableFuture<Void>
asyncFederateSaveNotComplete(
 )
   {
      hla.rti1516_202X.thin.FederateSaveNotCompleteRequest request;
      request = hla.rti1516_202X.thin.FederateSaveNotCompleteRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateSaveNotCompleteRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasFederateSaveNotCompleteResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.FEDERATESAVENOTCOMPLETERESPONSE));
         }
         return null;
      });
   }

   public void
abortFederationSave(
 )
   throws
      SaveNotInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
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

      public CompletableFuture<Void>
asyncAbortFederationSave(
 )
   {
      hla.rti1516_202X.thin.AbortFederationSaveRequest request;
      request = hla.rti1516_202X.thin.AbortFederationSaveRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setAbortFederationSaveRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasAbortFederationSaveResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ABORTFEDERATIONSAVERESPONSE));
         }
         return null;
      });
   }

   public void
queryFederationSaveStatus(
 )
   throws
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
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

      public CompletableFuture<Void>
asyncQueryFederationSaveStatus(
 )
   {
      hla.rti1516_202X.thin.QueryFederationSaveStatusRequest request;
      request = hla.rti1516_202X.thin.QueryFederationSaveStatusRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryFederationSaveStatusRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasQueryFederationSaveStatusResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.QUERYFEDERATIONSAVESTATUSRESPONSE));
         }
         return null;
      });
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
      try {
         CompletableFuture<Void> future =
            asyncRequestFederationRestore(
               label );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncRequestFederationRestore(
   java.lang.String label )
   {
      hla.rti1516_202X.thin.RequestFederationRestoreRequest request;
      hla.rti1516_202X.thin.RequestFederationRestoreRequest. Builder builder = hla.rti1516_202X.thin.RequestFederationRestoreRequest. newBuilder();

      try {
         builder.setLabel(_clientConverter.convertFromHla(label));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestFederationRestoreRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestFederationRestoreResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTFEDERATIONRESTORERESPONSE));
         }
         return null;
      });
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

      public CompletableFuture<Void>
asyncFederateRestoreComplete(
 )
   {
      hla.rti1516_202X.thin.FederateRestoreCompleteRequest request;
      request = hla.rti1516_202X.thin.FederateRestoreCompleteRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateRestoreCompleteRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasFederateRestoreCompleteResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.FEDERATERESTORECOMPLETERESPONSE));
         }
         return null;
      });
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

      public CompletableFuture<Void>
asyncFederateRestoreNotComplete(
 )
   {
      hla.rti1516_202X.thin.FederateRestoreNotCompleteRequest request;
      request = hla.rti1516_202X.thin.FederateRestoreNotCompleteRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setFederateRestoreNotCompleteRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasFederateRestoreNotCompleteResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.FEDERATERESTORENOTCOMPLETERESPONSE));
         }
         return null;
      });
   }

   public void
abortFederationRestore(
 )
   throws
      RestoreNotInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
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

      public CompletableFuture<Void>
asyncAbortFederationRestore(
 )
   {
      hla.rti1516_202X.thin.AbortFederationRestoreRequest request;
      request = hla.rti1516_202X.thin.AbortFederationRestoreRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setAbortFederationRestoreRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasAbortFederationRestoreResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ABORTFEDERATIONRESTORERESPONSE));
         }
         return null;
      });
   }

   public void
queryFederationRestoreStatus(
 )
   throws
      SaveInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
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

      public CompletableFuture<Void>
asyncQueryFederationRestoreStatus(
 )
   {
      hla.rti1516_202X.thin.QueryFederationRestoreStatusRequest request;
      request = hla.rti1516_202X.thin.QueryFederationRestoreStatusRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryFederationRestoreStatusRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasQueryFederationRestoreStatusResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.QUERYFEDERATIONRESTORESTATUSRESPONSE));
         }
         return null;
      });
   }

   public void
publishObjectClassAttributes(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet attributeList )
   throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncPublishObjectClassAttributes(
               theClass,
               attributeList );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncPublishObjectClassAttributes(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet attributeList )
   {
      hla.rti1516_202X.thin.PublishObjectClassAttributesRequest request;
      hla.rti1516_202X.thin.PublishObjectClassAttributesRequest. Builder builder = hla.rti1516_202X.thin.PublishObjectClassAttributesRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setAttributeList(_clientConverter.convertFromHla(attributeList));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setPublishObjectClassAttributesRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasPublishObjectClassAttributesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.PUBLISHOBJECTCLASSATTRIBUTESRESPONSE));
         }
         return null;
      });
   }

   public void
unpublishObjectClass(
   hla.rti1516_202X.ObjectClassHandle theClass )
   throws
      OwnershipAcquisitionPending,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncUnpublishObjectClass(
               theClass );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUnpublishObjectClass(
   hla.rti1516_202X.ObjectClassHandle theClass )
   {
      hla.rti1516_202X.thin.UnpublishObjectClassRequest request;
      hla.rti1516_202X.thin.UnpublishObjectClassRequest. Builder builder = hla.rti1516_202X.thin.UnpublishObjectClassRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishObjectClassRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnpublishObjectClassResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNPUBLISHOBJECTCLASSRESPONSE));
         }
         return null;
      });
   }

   public void
unpublishObjectClassAttributes(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet attributeList )
   throws
      OwnershipAcquisitionPending,
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncUnpublishObjectClassAttributes(
               theClass,
               attributeList );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUnpublishObjectClassAttributes(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet attributeList )
   {
      hla.rti1516_202X.thin.UnpublishObjectClassAttributesRequest request;
      hla.rti1516_202X.thin.UnpublishObjectClassAttributesRequest. Builder builder = hla.rti1516_202X.thin.UnpublishObjectClassAttributesRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setAttributeList(_clientConverter.convertFromHla(attributeList));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishObjectClassAttributesRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnpublishObjectClassAttributesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNPUBLISHOBJECTCLASSATTRIBUTESRESPONSE));
         }
         return null;
      });
   }

   public void
publishObjectClassDirectedInteractions(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.InteractionClassHandleSet interactionList )
   throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncPublishObjectClassDirectedInteractions(
               theClass,
               interactionList );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncPublishObjectClassDirectedInteractions(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.InteractionClassHandleSet interactionList )
   {
      hla.rti1516_202X.thin.PublishObjectClassDirectedInteractionsRequest request;
      hla.rti1516_202X.thin.PublishObjectClassDirectedInteractionsRequest. Builder builder = hla.rti1516_202X.thin.PublishObjectClassDirectedInteractionsRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setInteractionList(_clientConverter.convertFromHla(interactionList));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setPublishObjectClassDirectedInteractionsRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasPublishObjectClassDirectedInteractionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.PUBLISHOBJECTCLASSDIRECTEDINTERACTIONSRESPONSE));
         }
         return null;
      });
   }

   public void
unpublishObjectClassDirectedInteractions(
   hla.rti1516_202X.ObjectClassHandle theClass )
   throws
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncUnpublishObjectClassDirectedInteractions(
               theClass );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUnpublishObjectClassDirectedInteractions(
   hla.rti1516_202X.ObjectClassHandle theClass )
   {
      hla.rti1516_202X.thin.UnpublishObjectClassDirectedInteractionsRequest request;
      hla.rti1516_202X.thin.UnpublishObjectClassDirectedInteractionsRequest. Builder builder = hla.rti1516_202X.thin.UnpublishObjectClassDirectedInteractionsRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishObjectClassDirectedInteractionsRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnpublishObjectClassDirectedInteractionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNPUBLISHOBJECTCLASSDIRECTEDINTERACTIONSRESPONSE));
         }
         return null;
      });
   }

   public void
unpublishObjectClassDirectedInteractionsWithSet(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.InteractionClassHandleSet interactionList )
   throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncUnpublishObjectClassDirectedInteractionsWithSet(
               theClass,
               interactionList );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUnpublishObjectClassDirectedInteractionsWithSet(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.InteractionClassHandleSet interactionList )
   {
      hla.rti1516_202X.thin.UnpublishObjectClassDirectedInteractionsWithSetRequest request;
      hla.rti1516_202X.thin.UnpublishObjectClassDirectedInteractionsWithSetRequest. Builder builder = hla.rti1516_202X.thin.UnpublishObjectClassDirectedInteractionsWithSetRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setInteractionList(_clientConverter.convertFromHla(interactionList));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishObjectClassDirectedInteractionsWithSetRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnpublishObjectClassDirectedInteractionsWithSetResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNPUBLISHOBJECTCLASSDIRECTEDINTERACTIONSWITHSETRESPONSE));
         }
         return null;
      });
   }

   public void
publishInteractionClass(
   hla.rti1516_202X.InteractionClassHandle theInteraction )
   throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncPublishInteractionClass(
               theInteraction );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncPublishInteractionClass(
   hla.rti1516_202X.InteractionClassHandle theInteraction )
   {
      hla.rti1516_202X.thin.PublishInteractionClassRequest request;
      hla.rti1516_202X.thin.PublishInteractionClassRequest. Builder builder = hla.rti1516_202X.thin.PublishInteractionClassRequest. newBuilder();

      try {
         builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setPublishInteractionClassRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasPublishInteractionClassResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.PUBLISHINTERACTIONCLASSRESPONSE));
         }
         return null;
      });
   }

   public void
unpublishInteractionClass(
   hla.rti1516_202X.InteractionClassHandle theInteraction )
   throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncUnpublishInteractionClass(
               theInteraction );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUnpublishInteractionClass(
   hla.rti1516_202X.InteractionClassHandle theInteraction )
   {
      hla.rti1516_202X.thin.UnpublishInteractionClassRequest request;
      hla.rti1516_202X.thin.UnpublishInteractionClassRequest. Builder builder = hla.rti1516_202X.thin.UnpublishInteractionClassRequest. newBuilder();

      try {
         builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnpublishInteractionClassRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnpublishInteractionClassResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNPUBLISHINTERACTIONCLASSRESPONSE));
         }
         return null;
      });
   }

   public void
subscribeObjectClassAttributes(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet attributeList )
   throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassAttributes(
               theClass,
               attributeList );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSubscribeObjectClassAttributes(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet attributeList )
   {
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesRequest request;
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesRequest. Builder builder = hla.rti1516_202X.thin.SubscribeObjectClassAttributesRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setAttributeList(_clientConverter.convertFromHla(attributeList));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassAttributesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSATTRIBUTESRESPONSE));
         }
         return null;
      });
   }

   public void
subscribeObjectClassAttributesWithRate(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet attributeList,
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
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassAttributesWithRate(
               theClass,
               attributeList,
               updateRateDesignator );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSubscribeObjectClassAttributesWithRate(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet attributeList,
   java.lang.String updateRateDesignator )
   {
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRateRequest request;
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRateRequest. Builder builder = hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRateRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setAttributeList(_clientConverter.convertFromHla(attributeList));
         builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesWithRateRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassAttributesWithRateResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSATTRIBUTESWITHRATERESPONSE));
         }
         return null;
      });
   }

   public void
subscribeObjectClassAttributesPassively(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet attributeList )
   throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassAttributesPassively(
               theClass,
               attributeList );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSubscribeObjectClassAttributesPassively(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet attributeList )
   {
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesPassivelyRequest request;
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesPassivelyRequest. Builder builder = hla.rti1516_202X.thin.SubscribeObjectClassAttributesPassivelyRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setAttributeList(_clientConverter.convertFromHla(attributeList));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesPassivelyRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassAttributesPassivelyResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSATTRIBUTESPASSIVELYRESPONSE));
         }
         return null;
      });
   }

   public void
subscribeObjectClassAttributesPassivelyWithRate(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet attributeList,
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
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassAttributesPassivelyWithRate(
               theClass,
               attributeList,
               updateRateDesignator );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSubscribeObjectClassAttributesPassivelyWithRate(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet attributeList,
   java.lang.String updateRateDesignator )
   {
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesPassivelyWithRateRequest request;
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesPassivelyWithRateRequest. Builder builder = hla.rti1516_202X.thin.SubscribeObjectClassAttributesPassivelyWithRateRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setAttributeList(_clientConverter.convertFromHla(attributeList));
         builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesPassivelyWithRateRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassAttributesPassivelyWithRateResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSATTRIBUTESPASSIVELYWITHRATERESPONSE));
         }
         return null;
      });
   }

   public void
unsubscribeObjectClass(
   hla.rti1516_202X.ObjectClassHandle theClass )
   throws
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncUnsubscribeObjectClass(
               theClass );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUnsubscribeObjectClass(
   hla.rti1516_202X.ObjectClassHandle theClass )
   {
      hla.rti1516_202X.thin.UnsubscribeObjectClassRequest request;
      hla.rti1516_202X.thin.UnsubscribeObjectClassRequest. Builder builder = hla.rti1516_202X.thin.UnsubscribeObjectClassRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnsubscribeObjectClassResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNSUBSCRIBEOBJECTCLASSRESPONSE));
         }
         return null;
      });
   }

   public void
unsubscribeObjectClassAttributes(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet attributeList )
   throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncUnsubscribeObjectClassAttributes(
               theClass,
               attributeList );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUnsubscribeObjectClassAttributes(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet attributeList )
   {
      hla.rti1516_202X.thin.UnsubscribeObjectClassAttributesRequest request;
      hla.rti1516_202X.thin.UnsubscribeObjectClassAttributesRequest. Builder builder = hla.rti1516_202X.thin.UnsubscribeObjectClassAttributesRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setAttributeList(_clientConverter.convertFromHla(attributeList));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassAttributesRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnsubscribeObjectClassAttributesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNSUBSCRIBEOBJECTCLASSATTRIBUTESRESPONSE));
         }
         return null;
      });
   }

   public void
subscribeObjectClassDirectedInteractions(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.InteractionClassHandleSet interactionList )
   throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassDirectedInteractions(
               theClass,
               interactionList );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSubscribeObjectClassDirectedInteractions(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.InteractionClassHandleSet interactionList )
   {
      hla.rti1516_202X.thin.SubscribeObjectClassDirectedInteractionsRequest request;
      hla.rti1516_202X.thin.SubscribeObjectClassDirectedInteractionsRequest. Builder builder = hla.rti1516_202X.thin.SubscribeObjectClassDirectedInteractionsRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setInteractionList(_clientConverter.convertFromHla(interactionList));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassDirectedInteractionsRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassDirectedInteractionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSDIRECTEDINTERACTIONSRESPONSE));
         }
         return null;
      });
   }

   public void
unsubscribeObjectClassDirectedInteractions(
   hla.rti1516_202X.ObjectClassHandle theClass )
   throws
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncUnsubscribeObjectClassDirectedInteractions(
               theClass );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUnsubscribeObjectClassDirectedInteractions(
   hla.rti1516_202X.ObjectClassHandle theClass )
   {
      hla.rti1516_202X.thin.UnsubscribeObjectClassDirectedInteractionsRequest request;
      hla.rti1516_202X.thin.UnsubscribeObjectClassDirectedInteractionsRequest. Builder builder = hla.rti1516_202X.thin.UnsubscribeObjectClassDirectedInteractionsRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassDirectedInteractionsRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnsubscribeObjectClassDirectedInteractionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNSUBSCRIBEOBJECTCLASSDIRECTEDINTERACTIONSRESPONSE));
         }
         return null;
      });
   }

   public void
unsubscribeObjectClassDirectedInteractionsWithSet(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.InteractionClassHandleSet interactionList )
   throws
      InteractionClassNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncUnsubscribeObjectClassDirectedInteractionsWithSet(
               theClass,
               interactionList );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUnsubscribeObjectClassDirectedInteractionsWithSet(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.InteractionClassHandleSet interactionList )
   {
      hla.rti1516_202X.thin.UnsubscribeObjectClassDirectedInteractionsWithSetRequest request;
      hla.rti1516_202X.thin.UnsubscribeObjectClassDirectedInteractionsWithSetRequest. Builder builder = hla.rti1516_202X.thin.UnsubscribeObjectClassDirectedInteractionsWithSetRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setInteractionList(_clientConverter.convertFromHla(interactionList));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassDirectedInteractionsWithSetRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnsubscribeObjectClassDirectedInteractionsWithSetResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNSUBSCRIBEOBJECTCLASSDIRECTEDINTERACTIONSWITHSETRESPONSE));
         }
         return null;
      });
   }

   public void
subscribeInteractionClass(
   hla.rti1516_202X.InteractionClassHandle theClass )
   throws
      FederateServiceInvocationsAreBeingReportedViaMOM,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncSubscribeInteractionClass(
               theClass );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSubscribeInteractionClass(
   hla.rti1516_202X.InteractionClassHandle theClass )
   {
      hla.rti1516_202X.thin.SubscribeInteractionClassRequest request;
      hla.rti1516_202X.thin.SubscribeInteractionClassRequest. Builder builder = hla.rti1516_202X.thin.SubscribeInteractionClassRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeInteractionClassRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeInteractionClassResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEINTERACTIONCLASSRESPONSE));
         }
         return null;
      });
   }

   public void
subscribeInteractionClassPassively(
   hla.rti1516_202X.InteractionClassHandle theClass )
   throws
      FederateServiceInvocationsAreBeingReportedViaMOM,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncSubscribeInteractionClassPassively(
               theClass );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSubscribeInteractionClassPassively(
   hla.rti1516_202X.InteractionClassHandle theClass )
   {
      hla.rti1516_202X.thin.SubscribeInteractionClassPassivelyRequest request;
      hla.rti1516_202X.thin.SubscribeInteractionClassPassivelyRequest. Builder builder = hla.rti1516_202X.thin.SubscribeInteractionClassPassivelyRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeInteractionClassPassivelyRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeInteractionClassPassivelyResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEINTERACTIONCLASSPASSIVELYRESPONSE));
         }
         return null;
      });
   }

   public void
unsubscribeInteractionClass(
   hla.rti1516_202X.InteractionClassHandle theClass )
   throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncUnsubscribeInteractionClass(
               theClass );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUnsubscribeInteractionClass(
   hla.rti1516_202X.InteractionClassHandle theClass )
   {
      hla.rti1516_202X.thin.UnsubscribeInteractionClassRequest request;
      hla.rti1516_202X.thin.UnsubscribeInteractionClassRequest. Builder builder = hla.rti1516_202X.thin.UnsubscribeInteractionClassRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeInteractionClassRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnsubscribeInteractionClassResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNSUBSCRIBEINTERACTIONCLASSRESPONSE));
         }
         return null;
      });
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
      try {
         CompletableFuture<Void> future =
            asyncReserveObjectInstanceName(
               theObjectName );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncReserveObjectInstanceName(
   java.lang.String theObjectName )
   {
      hla.rti1516_202X.thin.ReserveObjectInstanceNameRequest request;
      hla.rti1516_202X.thin.ReserveObjectInstanceNameRequest. Builder builder = hla.rti1516_202X.thin.ReserveObjectInstanceNameRequest. newBuilder();

      try {
         builder.setTheObjectName(_clientConverter.convertFromHla(theObjectName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReserveObjectInstanceNameRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasReserveObjectInstanceNameResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.RESERVEOBJECTINSTANCENAMERESPONSE));
         }
         return null;
      });
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
      try {
         CompletableFuture<Void> future =
            asyncReleaseObjectInstanceName(
               theObjectInstanceName );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncReleaseObjectInstanceName(
   java.lang.String theObjectInstanceName )
   {
      hla.rti1516_202X.thin.ReleaseObjectInstanceNameRequest request;
      hla.rti1516_202X.thin.ReleaseObjectInstanceNameRequest. Builder builder = hla.rti1516_202X.thin.ReleaseObjectInstanceNameRequest. newBuilder();

      try {
         builder.setTheObjectInstanceName(_clientConverter.convertFromHla(theObjectInstanceName));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReleaseObjectInstanceNameRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasReleaseObjectInstanceNameResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.RELEASEOBJECTINSTANCENAMERESPONSE));
         }
         return null;
      });
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
      try {
         CompletableFuture<Void> future =
            asyncReserveMultipleObjectInstanceName(
               theObjectNames );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncReserveMultipleObjectInstanceName(
   java.util.Set<java.lang.String> theObjectNames )
   {
      hla.rti1516_202X.thin.ReserveMultipleObjectInstanceNameRequest request;
      hla.rti1516_202X.thin.ReserveMultipleObjectInstanceNameRequest. Builder builder = hla.rti1516_202X.thin.ReserveMultipleObjectInstanceNameRequest. newBuilder();

      try {
         builder.addAllTheObjectNames(_clientConverter.convertFromHla(theObjectNames));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReserveMultipleObjectInstanceNameRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasReserveMultipleObjectInstanceNameResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.RESERVEMULTIPLEOBJECTINSTANCENAMERESPONSE));
         }
         return null;
      });
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
      try {
         CompletableFuture<Void> future =
            asyncReleaseMultipleObjectInstanceName(
               theObjectNames );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncReleaseMultipleObjectInstanceName(
   java.util.Set<java.lang.String> theObjectNames )
   {
      hla.rti1516_202X.thin.ReleaseMultipleObjectInstanceNameRequest request;
      hla.rti1516_202X.thin.ReleaseMultipleObjectInstanceNameRequest. Builder builder = hla.rti1516_202X.thin.ReleaseMultipleObjectInstanceNameRequest. newBuilder();

      try {
         builder.addAllTheObjectNames(_clientConverter.convertFromHla(theObjectNames));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setReleaseMultipleObjectInstanceNameRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasReleaseMultipleObjectInstanceNameResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.RELEASEMULTIPLEOBJECTINSTANCENAMERESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_202X.ObjectInstanceHandle
registerObjectInstance(
   hla.rti1516_202X.ObjectClassHandle theClass )
   throws
      ObjectClassNotPublished,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<ObjectInstanceHandle> future =
            asyncRegisterObjectInstance(
               theClass );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<ObjectInstanceHandle>
asyncRegisterObjectInstance(
   hla.rti1516_202X.ObjectClassHandle theClass )
   {
      hla.rti1516_202X.thin.RegisterObjectInstanceRequest request;
      hla.rti1516_202X.thin.RegisterObjectInstanceRequest. Builder builder = hla.rti1516_202X.thin.RegisterObjectInstanceRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
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
            hla.rti1516_202X.thin.RegisterObjectInstanceResponse response = callResponse.getRegisterObjectInstanceResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.ObjectInstanceHandle
registerObjectInstanceWithName(
   hla.rti1516_202X.ObjectClassHandle theClass,
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
      try {
         CompletableFuture<ObjectInstanceHandle> future =
            asyncRegisterObjectInstanceWithName(
               theClass,
               theObjectName );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<ObjectInstanceHandle>
asyncRegisterObjectInstanceWithName(
   hla.rti1516_202X.ObjectClassHandle theClass,
   java.lang.String theObjectName )
   {
      hla.rti1516_202X.thin.RegisterObjectInstanceWithNameRequest request;
      hla.rti1516_202X.thin.RegisterObjectInstanceWithNameRequest. Builder builder = hla.rti1516_202X.thin.RegisterObjectInstanceWithNameRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setTheObjectName(_clientConverter.convertFromHla(theObjectName));
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
            hla.rti1516_202X.thin.RegisterObjectInstanceWithNameResponse response = callResponse.getRegisterObjectInstanceWithNameResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void
updateAttributeValues(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleValueMap theAttributes,
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
      try {
         CompletableFuture<Void> future =
            asyncUpdateAttributeValues(
               theObject,
               theAttributes,
               userSuppliedTag );
         if (_asyncUpdates) {
            // Ignore future result
            return;
         }
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUpdateAttributeValues(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleValueMap theAttributes,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.UpdateAttributeValuesRequest request;
      hla.rti1516_202X.thin.UpdateAttributeValuesRequest. Builder builder = hla.rti1516_202X.thin.UpdateAttributeValuesRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUpdateAttributeValuesRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUpdateAttributeValuesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UPDATEATTRIBUTEVALUESRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_202X.MessageRetractionReturn
updateAttributeValuesWithTime(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleValueMap theAttributes,
   byte[] userSuppliedTag,
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
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
      try {
         CompletableFuture<MessageRetractionReturn> future =
            asyncUpdateAttributeValuesWithTime(
               theObject,
               theAttributes,
               userSuppliedTag,
               theTime );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<MessageRetractionReturn>
asyncUpdateAttributeValuesWithTime(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleValueMap theAttributes,
   byte[] userSuppliedTag,
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
   {
      hla.rti1516_202X.thin.UpdateAttributeValuesWithTimeRequest request;
      hla.rti1516_202X.thin.UpdateAttributeValuesWithTimeRequest. Builder builder = hla.rti1516_202X.thin.UpdateAttributeValuesWithTimeRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
         builder.setTheTime(_clientConverter.convertFromHla(theTime));
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
            hla.rti1516_202X.thin.UpdateAttributeValuesWithTimeResponse response = callResponse.getUpdateAttributeValuesWithTimeResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void
sendInteraction(
   hla.rti1516_202X.InteractionClassHandle theInteraction,
   hla.rti1516_202X.ParameterHandleValueMap theParameters,
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
      try {
         CompletableFuture<Void> future =
            asyncSendInteraction(
               theInteraction,
               theParameters,
               userSuppliedTag );
         if (_asyncUpdates) {
            // Ignore future result
            return;
         }
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSendInteraction(
   hla.rti1516_202X.InteractionClassHandle theInteraction,
   hla.rti1516_202X.ParameterHandleValueMap theParameters,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.SendInteractionRequest request;
      hla.rti1516_202X.thin.SendInteractionRequest. Builder builder = hla.rti1516_202X.thin.SendInteractionRequest. newBuilder();

      try {
         builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));
         builder.setTheParameters(_clientConverter.convertFromHla(theParameters));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSendInteractionResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SENDINTERACTIONRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_202X.MessageRetractionReturn
sendInteractionWithTime(
   hla.rti1516_202X.InteractionClassHandle theInteraction,
   hla.rti1516_202X.ParameterHandleValueMap theParameters,
   byte[] userSuppliedTag,
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
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
      try {
         CompletableFuture<MessageRetractionReturn> future =
            asyncSendInteractionWithTime(
               theInteraction,
               theParameters,
               userSuppliedTag,
               theTime );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<MessageRetractionReturn>
asyncSendInteractionWithTime(
   hla.rti1516_202X.InteractionClassHandle theInteraction,
   hla.rti1516_202X.ParameterHandleValueMap theParameters,
   byte[] userSuppliedTag,
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
   {
      hla.rti1516_202X.thin.SendInteractionWithTimeRequest request;
      hla.rti1516_202X.thin.SendInteractionWithTimeRequest. Builder builder = hla.rti1516_202X.thin.SendInteractionWithTimeRequest. newBuilder();

      try {
         builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));
         builder.setTheParameters(_clientConverter.convertFromHla(theParameters));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
         builder.setTheTime(_clientConverter.convertFromHla(theTime));
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
            hla.rti1516_202X.thin.SendInteractionWithTimeResponse response = callResponse.getSendInteractionWithTimeResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void
sendDirectedInteraction(
   hla.rti1516_202X.InteractionClassHandle theInteraction,
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.ParameterHandleValueMap theParameters,
   byte[] userSuppliedTag )
   throws
      ObjectInstanceNotKnown,
      InteractionClassNotPublished,
      InteractionParameterNotDefined,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncSendDirectedInteraction(
               theInteraction,
               theObject,
               theParameters,
               userSuppliedTag );
         if (_asyncUpdates) {
            // Ignore future result
            return;
         }
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSendDirectedInteraction(
   hla.rti1516_202X.InteractionClassHandle theInteraction,
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.ParameterHandleValueMap theParameters,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.SendDirectedInteractionRequest request;
      hla.rti1516_202X.thin.SendDirectedInteractionRequest. Builder builder = hla.rti1516_202X.thin.SendDirectedInteractionRequest. newBuilder();

      try {
         builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheParameters(_clientConverter.convertFromHla(theParameters));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendDirectedInteractionRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSendDirectedInteractionResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SENDDIRECTEDINTERACTIONRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_202X.MessageRetractionReturn
sendDirectedInteractionWithTime(
   hla.rti1516_202X.InteractionClassHandle theInteraction,
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.ParameterHandleValueMap theParameters,
   byte[] userSuppliedTag,
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
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
      RTIinternalError    {
      try {
         CompletableFuture<MessageRetractionReturn> future =
            asyncSendDirectedInteractionWithTime(
               theInteraction,
               theObject,
               theParameters,
               userSuppliedTag,
               theTime );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<MessageRetractionReturn>
asyncSendDirectedInteractionWithTime(
   hla.rti1516_202X.InteractionClassHandle theInteraction,
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.ParameterHandleValueMap theParameters,
   byte[] userSuppliedTag,
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
   {
      hla.rti1516_202X.thin.SendDirectedInteractionWithTimeRequest request;
      hla.rti1516_202X.thin.SendDirectedInteractionWithTimeRequest. Builder builder = hla.rti1516_202X.thin.SendDirectedInteractionWithTimeRequest. newBuilder();

      try {
         builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheParameters(_clientConverter.convertFromHla(theParameters));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
         builder.setTheTime(_clientConverter.convertFromHla(theTime));
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
            hla.rti1516_202X.thin.SendDirectedInteractionWithTimeResponse response = callResponse.getSendDirectedInteractionWithTimeResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void
deleteObjectInstance(
   hla.rti1516_202X.ObjectInstanceHandle objectHandle,
   byte[] userSuppliedTag )
   throws
      DeletePrivilegeNotHeld,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncDeleteObjectInstance(
               objectHandle,
               userSuppliedTag );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncDeleteObjectInstance(
   hla.rti1516_202X.ObjectInstanceHandle objectHandle,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.DeleteObjectInstanceRequest request;
      hla.rti1516_202X.thin.DeleteObjectInstanceRequest. Builder builder = hla.rti1516_202X.thin.DeleteObjectInstanceRequest. newBuilder();

      try {
         builder.setObjectHandle(_clientConverter.convertFromHla(objectHandle));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setDeleteObjectInstanceRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasDeleteObjectInstanceResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.DELETEOBJECTINSTANCERESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_202X.MessageRetractionReturn
deleteObjectInstanceWithTime(
   hla.rti1516_202X.ObjectInstanceHandle objectHandle,
   byte[] userSuppliedTag,
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
   throws
      InvalidLogicalTime,
      DeletePrivilegeNotHeld,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<MessageRetractionReturn> future =
            asyncDeleteObjectInstanceWithTime(
               objectHandle,
               userSuppliedTag,
               theTime );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<MessageRetractionReturn>
asyncDeleteObjectInstanceWithTime(
   hla.rti1516_202X.ObjectInstanceHandle objectHandle,
   byte[] userSuppliedTag,
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
   {
      hla.rti1516_202X.thin.DeleteObjectInstanceWithTimeRequest request;
      hla.rti1516_202X.thin.DeleteObjectInstanceWithTimeRequest. Builder builder = hla.rti1516_202X.thin.DeleteObjectInstanceWithTimeRequest. newBuilder();

      try {
         builder.setObjectHandle(_clientConverter.convertFromHla(objectHandle));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
         builder.setTheTime(_clientConverter.convertFromHla(theTime));
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
            hla.rti1516_202X.thin.DeleteObjectInstanceWithTimeResponse response = callResponse.getDeleteObjectInstanceWithTimeResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void
localDeleteObjectInstance(
   hla.rti1516_202X.ObjectInstanceHandle objectHandle )
   throws
      OwnershipAcquisitionPending,
      FederateOwnsAttributes,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncLocalDeleteObjectInstance(
               objectHandle );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncLocalDeleteObjectInstance(
   hla.rti1516_202X.ObjectInstanceHandle objectHandle )
   {
      hla.rti1516_202X.thin.LocalDeleteObjectInstanceRequest request;
      hla.rti1516_202X.thin.LocalDeleteObjectInstanceRequest. Builder builder = hla.rti1516_202X.thin.LocalDeleteObjectInstanceRequest. newBuilder();

      try {
         builder.setObjectHandle(_clientConverter.convertFromHla(objectHandle));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setLocalDeleteObjectInstanceRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasLocalDeleteObjectInstanceResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.LOCALDELETEOBJECTINSTANCERESPONSE));
         }
         return null;
      });
   }

   public void
requestInstanceAttributeValueUpdate(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   byte[] userSuppliedTag )
   throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncRequestInstanceAttributeValueUpdate(
               theObject,
               theAttributes,
               userSuppliedTag );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncRequestInstanceAttributeValueUpdate(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.RequestInstanceAttributeValueUpdateRequest request;
      hla.rti1516_202X.thin.RequestInstanceAttributeValueUpdateRequest. Builder builder = hla.rti1516_202X.thin.RequestInstanceAttributeValueUpdateRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestInstanceAttributeValueUpdateRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestInstanceAttributeValueUpdateResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTINSTANCEATTRIBUTEVALUEUPDATERESPONSE));
         }
         return null;
      });
   }

   public void
requestClassAttributeValueUpdate(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   byte[] userSuppliedTag )
   throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncRequestClassAttributeValueUpdate(
               theClass,
               theAttributes,
               userSuppliedTag );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncRequestClassAttributeValueUpdate(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.RequestClassAttributeValueUpdateRequest request;
      hla.rti1516_202X.thin.RequestClassAttributeValueUpdateRequest. Builder builder = hla.rti1516_202X.thin.RequestClassAttributeValueUpdateRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestClassAttributeValueUpdateRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestClassAttributeValueUpdateResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTCLASSATTRIBUTEVALUEUPDATERESPONSE));
         }
         return null;
      });
   }

   public void
requestAttributeTransportationTypeChange(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   hla.rti1516_202X.TransportationTypeHandle theType )
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
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncRequestAttributeTransportationTypeChange(
               theObject,
               theAttributes,
               theType );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncRequestAttributeTransportationTypeChange(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   hla.rti1516_202X.TransportationTypeHandle theType )
   {
      hla.rti1516_202X.thin.RequestAttributeTransportationTypeChangeRequest request;
      hla.rti1516_202X.thin.RequestAttributeTransportationTypeChangeRequest. Builder builder = hla.rti1516_202X.thin.RequestAttributeTransportationTypeChangeRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
         builder.setTheType(_clientConverter.convertFromHla(theType));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestAttributeTransportationTypeChangeRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestAttributeTransportationTypeChangeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTATTRIBUTETRANSPORTATIONTYPECHANGERESPONSE));
         }
         return null;
      });
   }

   public void
changeDefaultAttributeTransportationType(
   hla.rti1516_202X.ObjectClassHandle theObjectClass,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   hla.rti1516_202X.TransportationTypeHandle theType )
   throws
      AttributeAlreadyBeingChanged,
      AttributeNotDefined,
      ObjectClassNotDefined,
      InvalidTransportationTypeHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncChangeDefaultAttributeTransportationType(
               theObjectClass,
               theAttributes,
               theType );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncChangeDefaultAttributeTransportationType(
   hla.rti1516_202X.ObjectClassHandle theObjectClass,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   hla.rti1516_202X.TransportationTypeHandle theType )
   {
      hla.rti1516_202X.thin.ChangeDefaultAttributeTransportationTypeRequest request;
      hla.rti1516_202X.thin.ChangeDefaultAttributeTransportationTypeRequest. Builder builder = hla.rti1516_202X.thin.ChangeDefaultAttributeTransportationTypeRequest. newBuilder();

      try {
         builder.setTheObjectClass(_clientConverter.convertFromHla(theObjectClass));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
         builder.setTheType(_clientConverter.convertFromHla(theType));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setChangeDefaultAttributeTransportationTypeRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasChangeDefaultAttributeTransportationTypeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.CHANGEDEFAULTATTRIBUTETRANSPORTATIONTYPERESPONSE));
         }
         return null;
      });
   }

   public void
queryAttributeTransportationType(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandle theAttribute )
   throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncQueryAttributeTransportationType(
               theObject,
               theAttribute );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncQueryAttributeTransportationType(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandle theAttribute )
   {
      hla.rti1516_202X.thin.QueryAttributeTransportationTypeRequest request;
      hla.rti1516_202X.thin.QueryAttributeTransportationTypeRequest. Builder builder = hla.rti1516_202X.thin.QueryAttributeTransportationTypeRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttribute(_clientConverter.convertFromHla(theAttribute));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setQueryAttributeTransportationTypeRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasQueryAttributeTransportationTypeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.QUERYATTRIBUTETRANSPORTATIONTYPERESPONSE));
         }
         return null;
      });
   }

   public void
requestInteractionTransportationTypeChange(
   hla.rti1516_202X.InteractionClassHandle theClass,
   hla.rti1516_202X.TransportationTypeHandle theType )
   throws
      InteractionClassAlreadyBeingChanged,
      InteractionClassNotPublished,
      InteractionClassNotDefined,
      InvalidTransportationTypeHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncRequestInteractionTransportationTypeChange(
               theClass,
               theType );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncRequestInteractionTransportationTypeChange(
   hla.rti1516_202X.InteractionClassHandle theClass,
   hla.rti1516_202X.TransportationTypeHandle theType )
   {
      hla.rti1516_202X.thin.RequestInteractionTransportationTypeChangeRequest request;
      hla.rti1516_202X.thin.RequestInteractionTransportationTypeChangeRequest. Builder builder = hla.rti1516_202X.thin.RequestInteractionTransportationTypeChangeRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setTheType(_clientConverter.convertFromHla(theType));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestInteractionTransportationTypeChangeRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestInteractionTransportationTypeChangeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTINTERACTIONTRANSPORTATIONTYPECHANGERESPONSE));
         }
         return null;
      });
   }

   public void
queryInteractionTransportationType(
   hla.rti1516_202X.FederateHandle theFederate,
   hla.rti1516_202X.InteractionClassHandle theInteraction )
   throws
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncQueryInteractionTransportationType(
               theFederate,
               theInteraction );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncQueryInteractionTransportationType(
   hla.rti1516_202X.FederateHandle theFederate,
   hla.rti1516_202X.InteractionClassHandle theInteraction )
   {
      hla.rti1516_202X.thin.QueryInteractionTransportationTypeRequest request;
      hla.rti1516_202X.thin.QueryInteractionTransportationTypeRequest. Builder builder = hla.rti1516_202X.thin.QueryInteractionTransportationTypeRequest. newBuilder();

      try {
         builder.setTheFederate(_clientConverter.convertFromHla(theFederate));
         builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setQueryInteractionTransportationTypeRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasQueryInteractionTransportationTypeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.QUERYINTERACTIONTRANSPORTATIONTYPERESPONSE));
         }
         return null;
      });
   }

   public void
unconditionalAttributeOwnershipDivestiture(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
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
      try {
         CompletableFuture<Void> future =
            asyncUnconditionalAttributeOwnershipDivestiture(
               theObject,
               theAttributes,
               userSuppliedTag );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUnconditionalAttributeOwnershipDivestiture(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.UnconditionalAttributeOwnershipDivestitureRequest request;
      hla.rti1516_202X.thin.UnconditionalAttributeOwnershipDivestitureRequest. Builder builder = hla.rti1516_202X.thin.UnconditionalAttributeOwnershipDivestitureRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnconditionalAttributeOwnershipDivestitureRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnconditionalAttributeOwnershipDivestitureResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNCONDITIONALATTRIBUTEOWNERSHIPDIVESTITURERESPONSE));
         }
         return null;
      });
   }

   public void
negotiatedAttributeOwnershipDivestiture(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
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
      try {
         CompletableFuture<Void> future =
            asyncNegotiatedAttributeOwnershipDivestiture(
               theObject,
               theAttributes,
               userSuppliedTag );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncNegotiatedAttributeOwnershipDivestiture(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.NegotiatedAttributeOwnershipDivestitureRequest request;
      hla.rti1516_202X.thin.NegotiatedAttributeOwnershipDivestitureRequest. Builder builder = hla.rti1516_202X.thin.NegotiatedAttributeOwnershipDivestitureRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNegotiatedAttributeOwnershipDivestitureRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasNegotiatedAttributeOwnershipDivestitureResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.NEGOTIATEDATTRIBUTEOWNERSHIPDIVESTITURERESPONSE));
         }
         return null;
      });
   }

   public void
confirmDivestiture(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
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
      try {
         CompletableFuture<Void> future =
            asyncConfirmDivestiture(
               theObject,
               theAttributes,
               userSuppliedTag );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncConfirmDivestiture(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.ConfirmDivestitureRequest request;
      hla.rti1516_202X.thin.ConfirmDivestitureRequest. Builder builder = hla.rti1516_202X.thin.ConfirmDivestitureRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setConfirmDivestitureRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasConfirmDivestitureResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.CONFIRMDIVESTITURERESPONSE));
         }
         return null;
      });
   }

   public void
attributeOwnershipAcquisition(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet desiredAttributes,
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
      try {
         CompletableFuture<Void> future =
            asyncAttributeOwnershipAcquisition(
               theObject,
               desiredAttributes,
               userSuppliedTag );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncAttributeOwnershipAcquisition(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet desiredAttributes,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.AttributeOwnershipAcquisitionRequest request;
      hla.rti1516_202X.thin.AttributeOwnershipAcquisitionRequest. Builder builder = hla.rti1516_202X.thin.AttributeOwnershipAcquisitionRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setDesiredAttributes(_clientConverter.convertFromHla(desiredAttributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAttributeOwnershipAcquisitionRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasAttributeOwnershipAcquisitionResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ATTRIBUTEOWNERSHIPACQUISITIONRESPONSE));
         }
         return null;
      });
   }

   public void
attributeOwnershipAcquisitionIfAvailable(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet desiredAttributes,
   byte[] userSuppliedTag )
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
      try {
         CompletableFuture<Void> future =
            asyncAttributeOwnershipAcquisitionIfAvailable(
               theObject,
               desiredAttributes,
               userSuppliedTag );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncAttributeOwnershipAcquisitionIfAvailable(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet desiredAttributes,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.AttributeOwnershipAcquisitionIfAvailableRequest request;
      hla.rti1516_202X.thin.AttributeOwnershipAcquisitionIfAvailableRequest. Builder builder = hla.rti1516_202X.thin.AttributeOwnershipAcquisitionIfAvailableRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setDesiredAttributes(_clientConverter.convertFromHla(desiredAttributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAttributeOwnershipAcquisitionIfAvailableRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasAttributeOwnershipAcquisitionIfAvailableResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ATTRIBUTEOWNERSHIPACQUISITIONIFAVAILABLERESPONSE));
         }
         return null;
      });
   }

   public void
attributeOwnershipReleaseDenied(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
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
      try {
         CompletableFuture<Void> future =
            asyncAttributeOwnershipReleaseDenied(
               theObject,
               theAttributes,
               userSuppliedTag );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncAttributeOwnershipReleaseDenied(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.AttributeOwnershipReleaseDeniedRequest request;
      hla.rti1516_202X.thin.AttributeOwnershipReleaseDeniedRequest. Builder builder = hla.rti1516_202X.thin.AttributeOwnershipReleaseDeniedRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAttributeOwnershipReleaseDeniedRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasAttributeOwnershipReleaseDeniedResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ATTRIBUTEOWNERSHIPRELEASEDENIEDRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_202X.AttributeHandleSet
attributeOwnershipDivestitureIfWanted(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
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
      try {
         CompletableFuture<AttributeHandleSet> future =
            asyncAttributeOwnershipDivestitureIfWanted(
               theObject,
               theAttributes,
               userSuppliedTag );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<AttributeHandleSet>
asyncAttributeOwnershipDivestitureIfWanted(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.AttributeOwnershipDivestitureIfWantedRequest request;
      hla.rti1516_202X.thin.AttributeOwnershipDivestitureIfWantedRequest. Builder builder = hla.rti1516_202X.thin.AttributeOwnershipDivestitureIfWantedRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
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
            hla.rti1516_202X.thin.AttributeOwnershipDivestitureIfWantedResponse response = callResponse.getAttributeOwnershipDivestitureIfWantedResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void
cancelNegotiatedAttributeOwnershipDivestiture(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes )
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
      try {
         CompletableFuture<Void> future =
            asyncCancelNegotiatedAttributeOwnershipDivestiture(
               theObject,
               theAttributes );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncCancelNegotiatedAttributeOwnershipDivestiture(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes )
   {
      hla.rti1516_202X.thin.CancelNegotiatedAttributeOwnershipDivestitureRequest request;
      hla.rti1516_202X.thin.CancelNegotiatedAttributeOwnershipDivestitureRequest. Builder builder = hla.rti1516_202X.thin.CancelNegotiatedAttributeOwnershipDivestitureRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCancelNegotiatedAttributeOwnershipDivestitureRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasCancelNegotiatedAttributeOwnershipDivestitureResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.CANCELNEGOTIATEDATTRIBUTEOWNERSHIPDIVESTITURERESPONSE));
         }
         return null;
      });
   }

   public void
cancelAttributeOwnershipAcquisition(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes )
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
      try {
         CompletableFuture<Void> future =
            asyncCancelAttributeOwnershipAcquisition(
               theObject,
               theAttributes );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncCancelAttributeOwnershipAcquisition(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes )
   {
      hla.rti1516_202X.thin.CancelAttributeOwnershipAcquisitionRequest request;
      hla.rti1516_202X.thin.CancelAttributeOwnershipAcquisitionRequest. Builder builder = hla.rti1516_202X.thin.CancelAttributeOwnershipAcquisitionRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCancelAttributeOwnershipAcquisitionRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasCancelAttributeOwnershipAcquisitionResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.CANCELATTRIBUTEOWNERSHIPACQUISITIONRESPONSE));
         }
         return null;
      });
   }

   public void
queryAttributeOwnership(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes )
   throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncQueryAttributeOwnership(
               theObject,
               theAttributes );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncQueryAttributeOwnership(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes )
   {
      hla.rti1516_202X.thin.QueryAttributeOwnershipRequest request;
      hla.rti1516_202X.thin.QueryAttributeOwnershipRequest. Builder builder = hla.rti1516_202X.thin.QueryAttributeOwnershipRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setQueryAttributeOwnershipRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasQueryAttributeOwnershipResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.QUERYATTRIBUTEOWNERSHIPRESPONSE));
         }
         return null;
      });
   }

   public boolean
isAttributeOwnedByFederate(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandle theAttribute )
   throws
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Boolean> future =
            asyncIsAttributeOwnedByFederate(
               theObject,
               theAttribute );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Boolean>
asyncIsAttributeOwnedByFederate(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandle theAttribute )
   {
      hla.rti1516_202X.thin.IsAttributeOwnedByFederateRequest request;
      hla.rti1516_202X.thin.IsAttributeOwnedByFederateRequest. Builder builder = hla.rti1516_202X.thin.IsAttributeOwnedByFederateRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttribute(_clientConverter.convertFromHla(theAttribute));
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
            hla.rti1516_202X.thin.IsAttributeOwnedByFederateResponse response = callResponse.getIsAttributeOwnedByFederateResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void
enableTimeRegulation(
   hla.rti1516_202X.LogicalTimeInterval<?> theLookahead )
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
      try {
         CompletableFuture<Void> future =
            asyncEnableTimeRegulation(
               theLookahead );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncEnableTimeRegulation(
   hla.rti1516_202X.LogicalTimeInterval<?> theLookahead )
   {
      hla.rti1516_202X.thin.EnableTimeRegulationRequest request;
      hla.rti1516_202X.thin.EnableTimeRegulationRequest. Builder builder = hla.rti1516_202X.thin.EnableTimeRegulationRequest. newBuilder();

      try {
         builder.setTheLookahead(_clientConverter.convertFromHla(theLookahead));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setEnableTimeRegulationRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasEnableTimeRegulationResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ENABLETIMEREGULATIONRESPONSE));
         }
         return null;
      });
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

      public CompletableFuture<Void>
asyncDisableTimeRegulation(
 )
   {
      hla.rti1516_202X.thin.DisableTimeRegulationRequest request;
      request = hla.rti1516_202X.thin.DisableTimeRegulationRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableTimeRegulationRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasDisableTimeRegulationResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.DISABLETIMEREGULATIONRESPONSE));
         }
         return null;
      });
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

      public CompletableFuture<Void>
asyncEnableTimeConstrained(
 )
   {
      hla.rti1516_202X.thin.EnableTimeConstrainedRequest request;
      request = hla.rti1516_202X.thin.EnableTimeConstrainedRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setEnableTimeConstrainedRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasEnableTimeConstrainedResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ENABLETIMECONSTRAINEDRESPONSE));
         }
         return null;
      });
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

      public CompletableFuture<Void>
asyncDisableTimeConstrained(
 )
   {
      hla.rti1516_202X.thin.DisableTimeConstrainedRequest request;
      request = hla.rti1516_202X.thin.DisableTimeConstrainedRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableTimeConstrainedRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasDisableTimeConstrainedResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.DISABLETIMECONSTRAINEDRESPONSE));
         }
         return null;
      });
   }

   public void
timeAdvanceRequest(
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
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
      try {
         CompletableFuture<Void> future =
            asyncTimeAdvanceRequest(
               theTime );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncTimeAdvanceRequest(
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
   {
      hla.rti1516_202X.thin.TimeAdvanceRequestRequest request;
      hla.rti1516_202X.thin.TimeAdvanceRequestRequest. Builder builder = hla.rti1516_202X.thin.TimeAdvanceRequestRequest. newBuilder();

      try {
         builder.setTheTime(_clientConverter.convertFromHla(theTime));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setTimeAdvanceRequestRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasTimeAdvanceRequestResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.TIMEADVANCEREQUESTRESPONSE));
         }
         return null;
      });
   }

   public void
timeAdvanceRequestAvailable(
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
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
      try {
         CompletableFuture<Void> future =
            asyncTimeAdvanceRequestAvailable(
               theTime );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncTimeAdvanceRequestAvailable(
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
   {
      hla.rti1516_202X.thin.TimeAdvanceRequestAvailableRequest request;
      hla.rti1516_202X.thin.TimeAdvanceRequestAvailableRequest. Builder builder = hla.rti1516_202X.thin.TimeAdvanceRequestAvailableRequest. newBuilder();

      try {
         builder.setTheTime(_clientConverter.convertFromHla(theTime));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setTimeAdvanceRequestAvailableRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasTimeAdvanceRequestAvailableResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.TIMEADVANCEREQUESTAVAILABLERESPONSE));
         }
         return null;
      });
   }

   public void
nextMessageRequest(
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
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
      try {
         CompletableFuture<Void> future =
            asyncNextMessageRequest(
               theTime );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncNextMessageRequest(
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
   {
      hla.rti1516_202X.thin.NextMessageRequestRequest request;
      hla.rti1516_202X.thin.NextMessageRequestRequest. Builder builder = hla.rti1516_202X.thin.NextMessageRequestRequest. newBuilder();

      try {
         builder.setTheTime(_clientConverter.convertFromHla(theTime));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNextMessageRequestRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasNextMessageRequestResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.NEXTMESSAGEREQUESTRESPONSE));
         }
         return null;
      });
   }

   public void
nextMessageRequestAvailable(
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
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
      try {
         CompletableFuture<Void> future =
            asyncNextMessageRequestAvailable(
               theTime );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncNextMessageRequestAvailable(
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
   {
      hla.rti1516_202X.thin.NextMessageRequestAvailableRequest request;
      hla.rti1516_202X.thin.NextMessageRequestAvailableRequest. Builder builder = hla.rti1516_202X.thin.NextMessageRequestAvailableRequest. newBuilder();

      try {
         builder.setTheTime(_clientConverter.convertFromHla(theTime));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setNextMessageRequestAvailableRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasNextMessageRequestAvailableResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.NEXTMESSAGEREQUESTAVAILABLERESPONSE));
         }
         return null;
      });
   }

   public void
flushQueueRequest(
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
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
      try {
         CompletableFuture<Void> future =
            asyncFlushQueueRequest(
               theTime );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncFlushQueueRequest(
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
   {
      hla.rti1516_202X.thin.FlushQueueRequestRequest request;
      hla.rti1516_202X.thin.FlushQueueRequestRequest. Builder builder = hla.rti1516_202X.thin.FlushQueueRequestRequest. newBuilder();

      try {
         builder.setTheTime(_clientConverter.convertFromHla(theTime));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setFlushQueueRequestRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasFlushQueueRequestResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.FLUSHQUEUEREQUESTRESPONSE));
         }
         return null;
      });
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

      public CompletableFuture<Void>
asyncEnableAsynchronousDelivery(
 )
   {
      hla.rti1516_202X.thin.EnableAsynchronousDeliveryRequest request;
      request = hla.rti1516_202X.thin.EnableAsynchronousDeliveryRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setEnableAsynchronousDeliveryRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasEnableAsynchronousDeliveryResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ENABLEASYNCHRONOUSDELIVERYRESPONSE));
         }
         return null;
      });
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

      public CompletableFuture<Void>
asyncDisableAsynchronousDelivery(
 )
   {
      hla.rti1516_202X.thin.DisableAsynchronousDeliveryRequest request;
      request = hla.rti1516_202X.thin.DisableAsynchronousDeliveryRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableAsynchronousDeliveryRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasDisableAsynchronousDeliveryResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.DISABLEASYNCHRONOUSDELIVERYRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_202X.TimeQueryReturn
queryGALT(
 )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
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

      public CompletableFuture<TimeQueryReturn>
asyncQueryGALT(
 )
   {
      hla.rti1516_202X.thin.QueryGALTRequest request;
      request = hla.rti1516_202X.thin.QueryGALTRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryGALTRequest(request).build();
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasQueryGALTResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.QUERYGALTRESPONSE);
            }
            hla.rti1516_202X.thin.QueryGALTResponse response = callResponse.getQueryGALTResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.LogicalTime<?, ?>
queryLogicalTime(
 )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
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

      public CompletableFuture<LogicalTime>
asyncQueryLogicalTime(
 )
   {
      hla.rti1516_202X.thin.QueryLogicalTimeRequest request;
      request = hla.rti1516_202X.thin.QueryLogicalTimeRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLogicalTimeRequest(request).build();
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasQueryLogicalTimeResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.QUERYLOGICALTIMERESPONSE);
            }
            hla.rti1516_202X.thin.QueryLogicalTimeResponse response = callResponse.getQueryLogicalTimeResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.TimeQueryReturn
queryLITS(
 )
   throws
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
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

      public CompletableFuture<TimeQueryReturn>
asyncQueryLITS(
 )
   {
      hla.rti1516_202X.thin.QueryLITSRequest request;
      request = hla.rti1516_202X.thin.QueryLITSRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLITSRequest(request).build();
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasQueryLITSResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.QUERYLITSRESPONSE);
            }
            hla.rti1516_202X.thin.QueryLITSResponse response = callResponse.getQueryLITSResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void
modifyLookahead(
   hla.rti1516_202X.LogicalTimeInterval<?> theLookahead )
   throws
      InvalidLookahead,
      InTimeAdvancingState,
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncModifyLookahead(
               theLookahead );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncModifyLookahead(
   hla.rti1516_202X.LogicalTimeInterval<?> theLookahead )
   {
      hla.rti1516_202X.thin.ModifyLookaheadRequest request;
      hla.rti1516_202X.thin.ModifyLookaheadRequest. Builder builder = hla.rti1516_202X.thin.ModifyLookaheadRequest. newBuilder();

      try {
         builder.setTheLookahead(_clientConverter.convertFromHla(theLookahead));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setModifyLookaheadRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasModifyLookaheadResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.MODIFYLOOKAHEADRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_202X.LogicalTimeInterval<?>
queryLookahead(
 )
   throws
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
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

      public CompletableFuture<LogicalTimeInterval>
asyncQueryLookahead(
 )
   {
      hla.rti1516_202X.thin.QueryLookaheadRequest request;
      request = hla.rti1516_202X.thin.QueryLookaheadRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setQueryLookaheadRequest(request).build();
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasQueryLookaheadResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.QUERYLOOKAHEADRESPONSE);
            }
            hla.rti1516_202X.thin.QueryLookaheadResponse response = callResponse.getQueryLookaheadResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void
retract(
   hla.rti1516_202X.MessageRetractionHandle theHandle )
   throws
      MessageCanNoLongerBeRetracted,
      InvalidMessageRetractionHandle,
      TimeRegulationIsNotEnabled,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncRetract(
               theHandle );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncRetract(
   hla.rti1516_202X.MessageRetractionHandle theHandle )
   {
      hla.rti1516_202X.thin.RetractRequest request;
      hla.rti1516_202X.thin.RetractRequest. Builder builder = hla.rti1516_202X.thin.RetractRequest. newBuilder();

      try {
         builder.setTheHandle(_clientConverter.convertFromHla(theHandle));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRetractRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRetractResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.RETRACTRESPONSE));
         }
         return null;
      });
   }

   public void
changeAttributeOrderType(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   hla.rti1516_202X.OrderType theType )
   throws
      AttributeNotOwned,
      AttributeNotDefined,
      ObjectInstanceNotKnown,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncChangeAttributeOrderType(
               theObject,
               theAttributes,
               theType );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncChangeAttributeOrderType(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   hla.rti1516_202X.OrderType theType )
   {
      hla.rti1516_202X.thin.ChangeAttributeOrderTypeRequest request;
      hla.rti1516_202X.thin.ChangeAttributeOrderTypeRequest. Builder builder = hla.rti1516_202X.thin.ChangeAttributeOrderTypeRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
         builder.setTheType(_clientConverter.convertFromHla(theType));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setChangeAttributeOrderTypeRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasChangeAttributeOrderTypeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.CHANGEATTRIBUTEORDERTYPERESPONSE));
         }
         return null;
      });
   }

   public void
changeDefaultAttributeOrderType(
   hla.rti1516_202X.ObjectClassHandle theObjectClass,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   hla.rti1516_202X.OrderType theType )
   throws
      AttributeNotDefined,
      ObjectClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncChangeDefaultAttributeOrderType(
               theObjectClass,
               theAttributes,
               theType );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncChangeDefaultAttributeOrderType(
   hla.rti1516_202X.ObjectClassHandle theObjectClass,
   hla.rti1516_202X.AttributeHandleSet theAttributes,
   hla.rti1516_202X.OrderType theType )
   {
      hla.rti1516_202X.thin.ChangeDefaultAttributeOrderTypeRequest request;
      hla.rti1516_202X.thin.ChangeDefaultAttributeOrderTypeRequest. Builder builder = hla.rti1516_202X.thin.ChangeDefaultAttributeOrderTypeRequest. newBuilder();

      try {
         builder.setTheObjectClass(_clientConverter.convertFromHla(theObjectClass));
         builder.setTheAttributes(_clientConverter.convertFromHla(theAttributes));
         builder.setTheType(_clientConverter.convertFromHla(theType));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setChangeDefaultAttributeOrderTypeRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasChangeDefaultAttributeOrderTypeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.CHANGEDEFAULTATTRIBUTEORDERTYPERESPONSE));
         }
         return null;
      });
   }

   public void
changeInteractionOrderType(
   hla.rti1516_202X.InteractionClassHandle theClass,
   hla.rti1516_202X.OrderType theType )
   throws
      InteractionClassNotPublished,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncChangeInteractionOrderType(
               theClass,
               theType );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncChangeInteractionOrderType(
   hla.rti1516_202X.InteractionClassHandle theClass,
   hla.rti1516_202X.OrderType theType )
   {
      hla.rti1516_202X.thin.ChangeInteractionOrderTypeRequest request;
      hla.rti1516_202X.thin.ChangeInteractionOrderTypeRequest. Builder builder = hla.rti1516_202X.thin.ChangeInteractionOrderTypeRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setTheType(_clientConverter.convertFromHla(theType));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setChangeInteractionOrderTypeRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasChangeInteractionOrderTypeResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.CHANGEINTERACTIONORDERTYPERESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_202X.RegionHandle
createRegion(
   hla.rti1516_202X.DimensionHandleSet dimensions )
   throws
      InvalidDimensionHandle,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<RegionHandle> future =
            asyncCreateRegion(
               dimensions );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<RegionHandle>
asyncCreateRegion(
   hla.rti1516_202X.DimensionHandleSet dimensions )
   {
      hla.rti1516_202X.thin.CreateRegionRequest request;
      hla.rti1516_202X.thin.CreateRegionRequest. Builder builder = hla.rti1516_202X.thin.CreateRegionRequest. newBuilder();

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
            hla.rti1516_202X.thin.CreateRegionResponse response = callResponse.getCreateRegionResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void
commitRegionModifications(
   hla.rti1516_202X.RegionHandleSet regions )
   throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncCommitRegionModifications(
               regions );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncCommitRegionModifications(
   hla.rti1516_202X.RegionHandleSet regions )
   {
      hla.rti1516_202X.thin.CommitRegionModificationsRequest request;
      hla.rti1516_202X.thin.CommitRegionModificationsRequest. Builder builder = hla.rti1516_202X.thin.CommitRegionModificationsRequest. newBuilder();

      try {
         builder.setRegions(_clientConverter.convertFromHla(regions));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setCommitRegionModificationsRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasCommitRegionModificationsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.COMMITREGIONMODIFICATIONSRESPONSE));
         }
         return null;
      });
   }

   public void
deleteRegion(
   hla.rti1516_202X.RegionHandle theRegion )
   throws
      RegionInUseForUpdateOrSubscription,
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncDeleteRegion(
               theRegion );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncDeleteRegion(
   hla.rti1516_202X.RegionHandle theRegion )
   {
      hla.rti1516_202X.thin.DeleteRegionRequest request;
      hla.rti1516_202X.thin.DeleteRegionRequest. Builder builder = hla.rti1516_202X.thin.DeleteRegionRequest. newBuilder();

      try {
         builder.setTheRegion(_clientConverter.convertFromHla(theRegion));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setDeleteRegionRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasDeleteRegionResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.DELETEREGIONRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_202X.ObjectInstanceHandle
registerObjectInstanceWithRegions(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions )
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
      try {
         CompletableFuture<ObjectInstanceHandle> future =
            asyncRegisterObjectInstanceWithRegions(
               theClass,
               attributesAndRegions );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<ObjectInstanceHandle>
asyncRegisterObjectInstanceWithRegions(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions )
   {
      hla.rti1516_202X.thin.RegisterObjectInstanceWithRegionsRequest request;
      hla.rti1516_202X.thin.RegisterObjectInstanceWithRegionsRequest. Builder builder = hla.rti1516_202X.thin.RegisterObjectInstanceWithRegionsRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
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
            hla.rti1516_202X.thin.RegisterObjectInstanceWithRegionsResponse response = callResponse.getRegisterObjectInstanceWithRegionsResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.ObjectInstanceHandle
registerObjectInstanceWithNameAndRegions(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions,
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
      try {
         CompletableFuture<ObjectInstanceHandle> future =
            asyncRegisterObjectInstanceWithNameAndRegions(
               theClass,
               attributesAndRegions,
               theObject );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<ObjectInstanceHandle>
asyncRegisterObjectInstanceWithNameAndRegions(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions,
   java.lang.String theObject )
   {
      hla.rti1516_202X.thin.RegisterObjectInstanceWithNameAndRegionsRequest request;
      hla.rti1516_202X.thin.RegisterObjectInstanceWithNameAndRegionsRequest. Builder builder = hla.rti1516_202X.thin.RegisterObjectInstanceWithNameAndRegionsRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
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
            hla.rti1516_202X.thin.RegisterObjectInstanceWithNameAndRegionsResponse response = callResponse.getRegisterObjectInstanceWithNameAndRegionsResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void
associateRegionsForUpdates(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions )
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
      try {
         CompletableFuture<Void> future =
            asyncAssociateRegionsForUpdates(
               theObject,
               attributesAndRegions );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncAssociateRegionsForUpdates(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions )
   {
      hla.rti1516_202X.thin.AssociateRegionsForUpdatesRequest request;
      hla.rti1516_202X.thin.AssociateRegionsForUpdatesRequest. Builder builder = hla.rti1516_202X.thin.AssociateRegionsForUpdatesRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setAssociateRegionsForUpdatesRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasAssociateRegionsForUpdatesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ASSOCIATEREGIONSFORUPDATESRESPONSE));
         }
         return null;
      });
   }

   public void
unassociateRegionsForUpdates(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions )
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
      try {
         CompletableFuture<Void> future =
            asyncUnassociateRegionsForUpdates(
               theObject,
               attributesAndRegions );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUnassociateRegionsForUpdates(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions )
   {
      hla.rti1516_202X.thin.UnassociateRegionsForUpdatesRequest request;
      hla.rti1516_202X.thin.UnassociateRegionsForUpdatesRequest. Builder builder = hla.rti1516_202X.thin.UnassociateRegionsForUpdatesRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnassociateRegionsForUpdatesRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnassociateRegionsForUpdatesResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNASSOCIATEREGIONSFORUPDATESRESPONSE));
         }
         return null;
      });
   }

   public void
subscribeObjectClassAttributesWithRegions(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions,
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
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassAttributesWithRegions(
               theClass,
               attributesAndRegions,
               active );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSubscribeObjectClassAttributesWithRegions(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions,
   boolean active )
   {
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRegionsRequest request;
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRegionsRequest. Builder builder = hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRegionsRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
         builder.setActive(_clientConverter.convertFromHla(active));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesWithRegionsRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassAttributesWithRegionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSATTRIBUTESWITHREGIONSRESPONSE));
         }
         return null;
      });
   }

   public void
subscribeObjectClassAttributesWithRegionsAndRate(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions,
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
      try {
         CompletableFuture<Void> future =
            asyncSubscribeObjectClassAttributesWithRegionsAndRate(
               theClass,
               attributesAndRegions,
               active,
               updateRateDesignator );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSubscribeObjectClassAttributesWithRegionsAndRate(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions,
   boolean active,
   java.lang.String updateRateDesignator )
   {
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRegionsAndRateRequest request;
      hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRegionsAndRateRequest. Builder builder = hla.rti1516_202X.thin.SubscribeObjectClassAttributesWithRegionsAndRateRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
         builder.setActive(_clientConverter.convertFromHla(active));
         builder.setUpdateRateDesignator(_clientConverter.convertFromHla(updateRateDesignator));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeObjectClassAttributesWithRegionsAndRateRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeObjectClassAttributesWithRegionsAndRateResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEOBJECTCLASSATTRIBUTESWITHREGIONSANDRATERESPONSE));
         }
         return null;
      });
   }

   public void
unsubscribeObjectClassAttributesWithRegions(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions )
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
      try {
         CompletableFuture<Void> future =
            asyncUnsubscribeObjectClassAttributesWithRegions(
               theClass,
               attributesAndRegions );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUnsubscribeObjectClassAttributesWithRegions(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions )
   {
      hla.rti1516_202X.thin.UnsubscribeObjectClassAttributesWithRegionsRequest request;
      hla.rti1516_202X.thin.UnsubscribeObjectClassAttributesWithRegionsRequest. Builder builder = hla.rti1516_202X.thin.UnsubscribeObjectClassAttributesWithRegionsRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeObjectClassAttributesWithRegionsRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnsubscribeObjectClassAttributesWithRegionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNSUBSCRIBEOBJECTCLASSATTRIBUTESWITHREGIONSRESPONSE));
         }
         return null;
      });
   }

   public void
subscribeInteractionClassWithRegions(
   hla.rti1516_202X.InteractionClassHandle theClass,
   boolean active,
   hla.rti1516_202X.RegionHandleSet regions )
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
      try {
         CompletableFuture<Void> future =
            asyncSubscribeInteractionClassWithRegions(
               theClass,
               active,
               regions );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSubscribeInteractionClassWithRegions(
   hla.rti1516_202X.InteractionClassHandle theClass,
   boolean active,
   hla.rti1516_202X.RegionHandleSet regions )
   {
      hla.rti1516_202X.thin.SubscribeInteractionClassWithRegionsRequest request;
      hla.rti1516_202X.thin.SubscribeInteractionClassWithRegionsRequest. Builder builder = hla.rti1516_202X.thin.SubscribeInteractionClassWithRegionsRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setActive(_clientConverter.convertFromHla(active));
         builder.setRegions(_clientConverter.convertFromHla(regions));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSubscribeInteractionClassWithRegionsRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSubscribeInteractionClassWithRegionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SUBSCRIBEINTERACTIONCLASSWITHREGIONSRESPONSE));
         }
         return null;
      });
   }

   public void
unsubscribeInteractionClassWithRegions(
   hla.rti1516_202X.InteractionClassHandle theClass,
   hla.rti1516_202X.RegionHandleSet regions )
   throws
      RegionNotCreatedByThisFederate,
      InvalidRegion,
      InteractionClassNotDefined,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncUnsubscribeInteractionClassWithRegions(
               theClass,
               regions );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncUnsubscribeInteractionClassWithRegions(
   hla.rti1516_202X.InteractionClassHandle theClass,
   hla.rti1516_202X.RegionHandleSet regions )
   {
      hla.rti1516_202X.thin.UnsubscribeInteractionClassWithRegionsRequest request;
      hla.rti1516_202X.thin.UnsubscribeInteractionClassWithRegionsRequest. Builder builder = hla.rti1516_202X.thin.UnsubscribeInteractionClassWithRegionsRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setRegions(_clientConverter.convertFromHla(regions));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setUnsubscribeInteractionClassWithRegionsRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasUnsubscribeInteractionClassWithRegionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.UNSUBSCRIBEINTERACTIONCLASSWITHREGIONSRESPONSE));
         }
         return null;
      });
   }

   public void
sendInteractionWithRegions(
   hla.rti1516_202X.InteractionClassHandle theInteraction,
   hla.rti1516_202X.ParameterHandleValueMap theParameters,
   hla.rti1516_202X.RegionHandleSet regions,
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
      try {
         CompletableFuture<Void> future =
            asyncSendInteractionWithRegions(
               theInteraction,
               theParameters,
               regions,
               userSuppliedTag );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSendInteractionWithRegions(
   hla.rti1516_202X.InteractionClassHandle theInteraction,
   hla.rti1516_202X.ParameterHandleValueMap theParameters,
   hla.rti1516_202X.RegionHandleSet regions,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.SendInteractionWithRegionsRequest request;
      hla.rti1516_202X.thin.SendInteractionWithRegionsRequest. Builder builder = hla.rti1516_202X.thin.SendInteractionWithRegionsRequest. newBuilder();

      try {
         builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));
         builder.setTheParameters(_clientConverter.convertFromHla(theParameters));
         builder.setRegions(_clientConverter.convertFromHla(regions));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSendInteractionWithRegionsRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSendInteractionWithRegionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SENDINTERACTIONWITHREGIONSRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_202X.MessageRetractionReturn
sendInteractionWithRegionsAndTime(
   hla.rti1516_202X.InteractionClassHandle theInteraction,
   hla.rti1516_202X.ParameterHandleValueMap theParameters,
   hla.rti1516_202X.RegionHandleSet regions,
   byte[] userSuppliedTag,
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
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
      try {
         CompletableFuture<MessageRetractionReturn> future =
            asyncSendInteractionWithRegionsAndTime(
               theInteraction,
               theParameters,
               regions,
               userSuppliedTag,
               theTime );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<MessageRetractionReturn>
asyncSendInteractionWithRegionsAndTime(
   hla.rti1516_202X.InteractionClassHandle theInteraction,
   hla.rti1516_202X.ParameterHandleValueMap theParameters,
   hla.rti1516_202X.RegionHandleSet regions,
   byte[] userSuppliedTag,
   hla.rti1516_202X.LogicalTime<?, ?> theTime )
   {
      hla.rti1516_202X.thin.SendInteractionWithRegionsAndTimeRequest request;
      hla.rti1516_202X.thin.SendInteractionWithRegionsAndTimeRequest. Builder builder = hla.rti1516_202X.thin.SendInteractionWithRegionsAndTimeRequest. newBuilder();

      try {
         builder.setTheInteraction(_clientConverter.convertFromHla(theInteraction));
         builder.setTheParameters(_clientConverter.convertFromHla(theParameters));
         builder.setRegions(_clientConverter.convertFromHla(regions));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
         builder.setTheTime(_clientConverter.convertFromHla(theTime));
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
            hla.rti1516_202X.thin.SendInteractionWithRegionsAndTimeResponse response = callResponse.getSendInteractionWithRegionsAndTimeResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void
requestAttributeValueUpdateWithRegions(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions,
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
      try {
         CompletableFuture<Void> future =
            asyncRequestAttributeValueUpdateWithRegions(
               theClass,
               attributesAndRegions,
               userSuppliedTag );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncRequestAttributeValueUpdateWithRegions(
   hla.rti1516_202X.ObjectClassHandle theClass,
   hla.rti1516_202X.AttributeSetRegionSetPairList attributesAndRegions,
   byte[] userSuppliedTag )
   {
      hla.rti1516_202X.thin.RequestAttributeValueUpdateWithRegionsRequest request;
      hla.rti1516_202X.thin.RequestAttributeValueUpdateWithRegionsRequest. Builder builder = hla.rti1516_202X.thin.RequestAttributeValueUpdateWithRegionsRequest. newBuilder();

      try {
         builder.setTheClass(_clientConverter.convertFromHla(theClass));
         builder.setAttributesAndRegions(_clientConverter.convertFromHla(attributesAndRegions));
         builder.setUserSuppliedTag(_clientConverter.convertFromHla(userSuppliedTag));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setRequestAttributeValueUpdateWithRegionsRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasRequestAttributeValueUpdateWithRegionsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.REQUESTATTRIBUTEVALUEUPDATEWITHREGIONSRESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_202X.ResignAction
getAutomaticResignDirective(
 )
   throws
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
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

      public CompletableFuture<ResignAction>
asyncGetAutomaticResignDirective(
 )
   {
      hla.rti1516_202X.thin.GetAutomaticResignDirectiveRequest request;
      request = hla.rti1516_202X.thin.GetAutomaticResignDirectiveRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setGetAutomaticResignDirectiveRequest(request).build();
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetAutomaticResignDirectiveResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETAUTOMATICRESIGNDIRECTIVERESPONSE);
            }
            hla.rti1516_202X.thin.GetAutomaticResignDirectiveResponse response = callResponse.getGetAutomaticResignDirectiveResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void
setAutomaticResignDirective(
   hla.rti1516_202X.ResignAction resignAction )
   throws
      InvalidResignAction,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Void> future =
            asyncSetAutomaticResignDirective(
               resignAction );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSetAutomaticResignDirective(
   hla.rti1516_202X.ResignAction resignAction )
   {
      hla.rti1516_202X.thin.SetAutomaticResignDirectiveRequest request;
      hla.rti1516_202X.thin.SetAutomaticResignDirectiveRequest. Builder builder = hla.rti1516_202X.thin.SetAutomaticResignDirectiveRequest. newBuilder();

      try {
         builder.setResignAction(_clientConverter.convertFromHla(resignAction));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetAutomaticResignDirectiveRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSetAutomaticResignDirectiveResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SETAUTOMATICRESIGNDIRECTIVERESPONSE));
         }
         return null;
      });
   }

   public hla.rti1516_202X.FederateHandle
getFederateHandle(
   java.lang.String theName )
   throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<FederateHandle> future =
            asyncGetFederateHandle(
               theName );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<FederateHandle>
asyncGetFederateHandle(
   java.lang.String theName )
   {
      hla.rti1516_202X.thin.GetFederateHandleRequest request;
      hla.rti1516_202X.thin.GetFederateHandleRequest. Builder builder = hla.rti1516_202X.thin.GetFederateHandleRequest. newBuilder();

      try {
         builder.setTheName(_clientConverter.convertFromHla(theName));
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
            hla.rti1516_202X.thin.GetFederateHandleResponse response = callResponse.getGetFederateHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String
getFederateName(
   hla.rti1516_202X.FederateHandle theHandle )
   throws
      InvalidFederateHandle,
      FederateHandleNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<String> future =
            asyncGetFederateName(
               theHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<String>
asyncGetFederateName(
   hla.rti1516_202X.FederateHandle theHandle )
   {
      hla.rti1516_202X.thin.GetFederateNameRequest request;
      hla.rti1516_202X.thin.GetFederateNameRequest. Builder builder = hla.rti1516_202X.thin.GetFederateNameRequest. newBuilder();

      try {
         builder.setTheHandle(_clientConverter.convertFromHla(theHandle));
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
            hla.rti1516_202X.thin.GetFederateNameResponse response = callResponse.getGetFederateNameResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.ObjectClassHandle
getObjectClassHandle(
   java.lang.String theName )
   throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<ObjectClassHandle> future =
            asyncGetObjectClassHandle(
               theName );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<ObjectClassHandle>
asyncGetObjectClassHandle(
   java.lang.String theName )
   {
      hla.rti1516_202X.thin.GetObjectClassHandleRequest request;
      hla.rti1516_202X.thin.GetObjectClassHandleRequest. Builder builder = hla.rti1516_202X.thin.GetObjectClassHandleRequest. newBuilder();

      try {
         builder.setTheName(_clientConverter.convertFromHla(theName));
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
            hla.rti1516_202X.thin.GetObjectClassHandleResponse response = callResponse.getGetObjectClassHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String
getObjectClassName(
   hla.rti1516_202X.ObjectClassHandle theHandle )
   throws
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<String> future =
            asyncGetObjectClassName(
               theHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<String>
asyncGetObjectClassName(
   hla.rti1516_202X.ObjectClassHandle theHandle )
   {
      hla.rti1516_202X.thin.GetObjectClassNameRequest request;
      hla.rti1516_202X.thin.GetObjectClassNameRequest. Builder builder = hla.rti1516_202X.thin.GetObjectClassNameRequest. newBuilder();

      try {
         builder.setTheHandle(_clientConverter.convertFromHla(theHandle));
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
            hla.rti1516_202X.thin.GetObjectClassNameResponse response = callResponse.getGetObjectClassNameResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.ObjectClassHandle
getKnownObjectClassHandle(
   hla.rti1516_202X.ObjectInstanceHandle theObject )
   throws
      ObjectInstanceNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<ObjectClassHandle> future =
            asyncGetKnownObjectClassHandle(
               theObject );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<ObjectClassHandle>
asyncGetKnownObjectClassHandle(
   hla.rti1516_202X.ObjectInstanceHandle theObject )
   {
      hla.rti1516_202X.thin.GetKnownObjectClassHandleRequest request;
      hla.rti1516_202X.thin.GetKnownObjectClassHandleRequest. Builder builder = hla.rti1516_202X.thin.GetKnownObjectClassHandleRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
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
            hla.rti1516_202X.thin.GetKnownObjectClassHandleResponse response = callResponse.getGetKnownObjectClassHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.ObjectInstanceHandle
getObjectInstanceHandle(
   java.lang.String theName )
   throws
      ObjectInstanceNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<ObjectInstanceHandle> future =
            asyncGetObjectInstanceHandle(
               theName );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<ObjectInstanceHandle>
asyncGetObjectInstanceHandle(
   java.lang.String theName )
   {
      hla.rti1516_202X.thin.GetObjectInstanceHandleRequest request;
      hla.rti1516_202X.thin.GetObjectInstanceHandleRequest. Builder builder = hla.rti1516_202X.thin.GetObjectInstanceHandleRequest. newBuilder();

      try {
         builder.setTheName(_clientConverter.convertFromHla(theName));
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
            hla.rti1516_202X.thin.GetObjectInstanceHandleResponse response = callResponse.getGetObjectInstanceHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String
getObjectInstanceName(
   hla.rti1516_202X.ObjectInstanceHandle theHandle )
   throws
      ObjectInstanceNotKnown,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<String> future =
            asyncGetObjectInstanceName(
               theHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<String>
asyncGetObjectInstanceName(
   hla.rti1516_202X.ObjectInstanceHandle theHandle )
   {
      hla.rti1516_202X.thin.GetObjectInstanceNameRequest request;
      hla.rti1516_202X.thin.GetObjectInstanceNameRequest. Builder builder = hla.rti1516_202X.thin.GetObjectInstanceNameRequest. newBuilder();

      try {
         builder.setTheHandle(_clientConverter.convertFromHla(theHandle));
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
            hla.rti1516_202X.thin.GetObjectInstanceNameResponse response = callResponse.getGetObjectInstanceNameResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.AttributeHandle
getAttributeHandle(
   hla.rti1516_202X.ObjectClassHandle whichClass,
   java.lang.String theName )
   throws
      NameNotFound,
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<AttributeHandle> future =
            asyncGetAttributeHandle(
               whichClass,
               theName );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<AttributeHandle>
asyncGetAttributeHandle(
   hla.rti1516_202X.ObjectClassHandle whichClass,
   java.lang.String theName )
   {
      hla.rti1516_202X.thin.GetAttributeHandleRequest request;
      hla.rti1516_202X.thin.GetAttributeHandleRequest. Builder builder = hla.rti1516_202X.thin.GetAttributeHandleRequest. newBuilder();

      try {
         builder.setWhichClass(_clientConverter.convertFromHla(whichClass));
         builder.setTheName(_clientConverter.convertFromHla(theName));
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
            hla.rti1516_202X.thin.GetAttributeHandleResponse response = callResponse.getGetAttributeHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String
getAttributeName(
   hla.rti1516_202X.ObjectClassHandle whichClass,
   hla.rti1516_202X.AttributeHandle theHandle )
   throws
      AttributeNotDefined,
      InvalidAttributeHandle,
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<String> future =
            asyncGetAttributeName(
               whichClass,
               theHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<String>
asyncGetAttributeName(
   hla.rti1516_202X.ObjectClassHandle whichClass,
   hla.rti1516_202X.AttributeHandle theHandle )
   {
      hla.rti1516_202X.thin.GetAttributeNameRequest request;
      hla.rti1516_202X.thin.GetAttributeNameRequest. Builder builder = hla.rti1516_202X.thin.GetAttributeNameRequest. newBuilder();

      try {
         builder.setWhichClass(_clientConverter.convertFromHla(whichClass));
         builder.setTheHandle(_clientConverter.convertFromHla(theHandle));
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
            hla.rti1516_202X.thin.GetAttributeNameResponse response = callResponse.getGetAttributeNameResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public double
getUpdateRateValue(
   java.lang.String updateRateDesignator )
   throws
      InvalidUpdateRateDesignator,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Double> future =
            asyncGetUpdateRateValue(
               updateRateDesignator );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Double>
asyncGetUpdateRateValue(
   java.lang.String updateRateDesignator )
   {
      hla.rti1516_202X.thin.GetUpdateRateValueRequest request;
      hla.rti1516_202X.thin.GetUpdateRateValueRequest. Builder builder = hla.rti1516_202X.thin.GetUpdateRateValueRequest. newBuilder();

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
            hla.rti1516_202X.thin.GetUpdateRateValueResponse response = callResponse.getGetUpdateRateValueResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public double
getUpdateRateValueForAttribute(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandle theAttribute )
   throws
      ObjectInstanceNotKnown,
      AttributeNotDefined,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Double> future =
            asyncGetUpdateRateValueForAttribute(
               theObject,
               theAttribute );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Double>
asyncGetUpdateRateValueForAttribute(
   hla.rti1516_202X.ObjectInstanceHandle theObject,
   hla.rti1516_202X.AttributeHandle theAttribute )
   {
      hla.rti1516_202X.thin.GetUpdateRateValueForAttributeRequest request;
      hla.rti1516_202X.thin.GetUpdateRateValueForAttributeRequest. Builder builder = hla.rti1516_202X.thin.GetUpdateRateValueForAttributeRequest. newBuilder();

      try {
         builder.setTheObject(_clientConverter.convertFromHla(theObject));
         builder.setTheAttribute(_clientConverter.convertFromHla(theAttribute));
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
            hla.rti1516_202X.thin.GetUpdateRateValueForAttributeResponse response = callResponse.getGetUpdateRateValueForAttributeResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.InteractionClassHandle
getInteractionClassHandle(
   java.lang.String theName )
   throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<InteractionClassHandle> future =
            asyncGetInteractionClassHandle(
               theName );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<InteractionClassHandle>
asyncGetInteractionClassHandle(
   java.lang.String theName )
   {
      hla.rti1516_202X.thin.GetInteractionClassHandleRequest request;
      hla.rti1516_202X.thin.GetInteractionClassHandleRequest. Builder builder = hla.rti1516_202X.thin.GetInteractionClassHandleRequest. newBuilder();

      try {
         builder.setTheName(_clientConverter.convertFromHla(theName));
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
            hla.rti1516_202X.thin.GetInteractionClassHandleResponse response = callResponse.getGetInteractionClassHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String
getInteractionClassName(
   hla.rti1516_202X.InteractionClassHandle theHandle )
   throws
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<String> future =
            asyncGetInteractionClassName(
               theHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<String>
asyncGetInteractionClassName(
   hla.rti1516_202X.InteractionClassHandle theHandle )
   {
      hla.rti1516_202X.thin.GetInteractionClassNameRequest request;
      hla.rti1516_202X.thin.GetInteractionClassNameRequest. Builder builder = hla.rti1516_202X.thin.GetInteractionClassNameRequest. newBuilder();

      try {
         builder.setTheHandle(_clientConverter.convertFromHla(theHandle));
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
            hla.rti1516_202X.thin.GetInteractionClassNameResponse response = callResponse.getGetInteractionClassNameResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.ParameterHandle
getParameterHandle(
   hla.rti1516_202X.InteractionClassHandle whichClass,
   java.lang.String theName )
   throws
      NameNotFound,
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<ParameterHandle> future =
            asyncGetParameterHandle(
               whichClass,
               theName );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<ParameterHandle>
asyncGetParameterHandle(
   hla.rti1516_202X.InteractionClassHandle whichClass,
   java.lang.String theName )
   {
      hla.rti1516_202X.thin.GetParameterHandleRequest request;
      hla.rti1516_202X.thin.GetParameterHandleRequest. Builder builder = hla.rti1516_202X.thin.GetParameterHandleRequest. newBuilder();

      try {
         builder.setWhichClass(_clientConverter.convertFromHla(whichClass));
         builder.setTheName(_clientConverter.convertFromHla(theName));
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
            hla.rti1516_202X.thin.GetParameterHandleResponse response = callResponse.getGetParameterHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String
getParameterName(
   hla.rti1516_202X.InteractionClassHandle whichClass,
   hla.rti1516_202X.ParameterHandle theHandle )
   throws
      InteractionParameterNotDefined,
      InvalidParameterHandle,
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<String> future =
            asyncGetParameterName(
               whichClass,
               theHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<String>
asyncGetParameterName(
   hla.rti1516_202X.InteractionClassHandle whichClass,
   hla.rti1516_202X.ParameterHandle theHandle )
   {
      hla.rti1516_202X.thin.GetParameterNameRequest request;
      hla.rti1516_202X.thin.GetParameterNameRequest. Builder builder = hla.rti1516_202X.thin.GetParameterNameRequest. newBuilder();

      try {
         builder.setWhichClass(_clientConverter.convertFromHla(whichClass));
         builder.setTheHandle(_clientConverter.convertFromHla(theHandle));
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
            hla.rti1516_202X.thin.GetParameterNameResponse response = callResponse.getGetParameterNameResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.OrderType
getOrderType(
   java.lang.String theName )
   throws
      InvalidOrderName,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<OrderType> future =
            asyncGetOrderType(
               theName );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<OrderType>
asyncGetOrderType(
   java.lang.String theName )
   {
      hla.rti1516_202X.thin.GetOrderTypeRequest request;
      hla.rti1516_202X.thin.GetOrderTypeRequest. Builder builder = hla.rti1516_202X.thin.GetOrderTypeRequest. newBuilder();

      try {
         builder.setTheName(_clientConverter.convertFromHla(theName));
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
            hla.rti1516_202X.thin.GetOrderTypeResponse response = callResponse.getGetOrderTypeResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String
getOrderName(
   hla.rti1516_202X.OrderType theType )
   throws
      InvalidOrderType,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<String> future =
            asyncGetOrderName(
               theType );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<String>
asyncGetOrderName(
   hla.rti1516_202X.OrderType theType )
   {
      hla.rti1516_202X.thin.GetOrderNameRequest request;
      hla.rti1516_202X.thin.GetOrderNameRequest. Builder builder = hla.rti1516_202X.thin.GetOrderNameRequest. newBuilder();

      try {
         builder.setTheType(_clientConverter.convertFromHla(theType));
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
            hla.rti1516_202X.thin.GetOrderNameResponse response = callResponse.getGetOrderNameResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.TransportationTypeHandle
getTransportationTypeHandle(
   java.lang.String theName )
   throws
      InvalidTransportationName,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<TransportationTypeHandle> future =
            asyncGetTransportationTypeHandle(
               theName );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<TransportationTypeHandle>
asyncGetTransportationTypeHandle(
   java.lang.String theName )
   {
      hla.rti1516_202X.thin.GetTransportationTypeHandleRequest request;
      hla.rti1516_202X.thin.GetTransportationTypeHandleRequest. Builder builder = hla.rti1516_202X.thin.GetTransportationTypeHandleRequest. newBuilder();

      try {
         builder.setTheName(_clientConverter.convertFromHla(theName));
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
            hla.rti1516_202X.thin.GetTransportationTypeHandleResponse response = callResponse.getGetTransportationTypeHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String
getTransportationTypeName(
   hla.rti1516_202X.TransportationTypeHandle theHandle )
   throws
      InvalidTransportationTypeHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<String> future =
            asyncGetTransportationTypeName(
               theHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<String>
asyncGetTransportationTypeName(
   hla.rti1516_202X.TransportationTypeHandle theHandle )
   {
      hla.rti1516_202X.thin.GetTransportationTypeNameRequest request;
      hla.rti1516_202X.thin.GetTransportationTypeNameRequest. Builder builder = hla.rti1516_202X.thin.GetTransportationTypeNameRequest. newBuilder();

      try {
         builder.setTheHandle(_clientConverter.convertFromHla(theHandle));
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
            hla.rti1516_202X.thin.GetTransportationTypeNameResponse response = callResponse.getGetTransportationTypeNameResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.DimensionHandleSet
getAvailableDimensionsForClassAttribute(
   hla.rti1516_202X.ObjectClassHandle whichClass,
   hla.rti1516_202X.AttributeHandle theHandle )
   throws
      AttributeNotDefined,
      InvalidAttributeHandle,
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<DimensionHandleSet> future =
            asyncGetAvailableDimensionsForClassAttribute(
               whichClass,
               theHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<DimensionHandleSet>
asyncGetAvailableDimensionsForClassAttribute(
   hla.rti1516_202X.ObjectClassHandle whichClass,
   hla.rti1516_202X.AttributeHandle theHandle )
   {
      hla.rti1516_202X.thin.GetAvailableDimensionsForClassAttributeRequest request;
      hla.rti1516_202X.thin.GetAvailableDimensionsForClassAttributeRequest. Builder builder = hla.rti1516_202X.thin.GetAvailableDimensionsForClassAttributeRequest. newBuilder();

      try {
         builder.setWhichClass(_clientConverter.convertFromHla(whichClass));
         builder.setTheHandle(_clientConverter.convertFromHla(theHandle));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setGetAvailableDimensionsForClassAttributeRequest(request).build();
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         try {
            if (!callResponse.hasGetAvailableDimensionsForClassAttributeResponse()) {
               throw new RTIinternalError("Mismatched response message. " +
                  "Got: " + callResponse.getCallResponseCase() + ", " +
                  "expected " + CallResponse.CallResponseCase.GETAVAILABLEDIMENSIONSFORCLASSATTRIBUTERESPONSE);
            }
            hla.rti1516_202X.thin.GetAvailableDimensionsForClassAttributeResponse response = callResponse.getGetAvailableDimensionsForClassAttributeResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.DimensionHandleSet
getAvailableDimensionsForInteractionClass(
   hla.rti1516_202X.InteractionClassHandle theHandle )
   throws
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<DimensionHandleSet> future =
            asyncGetAvailableDimensionsForInteractionClass(
               theHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<DimensionHandleSet>
asyncGetAvailableDimensionsForInteractionClass(
   hla.rti1516_202X.InteractionClassHandle theHandle )
   {
      hla.rti1516_202X.thin.GetAvailableDimensionsForInteractionClassRequest request;
      hla.rti1516_202X.thin.GetAvailableDimensionsForInteractionClassRequest. Builder builder = hla.rti1516_202X.thin.GetAvailableDimensionsForInteractionClassRequest. newBuilder();

      try {
         builder.setTheHandle(_clientConverter.convertFromHla(theHandle));
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
            hla.rti1516_202X.thin.GetAvailableDimensionsForInteractionClassResponse response = callResponse.getGetAvailableDimensionsForInteractionClassResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.DimensionHandle
getDimensionHandle(
   java.lang.String theName )
   throws
      NameNotFound,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<DimensionHandle> future =
            asyncGetDimensionHandle(
               theName );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<DimensionHandle>
asyncGetDimensionHandle(
   java.lang.String theName )
   {
      hla.rti1516_202X.thin.GetDimensionHandleRequest request;
      hla.rti1516_202X.thin.GetDimensionHandleRequest. Builder builder = hla.rti1516_202X.thin.GetDimensionHandleRequest. newBuilder();

      try {
         builder.setTheName(_clientConverter.convertFromHla(theName));
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
            hla.rti1516_202X.thin.GetDimensionHandleResponse response = callResponse.getGetDimensionHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public java.lang.String
getDimensionName(
   hla.rti1516_202X.DimensionHandle theHandle )
   throws
      InvalidDimensionHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<String> future =
            asyncGetDimensionName(
               theHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<String>
asyncGetDimensionName(
   hla.rti1516_202X.DimensionHandle theHandle )
   {
      hla.rti1516_202X.thin.GetDimensionNameRequest request;
      hla.rti1516_202X.thin.GetDimensionNameRequest. Builder builder = hla.rti1516_202X.thin.GetDimensionNameRequest. newBuilder();

      try {
         builder.setTheHandle(_clientConverter.convertFromHla(theHandle));
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
            hla.rti1516_202X.thin.GetDimensionNameResponse response = callResponse.getGetDimensionNameResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public long
getDimensionUpperBound(
   hla.rti1516_202X.DimensionHandle theHandle )
   throws
      InvalidDimensionHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Long> future =
            asyncGetDimensionUpperBound(
               theHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Long>
asyncGetDimensionUpperBound(
   hla.rti1516_202X.DimensionHandle theHandle )
   {
      hla.rti1516_202X.thin.GetDimensionUpperBoundRequest request;
      hla.rti1516_202X.thin.GetDimensionUpperBoundRequest. Builder builder = hla.rti1516_202X.thin.GetDimensionUpperBoundRequest. newBuilder();

      try {
         builder.setTheHandle(_clientConverter.convertFromHla(theHandle));
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
            hla.rti1516_202X.thin.GetDimensionUpperBoundResponse response = callResponse.getGetDimensionUpperBoundResponse();
            return _clientConverter.convertToHla(Integer.toUnsignedLong(response.getResult()));
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.DimensionHandleSet
getDimensionHandleSet(
   hla.rti1516_202X.RegionHandle region )
   throws
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<DimensionHandleSet> future =
            asyncGetDimensionHandleSet(
               region );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<DimensionHandleSet>
asyncGetDimensionHandleSet(
   hla.rti1516_202X.RegionHandle region )
   {
      hla.rti1516_202X.thin.GetDimensionHandleSetRequest request;
      hla.rti1516_202X.thin.GetDimensionHandleSetRequest. Builder builder = hla.rti1516_202X.thin.GetDimensionHandleSetRequest. newBuilder();

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
            hla.rti1516_202X.thin.GetDimensionHandleSetResponse response = callResponse.getGetDimensionHandleSetResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public hla.rti1516_202X.RangeBounds
getRangeBounds(
   hla.rti1516_202X.RegionHandle region,
   hla.rti1516_202X.DimensionHandle dimension )
   throws
      RegionDoesNotContainSpecifiedDimension,
      InvalidRegion,
      SaveInProgress,
      RestoreInProgress,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<RangeBounds> future =
            asyncGetRangeBounds(
               region,
               dimension );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<RangeBounds>
asyncGetRangeBounds(
   hla.rti1516_202X.RegionHandle region,
   hla.rti1516_202X.DimensionHandle dimension )
   {
      hla.rti1516_202X.thin.GetRangeBoundsRequest request;
      hla.rti1516_202X.thin.GetRangeBoundsRequest. Builder builder = hla.rti1516_202X.thin.GetRangeBoundsRequest. newBuilder();

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
            hla.rti1516_202X.thin.GetRangeBoundsResponse response = callResponse.getGetRangeBoundsResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public void
setRangeBounds(
   hla.rti1516_202X.RegionHandle region,
   hla.rti1516_202X.DimensionHandle dimension,
   hla.rti1516_202X.RangeBounds bounds )
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
      try {
         CompletableFuture<Void> future =
            asyncSetRangeBounds(
               region,
               dimension,
               bounds );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncSetRangeBounds(
   hla.rti1516_202X.RegionHandle region,
   hla.rti1516_202X.DimensionHandle dimension,
   hla.rti1516_202X.RangeBounds bounds )
   {
      hla.rti1516_202X.thin.SetRangeBoundsRequest request;
      hla.rti1516_202X.thin.SetRangeBoundsRequest. Builder builder = hla.rti1516_202X.thin.SetRangeBoundsRequest. newBuilder();

      try {
         builder.setRegion(_clientConverter.convertFromHla(region));
         builder.setDimension(_clientConverter.convertFromHla(dimension));
         builder.setBounds(_clientConverter.convertFromHla(bounds));
      } catch (Exception e) {
         return CompletableFuture.failedFuture(e);
      }

      request = builder.build();
      CallRequest callRequest = CallRequest.newBuilder().setSetRangeBoundsRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasSetRangeBoundsResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.SETRANGEBOUNDSRESPONSE));
         }
         return null;
      });
   }

   public long
normalizeServiceGroup(
   hla.rti1516_202X.ServiceGroup group )
   throws
      InvalidServiceGroup,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Long> future =
            asyncNormalizeServiceGroup(
               group );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Long>
asyncNormalizeServiceGroup(
   hla.rti1516_202X.ServiceGroup group )
   {
      hla.rti1516_202X.thin.NormalizeServiceGroupRequest request;
      hla.rti1516_202X.thin.NormalizeServiceGroupRequest. Builder builder = hla.rti1516_202X.thin.NormalizeServiceGroupRequest. newBuilder();

      try {
         builder.setGroup(_clientConverter.convertFromHla(group));
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
            hla.rti1516_202X.thin.NormalizeServiceGroupResponse response = callResponse.getNormalizeServiceGroupResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public long
normalizeFederateHandle(
   hla.rti1516_202X.FederateHandle federateHandle )
   throws
      InvalidFederateHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Long> future =
            asyncNormalizeFederateHandle(
               federateHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Long>
asyncNormalizeFederateHandle(
   hla.rti1516_202X.FederateHandle federateHandle )
   {
      hla.rti1516_202X.thin.NormalizeFederateHandleRequest request;
      hla.rti1516_202X.thin.NormalizeFederateHandleRequest. Builder builder = hla.rti1516_202X.thin.NormalizeFederateHandleRequest. newBuilder();

      try {
         builder.setFederateHandle(_clientConverter.convertFromHla(federateHandle));
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
            hla.rti1516_202X.thin.NormalizeFederateHandleResponse response = callResponse.getNormalizeFederateHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public long
normalizeObjectClassHandle(
   hla.rti1516_202X.ObjectClassHandle objectClassHandle )
   throws
      InvalidObjectClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Long> future =
            asyncNormalizeObjectClassHandle(
               objectClassHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Long>
asyncNormalizeObjectClassHandle(
   hla.rti1516_202X.ObjectClassHandle objectClassHandle )
   {
      hla.rti1516_202X.thin.NormalizeObjectClassHandleRequest request;
      hla.rti1516_202X.thin.NormalizeObjectClassHandleRequest. Builder builder = hla.rti1516_202X.thin.NormalizeObjectClassHandleRequest. newBuilder();

      try {
         builder.setObjectClassHandle(_clientConverter.convertFromHla(objectClassHandle));
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
            hla.rti1516_202X.thin.NormalizeObjectClassHandleResponse response = callResponse.getNormalizeObjectClassHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public long
normalizeInteractionClassHandle(
   hla.rti1516_202X.InteractionClassHandle interactionClassHandle )
   throws
      InvalidInteractionClassHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Long> future =
            asyncNormalizeInteractionClassHandle(
               interactionClassHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Long>
asyncNormalizeInteractionClassHandle(
   hla.rti1516_202X.InteractionClassHandle interactionClassHandle )
   {
      hla.rti1516_202X.thin.NormalizeInteractionClassHandleRequest request;
      hla.rti1516_202X.thin.NormalizeInteractionClassHandleRequest. Builder builder = hla.rti1516_202X.thin.NormalizeInteractionClassHandleRequest. newBuilder();

      try {
         builder.setInteractionClassHandle(_clientConverter.convertFromHla(interactionClassHandle));
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
            hla.rti1516_202X.thin.NormalizeInteractionClassHandleResponse response = callResponse.getNormalizeInteractionClassHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
   }

   public long
normalizeObjectInstanceHandle(
   hla.rti1516_202X.ObjectInstanceHandle objectInstanceHandle )
   throws
      InvalidObjectInstanceHandle,
      FederateNotExecutionMember,
      NotConnected,
      RTIinternalError    {
      try {
         CompletableFuture<Long> future =
            asyncNormalizeObjectInstanceHandle(
               objectInstanceHandle );
         return future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Long>
asyncNormalizeObjectInstanceHandle(
   hla.rti1516_202X.ObjectInstanceHandle objectInstanceHandle )
   {
      hla.rti1516_202X.thin.NormalizeObjectInstanceHandleRequest request;
      hla.rti1516_202X.thin.NormalizeObjectInstanceHandleRequest. Builder builder = hla.rti1516_202X.thin.NormalizeObjectInstanceHandleRequest. newBuilder();

      try {
         builder.setObjectInstanceHandle(_clientConverter.convertFromHla(objectInstanceHandle));
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
            hla.rti1516_202X.thin.NormalizeObjectInstanceHandleResponse response = callResponse.getNormalizeObjectInstanceHandleResponse();
            return _clientConverter.convertToHla(response.getResult());
         } catch (Exception e) {
            throw new CompletionException(asRuntimeOrRtiException(e));
         }
      });
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
      try {
         CompletableFuture<Void> future =
            asyncEnableObjectClassRelevanceAdvisorySwitch(
 );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncEnableObjectClassRelevanceAdvisorySwitch(
 )
   {
      hla.rti1516_202X.thin.EnableObjectClassRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.EnableObjectClassRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setEnableObjectClassRelevanceAdvisorySwitchRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasEnableObjectClassRelevanceAdvisorySwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ENABLEOBJECTCLASSRELEVANCEADVISORYSWITCHRESPONSE));
         }
         return null;
      });
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
      try {
         CompletableFuture<Void> future =
            asyncDisableObjectClassRelevanceAdvisorySwitch(
 );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncDisableObjectClassRelevanceAdvisorySwitch(
 )
   {
      hla.rti1516_202X.thin.DisableObjectClassRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.DisableObjectClassRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableObjectClassRelevanceAdvisorySwitchRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasDisableObjectClassRelevanceAdvisorySwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.DISABLEOBJECTCLASSRELEVANCEADVISORYSWITCHRESPONSE));
         }
         return null;
      });
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
      try {
         CompletableFuture<Void> future =
            asyncEnableAttributeRelevanceAdvisorySwitch(
 );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncEnableAttributeRelevanceAdvisorySwitch(
 )
   {
      hla.rti1516_202X.thin.EnableAttributeRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.EnableAttributeRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setEnableAttributeRelevanceAdvisorySwitchRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasEnableAttributeRelevanceAdvisorySwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ENABLEATTRIBUTERELEVANCEADVISORYSWITCHRESPONSE));
         }
         return null;
      });
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
      try {
         CompletableFuture<Void> future =
            asyncDisableAttributeRelevanceAdvisorySwitch(
 );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncDisableAttributeRelevanceAdvisorySwitch(
 )
   {
      hla.rti1516_202X.thin.DisableAttributeRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.DisableAttributeRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableAttributeRelevanceAdvisorySwitchRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasDisableAttributeRelevanceAdvisorySwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.DISABLEATTRIBUTERELEVANCEADVISORYSWITCHRESPONSE));
         }
         return null;
      });
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
      try {
         CompletableFuture<Void> future =
            asyncEnableAttributeScopeAdvisorySwitch(
 );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncEnableAttributeScopeAdvisorySwitch(
 )
   {
      hla.rti1516_202X.thin.EnableAttributeScopeAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.EnableAttributeScopeAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setEnableAttributeScopeAdvisorySwitchRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasEnableAttributeScopeAdvisorySwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ENABLEATTRIBUTESCOPEADVISORYSWITCHRESPONSE));
         }
         return null;
      });
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
      try {
         CompletableFuture<Void> future =
            asyncDisableAttributeScopeAdvisorySwitch(
 );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncDisableAttributeScopeAdvisorySwitch(
 )
   {
      hla.rti1516_202X.thin.DisableAttributeScopeAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.DisableAttributeScopeAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableAttributeScopeAdvisorySwitchRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasDisableAttributeScopeAdvisorySwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.DISABLEATTRIBUTESCOPEADVISORYSWITCHRESPONSE));
         }
         return null;
      });
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
      try {
         CompletableFuture<Void> future =
            asyncEnableInteractionRelevanceAdvisorySwitch(
 );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncEnableInteractionRelevanceAdvisorySwitch(
 )
   {
      hla.rti1516_202X.thin.EnableInteractionRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.EnableInteractionRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setEnableInteractionRelevanceAdvisorySwitchRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasEnableInteractionRelevanceAdvisorySwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.ENABLEINTERACTIONRELEVANCEADVISORYSWITCHRESPONSE));
         }
         return null;
      });
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
      try {
         CompletableFuture<Void> future =
            asyncDisableInteractionRelevanceAdvisorySwitch(
 );
         future.join();
      } catch (CancellationException e) {
         throw new RTIinternalError("Call was cancelled '" + e);
      } catch (CompletionException e) {
         throwRealCause(e);
         throw new RTIinternalError("Unexpected exception '" + e);
      }
   }

      public CompletableFuture<Void>
asyncDisableInteractionRelevanceAdvisorySwitch(
 )
   {
      hla.rti1516_202X.thin.DisableInteractionRelevanceAdvisorySwitchRequest request;
      request = hla.rti1516_202X.thin.DisableInteractionRelevanceAdvisorySwitchRequest. getDefaultInstance();
      CallRequest callRequest = CallRequest.newBuilder().setDisableInteractionRelevanceAdvisorySwitchRequest(request).build();
      // No return value
      return doAsyncHlaCall(callRequest).thenApply(callResponse -> {
         if (!callResponse.hasDisableInteractionRelevanceAdvisorySwitchResponse()) {
            throw new CompletionException(new RTIinternalError("Mismatched response message. " +
               "Got: " + callResponse.getCallResponseCase() + ", " +
               "expected " + CallResponse.CallResponseCase.DISABLEINTERACTIONRELEVANCEADVISORYSWITCHRESPONSE));
         }
         return null;
      });
   }
}
