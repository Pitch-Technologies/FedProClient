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

import se.pitch.oss.fedpro.client.transport.*;
import se.pitch.oss.fedpro.common.TlsKeys;
import se.pitch.oss.fedpro.common.transport.TlsKeysImpl;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

public class TransportFactory {

   /**
    * Create a new TCP specific transport instance with default settings.
    *
    * @return A new Transport instance.
    */
   public static Transport createTcpTransport()
   {
      return new TcpTransport(null);
   }

   /**
    * Create a new TCP specific transport instance with specified settings.
    *
    * @param settings The FedProProperties object instance which transport layer settings
    *                 will be loaded from.
    *                 Unprovided settings will get default values and non-transport
    *                 settings will be ignored.
    * @return A new Transport instance.
    */
   public static Transport createTcpTransport(TypedProperties settings)
   {
      return new TcpTransport(settings);
   }

   /**
    * Create a new TLS specific transport instance with default settings.
    *
    * @return A new Transport instance.
    */
   public static Transport createTlsTransport()
   {
      return createTlsTransport(null);
   }

   /**
    * Create a new TLS specific transport instance with specified settings.
    *
    * @param settings The FedProProperties object instance which transport layer settings
    *                 will be loaded from.
    *                 Unprovided settings will get default values and non-transport
    *                 settings will be ignored.
    * @return A new Transport instance.
    */
   public static Transport createTlsTransport(TypedProperties settings)
   {
      return new TlsTransport(null, settings);
   }

   /**
    * Create a new TLS specific transport instance with specified TLS keys and settings.
    *
    * @param tlsKeys  The TLS keys containing the necessary cryptographic information for
    *                 TLS.
    * @param settings The FedProProperties object instance which transport layer settings
    *                 will be loaded from.
    *                 Unprovided settings will get default values and non-transport
    *                 settings will be ignored.
    * @return A new Transport instance.
    */
   public static Transport createTlsTransport(
         TlsKeys tlsKeys,
         TypedProperties settings)
   {
      SSLContext sslContext = SecurityUtil.provideClientSSLContext((TlsKeysImpl) tlsKeys, settings);
      return createTlsTransport(sslContext, settings);
   }

   /**
    * Create a new TLS specific transport instance with specified SSLContext and settings.
    *
    * @param context  Defines the secure socket configuration for the Transport.
    * @param settings The FedProProperties object instance which transport layer settings
    *                 will be loaded from.
    *                 Unprovided settings will get default values and non-transport
    *                 settings will be ignored.
    * @return A new Transport instance.
    */
   public static Transport createTlsTransport(SSLContext context, TypedProperties settings)
   {
      return new TlsTransport(context, settings);
   }

   /**
    * Create a new Web Socket specific transport instance with default settings.
    *
    * @return A new Transport instance.
    */
   public static Transport createWebSocketTransport()
   {
      return createWebSocketTransport(null);
   }

   /**
    * Create a new Web Socket specific transport instance with specified settings.
    *
    * @param settings The FedProProperties object instance which transport layer settings
    *                 will be loaded from.
    *                 Unprovided settings will get default values and non-transport
    *                 settings will be ignored.
    * @return A new Transport instance.
    */
   public static Transport createWebSocketTransport(TypedProperties settings)
   {
      return new WebSocketTransport(settings);
   }

   /**
    * Create a new Secure Web Socket specific transport instance with default settings.
    *
    * @return A new Transport instance.
    */
   public static Transport createWebSocketSecureTransport()
   {
      return createWebSocketSecureTransport(null);
   }

   /**
    * Create a new Secure Web Socket specific transport instance with specified settings.
    *
    * @param settings The FedProProperties object instance which transport layer settings
    *                 will be loaded from.
    *                 Unprovided settings will get default values and non-transport
    *                 settings will be ignored.
    * @return A new Transport instance.
    */
   public static Transport createWebSocketSecureTransport(TypedProperties settings)
   {
      return new WebSocketSecureTransport(null, settings);
   }

   /**
    * Create a new Secure Web Socket specific transport instance with specified TLS keys
    * and settings.
    *
    * @param tlsKeys  The TLS keys containing the necessary cryptographic information for TLS.
    * @param settings The FedProProperties object instance which transport layer settings
    *                 will be loaded from.
    *                 Unprovided settings will get default values and non-transport
    *                 settings will be ignored.
    * @return A new Transport instance.
    */
   public static Transport createWebSocketSecureTransport(
         TlsKeys tlsKeys,
         TypedProperties settings)
   {
      SSLContext sslContext = SecurityUtil.provideClientSSLContext((TlsKeysImpl) tlsKeys, settings);
      return createWebSocketSecureTransport(sslContext, settings);
   }

   /**
    * Create a new Secure Web Socket specific transport instance with specified SSLContext
    * and settings.
    *
    * @param context  Defines the secure socket configuration for the Transport.
    * @param settings The FedProProperties object instance which transport layer settings
    *                 will be loaded from.
    *                 Unprovided settings will get default values and non-transport
    *                 settings will be ignored.
    * @return A new Transport instance.
    */
   public static Transport createWebSocketSecureTransport(SSLContext context, TypedProperties settings)
   {
      return new WebSocketSecureTransport(context, settings);
   }

   public static TlsKeys createTlsKeys()
   {
      return new TlsKeysImpl();
   }

   public static TlsKeys createTlsKeys(KeyStore keyStore, char[] password)
   {
      return new TlsKeysImpl(keyStore, password);
   }
}
