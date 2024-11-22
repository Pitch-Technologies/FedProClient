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

#include <cstdint>
#include <sstream>
#include <string>

namespace FedPro
{
   class LogUtil
   {
   private:
      static const int NUMBER_LENGTH = 3;

   public:
      static const std::string CLIENT_PREFIX;

      static std::string logPrefix(
            uint64_t sessionId,
            const std::string & caller);

      static std::string logPrefix(
            const std::string & sessionId,
            const std::string & caller);

      static std::string padNumString(int nr);

      static std::string padNumString(const std::string & nr);

      static std::string formatSessionId(uint64_t sessionId);
   };

} // FedPro