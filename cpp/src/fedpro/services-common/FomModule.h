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
#include <string>

namespace FedPro
{
   class FomModule
   {
   public:
      enum class Type : uint8_t
      {
         FILE, COMPRESSED, URL
      };

      /**
       * Create a FomModule from an file.
       */
      FomModule(
            std::wstring  fileName,
            std::string  content);

      /**
       * Create a FomModule from an URL.
       */
      explicit FomModule(std::wstring  url);

      FomModule(const FomModule &) = default;

      FomModule(FomModule &&) noexcept = default;

      FomModule & operator=(const FomModule &) = default;

      FomModule & operator=(FomModule &&) noexcept = default;

      constexpr  Type getType() const {
         return _type;
      }

      constexpr const std::wstring& getFileName() const {
         return _fileName;
      }

      constexpr const std::string& getFileContent() const {
         return _fileContent;
      }

      std::string getCompressedModule() const {
         // Unsupported
         return {};
      }

      constexpr const std::wstring& getUrl() const {
         return _url;
      }

      bool operator==(const FomModule &rhs) const;

      bool operator<(const FomModule &rhs) const;

   private:
      Type _type{Type::FILE};
      std::wstring _fileName;
      std::string _fileContent;
      std::wstring _url;
   };
}
