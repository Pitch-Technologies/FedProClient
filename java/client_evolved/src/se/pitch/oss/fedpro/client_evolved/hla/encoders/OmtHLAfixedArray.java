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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class OmtHLAfixedArray<T extends DataElement> extends AbstractDataElement implements HLAfixedArray<T> {
   private final List<T> _elements;
   private int _octetBoundary = -1;

   OmtHLAfixedArray(DataElementFactory<T> factory, int size)
   {
      _elements = new ArrayList<>(size);
      for (int i = 0; i < size; i++) {
         _elements.add(factory.createElement(i));
      }
   }

   OmtHLAfixedArray(T[] elements)
   {
      _elements = new ArrayList<>(elements.length);
      _elements.addAll(Arrays.asList(elements));
   }

   public int size()
   {
      return _elements.size();
   }

   public T get(int index)
   {
      return _elements.get(index);
   }

   public Iterator<T> iterator()
   {
      _octetBoundary = -1; // In case iterator.remove is used to remove elements.
      return _elements.iterator();
   }

   public int getOctetBoundary()
   {
      if (_octetBoundary == -1) {
         int boundary = 1;
         for (T dataElement : _elements) {
            boundary = Math.max(boundary, dataElement.getOctetBoundary());
         }
         _octetBoundary = boundary;
      }
      return _octetBoundary;
   }

   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         for (T dataElement : _elements) {
            dataElement.encode(byteWrapper);
         }
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAfixedArray");
      }
   }

   public int getEncodedLength()
   {
      int size = 0;
      for (DataElement dataElement : _elements) {
         int octetBoundary = dataElement.getOctetBoundary();
         while ((size % octetBoundary) != 0) {
            size += 1;
         }
         size += dataElement.getEncodedLength();
      }
      return size;
   }

   public void decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         for (T dataElement : _elements) {
            dataElement.decode(byteWrapper);
         }
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAfixedArray", e);
      }
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

      final OmtHLAfixedArray<?> omtHLAfixedArray = (OmtHLAfixedArray<?>) o;

      if (!_elements.equals(omtHLAfixedArray._elements)) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return _elements.hashCode();
   }

   @Override
   public String toString()
   {
      StringBuilder sb = new StringBuilder("HLAfixedArray<");
      int pos = 0;
      for (T element : _elements) {
         if (pos++ != 0) {
            sb.append(", ");
         }
         sb.append(element.toString());
      }
      sb.append(">");
      return sb.toString();
   }
}
