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
import hla.rti1516_202X.encoding.HLAunicodeString;

class OmtHLAunicodeString extends AbstractDataElement implements HLAunicodeString {
   private String _string;

   OmtHLAunicodeString()
   {
      _string = "";
   }

   OmtHLAunicodeString(String s)
   {
      _string = (s != null) ? s : "";
   }

   /**
    * @noinspection PointlessBitwiseExpression
    */
   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         byteWrapper.putInt(_string.length());
         int len = _string.length();
         for (int i = 0; i < len; i++) {
            int v = _string.charAt(i);
            byteWrapper.put((v >>> 8) & 0xFF);
            byteWrapper.put((v >>> 0) & 0xFF);
         }
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAunicodeString");
      }
   }

   /**
    * @noinspection PointlessBitwiseExpression
    */
   public final OmtHLAunicodeString decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         int length = byteWrapper.getInt();
         byteWrapper.verify(length * 2);
         char[] chars = new char[length];
         for (int i = 0; i < length; i++) {
            int hi = byteWrapper.get();
            int lo = byteWrapper.get();
            chars[i] = (char) ((hi << 8) + (lo << 0));
         }
         _string = new String(chars);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAunicodeString", e);
      }
      return this;
   }

   @Override
   public HLAunicodeString decode(byte[] bytes)
   throws DecoderException
   {
      super.decode(bytes);
      return this;
   }

   public int getEncodedLength()
   {
      return 4 + _string.length() * 2;
   }

   public int getOctetBoundary()
   {
      return 4;
   }

   public String getValue()
   {
      return _string;
   }

   public HLAunicodeString setValue(String value)
   {
      _string = value;
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
      if (!(o instanceof HLAunicodeString)) {
         return false;
      }

      final OmtHLAunicodeString omtAunicodeString = (OmtHLAunicodeString) o;

      if (!_string.equals(omtAunicodeString._string)) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return _string.hashCode();
   }

   @Override
   public String toString()
   {
      return "HLAunicodeString<" + _string + ">";
   }
}
