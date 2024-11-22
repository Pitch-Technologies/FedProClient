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

#include "session/msg/HlaCallbackResponseMessage.h"
#include "session/msg/HlaCallRequestMessage.h"
#include <session/buffers/HistoryBuffer.h>
#include <session/buffers/RateLimitedBuffer.h>
#include <session/msg/HeartbeatResponseMessage.h>
#include <session/SequenceNumber.h>
#include "utility/LocalRateLimiter.h"

#include <gtest/gtest.h>

#include <thread>

class TestHistoryBuffer : public ::testing::Test
{
protected:
   FedPro::QueueableMessage _queueableMessage1 = dummyMessage(1);
   FedPro::QueueableMessage _queueableMessage2 = dummyMessage(2);
   FedPro::QueueableMessage _queueableMessage3 = dummyMessage(3);
   FedPro::QueueableMessage _queueableMessage4 = dummyMessage(4);

   std::shared_ptr<FedPro::RateLimiter> _rateLimiter = std::make_shared<LocalRateLimiter>();

   std::function<bool(const FedPro::QueueableMessage &)>
         queueAlternator = [](const FedPro::QueueableMessage & element) -> bool {
      return false;
   };

   std::shared_ptr<std::mutex> _mtx = std::make_shared<std::mutex>();

   static void assertSameMessage(
         FedPro::QueueableMessage & expectedMessage,
         FedPro::EncodedMessage & message)
   {
      FedPro::EncodedMessage expected = expectedMessage.createEncodedMessage(message.sequenceNumber);
      ASSERT_TRUE(std::equal(expected.data.begin(), expected.data.end(), message.data.begin(), message.data.end()));
   }

   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> getRoundRobinBuffer(
         uint32_t capacity)
   {
      // TODO: blockwhenfull
      return std::make_shared<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>>(
            std::make_shared<FedPro::NullRateLimiter>(), queueAlternator, _mtx, capacity);
   }

   FedPro::QueueableMessage dummyMessage(int32_t value)
   {
      return {0, value, 0, FedPro::MessageType{FedPro::MessageType::HLA_CALL_REQUEST}, nullptr, nullptr, nullptr};
   };
};


TEST_F(TestHistoryBuffer, oldestAvailableMessageIsReturned_When_PeekIsCalled)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   messageQueue->insert(dummyMessage(1));
   messageQueue->insert(dummyMessage(2));

   // When
   std::unique_ptr<FedPro::EncodedMessage> oldestAvailableMessage = historyBuffer.peek();

   // Then
   assertSameMessage(_queueableMessage1, *oldestAvailableMessage);
}

TEST_F(TestHistoryBuffer, peekReturnsNull_When_TryingToPeekPassedWriteIndex)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   messageQueue->insert(dummyMessage(1));
   historyBuffer.poll();

   // Then
   ASSERT_EQ(historyBuffer.peek(), nullptr);
}

TEST_F(TestHistoryBuffer, peekReturnsNull_WhenhistoryBufferIsEmpty)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};

   // Then
   ASSERT_EQ(historyBuffer.peek(), nullptr);
}

TEST_F(TestHistoryBuffer, oldestAvailableMessageIsReturned_When_PollIsCalled)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   messageQueue->insert(dummyMessage(1));
   messageQueue->insert(dummyMessage(2));

   // When
   std::unique_ptr<FedPro::EncodedMessage> oldestAvailableMessage = historyBuffer.poll();

   // Then
   assertSameMessage(_queueableMessage1, *oldestAvailableMessage);
}

TEST_F(TestHistoryBuffer, oldestMessageIsLost_When_HistoryIsOverwritten)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   messageQueue->insert(dummyMessage(1));
   historyBuffer.poll();
   messageQueue->insert(dummyMessage(2));
   historyBuffer.poll();
   messageQueue->insert(dummyMessage(3));
   historyBuffer.poll();

   // When
   messageQueue->insert(dummyMessage(4));
   historyBuffer.poll();

   // Then
   historyBuffer.rewindTo(FedPro::SequenceNumber{3});
   assertSameMessage(_queueableMessage4, *historyBuffer.peek());

   historyBuffer.rewindTo(FedPro::SequenceNumber{2});
   assertSameMessage(_queueableMessage3, *historyBuffer.peek());

   historyBuffer.rewindTo(FedPro::SequenceNumber{1});
   assertSameMessage(_queueableMessage2, *historyBuffer.peek());

   ASSERT_EQ(3, historyBuffer.size());
}

TEST_F(TestHistoryBuffer, PollReturnsNull_When_NothingIsLeftToPoll)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   messageQueue->insert(dummyMessage(1));
   historyBuffer.poll();

   // Then
   ASSERT_EQ(historyBuffer.poll(), nullptr);
}

TEST_F(TestHistoryBuffer, peekReturnsNextMessage_WhenhistoryBufferIsPolled)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   messageQueue->insert(dummyMessage(1));
   messageQueue->insert(dummyMessage(2));

   // When
   historyBuffer.poll();
   std::unique_ptr<FedPro::EncodedMessage> message = historyBuffer.peek();

   // Then
   assertSameMessage(_queueableMessage2, *message);
}

TEST_F(TestHistoryBuffer, pollReturnsNull_WhenhistoryBufferIsEmpty)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};

   // Then
   ASSERT_EQ(historyBuffer.poll(), nullptr);
}

TEST_F(TestHistoryBuffer, newestSequenceNumberIsReturned_When_GetNewestAddedSequenceNumberIsCalled)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   messageQueue->insert(dummyMessage(1));
   messageQueue->insert(dummyMessage(2));
   historyBuffer.poll();
   historyBuffer.poll();

   // When
   int newestNumber = historyBuffer.getNewestAddedSequenceNumber();

   // Then
   ASSERT_EQ(FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER + 1, newestNumber);
}

