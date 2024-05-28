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

class OmtHLAvariableArray<T extends DataElement> extends AbstractDataElement implements HLAvariableArray<T> {
   private final List<T> _elements = new ArrayList<>(10);
   private final DataElementFactory<T> _elementFactory;
   private int _octetBoundary = -1;

   OmtHLAvariableArray(DataElementFactory<T> elementFactory, T... elements)
   {
      _elementFactory = elementFactory;
      _elements.addAll(Arrays.asList(elements));
   }

   public void addElement(T dataElement)
   {
      _elements.add(dataElement);
      _octetBoundary = -1;
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

   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         byteWrapper.putInt(_elements.size());
         for (T dataElement : _elements) {
            dataElement.encode(byteWrapper);
         }
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAvariableArray");
      }
   }

   public void decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(getOctetBoundary());
         int elements = byteWrapper.getInt();
         // assume element.getEncodedLength() == 1, better than nothing
         byteWrapper.verify(elements);
         if (_elementFactory != null) {
            resize(elements);
            for (int i = 0; i < elements; i++) {
               T element = _elements.get(i);
               element.decode(byteWrapper);
            }
         } else {
            if (_elements.size() != elements) {
               throw new DecoderException("Preloaded elements count doesn't match decoded value");
            }
            for (T element : _elements) {
               element.decode(byteWrapper);
            }
         }
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAvariableArray", e);
      }
   }

   public int getEncodedLength()
   {
      int size = 4;
      for (T dataElement : _elements) {
         while ((size % dataElement.getOctetBoundary()) != 0) {
            size += 1;
         }
         size += dataElement.getEncodedLength();
      }
      return size;
   }

   public int getOctetBoundary()
   {
      if (_octetBoundary == -1) {
         int boundary = 4; // Minimum is 4 for the count-element
         for (T dataElement : _elements) {
            boundary = Math.max(boundary, dataElement.getOctetBoundary());
         }
         if (_elements.isEmpty() && _elementFactory != null) {
            DataElement dataElement = _elementFactory.createElement(0);
            boundary = Math.max(boundary, dataElement.getOctetBoundary());
         }
         _octetBoundary = boundary;
      }
      return _octetBoundary;
   }

   public void resize(int newSize)
   {
      if (newSize < _elements.size()) {
         while (newSize < _elements.size()) {
            _elements.remove(_elements.size() - 1);
         }
      } else if (newSize > _elements.size()) {
         while (newSize > _elements.size()) {
            _elements.add(_elementFactory.createElement(_elements.size()));
         }
      } else {
         //size is already equal
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
      if (!(o instanceof HLAvariableArray)) {
         return false;
      }

      final OmtHLAvariableArray<?> omtAvariableArray = (OmtHLAvariableArray<?>) o;

      if (!_elements.equals(omtAvariableArray._elements)) {
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
      StringBuilder sb = new StringBuilder("HLAvariableArray<");
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
