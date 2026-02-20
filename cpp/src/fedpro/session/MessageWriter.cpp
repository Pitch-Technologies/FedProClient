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

#include "MessageWriter.h"

#include <fedpro/IOException.h>
#include <fedpro/FedProExceptions.h>
#include "msg/ByteInfo.h"
#include "SequenceNumber.h"

namespace FedPro
{
   MessageWriter::MessageWriter(
         uint64_t sessionId,
         std::shared_ptr<RoundRobinBuffer<QueueableMessage>> messageQueue)
         : _sessionId{sessionId},
           _messageQueue{std::move(messageQueue)}
   {
   }

   EncodedMessage MessageWriter::createNewSessionMessage()
   {
      NewSessionMessage message{NewSessionMessage::FEDERATE_PROTOCOL_VERSION};
      return EncodedMessage::create(
            MessageHeader::with(
                  INT32_SIZE,
                  SequenceNumber::INITIAL_SEQUENCE_NUMBER,
                  MessageHeader::NO_SESSION_ID,
                  SequenceNumber::NO_SEQUENCE_NUMBER,
                  MessageType::CTRL_NEW_SESSION),
                  &message);
   }

   std::future<ByteSequence> MessageWriter::writeHeartbeatMessage(
         int32_t lastReceivedSequenceNumber,
         ConcurrentHashMap<int32_t, std::promise<ByteSequence>> * futuresMap)
   {
      return addRequest(
            lastReceivedSequenceNumber, MessageType::CTRL_HEARTBEAT, futuresMap);
   }

   EncodedMessage MessageWriter::createResumeSessionMessage(
         uint64_t sessionId,
         int32_t lastReceivedSequenceNumber,
         int32_t oldestAvailableSequenceNumber)
   {
      ResumeRequestMessage message{lastReceivedSequenceNumber, oldestAvailableSequenceNumber};
      return EncodedMessage::create(
            MessageHeader::with(
                  INT32_SIZE * 2,
                  SequenceNumber::NO_SEQUENCE_NUMBER,
                  sessionId,
                  lastReceivedSequenceNumber,
                  MessageType::CTRL_RESUME_REQUEST), &message);
   }

   void MessageWriter::writeTerminateMessage(int32_t lastReceivedSequenceNumber)
   {
      addMessage(lastReceivedSequenceNumber, MessageType::CTRL_TERMINATE_SESSION);
   }

   std::future<ByteSequence> MessageWriter::writeHlaCallRequest(
         const ByteSequence & hlaServiceCallWithParams,
         int32_t lastReceivedSequenceNumber,
         ConcurrentHashMap<int32_t, std::promise<ByteSequence>> * futuresMap)
   {
      if (hlaServiceCallWithParams.size() > std::numeric_limits<uint32_t>::max()) {
         throw IOException{"writeHlaCallRequest fail. Request size exceed payload limit. "};
      }
      return addRequest(
            static_cast<uint32_t>(hlaServiceCallWithParams.size()),
            lastReceivedSequenceNumber,
            MessageType::HLA_CALL_REQUEST,
            std::make_unique<HlaCallRequestMessage>(hlaServiceCallWithParams),
            futuresMap);
   }

   void MessageWriter::writeHlaCallbackResponse(
         int32_t responseToSequenceNumber,
         const ByteSequence & encodedResponse,
         int32_t lastReceivedSequenceNumber)
   {
      if (encodedResponse.size() > std::numeric_limits<uint32_t>::max() - INT32_SIZE) {
         throw IOException{"writeHlaCallbackResponse fail. Response size exceed payload limit. "};
      }
      addMessage(
            static_cast<uint32_t>(INT32_SIZE + encodedResponse.size()),
            lastReceivedSequenceNumber,
            MessageType::HLA_CALLBACK_RESPONSE,
            std::make_unique<HlaCallbackResponseMessage>(responseToSequenceNumber, encodedResponse));
   }

   void MessageWriter::setSessionId(uint64_t sessionId)
   {
      _sessionId = sessionId;
   }

   void MessageWriter::disableMessageQueueInsertion()
   {
      if (_messageQueue) {
         _messageQueue->interrupt(true);
      }
   }

   // private functions:

   void MessageWriter::addMessage(
         int32_t lastReceivedSequenceNumber,
         MessageType messageType)
   {
      addMessage(0, lastReceivedSequenceNumber, messageType, nullptr);
   }

   void MessageWriter::addMessage(
         uint32_t payloadSize,
         int32_t lastReceivedSequenceNumber,
         MessageType messageType,
         std::unique_ptr<EncodableMessage> payload)
   {
      QueueableMessage queueableMessage
            {payloadSize, lastReceivedSequenceNumber, _sessionId, messageType, std::move(payload), nullptr, nullptr};
      bool successfulInsert = _messageQueue->insert(std::move(queueableMessage));
      if (!successfulInsert) {
         throw MessageQueueFull{"The message queue is full."};
      }
   }

   std::future<ByteSequence> MessageWriter::addRequest(
         int32_t lastReceivedSequenceNumber,
         MessageType messageType,
         ConcurrentHashMap<int32_t, std::promise<ByteSequence>> * futuresMap)
   {
      return addRequest(0, lastReceivedSequenceNumber, messageType, nullptr, futuresMap);
   }

   std::future<ByteSequence> MessageWriter::addRequest(
         uint32_t payloadSize,
         int32_t lastReceivedSequenceNumber,
         MessageType messageType,
         std::unique_ptr<EncodableMessage> payload,
         ConcurrentHashMap<int32_t, std::promise<ByteSequence>> * futuresMap)
   {
      auto promise = std::make_unique<std::promise<ByteSequence>>();
      std::future<ByteSequence> result = promise->get_future();
      QueueableMessage queueableMessage
            {payloadSize, lastReceivedSequenceNumber, _sessionId, messageType, std::move(payload), std::move(promise), futuresMap};
      bool successfulInsert = _messageQueue->insert(std::move(queueableMessage));
      if (successfulInsert) {
         return result;
      } else {
         // The above "promise" has been moved and may be empty,
         // so create a new promise to propagate the exception.
         auto failedPromise = std::make_unique<std::promise<ByteSequence>>();
         failedPromise->set_exception(std::make_exception_ptr(MessageQueueFull{"The message queue is full."}));
         return failedPromise->get_future();
      }
   }

} // FedPro
