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

package se.pitch.oss.fedpro.common.session;

import se.pitch.oss.fedpro.common.session.buffers.GenericBuffer;
import se.pitch.oss.fedpro.common.exceptions.MessageQueueFull;
import se.pitch.oss.fedpro.client.session.msg.EncodableMessage;
import se.pitch.oss.fedpro.client.session.msg.QueueableMessage;
import net.jcip.annotations.GuardedBy;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class MessageWriter {

   private long _sessionId;

   private final Object _sessionLock;

   @GuardedBy("_sessionLock")
   private final GenericBuffer<QueueableMessage> _messageQueue;

   public MessageWriter(
         long sessionId,
         GenericBuffer<QueueableMessage> messageQueue,
         Object sessionLock)
   {
      _messageQueue = messageQueue;
      _sessionId = sessionId;
      _sessionLock = sessionLock;
   }

   protected void addMessage(
         int lastReceivedSequenceNumber,
         MessageType messageType)
   throws InterruptedException
   {
      addMessage(0, lastReceivedSequenceNumber, messageType, null);
   }

   protected void addMessage(
         long payloadSize,
         int lastReceivedSequenceNumber,
         MessageType messageType,
         EncodableMessage message)
   throws InterruptedException
   {
      CompletableFuture<byte[]> result = addRequest(payloadSize, lastReceivedSequenceNumber, messageType, message, null, false);
      if (result.isCompletedExceptionally()) {
         throw new InterruptedException();
      }
   }

   protected CompletableFuture<byte[]> addRequest(
         int lastReceivedSequenceNumber,
         MessageType messageType,
         Map<Integer, CompletableFuture<byte[]>> futuresMap)
   {
      return addRequest(0, lastReceivedSequenceNumber, messageType, null, futuresMap);
   }

   protected CompletableFuture<byte[]> addRequest(
         long payloadSize,
         int lastReceivedSequenceNumber,
         MessageType messageType,
         EncodableMessage message,
         Map<Integer, CompletableFuture<byte[]>> futuresMap)
   {
      return addRequest(payloadSize, lastReceivedSequenceNumber, messageType, message, futuresMap, true);
   }

   private CompletableFuture<byte[]> addRequest(
         long payloadSize,
         int lastReceivedSequenceNumber,
         MessageType messageType,
         EncodableMessage message,
         Map<Integer, CompletableFuture<byte[]>> futuresMap,
         boolean useFuturesMap)
   {
      synchronized (_sessionLock) {
         CompletableFuture<byte[]> result = new CompletableFuture<>();
         QueueableMessage queueableMessage = new QueueableMessage(
               payloadSize,
               lastReceivedSequenceNumber,
               _sessionId,
               messageType,
               message,
               result,
               useFuturesMap ? futuresMap : null);
         // With a rate-limiting strategy that always blocks on full queue, this insert should always succeed.
         // There is a theoretical exception when the writing thread gets interrupted while waiting to insert.
         // (See UnboundedBuffer.insert())
         boolean successfulInsert = _messageQueue.insert(queueableMessage);
         if (!successfulInsert) {
            result.completeExceptionally(new MessageQueueFull("The message queue is full."));
         }
         return result;
      }
   }

   public void setSessionId(long sessionId)
   {
      _sessionId = sessionId;
   }

}
