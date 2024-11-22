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

#include "session/TimeoutTimer.h"

#include <gtest/gtest.h>

using namespace FedPro;

class TestTimeoutTimer : public ::testing::Test
{
};

TEST_F(TestTimeoutTimer, ctor)
{
   EXPECT_NO_THROW(TimeoutTimer(FedProDuration{0}, TimeoutTimer::State::CANCELED));
   EXPECT_NO_THROW(TimeoutTimer(FedProDuration{1}, TimeoutTimer::State::EAGER));
   EXPECT_NO_THROW(TimeoutTimer(FedProDuration{2}, TimeoutTimer::State::LAZY));
}

TEST_F(TestTimeoutTimer, create)
{
   EXPECT_NO_THROW(TimeoutTimer::createLazyTimeoutTimer(FedProDuration{1}));
   EXPECT_NO_THROW(TimeoutTimer::createEagerTimeoutTimer(FedProDuration{2}));
}

TEST_F(TestTimeoutTimer, getTimeoutDuration)
{
   FedProDuration timeoutDurationInput{1000000};
   auto timer = TimeoutTimer::createLazyTimeoutTimer(timeoutDurationInput);
   FedProDuration timeoutDuration = timer->getTimeoutDuration();
   EXPECT_EQ(timeoutDuration, timer->getTimeoutDuration());
   timeoutDurationInput = FedProDuration{0};
   EXPECT_EQ(timeoutDuration, timer->getTimeoutDuration());
}

TEST_F(TestTimeoutTimer, start)
{
   std::atomic<bool> ding{false};
   auto timer = TimeoutTimer::createLazyTimeoutTimer(FedProDuration{1});
   timer->start([&ding](){ding = true;});
   while (!ding) {
      std::this_thread::yield();
   }
   EXPECT_TRUE(ding);
}

TEST_F(TestTimeoutTimer, startThenCancel)
{
   std::atomic<bool> ding{false};
   auto timer = TimeoutTimer::createLazyTimeoutTimer(FedProDuration{1000000});
   timer->start([&ding](){ding = true;});
   timer->cancel();
}

TEST_F(TestTimeoutTimer, startThenPauseThenResume)
{
   std::atomic<bool> ding{false};
   FedProDuration timeoutDuration{20};
   auto timer = TimeoutTimer::createLazyTimeoutTimer(timeoutDuration);
   timer->start([&ding](){ding = true;});
   timer->pause();
   timer->resume();
   while (!ding) {
      std::this_thread::yield();
   }
   EXPECT_TRUE(ding);
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)