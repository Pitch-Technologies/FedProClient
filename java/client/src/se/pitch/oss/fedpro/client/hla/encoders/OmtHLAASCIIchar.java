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
import hla.rti1516_202X.encoding.HLAASCIIchar;

class OmtHLAASCIIchar extends AbstractDataElement implements HLAASCIIchar {
   private final OmtHLAoctet _value;

   OmtHLAASCIIchar(byte value)
   {
      _value = new OmtHLAoctet(value);
   }

   OmtHLAASCIIchar()
   {
      _value = new OmtHLAoctet();
   }

   public int getOctetBoundary()
   {
      return _value.getOctetBoundary();
   }

   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      _value.encode(byteWrapper);
   }

   public int getEncodedLength()
   {
      return _value.getEncodedLength();
   }

   public OmtHLAASCIIchar decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      _value.decode(byteWrapper);
      return this;
   }

   @Override
   public HLAASCIIchar decode(byte[] bytes)
   throws DecoderException
   {
      super.decode(bytes);
      return this;
   }

   public byte getValue()
   {
      return _value.getValue();
   }

   public HLAASCIIchar setValue(byte value)
   {
      _value.setValue(value);
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

      final OmtHLAASCIIchar omtHLAASCIIchar = (OmtHLAASCIIchar) o;

      if (!_value.equals(omtHLAASCIIchar._value)) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return _value.hashCode();
   }

   @Override
   public String toString()
   {
      return "HLAASCIIchar<" + _value + ">";
   }
}
