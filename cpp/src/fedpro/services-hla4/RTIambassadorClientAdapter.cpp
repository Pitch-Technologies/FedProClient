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
// NOLINTBEGIN(hicpp-exception-baseclass)

#include "RTIambassadorClientAdapter.h"

#include <fedpro/FedProExceptions.h>
#include <fedpro/Properties.h>
#include "services-common/FomModuleLoader.h"
#include "services-common/HandleImplementation.h"
#include "services-common/RegionHandleImplementation.h"
#include "services-common/SettingsParser.h"
#include "utility/StringUtil.h"
#include "utility/string_view.h"

#include <RTI/RtiConfiguration.h>
#include <RTI/time/HLAfloat64Interval.h>
#include <RTI/time/HLAfloat64Time.h>
#include <RTI/time/HLAfloat64TimeFactory.h>
#include <RTI/time/HLAinteger64Interval.h>
#include <RTI/time/HLAinteger64Time.h>
#include <RTI/time/HLAinteger64TimeFactory.h>

using namespace FedPro;

namespace RTI_NAMESPACE
{
   RTIambassadorClientAdapter::RTIambassadorClientAdapter()
         : _clientConverter{std::make_shared<FedPro::ClientConverter>()},
           _rtiAmbassadorClient{_clientConverter}
   {
   }

   RTIambassadorClientAdapter::~RTIambassadorClientAdapter() = default;

   ConfigurationResult RTIambassadorClientAdapter::connect(
         FederateAmbassador & federateAmbassador,
         CallbackModel callbackModel)
   {
      try {
         ConfigurationResult result = _clientConverter->convertToHla(
               _rtiAmbassadorClient.connect(
                     string_view{},
                     federateAmbassador,
                     callbackModel));
         _rtiAmbassadorClient.prefetch();
         return result;
      } catch (const std::exception & e) {
         throw RTIinternalError(toWString(e.what()));
      }
   }

   ConfigurationResult RTIambassadorClientAdapter::connect(
         FederateAmbassador & federateAmbassador,
         CallbackModel callbackModel,
         const RtiConfiguration & configuration)
   {
      std::vector<std::wstring> inputValueList =
            RTIambassadorClientGenericBase::splitFederateConnectSettings(configuration.additionalSettings());
      addServerAddressToList(inputValueList, configuration);
      const std::string
            clientSettings = RTIambassadorClientGenericBase::extractAndRemoveClientSettings(inputValueList, false);
      std::unique_ptr<rti1516_202X::fedpro::RtiConfiguration> parsedRtiConfiguration = parseRtiConfiguration(configuration, inputValueList);

      try {
         ConfigurationResult result = _clientConverter->convertToHla(
               _rtiAmbassadorClient.connect(
                     clientSettings, federateAmbassador, callbackModel, std::move(parsedRtiConfiguration)));
         _rtiAmbassadorClient.prefetch();
         return result;
      } catch (const std::exception & e) {
         throw RTIinternalError(toWString(e.what()));
      }
   }

   ConfigurationResult RTIambassadorClientAdapter::connect(
         FederateAmbassador & federateAmbassador,
         CallbackModel callbackModel,
         const Credentials & credentials)
   {
      try {
         ConfigurationResult result = _clientConverter->convertToHla(
               _rtiAmbassadorClient.connect(string_view{}, federateAmbassador, callbackModel, nullptr, &credentials));
         _rtiAmbassadorClient.prefetch();
         return result;
      } catch (const std::exception & e) {
         throw RTIinternalError(toWString(e.what()));
      }
   }

   ConfigurationResult RTIambassadorClientAdapter::connect(
         FederateAmbassador & federateAmbassador,
         CallbackModel callbackModel,
         const RtiConfiguration & configuration,
         const Credentials & credentials)
   {
      try {
         std::vector<std::wstring> inputValueList =
               RTIambassadorClientGenericBase::splitFederateConnectSettings(configuration.additionalSettings());
         addServerAddressToList(inputValueList, configuration);
         const std::string
               clientSettings = RTIambassadorClientGenericBase::extractAndRemoveClientSettings(inputValueList, false);
         std::unique_ptr<rti1516_202X::fedpro::RtiConfiguration> parsedRtiConfiguration = parseRtiConfiguration(configuration, inputValueList);

         ConfigurationResult result = _clientConverter->convertToHla(_rtiAmbassadorClient.connect(
               clientSettings, federateAmbassador, callbackModel, std::move(parsedRtiConfiguration), &credentials));
         _rtiAmbassadorClient.prefetch();
         return result;
      } catch (const std::exception & e) {
         throw RTIinternalError(toWString(e.what()));
      }
   }

   void RTIambassadorClientAdapter::disconnect()
   {
      _rtiAmbassadorClient.disconnect();
   }

