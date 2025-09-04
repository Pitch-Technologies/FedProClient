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

#include "buffers/GenericBuffer.h"
#include "buffers/HistoryBuffer.h"
#include <fedpro/Session.h>
#include "msg/EncodedMessage.h"
#include "msg/QueueableMessage.h"
#include "SequenceNumber.h"

#include <atomic>
#include <cstdint>
#include <functional>
#include <string>

namespace FedPro
{
   class Socket;

   class SocketWriter
   {
   public:

      class Listener
      {
      public:
         virtual void exceptionOnWrite(const std::exception & message) = 0;

         virtual void messageSent(int32_t sequenceNumber, bool isControl) = 0;

         virtual ~Listener() = default;
      };

      static std::unique_ptr<SocketWriter> createSocketWriter(
            uint64_t sessionId,
            std::unique_ptr<Listener> listener,
            std::unique_ptr<HistoryBuffer> messageQueue,
            std::shared_ptr<Socket> socket,
            int32_t expectedNextSequenceNumber);

      static std::unique_ptr<SocketWriter> createDirectSocketWriter(
            uint64_t sessionId,
            std::shared_ptr<Socket> socket);

      bool isDirectOnly() const;

      void socketWriterLoop();

      void writeNextMessage();

      void writeDirectMessage(const EncodedMessage & message);

      void enableWriterLoop();

      void stopWriterLoop();

      bool waitForEmptyQueue(FedProDuration timeout);

      void rewindToFirstMessage();

      void rewindToSequenceNumberAfter(const SequenceNumber & sequenceNumber);

      void setSessionId(uint64_t sessionId);

      void setNewSocket(std::shared_ptr<Socket> socket);

      int32_t getOldestAddedSequenceNumber();

      int32_t getNewestAddedSequenceNumber();

   private:

      std::string _sessionIdString;
      std::unique_ptr<Listener> _listener;
      Session::MessageSentListener _onMessageSent;
      // TODO (from the java code): If possible, inherit from this class to create another SocketWriter based on BlockingQueue,
      //  to create back pressure in federate.
      std::unique_ptr<HistoryBuffer> _historyBuffer;
      std::shared_ptr<Socket> _socket;
      SequenceNumber _expectedNextSequenceNumber;
      int32_t _lastSequenceNumber{SequenceNumber::NO_SEQUENCE_NUMBER};
      std::mutex _threadMutex{};
      std::condition_variable _condition{};
      bool _run{false};

      SocketWriter(
            uint64_t sessionId,
            std::unique_ptr<Listener> listener,
            std::unique_ptr<HistoryBuffer> messageQueue,
            std::shared_ptr<Socket> socket,
            int32_t expectedNextSequenceNumber);

      SocketWriter(
            uint64_t sessionId,
            std::shared_ptr<Socket> socket,
            int32_t expectedNextSequenceNumber);

      void writeMessage(const EncodedMessage & message);

      void logSentMessage(const EncodedMessage & message) const;

      std::string logPrefix() const noexcept;

   };

} // FedPro
