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

package se.pitch.oss.fedpro.client_evolved.hla.encoders;

import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.encoding.DecoderException;
import hla.rti1516e.encoding.EncoderException;
import hla.rti1516e.encoding.HLAfloat64LE;

class OmtHLAfloat64LE extends AbstractDataElement implements HLAfloat64LE {
   private double _value;

   public OmtHLAfloat64LE()
   {
      _value = 0.0;
   }

   public OmtHLAfloat64LE(double value)
   {
      _value = value;
   }

   public int getOctetBoundary()
   {
      return 8;
   }

   /**
    * @noinspection PointlessBitwiseExpression
    */
   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         final long longBits = Double.doubleToLongBits(_value);
         byteWrapper.put((int) (longBits >>> 0) & 0xFF);
         byteWrapper.put((int) (longBits >>> 8) & 0xFF);
         byteWrapper.put((int) (longBits >>> 16) & 0xFF);
         byteWrapper.put((int) (longBits >>> 24) & 0xFF);
         byteWrapper.put((int) (longBits >>> 32) & 0xFF);
         byteWrapper.put((int) (longBits >>> 40) & 0xFF);
         byteWrapper.put((int) (longBits >>> 48) & 0xFF);
         byteWrapper.put((int) (longBits >>> 56) & 0xFF);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAfloat64LE");
      }
   }

   public int getEncodedLength()
   {
      return 8;
   }

   /**
    * @noinspection PointlessBitwiseExpression
    */
   public void decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         long longBits = 0L;
         longBits += (long) byteWrapper.get() << 0;
         longBits += (long) byteWrapper.get() << 8;
         longBits += (long) byteWrapper.get() << 16;
         longBits += (long) byteWrapper.get() << 24;
         longBits += (long) byteWrapper.get() << 32;
         longBits += (long) byteWrapper.get() << 40;
         longBits += (long) byteWrapper.get() << 48;
         longBits += (long) byteWrapper.get() << 56;
         _value = Double.longBitsToDouble(longBits);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAfloat64LE", e);
      }
   }

   public double getValue()
   {
      return _value;
   }

   public void setValue(double value)
   {
      _value = value;
   }

   /**
    * @noinspection RedundantIfStatement
    */
   @Override
   public boolean equals(Object o)
   {
      if (this == o) {
         return true;
      }
      if (!(o instanceof OmtHLAfloat64LE)) {
         return false;
      }

      final OmtHLAfloat64LE omtHLAfloat64LE = (OmtHLAfloat64LE) o;

      if (_value != omtHLAfloat64LE._value) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return Double.valueOf(_value).hashCode();
   }

   @Override
   public String toString()
   {
      return "HLAfloat64LE<" + _value + ">";
   }
}
