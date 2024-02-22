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

import hla.rti1516_202X.exceptions.RTIexception;
import hla.rti1516_202X.exceptions.RTIinternalError;
import se.pitch.oss.fedpro.client_abstract.exceptions.FedProRtiInternalError;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exceptions {
/*
   % zip -sf hla1516_4.jar | grep exceptions | grep class | sort -u
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
         "AttributeRelevanceAdvisorySwitchIsOff",
         "AttributeRelevanceAdvisorySwitchIsOn",
         "AttributeScopeAdvisorySwitchIsOff",
         "AttributeScopeAdvisorySwitchIsOn",
         "CallNotAllowedFromWithinCallback",
         "ConnectionFailed",
         "CouldNotCreateLogicalTimeFactory",
         "CouldNotDecode",
         "CouldNotEncode",
         "CouldNotOpenFOM",
         "CouldNotOpenMIM",
         "DeletePrivilegeNotHeld",
         "DesignatorIsHLAstandardMIM",
         "ErrorReadingFOM",
         "ErrorReadingMIM",
         "FederateAlreadyExecutionMember",
         "FederateHandleNotKnown",
         "FederateHasNotBegunSave",
         "FederateInternalError",
         "FederateIsExecutionMember",
         "FederateNameAlreadyInUse",
         "FederateNotExecutionMember",
         "FederateOwnsAttributes",
         "FederatesCurrentlyJoined",
         "FederateServiceInvocationsAreBeingReportedViaMOM",
         "FederateUnableToUseTime",
         "FederationExecutionAlreadyExists",
         "FederationExecutionDoesNotExist",
         "IllegalName",
         "IllegalTimeArithmetic",
         "InconsistentFOM",
         "InteractionClassAlreadyBeingChanged",
         "InteractionClassNotDefined",
         "InteractionClassNotPublished",
         "InteractionParameterNotDefined",
         "InteractionRelevanceAdvisorySwitchIsOff",
         "InteractionRelevanceAdvisorySwitchIsOn",
         "InTimeAdvancingState",
         "InvalidAttributeHandle",
         "InvalidCredentials",
         "InvalidDimensionHandle",
         "InvalidFederateHandle",
         "InvalidFOM",
         "InvalidInteractionClassHandle",
         "InvalidLogicalTime",
         "InvalidLogicalTimeInterval",
         "InvalidLookahead",
         "InvalidMessageRetractionHandle",
         "InvalidMIM",
         "InvalidObjectClassHandle",
         "InvalidObjectInstanceHandle",
         "InvalidOrderName",
         "InvalidOrderType",
         "InvalidParameterHandle",
         "InvalidRangeBound",
         "InvalidRegion",
         "InvalidRegionContext",
         "InvalidResignAction",
         "InvalidServiceGroup",
         "InvalidTransportationName",
         "InvalidTransportationTypeHandle",
         "InvalidUpdateRateDesignator",
         "LogicalTimeAlreadyPassed",
         "MessageCanNoLongerBeRetracted",
         "NameNotFound",
         "NameSetWasEmpty",
         "NoAcquisitionPending",
         "NotConnected",
         "ObjectClassNotDefined",
         "ObjectClassNotPublished",
         "ObjectClassRelevanceAdvisorySwitchIsOff",
         "ObjectClassRelevanceAdvisorySwitchIsOn",
         "ObjectInstanceNameInUse",
         "ObjectInstanceNameNotReserved",
         "ObjectInstanceNotKnown",
         "OwnershipAcquisitionPending",
         "RegionDoesNotContainSpecifiedDimension",
         "RegionInUseForUpdateOrSubscription",
         "RegionNotCreatedByThisFederate",
         "RequestForTimeConstrainedPending",
         "RequestForTimeRegulationPending",
         "RestoreInProgress",
         "RestoreNotInProgress",
         "RestoreNotRequested",
         "RTIexception",
         "RTIinternalError",
         "SaveInProgress",
         "SaveNotInitiated",
         "SaveNotInProgress",
         "SynchronizationPointLabelNotAnnounced",
         "TimeConstrainedAlreadyEnabled",
         "TimeConstrainedIsNotEnabled",
         "TimeRegulationAlreadyEnabled",
         "TimeRegulationIsNotEnabled",
         "Unauthorized",
         "UnsupportedCallbackModel");

   private static class Init {
      private static final Map<String, Constructor<? extends RTIexception>> CONSTRUCTORS = init();

      private static Map<String, Constructor<? extends RTIexception>> init()
      {
         Map<String, Constructor<? extends RTIexception>> map = new HashMap<>();
         for (String name : NAMES) {
            try {
               Class<?> c = Class.forName("hla.rti1516_202X.exceptions." + name);
               if (RTIexception.class.isAssignableFrom(c)) {
                  //noinspection unchecked
                  Class<? extends RTIexception> c2 = (Class<? extends RTIexception>) c;
                  Constructor<? extends RTIexception> constructor = c2.getDeclaredConstructor(String.class);
                  map.put(name, constructor);
               }
            } catch (ClassNotFoundException | NoSuchMethodException e) {
               throw new RuntimeException(e);
            }
         }
         return map;
      }
   }

   @SuppressWarnings("unchecked")
   public static <E extends Throwable> void sneakyThrow(Throwable e)
   throws E
   {
      throw (E) e;
   }

   public static void doThrow(
         String exceptionName,
         String details)
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

   public static RTIexception createException(
         String exceptionName,
         String details)
   {
      Constructor<? extends RTIexception> constructor = Init.CONSTRUCTORS.get(exceptionName);
      if (constructor != null) {
         try {
            return constructor.newInstance(details);
         } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            return new RTIinternalError("Failed to create exception", e);
         }
      }
      return new RTIinternalError("Unexpected exception '" + exceptionName + "': " + details);
   }
}
