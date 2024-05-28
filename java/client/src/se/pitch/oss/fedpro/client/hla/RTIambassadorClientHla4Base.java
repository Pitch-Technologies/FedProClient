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

import hla.rti1516_202X.FederateAmbassador;
import hla.rti1516_202X.RtiConfiguration;
import hla.rti1516_202X.auth.Credentials;
import hla.rti1516_202X.exceptions.*;
import hla.rti1516_202X.thin.*;
import net.jcip.annotations.GuardedBy;
import se.pitch.oss.fedpro.client_abstract.ConnectSettings;
import se.pitch.oss.fedpro.client_abstract.RTIambassadorClientGenericBase;
import se.pitch.oss.fedpro.client_abstract.exceptions.FedProConnectionFailed;
import se.pitch.oss.fedpro.client_abstract.exceptions.FedProFederateInternalError;
import se.pitch.oss.fedpro.client_abstract.exceptions.FedProNotConnected;
import se.pitch.oss.fedpro.client_abstract.exceptions.FedProRtiInternalError;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class RTIambassadorClientHla4Base extends RTIambassadorClientGenericBase
{
   protected final ClientConverter _clientConverter;
   private FederateAmbassadorDispatcher _federateAmbassadorDispatcher;

   public RTIambassadorClientHla4Base(ClientConverter clientConverter)
   {
      super();
      _clientConverter = clientConverter;
   }

   protected void throwIfInCallback(String methodName) throws CallNotAllowedFromWithinCallback
   {
      if (isInCurrentCallbackThread()) {
         throw new CallNotAllowedFromWithinCallback(
            "Call attempted to " + methodName + " from within callback" + " (909142001)");
      }
   }

   public boolean evokeCallback(double approximateMinimumTimeInSeconds)
      throws CallNotAllowedFromWithinCallback, RTIinternalError
   {
      throwIfInCallback("evokeCallback");
      try {
         return evokeCallbackBase(approximateMinimumTimeInSeconds);
      } catch (FedProRtiInternalError e) {
         throw new RTIinternalError(e.getMessage());
      }
   }

   public boolean evokeMultipleCallbacks(double minimumTime,
                                         double maximumTime) throws CallNotAllowedFromWithinCallback, RTIinternalError
   {
      throwIfInCallback("evokeMultipleCallbacks");
      try {
         return evokeMultipleCallbacksBase(minimumTime, maximumTime);
      } catch (FedProRtiInternalError e) {
         throw new RTIinternalError(e.getMessage());
      }
   }

   @Override
   protected void dispatchHlaVersionSpecificCallback(CallbackRequest callbackRequest)
      throws FedProFederateInternalError, FedProRtiInternalError
   {
      try {
         _federateAmbassadorDispatcher.dispatchCallback(callbackRequest);
      } catch (FederateInternalError e) {
         throw new FedProFederateInternalError(e);
      } catch (RTIinternalError e) {
         throw new FedProRtiInternalError(e);
      }
   }

   public void enableCallbacks() throws RTIinternalError
   {
      try {
         enableCallbacksBase();
      } catch (FedProRtiInternalError e) {
         throw new RTIinternalError(e.getMessage());
      }
   }

   public void disableCallbacks() throws RTIinternalError
   {
      try {
         disableCallbacksBase();
      } catch (FedProRtiInternalError e) {
         throw new RTIinternalError(e.getMessage());
      }
   }

   protected Throwable asRuntimeOrRtiException(Throwable e)
   {
      if (e instanceof RTIexception) {
         return e;
      } else if (e instanceof RuntimeException) {
         return e;
      } else {
         // Implementation-specific exceptions, e.g. protobuf or session,
         // shall be presented as RTIinternalError.
         return new RTIinternalError("Unexpected exception: " + e);
      }
   }

   protected void throwRealCause(CompletionException e)
   {
      Exceptions.sneakyThrow(asRuntimeOrRtiException(e.getCause()));
   }

   @GuardedBy("_connectionStateLock")
   private void throwIfAlreadyConnected() throws AlreadyConnected
   {
      if (_persistentSession != null) {
         throw new AlreadyConnected("Already has a session");
      }
   }

   @Override
   protected void throwOnException(CallResponse callResponse) throws FedProRtiInternalError
   {
      if (callResponse.hasExceptionData()) {
         ExceptionData exceptionData = callResponse.getExceptionData();
         String exceptionName = exceptionData.getExceptionName();
         String details = exceptionData.getDetails();

         Exceptions.doThrow(exceptionName, details);
         throw new FedProRtiInternalError("Unexpected exception '" + exceptionName + "': " + details);
      }
   }

   protected CallResponse doHlaCall(CallRequest.Builder callRequestBuilder) throws NotConnected, RTIinternalError
   {
      return doHlaCall(callRequestBuilder.build());
   }

   protected CallResponse doHlaCall(CallRequest callRequest) throws RTIinternalError, NotConnected
   {
      try {
         return doHlaCallBase(callRequest);
      } catch (FedProRtiInternalError e) {
         throw new RTIinternalError(e.getMessage());
      } catch (FedProNotConnected e) {
         throw new NotConnected(e.getMessage());
      }
   }

   protected CompletableFuture<CallResponse> doAsyncHlaCall(CallRequest callRequest)
   {
      try {
         return doAsyncHlaCallBase(callRequest);
      } catch (FedProNotConnected e) {
         return CompletableFuture.failedFuture(new NotConnected(e.getMessage()));
      }
   }

   public hla.rti1516_202X.ConfigurationResult connect(FederateAmbassador federateReference,
                                                       hla.rti1516_202X.CallbackModel callbackModel) throws
      ConnectionFailed,
      UnsupportedCallbackModel,
      AlreadyConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError
   {
      throwIfInCallback("connect");
      synchronized (_connectionStateLock) {

         throwIfAlreadyConnected();

         ConnectSettings connectSettings;
         try {
            connectSettings = ConnectSettings.parse();
         } catch (FedProConnectionFailed e) {
            throw new ConnectionFailed(e.getMessage());
         }

         try {
            _persistentSession = createPersistentSession(connectSettings);
         } catch (FedProRtiInternalError e) {
            throw new RTIinternalError(e.getMessage());
         }

         CallRequest.Builder callRequest = CallRequest.newBuilder().setConnectRequest(ConnectRequest.newBuilder());
         CallResponse callResponse = doConnect(federateReference, callbackModel, callRequest, connectSettings);
         ConnectResponse connectResponse = callResponse.getConnectResponse();
         return _clientConverter.convertToHla(connectResponse.getConfigurationResult());
      }
   }

   public hla.rti1516_202X.ConfigurationResult connect(FederateAmbassador federateReference,
                                                       hla.rti1516_202X.CallbackModel callbackModel,
                                                       hla.rti1516_202X.RtiConfiguration rtiConfiguration) throws
      ConnectionFailed,
      UnsupportedCallbackModel,
      AlreadyConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError
   {
      throwIfInCallback("connect");
      synchronized (_connectionStateLock) {

         throwIfAlreadyConnected();

         RtiConfiguration parsedRtiConfiguration;
         ConnectSettings connectSettings;
         try {
            parsedRtiConfiguration = parseRtiConfiguration(rtiConfiguration);
            connectSettings = ConnectSettings.parse(
               parsedRtiConfiguration.federateProtocolServerAddress(),
               parsedRtiConfiguration.federateProtocolAdditionalSettings());
         } catch (FedProConnectionFailed e) {
            throw new ConnectionFailed(e.getMessage());
         }

         try {
            _persistentSession = createPersistentSession(connectSettings);
         } catch (FedProRtiInternalError e) {
            throw new RTIinternalError(e.getMessage());
         }

         CallRequest.Builder callRequest = CallRequest.newBuilder()
            .setConnectWithConfigurationRequest(ConnectWithConfigurationRequest.newBuilder()
               .setRtiConfiguration(_clientConverter.convertFromHla(parsedRtiConfiguration)));

         CallResponse callResponse = doConnect(federateReference, callbackModel, callRequest, connectSettings);
         ConnectWithConfigurationResponse connectResponse = callResponse.getConnectWithConfigurationResponse();
         return _clientConverter.convertToHla(connectResponse.getConfigurationResult());
      }
   }

   public hla.rti1516_202X.ConfigurationResult connect(FederateAmbassador federateReference,
                                                       hla.rti1516_202X.CallbackModel callbackModel,
                                                       hla.rti1516_202X.auth.Credentials credentials) throws
      Unauthorized,
      InvalidCredentials,
      ConnectionFailed,
      UnsupportedCallbackModel,
      AlreadyConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError
   {
      throwIfInCallback("connect");
      synchronized (_connectionStateLock) {

         if (credentials == null) {
            return connect(federateReference, callbackModel);
         }
         throwIfAlreadyConnected();

         ConnectSettings connectSettings;
         try {
            connectSettings = ConnectSettings.parse();
         } catch (FedProConnectionFailed e) {
            throw new ConnectionFailed(e.getMessage());
         }

         try {
            _persistentSession = createPersistentSession(connectSettings);
         } catch (FedProRtiInternalError e) {
            throw new RTIinternalError(e.getMessage());
         }

         CallRequest.Builder callRequest = CallRequest.newBuilder()
            .setConnectWithCredentialsRequest(ConnectWithCredentialsRequest.newBuilder()
               .setCredentials(_clientConverter.convertFromHla(credentials)));
         CallResponse callResponse = doConnect(federateReference, callbackModel, callRequest, connectSettings);
         ConnectWithCredentialsResponse connectResponse = callResponse.getConnectWithCredentialsResponse();
         return _clientConverter.convertToHla(connectResponse.getConfigurationResult());
      }
   }

   public hla.rti1516_202X.ConfigurationResult connect(FederateAmbassador federateReference,
                                                       hla.rti1516_202X.CallbackModel callbackModel,
                                                       hla.rti1516_202X.RtiConfiguration rtiConfiguration,
                                                       Credentials credentials) throws
      Unauthorized,
      InvalidCredentials,
      ConnectionFailed,
      UnsupportedCallbackModel,
      AlreadyConnected,
      CallNotAllowedFromWithinCallback,
      RTIinternalError
   {
      throwIfInCallback("connect");
      synchronized (_connectionStateLock) {

         if (credentials == null) {
            return connect(federateReference, callbackModel, rtiConfiguration);
         }
         throwIfAlreadyConnected();

         RtiConfiguration parsedRtiConfiguration;
         ConnectSettings connectSettings;
         try {
            parsedRtiConfiguration = parseRtiConfiguration(rtiConfiguration);
            connectSettings = ConnectSettings.parse(
               parsedRtiConfiguration.federateProtocolServerAddress(),
               parsedRtiConfiguration.federateProtocolAdditionalSettings());
         } catch (FedProConnectionFailed e) {
            throw new ConnectionFailed(e.getMessage());
         }

         try {
            _persistentSession = createPersistentSession(connectSettings);
         } catch (FedProRtiInternalError e) {
            throw new RTIinternalError(e.getMessage());
         }

         CallRequest.Builder callRequest = CallRequest.newBuilder()
            .setConnectWithConfigurationAndCredentialsRequest(ConnectWithConfigurationAndCredentialsRequest.newBuilder()
               .setRtiConfiguration(_clientConverter.convertFromHla(parsedRtiConfiguration))
               .setCredentials(_clientConverter.convertFromHla(credentials)));

         CallResponse callResponse = doConnect(federateReference, callbackModel, callRequest, connectSettings);
         ConnectWithConfigurationAndCredentialsResponse connectResponse = callResponse.getConnectWithConfigurationAndCredentialsResponse();
         return _clientConverter.convertToHla(connectResponse.getConfigurationResult());
      }
   }

   @GuardedBy("_connectionStateLock")
   private CallResponse doConnect(FederateAmbassador federateReference,
                                  hla.rti1516_202X.CallbackModel callbackModel,
                                  CallRequest.Builder callRequest,
                                  ConnectSettings connectSettings) throws ConnectionFailed, RTIinternalError
   {
      try {
         startPersistentSession(connectSettings);
         CallResponse callResponse = doHlaCall(callRequest);
         _federateAmbassadorDispatcher = new FederateAmbassadorDispatcher(federateReference, _clientConverter);
         // Todo - I don't see how we need throwOnException() here? remove?
         throwOnException(callResponse);
         if (callbackModel == hla.rti1516_202X.CallbackModel.HLA_IMMEDIATE) {
            startCallbackThread();
         }
         return callResponse;
      } catch (FedProNotConnected | NotConnected e) {
         throw new ConnectionFailed("Failed to connect");
      } catch (FedProConnectionFailed e) {
         throw new ConnectionFailed(
            "Failed to create session to server " + connectSettings.getServer() + ":" + connectSettings.getPort(), e);
      } catch (FedProRtiInternalError e) {
         throw new RTIinternalError(e.getMessage());
      }

   }

   // Once FedPro server address gets its own field in RtiConfiguration in the HLA standard, this method can be removed.
   private RtiConfiguration parseRtiConfiguration(RtiConfiguration rtiConfiguration) throws FedProConnectionFailed
   {
      if (rtiConfiguration.rtiAddress().equals("")) {
         return rtiConfiguration;
      }
      if (!rtiConfiguration.federateProtocolServerAddress().equals("")) {
         return rtiConfiguration;
      }

      // Todo - In the Evolved case we do not throw error in its counterpart place, why do it here then?
      //  Maybe we don't need to worry about it since we probably will remove this method int the future.
      String[] splitConfiguration = rtiConfiguration.rtiAddress().split(";", 4);
      if (splitConfiguration.length > 3) {
         throw new FedProConnectionFailed("Configuration address contains too many ';', should at most contain 2.");
      }

      String fedProAddress = splitConfiguration[0];
      String fedProAdditionalSettings = rtiConfiguration.federateProtocolAdditionalSettings();
      String rtiAddress = "";

      if (splitConfiguration.length >= 2) {
         fedProAdditionalSettings = splitConfiguration[1];
      }
      if (splitConfiguration.length == 3) {
         rtiAddress = splitConfiguration[2];
      }

      return RtiConfiguration.createConfiguration()
         .withRtiAddress(rtiAddress)
         .withConfigurationName(rtiConfiguration.configurationName())
         .withAdditionalSettings(rtiConfiguration.additionalSettings())
         .withFederateProtocolServerAddress(fedProAddress)
         .withFederateProtocolAdditionalSettings(fedProAdditionalSettings);
   }

   public void disconnect() throws FederateIsExecutionMember, CallNotAllowedFromWithinCallback, RTIinternalError
   {
      throwIfInCallback("disconnect");

      try {
         disconnectBase();
      } catch (FedProRtiInternalError | RuntimeException e) {
         // TODO: What happens in the standard API when we call disconnect before the RTIambassador is connected?
         throw new RTIinternalError("" + e, e);
      }
   }

}
