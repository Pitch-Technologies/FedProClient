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

#include <fedpro/Config.h>

#include <stdexcept>
#include <system_error>

namespace FedPro
{
   class IOException : public std::runtime_error
   {
   public:

      FEDPRO_EXPORT explicit IOException(const std::string & message);

      FEDPRO_EXPORT explicit IOException(std::error_code errorCode);

      FEDPRO_EXPORT IOException(const std::string & message, std::error_code code);
   };
}