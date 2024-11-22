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

#include <chrono>
#include <condition_variable>
#include <mutex>

namespace FedPro
{
   /**
    * This class is std::condition_variable wrapper with the ability to interrupt
    * wait calls by having those calls throw InterruptedException.
    */
   class InterruptibleCondition
   {
   public:

      InterruptibleCondition();

      InterruptibleCondition(const InterruptibleCondition &) = delete;

      /**
       * Thread safety: It is safe to destroy if all waiting threads have been notified.
       */
      ~InterruptibleCondition();

      /**
       * Interrupts one pending wait() call within other threads.
       * This causes pending wait() calls to throw InterruptedException.
       *
       * Thread safety: This method is safe to call if holding the lock used to wait().
       *
       */
      void interruptOne();

      /**
       * Wake all threads with pending wait() calls.
       * This allows wait() to return when an action causes the predicate passed to wait() to be false.
       *
       * Thread safety: This method is safe to call if both the notifyOne() call and corresponding action
       * occur while holding the lock used to wait().
       */
      void notifyAll();

      /**
       * Wake one threads with a pending wait() call.
       * This allows wait() to return when an action causes the predicate passed to wait() to be false.
       *
       * Thread safety: This method is safe to call if both the notifyOne() call and corresponding action
       * occur while holding the lock used to wait().
       */
      void notifyOne();

      /**
       * Unlocks this lock, then block the current thread while waiting,
       * then reacquire this lock upon wakeup.
       *
       * This may be interrupted by a concurrent call to interruptOne().
       *
       * @throw InterruptedException
       */
      void wait(std::unique_lock<std::mutex> & lock);

      /**
       * Wait for the provided predicate function to return false.
       * Unlocks this lock, then block the current thread while waiting,
       * then reacquire this lock upon wakeup.
       *
       * This may be interrupted by a concurrent call to interruptOne().
       *
       * @throw InterruptedException
       */
      template<typename PredicateT>
      void wait(
            std::unique_lock<std::mutex> & lock,
            PredicateT predicate)
      {
         while (!throwIfInterrupted() && !predicate()) {
            _condition.wait(lock);
         }
      }

      /**
       * Wait until the provided timeout time is reached, or the condition is notified, or a spurious wakeup occurs.
       * This may be interrupted by a concurrent call to interruptOne().
       *
       * @throw InterruptedException
       */
      template<typename ClockT, typename DurationT>
      std::cv_status waitUntil(
            std::unique_lock<std::mutex> & lock,
            const std::chrono::time_point<ClockT, DurationT> & timeout_time)
      {
         throwIfInterrupted();
         std::cv_status status = _condition.wait_until(lock, timeout_time);
         throwIfInterrupted();
         return status;
      }

      template<typename RepT, typename DurationT>
      std::cv_status waitFor(
            std::unique_lock<std::mutex> & lock,
            const std::chrono::duration<RepT, DurationT> & duration)
      {
         throwIfInterrupted();
         std::cv_status status = _condition.wait_for(lock, duration);
         throwIfInterrupted();
         return status;
      }

   private:

      bool throwIfInterrupted();

      // Condition to signal events:
      // * call to interruptOne(),
      // * call to notifyOne() or notifyAll()
      std::condition_variable _condition;

      bool _interrupting{false};

   };

} // FedPro
