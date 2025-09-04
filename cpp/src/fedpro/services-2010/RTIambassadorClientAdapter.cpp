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

// Silence clang-tidy issues reported for standard HLA exception.
// NOLINTBEGIN(hicpp-exception-baseclass, modernize-use-noexcept)

#include "RTIambassadorClientAdapter.h"

#include <fedpro/FedProExceptions.h>
#include "services-common/FomModuleLoader.h"
#include "services-common/HandleImplementation.h"
#include "services-common/RegionHandleImplementation.h"
#include "services-common/RTIcompat.h"
#include "services-common/RtiConfiguration.h"
#include "services-common/SettingsParser.h"
#include "utility/StringUtil.h"

#include <RTI/time/HLAfloat64Interval.h>
#include <RTI/time/HLAfloat64Time.h>
#include <RTI/time/HLAfloat64TimeFactory.h>
#include <RTI/time/HLAinteger64Interval.h>
#include <RTI/time/HLAinteger64Time.h>
#include <RTI/time/HLAinteger64TimeFactory.h>

namespace RTI_NAMESPACE
{
   using namespace FedPro;

   RTIambassadorClientAdapter::RTIambassadorClientAdapter()
         : _clientConverter{std::make_shared<FedPro::ClientConverter>()},
           _rtiAmbassadorClient{_clientConverter}
   {
   }

   RTIambassadorClientAdapter::~RTIambassadorClientAdapter() = default;

   void RTIambassadorClientAdapter::connect(
         FederateAmbassador & federateAmbassador,
         CallbackModel callbackModel,
         std::wstring const & localSettingsDesignator) RTI_THROW(ConnectionFailed, InvalidLocalSettingsDesignator, UnsupportedCallbackModel, AlreadyConnected, CallNotAllowedFromWithinCallback, RTIinternalError)
   {
      std::vector<std::wstring>
            inputValueList = RTIambassadorClientGenericBase::splitFederateConnectSettings(localSettingsDesignator);
      const std::string
            clientSettingsLine = RTIambassadorClientGenericBase::extractAndRemoveClientSettings(inputValueList, true);
      RtiConfiguration parsedRtiConfiguration = createRtiConfiguration(inputValueList);

      try {
         const Properties clientSettings{SettingsParser::parse(clientSettingsLine)};
         // API.version is a nonstandard setting supported by Pitch pRTI to improve handling of Evolved federates that use HLA 4's federate protocol.
         // Other RTIs will simply ignore it and interpret that the federate is HLA 4 compliant.
         std::string prtiApiVersion{clientSettings.getString(SETTING_NAME_HLA_API_VERSION, "IEEE 1516-2010")};
         if (!prtiApiVersion.empty()) {
            parsedRtiConfiguration.withAdditionalSettings(
                  L"API.version=" + toWString(prtiApiVersion) + L"\n" + parsedRtiConfiguration.additionalSettings());
         }

         _rtiAmbassadorClient.connect(clientSettings, federateAmbassador, callbackModel, &parsedRtiConfiguration);
      } catch (const std::exception & e) {
         throw RTIinternalError(toWString(e.what()));
      }
   }

   void RTIambassadorClientAdapter::disconnect() RTI_THROW(FederateIsExecutionMember, CallNotAllowedFromWithinCallback, RTIinternalError)
   {
      _rtiAmbassadorClient.disconnect();
   }

   void RTIambassadorClientAdapter::createFederationExecution(
         const std::wstring & federationName,
         const std::wstring & fomModule,
         const std::wstring & logicalTimeImplementationName) RTI_THROW(CouldNotCreateLogicalTimeFactory, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, FederationExecutionAlreadyExists, NotConnected, RTIinternalError)
   {
      FomModule fomModuleObject = _fomModuleLoader.getFomModule(fomModule);
      _rtiAmbassadorClient.createFederationExecutionWithTime(
            _clientConverter->convertStringFromHla(federationName),
            fomModuleObject,
            _clientConverter->convertStringFromHla(
                  logicalTimeImplementationName));
   }

   void RTIambassadorClientAdapter::createFederationExecution(
         const std::wstring & federationName,
         const std::vector<std::wstring> & fomModules,
         const std::wstring & logicalTimeImplementationName) RTI_THROW(CouldNotCreateLogicalTimeFactory, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, FederationExecutionAlreadyExists, NotConnected, RTIinternalError)
   {
      FomModuleSet fomModuleSet = _fomModuleLoader.getFomModuleSet(fomModules);
      _rtiAmbassadorClient.createFederationExecutionWithModulesAndTime(
            _clientConverter->convertStringFromHla(
                  federationName), fomModuleSet, _clientConverter->convertStringFromHla(logicalTimeImplementationName));
   }

   void RTIambassadorClientAdapter::createFederationExecutionWithMIM(
         const std::wstring & federationName,
         const std::vector<std::wstring> & fomModules,
         const std::wstring & mimModule,
         const std::wstring & logicalTimeImplementationName) RTI_THROW(CouldNotCreateLogicalTimeFactory, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, DesignatorIsHLAstandardMIM, ErrorReadingMIM, CouldNotOpenMIM, FederationExecutionAlreadyExists, NotConnected, RTIinternalError)
   {
      FomModuleSet fomModuleSet = _fomModuleLoader.getFomModuleSet(fomModules);
      if (logicalTimeImplementationName.empty()) {
         _rtiAmbassadorClient.createFederationExecutionWithModulesAndTime(
               _clientConverter->convertStringFromHla(federationName),
               fomModuleSet,
               _clientConverter->convertStringFromHla(logicalTimeImplementationName));
      } else {
         try {
            FomModule mimModuleObject = _fomModuleLoader.getFomModule(mimModule);
            _rtiAmbassadorClient.createFederationExecutionWithMIMAndTime(
                  _clientConverter->convertStringFromHla(federationName),
                  fomModuleSet,
                  mimModuleObject,
                  _clientConverter->convertStringFromHla(logicalTimeImplementationName));
         } catch (const CouldNotOpenFDD & e) {
            throw CouldNotOpenMIM(e.what());
         }
      }
   }

   void RTIambassadorClientAdapter::destroyFederationExecution(const std::wstring & federationName) RTI_THROW(FederatesCurrentlyJoined, FederationExecutionDoesNotExist, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.destroyFederationExecution(_clientConverter->convertStringFromHla(federationName));
   }

   void RTIambassadorClientAdapter::listFederationExecutions() RTI_THROW(NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.listFederationExecutions();
   }

   FederateHandle RTIambassadorClientAdapter::joinFederationExecution(
         std::wstring const & federateType,
         std::wstring const & federationName,
         std::vector<std::wstring> const & additionalFomModules) RTI_THROW(CouldNotCreateLogicalTimeFactory, FederationExecutionDoesNotExist, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, SaveInProgress, RestoreInProgress, FederateAlreadyExecutionMember, NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError)
   {
      FomModuleSet fomModuleSet = _fomModuleLoader.getFomModuleSet(additionalFomModules);
      JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecutionWithModules(
            _clientConverter->convertStringFromHla(federateType),
            _clientConverter->convertStringFromHla(federationName),
            fomModuleSet);
      _timeFactory = LogicalTimeFactoryFactory::makeLogicalTimeFactory(joinResult.getLogicalTimeImplementationName());
      _clientConverter->setTimeFactory(_timeFactory);

      _rtiAmbassadorClient.prefetch();

      return joinResult.getFederateHandle();
   }

