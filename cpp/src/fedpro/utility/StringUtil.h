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

#include "string_view.h"

#include <regex>
#include <string>
#include <vector>

namespace FedPro
{
   std::wstring make_wstring(
         string_view str);

   /**
    * Make an ASCII-encoded std::string from a std::wstring.
    * @throw std::invalid_argument if the input string is not ASCII encoded.
    */
   std::string make_string_ascii(
         wstring_view wideStr);

   /**
    * Create a std::string from a wire character string, using std::locale.
    * @param s Wide character string
    * @return UTF-8 encoded character string
    */
   std::string toString(wstring_view s);

   /**
    * Create a std::wstring from a character string, using std::locale.
    * @param s UTF-8 encoded character string
    * @return Wide character string
    */
   std::wstring toWString(string_view s);

   /**
    * Create a std::string from a string_view.
    * @param s basic_string_view<char>
    * @return std::string
    */
   std::string toString(string_view s);

   /**
    * Create a std::wstring from a wstring_view.
    * @param s basic_string_view<wchar_t>
    * @return std::wstring
    */
   std::wstring toWString(wstring_view s);

   void toLower(std::string & str);

   void toLower(std::wstring & wideStr);

   /**
    * Case-insensitive comparison of strings.
    * Note this function is NOT unicode aware.
    */
   bool equalInsensitive(
         string_view lhs,
         string_view rhs);

   /**
    * Case-insensitive comparison of wide characters.
    * Note this function is NOT unicode aware.
    */
   bool equalInsensitive(
         wchar_t lhs,
         wchar_t rhs);

   /**
    * Case-insensitive comparison of wide strings.
    * Note this function is NOT unicode aware.
    */
   bool equalInsensitive(
         wstring_view lhs,
         wstring_view rhs);

   std::vector<wstring_view> splitString(
         wstring_view wideStr,
         wchar_t delim,
         size_t max_parts = size_t(-1));

   std::vector<wstring_view> splitString(
         wstring_view wideStr,
         const std::wregex & delim,
         size_t max_parts = size_t(-1));

   std::vector<string_view> splitString(
         string_view str,
         char delim,
         size_t max_parts = size_t(-1));

   std::vector<string_view> splitString(
         string_view str,
         const std::regex & delim,
         size_t max_parts = size_t(-1));

   std::wstring replaceAll(
         wstring_view input,
         const std::wstring & substrToReplace,
         const std::wstring & replacement);

   std::vector<std::wstring> splitStringByNewlines(const std::wstring & input);

   std::pair<string_view, string_view> splitIntoPair(
         string_view toSplit,
         char delimiter);

   std::pair<wstring_view, wstring_view> splitIntoPair(
         wstring_view toSplit,
         wchar_t delimiter);

   void unescape(
         std::string & str,
         char escapeChar);
}
