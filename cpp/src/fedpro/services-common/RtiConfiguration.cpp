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

#include "RtiConfiguration.h"
#if (RTI_HLA_VERSION >= 2024)
namespace RTI_NAMESPACE
{
   RtiConfiguration RtiConfiguration::createConfiguration()
   {
      return {};
   }

   RtiConfiguration & RtiConfiguration::withConfigurationName(std::wstring const & configurationName)
   {
      _configurationName= configurationName;
      return *this;
   }

   RtiConfiguration & RtiConfiguration::withRtiAddress(std::wstring const & rtiAddress)
   {
      _rtiAddress = rtiAddress;
      return *this;
   }

   RtiConfiguration & RtiConfiguration::withAdditionalSettings(std::wstring const & additionalSettings)
   {
      _additionalSettings = additionalSettings;
      return *this;
   }

   const std::wstring & RtiConfiguration::configurationName() const
   {
      return _configurationName;
   }

   const std::wstring & RtiConfiguration::rtiAddress() const
   {
      return _rtiAddress;
   }

   const std::wstring & RtiConfiguration::additionalSettings() const
   {
      return _additionalSettings;
   }

}
#endif // RTI_HLA_VERSION >= 2024