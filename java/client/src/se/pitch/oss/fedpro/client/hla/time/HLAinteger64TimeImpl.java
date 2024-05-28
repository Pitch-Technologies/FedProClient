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

package se.pitch.oss.fedpro.client.hla.time;

import hla.rti1516_202X.encoding.ByteWrapper;
import hla.rti1516_202X.encoding.DecoderException;
import hla.rti1516_202X.encoding.EncoderException;
import hla.rti1516_202X.encoding.HLAinteger64BE;
import hla.rti1516_202X.exceptions.CouldNotDecode;
import hla.rti1516_202X.exceptions.CouldNotEncode;
import hla.rti1516_202X.exceptions.IllegalTimeArithmetic;
import hla.rti1516_202X.time.HLAinteger64Interval;
import hla.rti1516_202X.time.HLAinteger64Time;
import se.pitch.oss.fedpro.client.hla.encoders.OmtEncoderFactory;


public class HLAinteger64TimeImpl implements HLAinteger64Time {
   private static final long INITIAL_VALUE = 0;
   private static final long FINAL_VALUE = Long.MAX_VALUE;

   static final HLAinteger64Time INITIAL = new HLAinteger64TimeImpl(INITIAL_VALUE);
   static final HLAinteger64Time FINAL = new HLAinteger64TimeImpl(FINAL_VALUE);

   private final long _value;

   public HLAinteger64TimeImpl()
   {
      _value = INITIAL_VALUE;
   }

   public HLAinteger64TimeImpl(long value)
   {
      _value = value;
   }

   public HLAinteger64TimeImpl(
         byte[] buffer,
         int offset)
   throws CouldNotDecode
   {
      if (buffer == null) {
         throw new CouldNotDecode("Cannot decode null pointer");
      }
      try {
         final ByteWrapper wrapper = new ByteWrapper(buffer, offset);
         HLAinteger64BE decoder = OmtEncoderFactory.getInstance().createHLAinteger64BE();
         decoder.decode(wrapper);
         _value = decoder.getValue();
      } catch (ArrayIndexOutOfBoundsException | DecoderException e) {
         throw new CouldNotDecode("Unable to decode HLAinteger64Time: " + e);
      }
   }

   public boolean isInitial()
   {
      return _value == INITIAL_VALUE;
   }

   public boolean isFinal()
   {
      return _value == FINAL_VALUE;
   }

   public HLAinteger64Time add(HLAinteger64Interval val)
   throws IllegalTimeArithmetic
   {
      long lhs = _value;
      long rhs = val.getValue();
      if ((rhs > 0L) ? (lhs > (Long.MAX_VALUE - rhs)) : (lhs < (Long.MIN_VALUE - rhs))) {
         throw new IllegalTimeArithmetic("Calculation overflow in add");
      }
      return new HLAinteger64TimeImpl(lhs + rhs);
   }

   public HLAinteger64Time subtract(HLAinteger64Interval val)
   throws IllegalTimeArithmetic
   {
      long lhs = _value;
      long rhs = val.getValue();
      if ((rhs > 0L) ? (lhs < (Long.MIN_VALUE + rhs)) : (lhs > (Long.MAX_VALUE + rhs))) {
         throw new IllegalTimeArithmetic("Calculation overflow in subtract");
      }
      return new HLAinteger64TimeImpl(lhs - rhs);
   }

   public HLAinteger64Interval distance(HLAinteger64Time val)
   {
      return new HLAinteger64IntervalImpl(Math.abs(_value - val.getValue()));
   }

   public int compareTo(HLAinteger64Time other)
   {
      return Long.compare(_value, other.getValue());
   }

   public int encodedLength()
   {
      return OmtEncoderFactory.getInstance().createHLAinteger64BE().getEncodedLength();
   }

   public void encode(
         byte[] buffer,
         int offset)
   throws CouldNotEncode
   {
      if (buffer == null) {
         throw new CouldNotEncode("Cannot encode to null buffer");
      }
      final ByteWrapper wrapper = new ByteWrapper(buffer, offset);
      HLAinteger64BE encoder = OmtEncoderFactory.getInstance().createHLAinteger64BE(_value);
      try {
         encoder.encode(wrapper);
      } catch (EncoderException e) {
         throw new CouldNotEncode("Failed to encode HLAinteger64Time", e);
      }
   }

   public long getValue()
   {
      return _value;
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) {
         return true;
      }
      if (!(o instanceof HLAinteger64Time)) {
         return false;
      }

      HLAinteger64Time that = (HLAinteger64Time) o;

      return _value == that.getValue();
   }

   @Override
   public int hashCode()
   {
      return Long.valueOf(_value).hashCode();
   }

   @Override
   public String toString()
   {
      if (isFinal()) {
         return "HLAinteger64Time<INF>";
      } else {
         return "HLAinteger64Time<" + _value + ">";
      }
   }
}
