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
import hla.rti1516e.encoding.HLAinteger32BE;

class OmtHLAinteger32BE extends AbstractDataElement implements HLAinteger32BE {
   private int _value;

   OmtHLAinteger32BE()
   {
      _value = 0;
   }

   OmtHLAinteger32BE(int value)
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
         byteWrapper.putInt(_value);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAinteger32BE");
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
         _value = byteWrapper.getInt();
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAinteger32BE", e);
      }
   }

   public int getValue()
   {
      return _value;
   }

   public void setValue(int value)
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
      if (!(o instanceof OmtHLAinteger32BE)) {
         return false;
      }

      final OmtHLAinteger32BE other = (OmtHLAinteger32BE) o;

      if (_value != other._value) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return _value;
   }

   @Override
   public String toString()
   {
      return "HLAinteger32BE<" + _value + ">";
   }
}
