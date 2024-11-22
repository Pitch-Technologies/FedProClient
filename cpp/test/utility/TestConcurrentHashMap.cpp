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

#include "session/ConcurrentHashMap.h"

#include <gtest/gtest.h>

using namespace FedPro;

class TestConcurrentHashMap : public ::testing::Test
{
};

TEST_F(TestConcurrentHashMap, ctor)
{
   using BooleanMap = ConcurrentHashMap<bool, bool>;
   EXPECT_NO_THROW(BooleanMap{});
}

TEST_F(TestConcurrentHashMap, moveAndInsert)
{
   ConcurrentHashMap<int, int> map;
   map.moveAndInsert(2, 4);
   EXPECT_EQ(*map.get(2), 4);
}

TEST_F(TestConcurrentHashMap, remove)
{
   ConcurrentHashMap<int, int> map;
   map.moveAndInsert(2, 4);
   EXPECT_EQ(*map.remove(2), 4);
   EXPECT_EQ(map.remove(2), nullptr);
}

TEST_F(TestConcurrentHashMap, swap)
{
   ConcurrentHashMap<int, int> map1;
   map1.moveAndInsert(2, 4);

   std::unordered_map<int, int> map2;
   map1.swap(map2);

   EXPECT_EQ(map1.get(2), nullptr);
   EXPECT_EQ(map2[2], 4);
}

TEST_F(TestConcurrentHashMap, clear)
{
   ConcurrentHashMap<int, int> map;
   map.moveAndInsert(2, 4);
   map.clear();
   EXPECT_EQ(map.get(2), nullptr);
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)