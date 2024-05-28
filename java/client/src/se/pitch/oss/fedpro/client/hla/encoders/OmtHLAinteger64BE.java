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
import hla.rti1516_202X.encoding.HLAinteger64BE;

class OmtHLAinteger64BE extends AbstractDataElement implements HLAinteger64BE {
   private long _value;

   OmtHLAinteger64BE()
   {
      _value = 0L;
   }

   OmtHLAinteger64BE(long value)
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
         byteWrapper.put((int) (value >>> 56) & 0xFF);
         byteWrapper.put((int) (value >>> 48) & 0xFF);
         byteWrapper.put((int) (value >>> 40) & 0xFF);
         byteWrapper.put((int) (value >>> 32) & 0xFF);
         byteWrapper.put((int) (value >>> 24) & 0xFF);
         byteWrapper.put((int) (value >>> 16) & 0xFF);
         byteWrapper.put((int) (value >>> 8) & 0xFF);
         byteWrapper.put((int) (value >>> 0) & 0xFF);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAinteger64BE");
      }
   }

   public int getEncodedLength()
   {
      return 8;
   }

   /**
    * @noinspection PointlessBitwiseExpression
    */
   public HLAinteger64BE decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         long value = 0L;
         value += (long) byteWrapper.get() << 56;
         value += (long) byteWrapper.get() << 48;
         value += (long) byteWrapper.get() << 40;
         value += (long) byteWrapper.get() << 32;
         value += (long) byteWrapper.get() << 24;
         value += (long) byteWrapper.get() << 16;
         value += (long) byteWrapper.get() << 8;
         value += (long) byteWrapper.get() << 0;
         _value = value;
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAinteger64BE", e);
      }
      return this;
   }

   @Override
   public HLAinteger64BE decode(byte[] bytes)
   throws DecoderException
   {
      super.decode(bytes);
      return this;
   }

   public long getValue()
   {
      return _value;
   }

   public HLAinteger64BE setValue(long value)
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
      if (!(o instanceof OmtHLAinteger64BE)) {
         return false;
      }

      final OmtHLAinteger64BE other = (OmtHLAinteger64BE) o;

      if (_value != other._value) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return Long.valueOf(_value).hashCode();
   }

   @Override
   public String toString()
   {
      return "HLAinteger64BE<" + _value + ">";
   }
}
