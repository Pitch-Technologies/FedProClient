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

import hla.rti1516_202X.thin.*;
import hla.rti1516_202X.FederateAmbassador;
import hla.rti1516_202X.exceptions.FederateInternalError;
import hla.rti1516_202X.exceptions.RTIinternalError;

public class FederateAmbassadorDispatcher {
   private final FederateAmbassadorClientAdapter _federateReference;
   private final ClientConverter _clientConverter;

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
        case CONNECTIONLOST: {
           ConnectionLost request = callback.getConnectionLost();
           _federateReference.connectionLost(
              _clientConverter.convertToHla(request.getFaultDescription()) );
           break;
        }

         case FEDERATERESIGNED: {
           FederateResigned request = callback.getFederateResigned();
           _federateReference.federateResigned(
              _clientConverter.convertToHla(request.getReasonForResignDescription()) );
           break;
        }

         case REPORTFEDERATIONEXECUTIONS: {
           ReportFederationExecutions request = callback.getReportFederationExecutions();
           _federateReference.reportFederationExecutions(
              _clientConverter.convertToHla(request.getTheFederationExecutionInformationSet()) );
           break;
        }

         case REPORTFEDERATIONEXECUTIONMEMBERS: {
           ReportFederationExecutionMembers request = callback.getReportFederationExecutionMembers();
           _federateReference.reportFederationExecutionMembers(
              _clientConverter.convertToHla(request.getFederationName()),
              _clientConverter.convertToHla(request.getFederationExecutionMemberInformationSet()) );
           break;
        }

         case REPORTFEDERATIONEXECUTIONDOESNOTEXIST: {
           ReportFederationExecutionDoesNotExist request = callback.getReportFederationExecutionDoesNotExist();
           _federateReference.reportFederationExecutionDoesNotExist(
              _clientConverter.convertToHla(request.getFederationName()) );
           break;
        }

         case SYNCHRONIZATIONPOINTREGISTRATIONSUCCEEDED: {
           SynchronizationPointRegistrationSucceeded request = callback.getSynchronizationPointRegistrationSucceeded();
           _federateReference.synchronizationPointRegistrationSucceeded(
              _clientConverter.convertToHla(request.getSynchronizationPointLabel()) );
           break;
        }

         case SYNCHRONIZATIONPOINTREGISTRATIONFAILED: {
           SynchronizationPointRegistrationFailed request = callback.getSynchronizationPointRegistrationFailed();
           _federateReference.synchronizationPointRegistrationFailed(
              _clientConverter.convertToHla(request.getSynchronizationPointLabel()),
              _clientConverter.convertToHla(request.getReason()) );
           break;
        }

         case ANNOUNCESYNCHRONIZATIONPOINT: {
           AnnounceSynchronizationPoint request = callback.getAnnounceSynchronizationPoint();
           _federateReference.announceSynchronizationPoint(
              _clientConverter.convertToHla(request.getSynchronizationPointLabel()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()) );
           break;
        }

         case FEDERATIONSYNCHRONIZED: {
           FederationSynchronized request = callback.getFederationSynchronized();
           _federateReference.federationSynchronized(
              _clientConverter.convertToHla(request.getSynchronizationPointLabel()),
              _clientConverter.convertToHla(request.getFailedToSyncSet()) );
           break;
        }

         case INITIATEFEDERATESAVE: {
           InitiateFederateSave request = callback.getInitiateFederateSave();
           _federateReference.initiateFederateSave(
              _clientConverter.convertToHla(request.getLabel()) );
           break;
        }

