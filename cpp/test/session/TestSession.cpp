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

// Silence clang-tidy issues reported for gtest macros.
// NOLINTBEGIN(cppcoreguidelines-avoid-non-const-global-variables)

#include <FedPro.h>
#include <services-common/SettingsParser.h>
#include "session/ThreadPool.h"
#include "utility/BrokenSocket.h"
#include "utility/MemorySocket.h"
#include "utility/PacketFactory.h"
#include "utility/SocketSupplierTransport.h"
#include "utility/StateWaiter.h"

#include <gtest/gtest.h>

#include <atomic>

using namespace FedPro;

class TestSession : public ::testing::Test
{
public:

   void SetUp() override
   {
      ::testing::Test::SetUp();
      std::string settingsLine;
      (settingsLine += SETTING_NAME_CONNECTION_MAX_RETRY_ATTEMPTS) += "=3";
      _clientSettings = SettingsParser::parse(settingsLine);
   }

   void TearDown() override
   {
      _clientSettings = Properties{};
      ::testing::Test::TearDown();
   }

   std::unique_ptr<Session> createSession(std::unique_ptr<Transport> transport)
   {
      return createSession(std::move(transport), _clientSettings);
   }

   std::unique_ptr<Session> createSession(std::unique_ptr<Transport> transport, const Properties & settings)
   {
      auto session = FedPro::createSession(std::move(transport), settings);
      session->addStateListener(
            [this](
                  Session::State oldState,
                  Session::State newState,
                  const std::string & reason) { _stateWaiter.onStateTransition(oldState, newState, reason); });

      return session;
   }

   void onHlaCallbackRequest(int32_t /* sequenceNumber */,
                             const ByteSequence & /* hlaCallback */)
   {
   }

   static std::unique_ptr<Transport> createMemoryTransport(FedPro::string_view firstPacket)
   {
      return createMemoryTransport({firstPacket});
   }

   static std::unique_ptr<Transport> createMemoryTransport(std::initializer_list<FedPro::string_view> packets)
   {
      auto socket = std::make_shared<MemorySocket>();
      for (FedPro::string_view packet : packets)  {
         socket->addInboundPacket(packet);
      }
      socket->connect();
      return std::make_unique<SocketSupplierTransport>(std::move(socket));
   }

   const uint64_t _fakeSessionId{42};

   FedPro::Properties _clientSettings;

   StateWaiter _stateWaiter;

};

TEST_F(TestSession, messageQueueFull)
{
   auto transport = createMemoryTransport(
         PacketFactory::createNewSessionStatus(_fakeSessionId));
   _clientSettings.setUnsignedInt32(SETTING_NAME_MESSAGE_QUEUE_SIZE, 1);
   auto session = createSession(std::move(transport), _clientSettings);

   // Given
   session->start(
         [this](
               int32_t sequenceNumber,
               const ByteSequence & hlaCallback) { onHlaCallbackRequest(sequenceNumber, hlaCallback); });

   try {
      // When
      session->sendHeartbeat();
      session->sendHeartbeat();
      session->sendHeartbeat();
      session->sendHeartbeat();
      session->sendHeartbeat();
   } catch (const FedPro::MessageQueueFull &) {
      // Then maybe
   }

}

TEST_F(TestSession, two_sessions)
{
   // Create two states with a MemoryTransport containing a bad message to cause an immediate termination-on-start.
   // Then verify that both termination were reported.

   auto transportA = createMemoryTransport(
         PacketFactory::createBadMessageType());
   auto sessionA = createSession(std::move(transportA));

   auto transportB = createMemoryTransport(
         PacketFactory::createBadMessageType());
   auto sessionB = createSession(std::move(transportB));

   try {
      sessionB->start(
            [this](
                  int32_t sequenceNumber,
                  const ByteSequence & hlaCallback) { onHlaCallbackRequest(sequenceNumber, hlaCallback); });
      sessionB->terminate();
   } catch (const FedProException &) {
   }

   // Make sure session the state now is TERMINATED.
   ASSERT_THROW({
                   sessionB->terminate();
                }, FedPro::SessionAlreadyTerminated);

   // Destroy sessionB. If the implementation is correct, this should not affect sessionA.
   sessionB.reset();

   try {
      sessionA->start(
            [this](
                  int32_t sequenceNumber,
                  const ByteSequence & hlaCallback) { onHlaCallbackRequest(sequenceNumber, hlaCallback); });
      sessionA->terminate();
   } catch (const FedProException &) {
   }

   // Make sure the session state now is TERMINATED.
   ASSERT_THROW({
                   sessionA->terminate();
                }, FedPro::SessionAlreadyTerminated);

   // Destroy sessionA to ensure dispatching of all Session::State changes.
   sessionA.reset();

   EXPECT_EQ(_stateWaiter.count(Session::State::TERMINATED), 2);
}

