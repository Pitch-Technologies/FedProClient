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
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.OrderType;
import hla.rti1516e.exceptions.FederateInternalError;
import hla.rti1516e.exceptions.RTIinternalError;

public class FederateAmbassadorDispatcher {
   final FederateAmbassadorClientAdapter _federateReference;
   final ClientConverter _clientConverter;

   public FederateAmbassadorDispatcher(FederateAmbassador federateAmbassador, ClientConverter clientConverter)
   {
      _federateReference = new FederateAmbassadorClientAdapter(federateAmbassador);
      _clientConverter = clientConverter;
   }

   void dispatchCallback(CallbackRequest callback)
   throws
         FederateInternalError,
         RTIinternalError
   {
      switch (callback.getCallbackRequestCase()) {
         case SYNCHRONIZATIONPOINTREGISTRATIONSUCCEEDED: {
            SynchronizationPointRegistrationSucceeded request = callback.getSynchronizationPointRegistrationSucceeded();
            _federateReference.synchronizationPointRegistrationSucceeded(
                  _clientConverter.convertToHla(request.getSynchronizationPointLabel()));
            break;
         }

         case SYNCHRONIZATIONPOINTREGISTRATIONFAILED: {
            SynchronizationPointRegistrationFailed request = callback.getSynchronizationPointRegistrationFailed();
            _federateReference.synchronizationPointRegistrationFailed(
                  _clientConverter.convertToHla(request.getSynchronizationPointLabel()),
                  _clientConverter.convertToHla(request.getReason()));
            break;
         }

         case ANNOUNCESYNCHRONIZATIONPOINT: {
            AnnounceSynchronizationPoint request = callback.getAnnounceSynchronizationPoint();
            _federateReference.announceSynchronizationPoint(
                  _clientConverter.convertToHla(request.getSynchronizationPointLabel()),
                  _clientConverter.convertToHla(request.getUserSuppliedTag()));
            break;
         }

         case FEDERATIONSYNCHRONIZED: {
            FederationSynchronized request = callback.getFederationSynchronized();
            _federateReference.federationSynchronized(
                  _clientConverter.convertToHla(request.getSynchronizationPointLabel()),
                  _clientConverter.convertToHla(request.getFailedToSyncSet()));
            break;
         }

         case INITIATEFEDERATESAVE: {
            InitiateFederateSave request = callback.getInitiateFederateSave();
            _federateReference.initiateFederateSave(
                  _clientConverter.convertToHla(request.getLabel()));
            break;
         }

         case INITIATEFEDERATESAVEWITHTIME: {
            InitiateFederateSaveWithTime request = callback.getInitiateFederateSaveWithTime();
            _federateReference.initiateFederateSaveWithTime(
                  _clientConverter.convertToHla(request.getLabel()),
                  _clientConverter.convertToHla(request.getTime()));
            break;
         }

         case FEDERATIONSAVED: {
            FederationSaved request = callback.getFederationSaved();
            _federateReference.federationSaved(
            );
            break;
         }

         case FEDERATIONNOTSAVED: {
            FederationNotSaved request = callback.getFederationNotSaved();
            _federateReference.federationNotSaved(
                  _clientConverter.convertToHla(request.getReason()));
            break;
         }

         case FEDERATIONSAVESTATUSRESPONSE: {
            FederationSaveStatusResponse request = callback.getFederationSaveStatusResponse();
            _federateReference.federationSaveStatusResponse(
                  _clientConverter.convertToHla(request.getResponse()));
            break;
         }

         case REQUESTFEDERATIONRESTORESUCCEEDED: {
            RequestFederationRestoreSucceeded request = callback.getRequestFederationRestoreSucceeded();
            _federateReference.requestFederationRestoreSucceeded(
                  _clientConverter.convertToHla(request.getLabel()));
            break;
         }

         case REQUESTFEDERATIONRESTOREFAILED: {
            RequestFederationRestoreFailed request = callback.getRequestFederationRestoreFailed();
            _federateReference.requestFederationRestoreFailed(
                  _clientConverter.convertToHla(request.getLabel()));
            break;
         }

         case FEDERATIONRESTOREBEGUN: {
            FederationRestoreBegun request = callback.getFederationRestoreBegun();
            _federateReference.federationRestoreBegun(
            );
            break;
         }

         case INITIATEFEDERATERESTORE: {
            InitiateFederateRestore request = callback.getInitiateFederateRestore();
            _federateReference.initiateFederateRestore(
                  _clientConverter.convertToHla(request.getLabel()),
                  _clientConverter.convertToHla(request.getFederateName()),
                  _clientConverter.convertToHla(request.getPostRestoreFederateHandle()));
            break;
         }

         case FEDERATIONRESTORED: {
            FederationRestored request = callback.getFederationRestored();
            _federateReference.federationRestored(
            );
            break;
         }

         case FEDERATIONNOTRESTORED: {
            FederationNotRestored request = callback.getFederationNotRestored();
            _federateReference.federationNotRestored(
                  _clientConverter.convertToHla(request.getReason()));
            break;
         }

         case FEDERATIONRESTORESTATUSRESPONSE: {
            FederationRestoreStatusResponse request = callback.getFederationRestoreStatusResponse();
            _federateReference.federationRestoreStatusResponse(
                  _clientConverter.convertToHla(request.getResponse()));
            break;
         }

         case CONNECTIONLOST: {
            ConnectionLost request = callback.getConnectionLost();
            _federateReference.connectionLost(
                  _clientConverter.convertToHla(request.getFaultDescription()));
            break;
         }

         case REPORTFEDERATIONEXECUTIONS: {
            ReportFederationExecutions request = callback.getReportFederationExecutions();
            _federateReference.reportFederationExecutions(
                  _clientConverter.convertToHla(request.getReport()));
            break;
         }

         case STARTREGISTRATIONFOROBJECTCLASS: {
            StartRegistrationForObjectClass request = callback.getStartRegistrationForObjectClass();
            _federateReference.startRegistrationForObjectClass(
                  _clientConverter.convertToHla(request.getObjectClass()));
            break;
         }

         case STOPREGISTRATIONFOROBJECTCLASS: {
            StopRegistrationForObjectClass request = callback.getStopRegistrationForObjectClass();
            _federateReference.stopRegistrationForObjectClass(
                  _clientConverter.convertToHla(request.getObjectClass()));
            break;
         }

         case TURNINTERACTIONSON: {
            TurnInteractionsOn request = callback.getTurnInteractionsOn();
            _federateReference.turnInteractionsOn(
                  _clientConverter.convertToHla(request.getInteractionClass()));
            break;
         }

         case TURNINTERACTIONSOFF: {
            TurnInteractionsOff request = callback.getTurnInteractionsOff();
            _federateReference.turnInteractionsOff(
                  _clientConverter.convertToHla(request.getInteractionClass()));
            break;
         }

         case OBJECTINSTANCENAMERESERVATIONSUCCEEDED: {
            ObjectInstanceNameReservationSucceeded request = callback.getObjectInstanceNameReservationSucceeded();
            _federateReference.objectInstanceNameReservationSucceeded(
                  _clientConverter.convertToHla(request.getObjectInstanceName()));
            break;
         }

         case MULTIPLEOBJECTINSTANCENAMERESERVATIONSUCCEEDED: {
            MultipleObjectInstanceNameReservationSucceeded request =
                  callback.getMultipleObjectInstanceNameReservationSucceeded();
            _federateReference.multipleObjectInstanceNameReservationSucceeded(
                  _clientConverter.convertToHla(request.getObjectInstanceNamesList()));
            break;
         }

         case OBJECTINSTANCENAMERESERVATIONFAILED: {
            ObjectInstanceNameReservationFailed request = callback.getObjectInstanceNameReservationFailed();
            _federateReference.objectInstanceNameReservationFailed(
                  _clientConverter.convertToHla(request.getObjectInstanceName()));
            break;
         }

         case MULTIPLEOBJECTINSTANCENAMERESERVATIONFAILED: {
            MultipleObjectInstanceNameReservationFailed request =
                  callback.getMultipleObjectInstanceNameReservationFailed();
            _federateReference.multipleObjectInstanceNameReservationFailed(
                  _clientConverter.convertToHla(request.getObjectInstanceNamesList()));
            break;
         }

         case DISCOVEROBJECTINSTANCE: {
            DiscoverObjectInstance request = callback.getDiscoverObjectInstance();
            _federateReference.discoverObjectInstance(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getObjectClass()),
                  _clientConverter.convertToHla(request.getObjectInstanceName()));
            break;
         }

         case REFLECTATTRIBUTEVALUES: {
            ReflectAttributeValues request = callback.getReflectAttributeValues();
            _federateReference.reflectAttributeValues(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getAttributeValues()),
                  _clientConverter.convertToHla(request.getUserSuppliedTag()),
                  OrderType.RECEIVE,
                  _clientConverter.convertToHla(request.getTransportationType()),
                  _clientConverter.convertToHla(request.getOptionalSentRegions(), request.getProducingFederate()));
            break;
         }

