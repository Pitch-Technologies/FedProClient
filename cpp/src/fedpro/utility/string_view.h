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

#include <algorithm>
#include <stdexcept>
#include <string>

namespace FedPro
{
   /**
    * Lightweight character string wrapper that provides a subset of
    * std::basic_string_view API from C++17.
    */
   template<class CharT, class Traits = std::char_traits<CharT>>
   class basic_string_view
   {
   public:

      static constexpr size_t npos = size_t(-1);

      basic_string_view(
            const std::basic_string<CharT> & str) noexcept : _str{str.data()}, _strlen{str.length()}
      {
      }

      constexpr basic_string_view() = default;

      constexpr basic_string_view(
            const CharT * str,
            size_t strlen) noexcept : _str{str}, _strlen{strlen}
      {
      }

      basic_string_view(
            const CharT * str) noexcept : _str{str}, _strlen{Traits::length(str)}
      {
      }

      const CharT * begin() const
      {
         return _str;
      }

      const CharT * end() const
      {
         return _str + _strlen;
      }

      constexpr const CharT & operator[](size_t pos) const
      {
         return _str[pos];
      }

      constexpr const CharT & at(size_t pos) const
      {
         return _str[pos];
      }

      constexpr const CharT * data() const noexcept
      {
         return _str;
      }

      constexpr size_t size() const noexcept
      {
         return _strlen;
      }

      constexpr size_t length() const noexcept
      {
         return _strlen;
      }

      constexpr bool empty() const noexcept
      {
         return size() == 0;
      }

      void remove_prefix(size_t n) noexcept
      {
         if (n <= size()) {
            _str += n;
            _strlen -= n;
         }
      }

      basic_string_view substr(
            size_t pos = 0,
            size_t count = npos) const
      {
         if (pos > size()) {
            throw std::out_of_range("Position is the end of the string view");
         }

         size_t substr_len = (std::min) (count, size() - pos);
         return basic_string_view(_str + pos, substr_len);
      }

      int compare(basic_string_view rhs) const noexcept
      {
         // Parenthesis around std::min prevent collision with min macros from Windows.h
         size_t minsize = (std::min)(size(), rhs.size());
         int comparison = Traits::compare(data(), rhs.data(), minsize);
         if (comparison == 0) {
            if (size() < rhs.size()) {
               comparison = -1;
            } else if (size() > rhs.size()) {
               comparison = +1;
            }
         }
         return comparison;
      }

      bool starts_with(basic_string_view other) const noexcept
      {
         if (size() < other.size()) {
            return false;
         }

         return Traits::compare(data(), other.data(), other.size()) == 0;
      }

      size_t find(
            CharT ch,
            size_t pos = 0) const
      {
         if (pos >= _strlen)
            return npos;

         const CharT * it = Traits::find(_str + pos, _strlen - pos, ch);
         if (it == nullptr)
            return npos;
         else
            return it - _str;
      }

   private:

      const CharT * _str{nullptr};
      size_t _strlen{0};
   };

   // Redundant declaration for C++14 compatibility.
   template<class CharT, class Traits>
   constexpr size_t basic_string_view<CharT, Traits>::npos;

   using string_view = basic_string_view<char>;

   using wstring_view = basic_string_view<wchar_t>;

   inline bool operator<(
         string_view lhs,
         string_view rhs)
   {
      return lhs.compare(rhs) < 0;
   }

   inline bool operator==(
         string_view lhs,
         string_view rhs)
   {
      return lhs.compare(rhs) == 0;
   }

   inline bool operator!=(
         string_view lhs,
         string_view rhs)
   {
      return lhs.compare(rhs) != 0;
   }

   inline bool operator<(
         string_view lhs,
         const std::string & rhs)
   {
      return lhs < static_cast<string_view>(rhs);
   }

   inline bool operator<(
         const std::string & lhs,
         string_view rhs)
   {
      return static_cast<string_view>(lhs) < rhs;
   }

   inline bool operator<(
         wstring_view lhs,
         wstring_view rhs)
   {
      return lhs.compare(rhs) < 0;
   }

   inline bool operator==(
         wstring_view lhs,
         wstring_view rhs)
   {
      return lhs.compare(rhs) == 0;
   }

   inline bool operator!=(
         wstring_view lhs,
         wstring_view rhs)
   {
      return lhs.compare(rhs) != 0;
   }

   inline bool operator<(
         wstring_view lhs,
         const std::wstring & rhs)
   {
      return lhs < static_cast<wstring_view>(rhs);
   }

   inline bool operator<(
         const std::wstring & lhs,
         wstring_view rhs)
   {
      return static_cast<wstring_view>(lhs) < rhs;
   }

   template<class CharT>
   inline std::basic_string<CharT> operator+(
         const std::basic_string<CharT> & lhs,
         basic_string_view<CharT> rhs)
   {
      return lhs + std::basic_string<CharT>{rhs.data(), rhs.size()};
   }

   template<class CharT>
   inline std::basic_string<CharT> operator+(
         basic_string_view<CharT> lhs,
         const std::basic_string<CharT> & rhs)
   {
      return std::basic_string<CharT>{lhs.data(), lhs.size()} + rhs;
   }
} // FedPro