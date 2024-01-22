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
import hla.rti1516_202X.encoding.HLAopaqueData;

import java.util.Arrays;
import java.util.Iterator;

import static java.lang.Integer.toHexString;
import static java.lang.System.arraycopy;

class OmtHLAopaqueData extends AbstractDataElement implements HLAopaqueData {
   private static final byte[] EMPTY_ARRAY = new byte[0];

   private byte[] _elements;

   OmtHLAopaqueData()
   {
      _elements = EMPTY_ARRAY;
   }

   OmtHLAopaqueData(byte[] bytes)
   {
      _elements = new byte[bytes.length];
      arraycopy(bytes, 0, _elements, 0, bytes.length);
   }

   public int size()
   {
      return _elements.length;
   }

   public byte get(int index)
   {
      return _elements[index];
   }

   public Iterator<Byte> iterator()
   {
      return new ByteArrayIterator(_elements);
   }

   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      try {
         byteWrapper.align(4);
         byteWrapper.putInt(_elements.length);
         byteWrapper.put(_elements);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAopaqueData");
      }
   }

   public OmtHLAopaqueData decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(4);
         int encodedLength = byteWrapper.getInt();
         byteWrapper.verify(encodedLength);
         _elements = new byte[encodedLength];
         byteWrapper.get(_elements);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAopaqueData", e);
      }
      return this;
   }

   @Override
   public HLAopaqueData decode(byte[] bytes)
   throws DecoderException
   {
      super.decode(bytes);
      return this;
   }

   public int getEncodedLength()
   {
      return 4 + _elements.length;
   }

   public int getOctetBoundary()
   {
      return 4;
   }

   public byte[] getValue()
   {
      return _elements;
   }

   public HLAopaqueData setValue(byte[] value)
   {
      _elements = value;
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
      if (o == null || getClass() != o.getClass()) {
         return false;
      }

      final OmtHLAopaqueData omtHLAopaqueData = (OmtHLAopaqueData) o;

      if (!Arrays.equals(_elements, omtHLAopaqueData._elements)) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return Arrays.hashCode(_elements);
   }

   static void appendByteArray(
         StringBuffer sb,
         byte[] data)
   {
      for (int i = 0; i < data.length; i++) {
         if ((i != 0) && (i % 4 == 0)) {
            sb.append(' ');
         }
         int element = (int) data[i] & 0xFF;
         if (element < 16) {
            sb.append('0');
         }
         sb.append(toHexString(element).toUpperCase());
      }
   }

   @Override
   public String toString()
   {
      StringBuffer sb = new StringBuffer("HLAopaqueData<");
      appendByteArray(sb, _elements);
      sb.append(">");
      return sb.toString();
   }
}
