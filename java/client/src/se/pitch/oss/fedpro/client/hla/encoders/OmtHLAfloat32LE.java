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
import hla.rti1516_202X.encoding.HLAfloat32LE;

import static java.lang.Float.*;

class OmtHLAfloat32LE extends AbstractDataElement implements HLAfloat32LE {
   private float _value;

   OmtHLAfloat32LE()
   {
      _value = 0.0f;
   }

   OmtHLAfloat32LE(float value)
   {
      _value = value;
   }

   public int getOctetBoundary()
   {
      return 4;
   }

   /**
    * @noinspection PointlessBitwiseExpression
    */
   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         final int intBits = floatToIntBits(_value);
         byteWrapper.put((intBits >>> 0) & 0xFF);
         byteWrapper.put((intBits >>> 8) & 0xFF);
         byteWrapper.put((intBits >>> 16) & 0xFF);
         byteWrapper.put((intBits >>> 24) & 0xFF);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAfloat32LE");
      }
   }

   public int getEncodedLength()
   {
      return 4;
   }

   /**
    * @noinspection PointlessBitwiseExpression
    */
   public OmtHLAfloat32LE decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         int intBits = 0;
         intBits += (short) byteWrapper.get() << 0;
         intBits += (short) byteWrapper.get() << 8;
         intBits += (short) byteWrapper.get() << 16;
         intBits += (short) byteWrapper.get() << 24;
         _value = intBitsToFloat(intBits);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAfloat32LE", e);
      }
      return this;
   }

   @Override
   public HLAfloat32LE decode(byte[] bytes)
   throws DecoderException
   {
      super.decode(bytes);
      return this;
   }

   public float getValue()
   {
      return _value;
   }

   public HLAfloat32LE setValue(float value)
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
      if (!(o instanceof OmtHLAfloat32LE)) {
         return false;
      }

      final OmtHLAfloat32LE omtHLAfloat32BE = (OmtHLAfloat32LE) o;

      if (_value != omtHLAfloat32BE._value) {
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
      return "HLAfloat32LE<" + _value + ">";
   }
}
