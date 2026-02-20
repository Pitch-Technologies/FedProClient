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

#include <fedpro/IOException.h>

namespace FedPro
{
   IOException::IOException(const std::string & message)
         : std::runtime_error{message}
   {
   }

   IOException::IOException(std::error_code errorCode)
         : std::runtime_error{errorCode.message()}
   {
   }

   IOException::IOException(
         const std::string & message,
         std::error_code errorCode)
         : std::runtime_error{message + ": " + errorCode.message()}
   {
   }
} // FedPro
