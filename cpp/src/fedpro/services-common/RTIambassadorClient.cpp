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

#include "RTIambassadorClient.h"

#include <protobuf/generated/RTIambassador.pb.h>
#include <protobuf/generated/datatypes.pb.h>

#include <string>

//
// Note:
//
//   This file contains RTIambassadorClient implementation for most methods or HLA services.
//   See also RTIambassadorClient_connect_callbacks.cpp for the rest of the implementation.
//

namespace FedPro
{

   void RTIambassadorClient::createFederationExecution(
      std::string const & federationName,
      const FedPro::FomModule & fomModule) 
   {
      auto * request = new rti1516_2025::fedpro::CreateFederationExecutionRequest();
      request->set_allocated_federationname(_clientConverter->convertFromHla(federationName));
      request->set_allocated_fommodule(_clientConverter->convertFromHla(fomModule));

      rti1516_2025::fedpro::CallRequest callRequest;
      callRequest.set_allocated_createfederationexecutionrequest(request);

      rti1516_2025::fedpro::CallResponse callResponse{doHlaCall(callRequest)};
      if (!callResponse.has_createfederationexecutionresponse()) {
         throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol createFederationExecution response");
      }

      // No return value
   }

   void RTIambassadorClient::createFederationExecutionWithTime(
      std::string const & federationName,
      const FedPro::FomModule & fomModule,
      std::string const & logicalTimeImplementationName) 
   {
      auto * request = new rti1516_2025::fedpro::CreateFederationExecutionWithTimeRequest();
      request->set_allocated_federationname(_clientConverter->convertFromHla(federationName));
      request->set_allocated_fommodule(_clientConverter->convertFromHla(fomModule));
      request->set_allocated_logicaltimeimplementationname(_clientConverter->convertFromHla(logicalTimeImplementationName));

      rti1516_2025::fedpro::CallRequest callRequest;
      callRequest.set_allocated_createfederationexecutionwithtimerequest(request);

      rti1516_2025::fedpro::CallResponse callResponse{doHlaCall(callRequest)};
      if (!callResponse.has_createfederationexecutionwithtimeresponse()) {
         throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol createFederationExecutionWithTime response");
      }

      // No return value
   }

   void RTIambassadorClient::createFederationExecutionWithModules(
      std::string const & federationName,
      const FedPro::FomModuleSet & fomModules) 
   {
      auto * request = new rti1516_2025::fedpro::CreateFederationExecutionWithModulesRequest();
      request->set_allocated_federationname(_clientConverter->convertFromHla(federationName));
      request->set_allocated_fommodules(_clientConverter->convertFromHla(fomModules));

      rti1516_2025::fedpro::CallRequest callRequest;
      callRequest.set_allocated_createfederationexecutionwithmodulesrequest(request);

      rti1516_2025::fedpro::CallResponse callResponse{doHlaCall(callRequest)};
      if (!callResponse.has_createfederationexecutionwithmodulesresponse()) {
         throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol createFederationExecutionWithModules response");
      }

      // No return value
   }

   void RTIambassadorClient::createFederationExecutionWithModulesAndTime(
      std::string const & federationName,
      const FedPro::FomModuleSet & fomModules,
      std::string const & logicalTimeImplementationName) 
   {
      auto * request = new rti1516_2025::fedpro::CreateFederationExecutionWithModulesAndTimeRequest();
      request->set_allocated_federationname(_clientConverter->convertFromHla(federationName));
      request->set_allocated_fommodules(_clientConverter->convertFromHla(fomModules));
      request->set_allocated_logicaltimeimplementationname(_clientConverter->convertFromHla(logicalTimeImplementationName));

      rti1516_2025::fedpro::CallRequest callRequest;
      callRequest.set_allocated_createfederationexecutionwithmodulesandtimerequest(request);

      rti1516_2025::fedpro::CallResponse callResponse{doHlaCall(callRequest)};
      if (!callResponse.has_createfederationexecutionwithmodulesandtimeresponse()) {
         throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol createFederationExecutionWithModulesAndTime response");
      }

      // No return value
   }

   void RTIambassadorClient::createFederationExecutionWithMIM(
      std::string const & federationName,
      const FedPro::FomModuleSet & fomModules,
      const FedPro::FomModule & mimModule) 
   {
      auto * request = new rti1516_2025::fedpro::CreateFederationExecutionWithMIMRequest();
      request->set_allocated_federationname(_clientConverter->convertFromHla(federationName));
      request->set_allocated_fommodules(_clientConverter->convertFromHla(fomModules));
      request->set_allocated_mimmodule(_clientConverter->convertFromHla(mimModule));

      rti1516_2025::fedpro::CallRequest callRequest;
      callRequest.set_allocated_createfederationexecutionwithmimrequest(request);

      rti1516_2025::fedpro::CallResponse callResponse{doHlaCall(callRequest)};
      if (!callResponse.has_createfederationexecutionwithmimresponse()) {
         throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol createFederationExecutionWithMIM response");
      }

      // No return value
   }

   void RTIambassadorClient::createFederationExecutionWithMIMAndTime(
      std::string const & federationName,
      const FedPro::FomModuleSet & fomModules,
      const FedPro::FomModule & mimModule,
      std::string const & logicalTimeImplementationName) 
   {
      auto * request = new rti1516_2025::fedpro::CreateFederationExecutionWithMIMAndTimeRequest();
      request->set_allocated_federationname(_clientConverter->convertFromHla(federationName));
      request->set_allocated_fommodules(_clientConverter->convertFromHla(fomModules));
      request->set_allocated_mimmodule(_clientConverter->convertFromHla(mimModule));
      request->set_allocated_logicaltimeimplementationname(_clientConverter->convertFromHla(logicalTimeImplementationName));

      rti1516_2025::fedpro::CallRequest callRequest;
      callRequest.set_allocated_createfederationexecutionwithmimandtimerequest(request);

      rti1516_2025::fedpro::CallResponse callResponse{doHlaCall(callRequest)};
      if (!callResponse.has_createfederationexecutionwithmimandtimeresponse()) {
         throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol createFederationExecutionWithMIMAndTime response");
      }

      // No return value
   }

   void RTIambassadorClient::destroyFederationExecution(
      std::string const & federationName) 
   {
      auto * request = new rti1516_2025::fedpro::DestroyFederationExecutionRequest();
      request->set_allocated_federationname(_clientConverter->convertFromHla(federationName));

      rti1516_2025::fedpro::CallRequest callRequest;
      callRequest.set_allocated_destroyfederationexecutionrequest(request);

      rti1516_2025::fedpro::CallResponse callResponse{doHlaCall(callRequest)};
      if (!callResponse.has_destroyfederationexecutionresponse()) {
         throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol destroyFederationExecution response");
      }

      // No return value
   }

