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

#include "session/ThreadPool.h"

#include <gtest/gtest.h>

#include <cmath>

using namespace FedPro;

class TestThreadPool : public ::testing::Test
{
};

TEST_F(TestThreadPool, ctor)
{
   EXPECT_NO_THROW(ThreadPool{0});
   EXPECT_NO_THROW(ThreadPool{1});
   EXPECT_NO_THROW(ThreadPool{2});
}

TEST_F(TestThreadPool, startThenStart)
{
   ThreadPool pool{1};
   EXPECT_NO_THROW(pool.start());
   EXPECT_THROW(pool.start(), std::logic_error);
}

TEST_F(TestThreadPool, queueJobThenStartThenStop)
{
   std::atomic<uint32_t> counter{0};
   ThreadPool pool{1};
   pool.queueJob([&counter]() { ++counter; });
   pool.start();
   pool.stop(true);
   EXPECT_EQ(counter.load(), 1);
}

TEST_F(TestThreadPool, startThenQueueJobThenStop)
{
   std::atomic<uint32_t> counter{0};
   ThreadPool pool{1};
   pool.start();
   pool.queueJob([&counter]() { ++counter; });
   pool.stop(true);
   EXPECT_EQ(counter.load(), 1);
}

TEST_F(TestThreadPool, clearJobsThenStartThenStop)
{
   std::atomic<uint32_t> counter{0};
   ThreadPool pool{1};
   pool.queueJob([&counter]() { ++counter; });
   pool.clearJobs();
   pool.start();
   pool.stop(true);
   EXPECT_EQ(counter.load(), 0);
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)