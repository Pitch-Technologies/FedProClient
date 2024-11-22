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
#include "utility/MemoryTransport.h"
#include "utility/PacketFactory.h"

#include <gtest/gtest.h>

#include <atomic>

using namespace FedPro;

class TestSession : public ::testing::Test
{
public:

   std::unique_ptr<Session> createSession(std::unique_ptr<Transport> transport)
   {
      return createSession(std::move(transport), Properties{});
   }

   std::unique_ptr<Session> createSession(std::unique_ptr<Transport> transport, const Properties & settings)
   {
      auto session = FedPro::createSession(std::move(transport), settings);
      session->addStateListener(
            [this](
                  Session::State oldState,
                  Session::State newState,
                  const std::string & reason) { onSessionStateChange(oldState, newState, reason); });
      return session;
   }

   void onHlaCallbackRequest(int32_t /* sequenceNumber */,
                             const ByteSequence & /* hlaCallback */)
   {
   }

   void onSessionStateChange(
         Session::State /* oldState */,
         Session::State newState,
         const std::string & /* reason */)
   {
      if (newState == Session::State::TERMINATED) {
         ++_stateTerminationCount;
      }
   }

   std::atomic<size_t> _stateTerminationCount{0};

};

TEST_F(TestSession, messageQueueFull)
{
   auto transport = std::make_unique<MemoryTransport>();
   transport->addInboundPacket(PacketFactory::createNewSessionStatus());
   Properties settings;
   settings.setUnsignedInt32(SETTING_NAME_MESSAGE_QUEUE_SIZE, 1);
   auto session = createSession(std::move(transport), settings);

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
   auto transportA = std::make_unique<MemoryTransport>();
   transportA->addInboundPacket(PacketFactory::createBadMessageType());
   auto sessionA = createSession(std::move(transportA));

   auto transportB = std::make_unique<MemoryTransport>();
   transportB->addInboundPacket(PacketFactory::createBadMessageType());
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

   EXPECT_EQ(_stateTerminationCount.load(), 2);
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)
