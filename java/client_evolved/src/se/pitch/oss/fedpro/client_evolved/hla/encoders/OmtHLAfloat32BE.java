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
import hla.rti1516e.encoding.HLAfloat32BE;

class OmtHLAfloat32BE extends AbstractDataElement implements HLAfloat32BE {
   private float _value;

   OmtHLAfloat32BE()
   {
      _value = 0.0f;
   }

   OmtHLAfloat32BE(float value)
   {
      _value = value;
   }

   public int getOctetBoundary()
   {
      return 4;
   }

   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         int intBits = Float.floatToIntBits(_value);
         byteWrapper.putInt(intBits);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAfloat32BE");
      }
   }

   public int getEncodedLength()
   {
      return 4;
   }

   public void decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         int intBits = byteWrapper.getInt();
         _value = Float.intBitsToFloat(intBits);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAfloat32BE", e);
      }
   }

   public float getValue()
   {
      return _value;
   }

   public void setValue(float value)
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
      if (!(o instanceof OmtHLAfloat32BE)) {
         return false;
      }

      final OmtHLAfloat32BE omtHLAfloat32BE = (OmtHLAfloat32BE) o;

      if (_value != omtHLAfloat32BE._value) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return Float.valueOf(_value).hashCode();
   }

   @Override
   public String toString()
   {
      return "HLAfloat32BE<" + _value + ">";
   }
}
