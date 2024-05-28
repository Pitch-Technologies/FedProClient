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

package se.pitch.oss.fedpro.client.hla.hla_time;

import hla.rti1516_202X.exceptions.CouldNotDecode;
import hla.rti1516_202X.time.HLAfloat64Interval;
import hla.rti1516_202X.time.HLAfloat64Time;
import hla.rti1516_202X.time.HLAfloat64TimeFactory;

public class HLAfloat64TimeFactoryImplHla4 implements HLAfloat64TimeFactory {

   public HLAfloat64Time decodeTime(byte[] buffer,
                                    int offset) throws CouldNotDecode
   {
      return new HLAfloat64TimeImplHla4(buffer, offset);
   }

   public HLAfloat64Interval decodeInterval(byte[] buffer,
                                            int offset) throws CouldNotDecode
   {
      return new HLAfloat64IntervalImplHla4(buffer, offset);
   }

   public HLAfloat64Time makeInitial()
   {
      return HLAfloat64TimeImplHla4.INITIAL;
   }

   public HLAfloat64Time makeFinal()
   {
      return HLAfloat64TimeImplHla4.FINAL;
   }

   public HLAfloat64Time makeTime(double value)
   {
      return new HLAfloat64TimeImplHla4(value);
   }

   public HLAfloat64Interval makeZero()
   {
      return HLAfloat64IntervalImplHla4.ZERO;
   }

   public HLAfloat64Interval makeEpsilon()
   {
      return HLAfloat64IntervalImplHla4.EPSILON;
   }

   public HLAfloat64Interval makeInterval(double value)
   {
      return new HLAfloat64IntervalImplHla4(value);
   }

   public String getName()
   {
      return HLAfloat64TimeFactory.NAME;
   }
}
