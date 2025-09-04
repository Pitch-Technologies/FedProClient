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

#include "FomModuleLoader.h"

#include "utility/StringUtil.h"
#include "utility/string_view.h"

#include <fstream>
#include <sstream>

namespace FedPro
{
   FomModuleSet FomModuleLoader::getFomModuleSet(const std::vector<std::wstring> & fomModules) const
   {
      FomModuleSet result;
      for (const std::wstring & fomModule : fomModules) {
         result.emplace(getFomModule(fomModule));
      }
      return result;
   }

   FomModule FomModuleLoader::getFomModule(const std::wstring & fomModule) const
   {
      std::ifstream file{toString(fomModule)};
      if (file.is_open()) {
         try {
            return getFomModuleFromFile(fomModule, file);
         } catch (const std::exception & ) {
            // Ignore file I/O errors, fallback to URI
         }
      }

      return getFomModuleFromURI(fomModule);
   }

   FomModule FomModuleLoader::getFomModuleFromFile(
         const std::wstring & fileName,
         std::istream & file) const
   {
      std::stringstream buffer;
      buffer << file.rdbuf();
      return FomModule{fileName, buffer.str()};
   }

   FomModule FomModuleLoader::getFomModuleFromURI(const std::wstring & fomModuleURI) const
   {
      const wstring_view expectedPrefix{L"file://"};
      const wstring_view fomModule(fomModuleURI);
      const wstring_view fomModulePrefix(fomModule.substr(0, expectedPrefix.length()));
      if (equalInsensitive(expectedPrefix, fomModulePrefix)) {
         const wstring_view fomModuleWidePath{fomModule.substr(expectedPrefix.size())};
         std::string fomModulePath{toString(fomModuleWidePath)};
         std::ifstream file(fomModulePath);
         if (file.is_open()) {
            try {
               return getFomModuleFromFile(fomModuleWidePath.data(), file);
            } catch (const std::exception &) {
               // Ignore file I/O errors, fallback to URL
            }
         }
      }

      // Don't know what this is. Let someone else try it as a URL.
      return FomModule(fomModuleURI);
   }

} // FedPro