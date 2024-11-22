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

#include "FederateAmbassadorAdapter.h"

#include "services-common/RTIambassadorClient.h"

#include <RTI/FederateAmbassador.h>

namespace FedPro
{
   using namespace RTI_NAMESPACE;

   FederateAmbassadorAdapter::FederateAmbassadorAdapter(
         FederateAmbassador * federateReference)
         : _federateReference{federateReference}
   {
   }

   FederateAmbassadorAdapter::~FederateAmbassadorAdapter() = default;

   void FederateAmbassadorAdapter::prefetch(
         RTIambassadorClient * client)
   {
      // Populate the local cache by fetching well-known values.
      // For an Evolved federate, we hope two TransportationTypeHandle exist.

      try {
         _reliableTransportType = client->getTransportationTypeHandle("HLAreliable");
      } catch (const RTI_NAMESPACE::Exception & e) {
         // TODO log error
      }

      try {
         _bestEffortTransportType = client->getTransportationTypeHandle("HLAbestEffort");
      } catch (const RTI_NAMESPACE::Exception & e) {
         // TODO log error
      }
   }

   TransportationType FederateAmbassadorAdapter::getTransportationType(const TransportationTypeHandle & handle) const
   {
      if (handle == _reliableTransportType) {
         return RELIABLE;
      } else if (handle == _bestEffortTransportType) {
         return BEST_EFFORT;
      } else {
         throw FederateInternalError(L"Unknown TransportationTypeHandle. Expecting either HLAreliable or HLAbestEffort. ");
      }
   }

   void FederateAmbassadorAdapter::connectionLost(
         std::wstring const & faultDescription)
   {
      _federateReference->connectionLost(faultDescription);
   }

   void FederateAmbassadorAdapter::reportFederationExecutions(
         FederationExecutionInformationVector const & report)
   {
      _federateReference->reportFederationExecutions(report);
   }

   void FederateAmbassadorAdapter::synchronizationPointRegistrationSucceeded(
         std::wstring const & synchronizationPointLabel)
   {
      _federateReference->synchronizationPointRegistrationSucceeded(synchronizationPointLabel);
   }

   void FederateAmbassadorAdapter::synchronizationPointRegistrationFailed(
         std::wstring const & synchronizationPointLabel,
         SynchronizationPointFailureReason reason)
   {
      _federateReference->synchronizationPointRegistrationFailed(synchronizationPointLabel, reason);
   }

   void FederateAmbassadorAdapter::announceSynchronizationPoint(
         std::wstring const & synchronizationPointLabel,
         VariableLengthData const & userSuppliedTag)
   {
      _federateReference->announceSynchronizationPoint(synchronizationPointLabel, userSuppliedTag);
   }

   void FederateAmbassadorAdapter::federationSynchronized(
         std::wstring const & synchronizationPointLabel,
         FederateHandleSet const & failedToSyncSet)
   {
      _federateReference->federationSynchronized(synchronizationPointLabel, failedToSyncSet);
   }

   void FederateAmbassadorAdapter::initiateFederateSave(
         std::wstring const & label)
   {
      _federateReference->initiateFederateSave(label);
   }

   void FederateAmbassadorAdapter::initiateFederateSave(
         std::wstring const & label,
         LogicalTime const & time)
   {
      _federateReference->initiateFederateSave(label, time);
   }

   void FederateAmbassadorAdapter::federationSaved()
   {
      _federateReference->federationSaved();
   }

   void FederateAmbassadorAdapter::federationNotSaved(
         SaveFailureReason reason)
   {
      _federateReference->federationNotSaved(reason);
   }

   void FederateAmbassadorAdapter::federationSaveStatusResponse(
         FederateHandleSaveStatusPairVector const & response)
   {
      _federateReference->federationSaveStatusResponse(response);
   }

   void FederateAmbassadorAdapter::requestFederationRestoreSucceeded(
         std::wstring const & label)
   {
      _federateReference->requestFederationRestoreSucceeded(label);
   }

   void FederateAmbassadorAdapter::requestFederationRestoreFailed(
         std::wstring const & label)
   {
      _federateReference->requestFederationRestoreFailed(label);
   }

   void FederateAmbassadorAdapter::federationRestoreBegun()
   {
      _federateReference->federationRestoreBegun();
   }

   void FederateAmbassadorAdapter::initiateFederateRestore(
         std::wstring const & label,
         std::wstring const & federateName,
         FederateHandle const & postRestoreFederateHandle)
   {
      _federateReference->initiateFederateRestore(label, federateName, postRestoreFederateHandle);
   }

   void FederateAmbassadorAdapter::federationRestored()
   {
      _federateReference->federationRestored();
   }

   void FederateAmbassadorAdapter::federationNotRestored(
         RestoreFailureReason reason)
   {
      _federateReference->federationNotRestored(reason);
   }

