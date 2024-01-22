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
import se.pitch.oss.fedpro.client.hla.encoders.OmtEncoderFactory;


public class HLAinteger64IntervalImpl implements HLAinteger64Interval {
   private static final long ZERO_VALUE = 0;
   private static final long EPSILON_VALUE = 1;

   static final HLAinteger64Interval ZERO = new HLAinteger64IntervalImpl(ZERO_VALUE);
   static final HLAinteger64Interval EPSILON = new HLAinteger64IntervalImpl(EPSILON_VALUE);

   private final long _value;

   public HLAinteger64IntervalImpl()
   {
      _value = ZERO_VALUE;
   }

   public HLAinteger64IntervalImpl(long value)
   {
      _value = value;
   }

   public HLAinteger64IntervalImpl(
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
         throw new CouldNotDecode("Unable to decode HLAinteger64Interval: " + e);
      }
   }

   public boolean isZero()
   {
      return _value == ZERO_VALUE;
   }

   public boolean isEpsilon()
   {
      return _value == EPSILON_VALUE;
   }

   public HLAinteger64Interval add(HLAinteger64Interval addend)
   throws IllegalTimeArithmetic
   {
      long lhs = _value;
      long rhs = addend.getValue();
      if ((rhs > 0L) ? (lhs > (Long.MAX_VALUE - rhs)) : (lhs < (Long.MIN_VALUE - rhs))) {
         throw new IllegalTimeArithmetic("Calculation overflow in add");
      }
      return new HLAinteger64IntervalImpl(lhs + rhs);
   }

   public HLAinteger64Interval subtract(HLAinteger64Interval subtrahend)
   throws IllegalTimeArithmetic
   {
      long lhs = _value;
      long rhs = subtrahend.getValue();
      if ((rhs > 0L) ? (lhs < (Long.MIN_VALUE + rhs)) : (lhs > (Long.MAX_VALUE + rhs))) {
         throw new IllegalTimeArithmetic("Calculation overflow in subtract");
      }
      return new HLAinteger64IntervalImpl(lhs - rhs);
   }

   public int compareTo(HLAinteger64Interval other)
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
      try {
         HLAinteger64BE encoder = OmtEncoderFactory.getInstance().createHLAinteger64BE(_value);
         encoder.encode(new ByteWrapper(buffer, offset));
      } catch (EncoderException e) {
         throw new CouldNotEncode("Failed to encode HLAinteger64Interval", e);
      }
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) {
         return true;
      }
      if (!(o instanceof HLAinteger64Interval)) {
         return false;
      }

      HLAinteger64Interval that = (HLAinteger64Interval) o;

      return _value == that.getValue();
   }

   @Override
   public int hashCode()
   {
      return Long.valueOf(_value).hashCode();
   }

   public long getValue()
   {
      return _value;
   }

   @Override
   public String toString()
   {
      return "HLAinteger64Interval<" + _value + ">";
   }
}
