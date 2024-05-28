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

package se.pitch.oss.fedpro.common.transport.websockets;

import org.java_websocket.WebSocket;

import java.io.InputStream;
import java.util.Collection;

public interface WebSocketSender {

   void send(byte[] data);

   void send(
         byte[] data,
         Collection<WebSocket> clients);

   InputStream getInputStream(WebSocket socket);

   void close(WebSocket webSocket);
}
