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

class OmtHLAextendableVariantRecord<T extends DataElement> extends AbstractDataElement implements HLAextendableVariantRecord<T> {
   public static final int ELEMENT_SIZE_OCTET_BOUNDARY = 4;
   private final Map<T, DataElement> _variantMap = new HashMap<>(10);
   private T _activeDiscriminant;

   OmtHLAextendableVariantRecord(T discriminant)
   {
      _activeDiscriminant = discriminant;
   }

   public OmtHLAextendableVariantRecord<T> setVariant(
         T discriminant,
         DataElement dataElement)
   {
      _variantMap.put(discriminant, dataElement);
      return this;
   }

   public HLAextendableVariantRecord<T> setDiscriminant(T discriminant)
   {
      _activeDiscriminant = discriminant;
      return this;
   }

   public T getDiscriminant()
   {
      return _activeDiscriminant;
   }

   public DataElement getValue()
   throws EncoderException
   {
      DataElement dataElement = _variantMap.get(_activeDiscriminant);
      if (dataElement == null) {
         throw new EncoderException("Discriminant is not mapped to a value");
      }
      return dataElement;
   }

   private int getVariantsOctetBoundary()
   {
      // In the standard, octet boundary is hardcoded to 8.
      return 8;
   }

   public int getOctetBoundary()
   {
      // In the standard, octet boundary is hardcoded to 8.
      return 8;
   }

   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      try {
         DataElement variantValue = getValue();
         byteWrapper.align(getOctetBoundary());
         _activeDiscriminant.encode(byteWrapper);
         // Align for element size
         byteWrapper.align(ELEMENT_SIZE_OCTET_BOUNDARY);
         byteWrapper.putInt(variantValue.getEncodedLength());
         byteWrapper.align(getVariantsOctetBoundary());
         variantValue.encode(byteWrapper);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAextendableVariantRecord");
      }
   }

   @Override
   public int getEncodedLength()
   {
      int size = _activeDiscriminant.getEncodedLength();
      while ((size % ELEMENT_SIZE_OCTET_BOUNDARY) != 0) {
         size += 1;
      }
      size += 4;
      DataElement variantValue = getValue();
      int octetBoundary = getVariantsOctetBoundary();
      while ((size % octetBoundary) != 0) {
         size += 1;
      }
      size += variantValue.getEncodedLength();
      return size;
   }

   public OmtHLAextendableVariantRecord<T> decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         _activeDiscriminant.decode(byteWrapper);
         byteWrapper.align(ELEMENT_SIZE_OCTET_BOUNDARY);
         int elementSize = byteWrapper.getInt();
         byteWrapper.align(getVariantsOctetBoundary());
         if (_variantMap.containsKey(_activeDiscriminant)) {
            DataElement variantValue = getValue();
            variantValue.decode(byteWrapper);
         } else {
            // Skip past unknown element type
            byteWrapper.advance(elementSize);
         }
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAextendableVariantRecord", e);
      }
      return this;
   }

   @Override
   public HLAextendableVariantRecord<T> decode(byte[] bytes)
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

      final OmtHLAextendableVariantRecord<T> omtHLAextendableVariantRecord = (OmtHLAextendableVariantRecord<T>) o;

      if (!Objects.equals(_activeDiscriminant, omtHLAextendableVariantRecord._activeDiscriminant)) {
         return false;
      }
      // Elements will be equal if activeDiscriminant and active variant are equal.
      if (_activeDiscriminant != null && !Objects.equals(
            _variantMap.get(_activeDiscriminant),
            omtHLAextendableVariantRecord._variantMap.get(_activeDiscriminant))) {
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
      StringBuilder sb = new StringBuilder("HLAextendableVariantRecord<");
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