         case REFLECTATTRIBUTEVALUESWITHTIME: {
            ReflectAttributeValuesWithTime request = callback.getReflectAttributeValuesWithTime();
            if (request.hasOptionalRetraction()) {
               _federateReference.reflectAttributeValuesWithRetraction(
                     _clientConverter.convertToHla(request.getObjectInstance()),
                     _clientConverter.convertToHla(request.getAttributeValues()),
                     _clientConverter.convertToHla(request.getUserSuppliedTag()),
                     _clientConverter.convertToHla(request.getSentOrderType()),
                     _clientConverter.convertToHla(request.getTransportationType()),
                     _clientConverter.convertToHla(request.getTime()),
                     _clientConverter.convertToHla(request.getReceivedOrderType()),
                     _clientConverter.convertToHla(request.getOptionalRetraction()),
                     _clientConverter.convertToHla(request.getOptionalSentRegions(), request.getProducingFederate()));
            } else {
               _federateReference.reflectAttributeValuesWithTime(
                     _clientConverter.convertToHla(request.getObjectInstance()),
                     _clientConverter.convertToHla(request.getAttributeValues()),
                     _clientConverter.convertToHla(request.getUserSuppliedTag()),
                     _clientConverter.convertToHla(request.getSentOrderType()),
                     _clientConverter.convertToHla(request.getTransportationType()),
                     _clientConverter.convertToHla(request.getTime()),
                     _clientConverter.convertToHla(request.getReceivedOrderType()),
                     _clientConverter.convertToHla(request.getOptionalSentRegions(), request.getProducingFederate()));
            }
            break;
         }

