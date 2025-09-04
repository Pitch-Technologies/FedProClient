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

import hla.rti1516_2025.*;
import hla.rti1516_2025.encoding.DecoderException;
import hla.rti1516_2025.encoding.EncoderFactory;
import hla.rti1516_2025.encoding.HLAunicodeString;
import hla.rti1516_2025.exceptions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * HLA 4 Chat sample for Federate Protocol.
 */
class Chat extends NullFederateAmbassador {
   private final List<String> _args;
   private final BufferedReader _systemInput;

   private RTIambassador _rtiAmbassador;

   private InteractionClassHandle _messageId;
   private ParameterHandle _parameterIdText;
   private ParameterHandle _parameterIdSender;
   private ObjectInstanceHandle _userId;
   private AttributeHandle _attributeIdName;
   private AttributeHandleSet _attributeHandleSet;
   private String _username;

   private volatile boolean _reservationComplete;
   private volatile boolean _reservationSucceeded;
   private final Object _reservationSemaphore = new Object();
   private static final String FEDERATION_NAME = "ChatRoom";
   private EncoderFactory _encoderFactory;

   private final Map<ObjectInstanceHandle, Participant> _knownObjects = new HashMap<>();
   private final Map<String, Participant> _knownParticipants = new HashMap<>();

   private static class Participant {
      private final String _name;
      private final ObjectInstanceHandle _objectInstanceHandle;

      Participant(String name, ObjectInstanceHandle objectInstanceHandle)
      {
         _name = name;
         _objectInstanceHandle = objectInstanceHandle;
      }

      public ObjectInstanceHandle getObjectInstanceHandle()
      {
         return _objectInstanceHandle;
      }

      @Override
      public String toString()
      {
         return _name;
      }
   }

   public static void main(String[] args)
   {
      new Chat(args).tryRun();
   }

   private Chat(String[] args)
   {
      _args = new ArrayList<>(List.of(args));
      _systemInput = new BufferedReader(new InputStreamReader(System.in));
   }

   private void tryRun()
   {
      try {
         run();
      } catch (RuntimeException e) {
         System.err.println("Unexpected exception: " + e);
         e.printStackTrace();
         waitForEnterThenExit(1);
      } catch (Exception e) {
         System.err.println("" + e);
         waitForEnterThenExit(1);
      }
   }

   private void waitForEnterThenExit(int exitStatus)
   {
      try {
         System.out.println("Press <ENTER> to exit");
         _systemInput.readLine();
      } catch (IOException ignored) {
      }
      System.exit(exitStatus);
   }

