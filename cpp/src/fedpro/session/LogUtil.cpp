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

#include "LogUtil.h"

#include <iomanip>

namespace FedPro
{
   const std::string LogUtil::CLIENT_PREFIX = "Client";

   std::string LogUtil::logPrefix(
         uint64_t sessionId,
         const std::string & caller)
   {
      return logPrefix(formatSessionId(sessionId), caller);
   }

   std::string LogUtil::logPrefix(
         const std::string & sessionIdString,
         const std::string & caller)
   {
      std::ostringstream stream;
      stream << caller << " Session " << sessionIdString;
      return stream.str();
   }

   std::string LogUtil::padNumString(int nr)
   {
      return padNumString(std::to_string(nr));
   }

   std::string LogUtil::padNumString(const std::string & nr)
   {
      std::string numStr;
      for (int i = 0; i < NUMBER_LENGTH - static_cast<int>(nr.length()); i++) {
         numStr += ' ';
      }
      numStr += nr;
      return numStr;
   }

   std::string LogUtil::formatSessionId(uint64_t sessionId)
   {
      std::ostringstream stream;
      // @formatter:off
      stream << std::setfill('0') << std::hex
             << std::setw(4) << ((sessionId >> 48) & 0xffff) << '-'
             << std::setw(4) << ((sessionId >> 32) & 0xffff) << '-'
             << std::setw(4) << ((sessionId >> 16) & 0xffff) << '-'
             << std::setw(4) << (sessionId & 0xffff);
      // @formatter:on
      return stream.str();
   }

} // FedPro
