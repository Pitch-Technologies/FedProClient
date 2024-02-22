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

import hla.rti1516_202X.encoding.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static java.lang.Math.max;

class OmtHLAvariantRecord<T extends DataElement> extends AbstractDataElement implements HLAvariantRecord<T> {
   private final Map<T, DataElement> _variantMap = new HashMap<>(10);
   private T _activeDiscriminant;
   private int _octetBoundary = -1;
   private int _variantsOctetBoundary = -1;

   OmtHLAvariantRecord(T discriminant)
   {
      _activeDiscriminant = discriminant;
   }

   public OmtHLAvariantRecord<T> setVariant(
         T discriminant,
         DataElement dataElement)
   {
      _variantMap.put(discriminant, dataElement);
      _octetBoundary = -1;
      _variantsOctetBoundary = -1;
      return this;
   }

   public HLAvariantRecord<T> setDiscriminant(T discriminant)
   {
      if (_activeDiscriminant != null) {
         _octetBoundary = -1;
      }
      _activeDiscriminant = discriminant;
      return this;
   }

   public T getDiscriminant()
   {
      return _activeDiscriminant;
   }

   public DataElement getValue()
   {
      return _variantMap.get(_activeDiscriminant);
   }

   private int getVariantsOctetBoundary()
   {
      if (_variantsOctetBoundary == -1) {
         int octetBoundary = 0;
         for (DataElement dataElement : _variantMap.values()) {
            octetBoundary = max(octetBoundary, dataElement.getOctetBoundary());
         }
         _variantsOctetBoundary = octetBoundary;
      }
      return _variantsOctetBoundary;
   }

   public int getOctetBoundary()
   {
      if (_octetBoundary == -1) {
         _octetBoundary = max(_activeDiscriminant.getOctetBoundary(), getVariantsOctetBoundary());
      }
      return _octetBoundary;
   }

   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         _activeDiscriminant.encode(byteWrapper);
         byteWrapper.align(getVariantsOctetBoundary());
         DataElement variantValue = getValue();
         if (variantValue != null) {
            variantValue.encode(byteWrapper);
         }
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAvariantRecord");
      }
   }

   public int getEncodedLength()
   {
      int size = _activeDiscriminant.getEncodedLength();
      int octetBoundary = getVariantsOctetBoundary();
      while ((size % octetBoundary) != 0) {
         size += 1;
      }
      DataElement variantValue = getValue();
      if (variantValue != null) {
         size += variantValue.getEncodedLength();
      }
      return size;
   }

   public OmtHLAvariantRecord<T> decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         _activeDiscriminant.decode(byteWrapper);
         byteWrapper.align(getVariantsOctetBoundary());
         if (!_variantMap.containsKey(_activeDiscriminant)) {
            throw new DecoderException("Unknown discriminant: " + _activeDiscriminant);
         }
         DataElement variantValue = getValue();
         if (variantValue != null) {
            variantValue.decode(byteWrapper);
         }
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAvariantRecord", e);
      }
      return this;
   }

   @Override
   public HLAvariantRecord<T> decode(byte[] bytes)
   throws DecoderException
   {
      super.decode(bytes);
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

      final OmtHLAvariantRecord<?> omtHLAvariantRecord = (OmtHLAvariantRecord<?>) o;

      if (!Objects.equals(_activeDiscriminant, omtHLAvariantRecord._activeDiscriminant)) {
         return false;
      }
      if (_activeDiscriminant != null && !Objects.equals(getValue(), omtHLAvariantRecord.getValue())) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      int result;
      result = _variantMap.hashCode();
      result = 29 * result + (_activeDiscriminant != null ? _activeDiscriminant.hashCode() : 0);
      return result;
   }

   @Override
   public String toString()
   {
      StringBuilder sb = new StringBuilder("HLAvariantRecord<");
      sb.append("activeDiscriminant = ").append(_activeDiscriminant);
      for (Map.Entry<T, DataElement> entry : _variantMap.entrySet()) {
         sb.append(", ");
         T discriminant = entry.getKey();
         DataElement variant = entry.getValue();
         sb.append("[discriminant = ").append(discriminant).append(", variant = ").append(variant).append("]");
      }
      sb.append(">");
      return sb.toString();
   }
}