TEST_F(TestSession, terminateSession_When_ReceiveInvalidSequenceNumber)
{
   // Given
   const int32_t invalidSequenceNumber = std::numeric_limits<int32_t>::min();

   auto transport = createMemoryTransport(
         {
               PacketFactory::createNewSessionStatus(_fakeSessionId),
               PacketFactory::createHeartbeatResponse(_fakeSessionId, invalidSequenceNumber)
         });

   // When
   auto session = createSession(std::move(transport));
   session->start(
         [this](
               int32_t sequenceNumber,
               const ByteSequence & hlaCallback) { onHlaCallbackRequest(sequenceNumber, hlaCallback); });

   // Then
   EXPECT_EQ(std::cv_status::no_timeout, _stateWaiter.waitForState(Session::State::TERMINATED, std::chrono::seconds{4}));
}

TEST_F(TestSession, startSucceed_When_BrokenSocketOnFirstAttempt)
{
   std::atomic<int> connectionAttemptCount{0};
   auto transport = std::make_unique<SocketSupplierTransport>(
         [&connectionAttemptCount, this]() -> std::shared_ptr<Socket> {
            // Given the first connect attempt produce a broken socket
            if ((connectionAttemptCount++) == 0) {
               return std::make_shared<BrokenSocket>();
            } else {
               auto socket = std::make_shared<MemorySocket>();
               socket->addInboundPacket(PacketFactory::createNewSessionStatus(_fakeSessionId));
               socket->connect();
               return socket;
            }
         });

   // When
   auto session = createSession(std::move(transport));
   session->start(
         [this](
               int32_t sequenceNumber,
               const ByteSequence & hlaCallback) { onHlaCallbackRequest(sequenceNumber, hlaCallback); });

   // Then
   EXPECT_EQ(std::cv_status::no_timeout, _stateWaiter.waitForState(Session::State::RUNNING, std::chrono::seconds{4}));
   EXPECT_EQ(_fakeSessionId, session->getId());
   EXPECT_GT(connectionAttemptCount, 1);
}

TEST_F(TestSession, terminateSucceed_When_ConcurrentTerminateCalls)
{
   auto socket = std::make_shared<MemorySocket>();
   socket->addInboundPacket(PacketFactory::createNewSessionStatus(_fakeSessionId));
   socket->connect();
   auto transport = std::make_unique<SocketSupplierTransport>(socket);
   auto session = createSession(std::move(transport));

   session->start(
         [this](
               int32_t sequenceNumber,
               const ByteSequence & hlaCallback) { onHlaCallbackRequest(sequenceNumber, hlaCallback); });
   // Close the socket immediately. The socket only has 1 packet in queue, any further read attempt would time out.
   socket->close();

   // Given multiple threads do concurrent session operations
   FedPro::ThreadPool threadPool(3);
   for (int i = 0; i < 9; ++i) {
      threadPool.queueJob(
            [&session]() {
               try {
                  // When any thread call terminate
                  session->terminate(std::chrono::seconds{12});
               } catch (const FedPro::SessionAlreadyTerminated &) {
               } catch (const FedPro::SessionLost & e) {
                  // Terminate throws SessionLost, which is plausible for a real socket.
                  // But this is odd for a MemorySocket as it should be perfectly reliable.
                  // So log a message.
                  std::cerr << "Terminate throws " << e.getName() << ": " << e.what() << std::endl;
               } catch (const std::exception & e) {
                  FAIL() << e.what();
               }
               // Then succeed
            });
   }
   threadPool.start();
   threadPool.shutdown(true);
   ASSERT_TRUE(threadPool.waitTermination(std::chrono::seconds{16}));
}


// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)
