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

package se.pitch.oss.fedpro.client.hla.encoders;

import hla.rti1516_202X.encoding.ByteWrapper;
import hla.rti1516_202X.encoding.DecoderException;
import hla.rti1516_202X.encoding.EncoderException;
import hla.rti1516_202X.encoding.HLAfloat64BE;

import static java.lang.Double.*;

class OmtHLAfloat64BE extends AbstractDataElement implements HLAfloat64BE {
   private double _value;

   OmtHLAfloat64BE()
   {
      _value = 0.0;
   }

   OmtHLAfloat64BE(double value)
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
         final long longBits = doubleToLongBits(_value);
         byteWrapper.put((int) (longBits >>> 56) & 0xFF);
         byteWrapper.put((int) (longBits >>> 48) & 0xFF);
         byteWrapper.put((int) (longBits >>> 40) & 0xFF);
         byteWrapper.put((int) (longBits >>> 32) & 0xFF);
         byteWrapper.put((int) (longBits >>> 24) & 0xFF);
         byteWrapper.put((int) (longBits >>> 16) & 0xFF);
         byteWrapper.put((int) (longBits >>> 8) & 0xFF);
         byteWrapper.put((int) (longBits >>> 0) & 0xFF);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAfloat64BE");
      }
   }

   public int getEncodedLength()
   {
      return 8;
   }

   /**
    * @noinspection PointlessBitwiseExpression
    */
   public OmtHLAfloat64BE decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         long longBits = 0L;
         longBits += (long) byteWrapper.get() << 56;
         longBits += (long) byteWrapper.get() << 48;
         longBits += (long) byteWrapper.get() << 40;
         longBits += (long) byteWrapper.get() << 32;
         longBits += (long) byteWrapper.get() << 24;
         longBits += (long) byteWrapper.get() << 16;
         longBits += (long) byteWrapper.get() << 8;
         longBits += (long) byteWrapper.get() << 0;
         _value = longBitsToDouble(longBits);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAfloat64BE", e);
      }
      return this;
   }

   @Override
   public HLAfloat64BE decode(byte[] bytes)
   throws DecoderException
   {
      super.decode(bytes);
      return this;
   }

   public double getValue()
   {
      return _value;
   }

   public HLAfloat64BE setValue(double value)
   {
      _value = value;
      return this;
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
      if (!(o instanceof OmtHLAfloat64BE)) {
         return false;
      }

      final OmtHLAfloat64BE omtHLAfloat64LE = (OmtHLAfloat64BE) o;

      if (_value != omtHLAfloat64LE._value) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return valueOf(_value).hashCode();
   }

   @Override
   public String toString()
   {
      return "HLAfloat64BE<" + _value + ">";
   }
}