   void FederateAmbassadorAdapter::federationRestoreStatusResponse(
         FederateRestoreStatusVector const & response)
   {
      _federateReference->federationRestoreStatusResponse(response);
   }

   void FederateAmbassadorAdapter::startRegistrationForObjectClass(
         ObjectClassHandle const & objectClass)
   {
      _federateReference->startRegistrationForObjectClass(objectClass);
   }

   void FederateAmbassadorAdapter::stopRegistrationForObjectClass(
         ObjectClassHandle const & objectClass)
   {
      _federateReference->stopRegistrationForObjectClass(objectClass);
   }

   void FederateAmbassadorAdapter::turnInteractionsOn(
         InteractionClassHandle const & interactionClass)
   {
      _federateReference->turnInteractionsOn(interactionClass);
   }

   void FederateAmbassadorAdapter::turnInteractionsOff(
         InteractionClassHandle const & interactionClass)
   {
      _federateReference->turnInteractionsOff(interactionClass);
   }

   void FederateAmbassadorAdapter::objectInstanceNameReservationSucceeded(
         std::wstring const & objectInstanceName)
   {
      _federateReference->objectInstanceNameReservationSucceeded(objectInstanceName);
   }

   void FederateAmbassadorAdapter::objectInstanceNameReservationFailed(
         std::wstring const & objectInstanceName)
   {
      _federateReference->objectInstanceNameReservationFailed(objectInstanceName);
   }

   void FederateAmbassadorAdapter::multipleObjectInstanceNameReservationSucceeded(
         std::set<std::wstring> const & objectInstanceNames)
   {
      _federateReference->multipleObjectInstanceNameReservationSucceeded(objectInstanceNames);
   }

   void FederateAmbassadorAdapter::multipleObjectInstanceNameReservationFailed(
         std::set<std::wstring> const & objectInstanceNames)
   {
      _federateReference->multipleObjectInstanceNameReservationFailed(objectInstanceNames);
   }

