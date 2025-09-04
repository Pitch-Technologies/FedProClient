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
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/
/***********************************************************************
   IEEE 1516.1 High Level Architecture Interface Specification C++ API
   File: RTI/Typedefs.h
***********************************************************************/

#include <RTI/Typedefs.h>

#if (RTI_HLA_VERSION < 2025)
// The following declarations are the same as the one added to HLA4's RTI API.
// In order to build RTI libraries targeting earlier HLA versions and
// maximize code reuse, use the same declarations for earlier versions as well.
// Note the following API is for internal use. The shared library does not export it.

namespace RTI_NAMESPACE
{
   enum AdditionalSettingsResultCode
   {
      SETTINGS_IGNORED,
      SETTINGS_FAILED_TO_PARSE,
      SETTINGS_APPLIED
   };

   class ConfigurationResult
   {
   public:
      ConfigurationResult ();
      ConfigurationResult (
            bool configurationUsed,
            bool addressUsed,
            AdditionalSettingsResultCode settingsResultCode,
            std::wstring const & message = L"");

      bool configurationUsed;
      bool addressUsed;
      AdditionalSettingsResultCode additionalSettingsResult;
      std::wstring message;
   };
}
#endif

