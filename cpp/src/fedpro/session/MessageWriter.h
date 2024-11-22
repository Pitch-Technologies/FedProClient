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

#pragma once

#include "buffers/RoundRobinBuffer.h"
#include "ConcurrentHashMap.h"
#include <fedpro/Aliases.h>
#include "msg/EncodedMessage.h"
#include "msg/HlaCallbackResponseMessage.h"
#include "msg/HlaCallRequestMessage.h"
#include "msg/NewSessionMessage.h"
#include "msg/QueueableMessage.h"
#include "msg/ResumeRequestMessage.h"

#include <cstdint>
#include <future>

namespace FedPro
{
   // Not thread-safe
   class MessageWriter
   {
   public:
      MessageWriter(
            uint64_t sessionId,
            std::shared_ptr<RoundRobinBuffer<QueueableMessage>> messageQueue);

      static EncodedMessage createNewSessionMessage();

      /**
       * @param [out] futuresMap Caller is responsible for keeping the map alive. Valid as long as SessionImpl lives.
       */
      std::future<ByteSequence> writeHeartbeatMessage(
            int32_t lastReceivedSequenceNumber,
            ConcurrentHashMap<int32_t, std::promise<ByteSequence>> * futuresMap);

      static EncodedMessage createResumeSessionMessage(
            uint64_t sessionId,
            int32_t lastReceivedSequenceNumber,
            int32_t oldestAvailableSequenceNumber);

      void writeTerminateMessage(int32_t lastReceivedSequenceNumber);

      /**
       * @param [out] futuresMap Caller is responsible for keeping the map alive. Valid as long as SessionImpl lives.
       */
      std::future<ByteSequence> writeHlaCallRequest(
            const ByteSequence & hlaServiceCallWithParams,
            int32_t lastReceivedSequenceNumber,
            ConcurrentHashMap<int32_t, std::promise<ByteSequence>> * futuresMap);

      void writeHlaCallbackResponse(
            int32_t responseToSequenceNumber,
            const ByteSequence & encodedResponse,
            int32_t lastReceivedSequenceNumber);

      void setSessionId(uint64_t sessionId);

      void disableMessageQueueInsertion();

   private:
      uint64_t _sessionId;
      std::shared_ptr<RoundRobinBuffer<QueueableMessage>> _messageQueue;

      void addMessage(
            int32_t lastReceivedSequenceNumber,
            MessageType messageType);

      void addMessage(
            uint64_t payloadSize,
            int32_t lastReceivedSequenceNumber,
            MessageType messageType,
            std::unique_ptr<EncodableMessage> payload);

      std::future<ByteSequence> addRequest(
            int32_t lastReceivedSequenceNumber,
            MessageType messageType,
            ConcurrentHashMap<int32_t, std::promise<ByteSequence>> * futuresMap);

      std::future<ByteSequence> addRequest(
            uint64_t payloadSize,
            int32_t lastReceivedSequenceNumber,
            MessageType messageType,
            std::unique_ptr<EncodableMessage> payload,
            ConcurrentHashMap<int32_t, std::promise<ByteSequence>> * futuresMap);

   };

} // FedPro