TEST_F(TestHistoryBuffer, newestSequenceNumberIsNO_SEQUENCE_NUMBER_WhenhistoryBufferIsEmpty)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};

   // Then
   ASSERT_EQ(FedPro::SequenceNumber::NO_SEQUENCE_NUMBER, historyBuffer.getNewestAddedSequenceNumber());
}

TEST_F(TestHistoryBuffer, oldestSequenceNumberIsReturned_When_GetOldestAddedSequenceNumberIsCalled)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   messageQueue->insert(dummyMessage(1));
   messageQueue->insert(dummyMessage(2));
   historyBuffer.poll();
   historyBuffer.poll();

   // When
   int oldestNumber = historyBuffer.getOldestAddedSequenceNumber();

   // Then
   ASSERT_EQ(FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER, oldestNumber);
}

TEST_F(TestHistoryBuffer, oldestSequenceNumberIsNO_SEQUENCE_NUMBER_WhenhistoryBufferIsEmpty)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};

   // Then
   ASSERT_EQ(FedPro::SequenceNumber::NO_SEQUENCE_NUMBER, historyBuffer.getOldestAddedSequenceNumber());
}

TEST_F(TestHistoryBuffer, sizeReturnsZero_WhenmessageQueueIsEmpty)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};

   // Then
   ASSERT_EQ(0, historyBuffer.size());
}

TEST_F(TestHistoryBuffer, sizeReturnsTwo_When_TwoMessagesHaveBeenInsertedIntoMessageQueue)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};

   // When
   messageQueue->insert(dummyMessage(1));
   messageQueue->insert(dummyMessage(2));

   // Then
   ASSERT_EQ(2, historyBuffer.size());
}

TEST_F(TestHistoryBuffer, sizeIsReducedByOne_WhenhistoryBufferIsPolled)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   messageQueue->insert(dummyMessage(1));
   messageQueue->insert(dummyMessage(2));

   uint32_t size = historyBuffer.size();

   // When
   historyBuffer.poll();

   // Then
   ASSERT_EQ(size - 1, historyBuffer.size());
}

TEST_F(TestHistoryBuffer, isEmpty_When_HistoryAndMessageQueuesAre)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};

   // Then
   ASSERT_TRUE(historyBuffer.isEmpty());
}

TEST_F(TestHistoryBuffer, isNotEmpty_WhenmessageQueueIsNot)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};

   // When
   messageQueue->insert(dummyMessage(1));

   // Then
   ASSERT_FALSE(historyBuffer.isEmpty());
}

TEST_F(TestHistoryBuffer, isEmpty_When_EverythingIsPolledOutOfHistoryBuffer)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   messageQueue->insert(dummyMessage(1));

   // When
   historyBuffer.poll();

   // Then
   ASSERT_TRUE(historyBuffer.isEmpty());
}

TEST_F(TestHistoryBuffer, oldestAvailableMessageIsReturned_When_waitAndPollIsCalled)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   messageQueue->insert(dummyMessage(1));
   messageQueue->insert(dummyMessage(2));

   // When
   std::unique_ptr<FedPro::EncodedMessage> oldestAvailableMessage = historyBuffer.waitAndPoll();

   // Then
   assertSameMessage(_queueableMessage1, *oldestAvailableMessage);
}

TEST_F(TestHistoryBuffer, waitAndPollWorks_When_InitiallyCalledOnAnEmptyFedProHistoryBuffer)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   std::thread myThread(
         [&messageQueue, this]() {
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
            messageQueue->insert(dummyMessage(1));
         });

   // Then
   ASSERT_NE(historyBuffer.waitAndPoll(), nullptr);

   if (myThread.joinable()) {
      myThread.join();
   }
}

TEST_F(TestHistoryBuffer, oldestAvailableMessageIsReturned_When_waitAndPeekIsCalled)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   messageQueue->insert(dummyMessage(1));
   messageQueue->insert(dummyMessage(2));

   // When
   std::unique_ptr<FedPro::EncodedMessage> oldestAvailableMessage = historyBuffer.waitAndPeek();

   // Then
   assertSameMessage(_queueableMessage1, *oldestAvailableMessage);
}

TEST_F(TestHistoryBuffer, waitAndPeekWorks_When_InitiallyCalledOnAnEmptyFedProHistoryBuffer)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   std::thread myThread(
         [&messageQueue, this]() {
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
            messageQueue->insert(dummyMessage(1));
         });

   // When
   ASSERT_NE(historyBuffer.waitAndPeek(), nullptr);

   // Then do not throw.
   if (myThread.joinable()) {
      myThread.join();
   }
}

TEST_F(TestHistoryBuffer, peeksCorrectMessage_When_RewindingOneMessage)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};
   messageQueue->insert(dummyMessage(1));
   messageQueue->insert(dummyMessage(2));
   historyBuffer.poll();
   historyBuffer.poll();

   // When
   historyBuffer.rewindTo(FedPro::SequenceNumber{1});

   // Then
   assertSameMessage(_queueableMessage2, *historyBuffer.peek());
}


TEST_F(TestHistoryBuffer, rewindToThrows_When_RewindingToAnUnusedSequenceNumberNotYetAssigned)
{
   // Given
   std::shared_ptr<FedPro::RoundRobinBuffer<FedPro::QueueableMessage>> messageQueue = getRoundRobinBuffer(3);
   FedPro::HistoryBuffer historyBuffer{_mtx, messageQueue, FedPro::SequenceNumber::INITIAL_SEQUENCE_NUMBER};

   // Then
   EXPECT_THROW(historyBuffer.rewindTo(FedPro::SequenceNumber{1000}), std::invalid_argument);
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)
