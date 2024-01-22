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

import se.pitch.oss.fedpro.common.transport.FedProSocket;
import se.pitch.oss.fedpro.common.transport.FedProSocketImpl;
import se.pitch.oss.fedpro.common.transport.Tls;

import javax.net.ssl.SNIHostName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import java.io.IOException;
import java.util.Collections;

public class TlsTransport extends TransportBase {

   private static final String SNI_HOST_NAME = System.getProperty("alias");
   private final SSLContext _context;

   public TlsTransport(SSLContext context, String host, int port)
   {
      super(host, port);
      _context = context;
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

      if (SNI_HOST_NAME != null) {
         // Use Server Name Indication (SNI) extension to send a host name
         // to indicate we want the new cert.
         SSLParameters params = socket.getSSLParameters();
         params.setServerNames(Collections.singletonList(new SNIHostName(SNI_HOST_NAME)));
         socket.setSSLParameters(params);
      }

      Tls.verifyTlsHandshakeOrClose(socket);

      return new FedProSocketImpl(socket);
   }
}
