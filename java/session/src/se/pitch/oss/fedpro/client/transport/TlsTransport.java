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
import se.pitch.oss.fedpro.common.transport.*;

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.util.Collections;

import static se.pitch.oss.fedpro.client.Settings.*;
import static se.pitch.oss.fedpro.client.transport.TransportSettings.*;
import static se.pitch.oss.fedpro.common.Ports.DEFAULT_PORT_TLS;

public class TlsTransport extends TransportBase {

   private final SSLContext _context;

   // Transport layer settings
   private final String _keyStoreAlgorithm;
   private final String _passwordPath;
   private final String _keystorePath;
   private final String _keyStoreType;
   private final boolean _useDefaultKeystore;
   private final String _tlsModeString;
   private final String _sniHostName;

   public TlsTransport(SSLContext sslContext, TypedProperties settings)
   {
      super(
            settings == null ?
                  DEFAULT_CONNECTION_HOST :
                  settings.getString(SETTING_NAME_CONNECTION_HOST, DEFAULT_CONNECTION_HOST),
            settings == null ?
                  DEFAULT_PORT_TLS :
                  settings.getInt(SETTING_NAME_CONNECTION_PORT, DEFAULT_PORT_TLS));

      if (settings == null) {
         settings = new TypedProperties();
      }

      _keyStoreAlgorithm = settings.getString(SETTING_NAME_KEYSTORE_ALGORITHM, DEFAULT_KEYSTORE_ALGORITHM);
      _passwordPath = settings.getString(SETTING_NAME_KEYSTORE_PASSWORD_PATH, DEFAULT_KEYSTORE_PASSWORD_PATH);
      _keystorePath = settings.getString(SETTING_NAME_KEYSTORE_PATH, DEFAULT_KEYSTORE_PATH);
      _keyStoreType = settings.getString(SETTING_NAME_KEYSTORE_TYPE, DEFAULT_KEYSTORE_TYPE);
      _useDefaultKeystore = settings.getBoolean(SETTING_NAME_KEYSTORE_USE_DEFAULT, DEFAULT_KEYSTORE_USE_DEFAULT);
      _tlsModeString = settings.getString(SETTING_NAME_TLS_MODE, DEFAULT_TLS_MODE.name());
      _sniHostName = settings.getString(SETTING_NAME_TLS_SNI, DEFAULT_TLS_SNI);

      if (sslContext != null) {
         _context = sslContext;
      } else {
         _context = SecurityUtil.getSslContext(
               SecurityUtil.toTlsMode(_tlsModeString), _passwordPath, _keystorePath, _keyStoreType,
               _keyStoreAlgorithm, _useDefaultKeystore);
      }
      logSettings();
   }

   @Override
   protected FedProSocket doConnect(String host, int port)
   throws IOException
   {
      SSLSocket socket = (SSLSocket) _context.getSocketFactory().createSocket(host, port);

      /*
       * If we want to check the `host` name against the cert that we receive as
       * part of the handshake we need to send this `host` name into the SSLContext,
       * all the way to our X509ExtendedTrustManagerImpl, and then perform the
       * host name checking.
       *
       * Host name verification is mostly used when relying on public CAs (for example
       * via the OS), where someone else may have a CA signed cert and meddle in the middle.
       * If we use self-signed server certs imported directly in the client this is not needed.
       */

      if (_sniHostName != null && !_sniHostName.isEmpty()) {
         // Use Server Name Indication (SNI) extension to send a host name
         // to indicate we want the new cert.
         SSLParameters params = socket.getSSLParameters();
         params.setServerNames(Collections.singletonList(new SNIHostName(_sniHostName)));
         socket.setSSLParameters(params);
      }

      Tls.verifyTlsHandshakeOrClose(socket);

      return new FedProSocketImpl(socket);
   }

   private void logSettings()
   {
      TypedProperties allTransportSettingsUsed = new TypedProperties();
      allTransportSettingsUsed.setString(SETTING_NAME_KEYSTORE_ALGORITHM, _keyStoreAlgorithm);
      allTransportSettingsUsed.setString(SETTING_NAME_KEYSTORE_PASSWORD_PATH, _passwordPath);
      allTransportSettingsUsed.setString(SETTING_NAME_KEYSTORE_PATH, _keystorePath);
      allTransportSettingsUsed.setString(SETTING_NAME_KEYSTORE_TYPE, _keyStoreType);
      allTransportSettingsUsed.setBoolean(SETTING_NAME_KEYSTORE_USE_DEFAULT, _useDefaultKeystore);
      allTransportSettingsUsed.setString(SETTING_NAME_TLS_MODE, _tlsModeString);
      allTransportSettingsUsed.setString(SETTING_NAME_TLS_SNI, _sniHostName);
      LOGGER.config(() -> String.format(
            "Federate Protocol client transport layer settings used:\n%s",
            allTransportSettingsUsed.toPrettyString()));
   }

}
