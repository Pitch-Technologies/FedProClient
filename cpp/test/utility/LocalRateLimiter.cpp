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


#include "LocalRateLimiter.h"

#include <mutex>

LocalRateLimiter::LocalRateLimiter(
      std::mutex * preInsertLock,
      std::mutex * postInsertLock) : _preInsertLock(preInsertLock), _postInsertLock(postInsertLock)
{
}

void LocalRateLimiter::preInsert(uint32_t size) noexcept
{
   if (_preInsertLock != nullptr) {
      std::unique_lock<std::mutex> lock(*_preInsertLock);
   }
}

void LocalRateLimiter::postInsert(uint32_t size) noexcept
{
   if (_postInsertLock != nullptr) {
      std::unique_lock<std::mutex> lock(*_postInsertLock);
   }
}

std::string LocalRateLimiter::toString() noexcept
{
   return "LocalRateLimiter";
}
