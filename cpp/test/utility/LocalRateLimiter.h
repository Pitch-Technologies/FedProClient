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

#include <session/flowcontrol/RateLimiter.h>

#include <mutex>

class LocalRateLimiter : public FedPro::RateLimiter
{
public:
   explicit LocalRateLimiter(
         std::mutex * preInsertLock = nullptr,
         std::mutex * postInsertLock = nullptr);

   void preInsert(uint32_t size) noexcept override;

   void postInsert(uint32_t size) noexcept override;

   std::string toString() noexcept override;

   std::mutex * _preInsertLock;
   std::mutex * _postInsertLock;
};

