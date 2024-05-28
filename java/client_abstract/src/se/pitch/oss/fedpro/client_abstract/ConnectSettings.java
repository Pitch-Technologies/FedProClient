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

package se.pitch.oss.fedpro.client_abstract;

import se.pitch.oss.fedpro.client_abstract.exceptions.FedProConnectionFailed;

import java.util.HashMap;
import java.util.Map;

public class ConnectSettings {
   private final String _server;
   private int _port;
   private final Map<String, String> _additionalSettings;

   private ConnectSettings(String server,
                           int port,
                           Map<String, String> additionalSettings)
   {
      _server = server;
      _port = port;
      _additionalSettings = additionalSettings;
   }

   public void setDefaultPort(int defaultPort)
   {
      if (_port == -1) {
         _port = defaultPort;
      }
   }

   public String getServer()
   {
      return _server;
   }

   public int getPort()
   {
      return _port;
   }

   public Map<String, String> getAdditionalSettings()
   {
      return _additionalSettings;
   }

   public String getSetting(String settingName)
   {
      return _additionalSettings.get(settingName.toLowerCase());
   }

   public boolean hasSetting(String settingName)
   {
      return _additionalSettings.containsKey(settingName.toLowerCase());
   }

   public static ConnectSettings parse() throws FedProConnectionFailed
   {
      return parse("");
   }

   public static ConnectSettings parse(String federateProtocolServerAddress) throws FedProConnectionFailed
   {
      return parse(federateProtocolServerAddress, "");
   }

   public static ConnectSettings parse(String federateProtocolServerAddress, String federateProtocolAdditionalSettings) throws FedProConnectionFailed
   {
      String server = federateProtocolServerAddress;
      int port = -1;
      Map<String, String> additionalSettings = new HashMap<>();

      if (server.isEmpty()) {
         server = "localhost";
      }

      if (server.contains(":")) {
         int lastColon = server.lastIndexOf(":");
         try {
            port = Integer.parseInt(server.substring(lastColon + 1));
         } catch (NumberFormatException e) {
            throw new FedProConnectionFailed("Invalid port number '" + server.substring(lastColon + 1) + "' provided.");
         }
         server = server.substring(0, lastColon);
      }

      if (!federateProtocolAdditionalSettings.isEmpty()) {
         for (String setting : federateProtocolAdditionalSettings.split(",")) {
            String key;
            String value = "";
            if (setting.contains("=")) {
               String[] splitValue = setting.split("=", 2);
               key = splitValue[0];
               value = splitValue[1];
            } else {
               key = setting;
            }
            additionalSettings.put(key.toLowerCase(), value);
         }
      }

      return new ConnectSettings(server, port, additionalSettings);
   }
}
