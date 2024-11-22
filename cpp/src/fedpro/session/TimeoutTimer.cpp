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

#include "TimeoutTimer.h"

namespace FedPro
{
   TimeoutTimer::TimeoutTimer(
         const FedProDuration & timeoutDuration,
         State type)
         : _timerStateMutex{},
           _condition{},
           _timerThread{},
           _timeoutDuration{timeoutDuration},
           _state{type},
           _offsetTime{timeoutDuration},
           _timeoutTimestamp{Clock::time_point::max()}
   {
   }

   TimeoutTimer::~TimeoutTimer()
   {
      cancel();
   }

   std::unique_ptr<TimeoutTimer> TimeoutTimer::createLazyTimeoutTimer(
         const FedProDuration & timeoutDuration)
   {
      return std::make_unique<TimeoutTimer>(timeoutDuration, State::LAZY);
   }

   std::unique_ptr<TimeoutTimer> TimeoutTimer::createEagerTimeoutTimer(
         const FedProDuration & timeoutDuration)
   {
      return std::make_unique<TimeoutTimer>(timeoutDuration, State::EAGER);
   }

   void TimeoutTimer::start(TimerTask whenTimedOut)
   {
      std::unique_lock<std::mutex> lock(_timerStateMutex);
      if (_state == State::CANCELED || _timerThread.joinable()) {
         // Timer already started or canceled.
         return;
      }
      if (_timeoutDuration == FedProDuration::zero()) {
         // Do not time out?
         return;
      }
      uint32_t wakeUpTimesPerInterval = 6;
      FedProDuration timeoutCheckInterval = _timeoutDuration / wakeUpTimesPerInterval;
      if (timeoutCheckInterval == FedProDuration::zero()) {
         timeoutCheckInterval++;
      }
      _offsetTime = _state == State::EAGER ? timeoutCheckInterval * (wakeUpTimesPerInterval - 1) : _timeoutDuration;

      // Start the timer task
      extend();
      _timerThread = std::thread(
            [this, whenTimedOut]() {
               executeTimerTask(whenTimedOut);
            });
   }

   void TimeoutTimer::pause()
   {
      _timeoutTimestamp = Clock::time_point::max();
   }

   void TimeoutTimer::resume()
   {
      extend();
   }

   void TimeoutTimer::extend()
   {
      _timeoutTimestamp = Clock::now() + _offsetTime;
   }

   void TimeoutTimer::cancel()
   {
      // Will terminate the internal thread.
      {
         std::unique_lock<std::mutex> lock(_timerStateMutex);
         if (_state == State::CANCELED) {
            return;
         }
         _state = State::CANCELED;
      }
      _condition.notify_all();

      // Only wait if start() has been called on this Timer.
      if (_timerThread.joinable()) {
         try {
            _timerThread.join();
         } catch (const std::system_error &) {
         }
      }
   }

   FedProDuration TimeoutTimer::getTimeoutDuration()
   {
      return _timeoutDuration;
   }

   // Private methods

   void TimeoutTimer::executeTimerTask(TimerTask whenTimedOut)
   {
      while (_state != State::CANCELED) {
         {
            std::unique_lock<std::mutex> lock(_timerStateMutex);
            while (true) {
               if (_condition.wait_for(lock, _offsetTime, [this] { return _state == State::CANCELED; })) {
                  pause();
                  return;
               }
               if (hasTimedOut(_timeoutTimestamp.load())) {
                  break;
               }
            }
         }
         pause();
         whenTimedOut();
      }
   }

   // GuardedBy _timerStateMutex
   bool TimeoutTimer::hasTimedOut(Clock::time_point timeStamp)
   {
      return Clock::now() >= timeStamp;
   }

} // FedPro
