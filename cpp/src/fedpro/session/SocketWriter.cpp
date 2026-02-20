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

#include "SocketWriter.h"

#include <fedpro/IOException.h>
#include <fedpro/FedProExceptions.h>
#include "LogUtil.h"

#include <spdlog/spdlog.h>

#include <cassert>
#include <limits>
#include <mutex>
#include <utility>

namespace FedPro
{

   std::unique_ptr<SocketWriter> SocketWriter::createSocketWriter(
         uint64_t sessionId,
         std::unique_ptr<Listener> listener,
         std::unique_ptr<HistoryBuffer> messageQueue,
         std::shared_ptr<Socket> socket,
         int32_t expectedNextSequenceNumber)
   {
      return std::unique_ptr<SocketWriter>(
            new SocketWriter{sessionId, std::move(listener), std::move(messageQueue), std::move(socket),
                             expectedNextSequenceNumber});
   }

   std::unique_ptr<SocketWriter> SocketWriter::createDirectSocketWriter(
         uint64_t sessionId,
         std::shared_ptr<Socket> socket)
   {
      // Todo - why do we hard-code seq num to 1 here?
      return std::unique_ptr<SocketWriter>(new SocketWriter{sessionId, std::move(socket), 1});
   }

   bool SocketWriter::isDirectOnly() const
   {
      return _historyBuffer == nullptr;
   }

   void SocketWriter::socketWriterLoop()
   {
      assert(!isDirectOnly());
      try {
         while (_run) {
            // This loop contains non-interruptible I/O, which means we have to close the socket to be sure the thread
            // will exit. Since that's the case anyway, we make closing the socket the *only* way to exit.

            writeNextMessage();
         }
      } catch (const std::exception & e) {
         // End the thread
         SPDLOG_DEBUG("{}: {}", logPrefix(), e.what());
      } catch (...) {
         SPDLOG_ERROR("{}: Unexpected exception.", logPrefix());
      }
   }

   void SocketWriter::writeNextMessage()
   {
      assert(!isDirectOnly());
      std::unique_ptr<EncodedMessage> message = _historyBuffer->waitAndPeek();
      if (!message) {
         throw BadMessage("Failed retrieving next EncodedMessage from buffer.");
      }

      try {
         writeMessage(*message);
      } catch (const std::exception & e) {
         _listener->exceptionOnWrite(e);
         _run = false;
         return;
      }

      if (_expectedNextSequenceNumber.get() != message->sequenceNumber) {
         SPDLOG_DEBUG(
               "{}: Use of non-sequential sequence number in outgoing message: {} after previous sequence number {}",
               logPrefix(),
               message->sequenceNumber,
               _lastSequenceNumber);
      }

      _lastSequenceNumber = message->sequenceNumber;
      _expectedNextSequenceNumber.set(_lastSequenceNumber);
      _expectedNextSequenceNumber.increment();

      _historyBuffer->poll();
      _listener->messageSent(message->sequenceNumber, message->isControl);
   }

   void SocketWriter::writeDirectMessage(const EncodedMessage & message)
   {
      writeMessage(message);
   }

   // Only used by server
   bool SocketWriter::waitForEmptyQueue(FedProDuration timeout)
   {
      assert(!isDirectOnly());
      return _historyBuffer->waitUntilEmpty(timeout);
   }

   void SocketWriter::rewindToFirstMessage()
   {
      assert(!isDirectOnly());
      _historyBuffer->rewindToFirst();
      if (!_historyBuffer->isEmpty()) {
         _expectedNextSequenceNumber.set(_historyBuffer->peek()->sequenceNumber);
      }
   }

   void SocketWriter::rewindToSequenceNumberAfter(const SequenceNumber & sequenceNumber)
   {
      assert(!isDirectOnly());
      _historyBuffer->rewindTo(sequenceNumber);
      // Consume one message to end up on the message after the given sequence number
      _historyBuffer->poll();
      if (!_historyBuffer->isEmpty()) {
         _expectedNextSequenceNumber.set(_historyBuffer->peek()->sequenceNumber);
      }
   }

   int32_t SocketWriter::getOldestAddedSequenceNumber()
   {
      assert(!isDirectOnly());
      return _historyBuffer->getOldestAddedSequenceNumber();
   }

   int32_t SocketWriter::getNewestAddedSequenceNumber()
   {
      assert(!isDirectOnly());
      return _historyBuffer->getNewestAddedSequenceNumber();
   }

   void SocketWriter::setSessionId(uint64_t sessionId)
   {
      _sessionIdString = LogUtil::formatSessionId(sessionId);
   }

   void SocketWriter::setNewSocket(std::shared_ptr<Socket> socket)
   {
      assert(!isDirectOnly());
      _socket = std::move(socket);
   }

   void SocketWriter::enableWriterLoop()
   {
      _run = true;
      _historyBuffer->interrupt(false);
   }

   void SocketWriter::stopWriterLoop()
   {
      // not really needed, but could save an iteration in the loop.
      _run = false;
      _historyBuffer->interrupt(true);
   }

   // Private methods

   // Main constructor
   SocketWriter::SocketWriter(
         uint64_t sessionId,
         std::unique_ptr<Listener> listener,
         std::unique_ptr<HistoryBuffer> messageQueue,
         std::shared_ptr<Socket> socket,
         int32_t expectedNextSequenceNumber)
         : _sessionIdString{LogUtil::formatSessionId(sessionId)},
           _listener{std::move(listener)},
           _historyBuffer{std::move(messageQueue)},
           _socket{std::move(socket)},
           _expectedNextSequenceNumber{expectedNextSequenceNumber}
   {
   }

   // Direct constructor
   SocketWriter::SocketWriter(
         uint64_t sessionId,
         std::shared_ptr<Socket> socket,
         int32_t expectedNextSequenceNumber)
         : _listener{nullptr},
           _historyBuffer{nullptr},
           _socket{std::move(socket)},
           _expectedNextSequenceNumber{expectedNextSequenceNumber}
   {
   }

   void SocketWriter::writeMessage(const EncodedMessage & message)
   {
      constexpr const size_t maxMessageSize{(std::numeric_limits<uint32_t>::max)()};
      if (message.data.size() <= maxMessageSize) {
         _socket->send(&message.data[0], static_cast<uint32_t>(message.data.length()));
      } else {
         throw IOException(
               "Failed to write message because its size exceed " + std::to_string(maxMessageSize));
      }
      logSentMessage(message);
   }

   void SocketWriter::logSentMessage(const EncodedMessage & message) const
   {
      // NOTE: spdlog has no lazy evaluation of arguments, so unless this statement is removed at compile-time, the
      //   arguments will always be evaluated, even when the DEBUG log scope is off.
      SPDLOG_DEBUG("{}: Sent     seq.nr. {}, type {}.",
                   logPrefix(),
                   LogUtil::padNumString(message.sequenceNumber),
                   MessageHeader::getMessageTypeFromBytes(message.data));
   }

   std::string SocketWriter::logPrefix() const noexcept
   {
      return LogUtil::logPrefix(_sessionIdString, LogUtil::CLIENT_PREFIX);
   }

} // FedPro
