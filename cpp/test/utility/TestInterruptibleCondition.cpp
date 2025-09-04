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

#include <fedpro/FedProExceptions.h>
#include "utility/InterruptibleCondition.h"

#include <gtest/gtest.h>

#include <future>

using namespace FedPro;

class TestInterruptible_condition : public ::testing::Test
{
};

TEST_F(TestInterruptible_condition, wait)
{
   std::mutex mutex;
   InterruptibleCondition condition;
   std::atomic<bool> wait{false};

   auto future = std::async(
         [&mutex, &condition, &wait]() {
            std::unique_lock<std::mutex> lock{mutex, std::defer_lock};
            EXPECT_NO_THROW(lock.lock());
            wait = true;
            while (wait) {
               EXPECT_NO_THROW(condition.wait(lock));
            }
            EXPECT_NO_THROW(lock.unlock());
         });

   // Wait for async function to acquire lock
   while (!wait) {
      std::this_thread::yield();
   }

   // Tell async function to stop
   {
      std::unique_lock<std::mutex> lock{mutex};
      wait = false;
      condition.notifyAll();
   }

   // Wait for async function to return
   EXPECT_NO_THROW(future.get());
}

TEST_F(TestInterruptible_condition, wait_w_interrupt)
{
   std::mutex mutex;
   InterruptibleCondition condition;
   std::atomic<bool> wait{false};

   auto future = std::async(
         [&mutex, &condition, &wait]() {
            std::unique_lock<std::mutex> lock{mutex};
            wait = true;
            condition.wait(lock, [&wait]() -> bool { return !wait; });
         });

   // Wait for async function to acquire lock
   while (!wait) {
      std::this_thread::yield();
   }

   // Tell wait to interrupt
   {
      std::unique_lock<std::mutex> lock{mutex};
      condition.interruptOne();
   }

   // Wait for async function to return with an exception
   EXPECT_THROW(future.get(), InterruptedException);
}

TEST_F(TestInterruptible_condition, wait_until)
{
   using clock_type = std::chrono::high_resolution_clock;

   std::mutex mutex;
   InterruptibleCondition condition;
   {
      std::unique_lock<std::mutex> lock{mutex};

      std::chrono::time_point<clock_type> timeout = clock_type::now() + std::chrono::milliseconds{5};
      while (condition.waitUntil(lock, timeout) != std::cv_status::timeout);
      EXPECT_GE(clock_type::now(), timeout);
   }
}

TEST_F(TestInterruptible_condition, wait_for)
{
   using clock_type = std::chrono::high_resolution_clock;

   std::mutex mutex;
   InterruptibleCondition condition;
   {
      std::unique_lock<std::mutex> lock{mutex};

      std::chrono::milliseconds timeout_duration{5};
      clock_type::time_point beginTime{clock_type::now()};
      while (condition.waitFor(lock, timeout_duration) != std::cv_status::timeout);
      EXPECT_GE(clock_type::now(), beginTime + timeout_duration);
   }
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)