         case RECEIVEINTERACTION: {
            ReceiveInteraction request = callback.getReceiveInteraction();
            _federateReference.receiveInteraction(
                  _clientConverter.convertToHla(request.getInteractionClass()),
                  _clientConverter.convertToHla(request.getParameterValues()),
                  _clientConverter.convertToHla(request.getUserSuppliedTag()),
                  OrderType.RECEIVE,
                  _clientConverter.convertToHla(request.getTransportationType()),
                  _clientConverter.convertToHlaSupplementalReceiveInfo(
                        request.getOptionalSentRegions(),
                        request.getProducingFederate()));
            break;
         }

         case RECEIVEINTERACTIONWITHTIME: {
            ReceiveInteractionWithTime request = callback.getReceiveInteractionWithTime();
            if (request.hasOptionalRetraction()) {
               _federateReference.receiveInteractionWithRetraction(
                     _clientConverter.convertToHla(request.getInteractionClass()),
                     _clientConverter.convertToHla(request.getParameterValues()),
                     _clientConverter.convertToHla(request.getUserSuppliedTag()),
                     _clientConverter.convertToHla(request.getSentOrderType()),
                     _clientConverter.convertToHla(request.getTransportationType()),
                     _clientConverter.convertToHla(request.getTime()),
                     _clientConverter.convertToHla(request.getReceivedOrderType()),
                     _clientConverter.convertToHla(request.getOptionalRetraction()),
                     _clientConverter.convertToHlaSupplementalReceiveInfo(
                           request.getOptionalSentRegions(),
                           request.getProducingFederate()));
            } else {
               _federateReference.receiveInteractionWithTime(
                     _clientConverter.convertToHla(request.getInteractionClass()),
                     _clientConverter.convertToHla(request.getParameterValues()),
                     _clientConverter.convertToHla(request.getUserSuppliedTag()),
                     _clientConverter.convertToHla(request.getSentOrderType()),
                     _clientConverter.convertToHla(request.getTransportationType()),
                     _clientConverter.convertToHla(request.getTime()),
                     _clientConverter.convertToHla(request.getReceivedOrderType()),
                     _clientConverter.convertToHlaSupplementalReceiveInfo(
                           request.getOptionalSentRegions(),
                           request.getProducingFederate()));
            }
            break;
         }

