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

#include "LoggerInitializer.h"

#include "StringUtil.h"

#include <spdlog/sinks/rotating_file_sink.h>

namespace FedPro
{

   void LoggerInitializer::initialize(const Properties & settings)
   {
      int consoleLevelInt = settings.getInt(SETTING_NAME_CONSOLE_LOG_LEVEL, DEFAULT_CONSOLE_LOG_LEVEL);
      int rotatingFileLevelInt = settings.getInt(SETTING_NAME_ROTATING_FILE_LOG_LEVEL, DEFAULT_ROTATING_FILE_LOG_LEVEL);
      std::string path = settings.getString(SETTING_NAME_ROTATING_FILE_LOG_PATH, DEFAULT_ROTATING_FILE_LOG_PATH);

      // Overwrite the default logger with our own, without a name to avoid it being a part of the log messages.
      auto fedProLogger = std::make_shared<spdlog::logger>(FED_PRO_LOGGER_NAME);
      spdlog::set_default_logger(fedProLogger);

      // Create and link sinks (output destinations) with our logger.
      auto consoleSink = createConsoleSink(toLogSeverityLevel(consoleLevelInt));
      spdlog::default_logger()->sinks().push_back(consoleSink);

      std::string pathToUse = path.empty() ? DEFAULT_ROTATING_FILE_LOG_PATH : path;
      LogLevel rotatingFileLevel = toLogSeverityLevel(rotatingFileLevelInt);

      LogLevel lowestLevel = consoleSink->level();

      if (rotatingFileLevel != spdlog::level::off) {
         auto rotatingFileSink = createRotatingFileSink(rotatingFileLevel, pathToUse);
         spdlog::default_logger()->sinks().push_back(rotatingFileSink);
         // No need to create lower severity level logs than what the sink with the lowest level creates.
         lowestLevel = getLowest({consoleSink->level(), rotatingFileSink->level()});
      }

      spdlog::default_logger()->set_level(lowestLevel);
      spdlog::set_pattern(DEFAULT_LOG_PATTERN);

      Properties settingsToLog;
      settingsToLog.setString(SETTING_NAME_CONSOLE_LOG_LEVEL, toString(consoleSink->level()));
      settingsToLog.setString(SETTING_NAME_ROTATING_FILE_LOG_LEVEL, toString(rotatingFileLevel));
      settingsToLog.setString(SETTING_NAME_ROTATING_FILE_LOG_PATH, pathToUse);
      SPDLOG_INFO("Federate Protocol client log settings used:\n" + settingsToLog.toPrettyString());
   }

   LogLevel LoggerInitializer::getLowest(std::initializer_list<LogLevel> levels)
   {
      if (levels.size() == 0) {
         throw std::invalid_argument{"At least one log severity level must be provided"};
      }

      // 'off' is considered the highest level.
      LogLevel lowestLevel = spdlog::level::off;
      for (const auto & level : levels) {
         if (level < lowestLevel) {
            lowestLevel = level;
         }
      }
      return lowestLevel;
   }

   LogLevel LoggerInitializer::toLogSeverityLevel(int levelAsInt)
   {
      int minLevel{static_cast<int>(spdlog::level::trace)};
      int maxLevel{static_cast<int>(spdlog::level::off)};
      if (levelAsInt < minLevel || maxLevel < levelAsInt) {
         throw std::invalid_argument{
               "'" + std::to_string(levelAsInt) + "' is not a valid log severity level. Must be in range " +
               std::to_string(minLevel) + "-" + std::to_string(maxLevel) + "."};
      }
      return static_cast<spdlog::level::level_enum>(levelAsInt);
   }

   LogLevel LoggerInitializer::toLogSeverityLevel(string_view levelAsView)
   {
      std::string levelAsLowerCase = FedPro::toString(levelAsView);
      toLower(levelAsLowerCase);
      LogLevel logLevel = spdlog::level::from_str(levelAsLowerCase);

      if (logLevel == spdlog::level::off && levelAsView != "off") {
         // This means from_str() failed and returned level 'off'. But we want the default level instead of 'off'.
         throw std::invalid_argument{"'" + levelAsLowerCase + "' is not a valid log severity level."};
      }
      return logLevel;
   }

   std::string LoggerInitializer::toString(const LogLevel & level)
   {
      spdlog::string_view_t levelAsStringView = spdlog::level::to_string_view(level);
      return {levelAsStringView.data(), levelAsStringView.size()};
   }

   // Private methods:

   std::shared_ptr<spdlog::sinks::sink> LoggerInitializer::createConsoleSink(const LogLevel & level)
   {
#ifdef _WIN32
      auto sink = std::make_shared<spdlog::sinks::wincolor_stdout_sink_mt>();
#else
      auto sink = std::make_shared<spdlog::sinks::ansicolor_stdout_sink_mt>();
#endif
      sink->set_level(level);
      return sink;
   }

   std::shared_ptr<spdlog::sinks::sink> LoggerInitializer::createRotatingFileSink(
         const LogLevel & level,
         const std::string & path)
   {
         // This statement creates the rotating file sink, it also creates an initial empty log file at specified path.
      auto sink = std::make_shared<spdlog::sinks::rotating_file_sink_mt>(
            path, DEFAULT_ROTATING_FILE_MAX_SIZE_BYTES, DEFAULT_ROTATING_FILE_MAX_QUANTITY);
      sink->set_level(level);
      return sink;
   }

} // FedPro