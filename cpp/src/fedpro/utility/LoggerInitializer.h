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

#include <fedpro/Aliases.h>
#include <fedpro/Properties.h>
#include <fedpro/Settings.h>

#include "string_view.h"

#include <spdlog/spdlog.h>

namespace FedPro
{

   using LogLevel = spdlog::level::level_enum;

   static constexpr const char * FED_PRO_LOGGER_NAME{"FedProLogger"};
   static constexpr const std::size_t DEFAULT_ROTATING_FILE_MAX_SIZE_BYTES = 10 * 1024 * 1024; // 10 MB
   static constexpr const std::size_t DEFAULT_ROTATING_FILE_MAX_QUANTITY = 5;

   class LoggerInitializer
   {
   public:

      static void initialize(const Properties & settings);

      static LogLevel getLowest(std::initializer_list<LogLevel> levels);

      static LogLevel toLogSeverityLevel(int levelAsInt);

      static LogLevel toLogSeverityLevel(string_view levelAsView);

      static std::string toString(const LogLevel & level);

   private:

      static constexpr const char * DEFAULT_LOG_PATTERN{"[%Y-%m-%d %H:%M:%S.%e] [%-8l] %v"};
      static const LogLevel DEFAULT_CONSOLE_LOG_LEVEL{spdlog::level::warn};
      static const LogLevel DEFAULT_ROTATING_FILE_LOG_LEVEL{spdlog::level::off};
      static constexpr const char * DEFAULT_ROTATING_FILE_LOG_PATH{"FedProClientCppLogs/FedProClientCpp.log"};

      static std::shared_ptr<spdlog::sinks::sink> createConsoleSink(const LogLevel & level);

      static std::shared_ptr<spdlog::sinks::sink> createRotatingFileSink(
            const LogLevel & level,
            const std::string & path);
   };

} // FedPro
