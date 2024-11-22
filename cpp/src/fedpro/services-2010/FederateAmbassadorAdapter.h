/***********************************************************************
  Copyright (C) 2023 Pitch Technologies AB

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 **********************************************************************/

#pragma once

#include "services-common/Handle.h"
#include "services-common/RTITypedefs.h"

#include <RTI/Enums.h>

#include <memory>
#include <string>

namespace RTI_NAMESPACE
{
   class FederateAmbassador;
   class LogicalTime;
}

namespace FedPro
{
   using namespace RTI_NAMESPACE;

   class RTIambassadorClient;

   class FederateAmbassadorAdapter
   {
   public:
      explicit FederateAmbassadorAdapter(
            FederateAmbassador * federateReference);

      ~FederateAmbassadorAdapter();

      void prefetch(
            RTIambassadorClient * client);

      void connectionLost (
            std::wstring const & faultDescription);

      void reportFederationExecutions (
            FederationExecutionInformationVector const & report);

      void synchronizationPointRegistrationSucceeded (
            std::wstring const & synchronizationPointLabel);

      void synchronizationPointRegistrationFailed (
            std::wstring const & synchronizationPointLabel,
            SynchronizationPointFailureReason reason);

      void announceSynchronizationPoint (
            std::wstring  const & synchronizationPointLabel,
            VariableLengthData const & userSuppliedTag);

      void federationSynchronized (
            std::wstring const & synchronizationPointLabel,
            FederateHandleSet const & failedToSyncSet);

      void initiateFederateSave (
            std::wstring const & label);

      void initiateFederateSave (
            std::wstring const & label,
            LogicalTime const & time);

      void federationSaved ();

      void federationNotSaved (
            SaveFailureReason reason);

      void federationSaveStatusResponse (
            FederateHandleSaveStatusPairVector const & response);

      void requestFederationRestoreSucceeded (
            std::wstring const & label);

      void requestFederationRestoreFailed (
            std::wstring const & label);

      void federationRestoreBegun ();

      void initiateFederateRestore (
            std::wstring const & label,
            std::wstring const & federateName,
            FederateHandle const & postRestoreFederateHandle);

      void federationRestored ();

      void federationNotRestored (
            RestoreFailureReason reason);

      void federationRestoreStatusResponse (
            FederateRestoreStatusVector const & response);

      void startRegistrationForObjectClass (
            ObjectClassHandle const & objectClass);

      void stopRegistrationForObjectClass (
            ObjectClassHandle const & objectClass);

      void turnInteractionsOn (
            InteractionClassHandle const & interactionClass);

      void turnInteractionsOff (
            InteractionClassHandle const & interactionClass);

      void objectInstanceNameReservationSucceeded (
            std::wstring const & objectInstanceName);

      void objectInstanceNameReservationFailed (
            std::wstring const & objectInstanceName);

      void multipleObjectInstanceNameReservationSucceeded (
            std::set<std::wstring> const & objectInstanceNames);

      void multipleObjectInstanceNameReservationFailed (
            std::set<std::wstring> const & objectInstanceNames);

      void discoverObjectInstance (
            ObjectInstanceHandle const & objectInstance,
            ObjectClassHandle const & objectClass,
            std::wstring const & objectInstanceName,
            FederateHandle const & producingFederate);

      void reflectAttributeValues(
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleValueMap const & attributeValues,
            VariableLengthData const & userSuppliedTag,
            TransportationTypeHandle const & transportationType,
            FederateHandle const & producingFederate,
            RegionHandleSet const * optionalSentRegions);

      void reflectAttributeValues(
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleValueMap const & attributeValues,
            VariableLengthData const & userSuppliedTag,
            TransportationTypeHandle const & transportationType,
            FederateHandle const & producingFederate,
            RegionHandleSet const * optionalSentRegions,
            LogicalTime const & time,
            OrderType sentOrderType,
            OrderType receivedOrderType,
            MessageRetractionHandle const * optionalRetraction);

      void receiveInteraction(
            InteractionClassHandle const & interactionClass,
            ParameterHandleValueMap const & parameterValues,
            VariableLengthData const & userSuppliedTag,
            TransportationTypeHandle const & transportationType,
            FederateHandle const & producingFederate,
            const RegionHandleSet * optionalSentRegions);

