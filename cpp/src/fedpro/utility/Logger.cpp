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

#include <fedpro/Logger.h>

#include "LoggerInitializer.h"

#include <spdlog/spdlog.h>

#include <memory>

using namespace spdlog;

namespace FedPro
{
   class WrapperSink : public sinks::sink
   {
   public:

      explicit WrapperSink(Logger::LogSinkFunction sinkFunction)
            : _sinkFunction{std::move(sinkFunction)}
      {
      }

      void log(const details::log_msg &msg) override
      {
         _sinkFunction(msg.level, msg.payload.data(), msg.payload.size());
      }

      void flush() override
      {
      }

      void set_pattern(const std::string &pattern) override
      {
      }

       void set_formatter(std::unique_ptr<spdlog::formatter> sink_formatter) override
       {
       }

   private:

      Logger::LogSinkFunction _sinkFunction;
   };

   const int Logger::LEVEL_ERROR = spdlog::level::err;
   const int Logger::LEVEL_CRITICAL = spdlog::level::critical;

   void Logger::setSink(LogSinkFunction logSinkFunction)
   {
      auto logSink = std::make_shared<WrapperSink>(std::move(logSinkFunction));
      LoggerInitializer::setSink(logSink);
   }

} // FedPro
