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
import hla.rti1516_202X.encoding.HLAfloat64BE;
import hla.rti1516_202X.exceptions.CouldNotDecode;
import hla.rti1516_202X.exceptions.CouldNotEncode;
import hla.rti1516_202X.exceptions.IllegalTimeArithmetic;
import hla.rti1516_202X.time.HLAfloat64Interval;
import se.pitch.oss.fedpro.client.hla.encoders.OmtEncoderFactory;


public class HLAfloat64IntervalImpl implements HLAfloat64Interval {
   private static final double ZERO_VALUE = 0;
   private static final double EPSILON_VALUE = Double.MIN_VALUE;

   static final HLAfloat64Interval ZERO = new HLAfloat64IntervalImpl(ZERO_VALUE);
   static final HLAfloat64Interval EPSILON = new HLAfloat64IntervalImpl(EPSILON_VALUE);

   private final double _value;

   public HLAfloat64IntervalImpl()
   {
      _value = ZERO_VALUE;
   }

   public HLAfloat64IntervalImpl(double value)
   {
      _value = value;
   }

   public HLAfloat64IntervalImpl(
         byte[] buffer,
         int offset)
   throws CouldNotDecode
   {
      if (buffer == null) {
         throw new CouldNotDecode("Cannot decode null pointer");
      }
      try {
         final ByteWrapper wrapper = new ByteWrapper(buffer, offset);
         HLAfloat64BE decoder = OmtEncoderFactory.getInstance().createHLAfloat64BE();
         decoder.decode(wrapper);
         _value = decoder.getValue();
      } catch (ArrayIndexOutOfBoundsException | DecoderException e) {
         throw new CouldNotDecode("Unable to decode HLAfloat64Interval: " + e);
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

   public HLAfloat64Interval add(HLAfloat64Interval addend)
   throws IllegalTimeArithmetic
   {
      double interval = addend.getValue();
      double value = _value + interval;
      if (value == _value && !addend.isZero()) {
         value = _value + Math.ulp(_value);
      }
      if (Double.isInfinite(value)) {
         throw new IllegalTimeArithmetic("Calculation overflow in add");
      }
      return new HLAfloat64IntervalImpl(value);
   }

   public HLAfloat64Interval subtract(HLAfloat64Interval subtrahend)
   throws IllegalTimeArithmetic
   {
      double interval = subtrahend.getValue();
      double value = _value - interval;
      if (value == _value && !subtrahend.isZero()) {
         value = _value - Math.ulp(_value);
      }
      if (Double.isInfinite(value)) {
         throw new IllegalTimeArithmetic("Calculation overflow in subtract");
      }
      return new HLAfloat64IntervalImpl(value);
   }

   public int compareTo(HLAfloat64Interval other)
   {
      return Double.compare(_value, other.getValue());
   }

   public int encodedLength()
   {
      return OmtEncoderFactory.getInstance().createHLAfloat64BE().getEncodedLength();
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
         HLAfloat64BE encoder = OmtEncoderFactory.getInstance().createHLAfloat64BE(_value);
         encoder.encode(new ByteWrapper(buffer, offset));
      } catch (EncoderException e) {
         throw new CouldNotEncode("Failed to encode HLAfloat64Interval", e);
      }
   }

   public double getValue()
   {
      return _value;
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) {
         return true;
      }
      if (!(o instanceof HLAfloat64Interval)) {
         return false;
      }

      HLAfloat64Interval that = (HLAfloat64Interval) o;

      return Double.compare(_value, that.getValue()) == 0;
   }

   @Override
   public int hashCode()
   {
      return Double.valueOf(_value).hashCode();
   }

   @Override
   public String toString()
   {
      return "HLAfloat64Interval(" + _value + ")";
   }
}
