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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class TimeoutTimer {

   private final ScheduledExecutorService _internalExecutorService;
   private final long _timeoutDurationMillis;
   // Eager means the timeout may trigger early, but never late. Lazy is the opposite.
   private final boolean _eager;
   private long _offsetTimeMillis;
   private final AtomicLong _timeoutTimestampMillis = new AtomicLong(Long.MAX_VALUE);

   private TimeoutTimer(
         String timerName,
         long timeoutDurationMillis,
         boolean eager)
   {
      _timeoutDurationMillis = timeoutDurationMillis;
      _internalExecutorService = Executors.newSingleThreadScheduledExecutor(r -> {
         Thread thread = Executors.defaultThreadFactory().newThread(r);
         thread.setName(timerName);
         thread.setDaemon(true);
         return thread;
      });
      _eager = eager;
      _offsetTimeMillis = timeoutDurationMillis;
   }

   static TimeoutTimer createLazyTimeoutTimer(String timerName, long timeoutDurationMillis)
   {
      return new TimeoutTimer(timerName, timeoutDurationMillis, false);
   }

   static TimeoutTimer createEagerTimeoutTimer(String timerName, long timeoutDurationMillis)
   {
      return new TimeoutTimer(timerName, timeoutDurationMillis, true);
   }

   void start(Runnable whenTimedOut)
   {
      if (_timeoutDurationMillis == 0) {
         // Do not timeout?
         return;
      }
      final int wakeUpTimesPerInterval = 6;
      long timeoutCheckIntervalMillis = _timeoutDurationMillis / wakeUpTimesPerInterval;
      if (timeoutCheckIntervalMillis == 0) {
         // Minimum time between checks
         timeoutCheckIntervalMillis = 1;
      }
      _offsetTimeMillis = _eager ? timeoutCheckIntervalMillis * (wakeUpTimesPerInterval - 1) : _timeoutDurationMillis;

      Runnable timerTask = () -> {
         if (hasTimedOut(_timeoutTimestampMillis)) {
            pause();
            whenTimedOut.run();
         }
      };

      _internalExecutorService.scheduleAtFixedRate(
            timerTask,
            timeoutCheckIntervalMillis,
            timeoutCheckIntervalMillis,
            TimeUnit.MILLISECONDS);
      extend();
   }

   private boolean hasTimedOut(AtomicLong timestampMillis)
   {
      return TimeUnit.NANOSECONDS.toMillis(System.nanoTime()) >= timestampMillis.get();
   }

   void pause()
   {
      _timeoutTimestampMillis.set(Long.MAX_VALUE);
   }

   void resume()
   {
      extend();
   }

   void extend()
   {
      // This method extends the time it takes for the timer to timeout.
      _timeoutTimestampMillis.set(TimeUnit.NANOSECONDS.toMillis(System.nanoTime()) + _offsetTimeMillis);
   }

   void cancel()
   {
      _internalExecutorService.shutdownNow();
   }

   long getTimeoutDurationMillis()
   {
      return _timeoutDurationMillis;
   }
}
