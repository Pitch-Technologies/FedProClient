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


#include "StateWaiter.h"

#include <algorithm>
#include <chrono>
#include <thread>

using namespace FedPro;
using Clock = std::chrono::steady_clock;

void StateWaiter::waitForState(FedPro::Session::State expectedState) noexcept
{
   std::unique_lock<std::mutex> sessionLock(_sessionMutex);
   while (true) {
      _condition.wait(sessionLock, [this]() {return !_states.empty();});
      Session::State state = _states.front();
      _states.pop_front();
      if (state == expectedState) {
         break;
      }
   }
}

std::cv_status StateWaiter::waitForState(
      FedPro::Session::State expectedState,
      FedPro::FedProDuration timeout) noexcept
{
   Clock::time_point endTime = Clock::now() + timeout;
   std::unique_lock<std::mutex> sessionLock(_sessionMutex);
   while (true) {
      _condition.wait_until(sessionLock, endTime, [this]() { return !_states.empty(); });
      if (_states.empty()) {
         break; // Reached timeout
      }
      Session::State state = _states.front();
      _states.pop_front();
      if (state == expectedState) {
         return std::cv_status::no_timeout;
      }
   }
   return std::cv_status::timeout;
}

std::cv_status StateWaiter::waitForStates(
      FedPro::FedProDuration timeout,
      std::initializer_list<FedPro::Session::State> expectedStates) noexcept
{
   Clock::time_point endTime = Clock::now() + timeout;
   std::unique_lock<std::mutex> sessionLock(_sessionMutex);
   while (true) {
      _condition.wait_until(sessionLock, endTime, [this]() { return !_states.empty(); });
      if (_states.empty()) {
         break; // Reached timeout
      }
      Session::State state = _states.front();
      _states.pop_front();
      if (std::find(expectedStates.begin(), expectedStates.end(), state) != expectedStates.end()) {
         return std::cv_status::no_timeout;
      }
   }
   return std::cv_status::timeout;
}

size_t StateWaiter::count(FedPro::Session::State state)
{
   std::unique_lock<std::mutex> sessionLock(_sessionMutex);
   return std::count(_states.begin(), _states.end(), state);
}

void StateWaiter::onStateTransition(
      FedPro::Session::State /* oldState */,
      FedPro::Session::State newState,
      const std::string & /* reason */)
{
   {
      std::unique_lock<std::mutex> sessionLock(_sessionMutex);
      _states.push_back(newState);
      _condition.notify_all();
   }
}