      void receiveInteraction(
            InteractionClassHandle const & interactionClass,
            ParameterHandleValueMap const & parameterValues,
            VariableLengthData const & userSuppliedTag,
            TransportationTypeHandle const & transportationType,
            FederateHandle const & producingFederate,
            RegionHandleSet const * optionalSentRegions,
            LogicalTime const & time,
            OrderType sentOrderType,
            OrderType receivedOrderType,
            MessageRetractionHandle const * optionalRetraction);

      void receiveDirectedInteraction(
            InteractionClassHandle const & interactionClass,
            ObjectInstanceHandle const & objectInstance,
            ParameterHandleValueMap const & parameterValues,
            VariableLengthData const & userSuppliedTag,
            TransportationTypeHandle const & transportationType,
            FederateHandle const & producingFederate);

      void receiveDirectedInteraction(
            InteractionClassHandle const & interactionClass,
            ObjectInstanceHandle const & objectInstance,
            ParameterHandleValueMap const & parameterValues,
            VariableLengthData const & userSuppliedTag,
            TransportationTypeHandle const & transportationType,
            FederateHandle const & producingFederate,
            LogicalTime const & time,
            OrderType sentOrderType,
            OrderType receivedOrderType,
            MessageRetractionHandle const * optionalRetraction);

      void removeObjectInstance(
            ObjectInstanceHandle const & objectInstance,
            VariableLengthData const & userSuppliedTag,
            FederateHandle const & producingFederate);

      void removeObjectInstance(
            ObjectInstanceHandle const & objectInstance,
            VariableLengthData const & userSuppliedTag,
            FederateHandle const & producingFederate,
            LogicalTime const & time,
            OrderType sentOrderType,
            OrderType receivedOrderType,
            MessageRetractionHandle const * optionalRetraction);

      void attributesInScope (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & attributes);

      void attributesOutOfScope (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & attributes);

      void provideAttributeValueUpdate (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & attributes,
            VariableLengthData const & userSuppliedTag);

      void turnUpdatesOnForObjectInstance (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & attributes);

      void turnUpdatesOnForObjectInstance (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & attributes,
            std::wstring const & updateRateDesignator);

      void turnUpdatesOffForObjectInstance (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & attributes);

      void confirmAttributeTransportationTypeChange (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & attributes,
            TransportationTypeHandle const & transportationType);

      void reportAttributeTransportationType (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandle const & attribute,
            TransportationTypeHandle const & transportationType);

      void confirmInteractionTransportationTypeChange (
            InteractionClassHandle const & interactionClass,
            TransportationTypeHandle const & transportationType);

      void reportInteractionTransportationType (
            FederateHandle const & federate,
            InteractionClassHandle const & interactionClass,
            TransportationTypeHandle const & transportationType);

      void requestAttributeOwnershipAssumption (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & offeredAttributes,
            VariableLengthData const & userSuppliedTag);

      void requestDivestitureConfirmation (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & releasedAttributes,
            VariableLengthData const & userSuppliedTag);

      void attributeOwnershipAcquisitionNotification (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & securedAttributes,
            VariableLengthData const & userSuppliedTag);

      void attributeOwnershipUnavailable (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & attributes,
            VariableLengthData const & userSuppliedTag);

      void requestAttributeOwnershipRelease (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & candidateAttributes,
            VariableLengthData const & userSuppliedTag);

      void confirmAttributeOwnershipAcquisitionCancellation (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & attributes);

      void informAttributeOwnership (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & attributes,
            FederateHandle const & owner);

      void attributeIsNotOwned (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & attributes);

      void attributeIsOwnedByRTI (
            ObjectInstanceHandle const & objectInstance,
            AttributeHandleSet const & attributes);

      void timeRegulationEnabled (
            LogicalTime const & time);

      void timeConstrainedEnabled (
            LogicalTime const & time);

      void timeAdvanceGrant (
            LogicalTime const & time);

      void requestRetraction (
            MessageRetractionHandle const & retraction);

   private:

      TransportationType getTransportationType(const TransportationTypeHandle & handle) const;

      FederateAmbassador * _federateReference{nullptr};

      TransportationTypeHandle _reliableTransportType;
      TransportationTypeHandle _bestEffortTransportType;
   };

} // FedPro
