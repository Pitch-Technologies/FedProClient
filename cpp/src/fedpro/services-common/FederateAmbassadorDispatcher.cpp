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

#include "FederateAmbassadorDispatcher.h"

#if (RTI_HLA_VERSION >= 2024)
#include <RTI/time/LogicalTime.h>
#else
#include "services-2010/FederateAmbassadorAdapter.h"

#include <RTI/LogicalTime.h>
#endif

namespace FedPro
{
   FederateAmbassadorDispatcher::FederateAmbassadorDispatcher(
         RTI_NAMESPACE::FederateAmbassador * federateReference,
         std::shared_ptr<FedPro::ClientConverter> clientConverter)
#if (RTI_HLA_VERSION >= 2024)
         : _federateReference(federateReference),
#else
         : _federateReference(std::make_unique<FederateAmbassadorAdapter>(federateReference)),
#endif
           _clientConverter(std::move(clientConverter))
   {
   }

   FederateAmbassadorDispatcher::~FederateAmbassadorDispatcher() = default;

   void FederateAmbassadorDispatcher::prefetch(RTIambassadorClient * client)
   {
#if (RTI_HLA_VERSION < 2024)
      _federateReference->prefetch(client);
#endif
   }

   void FederateAmbassadorDispatcher::dispatchCallback(std::unique_ptr<rti1516_202X::fedpro::CallbackRequest> callback) {
      switch (callback->callbackRequest_case()) {
         case rti1516_202X::fedpro::CallbackRequest::kConnectionLost: {
            auto request = callback->mutable_connectionlost();
            _federateReference->connectionLost(
                _clientConverter->convertToHlaAndDelete(request->release_faultdescription()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kReportFederationExecutions: {
            auto request = callback->mutable_reportfederationexecutions();
            _federateReference->reportFederationExecutions(
                _clientConverter->convertToHlaAndDelete(request->release_report()));
            break;
         }

#if (RTI_HLA_VERSION >= 2024)
         case rti1516_202X::fedpro::CallbackRequest::kReportFederationExecutionMembers: {
            auto request = callback->mutable_reportfederationexecutionmembers();
            _federateReference->reportFederationExecutionMembers(
                _clientConverter->convertToHlaAndDelete(request->release_federationname()),
                _clientConverter->convertToHlaAndDelete(request->release_report()));
            break;
         }

#endif
#if (RTI_HLA_VERSION >= 2024)
         case rti1516_202X::fedpro::CallbackRequest::kReportFederationExecutionDoesNotExist: {
            auto request = callback->mutable_reportfederationexecutiondoesnotexist();
            _federateReference->reportFederationExecutionDoesNotExist(
                _clientConverter->convertToHlaAndDelete(request->release_federationname()));
            break;
         }

#endif
#if (RTI_HLA_VERSION >= 2024)
         case rti1516_202X::fedpro::CallbackRequest::kFederateResigned: {
            auto request = callback->mutable_federateresigned();
            _federateReference->federateResigned(
                _clientConverter->convertToHlaAndDelete(request->release_reasonforresigndescription()));
            break;
         }

#endif
         case rti1516_202X::fedpro::CallbackRequest::kSynchronizationPointRegistrationSucceeded: {
            auto request = callback->mutable_synchronizationpointregistrationsucceeded();
            _federateReference->synchronizationPointRegistrationSucceeded(
                _clientConverter->convertToHlaAndDelete(request->release_synchronizationpointlabel()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kSynchronizationPointRegistrationFailed: {
            auto request = callback->mutable_synchronizationpointregistrationfailed();
            _federateReference->synchronizationPointRegistrationFailed(
                _clientConverter->convertToHlaAndDelete(request->release_synchronizationpointlabel()),
                _clientConverter->convertToHla(request->reason()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kAnnounceSynchronizationPoint: {
            auto request = callback->mutable_announcesynchronizationpoint();
            _federateReference->announceSynchronizationPoint(
                _clientConverter->convertToHlaAndDelete(request->release_synchronizationpointlabel()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kFederationSynchronized: {
            auto request = callback->mutable_federationsynchronized();
            _federateReference->federationSynchronized(
                _clientConverter->convertToHlaAndDelete(request->release_synchronizationpointlabel()),
                _clientConverter->convertToHlaAndDelete(request->release_failedtosyncset()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kInitiateFederateSave: {
            auto request = callback->mutable_initiatefederatesave();
            _federateReference->initiateFederateSave(
                _clientConverter->convertToHlaAndDelete(request->release_label()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kInitiateFederateSaveWithTime: {
            auto request = callback->mutable_initiatefederatesavewithtime();
            _federateReference->initiateFederateSave(
                _clientConverter->convertToHlaAndDelete(request->release_label()),
                *_clientConverter->convertToHlaAndDelete(request->release_time()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kFederationSaved: {
            _federateReference->federationSaved(
);
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kFederationNotSaved: {
            auto request = callback->mutable_federationnotsaved();
            _federateReference->federationNotSaved(
                _clientConverter->convertToHla(request->reason()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kFederationSaveStatusResponse: {
            auto request = callback->mutable_federationsavestatusresponse();
            _federateReference->federationSaveStatusResponse(
                _clientConverter->convertToHlaAndDelete(request->release_response()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kRequestFederationRestoreSucceeded: {
            auto request = callback->mutable_requestfederationrestoresucceeded();
            _federateReference->requestFederationRestoreSucceeded(
                _clientConverter->convertToHlaAndDelete(request->release_label()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kRequestFederationRestoreFailed: {
            auto request = callback->mutable_requestfederationrestorefailed();
            _federateReference->requestFederationRestoreFailed(
                _clientConverter->convertToHlaAndDelete(request->release_label()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kFederationRestoreBegun: {
            _federateReference->federationRestoreBegun(
);
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kInitiateFederateRestore: {
            auto request = callback->mutable_initiatefederaterestore();
            _federateReference->initiateFederateRestore(
                _clientConverter->convertToHlaAndDelete(request->release_label()),
                _clientConverter->convertToHlaAndDelete(request->release_federatename()),
                _clientConverter->convertToHlaAndDelete(request->release_postrestorefederatehandle()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kFederationRestored: {
            _federateReference->federationRestored(
);
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kFederationNotRestored: {
            auto request = callback->mutable_federationnotrestored();
            _federateReference->federationNotRestored(
                _clientConverter->convertToHla(request->reason()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kFederationRestoreStatusResponse: {
            auto request = callback->mutable_federationrestorestatusresponse();
            _federateReference->federationRestoreStatusResponse(
                _clientConverter->convertToHlaAndDelete(request->release_response()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kStartRegistrationForObjectClass: {
            auto request = callback->mutable_startregistrationforobjectclass();
            _federateReference->startRegistrationForObjectClass(
                _clientConverter->convertToHlaAndDelete(request->release_objectclass()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kStopRegistrationForObjectClass: {
            auto request = callback->mutable_stopregistrationforobjectclass();
            _federateReference->stopRegistrationForObjectClass(
                _clientConverter->convertToHlaAndDelete(request->release_objectclass()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kTurnInteractionsOn: {
            auto request = callback->mutable_turninteractionson();
            _federateReference->turnInteractionsOn(
                _clientConverter->convertToHlaAndDelete(request->release_interactionclass()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kTurnInteractionsOff: {
            auto request = callback->mutable_turninteractionsoff();
            _federateReference->turnInteractionsOff(
                _clientConverter->convertToHlaAndDelete(request->release_interactionclass()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kObjectInstanceNameReservationSucceeded: {
            auto request = callback->mutable_objectinstancenamereservationsucceeded();
            _federateReference->objectInstanceNameReservationSucceeded(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstancename()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kObjectInstanceNameReservationFailed: {
            auto request = callback->mutable_objectinstancenamereservationfailed();
            _federateReference->objectInstanceNameReservationFailed(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstancename()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kMultipleObjectInstanceNameReservationSucceeded: {
            auto request = callback->mutable_multipleobjectinstancenamereservationsucceeded();
            _federateReference->multipleObjectInstanceNameReservationSucceeded(
                _clientConverter->convertToHlaAndMove(request->mutable_objectinstancenames()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kMultipleObjectInstanceNameReservationFailed: {
            auto request = callback->mutable_multipleobjectinstancenamereservationfailed();
            _federateReference->multipleObjectInstanceNameReservationFailed(
                _clientConverter->convertToHlaAndMove(request->mutable_objectinstancenames()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kDiscoverObjectInstance: {
            auto request = callback->mutable_discoverobjectinstance();
            _federateReference->discoverObjectInstance(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_objectclass()),
                _clientConverter->convertToHlaAndDelete(request->release_objectinstancename()),
                _clientConverter->convertToHlaAndDelete(request->release_producingfederate()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kReflectAttributeValues: {
            auto request = callback->mutable_reflectattributevalues();
            _federateReference->reflectAttributeValues(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attributevalues()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()),
                _clientConverter->convertToHlaAndDelete(request->release_transportationtype()),
                _clientConverter->convertToHlaAndDelete(request->release_producingfederate()),
                _clientConverter->convertToHlaAndDelete(request->release_optionalsentregions()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kReflectAttributeValuesWithTime: {
            auto request = callback->mutable_reflectattributevalueswithtime();
            std::unique_ptr<RTI_NAMESPACE::MessageRetractionHandle> optionalretraction;
            if (request->has_optionalretraction()) {
                optionalretraction = _clientConverter->convertToHlaAndDelete(request->release_optionalretraction());
            }
            _federateReference->reflectAttributeValues(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attributevalues()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()),
                _clientConverter->convertToHlaAndDelete(request->release_transportationtype()),
                _clientConverter->convertToHlaAndDelete(request->release_producingfederate()),
                _clientConverter->convertToHlaAndDelete(request->release_optionalsentregions()),
                *_clientConverter->convertToHlaAndDelete(request->release_time()),
                _clientConverter->convertToHla(request->sentordertype()),
                _clientConverter->convertToHla(request->receivedordertype()),
                optionalretraction.get());
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kReceiveInteraction: {
            auto request = callback->mutable_receiveinteraction();
            _federateReference->receiveInteraction(
                _clientConverter->convertToHlaAndDelete(request->release_interactionclass()),
                _clientConverter->convertToHlaAndDelete(request->release_parametervalues()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()),
                _clientConverter->convertToHlaAndDelete(request->release_transportationtype()),
                _clientConverter->convertToHlaAndDelete(request->release_producingfederate()),
                _clientConverter->convertToHlaAndDelete(request->release_optionalsentregions()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kReceiveInteractionWithTime: {
            auto request = callback->mutable_receiveinteractionwithtime();
            std::unique_ptr<RTI_NAMESPACE::MessageRetractionHandle> optionalretraction;
            if (request->has_optionalretraction()) {
                optionalretraction = _clientConverter->convertToHlaAndDelete(request->release_optionalretraction());
            }
            _federateReference->receiveInteraction(
                _clientConverter->convertToHlaAndDelete(request->release_interactionclass()),
                _clientConverter->convertToHlaAndDelete(request->release_parametervalues()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()),
                _clientConverter->convertToHlaAndDelete(request->release_transportationtype()),
                _clientConverter->convertToHlaAndDelete(request->release_producingfederate()),
                _clientConverter->convertToHlaAndDelete(request->release_optionalsentregions()),
                *_clientConverter->convertToHlaAndDelete(request->release_time()),
                _clientConverter->convertToHla(request->sentordertype()),
                _clientConverter->convertToHla(request->receivedordertype()),
                optionalretraction.get());
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kReceiveDirectedInteraction: {
            auto request = callback->mutable_receivedirectedinteraction();
            _federateReference->receiveDirectedInteraction(
                _clientConverter->convertToHlaAndDelete(request->release_interactionclass()),
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_parametervalues()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()),
                _clientConverter->convertToHlaAndDelete(request->release_transportationtype()),
                _clientConverter->convertToHlaAndDelete(request->release_producingfederate()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kReceiveDirectedInteractionWithTime: {
            auto request = callback->mutable_receivedirectedinteractionwithtime();
            std::unique_ptr<RTI_NAMESPACE::MessageRetractionHandle> optionalretraction;
            if (request->has_optionalretraction()) {
                optionalretraction = _clientConverter->convertToHlaAndDelete(request->release_optionalretraction());
            }
            _federateReference->receiveDirectedInteraction(
                _clientConverter->convertToHlaAndDelete(request->release_interactionclass()),
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_parametervalues()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()),
                _clientConverter->convertToHlaAndDelete(request->release_transportationtype()),
                _clientConverter->convertToHlaAndDelete(request->release_producingfederate()),
                *_clientConverter->convertToHlaAndDelete(request->release_time()),
                _clientConverter->convertToHla(request->sentordertype()),
                _clientConverter->convertToHla(request->receivedordertype()),
                optionalretraction.get());
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kRemoveObjectInstance: {
            auto request = callback->mutable_removeobjectinstance();
            _federateReference->removeObjectInstance(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()),
                _clientConverter->convertToHlaAndDelete(request->release_producingfederate()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kRemoveObjectInstanceWithTime: {
            auto request = callback->mutable_removeobjectinstancewithtime();
            std::unique_ptr<RTI_NAMESPACE::MessageRetractionHandle> optionalretraction;
            if (request->has_optionalretraction()) {
                optionalretraction = _clientConverter->convertToHlaAndDelete(request->release_optionalretraction());
            }
            _federateReference->removeObjectInstance(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()),
                _clientConverter->convertToHlaAndDelete(request->release_producingfederate()),
                *_clientConverter->convertToHlaAndDelete(request->release_time()),
                _clientConverter->convertToHla(request->sentordertype()),
                _clientConverter->convertToHla(request->receivedordertype()),
                optionalretraction.get());
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kAttributesInScope: {
            auto request = callback->mutable_attributesinscope();
            _federateReference->attributesInScope(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attributes()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kAttributesOutOfScope: {
            auto request = callback->mutable_attributesoutofscope();
            _federateReference->attributesOutOfScope(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attributes()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kProvideAttributeValueUpdate: {
            auto request = callback->mutable_provideattributevalueupdate();
            _federateReference->provideAttributeValueUpdate(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attributes()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kTurnUpdatesOnForObjectInstance: {
            auto request = callback->mutable_turnupdatesonforobjectinstance();
            _federateReference->turnUpdatesOnForObjectInstance(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attributes()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kTurnUpdatesOnForObjectInstanceWithRate: {
            auto request = callback->mutable_turnupdatesonforobjectinstancewithrate();
            _federateReference->turnUpdatesOnForObjectInstance(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attributes()),
                _clientConverter->convertToHlaAndDelete(request->release_updateratedesignator()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kTurnUpdatesOffForObjectInstance: {
            auto request = callback->mutable_turnupdatesoffforobjectinstance();
            _federateReference->turnUpdatesOffForObjectInstance(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attributes()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kConfirmAttributeTransportationTypeChange: {
            auto request = callback->mutable_confirmattributetransportationtypechange();
            _federateReference->confirmAttributeTransportationTypeChange(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attributes()),
                _clientConverter->convertToHlaAndDelete(request->release_transportationtype()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kReportAttributeTransportationType: {
            auto request = callback->mutable_reportattributetransportationtype();
            _federateReference->reportAttributeTransportationType(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attribute()),
                _clientConverter->convertToHlaAndDelete(request->release_transportationtype()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kConfirmInteractionTransportationTypeChange: {
            auto request = callback->mutable_confirminteractiontransportationtypechange();
            _federateReference->confirmInteractionTransportationTypeChange(
                _clientConverter->convertToHlaAndDelete(request->release_interactionclass()),
                _clientConverter->convertToHlaAndDelete(request->release_transportationtype()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kReportInteractionTransportationType: {
            auto request = callback->mutable_reportinteractiontransportationtype();
            _federateReference->reportInteractionTransportationType(
                _clientConverter->convertToHlaAndDelete(request->release_federate()),
                _clientConverter->convertToHlaAndDelete(request->release_interactionclass()),
                _clientConverter->convertToHlaAndDelete(request->release_transportationtype()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kRequestAttributeOwnershipAssumption: {
            auto request = callback->mutable_requestattributeownershipassumption();
            _federateReference->requestAttributeOwnershipAssumption(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_offeredattributes()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kRequestDivestitureConfirmation: {
            auto request = callback->mutable_requestdivestitureconfirmation();
            _federateReference->requestDivestitureConfirmation(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_releasedattributes()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kAttributeOwnershipAcquisitionNotification: {
            auto request = callback->mutable_attributeownershipacquisitionnotification();
            _federateReference->attributeOwnershipAcquisitionNotification(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_securedattributes()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kAttributeOwnershipUnavailable: {
            auto request = callback->mutable_attributeownershipunavailable();
            _federateReference->attributeOwnershipUnavailable(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attributes()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kRequestAttributeOwnershipRelease: {
            auto request = callback->mutable_requestattributeownershiprelease();
            _federateReference->requestAttributeOwnershipRelease(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_candidateattributes()),
                _clientConverter->convertToHlaByteArray(request->usersuppliedtag()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kConfirmAttributeOwnershipAcquisitionCancellation: {
            auto request = callback->mutable_confirmattributeownershipacquisitioncancellation();
            _federateReference->confirmAttributeOwnershipAcquisitionCancellation(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attributes()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kInformAttributeOwnership: {
            auto request = callback->mutable_informattributeownership();
            _federateReference->informAttributeOwnership(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attributes()),
                _clientConverter->convertToHlaAndDelete(request->release_federate()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kAttributeIsNotOwned: {
            auto request = callback->mutable_attributeisnotowned();
            _federateReference->attributeIsNotOwned(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attributes()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kAttributeIsOwnedByRTI: {
            auto request = callback->mutable_attributeisownedbyrti();
            _federateReference->attributeIsOwnedByRTI(
                _clientConverter->convertToHlaAndDelete(request->release_objectinstance()),
                _clientConverter->convertToHlaAndDelete(request->release_attributes()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kTimeRegulationEnabled: {
            auto request = callback->mutable_timeregulationenabled();
            _federateReference->timeRegulationEnabled(
                *_clientConverter->convertToHlaAndDelete(request->release_time()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kTimeConstrainedEnabled: {
            auto request = callback->mutable_timeconstrainedenabled();
            _federateReference->timeConstrainedEnabled(
                *_clientConverter->convertToHlaAndDelete(request->release_time()));
            break;
         }

#if (RTI_HLA_VERSION >= 2024)
         case rti1516_202X::fedpro::CallbackRequest::kFlushQueueGrant: {
            auto request = callback->mutable_flushqueuegrant();
            _federateReference->flushQueueGrant(
                *_clientConverter->convertToHlaAndDelete(request->release_time()),
                *_clientConverter->convertToHlaAndDelete(request->release_optimistictime()));
            break;
         }

#endif
         case rti1516_202X::fedpro::CallbackRequest::kTimeAdvanceGrant: {
            auto request = callback->mutable_timeadvancegrant();
            _federateReference->timeAdvanceGrant(
                *_clientConverter->convertToHlaAndDelete(request->release_time()));
            break;
         }

         case rti1516_202X::fedpro::CallbackRequest::kRequestRetraction: {
            auto request = callback->mutable_requestretraction();
            _federateReference->requestRetraction(
                *_clientConverter->convertToHlaAndDelete(request->release_retraction()));
            break;
         }

         default:
            throw RTI_NAMESPACE::FederateInternalError(
                  L"Unknown callback: " + std::to_wstring(callback->callbackRequest_case()));
      }
   }
}
