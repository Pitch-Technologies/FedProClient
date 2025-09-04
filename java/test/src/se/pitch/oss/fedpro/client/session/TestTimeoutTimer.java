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

package se.pitch.oss.fedpro.client.session;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class TestTimeoutTimer {

   @Test
   public void create()
   {
      TimeoutTimer.createLazyTimeoutTimer("placeholder", 1);
      TimeoutTimer.createEagerTimeoutTimer("placeholder", 1);
   }

   @Test
   public void getTimeoutDuration()
   {
      // Given
      long duration = 1;

      // When
      TimeoutTimer timer = TimeoutTimer.createLazyTimeoutTimer("placeholder", 1);

      // Then
      assertEquals(timer.getTimeoutDurationMillis(), duration);
   }

   @Test
   public void start()
   {
      // Given
      long timeoutDurationMs = 1;
      CountDownLatch ding = new CountDownLatch(1);
      TimeoutTimer timer = TimeoutTimer.createLazyTimeoutTimer("placeholder", timeoutDurationMs);

      // When
      timer.start(ding::countDown);

      // Then
      try {
         assertTrue(ding.await(timeoutDurationMs + 1000, TimeUnit.MILLISECONDS));
      } catch (InterruptedException e) {
         fail(e.getMessage());
      }
   }

   @Test
   public void startThenCancel()
   {
      // Given
      CountDownLatch ding = new CountDownLatch(1);
      TimeoutTimer timer = TimeoutTimer.createLazyTimeoutTimer("placeholder", 3600000);

      // When
      timer.start(ding::countDown);
      timer.cancel();

      // Then
      // Due to timeout duration of 3600s (1h) , we are reasonably confident the task will not run.
      assertEquals(ding.getCount(), 1);
   }

   @Test
   public void startThenPauseThenResume()
   {
      // Given
      long timeoutDurationMs = 20;
      CountDownLatch ding = new CountDownLatch(1);
      TimeoutTimer timer = TimeoutTimer.createLazyTimeoutTimer("placeholder", timeoutDurationMs);
      timer.start(ding::countDown);

      // When
      timer.pause();
      timer.resume();

      // Then
      try {
         assertTrue(ding.await(timeoutDurationMs + 1000, TimeUnit.MILLISECONDS));
      } catch (InterruptedException e) {
         fail(e.getMessage());
      }
   }

}
