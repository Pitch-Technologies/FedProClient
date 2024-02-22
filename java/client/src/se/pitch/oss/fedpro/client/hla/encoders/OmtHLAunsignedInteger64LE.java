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
import hla.rti1516_202X.encoding.HLAunsignedInteger64LE;

import static java.lang.Long.valueOf;

class OmtHLAunsignedInteger64LE extends AbstractDataElement implements HLAunsignedInteger64LE {
   private long _value;

   OmtHLAunsignedInteger64LE()
   {
      _value = 0L;
   }

   OmtHLAunsignedInteger64LE(long value)
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
         long value = _value;
         byteWrapper.put((int) (value >>> 0) & 0xFF);
         byteWrapper.put((int) (value >>> 8) & 0xFF);
         byteWrapper.put((int) (value >>> 16) & 0xFF);
         byteWrapper.put((int) (value >>> 24) & 0xFF);
         byteWrapper.put((int) (value >>> 32) & 0xFF);
         byteWrapper.put((int) (value >>> 40) & 0xFF);
         byteWrapper.put((int) (value >>> 48) & 0xFF);
         byteWrapper.put((int) (value >>> 56) & 0xFF);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAinteger64LE", e);
      }
   }

   public int getEncodedLength()
   {
      return 8;
   }

   /**
    * @noinspection PointlessBitwiseExpression
    */
   public HLAunsignedInteger64LE decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         long value = 0L;
         value += (long) byteWrapper.get() << 0;
         value += (long) byteWrapper.get() << 8;
         value += (long) byteWrapper.get() << 16;
         value += (long) byteWrapper.get() << 24;
         value += (long) byteWrapper.get() << 32;
         value += (long) byteWrapper.get() << 40;
         value += (long) byteWrapper.get() << 48;
         value += (long) byteWrapper.get() << 56;
         _value = value;
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAinteger64LE", e);
      }
      return this;
   }

   @Override
   public HLAunsignedInteger64LE decode(byte[] bytes)
   throws DecoderException
   {
      super.decode(bytes);
      return this;
   }

   public long getValue()
   {
      return _value;
   }

   public HLAunsignedInteger64LE setValue(long value)
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
      if (!(o instanceof OmtHLAunsignedInteger64LE)) {
         return false;
      }

      final OmtHLAunsignedInteger64LE other = (OmtHLAunsignedInteger64LE) o;

      if (_value != other._value) {
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
      return "HLAinteger64LE<" + _value + ">";
   }
}
