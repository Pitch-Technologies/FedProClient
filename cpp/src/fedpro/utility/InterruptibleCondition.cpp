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

#include "InterruptibleCondition.h"

#include <fedpro/FedProExceptions.h>

namespace FedPro
{
   InterruptibleCondition::InterruptibleCondition() = default;

   InterruptibleCondition::~InterruptibleCondition() = default;

   void InterruptibleCondition::interruptOne()
   {
      _interrupting = true;
      _condition.notify_one();
   }

   void InterruptibleCondition::notifyAll()
   {
      _condition.notify_all();
   }

   void InterruptibleCondition::notifyOne()
   {
      _condition.notify_one();
   }

   void InterruptibleCondition::wait(std::unique_lock<std::mutex> & lock)
   {
      throwIfInterrupted();
      _condition.wait(lock);
      throwIfInterrupted();
   }

   bool InterruptibleCondition::throwIfInterrupted()
   {
      if (_interrupting) {
         _interrupting = false;
         throw InterruptedException("Interrupted while waiting");
      }

      return false;
   }

   InterruptibleConditionState::InterruptibleConditionState() = default;

   void InterruptibleConditionState::interrupt()
   {
      std::lock_guard<std::mutex> lockGuard{_mutex};
      _condition.interruptOne();
   }

} // FedPro