   void RTIambassadorClient::listFederationExecutions(
) 
    {
      auto getResponse = asyncListFederationExecutions(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncListFederationExecutions(
)
   {
      auto * request = new rti1516_2025::fedpro::ListFederationExecutionsRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_listfederationexecutionsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_listfederationexecutionsresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


#if (RTI_HLA_VERSION >= 2025)
   void RTIambassadorClient::listFederationExecutionMembers(
      std::string const & federationName) 
    {
      auto getResponse = asyncListFederationExecutionMembers(
         federationName      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncListFederationExecutionMembers(
      std::string const & federationName)
   {
      auto * request = new rti1516_2025::fedpro::ListFederationExecutionMembersRequest();
      request->set_allocated_federationname(_clientConverter->convertFromHla(federationName));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_listfederationexecutionmembersrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_listfederationexecutionmembersresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }

#endif // RTI_HLA_VERSION

   FedPro::JoinResult RTIambassadorClient::joinFederationExecution(
      std::string const & federateType,
      std::string const & federationName) 
   {
      throwIfInCallback(__func__);

      auto * request = new rti1516_2025::fedpro::JoinFederationExecutionRequest();
      request->set_allocated_federatetype(_clientConverter->convertFromHla(federateType));
      request->set_allocated_federationname(_clientConverter->convertFromHla(federationName));

      rti1516_2025::fedpro::CallRequest callRequest;
      callRequest.set_allocated_joinfederationexecutionrequest(request);

      rti1516_2025::fedpro::CallResponse callResponse{doHlaCall(callRequest)};
      if (!callResponse.has_joinfederationexecutionresponse()) {
         throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol joinFederationExecution response");
      }

      return _clientConverter->convertToHlaAndMove(callResponse.mutable_joinfederationexecutionresponse());
   }

   FedPro::JoinResult RTIambassadorClient::joinFederationExecutionWithModules(
      std::string const & federateType,
      std::string const & federationName,
      const FedPro::FomModuleSet & additionalFomModules) 
   {
      throwIfInCallback(__func__);

      auto * request = new rti1516_2025::fedpro::JoinFederationExecutionWithModulesRequest();
      request->set_allocated_federatetype(_clientConverter->convertFromHla(federateType));
      request->set_allocated_federationname(_clientConverter->convertFromHla(federationName));
      request->set_allocated_additionalfommodules(_clientConverter->convertFromHla(additionalFomModules));

      rti1516_2025::fedpro::CallRequest callRequest;
      callRequest.set_allocated_joinfederationexecutionwithmodulesrequest(request);

      rti1516_2025::fedpro::CallResponse callResponse{doHlaCall(callRequest)};
      if (!callResponse.has_joinfederationexecutionwithmodulesresponse()) {
         throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol joinFederationExecutionWithModules response");
      }

      return _clientConverter->convertToHlaAndMove(callResponse.mutable_joinfederationexecutionwithmodulesresponse());
   }

   FedPro::JoinResult RTIambassadorClient::joinFederationExecutionWithName(
      std::string const & federateName,
      std::string const & federateType,
      std::string const & federationName) 
   {
      throwIfInCallback(__func__);

      auto * request = new rti1516_2025::fedpro::JoinFederationExecutionWithNameRequest();
      request->set_allocated_federatename(_clientConverter->convertFromHla(federateName));
      request->set_allocated_federatetype(_clientConverter->convertFromHla(federateType));
      request->set_allocated_federationname(_clientConverter->convertFromHla(federationName));

      rti1516_2025::fedpro::CallRequest callRequest;
      callRequest.set_allocated_joinfederationexecutionwithnamerequest(request);

      rti1516_2025::fedpro::CallResponse callResponse{doHlaCall(callRequest)};
      if (!callResponse.has_joinfederationexecutionwithnameresponse()) {
         throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol joinFederationExecutionWithName response");
      }

      return _clientConverter->convertToHlaAndMove(callResponse.mutable_joinfederationexecutionwithnameresponse());
   }

   FedPro::JoinResult RTIambassadorClient::joinFederationExecutionWithNameAndModules(
      std::string const & federateName,
      std::string const & federateType,
      std::string const & federationName,
      const FedPro::FomModuleSet & additionalFomModules) 
   {
      throwIfInCallback(__func__);

      auto * request = new rti1516_2025::fedpro::JoinFederationExecutionWithNameAndModulesRequest();
      request->set_allocated_federatename(_clientConverter->convertFromHla(federateName));
      request->set_allocated_federatetype(_clientConverter->convertFromHla(federateType));
      request->set_allocated_federationname(_clientConverter->convertFromHla(federationName));
      request->set_allocated_additionalfommodules(_clientConverter->convertFromHla(additionalFomModules));

      rti1516_2025::fedpro::CallRequest callRequest;
      callRequest.set_allocated_joinfederationexecutionwithnameandmodulesrequest(request);

      rti1516_2025::fedpro::CallResponse callResponse{doHlaCall(callRequest)};
      if (!callResponse.has_joinfederationexecutionwithnameandmodulesresponse()) {
         throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol joinFederationExecutionWithNameAndModules response");
      }

      return _clientConverter->convertToHlaAndMove(callResponse.mutable_joinfederationexecutionwithnameandmodulesresponse());
   }

   void RTIambassadorClient::resignFederationExecution(
      RTI_NAMESPACE::ResignAction resignAction) 
   {
      throwIfInCallback(__func__);

      auto * request = new rti1516_2025::fedpro::ResignFederationExecutionRequest();
      request->set_resignaction(_clientConverter->convertFromHla(resignAction));

      rti1516_2025::fedpro::CallRequest callRequest;
      callRequest.set_allocated_resignfederationexecutionrequest(request);

      rti1516_2025::fedpro::CallResponse callResponse{doHlaCall(callRequest)};
      if (!callResponse.has_resignfederationexecutionresponse()) {
         throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol resignFederationExecution response");
      }

      // No return value
   }

   void RTIambassadorClient::registerFederationSynchronizationPoint(
      std::string const & synchronizationPointLabel,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncRegisterFederationSynchronizationPoint(
         synchronizationPointLabel,
         userSuppliedTag      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncRegisterFederationSynchronizationPoint(
      std::string const & synchronizationPointLabel,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::RegisterFederationSynchronizationPointRequest();
      request->set_allocated_synchronizationpointlabel(_clientConverter->convertFromHla(synchronizationPointLabel));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_registerfederationsynchronizationpointrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_registerfederationsynchronizationpointresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::registerFederationSynchronizationPoint(
      std::string const & synchronizationPointLabel,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
      const RTI_NAMESPACE::FederateHandleSet & synchronizationSet) 
    {
      auto getResponse = asyncRegisterFederationSynchronizationPoint(
         synchronizationPointLabel,
         userSuppliedTag,
         synchronizationSet      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncRegisterFederationSynchronizationPoint(
      std::string const & synchronizationPointLabel,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
      const RTI_NAMESPACE::FederateHandleSet & synchronizationSet)
   {
      auto * request = new rti1516_2025::fedpro::RegisterFederationSynchronizationPointWithSetRequest();
      request->set_allocated_synchronizationpointlabel(_clientConverter->convertFromHla(synchronizationPointLabel));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));
      request->set_allocated_synchronizationset(_clientConverter->convertFromHla(synchronizationSet));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_registerfederationsynchronizationpointwithsetrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_registerfederationsynchronizationpointwithsetresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::synchronizationPointAchieved(
      std::string const & synchronizationPointLabel,
      bool successfully) 
    {
      auto getResponse = asyncSynchronizationPointAchieved(
         synchronizationPointLabel,
         successfully      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSynchronizationPointAchieved(
      std::string const & synchronizationPointLabel,
      bool successfully)
   {
      auto * request = new rti1516_2025::fedpro::SynchronizationPointAchievedRequest();
      request->set_allocated_synchronizationpointlabel(_clientConverter->convertFromHla(synchronizationPointLabel));
      request->set_successfully(_clientConverter->convertFromHla(successfully));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_synchronizationpointachievedrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_synchronizationpointachievedresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::requestFederationSave(
      std::string const & label) 
    {
      auto getResponse = asyncRequestFederationSave(
         label      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncRequestFederationSave(
      std::string const & label)
   {
      auto * request = new rti1516_2025::fedpro::RequestFederationSaveRequest();
      request->set_allocated_label(_clientConverter->convertFromHla(label));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_requestfederationsaverequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_requestfederationsaveresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::requestFederationSave(
      std::string const & label,
      const RTI_NAMESPACE::LogicalTime & time) 
    {
      auto getResponse = asyncRequestFederationSave(
         label,
         time      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncRequestFederationSave(
      std::string const & label,
      const RTI_NAMESPACE::LogicalTime & time)
   {
      auto * request = new rti1516_2025::fedpro::RequestFederationSaveWithTimeRequest();
      request->set_allocated_label(_clientConverter->convertFromHla(label));
      request->set_allocated_time(_clientConverter->convertFromHla(time));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_requestfederationsavewithtimerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_requestfederationsavewithtimeresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::federateSaveBegun(
) 
    {
      auto getResponse = asyncFederateSaveBegun(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncFederateSaveBegun(
)
   {
      auto * request = new rti1516_2025::fedpro::FederateSaveBegunRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_federatesavebegunrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_federatesavebegunresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::federateSaveComplete(
) 
    {
      auto getResponse = asyncFederateSaveComplete(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncFederateSaveComplete(
)
   {
      auto * request = new rti1516_2025::fedpro::FederateSaveCompleteRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_federatesavecompleterequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_federatesavecompleteresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::federateSaveNotComplete(
) 
    {
      auto getResponse = asyncFederateSaveNotComplete(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncFederateSaveNotComplete(
)
   {
      auto * request = new rti1516_2025::fedpro::FederateSaveNotCompleteRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_federatesavenotcompleterequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_federatesavenotcompleteresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::abortFederationSave(
) 
    {
      auto getResponse = asyncAbortFederationSave(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncAbortFederationSave(
)
   {
      auto * request = new rti1516_2025::fedpro::AbortFederationSaveRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_abortfederationsaverequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_abortfederationsaveresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::queryFederationSaveStatus(
) 
    {
      auto getResponse = asyncQueryFederationSaveStatus(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncQueryFederationSaveStatus(
)
   {
      auto * request = new rti1516_2025::fedpro::QueryFederationSaveStatusRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_queryfederationsavestatusrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_queryfederationsavestatusresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::requestFederationRestore(
      std::string const & label) 
    {
      auto getResponse = asyncRequestFederationRestore(
         label      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncRequestFederationRestore(
      std::string const & label)
   {
      auto * request = new rti1516_2025::fedpro::RequestFederationRestoreRequest();
      request->set_allocated_label(_clientConverter->convertFromHla(label));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_requestfederationrestorerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_requestfederationrestoreresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::federateRestoreComplete(
) 
    {
      auto getResponse = asyncFederateRestoreComplete(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncFederateRestoreComplete(
)
   {
      auto * request = new rti1516_2025::fedpro::FederateRestoreCompleteRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_federaterestorecompleterequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_federaterestorecompleteresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::federateRestoreNotComplete(
) 
    {
      auto getResponse = asyncFederateRestoreNotComplete(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncFederateRestoreNotComplete(
)
   {
      auto * request = new rti1516_2025::fedpro::FederateRestoreNotCompleteRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_federaterestorenotcompleterequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_federaterestorenotcompleteresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::abortFederationRestore(
) 
    {
      auto getResponse = asyncAbortFederationRestore(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncAbortFederationRestore(
)
   {
      auto * request = new rti1516_2025::fedpro::AbortFederationRestoreRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_abortfederationrestorerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_abortfederationrestoreresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::queryFederationRestoreStatus(
) 
    {
      auto getResponse = asyncQueryFederationRestoreStatus(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncQueryFederationRestoreStatus(
)
   {
      auto * request = new rti1516_2025::fedpro::QueryFederationRestoreStatusRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_queryfederationrestorestatusrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_queryfederationrestorestatusresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::publishObjectClassAttributes(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes) 
    {
      auto getResponse = asyncPublishObjectClassAttributes(
         objectClass,
         attributes      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncPublishObjectClassAttributes(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes)
   {
      auto * request = new rti1516_2025::fedpro::PublishObjectClassAttributesRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_publishobjectclassattributesrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_publishobjectclassattributesresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::unpublishObjectClass(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass) 
    {
      auto getResponse = asyncUnpublishObjectClass(
         objectClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUnpublishObjectClass(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass)
   {
      auto * request = new rti1516_2025::fedpro::UnpublishObjectClassRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_unpublishobjectclassrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_unpublishobjectclassresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::unpublishObjectClassAttributes(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes) 
    {
      auto getResponse = asyncUnpublishObjectClassAttributes(
         objectClass,
         attributes      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUnpublishObjectClassAttributes(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes)
   {
      auto * request = new rti1516_2025::fedpro::UnpublishObjectClassAttributesRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_unpublishobjectclassattributesrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_unpublishobjectclassattributesresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::publishInteractionClass(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass) 
    {
      auto getResponse = asyncPublishInteractionClass(
         interactionClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncPublishInteractionClass(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass)
   {
      auto * request = new rti1516_2025::fedpro::PublishInteractionClassRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_publishinteractionclassrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_publishinteractionclassresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::unpublishInteractionClass(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass) 
    {
      auto getResponse = asyncUnpublishInteractionClass(
         interactionClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUnpublishInteractionClass(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass)
   {
      auto * request = new rti1516_2025::fedpro::UnpublishInteractionClassRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_unpublishinteractionclassrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_unpublishinteractionclassresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


#if (RTI_HLA_VERSION >= 2025)
   void RTIambassadorClient::publishObjectClassDirectedInteractions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses) 
    {
      auto getResponse = asyncPublishObjectClassDirectedInteractions(
         objectClass,
         interactionClasses      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncPublishObjectClassDirectedInteractions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses)
   {
      auto * request = new rti1516_2025::fedpro::PublishObjectClassDirectedInteractionsRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_interactionclasses(_clientConverter->convertFromHla(interactionClasses));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_publishobjectclassdirectedinteractionsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_publishobjectclassdirectedinteractionsresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }

#endif // RTI_HLA_VERSION

#if (RTI_HLA_VERSION >= 2025)
   void RTIambassadorClient::unpublishObjectClassDirectedInteractions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass) 
    {
      auto getResponse = asyncUnpublishObjectClassDirectedInteractions(
         objectClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUnpublishObjectClassDirectedInteractions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass)
   {
      auto * request = new rti1516_2025::fedpro::UnpublishObjectClassDirectedInteractionsRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_unpublishobjectclassdirectedinteractionsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_unpublishobjectclassdirectedinteractionsresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }

#endif // RTI_HLA_VERSION

#if (RTI_HLA_VERSION >= 2025)
   void RTIambassadorClient::unpublishObjectClassDirectedInteractions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses) 
    {
      auto getResponse = asyncUnpublishObjectClassDirectedInteractions(
         objectClass,
         interactionClasses      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUnpublishObjectClassDirectedInteractions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses)
   {
      auto * request = new rti1516_2025::fedpro::UnpublishObjectClassDirectedInteractionsWithSetRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_interactionclasses(_clientConverter->convertFromHla(interactionClasses));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_unpublishobjectclassdirectedinteractionswithsetrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_unpublishobjectclassdirectedinteractionswithsetresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }

#endif // RTI_HLA_VERSION

   void RTIambassadorClient::subscribeObjectClassAttributes(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes) 
    {
      auto getResponse = asyncSubscribeObjectClassAttributes(
         objectClass,
         attributes      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSubscribeObjectClassAttributes(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes)
   {
      auto * request = new rti1516_2025::fedpro::SubscribeObjectClassAttributesRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_subscribeobjectclassattributesrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_subscribeobjectclassattributesresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::subscribeObjectClassAttributes(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      std::string const & updateRateDesignator) 
    {
      auto getResponse = asyncSubscribeObjectClassAttributes(
         objectClass,
         attributes,
         updateRateDesignator      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSubscribeObjectClassAttributes(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      std::string const & updateRateDesignator)
   {
      auto * request = new rti1516_2025::fedpro::SubscribeObjectClassAttributesWithRateRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));
      request->set_allocated_updateratedesignator(_clientConverter->convertFromHla(updateRateDesignator));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_subscribeobjectclassattributeswithraterequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_subscribeobjectclassattributeswithrateresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::subscribeObjectClassAttributesPassively(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes) 
    {
      auto getResponse = asyncSubscribeObjectClassAttributesPassively(
         objectClass,
         attributes      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSubscribeObjectClassAttributesPassively(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes)
   {
      auto * request = new rti1516_2025::fedpro::SubscribeObjectClassAttributesPassivelyRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_subscribeobjectclassattributespassivelyrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_subscribeobjectclassattributespassivelyresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::subscribeObjectClassAttributesPassively(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      std::string const & updateRateDesignator) 
    {
      auto getResponse = asyncSubscribeObjectClassAttributesPassively(
         objectClass,
         attributes,
         updateRateDesignator      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSubscribeObjectClassAttributesPassively(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      std::string const & updateRateDesignator)
   {
      auto * request = new rti1516_2025::fedpro::SubscribeObjectClassAttributesPassivelyWithRateRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));
      request->set_allocated_updateratedesignator(_clientConverter->convertFromHla(updateRateDesignator));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_subscribeobjectclassattributespassivelywithraterequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_subscribeobjectclassattributespassivelywithrateresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::unsubscribeObjectClass(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass) 
    {
      auto getResponse = asyncUnsubscribeObjectClass(
         objectClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUnsubscribeObjectClass(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass)
   {
      auto * request = new rti1516_2025::fedpro::UnsubscribeObjectClassRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_unsubscribeobjectclassrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_unsubscribeobjectclassresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::unsubscribeObjectClassAttributes(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes) 
    {
      auto getResponse = asyncUnsubscribeObjectClassAttributes(
         objectClass,
         attributes      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUnsubscribeObjectClassAttributes(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes)
   {
      auto * request = new rti1516_2025::fedpro::UnsubscribeObjectClassAttributesRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_unsubscribeobjectclassattributesrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_unsubscribeobjectclassattributesresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::subscribeInteractionClass(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass) 
    {
      auto getResponse = asyncSubscribeInteractionClass(
         interactionClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSubscribeInteractionClass(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass)
   {
      auto * request = new rti1516_2025::fedpro::SubscribeInteractionClassRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_subscribeinteractionclassrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_subscribeinteractionclassresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::subscribeInteractionClassPassively(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass) 
    {
      auto getResponse = asyncSubscribeInteractionClassPassively(
         interactionClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSubscribeInteractionClassPassively(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass)
   {
      auto * request = new rti1516_2025::fedpro::SubscribeInteractionClassPassivelyRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_subscribeinteractionclasspassivelyrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_subscribeinteractionclasspassivelyresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::unsubscribeInteractionClass(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass) 
    {
      auto getResponse = asyncUnsubscribeInteractionClass(
         interactionClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUnsubscribeInteractionClass(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass)
   {
      auto * request = new rti1516_2025::fedpro::UnsubscribeInteractionClassRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_unsubscribeinteractionclassrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_unsubscribeinteractionclassresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


#if (RTI_HLA_VERSION >= 2025)
   void RTIambassadorClient::subscribeObjectClassDirectedInteractions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses) 
    {
      auto getResponse = asyncSubscribeObjectClassDirectedInteractions(
         objectClass,
         interactionClasses      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSubscribeObjectClassDirectedInteractions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses)
   {
      auto * request = new rti1516_2025::fedpro::SubscribeObjectClassDirectedInteractionsRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_interactionclasses(_clientConverter->convertFromHla(interactionClasses));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_subscribeobjectclassdirectedinteractionsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_subscribeobjectclassdirectedinteractionsresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }

#endif // RTI_HLA_VERSION

#if (RTI_HLA_VERSION >= 2025)
   void RTIambassadorClient::subscribeObjectClassDirectedInteractionsUniversally(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses) 
    {
      auto getResponse = asyncSubscribeObjectClassDirectedInteractionsUniversally(
         objectClass,
         interactionClasses      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSubscribeObjectClassDirectedInteractionsUniversally(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses)
   {
      auto * request = new rti1516_2025::fedpro::SubscribeObjectClassDirectedInteractionsUniversallyRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_interactionclasses(_clientConverter->convertFromHla(interactionClasses));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_subscribeobjectclassdirectedinteractionsuniversallyrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_subscribeobjectclassdirectedinteractionsuniversallyresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }

#endif // RTI_HLA_VERSION

#if (RTI_HLA_VERSION >= 2025)
   void RTIambassadorClient::unsubscribeObjectClassDirectedInteractions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass) 
    {
      auto getResponse = asyncUnsubscribeObjectClassDirectedInteractions(
         objectClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUnsubscribeObjectClassDirectedInteractions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass)
   {
      auto * request = new rti1516_2025::fedpro::UnsubscribeObjectClassDirectedInteractionsRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_unsubscribeobjectclassdirectedinteractionsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_unsubscribeobjectclassdirectedinteractionsresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }

#endif // RTI_HLA_VERSION

#if (RTI_HLA_VERSION >= 2025)
   void RTIambassadorClient::unsubscribeObjectClassDirectedInteractions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses) 
    {
      auto getResponse = asyncUnsubscribeObjectClassDirectedInteractions(
         objectClass,
         interactionClasses      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUnsubscribeObjectClassDirectedInteractions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::InteractionClassHandleSet & interactionClasses)
   {
      auto * request = new rti1516_2025::fedpro::UnsubscribeObjectClassDirectedInteractionsWithSetRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_interactionclasses(_clientConverter->convertFromHla(interactionClasses));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_unsubscribeobjectclassdirectedinteractionswithsetrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_unsubscribeobjectclassdirectedinteractionswithsetresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }

#endif // RTI_HLA_VERSION

   void RTIambassadorClient::reserveObjectInstanceName(
      std::string const & objectInstanceName) 
    {
      auto getResponse = asyncReserveObjectInstanceName(
         objectInstanceName      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncReserveObjectInstanceName(
      std::string const & objectInstanceName)
   {
      auto * request = new rti1516_2025::fedpro::ReserveObjectInstanceNameRequest();
      request->set_allocated_objectinstancename(_clientConverter->convertFromHla(objectInstanceName));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_reserveobjectinstancenamerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_reserveobjectinstancenameresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::releaseObjectInstanceName(
      std::string const & objectInstanceName) 
    {
      auto getResponse = asyncReleaseObjectInstanceName(
         objectInstanceName      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncReleaseObjectInstanceName(
      std::string const & objectInstanceName)
   {
      auto * request = new rti1516_2025::fedpro::ReleaseObjectInstanceNameRequest();
      request->set_allocated_objectinstancename(_clientConverter->convertFromHla(objectInstanceName));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_releaseobjectinstancenamerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_releaseobjectinstancenameresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::reserveMultipleObjectInstanceNames(
      std::set<std::wstring> const & objectInstanceNames) 
    {
      auto getResponse = asyncReserveMultipleObjectInstanceNames(
         objectInstanceNames      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncReserveMultipleObjectInstanceNames(
      std::set<std::wstring> const & objectInstanceNames)
   {
      auto * request = new rti1516_2025::fedpro::ReserveMultipleObjectInstanceNamesRequest();
      for (auto & entry : _clientConverter->convertFromHla(objectInstanceNames)) {
         request->add_objectinstancenames(std::move(entry));
      }

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_reservemultipleobjectinstancenamesrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_reservemultipleobjectinstancenamesresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::releaseMultipleObjectInstanceNames(
      std::set<std::wstring> const & objectInstanceNames) 
    {
      auto getResponse = asyncReleaseMultipleObjectInstanceNames(
         objectInstanceNames      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncReleaseMultipleObjectInstanceNames(
      std::set<std::wstring> const & objectInstanceNames)
   {
      auto * request = new rti1516_2025::fedpro::ReleaseMultipleObjectInstanceNamesRequest();
      for (auto & entry : _clientConverter->convertFromHla(objectInstanceNames)) {
         request->add_objectinstancenames(std::move(entry));
      }

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_releasemultipleobjectinstancenamesrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_releasemultipleobjectinstancenamesresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::ObjectInstanceHandle RTIambassadorClient::registerObjectInstance(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass) 
    {
      auto getResponse = asyncRegisterObjectInstance(
         objectClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::ObjectInstanceHandle> RTIambassadorClient::asyncRegisterObjectInstance(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass)
   {
      auto * request = new rti1516_2025::fedpro::RegisterObjectInstanceRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_registerobjectinstancerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_registerobjectinstanceresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::ObjectInstanceHandle RTIambassadorClient::registerObjectInstanceWithName(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      std::string const & objectInstanceName) 
    {
      auto getResponse = asyncRegisterObjectInstanceWithName(
         objectClass,
         objectInstanceName      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::ObjectInstanceHandle> RTIambassadorClient::asyncRegisterObjectInstanceWithName(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      std::string const & objectInstanceName)
   {
      auto * request = new rti1516_2025::fedpro::RegisterObjectInstanceWithNameRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_objectinstancename(_clientConverter->convertFromHla(objectInstanceName));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_registerobjectinstancewithnamerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_registerobjectinstancewithnameresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::updateAttributeValues(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleValueMap & attributeValues,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncUpdateAttributeValues(
         objectInstance,
         attributeValues,
         userSuppliedTag      );
      if (_asyncUpdates) {
         countAsyncUpdateForStats();
         // Ignore future result
         return;
      }
      countSyncUpdateForStats();
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUpdateAttributeValues(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleValueMap & attributeValues,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::UpdateAttributeValuesRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attributevalues(_clientConverter->convertFromHla(attributeValues));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_updateattributevaluesrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_updateattributevaluesresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::MessageRetractionHandle RTIambassadorClient::updateAttributeValues(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleValueMap & attributeValues,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
      const RTI_NAMESPACE::LogicalTime & time) 
    {
      auto getResponse = asyncUpdateAttributeValues(
         objectInstance,
         attributeValues,
         userSuppliedTag,
         time      );
      countSyncUpdateForStats();
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::MessageRetractionHandle> RTIambassadorClient::asyncUpdateAttributeValues(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleValueMap & attributeValues,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
      const RTI_NAMESPACE::LogicalTime & time)
   {
      auto * request = new rti1516_2025::fedpro::UpdateAttributeValuesWithTimeRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attributevalues(_clientConverter->convertFromHla(attributeValues));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));
      request->set_allocated_time(_clientConverter->convertFromHla(time));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_updateattributevalueswithtimerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_updateattributevalueswithtimeresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::sendInteraction(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncSendInteraction(
         interactionClass,
         parameterValues,
         userSuppliedTag      );
      if (_asyncUpdates) {
         countAsyncSentInteractionForStats();
         // Ignore future result
         return;
      }
      countSyncSentInteractionForStats();
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSendInteraction(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::SendInteractionRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));
      request->set_allocated_parametervalues(_clientConverter->convertFromHla(parameterValues));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_sendinteractionrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_sendinteractionresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::MessageRetractionHandle RTIambassadorClient::sendInteraction(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
      const RTI_NAMESPACE::LogicalTime & time) 
    {
      auto getResponse = asyncSendInteraction(
         interactionClass,
         parameterValues,
         userSuppliedTag,
         time      );
      countSyncSentInteractionForStats();
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::MessageRetractionHandle> RTIambassadorClient::asyncSendInteraction(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
      const RTI_NAMESPACE::LogicalTime & time)
   {
      auto * request = new rti1516_2025::fedpro::SendInteractionWithTimeRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));
      request->set_allocated_parametervalues(_clientConverter->convertFromHla(parameterValues));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));
      request->set_allocated_time(_clientConverter->convertFromHla(time));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_sendinteractionwithtimerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_sendinteractionwithtimeresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::sendDirectedInteraction(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncSendDirectedInteraction(
         interactionClass,
         objectInstance,
         parameterValues,
         userSuppliedTag      );
      if (_asyncUpdates) {
         countAsyncSentDirectedInteractionForStats();
         // Ignore future result
         return;
      }
      countSyncSentDirectedInteractionForStats();
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSendDirectedInteraction(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::SendDirectedInteractionRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_parametervalues(_clientConverter->convertFromHla(parameterValues));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_senddirectedinteractionrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_senddirectedinteractionresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::MessageRetractionHandle RTIambassadorClient::sendDirectedInteraction(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
      const RTI_NAMESPACE::LogicalTime & time) 
    {
      auto getResponse = asyncSendDirectedInteraction(
         interactionClass,
         objectInstance,
         parameterValues,
         userSuppliedTag,
         time      );
      countSyncSentDirectedInteractionForStats();
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::MessageRetractionHandle> RTIambassadorClient::asyncSendDirectedInteraction(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
      const RTI_NAMESPACE::LogicalTime & time)
   {
      auto * request = new rti1516_2025::fedpro::SendDirectedInteractionWithTimeRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_parametervalues(_clientConverter->convertFromHla(parameterValues));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));
      request->set_allocated_time(_clientConverter->convertFromHla(time));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_senddirectedinteractionwithtimerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_senddirectedinteractionwithtimeresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::deleteObjectInstance(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncDeleteObjectInstance(
         objectInstance,
         userSuppliedTag      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncDeleteObjectInstance(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::DeleteObjectInstanceRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_deleteobjectinstancerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_deleteobjectinstanceresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::MessageRetractionHandle RTIambassadorClient::deleteObjectInstance(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
      const RTI_NAMESPACE::LogicalTime & time) 
    {
      auto getResponse = asyncDeleteObjectInstance(
         objectInstance,
         userSuppliedTag,
         time      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::MessageRetractionHandle> RTIambassadorClient::asyncDeleteObjectInstance(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
      const RTI_NAMESPACE::LogicalTime & time)
   {
      auto * request = new rti1516_2025::fedpro::DeleteObjectInstanceWithTimeRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));
      request->set_allocated_time(_clientConverter->convertFromHla(time));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_deleteobjectinstancewithtimerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_deleteobjectinstancewithtimeresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::localDeleteObjectInstance(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance) 
    {
      auto getResponse = asyncLocalDeleteObjectInstance(
         objectInstance      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncLocalDeleteObjectInstance(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance)
   {
      auto * request = new rti1516_2025::fedpro::LocalDeleteObjectInstanceRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_localdeleteobjectinstancerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_localdeleteobjectinstanceresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::requestAttributeValueUpdate(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncRequestAttributeValueUpdate(
         objectInstance,
         attributes,
         userSuppliedTag      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncRequestAttributeValueUpdate(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::RequestInstanceAttributeValueUpdateRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_requestinstanceattributevalueupdaterequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_requestinstanceattributevalueupdateresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::requestAttributeValueUpdate(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncRequestAttributeValueUpdate(
         objectClass,
         attributes,
         userSuppliedTag      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncRequestAttributeValueUpdate(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::RequestClassAttributeValueUpdateRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_requestclassattributevalueupdaterequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_requestclassattributevalueupdateresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::requestAttributeTransportationTypeChange(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::TransportationTypeHandle & transportationType) 
    {
      auto getResponse = asyncRequestAttributeTransportationTypeChange(
         objectInstance,
         attributes,
         transportationType      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncRequestAttributeTransportationTypeChange(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::TransportationTypeHandle & transportationType)
   {
      auto * request = new rti1516_2025::fedpro::RequestAttributeTransportationTypeChangeRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));
      request->set_allocated_transportationtype(_clientConverter->convertFromHla(transportationType));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_requestattributetransportationtypechangerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_requestattributetransportationtypechangeresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::changeDefaultAttributeTransportationType(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::TransportationTypeHandle & transportationType) 
    {
      auto getResponse = asyncChangeDefaultAttributeTransportationType(
         objectClass,
         attributes,
         transportationType      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncChangeDefaultAttributeTransportationType(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::TransportationTypeHandle & transportationType)
   {
      auto * request = new rti1516_2025::fedpro::ChangeDefaultAttributeTransportationTypeRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));
      request->set_allocated_transportationtype(_clientConverter->convertFromHla(transportationType));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_changedefaultattributetransportationtyperequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_changedefaultattributetransportationtyperesponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::queryAttributeTransportationType(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandle & attribute) 
    {
      auto getResponse = asyncQueryAttributeTransportationType(
         objectInstance,
         attribute      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncQueryAttributeTransportationType(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandle & attribute)
   {
      auto * request = new rti1516_2025::fedpro::QueryAttributeTransportationTypeRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attribute(_clientConverter->convertFromHla(attribute));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_queryattributetransportationtyperequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_queryattributetransportationtyperesponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::requestInteractionTransportationTypeChange(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::TransportationTypeHandle & transportationType) 
    {
      auto getResponse = asyncRequestInteractionTransportationTypeChange(
         interactionClass,
         transportationType      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncRequestInteractionTransportationTypeChange(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::TransportationTypeHandle & transportationType)
   {
      auto * request = new rti1516_2025::fedpro::RequestInteractionTransportationTypeChangeRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));
      request->set_allocated_transportationtype(_clientConverter->convertFromHla(transportationType));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_requestinteractiontransportationtypechangerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_requestinteractiontransportationtypechangeresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::queryInteractionTransportationType(
      const RTI_NAMESPACE::FederateHandle & federate,
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass) 
    {
      auto getResponse = asyncQueryInteractionTransportationType(
         federate,
         interactionClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncQueryInteractionTransportationType(
      const RTI_NAMESPACE::FederateHandle & federate,
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass)
   {
      auto * request = new rti1516_2025::fedpro::QueryInteractionTransportationTypeRequest();
      request->set_allocated_federate(_clientConverter->convertFromHla(federate));
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_queryinteractiontransportationtyperequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_queryinteractiontransportationtyperesponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::unconditionalAttributeOwnershipDivestiture(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncUnconditionalAttributeOwnershipDivestiture(
         objectInstance,
         attributes,
         userSuppliedTag      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUnconditionalAttributeOwnershipDivestiture(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::UnconditionalAttributeOwnershipDivestitureRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_unconditionalattributeownershipdivestiturerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_unconditionalattributeownershipdivestitureresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::negotiatedAttributeOwnershipDivestiture(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncNegotiatedAttributeOwnershipDivestiture(
         objectInstance,
         attributes,
         userSuppliedTag      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncNegotiatedAttributeOwnershipDivestiture(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::NegotiatedAttributeOwnershipDivestitureRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_negotiatedattributeownershipdivestiturerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_negotiatedattributeownershipdivestitureresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::confirmDivestiture(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & confirmedAttributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncConfirmDivestiture(
         objectInstance,
         confirmedAttributes,
         userSuppliedTag      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncConfirmDivestiture(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & confirmedAttributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::ConfirmDivestitureRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_confirmedattributes(_clientConverter->convertFromHla(confirmedAttributes));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_confirmdivestiturerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_confirmdivestitureresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::attributeOwnershipAcquisition(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & desiredAttributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncAttributeOwnershipAcquisition(
         objectInstance,
         desiredAttributes,
         userSuppliedTag      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncAttributeOwnershipAcquisition(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & desiredAttributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::AttributeOwnershipAcquisitionRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_desiredattributes(_clientConverter->convertFromHla(desiredAttributes));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_attributeownershipacquisitionrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_attributeownershipacquisitionresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::attributeOwnershipAcquisitionIfAvailable(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & desiredAttributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncAttributeOwnershipAcquisitionIfAvailable(
         objectInstance,
         desiredAttributes,
         userSuppliedTag      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncAttributeOwnershipAcquisitionIfAvailable(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & desiredAttributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::AttributeOwnershipAcquisitionIfAvailableRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_desiredattributes(_clientConverter->convertFromHla(desiredAttributes));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_attributeownershipacquisitionifavailablerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_attributeownershipacquisitionifavailableresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::attributeOwnershipReleaseDenied(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncAttributeOwnershipReleaseDenied(
         objectInstance,
         attributes,
         userSuppliedTag      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncAttributeOwnershipReleaseDenied(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::AttributeOwnershipReleaseDeniedRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_attributeownershipreleasedeniedrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_attributeownershipreleasedeniedresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::AttributeHandleSet RTIambassadorClient::attributeOwnershipDivestitureIfWanted(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncAttributeOwnershipDivestitureIfWanted(
         objectInstance,
         attributes,
         userSuppliedTag      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::AttributeHandleSet> RTIambassadorClient::asyncAttributeOwnershipDivestitureIfWanted(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::AttributeOwnershipDivestitureIfWantedRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_attributeownershipdivestitureifwantedrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_attributeownershipdivestitureifwantedresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::cancelNegotiatedAttributeOwnershipDivestiture(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes) 
    {
      auto getResponse = asyncCancelNegotiatedAttributeOwnershipDivestiture(
         objectInstance,
         attributes      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncCancelNegotiatedAttributeOwnershipDivestiture(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes)
   {
      auto * request = new rti1516_2025::fedpro::CancelNegotiatedAttributeOwnershipDivestitureRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_cancelnegotiatedattributeownershipdivestiturerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_cancelnegotiatedattributeownershipdivestitureresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::cancelAttributeOwnershipAcquisition(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes) 
    {
      auto getResponse = asyncCancelAttributeOwnershipAcquisition(
         objectInstance,
         attributes      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncCancelAttributeOwnershipAcquisition(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes)
   {
      auto * request = new rti1516_2025::fedpro::CancelAttributeOwnershipAcquisitionRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_cancelattributeownershipacquisitionrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_cancelattributeownershipacquisitionresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::queryAttributeOwnership(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes) 
    {
      auto getResponse = asyncQueryAttributeOwnership(
         objectInstance,
         attributes      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncQueryAttributeOwnership(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes)
   {
      auto * request = new rti1516_2025::fedpro::QueryAttributeOwnershipRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_queryattributeownershiprequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_queryattributeownershipresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   bool RTIambassadorClient::isAttributeOwnedByFederate(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandle & attribute) 
    {
      auto getResponse = asyncIsAttributeOwnedByFederate(
         objectInstance,
         attribute      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<bool> RTIambassadorClient::asyncIsAttributeOwnedByFederate(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandle & attribute)
   {
      auto * request = new rti1516_2025::fedpro::IsAttributeOwnedByFederateRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attribute(_clientConverter->convertFromHla(attribute));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_isattributeownedbyfederaterequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_isattributeownedbyfederateresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::enableTimeRegulation(
      const RTI_NAMESPACE::LogicalTimeInterval & lookahead) 
    {
      auto getResponse = asyncEnableTimeRegulation(
         lookahead      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncEnableTimeRegulation(
      const RTI_NAMESPACE::LogicalTimeInterval & lookahead)
   {
      auto * request = new rti1516_2025::fedpro::EnableTimeRegulationRequest();
      request->set_allocated_lookahead(_clientConverter->convertFromHla(lookahead));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_enabletimeregulationrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_enabletimeregulationresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::disableTimeRegulation(
) 
    {
      auto getResponse = asyncDisableTimeRegulation(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncDisableTimeRegulation(
)
   {
      auto * request = new rti1516_2025::fedpro::DisableTimeRegulationRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_disabletimeregulationrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_disabletimeregulationresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::enableTimeConstrained(
) 
    {
      auto getResponse = asyncEnableTimeConstrained(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncEnableTimeConstrained(
)
   {
      auto * request = new rti1516_2025::fedpro::EnableTimeConstrainedRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_enabletimeconstrainedrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_enabletimeconstrainedresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::disableTimeConstrained(
) 
    {
      auto getResponse = asyncDisableTimeConstrained(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncDisableTimeConstrained(
)
   {
      auto * request = new rti1516_2025::fedpro::DisableTimeConstrainedRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_disabletimeconstrainedrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_disabletimeconstrainedresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::timeAdvanceRequest(
      const RTI_NAMESPACE::LogicalTime & time) 
    {
      auto getResponse = asyncTimeAdvanceRequest(
         time      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncTimeAdvanceRequest(
      const RTI_NAMESPACE::LogicalTime & time)
   {
      auto * request = new rti1516_2025::fedpro::TimeAdvanceRequestRequest();
      request->set_allocated_time(_clientConverter->convertFromHla(time));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_timeadvancerequestrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_timeadvancerequestresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::timeAdvanceRequestAvailable(
      const RTI_NAMESPACE::LogicalTime & time) 
    {
      auto getResponse = asyncTimeAdvanceRequestAvailable(
         time      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncTimeAdvanceRequestAvailable(
      const RTI_NAMESPACE::LogicalTime & time)
   {
      auto * request = new rti1516_2025::fedpro::TimeAdvanceRequestAvailableRequest();
      request->set_allocated_time(_clientConverter->convertFromHla(time));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_timeadvancerequestavailablerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_timeadvancerequestavailableresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::nextMessageRequest(
      const RTI_NAMESPACE::LogicalTime & time) 
    {
      auto getResponse = asyncNextMessageRequest(
         time      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncNextMessageRequest(
      const RTI_NAMESPACE::LogicalTime & time)
   {
      auto * request = new rti1516_2025::fedpro::NextMessageRequestRequest();
      request->set_allocated_time(_clientConverter->convertFromHla(time));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_nextmessagerequestrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_nextmessagerequestresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::nextMessageRequestAvailable(
      const RTI_NAMESPACE::LogicalTime & time) 
    {
      auto getResponse = asyncNextMessageRequestAvailable(
         time      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncNextMessageRequestAvailable(
      const RTI_NAMESPACE::LogicalTime & time)
   {
      auto * request = new rti1516_2025::fedpro::NextMessageRequestAvailableRequest();
      request->set_allocated_time(_clientConverter->convertFromHla(time));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_nextmessagerequestavailablerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_nextmessagerequestavailableresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::flushQueueRequest(
      const RTI_NAMESPACE::LogicalTime & time) 
    {
      auto getResponse = asyncFlushQueueRequest(
         time      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncFlushQueueRequest(
      const RTI_NAMESPACE::LogicalTime & time)
   {
      auto * request = new rti1516_2025::fedpro::FlushQueueRequestRequest();
      request->set_allocated_time(_clientConverter->convertFromHla(time));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_flushqueuerequestrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_flushqueuerequestresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::enableAsynchronousDelivery(
) 
    {
      auto getResponse = asyncEnableAsynchronousDelivery(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncEnableAsynchronousDelivery(
)
   {
      auto * request = new rti1516_2025::fedpro::EnableAsynchronousDeliveryRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_enableasynchronousdeliveryrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_enableasynchronousdeliveryresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::disableAsynchronousDelivery(
) 
    {
      auto getResponse = asyncDisableAsynchronousDelivery(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncDisableAsynchronousDelivery(
)
   {
      auto * request = new rti1516_2025::fedpro::DisableAsynchronousDeliveryRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_disableasynchronousdeliveryrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_disableasynchronousdeliveryresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   std::pair< bool, std::unique_ptr<RTI_NAMESPACE::LogicalTime> > RTIambassadorClient::queryGALT(
) 
    {
      auto getResponse = asyncQueryGALT(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<std::pair< bool, std::unique_ptr<RTI_NAMESPACE::LogicalTime> >> RTIambassadorClient::asyncQueryGALT(
)
   {
      auto * request = new rti1516_2025::fedpro::QueryGALTRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_querygaltrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_querygaltresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_UNIQUE_PTR<RTI_NAMESPACE::LogicalTime> RTIambassadorClient::queryLogicalTime(
) 
    {
      auto getResponse = asyncQueryLogicalTime(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_UNIQUE_PTR<RTI_NAMESPACE::LogicalTime>> RTIambassadorClient::asyncQueryLogicalTime(
)
   {
      auto * request = new rti1516_2025::fedpro::QueryLogicalTimeRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_querylogicaltimerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_querylogicaltimeresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   std::pair< bool, std::unique_ptr<RTI_NAMESPACE::LogicalTime> > RTIambassadorClient::queryLITS(
) 
    {
      auto getResponse = asyncQueryLITS(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<std::pair< bool, std::unique_ptr<RTI_NAMESPACE::LogicalTime> >> RTIambassadorClient::asyncQueryLITS(
)
   {
      auto * request = new rti1516_2025::fedpro::QueryLITSRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_querylitsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_querylitsresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::modifyLookahead(
      const RTI_NAMESPACE::LogicalTimeInterval & lookahead) 
    {
      auto getResponse = asyncModifyLookahead(
         lookahead      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncModifyLookahead(
      const RTI_NAMESPACE::LogicalTimeInterval & lookahead)
   {
      auto * request = new rti1516_2025::fedpro::ModifyLookaheadRequest();
      request->set_allocated_lookahead(_clientConverter->convertFromHla(lookahead));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_modifylookaheadrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_modifylookaheadresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_UNIQUE_PTR<RTI_NAMESPACE::LogicalTimeInterval> RTIambassadorClient::queryLookahead(
) 
    {
      auto getResponse = asyncQueryLookahead(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_UNIQUE_PTR<RTI_NAMESPACE::LogicalTimeInterval>> RTIambassadorClient::asyncQueryLookahead(
)
   {
      auto * request = new rti1516_2025::fedpro::QueryLookaheadRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_querylookaheadrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_querylookaheadresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::retract(
      const RTI_NAMESPACE::MessageRetractionHandle & retraction) 
    {
      auto getResponse = asyncRetract(
         retraction      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncRetract(
      const RTI_NAMESPACE::MessageRetractionHandle & retraction)
   {
      auto * request = new rti1516_2025::fedpro::RetractRequest();
      request->set_allocated_retraction(_clientConverter->convertFromHla(retraction));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_retractrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_retractresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::changeAttributeOrderType(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      RTI_NAMESPACE::OrderType orderType) 
    {
      auto getResponse = asyncChangeAttributeOrderType(
         objectInstance,
         attributes,
         orderType      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncChangeAttributeOrderType(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      RTI_NAMESPACE::OrderType orderType)
   {
      auto * request = new rti1516_2025::fedpro::ChangeAttributeOrderTypeRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));
      request->set_ordertype(_clientConverter->convertFromHla(orderType));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_changeattributeordertyperequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_changeattributeordertyperesponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::changeDefaultAttributeOrderType(
      const RTI_NAMESPACE::ObjectClassHandle & theObjectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      RTI_NAMESPACE::OrderType orderType) 
    {
      auto getResponse = asyncChangeDefaultAttributeOrderType(
         theObjectClass,
         attributes,
         orderType      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncChangeDefaultAttributeOrderType(
      const RTI_NAMESPACE::ObjectClassHandle & theObjectClass,
      const RTI_NAMESPACE::AttributeHandleSet & attributes,
      RTI_NAMESPACE::OrderType orderType)
   {
      auto * request = new rti1516_2025::fedpro::ChangeDefaultAttributeOrderTypeRequest();
      request->set_allocated_theobjectclass(_clientConverter->convertFromHla(theObjectClass));
      request->set_allocated_attributes(_clientConverter->convertFromHla(attributes));
      request->set_ordertype(_clientConverter->convertFromHla(orderType));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_changedefaultattributeordertyperequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_changedefaultattributeordertyperesponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::changeInteractionOrderType(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      RTI_NAMESPACE::OrderType orderType) 
    {
      auto getResponse = asyncChangeInteractionOrderType(
         interactionClass,
         orderType      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncChangeInteractionOrderType(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      RTI_NAMESPACE::OrderType orderType)
   {
      auto * request = new rti1516_2025::fedpro::ChangeInteractionOrderTypeRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));
      request->set_ordertype(_clientConverter->convertFromHla(orderType));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_changeinteractionordertyperequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_changeinteractionordertyperesponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::RegionHandle RTIambassadorClient::createRegion(
      const RTI_NAMESPACE::DimensionHandleSet & dimensions) 
    {
      auto getResponse = asyncCreateRegion(
         dimensions      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::RegionHandle> RTIambassadorClient::asyncCreateRegion(
      const RTI_NAMESPACE::DimensionHandleSet & dimensions)
   {
      auto * request = new rti1516_2025::fedpro::CreateRegionRequest();
      request->set_allocated_dimensions(_clientConverter->convertFromHla(dimensions));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_createregionrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_createregionresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::commitRegionModifications(
      const RTI_NAMESPACE::RegionHandleSet & regions) 
    {
      auto getResponse = asyncCommitRegionModifications(
         regions      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncCommitRegionModifications(
      const RTI_NAMESPACE::RegionHandleSet & regions)
   {
      auto * request = new rti1516_2025::fedpro::CommitRegionModificationsRequest();
      request->set_allocated_regions(_clientConverter->convertFromHla(regions));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_commitregionmodificationsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_commitregionmodificationsresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::deleteRegion(
      const RTI_NAMESPACE::RegionHandle & region) 
    {
      auto getResponse = asyncDeleteRegion(
         region      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncDeleteRegion(
      const RTI_NAMESPACE::RegionHandle & region)
   {
      auto * request = new rti1516_2025::fedpro::DeleteRegionRequest();
      request->set_allocated_region(_clientConverter->convertFromHla(region));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_deleteregionrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_deleteregionresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::ObjectInstanceHandle RTIambassadorClient::registerObjectInstanceWithRegions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions) 
    {
      auto getResponse = asyncRegisterObjectInstanceWithRegions(
         objectClass,
         attributesAndRegions      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::ObjectInstanceHandle> RTIambassadorClient::asyncRegisterObjectInstanceWithRegions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions)
   {
      auto * request = new rti1516_2025::fedpro::RegisterObjectInstanceWithRegionsRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributesandregions(_clientConverter->convertFromHla(attributesAndRegions));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_registerobjectinstancewithregionsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_registerobjectinstancewithregionsresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::ObjectInstanceHandle RTIambassadorClient::registerObjectInstanceWithRegions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
      std::string const & objectInstanceName) 
    {
      auto getResponse = asyncRegisterObjectInstanceWithRegions(
         objectClass,
         attributesAndRegions,
         objectInstanceName      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::ObjectInstanceHandle> RTIambassadorClient::asyncRegisterObjectInstanceWithRegions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
      std::string const & objectInstanceName)
   {
      auto * request = new rti1516_2025::fedpro::RegisterObjectInstanceWithNameAndRegionsRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributesandregions(_clientConverter->convertFromHla(attributesAndRegions));
      request->set_allocated_objectinstancename(_clientConverter->convertFromHla(objectInstanceName));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_registerobjectinstancewithnameandregionsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_registerobjectinstancewithnameandregionsresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::associateRegionsForUpdates(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions) 
    {
      auto getResponse = asyncAssociateRegionsForUpdates(
         objectInstance,
         attributesAndRegions      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncAssociateRegionsForUpdates(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions)
   {
      auto * request = new rti1516_2025::fedpro::AssociateRegionsForUpdatesRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attributesandregions(_clientConverter->convertFromHla(attributesAndRegions));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_associateregionsforupdatesrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_associateregionsforupdatesresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::unassociateRegionsForUpdates(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions) 
    {
      auto getResponse = asyncUnassociateRegionsForUpdates(
         objectInstance,
         attributesAndRegions      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUnassociateRegionsForUpdates(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions)
   {
      auto * request = new rti1516_2025::fedpro::UnassociateRegionsForUpdatesRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attributesandregions(_clientConverter->convertFromHla(attributesAndRegions));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_unassociateregionsforupdatesrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_unassociateregionsforupdatesresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::subscribeObjectClassAttributesWithRegions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
      bool active) 
    {
      auto getResponse = asyncSubscribeObjectClassAttributesWithRegions(
         objectClass,
         attributesAndRegions,
         active      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSubscribeObjectClassAttributesWithRegions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
      bool active)
   {
      auto * request = new rti1516_2025::fedpro::SubscribeObjectClassAttributesWithRegionsRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributesandregions(_clientConverter->convertFromHla(attributesAndRegions));
      request->set_active(_clientConverter->convertFromHla(active));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_subscribeobjectclassattributeswithregionsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_subscribeobjectclassattributeswithregionsresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::subscribeObjectClassAttributesWithRegions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
      bool active,
      std::string const & updateRateDesignator) 
    {
      auto getResponse = asyncSubscribeObjectClassAttributesWithRegions(
         objectClass,
         attributesAndRegions,
         active,
         updateRateDesignator      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSubscribeObjectClassAttributesWithRegions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
      bool active,
      std::string const & updateRateDesignator)
   {
      auto * request = new rti1516_2025::fedpro::SubscribeObjectClassAttributesWithRegionsAndRateRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributesandregions(_clientConverter->convertFromHla(attributesAndRegions));
      request->set_active(_clientConverter->convertFromHla(active));
      request->set_allocated_updateratedesignator(_clientConverter->convertFromHla(updateRateDesignator));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_subscribeobjectclassattributeswithregionsandraterequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_subscribeobjectclassattributeswithregionsandrateresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::unsubscribeObjectClassAttributesWithRegions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions) 
    {
      auto getResponse = asyncUnsubscribeObjectClassAttributesWithRegions(
         objectClass,
         attributesAndRegions      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUnsubscribeObjectClassAttributesWithRegions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions)
   {
      auto * request = new rti1516_2025::fedpro::UnsubscribeObjectClassAttributesWithRegionsRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributesandregions(_clientConverter->convertFromHla(attributesAndRegions));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_unsubscribeobjectclassattributeswithregionsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_unsubscribeobjectclassattributeswithregionsresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::subscribeInteractionClassWithRegions(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      bool active,
      const RTI_NAMESPACE::RegionHandleSet & regions) 
    {
      auto getResponse = asyncSubscribeInteractionClassWithRegions(
         interactionClass,
         active,
         regions      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSubscribeInteractionClassWithRegions(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      bool active,
      const RTI_NAMESPACE::RegionHandleSet & regions)
   {
      auto * request = new rti1516_2025::fedpro::SubscribeInteractionClassWithRegionsRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));
      request->set_active(_clientConverter->convertFromHla(active));
      request->set_allocated_regions(_clientConverter->convertFromHla(regions));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_subscribeinteractionclasswithregionsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_subscribeinteractionclasswithregionsresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::unsubscribeInteractionClassWithRegions(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::RegionHandleSet & regions) 
    {
      auto getResponse = asyncUnsubscribeInteractionClassWithRegions(
         interactionClass,
         regions      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncUnsubscribeInteractionClassWithRegions(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::RegionHandleSet & regions)
   {
      auto * request = new rti1516_2025::fedpro::UnsubscribeInteractionClassWithRegionsRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));
      request->set_allocated_regions(_clientConverter->convertFromHla(regions));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_unsubscribeinteractionclasswithregionsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_unsubscribeinteractionclasswithregionsresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::sendInteractionWithRegions(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
      const RTI_NAMESPACE::RegionHandleSet & regions,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncSendInteractionWithRegions(
         interactionClass,
         parameterValues,
         regions,
         userSuppliedTag      );
      countSyncSentInteractionForStats();
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSendInteractionWithRegions(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
      const RTI_NAMESPACE::RegionHandleSet & regions,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::SendInteractionWithRegionsRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));
      request->set_allocated_parametervalues(_clientConverter->convertFromHla(parameterValues));
      request->set_allocated_regions(_clientConverter->convertFromHla(regions));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_sendinteractionwithregionsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_sendinteractionwithregionsresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::MessageRetractionHandle RTIambassadorClient::sendInteractionWithRegions(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
      const RTI_NAMESPACE::RegionHandleSet & regions,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
      const RTI_NAMESPACE::LogicalTime & time) 
    {
      auto getResponse = asyncSendInteractionWithRegions(
         interactionClass,
         parameterValues,
         regions,
         userSuppliedTag,
         time      );
      countSyncSentInteractionForStats();
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::MessageRetractionHandle> RTIambassadorClient::asyncSendInteractionWithRegions(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::ParameterHandleValueMap & parameterValues,
      const RTI_NAMESPACE::RegionHandleSet & regions,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag,
      const RTI_NAMESPACE::LogicalTime & time)
   {
      auto * request = new rti1516_2025::fedpro::SendInteractionWithRegionsAndTimeRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));
      request->set_allocated_parametervalues(_clientConverter->convertFromHla(parameterValues));
      request->set_allocated_regions(_clientConverter->convertFromHla(regions));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));
      request->set_allocated_time(_clientConverter->convertFromHla(time));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_sendinteractionwithregionsandtimerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_sendinteractionwithregionsandtimeresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::requestAttributeValueUpdateWithRegions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag) 
    {
      auto getResponse = asyncRequestAttributeValueUpdateWithRegions(
         objectClass,
         attributesAndRegions,
         userSuppliedTag      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncRequestAttributeValueUpdateWithRegions(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
      const RTI_NAMESPACE::VariableLengthData & userSuppliedTag)
   {
      auto * request = new rti1516_2025::fedpro::RequestAttributeValueUpdateWithRegionsRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributesandregions(_clientConverter->convertFromHla(attributesAndRegions));
      request->set_usersuppliedtag(_clientConverter->convertFromHla(userSuppliedTag));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_requestattributevalueupdatewithregionsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_requestattributevalueupdatewithregionsresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::FederateHandle RTIambassadorClient::getFederateHandle(
      std::string const & federateName) 
    {
      auto getResponse = asyncGetFederateHandle(
         federateName      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::FederateHandle> RTIambassadorClient::asyncGetFederateHandle(
      std::string const & federateName)
   {
      auto * request = new rti1516_2025::fedpro::GetFederateHandleRequest();
      request->set_allocated_federatename(_clientConverter->convertFromHla(federateName));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getfederatehandlerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getfederatehandleresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   std::wstring RTIambassadorClient::getFederateName(
      const RTI_NAMESPACE::FederateHandle & federate) 
    {
      auto getResponse = asyncGetFederateName(
         federate      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<std::wstring> RTIambassadorClient::asyncGetFederateName(
      const RTI_NAMESPACE::FederateHandle & federate)
   {
      auto * request = new rti1516_2025::fedpro::GetFederateNameRequest();
      request->set_allocated_federate(_clientConverter->convertFromHla(federate));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getfederatenamerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getfederatenameresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::ObjectClassHandle RTIambassadorClient::getObjectClassHandle(
      std::string const & objectClassName) 
    {
      auto getResponse = asyncGetObjectClassHandle(
         objectClassName      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::ObjectClassHandle> RTIambassadorClient::asyncGetObjectClassHandle(
      std::string const & objectClassName)
   {
      auto * request = new rti1516_2025::fedpro::GetObjectClassHandleRequest();
      request->set_allocated_objectclassname(_clientConverter->convertFromHla(objectClassName));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getobjectclasshandlerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getobjectclasshandleresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   std::wstring RTIambassadorClient::getObjectClassName(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass) 
    {
      auto getResponse = asyncGetObjectClassName(
         objectClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<std::wstring> RTIambassadorClient::asyncGetObjectClassName(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass)
   {
      auto * request = new rti1516_2025::fedpro::GetObjectClassNameRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getobjectclassnamerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getobjectclassnameresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::ObjectClassHandle RTIambassadorClient::getKnownObjectClassHandle(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance) 
    {
      auto getResponse = asyncGetKnownObjectClassHandle(
         objectInstance      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::ObjectClassHandle> RTIambassadorClient::asyncGetKnownObjectClassHandle(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance)
   {
      auto * request = new rti1516_2025::fedpro::GetKnownObjectClassHandleRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getknownobjectclasshandlerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getknownobjectclasshandleresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::ObjectInstanceHandle RTIambassadorClient::getObjectInstanceHandle(
      std::string const & objectInstanceName) 
    {
      auto getResponse = asyncGetObjectInstanceHandle(
         objectInstanceName      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::ObjectInstanceHandle> RTIambassadorClient::asyncGetObjectInstanceHandle(
      std::string const & objectInstanceName)
   {
      auto * request = new rti1516_2025::fedpro::GetObjectInstanceHandleRequest();
      request->set_allocated_objectinstancename(_clientConverter->convertFromHla(objectInstanceName));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getobjectinstancehandlerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getobjectinstancehandleresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   std::wstring RTIambassadorClient::getObjectInstanceName(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance) 
    {
      auto getResponse = asyncGetObjectInstanceName(
         objectInstance      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<std::wstring> RTIambassadorClient::asyncGetObjectInstanceName(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance)
   {
      auto * request = new rti1516_2025::fedpro::GetObjectInstanceNameRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getobjectinstancenamerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getobjectinstancenameresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::AttributeHandle RTIambassadorClient::getAttributeHandle(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      std::string const & attributeName) 
    {
      auto getResponse = asyncGetAttributeHandle(
         objectClass,
         attributeName      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::AttributeHandle> RTIambassadorClient::asyncGetAttributeHandle(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      std::string const & attributeName)
   {
      auto * request = new rti1516_2025::fedpro::GetAttributeHandleRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attributename(_clientConverter->convertFromHla(attributeName));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getattributehandlerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getattributehandleresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   std::wstring RTIambassadorClient::getAttributeName(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandle & attribute) 
    {
      auto getResponse = asyncGetAttributeName(
         objectClass,
         attribute      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<std::wstring> RTIambassadorClient::asyncGetAttributeName(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass,
      const RTI_NAMESPACE::AttributeHandle & attribute)
   {
      auto * request = new rti1516_2025::fedpro::GetAttributeNameRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));
      request->set_allocated_attribute(_clientConverter->convertFromHla(attribute));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getattributenamerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getattributenameresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   double RTIambassadorClient::getUpdateRateValue(
      std::string const & updateRateDesignator) 
    {
      auto getResponse = asyncGetUpdateRateValue(
         updateRateDesignator      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<double> RTIambassadorClient::asyncGetUpdateRateValue(
      std::string const & updateRateDesignator)
   {
      auto * request = new rti1516_2025::fedpro::GetUpdateRateValueRequest();
      request->set_allocated_updateratedesignator(_clientConverter->convertFromHla(updateRateDesignator));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getupdateratevaluerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getupdateratevalueresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   double RTIambassadorClient::getUpdateRateValueForAttribute(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandle & attribute) 
    {
      auto getResponse = asyncGetUpdateRateValueForAttribute(
         objectInstance,
         attribute      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<double> RTIambassadorClient::asyncGetUpdateRateValueForAttribute(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance,
      const RTI_NAMESPACE::AttributeHandle & attribute)
   {
      auto * request = new rti1516_2025::fedpro::GetUpdateRateValueForAttributeRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));
      request->set_allocated_attribute(_clientConverter->convertFromHla(attribute));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getupdateratevalueforattributerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getupdateratevalueforattributeresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::InteractionClassHandle RTIambassadorClient::getInteractionClassHandle(
      std::string const & interactionClassName) 
    {
      auto getResponse = asyncGetInteractionClassHandle(
         interactionClassName      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::InteractionClassHandle> RTIambassadorClient::asyncGetInteractionClassHandle(
      std::string const & interactionClassName)
   {
      auto * request = new rti1516_2025::fedpro::GetInteractionClassHandleRequest();
      request->set_allocated_interactionclassname(_clientConverter->convertFromHla(interactionClassName));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getinteractionclasshandlerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getinteractionclasshandleresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   std::wstring RTIambassadorClient::getInteractionClassName(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass) 
    {
      auto getResponse = asyncGetInteractionClassName(
         interactionClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<std::wstring> RTIambassadorClient::asyncGetInteractionClassName(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass)
   {
      auto * request = new rti1516_2025::fedpro::GetInteractionClassNameRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getinteractionclassnamerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getinteractionclassnameresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::ParameterHandle RTIambassadorClient::getParameterHandle(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      std::string const & parameterName) 
    {
      auto getResponse = asyncGetParameterHandle(
         interactionClass,
         parameterName      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::ParameterHandle> RTIambassadorClient::asyncGetParameterHandle(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      std::string const & parameterName)
   {
      auto * request = new rti1516_2025::fedpro::GetParameterHandleRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));
      request->set_allocated_parametername(_clientConverter->convertFromHla(parameterName));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getparameterhandlerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getparameterhandleresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   std::wstring RTIambassadorClient::getParameterName(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::ParameterHandle & parameter) 
    {
      auto getResponse = asyncGetParameterName(
         interactionClass,
         parameter      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<std::wstring> RTIambassadorClient::asyncGetParameterName(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass,
      const RTI_NAMESPACE::ParameterHandle & parameter)
   {
      auto * request = new rti1516_2025::fedpro::GetParameterNameRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));
      request->set_allocated_parameter(_clientConverter->convertFromHla(parameter));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getparameternamerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getparameternameresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::OrderType RTIambassadorClient::getOrderType(
      std::string const & orderTypeName) 
    {
      auto getResponse = asyncGetOrderType(
         orderTypeName      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::OrderType> RTIambassadorClient::asyncGetOrderType(
      std::string const & orderTypeName)
   {
      auto * request = new rti1516_2025::fedpro::GetOrderTypeRequest();
      request->set_allocated_ordertypename(_clientConverter->convertFromHla(orderTypeName));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getordertyperequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getordertyperesponse());
      }, std::move(futureResponse), _clientConverter};
   }


   std::wstring RTIambassadorClient::getOrderName(
      RTI_NAMESPACE::OrderType orderType) 
    {
      auto getResponse = asyncGetOrderName(
         orderType      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<std::wstring> RTIambassadorClient::asyncGetOrderName(
      RTI_NAMESPACE::OrderType orderType)
   {
      auto * request = new rti1516_2025::fedpro::GetOrderNameRequest();
      request->set_ordertype(_clientConverter->convertFromHla(orderType));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getordernamerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getordernameresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::TransportationTypeHandle RTIambassadorClient::getTransportationTypeHandle(
      std::string const & transportationTypeName) 
    {
      auto getResponse = asyncGetTransportationTypeHandle(
         transportationTypeName      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::TransportationTypeHandle> RTIambassadorClient::asyncGetTransportationTypeHandle(
      std::string const & transportationTypeName)
   {
      auto * request = new rti1516_2025::fedpro::GetTransportationTypeHandleRequest();
      request->set_allocated_transportationtypename(_clientConverter->convertFromHla(transportationTypeName));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_gettransportationtypehandlerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_gettransportationtypehandleresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   std::wstring RTIambassadorClient::getTransportationTypeName(
      const RTI_NAMESPACE::TransportationTypeHandle & transportationType) 
    {
      auto getResponse = asyncGetTransportationTypeName(
         transportationType      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<std::wstring> RTIambassadorClient::asyncGetTransportationTypeName(
      const RTI_NAMESPACE::TransportationTypeHandle & transportationType)
   {
      auto * request = new rti1516_2025::fedpro::GetTransportationTypeNameRequest();
      request->set_allocated_transportationtype(_clientConverter->convertFromHla(transportationType));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_gettransportationtypenamerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_gettransportationtypenameresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::DimensionHandleSet RTIambassadorClient::getAvailableDimensionsForObjectClass(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass) 
    {
      auto getResponse = asyncGetAvailableDimensionsForObjectClass(
         objectClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::DimensionHandleSet> RTIambassadorClient::asyncGetAvailableDimensionsForObjectClass(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass)
   {
      auto * request = new rti1516_2025::fedpro::GetAvailableDimensionsForObjectClassRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getavailabledimensionsforobjectclassrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getavailabledimensionsforobjectclassresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::DimensionHandleSet RTIambassadorClient::getAvailableDimensionsForInteractionClass(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass) 
    {
      auto getResponse = asyncGetAvailableDimensionsForInteractionClass(
         interactionClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::DimensionHandleSet> RTIambassadorClient::asyncGetAvailableDimensionsForInteractionClass(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass)
   {
      auto * request = new rti1516_2025::fedpro::GetAvailableDimensionsForInteractionClassRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getavailabledimensionsforinteractionclassrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getavailabledimensionsforinteractionclassresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::DimensionHandle RTIambassadorClient::getDimensionHandle(
      std::string const & dimensionName) 
    {
      auto getResponse = asyncGetDimensionHandle(
         dimensionName      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::DimensionHandle> RTIambassadorClient::asyncGetDimensionHandle(
      std::string const & dimensionName)
   {
      auto * request = new rti1516_2025::fedpro::GetDimensionHandleRequest();
      request->set_allocated_dimensionname(_clientConverter->convertFromHla(dimensionName));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getdimensionhandlerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getdimensionhandleresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   std::wstring RTIambassadorClient::getDimensionName(
      const RTI_NAMESPACE::DimensionHandle & dimension) 
    {
      auto getResponse = asyncGetDimensionName(
         dimension      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<std::wstring> RTIambassadorClient::asyncGetDimensionName(
      const RTI_NAMESPACE::DimensionHandle & dimension)
   {
      auto * request = new rti1516_2025::fedpro::GetDimensionNameRequest();
      request->set_allocated_dimension(_clientConverter->convertFromHla(dimension));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getdimensionnamerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getdimensionnameresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   unsigned long RTIambassadorClient::getDimensionUpperBound(
      const RTI_NAMESPACE::DimensionHandle & dimension) 
    {
      auto getResponse = asyncGetDimensionUpperBound(
         dimension      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<unsigned long> RTIambassadorClient::asyncGetDimensionUpperBound(
      const RTI_NAMESPACE::DimensionHandle & dimension)
   {
      auto * request = new rti1516_2025::fedpro::GetDimensionUpperBoundRequest();
      request->set_allocated_dimension(_clientConverter->convertFromHla(dimension));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getdimensionupperboundrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getdimensionupperboundresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::DimensionHandleSet RTIambassadorClient::getDimensionHandleSet(
      const RTI_NAMESPACE::RegionHandle & region) 
    {
      auto getResponse = asyncGetDimensionHandleSet(
         region      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::DimensionHandleSet> RTIambassadorClient::asyncGetDimensionHandleSet(
      const RTI_NAMESPACE::RegionHandle & region)
   {
      auto * request = new rti1516_2025::fedpro::GetDimensionHandleSetRequest();
      request->set_allocated_region(_clientConverter->convertFromHla(region));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getdimensionhandlesetrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getdimensionhandlesetresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::RangeBounds RTIambassadorClient::getRangeBounds(
      const RTI_NAMESPACE::RegionHandle & region,
      const RTI_NAMESPACE::DimensionHandle & dimension) 
    {
      auto getResponse = asyncGetRangeBounds(
         region,
         dimension      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::RangeBounds> RTIambassadorClient::asyncGetRangeBounds(
      const RTI_NAMESPACE::RegionHandle & region,
      const RTI_NAMESPACE::DimensionHandle & dimension)
   {
      auto * request = new rti1516_2025::fedpro::GetRangeBoundsRequest();
      request->set_allocated_region(_clientConverter->convertFromHla(region));
      request->set_allocated_dimension(_clientConverter->convertFromHla(dimension));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getrangeboundsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getrangeboundsresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::setRangeBounds(
      const RTI_NAMESPACE::RegionHandle & region,
      const RTI_NAMESPACE::DimensionHandle & dimension,
      const RTI_NAMESPACE::RangeBounds & rangeBounds) 
    {
      auto getResponse = asyncSetRangeBounds(
         region,
         dimension,
         rangeBounds      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSetRangeBounds(
      const RTI_NAMESPACE::RegionHandle & region,
      const RTI_NAMESPACE::DimensionHandle & dimension,
      const RTI_NAMESPACE::RangeBounds & rangeBounds)
   {
      auto * request = new rti1516_2025::fedpro::SetRangeBoundsRequest();
      request->set_allocated_region(_clientConverter->convertFromHla(region));
      request->set_allocated_dimension(_clientConverter->convertFromHla(dimension));
      request->set_allocated_rangebounds(_clientConverter->convertFromHla(rangeBounds));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_setrangeboundsrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_setrangeboundsresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   unsigned long RTIambassadorClient::normalizeServiceGroup(
      RTI_NAMESPACE::ServiceGroup serviceGroup) 
    {
      auto getResponse = asyncNormalizeServiceGroup(
         serviceGroup      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<unsigned long> RTIambassadorClient::asyncNormalizeServiceGroup(
      RTI_NAMESPACE::ServiceGroup serviceGroup)
   {
      auto * request = new rti1516_2025::fedpro::NormalizeServiceGroupRequest();
      request->set_servicegroup(_clientConverter->convertFromHla(serviceGroup));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_normalizeservicegrouprequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_normalizeservicegroupresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   unsigned long RTIambassadorClient::normalizeFederateHandle(
      const RTI_NAMESPACE::FederateHandle & federate) 
    {
      auto getResponse = asyncNormalizeFederateHandle(
         federate      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<unsigned long> RTIambassadorClient::asyncNormalizeFederateHandle(
      const RTI_NAMESPACE::FederateHandle & federate)
   {
      auto * request = new rti1516_2025::fedpro::NormalizeFederateHandleRequest();
      request->set_allocated_federate(_clientConverter->convertFromHla(federate));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_normalizefederatehandlerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_normalizefederatehandleresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   unsigned long RTIambassadorClient::normalizeObjectClassHandle(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass) 
    {
      auto getResponse = asyncNormalizeObjectClassHandle(
         objectClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<unsigned long> RTIambassadorClient::asyncNormalizeObjectClassHandle(
      const RTI_NAMESPACE::ObjectClassHandle & objectClass)
   {
      auto * request = new rti1516_2025::fedpro::NormalizeObjectClassHandleRequest();
      request->set_allocated_objectclass(_clientConverter->convertFromHla(objectClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_normalizeobjectclasshandlerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_normalizeobjectclasshandleresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   unsigned long RTIambassadorClient::normalizeInteractionClassHandle(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass) 
    {
      auto getResponse = asyncNormalizeInteractionClassHandle(
         interactionClass      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<unsigned long> RTIambassadorClient::asyncNormalizeInteractionClassHandle(
      const RTI_NAMESPACE::InteractionClassHandle & interactionClass)
   {
      auto * request = new rti1516_2025::fedpro::NormalizeInteractionClassHandleRequest();
      request->set_allocated_interactionclass(_clientConverter->convertFromHla(interactionClass));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_normalizeinteractionclasshandlerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_normalizeinteractionclasshandleresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   unsigned long RTIambassadorClient::normalizeObjectInstanceHandle(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance) 
    {
      auto getResponse = asyncNormalizeObjectInstanceHandle(
         objectInstance      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<unsigned long> RTIambassadorClient::asyncNormalizeObjectInstanceHandle(
      const RTI_NAMESPACE::ObjectInstanceHandle & objectInstance)
   {
      auto * request = new rti1516_2025::fedpro::NormalizeObjectInstanceHandleRequest();
      request->set_allocated_objectinstance(_clientConverter->convertFromHla(objectInstance));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_normalizeobjectinstancehandlerequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_normalizeobjectinstancehandleresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   bool RTIambassadorClient::getObjectClassRelevanceAdvisorySwitch(
) 
    {
      auto getResponse = asyncGetObjectClassRelevanceAdvisorySwitch(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<bool> RTIambassadorClient::asyncGetObjectClassRelevanceAdvisorySwitch(
)
   {
      auto * request = new rti1516_2025::fedpro::GetObjectClassRelevanceAdvisorySwitchRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getobjectclassrelevanceadvisoryswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getobjectclassrelevanceadvisoryswitchresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::setObjectClassRelevanceAdvisorySwitch(
      bool value) 
    {
      auto getResponse = asyncSetObjectClassRelevanceAdvisorySwitch(
         value      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSetObjectClassRelevanceAdvisorySwitch(
      bool value)
   {
      auto * request = new rti1516_2025::fedpro::SetObjectClassRelevanceAdvisorySwitchRequest();
      request->set_value(_clientConverter->convertFromHla(value));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_setobjectclassrelevanceadvisoryswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_setobjectclassrelevanceadvisoryswitchresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   bool RTIambassadorClient::getAttributeRelevanceAdvisorySwitch(
) 
    {
      auto getResponse = asyncGetAttributeRelevanceAdvisorySwitch(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<bool> RTIambassadorClient::asyncGetAttributeRelevanceAdvisorySwitch(
)
   {
      auto * request = new rti1516_2025::fedpro::GetAttributeRelevanceAdvisorySwitchRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getattributerelevanceadvisoryswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getattributerelevanceadvisoryswitchresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::setAttributeRelevanceAdvisorySwitch(
      bool value) 
    {
      auto getResponse = asyncSetAttributeRelevanceAdvisorySwitch(
         value      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSetAttributeRelevanceAdvisorySwitch(
      bool value)
   {
      auto * request = new rti1516_2025::fedpro::SetAttributeRelevanceAdvisorySwitchRequest();
      request->set_value(_clientConverter->convertFromHla(value));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_setattributerelevanceadvisoryswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_setattributerelevanceadvisoryswitchresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   bool RTIambassadorClient::getAttributeScopeAdvisorySwitch(
) 
    {
      auto getResponse = asyncGetAttributeScopeAdvisorySwitch(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<bool> RTIambassadorClient::asyncGetAttributeScopeAdvisorySwitch(
)
   {
      auto * request = new rti1516_2025::fedpro::GetAttributeScopeAdvisorySwitchRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getattributescopeadvisoryswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getattributescopeadvisoryswitchresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::setAttributeScopeAdvisorySwitch(
      bool value) 
    {
      auto getResponse = asyncSetAttributeScopeAdvisorySwitch(
         value      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSetAttributeScopeAdvisorySwitch(
      bool value)
   {
      auto * request = new rti1516_2025::fedpro::SetAttributeScopeAdvisorySwitchRequest();
      request->set_value(_clientConverter->convertFromHla(value));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_setattributescopeadvisoryswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_setattributescopeadvisoryswitchresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   bool RTIambassadorClient::getInteractionRelevanceAdvisorySwitch(
) 
    {
      auto getResponse = asyncGetInteractionRelevanceAdvisorySwitch(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<bool> RTIambassadorClient::asyncGetInteractionRelevanceAdvisorySwitch(
)
   {
      auto * request = new rti1516_2025::fedpro::GetInteractionRelevanceAdvisorySwitchRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getinteractionrelevanceadvisoryswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getinteractionrelevanceadvisoryswitchresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::setInteractionRelevanceAdvisorySwitch(
      bool value) 
    {
      auto getResponse = asyncSetInteractionRelevanceAdvisorySwitch(
         value      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSetInteractionRelevanceAdvisorySwitch(
      bool value)
   {
      auto * request = new rti1516_2025::fedpro::SetInteractionRelevanceAdvisorySwitchRequest();
      request->set_value(_clientConverter->convertFromHla(value));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_setinteractionrelevanceadvisoryswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_setinteractionrelevanceadvisoryswitchresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   bool RTIambassadorClient::getConveyRegionDesignatorSetsSwitch(
) 
    {
      auto getResponse = asyncGetConveyRegionDesignatorSetsSwitch(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<bool> RTIambassadorClient::asyncGetConveyRegionDesignatorSetsSwitch(
)
   {
      auto * request = new rti1516_2025::fedpro::GetConveyRegionDesignatorSetsSwitchRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getconveyregiondesignatorsetsswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getconveyregiondesignatorsetsswitchresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::setConveyRegionDesignatorSetsSwitch(
      bool value) 
    {
      auto getResponse = asyncSetConveyRegionDesignatorSetsSwitch(
         value      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSetConveyRegionDesignatorSetsSwitch(
      bool value)
   {
      auto * request = new rti1516_2025::fedpro::SetConveyRegionDesignatorSetsSwitchRequest();
      request->set_value(_clientConverter->convertFromHla(value));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_setconveyregiondesignatorsetsswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_setconveyregiondesignatorsetsswitchresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   RTI_NAMESPACE::ResignAction RTIambassadorClient::getAutomaticResignDirective(
) 
    {
      auto getResponse = asyncGetAutomaticResignDirective(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<RTI_NAMESPACE::ResignAction> RTIambassadorClient::asyncGetAutomaticResignDirective(
)
   {
      auto * request = new rti1516_2025::fedpro::GetAutomaticResignDirectiveRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getautomaticresigndirectiverequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getautomaticresigndirectiveresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::setAutomaticResignDirective(
      RTI_NAMESPACE::ResignAction value) 
    {
      auto getResponse = asyncSetAutomaticResignDirective(
         value      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSetAutomaticResignDirective(
      RTI_NAMESPACE::ResignAction value)
   {
      auto * request = new rti1516_2025::fedpro::SetAutomaticResignDirectiveRequest();
      request->set_value(_clientConverter->convertFromHla(value));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_setautomaticresigndirectiverequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_setautomaticresigndirectiveresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   bool RTIambassadorClient::getServiceReportingSwitch(
) 
    {
      auto getResponse = asyncGetServiceReportingSwitch(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<bool> RTIambassadorClient::asyncGetServiceReportingSwitch(
)
   {
      auto * request = new rti1516_2025::fedpro::GetServiceReportingSwitchRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getservicereportingswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getservicereportingswitchresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::setServiceReportingSwitch(
      bool value) 
    {
      auto getResponse = asyncSetServiceReportingSwitch(
         value      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSetServiceReportingSwitch(
      bool value)
   {
      auto * request = new rti1516_2025::fedpro::SetServiceReportingSwitchRequest();
      request->set_value(_clientConverter->convertFromHla(value));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_setservicereportingswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_setservicereportingswitchresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   bool RTIambassadorClient::getExceptionReportingSwitch(
) 
    {
      auto getResponse = asyncGetExceptionReportingSwitch(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<bool> RTIambassadorClient::asyncGetExceptionReportingSwitch(
)
   {
      auto * request = new rti1516_2025::fedpro::GetExceptionReportingSwitchRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getexceptionreportingswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getexceptionreportingswitchresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::setExceptionReportingSwitch(
      bool value) 
    {
      auto getResponse = asyncSetExceptionReportingSwitch(
         value      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSetExceptionReportingSwitch(
      bool value)
   {
      auto * request = new rti1516_2025::fedpro::SetExceptionReportingSwitchRequest();
      request->set_value(_clientConverter->convertFromHla(value));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_setexceptionreportingswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_setexceptionreportingswitchresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   bool RTIambassadorClient::getSendServiceReportsToFileSwitch(
) 
    {
      auto getResponse = asyncGetSendServiceReportsToFileSwitch(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<bool> RTIambassadorClient::asyncGetSendServiceReportsToFileSwitch(
)
   {
      auto * request = new rti1516_2025::fedpro::GetSendServiceReportsToFileSwitchRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getsendservicereportstofileswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getsendservicereportstofileswitchresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   void RTIambassadorClient::setSendServiceReportsToFileSwitch(
      bool value) 
    {
      auto getResponse = asyncSetSendServiceReportsToFileSwitch(
         value      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<void> RTIambassadorClient::asyncSetSendServiceReportsToFileSwitch(
      bool value)
   {
      auto * request = new rti1516_2025::fedpro::SetSendServiceReportsToFileSwitchRequest();
      request->set_value(_clientConverter->convertFromHla(value));

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_setsendservicereportstofileswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         if (!response.has_setsendservicereportstofileswitchresponse()) {
            throw RTI_NAMESPACE::InternalError(L"Missing data in Federate Protocol response");
         }
      }, std::move(futureResponse), _clientConverter};
   }


   bool RTIambassadorClient::getAutoProvideSwitch(
) 
    {
      auto getResponse = asyncGetAutoProvideSwitch(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<bool> RTIambassadorClient::asyncGetAutoProvideSwitch(
)
   {
      auto * request = new rti1516_2025::fedpro::GetAutoProvideSwitchRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getautoprovideswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getautoprovideswitchresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   bool RTIambassadorClient::getDelaySubscriptionEvaluationSwitch(
) 
    {
      auto getResponse = asyncGetDelaySubscriptionEvaluationSwitch(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<bool> RTIambassadorClient::asyncGetDelaySubscriptionEvaluationSwitch(
)
   {
      auto * request = new rti1516_2025::fedpro::GetDelaySubscriptionEvaluationSwitchRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getdelaysubscriptionevaluationswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getdelaysubscriptionevaluationswitchresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   bool RTIambassadorClient::getAdvisoriesUseKnownClassSwitch(
) 
    {
      auto getResponse = asyncGetAdvisoriesUseKnownClassSwitch(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<bool> RTIambassadorClient::asyncGetAdvisoriesUseKnownClassSwitch(
)
   {
      auto * request = new rti1516_2025::fedpro::GetAdvisoriesUseKnownClassSwitchRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getadvisoriesuseknownclassswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getadvisoriesuseknownclassswitchresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   bool RTIambassadorClient::getAllowRelaxedDDMSwitch(
) 
    {
      auto getResponse = asyncGetAllowRelaxedDDMSwitch(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<bool> RTIambassadorClient::asyncGetAllowRelaxedDDMSwitch(
)
   {
      auto * request = new rti1516_2025::fedpro::GetAllowRelaxedDDMSwitchRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getallowrelaxedddmswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getallowrelaxedddmswitchresponse());
      }, std::move(futureResponse), _clientConverter};
   }


   bool RTIambassadorClient::getNonRegulatedGrantSwitch(
) 
    {
      auto getResponse = asyncGetNonRegulatedGrantSwitch(
      );
      return getResponse();
   }

   RTIambassadorClient::GetResponseFunc<bool> RTIambassadorClient::asyncGetNonRegulatedGrantSwitch(
)
   {
      auto * request = new rti1516_2025::fedpro::GetNonRegulatedGrantSwitchRequest();

      rti1516_2025::fedpro::CallRequest callRequest{};
      callRequest.set_allocated_getnonregulatedgrantswitchrequest(request);

      // Submit CallRequest asynchronously, then return a lambda function so the caller
      // may wait on a response, then decode the response.

      auto futureResponse = doAsyncHlaCall(callRequest);

      return {[](
            std::future<ByteSequence> & futureResponse,
            const ClientConverter & clientConverter) {
         auto encodedResponse = futureResponse.get();
         auto response = decodeHlaCallResponse(encodedResponse);
         return clientConverter.convertToHlaAndDelete(response.release_getnonregulatedgrantswitchresponse());
      }, std::move(futureResponse), _clientConverter};
   }

}
