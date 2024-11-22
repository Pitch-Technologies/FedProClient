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

#include <fedpro/Aliases.h>

#include <atomic>
#include <chrono>
#include <condition_variable>
#include <functional>
#include <memory>
#include <thread>

namespace FedPro
{
   /**
    * @brief Utility class to execute a task after a timer expires.
    *
    * Upon destruction, this cancels any pending timer. If the task must be executed,
    * please ensure the corresponding timer is not destroyed before the timer expires.
    */
   class TimeoutTimer
   {
   public:
      enum class State
      {
         CANCELED, EAGER, LAZY
      };

      // Specify which clock to use. steady_clock, high_resolution_clock or system_clock.
      using Clock = std::chrono::steady_clock;

      using TimerTask = const std::function<void()> &;

      static std::unique_ptr<TimeoutTimer> createLazyTimeoutTimer(const FedProDuration & timeoutDuration);

      static std::unique_ptr<TimeoutTimer> createEagerTimeoutTimer(const FedProDuration & timeoutDuration);

      TimeoutTimer(
            const FedProDuration & timeoutDuration,
            State type);

      ~TimeoutTimer();

      void start(TimerTask whenTimedOut);

      void pause();

      void resume();

      void extend();

      void cancel();

      FedProDuration getTimeoutDuration();

   private:
      std::mutex _timerStateMutex;
      std::condition_variable _condition;
      std::thread _timerThread;
      const FedProDuration _timeoutDuration;
      State _state;
      FedProDuration _offsetTime;
      std::atomic<Clock::time_point> _timeoutTimestamp;

      static bool hasTimedOut(Clock::time_point timeStamp);

      void executeTimerTask(TimerTask whenTimedOut);
   };

} // FedPro
