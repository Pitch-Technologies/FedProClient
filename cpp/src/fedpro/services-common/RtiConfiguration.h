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
   File: RTI/RtiConfiguration.h
***********************************************************************/

#include <string>

#if (RTI_HLA_VERSION >= 2025)
#include <RTI/RtiConfiguration.h>
#else // RTI_HLA_VERSION < 2025
// The following RtiConfiguration declaration is the same as the one added to HLA4's RTI API.
// In order to build RTI libraries targetting different HLA versions and
// maximumize code reuse, use the same RtiConfiguration for earlier versions as well.
// Note the following API is for internal use. The shared library does not export it.

namespace RTI_NAMESPACE
{
   class RtiConfiguration
   {
   public:
      static RtiConfiguration createConfiguration();

      RtiConfiguration& withConfigurationName(std::wstring const & configurationName);

      RtiConfiguration& withRtiAddress(std::wstring const & rtiAddress);

      RtiConfiguration& withAdditionalSettings(std::wstring const & additionalSettings);

   public:
      const std::wstring& configurationName() const;

      const std::wstring& rtiAddress() const;

      const std::wstring& additionalSettings() const;

   private:
      std::wstring _configurationName;
      std::wstring _rtiAddress;
      std::wstring _additionalSettings;
   };

}

#endif // RTI_HLA_VERSION < 2025
