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

import hla.rti1516e.*;
import hla.rti1516e.exceptions.*;
import hla.rti1516e.time.HLAfloat64Interval;
import hla.rti1516e.time.HLAfloat64Time;
import hla.rti1516e.time.HLAinteger64Interval;
import hla.rti1516e.time.HLAinteger64Time;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class RTIambassadorClientAdapter implements RTIambassador {

   private final RTIambassadorClient _rtiAmbassadorClient;
   private LogicalTimeFactory _timeFactory;
   private final ClientConverter _clientConverter;

   public RTIambassadorClientAdapter()
   {
      _clientConverter = new ClientConverter();
      _rtiAmbassadorClient = new RTIambassadorClient(_clientConverter);
   }

   public void connect(
         FederateAmbassador federateReference,
         CallbackModel callbackModel,
         String localSettingsDesignator)
   throws
         ConnectionFailed,
         InvalidLocalSettingsDesignator,
         UnsupportedCallbackModel,
         AlreadyConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      checkCallNotAllowedFromWithinCallback();
      _rtiAmbassadorClient.connect(federateReference, callbackModel, localSettingsDesignator);
   }

   public void connect(FederateAmbassador federateReference, CallbackModel callbackModel)
   throws
         ConnectionFailed,
         InvalidLocalSettingsDesignator,
         UnsupportedCallbackModel,
         AlreadyConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      checkCallNotAllowedFromWithinCallback();
      _rtiAmbassadorClient.connect(federateReference, callbackModel, "");
   }

   public void disconnect()
   throws
         FederateIsExecutionMember,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      checkCallNotAllowedFromWithinCallback();
      _rtiAmbassadorClient.disconnect();
   }

   private void checkCallNotAllowedFromWithinCallback()
   throws
         CallNotAllowedFromWithinCallback
   {
      if (_rtiAmbassadorClient.isInCurrentCallbackThread()) {
         throw new CallNotAllowedFromWithinCallback("Call not allowed from within callback");
      }
   }

   public void createFederationExecution(
         String federationExecutionName,
         URL[] fomModules,
         URL mimModule,
         String logicalTimeImplementationName)
   throws
         CouldNotCreateLogicalTimeFactory,
         InconsistentFDD,
         ErrorReadingFDD,
         CouldNotOpenFDD,
         ErrorReadingMIM,
         CouldNotOpenMIM,
         DesignatorIsHLAstandardMIM,
         FederationExecutionAlreadyExists,
         NotConnected,
         RTIinternalError
   {
      try {
         FomModuleSet fomModuleSet = getFomModuleSet(fomModules);
         _rtiAmbassadorClient.createFederationExecutionWithMIM(
               federationExecutionName, fomModuleSet, getFomModule(mimModule), logicalTimeImplementationName);
      } catch (RTIexception e) {
         // HLA 4 exception Unauthorized is converted to Evolved ConnectionFailed.
         // This is fine for the connect call but not here, so we convert to RTIinternalError.
         //noinspection ConstantValue
         if (e instanceof ConnectionFailed) {
            throw new RTIinternalError(e.getMessage());
         } else {
            throw e;
         }
      }
   }

   public void createFederationExecution(
         String federationExecutionName,
         URL[] fomModules,
         String logicalTimeImplementationName)
   throws
         CouldNotCreateLogicalTimeFactory,
         InconsistentFDD,
         ErrorReadingFDD,
         CouldNotOpenFDD,
         FederationExecutionAlreadyExists,
         NotConnected,
         RTIinternalError
   {
      try {
         FomModuleSet fomModuleSet = getFomModuleSet(fomModules);
         _rtiAmbassadorClient.createFederationExecutionWithModules(
               federationExecutionName,
               fomModuleSet,
               logicalTimeImplementationName);
      } catch (RTIexception e) {
         // HLA 4 exception Unauthorized is converted to Evolved ConnectionFailed.
         // This is fine for the connect call but not here, so we convert to RTIinternalError.
         //noinspection ConstantValue
         if (e instanceof ConnectionFailed) {
            throw new RTIinternalError(e.getMessage());
         } else {
            throw e;
         }
      }
   }

   private FomModuleSet getFomModuleSet(URL[] fomModules)
   {
      FomModuleSet result = new FomModuleSet();
      for (URL fomModule : fomModules) {
         result.add(getFomModule(fomModule));
      }
      return result;
   }

   private FomModule getFomModule(URL fomModule)
   {
      FomModule module = new FomModule();
      if ("file".equalsIgnoreCase(fomModule.getProtocol())) {
         try {
            File file = new File(fomModule.toURI());
            if (file.length() > 1024) {
               module.setCompressedModule(zip(file));
               return module;
            } else {
               module.setFileNameAndContent(file.getName(), Files.readAllBytes(file.toPath()));
               return module;
            }
         } catch (IllegalArgumentException | URISyntaxException | IOException ignore) {
            // fallback to url
         }
      }
      module.setUrl(fomModule.toString());
      return module;
   }

   private static byte[] zip(File file)
   throws
         IOException
   {
      ByteArrayOutputStream result = new ByteArrayOutputStream();

      ZipOutputStream zip = new ZipOutputStream(result);

      zip.putNextEntry(new ZipEntry(file.getName()));
      Files.copy(file.toPath(), zip);
      zip.closeEntry();

      zip.close();

      return result.toByteArray();
   }

   public void createFederationExecution(String federationExecutionName, URL[] fomModules, URL mimModule)
   throws
         InconsistentFDD,
         ErrorReadingFDD,
         CouldNotOpenFDD,
         ErrorReadingMIM,
         CouldNotOpenMIM,
         DesignatorIsHLAstandardMIM,
         FederationExecutionAlreadyExists,
         NotConnected,
         RTIinternalError
   {
      try {
         FomModuleSet fomModuleSet = getFomModuleSet(fomModules);
         _rtiAmbassadorClient.createFederationExecutionWithMIM(
               federationExecutionName,
               fomModuleSet,
               getFomModule(mimModule),
               "HLAfloat64Time");
      } catch (CouldNotCreateLogicalTimeFactory e) {
         throw new RTIinternalError("Failed to create default time HLAfloat64Time");
      } catch (RTIexception e) {
         // HLA 4 exception Unauthorized is converted to Evolved ConnectionFailed.
         // This is fine for the connect call but not here, so we convert to RTIinternalError.
         //noinspection ConstantValue
         if (e instanceof ConnectionFailed) {
            throw new RTIinternalError(e.getMessage());
         } else {
            throw e;
         }
      }
   }

   public void createFederationExecution(String federationExecutionName, URL[] fomModules)
   throws
         InconsistentFDD,
         ErrorReadingFDD,
         CouldNotOpenFDD,
         FederationExecutionAlreadyExists,
         NotConnected,
         RTIinternalError
   {
      try {
         FomModuleSet fomModuleSet = getFomModuleSet(fomModules);
         _rtiAmbassadorClient.createFederationExecutionWithModules(
               federationExecutionName,
               fomModuleSet,
               "HLAfloat64Time");
      } catch (CouldNotCreateLogicalTimeFactory e) {
         throw new RTIinternalError("Failed to create default time HLAfloat64Time");
      } catch (RTIexception e) {
         // HLA 4 exception Unauthorized is converted to Evolved ConnectionFailed.
         // This is fine for the connect call but not here, so we convert to RTIinternalError.
         //noinspection ConstantValue
         if (e instanceof ConnectionFailed) {
            throw new RTIinternalError(e.getMessage());
         } else {
            throw e;
         }
      }
   }

   public void createFederationExecution(String federationExecutionName, URL fomModule)
   throws
         InconsistentFDD,
         ErrorReadingFDD,
         CouldNotOpenFDD,
         FederationExecutionAlreadyExists,
         NotConnected,
         RTIinternalError
   {
      try {
         _rtiAmbassadorClient.createFederationExecution(
               federationExecutionName,
               getFomModule(fomModule),
               "HLAfloat64Time");
      } catch (CouldNotCreateLogicalTimeFactory e) {
         throw new RTIinternalError("Failed to create default time HLAfloat64Time");
      } catch (RTIexception e) {
         // HLA 4 exception Unauthorized is converted to Evolved ConnectionFailed.
         // This is fine for the connect call but not here, so we convert to RTIinternalError.
         //noinspection ConstantValue
         if (e instanceof ConnectionFailed) {
            throw new RTIinternalError(e.getMessage());
         } else {
            throw e;
         }
      }
   }

   public void destroyFederationExecution(String federationExecutionName)
   throws
         FederatesCurrentlyJoined,
         FederationExecutionDoesNotExist,
         NotConnected,
         RTIinternalError
   {
      try {
         _rtiAmbassadorClient.destroyFederationExecution(federationExecutionName);
      } catch (RTIexception e) {
         // HLA 4 exception Unauthorized is converted to Evolved ConnectionFailed.
         // This is fine for the connect call but not here, so we convert to RTIinternalError.
         //noinspection ConstantValue
         if (e instanceof ConnectionFailed) {
            throw new RTIinternalError(e.getMessage());
         } else {
            throw e;
         }
      }
   }

   public void listFederationExecutions()
   throws
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.listFederationExecutions();
   }

   public FederateHandle joinFederationExecution(
         String federateName,
         String federateType,
         String federationExecutionName,
         URL[] additionalFomModules)
   throws
         CouldNotCreateLogicalTimeFactory,
         FederateNameAlreadyInUse,
         FederationExecutionDoesNotExist,
         InconsistentFDD,
         ErrorReadingFDD,
         CouldNotOpenFDD,
         SaveInProgress,
         RestoreInProgress,
         FederateAlreadyExecutionMember,
         NotConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      try {
         checkCallNotAllowedFromWithinCallback();
         FomModuleSet fomModuleSet = getFomModuleSet(additionalFomModules);
         JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecutionWithNameAndModules(federateName,
               federateType,
               federationExecutionName,
               fomModuleSet);
         FederateHandle federateHandle = joinResult.getFederateHandle();
         String timeFactoryName = joinResult.getLogicalTimeImplementationName();
         _timeFactory = LogicalTimeFactoryFactory.getLogicalTimeFactory(timeFactoryName);
         _clientConverter.setTimeFactory(_timeFactory);
         return federateHandle;
      } catch (RTIexception e) {
         // HLA 4 exception Unauthorized is converted to Evolved ConnectionFailed.
         // This is fine for the connect call but not here, so we convert to RTIinternalError.
         //noinspection ConstantValue
         if (e instanceof ConnectionFailed) {
            throw new RTIinternalError(e.getMessage());
         } else {
            throw e;
         }
      }
   }

   public FederateHandle joinFederationExecution(
         String federateType,
         String federationExecutionName,
         URL[] additionalFomModules)
   throws
         CouldNotCreateLogicalTimeFactory,
         FederationExecutionDoesNotExist,
         InconsistentFDD,
         ErrorReadingFDD,
         CouldNotOpenFDD,
         SaveInProgress,
         RestoreInProgress,
         FederateAlreadyExecutionMember,
         NotConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      try {
         checkCallNotAllowedFromWithinCallback();
         FomModuleSet fomModuleSet = getFomModuleSet(additionalFomModules);
         JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecutionWithModules(federateType,
               federationExecutionName,
               fomModuleSet);
         FederateHandle federateHandle = joinResult.getFederateHandle();
         String timeFactoryName = joinResult.getLogicalTimeImplementationName();
         _timeFactory = LogicalTimeFactoryFactory.getLogicalTimeFactory(timeFactoryName);
         _clientConverter.setTimeFactory(_timeFactory);
         return federateHandle;
      } catch (RTIexception e) {
         // HLA 4 exception Unauthorized is converted to Evolved ConnectionFailed.
         // This is fine for the connect call but not here, so we convert to RTIinternalError.
         //noinspection ConstantValue
         if (e instanceof ConnectionFailed) {
            throw new RTIinternalError(e.getMessage());
         } else {
            throw e;
         }
      }
   }

   public FederateHandle joinFederationExecution(
         String federateName,
         String federateType,
         String federationExecutionName)
   throws
         CouldNotCreateLogicalTimeFactory,
         FederateNameAlreadyInUse,
         FederationExecutionDoesNotExist,
         SaveInProgress,
         RestoreInProgress,
         FederateAlreadyExecutionMember,
         NotConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      try {
         checkCallNotAllowedFromWithinCallback();
         // FIXME join throws incorrect exceptions
         JoinResult joinResult =
               _rtiAmbassadorClient.joinFederationExecutionWithName(federateName, federateType, federationExecutionName);
         FederateHandle federateHandle = joinResult.getFederateHandle();
         String timeFactoryName = joinResult.getLogicalTimeImplementationName();
         _timeFactory = LogicalTimeFactoryFactory.getLogicalTimeFactory(timeFactoryName);
         _clientConverter.setTimeFactory(_timeFactory);
         return federateHandle;
      } catch (RTIexception e) {
         // HLA 4 exception Unauthorized is converted to Evolved ConnectionFailed.
         // This is fine for the connect call but not here, so we convert to RTIinternalError.
         //noinspection ConstantValue
         if (e instanceof ConnectionFailed) {
            throw new RTIinternalError(e.getMessage());
         } else {
            throw e;
         }
      }
   }

   public FederateHandle joinFederationExecution(
         String federateType,
         String federationExecutionName)
   throws
         CouldNotCreateLogicalTimeFactory,
         FederationExecutionDoesNotExist,
         SaveInProgress,
         RestoreInProgress,
         FederateAlreadyExecutionMember,
         NotConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      try {
         checkCallNotAllowedFromWithinCallback();
         // FIXME join throws incorrect exceptions
         JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecution(federateType, federationExecutionName);
         FederateHandle federateHandle = joinResult.getFederateHandle();
         String timeFactoryName = joinResult.getLogicalTimeImplementationName();
         _timeFactory = LogicalTimeFactoryFactory.getLogicalTimeFactory(timeFactoryName);
         _clientConverter.setTimeFactory(_timeFactory);
         return federateHandle;
      } catch (RTIexception e) {
         // HLA 4 exception Unauthorized is converted to Evolved ConnectionFailed.
         // This is fine for the connect call but not here, so we convert to RTIinternalError.
         //noinspection ConstantValue
         if (e instanceof ConnectionFailed) {
            throw new RTIinternalError(e.getMessage());
         } else {
            throw e;
         }
      }
   }

   public void resignFederationExecution(ResignAction resignAction)
   throws
         InvalidResignAction,
         OwnershipAcquisitionPending,
         FederateOwnsAttributes,
         FederateNotExecutionMember,
         NotConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      checkCallNotAllowedFromWithinCallback();
      _rtiAmbassadorClient.resignFederationExecution(resignAction);
      // Let's leave the time factories assigned since we can get late callbacks.
      //_timeFactory = null;
      //_clientConverter.setTimeFactory(null);
   }

   public void registerFederationSynchronizationPoint(String synchronizationPointLabel, byte[] userSuppliedTag)
   throws
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.registerFederationSynchronizationPoint(synchronizationPointLabel, userSuppliedTag);
   }

   public void registerFederationSynchronizationPoint(
         String synchronizationPointLabel,
         byte[] userSuppliedTag,
         FederateHandleSet synchronizationSet)
   throws
         InvalidFederateHandle,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.registerFederationSynchronizationPointWithSet(
            synchronizationPointLabel,
            userSuppliedTag,
            synchronizationSet);
   }

   public void synchronizationPointAchieved(String synchronizationPointLabel)
   throws
         SynchronizationPointLabelNotAnnounced,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.synchronizationPointAchieved(synchronizationPointLabel, true);
   }

   public void synchronizationPointAchieved(String synchronizationPointLabel, boolean successIndicator)
   throws
         SynchronizationPointLabelNotAnnounced,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.synchronizationPointAchieved(synchronizationPointLabel, successIndicator);
   }

   public void requestFederationSave(String label)
   throws
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.requestFederationSave(label);
   }

   public void requestFederationSave(String label, LogicalTime theTime)
   throws
         LogicalTimeAlreadyPassed,
         InvalidLogicalTime,
         FederateUnableToUseTime,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.requestFederationSaveWithTime(label, theTime);
   }

   public void federateSaveBegun()
   throws
         SaveNotInitiated,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.federateSaveBegun();
   }

   public void federateSaveComplete()
   throws
         FederateHasNotBegunSave,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.federateSaveComplete();
   }

   public void federateSaveNotComplete()
   throws
         FederateHasNotBegunSave,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.federateSaveNotComplete();
   }

   public void abortFederationSave()
   throws
         SaveNotInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.abortFederationSave();
   }

   public void queryFederationSaveStatus()
   throws
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.queryFederationSaveStatus();
   }

   public void requestFederationRestore(String label)
   throws
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.requestFederationRestore(label);
   }

   public void federateRestoreComplete()
   throws
         RestoreNotRequested,
         SaveInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.federateRestoreComplete();
   }

   public void federateRestoreNotComplete()
   throws
         RestoreNotRequested,
         SaveInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.federateRestoreNotComplete();
   }

   public void abortFederationRestore()
   throws
         RestoreNotInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.abortFederationRestore();
   }

   public void queryFederationRestoreStatus()
   throws
         SaveInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.queryFederationRestoreStatus();
   }

   public void publishObjectClassAttributes(
         ObjectClassHandle theClass,
         AttributeHandleSet attributeList)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.publishObjectClassAttributes(theClass, attributeList);
   }

   public void unpublishObjectClass(ObjectClassHandle theClass)
   throws
         OwnershipAcquisitionPending,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unpublishObjectClass(theClass);
   }

   public void unpublishObjectClassAttributes(
         ObjectClassHandle theClass,
         AttributeHandleSet attributeList)
   throws
         OwnershipAcquisitionPending,
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unpublishObjectClassAttributes(theClass, attributeList);
   }

   public void publishInteractionClass(InteractionClassHandle theInteraction)
   throws
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theInteraction == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.publishInteractionClass(theInteraction);
   }

   public void unpublishInteractionClass(InteractionClassHandle theInteraction)
   throws
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theInteraction == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.unpublishInteractionClass(theInteraction);
   }

   public void subscribeObjectClassAttributes(
         ObjectClassHandle theClass,
         AttributeHandleSet attributeList)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributes(theClass, attributeList);
   }

   public void subscribeObjectClassAttributes(
         ObjectClassHandle theClass,
         AttributeHandleSet attributeList,
         String updateRateDesignator)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         InvalidUpdateRateDesignator,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRate(theClass, attributeList, updateRateDesignator);
   }

   public void subscribeObjectClassAttributesPassively(
         ObjectClassHandle theClass,
         AttributeHandleSet attributeList)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesPassively(theClass, attributeList);
   }

   public void subscribeObjectClassAttributesPassively(
         ObjectClassHandle theClass,
         AttributeHandleSet attributeList,
         String updateRateDesignator)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         InvalidUpdateRateDesignator,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesPassivelyWithRate(
            theClass,
            attributeList,
            updateRateDesignator);
   }

   public void unsubscribeObjectClass(ObjectClassHandle theClass)
   throws
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeObjectClass(theClass);
   }

   public void unsubscribeObjectClassAttributes(
         ObjectClassHandle theClass,
         AttributeHandleSet attributeList)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeObjectClassAttributes(theClass, attributeList);
   }

   public void subscribeInteractionClass(InteractionClassHandle theClass)
   throws
         FederateServiceInvocationsAreBeingReportedViaMOM,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeInteractionClass(theClass);
   }

   public void subscribeInteractionClassPassively(InteractionClassHandle theClass)
   throws
         FederateServiceInvocationsAreBeingReportedViaMOM,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeInteractionClassPassively(theClass);
   }

   public void unsubscribeInteractionClass(InteractionClassHandle theClass)
   throws
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeInteractionClass(theClass);
   }

   public void reserveObjectInstanceName(String theObjectName)
   throws
         IllegalName,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.reserveObjectInstanceName(theObjectName);
   }

   public void releaseObjectInstanceName(String theObjectInstanceName)
   throws
         ObjectInstanceNameNotReserved,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.releaseObjectInstanceName(theObjectInstanceName);
   }

   public void reserveMultipleObjectInstanceName(Set<String> theObjectNames)
   throws
         IllegalName,
         NameSetWasEmpty,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.reserveMultipleObjectInstanceName(theObjectNames);
   }

   public void releaseMultipleObjectInstanceName(Set<String> theObjectNames)
   throws
         ObjectInstanceNameNotReserved,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.releaseMultipleObjectInstanceName(theObjectNames);
   }

   public ObjectInstanceHandle registerObjectInstance(ObjectClassHandle theClass)
   throws
         ObjectClassNotPublished,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      return _rtiAmbassadorClient.registerObjectInstance(theClass);
   }

   public ObjectInstanceHandle registerObjectInstance(
         ObjectClassHandle theClass,
         String theObjectName)
   throws
         ObjectInstanceNameInUse,
         ObjectInstanceNameNotReserved,
         ObjectClassNotPublished,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithName(theClass, theObjectName);
   }

   public void updateAttributeValues(
         ObjectInstanceHandle theObject,
         AttributeHandleValueMap theAttributes, byte[] userSuppliedTag)
   throws
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.updateAttributeValues(theObject, theAttributes, userSuppliedTag);
   }

   public MessageRetractionReturn updateAttributeValues(
         ObjectInstanceHandle theObject,
         AttributeHandleValueMap theAttributes,
         byte[] userSuppliedTag,
         LogicalTime theTime)
   throws
         InvalidLogicalTime,
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.updateAttributeValuesWithTime(theObject, theAttributes, userSuppliedTag, theTime);
   }

   public void sendInteraction(
         InteractionClassHandle theInteraction,
         ParameterHandleValueMap theParameters, byte[] userSuppliedTag)
   throws
         InteractionClassNotPublished,
         InteractionParameterNotDefined,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theInteraction == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.sendInteraction(theInteraction, theParameters, userSuppliedTag);
   }

   public MessageRetractionReturn sendInteraction(
         InteractionClassHandle theInteraction,
         ParameterHandleValueMap theParameters,
         byte[] userSuppliedTag, LogicalTime theTime)
   throws
         InvalidLogicalTime,
         InteractionClassNotPublished,
         InteractionParameterNotDefined,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theInteraction == null) {
         throw new InteractionClassNotDefined("null");
      }
      return _rtiAmbassadorClient.sendInteractionWithTime(theInteraction, theParameters, userSuppliedTag, theTime);
   }

   public void deleteObjectInstance(ObjectInstanceHandle objectHandle, byte[] userSuppliedTag)
   throws
         DeletePrivilegeNotHeld,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.deleteObjectInstance(objectHandle, userSuppliedTag);
   }

   public MessageRetractionReturn deleteObjectInstance(
         ObjectInstanceHandle objectHandle,
         byte[] userSuppliedTag,
         LogicalTime theTime)
   throws
         InvalidLogicalTime,
         DeletePrivilegeNotHeld,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.deleteObjectInstanceWithTime(objectHandle, userSuppliedTag, theTime);
   }

   public void localDeleteObjectInstance(ObjectInstanceHandle objectHandle)
   throws
         OwnershipAcquisitionPending,
         FederateOwnsAttributes,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.localDeleteObjectInstance(objectHandle);
   }

   public void requestAttributeValueUpdate(
         ObjectInstanceHandle theObject,
         AttributeHandleSet theAttributes, byte[] userSuppliedTag)
   throws
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.requestInstanceAttributeValueUpdate(theObject, theAttributes, userSuppliedTag);
   }

   public void requestAttributeValueUpdate(
         ObjectClassHandle theClass,
         AttributeHandleSet theAttributes, byte[] userSuppliedTag)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.requestClassAttributeValueUpdate(theClass, theAttributes, userSuppliedTag);
   }

   public void requestAttributeTransportationTypeChange(
         ObjectInstanceHandle theObject,
         AttributeHandleSet theAttributes,
         TransportationTypeHandle theType)
   throws
         AttributeAlreadyBeingChanged,
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         InvalidTransportationType,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.requestAttributeTransportationTypeChange(theObject, theAttributes, theType);
   }

   public void queryAttributeTransportationType(
         ObjectInstanceHandle theObject,
         AttributeHandle theAttribute)
   throws
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.queryAttributeTransportationType(theObject, theAttribute);
   }

   public void requestInteractionTransportationTypeChange(
         InteractionClassHandle theClass,
         TransportationTypeHandle theType)
   throws
         InteractionClassAlreadyBeingChanged,
         InteractionClassNotPublished,
         InteractionClassNotDefined,
         InvalidTransportationType,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.requestInteractionTransportationTypeChange(theClass, theType);
   }

   public void queryInteractionTransportationType(
         FederateHandle theFederate,
         InteractionClassHandle theInteraction)
   throws
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.queryInteractionTransportationType(theFederate, theInteraction);
   }

   public void unconditionalAttributeOwnershipDivestiture(
         ObjectInstanceHandle theObject,
         AttributeHandleSet theAttributes)
   throws
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.unconditionalAttributeOwnershipDivestiture(theObject, theAttributes);
   }

   public void negotiatedAttributeOwnershipDivestiture(
         ObjectInstanceHandle theObject,
         AttributeHandleSet theAttributes,
         byte[] userSuppliedTag)
   throws
         AttributeAlreadyBeingDivested,
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.negotiatedAttributeOwnershipDivestiture(theObject, theAttributes, userSuppliedTag);
   }

   public void confirmDivestiture(
         ObjectInstanceHandle theObject,
         AttributeHandleSet theAttributes, byte[] userSuppliedTag)
   throws
         NoAcquisitionPending,
         AttributeDivestitureWasNotRequested,
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.confirmDivestiture(theObject, theAttributes, userSuppliedTag);
   }

   public void attributeOwnershipAcquisition(
         ObjectInstanceHandle theObject,
         AttributeHandleSet desiredAttributes, byte[] userSuppliedTag)
   throws
         AttributeNotPublished,
         ObjectClassNotPublished,
         FederateOwnsAttributes,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (desiredAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.attributeOwnershipAcquisition(theObject, desiredAttributes, userSuppliedTag);
   }

   public void attributeOwnershipAcquisitionIfAvailable(
         ObjectInstanceHandle theObject,
         AttributeHandleSet desiredAttributes)
   throws
         AttributeAlreadyBeingAcquired,
         AttributeNotPublished,
         ObjectClassNotPublished,
         FederateOwnsAttributes,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (desiredAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.attributeOwnershipAcquisitionIfAvailable(theObject, desiredAttributes);
   }

   public void attributeOwnershipReleaseDenied(
         ObjectInstanceHandle theObject,
         AttributeHandleSet theAttributes)
   throws
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.attributeOwnershipReleaseDenied(theObject, theAttributes);
   }

   public AttributeHandleSet attributeOwnershipDivestitureIfWanted(
         ObjectInstanceHandle theObject,
         AttributeHandleSet theAttributes)
   throws
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      return _rtiAmbassadorClient.attributeOwnershipDivestitureIfWanted(theObject, theAttributes);
   }

   public void cancelNegotiatedAttributeOwnershipDivestiture(
         ObjectInstanceHandle theObject,
         AttributeHandleSet theAttributes)
   throws
         AttributeDivestitureWasNotRequested,
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.cancelNegotiatedAttributeOwnershipDivestiture(theObject, theAttributes);
   }

   public void cancelAttributeOwnershipAcquisition(
         ObjectInstanceHandle theObject,
         AttributeHandleSet theAttributes)
   throws
         AttributeAcquisitionWasNotRequested,
         AttributeAlreadyOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.cancelAttributeOwnershipAcquisition(theObject, theAttributes);
   }

   public void queryAttributeOwnership(
         ObjectInstanceHandle theObject,
         AttributeHandle theAttribute)
   throws
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.queryAttributeOwnership(theObject, theAttribute);
   }

   public boolean isAttributeOwnedByFederate(
         ObjectInstanceHandle theObject,
         AttributeHandle theAttribute)
   throws
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.isAttributeOwnedByFederate(theObject, theAttribute);
   }

   public void enableTimeRegulation(LogicalTimeInterval theLookahead)
   throws
         InvalidLookahead,
         InTimeAdvancingState,
         RequestForTimeRegulationPending,
         TimeRegulationAlreadyEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      checkIntervalType(theLookahead);
      _rtiAmbassadorClient.enableTimeRegulation(theLookahead);
   }

   private void checkIntervalType(LogicalTimeInterval theLookahead)
   throws
         InvalidLookahead
   {
      // Types match if they implement the same basic time type, e.g. HLAinteger64Time.
      LogicalTimeInterval logicalTimeInterval = _timeFactory.makeEpsilon();
      if (theLookahead instanceof HLAfloat64Interval && logicalTimeInterval instanceof HLAfloat64Interval) {
         return;
      }
      if (theLookahead instanceof HLAinteger64Interval && logicalTimeInterval instanceof HLAinteger64Interval) {
         return;
      }
      boolean sameClass = logicalTimeInterval.getClass().equals(theLookahead.getClass());
      if (!sameClass) {
         throw new InvalidLookahead("wrong time type");
      }
   }

   private void checkTimeType(LogicalTime theTime)
   throws
         InvalidLogicalTime
   {
      LogicalTime logicalTime = _timeFactory.makeInitial();
      if (theTime instanceof HLAfloat64Time && logicalTime instanceof HLAfloat64Time) {
         return;
      }
      if (theTime instanceof HLAinteger64Time && logicalTime instanceof HLAinteger64Time) {
         return;
      }
      boolean sameClass = logicalTime.getClass().equals(theTime.getClass());
      if (!sameClass) {
         throw new InvalidLogicalTime("wrong time type");
      }
   }

   public void disableTimeRegulation()
   throws
         TimeRegulationIsNotEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.disableTimeRegulation();
   }

   public void enableTimeConstrained()
   throws
         InTimeAdvancingState,
         RequestForTimeConstrainedPending,
         TimeConstrainedAlreadyEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.enableTimeConstrained();
   }

   public void disableTimeConstrained()
   throws
         TimeConstrainedIsNotEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.disableTimeConstrained();
   }

   public void timeAdvanceRequest(LogicalTime theTime)
   throws
         LogicalTimeAlreadyPassed,
         InvalidLogicalTime,
         InTimeAdvancingState,
         RequestForTimeRegulationPending,
         RequestForTimeConstrainedPending,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      checkTimeType(theTime);
      _rtiAmbassadorClient.timeAdvanceRequest(theTime);
   }

   public void timeAdvanceRequestAvailable(LogicalTime theTime)
   throws
         LogicalTimeAlreadyPassed,
         InvalidLogicalTime,
         InTimeAdvancingState,
         RequestForTimeRegulationPending,
         RequestForTimeConstrainedPending,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      checkTimeType(theTime);
      _rtiAmbassadorClient.timeAdvanceRequestAvailable(theTime);
   }

   public void nextMessageRequest(LogicalTime theTime)
   throws
         LogicalTimeAlreadyPassed,
         InvalidLogicalTime,
         InTimeAdvancingState,
         RequestForTimeRegulationPending,
         RequestForTimeConstrainedPending,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      checkTimeType(theTime);
      _rtiAmbassadorClient.nextMessageRequest(theTime);
   }

   public void nextMessageRequestAvailable(LogicalTime theTime)
   throws
         LogicalTimeAlreadyPassed,
         InvalidLogicalTime,
         InTimeAdvancingState,
         RequestForTimeRegulationPending,
         RequestForTimeConstrainedPending,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      checkTimeType(theTime);
      _rtiAmbassadorClient.nextMessageRequestAvailable(theTime);
   }

   public void flushQueueRequest(LogicalTime theTime)
   throws
         LogicalTimeAlreadyPassed,
         InvalidLogicalTime,
         InTimeAdvancingState,
         RequestForTimeRegulationPending,
         RequestForTimeConstrainedPending,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      checkTimeType(theTime);
      _rtiAmbassadorClient.flushQueueRequest(theTime);
   }

   public void enableAsynchronousDelivery()
   throws
         AsynchronousDeliveryAlreadyEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.enableAsynchronousDelivery();
   }

   public void disableAsynchronousDelivery()
   throws
         AsynchronousDeliveryAlreadyDisabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.disableAsynchronousDelivery();
   }

   public TimeQueryReturn queryGALT()
   throws
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.queryGALT();
   }

   public LogicalTime queryLogicalTime()
   throws
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.queryLogicalTime();
   }

   public TimeQueryReturn queryLITS()
   throws
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.queryLITS();
   }

   public void modifyLookahead(LogicalTimeInterval theLookahead)
   throws
         InvalidLookahead,
         InTimeAdvancingState,
         TimeRegulationIsNotEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.modifyLookahead(theLookahead);
   }

   public LogicalTimeInterval queryLookahead()
   throws
         TimeRegulationIsNotEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.queryLookahead();
   }

   public void retract(MessageRetractionHandle theHandle)
   throws
         MessageCanNoLongerBeRetracted,
         InvalidMessageRetractionHandle,
         TimeRegulationIsNotEnabled,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.retract(theHandle);
   }

   public void changeAttributeOrderType(
         ObjectInstanceHandle theObject,
         AttributeHandleSet theAttributes, OrderType theType)
   throws
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.changeAttributeOrderType(theObject, theAttributes, theType);
   }

   public void changeInteractionOrderType(InteractionClassHandle theClass, OrderType theType)
   throws
         InteractionClassNotPublished,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.changeInteractionOrderType(theClass, theType);
   }

   public RegionHandle createRegion(DimensionHandleSet dimensions)
   throws
         InvalidDimensionHandle,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.createRegion(dimensions);
   }

   public void commitRegionModifications(RegionHandleSet regions)
   throws
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.commitRegionModifications(regions);
   }

   public void deleteRegion(RegionHandle theRegion)
   throws
         RegionInUseForUpdateOrSubscription,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theRegion == null) {
         throw new InvalidRegion("null");
      }
      if (theRegion instanceof ClientConverter.ConveyedRegionHandleImpl) {
         throw new RegionNotCreatedByThisFederate("conveyed region");
      }
      _rtiAmbassadorClient.deleteRegion(theRegion);
   }

   public ObjectInstanceHandle registerObjectInstanceWithRegions(
         ObjectClassHandle theClass,
         AttributeSetRegionSetPairList attributesAndRegions)
   throws
         InvalidRegionContext,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         AttributeNotPublished,
         ObjectClassNotPublished,
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithRegions(theClass, attributesAndRegions);
   }

   public ObjectInstanceHandle registerObjectInstanceWithRegions(
         ObjectClassHandle theClass,
         AttributeSetRegionSetPairList attributesAndRegions,
         String theObject)
   throws
         ObjectInstanceNameInUse,
         ObjectInstanceNameNotReserved,
         InvalidRegionContext,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         AttributeNotPublished,
         ObjectClassNotPublished,
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithNameAndRegions(theClass, attributesAndRegions, theObject);
   }

   public void associateRegionsForUpdates(
         ObjectInstanceHandle theObject,
         AttributeSetRegionSetPairList attributesAndRegions)
   throws
         InvalidRegionContext,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theObject == null) {
         throw new ObjectInstanceNotKnown("null");
      }
      _rtiAmbassadorClient.associateRegionsForUpdates(theObject, attributesAndRegions);
   }

   public void unassociateRegionsForUpdates(
         ObjectInstanceHandle theObject,
         AttributeSetRegionSetPairList attributesAndRegions)
   throws
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theObject == null) {
         throw new ObjectInstanceNotKnown("null");
      }
      _rtiAmbassadorClient.unassociateRegionsForUpdates(theObject, attributesAndRegions);
   }

   public void subscribeObjectClassAttributesWithRegions(
         ObjectClassHandle theClass,
         AttributeSetRegionSetPairList attributesAndRegions)
   throws
         InvalidRegionContext,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegions(theClass, attributesAndRegions, true);
   }

   public void subscribeObjectClassAttributesWithRegions(
         ObjectClassHandle theClass,
         AttributeSetRegionSetPairList attributesAndRegions,
         String updateRateDesignator)
   throws
         InvalidRegionContext,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         AttributeNotDefined,
         ObjectClassNotDefined,
         InvalidUpdateRateDesignator,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegionsAndRate(
            theClass,
            attributesAndRegions,
            true,
            updateRateDesignator);
   }

   public void subscribeObjectClassAttributesPassivelyWithRegions(
         ObjectClassHandle theClass,
         AttributeSetRegionSetPairList attributesAndRegions)
   throws
         InvalidRegionContext,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegions(theClass, attributesAndRegions, false);
   }

   public void subscribeObjectClassAttributesPassivelyWithRegions(
         ObjectClassHandle theClass,
         AttributeSetRegionSetPairList attributesAndRegions,
         String updateRateDesignator)
   throws
         InvalidRegionContext,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         AttributeNotDefined,
         ObjectClassNotDefined,
         InvalidUpdateRateDesignator,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegionsAndRate(
            theClass,
            attributesAndRegions,
            false,
            updateRateDesignator);
   }

   public void unsubscribeObjectClassAttributesWithRegions(
         ObjectClassHandle theClass,
         AttributeSetRegionSetPairList attributesAndRegions)
   throws
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeObjectClassAttributesWithRegions(theClass, attributesAndRegions);
   }

   public void subscribeInteractionClassWithRegions(
         InteractionClassHandle theClass,
         RegionHandleSet regions)
   throws
         FederateServiceInvocationsAreBeingReportedViaMOM,
         InvalidRegionContext,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeInteractionClassWithRegions(theClass, true, regions);
   }

   public void subscribeInteractionClassPassivelyWithRegions(
         InteractionClassHandle theClass,
         RegionHandleSet regions)
   throws
         FederateServiceInvocationsAreBeingReportedViaMOM,
         InvalidRegionContext,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeInteractionClassWithRegions(theClass, false, regions);
   }

   public void unsubscribeInteractionClassWithRegions(
         InteractionClassHandle theClass,
         RegionHandleSet regions)
   throws
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeInteractionClassWithRegions(theClass, regions);
   }

   public void sendInteractionWithRegions(
         InteractionClassHandle theInteraction,
         ParameterHandleValueMap theParameters,
         RegionHandleSet regions, byte[] userSuppliedTag)
   throws
         InvalidRegionContext,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         InteractionClassNotPublished,
         InteractionParameterNotDefined,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theInteraction == null) {
         throw new NullPointerException("null");
      }
      _rtiAmbassadorClient.sendInteractionWithRegions(theInteraction, theParameters, regions, userSuppliedTag);
   }

   public MessageRetractionReturn sendInteractionWithRegions(
         InteractionClassHandle theInteraction,
         ParameterHandleValueMap theParameters,
         RegionHandleSet regions,
         byte[] userSuppliedTag,
         LogicalTime theTime)
   throws
         InvalidLogicalTime,
         InvalidRegionContext,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         InteractionClassNotPublished,
         InteractionParameterNotDefined,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theInteraction == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.sendInteractionWithRegionsAndTime(
            theInteraction,
            theParameters,
            regions,
            userSuppliedTag,
            theTime);
   }

   public void requestAttributeValueUpdateWithRegions(
         ObjectClassHandle theClass,
         AttributeSetRegionSetPairList attributesAndRegions,
         byte[] userSuppliedTag)
   throws
         InvalidRegionContext,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.requestAttributeValueUpdateWithRegions(theClass, attributesAndRegions, userSuppliedTag);
   }

   public ResignAction getAutomaticResignDirective()
   throws
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.getAutomaticResignDirective();
   }

   public void setAutomaticResignDirective(ResignAction resignAction)
   throws
         InvalidResignAction,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.setAutomaticResignDirective(resignAction);
   }

   public FederateHandle getFederateHandle(String theName)
   throws
         NameNotFound,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theName == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.getFederateHandle(theName);
   }

   public String getFederateName(FederateHandle theHandle)
   throws
         InvalidFederateHandle,
         FederateHandleNotKnown,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theHandle == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.getFederateName(theHandle);
   }

   public ObjectClassHandle getObjectClassHandle(String theName)
   throws
         NameNotFound,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.getObjectClassHandle(theName);
   }

   public String getObjectClassName(ObjectClassHandle theHandle)
   throws
         InvalidObjectClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theHandle == null) {
         throw new InvalidObjectClassHandle("null");
      }
      return _rtiAmbassadorClient.getObjectClassName(theHandle);
   }

   public ObjectClassHandle getKnownObjectClassHandle(ObjectInstanceHandle theObject)
   throws
         ObjectInstanceNotKnown,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theObject == null) {
         throw new ObjectInstanceNotKnown("null");
      }
      return _rtiAmbassadorClient.getKnownObjectClassHandle(theObject);
   }

   public ObjectInstanceHandle getObjectInstanceHandle(String theName)
   throws
         ObjectInstanceNotKnown,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.getObjectInstanceHandle(theName);
   }

   public String getObjectInstanceName(ObjectInstanceHandle theHandle)
   throws
         ObjectInstanceNotKnown,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theHandle == null) {
         throw new ObjectInstanceNotKnown("null");
      }
      return _rtiAmbassadorClient.getObjectInstanceName(theHandle);
   }

   public AttributeHandle getAttributeHandle(
         ObjectClassHandle whichClass,
         String theName)
   throws
         NameNotFound,
         InvalidObjectClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (whichClass == null) {
         throw new InvalidObjectClassHandle("null");
      }
      return _rtiAmbassadorClient.getAttributeHandle(whichClass, theName);
   }

   public String getAttributeName(ObjectClassHandle whichClass, AttributeHandle theHandle)
   throws
         AttributeNotDefined,
         InvalidAttributeHandle,
         InvalidObjectClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (whichClass == null) {
         throw new InvalidObjectClassHandle("null");
      }
      if (theHandle == null) {
         throw new InvalidAttributeHandle("null");
      }
      return _rtiAmbassadorClient.getAttributeName(whichClass, theHandle);
   }

   public double getUpdateRateValue(String updateRateDesignator)
   throws
         InvalidUpdateRateDesignator,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.getUpdateRateValue(updateRateDesignator);
   }

   public double getUpdateRateValueForAttribute(
         ObjectInstanceHandle theObject,
         AttributeHandle theAttribute)
   throws
         ObjectInstanceNotKnown,
         AttributeNotDefined,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.getUpdateRateValueForAttribute(theObject, theAttribute);
   }

   public InteractionClassHandle getInteractionClassHandle(String theName)
   throws
         NameNotFound,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.getInteractionClassHandle(theName);
   }

   public String getInteractionClassName(InteractionClassHandle theHandle)
   throws
         InvalidInteractionClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theHandle == null) {
         throw new InvalidInteractionClassHandle("null");
      }
      return _rtiAmbassadorClient.getInteractionClassName(theHandle);
   }

   public ParameterHandle getParameterHandle(
         InteractionClassHandle whichClass,
         String theName)
   throws
         NameNotFound,
         InvalidInteractionClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (whichClass == null) {
         throw new InvalidInteractionClassHandle("null");
      }
      return _rtiAmbassadorClient.getParameterHandle(whichClass, theName);
   }

   public String getParameterName(InteractionClassHandle whichClass, ParameterHandle theHandle)
   throws
         InteractionParameterNotDefined,
         InvalidParameterHandle,
         InvalidInteractionClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (whichClass == null) {
         throw new InvalidInteractionClassHandle("null");
      }
      if (theHandle == null) {
         throw new InvalidParameterHandle("null");
      }
      return _rtiAmbassadorClient.getParameterName(whichClass, theHandle);
   }

   public OrderType getOrderType(String theName)
   throws
         InvalidOrderName,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theName == null) {
         throw new InvalidOrderName("null");
      }
      return _rtiAmbassadorClient.getOrderType(theName);
   }

   public String getOrderName(OrderType theType)
   throws
         InvalidOrderType,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theType == null) {
         throw new InvalidOrderType("null");
      }
      return _rtiAmbassadorClient.getOrderName(theType);
   }

   public TransportationTypeHandle getTransportationTypeHandle(String theName)
   throws
         InvalidTransportationName,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theName == null) {
         throw new InvalidTransportationName("null");
      }
      return _rtiAmbassadorClient.getTransportationTypeHandle(theName);
   }

   public String getTransportationTypeName(TransportationTypeHandle theHandle)
   throws
         InvalidTransportationType,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theHandle == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.getTransportationTypeName(theHandle);
   }

   public DimensionHandleSet getAvailableDimensionsForClassAttribute(
         ObjectClassHandle whichClass,
         AttributeHandle theHandle)
   throws
         AttributeNotDefined,
         InvalidAttributeHandle,
         InvalidObjectClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (whichClass == null) {
         throw new InvalidObjectClassHandle("null");
      }
      if (theHandle == null) {
         throw new InvalidAttributeHandle("null");
      }
      return _rtiAmbassadorClient.getAvailableDimensionsForClassAttribute(whichClass, theHandle);
   }

   public DimensionHandleSet getAvailableDimensionsForInteractionClass(InteractionClassHandle theHandle)
   throws
         InvalidInteractionClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theHandle == null) {
         throw new InvalidInteractionClassHandle("null");
      }
      return _rtiAmbassadorClient.getAvailableDimensionsForInteractionClass(theHandle);
   }

   public DimensionHandle getDimensionHandle(String theName)
   throws
         NameNotFound,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.getDimensionHandle(theName);
   }

   public String getDimensionName(DimensionHandle theHandle)
   throws
         InvalidDimensionHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theHandle == null) {
         throw new InvalidDimensionHandle("null");
      }
      return _rtiAmbassadorClient.getDimensionName(theHandle);
   }

   public long getDimensionUpperBound(DimensionHandle theHandle)
   throws
         InvalidDimensionHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (theHandle == null) {
         throw new InvalidDimensionHandle("null");
      }
      return _rtiAmbassadorClient.getDimensionUpperBound(theHandle);
   }

   public DimensionHandleSet getDimensionHandleSet(RegionHandle region)
   throws
         InvalidRegion,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (region == null) {
         throw new InvalidRegion("null");
      }
      if (region instanceof ClientConverter.ConveyedRegionHandleImpl) {
         ClientConverter.ConveyedRegionHandleImpl conveyedRegionHandle =
               (ClientConverter.ConveyedRegionHandleImpl) region;
         return conveyedRegionHandle.getDimensionHandleSet();
      }
      return _rtiAmbassadorClient.getDimensionHandleSet(region);
   }

   public RangeBounds getRangeBounds(RegionHandle region, DimensionHandle dimension)
   throws
         RegionDoesNotContainSpecifiedDimension,
         InvalidRegion,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (region == null) {
         throw new InvalidRegion("null");
      }
      if (region instanceof ClientConverter.ConveyedRegionHandleImpl) {
         ClientConverter.ConveyedRegionHandleImpl conveyedRegionHandle =
               (ClientConverter.ConveyedRegionHandleImpl) region;
         return conveyedRegionHandle.getRangeBounds(dimension);
      }
      return _rtiAmbassadorClient.getRangeBounds(region, dimension);
   }

   public void setRangeBounds(
         RegionHandle region,
         DimensionHandle dimension,
         RangeBounds bounds)
   throws
         InvalidRangeBound,
         RegionDoesNotContainSpecifiedDimension,
         RegionNotCreatedByThisFederate,
         InvalidRegion,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (region == null) {
         throw new InvalidRegion("null");
      }
      if (region instanceof ClientConverter.ConveyedRegionHandleImpl) {
         throw new RegionNotCreatedByThisFederate("conveyed region");
      }
      _rtiAmbassadorClient.setRangeBounds(region, dimension, bounds);
   }

   public long normalizeFederateHandle(FederateHandle federateHandle)
   throws
         InvalidFederateHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (federateHandle == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.normalizeFederateHandle(federateHandle);
   }

   public long normalizeServiceGroup(ServiceGroup group)
   throws
         InvalidServiceGroup,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.normalizeServiceGroup(group);
   }

   public void enableObjectClassRelevanceAdvisorySwitch()
   throws
         ObjectClassRelevanceAdvisorySwitchIsOn,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.enableObjectClassRelevanceAdvisorySwitch();
   }

   public void disableObjectClassRelevanceAdvisorySwitch()
   throws
         ObjectClassRelevanceAdvisorySwitchIsOff,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.disableObjectClassRelevanceAdvisorySwitch();
   }

   public void enableAttributeRelevanceAdvisorySwitch()
   throws
         AttributeRelevanceAdvisorySwitchIsOn,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.enableAttributeRelevanceAdvisorySwitch();
   }

   public void disableAttributeRelevanceAdvisorySwitch()
   throws
         AttributeRelevanceAdvisorySwitchIsOff,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.disableAttributeRelevanceAdvisorySwitch();
   }

   public void enableAttributeScopeAdvisorySwitch()
   throws
         AttributeScopeAdvisorySwitchIsOn,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.enableAttributeScopeAdvisorySwitch();
   }

   public void disableAttributeScopeAdvisorySwitch()
   throws
         AttributeScopeAdvisorySwitchIsOff,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.disableAttributeScopeAdvisorySwitch();
   }

   public void enableInteractionRelevanceAdvisorySwitch()
   throws
         InteractionRelevanceAdvisorySwitchIsOn,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.enableInteractionRelevanceAdvisorySwitch();
   }

   public void disableInteractionRelevanceAdvisorySwitch()
   throws
         InteractionRelevanceAdvisorySwitchIsOff,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.disableInteractionRelevanceAdvisorySwitch();
   }

   public boolean evokeCallback(double approximateMinimumTimeInSeconds)
   throws
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      checkCallNotAllowedFromWithinCallback();
      return _rtiAmbassadorClient.evokeCallback(approximateMinimumTimeInSeconds);
   }

   public boolean evokeMultipleCallbacks(double approximateMinimumTimeInSeconds, double approximateMaximumTimeInSeconds)
   throws
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      checkCallNotAllowedFromWithinCallback();
      return _rtiAmbassadorClient.evokeMultipleCallbacks(
            approximateMinimumTimeInSeconds,
            approximateMaximumTimeInSeconds);
   }

   public void enableCallbacks()
   throws
         SaveInProgress,
         RestoreInProgress,
         RTIinternalError
   {
      _rtiAmbassadorClient.enableCallbacks();
   }

   public void disableCallbacks()
   throws
         SaveInProgress,
         RestoreInProgress,
         RTIinternalError
   {
      _rtiAmbassadorClient.disableCallbacks();
      //TODO: add disable callbacks to evolved protocol
   }

   public AttributeHandleFactory getAttributeHandleFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getAttributeHandleFactory();
   }

   public AttributeHandleSetFactory getAttributeHandleSetFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getAttributeHandleSetFactory();
   }

   public AttributeHandleValueMapFactory getAttributeHandleValueMapFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getAttributeHandleValueMapFactory();
   }

   public AttributeSetRegionSetPairListFactory getAttributeSetRegionSetPairListFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getAttributeSetRegionSetPairListFactory();
   }

   public DimensionHandleFactory getDimensionHandleFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getDimensionHandleFactory();
   }

   public DimensionHandleSetFactory getDimensionHandleSetFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getDimensionHandleSetFactory();
   }

   public FederateHandleFactory getFederateHandleFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getFederateHandleFactory();
   }

   public FederateHandleSetFactory getFederateHandleSetFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getFederateHandleSetFactory();
   }

   public InteractionClassHandleFactory getInteractionClassHandleFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getInteractionClassHandleFactory();
   }

   public ObjectClassHandleFactory getObjectClassHandleFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getObjectClassHandleFactory();
   }

   public ObjectInstanceHandleFactory getObjectInstanceHandleFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getObjectInstanceHandleFactory();
   }

   public ParameterHandleFactory getParameterHandleFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getParameterHandleFactory();
   }

   public ParameterHandleValueMapFactory getParameterHandleValueMapFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getParameterHandleValueMapFactory();
   }

   public RegionHandleSetFactory getRegionHandleSetFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getRegionHandleSetFactory();
   }

   public TransportationTypeHandleFactory getTransportationTypeHandleFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return _clientConverter.getTransportationTypeHandleFactory();
   }

   public String getHLAversion()
   {
      return "IEEE 1516-2010";
   }

   public LogicalTimeFactory getTimeFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      if (_timeFactory != null) {
         return _timeFactory;
      } else {
         throw new FederateNotExecutionMember("No time factory available");
      }
   }
}
