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

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAfloat64BE;
import hla.rti1516e.exceptions.CouldNotDecode;
import hla.rti1516e.exceptions.CouldNotEncode;
import hla.rti1516e.exceptions.IllegalTimeArithmetic;
import hla.rti1516e.time.HLAfloat64Interval;
import hla.rti1516e.time.HLAfloat64Time;
import se.pitch.oss.fedpro.client_evolved.hla.encoders.OmtEncoderFactory;


public class HLAfloat64TimeImpl implements HLAfloat64Time {
   private static final double INITIAL_VALUE = 0;
   private static final double FINAL_VALUE = Double.MAX_VALUE;

   static final HLAfloat64Time INITIAL = new HLAfloat64TimeImpl(INITIAL_VALUE);
   static final HLAfloat64Time FINAL = new HLAfloat64TimeImpl(FINAL_VALUE);

   private final double _value;

   public HLAfloat64TimeImpl()
   {
      _value = INITIAL_VALUE;
   }

   public HLAfloat64TimeImpl(double value)
   {
      _value = value;
   }

   public HLAfloat64TimeImpl(byte[] buffer, int offset)
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
         throw new CouldNotDecode("Unable to decode HLAfloat64Time: " + e);
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

   public HLAfloat64Time add(HLAfloat64Interval val)
   throws IllegalTimeArithmetic
   {
      double interval = val.getValue();
      double value = _value + interval;
      if (value == _value && !val.isZero()) {
         value = _value + Math.ulp(_value);
      }
      if (Double.isInfinite(value)) {
         throw new IllegalTimeArithmetic("Calculation overflow in add");
      }
      return new HLAfloat64TimeImpl(value);
   }

   public HLAfloat64Time subtract(HLAfloat64Interval val)
   throws IllegalTimeArithmetic
   {
      double interval = val.getValue();
      double value = _value - interval;
      if (value == _value && !val.isZero()) {
         double ulp = Math.ulp(_value);
         value = _value - ulp;
      }
      if (Double.isInfinite(value)) {
         throw new IllegalTimeArithmetic("Calculation overflow in subtract");
      }
      return new HLAfloat64TimeImpl(value);
   }

   public HLAfloat64Interval distance(HLAfloat64Time val)
   {
      return new HLAfloat64IntervalImpl(Math.abs(_value - val.getValue()));
   }

   public int compareTo(HLAfloat64Time other)
   {
      return Double.compare(_value, other.getValue());
   }

   public int encodedLength()
   {
      return OmtEncoderFactory.getInstance().createHLAfloat64BE().getEncodedLength();
   }

   public void encode(byte[] buffer, int offset)
   throws CouldNotEncode
   {
      if (buffer == null) {
         throw new CouldNotEncode("Cannot encode to null buffer");
      }
      final ByteWrapper wrapper = new ByteWrapper(buffer, offset);
      HLAfloat64BE encoder = OmtEncoderFactory.getInstance().createHLAfloat64BE(_value);
      try {
         encoder.encode(wrapper);
      } catch (EncoderException e) {
         throw new CouldNotEncode("Failed to encode HLAfloat64Time", e);
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
      if (!(o instanceof HLAfloat64Time)) {
         return false;
      }

      HLAfloat64Time that = (HLAfloat64Time) o;

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
      if (isFinal()) {
         return "HLAfloat64Time(INF)";
      } else {
         return "HLAfloat64Time(" + _value + ")";
      }
   }
}
