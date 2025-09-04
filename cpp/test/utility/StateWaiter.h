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

#include "FedPro.h"

#include <condition_variable>
#include <deque>

class StateWaiter
{
public:
   void waitForState(FedPro::Session::State state) noexcept;

   std::cv_status waitForState(
         FedPro::Session::State state,
         FedPro::FedProDuration timeout) noexcept;

   std::cv_status waitForStates(
         FedPro::FedProDuration timeout,
         std::initializer_list<FedPro::Session::State> anticipatedStates) noexcept;

   size_t count(FedPro::Session::State state);

   void onStateTransition(
         FedPro::Session::State oldState,
         FedPro::Session::State newState,
         const std::string & reason);

private:
   std::deque<FedPro::Session::State> _states; // GUARDED_BY(_sessionMutex)
   std::mutex _sessionMutex;
   std::condition_variable _condition;
};
