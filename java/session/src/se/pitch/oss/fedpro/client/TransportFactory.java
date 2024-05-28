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

import se.pitch.oss.fedpro.client.transport.TcpClientTransport;
import se.pitch.oss.fedpro.client.transport.TlsClientTransport;
import se.pitch.oss.fedpro.client.transport.WebSocketClientTransport;
import se.pitch.oss.fedpro.client.transport.WebSocketSecureClientTransport;
import se.pitch.oss.fedpro.common.Ports;
import se.pitch.oss.fedpro.common.TlsKeys;
import se.pitch.oss.fedpro.common.TlsMode;
import se.pitch.oss.fedpro.common.transport.TlsContext;
import se.pitch.oss.fedpro.common.transport.TlsKeysImpl;

import javax.net.ssl.SSLContext;
import java.security.KeyStore;

public class TransportFactory {

   public static ClientTransport createTcpClient()
   {
      return new TcpClientTransport();
   }

   public static ClientTransport createTlsClient(TlsMode mode,
                                                 TlsKeys tlsKeys)
   {
      boolean trustAllServers = mode == TlsMode.ENCRYPTED;
      return new TlsClientTransport(TlsContext.provideSSLContext((TlsKeysImpl) tlsKeys, trustAllServers));
   }

   public static ClientTransport createWebSocketClient()
   {
      return new WebSocketClientTransport();
   }

   public static ClientTransport createWebSocketClient(int port)
   {
      return new WebSocketClientTransport(port);
   }

   public static ClientTransport createWebSocketSecureClient(SSLContext context)
   {
      return new WebSocketSecureClientTransport(Ports.DEFAULT_PORT_WSS, context);
   }

   public static ClientTransport createWebSocketSecureClient(int port,
                                                    SSLContext context)
   {
      return new WebSocketSecureClientTransport(port, context);
   }

   public static ClientTransport createWebSocketSecureClient(TlsMode mode,
                                                             TlsKeys keys)
   {
      boolean trustAllServers = mode == TlsMode.ENCRYPTED;
      return new WebSocketSecureClientTransport(Ports.DEFAULT_PORT_WSS,
         TlsContext.provideSSLContext((TlsKeysImpl) keys, trustAllServers));
   }

   public static ClientTransport createWebSocketSecureClient(int port,
                                                    TlsMode mode,
                                                    TlsKeys keys)
   {
      boolean trustAllServers = mode == TlsMode.ENCRYPTED;
      return new WebSocketSecureClientTransport(port, TlsContext.provideSSLContext((TlsKeysImpl) keys, trustAllServers));
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
