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

#include "FomModule.h"

#include <functional>
#include <tuple>
#include <utility>

namespace FedPro
{
   FomModule::FomModule(
         std::wstring fileName,
         std::string content) : _type{Type::FILE}, _fileName{std::move(fileName)}, _fileContent{std::move(content)}
   {
   }

   FomModule::FomModule(std::wstring  url) : _type{Type::URL}, _url{std::move(url)}
   {
   }

   bool FomModule::operator==(const FomModule & rhs) const
   {
      return _type == rhs._type && _fileName == rhs._fileName && _fileContent == rhs._fileContent && _url == rhs._url;
   }

   bool FomModule::operator<(const FomModule & rhs) const
   {
      return std::make_tuple(
            _type, std::ref(_fileName), std::ref(_fileContent), std::ref(_url)) < std::make_tuple(
            rhs._type, std::ref(rhs._fileName), std::ref(rhs._fileContent), std::ref(rhs._url));
   }
}
