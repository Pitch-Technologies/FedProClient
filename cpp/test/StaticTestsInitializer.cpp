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

#include "StaticTestsInitializer.h"

#include <services-common/SettingsParser.h>

namespace FedPro
{
   StaticTestsInitializer::StaticTestsInitializer()
   {
      // The parse method below reads configuration values from environment variables.
      // These variables might be modified in some test cases.
      // Therefore, this code is only executed once, and before any test has executed,
      // so that the logging configuration is not overridden by unwanted default values.
      const FedPro::Properties settings = FedPro::SettingsParser::parse();
      FedPro::initializeLogger(settings);
   }
}
