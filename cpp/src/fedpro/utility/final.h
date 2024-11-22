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

#include <utility>

namespace FedPro
{
   /**
    * final_guard executes the provided function when going out of scope.
    *
    * This may be used as local variable for RAII-style object lifetime management.
    */
   template<class Function>
   class final_guard
   {
   public:

      /**
       * Wait until processing is enabled, then indicate beginning of processing.
       */
      explicit final_guard(Function finalFunc) : _finalFunc(std::move(finalFunc))
      {
      }

      /**
       * Indicate end of processing.
       */
      ~final_guard()
      {
         _finalFunc();
      }

      final_guard(const final_guard &) = delete;

      final_guard(final_guard &&) noexcept = default;

      final_guard & operator=(const final_guard &) = delete;

      final_guard & operator=(final_guard &&) noexcept = default;

   private:

      Function _finalFunc;
   };

   template<class Function>
   final_guard<Function> make_final(Function function)
   {
      return final_guard<Function>(std::move(function));
   }
} // FedPro

