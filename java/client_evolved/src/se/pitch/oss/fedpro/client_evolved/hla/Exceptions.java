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

import hla.rti1516e.exceptions.RTIexception;
import hla.rti1516e.exceptions.RTIinternalError;
import se.pitch.oss.fedpro.client_abstract.exceptions.FedProRtiInternalError;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exceptions {
/*
   % zip -sf hla1516e.jar | grep exceptions | grep class | sort -u
*/

   private static final List<String> NAMES = Arrays.asList(
         "AlreadyConnected",
         "AsynchronousDeliveryAlreadyDisabled",
         "AsynchronousDeliveryAlreadyEnabled",
         "AttributeAcquisitionWasNotRequested",
         "AttributeAlreadyBeingAcquired",
         "AttributeAlreadyBeingChanged",
         "AttributeAlreadyBeingDivested",
         "AttributeAlreadyOwned",
         "AttributeDivestitureWasNotRequested",
         "AttributeNotDefined",
         "AttributeNotOwned",
         "AttributeNotPublished",
         "AttributeNotRecognized",
         "AttributeNotSubscribed",
         "AttributeRelevanceAdvisorySwitchIsOff",
         "AttributeRelevanceAdvisorySwitchIsOn",
         "AttributeScopeAdvisorySwitchIsOff",
         "AttributeScopeAdvisorySwitchIsOn",
         "CallNotAllowedFromWithinCallback",
         "ConnectionFailed",
         "CouldNotCreateLogicalTimeFactory",
         "CouldNotDecode",
         "CouldNotEncode",
         "CouldNotOpenFOM-->CouldNotOpenFDD",
         "CouldNotOpenMIM",
         "DeletePrivilegeNotHeld",
         "DesignatorIsHLAstandardMIM",
         "ErrorReadingFOM-->ErrorReadingFDD",
         "ErrorReadingMIM",
         "FederateAlreadyExecutionMember",
         "FederateHandleNotKnown",
         "FederateHasNotBegunSave",
         "FederateInternalError",
         "FederateIsExecutionMember",
         "FederateNameAlreadyInUse",
         "FederateNotExecutionMember",
         "FederateOwnsAttributes",
         "FederateServiceInvocationsAreBeingReportedViaMOM",
         "FederateUnableToUseTime",
         "FederatesCurrentlyJoined",
         "FederationExecutionAlreadyExists",
         "FederationExecutionDoesNotExist",
         "IllegalName",
         "IllegalTimeArithmetic",
         "InTimeAdvancingState",
         "InconsistentFDD",
         "InteractionClassAlreadyBeingChanged",
         "InteractionClassNotDefined",
         "InteractionClassNotPublished",
         "InteractionParameterNotDefined",
         "InteractionRelevanceAdvisorySwitchIsOff",
         "InteractionRelevanceAdvisorySwitchIsOn",
         "InvalidAttributeHandle",
         "InvalidDimensionHandle",
         "InvalidFederateHandle",
         "InvalidInteractionClassHandle",
         "InvalidLocalSettingsDesignator",
         "InvalidLogicalTime",
         "InvalidLogicalTimeInterval",
         "InvalidLookahead",
         "InvalidMessageRetractionHandle",
         "InvalidObjectClassHandle",
         "InvalidOrderName",
         "InvalidOrderType",
         "InvalidParameterHandle",
         "InvalidRangeBound",
         "InvalidRegion",
         "InvalidRegionContext",
         "InvalidResignAction",
         "InvalidServiceGroup",
         "InvalidTransportationName",
         "InvalidTransportationType",
         "InvalidUpdateRateDesignator",
         "LogicalTimeAlreadyPassed",
         "MessageCanNoLongerBeRetracted",
         "NameNotFound",
         "NameSetWasEmpty",
         "NoAcquisitionPending",
         "NoRequestToEnableTimeConstrainedWasPending",
         "NoRequestToEnableTimeRegulationWasPending",
         "NotConnected",
         "ObjectClassNotDefined",
         "ObjectClassNotPublished",
         "ObjectClassRelevanceAdvisorySwitchIsOff",
         "ObjectClassRelevanceAdvisorySwitchIsOn",
         "ObjectInstanceNameInUse",
         "ObjectInstanceNameNotReserved",
         "ObjectInstanceNotKnown",
         "OwnershipAcquisitionPending",
         "RTIexception",
         "RTIinternalError",
         "RegionDoesNotContainSpecifiedDimension",
         "RegionInUseForUpdateOrSubscription",
         "RegionNotCreatedByThisFederate",
         "RequestForTimeConstrainedPending",
         "RequestForTimeRegulationPending",
         "RestoreInProgress",
         "RestoreNotInProgress",
         "RestoreNotRequested",
         "SaveInProgress",
         "SaveNotInProgress",
         "SaveNotInitiated",
         "SynchronizationPointLabelNotAnnounced",
         "TimeConstrainedAlreadyEnabled",
         "TimeConstrainedIsNotEnabled",
         "TimeRegulationAlreadyEnabled",
         "TimeRegulationIsNotEnabled",
         "UnableToPerformSave",
         "Unauthorized-->ConnectionFailed",
         "UnknownName",
         "UnsupportedCallbackModel"
   );

   private static class Init {
      private static final Map<String, Constructor<? extends RTIexception>> CONSTRUCTORS = init();

      private static Map<String, Constructor<? extends RTIexception>> init()
      {
         Map<String, Constructor<? extends RTIexception>> map = new HashMap<>();
         for (String name : NAMES) {
            try {
               String hla4Name;
               String evolvedName;
               if (name.contains("-->")) {
                  String[] split = name.split("-->");
                  hla4Name = split[0];
                  evolvedName = split[1];
               } else {
                  hla4Name = name;
                  evolvedName = name;
               }

               Class<?> c = Class.forName("hla.rti1516e.exceptions." + evolvedName);
               if (RTIexception.class.isAssignableFrom(c)) {
                  //noinspection unchecked
                  Class<? extends RTIexception> c2 = (Class<? extends RTIexception>) c;
                  Constructor<? extends RTIexception> constructor = c2.getDeclaredConstructor(String.class);
                  map.put(hla4Name, constructor);
               }
            } catch (ClassNotFoundException | NoSuchMethodException e) {
               throw new RuntimeException(e);
            }
         }
         return map;
      }
   }

   @SuppressWarnings("unchecked")
   private static <E extends Throwable> void sneakyThrow(Throwable e)
   throws E
   {
      throw (E) e;
   }

   public static void doThrow(String exceptionName, String details)
   throws FedProRtiInternalError
   {
      Constructor<? extends RTIexception> constructor = Init.CONSTRUCTORS.get(exceptionName);
      if (constructor != null) {
         try {
            sneakyThrow(constructor.newInstance(details));
         } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new FedProRtiInternalError("Failed to create exception", e);
         }
      }
   }

   public static RTIexception createException(String exceptionName, String details)
   {
      Constructor<? extends RTIexception> constructor = Init.CONSTRUCTORS.get(exceptionName);
      if (constructor != null) {
         try {
            RTIexception e = constructor.newInstance(details);
            return e;
         } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return new RTIinternalError("Failed to create exception", e);
         }
      }
      return new RTIinternalError("Unexpected exception '" + exceptionName + "': " + details);
   }
}
