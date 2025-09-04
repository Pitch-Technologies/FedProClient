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

#include <queue>
#include <sstream>

namespace FedPro
{
   // These methods assume that elements of the passed container works with operator<<.

   template<typename E, typename SizeType>
   std::string sequenceToString(
         E * sequence,
         SizeType size)
   {
      std::ostringstream oss;
      oss << "[";
      bool first = true;
      for (SizeType i = 0; i < size; i++) {
         if (!first) {
            oss << ", ";
         } else {
            first = false;
         }
         oss << sequence[i];
      }
      oss << "]";
      return oss.str();
   }

   template<typename E>
   std::string queueToString(const std::queue<E> & queue)
   {
      std::ostringstream oss;
      oss << "[";
      if (!queue.empty()) {
         const E & element = queue.front();
         oss << element;
         if (queue.size() > 1) {
            oss << ", ...";
         }
      }
      oss << "]";
      return oss.str();
   }

} // FedPro
