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

#include <fedpro/Properties.h>

#include "utility/StringUtil.h"

namespace FedPro
{
   // TODO use std::variant when moving to C++17 to merge _booleanProperties, _durationProperties, _intProperties, etc
   // into a single map.

   Properties::Properties() = default;

   bool Properties::getBoolean(
         const std::string & propertyName,
         bool defaultValue) const
   {
      auto it = _booleanProperties.find(propertyName);
      if (it == _booleanProperties.end()) {
         return defaultValue;
      }
      return it->second;
   }

   FedProDuration Properties::getDuration(
         const std::string & settingName,
         FedProDuration defaultValue) const
   {
      auto it = _durationProperties.find(settingName);
      if (it == _durationProperties.end()) {
         return defaultValue;
      }
      return it->second;
   }

   int Properties::getInt(
         const std::string & settingName,
         int defaultValue) const
   {
      auto it = _intProperties.find(settingName);
      if (it == _intProperties.end()) {
         return defaultValue;
      }
      return it->second;
   }

   std::string Properties::getString(
         const std::string & propertyName,
         const std::string & defaultValue) const
   {
      auto it = _stringProperties.find(propertyName);
      if (it == _stringProperties.end()) {
         return defaultValue;
      }
      return it->second;
   }

   uint16_t Properties::getUnsignedInt16(
         const std::string & settingName,
         uint16_t defaultValue) const
   {
      auto it = _unsignedInt16Properties.find(settingName);
      if (it == _unsignedInt16Properties.end()) {
         return defaultValue;
      }
      return it->second;
   }

   uint32_t Properties::getUnsignedInt32(
         const std::string & propertyName,
         uint32_t defaultValue) const
   {
      auto it = _unsignedInt32Properties.find(propertyName);
      if (it == _unsignedInt32Properties.end()) {
         return defaultValue;
      }
      return it->second;
   }


   void Properties::setBoolean(
         const std::string & propertyName,
         bool value)
   {
      _booleanProperties[toString(propertyName)] = value;
   }

   void Properties::setDuration(
         const std::string & propertyName,
         FedProDuration value)
   {
      _durationProperties[toString(propertyName)] = value;
   }

   void Properties::setInt(
         const std::string & propertyName,
         int value)
   {
      _intProperties[toString(propertyName)] = value;
   }

   void Properties::setString(
         const std::string & propertyName,
         const std::string & value)
   {
      _stringProperties[toString(propertyName)] = toString(value);
   }

   void Properties::setUnsignedInt16(
         const std::string & propertyName,
         uint16_t value)
   {
      _unsignedInt16Properties[toString(propertyName)] = value;
   }

   void Properties::setUnsignedInt32(
         const std::string & propertyName,
         uint32_t value)
   {
      _unsignedInt32Properties[toString(propertyName)] = value;
   }

   bool Properties::empty() const
   {
      return _booleanProperties.empty() && _durationProperties.empty() && _intProperties.empty() &&
             _stringProperties.empty() && _unsignedInt16Properties.empty() && _unsignedInt32Properties.empty();
   }

   std::string Properties::toPrettyString() const
   {
      std::string string{};
      for (const auto & setting : _stringProperties) {
         string += "\t" + setting.first + " = " + setting.second + "\n";
      }
      for (const auto & setting : _booleanProperties) {
         std::string settingValue = setting.second ? "true" : "false";
         string += "\t" + setting.first + " = " + settingValue + "\n";
      }
      for (const auto & setting : _unsignedInt32Properties) {
         string += "\t" + setting.first + " = " + std::to_string(setting.second) + "\n";
      }
      for (const auto & setting : _unsignedInt16Properties) {
         string += "\t" + setting.first + " = " + std::to_string(setting.second) + "\n";
      }
      for (const auto & setting : _intProperties) {
         string += "\t" + setting.first + " = " + std::to_string(setting.second) + "\n";
      }
      for (const auto & setting : _durationProperties) {
         auto durationSeconds = std::chrono::duration_cast<std::chrono::seconds>(setting.second);
         string += "\t" + setting.first + " = " + std::to_string(durationSeconds.count()) + " seconds" + "\n";
      }
      return string;
   }

} // FedPro
