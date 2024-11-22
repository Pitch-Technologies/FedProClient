/*
 *  Copyright (C) 2022 Pitch Technologies AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.pitch.oss.fedpro.client;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class TypedProperties {

   private final Map<String, Boolean> _booleanProperties;
   private final Map<String, Duration> _durationProperties;
   private final Map<String, Integer> _intProperties;
   private final Map<String, String> _stringProperties;

   public TypedProperties()
   {
      this._booleanProperties = new HashMap<>();
      this._durationProperties = new HashMap<>();
      this._intProperties = new HashMap<>();
      this._stringProperties = new HashMap<>();
   }

   public boolean getBoolean(String propertyName, boolean defaultValue)
   {
      return _booleanProperties.getOrDefault(propertyName, defaultValue);
   }

   public Duration getDuration(String settingName, Duration defaultValue)
   {
      return _durationProperties.getOrDefault(settingName, defaultValue);
   }

   public int getInt(String settingName, int defaultValue)
   {
      return _intProperties.getOrDefault(settingName, defaultValue);
   }

   public String getString(String propertyName, String defaultValue)
   {
      return _stringProperties.getOrDefault(propertyName, defaultValue);
   }

   public void setBoolean(String propertyName, boolean value)
   {
      _booleanProperties.put(propertyName, value);
   }

   public void setDuration(String propertyName, Duration value)
   {
      _durationProperties.put(propertyName, value);
   }

   public void setInt(String propertyName, int value)
   {
      _intProperties.put(propertyName, value);
   }

   public void setString(String propertyName, String value)
   {
      _stringProperties.put(propertyName, value);
   }

   public String toPrettyString()
   {
      StringBuilder sb = new StringBuilder();
      for (Map.Entry<String, String> entry : _stringProperties.entrySet()) {
         sb.append("\t").append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
      }
      for (Map.Entry<String, Boolean> entry : _booleanProperties.entrySet()) {
         sb.append("\t").append(entry.getKey()).append(" = ").append(entry.getValue() ? "true" : "false").append("\n");
      }
      for (Map.Entry<String, Integer> entry : _intProperties.entrySet()) {
         sb.append("\t").append(entry.getKey()).append(" = ").append(entry.getValue()).append("\n");
      }
      for (Map.Entry<String, Duration> entry : _durationProperties.entrySet()) {
         sb.append("\t")
               .append(entry.getKey())
               .append(" = ")
               .append(entry.getValue().getSeconds())
               .append(" seconds")
               .append("\n");
      }
      return sb.toString();
   }

}