   FederateHandle RTIambassadorClientAdapter::joinFederationExecution(
         std::wstring const & federateName,
         std::wstring const & federateType,
         std::wstring const & federationName,
         std::vector<std::wstring> const & additionalFomModules) RTI_THROW(CouldNotCreateLogicalTimeFactory, FederateNameAlreadyInUse, FederationExecutionDoesNotExist, InconsistentFDD, ErrorReadingFDD, CouldNotOpenFDD, SaveInProgress, RestoreInProgress, FederateAlreadyExecutionMember, NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError)
   {
      FomModuleSet fomModuleSet = _fomModuleLoader.getFomModuleSet(additionalFomModules);
      JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecutionWithNameAndModules(
            _clientConverter->convertStringFromHla(federateName),
            _clientConverter->convertStringFromHla(federateType),
            _clientConverter->convertStringFromHla(federationName),
            fomModuleSet);
      _timeFactory = LogicalTimeFactoryFactory::makeLogicalTimeFactory(joinResult.getLogicalTimeImplementationName());
      _clientConverter->setTimeFactory(_timeFactory);

      _rtiAmbassadorClient.prefetch();

      return joinResult.getFederateHandle();
   }

   void RTIambassadorClientAdapter::resignFederationExecution(
         ResignAction resignAction) RTI_THROW(InvalidResignAction, OwnershipAcquisitionPending, FederateOwnsAttributes, FederateNotExecutionMember, NotConnected, CallNotAllowedFromWithinCallback, RTIinternalError)
   {
      _rtiAmbassadorClient.resignFederationExecution(resignAction);
   }

   void RTIambassadorClientAdapter::registerFederationSynchronizationPoint(
         std::wstring const & synchronizationPointLabel,
         const VariableLengthData & userSuppliedTag) RTI_THROW(SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.registerFederationSynchronizationPoint(
            _clientConverter->convertStringFromHla(synchronizationPointLabel), userSuppliedTag);
   }

   void RTIambassadorClientAdapter::registerFederationSynchronizationPoint(
         std::wstring const & synchronizationPointLabel,
         const VariableLengthData & userSuppliedTag,
         FederateHandleSet const & synchronizationSet) RTI_THROW(InvalidFederateHandle, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.registerFederationSynchronizationPoint(
            _clientConverter->convertStringFromHla(synchronizationPointLabel), userSuppliedTag, synchronizationSet);
   }

   void RTIambassadorClientAdapter::synchronizationPointAchieved(
         std::wstring const & synchronizationPointLabel,
         bool successfully) RTI_THROW(SynchronizationPointLabelNotAnnounced, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.synchronizationPointAchieved(
            _clientConverter->convertStringFromHla(synchronizationPointLabel), successfully);
   }

   void RTIambassadorClientAdapter::requestFederationSave(
         std::wstring const & label) RTI_THROW(SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.requestFederationSave(_clientConverter->convertStringFromHla(label));
   }

   void RTIambassadorClientAdapter::requestFederationSave(
         std::wstring const & label,
         const LogicalTime & time) RTI_THROW(LogicalTimeAlreadyPassed, InvalidLogicalTime, FederateUnableToUseTime, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.requestFederationSave(_clientConverter->convertStringFromHla(label), time);
   }

   void RTIambassadorClientAdapter::federateSaveBegun() RTI_THROW(SaveNotInitiated, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.federateSaveBegun();
   }

   void RTIambassadorClientAdapter::federateSaveComplete() RTI_THROW(FederateHasNotBegunSave, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.federateSaveComplete();
   }

   void RTIambassadorClientAdapter::federateSaveNotComplete() RTI_THROW(FederateHasNotBegunSave, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.federateSaveNotComplete();
   }

   void RTIambassadorClientAdapter::abortFederationSave() RTI_THROW(SaveNotInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.abortFederationSave();
   }

   void RTIambassadorClientAdapter::queryFederationSaveStatus() RTI_THROW(RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.queryFederationSaveStatus();
   }

   void RTIambassadorClientAdapter::requestFederationRestore(
         std::wstring const & label) RTI_THROW(SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.requestFederationRestore(_clientConverter->convertStringFromHla(label));
   }

   void RTIambassadorClientAdapter::federateRestoreComplete() RTI_THROW(RestoreNotRequested, SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.federateRestoreComplete();
   }

   void RTIambassadorClientAdapter::federateRestoreNotComplete() RTI_THROW(RestoreNotRequested, SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.federateRestoreNotComplete();
   }

   void RTIambassadorClientAdapter::abortFederationRestore() RTI_THROW(RestoreNotInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.abortFederationRestore();
   }

   void RTIambassadorClientAdapter::queryFederationRestoreStatus() RTI_THROW(SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.queryFederationRestoreStatus();
   }

   void RTIambassadorClientAdapter::publishObjectClassAttributes(
         ObjectClassHandle objectClass,
         const AttributeHandleSet & attributes) RTI_THROW(AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.publishObjectClassAttributes(objectClass, attributes);
   }

   void RTIambassadorClientAdapter::unpublishObjectClass(ObjectClassHandle objectClass) RTI_THROW(OwnershipAcquisitionPending, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      _rtiAmbassadorClient.unpublishObjectClass(objectClass);
   }

   void RTIambassadorClientAdapter::unpublishObjectClassAttributes(
         ObjectClassHandle objectClass,
         const AttributeHandleSet & attributes) RTI_THROW(OwnershipAcquisitionPending, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.unpublishObjectClassAttributes(objectClass, attributes);
   }

   void RTIambassadorClientAdapter::publishInteractionClass(InteractionClassHandle interactionClass) RTI_THROW(InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      _rtiAmbassadorClient.publishInteractionClass(interactionClass);
   }

   void RTIambassadorClientAdapter::unpublishInteractionClass(InteractionClassHandle interactionClass) RTI_THROW(InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      _rtiAmbassadorClient.unpublishInteractionClass(interactionClass);
   }

   void RTIambassadorClientAdapter::subscribeObjectClassAttributes(
         ObjectClassHandle objectClass,
         const AttributeHandleSet & attributes,
         bool active,
         std::wstring const & updateRateDesignator) RTI_THROW(AttributeNotDefined, ObjectClassNotDefined, InvalidUpdateRateDesignator, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      if (active) {
         if (updateRateDesignator.empty()) {
            return _rtiAmbassadorClient.subscribeObjectClassAttributes(
                  objectClass, attributes);
         } else {
            return _rtiAmbassadorClient.subscribeObjectClassAttributes(
                  objectClass, attributes, _clientConverter->convertStringFromHla(updateRateDesignator));
         }
      } else {
         if (updateRateDesignator.empty()) {
            return _rtiAmbassadorClient.subscribeObjectClassAttributesPassively(
                  objectClass, attributes);
         } else {
            return _rtiAmbassadorClient.subscribeObjectClassAttributesPassively(
                  objectClass, attributes, _clientConverter->convertStringFromHla(updateRateDesignator));
         }
      }
   }