   void FederateAmbassadorAdapter::discoverObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         ObjectClassHandle const & objectClass,
         std::wstring const & objectInstanceName,
         FederateHandle const & producingFederate)
   {
      _federateReference->discoverObjectInstance(objectInstance, objectClass, objectInstanceName, producingFederate);
   }



   void FederateAmbassadorAdapter::reflectAttributeValues(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleValueMap const & attributeValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationTypeHandle,
         FederateHandle const & producingFederate,
         RegionHandleSet const * optionalSentRegions)
   {
      try {
         TransportationType transportationType = getTransportationType(transportationTypeHandle);
         _federateReference->reflectAttributeValues(
               objectInstance,
               attributeValues,
               userSuppliedTag,
               RECEIVE,
               transportationType,
               optionalSentRegions ? SupplementalReflectInfo{producingFederate, *optionalSentRegions}
                                   : SupplementalReflectInfo{producingFederate});
      } catch (const std::exception &) {
         // TODO log exception as error
      }
   }

   void FederateAmbassadorAdapter::reflectAttributeValues(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleValueMap const & attributeValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationTypeHandle,
         FederateHandle const & producingFederate,
         RegionHandleSet const * optionalSentRegions,
         LogicalTime const & time,
         OrderType sentOrderType,
         OrderType receivedOrderType,
         MessageRetractionHandle const * optionalRetraction)
   {
      try {
         TransportationType transportationType = getTransportationType(transportationTypeHandle);
         if (optionalRetraction) {
            _federateReference->reflectAttributeValues(
                  objectInstance,
                  attributeValues,
                  userSuppliedTag,
                  sentOrderType,
                  transportationType,
                  time,
                  receivedOrderType,
                  *optionalRetraction,
                  optionalSentRegions ? SupplementalReflectInfo{producingFederate, *optionalSentRegions}
                                      : SupplementalReflectInfo{producingFederate});
         } else {
            _federateReference->reflectAttributeValues(
                  objectInstance,
                  attributeValues,
                  userSuppliedTag,
                  sentOrderType,
                  transportationType,
                  time,
                  receivedOrderType,
                  optionalSentRegions ? SupplementalReflectInfo{producingFederate, *optionalSentRegions}
                                      : SupplementalReflectInfo{producingFederate});
         }
      } catch (const std::exception &) {
         // TODO log exception as error
      }
   }

   void FederateAmbassadorAdapter::receiveInteraction(
         InteractionClassHandle const & interactionClass,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationTypeHandle,
         FederateHandle const & producingFederate,
         const RegionHandleSet * optionalSentRegions)
   {
      try {
         TransportationType transportationType = getTransportationType(transportationTypeHandle);
         _federateReference->receiveInteraction(
               interactionClass,
               parameterValues,
               userSuppliedTag,
               RECEIVE,
               transportationType,
               optionalSentRegions ? SupplementalReceiveInfo{producingFederate, *optionalSentRegions}
                                   : SupplementalReceiveInfo{producingFederate});
      } catch (const std::exception &) {
         // TODO log exception as error
      }
   }

   void FederateAmbassadorAdapter::receiveInteraction(
         InteractionClassHandle const & interactionClass,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationTypeHandle,
         FederateHandle const & producingFederate,
         RegionHandleSet const * optionalSentRegions,
         LogicalTime const & time,
         OrderType sentOrderType,
         OrderType receivedOrderType,
         MessageRetractionHandle const * optionalRetraction)
   {
      try {
         TransportationType transportationType = getTransportationType(transportationTypeHandle);
         if (optionalRetraction) {
            _federateReference->receiveInteraction(
                  interactionClass,
                  parameterValues,
                  userSuppliedTag,
                  sentOrderType,
                  transportationType,
                  time,
                  receivedOrderType,
                  *optionalRetraction,
                  optionalSentRegions ? SupplementalReceiveInfo{producingFederate, *optionalSentRegions}
                                      : SupplementalReceiveInfo{producingFederate});
         }
         else {
            _federateReference->receiveInteraction(
                  interactionClass,
                  parameterValues,
                  userSuppliedTag,
                  sentOrderType,
                  transportationType,
                  time,
                  receivedOrderType,
                  optionalSentRegions ? SupplementalReceiveInfo{producingFederate, *optionalSentRegions}
                                      : SupplementalReceiveInfo{producingFederate});
         }
      } catch (const std::exception &) {
         // TODO log exception as error
      }
   }

   void FederateAmbassadorAdapter::receiveDirectedInteraction(
         InteractionClassHandle const & interactionClass,
         ObjectInstanceHandle const & objectInstance,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate)
   {
      // Since Evolved federates are not aware of DirectedInteraction, deliver this as a classic Interaction
      receiveInteraction(
            interactionClass,
            parameterValues,
            userSuppliedTag,
            transportationType,
            producingFederate,
            nullptr);
   }

   void FederateAmbassadorAdapter::receiveDirectedInteraction(
         InteractionClassHandle const & interactionClass,
         ObjectInstanceHandle const & objectInstance,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate,
         LogicalTime const & time,
         OrderType sentOrderType,
         OrderType receivedOrderType,
         MessageRetractionHandle const * optionalRetraction)
   {
      // Since Evolved federates are not aware of DirectedInteraction, deliver this as a classic Interaction
      receiveInteraction(
            interactionClass,
            parameterValues,
            userSuppliedTag,
            transportationType,
            producingFederate,
            nullptr,
            time,
            sentOrderType,
            receivedOrderType,
            optionalRetraction);
   }

   void FederateAmbassadorAdapter::removeObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         VariableLengthData const & userSuppliedTag,
         FederateHandle const & producingFederate)
   {
      _federateReference->removeObjectInstance(
            objectInstance, userSuppliedTag, RECEIVE, SupplementalRemoveInfo{producingFederate});
   }

   void FederateAmbassadorAdapter::removeObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         VariableLengthData const & userSuppliedTag,
         FederateHandle const & producingFederate,
         LogicalTime const & time,
         OrderType sentOrderType,
         OrderType receivedOrderType,
         MessageRetractionHandle const * optionalRetraction)
   {
      if (optionalRetraction) {
         _federateReference->removeObjectInstance(
               objectInstance,
               userSuppliedTag,
               sentOrderType,
               time,
               receivedOrderType,
               *optionalRetraction,
               SupplementalRemoveInfo{producingFederate});
      } else {
         _federateReference->removeObjectInstance(
               objectInstance,
               userSuppliedTag,
               sentOrderType,
               time,
               receivedOrderType,
               SupplementalRemoveInfo{producingFederate});
      }
   }

   void FederateAmbassadorAdapter::attributesInScope(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes)
   {
      _federateReference->attributesInScope(objectInstance, attributes);
   }

   void FederateAmbassadorAdapter::attributesOutOfScope(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes)
   {
      _federateReference->attributesOutOfScope(objectInstance, attributes);
   }

   void FederateAmbassadorAdapter::provideAttributeValueUpdate(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         VariableLengthData const & userSuppliedTag)
   {
      _federateReference->provideAttributeValueUpdate(objectInstance, attributes, userSuppliedTag);
   }

   void FederateAmbassadorAdapter::turnUpdatesOnForObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes)
   {
      _federateReference->turnUpdatesOnForObjectInstance(objectInstance, attributes);
   }

   void FederateAmbassadorAdapter::turnUpdatesOnForObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         std::wstring const & updateRateDesignator)
   {
      _federateReference->turnUpdatesOnForObjectInstance(objectInstance, attributes, updateRateDesignator);
   }

   void FederateAmbassadorAdapter::turnUpdatesOffForObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes)
   {
      _federateReference->turnUpdatesOffForObjectInstance(objectInstance, attributes);
   }

   void FederateAmbassadorAdapter::confirmAttributeTransportationTypeChange(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         TransportationTypeHandle const & transportationTypeHandle)
   {
      try {
         TransportationType transportationType = getTransportationType(transportationTypeHandle);
         _federateReference->confirmAttributeTransportationTypeChange(objectInstance, attributes, transportationType);
      } catch (const std::exception &) {
         // TODO log exception as error
      }
   }

   void FederateAmbassadorAdapter::reportAttributeTransportationType(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandle const & attribute,
         TransportationTypeHandle const & transportationTypeHandle)
   {
      try {
         TransportationType transportationType = getTransportationType(transportationTypeHandle);
         _federateReference->reportAttributeTransportationType(objectInstance, attribute, transportationType);
      } catch (const std::exception &) {
         // TODO log exception as error
      }
   }

   void FederateAmbassadorAdapter::confirmInteractionTransportationTypeChange(
         InteractionClassHandle const & interactionClass,
         TransportationTypeHandle const & transportationTypeHandle)
   {
      try {
         TransportationType transportationType = getTransportationType(transportationTypeHandle);
         _federateReference->confirmInteractionTransportationTypeChange(interactionClass, transportationType);
      } catch (const std::exception &) {
         // TODO log exception as error
      }
   }

   void FederateAmbassadorAdapter::reportInteractionTransportationType(
         FederateHandle const & federate,
         InteractionClassHandle const & interactionClass,
         TransportationTypeHandle const & transportationTypeHandle)
   {
      try {
         TransportationType transportationType = getTransportationType(transportationTypeHandle);
         _federateReference->reportInteractionTransportationType(federate, interactionClass, transportationType);
      } catch (const std::exception &) {
         // TODO log exception as error
      }
   }

   void FederateAmbassadorAdapter::requestAttributeOwnershipAssumption(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & offeredAttributes,
         VariableLengthData const & userSuppliedTag)
   {
      _federateReference->requestAttributeOwnershipAssumption(objectInstance, offeredAttributes, userSuppliedTag);
   }

   void FederateAmbassadorAdapter::requestDivestitureConfirmation(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & releasedAttributes,
         VariableLengthData const & )
   {
      _federateReference->requestDivestitureConfirmation(objectInstance, releasedAttributes);
   }

   void FederateAmbassadorAdapter::attributeOwnershipAcquisitionNotification(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & securedAttributes,
         VariableLengthData const & userSuppliedTag)
   {
      _federateReference->attributeOwnershipAcquisitionNotification(objectInstance, securedAttributes, userSuppliedTag);
   }

   void FederateAmbassadorAdapter::attributeOwnershipUnavailable(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         VariableLengthData const &)
   {
      _federateReference->attributeOwnershipUnavailable(objectInstance, attributes);
   }

   void FederateAmbassadorAdapter::requestAttributeOwnershipRelease(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & candidateAttributes,
         VariableLengthData const & userSuppliedTag)
   {
      _federateReference->requestAttributeOwnershipRelease(objectInstance, candidateAttributes, userSuppliedTag);
   }

   void FederateAmbassadorAdapter::confirmAttributeOwnershipAcquisitionCancellation(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes)
   {
      _federateReference->confirmAttributeOwnershipAcquisitionCancellation(objectInstance, attributes);
   }

   void FederateAmbassadorAdapter::informAttributeOwnership(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         FederateHandle const & owner)
   {
      for (const auto & attribute : attributes) {
         _federateReference->informAttributeOwnership(objectInstance, attribute, owner);
      }
   }

   void FederateAmbassadorAdapter::attributeIsNotOwned(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes)
   {
      for (const auto & attribute : attributes) {
         _federateReference->attributeIsNotOwned(objectInstance, attribute);
      }
   }

   void FederateAmbassadorAdapter::attributeIsOwnedByRTI(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes)
   {
      for (const auto & attribute : attributes) {
         _federateReference->attributeIsOwnedByRTI(objectInstance, attribute);
      }
   }

   void FederateAmbassadorAdapter::timeRegulationEnabled(
         LogicalTime const & time)
   {
      _federateReference->timeRegulationEnabled(time);
   }

   void FederateAmbassadorAdapter::timeConstrainedEnabled(
         LogicalTime const & time)
   {
      _federateReference->timeConstrainedEnabled(time);
   }

   void FederateAmbassadorAdapter::timeAdvanceGrant(
         LogicalTime const & time)
   {
      _federateReference->timeAdvanceGrant(time);
   }

   void FederateAmbassadorAdapter::requestRetraction(
         MessageRetractionHandle const & retraction)
   {
      _federateReference->requestRetraction(retraction);
   }

} // FedPro