         case REMOVEOBJECTINSTANCE: {
            RemoveObjectInstance request = callback.getRemoveObjectInstance();
            _federateReference.removeObjectInstance(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getUserSuppliedTag()),
                  OrderType.RECEIVE,
                  _clientConverter.convertToHlaSupplementalRemoveInfo(
                        _clientConverter.convertToHla(request.getProducingFederate())));
            break;
         }

         case REMOVEOBJECTINSTANCEWITHTIME: {
            RemoveObjectInstanceWithTime request = callback.getRemoveObjectInstanceWithTime();
            if (request.hasOptionalRetraction()) {
               _federateReference.removeObjectInstanceWithRetraction(
                     _clientConverter.convertToHla(request.getObjectInstance()),
                     _clientConverter.convertToHla(request.getUserSuppliedTag()),
                     _clientConverter.convertToHla(request.getSentOrderType()),
                     _clientConverter.convertToHla(request.getTime()),
                     _clientConverter.convertToHla(request.getReceivedOrderType()),
                     _clientConverter.convertToHla(request.getOptionalRetraction()),
                     _clientConverter.convertToHlaSupplementalRemoveInfo(
                           _clientConverter.convertToHla(request.getProducingFederate())));
            } else {
               _federateReference.removeObjectInstanceWithTime(
                     _clientConverter.convertToHla(request.getObjectInstance()),
                     _clientConverter.convertToHla(request.getUserSuppliedTag()),
                     _clientConverter.convertToHla(request.getSentOrderType()),
                     _clientConverter.convertToHla(request.getTime()),
                     _clientConverter.convertToHla(request.getReceivedOrderType()),
                     _clientConverter.convertToHlaSupplementalRemoveInfo(
                           _clientConverter.convertToHla(request.getProducingFederate())));
            }
            break;
         }

         case ATTRIBUTESINSCOPE: {
            AttributesInScope request = callback.getAttributesInScope();
            _federateReference.attributesInScope(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getAttributes()));
            break;
         }

         case ATTRIBUTESOUTOFSCOPE: {
            AttributesOutOfScope request = callback.getAttributesOutOfScope();
            _federateReference.attributesOutOfScope(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getAttributes()));
            break;
         }

         case PROVIDEATTRIBUTEVALUEUPDATE: {
            ProvideAttributeValueUpdate request = callback.getProvideAttributeValueUpdate();
            _federateReference.provideAttributeValueUpdate(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getAttributes()),
                  _clientConverter.convertToHla(request.getUserSuppliedTag()));
            break;
         }

         case TURNUPDATESONFOROBJECTINSTANCE: {
            TurnUpdatesOnForObjectInstance request = callback.getTurnUpdatesOnForObjectInstance();
            _federateReference.turnUpdatesOnForObjectInstance(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getAttributes()));
            break;
         }

         case TURNUPDATESONFOROBJECTINSTANCEWITHRATE: {
            TurnUpdatesOnForObjectInstanceWithRate request = callback.getTurnUpdatesOnForObjectInstanceWithRate();
            _federateReference.turnUpdatesOnForObjectInstanceWithRate(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getAttributes()),
                  _clientConverter.convertToHla(request.getUpdateRateDesignator()));
            break;
         }

         case TURNUPDATESOFFFOROBJECTINSTANCE: {
            TurnUpdatesOffForObjectInstance request = callback.getTurnUpdatesOffForObjectInstance();
            _federateReference.turnUpdatesOffForObjectInstance(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getAttributes()));
            break;
         }

         case CONFIRMATTRIBUTETRANSPORTATIONTYPECHANGE: {
            ConfirmAttributeTransportationTypeChange request = callback.getConfirmAttributeTransportationTypeChange();
            _federateReference.confirmAttributeTransportationTypeChange(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getAttributes()),
                  _clientConverter.convertToHla(request.getTransportationType()));
            break;
         }

         case CONFIRMINTERACTIONTRANSPORTATIONTYPECHANGE: {
            ConfirmInteractionTransportationTypeChange request =
                  callback.getConfirmInteractionTransportationTypeChange();
            _federateReference.confirmInteractionTransportationTypeChange(
                  _clientConverter.convertToHla(request.getInteractionClass()),
                  _clientConverter.convertToHla(request.getTransportationType()));
            break;
         }

         case REPORTATTRIBUTETRANSPORTATIONTYPE: {
            ReportAttributeTransportationType request = callback.getReportAttributeTransportationType();
            _federateReference.reportAttributeTransportationType(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getAttribute()),
                  _clientConverter.convertToHla(request.getTransportationType()));
            break;
         }

         case REPORTINTERACTIONTRANSPORTATIONTYPE: {
            ReportInteractionTransportationType request = callback.getReportInteractionTransportationType();
            _federateReference.reportInteractionTransportationType(
                  _clientConverter.convertToHla(request.getFederate()),
                  _clientConverter.convertToHla(request.getInteractionClass()),
                  _clientConverter.convertToHla(request.getTransportationType()));
            break;
         }

         case REQUESTATTRIBUTEOWNERSHIPASSUMPTION: {
            RequestAttributeOwnershipAssumption request = callback.getRequestAttributeOwnershipAssumption();
            _federateReference.requestAttributeOwnershipAssumption(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getOfferedAttributes()),
                  _clientConverter.convertToHla(request.getUserSuppliedTag()));
            break;
         }

         case REQUESTDIVESTITURECONFIRMATION: {
            RequestDivestitureConfirmation request = callback.getRequestDivestitureConfirmation();
            _federateReference.requestDivestitureConfirmation(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getReleasedAttributes()));
            break;
         }

         case ATTRIBUTEOWNERSHIPACQUISITIONNOTIFICATION: {
            AttributeOwnershipAcquisitionNotification request = callback.getAttributeOwnershipAcquisitionNotification();
            _federateReference.attributeOwnershipAcquisitionNotification(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getSecuredAttributes()),
                  _clientConverter.convertToHla(request.getUserSuppliedTag()));
            break;
         }

         case ATTRIBUTEOWNERSHIPUNAVAILABLE: {
            AttributeOwnershipUnavailable request = callback.getAttributeOwnershipUnavailable();
            _federateReference.attributeOwnershipUnavailable(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getAttributes()));
            break;
         }

         case REQUESTATTRIBUTEOWNERSHIPRELEASE: {
            RequestAttributeOwnershipRelease request = callback.getRequestAttributeOwnershipRelease();
            _federateReference.requestAttributeOwnershipRelease(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getCandidateAttributes()),
                  _clientConverter.convertToHla(request.getUserSuppliedTag()));
            break;
         }

         case CONFIRMATTRIBUTEOWNERSHIPACQUISITIONCANCELLATION: {
            ConfirmAttributeOwnershipAcquisitionCancellation request =
                  callback.getConfirmAttributeOwnershipAcquisitionCancellation();
            _federateReference.confirmAttributeOwnershipAcquisitionCancellation(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getAttributes()));
            break;
         }

         case INFORMATTRIBUTEOWNERSHIP: {
            InformAttributeOwnership request = callback.getInformAttributeOwnership();
            assert _clientConverter.convertToHla(request.getAttributes()).size() == 1;
            _federateReference.informAttributeOwnership(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getAttributes()).iterator().next(),
                  _clientConverter.convertToHla(request.getFederate()));
            break;
         }

         case ATTRIBUTEISNOTOWNED: {
            AttributeIsNotOwned request = callback.getAttributeIsNotOwned();
            assert _clientConverter.convertToHla(request.getAttributes()).size() == 1;
            _federateReference.attributeIsNotOwned(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getAttributes()).iterator().next());
            break;
         }

         case ATTRIBUTEISOWNEDBYRTI: {
            AttributeIsOwnedByRTI request = callback.getAttributeIsOwnedByRTI();
            assert _clientConverter.convertToHla(request.getAttributes()).size() == 1;
            _federateReference.attributeIsOwnedByRTI(
                  _clientConverter.convertToHla(request.getObjectInstance()),
                  _clientConverter.convertToHla(request.getAttributes()).iterator().next());
            break;
         }

         case TIMEREGULATIONENABLED: {
            TimeRegulationEnabled request = callback.getTimeRegulationEnabled();
            _federateReference.timeRegulationEnabled(
                  _clientConverter.convertToHla(request.getTime()));
            break;
         }

         case TIMECONSTRAINEDENABLED: {
            TimeConstrainedEnabled request = callback.getTimeConstrainedEnabled();
            _federateReference.timeConstrainedEnabled(
                  _clientConverter.convertToHla(request.getTime()));
            break;
         }

         case TIMEADVANCEGRANT: {
            TimeAdvanceGrant request = callback.getTimeAdvanceGrant();
            _federateReference.timeAdvanceGrant(
                  _clientConverter.convertToHla(request.getTime()));
            break;
         }

         case REQUESTRETRACTION: {
            RequestRetraction request = callback.getRequestRetraction();
            _federateReference.requestRetraction(
                  _clientConverter.convertToHla(request.getRetraction()));
            break;
         }


         default: {
            System.out.println("Unknown callback: " + callback.getCallbackRequestCase());
            throw new FederateInternalError("TODO " + callback.getCallbackRequestCase() + " not supported!");
         }
      }

   }

}