   void RTIambassadorClientAdapter::unsubscribeObjectClass(ObjectClassHandle objectClass) RTI_THROW(ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      _rtiAmbassadorClient.unsubscribeObjectClass(objectClass);
   }

   void RTIambassadorClientAdapter::unsubscribeObjectClassAttributes(
         ObjectClassHandle objectClass,
         const AttributeHandleSet & attributes) RTI_THROW(AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.unsubscribeObjectClassAttributes(objectClass, attributes);
   }

   void RTIambassadorClientAdapter::subscribeInteractionClass(
         InteractionClassHandle interactionClass,
         bool active) RTI_THROW(FederateServiceInvocationsAreBeingReportedViaMOM, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      if (active) {
         return _rtiAmbassadorClient.subscribeInteractionClass(interactionClass);
      } else {
         return _rtiAmbassadorClient.subscribeInteractionClassPassively(interactionClass);
      }
   }

   void RTIambassadorClientAdapter::unsubscribeInteractionClass(InteractionClassHandle interactionClass) RTI_THROW(InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      _rtiAmbassadorClient.unsubscribeInteractionClass(interactionClass);
   }

   void RTIambassadorClientAdapter::reserveObjectInstanceName(
         std::wstring const & objectInstanceName) RTI_THROW(IllegalName, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.reserveObjectInstanceName(
            _clientConverter->convertStringFromHla(objectInstanceName));
   }

   void RTIambassadorClientAdapter::releaseObjectInstanceName(
         std::wstring const & objectInstanceName) RTI_THROW(ObjectInstanceNameNotReserved, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.releaseObjectInstanceName(
            _clientConverter->convertStringFromHla(objectInstanceName));
   }

   void RTIambassadorClientAdapter::reserveMultipleObjectInstanceName(std::set<std::wstring> const & objectInstanceNames) RTI_THROW(IllegalName, NameSetWasEmpty, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.reserveMultipleObjectInstanceNames(objectInstanceNames);
   }

   void RTIambassadorClientAdapter::releaseMultipleObjectInstanceName(std::set<std::wstring> const & objectInstanceNames) RTI_THROW(ObjectInstanceNameNotReserved, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.releaseMultipleObjectInstanceNames(objectInstanceNames);
   }

   ObjectInstanceHandle RTIambassadorClientAdapter::registerObjectInstance(ObjectClassHandle objectClass) RTI_THROW(ObjectClassNotPublished, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.registerObjectInstance(objectClass);
   }

   ObjectInstanceHandle RTIambassadorClientAdapter::registerObjectInstance(
         ObjectClassHandle objectClass,
         std::wstring const & objectInstanceName) RTI_THROW(ObjectInstanceNameInUse, ObjectInstanceNameNotReserved, ObjectClassNotPublished, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithName(
            objectClass, _clientConverter->convertStringFromHla(
                  objectInstanceName));
   }

   void RTIambassadorClientAdapter::updateAttributeValues(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleValueMap & attributeValues,
         const VariableLengthData & userSuppliedTag) RTI_THROW(AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.updateAttributeValues(objectInstance, attributeValues, userSuppliedTag);
   }

   MessageRetractionHandle RTIambassadorClientAdapter::updateAttributeValues(
         ObjectInstanceHandle objectInstance,
         AttributeHandleValueMap const & attributeValues,
         const VariableLengthData & userSuppliedTag,
         const LogicalTime & time) RTI_THROW(InvalidLogicalTime, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.updateAttributeValues(objectInstance, attributeValues, userSuppliedTag, time);
   }

   void RTIambassadorClientAdapter::sendInteraction(
         InteractionClassHandle interactionClass,
         const ParameterHandleValueMap & parameterValues,
         const VariableLengthData & userSuppliedTag) RTI_THROW(InteractionClassNotPublished, InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.sendInteraction(interactionClass, parameterValues, userSuppliedTag);
   }

   MessageRetractionHandle RTIambassadorClientAdapter::sendInteraction(
         InteractionClassHandle interactionClass,
         const ParameterHandleValueMap & parameterValues,
         const VariableLengthData & userSuppliedTag,
         const LogicalTime & time) RTI_THROW(InvalidLogicalTime, InteractionClassNotPublished, InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      return _rtiAmbassadorClient.sendInteraction(interactionClass, parameterValues, userSuppliedTag, time);
   }

   void RTIambassadorClientAdapter::deleteObjectInstance(
         ObjectInstanceHandle objectInstance,
         const VariableLengthData & userSuppliedTag) RTI_THROW(DeletePrivilegeNotHeld, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.deleteObjectInstance(objectInstance, userSuppliedTag);
   }

   MessageRetractionHandle RTIambassadorClientAdapter::deleteObjectInstance(
         ObjectInstanceHandle objectInstance,
         const VariableLengthData & userSuppliedTag,
         LogicalTime const & time) RTI_THROW(InvalidLogicalTime, DeletePrivilegeNotHeld, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.deleteObjectInstance(objectInstance, userSuppliedTag, time);
   }

   void RTIambassadorClientAdapter::localDeleteObjectInstance(ObjectInstanceHandle objectInstance) RTI_THROW(OwnershipAcquisitionPending, FederateOwnsAttributes, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.localDeleteObjectInstance(objectInstance);
   }

   void RTIambassadorClientAdapter::requestAttributeValueUpdate(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleSet & attributes,
         const VariableLengthData & userSuppliedTag) RTI_THROW(AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.requestAttributeValueUpdate(objectInstance, attributes, userSuppliedTag);
   }

   void RTIambassadorClientAdapter::requestAttributeValueUpdate(
         ObjectClassHandle objectClass,
         const AttributeHandleSet & attributes,
         const VariableLengthData & userSuppliedTag) RTI_THROW(AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.requestAttributeValueUpdate(objectClass, attributes, userSuppliedTag);
   }

   void RTIambassadorClientAdapter::requestAttributeTransportationTypeChange(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleSet & attributes,
         TransportationType transportationType) RTI_THROW(AttributeAlreadyBeingChanged, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, InvalidTransportationType, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      try {
         const std::string & transportationName = getTransportationTypeName(transportationType);
         TransportationTypeHandle
               transportationHandle = _rtiAmbassadorClient.getTransportationTypeHandle(transportationName);
         return _rtiAmbassadorClient.requestAttributeTransportationTypeChange(
               objectInstance, attributes, transportationHandle);
      } catch (const std::out_of_range &) {
         throw InvalidTransportationType(L"Invalid transportationType parameter value");
      }
   }

   void RTIambassadorClientAdapter::queryAttributeTransportationType(
         ObjectInstanceHandle objectInstance,
         AttributeHandle attribute) RTI_THROW(AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.queryAttributeTransportationType(objectInstance, attribute);
   }

