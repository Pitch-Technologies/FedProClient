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
import hla.rti1516e.CallbackModel;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.exceptions.*;
import net.jcip.annotations.GuardedBy;
import se.pitch.oss.fedpro.client.TypedProperties;
import se.pitch.oss.fedpro.client_common.RTIambassadorClientGenericBase;
import se.pitch.oss.fedpro.client_common.SettingsParser;
import se.pitch.oss.fedpro.client_common.exceptions.*;
import se.pitch.oss.fedpro.common.session.MovingStats;
import se.pitch.oss.fedpro.common.session.MovingStatsNoOp;
import se.pitch.oss.fedpro.common.session.StandAloneMovingStats;

import java.util.ArrayList;

import static se.pitch.oss.fedpro.client.Settings.SETTING_NAME_HLA_API_VERSION;

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
         final String clientSettingsLine = extractAndRemoveClientSettings(inputValueList, true);
         final RtiConfiguration.Builder parsedRtiConfiguration = createRtiConfiguration(inputValueList);

         try {
            TypedProperties clientSettings = SettingsParser.parse(clientSettingsLine);
            // API.version is a nonstandard setting supported by Pitch pRTI to improve handling of Evolved federates that use HLA 4's federate protocol.
            // Other RTIs will simply ignore it and interpret that the federate is HLA 4 compliant.
            String prtiApiVersion = clientSettings.getString(SETTING_NAME_HLA_API_VERSION, "IEEE 1516-2010");
            if (!prtiApiVersion.isEmpty()) {
               parsedRtiConfiguration.setAdditionalSettings(
                     "API.version=" + prtiApiVersion + "\n" + parsedRtiConfiguration.getAdditionalSettings());
            }

            _persistentSession = createPersistentSession(clientSettings);
         } catch (InvalidSetting e) {
            throw new ConnectionFailed(e.getMessage());
         } catch (FedProRtiInternalError e) {
            throw new RTIinternalError(e.getMessage());
         }

         if (_printStats) {
            _federateAmbassadorDispatcher = new FederateAmbassadorDispatcher(federateReference, _clientConverter, () -> new StandAloneMovingStats(_printStatsIntervalMillis));
         } else {
            _federateAmbassadorDispatcher = new FederateAmbassadorDispatcher(federateReference, _clientConverter, MovingStatsNoOp::new);
         }

         CallRequest.Builder callRequest = CallRequest.newBuilder().setConnectWithConfigurationRequest(
               ConnectWithConfigurationRequest.newBuilder().setRtiConfiguration(parsedRtiConfiguration));

         try {
            startPersistentSession();
            CallResponse callResponse = doHlaCall(callRequest);
            // Todo - I don't see how we need throwOnException() here? remove?
            throwOnException(callResponse);
            startStatPrinting();
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

   public static RtiConfiguration.Builder createRtiConfiguration(ArrayList<String> inputValueList)
   {
      RtiConfiguration.Builder builder = RtiConfiguration.newBuilder();
      if (inputValueList.isEmpty()) {
         return builder;
      }

      String firstLine = inputValueList.get(0);
      if (!firstLine.contains("=")) {
         builder.setConfigurationName(firstLine);
         inputValueList.remove(0);
      }

      builder.setAdditionalSettings(String.join("\n", inputValueList));

      // No need to set the rtiAddress since an LRC may specify the CRC address through the additional settings field
      // of the RtiConfiguration object, which is done in this method.
      return builder;
   }

   public void disconnect()
   throws FederateIsExecutionMember, CallNotAllowedFromWithinCallback, RTIinternalError
   {
      throwIfInCallback("disconnect");

      try {
         disconnectBase();
      } catch (FedProRtiInternalError | RuntimeException e) {
         throw new RTIinternalError("" + e, e);
      }
   }

   @Override
   protected MovingStats.Stats getReflectStats(long time)
   {
      return _federateAmbassadorDispatcher.getReflectStats(time);
   }

   @Override
   protected MovingStats.Stats getReceivedInteractionStats(long time)
   {
      return _federateAmbassadorDispatcher.getReceivedInteractionStats(time);
   }

   @Override
   protected MovingStats.Stats getReceivedDirectedInteractionStats(long time)
   {
      return _federateAmbassadorDispatcher.getReceivedDirectedInteractionStats(time);
   }

   @Override
   protected MovingStats.Stats getCallbackTimeStats(long time)
   {
      return _federateAmbassadorDispatcher.getCallbackTimeStats(time);
   }
}
