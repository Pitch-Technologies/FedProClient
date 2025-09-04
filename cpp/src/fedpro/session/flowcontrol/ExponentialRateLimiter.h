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

#include "RateLimiter.h"

namespace FedPro
{
   class ExponentialRateLimiter : public RateLimiter
   {
   public:
      explicit ExponentialRateLimiter(size_t queueSize) noexcept;

      ~ExponentialRateLimiter() noexcept override = default;

      void preInsert(size_t size) noexcept override;

      void postInsert(size_t size) noexcept override;

      std::string toString() noexcept override;

   private:
      const size_t _cutoff;
   };

} // FedPro