   void RTIambassadorClientAdapter::requestInteractionTransportationTypeChange(
         InteractionClassHandle interactionClass,
         TransportationType transportationType) RTI_THROW(InteractionClassAlreadyBeingChanged, InteractionClassNotPublished, InteractionClassNotDefined, InvalidTransportationType, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      try {
         const std::string & transportationName = getTransportationTypeName(transportationType);
         TransportationTypeHandle
               transportationHandle = _rtiAmbassadorClient.getTransportationTypeHandle(transportationName);
         return _rtiAmbassadorClient.requestInteractionTransportationTypeChange(interactionClass, transportationHandle);
      } catch (const std::out_of_range &) {
         throw InvalidTransportationType(L"Invalid transportationType parameter value");
      }
   }

   void RTIambassadorClientAdapter::queryInteractionTransportationType(
         FederateHandle federate,
         InteractionClassHandle interactionClass) RTI_THROW(InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.queryInteractionTransportationType(federate, interactionClass);
   }

   void RTIambassadorClientAdapter::unconditionalAttributeOwnershipDivestiture(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleSet & attributes) RTI_THROW(AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.unconditionalAttributeOwnershipDivestiture(
            objectInstance, attributes, RTI_NAMESPACE::VariableLengthData{});
   }

   void RTIambassadorClientAdapter::negotiatedAttributeOwnershipDivestiture(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleSet & attributes,
         const VariableLengthData & userSuppliedTag) RTI_THROW(AttributeAlreadyBeingDivested, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.negotiatedAttributeOwnershipDivestiture(objectInstance, attributes, userSuppliedTag);
   }

   void RTIambassadorClientAdapter::confirmDivestiture(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleSet & confirmedAttributes,
         const VariableLengthData & userSuppliedTag) RTI_THROW(NoAcquisitionPending, AttributeDivestitureWasNotRequested, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.confirmDivestiture(objectInstance, confirmedAttributes, userSuppliedTag);
   }

   void RTIambassadorClientAdapter::attributeOwnershipAcquisition(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleSet & desiredAttributes,
         const VariableLengthData & userSuppliedTag) RTI_THROW(AttributeNotPublished, ObjectClassNotPublished, FederateOwnsAttributes, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.attributeOwnershipAcquisition(objectInstance, desiredAttributes, userSuppliedTag);
   }

   void RTIambassadorClientAdapter::attributeOwnershipAcquisitionIfAvailable(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleSet & desiredAttributes) RTI_THROW(AttributeAlreadyBeingAcquired, AttributeNotPublished, ObjectClassNotPublished, FederateOwnsAttributes, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.attributeOwnershipAcquisitionIfAvailable(
            objectInstance, desiredAttributes, RTI_NAMESPACE::VariableLengthData{});
   }

   void RTIambassadorClientAdapter::attributeOwnershipReleaseDenied(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleSet & attributes) RTI_THROW(AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.attributeOwnershipReleaseDenied(
            objectInstance, attributes, RTI_NAMESPACE::VariableLengthData{});
   }

   void RTIambassadorClientAdapter::attributeOwnershipDivestitureIfWanted(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleSet & attributes,
         AttributeHandleSet & divestedAttributes) RTI_THROW(AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      divestedAttributes = _rtiAmbassadorClient.attributeOwnershipDivestitureIfWanted(
            objectInstance, attributes, RTI_NAMESPACE::VariableLengthData{});
   }

   void RTIambassadorClientAdapter::cancelNegotiatedAttributeOwnershipDivestiture(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleSet & attributes) RTI_THROW(AttributeDivestitureWasNotRequested, AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.cancelNegotiatedAttributeOwnershipDivestiture(objectInstance, attributes);
   }

   void RTIambassadorClientAdapter::cancelAttributeOwnershipAcquisition(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleSet & attributes) RTI_THROW(AttributeAcquisitionWasNotRequested, AttributeAlreadyOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.cancelAttributeOwnershipAcquisition(objectInstance, attributes);
   }

   void RTIambassadorClientAdapter::queryAttributeOwnership(
         ObjectInstanceHandle objectInstance,
         AttributeHandle theAttribute) RTI_THROW(AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      AttributeHandleSet attributes{{theAttribute}};
      _rtiAmbassadorClient.queryAttributeOwnership(objectInstance, attributes);
   }

   bool RTIambassadorClientAdapter::isAttributeOwnedByFederate(
         ObjectInstanceHandle objectInstance,
         AttributeHandle attribute) RTI_THROW(AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.isAttributeOwnedByFederate(objectInstance, attribute);
   }

   void RTIambassadorClientAdapter::enableTimeRegulation(const LogicalTimeInterval & lookahead) RTI_THROW(InvalidLookahead, InTimeAdvancingState, RequestForTimeRegulationPending, TimeRegulationAlreadyEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      throwIfInvalidLookahead(lookahead);
      _rtiAmbassadorClient.enableTimeRegulation(lookahead);
   }

   void RTIambassadorClientAdapter::disableTimeRegulation() RTI_THROW(TimeRegulationIsNotEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.disableTimeRegulation();
   }

   void RTIambassadorClientAdapter::enableTimeConstrained() RTI_THROW(InTimeAdvancingState, RequestForTimeConstrainedPending, TimeConstrainedAlreadyEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.enableTimeConstrained();
   }

   void RTIambassadorClientAdapter::disableTimeConstrained() RTI_THROW(TimeConstrainedIsNotEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.disableTimeConstrained();
   }

   void RTIambassadorClientAdapter::timeAdvanceRequest(const LogicalTime & time) RTI_THROW(LogicalTimeAlreadyPassed, InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      throwIfInvalidTime(time);
      _rtiAmbassadorClient.timeAdvanceRequest(time);
   }

   void RTIambassadorClientAdapter::timeAdvanceRequestAvailable(const LogicalTime & time) RTI_THROW(LogicalTimeAlreadyPassed, InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      throwIfInvalidTime(time);
      _rtiAmbassadorClient.timeAdvanceRequestAvailable(time);
   }

   void RTIambassadorClientAdapter::nextMessageRequest(const LogicalTime & time) RTI_THROW(LogicalTimeAlreadyPassed, InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      throwIfInvalidTime(time);
      _rtiAmbassadorClient.nextMessageRequest(time);
   }

   void RTIambassadorClientAdapter::nextMessageRequestAvailable(const LogicalTime & time) RTI_THROW(LogicalTimeAlreadyPassed, InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      throwIfInvalidTime(time);
      _rtiAmbassadorClient.nextMessageRequestAvailable(time);
   }

   void RTIambassadorClientAdapter::flushQueueRequest(const LogicalTime & time) RTI_THROW(LogicalTimeAlreadyPassed, InvalidLogicalTime, InTimeAdvancingState, RequestForTimeRegulationPending, RequestForTimeConstrainedPending, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      throwIfInvalidTime(time);
      _rtiAmbassadorClient.flushQueueRequest(time);
   }

   void RTIambassadorClientAdapter::enableAsynchronousDelivery() RTI_THROW(AsynchronousDeliveryAlreadyEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.enableAsynchronousDelivery();
   }

   void RTIambassadorClientAdapter::disableAsynchronousDelivery() RTI_THROW(AsynchronousDeliveryAlreadyDisabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.disableAsynchronousDelivery();
   }

   bool RTIambassadorClientAdapter::queryGALT(LogicalTime & time) RTI_THROW(SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      auto resultPair = _rtiAmbassadorClient.queryGALT();
      if (resultPair.second.get()) {
         time = *resultPair.second;
      }
      return resultPair.first;
   }

