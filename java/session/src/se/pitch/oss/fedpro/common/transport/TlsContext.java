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

package se.pitch.oss.fedpro.common.transport;

import javax.net.ssl.*;
import java.net.Socket;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.*;
import java.util.Enumeration;

public class TlsContext {

   private static boolean hasPrivateKey(KeyStore keyStore)
   {
      try {
         for (Enumeration<String> aliases = keyStore.aliases(); aliases.hasMoreElements(); ) {
            String alias = aliases.nextElement();
            if (keyStore.isKeyEntry(alias)) {
               return true;
            }
         }
      } catch (KeyStoreException ignore) {
      }
      return false;
   }

   private static boolean hasTrustedCertificates(KeyStore keyStore)
   {
      try {
         for (Enumeration<String> aliases = keyStore.aliases(); aliases.hasMoreElements(); ) {
            String alias = aliases.nextElement();
            if (keyStore.isCertificateEntry(alias)) {
               return true;
            } else if (keyStore.isKeyEntry(alias)) {
               Certificate[] chain = keyStore.getCertificateChain(alias);
               if (chain != null && chain.length > 0) {
                  return true;
               }
            }
         }
      } catch (KeyStoreException ignore) {
      }
      return false;
   }

   public static SSLContext provideSSLContext(
         TlsKeysImpl tlsKeys,
         boolean trustAllServers)
   {
      KeyStore keyStore = tlsKeys.getKeyStore();
      char[] password = tlsKeys.getPassword();

      KeyManager[] keyManagers = hasPrivateKey(keyStore) ? X509ExtendedKeyManagerImpl.create(keyStore, password) : null;
      TrustManager[] trustManagers = hasTrustedCertificates(keyStore) ? X509ExtendedTrustManagerImpl.create(
            keyStore,
            trustAllServers,
            false) : null;

      SSLContext context;
      try {
         context = SSLContext.getInstance("TLSv1.3");
         context.init(keyManagers, trustManagers, null);
      } catch (NoSuchAlgorithmException e) {
         throw new IllegalStateException("TLSv1.3 must be supported");
      } catch (KeyManagementException e) {
         throw new IllegalStateException("Invalid key management");
      }

      return context;
   }

   private static class X509ExtendedKeyManagerImpl extends X509ExtendedKeyManager {
      private final X509ExtendedKeyManager _keyManager;

      public X509ExtendedKeyManagerImpl(X509ExtendedKeyManager keyManager)
      {
         _keyManager = keyManager;
      }

      public static KeyManager[] create(
            KeyStore keyStore,
            char[] password)
      {
         KeyManagerFactory keyManagerFactory;
         try {
            keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(keyStore, password);
         } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Default algorithm must be supported");
         } catch (KeyStoreException | UnrecoverableKeyException e) {
            throw new IllegalStateException("Invalid key store");
         }

         KeyManager[] keyManagers = keyManagerFactory.getKeyManagers().clone();

         for (int i = 0; i < keyManagers.length; i++) {
            KeyManager keyManager = keyManagers[i];
            if (keyManager instanceof X509ExtendedKeyManager) {
               keyManagers[i] = new X509ExtendedKeyManagerImpl((X509ExtendedKeyManager) keyManager);
            }
         }

         return keyManagers;
      }

      @Override
      public String chooseEngineClientAlias(
            String[] keyType,
            Principal[] issuers,
            SSLEngine engine)
      {
         return _keyManager.chooseEngineClientAlias(keyType, issuers, engine);
      }

      @Override
      public String chooseClientAlias(
            String[] keyType,
            Principal[] issuers,
            Socket socket)
      {
         return _keyManager.chooseClientAlias(keyType, issuers, socket);
      }

      @Override
      public String[] getClientAliases(
            String keyType,
            Principal[] issuers)
      {
         return _keyManager.getClientAliases(keyType, issuers);
      }

      private String getAliasBySniHostName(ExtendedSSLSession session)
      {
         String hostname = null;
         for (SNIServerName name : session.getRequestedServerNames()) {
            if (name.getType() == StandardConstants.SNI_HOST_NAME) {
               hostname = ((SNIHostName) name).getAsciiName();
               break;
            }
         }

         // If we got given a hostname over SNI, check if we have a cert and key for that hostname
         if (hostname != null && getCertificateChain(hostname) != null && getPrivateKey(hostname) != null) {
            return hostname;
         }
         return null;
      }

      @Override
      public String chooseEngineServerAlias(
            String keyType,
            Principal[] issuers,
            SSLEngine engine)
      {
         if (engine != null) {
            SSLSession session = engine.getHandshakeSession();
            if (session instanceof ExtendedSSLSession) {
               String alias = getAliasBySniHostName((ExtendedSSLSession) session);
               if (alias != null) {
                  return alias;
               }
            }
         }

         return _keyManager.chooseEngineServerAlias(keyType, issuers, engine);
      }

