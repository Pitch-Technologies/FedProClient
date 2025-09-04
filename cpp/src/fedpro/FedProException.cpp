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

#include <fedpro/FedProExceptions.h>

#include <cstring>
#include <typeinfo>
#include <utility>

namespace FedPro
{
   FedProException::FedProException(std::string message) noexcept
         : _message{std::move(message)}
   {
   }

   const char * FedProException::what() const noexcept
   {
      return _message.c_str();
   }

   const char * FedProException::getName() const noexcept
   {
      // If you only want to remove the class prefix, just assign "class " to the following variable instead:
      const char * prefix = "class FedPro::";

      // Always null-terminated
      const char * exceptionName = typeid(*this).name();

      size_t prefixLength = std::strlen(prefix);
      if (std::strlen(exceptionName) >= prefixLength && std::strncmp(exceptionName, prefix, prefixLength) == 0) {
         exceptionName += prefixLength;
      }
      return exceptionName;
   }
}
