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

package se.pitch.oss.fedpro.client.session.msg;

import se.pitch.oss.fedpro.common.session.MessageType;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class QueueableMessage {

   private final long _payloadSize;
   private final int _lastReceivedSequenceNumber;
   private final long _sessionId;
   private final MessageType _messageType;
   private final EncodableMessage _message;
   private final CompletableFuture<byte[]> _future;
   private final Map<Integer, CompletableFuture<byte[]>> _futuresMap;

   public QueueableMessage(
         long payloadSize,
         int lastReceivedSequenceNumber,
         long sessionId,
         MessageType messageType,
         EncodableMessage message,
         CompletableFuture<byte[]> future,
         Map<Integer, CompletableFuture<byte[]>> futuresMap)
   {
      _payloadSize = payloadSize;
      _lastReceivedSequenceNumber = lastReceivedSequenceNumber;
      _sessionId = sessionId;
      _messageType = messageType;
      _message = message;
      _future = future;
      _futuresMap = futuresMap;
   }

   public boolean isHlaResponse()
   {
      return _messageType.isHlaResponse();
   }

   public EncodedMessage createEncodedMessage(int nextSequenceNumber)
   {
      EncodedMessage msg = EncodedMessage.create(
            MessageHeader.with(_payloadSize, nextSequenceNumber, _sessionId, _lastReceivedSequenceNumber, _messageType),
            _message);

      // NOTE: It's very important that we add the future to our state *before* sending the request. Otherwise,
      //   the response may happen so fast that the future is not found when it gets handled.
      //   It's also important that we increment the sequence number at the atomically same time as we put the message
      //   on the message queue, since they must be sent in the correct order.
      if (_futuresMap != null) {
         _futuresMap.put(nextSequenceNumber, _future);
      }
      return msg;
   }
}
