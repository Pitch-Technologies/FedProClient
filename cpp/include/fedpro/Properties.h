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

#include "Aliases.h"
#include "Config.h"

#include <cstdint>
#include <map>
#include <string>

namespace FedPro
{

   class Properties
   {
   public:

      FEDPRO_EXPORT Properties();

      FEDPRO_EXPORT bool getBoolean(
            const std::string & propertyName,
            bool defaultValue) const;

      FEDPRO_EXPORT FedProDuration getDuration(
            const std::string & settingName,
            FedProDuration defaultValue) const;

      FEDPRO_EXPORT int getInt(
            const std::string & settingName,
            int defaultValue) const;

      FEDPRO_EXPORT std::string getString(
            const std::string & propertyName,
            const std::string & defaultValue) const;

      FEDPRO_EXPORT uint16_t getUnsignedInt16(
            const std::string & settingName,
            uint16_t defaultValue) const;

      FEDPRO_EXPORT uint32_t getUnsignedInt32(
            const std::string & propertyName,
            uint32_t defaultValue) const;

      FEDPRO_EXPORT void setBoolean(
            const std::string & propertyName,
            bool value);

      FEDPRO_EXPORT void setDuration(
            const std::string & propertyName,
            FedProDuration value);

      FEDPRO_EXPORT void setInt(
            const std::string & propertyName,
            int value);

      FEDPRO_EXPORT void setString(
            const std::string & propertyName,
            const std::string & value);

      FEDPRO_EXPORT void setUnsignedInt16(
            const std::string & propertyName,
            uint16_t value);

      FEDPRO_EXPORT void setUnsignedInt32(
            const std::string & propertyName,
            uint32_t value);

      FEDPRO_EXPORT bool empty() const;

      FEDPRO_EXPORT std::string toPrettyString() const;

   private:

      std::map<std::string, bool> _booleanProperties;
      std::map<std::string, FedProDuration> _durationProperties;
      std::map<std::string, int> _intProperties;
      std::map<std::string, std::string> _stringProperties;
      std::map<std::string, uint16_t> _unsignedInt16Properties;
      std::map<std::string, uint32_t> _unsignedInt32Properties;
   };

} // FedPro