   void RTIambassadorClientAdapter::queryLogicalTime(LogicalTime & time) RTI_THROW(SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      std::unique_ptr<RTI_NAMESPACE::LogicalTime> timePointer = _rtiAmbassadorClient.queryLogicalTime();
      if (timePointer) {
         time = *timePointer;
      } else {
         throw FederateNotExecutionMember(L"No query result available");
      }
   }

   bool RTIambassadorClientAdapter::queryLITS(LogicalTime & time) RTI_THROW(SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      auto resultPair = _rtiAmbassadorClient.queryLITS();
      if (resultPair.second.get()) {
         time = *resultPair.second;
      }
      return resultPair.first;
   }

   void RTIambassadorClientAdapter::modifyLookahead(const LogicalTimeInterval & lookahead) RTI_THROW(InvalidLookahead, InTimeAdvancingState, TimeRegulationIsNotEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.modifyLookahead(lookahead);
   }

   void RTIambassadorClientAdapter::queryLookahead(LogicalTimeInterval & interval) RTI_THROW(TimeRegulationIsNotEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      std::unique_ptr<RTI_NAMESPACE::LogicalTimeInterval> intervalPointer = _rtiAmbassadorClient.queryLookahead();
      if (intervalPointer) {
         interval = *intervalPointer;
      } else {
         throw FederateNotExecutionMember(L"No query result available");
      }
   }

   void RTIambassadorClientAdapter::retract(MessageRetractionHandle retraction) RTI_THROW(MessageCanNoLongerBeRetracted, InvalidMessageRetractionHandle, TimeRegulationIsNotEnabled, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.retract(retraction);
   }

   void RTIambassadorClientAdapter::changeAttributeOrderType(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleSet & attributes,
         OrderType orderType) RTI_THROW(AttributeNotOwned, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.changeAttributeOrderType(objectInstance, attributes, orderType);
   }

   void RTIambassadorClientAdapter::changeInteractionOrderType(
         InteractionClassHandle interactionClass,
         OrderType orderType) RTI_THROW(InteractionClassNotPublished, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.changeInteractionOrderType(interactionClass, orderType);
   }

   RegionHandle RTIambassadorClientAdapter::createRegion(DimensionHandleSet const & dimensions) RTI_THROW(InvalidDimensionHandle, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.createRegion(dimensions);
   }

   void RTIambassadorClientAdapter::commitRegionModifications(const RegionHandleSet & regions) RTI_THROW(RegionNotCreatedByThisFederate, InvalidRegion, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.commitRegionModifications(regions);
   }

