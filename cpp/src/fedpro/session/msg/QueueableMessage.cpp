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

#include "QueueableMessage.h"

#include <session/LogUtil.h>

#include <spdlog/spdlog.h>

#include <future>
#include <memory>
#include <string>

namespace FedPro
{
   QueueableMessage::QueueableMessage(
         uint32_t payloadSize,
         int32_t lastReceivedSequenceNumber,
         uint64_t sessionId,
         MessageType messageType,
         std::unique_ptr<EncodableMessage> message,
         std::unique_ptr<std::promise<ByteSequence>> promise,
         ConcurrentHashMap<int32_t, std::promise<ByteSequence>> * futuresMap) noexcept
         : _payloadSize{payloadSize},
           _lastReceivedSequenceNumber{lastReceivedSequenceNumber},
           _sessionId{sessionId},
           _messageType{messageType},
           _message{std::move(message)},
           _promise{std::move(promise)},
           _futuresMap{futuresMap}
   {
   }

   QueueableMessage::QueueableMessage(QueueableMessage && rhs) noexcept
         : _payloadSize{},
           _lastReceivedSequenceNumber{},
           _sessionId{},
           _messageType{MessageType::UNKNOWN},
           _message{},
           _promise{},
           _futuresMap{nullptr}
   {
      swap(rhs);
   }

   void QueueableMessage::swap(QueueableMessage & rhs) noexcept
   {
      if (this == &rhs) {
         return;
      }
      std::swap(this->_payloadSize, rhs._payloadSize);
      std::swap(this->_lastReceivedSequenceNumber, rhs._lastReceivedSequenceNumber);
      std::swap(this->_sessionId, rhs._sessionId);
      std::swap(this->_messageType, rhs._messageType);
      std::swap(this->_message, rhs._message);
      std::swap(this->_promise, rhs._promise);
      std::swap(this->_futuresMap, rhs._futuresMap);
   }

   QueueableMessage & QueueableMessage::operator=(QueueableMessage && rhs) noexcept
   {
      swap(rhs);
      return *this;
   }

   bool QueueableMessage::isHlaResponse() const noexcept
   {
      return MessageTypeHelper::isHlaResponse(_messageType);
   }

   EncodedMessage QueueableMessage::createEncodedMessage(int32_t nextSequenceNumber)
   {
      MessageHeader header = MessageHeader::with(
            _payloadSize, nextSequenceNumber, _sessionId, _lastReceivedSequenceNumber, _messageType);
      EncodedMessage msg = EncodedMessage::create(header, _message.get());

      if (_futuresMap) {
         // NOTE: It's very important that we add the future to our state *before* sending the request. Otherwise,
         // the response may happen so fast that the future is not found when it gets handled.
         // It's also important that we increment the sequence number at the atomically same time as we put the message
         // on the message queue, since they must be sent in the correct order.
         if (_promise) {
            _futuresMap->moveAndInsert(nextSequenceNumber, std::move(*_promise));
            _promise = nullptr;
         } else {
            SPDLOG_WARN(
                  "{}: An EncodedMessage has already been created by this QueueableMessage. There may only be one for each.",
                  LogUtil::logPrefix(_sessionId, LogUtil::CLIENT_PREFIX));
         }
      }

      return msg;
   }

   std::string QueueableMessage::toString() const noexcept
   {
      std::string result = "QueueableMessage {\n";
      result += "\tPayload size = " + std::to_string(_payloadSize) + ",\n";
      result += "\tLast received Sequence Number = " + std::to_string(_lastReceivedSequenceNumber) + ",\n";
      result += "\tClient session ID = " + std::to_string(_sessionId) + ",\n";
      result += "\tMessage type = " + MessageTypeHelper::asString(_messageType) + ",\n";
      result += "}";
      return result;
   }

   std::unique_ptr<std::promise<ByteSequence>> QueueableMessage::removeResponseFuture()
   {
      std::unique_ptr<std::promise<ByteSequence>> promise{std::move(_promise)};
      _promise.reset();
      return promise;
   }

   std::ostream & operator<<(
         std::ostream & os,
         const QueueableMessage & message)
   {
      return os << message.toString();
   }

} // FedPro
