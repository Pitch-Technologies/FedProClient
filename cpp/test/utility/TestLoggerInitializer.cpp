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

#include <utility/LoggerInitializer.h>

#include <gtest/gtest.h>

using namespace FedPro;

class TestLoggerInitializer : public ::testing::Test
{
};

TEST_F(TestLoggerInitializer, getLowestReturnsLowest)
{
   spdlog::level::level_enum lowestLevel = LoggerInitializer::getLowest(
         {spdlog::level::off, spdlog::level::critical, spdlog::level::err, spdlog::level::warn, spdlog::level::info,
          spdlog::level::debug, spdlog::level::trace,});
   EXPECT_EQ(lowestLevel, spdlog::level::trace);
}

TEST_F(TestLoggerInitializer, getLowestThrows_When_NoArgumentIsPassed)
{
   ASSERT_THROW({
                   spdlog::level::level_enum lowestLevel = LoggerInitializer::getLowest({});
                }, std::invalid_argument);
}

TEST_F(TestLoggerInitializer, convertIntToLogLevelWorks_When_ValidLevelProvided)
{
   EXPECT_EQ(spdlog::level::trace, LoggerInitializer::toLogSeverityLevel(0));
   EXPECT_EQ(spdlog::level::off, LoggerInitializer::toLogSeverityLevel(6));
}

TEST_F(TestLoggerInitializer, convertIntToLogLevelThrows_When_NegativeNumberProvided)
{
   ASSERT_THROW({
                   LoggerInitializer::toLogSeverityLevel(-1);
                }, std::invalid_argument);
}

TEST_F(TestLoggerInitializer, convertIntToLogLevelThrows_When_InvalidNumberProvided)
{
   ASSERT_THROW({
                   LoggerInitializer::toLogSeverityLevel(7);
                }, std::invalid_argument);
}

TEST_F(TestLoggerInitializer, convertStringToLogLevelWorks_When_ValidStringProvided)
{
   EXPECT_EQ(spdlog::level::debug, LoggerInitializer::toLogSeverityLevel("deBUg"));
}

TEST_F(TestLoggerInitializer, convertStringToLogLevelThrows_When_InvalidStringProvided)
{
   ASSERT_THROW({
                   LoggerInitializer::toLogSeverityLevel("I am no log level");
                }, std::invalid_argument);
}

TEST_F(TestLoggerInitializer, convertLogLevelToStringsWorks)
{
   EXPECT_EQ("critical", LoggerInitializer::toString(spdlog::level::critical));
}
