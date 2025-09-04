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

package se.pitch.oss.fedpro.utility;

import java.util.Set;

import static org.junit.Assert.fail;
import static se.pitch.oss.fedpro.client.Settings.SETTING_OVERRIDES_PREFIX;
import static se.pitch.oss.fedpro.client.Settings.SETTING_PREFIX;

public class SettingsHelper {

   public static String getSystemPropertySetting(String name, String defaultValue)
   {
      return System.getProperty(SETTING_OVERRIDES_PREFIX + name, System.getProperty(SETTING_PREFIX + name, defaultValue));
   }

   public static void setFedProOverride(String setting, String value)
   {
      System.setProperty(SETTING_OVERRIDES_PREFIX + setting, value);
   }

   public static void clearFedProSysProps()
   {
      clearSysPropsWithPrefix(SETTING_PREFIX);
   }

   public static void clearFedProOverrides()
   {
      clearSysPropsWithPrefix(SETTING_OVERRIDES_PREFIX);
   }

   private static void clearSysPropsWithPrefix(String prefix)
   {
      Set<String> sysPropNames = System.getProperties().stringPropertyNames();
      for (final String propName : sysPropNames) {
         if (propName.startsWith(prefix)) {
            System.clearProperty(propName);
         }
      }
   }

   public static void assertNoOverrides() {
      Set<String> sysPropNames = System.getProperties().stringPropertyNames();
      for (final String propName : sysPropNames) {
         if (propName.startsWith(SETTING_OVERRIDES_PREFIX)) {
            fail("No FedProOverrides allowed outside test code! Found: " + propName);
         }
      }
   }
}
