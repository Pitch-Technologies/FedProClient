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

package se.pitch.oss.chat1516_4;

/*
 * Chat sample for Federate Protocol.
 */

import hla.rti1516_202X.*;
import hla.rti1516_202X.encoding.DecoderException;
import hla.rti1516_202X.encoding.EncoderFactory;
import hla.rti1516_202X.encoding.HLAunicodeString;
import hla.rti1516_202X.exceptions.*;
import se.pitch.oss.fedpro.client.hla.AsyncRTIambassador;
import se.pitch.oss.fedpro.client.hla.RTIambassadorEx;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class AsyncChatAdvanced extends NullFederateAmbassador {
   private static final String FEDERATION_NAME = "ChatRoom";

   private final List<String> _args;
   private final BufferedReader _systemInput;

   private RTIambassador _rtiAmbassador;
   private AsyncRTIambassador _asyncRtiAmbassador;

   private InteractionClassHandle _communicationClassHandle;
   private ParameterHandle _messageParameterHandle;
   private ParameterHandle _senderParameterHandle;
   private ObjectInstanceHandle _userInstanceHandle;
   private AttributeHandle _nameAttributeHandle;

   private String _username;
   private HLAunicodeString _nameEncoder;
   private final CompletableFuture<Void> _reservationFuture;

   private EncoderFactory _encoderFactory;

   private final Map<ObjectInstanceHandle, Participant> _knownObjects = new HashMap<>();

   private static class Participant {
      private final String _name;

      Participant(String name)
      {
         _name = name;
      }

      @Override
      public String toString()
      {
         return _name;
      }
   }

   public static void main(String[] args)
   {
      new AsyncChatAdvanced(args).tryRun();
   }

   private AsyncChatAdvanced(String[] args)
   {
      _args = new ArrayList<>(List.of(args));
      _systemInput = new BufferedReader(new InputStreamReader(System.in));
      _reservationFuture = new CompletableFuture<>();
   }

   private void tryRun()
   {
      try {
         run();
      } catch (CompletionException ce) {
         Throwable e = ce.getCause() != null ? ce.getCause() : ce;
         System.err.println("Federate failed to start: " + e);
         pressEnterToShutDown();
      } catch (RuntimeException e) {
         System.err.println("Unexpected exception: " + e);
         e.printStackTrace();
         pressEnterToShutDown();
      } catch (Exception e) {
         System.err.println("" + e);
         pressEnterToShutDown();
      }
   }

   private void pressEnterToShutDown()
   {
      try {
         System.out.println("Press <ENTER> to shutdown");
         _systemInput.readLine();
         if (_rtiAmbassador != null) {
            try {
               _rtiAmbassador.resignFederationExecution(ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST);
            } catch (RTIexception ignore) {
            }
            try {
               _rtiAmbassador.disconnect();
            } catch (RTIexception ignore) {
            }
         }
      } catch (IOException ignored) {
      }
   }

   private void run()
   throws Exception
   {

      // These operations have no asynchronous counterparts
      connectAndJoinBlockingly();

      // Get class handles
      CompletableFuture<InteractionClassHandle> setupInteractionsFuture =
            _asyncRtiAmbassador.getInteractionClassHandle("Communication")
                  .thenCompose(this::prepInteractionClass)
                  .whenComplete(this::reportStartupErrorsIfAny);

      CompletableFuture<ObjectClassHandle> setupObjectsFuture =
            _asyncRtiAmbassador.getObjectClassHandle("Participant")
                  .thenCompose(this::prepObjectClass);

      setupObjectsFuture
            .thenRun(this::getNameAndSendNameReservation);

      _reservationFuture
            .thenCompose(ignoredVoid -> _asyncRtiAmbassador.registerObjectInstanceWithName(
                  setupObjectsFuture.join(),
                  _username))
            .thenCompose(this::setUserAttributes)
            .whenComplete(this::reportStartupErrorsIfAny)
            .runAfterBoth(setupInteractionsFuture, this::interactiveLoop)
            .thenRun(this::shutDown)
            .whenComplete(this::reportOtherErrorsIfAny)
            .join();
   }

   private void connectAndJoinBlockingly()
   throws Exception
   {
      String rtiHost;
      if (!_args.isEmpty()) {
         rtiHost = _args.get(0);
      } else {
         System.out.println("Enter the FedPro server and CRC address, such as");
         System.out.println("'localhost;;localhost', 'localhost:15164;;localhost:8989', '192.168.1.62;;localhost'.");
         System.out.println("Or if FedPro server is hosted on the same machine as CRC, only the CRC address, such as");
         System.out.println("'localhost', '192.168.1.62'.");
         System.out.println();
         System.out.print("[localhost]: ");
         rtiHost = _systemInput.readLine();
         if (rtiHost.isEmpty()) {
            rtiHost = "localhost";
         }
      }

      RtiFactory rtiFactory = RtiFactoryFactory.getRtiFactory("Federate Protocol");
      try {
         _rtiAmbassador = rtiFactory.getRtiAmbassador();
         _encoderFactory = rtiFactory.getEncoderFactory();
      } catch (Exception e) {
         throw new Exception("Unable to create RTI ambassador: " + e);
      }
      _asyncRtiAmbassador = ((RTIambassadorEx) _rtiAmbassador).async();

      RtiConfiguration rtiConfiguration = RtiConfiguration.createConfiguration()
            .withRtiAddress(rtiHost);
      _rtiAmbassador.connect(this, CallbackModel.HLA_IMMEDIATE, rtiConfiguration);

      try {
         // Clean up old federation
         _rtiAmbassador.destroyFederationExecution(FEDERATION_NAME);
      } catch (FederatesCurrentlyJoined | FederationExecutionDoesNotExist ignored) {
      }

      File fddFile = new File("Chat-evolved.xml");
      try {
         _rtiAmbassador.createFederationExecution(FEDERATION_NAME, new String[] {fddFile.getPath()}, "HLAfloat64Time");
      } catch (FederationExecutionAlreadyExists ignored) {
      }

      _rtiAmbassador.joinFederationExecution("Chat", FEDERATION_NAME, new String[] {fddFile.getPath()});
   }

   private CompletableFuture<InteractionClassHandle> prepInteractionClass(InteractionClassHandle communicationClassHandle)
   {
      _communicationClassHandle = communicationClassHandle;

      // Get and save parameter handles
      CompletableFuture<ParameterHandle> messageHandleFuture =
            _asyncRtiAmbassador.getParameterHandle(_communicationClassHandle, "Message")
                  .thenApply(messageHandle -> _messageParameterHandle = messageHandle);
      CompletableFuture<ParameterHandle> senderHandleFuture =
            _asyncRtiAmbassador.getParameterHandle(_communicationClassHandle, "Sender")
                  .thenApply(senderHandle -> _senderParameterHandle = senderHandle);

      // Subscribe to and publish Communication interaction class
      CompletableFuture<Void> subscribeInteractionFuture = _asyncRtiAmbassador.subscribeInteractionClass(
            _communicationClassHandle);
      CompletableFuture<Void> publishInteractionFuture = _asyncRtiAmbassador.publishInteractionClass(
            _communicationClassHandle);

      return CompletableFuture.allOf(
                  messageHandleFuture,
                  senderHandleFuture,
                  subscribeInteractionFuture,
                  publishInteractionFuture)
            .thenApply(ignoredVoid -> communicationClassHandle);
   }

   private CompletableFuture<ObjectClassHandle> prepObjectClass(ObjectClassHandle participantClassHandle)
   {
      return _asyncRtiAmbassador.getAttributeHandle(participantClassHandle, "Name")
            .thenCompose(nameHandle -> prepObjectClassWithAttributes(participantClassHandle, nameHandle));
   }

   private CompletableFuture<ObjectClassHandle> prepObjectClassWithAttributes(
         ObjectClassHandle participantClassHandle,
         AttributeHandle nameHandle)
   {
      try {
         _nameAttributeHandle = nameHandle;
         AttributeHandleSet attributeSet = _rtiAmbassador.getAttributeHandleSetFactory().create();
         attributeSet.add(_nameAttributeHandle);

         // Subscribe to and publish Participant object class
         CompletableFuture<Void> subscribeObjectFuture =
               _asyncRtiAmbassador.subscribeObjectClassAttributes(participantClassHandle, attributeSet);
         CompletableFuture<Void> publishObjectFuture =
               _asyncRtiAmbassador.publishObjectClassAttributes(participantClassHandle, attributeSet);

         return CompletableFuture.allOf(subscribeObjectFuture, publishObjectFuture)
               .thenApply(ignoredVoid -> participantClassHandle);

      } catch (FederateNotExecutionMember | NotConnected e) {
         throw new CompletionException(e);
      }
   }

   private void getNameAndSendNameReservation()
   {
      boolean callSucceeded;
      do {
         System.out.print("Enter your name: ");
         try {
            _username = _systemInput.readLine();
         } catch (IOException e) {
            throw new CompletionException(e);
         }

         callSucceeded = _asyncRtiAmbassador.reserveObjectInstanceName(_username)
               .thenApply(ignoredVoid -> true)
               .exceptionally(e -> {
                  if (e.getCause() instanceof IllegalName) {
                     System.out.println("Illegal name. Try again.");
                     return false;
                  } else {
                     System.err.println("Unexpected exception: " + e);
                     return false;
                  }
               })
               .join();

      } while (!callSucceeded);
   }

   @Override
   public final void objectInstanceNameReservationSucceeded(String objectName)
   {
      _reservationFuture.completeAsync(() -> null);
   }

   @Override
   public final void objectInstanceNameReservationFailed(String objectName)
   {
      System.out.println("Name already taken, try again.");
      CompletableFuture.runAsync(this::getNameAndSendNameReservation)
            .exceptionally(t -> {
               _reservationFuture.completeExceptionally(t);
               return null;
            });
   }

   private CompletableFuture<Void> setUserAttributes(ObjectInstanceHandle userHandle)
   {
      try {
         _userInstanceHandle = userHandle;
         _nameEncoder = _encoderFactory.createHLAunicodeString(_username);
         AttributeHandleValueMap attributeValues = _rtiAmbassador.getAttributeHandleValueMapFactory().create(1);
         attributeValues.put(_nameAttributeHandle, _nameEncoder.toByteArray());
         return _asyncRtiAmbassador.updateAttributeValues(_userInstanceHandle, attributeValues, null);

      } catch (FederateNotExecutionMember | NotConnected e) {
         throw new CompletionException(e);
      }
   }

   private void interactiveLoop()
   {
      try {
         System.out.println("Type messages you want to send. To exit, type . <ENTER>");
         while (true) {
            System.out.print("> ");
            String message = _systemInput.readLine();

            if (message.equals(".")) {
               break;
            }
            ParameterHandleValueMap parameters = _rtiAmbassador.getParameterHandleValueMapFactory().create(1);
            HLAunicodeString messageEncoder = _encoderFactory.createHLAunicodeString();
            messageEncoder.setValue(message);
            parameters.put(_messageParameterHandle, messageEncoder.toByteArray());
            parameters.put(_senderParameterHandle, _nameEncoder.toByteArray());

            _asyncRtiAmbassador.sendInteraction(_communicationClassHandle, parameters, null)
                  .whenComplete((voidResult, throwable) -> {
                     if (throwable != null) {
                        System.err.println("Send failed: " + throwable);
                     } else {
                        System.out.print("[Sent!] ");
                     }
                  });
         }
      } catch (IOException | FederateNotExecutionMember | NotConnected e) {
         throw new CompletionException(e);
      }
   }

   private void shutDown()
   {
      try {
         _rtiAmbassador.resignFederationExecution(ResignAction.DELETE_OBJECTS_THEN_DIVEST);
         try {
            _rtiAmbassador.destroyFederationExecution(FEDERATION_NAME);
         } catch (FederatesCurrentlyJoined ignored) {
         }
         _rtiAmbassador.disconnect();
         _rtiAmbassador = null;

      } catch (RTIexception e) {
         throw new CompletionException(e);
      }
   }

   private void reportOtherErrorsIfAny(Object result, Throwable throwable)
   {
      reportErrorsIfAny(result, throwable, "Exception thrown:");
   }

   private void reportStartupErrorsIfAny(Object result, Throwable throwable)
   {
      reportErrorsIfAny(result, throwable, "Exception during startup: ");
   }

   private void reportErrorsIfAny(Object result, Throwable throwable, String message)
   {
      if (throwable != null) {
         System.err.println(message + throwable);
         if (throwable instanceof RuntimeException) {
            throwable.printStackTrace();
         }
      }
   }

   @Override
   public void receiveInteraction(
         InteractionClassHandle interactionClass,
         ParameterHandleValueMap parameterValues,
         byte[] userSuppliedTag,
         TransportationTypeHandle transportationType,
         FederateHandle producingFederate,
         RegionHandleSet optionalSentRegions)
   throws FederateInternalError
   {
      if (interactionClass.equals(_communicationClassHandle)) {
         if (!parameterValues.containsKey(_messageParameterHandle)) {
            System.err.println("Bad message received: No text.");
            return;
         }
         if (!parameterValues.containsKey(_senderParameterHandle)) {
            System.err.println("Bad message received: No sender.");
            return;
         }
         try {
            HLAunicodeString messageDecoder = _encoderFactory.createHLAunicodeString();
            HLAunicodeString senderDecoder = _encoderFactory.createHLAunicodeString();
            messageDecoder.decode(parameterValues.get(_messageParameterHandle));
            senderDecoder.decode(parameterValues.get(_senderParameterHandle));
            String message = messageDecoder.getValue();
            String sender = senderDecoder.getValue();

            System.out.println(sender + ": " + message);
            System.out.print("> ");
         } catch (DecoderException e) {
            System.err.println("Failed to decode incoming interaction");
         }
      }
   }

   @Override
   public void removeObjectInstance(
         ObjectInstanceHandle objectInstance,
         byte[] userSuppliedTag,
         FederateHandle producingFederate)
   throws FederateInternalError
   {
      Participant member = _knownObjects.remove(objectInstance);
      if (member != null) {
         System.out.println("[" + member + " has left]");
      }
   }

   @Override
   public void reflectAttributeValues(
         ObjectInstanceHandle objectInstance,
         AttributeHandleValueMap attributeValues,
         byte[] userSuppliedTag,
         TransportationTypeHandle transportationType,
         FederateHandle producingFederate,
         RegionHandleSet optionalSentRegions)
   throws FederateInternalError
   {
      if (!_knownObjects.containsKey(objectInstance)) {
         if (attributeValues.containsKey(_nameAttributeHandle)) {
            try {
               final HLAunicodeString usernameDecoder = _encoderFactory.createHLAunicodeString();
               usernameDecoder.decode(attributeValues.get(_nameAttributeHandle));
               String memberName = usernameDecoder.getValue();
               Participant member = new Participant(memberName);
               System.out.println("[" + member + " has joined]");
               System.out.print("> ");
               _knownObjects.put(objectInstance, member);
            } catch (DecoderException e) {
               System.err.println("Failed to decode incoming attribute");
            }
         }
      }
   }

   @Override
   public final void provideAttributeValueUpdate(
         ObjectInstanceHandle theObject,
         AttributeHandleSet theAttributes,
         byte[] userSuppliedTag)
   {
      if (theObject.equals(_userInstanceHandle) && theAttributes.contains(_nameAttributeHandle)) {
         try {
            AttributeHandleValueMap attributeValues = _rtiAmbassador.getAttributeHandleValueMapFactory().create(1);
            HLAunicodeString nameEncoder = _encoderFactory.createHLAunicodeString(_username);
            attributeValues.put(_nameAttributeHandle, nameEncoder.toByteArray());
            _asyncRtiAmbassador.updateAttributeValues(_userInstanceHandle, attributeValues, null)
                  .exceptionally(throwable -> {
                     System.err.println("Update failed: " + throwable.toString());
                     return null;
                  });
         } catch (RTIexception ignored) {
         }
      }
   }
}
