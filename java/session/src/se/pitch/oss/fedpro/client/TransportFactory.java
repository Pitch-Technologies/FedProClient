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

import se.pitch.oss.fedpro.client.transport.TcpTransport;
import se.pitch.oss.fedpro.client.transport.TlsTransport;
import se.pitch.oss.fedpro.client.transport.WebSocketTransport;
import se.pitch.oss.fedpro.client.transport.WebSocketSecureTransport;
import se.pitch.oss.fedpro.common.Ports;
import se.pitch.oss.fedpro.common.TlsKeys;
import se.pitch.oss.fedpro.common.TlsMode;
import se.pitch.oss.fedpro.common.transport.TlsContext;
import se.pitch.oss.fedpro.common.transport.TlsKeysImpl;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

public class TransportFactory {

   public static Transport createTcpTransport(String host)
   {
      return new TcpTransport(host, Ports.DEFAULT_PORT_TCP);
   }

   public static Transport createTcpTransport(String host, int port)
   {
      return new TcpTransport(host, port);
   }

   public static Transport createTlsTransport(
         TlsMode mode,
         TlsKeys tlsKeys,
         String host)
   {
      boolean trustAllServers = mode == TlsMode.ENCRYPTED;
      return createTlsTransport(
            TlsContext.provideSSLContext((TlsKeysImpl) tlsKeys, trustAllServers),
            host,
            Ports.DEFAULT_PORT_TLS);
   }

   public static Transport createTlsTransport(
         TlsMode mode,
         TlsKeys tlsKeys,
         String host,
         int port)
   {
      boolean trustAllServers = mode == TlsMode.ENCRYPTED;
      return createTlsTransport(TlsContext.provideSSLContext((TlsKeysImpl) tlsKeys, trustAllServers), host, port);
   }

   public static Transport createTlsTransport(SSLContext context, String host)
   {
      return new TlsTransport(context, host, Ports.DEFAULT_PORT_TLS);
   }

   public static Transport createTlsTransport(SSLContext context, String host, int port)
   {
      return new TlsTransport(context, host, port);
   }

   public static Transport createWebSocketTransport(String host)
   {
      return new WebSocketTransport(host, Ports.DEFAULT_PORT_WS);
   }

   public static Transport createWebSocketTransport(String host, int port)
   {
      return new WebSocketTransport(host, port);
   }

   public static Transport createWebSocketSecureTransport(SSLContext context, String host)
   {
      return new WebSocketSecureTransport(context, host, Ports.DEFAULT_PORT_WSS);
   }

   public static Transport createWebSocketSecureTransport(SSLContext context, String host, int port)
   {
      return new WebSocketSecureTransport(context, host, port);
   }

   public static Transport createWebSocketSecureTransport(
         TlsMode mode, TlsKeys keys, String host)
   {
      boolean trustAllServers = mode == TlsMode.ENCRYPTED;
      return createWebSocketSecureTransport(
            TlsContext.provideSSLContext((TlsKeysImpl) keys, trustAllServers), host, Ports.DEFAULT_PORT_WSS);
   }

   public static Transport createWebSocketSecureTransport(
         TlsMode mode,
         TlsKeys keys, String host, int port)
   {
      boolean trustAllServers = mode == TlsMode.ENCRYPTED;
      return createWebSocketSecureTransport(
            TlsContext.provideSSLContext((TlsKeysImpl) keys, trustAllServers), host, port);
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
