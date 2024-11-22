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

#include "StringUtil.h"

#include <algorithm>
#include <cctype>
#include <cwctype>   // for std::towlower
#include <codecvt>
#include <locale>
#include <sstream>
#include <stdexcept>

namespace FedPro
{

   std::wstring make_wstring(string_view str)
   {
      std::wstring wstr;
      wstr.resize(str.length());
      for (size_t i = 0; i < str.length(); ++i)
         wstr[i] = static_cast<wchar_t>(str[i]);

      return wstr;
   }

   std::string make_string_ascii(
         wstring_view wideStr)
   {
      std::string str;
      str.resize(wideStr.length());
      for (size_t i = 0; i < str.length(); ++i) {
         if (wideStr[i] > 127) {
            throw std::invalid_argument("Non-ASCII character found in input");
         }

         str[i] = static_cast<char>(wideStr[i]);
      }

      return str;
   }

   std::string toString(wstring_view s)
   {
      // Use static thread_local storage for performance
      // because wstring_convert is expensive to create.
      static thread_local std::wstring_convert<std::codecvt_utf8<wchar_t>, wchar_t> cvt;

      // If sizeof(wchar_t) == 2, which applies to Windows, convert from UTF-16 to UTF-8.
      // If sizeof(wchar_t) == 4, which applies to Linux, convert from UTF-32 to UTF-8.
      return cvt.to_bytes(s.begin(), s.end());
   }

   std::string toString(string_view s)
   {
      return {s.begin(), s.end()};
   }

   std::wstring toWString(wstring_view s)
   {
      return {s.begin(), s.end()};
   }

   std::wstring toWString(string_view s)
   {
      // Use static thread_local storage for performance
      // because wstring_convert is expensive to create.
      static thread_local std::wstring_convert<std::codecvt_utf8<wchar_t>, wchar_t> cvt;

      // If sizeof(wchar_t) == 2, which applies to Windows, convert from UTF-8 to UTF-16.
      // If sizeof(wchar_t) == 4, which applies to Linux, convert from UTF-8 to UTF-32.
      return cvt.from_bytes(s.begin(), s.end());
   }

   void toLower(std::string & str)
   {
      for (char & c : str) {
         c = static_cast<char>(std::tolower(static_cast<unsigned char>(c)));
      }
   }

   void toLower(std::wstring & wideStr)
   {
      for (wchar_t & c : wideStr) {
         c = std::towlower(c);
      }
   }

   bool equalInsensitive(
         wchar_t lhs,
         wchar_t rhs)
   {
      return std::towlower(lhs) == std::towlower(rhs);
   }

   bool equalInsensitive(
         wstring_view lhs,
         wstring_view rhs)
   {
      return std::equal(
            lhs.begin(), lhs.end(), rhs.begin(), rhs.end(), [](
                  wchar_t lhs,
                  wchar_t rhs) {
               return std::towlower(lhs) == std::towlower(rhs);
            });
   }

   bool equalInsensitive(
         string_view lhs,
         string_view rhs)
   {
      return std::equal(
            lhs.begin(), lhs.end(), rhs.begin(), rhs.end(), [](
                  wchar_t lhs,
                  wchar_t rhs) {
               return std::tolower(lhs) == std::tolower(rhs);
            });
   }

   template<typename CharT>
   static std::vector<basic_string_view<CharT>> split_string_generic(
         basic_string_view<CharT> str,
         CharT delim,
         size_t max_parts)
   {
      std::vector<basic_string_view<CharT>> parts;
      size_t part_begin = 0;
      size_t part_end = std::min(str.length(), str.find(delim, part_begin));
      while (part_begin != str.length()) {
         size_t part_len = part_end - part_begin;
         parts.emplace_back(&str.at(part_begin), part_len);
         if (parts.size() >= max_parts)
            break;

         part_begin = std::min(str.length(), part_end + 1);
         part_end = std::min(str.length(), str.find(delim, part_begin));
      }

      return parts;
   }

