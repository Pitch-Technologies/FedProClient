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

#include <session/buffers/RoundRobinBuffer.h>

#include <gtest/gtest.h>

#include <thread>

class TestRoundRobinBuffer : public ::testing::Test
{
public:
   std::shared_ptr<std::mutex> localMutex = std::make_shared<std::mutex>();
protected:

   static std::function<bool(const std::string &)> getQueueAlternator(const std::string & val)
   {
      return [val](const std::string & element) -> bool {
         return std::equal(val.begin(), val.end(), element.begin(), element.end());
      };
   }

   static void assertArrayEquals(
         std::string arrayOne[3],
         std::string arrayTwo[3])
   {
      ASSERT_EQ(arrayOne[0], arrayTwo[0]);
      ASSERT_EQ(arrayOne[1], arrayTwo[1]);
      ASSERT_EQ(arrayOne[2], arrayTwo[2]);
   }

   static std::string getPrimaryValue()
   {
      return {"primary"};
   }

   static std::string getAlternateValue()
   {
      return {"alternate"};
   }
};


TEST_F(TestRoundRobinBuffer, insertDoesNotBlock_When_BothQueuesAreUsed)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};

   // When
   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getAlternateValue()));
   roundRobinBuffer.insert(std::move(getAlternateValue()));
   roundRobinBuffer.insert(std::move(getAlternateValue()));

   // Then don't block
}

TEST_F(TestRoundRobinBuffer, pollAlternates_When_BothQueuesAreUsed)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};

   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getAlternateValue()));

   // When
   std::string firstThreePolls[]{*roundRobinBuffer.poll(), *roundRobinBuffer.poll(), *roundRobinBuffer.poll()};

   // Then
   std::string expectedPollSequence[]{getPrimaryValue(), getAlternateValue(), getPrimaryValue()};
   assertArrayEquals(expectedPollSequence, firstThreePolls);
}

TEST_F(TestRoundRobinBuffer, pollPrioritizesPrimaryQueue_When_BothQueuesAreUsed)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};

   roundRobinBuffer.insert(std::move(getAlternateValue()));
   roundRobinBuffer.insert(std::move(getAlternateValue()));
   roundRobinBuffer.insert(std::move(getAlternateValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));

   // When
   std::string firstThreePolls[]{*roundRobinBuffer.poll(), *roundRobinBuffer.poll(), *roundRobinBuffer.poll()};

   // Then
   std::string expectedPollSequence[]{getPrimaryValue(), getAlternateValue(), getPrimaryValue()};

   assertArrayEquals(expectedPollSequence, firstThreePolls);
}

TEST_F(TestRoundRobinBuffer, peekDoesNotAlterPollOrder)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};
   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getAlternateValue()));
   roundRobinBuffer.insert(std::move(getAlternateValue()));

   // When
   roundRobinBuffer.peek();

   // Then
   std::string result = *roundRobinBuffer.poll();
   ASSERT_EQ(getPrimaryValue(), result);
}

TEST_F(TestRoundRobinBuffer, pollReturnsElement_When_PrimaryQueueIsEmpty)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};

   roundRobinBuffer.insert(std::move(getAlternateValue()));
   roundRobinBuffer.insert(std::move(getAlternateValue()));
   roundRobinBuffer.insert(std::move(getAlternateValue()));

   // When
   std::string firstThreePolls[]{*roundRobinBuffer.poll(), *roundRobinBuffer.poll(), *roundRobinBuffer.poll()};

   // Then
   std::string expectedPollSequence[]{getAlternateValue(), getAlternateValue(), getAlternateValue()};
   assertArrayEquals(expectedPollSequence, firstThreePolls);
}

TEST_F(TestRoundRobinBuffer, pollReturnsElement_When_AlternateQueueIsEmpty)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};

   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));

   // When
   std::string firstThreePolls[]{*roundRobinBuffer.poll(), *roundRobinBuffer.poll(), *roundRobinBuffer.poll()};

   // Then
   std::string expectedPollSequence[]{getPrimaryValue(), getPrimaryValue(), getPrimaryValue()};
   assertArrayEquals(expectedPollSequence, firstThreePolls);
}

TEST_F(TestRoundRobinBuffer, sizeGivesMaxOfLongestQueue_When_PrimaryQueueIsLongest)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};

   roundRobinBuffer.insert(std::move(getAlternateValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));

   // When
   uint32_t actualSize = roundRobinBuffer.size();

   // Then
   ASSERT_EQ(2, actualSize);
}

