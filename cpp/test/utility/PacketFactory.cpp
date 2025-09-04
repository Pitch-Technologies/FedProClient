/***********************************************************************
  Copyright (C) 2023 Pitch Technologies AB

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 **********************************************************************/

#include "PacketFactory.h"

#include "session/msg/MessageHeader.h"
#include "session/msg/NewSessionStatusMessage.h"
#include "session/msg/HeartbeatResponseMessage.h"

#include "protobuf/generated/RTIambassador.pb.h"

using namespace FedPro;

std::string PacketFactory::createBadMessageType()
{
   auto badMessageType = static_cast<MessageType>(0);
   MessageHeader header = MessageHeader::with(
         0, 0, 0, 0, badMessageType);
   return header.encode();
}

std::string PacketFactory::createNewSessionStatus(uint64_t sessionId)
{
   NewSessionStatusMessage statusMessage{NewSessionStatusMessage::REASON_SUCCESS};
   std::string payload{statusMessage.encode()};

   MessageHeader header = MessageHeader::with(
         payload.size(), 0, sessionId, 0, MessageType::CTRL_NEW_SESSION_STATUS);

   return header.encode() + payload;
}

std::string PacketFactory::createHeartbeatResponse(
      uint64_t sessionId,
      int32_t sequenceNumber,
      int32_t responseTo)
{
   HeartbeatResponseMessage message{responseTo};
   std::string payload{message.encode()};

   MessageHeader header = MessageHeader::with(
         payload.size(), sequenceNumber, sessionId, 0, MessageType::CTRL_HEARTBEAT_RESPONSE);

   return header.encode() + payload;
}
