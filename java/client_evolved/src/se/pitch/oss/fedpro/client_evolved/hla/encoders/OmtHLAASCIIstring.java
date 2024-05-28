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

import hla.rti1516e.encoding.*;

import java.io.UnsupportedEncodingException;

class OmtHLAASCIIstring extends AbstractDataElement implements HLAASCIIstring {
   private String _string;
   private static final String CHARSET_NAME = "ISO-8859-1";

   OmtHLAASCIIstring()
   {
      _string = "";
   }

   OmtHLAASCIIstring(String s)
   {
      _string = (s != null) ? s : "";
   }

   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         byte[] bytes;
         try {
            bytes = _string.getBytes(CHARSET_NAME);
         } catch (UnsupportedEncodingException e) {
            bytes = _string.getBytes();
         }
         byteWrapper.putInt(_string.length());
         byteWrapper.put(bytes);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAASCIIstring", e);
      }
   }

   public final void decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         int length = byteWrapper.getInt();
         byteWrapper.verify(length);
         byte[] bytes = new byte[length];
         byteWrapper.get(bytes);
         try {
            _string = new String(bytes, CHARSET_NAME);
         } catch (UnsupportedEncodingException e) {
            _string = "<INVALID>";
            throw new IllegalArgumentException("Cannot decode ASCII string");
         }
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAASCIIstring", e);
      }
   }

   public int getEncodedLength()
   {
      return 4 + _string.length();
   }

   public int getOctetBoundary()
   {
      return 4;
   }

   public String getValue()
   {
      return _string;
   }

   public void setValue(String value)
   {
      _string = value;
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

      final OmtHLAASCIIstring omtAunicodeString = (OmtHLAASCIIstring) o;

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
      return "HLAASCIIstring<" + _string + ">";
   }
}
