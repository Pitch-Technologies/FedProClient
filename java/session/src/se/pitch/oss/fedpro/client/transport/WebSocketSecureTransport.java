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

package se.pitch.oss.fedpro.client.transport;

import se.pitch.oss.fedpro.client.TypedProperties;
import se.pitch.oss.fedpro.common.Protocol;

import javax.net.ssl.SSLContext;
import java.net.URI;
import java.net.UnknownHostException;

import static se.pitch.oss.fedpro.client.Settings.*;
import static se.pitch.oss.fedpro.client.transport.TransportSettings.*;
import static se.pitch.oss.fedpro.common.Ports.DEFAULT_PORT_WSS;

public class WebSocketSecureTransport extends WebSocketTransport {

   private final SSLContext _context;

   // Transport layer settings
   private final String _keyStoreAlgorithm;
   private final String _passwordPath;
   private final String _keystorePath;
   private final String _keyStoreType;
   private final String _tlsModeString;

   public WebSocketSecureTransport(SSLContext sslContext, TypedProperties settings)
   {
      super(
            settings == null ?
                  DEFAULT_CONNECTION_HOST :
                  settings.getString(SETTING_NAME_CONNECTION_HOST, DEFAULT_CONNECTION_HOST),
            settings == null ?
                  DEFAULT_PORT_WSS :
                  settings.getInt(SETTING_NAME_CONNECTION_PORT, DEFAULT_PORT_WSS),
            Protocol.WSS);

      if (settings == null) {
         settings = new TypedProperties();
      }

      _keyStoreAlgorithm = settings.getString(SETTING_NAME_KEYSTORE_ALGORITHM, DEFAULT_KEYSTORE_ALGORITHM);
      _passwordPath = settings.getString(SETTING_NAME_KEYSTORE_PASSWORD_PATH, DEFAULT_KEYSTORE_PASSWORD_PATH);
      _keystorePath = settings.getString(SETTING_NAME_KEYSTORE_PATH, DEFAULT_KEYSTORE_PATH);
      _keyStoreType = settings.getString(SETTING_NAME_KEYSTORE_TYPE, DEFAULT_KEYSTORE_TYPE);
      _tlsModeString = settings.getString(SETTING_NAME_TLS_MODE, DEFAULT_TLS_MODE.name());

      if (sslContext != null) {
         _context = sslContext;
      } else {
         boolean useDefaultKeystore = (_keystorePath == null);
         _context = SecurityUtil.getSslContext(
               SecurityUtil.toTlsMode(_tlsModeString), _passwordPath, _keystorePath, _keyStoreType,
               _keyStoreAlgorithm, useDefaultKeystore);
      }
      logSettings();
   }

   @Override
   WebSocketClientImpl createWebSocketClient(URI uri)
   {
      WebSocketClientImpl impl = super.createWebSocketClient(uri);
      impl.setSocketFactory(_context.getSocketFactory());
      return impl;
   }

   @Override
   protected String addHostPrefix(String host)
   throws UnknownHostException
   {
      String[] splitHost = host.split("://", 2);

      boolean hasProtocol = splitHost.length == 2;
      if (hasProtocol) {
         String protocol = splitHost[0];
         if (!protocol.equals("wss")) {
            throw new UnknownHostException(String.format(
                  "Invalid protocol '%s' used together with WebSocketSecure, expected 'wss'.",
                  protocol));
         }
      } else {
         host = "wss://" + host;
      }

      return host;
   }

   private void logSettings()
   {
      TypedProperties allTransportSettingsUsed = new TypedProperties();
      allTransportSettingsUsed.setString(SETTING_NAME_KEYSTORE_ALGORITHM, _keyStoreAlgorithm);
      allTransportSettingsUsed.setString(SETTING_NAME_KEYSTORE_PASSWORD_PATH, _passwordPath);
      allTransportSettingsUsed.setString(SETTING_NAME_KEYSTORE_PATH, _keystorePath);
      allTransportSettingsUsed.setString(SETTING_NAME_KEYSTORE_TYPE, _keyStoreType);
      allTransportSettingsUsed.getString(SETTING_NAME_TLS_MODE, _tlsModeString);
      LOGGER.config(() -> String.format(
            "Federate Protocol client transport layer settings used:\n%s",
            allTransportSettingsUsed.toPrettyString()));
   }
}
