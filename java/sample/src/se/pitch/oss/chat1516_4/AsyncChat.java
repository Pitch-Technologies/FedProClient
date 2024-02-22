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

class AsyncChat extends NullFederateAmbassador {
   private static final String FEDERATION_NAME = "ChatRoom";

   private final List<String> _args;
   private final BufferedReader _systemInput;

   private RTIambassador _rtiAmbassador;

   private InteractionClassHandle _communicationClassHandle;
   private ParameterHandle _messageParameterHandle;
   private ParameterHandle _senderParameterHandle;
   private ObjectInstanceHandle _userInstanceHandle;
   private AttributeHandle _nameAttributeHandle;

   private String _username;

   private volatile boolean _reservationComplete;
   private volatile boolean _reservationSucceeded;
   private final Object _reservationSemaphore = new Object();

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
      new AsyncChat(args).tryRun();
   }

   private AsyncChat(String[] args)
   {
      _args = new ArrayList<>(List.of(args));
      _systemInput = new BufferedReader(new InputStreamReader(System.in));
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

      // Get the asynchronous version of the RTI ambassador
      AsyncRTIambassador asyncRtiAmbassador = ((RTIambassadorEx) _rtiAmbassador).async();

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

      // Subscribe to and publish Communication interaction class
      _communicationClassHandle = _rtiAmbassador.getInteractionClassHandle("Communication");
      _messageParameterHandle = _rtiAmbassador.getParameterHandle(_communicationClassHandle, "Message");
      _senderParameterHandle = _rtiAmbassador.getParameterHandle(_communicationClassHandle, "Sender");

      _rtiAmbassador.subscribeInteractionClass(_communicationClassHandle);
      _rtiAmbassador.publishInteractionClass(_communicationClassHandle);

      // Subscribe to and publish Participant object class
      ObjectClassHandle participantId = _rtiAmbassador.getObjectClassHandle("Participant");
      _nameAttributeHandle = _rtiAmbassador.getAttributeHandle(participantId, "Name");

      AttributeHandleSet attributeSet = _rtiAmbassador.getAttributeHandleSetFactory().create();
      attributeSet.add(_nameAttributeHandle);

      _rtiAmbassador.subscribeObjectClassAttributes(participantId, attributeSet);
      _rtiAmbassador.publishObjectClassAttributes(participantId, attributeSet);

      // Reserve your user name as an object instance name
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
            System.out.println("RTI exception when reserving name: " + e.getMessage());
            return;
         }
      } while (!_reservationSucceeded);

      // Register your user object instance
      _userInstanceHandle = _rtiAmbassador.registerObjectInstance(participantId, _username);
      AttributeHandleValueMap attributeValues = _rtiAmbassador.getAttributeHandleValueMapFactory().create(1);
      HLAunicodeString nameEncoder = _encoderFactory.createHLAunicodeString(_username);
      attributeValues.put(_nameAttributeHandle, nameEncoder.toByteArray());

      // Calling join() on a CompletableFuture means execution is blocked until the call has completed, either
      // successfully or with an exception, which is then thrown by join(). Calling join on this future right after the
      // asynchronous call to updateAttributeValues() is equivalent to calling the standard, blocking version of the method.
      CompletableFuture<Void> valuesUpdatedFuture =
            asyncRtiAmbassador.updateAttributeValues(_userInstanceHandle, attributeValues, null);
      valuesUpdatedFuture.join();

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
         parameters.put(_senderParameterHandle, nameEncoder.toByteArray());

         // Handle the result of sendInteraction asynchronously.
         asyncRtiAmbassador.sendInteraction(_communicationClassHandle, parameters, null)
               .whenComplete((voidResult, throwable) -> {
                  if (throwable != null) {
                     System.err.println("Send failed: " + throwable);
                  } else {
                     System.out.print("[Sent!] ");
                  }
               });
      }

      _rtiAmbassador.resignFederationExecution(ResignAction.DELETE_OBJECTS_THEN_DIVEST);
      try {
         _rtiAmbassador.destroyFederationExecution(FEDERATION_NAME);
      } catch (FederatesCurrentlyJoined ignored) {
      }
      _rtiAmbassador.disconnect();
      _rtiAmbassador = null;
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
            _rtiAmbassador.updateAttributeValues(_userInstanceHandle, attributeValues, null);
         } catch (RTIexception ignored) {
         }
      }
   }
}
