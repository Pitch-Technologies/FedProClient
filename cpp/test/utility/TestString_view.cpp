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

#include "utility/string_view.h"

#include <gtest/gtest.h>

#include <cmath>

using namespace FedPro;

class TestString : public ::testing::Test
{
};

TEST_F(TestString, ctorDefault)
{
   EXPECT_EQ(string_view{}, string_view{});
   EXPECT_TRUE(string_view{}.empty());
}

TEST_F(TestString, ctorNullTerminatedString)
{
   EXPECT_EQ(string_view{"test"}, string_view{"test"});
   EXPECT_EQ(string_view{"test"}.length(), 4);
}

TEST_F(TestString, ctorExplicitLength)
{
   EXPECT_EQ(string_view("test", 3), string_view{"tes"});
   EXPECT_EQ(string_view("test", 3).length(), 3);
}

TEST_F(TestString, beginAndEnd)
{
   string_view empty{};
   EXPECT_EQ(empty.begin(), empty.end());

   string_view test{"test"};
   EXPECT_NE(test.begin(), test.end());
   EXPECT_EQ(*test.begin(), 't');
}

TEST_F(TestString, subscriptOperator)
{
   string_view test{"test"};
   EXPECT_EQ(test[0], 't');
   EXPECT_EQ(test[1], 'e');
   EXPECT_EQ(test[2], 's');
   EXPECT_EQ(test[3], 't');
}

TEST_F(TestString, at)
{
   string_view test{"test"};
   EXPECT_EQ(test.at(0), 't');
   EXPECT_EQ(test.at(1), 'e');
   EXPECT_EQ(test.at(2), 's');
   EXPECT_EQ(test.at(3), 't');
}

TEST_F(TestString, data)
{
   string_view test{"test"};
   EXPECT_TRUE(std::equal(test.data(), test.data() + test.size(), test.begin()));
}

TEST_F(TestString, length)
{
   EXPECT_EQ(string_view{}.length(), 0);
   EXPECT_EQ(string_view{"test"}.length(), 4);
}

TEST_F(TestString, remove_prefix)
{
   string_view test{"test"};
   EXPECT_EQ((test.remove_prefix(0), test), "test");
   EXPECT_EQ((test.remove_prefix(1), test), "est");
   EXPECT_EQ((test.remove_prefix(3), test), "");
}

TEST_F(TestString, substr)
{
   string_view test{"test"};
   EXPECT_EQ(test.substr(), test);
   EXPECT_EQ(test.substr(1), "est");
   EXPECT_EQ(test.substr(4), "");
   EXPECT_THROW(test.substr(5), std::out_of_range);
}

TEST_F(TestString, compare)
{
   string_view empty{};
   string_view rest{"rest"};
   string_view test{"test"};

   EXPECT_TRUE(empty.compare(empty) == 0);
   EXPECT_TRUE(rest.compare(rest) == 0);
   EXPECT_TRUE(test.compare(test) == 0);

   EXPECT_NE(empty.compare(test) > 0, test.compare(empty) > 0);
   EXPECT_NE(rest.compare(test) > 0, test.compare(rest) > 0);
}

TEST_F(TestString, starts_with_returns_true)
{
   EXPECT_TRUE(string_view("hello").starts_with(""));
   EXPECT_TRUE(string_view("hello").starts_with("he"));
   EXPECT_TRUE(string_view("hello").starts_with("hello"));
}

TEST_F(TestString, starts_with_returns_false)
{
   EXPECT_FALSE(string_view("hello").starts_with("ha"));
   EXPECT_FALSE(string_view("hello").starts_with("hello world"));
}

TEST_F(TestString, find)
{
   string_view test{"test"};

   EXPECT_EQ(test.find('t'), 0);
   EXPECT_EQ(test.find('e'), 1);
   EXPECT_EQ(test.find('s'), 2);
   EXPECT_EQ(test.find('t', 1), 3);
   EXPECT_EQ(test.find('t', 4), string_view::npos);
   EXPECT_EQ(test.find('a'), string_view::npos);
}

TEST_F(TestString, operatorLess)
{
   EXPECT_TRUE(string_view{} < string_view{"test"} );
   EXPECT_FALSE(string_view{"test"} < string_view{"test"});
}

TEST_F(TestString, operatorNotEqual)
{
   EXPECT_TRUE(string_view{"test"} != string_view{});
   EXPECT_FALSE(string_view{"test"} != string_view{"test"});
}


// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)