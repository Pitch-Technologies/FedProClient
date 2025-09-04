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

package se.pitch.oss.fedpro.utility;

import se.pitch.oss.fedpro.client.Session;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class StateWaiter implements Session.StateListener {

   private final Object _stateLock = new Object();
   private Session.State _state = Session.State.NEW;

   public void waitForState(Session.State state)
   throws InterruptedException
   {
      synchronized (_stateLock) {
         while (_state != state) {
            _stateLock.wait();
         }
      }
   }

   public Session.State waitForState(Session.State state, long timeout, TimeUnit timeUnit)
   throws InterruptedException
   {
      long endTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
      synchronized (_stateLock) {
         for (long timeLeft = endTime - System.currentTimeMillis();
              _state != state && timeLeft > 0L; timeLeft = endTime - System.currentTimeMillis()) {
            _stateLock.wait(timeLeft);
         }

         return _state;
      }
   }

   public Session.State waitForStates(long timeout, TimeUnit timeUnit, Session.State... anticipatedStates)
   throws InterruptedException
   {
      long endTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(timeout, timeUnit);
      synchronized (_stateLock) {
         for (long timeLeft = endTime - System.currentTimeMillis();
              !Arrays.asList(anticipatedStates).contains(_state) && timeLeft > 0L;
              timeLeft = endTime - System.currentTimeMillis()) {
            _stateLock.wait(timeLeft);
         }

         return this._state;
      }
   }

   @Override
   public void onStateTransition(
         Session.State oldState,
         Session.State newState,
         String reason)
   {
      synchronized (_stateLock) {
         if (oldState != _state) {
            System.err.println("StateWaiter: Receiving a transition from " + oldState + ", but expected " + _state +
                  ". Possibly due to a missed transition");
         }
         _state = newState;
         _stateLock.notifyAll();
      }
      try {
         // To decrease the likelihood we're not transitioning away from an interesting state before the tests have a
         // chance to react, sleep a bit.
         Thread.sleep(1);
      } catch (InterruptedException e) {
      }
   }
}
