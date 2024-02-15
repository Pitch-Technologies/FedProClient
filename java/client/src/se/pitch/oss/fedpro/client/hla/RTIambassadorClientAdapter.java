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

import hla.rti1516_202X.*;
import hla.rti1516_202X.auth.Credentials;
import hla.rti1516_202X.exceptions.*;
import hla.rti1516_202X.time.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class RTIambassadorClientAdapter implements RTIambassadorEx {

   private final RTIambassadorClient _rtiAmbassadorClient;
   private LogicalTimeFactory<?, ?> _timeFactory;
   private final ClientConverter _clientConverter;

   private final AsyncRTIambassador _asyncRTIambassador;

   public RTIambassadorClientAdapter()
   {
      _clientConverter = new ClientConverter();
      _rtiAmbassadorClient = new RTIambassadorClient(_clientConverter);
      _asyncRTIambassador = AsyncHelper.getAsyncRTIambassador(_rtiAmbassadorClient);
   }

   @Override
   public AsyncRTIambassador async()
   {
      return _asyncRTIambassador;
   }

   @Override
   public ConfigurationResult connect(
         FederateAmbassador federateReference,
         CallbackModel callbackModel,
         RtiConfiguration configuration)
   throws
         ConnectionFailed,
         UnsupportedCallbackModel,
         AlreadyConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      return _rtiAmbassadorClient.connect(federateReference, callbackModel, configuration);
   }

   @Override
   public ConfigurationResult connect(
         FederateAmbassador federateReference,
         CallbackModel callbackModel,
         Credentials credentials)
   throws
         Unauthorized,
         InvalidCredentials,
         ConnectionFailed,
         UnsupportedCallbackModel,
         AlreadyConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      return _rtiAmbassadorClient.connect(federateReference, callbackModel, credentials);
   }

   @Override
   public ConfigurationResult connect(
         FederateAmbassador federateReference,
         CallbackModel callbackModel,
         RtiConfiguration configuration,
         Credentials credentials)
   throws
         Unauthorized,
         InvalidCredentials,
         ConnectionFailed,
         UnsupportedCallbackModel,
         AlreadyConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      return _rtiAmbassadorClient.connect(federateReference, callbackModel, configuration, credentials);
   }

   public ConfigurationResult connect(
         FederateAmbassador federateReference,
         CallbackModel callbackModel)
   throws
         ConnectionFailed,
         UnsupportedCallbackModel,
         AlreadyConnected,
         CallNotAllowedFromWithinCallback,
         RTIinternalError
   {
      return _rtiAmbassadorClient.connect(federateReference, callbackModel);
   }

   public void disconnect()
   throws FederateIsExecutionMember, CallNotAllowedFromWithinCallback, RTIinternalError
   {
      _rtiAmbassadorClient.disconnect();
   }

   public void createFederationExecution(
         String federationName,
         String fomModule)
   throws
         InconsistentFOM,
         InvalidFOM,
         ErrorReadingFOM,
         CouldNotOpenFOM,
         FederationExecutionAlreadyExists,
         NotConnected,
         Unauthorized,
         RTIinternalError
   {
      _rtiAmbassadorClient.createFederationExecution(federationName, getFomModule(fomModule));
   }

   public void createFederationExecution(
         String federationName,
         String fomModule,
         String logicalTimeImplementationName)
   throws
         CouldNotCreateLogicalTimeFactory,
         InconsistentFOM,
         InvalidFOM,
         ErrorReadingFOM,
         CouldNotOpenFOM,
         FederationExecutionAlreadyExists,
         NotConnected,
         Unauthorized,
         RTIinternalError
   {
      _rtiAmbassadorClient.createFederationExecutionWithTime(
            federationName,
            getFomModule(fomModule),
            logicalTimeImplementationName);
   }

   public void createFederationExecutionWithMIM(
         String federationName,
         String[] fomModules,
         String mimModule,
         String logicalTimeImplementationName)
   throws
         CouldNotCreateLogicalTimeFactory,
         InconsistentFOM,
         InvalidFOM,
         ErrorReadingFOM,
         CouldNotOpenFOM,
         ErrorReadingMIM,
         InvalidMIM,
         CouldNotOpenMIM,
         DesignatorIsHLAstandardMIM,
         FederationExecutionAlreadyExists,
         NotConnected,
         RTIinternalError,
         Unauthorized
   {
      FomModuleSet fomModuleSet = getFomModuleSet(fomModules);
      FomModule mimModule1;
      try {
         mimModule1 = getFomModule(mimModule);
      } catch (CouldNotOpenFOM e) {
         throw new CouldNotOpenMIM(e.getMessage());
      }
      _rtiAmbassadorClient.createFederationExecutionWithMIMAndTime(
            federationName,
            fomModuleSet,
            mimModule1,
            logicalTimeImplementationName);
   }

   public void createFederationExecution(
         String federationName,
         String[] fomModules,
         String logicalTimeImplementationName)
   throws
         CouldNotCreateLogicalTimeFactory,
         InconsistentFOM,
         InvalidFOM,
         ErrorReadingFOM,
         CouldNotOpenFOM,
         FederationExecutionAlreadyExists,
         NotConnected,
         Unauthorized,
         RTIinternalError
   {
      FomModuleSet fomModuleSet = getFomModuleSet(fomModules);
      _rtiAmbassadorClient.createFederationExecutionWithModulesAndTime(
            federationName,
            fomModuleSet,
            logicalTimeImplementationName);
   }

   private FomModuleSet getFomModuleSet(String[] fomModules)
   throws CouldNotOpenFOM
   {
      FomModuleSet result = new FomModuleSet();
      for (String fomModule : fomModules) {
         result.add(getFomModule(fomModule));
      }
      return result;
   }

   private FomModule getFomModule(String fomModule)
   throws CouldNotOpenFOM
   {
      // Never pass a file reference to server. All file references must be resolved locally
      // and passed as raw or zipped blob.

      // Is it a local file?
      File file1 = new File(fomModule);
      if (file1.exists()) {
         FomModule moduleFromFile = getFomModuleFromFile(file1);
         if (moduleFromFile != null) {
            return moduleFromFile;
         }
      }
      try {
         // Try to load as resource, first from Thread.currentThread().getContextClassLoader(), then getClass().getClassLoader()
         URL resource = Thread.currentThread().getContextClassLoader().getResource(fomModule);
         if (resource == null) {
            resource = getClass().getResource(fomModule);
         }
         if (resource != null) {
            File file = new File(resource.toURI());
            FomModule moduleFromFile = getFomModuleFromFile(file);
            if (moduleFromFile != null) {
               return moduleFromFile;
            }
         }
         // Not found as resource
         // Try to interpret FOM reference as URI
         URI uri = new URI(fomModule);
         // Successfully created an URI
         if ("file".equalsIgnoreCase(uri.getScheme())) {
            // Try creating FomModule from file URI
            File file;
            try {
               file = new File(uri);
            } catch (IllegalArgumentException e) {
               // Invalid local file URI. Hand it over to server.
               return new FomModule(fomModule);
            }
            FomModule moduleFromFile = getFomModuleFromFile(file);
            if (moduleFromFile != null) {
               return moduleFromFile;
            }
         } else {
            // Other type of URL. Pass it to server.
            return new FomModule(fomModule);
         }
      } catch (URISyntaxException e) {
         // Not a URL.
      }
      // Don't know what this is. Let someone else try it as a URL.
      return new FomModule(fomModule);
   }

   private static FomModule getFomModuleFromFile(File file)
   {
      try {
         if (file.length() > 1024) {
            return new FomModule(zip(file));
         } else {
            return new FomModule(file.getName(), Files.readAllBytes(file.toPath()));
         }
      } catch (IllegalArgumentException | IOException ignore) {
         // fallback to url
      }
      return null;
   }

   private FomModule getFomModule(URL fomModule)
   {
      if ("file".equalsIgnoreCase(fomModule.getProtocol())) {
         try {
            File file = new File(fomModule.toURI());
            if (file.length() > 1024) {
               return new FomModule(zip(file));
            } else {
               return new FomModule(file.getName(), Files.readAllBytes(file.toPath()));
            }
         } catch (IllegalArgumentException | URISyntaxException | IOException ignore) {
            // fallback to url
         }
      }
      return new FomModule(fomModule.toString());
   }

   private static byte[] zip(File file)
   throws IOException
   {
      ByteArrayOutputStream result = new ByteArrayOutputStream();

      ZipOutputStream zip = new ZipOutputStream(result);

      zip.putNextEntry(new ZipEntry(file.getName()));
      Files.copy(file.toPath(), zip);
      zip.closeEntry();

      zip.close();

      return result.toByteArray();
   }

   public void createFederationExecutionWithMIM(
         String federationName,
         String[] fomModules,
         String mimModule)
   throws
         InconsistentFOM,
         InvalidFOM,
         ErrorReadingFOM,
         CouldNotOpenFOM,
         ErrorReadingMIM,
         InvalidMIM,
         CouldNotOpenMIM,
         DesignatorIsHLAstandardMIM,
         FederationExecutionAlreadyExists,
         NotConnected,
         Unauthorized,
         RTIinternalError
   {
      try {
         FomModuleSet fomModuleSet = getFomModuleSet(fomModules);
         FomModule mimModule1;
         try {
            mimModule1 = getFomModule(mimModule);
         } catch (CouldNotOpenFOM e) {
            throw new CouldNotOpenMIM(e.getMessage());
         }
         _rtiAmbassadorClient.createFederationExecutionWithMIMAndTime(
               federationName,
               fomModuleSet,
               mimModule1,
               "HLAfloat64Time");
      } catch (CouldNotCreateLogicalTimeFactory e) {
         throw new RTIinternalError("Failed to create default time HLAfloat64Time");
      }
   }

   public void createFederationExecution(
         String federationName,
         String[] fomModules)
   throws
         InconsistentFOM,
         InvalidFOM,
         ErrorReadingFOM,
         CouldNotOpenFOM,
         FederationExecutionAlreadyExists,
         NotConnected,
         Unauthorized,
         RTIinternalError
   {
      FomModuleSet fomModuleSet = getFomModuleSet(fomModules);
      _rtiAmbassadorClient.createFederationExecutionWithModules(federationName, fomModuleSet);
   }

   public void destroyFederationExecution(String federationName)
   throws FederatesCurrentlyJoined, FederationExecutionDoesNotExist, NotConnected, Unauthorized, RTIinternalError
   {
      _rtiAmbassadorClient.destroyFederationExecution(federationName);
   }

   public void listFederationExecutions()
   throws NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.listFederationExecutions();
   }

   @Override
   public void listFederationExecutionMembers(String federationName)
   throws NotConnected, RTIinternalError
   {
      if (federationName == null) {
         throw new NullPointerException("String");
      }
      _rtiAmbassadorClient.listFederationExecutionMembers(federationName);
   }

   public FederateHandle joinFederationExecution(
         String federateName,
         String federateType,
         String federationName,
         String[] additionalFomModules)
   throws
         CouldNotCreateLogicalTimeFactory,
         FederateNameAlreadyInUse,
         FederationExecutionDoesNotExist,
         InconsistentFOM,
         InvalidFOM,
         ErrorReadingFOM,
         CouldNotOpenFOM,
         SaveInProgress,
         RestoreInProgress,
         FederateAlreadyExecutionMember,
         NotConnected,
         CallNotAllowedFromWithinCallback,
         Unauthorized,
         RTIinternalError
   {
      FomModuleSet fomModuleSet = getFomModuleSet(additionalFomModules);
      JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecutionWithNameAndModules(
            federateName,
            federateType,
            federationName,
            fomModuleSet);
      FederateHandle federateHandle = joinResult.getFederateHandle();
      String timeFactoryName = joinResult.getLogicalTimeImplementationName();
      _timeFactory = LogicalTimeFactoryFactory.getLogicalTimeFactory(timeFactoryName);
      _clientConverter.setTimeFactory(_timeFactory);
      return federateHandle;
   }

   public FederateHandle joinFederationExecution(
         String federateType,
         String federationName,
         String[] additionalFomModules)
   throws
         CouldNotCreateLogicalTimeFactory,
         FederationExecutionDoesNotExist,
         InconsistentFOM,
         InvalidFOM,
         ErrorReadingFOM,
         CouldNotOpenFOM,
         SaveInProgress,
         RestoreInProgress,
         FederateAlreadyExecutionMember,
         NotConnected,
         CallNotAllowedFromWithinCallback,
         Unauthorized,
         RTIinternalError
   {
      FomModuleSet fomModuleSet = getFomModuleSet(additionalFomModules);
      JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecutionWithModules(
            federateType,
            federationName,
            fomModuleSet);
      FederateHandle federateHandle = joinResult.getFederateHandle();
      String timeFactoryName = joinResult.getLogicalTimeImplementationName();
      _timeFactory = LogicalTimeFactoryFactory.getLogicalTimeFactory(timeFactoryName);
      _clientConverter.setTimeFactory(_timeFactory);
      return federateHandle;
   }

   public FederateHandle joinFederationExecution(
         String federateName,
         String federateType,
         String federationName)
   throws
         CouldNotCreateLogicalTimeFactory,
         FederateNameAlreadyInUse,
         FederationExecutionDoesNotExist,
         SaveInProgress,
         RestoreInProgress,
         FederateAlreadyExecutionMember,
         NotConnected,
         CallNotAllowedFromWithinCallback,
         Unauthorized,
         RTIinternalError
   {
      // FIXME join throws incorrect exceptions
      JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecutionWithName(
            federateName,
            federateType,
            federationName);
      FederateHandle federateHandle = joinResult.getFederateHandle();
      String timeFactoryName = joinResult.getLogicalTimeImplementationName();
      _timeFactory = LogicalTimeFactoryFactory.getLogicalTimeFactory(timeFactoryName);
      _clientConverter.setTimeFactory(_timeFactory);
      return federateHandle;
   }

   public FederateHandle joinFederationExecution(
         String federateType,
         String federationName)
   throws
         CouldNotCreateLogicalTimeFactory,
         FederationExecutionDoesNotExist,
         SaveInProgress,
         RestoreInProgress,
         FederateAlreadyExecutionMember,
         NotConnected,
         CallNotAllowedFromWithinCallback,
         Unauthorized,
         RTIinternalError
   {
      // FIXME join throws incorrect exceptions
      JoinResult joinResult = _rtiAmbassadorClient.joinFederationExecution(federateType, federationName);
      FederateHandle federateHandle = joinResult.getFederateHandle();
      String timeFactoryName = joinResult.getLogicalTimeImplementationName();
      _timeFactory = LogicalTimeFactoryFactory.getLogicalTimeFactory(timeFactoryName);
      _clientConverter.setTimeFactory(_timeFactory);
      return federateHandle;
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
      _rtiAmbassadorClient.resignFederationExecution(resignAction);
      // Let's leave the time factories assigned since we can get late callbacks.
      //_timeFactory = null;
      //_clientConverter.setTimeFactory(null);
   }

   public void registerFederationSynchronizationPoint(
         String synchronizationPointLabel,
         byte[] userSuppliedTag)
   throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
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

   public void synchronizationPointAchieved(
         String synchronizationPointLabel,
         boolean successIndicator)
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
   throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.requestFederationSave(label);
   }

   public void requestFederationSave(
         String label,
         LogicalTime<?, ?> time)
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
      _rtiAmbassadorClient.requestFederationSaveWithTime(label, time);
   }

   public void federateSaveBegun()
   throws SaveNotInitiated, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.federateSaveBegun();
   }

   public void federateSaveComplete()
   throws FederateHasNotBegunSave, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.federateSaveComplete();
   }

   public void federateSaveNotComplete()
   throws FederateHasNotBegunSave, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.federateSaveNotComplete();
   }

   public void abortFederationSave()
   throws SaveNotInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.abortFederationSave();
   }

   public void queryFederationSaveStatus()
   throws RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.queryFederationSaveStatus();
   }

   public void requestFederationRestore(String label)
   throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.requestFederationRestore(label);
   }

   public void federateRestoreComplete()
   throws RestoreNotRequested, SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.federateRestoreComplete();
   }

   public void federateRestoreNotComplete()
   throws RestoreNotRequested, SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.federateRestoreNotComplete();
   }

   public void abortFederationRestore()
   throws RestoreNotInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.abortFederationRestore();
   }

   public void queryFederationRestoreStatus()
   throws SaveInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.queryFederationRestoreStatus();
   }

   public void publishObjectClassAttributes(
         ObjectClassHandle objectClass,
         AttributeHandleSet attributes)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.publishObjectClassAttributes(objectClass, attributes);
   }

   public void unpublishObjectClass(ObjectClassHandle objectClass)
   throws
         OwnershipAcquisitionPending,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unpublishObjectClass(objectClass);
   }

   public void unpublishObjectClassAttributes(
         ObjectClassHandle objectClass,
         AttributeHandleSet attributes)
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
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unpublishObjectClassAttributes(objectClass, attributes);
   }

   @Override
   public void publishObjectClassDirectedInteractions(
         ObjectClassHandle objectClass,
         InteractionClassHandleSet interactionClasses)
   throws
         InteractionClassNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.publishObjectClassDirectedInteractions(objectClass, interactionClasses);
   }

   @Override
   public void unpublishObjectClassDirectedInteractions(ObjectClassHandle objectClass)
   throws
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unpublishObjectClassDirectedInteractions(objectClass);
   }

   @Override
   public void unpublishObjectClassDirectedInteractions(
         ObjectClassHandle objectClass,
         InteractionClassHandleSet interactionClasses)
   throws
         InteractionClassNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unpublishObjectClassDirectedInteractionsWithSet(objectClass, interactionClasses);
   }

   public void publishInteractionClass(InteractionClassHandle interactionClass)
   throws
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (interactionClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.publishInteractionClass(interactionClass);
   }

   public void unpublishInteractionClass(InteractionClassHandle interactionClass)
   throws
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (interactionClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.unpublishInteractionClass(interactionClass);
   }

   public void subscribeObjectClassAttributes(
         ObjectClassHandle objectClass,
         AttributeHandleSet attributes)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributes(objectClass, attributes);
   }

   public void subscribeObjectClassAttributes(
         ObjectClassHandle objectClass,
         AttributeHandleSet attributes,
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
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRate(objectClass, attributes, updateRateDesignator);
   }

   public void subscribeObjectClassAttributesPassively(
         ObjectClassHandle objectClass,
         AttributeHandleSet attributes)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesPassively(objectClass, attributes);
   }

   public void subscribeObjectClassAttributesPassively(
         ObjectClassHandle objectClass,
         AttributeHandleSet attributes,
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
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesPassivelyWithRate(
            objectClass,
            attributes,
            updateRateDesignator);
   }

   public void unsubscribeObjectClass(ObjectClassHandle objectClass)
   throws
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeObjectClass(objectClass);
   }

   public void unsubscribeObjectClassAttributes(
         ObjectClassHandle objectClass,
         AttributeHandleSet attributes)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeObjectClassAttributes(objectClass, attributes);
   }

   @Override
   public void subscribeObjectClassDirectedInteractions(
         ObjectClassHandle objectClass,
         InteractionClassHandleSet interactionClasses)
   throws
         InteractionClassNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassDirectedInteractions(objectClass, interactionClasses);
   }

   @Override
   public void unsubscribeObjectClassDirectedInteractions(ObjectClassHandle objectClass)
   throws
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeObjectClassDirectedInteractions(objectClass);
   }

   @Override
   public void unsubscribeObjectClassDirectedInteractions(
         ObjectClassHandle objectClass,
         InteractionClassHandleSet interactionClasses)
   throws
         InteractionClassNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeObjectClassDirectedInteractionsWithSet(objectClass, interactionClasses);
   }

   public void subscribeInteractionClass(InteractionClassHandle interactionClass)
   throws
         FederateServiceInvocationsAreBeingReportedViaMOM,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (interactionClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeInteractionClass(interactionClass);
   }

   public void subscribeInteractionClassPassively(InteractionClassHandle interactionClass)
   throws
         FederateServiceInvocationsAreBeingReportedViaMOM,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (interactionClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeInteractionClassPassively(interactionClass);
   }

   public void unsubscribeInteractionClass(InteractionClassHandle interactionClass)
   throws
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (interactionClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeInteractionClass(interactionClass);
   }

   public void reserveObjectInstanceName(String objectInstanceName)
   throws IllegalName, SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.reserveObjectInstanceName(objectInstanceName);
   }

   public void releaseObjectInstanceName(String objectInstanceName)
   throws
         ObjectInstanceNameNotReserved,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.releaseObjectInstanceName(objectInstanceName);
   }

   public void reserveMultipleObjectInstanceName(Set<String> objectInstanceNames)
   throws
         IllegalName,
         NameSetWasEmpty,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.reserveMultipleObjectInstanceName(objectInstanceNames);
   }

   public void releaseMultipleObjectInstanceName(Set<String> objectInstanceNames)
   throws
         ObjectInstanceNameNotReserved,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.releaseMultipleObjectInstanceName(objectInstanceNames);
   }

   public ObjectInstanceHandle registerObjectInstance(ObjectClassHandle objectClass)
   throws
         ObjectClassNotPublished,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      return _rtiAmbassadorClient.registerObjectInstance(objectClass);
   }

   public ObjectInstanceHandle registerObjectInstance(
         ObjectClassHandle objectClass,
         String objectInstanceName)
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
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithName(objectClass, objectInstanceName);
   }

   public void updateAttributeValues(
         ObjectInstanceHandle objectInstance,
         AttributeHandleValueMap attributeValues,
         byte[] userSuppliedTag)
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
      _rtiAmbassadorClient.updateAttributeValues(objectInstance, attributeValues, userSuppliedTag);
   }

   public MessageRetractionReturn updateAttributeValues(
         ObjectInstanceHandle objectInstance,
         AttributeHandleValueMap attributeValues,
         byte[] userSuppliedTag,
         LogicalTime<?, ?> time)
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
      return _rtiAmbassadorClient.updateAttributeValuesWithTime(objectInstance, attributeValues, userSuppliedTag, time);
   }

   public void sendInteraction(
         InteractionClassHandle interactionClass,
         ParameterHandleValueMap parameterValues,
         byte[] userSuppliedTag)
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
      if (interactionClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.sendInteraction(interactionClass, parameterValues, userSuppliedTag);
   }

   public MessageRetractionReturn sendInteraction(
         InteractionClassHandle interactionClass,
         ParameterHandleValueMap parameterValues,
         byte[] userSuppliedTag,
         LogicalTime<?, ?> time)
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
      if (interactionClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      return _rtiAmbassadorClient.sendInteractionWithTime(interactionClass, parameterValues, userSuppliedTag, time);
   }

   @Override
   public void sendDirectedInteraction(
         InteractionClassHandle interactionClass,
         ObjectInstanceHandle objectInstance,
         ParameterHandleValueMap parameterValues,
         byte[] userSuppliedTag)
   throws
         ObjectInstanceNotKnown,
         InteractionClassNotPublished,
         InteractionParameterNotDefined,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (interactionClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.sendDirectedInteraction(interactionClass, objectInstance, parameterValues, userSuppliedTag);
   }

   @Override
   public MessageRetractionReturn sendDirectedInteraction(
         InteractionClassHandle interactionClass,
         ObjectInstanceHandle objectInstance,
         ParameterHandleValueMap parameterValues,
         byte[] userSuppliedTag,
         LogicalTime<?, ?> time)
   throws
         InvalidLogicalTime,
         ObjectInstanceNotKnown,
         InteractionClassNotPublished,
         InteractionParameterNotDefined,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (interactionClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      return _rtiAmbassadorClient.sendDirectedInteractionWithTime(interactionClass, objectInstance, parameterValues, userSuppliedTag, time);
   }

   public void deleteObjectInstance(
         ObjectInstanceHandle objectInstance,
         byte[] userSuppliedTag)
   throws
         DeletePrivilegeNotHeld,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.deleteObjectInstance(objectInstance, userSuppliedTag);
   }

   public MessageRetractionReturn deleteObjectInstance(
         ObjectInstanceHandle objectInstance,
         byte[] userSuppliedTag,
         LogicalTime<?, ?> time)
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
      return _rtiAmbassadorClient.deleteObjectInstanceWithTime(objectInstance, userSuppliedTag, time);
   }

   public void localDeleteObjectInstance(ObjectInstanceHandle objectInstance)
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
      _rtiAmbassadorClient.localDeleteObjectInstance(objectInstance);
   }

   public void requestAttributeValueUpdate(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes,
         byte[] userSuppliedTag)
   throws
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.requestInstanceAttributeValueUpdate(objectInstance, attributes, userSuppliedTag);
   }

   public void requestAttributeValueUpdate(
         ObjectClassHandle objectClass,
         AttributeHandleSet attributes,
         byte[] userSuppliedTag)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.requestClassAttributeValueUpdate(objectClass, attributes, userSuppliedTag);
   }

   public void requestAttributeTransportationTypeChange(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes,
         TransportationTypeHandle transportationType)
   throws
         AttributeAlreadyBeingChanged,
         AttributeNotOwned,
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         InvalidTransportationTypeHandle,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.requestAttributeTransportationTypeChange(objectInstance, attributes, transportationType);
   }

   @Override
   public void changeDefaultAttributeTransportationType(
         ObjectClassHandle objectClass,
         AttributeHandleSet attributes,
         TransportationTypeHandle transportationType)
   throws
         AttributeAlreadyBeingChanged,
         AttributeNotDefined,
         ObjectClassNotDefined,
         InvalidTransportationTypeHandle,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.changeDefaultAttributeTransportationType(objectClass, attributes, transportationType);
   }

   public void queryAttributeTransportationType(
         ObjectInstanceHandle objectInstance,
         AttributeHandle attribute)
   throws
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.queryAttributeTransportationType(objectInstance, attribute);
   }

   public void requestInteractionTransportationTypeChange(
         InteractionClassHandle interactionClass,
         TransportationTypeHandle transportationType)
   throws
         InteractionClassAlreadyBeingChanged,
         InteractionClassNotPublished,
         InteractionClassNotDefined,
         InvalidTransportationTypeHandle,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.requestInteractionTransportationTypeChange(interactionClass, transportationType);
   }

   public void queryInteractionTransportationType(
         FederateHandle federate,
         InteractionClassHandle interactionClass)
   throws
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.queryInteractionTransportationType(federate, interactionClass);
   }

   public void unconditionalAttributeOwnershipDivestiture(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes,
         byte[] userSuppliedTag)
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
      if (attributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.unconditionalAttributeOwnershipDivestiture(objectInstance, attributes, userSuppliedTag);
   }

   public void negotiatedAttributeOwnershipDivestiture(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes,
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
      if (attributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.negotiatedAttributeOwnershipDivestiture(objectInstance, attributes, userSuppliedTag);
   }

   public void confirmDivestiture(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet confirmedAttributes,
         byte[] userSuppliedTag)
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
      if (confirmedAttributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.confirmDivestiture(objectInstance, confirmedAttributes, userSuppliedTag);
   }

   public void attributeOwnershipAcquisition(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet desiredAttributes,
         byte[] userSuppliedTag)
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
      _rtiAmbassadorClient.attributeOwnershipAcquisition(objectInstance, desiredAttributes, userSuppliedTag);
   }

   public void attributeOwnershipAcquisitionIfAvailable(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet desiredAttributes,
         byte[] userSuppliedTag)
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
      _rtiAmbassadorClient.attributeOwnershipAcquisitionIfAvailable(objectInstance, desiredAttributes, userSuppliedTag);
   }

   public void attributeOwnershipReleaseDenied(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes,
         byte[] userSuppliedTag)
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
      if (attributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.attributeOwnershipReleaseDenied(objectInstance, attributes, userSuppliedTag);
   }

   public AttributeHandleSet attributeOwnershipDivestitureIfWanted(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes,
         byte[] userSuppliedTag)
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
      if (attributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      return _rtiAmbassadorClient.attributeOwnershipDivestitureIfWanted(objectInstance, attributes, userSuppliedTag);
   }

   public void cancelNegotiatedAttributeOwnershipDivestiture(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes)
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
      if (attributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.cancelNegotiatedAttributeOwnershipDivestiture(objectInstance, attributes);
   }

   public void cancelAttributeOwnershipAcquisition(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes)
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
      if (attributes == null) {
         throw new NullPointerException("AttributeHandleSet");
      }
      _rtiAmbassadorClient.cancelAttributeOwnershipAcquisition(objectInstance, attributes);
   }

   public void queryAttributeOwnership(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes)
   throws
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.queryAttributeOwnership(objectInstance, attributes);
   }

   public boolean isAttributeOwnedByFederate(
         ObjectInstanceHandle objectInstance,
         AttributeHandle attribute)
   throws
         AttributeNotDefined,
         ObjectInstanceNotKnown,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      return _rtiAmbassadorClient.isAttributeOwnedByFederate(objectInstance, attribute);
   }

   public void enableTimeRegulation(LogicalTimeInterval<?> lookahead)
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
      checkIntervalType(lookahead);
      _rtiAmbassadorClient.enableTimeRegulation(lookahead);
   }

   private void checkIntervalType(LogicalTimeInterval<?> lookahead)
   throws InvalidLookahead
   {
      // Types match if they implement the same basic time type, e.g. HLAinteger64Time.
      LogicalTimeInterval<?> logicalTimeInterval = _timeFactory.makeEpsilon();
      if (lookahead instanceof HLAfloat64Interval && logicalTimeInterval instanceof HLAfloat64Interval) {
         return;
      }
      if (lookahead instanceof HLAinteger64Interval && logicalTimeInterval instanceof HLAinteger64Interval) {
         return;
      }
      boolean sameClass = logicalTimeInterval.getClass().equals(lookahead.getClass());
      if (!sameClass) {
         throw new InvalidLookahead("wrong time type");
      }
   }

   private void checkTimeType(LogicalTime<?, ?> time)
   throws InvalidLogicalTime
   {
      LogicalTime<?, ?> logicalTime = _timeFactory.makeInitial();
      if (time instanceof HLAfloat64Time && logicalTime instanceof HLAfloat64Time) {
         return;
      }
      if (time instanceof HLAinteger64Time && logicalTime instanceof HLAinteger64Time) {
         return;
      }
      boolean sameClass = logicalTime.getClass().equals(time.getClass());
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

   public void timeAdvanceRequest(LogicalTime<?, ?> time)
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
      checkTimeType(time);
      _rtiAmbassadorClient.timeAdvanceRequest(time);
   }

   public void timeAdvanceRequestAvailable(LogicalTime<?, ?> time)
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
      checkTimeType(time);
      _rtiAmbassadorClient.timeAdvanceRequestAvailable(time);
   }

   public void nextMessageRequest(LogicalTime<?, ?> time)
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
      checkTimeType(time);
      _rtiAmbassadorClient.nextMessageRequest(time);
   }

   public void nextMessageRequestAvailable(LogicalTime<?, ?> time)
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
      checkTimeType(time);
      _rtiAmbassadorClient.nextMessageRequestAvailable(time);
   }

   public void flushQueueRequest(LogicalTime<?, ?> time)
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
      checkTimeType(time);
      _rtiAmbassadorClient.flushQueueRequest(time);
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
   throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.queryGALT();
   }

   public LogicalTime<?, ?> queryLogicalTime()
   throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.queryLogicalTime();
   }

   public TimeQueryReturn queryLITS()
   throws SaveInProgress, RestoreInProgress, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.queryLITS();
   }

   public void modifyLookahead(LogicalTimeInterval<?> lookahead)
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
      _rtiAmbassadorClient.modifyLookahead(lookahead);
   }

   public LogicalTimeInterval<?> queryLookahead()
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

   public void retract(MessageRetractionHandle retraction)
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
      _rtiAmbassadorClient.retract(retraction);
   }

   public void changeAttributeOrderType(
         ObjectInstanceHandle objectInstance,
         AttributeHandleSet attributes,
         OrderType orderType)
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
      _rtiAmbassadorClient.changeAttributeOrderType(objectInstance, attributes, orderType);
   }

   @Override
   public void changeDefaultAttributeOrderType(
         ObjectClassHandle objectClass,
         AttributeHandleSet attributes,
         OrderType orderType)
   throws
         AttributeNotDefined,
         ObjectClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.changeDefaultAttributeOrderType(objectClass, attributes, orderType);
   }

   public void changeInteractionOrderType(
         InteractionClassHandle interactionClass,
         OrderType orderType)
   throws
         InteractionClassNotPublished,
         InteractionClassNotDefined,
         SaveInProgress,
         RestoreInProgress,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      _rtiAmbassadorClient.changeInteractionOrderType(interactionClass, orderType);
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

   public void deleteRegion(RegionHandle region)
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
      if (region == null) {
         throw new InvalidRegion("null");
      }
      if (region instanceof ClientConverter.ConveyedRegionHandleImpl) {
         throw new RegionNotCreatedByThisFederate("conveyed region");
      }
      _rtiAmbassadorClient.deleteRegion(region);
   }

   public ObjectInstanceHandle registerObjectInstanceWithRegions(
         ObjectClassHandle objectClass,
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
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithRegions(objectClass, attributesAndRegions);
   }

   public ObjectInstanceHandle registerObjectInstanceWithRegions(
         ObjectClassHandle objectClass,
         AttributeSetRegionSetPairList attributesAndRegions,
         String objectInstanceName)
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
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      return _rtiAmbassadorClient.registerObjectInstanceWithNameAndRegions(
            objectClass,
            attributesAndRegions,
            objectInstanceName);
   }

   public void associateRegionsForUpdates(
         ObjectInstanceHandle objectInstance,
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
      if (objectInstance == null) {
         throw new ObjectInstanceNotKnown("null");
      }
      _rtiAmbassadorClient.associateRegionsForUpdates(objectInstance, attributesAndRegions);
   }

   public void unassociateRegionsForUpdates(
         ObjectInstanceHandle objectInstance,
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
      if (objectInstance == null) {
         throw new ObjectInstanceNotKnown("null");
      }
      _rtiAmbassadorClient.unassociateRegionsForUpdates(objectInstance, attributesAndRegions);
   }

   public void subscribeObjectClassAttributesWithRegions(
         ObjectClassHandle objectClass,
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
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegions(objectClass, attributesAndRegions, true);
   }

   public void subscribeObjectClassAttributesWithRegions(
         ObjectClassHandle objectClass,
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
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegionsAndRate(
            objectClass,
            attributesAndRegions,
            true,
            updateRateDesignator);
   }

   public void subscribeObjectClassAttributesPassivelyWithRegions(
         ObjectClassHandle objectClass,
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
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegions(objectClass, attributesAndRegions, false);
   }

   public void subscribeObjectClassAttributesPassivelyWithRegions(
         ObjectClassHandle objectClass,
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
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeObjectClassAttributesWithRegionsAndRate(
            objectClass,
            attributesAndRegions,
            false,
            updateRateDesignator);
   }

   public void unsubscribeObjectClassAttributesWithRegions(
         ObjectClassHandle objectClass,
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
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeObjectClassAttributesWithRegions(objectClass, attributesAndRegions);
   }

   public void subscribeInteractionClassWithRegions(
         InteractionClassHandle interactionClass,
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
      if (interactionClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeInteractionClassWithRegions(interactionClass, true, regions);
   }

   public void subscribeInteractionClassPassivelyWithRegions(
         InteractionClassHandle interactionClass,
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
      if (interactionClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.subscribeInteractionClassWithRegions(interactionClass, false, regions);
   }

   public void unsubscribeInteractionClassWithRegions(
         InteractionClassHandle interactionClass,
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
      if (interactionClass == null) {
         throw new InteractionClassNotDefined("null");
      }
      _rtiAmbassadorClient.unsubscribeInteractionClassWithRegions(interactionClass, regions);
   }

   public void sendInteractionWithRegions(
         InteractionClassHandle interactionClass,
         ParameterHandleValueMap parameterValues,
         RegionHandleSet regions,
         byte[] userSuppliedTag)
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
      if (interactionClass == null) {
         throw new NullPointerException("null");
      }
      _rtiAmbassadorClient.sendInteractionWithRegions(interactionClass, parameterValues, regions, userSuppliedTag);
   }

   public MessageRetractionReturn sendInteractionWithRegions(
         InteractionClassHandle interactionClass,
         ParameterHandleValueMap parameterValues,
         RegionHandleSet regions,
         byte[] userSuppliedTag,
         LogicalTime<?, ?> time)
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
      if (interactionClass == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.sendInteractionWithRegionsAndTime(
            interactionClass,
            parameterValues,
            regions,
            userSuppliedTag,
            time);
   }

   public void requestAttributeValueUpdateWithRegions(
         ObjectClassHandle objectClass,
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
      if (objectClass == null) {
         throw new ObjectClassNotDefined("null");
      }
      _rtiAmbassadorClient.requestAttributeValueUpdateWithRegions(objectClass, attributesAndRegions, userSuppliedTag);
   }

   public ResignAction getAutomaticResignDirective()
   throws FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.getAutomaticResignDirective();
   }

   public void setAutomaticResignDirective(ResignAction resignAction)
   throws InvalidResignAction, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      _rtiAmbassadorClient.setAutomaticResignDirective(resignAction);
   }

   public FederateHandle getFederateHandle(String federateName)
   throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (federateName == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.getFederateHandle(federateName);
   }

   public String getFederateName(FederateHandle federate)
   throws InvalidFederateHandle, FederateHandleNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (federate == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.getFederateName(federate);
   }

   public ObjectClassHandle getObjectClassHandle(String objectClassName)
   throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.getObjectClassHandle(objectClassName);
   }

   public String getObjectClassName(ObjectClassHandle objectClass)
   throws InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (objectClass == null) {
         throw new InvalidObjectClassHandle("null");
      }
      return _rtiAmbassadorClient.getObjectClassName(objectClass);
   }

   public ObjectClassHandle getKnownObjectClassHandle(ObjectInstanceHandle objectInstance)
   throws ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (objectInstance == null) {
         throw new ObjectInstanceNotKnown("null");
      }
      return _rtiAmbassadorClient.getKnownObjectClassHandle(objectInstance);
   }

   public ObjectInstanceHandle getObjectInstanceHandle(String objectInstanceName)
   throws ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.getObjectInstanceHandle(objectInstanceName);
   }

   public String getObjectInstanceName(ObjectInstanceHandle objectInstance)
   throws ObjectInstanceNotKnown, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (objectInstance == null) {
         throw new ObjectInstanceNotKnown("null");
      }
      return _rtiAmbassadorClient.getObjectInstanceName(objectInstance);
   }

   public AttributeHandle getAttributeHandle(
         ObjectClassHandle objectClass,
         String attributeName)
   throws NameNotFound, InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (objectClass == null) {
         throw new InvalidObjectClassHandle("null");
      }
      return _rtiAmbassadorClient.getAttributeHandle(objectClass, attributeName);
   }

   public String getAttributeName(
         ObjectClassHandle objectClass,
         AttributeHandle attribute)
   throws
         AttributeNotDefined,
         InvalidAttributeHandle,
         InvalidObjectClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new InvalidObjectClassHandle("null");
      }
      if (attribute == null) {
         throw new InvalidAttributeHandle("null");
      }
      return _rtiAmbassadorClient.getAttributeName(objectClass, attribute);
   }

   public double getUpdateRateValue(String updateRateDesignator)
   throws InvalidUpdateRateDesignator, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.getUpdateRateValue(updateRateDesignator);
   }

   public double getUpdateRateValueForAttribute(
         ObjectInstanceHandle objectInstance,
         AttributeHandle attribute)
   throws ObjectInstanceNotKnown, AttributeNotDefined, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.getUpdateRateValueForAttribute(objectInstance, attribute);
   }

   public InteractionClassHandle getInteractionClassHandle(String interactionClassName)
   throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.getInteractionClassHandle(interactionClassName);
   }

   public CompletableFuture<InteractionClassHandle> asyncGetInteractionClassHandle(String interactionClassName)
   throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.asyncGetInteractionClassHandle(interactionClassName);
   }

   public String getInteractionClassName(InteractionClassHandle interactionClass)
   throws InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (interactionClass == null) {
         throw new InvalidInteractionClassHandle("null");
      }
      return _rtiAmbassadorClient.getInteractionClassName(interactionClass);
   }

   public ParameterHandle getParameterHandle(
         InteractionClassHandle interactionClass,
         String parameterName)
   throws NameNotFound, InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (interactionClass == null) {
         throw new InvalidInteractionClassHandle("null");
      }
      return _rtiAmbassadorClient.getParameterHandle(interactionClass, parameterName);
   }

   public String getParameterName(
         InteractionClassHandle interactionClass,
         ParameterHandle parameter)
   throws
         InteractionParameterNotDefined,
         InvalidParameterHandle,
         InvalidInteractionClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (interactionClass == null) {
         throw new InvalidInteractionClassHandle("null");
      }
      if (parameter == null) {
         throw new InvalidParameterHandle("null");
      }
      return _rtiAmbassadorClient.getParameterName(interactionClass, parameter);
   }

   public OrderType getOrderType(String orderTypeName)
   throws InvalidOrderName, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (orderTypeName == null) {
         throw new InvalidOrderName("null");
      }
      return _rtiAmbassadorClient.getOrderType(orderTypeName);
   }

   public String getOrderName(OrderType orderType)
   throws InvalidOrderType, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (orderType == null) {
         throw new InvalidOrderType("null");
      }
      return _rtiAmbassadorClient.getOrderName(orderType);
   }

   public TransportationTypeHandle getTransportationTypeHandle(String transportationTypeName)
   throws InvalidTransportationName, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (transportationTypeName == null) {
         throw new InvalidTransportationName("null");
      }
      return _rtiAmbassadorClient.getTransportationTypeHandle(transportationTypeName);
   }

   public String getTransportationTypeName(TransportationTypeHandle transportationType)
   throws InvalidTransportationTypeHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (transportationType == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.getTransportationTypeName(transportationType);
   }

   public DimensionHandleSet getAvailableDimensionsForClassAttribute(
         ObjectClassHandle objectClass,
         AttributeHandle attribute)
   throws
         AttributeNotDefined,
         InvalidAttributeHandle,
         InvalidObjectClassHandle,
         FederateNotExecutionMember,
         NotConnected,
         RTIinternalError
   {
      if (objectClass == null) {
         throw new InvalidObjectClassHandle("null");
      }
      if (attribute == null) {
         throw new InvalidAttributeHandle("null");
      }
      return _rtiAmbassadorClient.getAvailableDimensionsForClassAttribute(objectClass, attribute);
   }

   public DimensionHandleSet getAvailableDimensionsForInteractionClass(InteractionClassHandle interactionClass)
   throws InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (interactionClass == null) {
         throw new InvalidInteractionClassHandle("null");
      }
      return _rtiAmbassadorClient.getAvailableDimensionsForInteractionClass(interactionClass);
   }

   public DimensionHandle getDimensionHandle(String dimensionName)
   throws NameNotFound, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.getDimensionHandle(dimensionName);
   }

   public String getDimensionName(DimensionHandle dimension)
   throws InvalidDimensionHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (dimension == null) {
         throw new InvalidDimensionHandle("null");
      }
      return _rtiAmbassadorClient.getDimensionName(dimension);
   }

   public long getDimensionUpperBound(DimensionHandle dimension)
   throws InvalidDimensionHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (dimension == null) {
         throw new InvalidDimensionHandle("null");
      }
      return _rtiAmbassadorClient.getDimensionUpperBound(dimension);
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

   public RangeBounds getRangeBounds(
         RegionHandle region,
         DimensionHandle dimension)
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
         RangeBounds rangeBounds)
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
      _rtiAmbassadorClient.setRangeBounds(region, dimension, rangeBounds);
   }

   public long normalizeFederateHandle(FederateHandle federate)
   throws InvalidFederateHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      if (federate == null) {
         throw new NullPointerException("null");
      }
      return _rtiAmbassadorClient.normalizeFederateHandle(federate);
   }

   @Override
   public long normalizeObjectClassHandle(ObjectClassHandle objectClass)
   throws InvalidObjectClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.normalizeObjectClassHandle(objectClass);
   }

   @Override
   public long normalizeInteractionClassHandle(InteractionClassHandle interactionClass)
   throws InvalidInteractionClassHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.normalizeInteractionClassHandle(interactionClass);
   }

   @Override
   public long normalizeObjectInstanceHandle(ObjectInstanceHandle objectInstance)
   throws InvalidObjectInstanceHandle, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.normalizeObjectInstanceHandle(objectInstance);
   }

   public long normalizeServiceGroup(ServiceGroup serviceGroup)
   throws InvalidServiceGroup, FederateNotExecutionMember, NotConnected, RTIinternalError
   {
      return _rtiAmbassadorClient.normalizeServiceGroup(serviceGroup);
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
   throws CallNotAllowedFromWithinCallback, RTIinternalError
   {
      return _rtiAmbassadorClient.evokeCallback(approximateMinimumTimeInSeconds);
   }

   public boolean evokeMultipleCallbacks(
         double approximateMinimumTimeInSeconds,
         double approximateMaximumTimeInSeconds)
   throws CallNotAllowedFromWithinCallback, RTIinternalError
   {
      return _rtiAmbassadorClient.evokeMultipleCallbacks(
            approximateMinimumTimeInSeconds,
            approximateMaximumTimeInSeconds);
   }

   public void enableCallbacks()
   throws SaveInProgress, RestoreInProgress, RTIinternalError
   {
      _rtiAmbassadorClient.enableCallbacks();
   }

   public void disableCallbacks()
   throws SaveInProgress, RestoreInProgress, RTIinternalError
   {
      _rtiAmbassadorClient.disableCallbacks();
   }

   public AttributeHandleFactory getAttributeHandleFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getAttributeHandleFactory();
   }

   public AttributeHandleSetFactory getAttributeHandleSetFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getAttributeHandleSetFactory();
   }

   public AttributeHandleValueMapFactory getAttributeHandleValueMapFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getAttributeHandleValueMapFactory();
   }

   public AttributeSetRegionSetPairListFactory getAttributeSetRegionSetPairListFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getAttributeSetRegionSetPairListFactory();
   }

   public DimensionHandleFactory getDimensionHandleFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getDimensionHandleFactory();
   }

   public DimensionHandleSetFactory getDimensionHandleSetFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getDimensionHandleSetFactory();
   }

   public FederateHandleFactory getFederateHandleFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getFederateHandleFactory();
   }

   public FederateHandleSetFactory getFederateHandleSetFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getFederateHandleSetFactory();
   }

   public InteractionClassHandleFactory getInteractionClassHandleFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getInteractionClassHandleFactory();
   }

   @Override
   public InteractionClassHandleSetFactory getInteractionClassHandleSetFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getInteractionClassHandleSetFactory();
   }

   public ObjectClassHandleFactory getObjectClassHandleFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getObjectClassHandleFactory();
   }

   public ObjectInstanceHandleFactory getObjectInstanceHandleFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getObjectInstanceHandleFactory();
   }

   public ParameterHandleFactory getParameterHandleFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getParameterHandleFactory();
   }

   public ParameterHandleValueMapFactory getParameterHandleValueMapFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getParameterHandleValueMapFactory();
   }

   public RegionHandleSetFactory getRegionHandleSetFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getRegionHandleSetFactory();
   }

   public TransportationTypeHandleFactory getTransportationTypeHandleFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getTransportationTypeHandleFactory();
   }

   @Override
   public RegionHandleFactory getRegionHandleFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getRegionHandleFactory();
   }

   @Override
   public MessageRetractionHandleFactory getMessageRetractionHandleFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      return _clientConverter.getMessageRetractionHandleFactory();
   }

   public String getHLAversion()
   {
      return "IEEE 1516-202X";
   }

   public LogicalTimeFactory<?, ?> getTimeFactory()
   throws FederateNotExecutionMember, NotConnected
   {
      if (_timeFactory != null) {
         return _timeFactory;
      } else {
         throw new FederateNotExecutionMember("No time factory available");
      }
   }

}
