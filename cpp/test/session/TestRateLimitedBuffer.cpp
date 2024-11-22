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

#include <session/buffers/RateLimitedBuffer.h>
#include "utility/LocalRateLimiter.h"

#include <gtest/gtest.h>

#include <thread>

class TestRateLimitedBuffer : public ::testing::Test
{
public:
   std::shared_ptr<std::mutex> _mtx = std::make_shared<std::mutex>();
};

TEST_F(TestRateLimitedBuffer, bufferUnmodified_When_Peeked)
{
   // Given
   FedPro::RateLimitedBuffer<int> rateLimitedBuffer{_mtx, 1, std::make_shared<LocalRateLimiter>()};
   rateLimitedBuffer.insert(1);

   // When
   int returnValue = *rateLimitedBuffer.peek();

   // Then
   ASSERT_EQ(1, returnValue);
   ASSERT_EQ(1, rateLimitedBuffer.size());
}

TEST_F(TestRateLimitedBuffer, bufferModified_When_Polled)
{
   // Given
   FedPro::RateLimitedBuffer<int> rateLimitedBuffer{_mtx, 1, std::make_shared<LocalRateLimiter>()};
   rateLimitedBuffer.insert(1);

   // When
   int returnValue = *rateLimitedBuffer.poll();

   // Then
   ASSERT_EQ(1, returnValue);
   ASSERT_EQ(0, rateLimitedBuffer.size());
}

TEST_F(TestRateLimitedBuffer, peekReturnsNull_When_Empty)
{
   // Given
   FedPro::RateLimitedBuffer<int> rateLimitedBuffer{_mtx, 1, std::make_shared<LocalRateLimiter>()};

   // Then
   ASSERT_EQ(rateLimitedBuffer.peek(), nullptr);
}

TEST_F(TestRateLimitedBuffer, pollReturnsNull_When_Empty)
{
   // Given
   FedPro::RateLimitedBuffer<int> rateLimitedBuffer{_mtx, 1, std::make_shared<LocalRateLimiter>()};

   // Then
   ASSERT_EQ(rateLimitedBuffer.peek(), nullptr);
}

TEST_F(TestRateLimitedBuffer, insertFails_When_BufferIsFullAndNonBlocking)
{
   // Given
   FedPro::RateLimitedBuffer<int> rateLimitedBuffer{_mtx, 1, std::make_shared<LocalRateLimiter>(), false};

   // When
   rateLimitedBuffer.insert(1);

   // Then
   ASSERT_FALSE(rateLimitedBuffer.insert(1));
   ASSERT_EQ(1, rateLimitedBuffer.size());
}

TEST_F(TestRateLimitedBuffer, insertBlocks_When_BufferIsFullAndBlocking)
{
   // Given
   FedPro::RateLimitedBuffer<int> rateLimitedBuffer{_mtx, 1, std::make_shared<LocalRateLimiter>(), true};

   rateLimitedBuffer.insert(1);

   std::thread myThread(
         [&]() {
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
            rateLimitedBuffer.poll();
         });

   // When inserting
   auto preInsert = std::chrono::high_resolution_clock::now();
   bool returnValue;
   {
      std::unique_lock<std::mutex> rateLimitedBufferLock(*_mtx);
      returnValue = rateLimitedBuffer.insert(1);
   }
   auto postInsert = std::chrono::high_resolution_clock::now();

   auto elapsedTime = std::chrono::duration_cast<std::chrono::milliseconds>(postInsert - preInsert).count();

   // Then the time taken for insert should be non-instant
   ASSERT_TRUE(returnValue);
   ASSERT_TRUE(elapsedTime > 30);

   myThread.join();
}

TEST_F(TestRateLimitedBuffer, insertBlocks_When_BufferIsNonBlockingAndRateLimitedOnPreInsert)
{
   // Given
   std::mutex syncMtx;

   std::mutex preInsertMutex;
   auto rateLimiter = std::make_shared<LocalRateLimiter>(&preInsertMutex);

   FedPro::RateLimitedBuffer<int> rateLimitedBuffer{_mtx, 1, rateLimiter, false};
   std::condition_variable lockHasBeenAcquired;
   bool lockAcquired = false;

   // Simulate the pre-insert function with a separate thread
   std::thread preInsertThread(
         [&] {
            std::unique_lock<std::mutex> preInsertLock(preInsertMutex);
            {
               std::lock_guard<std::mutex> lock(syncMtx);
               lockAcquired = true;
               lockHasBeenAcquired.notify_one();
            }
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
         });

   // Wait until the thread has been started
   std::unique_lock<std::mutex> syncLock(syncMtx);
   while (!lockAcquired) {
      lockHasBeenAcquired.wait(syncLock);
   }

   // When inserting
   auto preInsert = std::chrono::high_resolution_clock::now();
   bool returnValue = rateLimitedBuffer.insert(1);
   auto postInsert = std::chrono::high_resolution_clock::now();

   auto elapsedTime = std::chrono::duration_cast<std::chrono::milliseconds>(postInsert - preInsert).count();

   // Then the time taken for insert should be non-instant
   ASSERT_TRUE(returnValue);
   ASSERT_TRUE(elapsedTime > 30);

   preInsertThread.join();
}

