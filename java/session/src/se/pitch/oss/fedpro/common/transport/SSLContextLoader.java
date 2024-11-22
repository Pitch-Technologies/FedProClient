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
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Scanner;

public class SSLContextLoader {
   private static TrustManager[] TRUST_MANAGERS = null;

   public static TrustManager[] getPermissiveTrustManagers()
   throws NoSuchAlgorithmException, KeyManagementException
   {
      if (TRUST_MANAGERS == null) {
         TRUST_MANAGERS = new TrustManager[] {
               new X509TrustManager() {
                  @Override
                  public void checkClientTrusted(X509Certificate[] chain, String authType)
                  throws CertificateException
                  {
                     // Intentionally left empty
                  }

                  @Override
                  public void checkServerTrusted(X509Certificate[] chain, String authType)
                  throws CertificateException
                  {
                     // Intentionally left empty
                  }

                  @Override
                  public X509Certificate[] getAcceptedIssuers()
                  {
                     return null;
                  }
               }
         };
      }
      return TRUST_MANAGERS;
   }

   public static SSLContext loadSslContextClient(
         String passwordPath,
         String keyStorePath,
         String keyStoreType,
         String keyStoreAlgorithm)
   throws
         UnrecoverableKeyException,
         CertificateException,
         IOException,
         KeyStoreException,
         NoSuchAlgorithmException,
         KeyManagementException
   {
      return loadSslContext(passwordPath, keyStorePath, keyStoreType, keyStoreAlgorithm, true, false);
   }

   public static SSLContext loadSslContextServer(
         String passwordPath,
         String keyStorePath,
         String keyStoreType,
         String keyStoreAlgorithm,
         boolean trustAllClients)
   throws
         UnrecoverableKeyException,
         CertificateException,
         IOException,
         KeyStoreException,
         NoSuchAlgorithmException,
         KeyManagementException
   {
      return loadSslContext(passwordPath, keyStorePath, keyStoreType, keyStoreAlgorithm, false, trustAllClients);
   }

   private static SSLContext loadSslContext(
         String passwordPath,
         String keyStorePath,
         String keyStoreType,
         String keyStoreAlgorithm,
         boolean client,
         boolean trustAllClients)
   throws
         IOException,
         KeyStoreException,
         CertificateException,
         NoSuchAlgorithmException,
         KeyManagementException,
         UnrecoverableKeyException
   {
      FileInputStream keyStoreFile = new FileInputStream(keyStorePath);

      // Load keystore with server certificate and private key
      KeyStore keyStore = KeyStore.getInstance(keyStoreType);

      SSLContext sslContext = SSLContext.getInstance("TLS");

      // Create a KeyManagerFactory with the keyStore
      KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(keyStoreAlgorithm);

      // Load, use, and unload password
      char[] keystorePassword = readPassword(passwordPath);
      keyStore.load(keyStoreFile, keystorePassword);
      keyManagerFactory.init(keyStore, keystorePassword);
      Arrays.fill(keystorePassword, '0');

      TrustManager[] trustManagers;

      if (!client && trustAllClients) {
         trustManagers = getPermissiveTrustManagers();
      } else {
         // Create a TrustManagerFactory with the truststore
         TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(keyStoreAlgorithm);
         trustManagerFactory.init(keyStore);

         trustManagers = trustManagerFactory.getTrustManagers();
      }
         // Create SSLContext with the TrustManagerFactory
      sslContext.init(keyManagerFactory.getKeyManagers(), trustManagers, null);

      return sslContext;
   }

   private static char[] readPassword(String passwordPath)
   throws FileNotFoundException
   {
      FileReader file = new FileReader(passwordPath);
      Scanner scanner = new Scanner(file);

      return scanner.nextLine().toCharArray();
   }

}
