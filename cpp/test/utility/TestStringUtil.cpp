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

// Silence clang-tidy issues reported for gtest macros.
// NOLINTBEGIN(cppcoreguidelines-avoid-non-const-global-variables)

#include "utility/StringUtil.h"

#include <gtest/gtest.h>

using namespace FedPro;

class TestStringUtil : public ::testing::Test
{
};

TEST_F(TestStringUtil, toWString)
{
   EXPECT_EQ(toWString("hello"), L"hello");
   EXPECT_EQ(toWString("Bj\xC3\xB6rn"), L"Bj\x00F6rn");
}

TEST_F(TestStringUtil, toString)
{
   EXPECT_EQ(toString(L"hello"), "hello");
   EXPECT_EQ(toString(L"Bj\x00F6rn"), "Bj\xC3\xB6rn");
}

TEST_F(TestStringUtil, toLower)
{
   std::string empty;
   EXPECT_EQ((toLower(empty), empty), std::string());

   std::string hello("HeLlO");
   EXPECT_EQ((toLower(hello), hello), "hello");
}

TEST_F(TestStringUtil, toLowerWideCharacters)
{
   std::wstring empty;
   EXPECT_EQ((toLower(empty), empty), std::wstring());

   std::wstring world(L"WoRlD");
   EXPECT_EQ((toLower(world), world), L"world");
}

TEST_F(TestStringUtil, equalInsensitiveString)
{
   EXPECT_TRUE(equalInsensitive("HeLlO", "hELLo"));
   EXPECT_FALSE(equalInsensitive("HeLlO", "hELL"));
}

TEST_F(TestStringUtil, equalInsensitiveStringWideCharacters)
{
   EXPECT_TRUE(equalInsensitive(L"WoRlD", L"WOrLd"));
   EXPECT_FALSE(equalInsensitive(L"WoRlD", L"WORLDS"));
}

TEST_F(TestStringUtil, splitString)
{
   const string_view empty;
   EXPECT_EQ(splitString(empty, ','), std::vector<string_view>());

   const string_view sequence{"one,two,three"};
   EXPECT_EQ(splitString(sequence, ','), std::vector<string_view>({"one", "two", "three"}));
}

TEST_F(TestStringUtil, splitStringWideCharacters)
{
   const wstring_view empty;
   EXPECT_EQ(splitString(empty, ','), std::vector<wstring_view>());

   const wstring_view wideSequence{L"one,two,three"};
   EXPECT_EQ(splitString(wideSequence, ','), std::vector<wstring_view>({L"one", L"two", L"three"}));
}

TEST_F(TestStringUtil, splitStringWithRegex)
{
   const string_view empty;
   EXPECT_EQ(splitString(empty, std::regex("[\r\n]+")), std::vector<string_view>({empty}));

   const string_view lines{"one=1\r\ntwo=2"};
   EXPECT_EQ(splitString(lines, std::regex("[\r\n]+")), std::vector<string_view>({"one=1", "two=2"}));
}

TEST_F(TestStringUtil, splitStringWithRegexMaxParts)
{
   const string_view lines{"one=1\r\ntwo=2\r\nthree=3"};
   EXPECT_EQ(splitString(lines, std::regex("[\r\n]+"), 0), std::vector<string_view>());
   EXPECT_EQ(splitString(lines, std::regex("[\r\n]+"), 2), std::vector<string_view>({"one=1", "two=2\r\nthree=3"}));
   EXPECT_EQ(splitString(lines, std::regex("[\r\n]+"), 3), std::vector<string_view>({"one=1", "two=2", "three=3"}));
}

TEST_F(TestStringUtil, splitStringWithRegexWideCharacters)
{
   const wstring_view empty;
   EXPECT_EQ(splitString(empty, std::wregex(L"[\r\n]+")), std::vector<wstring_view>({empty}));

   const wstring_view lines{L"one=1\r\ntwo=2"};
   EXPECT_EQ(splitString(lines, std::wregex(L"[\r\n]+")), std::vector<wstring_view>({L"one=1", L"two=2"}));
}

TEST_F(TestStringUtil, splitStringWithRegexMaxPartsWideCharacters)
{
   const wstring_view lines{L"one=1\r\ntwo=2\r\nthree=3"};
   EXPECT_EQ(splitString(lines, std::wregex(L"[\r\n]+"), 0), std::vector<wstring_view>());
   EXPECT_EQ(splitString(lines, std::wregex(L"[\r\n]+"), 2), std::vector<wstring_view>({L"one=1", L"two=2\r\nthree=3"}));
   EXPECT_EQ(splitString(lines, std::wregex(L"[\r\n]+"), 3), std::vector<wstring_view>({L"one=1", L"two=2", L"three=3"}));
}

TEST_F(TestStringUtil, unescape)
{
   std::string name("Jack O\\'Neill");
   EXPECT_EQ((unescape(name, '\\'), name), "Jack O'Neill");
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)