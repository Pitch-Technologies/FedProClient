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

public enum MessageType {

   //Session Management
   CTRL_NEW_SESSION(1),
   CTRL_NEW_SESSION_STATUS(2),
   CTRL_HEARTBEAT(3),
   CTRL_HEARTBEAT_RESPONSE(4),
   CTRL_TERMINATE_SESSION(5),
   CTRL_SESSION_TERMINATED(6),

   //Reconnection
   CTRL_RESUME_REQUEST(10),
   CTRL_RESUME_STATUS(11),

   //HLA Calls and Callbacks
   HLA_CALL_REQUEST(20),
   HLA_CALL_RESPONSE(21),
   HLA_CALLBACK_REQUEST(22),
   HLA_CALLBACK_RESPONSE(23);

   private final int _encodedValue;

   MessageType(int encodedValue)
   {
      _encodedValue = encodedValue;
   }

   public int asInt()
   {
      return _encodedValue;
   }

   public boolean isControl()
   {
      return _encodedValue < HLA_CALL_REQUEST._encodedValue;
   }

   public boolean isHlaResponse()
   {
      return _encodedValue == HLA_CALL_RESPONSE._encodedValue || _encodedValue == HLA_CALLBACK_RESPONSE._encodedValue;
   }

   public static MessageType fromInt(int encodedValue)
   {
      for (MessageType value : values()) {
         if (value._encodedValue == encodedValue) {
            return value;
         }
      }
      return null;
   }
}
