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

#include "FomModule.h"
#include "FomModuleSet.h"

#include <iosfwd>
#include <vector>

namespace FedPro
{
   class FomModuleLoader
   {
   public:

      FomModuleSet getFomModuleSet(const std::vector<std::wstring> & fomModules) const;

      FomModule getFomModule(const std::wstring & fomModule) const;

      FomModule getFomModuleFromFile(const std::wstring & fileName, std::istream & file) const;

      FomModule getFomModuleFromURI(const std::wstring & fomModuleURI) const;
   };

} // FedPro