      @Override
      public String chooseServerAlias(
            String keyType,
            Principal[] issuers,
            Socket socket)
      {
         if (socket instanceof SSLSocket) {
            SSLSession session = ((SSLSocket) socket).getHandshakeSession();
            if (session instanceof ExtendedSSLSession) {
               String alias = getAliasBySniHostName((ExtendedSSLSession) session);
               if (alias != null) {
                  return alias;
               }
            }
         }

         return _keyManager.chooseServerAlias(keyType, issuers, socket);
      }

      @Override
      public String[] getServerAliases(
            String keyType,
            Principal[] issuers)
      {
         return _keyManager.getServerAliases(keyType, issuers);
      }

      @Override
      public X509Certificate[] getCertificateChain(String alias)
      {
         return _keyManager.getCertificateChain(alias);
      }

      @Override
      public PrivateKey getPrivateKey(String alias)
      {
         return _keyManager.getPrivateKey(alias);
      }
   }

   private static class X509ExtendedTrustManagerImpl extends X509ExtendedTrustManager {
      private final X509ExtendedTrustManager _trustManager;
      private final boolean _trustAllServers;
      private final boolean _checkValidity;

      private X509ExtendedTrustManagerImpl(
            X509ExtendedTrustManager trustManager,
            boolean trustAllServers,
            boolean checkValidity)
      {
         _trustManager = trustManager;
         _trustAllServers = trustAllServers;
         _checkValidity = checkValidity;
      }

      public static TrustManager[] create(
            KeyStore keyStore,
            boolean trustAllServers,
            boolean checkValidity)
      {
         TrustManagerFactory trustManagerFactory;
         try {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);
         } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Default algorithm must be supported");
         } catch (KeyStoreException e) {
            throw new IllegalStateException("Invalid key store");
         }

         TrustManager[] trustManagers = trustManagerFactory.getTrustManagers().clone();

         for (int i = 0; i < trustManagers.length; i++) {
            TrustManager trustManager = trustManagers[i];
            if (trustManager instanceof X509ExtendedTrustManager) {
               trustManagers[i] = new X509ExtendedTrustManagerImpl(
                     (X509ExtendedTrustManager) trustManager,
                     trustAllServers,
                     checkValidity);
            }
         }

         return trustManagers;
      }

      @Override
      public void checkClientTrusted(
            X509Certificate[] chain,
            String authType)
      throws CertificateException
      {
         _trustManager.checkClientTrusted(chain, authType);

         checkValidity(chain);
      }

      @Override
      public void checkClientTrusted(
            X509Certificate[] chain,
            String authType,
            Socket socket)
      throws CertificateException
      {
         _trustManager.checkClientTrusted(chain, authType, socket);

         checkValidity(chain);
      }

      @Override
      public void checkClientTrusted(
            X509Certificate[] chain,
            String authType,
            SSLEngine engine)
      throws CertificateException
      {
         _trustManager.checkClientTrusted(chain, authType, engine);

         checkValidity(chain);
      }

      @Override
      public void checkServerTrusted(
            X509Certificate[] chain,
            String authType)
      throws CertificateException
      {
         if (_trustAllServers) {
            return;
         }

         if (getAcceptedIssuers().length == 0) {
            throw new CertificateException("TrustManager has no trusted certificates");
         }

         _trustManager.checkServerTrusted(chain, authType);

         checkValidity(chain);
      }

      @Override
      public void checkServerTrusted(
            X509Certificate[] chain,
            String authType,
            Socket socket)
      throws CertificateException
      {
         if (_trustAllServers) {
            return;
         }

         if (getAcceptedIssuers().length == 0) {
            throw new CertificateException("TrustManager has no trusted certificates");
         }

         _trustManager.checkServerTrusted(chain, authType, socket);

         checkValidity(chain);
      }

      @Override
      public void checkServerTrusted(
            X509Certificate[] chain,
            String authType,
            SSLEngine engine)
      throws CertificateException
      {
         if (_trustAllServers) {
            return;
         }

         if (getAcceptedIssuers().length == 0) {
            throw new CertificateException("TrustManager has no trusted certificates");
         }

         _trustManager.checkServerTrusted(chain, authType, engine);

         checkValidity(chain);
      }

      @Override
      public X509Certificate[] getAcceptedIssuers()
      {
         return _trustManager.getAcceptedIssuers();
      }

      private void checkValidity(X509Certificate[] chain)
      throws CertificateExpiredException, CertificateNotYetValidException
      {
         if (_checkValidity) {
            for (X509Certificate x509Certificate : chain) {
               x509Certificate.checkValidity();
            }
         }
      }
   }
}
