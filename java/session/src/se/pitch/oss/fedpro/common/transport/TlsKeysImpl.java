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

import se.pitch.oss.fedpro.common.TlsKeys;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class TlsKeysImpl implements TlsKeys {

   private final KeyStore _keyStore;
   private final char[] _password;

   public TlsKeysImpl(
         KeyStore keyStore,
         char[] password)
   {
      _keyStore = keyStore;
      _password = password;
   }

   public TlsKeysImpl()
   {
      this(createInMemoryKeyStore(), null);
   }

   public KeyStore getKeyStore()
   {
      return _keyStore;
   }

   public char[] getPassword()
   {
      return _password;
   }

   private static KeyStore createInMemoryKeyStore()
   {
      try {
         KeyStore keyStore = KeyStore.getInstance("PKCS12");
         keyStore.load(null, null);
         return keyStore;
      } catch (KeyStoreException e) {
         throw new IllegalStateException("Every implementation of the Java platform is required to support PKCS12");
      } catch (IOException e) {
         throw new IllegalStateException("Can not happen for in memory key store");
      } catch (NoSuchAlgorithmException e) {
         throw new IllegalStateException("Can not happen for non-locked key store");
      } catch (CertificateException e) {
         throw new IllegalStateException("Can not happen for empty key store");
      }
   }
}
