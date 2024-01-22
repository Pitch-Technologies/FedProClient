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

package se.pitch.oss.fedpro.client.session;

import se.pitch.oss.fedpro.common.session.MessageType;
import se.pitch.oss.fedpro.common.session.MessageWriter;
import se.pitch.oss.fedpro.common.session.SequenceNumber;
import se.pitch.oss.fedpro.common.session.buffers.GenericBuffer;
import se.pitch.oss.fedpro.client.session.msg.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ClientMessageWriter extends MessageWriter {

   public ClientMessageWriter(
         long sessionId,
         GenericBuffer<QueueableMessage> messageQueue,
         Object sessionLock)
   {
      super(sessionId, messageQueue, sessionLock);
   }

   public void writeNewSessionMessage(int protocolVersion)
   throws InterruptedException
   {
      addMessage(
            4,
            SequenceNumber.NO_SEQUENCE_NUMBER,
            MessageType.CTRL_NEW_SESSION,
            new NewSessionMessage(protocolVersion));
   }

   public CompletableFuture<byte[]> writeHeartbeatMessage(
         int lastReceivedSequenceNumber,
         Map<Integer, CompletableFuture<byte[]>> futuresMap)
   {
      return addRequest(lastReceivedSequenceNumber, MessageType.CTRL_HEARTBEAT, futuresMap);
   }

   public static EncodedMessage createResumeSessionMessage(
         long sessionId,
         int lastReceivedSequenceNumber,
         int oldestAvailableSequenceNumber)
   {
      return EncodedMessage.create(
            MessageHeader.with(
                  4 + 4,
                  SequenceNumber.NO_SEQUENCE_NUMBER,
                  sessionId,
                  lastReceivedSequenceNumber,
                  MessageType.CTRL_RESUME_REQUEST),
            new ResumeRequestMessage(lastReceivedSequenceNumber, oldestAvailableSequenceNumber));
   }

   public void writeTerminateMessage(int lastReceivedSequenceNumber)
   throws InterruptedException
   {
      addMessage(lastReceivedSequenceNumber, MessageType.CTRL_TERMINATE_SESSION);
   }

   public CompletableFuture<byte[]> writeHlaCallRequest(
         byte[] hlaServiceCallWithParams,
         int lastReceivedSequenceNumber,
         Map<Integer, CompletableFuture<byte[]>> futuresMap)
   {
      return addRequest(
            hlaServiceCallWithParams.length,
            lastReceivedSequenceNumber,
            MessageType.HLA_CALL_REQUEST,
            new HlaCallRequestMessage(hlaServiceCallWithParams),
            futuresMap);
   }

   public void writeHlaCallbackResponse(
         int responseToSequenceNumber,
         byte[] encodedResponse,
         int lastReceivedSequenceNumber)
   throws InterruptedException
   {
      addMessage(
            4 + encodedResponse.length,
            lastReceivedSequenceNumber,
            MessageType.HLA_CALLBACK_RESPONSE,
            new HlaCallbackResponseMessage(responseToSequenceNumber, encodedResponse));
   }

}