TEST_F(TestRoundRobinBuffer, sizeGivesMaxOfLongestQueue_When_AlternateQueueIsLongest)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};

   roundRobinBuffer.insert(std::move(getAlternateValue()));
   roundRobinBuffer.insert(std::move(getAlternateValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));

   // When
   uint32_t actualSize = roundRobinBuffer.size();

   // Then
   ASSERT_EQ(2, actualSize);
}

TEST_F(TestRoundRobinBuffer, peekDoesNotModifyQueues_When_PeekingPrimaryQueue)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};

   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));

   // When
   roundRobinBuffer.peek();

   // Then
   ASSERT_EQ(2, roundRobinBuffer.size());
}

TEST_F(TestRoundRobinBuffer, peekDoesNotModifyQueues_When_PeekingAlternateQueue)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};

   roundRobinBuffer.insert(std::move(getAlternateValue()));
   roundRobinBuffer.insert(std::move(getAlternateValue()));

   // When
   roundRobinBuffer.peek();

   // Then
   ASSERT_EQ(2, roundRobinBuffer.size());
}

TEST_F(TestRoundRobinBuffer, waitAndPollReturns_When_InsertedIntoPrimaryQueue)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};

   std::thread myThread(
         [&roundRobinBuffer]() {
            std::this_thread::sleep_for(std::chrono::milliseconds(20));
            roundRobinBuffer.insert(std::move(getPrimaryValue()));
         });

   // When
   std::lock_guard<std::mutex> lock(*localMutex);
   std::string returnedValue = *roundRobinBuffer.waitAndPoll();

   // Then
   ASSERT_EQ(getPrimaryValue(), returnedValue);

   if (myThread.joinable()) {
      myThread.join();
   }
}

TEST_F(TestRoundRobinBuffer, waitAndPeekReturns_When_InsertedIntoPrimaryQueue)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};

   std::thread myThread(
         [&roundRobinBuffer]() {
            std::this_thread::sleep_for(std::chrono::milliseconds(20));
            roundRobinBuffer.insert(std::move(getPrimaryValue()));
         });

   // When
   std::lock_guard<std::mutex> lock(*localMutex);
   std::string returnedValue = *roundRobinBuffer.waitAndPeek();

   // Then
   ASSERT_EQ(getPrimaryValue(), returnedValue);

   if (myThread.joinable()) {
      myThread.join();
   }
}

TEST_F(TestRoundRobinBuffer, waitAndPollReturns_When_InsertedIntoAlternateQueue)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};

   std::thread myThread(
         [&roundRobinBuffer]() {
            std::this_thread::sleep_for(std::chrono::milliseconds(20));
            roundRobinBuffer.insert(std::move(getAlternateValue()));
         });

   // When
   std::lock_guard<std::mutex> lock(*localMutex);
   std::string returnedValue = *roundRobinBuffer.waitAndPoll();

   // Then
   ASSERT_EQ(getAlternateValue(), returnedValue);

   if (myThread.joinable()) {
      myThread.join();
   }
}

TEST_F(TestRoundRobinBuffer, waitAndPeekReturns_When_InsertedIntoAlternateQueue)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};

   std::thread myThread(
         [&roundRobinBuffer]() {
            std::this_thread::sleep_for(std::chrono::milliseconds(20));
            roundRobinBuffer.insert(std::move(getAlternateValue()));
         });

   // When
   std::lock_guard<std::mutex> lock(*localMutex);
   std::string returnedValue = *roundRobinBuffer.waitAndPeek();

   // Then
   ASSERT_EQ(getAlternateValue(), returnedValue);

   if (myThread.joinable()) {
      myThread.join();
   }
}

TEST_F(TestRoundRobinBuffer, insertWaits_When_InsertingMoreElementsThanCapacity)
{
   // Given
   FedPro::RoundRobinBuffer<std::string> roundRobinBuffer
         {std::make_shared<FedPro::NullRateLimiter>(), getQueueAlternator(getAlternateValue()), localMutex, 3};

   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));
   roundRobinBuffer.insert(std::move(getPrimaryValue()));

   std::thread myThread(
         [&roundRobinBuffer]() {
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
            roundRobinBuffer.poll();
         });

   // When inserting
   auto preInsert = std::chrono::high_resolution_clock::now();
   std::lock_guard<std::mutex> lock(*localMutex);
   bool returnValue = roundRobinBuffer.insert(std::move(getPrimaryValue()));
   auto postInsert = std::chrono::high_resolution_clock::now();

   auto elapsedTime = std::chrono::duration_cast<std::chrono::milliseconds>(postInsert - preInsert).count();

   // Then the time taken for insert should be non-instant
   ASSERT_TRUE(returnValue);
   ASSERT_TRUE(elapsedTime > 30);

   if (myThread.joinable()) {
      myThread.join();
   }
}
