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

import se.pitch.oss.fedpro.common.TlsMode;

import static se.pitch.oss.fedpro.common.Ports.DEFAULT_PORT_TCP;

public class TransportSettings {

   // Default values
   static final String DEFAULT_CONNECTION_HOST = "localhost";
   static final int DEFAULT_CONNECTION_PORT = DEFAULT_PORT_TCP;
   static final String DEFAULT_KEYSTORE_ALGORITHM = "SunX509";
   static final String DEFAULT_KEYSTORE_PASSWORD_PATH = null;
   static final String DEFAULT_KEYSTORE_PATH = null;
   static final String DEFAULT_KEYSTORE_TYPE = "JKS";
   static final boolean DEFAULT_KEYSTORE_USE_DEFAULT = false;
   static final TlsMode DEFAULT_TLS_MODE = TlsMode.DEFAULT;
   static final String DEFAULT_TLS_SNI = null;

}