   void RTIambassadorClientAdapter::createFederationExecution(
         const std::wstring & federationName,
         const std::wstring & fomModule,
         const std::wstring & logicalTimeImplementationName)
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
         const std::wstring & logicalTimeImplementationName)
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
         const std::wstring & logicalTimeImplementationName)
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
         } catch (const CouldNotOpenFOM & e) {
            throw CouldNotOpenMIM(e.what());
         }
      }
   }

   void RTIambassadorClientAdapter::destroyFederationExecution(const std::wstring & federationName)
   {
      _rtiAmbassadorClient.destroyFederationExecution(_clientConverter->convertStringFromHla(federationName));
   }

   void RTIambassadorClientAdapter::listFederationExecutions()
   {
      _rtiAmbassadorClient.listFederationExecutions();
   }

   void RTIambassadorClientAdapter::listFederationExecutionMembers(std::wstring const & federationName)
   {
      _rtiAmbassadorClient.listFederationExecutionMembers(_clientConverter->convertStringFromHla(federationName));
   }

   FederateHandle RTIambassadorClientAdapter::joinFederationExecution(
         std::wstring const & federateType,
         std::wstring const & federationName,
         std::vector<std::wstring> const & additionalFomModules)
   {
      FomModuleSet fomModuleSet = _fomModuleLoader.getFomModuleSet(additionalFomModules);
      JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecutionWithModules(
            _clientConverter->convertStringFromHla(federateType),
            _clientConverter->convertStringFromHla(federationName),
            fomModuleSet);
      _timeFactory = LogicalTimeFactoryFactory::makeLogicalTimeFactory(joinResult.getLogicalTimeImplementationName());
      _clientConverter->setTimeFactory(_timeFactory);
      return joinResult.getFederateHandle();
   }

   FederateHandle RTIambassadorClientAdapter::joinFederationExecution(
         std::wstring const & federateName,
         std::wstring const & federateType,
         std::wstring const & federationName,
         std::vector<std::wstring> const & additionalFomModules)
   {
      FomModuleSet fomModuleSet = _fomModuleLoader.getFomModuleSet(additionalFomModules);
      JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecutionWithNameAndModules(
            _clientConverter->convertStringFromHla(federateName),
            _clientConverter->convertStringFromHla(federateType),
            _clientConverter->convertStringFromHla(federationName),
            fomModuleSet);
      _timeFactory = LogicalTimeFactoryFactory::makeLogicalTimeFactory(joinResult.getLogicalTimeImplementationName());
      _clientConverter->setTimeFactory(_timeFactory);
      return joinResult.getFederateHandle();
   }

   void RTIambassadorClientAdapter::resignFederationExecution(
         ResignAction resignAction)
   {
      _rtiAmbassadorClient.resignFederationExecution(resignAction);
   }

   void RTIambassadorClientAdapter::registerFederationSynchronizationPoint(
         std::wstring const & synchronizationPointLabel,
         const VariableLengthData & userSuppliedTag)
   {
      _rtiAmbassadorClient.registerFederationSynchronizationPoint(
            _clientConverter->convertStringFromHla(synchronizationPointLabel), userSuppliedTag);
   }

   void RTIambassadorClientAdapter::registerFederationSynchronizationPoint(
         std::wstring const & synchronizationPointLabel,
         const VariableLengthData & userSuppliedTag,
         FederateHandleSet const & synchronizationSet)
   {
      _rtiAmbassadorClient.registerFederationSynchronizationPointWithSet(
            _clientConverter->convertStringFromHla(synchronizationPointLabel), userSuppliedTag, synchronizationSet);
   }

   void RTIambassadorClientAdapter::synchronizationPointAchieved(
         std::wstring const & synchronizationPointLabel,
         bool successfully)
   {
      _rtiAmbassadorClient.synchronizationPointAchieved(
            _clientConverter->convertStringFromHla(synchronizationPointLabel), successfully);
   }

   void RTIambassadorClientAdapter::requestFederationSave(
         std::wstring const & label)
   {
      _rtiAmbassadorClient.requestFederationSave(_clientConverter->convertStringFromHla(label));
   }

   void RTIambassadorClientAdapter::requestFederationSave(
         std::wstring const & label,
         const LogicalTime & time)
   {
      _rtiAmbassadorClient.requestFederationSaveWithTime(_clientConverter->convertStringFromHla(label), time);
   }

   void RTIambassadorClientAdapter::federateSaveBegun()
   {
      _rtiAmbassadorClient.federateSaveBegun();
   }

   void RTIambassadorClientAdapter::federateSaveComplete()
   {
      _rtiAmbassadorClient.federateSaveComplete();
   }

   void RTIambassadorClientAdapter::federateSaveNotComplete()
   {
      _rtiAmbassadorClient.federateSaveNotComplete();
   }

   void RTIambassadorClientAdapter::abortFederationSave()
   {
      _rtiAmbassadorClient.abortFederationSave();
   }

   void RTIambassadorClientAdapter::queryFederationSaveStatus()
   {
      _rtiAmbassadorClient.queryFederationSaveStatus();
   }

   void RTIambassadorClientAdapter::requestFederationRestore(
         std::wstring const & label)
   {
      _rtiAmbassadorClient.requestFederationRestore(_clientConverter->convertStringFromHla(label));
   }

   void RTIambassadorClientAdapter::federateRestoreComplete()
   {
      _rtiAmbassadorClient.federateRestoreComplete();
   }

   void RTIambassadorClientAdapter::federateRestoreNotComplete()
   {
      _rtiAmbassadorClient.federateRestoreNotComplete();
   }

   void RTIambassadorClientAdapter::abortFederationRestore()
   {
      _rtiAmbassadorClient.abortFederationRestore();
   }

   void RTIambassadorClientAdapter::queryFederationRestoreStatus()
   {
      _rtiAmbassadorClient.queryFederationRestoreStatus();
   }

   void RTIambassadorClientAdapter::publishObjectClassAttributes(
         const ObjectClassHandle & objectClass,
         const AttributeHandleSet & attributes)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.publishObjectClassAttributes(objectClass, attributes);
   }

   void RTIambassadorClientAdapter::unpublishObjectClass(const ObjectClassHandle & objectClass)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      _rtiAmbassadorClient.unpublishObjectClass(objectClass);
   }

   void RTIambassadorClientAdapter::unpublishObjectClassAttributes(
         const ObjectClassHandle & objectClass,
         const AttributeHandleSet & attributes)
   {
      return _rtiAmbassadorClient.unpublishObjectClassAttributes(objectClass, attributes);
   }

   void RTIambassadorClientAdapter::publishObjectClassDirectedInteractions(
         const ObjectClassHandle & objectClass,
         InteractionClassHandleSet const & interactionClasses)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.publishObjectClassDirectedInteractions(objectClass, interactionClasses);
   }

   void RTIambassadorClientAdapter::unpublishObjectClassDirectedInteractions(const ObjectClassHandle & objectClass)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      _rtiAmbassadorClient.unpublishObjectClassDirectedInteractions(objectClass);
   }

   void RTIambassadorClientAdapter::unpublishObjectClassDirectedInteractions(
         const ObjectClassHandle & objectClass,
         InteractionClassHandleSet const & interactionClasses)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.unpublishObjectClassDirectedInteractionsWithSet(objectClass, interactionClasses);
   }

   void RTIambassadorClientAdapter::publishInteractionClass(const InteractionClassHandle & interactionClass)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      _rtiAmbassadorClient.publishInteractionClass(interactionClass);
   }

   void RTIambassadorClientAdapter::unpublishInteractionClass(const InteractionClassHandle & interactionClass)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      _rtiAmbassadorClient.unpublishInteractionClass(interactionClass);
   }

   void RTIambassadorClientAdapter::subscribeObjectClassAttributes(
         const ObjectClassHandle & objectClass,
         const AttributeHandleSet & attributes,
         bool active,
         std::wstring const & updateRateDesignator)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      if (active) {
         if (updateRateDesignator.empty()) {
            return _rtiAmbassadorClient.subscribeObjectClassAttributes(
                  objectClass, attributes);
         } else {
            return _rtiAmbassadorClient.subscribeObjectClassAttributesWithRate(
                  objectClass, attributes, _clientConverter->convertStringFromHla(updateRateDesignator));
         }
      } else {
         if (updateRateDesignator.empty()) {
            return _rtiAmbassadorClient.subscribeObjectClassAttributesPassively(
                  objectClass, attributes);
         } else {
            return _rtiAmbassadorClient.subscribeObjectClassAttributesPassivelyWithRate(
                  objectClass, attributes, _clientConverter->convertStringFromHla(updateRateDesignator));
         }
      }
   }

   void RTIambassadorClientAdapter::unsubscribeObjectClass(const ObjectClassHandle & objectClass)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      _rtiAmbassadorClient.unsubscribeObjectClass(objectClass);
   }

   void RTIambassadorClientAdapter::unsubscribeObjectClassAttributes(
         const ObjectClassHandle & objectClass,
         const AttributeHandleSet & attributes)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.unsubscribeObjectClassAttributes(objectClass, attributes);
   }

   void RTIambassadorClientAdapter::subscribeObjectClassDirectedInteractions(
         const ObjectClassHandle & objectClass,
         InteractionClassHandleSet const & interactionClasses,
         bool universally)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }

      if (universally) {
         return _rtiAmbassadorClient.subscribeObjectClassDirectedInteractionsUniversally(
               objectClass, interactionClasses);
      } else {
         return _rtiAmbassadorClient.subscribeObjectClassDirectedInteractions(objectClass, interactionClasses);
      }
   }

   void RTIambassadorClientAdapter::unsubscribeObjectClassDirectedInteractions(const ObjectClassHandle & objectClass)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      _rtiAmbassadorClient.unsubscribeObjectClassDirectedInteractions(objectClass);
   }

   void RTIambassadorClientAdapter::unsubscribeObjectClassDirectedInteractions(
         const ObjectClassHandle & objectClass,
         InteractionClassHandleSet const & interactionClasses)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.unsubscribeObjectClassDirectedInteractionsWithSet(objectClass, interactionClasses);
   }

   void RTIambassadorClientAdapter::subscribeInteractionClass(
         const InteractionClassHandle & interactionClass,
         bool active)
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

   void RTIambassadorClientAdapter::unsubscribeInteractionClass(const InteractionClassHandle & interactionClass)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      _rtiAmbassadorClient.unsubscribeInteractionClass(interactionClass);
   }

   void RTIambassadorClientAdapter::reserveObjectInstanceName(
         std::wstring const & objectInstanceName)
   {
      _rtiAmbassadorClient.reserveObjectInstanceName(
            _clientConverter->convertStringFromHla(objectInstanceName));
   }

   void RTIambassadorClientAdapter::releaseObjectInstanceName(
         std::wstring const & objectInstanceName)
   {
      _rtiAmbassadorClient.releaseObjectInstanceName(
            _clientConverter->convertStringFromHla(objectInstanceName));
   }

   void RTIambassadorClientAdapter::reserveMultipleObjectInstanceNames(std::set<std::wstring> const & objectInstanceNames)
   {
      _rtiAmbassadorClient.reserveMultipleObjectInstanceNames(objectInstanceNames);
   }

   void RTIambassadorClientAdapter::releaseMultipleObjectInstanceNames(std::set<std::wstring> const & objectInstanceNames)
   {
      _rtiAmbassadorClient.releaseMultipleObjectInstanceNames(objectInstanceNames);
   }

   ObjectInstanceHandle RTIambassadorClientAdapter::registerObjectInstance(const ObjectClassHandle & objectClass)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.registerObjectInstance(objectClass);
   }

   ObjectInstanceHandle RTIambassadorClientAdapter::registerObjectInstance(
         const ObjectClassHandle & objectClass,
         std::wstring const & objectInstanceName)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithName(
            objectClass, _clientConverter->convertStringFromHla(
                  objectInstanceName));
   }

   void RTIambassadorClientAdapter::updateAttributeValues(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleValueMap & attributeValues,
         const VariableLengthData & userSuppliedTag)
   {
      _rtiAmbassadorClient.updateAttributeValues(objectInstance, attributeValues, userSuppliedTag);
   }

   MessageRetractionHandle RTIambassadorClientAdapter::updateAttributeValues(
         const ObjectInstanceHandle & objectInstance,
         AttributeHandleValueMap const & attributeValues,
         const VariableLengthData & userSuppliedTag,
         const LogicalTime & time)
   {
      return _rtiAmbassadorClient.updateAttributeValuesWithTime(objectInstance, attributeValues, userSuppliedTag, time);
   }

   void RTIambassadorClientAdapter::sendInteraction(
         const InteractionClassHandle & interactionClass,
         const ParameterHandleValueMap & parameterValues,
         const VariableLengthData & userSuppliedTag)
   {
      _rtiAmbassadorClient.sendInteraction(interactionClass, parameterValues, userSuppliedTag);
   }

   MessageRetractionHandle RTIambassadorClientAdapter::sendInteraction(
         const InteractionClassHandle & interactionClass,
         const ParameterHandleValueMap & parameterValues,
         const VariableLengthData & userSuppliedTag,
         const LogicalTime & time)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      return _rtiAmbassadorClient.sendInteractionWithTime(interactionClass, parameterValues, userSuppliedTag, time);
   }

   void RTIambassadorClientAdapter::sendDirectedInteraction(
         const InteractionClassHandle & interactionClass,
         const ObjectInstanceHandle & objectInstance,
         const ParameterHandleValueMap & parameterValues,
         const VariableLengthData & userSuppliedTag)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      _rtiAmbassadorClient.sendDirectedInteraction(interactionClass, objectInstance, parameterValues, userSuppliedTag);
   }

   MessageRetractionHandle RTIambassadorClientAdapter::sendDirectedInteraction(
         const InteractionClassHandle & interactionClass,
         const ObjectInstanceHandle & objectInstance,
         const ParameterHandleValueMap & parameterValues,
         const VariableLengthData & userSuppliedTag,
         const LogicalTime & time)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      return _rtiAmbassadorClient.sendDirectedInteractionWithTime(
            interactionClass, objectInstance, parameterValues, userSuppliedTag, time);
   }

   void RTIambassadorClientAdapter::deleteObjectInstance(
         const ObjectInstanceHandle & objectInstance,
         const VariableLengthData & userSuppliedTag)
   {
      return _rtiAmbassadorClient.deleteObjectInstance(objectInstance, userSuppliedTag);
   }

   MessageRetractionHandle RTIambassadorClientAdapter::deleteObjectInstance(
         const ObjectInstanceHandle & objectInstance,
         const VariableLengthData & userSuppliedTag,
         LogicalTime const & time)
   {
      return _rtiAmbassadorClient.deleteObjectInstanceWithTime(objectInstance, userSuppliedTag, time);
   }

   void RTIambassadorClientAdapter::localDeleteObjectInstance(const ObjectInstanceHandle & objectInstance)
   {
      _rtiAmbassadorClient.localDeleteObjectInstance(objectInstance);
   }

   void RTIambassadorClientAdapter::requestAttributeValueUpdate(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSet & attributes,
         const VariableLengthData & userSuppliedTag)
   {
      return _rtiAmbassadorClient.requestInstanceAttributeValueUpdate(objectInstance, attributes, userSuppliedTag);
   }

   void RTIambassadorClientAdapter::requestAttributeValueUpdate(
         const ObjectClassHandle & objectClass,
         const AttributeHandleSet & attributes,
         const VariableLengthData & userSuppliedTag)
   {
      return _rtiAmbassadorClient.requestClassAttributeValueUpdate(objectClass, attributes, userSuppliedTag);
   }

   void RTIambassadorClientAdapter::requestAttributeTransportationTypeChange(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSet & attributes,
         const TransportationTypeHandle & transportationType)
   {
      return _rtiAmbassadorClient.requestAttributeTransportationTypeChange(
            objectInstance, attributes, transportationType);
   }

   void RTIambassadorClientAdapter::changeDefaultAttributeTransportationType(
         const ObjectClassHandle & objectClass,
         const AttributeHandleSet & attributes,
         const TransportationTypeHandle & transportationType)
   {
      return _rtiAmbassadorClient.changeDefaultAttributeTransportationType(objectClass, attributes, transportationType);
   }

   void RTIambassadorClientAdapter::queryAttributeTransportationType(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandle & attribute)
   {
      return _rtiAmbassadorClient.queryAttributeTransportationType(objectInstance, attribute);
   }

   void RTIambassadorClientAdapter::requestInteractionTransportationTypeChange(
         const InteractionClassHandle & interactionClass,
         const TransportationTypeHandle & transportationType)
   {
      return _rtiAmbassadorClient.requestInteractionTransportationTypeChange(interactionClass, transportationType);
   }

   void RTIambassadorClientAdapter::queryInteractionTransportationType(
         const FederateHandle & federate,
         const InteractionClassHandle & interactionClass)
   {
      _rtiAmbassadorClient.queryInteractionTransportationType(federate, interactionClass);
   }

   void RTIambassadorClientAdapter::unconditionalAttributeOwnershipDivestiture(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSet & attributes,
         const VariableLengthData & userSuppliedTag)
   {
      _rtiAmbassadorClient.unconditionalAttributeOwnershipDivestiture(
            objectInstance, attributes, userSuppliedTag);
   }

   void RTIambassadorClientAdapter::negotiatedAttributeOwnershipDivestiture(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSet & attributes,
         const VariableLengthData & userSuppliedTag)
   {
      _rtiAmbassadorClient.negotiatedAttributeOwnershipDivestiture(objectInstance, attributes, userSuppliedTag);
   }

   void RTIambassadorClientAdapter::confirmDivestiture(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSet & confirmedAttributes,
         const VariableLengthData & userSuppliedTag)
   {
      _rtiAmbassadorClient.confirmDivestiture(objectInstance, confirmedAttributes, userSuppliedTag);
   }

   void RTIambassadorClientAdapter::attributeOwnershipAcquisition(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSet & desiredAttributes,
         const VariableLengthData & userSuppliedTag)
   {
      _rtiAmbassadorClient.attributeOwnershipAcquisition(objectInstance, desiredAttributes, userSuppliedTag);
   }

   void RTIambassadorClientAdapter::attributeOwnershipAcquisitionIfAvailable(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSet & desiredAttributes,
         const VariableLengthData & userSuppliedTag)
   {
      _rtiAmbassadorClient.attributeOwnershipAcquisitionIfAvailable(
            objectInstance, desiredAttributes, userSuppliedTag);
   }

   void RTIambassadorClientAdapter::attributeOwnershipReleaseDenied(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSet & attributes,
         const VariableLengthData & userSuppliedTag)
   {
      _rtiAmbassadorClient.attributeOwnershipReleaseDenied(objectInstance, attributes, userSuppliedTag);
   }

   void RTIambassadorClientAdapter::attributeOwnershipDivestitureIfWanted(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSet & attributes,
         const VariableLengthData & userSuppliedTag,
         AttributeHandleSet & divestedAttributes)
   {
      divestedAttributes = _rtiAmbassadorClient.attributeOwnershipDivestitureIfWanted(
            objectInstance, attributes, userSuppliedTag);
   }

   void RTIambassadorClientAdapter::cancelNegotiatedAttributeOwnershipDivestiture(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSet & attributes)
   {
      _rtiAmbassadorClient.cancelNegotiatedAttributeOwnershipDivestiture(objectInstance, attributes);
   }

   void RTIambassadorClientAdapter::cancelAttributeOwnershipAcquisition(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSet & attributes)
   {
      _rtiAmbassadorClient.cancelAttributeOwnershipAcquisition(objectInstance, attributes);
   }

   void RTIambassadorClientAdapter::queryAttributeOwnership(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSet & attributes)
   {
      _rtiAmbassadorClient.queryAttributeOwnership(objectInstance, attributes);
   }

   bool RTIambassadorClientAdapter::isAttributeOwnedByFederate(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandle & attribute)
   {
      return _rtiAmbassadorClient.isAttributeOwnedByFederate(objectInstance, attribute);
   }

   void RTIambassadorClientAdapter::enableTimeRegulation(const LogicalTimeInterval & lookahead)
   {
      throwIfInvalidLookahead(lookahead);
      _rtiAmbassadorClient.enableTimeRegulation(lookahead);
   }

   void RTIambassadorClientAdapter::disableTimeRegulation()
   {
      _rtiAmbassadorClient.disableTimeRegulation();
   }

   void RTIambassadorClientAdapter::enableTimeConstrained()
   {
      _rtiAmbassadorClient.enableTimeConstrained();
   }

   void RTIambassadorClientAdapter::disableTimeConstrained()
   {
      _rtiAmbassadorClient.disableTimeConstrained();
   }

   void RTIambassadorClientAdapter::timeAdvanceRequest(const LogicalTime & time)
   {
      throwIfInvalidTime(time);
      _rtiAmbassadorClient.timeAdvanceRequest(time);
   }

   void RTIambassadorClientAdapter::timeAdvanceRequestAvailable(const LogicalTime & time)
   {
      throwIfInvalidTime(time);
      _rtiAmbassadorClient.timeAdvanceRequestAvailable(time);
   }

   void RTIambassadorClientAdapter::nextMessageRequest(const LogicalTime & time)
   {
      throwIfInvalidTime(time);
      _rtiAmbassadorClient.nextMessageRequest(time);
   }

   void RTIambassadorClientAdapter::nextMessageRequestAvailable(const LogicalTime & time)
   {
      throwIfInvalidTime(time);
      _rtiAmbassadorClient.nextMessageRequestAvailable(time);
   }

   void RTIambassadorClientAdapter::flushQueueRequest(const LogicalTime & time)
   {
      throwIfInvalidTime(time);
      _rtiAmbassadorClient.flushQueueRequest(time);
   }

   void RTIambassadorClientAdapter::enableAsynchronousDelivery()
   {
      _rtiAmbassadorClient.enableAsynchronousDelivery();
   }

   void RTIambassadorClientAdapter::disableAsynchronousDelivery()
   {
      _rtiAmbassadorClient.disableAsynchronousDelivery();
   }

   bool RTIambassadorClientAdapter::queryGALT(LogicalTime & time)
   {
      auto resultPair = _rtiAmbassadorClient.queryGALT();
      if (resultPair.second) {
         time = *resultPair.second;
      }
      return resultPair.first;
   }

   void RTIambassadorClientAdapter::queryLogicalTime(LogicalTime & time)
   {
      std::unique_ptr<RTI_NAMESPACE::LogicalTime> timePointer = _rtiAmbassadorClient.queryLogicalTime();
      if (timePointer) {
         time = *timePointer;
      } else {
         throw FederateNotExecutionMember(L"No query result available");
      }
   }

   bool RTIambassadorClientAdapter::queryLITS(LogicalTime & time)
   {
      auto resultPair = _rtiAmbassadorClient.queryLITS();
      if (resultPair.second) {
         time = *resultPair.second;
      }
      return resultPair.first;
   }

   void RTIambassadorClientAdapter::modifyLookahead(const LogicalTimeInterval & lookahead)
   {
      _rtiAmbassadorClient.modifyLookahead(lookahead);
   }

   void RTIambassadorClientAdapter::queryLookahead(LogicalTimeInterval & interval)
   {
      std::unique_ptr<RTI_NAMESPACE::LogicalTimeInterval> intervalPointer = _rtiAmbassadorClient.queryLookahead();
      if (intervalPointer) {
         interval = *intervalPointer;
      } else {
         throw FederateNotExecutionMember(L"No query result available");
      }
   }

   void RTIambassadorClientAdapter::retract(const MessageRetractionHandle & retraction)
   {
      _rtiAmbassadorClient.retract(retraction);
   }

   void RTIambassadorClientAdapter::changeAttributeOrderType(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSet & attributes,
         OrderType orderType)
   {
      return _rtiAmbassadorClient.changeAttributeOrderType(objectInstance, attributes, orderType);
   }

   void RTIambassadorClientAdapter::changeDefaultAttributeOrderType(
         const ObjectClassHandle & objectClass,
         const AttributeHandleSet & attributes,
         OrderType orderType)
   {
      return _rtiAmbassadorClient.changeDefaultAttributeOrderType(objectClass, attributes, orderType);
   }

   void RTIambassadorClientAdapter::changeInteractionOrderType(
         const InteractionClassHandle & interactionClass,
         OrderType orderType)
   {
      return _rtiAmbassadorClient.changeInteractionOrderType(interactionClass, orderType);
   }

   RegionHandle RTIambassadorClientAdapter::createRegion(DimensionHandleSet const & dimensions)
   {
      return _rtiAmbassadorClient.createRegion(dimensions);
   }

   void RTIambassadorClientAdapter::commitRegionModifications(const RegionHandleSet & regions)
   {
      _rtiAmbassadorClient.commitRegionModifications(regions);
   }

   void RTIambassadorClientAdapter::deleteRegion(const RegionHandle & region)
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
         const ObjectClassHandle & objectClass,
         const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithRegions(objectClass, attributesAndRegions);
   }

   ObjectInstanceHandle RTIambassadorClientAdapter::registerObjectInstanceWithRegions(
         const ObjectClassHandle & objectClass,
         const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
         std::wstring const & objectInstanceName)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithNameAndRegions(
            objectClass, attributesAndRegions, _clientConverter->convertStringFromHla(objectInstanceName));
   }

   void RTIambassadorClientAdapter::associateRegionsForUpdates(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions)
   {
      if (!objectInstance.isValid()) {
         throw ObjectInstanceNotKnown(L"Invalid ObjectInstanceHandle");
      }
      return _rtiAmbassadorClient.associateRegionsForUpdates(objectInstance, attributesAndRegions);
   }

   void RTIambassadorClientAdapter::unassociateRegionsForUpdates(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions)
   {
      if (!objectInstance.isValid()) {
         throw ObjectInstanceNotKnown(L"Invalid ObjectInstanceHandle");
      }
      return _rtiAmbassadorClient.unassociateRegionsForUpdates(objectInstance, attributesAndRegions);
   }

   void RTIambassadorClientAdapter::subscribeObjectClassAttributesWithRegions(
         const ObjectClassHandle & objectClass,
         const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
         bool active,
         std::wstring const & updateRateDesignator)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      if (updateRateDesignator.empty()) {
         _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegions(
               objectClass, attributesAndRegions, active);
      } else {
         _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegionsAndRate(
               objectClass, attributesAndRegions, active, _clientConverter->convertStringFromHla(updateRateDesignator));
      }
   }

   void RTIambassadorClientAdapter::unsubscribeObjectClassAttributesWithRegions(
         const ObjectClassHandle & objectClass,
         const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      _rtiAmbassadorClient.unsubscribeObjectClassAttributesWithRegions(objectClass, attributesAndRegions);
   }

   void RTIambassadorClientAdapter::subscribeInteractionClassWithRegions(
         const InteractionClassHandle & interactionClass,
         const RegionHandleSet & regions,
         bool active)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      _rtiAmbassadorClient.subscribeInteractionClassWithRegions(interactionClass, active, regions);
   }

   void RTIambassadorClientAdapter::unsubscribeInteractionClassWithRegions(
         const InteractionClassHandle & interactionClass,
         const RegionHandleSet & regions)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      _rtiAmbassadorClient.unsubscribeInteractionClassWithRegions(interactionClass, regions);
   }

   void RTIambassadorClientAdapter::sendInteractionWithRegions(
         const InteractionClassHandle & interactionClass,
         const ParameterHandleValueMap & parameterValues,
         const RegionHandleSet & regions,
         const VariableLengthData & userSuppliedTag)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      _rtiAmbassadorClient.sendInteractionWithRegions(
            interactionClass, parameterValues, regions, userSuppliedTag);
   }

   MessageRetractionHandle RTIambassadorClientAdapter::sendInteractionWithRegions(
         const InteractionClassHandle & interactionClass,
         const ParameterHandleValueMap & parameterValues,
         const RegionHandleSet & regions,
         const VariableLengthData & userSuppliedTag,
         const LogicalTime & time)
   {
      if (!interactionClass.isValid()) {
         throw InteractionClassNotDefined(L"Invalid InteractionClassHandle");
      }
      return _rtiAmbassadorClient.sendInteractionWithRegionsAndTime(
            interactionClass, parameterValues, regions, userSuppliedTag, time);
   }

   void RTIambassadorClientAdapter::requestAttributeValueUpdateWithRegions(
         const ObjectClassHandle & objectClass,
         const AttributeHandleSetRegionHandleSetPairVector & attributesAndRegions,
         const VariableLengthData & userSuppliedTag)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      _rtiAmbassadorClient.requestAttributeValueUpdateWithRegions(
            objectClass, attributesAndRegions, userSuppliedTag);
   }

   FederateHandle RTIambassadorClientAdapter::getFederateHandle(std::wstring const & federateName)
   {
      return _rtiAmbassadorClient.getFederateHandle(_clientConverter->convertStringFromHla(federateName));
   }

   std::wstring RTIambassadorClientAdapter::getFederateName(const FederateHandle & federate)
   {
      if (!federate.isValid()) {
         throw InvalidFederateHandle(L"Invalid FederateHandle");
      }
      return _rtiAmbassadorClient.getFederateName(federate);
   }

   ObjectClassHandle RTIambassadorClientAdapter::getObjectClassHandle(std::wstring const & objectClassName)
   {
      return _rtiAmbassadorClient.getObjectClassHandle(_clientConverter->convertStringFromHla(objectClassName));
   }

   std::wstring RTIambassadorClientAdapter::getObjectClassName(const ObjectClassHandle & objectClass)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.getObjectClassName(objectClass);
   }

   ObjectClassHandle RTIambassadorClientAdapter::getKnownObjectClassHandle(const ObjectInstanceHandle & objectInstance)
   {
      if (!objectInstance.isValid()) {
         throw ObjectInstanceNotKnown(L"Invalid ObjectInstanceHandle");
      }
      return _rtiAmbassadorClient.getKnownObjectClassHandle(objectInstance);
   }

   ObjectInstanceHandle RTIambassadorClientAdapter::getObjectInstanceHandle(std::wstring const & objectInstanceName)
   {
      return _rtiAmbassadorClient.getObjectInstanceHandle(_clientConverter->convertStringFromHla(objectInstanceName));
   }

   std::wstring RTIambassadorClientAdapter::getObjectInstanceName(const ObjectInstanceHandle & objectInstance)
   {
      if (!objectInstance.isValid()) {
         throw ObjectInstanceNotKnown(L"Invalid ObjectInstanceHandle");
      }
      return _rtiAmbassadorClient.getObjectInstanceName(objectInstance);
   }

   AttributeHandle RTIambassadorClientAdapter::getAttributeHandle(
         const ObjectClassHandle & objectClass,
         std::wstring const & attributeName)
   {
      if (!objectClass.isValid()) {
         throw ObjectClassNotDefined(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.getAttributeHandle(
            objectClass, _clientConverter->convertStringFromHla(attributeName));
   }

   std::wstring RTIambassadorClientAdapter::getAttributeName(
         const ObjectClassHandle & objectClass,
         const AttributeHandle & attribute)
   {
      if (!objectClass.isValid()) {
         throw InvalidObjectClassHandle(L"Invalid ObjectClassHandle");
      }
      if (!attribute.isValid()) {
         throw InvalidAttributeHandle(L"Invalid AttributeHandle");
      }

      return _rtiAmbassadorClient.getAttributeName(objectClass, attribute);
   }

   double RTIambassadorClientAdapter::getUpdateRateValue(std::wstring const & updateRateDesignator)
   {
      return _rtiAmbassadorClient.getUpdateRateValue(_clientConverter->convertStringFromHla(updateRateDesignator));
   }

   double RTIambassadorClientAdapter::getUpdateRateValueForAttribute(
         const ObjectInstanceHandle & objectInstance,
         const AttributeHandle & attribute)
   {
      return _rtiAmbassadorClient.getUpdateRateValueForAttribute(objectInstance, attribute);
   }

   InteractionClassHandle RTIambassadorClientAdapter::getInteractionClassHandle(std::wstring const & interactionClassName)
   {
      return _rtiAmbassadorClient.getInteractionClassHandle(_clientConverter->convertStringFromHla(interactionClassName));
   }

   std::wstring RTIambassadorClientAdapter::getInteractionClassName(const InteractionClassHandle & interactionClass)
   {
      if (!interactionClass.isValid()) {
         throw InvalidInteractionClassHandle(L"Invalid InteractionClassHandle");
      }
      return _rtiAmbassadorClient.getInteractionClassName(interactionClass);
   }

   ParameterHandle RTIambassadorClientAdapter::getParameterHandle(
         const InteractionClassHandle & interactionClass,
         std::wstring const & parameterName)
   {
      if (!interactionClass.isValid()) {
         throw InvalidInteractionClassHandle(L"Invalid InteractionClassHandle");
      }
      return _rtiAmbassadorClient.getParameterHandle(
            interactionClass, _clientConverter->convertStringFromHla(parameterName));
   }

   std::wstring RTIambassadorClientAdapter::getParameterName(
         const InteractionClassHandle & interactionClass,
         const ParameterHandle & parameter)
   {
      if (!interactionClass.isValid()) {
         throw InvalidInteractionClassHandle(L"Invalid InteractionClassHandle");
      }
      if (!parameter.isValid()) {
         throw InvalidParameterHandle(L"Invalid ParameterHandle");
      }
      return _rtiAmbassadorClient.getParameterName(interactionClass, parameter);
   }

   OrderType RTIambassadorClientAdapter::getOrderType(std::wstring const & orderTypeName)
   {
      return _rtiAmbassadorClient.getOrderType(_clientConverter->convertStringFromHla(orderTypeName));
   }

   std::wstring RTIambassadorClientAdapter::getOrderName(OrderType orderType)
   {
      return _rtiAmbassadorClient.getOrderName(orderType);
   }

   TransportationTypeHandle RTIambassadorClientAdapter::getTransportationTypeHandle(std::wstring const & transportationTypeName)
   {
      return _rtiAmbassadorClient.getTransportationTypeHandle(
            _clientConverter->convertStringFromHla(
                  transportationTypeName));
   }

   std::wstring RTIambassadorClientAdapter::getTransportationTypeName(const TransportationTypeHandle & transportationType)
   {
      return _rtiAmbassadorClient.getTransportationTypeName(transportationType);
   }

   DimensionHandleSet RTIambassadorClientAdapter::getAvailableDimensionsForObjectClass(
         const ObjectClassHandle & objectClass)
   {
      if (!objectClass.isValid()) {
         throw InvalidObjectClassHandle(L"Invalid ObjectClassHandle");
      }
      return _rtiAmbassadorClient.getAvailableDimensionsForObjectClass(objectClass);
   }

   DimensionHandleSet RTIambassadorClientAdapter::getAvailableDimensionsForInteractionClass(const InteractionClassHandle & interactionClass)
   {
      if (!interactionClass.isValid()) {
         throw InvalidInteractionClassHandle(L"Invalid InteractionClassHandle");
      }
      return _rtiAmbassadorClient.getAvailableDimensionsForInteractionClass(interactionClass);
   }

   DimensionHandle RTIambassadorClientAdapter::getDimensionHandle(std::wstring const & dimensionName)
   {
      return _rtiAmbassadorClient.getDimensionHandle(_clientConverter->convertStringFromHla(dimensionName));
   }

   std::wstring RTIambassadorClientAdapter::getDimensionName(const DimensionHandle & dimension)
   {
      if (!dimension.isValid()) {
         throw InvalidDimensionHandle(L"Invalid DimensionHandle");
      }
      return _rtiAmbassadorClient.getDimensionName(dimension);
   }

   unsigned long RTIambassadorClientAdapter::getDimensionUpperBound(const DimensionHandle & dimension)
   {
      if (!dimension.isValid()) {
         throw InvalidDimensionHandle(L"Invalid DimensionHandle");
      }
      return _rtiAmbassadorClient.getDimensionUpperBound(dimension);
   }

   DimensionHandleSet RTIambassadorClientAdapter::getDimensionHandleSet(const RegionHandle & region)
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
         const RegionHandle & region,
         const DimensionHandle & dimension)
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
         const RegionHandle & region,
         const DimensionHandle & dimension,
         const RangeBounds & rangeBounds)
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

   unsigned long RTIambassadorClientAdapter::normalizeServiceGroup(ServiceGroup serviceGroup)
   {
      return _rtiAmbassadorClient.normalizeServiceGroup(serviceGroup);
   }

   unsigned long RTIambassadorClientAdapter::normalizeFederateHandle(const FederateHandle & federate)
   {
      if (!federate.isValid()) {
         throw InvalidFederateHandle(L"Invalid FederateHandle");
      }
      return _rtiAmbassadorClient.normalizeFederateHandle(federate);
   }

   unsigned long RTIambassadorClientAdapter::normalizeObjectClassHandle(const ObjectClassHandle & objectClass)
   {
      return _rtiAmbassadorClient.normalizeObjectClassHandle(objectClass);
   }

   unsigned long RTIambassadorClientAdapter::normalizeInteractionClassHandle(const InteractionClassHandle & interactionClass)
   {
      return _rtiAmbassadorClient.normalizeInteractionClassHandle(interactionClass);
   }

   unsigned long RTIambassadorClientAdapter::normalizeObjectInstanceHandle(const ObjectInstanceHandle & objectInstance)
   {
      return _rtiAmbassadorClient.normalizeObjectInstanceHandle(objectInstance);
   }

   bool RTIambassadorClientAdapter::getObjectClassRelevanceAdvisorySwitch() const
   {
      return _rtiAmbassadorClient.getObjectClassRelevanceAdvisorySwitch();
   }

   void RTIambassadorClientAdapter::setObjectClassRelevanceAdvisorySwitch(bool switchValue)
   {
      _rtiAmbassadorClient.setObjectClassRelevanceAdvisorySwitch(switchValue);
   }

   bool RTIambassadorClientAdapter::getAttributeRelevanceAdvisorySwitch() const
   {
      return _rtiAmbassadorClient.getAttributeRelevanceAdvisorySwitch();
   }

   void RTIambassadorClientAdapter::setAttributeRelevanceAdvisorySwitch(bool switchValue)
   {
      _rtiAmbassadorClient.setAttributeRelevanceAdvisorySwitch(switchValue);
   }

   bool RTIambassadorClientAdapter::getAttributeScopeAdvisorySwitch() const
   {
      return _rtiAmbassadorClient.getAttributeScopeAdvisorySwitch();
   }

   void RTIambassadorClientAdapter::setAttributeScopeAdvisorySwitch(bool switchValue)
   {
      _rtiAmbassadorClient.setAttributeScopeAdvisorySwitch(switchValue);
   }

   bool RTIambassadorClientAdapter::getInteractionRelevanceAdvisorySwitch() const
   {
      return _rtiAmbassadorClient.getInteractionRelevanceAdvisorySwitch();
   }

   void RTIambassadorClientAdapter::setInteractionRelevanceAdvisorySwitch(bool switchValue)
   {
      _rtiAmbassadorClient.setInteractionRelevanceAdvisorySwitch(switchValue);
   }

   bool RTIambassadorClientAdapter::getConveyRegionDesignatorSetsSwitch() const
   {
      return _rtiAmbassadorClient.getConveyRegionDesignatorSetsSwitch();
   }

   void RTIambassadorClientAdapter::setConveyRegionDesignatorSetsSwitch(bool switchValue)
   {
      _rtiAmbassadorClient.setConveyRegionDesignatorSetsSwitch(switchValue);
   }

   ResignAction RTIambassadorClientAdapter::getAutomaticResignDirective()
   {
      return _rtiAmbassadorClient.getAutomaticResignDirective();
   }

   void RTIambassadorClientAdapter::setAutomaticResignDirective(ResignAction resignAction)
   {
      _rtiAmbassadorClient.setAutomaticResignDirective(resignAction);
   }

   bool RTIambassadorClientAdapter::getServiceReportingSwitch() const
   {
      return _rtiAmbassadorClient.getServiceReportingSwitch();
   }

   void RTIambassadorClientAdapter::setServiceReportingSwitch(bool switchValue)
   {
      _rtiAmbassadorClient.setServiceReportingSwitch(switchValue);
   }

   bool RTIambassadorClientAdapter::getExceptionReportingSwitch() const
   {
      return _rtiAmbassadorClient.getExceptionReportingSwitch();
   }

   void RTIambassadorClientAdapter::setExceptionReportingSwitch(bool switchValue)
   {
      _rtiAmbassadorClient.setExceptionReportingSwitch(switchValue);
   }

   bool RTIambassadorClientAdapter::getSendServiceReportsToFileSwitch() const
   {
      return _rtiAmbassadorClient.getSendServiceReportsToFileSwitch();
   }

   void RTIambassadorClientAdapter::setSendServiceReportsToFileSwitch(bool switchValue)
   {
      _rtiAmbassadorClient.setSendServiceReportsToFileSwitch(switchValue);
   }

   bool RTIambassadorClientAdapter::getAutoProvideSwitch() const
   {
      return _rtiAmbassadorClient.getAutoProvideSwitch();
   }

   bool RTIambassadorClientAdapter::getDelaySubscriptionEvaluationSwitch() const
   {
      return _rtiAmbassadorClient.getDelaySubscriptionEvaluationSwitch();
   }

   bool RTIambassadorClientAdapter::getAdvisoriesUseKnownClassSwitch() const
   {
      return _rtiAmbassadorClient.getAdvisoriesUseKnownClassSwitch();
   }

   bool RTIambassadorClientAdapter::getAllowRelaxedDDMSwitch() const
   {
      return _rtiAmbassadorClient.getAllowRelaxedDDMSwitch();
   }

   bool RTIambassadorClientAdapter::getNonRegulatedGrantSwitch() const
   {
      return _rtiAmbassadorClient.getNonRegulatedGrantSwitch();
   }

   bool RTIambassadorClientAdapter::evokeCallback(
         double approximateMinimumTimeInSeconds)
   {
      return _rtiAmbassadorClient.evokeCallback(approximateMinimumTimeInSeconds);
   }

   bool RTIambassadorClientAdapter::evokeMultipleCallbacks(
         double approximateMinimumTimeInSeconds,
         double approximateMaximumTimeInSeconds)
   {
      return _rtiAmbassadorClient.evokeMultipleCallbacks(
            approximateMinimumTimeInSeconds, approximateMaximumTimeInSeconds);
   }

   void RTIambassadorClientAdapter::enableCallbacks()
   {
      _rtiAmbassadorClient.enableCallbacks();
   }

   void RTIambassadorClientAdapter::disableCallbacks()
   {
      _rtiAmbassadorClient.disableCallbacks();
   }

   std::unique_ptr<LogicalTimeFactory> RTIambassadorClientAdapter::getTimeFactory() const
   {
      if (_timeFactory) {
         return LogicalTimeFactoryFactory::makeLogicalTimeFactory(_timeFactory->getName());
      } else {
         throw FederateNotExecutionMember(L"No time factory available");
      }
   }

   FederateHandle RTIambassadorClientAdapter::decodeFederateHandle(const VariableLengthData & encodedValue) const
   {
      return make_handle<FederateHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   ObjectClassHandle RTIambassadorClientAdapter::decodeObjectClassHandle(const VariableLengthData & encodedValue) const
   {
      return make_handle<ObjectClassHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   InteractionClassHandle RTIambassadorClientAdapter::decodeInteractionClassHandle(const VariableLengthData & encodedValue) const
   {
      return make_handle<InteractionClassHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   ObjectInstanceHandle RTIambassadorClientAdapter::decodeObjectInstanceHandle(const VariableLengthData & encodedValue) const
   {
      return make_handle<ObjectInstanceHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   AttributeHandle RTIambassadorClientAdapter::decodeAttributeHandle(const VariableLengthData & encodedValue) const
   {
      return make_handle<AttributeHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   ParameterHandle RTIambassadorClientAdapter::decodeParameterHandle(const VariableLengthData & encodedValue) const
   {
      return make_handle<ParameterHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   DimensionHandle RTIambassadorClientAdapter::decodeDimensionHandle(const VariableLengthData & encodedValue) const
   {
      return make_handle<DimensionHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   MessageRetractionHandle RTIambassadorClientAdapter::decodeMessageRetractionHandle(const VariableLengthData & encodedValue) const
   {
      return make_handle<MessageRetractionHandle>(_clientConverter->convertFromHla(encodedValue));
   }

   RegionHandle RTIambassadorClientAdapter::decodeRegionHandle(const VariableLengthData & encodedValue) const
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

   std::unique_ptr<rti1516_202X::fedpro::RtiConfiguration> RTIambassadorClientAdapter::parseRtiConfiguration(
         const RTI_NAMESPACE::RtiConfiguration & rtiConfiguration,
         const std::vector<std::wstring> & inputValueList)
   {
      // No need to set the rtiAddress since an LRC may specify the CRC address through the additional settings field
      // of the RtiConfiguration object, which is done in this method.
      return std::unique_ptr<rti1516_202X::fedpro::RtiConfiguration>(
            _clientConverter->convertFromHla(
                  RTI_NAMESPACE::RtiConfiguration::createConfiguration().withConfigurationName(
                        rtiConfiguration.configurationName()).withAdditionalSettings(
                        RTIambassadorClientGenericBase::toAdditionalSettingsString(inputValueList))));
   }

   void RTIambassadorClientAdapter::addServerAddressToList(
         std::vector<std::wstring> & settingsList,
         const RTI_NAMESPACE::RtiConfiguration & rtiConfiguration)
   {
      const std::wstring & serverAddress = rtiConfiguration.rtiAddress();
      std::vector<wstring_view> hostAndPort = splitString(serverAddress, L':', 2);
      if (!hostAndPort[0].empty()) {
         settingsList.emplace_back(SETTING_PREFIX_WIDE_STRING + toWString(SETTING_NAME_CONNECTION_HOST) + L"=" + hostAndPort[0]);
      }
      if (hostAndPort.size() > 1 && !hostAndPort[1].empty()) {
         settingsList.emplace_back(SETTING_PREFIX_WIDE_STRING + toWString(SETTING_NAME_CONNECTION_PORT) + L"=" + hostAndPort[1]);
      }
   }

} // RTI_NAMESPACE

// NOLINTEND(hicpp-exception-baseclass)
