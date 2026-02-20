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

#include "InterruptibleCondition.h"
#include <fedpro/FedProExceptions.h>

#include <chrono>
#include <memory>
#include <mutex>
#include <utility>

namespace FedPro
{
   template<typename FunctionType, typename DurationRepType, class DurationUnitType >
   class FixedRateExecutor
   {
   public:
      using Duration = std::chrono::duration<DurationRepType, DurationUnitType>;

      FixedRateExecutor(
            FunctionType function,
            Duration initialDelay,
            Duration period)
            : _function{std::move(function)},
              _initialDelay{initialDelay},
              _period{period},
              _state{std::make_shared<InterruptibleConditionState>()}
      {
      }

      FixedRateExecutor(const FixedRateExecutor &) = default;

      FixedRateExecutor(FixedRateExecutor &&) noexcept = default;

      FixedRateExecutor & operator=(const FixedRateExecutor &) = default;

      FixedRateExecutor & operator=(FixedRateExecutor &&) noexcept = default;

      std::shared_ptr<InterruptibleConditionState> getState() const
      {
         return _state;
      }

      void operator()()
      {
         try {
            auto nextTimePoint = std::chrono::steady_clock::now() + _initialDelay;
            std::unique_lock<std::mutex> lock{_state->mutex()};
            while (true) {
               while (_state->condition().waitUntil(lock, nextTimePoint) != std::cv_status::timeout);
               nextTimePoint += _period;
               _function();
            }
         } catch (const InterruptedException &) {
         }
      }

   private:

      FunctionType _function;
      const Duration _initialDelay;
      const Duration _period;
      std::shared_ptr<InterruptibleConditionState> _state;
   };

   template<typename FunctionType, typename DurationRepType, class DurationUnitType >
   auto makeFixedRateExecutor(
         FunctionType function,
         std::chrono::duration<DurationRepType, DurationUnitType> initialDelay,
         std::chrono::duration<DurationRepType, DurationUnitType> period)
   {
       return FixedRateExecutor<FunctionType, DurationRepType, DurationUnitType>(std::move(function), initialDelay, period);
   }
} // FedPro