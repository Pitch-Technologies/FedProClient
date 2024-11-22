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
 * Session layer exception thrown when calling a member method but the session state does
 * not allow for the operation to be performed.
 */
public class SessionIllegalState extends Exception {

   public SessionIllegalState()
   {
   }

   public SessionIllegalState(String message)
   {
      super(message);
   }
}