   private void run()
   throws Exception
   {
      String rtiHost;

      if (!_args.isEmpty()) {
         rtiHost = _args.get(0);
      } else {
         System.out.println("Enter the Federate Protocol server address, such as");
         System.out.println("'localhost', 'localhost:15164', '192.168.1.62'");
         System.out.println("If no value is provided, defaults will be used.");
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

      RtiConfiguration rtiConfiguration = RtiConfiguration.createConfiguration()
            .withRtiAddress(rtiHost);
      try {
         _rtiAmbassador.connect(this, CallbackModel.HLA_IMMEDIATE, rtiConfiguration);
      } catch (Unauthorized e) {
         System.err.println(e.getMessage());
      }

      try {
         // Clean up old federation
         _rtiAmbassador.destroyFederationExecution(FEDERATION_NAME);
      } catch (FederatesCurrentlyJoined | FederationExecutionDoesNotExist ignored) {
      }
      File fddFile = new File("Chat-hla4.xml");
      try {
         _rtiAmbassador.createFederationExecution(
               FEDERATION_NAME,
               new String[] {fddFile.getPath()},
               "HLAfloat64Time");
      } catch (FederationExecutionAlreadyExists ignored) {
      }

      _rtiAmbassador.joinFederationExecution("Chat", FEDERATION_NAME, new String[] {fddFile.getPath()});

      // Subscribe and publish interactions
      _messageId = _rtiAmbassador.getInteractionClassHandle("Communication");
      _parameterIdText = _rtiAmbassador.getParameterHandle(_messageId, "Message");
      _parameterIdSender = _rtiAmbassador.getParameterHandle(_messageId, "Sender");

      _rtiAmbassador.subscribeInteractionClass(_messageId);
      _rtiAmbassador.publishInteractionClass(_messageId);

      // Subscribe and publish objects
      ObjectClassHandle participantId = _rtiAmbassador.getObjectClassHandle("Participant");
      _attributeIdName = _rtiAmbassador.getAttributeHandle(participantId, "Name");

      InteractionClassHandleSet interactions = _rtiAmbassador.getInteractionClassHandleSetFactory().create();
      interactions.add(_messageId);

      _rtiAmbassador.subscribeObjectClassDirectedInteractions(participantId, interactions);
      _rtiAmbassador.publishObjectClassDirectedInteractions(participantId, interactions);

      _attributeHandleSet = _rtiAmbassador.getAttributeHandleSetFactory().create();
      _attributeHandleSet.add(_attributeIdName);

      _rtiAmbassador.subscribeObjectClassAttributes(participantId, _attributeHandleSet);
      _rtiAmbassador.publishObjectClassAttributes(participantId, _attributeHandleSet);

      // Reserve object instance name and register object instance
      do {
         System.out.print("Enter your name: ");
         _username = _systemInput.readLine();

         try {
            _reservationComplete = false;
            _rtiAmbassador.reserveObjectInstanceName(_username);
            synchronized (_reservationSemaphore) {
               // Wait for response from RTI
               while (!_reservationComplete) {
                  try {
                     _reservationSemaphore.wait();
                  } catch (InterruptedException ignored) {
                  }
               }
            }
            if (!_reservationSucceeded) {
               System.out.println("Name already taken, try again.");
            }
         } catch (IllegalName e) {
            System.out.println("Illegal name. Try again.");
         } catch (RTIexception e) {
            System.err.println("RTI exception when reserving name: " + e.getMessage());
            return;
         }
      } while (!_reservationSucceeded);

      _userId = _rtiAmbassador.registerObjectInstance(participantId, _username);
      AttributeHandleValueMap attributeValues = _rtiAmbassador.getAttributeHandleValueMapFactory().create(1);
      HLAunicodeString nameEncoder = _encoderFactory.createHLAunicodeString(_username);
      attributeValues.put(_attributeIdName, nameEncoder.toByteArray());
      _rtiAmbassador.updateAttributeValues(_userId, attributeValues, null);

      System.out.println("Type messages you want to send.");
      System.out.println("To send a private message, type \"<Participant Name>: <Message>\"");
      System.out.println("To exit, type . <ENTER>");
      while (true) {
         System.out.print("> ");
         String message = _systemInput.readLine();
         if (message == null || message.equals(".")) {
            break;
         }

         String[] splitMessage = message.split(": ", 2);

         Participant recipient = null;
         if (splitMessage.length == 2) {
            String recipientName = splitMessage[0];

            recipient = _knownParticipants.get(recipientName);

            if (recipient == null) {
               System.err.println("Unknown receiver of private message \"" + recipientName + "\"");
               continue;
            }

            message = splitMessage[1];
         }

         ParameterHandleValueMap parameters = _rtiAmbassador.getParameterHandleValueMapFactory().create(1);
         HLAunicodeString messageEncoder = _encoderFactory.createHLAunicodeString();
         messageEncoder.setValue(message);
         parameters.put(_parameterIdText, messageEncoder.toByteArray());
         parameters.put(_parameterIdSender, nameEncoder.toByteArray());

         if (recipient == null) {
            _rtiAmbassador.sendInteraction(_messageId, parameters, null);
         } else {
            _rtiAmbassador.sendDirectedInteraction(
                  _messageId,
                  recipient.getObjectInstanceHandle(),
                  parameters,
                  null);
         }
      }

      _rtiAmbassador.resignFederationExecution(ResignAction.DELETE_OBJECTS_THEN_DIVEST);
      try {
         _rtiAmbassador.destroyFederationExecution(FEDERATION_NAME);
      } catch (FederatesCurrentlyJoined ignored) {
      }
      _rtiAmbassador.disconnect();
      _rtiAmbassador = null;
   }

   private void printMessage(
         InteractionClassHandle interactionClass,
         ParameterHandleValueMap parameterValues,
         boolean directedInteraction)
   {
      if (interactionClass.equals(_messageId)) {
         if (!parameterValues.containsKey(_parameterIdText)) {
            System.err.println("Bad message received: No text.");
            return;
         }
         if (!parameterValues.containsKey(_parameterIdSender)) {
            System.err.println("Bad message received: No sender.");
            return;
         }
         try {
            HLAunicodeString messageDecoder = _encoderFactory.createHLAunicodeString();
            HLAunicodeString senderDecoder = _encoderFactory.createHLAunicodeString();
            messageDecoder.decode(parameterValues.get(_parameterIdText));
            senderDecoder.decode(parameterValues.get(_parameterIdSender));
            String message = messageDecoder.getValue();
            String sender = senderDecoder.getValue();

            if (directedInteraction) {
               System.out.println("Private message from " + sender + ": " + message);
            } else {
               System.out.println(sender + ": " + message);
            }
            System.out.print("> ");
         } catch (DecoderException e) {
            System.err.println("Failed to decode incoming interaction");
         }
      }
   }

   @Override
   public void receiveDirectedInteraction(
         InteractionClassHandle interactionClass,
         ObjectInstanceHandle objectInstance,
         ParameterHandleValueMap parameterValues,
         byte[] userSuppliedTag,
         TransportationTypeHandle transportationType,
         FederateHandle producingFederate)
   throws FederateInternalError
   {
      printMessage(interactionClass, parameterValues, true);
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
      printMessage(interactionClass, parameterValues, false);
   }

   @Override
   public final void objectInstanceNameReservationSucceeded(String objectName)
   {
      synchronized (_reservationSemaphore) {
         _reservationComplete = true;
         _reservationSucceeded = true;
         _reservationSemaphore.notifyAll();
      }
   }

   @Override
   public final void objectInstanceNameReservationFailed(String objectName)
   {
      synchronized (_reservationSemaphore) {
         _reservationComplete = true;
         _reservationSucceeded = false;
         _reservationSemaphore.notifyAll();
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
         _knownParticipants.remove(member._name);
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
         if (attributeValues.containsKey(_attributeIdName)) {
            try {
               final HLAunicodeString usernameDecoder = _encoderFactory.createHLAunicodeString();
               usernameDecoder.decode(attributeValues.get(_attributeIdName));
               String memberName = usernameDecoder.getValue();
               Participant member = new Participant(memberName, objectInstance);
               System.out.println("[" + member + " has joined]");
               System.out.print("> ");
               _knownObjects.put(objectInstance, member);
               _knownParticipants.put(memberName, member);
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
      if (theObject.equals(_userId) && theAttributes.contains(_attributeIdName)) {
         try {
            AttributeHandleValueMap attributeValues = _rtiAmbassador.getAttributeHandleValueMapFactory().create(1);
            HLAunicodeString nameEncoder = _encoderFactory.createHLAunicodeString(_username);
            attributeValues.put(_attributeIdName, nameEncoder.toByteArray());
            _rtiAmbassador.updateAttributeValues(_userId, attributeValues, null);
         } catch (RTIexception ignored) {
         }
      }
   }

   @Override
   public void discoverObjectInstance(
         ObjectInstanceHandle objectInstance,
         ObjectClassHandle objectClass,
         String objectInstanceName,
         FederateHandle producingFederate)
   throws FederateInternalError
   {
      try {
         _rtiAmbassador.requestAttributeValueUpdate(objectInstance, _attributeHandleSet, null);
      } catch (RTIexception ignored) {
      }
   }
}