   void RTIambassadorClientAdapter::deleteRegion(RegionHandle const & region) RTI_THROW(RegionInUseForUpdateOrSubscription, RegionNotCreatedByThisFederate, InvalidRegion, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!region.isValid()) {
         throw InvalidRegion(L"Invalid RegionHandle");
      }
      const HandleImplementation * handleImplementation = getImplementation(region);
      auto * conveyedRegion = dynamic_cast<const RegionHandleImplementation *>(handleImplementation);
      if (conveyedRegion != nullptr) {
         throw RegionNotCreatedByThisFederate(L"Conveyed region");
      } else {
         _rtiAmbassadorClient.deleteRegion(region);
      }
   }

   ObjectInstanceHandle RTIambassadorClientAdapter::registerObjectInstanceWithRegions(
         ObjectClassHandle objectClass,
         const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions) RTI_THROW(InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotPublished, ObjectClassNotPublished, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithRegions(objectClass, attributesAndRegions);
   }

   ObjectInstanceHandle RTIambassadorClientAdapter::registerObjectInstanceWithRegions(
         ObjectClassHandle objectClass,
         const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
         std::wstring const & objectInstanceName) RTI_THROW(ObjectInstanceNameInUse, ObjectInstanceNameNotReserved, InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotPublished, ObjectClassNotPublished, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithRegions(
            objectClass, attributesAndRegions, _clientConverter->convertStringFromHla(objectInstanceName));
   }

   void RTIambassadorClientAdapter::associateRegionsForUpdates(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions) RTI_THROW(InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectInstance.isValid()) {
         throw ObjectInstanceNotKnown(L"Invalid ObjectInstanceHandle");
      }
      return _rtiAmbassadorClient.associateRegionsForUpdates(objectInstance, attributesAndRegions);
   }

   void RTIambassadorClientAdapter::unassociateRegionsForUpdates(
         ObjectInstanceHandle objectInstance,
         const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions) RTI_THROW(RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectInstanceNotKnown, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectInstance.isValid()) {
         throw ObjectInstanceNotKnown(L"Invalid ObjectInstanceHandle");
      }
      return _rtiAmbassadorClient.unassociateRegionsForUpdates(objectInstance, attributesAndRegions);
   }

   void RTIambassadorClientAdapter::subscribeObjectClassAttributesWithRegions(
         ObjectClassHandle objectClass,
         const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
         bool active,
         std::wstring const & updateRateDesignator) RTI_THROW(InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectClassNotDefined, InvalidUpdateRateDesignator, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      if (updateRateDesignator.empty()) {
         _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegions(
               objectClass, attributesAndRegions, active);
      } else {
         _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegions(
               objectClass, attributesAndRegions, active, _clientConverter->convertStringFromHla(updateRateDesignator));
      }
   }

   void RTIambassadorClientAdapter::unsubscribeObjectClassAttributesWithRegions(
         ObjectClassHandle objectClass,
         const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions) RTI_THROW(RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      _rtiAmbassadorClient.unsubscribeObjectClassAttributesWithRegions(objectClass, attributesAndRegions);
   }

   void RTIambassadorClientAdapter::subscribeInteractionClassWithRegions(
         InteractionClassHandle interactionClass,
         const RegionHandleSet & regions,
         bool active) RTI_THROW(FederateServiceInvocationsAreBeingReportedViaMOM, InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      _rtiAmbassadorClient.subscribeInteractionClassWithRegions(interactionClass, active, regions);
   }

   void RTIambassadorClientAdapter::unsubscribeInteractionClassWithRegions(
         InteractionClassHandle interactionClass,
         const RegionHandleSet & regions) RTI_THROW(RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      _rtiAmbassadorClient.unsubscribeInteractionClassWithRegions(interactionClass, regions);
   }

   void RTIambassadorClientAdapter::sendInteractionWithRegions(
         InteractionClassHandle interactionClass,
         const ParameterHandleValueMap & parameterValues,
         const RegionHandleSet & regions,
         const VariableLengthData & userSuppliedTag) RTI_THROW(InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotPublished, InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      _rtiAmbassadorClient.sendInteractionWithRegions(
            interactionClass, parameterValues, regions, userSuppliedTag);
   }

   MessageRetractionHandle RTIambassadorClientAdapter::sendInteractionWithRegions(
         InteractionClassHandle interactionClass,
         const ParameterHandleValueMap & parameterValues,
         const RegionHandleSet & regions,
         const VariableLengthData & userSuppliedTag,
         const LogicalTime & time) RTI_THROW(InvalidLogicalTime, InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, InteractionClassNotPublished, InteractionParameterNotDefined, InteractionClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      return _rtiAmbassadorClient.sendInteractionWithRegions(
            interactionClass, parameterValues, regions, userSuppliedTag, time);
   }

   void RTIambassadorClientAdapter::requestAttributeValueUpdateWithRegions(
         ObjectClassHandle objectClass,
         const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
         const VariableLengthData & userSuppliedTag) RTI_THROW(InvalidRegionContext, RegionNotCreatedByThisFederate, InvalidRegion, AttributeNotDefined, ObjectClassNotDefined, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      _rtiAmbassadorClient.requestAttributeValueUpdateWithRegions(
            objectClass, attributesAndRegions, userSuppliedTag);
   }

   ResignAction RTIambassadorClientAdapter::getAutomaticResignDirective() RTI_THROW(FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.getAutomaticResignDirective();
   }

   void RTIambassadorClientAdapter::setAutomaticResignDirective(ResignAction resignAction) RTI_THROW(InvalidResignAction, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      _rtiAmbassadorClient.setAutomaticResignDirective(resignAction);
   }

   FederateHandle RTIambassadorClientAdapter::getFederateHandle(std::wstring const & federateName) RTI_THROW(NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.getFederateHandle(_clientConverter->convertStringFromHla(federateName));
   }

   std::wstring RTIambassadorClientAdapter::getFederateName(FederateHandle federate) RTI_THROW(InvalidFederateHandle, FederateHandleNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!federate.isValid()) {
         throw InvalidFederateHandle(L"Invalid FederateHandle");
      }
      return _rtiAmbassadorClient.getFederateName(federate);
   }

   ObjectClassHandle RTIambassadorClientAdapter::getObjectClassHandle(std::wstring const & objectClassName) RTI_THROW(NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.getObjectClassHandle(_clientConverter->convertStringFromHla(objectClassName));
   }

   std::wstring RTIambassadorClientAdapter::getObjectClassName(ObjectClassHandle objectClass) RTI_THROW(InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.getObjectClassName(objectClass);
   }

   ObjectClassHandle RTIambassadorClientAdapter::getKnownObjectClassHandle(ObjectInstanceHandle objectInstance) RTI_THROW(ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectInstance.isValid()) {
         throw ObjectInstanceNotKnown(L"Invalid ObjectInstanceHandle");
      }
      return _rtiAmbassadorClient.getKnownObjectClassHandle(objectInstance);
   }

   ObjectInstanceHandle RTIambassadorClientAdapter::getObjectInstanceHandle(std::wstring const & objectInstanceName) RTI_THROW(ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.getObjectInstanceHandle(_clientConverter->convertStringFromHla(objectInstanceName));
   }

   std::wstring RTIambassadorClientAdapter::getObjectInstanceName(ObjectInstanceHandle objectInstance) RTI_THROW(ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectInstance.isValid()) {
         throw ObjectInstanceNotKnown(L"Invalid ObjectInstanceHandle");
      }
      return _rtiAmbassadorClient.getObjectInstanceName(objectInstance);
   }

   AttributeHandle RTIambassadorClientAdapter::getAttributeHandle(
         ObjectClassHandle objectClass,
         std::wstring const & attributeName) RTI_THROW(NameNotFound, InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.getAttributeHandle(
            objectClass, _clientConverter->convertStringFromHla(attributeName));
   }

   std::wstring RTIambassadorClientAdapter::getAttributeName(
         ObjectClassHandle objectClass,
         AttributeHandle attribute) RTI_THROW(AttributeNotDefined, InvalidAttributeHandle, InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw InvalidObjectClassHandle(L"Invalid ObjectClassHandle");
      }
      if (!attribute.isValid()) {
         throw InvalidAttributeHandle(L"Invalid AttributeHandle");
      }

      return _rtiAmbassadorClient.getAttributeName(objectClass, attribute);
   }

   double RTIambassadorClientAdapter::getUpdateRateValue(std::wstring const & updateRateDesignator) RTI_THROW(InvalidUpdateRateDesignator, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.getUpdateRateValue(_clientConverter->convertStringFromHla(updateRateDesignator));
   }

   double RTIambassadorClientAdapter::getUpdateRateValueForAttribute(
         ObjectInstanceHandle objectInstance,
         AttributeHandle attribute) RTI_THROW(ObjectInstanceNotKnown, AttributeNotDefined, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.getUpdateRateValueForAttribute(objectInstance, attribute);
   }

   InteractionClassHandle RTIambassadorClientAdapter::getInteractionClassHandle(std::wstring const & interactionClassName) RTI_THROW(NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.getInteractionClassHandle(_clientConverter->convertStringFromHla(interactionClassName));
   }

   std::wstring RTIambassadorClientAdapter::getInteractionClassName(InteractionClassHandle interactionClass) RTI_THROW(InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!interactionClass.isValid()) {
         throw InvalidInteractionClassHandle(L"Invalid InteractionClassHandle");
      }
      return _rtiAmbassadorClient.getInteractionClassName(interactionClass);
   }

   ParameterHandle RTIambassadorClientAdapter::getParameterHandle(
         InteractionClassHandle interactionClass,
         std::wstring const & parameterName) RTI_THROW(NameNotFound, InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!interactionClass.isValid()) {
         throw InvalidInteractionClassHandle(L"Invalid InteractionClassHandle");
      }
      return _rtiAmbassadorClient.getParameterHandle(
            interactionClass, _clientConverter->convertStringFromHla(parameterName));
   }

   std::wstring RTIambassadorClientAdapter::getParameterName(
         InteractionClassHandle interactionClass,
         ParameterHandle parameter) RTI_THROW(InteractionParameterNotDefined, InvalidParameterHandle, InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!interactionClass.isValid()) {
         throw InvalidInteractionClassHandle(L"Invalid InteractionClassHandle");
      }
      if (!parameter.isValid()) {
         throw InvalidParameterHandle(L"Invalid ParameterHandle");
      }
      return _rtiAmbassadorClient.getParameterName(interactionClass, parameter);
   }

   OrderType RTIambassadorClientAdapter::getOrderType(std::wstring const & orderTypeName) RTI_THROW(InvalidOrderName, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.getOrderType(_clientConverter->convertStringFromHla(orderTypeName));
   }

   std::wstring RTIambassadorClientAdapter::getOrderName(OrderType orderType) RTI_THROW(InvalidOrderType, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.getOrderName(orderType);
   }

   TransportationType RTIambassadorClientAdapter::getTransportationType(std::wstring const & transportationTypeName) RTI_THROW(InvalidTransportationName, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (transportationTypeName == L"HLAreliable") {
         return RELIABLE;
      } else if (transportationTypeName == L"HLAbestEffort") {
         return BEST_EFFORT;
      } else {
         throw InvalidTransportationName(L"Name does not match pre-defined transportation types");
      }
   }

   std::wstring RTIambassadorClientAdapter::getTransportationName(TransportationType transportationType) RTI_THROW(InvalidTransportationType, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      switch (transportationType) {
         case RELIABLE:
            return L"HLAreliable";
         case BEST_EFFORT:
            return L"HLAbestEffort";
         default:
            throw InvalidTransportationName(L"Name does not match pre-defined transportation types");
      }
   }

   DimensionHandleSet RTIambassadorClientAdapter::getAvailableDimensionsForClassAttribute(
         ObjectClassHandle objectClass,
         AttributeHandle attribute) RTI_THROW(AttributeNotDefined, InvalidAttributeHandle, InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!objectClass.isValid()) {
         throw InvalidObjectClassHandle(L"Invalid ObjectClassHandle");
      }
      if (!attribute.isValid()) {
         throw InvalidAttributeHandle(L"Invalid AttributeHandle");
      }
      return _rtiAmbassadorClient.getAvailableDimensionsForObjectClass(objectClass);
   }

   DimensionHandleSet RTIambassadorClientAdapter::getAvailableDimensionsForInteractionClass(InteractionClassHandle interactionClass) RTI_THROW(InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!interactionClass.isValid()) {
         throw InvalidInteractionClassHandle(L"Invalid InteractionClassHandle");
      }
      return _rtiAmbassadorClient.getAvailableDimensionsForInteractionClass(interactionClass);
   }

   DimensionHandle RTIambassadorClientAdapter::getDimensionHandle(std::wstring const & dimensionName) RTI_THROW(NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.getDimensionHandle(_clientConverter->convertStringFromHla(dimensionName));
   }

   std::wstring RTIambassadorClientAdapter::getDimensionName(DimensionHandle dimension) RTI_THROW(InvalidDimensionHandle, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!dimension.isValid()) {
         throw InvalidDimensionHandle(L"Invalid DimensionHandle");
      }
      return _rtiAmbassadorClient.getDimensionName(dimension);
   }

   unsigned long RTIambassadorClientAdapter::getDimensionUpperBound(DimensionHandle dimension) RTI_THROW(InvalidDimensionHandle, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!dimension.isValid()) {
         throw InvalidDimensionHandle(L"Invalid DimensionHandle");
      }
      return _rtiAmbassadorClient.getDimensionUpperBound(dimension);
   }

   DimensionHandleSet RTIambassadorClientAdapter::getDimensionHandleSet(RegionHandle region) RTI_THROW(InvalidRegion, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!region.isValid()) {
         throw InvalidRegion(L"Invalid RegionHandle");
      }
      const HandleImplementation * handleImplementation = getImplementation(region);
      auto * conveyedRegion = dynamic_cast<const RegionHandleImplementation *>(handleImplementation);
      if (conveyedRegion != nullptr) {
         return conveyedRegion->getDimensionHandleSet();
      } else {
         return _rtiAmbassadorClient.getDimensionHandleSet(region);
      }
   }

   RangeBounds RTIambassadorClientAdapter::getRangeBounds(
         RegionHandle region,
         DimensionHandle dimension) RTI_THROW(RegionDoesNotContainSpecifiedDimension, InvalidRegion, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!region.isValid()) {
         throw InvalidRegion(L"Invalid RegionHandle");
      }
      const HandleImplementation * handleImplementation = getImplementation(region);
      auto * conveyedRegion = dynamic_cast<const RegionHandleImplementation *>(handleImplementation);
      if (conveyedRegion != nullptr) {
         return conveyedRegion->getRangeBounds(dimension);
      } else {
         return _rtiAmbassadorClient.getRangeBounds(region, dimension);
      }
   }

   void RTIambassadorClientAdapter::setRangeBounds(
         RegionHandle region,
         DimensionHandle dimension,
         const RangeBounds & rangeBounds) RTI_THROW(InvalidRangeBound, RegionDoesNotContainSpecifiedDimension, RegionNotCreatedByThisFederate, InvalidRegion, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!region.isValid()) {
         throw InvalidRegion(L"Invalid RegionHandle");
      }
      const HandleImplementation * handleImplementation = getImplementation(region);
      auto * conveyedRegion = dynamic_cast<const RegionHandleImplementation *>(handleImplementation);
      if (conveyedRegion != nullptr) {
         throw RegionNotCreatedByThisFederate(L"Conveyed region");
      } else {
         _rtiAmbassadorClient.setRangeBounds(region, dimension, rangeBounds);
      }

   }

   unsigned long RTIambassadorClientAdapter::normalizeFederateHandle(FederateHandle federate) RTI_THROW(InvalidFederateHandle, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!federate.isValid()) {
         throw InvalidFederateHandle(L"Invalid FederateHandle");
      }
      return _rtiAmbassadorClient.normalizeFederateHandle(federate);
   }

   unsigned long RTIambassadorClientAdapter::normalizeServiceGroup(ServiceGroup serviceGroup) RTI_THROW(InvalidServiceGroup, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return _rtiAmbassadorClient.normalizeServiceGroup(serviceGroup);
   }

   void RTIambassadorClientAdapter::enableObjectClassRelevanceAdvisorySwitch() RTI_THROW(ObjectClassRelevanceAdvisorySwitchIsOn, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (_rtiAmbassadorClient.getObjectClassRelevanceAdvisorySwitch()) {
         throw ObjectClassRelevanceAdvisorySwitchIsOn(L"Advisory switch is already enabled");
      }
      _rtiAmbassadorClient.setObjectClassRelevanceAdvisorySwitch(true);
   }

   void RTIambassadorClientAdapter::disableObjectClassRelevanceAdvisorySwitch() RTI_THROW(ObjectClassRelevanceAdvisorySwitchIsOff, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!_rtiAmbassadorClient.getObjectClassRelevanceAdvisorySwitch()) {
         throw ObjectClassRelevanceAdvisorySwitchIsOff(L"Advisory switch is already disabled");
      }
      _rtiAmbassadorClient.setObjectClassRelevanceAdvisorySwitch(false);
   }

   void RTIambassadorClientAdapter::enableAttributeRelevanceAdvisorySwitch() RTI_THROW(AttributeRelevanceAdvisorySwitchIsOn, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (_rtiAmbassadorClient.getAttributeRelevanceAdvisorySwitch()) {
         throw AttributeRelevanceAdvisorySwitchIsOn(L"Advisory switch is already enabled");
      }
      _rtiAmbassadorClient.setAttributeRelevanceAdvisorySwitch(true);
   }

   void RTIambassadorClientAdapter::disableAttributeRelevanceAdvisorySwitch() RTI_THROW(AttributeRelevanceAdvisorySwitchIsOff, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!_rtiAmbassadorClient.getAttributeRelevanceAdvisorySwitch()) {
         throw AttributeRelevanceAdvisorySwitchIsOff(L"Advisory switch is already disabled");
      }
      _rtiAmbassadorClient.setAttributeRelevanceAdvisorySwitch(false);
   }

   void RTIambassadorClientAdapter::enableAttributeScopeAdvisorySwitch() RTI_THROW(AttributeScopeAdvisorySwitchIsOn, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (_rtiAmbassadorClient.getAttributeScopeAdvisorySwitch()) {
         throw AttributeScopeAdvisorySwitchIsOn(L"Advisory switch is already enabled");
      }
      _rtiAmbassadorClient.setAttributeScopeAdvisorySwitch(true);
   }

   void RTIambassadorClientAdapter::disableAttributeScopeAdvisorySwitch() RTI_THROW(AttributeScopeAdvisorySwitchIsOff, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!_rtiAmbassadorClient.getAttributeScopeAdvisorySwitch()) {
         throw AttributeScopeAdvisorySwitchIsOff(L"Advisory switch is already disabled");
      }
      _rtiAmbassadorClient.setAttributeScopeAdvisorySwitch(false);
   }

   void RTIambassadorClientAdapter::enableInteractionRelevanceAdvisorySwitch() RTI_THROW(InteractionRelevanceAdvisorySwitchIsOn, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (_rtiAmbassadorClient.getInteractionRelevanceAdvisorySwitch()) {
         throw InteractionRelevanceAdvisorySwitchIsOn(L"Advisory switch is already enabled");
      }
      _rtiAmbassadorClient.setInteractionRelevanceAdvisorySwitch(true);
   }

   void RTIambassadorClientAdapter::disableInteractionRelevanceAdvisorySwitch() RTI_THROW(InteractionRelevanceAdvisorySwitchIsOff, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (!_rtiAmbassadorClient.getInteractionRelevanceAdvisorySwitch()) {
         throw InteractionRelevanceAdvisorySwitchIsOff(L"Advisory switch is already disabled");
      }
      _rtiAmbassadorClient.setInteractionRelevanceAdvisorySwitch(false);
   }

   bool RTIambassadorClientAdapter::evokeCallback(
         double approximateMinimumTimeInSeconds) RTI_THROW(CallNotAllowedFromWithinCallback, RTIinternalError)
   {
      return _rtiAmbassadorClient.evokeCallback(approximateMinimumTimeInSeconds);
   }

   bool RTIambassadorClientAdapter::evokeMultipleCallbacks(
         double approximateMinimumTimeInSeconds,
         double approximateMaximumTimeInSeconds) RTI_THROW(CallNotAllowedFromWithinCallback, RTIinternalError)
   {
      return _rtiAmbassadorClient.evokeMultipleCallbacks(
            approximateMinimumTimeInSeconds, approximateMaximumTimeInSeconds);
   }

   void RTIambassadorClientAdapter::enableCallbacks() RTI_THROW(SaveInProgress, RestoreInProgress, RTIinternalError)
   {
      _rtiAmbassadorClient.enableCallbacks();
   }

   void RTIambassadorClientAdapter::disableCallbacks() RTI_THROW(SaveInProgress, RestoreInProgress, RTIinternalError)
   {
      _rtiAmbassadorClient.disableCallbacks();
   }

   unique_ptr<LogicalTimeFactory> RTIambassadorClientAdapter::getTimeFactory() const RTI_THROW(FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      if (_timeFactory) {
         return LogicalTimeFactoryFactory::makeLogicalTimeFactory(_timeFactory->getName());
      } else {
         throw FederateNotExecutionMember(L"No time factory available");
      }
   }

   FederateHandle RTIambassadorClientAdapter::decodeFederateHandle(const VariableLengthData & encodedValue) const RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return make_handle<FederateHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   ObjectClassHandle RTIambassadorClientAdapter::decodeObjectClassHandle(const VariableLengthData & encodedValue) const RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return make_handle<ObjectClassHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   InteractionClassHandle RTIambassadorClientAdapter::decodeInteractionClassHandle(const VariableLengthData & encodedValue) const RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return make_handle<InteractionClassHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   ObjectInstanceHandle RTIambassadorClientAdapter::decodeObjectInstanceHandle(const VariableLengthData & encodedValue) const RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return make_handle<ObjectInstanceHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   AttributeHandle RTIambassadorClientAdapter::decodeAttributeHandle(const VariableLengthData & encodedValue) const RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return make_handle<AttributeHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   ParameterHandle RTIambassadorClientAdapter::decodeParameterHandle(const VariableLengthData & encodedValue) const RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return make_handle<ParameterHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   DimensionHandle RTIambassadorClientAdapter::decodeDimensionHandle(const VariableLengthData & encodedValue) const RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return make_handle<DimensionHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   MessageRetractionHandle RTIambassadorClientAdapter::decodeMessageRetractionHandle(const VariableLengthData & encodedValue) const RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return make_handle<MessageRetractionHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   RegionHandle RTIambassadorClientAdapter::decodeRegionHandle(const VariableLengthData & encodedValue) const RTI_THROW(CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError)
   {
      return make_handle<RegionHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   void RTIambassadorClientAdapter::throwIfInvalidLookahead(const LogicalTimeInterval & lookahead)
   {
      if (!_timeFactory) {
         throw InvalidLogicalTime(L"No time factory available");
      }

      if (dynamic_cast<const HLAfloat64Interval *>(&lookahead) &&
          dynamic_cast<HLAfloat64TimeFactory *>(_timeFactory.get())) {
         return;
      }
      if (dynamic_cast<const HLAinteger64Interval *>(&lookahead) &&
          dynamic_cast<HLAinteger64TimeFactory *>(_timeFactory.get())) {
         return;
      }
      throw InvalidLookahead(L"Incorrect LogicalTimeInterval type");
   }

   void RTIambassadorClientAdapter::throwIfInvalidTime(const LogicalTime & time)
   {
      if (!_timeFactory) {
         throw InvalidLogicalTime(L"No time factory available");
      }

      if (dynamic_cast<const HLAfloat64Time *>(&time) && dynamic_cast<HLAfloat64TimeFactory *>(_timeFactory.get())) {
         return;
      }
      if (dynamic_cast<const HLAinteger64Time *>(&time) &&
          dynamic_cast<HLAinteger64TimeFactory *>(_timeFactory.get())) {
         return;
      }
      throw InvalidLogicalTime(L"Incorrect LogicalTime type");
   }

   RTI_NAMESPACE::RtiConfiguration RTIambassadorClientAdapter::createRtiConfiguration(
         std::vector<std::wstring> & inputValueList)
   {
      if (inputValueList.empty()) {
         return {};
      }

      RTI_NAMESPACE::RtiConfiguration rtiConfiguration;

      std::wstring firstLine = inputValueList[0];
      if (!firstLine.empty() && firstLine.find('=') == wstring_view::npos) {
         rtiConfiguration.withConfigurationName(firstLine);
         inputValueList.erase(inputValueList.begin());
      }

      rtiConfiguration.withAdditionalSettings(RTIambassadorClientGenericBase::toAdditionalSettingsString(inputValueList));

      // No need to set the rtiAddress since an LRC may specify the CRC address through the additional settings field
      // of the RtiConfiguration object, which is done in this method.
      return rtiConfiguration;
   }

   const std::string & RTIambassadorClientAdapter::getTransportationTypeName(TransportationType transportationType)
   {
      if (transportationType == RELIABLE) {
         static const std::string reliable("HLAreliable");
         return reliable;
      } else if (transportationType == BEST_EFFORT) {
         static const std::string bestEffort("HLAbestEffort");
         return bestEffort;
      } else {
         throw std::out_of_range("TransportationType is not a pre-defined type");
      }
   }

} // RTI_NAMESPACE

// NOLINTEND(hicpp-exception-baseclass, modernize-use-noexcept)
