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
import java.util.Iterator;
import java.util.List;

class OmtHLAfixedRecord extends AbstractDataElement implements HLAfixedRecord {
   private final List<DataElement> _elements = new ArrayList<>(10);
   private int _octetBoundary = -1;

   OmtHLAfixedRecord()
   {
   }

   public final void add(DataElement dataElement)
   {
      _elements.add(dataElement);
      _octetBoundary = -1;
   }

   public int size()
   {
      return _elements.size();
   }

   public DataElement get(int index)
   {
      return _elements.get(index);
   }

   public Iterator<DataElement> iterator()
   {
      _octetBoundary = -1; // In case iterator.remove is used to remove elements.
      return _elements.iterator();
   }

   public int getOctetBoundary()
   {
      if (_octetBoundary == -1) {
         int boundary = 1;
         for (DataElement dataElement : _elements) {
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
         for (DataElement dataElement : _elements) {
            dataElement.encode(byteWrapper);
         }
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAfixedRecord");
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
         for (DataElement dataElement : _elements) {
            dataElement.decode(byteWrapper);
         }
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new DecoderException("Failed to decode HLAfixedRecord", e);
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

      final OmtHLAfixedRecord omtHLAfixedRecord = (OmtHLAfixedRecord) o;

      if (!_elements.equals(omtHLAfixedRecord._elements)) {
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
      StringBuilder sb = new StringBuilder("HLAfixedRecord<");
      int pos = 0;
      for (DataElement element : _elements) {
         if (pos++ != 0) {
            sb.append(", ");
         }
         sb.append(element.toString());
      }
      sb.append(">");
      return sb.toString();
   }
}
