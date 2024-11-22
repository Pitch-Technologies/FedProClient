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
import se.pitch.oss.fedpro.common.TlsMode;
import se.pitch.oss.fedpro.common.transport.SSLContextLoader;
import se.pitch.oss.fedpro.common.transport.TlsKeysImpl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Locale;
import java.util.logging.Logger;

import static se.pitch.oss.fedpro.client.Settings.*;
import static se.pitch.oss.fedpro.client.transport.TransportSettings.DEFAULT_TLS_MODE;
import static se.pitch.oss.fedpro.common.transport.TlsContext.provideSSLContext;

public class SecurityUtil {

   private static final Logger LOGGER = Logger.getLogger(SecurityUtil.class.getName());

   public static SSLContext provideClientSSLContext(
         TlsKeysImpl tlsKeys,
         TypedProperties settings)
   {
      TlsMode mode = DEFAULT_TLS_MODE;
      if (settings != null) {
         mode = toTlsMode(settings.getString(SETTING_NAME_TLS_MODE, DEFAULT_TLS_MODE.name()));
      }

      boolean trustAllServers = mode == TlsMode.ENCRYPTED;
      if (trustAllServers) {
         LOGGER.warning("Connection is encrypted, but certificates will not be verified");
      }

      return provideSSLContext(tlsKeys, trustAllServers);
   }

   public static TlsMode toTlsMode(String tlsModeString)
   throws IllegalArgumentException
   {
      try {
         return TlsMode.valueOf(tlsModeString.toUpperCase(Locale.ROOT));
      } catch (IllegalArgumentException e) {
         throw new IllegalArgumentException(String.format(
               "'%s' is not a valid mode for setting '%s': %s",
               tlsModeString,
               SETTING_NAME_TLS_MODE,
               e.getMessage()));
      }
   }

   public static SSLContext getSslContext(
         TlsMode tlsMode,
         String passwordPath,
         String keystorePath,
         String keyStoreType,
         String keyStoreAlgorithm,
         boolean useDefaultKeystore)
   {
      try {
         if (tlsMode == TlsMode.ENCRYPTED || tlsMode == TlsMode.SERVER_AUTH) {
            if (useDefaultKeystore) {
               SSLContext context = SSLContext.getInstance("TLS");
               TrustManager[] trustManagers = null;
               if (tlsMode == TlsMode.ENCRYPTED) {
                  trustManagers = SSLContextLoader.getPermissiveTrustManagers();
               }
               context.init(null, trustManagers, null);
               return context;
            }
            if (passwordPath == null) {
               throw new IllegalArgumentException(
                     "Missing setting for keystore password (" + SETTING_NAME_KEYSTORE_PASSWORD_PATH + ").");
            }
            if (keystorePath == null) {
               throw new IllegalArgumentException(
                     "Missing setting for keystore path (" + SETTING_NAME_KEYSTORE_PATH + ").");
            }
            return SSLContextLoader.loadSslContextClient(
                  passwordPath,
                  keystorePath,
                  keyStoreType,
                  keyStoreAlgorithm);
         } else {
            throw new IllegalArgumentException(tlsMode + " not yet implemented.");
         }
      } catch (IOException | KeyStoreException | CertificateException | NoSuchAlgorithmException |
               KeyManagementException | UnrecoverableKeyException e) {
         throw new IllegalArgumentException("Could not load SSL context", e);
      }
   }
}
