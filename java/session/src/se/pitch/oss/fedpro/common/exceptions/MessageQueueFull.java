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

package se.pitch.oss.fedpro.common.exceptions;

/**
 * Session layer exception that may be thrown when failing to insert a message into a
 * queue with no free allocated space left.
 */
public class MessageQueueFull extends Exception {

   public MessageQueueFull(String message)
   {
      super(message);
   }

   public MessageQueueFull(Throwable cause)
   {
      super(cause);
   }

   public MessageQueueFull(
         String message,
         Throwable cause)
   {
      super(message, cause);
   }
}
