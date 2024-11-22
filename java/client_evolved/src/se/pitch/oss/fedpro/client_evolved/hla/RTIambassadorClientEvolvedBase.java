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

import hla.rti1516_202X.fedpro.*;
import hla.rti1516e.CallbackModel;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.exceptions.*;
import net.jcip.annotations.GuardedBy;
import se.pitch.oss.fedpro.client_common.RTIambassadorClientGenericBase;
import se.pitch.oss.fedpro.client_common.exceptions.*;

import java.util.ArrayList;

public class RTIambassadorClientEvolvedBase extends RTIambassadorClientGenericBase {

   protected final ClientConverter _clientConverter;
   private FederateAmbassadorDispatcher _federateAmbassadorDispatcher;

   public RTIambassadorClientEvolvedBase(ClientConverter clientConverter)
   {
      super();
      _clientConverter = clientConverter;
   }

   protected void throwIfInCallback(String methodName)
   throws CallNotAllowedFromWithinCallback
   {
      if (isInCurrentCallbackThread()) {
         throw new CallNotAllowedFromWithinCallback(
               "Call attempted to " + methodName + " from within callback");
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

   public boolean evokeMultipleCallbacks(
         double minimumTime,
         double maximumTime)
   throws CallNotAllowedFromWithinCallback, RTIinternalError
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

   public void enableCallbacks()
   throws RTIinternalError
   {
      try {
         enableCallbacksBase();
      } catch (FedProRtiInternalError e) {
         throw new RTIinternalError(e.getMessage());
      }
   }

   public void disableCallbacks()
   throws RTIinternalError
   {
      try {
         disableCallbacksBase();
      } catch (FedProRtiInternalError e) {
         throw new RTIinternalError(e.getMessage());
      }
   }

   @GuardedBy("_connectionStateLock")
   private void throwIfAlreadyConnected()
   throws AlreadyConnected
   {
      if (_persistentSession != null) {
         throw new AlreadyConnected("Already has a session");
      }
   }

   @Override
   protected void throwOnException(CallResponse callResponse)
   throws FedProRtiInternalError
   {
      if (callResponse.hasExceptionData()) {
         ExceptionData exceptionData = callResponse.getExceptionData();
         String exceptionName = exceptionData.getExceptionName();
         String details = exceptionData.getDetails();

         Exceptions.doThrow(exceptionName, details);
         throw new FedProRtiInternalError("Unexpected exception '" + exceptionName + "': " + details);
      }
   }

   protected CallResponse doHlaCall(CallRequest.Builder callRequestBuilder)
   throws NotConnected, RTIinternalError
   {
      return doHlaCall(callRequestBuilder.build());
   }

   protected CallResponse doHlaCall(CallRequest callRequest)
   throws RTIinternalError, NotConnected
   {
      try {
         return doHlaCallBase(callRequest);
      } catch (FedProRtiInternalError e) {
         throw new RTIinternalError(e.getMessage());
      } catch (FedProNotConnected e) {
         throw new NotConnected(e.getMessage());
      }
   }

   protected void doAsyncHlaCall(CallRequest callRequest)
   throws NotConnected
   {
      try {
         // Evolved federates do not use the returned CompletableFuture,
         // so ignore the result and return nothing.
         doAsyncHlaCallBase(callRequest);
      } catch (FedProNotConnected e) {
         throw new NotConnected(e.getMessage());
      }
   }

   // Todo - We never throw exceptions like InvalidLocalSettingsDesignator at the moment, should we:
   //   start throwing them? (Applies for hla4 version as well)
   //   keep them in signature but do nothing
   //   remove them from signature?
   public void connect(
         FederateAmbassador federateReference,
         CallbackModel callbackModel,
         String localSettingsDesignator)
   throws
         ConnectionFailed,
         InvalidLocalSettingsDesignator,
         UnsupportedCallbackModel,
         AlreadyConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      throwIfInCallback("connect");

      synchronized (_connectionStateLock) {
         throwIfAlreadyConnected();

         ArrayList<String> inputValueList = splitFederateConnectSettings(localSettingsDesignator);
         final String clientSettings = extractAndRemoveClientSettings(inputValueList, true);
         final RtiConfiguration parsedRtiConfiguration = createRtiConfiguration(inputValueList);

         try {
            _persistentSession = createPersistentSession(clientSettings);
         } catch (InvalidSetting e) {
            throw new ConnectionFailed(e.getMessage());
         } catch (FedProRtiInternalError e) {
            throw new RTIinternalError(e.getMessage());
         }

         _federateAmbassadorDispatcher = new FederateAmbassadorDispatcher(federateReference, _clientConverter);

         CallRequest.Builder callRequest = CallRequest.newBuilder().setConnectWithConfigurationRequest(
               ConnectWithConfigurationRequest.newBuilder().setRtiConfiguration(parsedRtiConfiguration));

         try {
            startPersistentSession();
            CallResponse callResponse = doHlaCall(callRequest);
            // Todo - I don't see how we need throwOnException() here? remove?
            throwOnException(callResponse);
            if (callbackModel == CallbackModel.HLA_IMMEDIATE) {
               startCallbackThread();
            }
         } catch (FedProNotConnected | NotConnected e) {
            throw new RTIinternalError("Failed to connect");
         } catch (FedProConnectionFailed e) {
            throw new ConnectionFailed(e.getMessage(), e);
         } catch (FedProRtiInternalError e) {
            throw new RTIinternalError(e.getMessage());
         }
      }
   }

   public static RtiConfiguration createRtiConfiguration(ArrayList<String> inputValueList)
   {
      RtiConfiguration.Builder builder = RtiConfiguration.newBuilder();
      if (inputValueList.isEmpty()) {
         return builder.build();
      }

      String firstLine = inputValueList.get(0);
      if (!firstLine.contains("=")) {
         builder.setConfigurationName(firstLine);
         inputValueList.remove(0);
      }

      builder.setAdditionalSettings(String.join("\n", inputValueList));

      // No need to set the rtiAddress since an LRC may specify the CRC address through the additional settings field
      // of the RtiConfiguration object, which is done in this method.
      return builder.build();
   }

   public void disconnect()
   throws FederateIsExecutionMember, CallNotAllowedFromWithinCallback, RTIinternalError
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