TEST_F(TestRateLimitedBuffer, insertBlocks_When_BufferIsNonBlockingAndRateLimitedOnPostInsert)
{
   // Given
   std::mutex syncMtx;

   std::mutex postInsertMutex;
   LocalRateLimiter rateLimiter{nullptr, &postInsertMutex};

   FedPro::RateLimitedBuffer<int> rateLimitedBuffer{_mtx, 1, std::make_shared<LocalRateLimiter>(rateLimiter), false};
   std::condition_variable lockHasBeenAcquired;
   bool lockAcquired = false;

   // Simulate the pre-insert function with a separate thread
   std::thread postInsertThread(
         [&] {
            std::unique_lock<std::mutex> postInsertLock(postInsertMutex);
            {
               std::lock_guard<std::mutex> lock(syncMtx);
               lockAcquired = true;
               lockHasBeenAcquired.notify_one();
            }
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
         });

   // Wait until the thread has been started
   std::unique_lock<std::mutex> syncLock(syncMtx);
   while (!lockAcquired) {
      lockHasBeenAcquired.wait(syncLock);
   }

   // When inserting
   auto preInsert = std::chrono::high_resolution_clock::now();
   bool returnValue = rateLimitedBuffer.insert(1);
   auto postInsert = std::chrono::high_resolution_clock::now();

   auto elapsedTime = std::chrono::duration_cast<std::chrono::milliseconds>(postInsert - preInsert).count();

   // Then the time taken for insert should be non-instant
   ASSERT_TRUE(returnValue);
   ASSERT_TRUE(elapsedTime > 30);

   postInsertThread.join();
}

TEST_F(TestRateLimitedBuffer, insertBlocks_When_BufferIsBlockingAndEmptyAndRateLimitedOnPreInsert)
{
   // Given
   std::mutex syncMtx;

   std::mutex preInsertMtx;
   LocalRateLimiter rateLimiter{&preInsertMtx};

   FedPro::RateLimitedBuffer<int> rateLimitedBuffer{_mtx, 1, std::make_shared<LocalRateLimiter>(rateLimiter), false};
   std::condition_variable lockHasBeenAcquired;
   bool lockAcquired = false;

   // Simulate the pre-insert function with a separate thread
   std::thread preInsertThread(
         [&] {
            std::unique_lock<std::mutex> preInsertLock(preInsertMtx);
            {
               std::lock_guard<std::mutex> lock(syncMtx);
               lockAcquired = true;
               lockHasBeenAcquired.notify_one();
            }
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
         });

   // Wait until the thread has been started
   std::unique_lock<std::mutex> syncLock(syncMtx);
   while (!lockAcquired) {
      lockHasBeenAcquired.wait(syncLock);
   }

   // When inserting
   auto preInsert = std::chrono::high_resolution_clock::now();
   bool returnValue = rateLimitedBuffer.insert(1);
   auto postInsert = std::chrono::high_resolution_clock::now();

   auto elapsedTime = std::chrono::duration_cast<std::chrono::milliseconds>(postInsert - preInsert).count();

   // Then the time taken for insert should be non-instant
   ASSERT_TRUE(returnValue);
   ASSERT_TRUE(elapsedTime > 30);

   preInsertThread.join();
}

TEST_F(TestRateLimitedBuffer, insertBlocks_When_BufferIsBlockingAndEmptyAndRateLimitedOnPostInsert)
{
   // Given
   std::mutex syncMtx;

   std::mutex postInsertMutex;
   LocalRateLimiter rateLimiter{nullptr, &postInsertMutex};

   FedPro::RateLimitedBuffer<int> rateLimitedBuffer{_mtx, 1, std::make_shared<LocalRateLimiter>(rateLimiter), false};
   std::condition_variable lockHasBeenAcquired;
   bool lockAcquired = false;

   // Simulate the pre-insert function with a separate thread
   std::thread postInsertThread(
         [&] {
            std::unique_lock<std::mutex> postInsertLock(postInsertMutex);
            {
               std::lock_guard<std::mutex> lock(syncMtx);
               lockAcquired = true;
               lockHasBeenAcquired.notify_one();
            }
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
         });

   // Wait until the thread has been started
   std::unique_lock<std::mutex> syncLock(syncMtx);
   while (!lockAcquired) {
      lockHasBeenAcquired.wait(syncLock);
   }

   // When inserting
   auto preInsert = std::chrono::high_resolution_clock::now();
   bool returnValue = rateLimitedBuffer.insert(1);
   auto postInsert = std::chrono::high_resolution_clock::now();

   auto elapsedTime = std::chrono::duration_cast<std::chrono::milliseconds>(postInsert - preInsert).count();

   // Then the time taken for insert should be non-instant
   ASSERT_TRUE(returnValue);
   ASSERT_TRUE(elapsedTime > 30);

   postInsertThread.join();
}

