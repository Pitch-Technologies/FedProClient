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
import hla.rti1516_202X.encoding.DataElement;
import hla.rti1516_202X.encoding.DecoderException;
import hla.rti1516_202X.encoding.EncoderException;

/**
 * Base class for data elements.
 */
abstract class AbstractDataElement implements DataElement {
   @Override
   public ByteWrapper encode()
   throws EncoderException
   {
      try {
         ByteWrapper byteWrapper = new ByteWrapper(getEncodedLength());
         encode(byteWrapper);
         return byteWrapper;
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode DataElement");
      }
   }

   public byte[] toByteArray()
   throws EncoderException
   {
      try {
         ByteWrapper byteWrapper = encode();
         return byteWrapper.array();
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode DataElement");
      }
   }

   public DataElement decode(byte[] bytes)
   throws DecoderException
   {
      decode(new ByteWrapper(bytes));
      return this;
   }

   @Override
   public abstract int hashCode();

   @Override
   public abstract boolean equals(Object obj);

   @Override
   public abstract String toString();
}