   std::vector<wstring_view> splitString(
         wstring_view wideStr,
         wchar_t delim,
         size_t max_parts)
   {
      return split_string_generic<wchar_t>(wideStr, delim, max_parts);
   }

   std::vector<wstring_view> splitString(
         wstring_view wideStr,
         const std::wregex & delim,
         size_t max_parts)
   {
      std::vector<wstring_view> parts;
      if (max_parts == 0) {
         return parts;
      }

      std::wcregex_token_iterator it(
            wideStr.begin(), wideStr.end(), delim, -1);
      std::wcregex_token_iterator ite;
      for (; it != ite; ++it) {
         if (it->first < wideStr.begin() || it->first > wideStr.end()) {
            throw std::logic_error("String part is out of bound");
         }

         if (parts.size() + 1 < max_parts) {
            parts.emplace_back(it->first, it->length());
         } else {
            size_t lastPartLen = wideStr.end() - it->first;
            parts.emplace_back(it->first, lastPartLen);
            break;
         }
      }

      return parts;
   }

   std::vector<string_view> splitString(
         string_view str,
         char delim,
         size_t max_parts)
   {
      return split_string_generic<char>(str, delim, max_parts);
   }

   std::vector<string_view> splitString(
         string_view str,
         const std::regex & delim,
         size_t max_parts)
   {
      std::vector<string_view> parts;
      if (max_parts == 0) {
         return parts;
      }

      std::cregex_token_iterator it(
            str.begin(), str.end(), delim, -1);
      std::cregex_token_iterator ite;
      for (; it != ite; ++it) {
         if (it->first < str.begin() || it->first > str.end()) {
            throw std::logic_error("String part is out of bound");
         }

         if (parts.size() + 1 < max_parts) {
            parts.emplace_back(it->first, it->length());
         } else {
            size_t lastPartLen = str.end() - it->first;
            parts.emplace_back(it->first, lastPartLen);
            break;
         }
      }

      return parts;
   }

   std::wstring replaceAll(wstring_view input, const std::wstring & substrToReplace, const std::wstring & replacement)
   {
      std::wstring result = toWString(input);
      size_t startPos{0};
      while ((startPos = result.find(substrToReplace, startPos)) != std::string::npos) {
         result.replace(startPos, substrToReplace.length(), replacement);
      }
      return result;
   }

   std::vector<std::wstring> splitStringByNewlines(const std::wstring & input)
   {
      std::vector<std::wstring> result;
      std::wstringstream wss(input);
      std::wstring line;
      while (std::getline(wss, line)) {
         result.push_back(line);
      }
      return result;
   }

   template<class CharT>
   std::pair<basic_string_view<CharT>,basic_string_view<CharT>> genericSplitPair(
         basic_string_view<CharT> input,
         CharT delimiter) {
      // Verify that the toSplit contains at least one '=' character.
      size_t firstPos = input.find(delimiter);
      if (firstPos == string_view::npos) {
         throw std::invalid_argument("'" + toString(input) + "' must have the format '<key>=<value>'.");
      }

      return {input.substr(0, firstPos), input.substr(firstPos + 1)};
   }

         // Split at first occurrence of the delimiter argument.
   std::pair<string_view, string_view> splitIntoPair(
         string_view toSplit,
         char delimiter)
   {
      return genericSplitPair(toSplit, delimiter);
   }

   std::pair<wstring_view, wstring_view> splitIntoPair(
         wstring_view toSplit,
         wchar_t delimiter)
   {
      return genericSplitPair(toSplit, delimiter);
   }

   // Removes all occurrences of the provided char (escapeChar) in the provided string (str).
   void unescape(
         std::string & str,
         char escapeChar)
   {
      size_t escapePos = str.find(escapeChar);
      while (escapePos != std::string::npos) {
         str.erase(escapePos, 1);
         escapePos = str.find(escapeChar, escapePos + 1);
      }
   }

}