         case INITIATEFEDERATESAVEWITHTIME: {
           InitiateFederateSaveWithTime request = callback.getInitiateFederateSaveWithTime();
           _federateReference.initiateFederateSaveWithTime(
              _clientConverter.convertToHla(request.getLabel()),
              _clientConverter.convertToHla(request.getTime()) );
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
              _clientConverter.convertToHla(request.getReason()) );
           break;
        }

         case FEDERATIONSAVESTATUSRESPONSE: {
           FederationSaveStatusResponse request = callback.getFederationSaveStatusResponse();
           _federateReference.federationSaveStatusResponse(
              _clientConverter.convertToHla(request.getResponse()) );
           break;
        }

         case REQUESTFEDERATIONRESTORESUCCEEDED: {
           RequestFederationRestoreSucceeded request = callback.getRequestFederationRestoreSucceeded();
           _federateReference.requestFederationRestoreSucceeded(
              _clientConverter.convertToHla(request.getLabel()) );
           break;
        }

         case REQUESTFEDERATIONRESTOREFAILED: {
           RequestFederationRestoreFailed request = callback.getRequestFederationRestoreFailed();
           _federateReference.requestFederationRestoreFailed(
              _clientConverter.convertToHla(request.getLabel()) );
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
              _clientConverter.convertToHla(request.getFederateHandle()) );
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
              _clientConverter.convertToHla(request.getReason()) );
           break;
        }

         case FEDERATIONRESTORESTATUSRESPONSE: {
           FederationRestoreStatusResponse request = callback.getFederationRestoreStatusResponse();
           _federateReference.federationRestoreStatusResponse(
              _clientConverter.convertToHla(request.getResponse()) );
           break;
        }

         case STARTREGISTRATIONFOROBJECTCLASS: {
           StartRegistrationForObjectClass request = callback.getStartRegistrationForObjectClass();
           _federateReference.startRegistrationForObjectClass(
              _clientConverter.convertToHla(request.getTheClass()) );
           break;
        }

         case STOPREGISTRATIONFOROBJECTCLASS: {
           StopRegistrationForObjectClass request = callback.getStopRegistrationForObjectClass();
           _federateReference.stopRegistrationForObjectClass(
              _clientConverter.convertToHla(request.getTheClass()) );
           break;
        }

         case TURNINTERACTIONSON: {
           TurnInteractionsOn request = callback.getTurnInteractionsOn();
           _federateReference.turnInteractionsOn(
              _clientConverter.convertToHla(request.getTheHandle()) );
           break;
        }

         case TURNINTERACTIONSOFF: {
           TurnInteractionsOff request = callback.getTurnInteractionsOff();
           _federateReference.turnInteractionsOff(
              _clientConverter.convertToHla(request.getTheHandle()) );
           break;
        }

         case OBJECTINSTANCENAMERESERVATIONSUCCEEDED: {
           ObjectInstanceNameReservationSucceeded request = callback.getObjectInstanceNameReservationSucceeded();
           _federateReference.objectInstanceNameReservationSucceeded(
              _clientConverter.convertToHla(request.getObjectName()) );
           break;
        }

         case MULTIPLEOBJECTINSTANCENAMERESERVATIONSUCCEEDED: {
           MultipleObjectInstanceNameReservationSucceeded request = callback.getMultipleObjectInstanceNameReservationSucceeded();
           _federateReference.multipleObjectInstanceNameReservationSucceeded(
              _clientConverter.convertToHla(request.getObjectNamesList()) );
           break;
        }

         case OBJECTINSTANCENAMERESERVATIONFAILED: {
           ObjectInstanceNameReservationFailed request = callback.getObjectInstanceNameReservationFailed();
           _federateReference.objectInstanceNameReservationFailed(
              _clientConverter.convertToHla(request.getObjectName()) );
           break;
        }

         case MULTIPLEOBJECTINSTANCENAMERESERVATIONFAILED: {
           MultipleObjectInstanceNameReservationFailed request = callback.getMultipleObjectInstanceNameReservationFailed();
           _federateReference.multipleObjectInstanceNameReservationFailed(
              _clientConverter.convertToHla(request.getObjectNamesList()) );
           break;
        }

         case DISCOVEROBJECTINSTANCE: {
           DiscoverObjectInstance request = callback.getDiscoverObjectInstance();
           _federateReference.discoverObjectInstance(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheObjectClass()),
              _clientConverter.convertToHla(request.getObjectName()),
              _clientConverter.convertToHla(request.getProducingFederate()) );
           break;
        }

         case REFLECTATTRIBUTEVALUES: {
           ReflectAttributeValues request = callback.getReflectAttributeValues();
           _federateReference.reflectAttributeValues(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()),
              _clientConverter.convertToHla(request.getSentOrdering()),
              _clientConverter.convertToHla(request.getTheTransport()),
              _clientConverter.convertToHla(request.getReflectInfo()) );
           break;
        }

         case REFLECTATTRIBUTEVALUESWITHTIME: {
           ReflectAttributeValuesWithTime request = callback.getReflectAttributeValuesWithTime();
           _federateReference.reflectAttributeValuesWithTime(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()),
              _clientConverter.convertToHla(request.getSentOrdering()),
              _clientConverter.convertToHla(request.getTheTransport()),
              _clientConverter.convertToHla(request.getTheTime()),
              _clientConverter.convertToHla(request.getReceivedOrdering()),
              _clientConverter.convertToHla(request.getReflectInfo()) );
           break;
        }

         case REFLECTATTRIBUTEVALUESWITHRETRACTION: {
           ReflectAttributeValuesWithRetraction request = callback.getReflectAttributeValuesWithRetraction();
           _federateReference.reflectAttributeValuesWithRetraction(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()),
              _clientConverter.convertToHla(request.getSentOrdering()),
              _clientConverter.convertToHla(request.getTheTransport()),
              _clientConverter.convertToHla(request.getTheTime()),
              _clientConverter.convertToHla(request.getReceivedOrdering()),
              _clientConverter.convertToHla(request.getRetractionHandle()),
              _clientConverter.convertToHla(request.getReflectInfo()) );
           break;
        }

         case RECEIVEINTERACTION: {
           ReceiveInteraction request = callback.getReceiveInteraction();
           _federateReference.receiveInteraction(
              _clientConverter.convertToHla(request.getInteractionClass()),
              _clientConverter.convertToHla(request.getTheParameters()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()),
              _clientConverter.convertToHla(request.getSentOrdering()),
              _clientConverter.convertToHla(request.getTheTransport()),
              _clientConverter.convertToHla(request.getReceiveInfo()) );
           break;
        }

         case RECEIVEINTERACTIONWITHTIME: {
           ReceiveInteractionWithTime request = callback.getReceiveInteractionWithTime();
           _federateReference.receiveInteractionWithTime(
              _clientConverter.convertToHla(request.getInteractionClass()),
              _clientConverter.convertToHla(request.getTheParameters()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()),
              _clientConverter.convertToHla(request.getSentOrdering()),
              _clientConverter.convertToHla(request.getTheTransport()),
              _clientConverter.convertToHla(request.getTheTime()),
              _clientConverter.convertToHla(request.getReceivedOrdering()),
              _clientConverter.convertToHla(request.getReceiveInfo()) );
           break;
        }

         case RECEIVEINTERACTIONWITHRETRACTION: {
           ReceiveInteractionWithRetraction request = callback.getReceiveInteractionWithRetraction();
           _federateReference.receiveInteractionWithRetraction(
              _clientConverter.convertToHla(request.getInteractionClass()),
              _clientConverter.convertToHla(request.getTheParameters()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()),
              _clientConverter.convertToHla(request.getSentOrdering()),
              _clientConverter.convertToHla(request.getTheTransport()),
              _clientConverter.convertToHla(request.getTheTime()),
              _clientConverter.convertToHla(request.getReceivedOrdering()),
              _clientConverter.convertToHla(request.getRetractionHandle()),
              _clientConverter.convertToHla(request.getReceiveInfo()) );
           break;
        }

         case RECEIVEDIRECTEDINTERACTION: {
           ReceiveDirectedInteraction request = callback.getReceiveDirectedInteraction();
           _federateReference.receiveDirectedInteraction(
              _clientConverter.convertToHla(request.getInteractionClass()),
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheParameters()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()),
              _clientConverter.convertToHla(request.getSentOrdering()),
              _clientConverter.convertToHla(request.getTheTransport()),
              _clientConverter.convertToHla(request.getReceiveInfo()) );
           break;
        }

         case RECEIVEDIRECTEDINTERACTIONWITHTIME: {
           ReceiveDirectedInteractionWithTime request = callback.getReceiveDirectedInteractionWithTime();
           _federateReference.receiveDirectedInteractionWithTime(
              _clientConverter.convertToHla(request.getInteractionClass()),
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheParameters()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()),
              _clientConverter.convertToHla(request.getSentOrdering()),
              _clientConverter.convertToHla(request.getTheTransport()),
              _clientConverter.convertToHla(request.getTheTime()),
              _clientConverter.convertToHla(request.getReceivedOrdering()),
              _clientConverter.convertToHla(request.getReceiveInfo()) );
           break;
        }

         case RECEIVEDIRECTEDINTERACTIONWITHRETRACTION: {
           ReceiveDirectedInteractionWithRetraction request = callback.getReceiveDirectedInteractionWithRetraction();
           _federateReference.receiveDirectedInteractionWithRetraction(
              _clientConverter.convertToHla(request.getInteractionClass()),
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheParameters()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()),
              _clientConverter.convertToHla(request.getSentOrdering()),
              _clientConverter.convertToHla(request.getTheTransport()),
              _clientConverter.convertToHla(request.getTheTime()),
              _clientConverter.convertToHla(request.getReceivedOrdering()),
              _clientConverter.convertToHla(request.getRetractionHandle()),
              _clientConverter.convertToHla(request.getReceiveInfo()) );
           break;
        }

         case REMOVEOBJECTINSTANCE: {
           RemoveObjectInstance request = callback.getRemoveObjectInstance();
           _federateReference.removeObjectInstance(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()),
              _clientConverter.convertToHla(request.getSentOrdering()),
              _clientConverter.convertToHla(request.getProducingFederate()) );
           break;
        }

         case REMOVEOBJECTINSTANCEWITHTIME: {
           RemoveObjectInstanceWithTime request = callback.getRemoveObjectInstanceWithTime();
           _federateReference.removeObjectInstanceWithTime(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()),
              _clientConverter.convertToHla(request.getSentOrdering()),
              _clientConverter.convertToHla(request.getTheTime()),
              _clientConverter.convertToHla(request.getReceivedOrdering()),
              _clientConverter.convertToHla(request.getProducingFederate()) );
           break;
        }

         case REMOVEOBJECTINSTANCEWITHRETRACTION: {
           RemoveObjectInstanceWithRetraction request = callback.getRemoveObjectInstanceWithRetraction();
           _federateReference.removeObjectInstanceWithRetraction(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()),
              _clientConverter.convertToHla(request.getSentOrdering()),
              _clientConverter.convertToHla(request.getTheTime()),
              _clientConverter.convertToHla(request.getReceivedOrdering()),
              _clientConverter.convertToHla(request.getRetractionHandle()),
              _clientConverter.convertToHla(request.getProducingFederate()) );
           break;
        }

         case ATTRIBUTESINSCOPE: {
           AttributesInScope request = callback.getAttributesInScope();
           _federateReference.attributesInScope(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()) );
           break;
        }

         case ATTRIBUTESOUTOFSCOPE: {
           AttributesOutOfScope request = callback.getAttributesOutOfScope();
           _federateReference.attributesOutOfScope(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()) );
           break;
        }

         case PROVIDEATTRIBUTEVALUEUPDATE: {
           ProvideAttributeValueUpdate request = callback.getProvideAttributeValueUpdate();
           _federateReference.provideAttributeValueUpdate(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()) );
           break;
        }

         case TURNUPDATESONFOROBJECTINSTANCE: {
           TurnUpdatesOnForObjectInstance request = callback.getTurnUpdatesOnForObjectInstance();
           _federateReference.turnUpdatesOnForObjectInstance(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()) );
           break;
        }

         case TURNUPDATESONFOROBJECTINSTANCEWITHRATE: {
           TurnUpdatesOnForObjectInstanceWithRate request = callback.getTurnUpdatesOnForObjectInstanceWithRate();
           _federateReference.turnUpdatesOnForObjectInstanceWithRate(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()),
              _clientConverter.convertToHla(request.getUpdateRateDesignator()) );
           break;
        }

         case TURNUPDATESOFFFOROBJECTINSTANCE: {
           TurnUpdatesOffForObjectInstance request = callback.getTurnUpdatesOffForObjectInstance();
           _federateReference.turnUpdatesOffForObjectInstance(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()) );
           break;
        }

         case CONFIRMATTRIBUTETRANSPORTATIONTYPECHANGE: {
           ConfirmAttributeTransportationTypeChange request = callback.getConfirmAttributeTransportationTypeChange();
           _federateReference.confirmAttributeTransportationTypeChange(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()),
              _clientConverter.convertToHla(request.getTheTransportation()) );
           break;
        }

         case CONFIRMINTERACTIONTRANSPORTATIONTYPECHANGE: {
           ConfirmInteractionTransportationTypeChange request = callback.getConfirmInteractionTransportationTypeChange();
           _federateReference.confirmInteractionTransportationTypeChange(
              _clientConverter.convertToHla(request.getTheInteraction()),
              _clientConverter.convertToHla(request.getTheTransportation()) );
           break;
        }

         case REPORTATTRIBUTETRANSPORTATIONTYPE: {
           ReportAttributeTransportationType request = callback.getReportAttributeTransportationType();
           _federateReference.reportAttributeTransportationType(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttribute()),
              _clientConverter.convertToHla(request.getTheTransportation()) );
           break;
        }

         case REPORTINTERACTIONTRANSPORTATIONTYPE: {
           ReportInteractionTransportationType request = callback.getReportInteractionTransportationType();
           _federateReference.reportInteractionTransportationType(
              _clientConverter.convertToHla(request.getTheFederate()),
              _clientConverter.convertToHla(request.getTheInteraction()),
              _clientConverter.convertToHla(request.getTheTransportation()) );
           break;
        }

         case REQUESTATTRIBUTEOWNERSHIPASSUMPTION: {
           RequestAttributeOwnershipAssumption request = callback.getRequestAttributeOwnershipAssumption();
           _federateReference.requestAttributeOwnershipAssumption(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getOfferedAttributes()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()) );
           break;
        }

         case REQUESTDIVESTITURECONFIRMATION: {
           RequestDivestitureConfirmation request = callback.getRequestDivestitureConfirmation();
           _federateReference.requestDivestitureConfirmation(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getOfferedAttributes()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()) );
           break;
        }

         case ATTRIBUTEOWNERSHIPACQUISITIONNOTIFICATION: {
           AttributeOwnershipAcquisitionNotification request = callback.getAttributeOwnershipAcquisitionNotification();
           _federateReference.attributeOwnershipAcquisitionNotification(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getSecuredAttributes()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()) );
           break;
        }

         case ATTRIBUTEOWNERSHIPUNAVAILABLE: {
           AttributeOwnershipUnavailable request = callback.getAttributeOwnershipUnavailable();
           _federateReference.attributeOwnershipUnavailable(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()) );
           break;
        }

         case REQUESTATTRIBUTEOWNERSHIPRELEASE: {
           RequestAttributeOwnershipRelease request = callback.getRequestAttributeOwnershipRelease();
           _federateReference.requestAttributeOwnershipRelease(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getCandidateAttributes()),
              _clientConverter.convertToHla(request.getUserSuppliedTag()) );
           break;
        }

         case CONFIRMATTRIBUTEOWNERSHIPACQUISITIONCANCELLATION: {
           ConfirmAttributeOwnershipAcquisitionCancellation request = callback.getConfirmAttributeOwnershipAcquisitionCancellation();
           _federateReference.confirmAttributeOwnershipAcquisitionCancellation(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()) );
           break;
        }

         case INFORMATTRIBUTEOWNERSHIP: {
           InformAttributeOwnership request = callback.getInformAttributeOwnership();
           _federateReference.informAttributeOwnership(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()),
              _clientConverter.convertToHla(request.getTheOwner()) );
           break;
        }

         case ATTRIBUTEISNOTOWNED: {
           AttributeIsNotOwned request = callback.getAttributeIsNotOwned();
           _federateReference.attributeIsNotOwned(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()) );
           break;
        }

         case ATTRIBUTEISOWNEDBYRTI: {
           AttributeIsOwnedByRTI request = callback.getAttributeIsOwnedByRTI();
           _federateReference.attributeIsOwnedByRTI(
              _clientConverter.convertToHla(request.getTheObject()),
              _clientConverter.convertToHla(request.getTheAttributes()) );
           break;
        }

         case TIMEREGULATIONENABLED: {
           TimeRegulationEnabled request = callback.getTimeRegulationEnabled();
           _federateReference.timeRegulationEnabled(
              _clientConverter.convertToHla(request.getTime()) );
           break;
        }

         case TIMECONSTRAINEDENABLED: {
           TimeConstrainedEnabled request = callback.getTimeConstrainedEnabled();
           _federateReference.timeConstrainedEnabled(
              _clientConverter.convertToHla(request.getTime()) );
           break;
        }

         case FLUSHQUEUEGRANT: {
           FlushQueueGrant request = callback.getFlushQueueGrant();
           _federateReference.flushQueueGrant(
              _clientConverter.convertToHla(request.getTheTime()),
              _clientConverter.convertToHla(request.getOptimisticTime()) );
           break;
        }

         case TIMEADVANCEGRANT: {
           TimeAdvanceGrant request = callback.getTimeAdvanceGrant();
           _federateReference.timeAdvanceGrant(
              _clientConverter.convertToHla(request.getTheTime()) );
           break;
        }

         case REQUESTRETRACTION: {
           RequestRetraction request = callback.getRequestRetraction();
           _federateReference.requestRetraction(
              _clientConverter.convertToHla(request.getTheHandle()) );
           break;
        }

  
        default: {
           System.out.println("Unknown callback: " + callback.getCallbackRequestCase());
           throw new FederateInternalError("TODO " + callback.getCallbackRequestCase().toString() + " not supported!");
        }
     }

   }

}
