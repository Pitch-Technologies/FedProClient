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

package se.pitch.oss.fedpro.client_evolved.hla.time;

import hla.rti1516e.exceptions.CouldNotDecode;
import hla.rti1516e.time.HLAinteger64Interval;
import hla.rti1516e.time.HLAinteger64Time;
import hla.rti1516e.time.HLAinteger64TimeFactory;


public class HLAinteger64TimeFactoryImpl implements HLAinteger64TimeFactory {

   public HLAinteger64Time decodeTime(byte[] buffer, int offset)
   throws CouldNotDecode
   {
      return new HLAinteger64TimeImpl(buffer, offset);
   }

   public HLAinteger64Interval decodeInterval(byte[] buffer, int offset)
   throws CouldNotDecode
   {
      return new HLAinteger64IntervalImpl(buffer, offset);
   }

   public HLAinteger64Time makeInitial()
   {
      return HLAinteger64TimeImpl.INITIAL;
   }

   public HLAinteger64Time makeFinal()
   {
      return HLAinteger64TimeImpl.FINAL;
   }

   public HLAinteger64Time makeTime(long value)
   {
      return new HLAinteger64TimeImpl(value);
   }

   public HLAinteger64Interval makeZero()
   {
      return HLAinteger64IntervalImpl.ZERO;
   }

   public HLAinteger64Interval makeEpsilon()
   {
      return HLAinteger64IntervalImpl.EPSILON;
   }

   public HLAinteger64Interval makeInterval(long value)
   {
      return new HLAinteger64IntervalImpl(value);
   }

   public String getName()
   {
      return HLAinteger64TimeFactory.NAME;
   }
}
