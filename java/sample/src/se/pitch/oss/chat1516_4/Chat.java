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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Chat extends NullFederateAmbassador {
   private RTIambassador _rtiAmbassador;
   private final List<String> _args;
   private InteractionClassHandle _messageId;
   private ParameterHandle _parameterIdText;
   private ParameterHandle _parameterIdSender;
   private ObjectInstanceHandle _userId;
   private AttributeHandle _attributeIdName;
   private String _username;

   private volatile boolean _reservationComplete;
   private volatile boolean _reservationSucceeded;
   private final Object _reservationSemaphore = new Object();
   private static final String FEDERATION_NAME = "ChatRoom";
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
      new Chat(args).run();
   }

   private Chat(String[] args)
   {
      _args = new ArrayList<>(List.of(args));
   }

   private void run()
   {
      try {
         BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

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
            rtiHost = in.readLine();
            if (rtiHost.isEmpty()) {
               rtiHost = "localhost";
            }
         }

         try {
            RtiFactory rtiFactory = RtiFactoryFactory.getRtiFactory("Federate Protocol");
            _rtiAmbassador = rtiFactory.getRtiAmbassador();
            _encoderFactory = rtiFactory.getEncoderFactory();
         } catch (Exception e) {
            System.out.println("Unable to create RTI ambassador.");
            return;
         }

         RtiConfiguration rtiConfiguration = RtiConfiguration.createConfiguration().withRtiAddress(rtiHost);
         _rtiAmbassador.connect(this, CallbackModel.HLA_IMMEDIATE, rtiConfiguration);

         try {
            // Clean up old federation
            _rtiAmbassador.destroyFederationExecution(FEDERATION_NAME);
         } catch (FederatesCurrentlyJoined | FederationExecutionDoesNotExist ignored) {
         }
         File fddFile = new File("Chat-evolved.xml");
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

         AttributeHandleSet attributeSet = _rtiAmbassador.getAttributeHandleSetFactory().create();
         attributeSet.add(_attributeIdName);

         _rtiAmbassador.subscribeObjectClassAttributes(participantId, attributeSet);
         _rtiAmbassador.publishObjectClassAttributes(participantId, attributeSet);

         // Reserve object instance name and register object instance
         do {
            System.out.print("Enter your name: ");
            _username = in.readLine();

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
               System.out.println("RTI exception when reserving name: " + e.getMessage());
               return;
            }
         } while (!_reservationSucceeded);

         _userId = _rtiAmbassador.registerObjectInstance(participantId, _username);
         AttributeHandleValueMap attributeValues = _rtiAmbassador.getAttributeHandleValueMapFactory().create(1);
         HLAunicodeString nameEncoder = _encoderFactory.createHLAunicodeString(_username);
         attributeValues.put(_attributeIdName, nameEncoder.toByteArray());
         _rtiAmbassador.updateAttributeValues(_userId, attributeValues, null);

         System.out.println("Type messages you want to send. To exit, type . <ENTER>");
         while (true) {
            System.out.print("> ");
            String message = in.readLine();

            if (message.equals(".")) {
               break;
            }
            ParameterHandleValueMap parameters = _rtiAmbassador.getParameterHandleValueMapFactory().create(1);
            HLAunicodeString messageEncoder = _encoderFactory.createHLAunicodeString();
            messageEncoder.setValue(message);
            parameters.put(_parameterIdText, messageEncoder.toByteArray());
            parameters.put(_parameterIdSender, nameEncoder.toByteArray());

            _rtiAmbassador.sendInteraction(_messageId, parameters, null);
         }

         _rtiAmbassador.resignFederationExecution(ResignAction.DELETE_OBJECTS_THEN_DIVEST);
         try {
            _rtiAmbassador.destroyFederationExecution(FEDERATION_NAME);
         } catch (FederatesCurrentlyJoined ignored) {
         }
         _rtiAmbassador.disconnect();
         _rtiAmbassador = null;
      } catch (Exception e) {
         e.printStackTrace();
         try {
            System.out.println("Press <ENTER> to shutdown");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            in.readLine();
         } catch (IOException ignored) {
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
      if (interactionClass.equals(_messageId)) {
         if (!parameterValues.containsKey(_parameterIdText)) {
            System.out.println("Bad message received: No text.");
            return;
         }
         if (!parameterValues.containsKey(_parameterIdSender)) {
            System.out.println("Bad message received: No sender.");
            return;
         }
         try {
            HLAunicodeString messageDecoder = _encoderFactory.createHLAunicodeString();
            HLAunicodeString senderDecoder = _encoderFactory.createHLAunicodeString();
            messageDecoder.decode(parameterValues.get(_parameterIdText));
            senderDecoder.decode(parameterValues.get(_parameterIdSender));
            String message = messageDecoder.getValue();
            String sender = senderDecoder.getValue();

            System.out.println(sender + ": " + message);
            System.out.print("> ");
         } catch (DecoderException e) {
            System.out.println("Failed to decode incoming interaction");
         }
      }
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
               Participant member = new Participant(memberName);
               System.out.println("[" + member + " has joined]");
               System.out.print("> ");
               _knownObjects.put(objectInstance, member);
            } catch (DecoderException e) {
               System.out.println("Failed to decode incoming attribute");
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
}
