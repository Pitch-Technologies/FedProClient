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

package se.pitch.oss.fedpro.common.session;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import se.pitch.oss.fedpro.client.*;
import se.pitch.oss.fedpro.client.session.msg.HeartbeatResponseMessage;
import se.pitch.oss.fedpro.client.session.msg.MessageHeader;
import se.pitch.oss.fedpro.client.session.msg.NewSessionStatusMessage;
import se.pitch.oss.fedpro.client_common.SettingsParser;
import se.pitch.oss.fedpro.common.exceptions.SessionIllegalState;
import se.pitch.oss.fedpro.common.exceptions.SessionLost;
import se.pitch.oss.fedpro.utility.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TestSession {

   private final long _fakeSessionId = 42;
   private TypedProperties _clientSettings;

   private final StateWaiter _stateWaiter = new StateWaiter();

   @Before
   public void setUp()
   throws Exception
   {
      SettingsHelper.setFedProOverride(Settings.SETTING_NAME_CONNECTION_MAX_RETRY_ATTEMPTS, "3");
      _clientSettings = SettingsParser.parse();
   }

   @After
   public void tearDown()
   {
      _clientSettings = null;
      SettingsHelper.clearFedProOverrides();
   }

   @Test
   public void terminateSession_When_ReceiveInvalidSequenceNumber()
   throws IOException, SessionLost, SessionIllegalState, InterruptedException
   {
      MemoryTransport transport = new MemoryTransport();
      transport.addInboundPacket(createNewSessionStatus(_fakeSessionId));

      // Given
      final int invalidSequenceNumber = Integer.MIN_VALUE;
      transport.addInboundPacket(createHeartbeatResponse(_fakeSessionId, invalidSequenceNumber, 42));

      // When
      Session session = createSession(transport);
      session.start((sequenceNumber, hlaCallback) -> {});

      // Then
      Assert.assertEquals(
            Session.State.TERMINATED,
            _stateWaiter.waitForState(Session.State.TERMINATED, 4, TimeUnit.SECONDS));
   }

   @Test
   public void startSucceed_When_BrokenSocketOnFirstAttempt()
   throws SessionLost, SessionIllegalState, InterruptedException
   {
      final AtomicInteger connectionAttemptCount = new AtomicInteger(0);
      Transport transport = new SocketSupplierTransport(() -> {
         try {
            // Given the first connect attempt produce a broken socket
            if (connectionAttemptCount.getAndIncrement() == 0) {
               return new BrokenSocket();
            } else {
               MemorySocket socket = new MemorySocket();
               socket.addInboundPacket(createNewSessionStatus(_fakeSessionId));
               return socket;
            }
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      });

      // When
      Session session = createSession(transport);
         session.start((sequenceNumber, hlaCallback) -> {});

      // Then
      Assert.assertEquals(
            Session.State.RUNNING,
            _stateWaiter.waitForState(Session.State.RUNNING, 4, TimeUnit.SECONDS));
      Assert.assertEquals(_fakeSessionId, session.getId());
      Assert.assertTrue(connectionAttemptCount.get() > 1);
   }

   protected Session createSession(Transport transport)
   {
      return createSession(transport, _clientSettings);
   }

   protected Session createSession(Transport transport, TypedProperties settings)
   {
      Session session = SessionFactory.createSession(transport, settings);
      session.addStateListener(_stateWaiter);
      return session;
   }

   protected static byte[] createNewSessionStatus(long sessionId)
   throws IOException
   {
      NewSessionStatusMessage statusMessage = new NewSessionStatusMessage(NewSessionStatusMessage.REASON_SUCCESS);
      byte[] payload = statusMessage.encode();

      MessageHeader header = MessageHeader.with(
            payload.length, 0, sessionId, 0, MessageType.CTRL_NEW_SESSION_STATUS);

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      outputStream.write(header.encode());
      outputStream.write(payload);
      return outputStream.toByteArray();
   }

   protected static byte[] createHeartbeatResponse(long sessionId, int sequenceNumber, int responseTo)
   throws IOException
   {
      HeartbeatResponseMessage message = new HeartbeatResponseMessage(responseTo);
      byte[] payload = message.encode();

      MessageHeader header = MessageHeader.with(
            payload.length, sequenceNumber, sessionId, 0, MessageType.CTRL_HEARTBEAT_RESPONSE);

      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      outputStream.write(header.encode());
      outputStream.write(payload);
      return outputStream.toByteArray();
   }
}
