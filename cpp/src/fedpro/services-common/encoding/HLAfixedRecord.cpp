/***********************************************************************
  Copyright (C) 2023 Pitch Technologies AB

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 **********************************************************************/

// Silence clang-tidy issues reported for standard HLA exception.
// NOLINTBEGIN(hicpp-exception-baseclass)

#include "Encoder.h"
#include "services-common/RTIcompat.h"

#include <RTI/encoding/HLAfixedRecord.h>
#include <RTI/VariableLengthData.h>

#include <typeinfo>
#include <vector>

namespace RTI_NAMESPACE
{
   class HLAfixedRecordImplementation
   {
   public:
      HLAfixedRecordImplementation() = default;

      std::vector <std::pair<bool, DataElement *>> elements;

      ~HLAfixedRecordImplementation()
      {
         for (auto & element : elements) {
            if (element.first) {
               delete element.second;
            }
         }
      }
   };

   // Default constructor
   HLAfixedRecord::HLAfixedRecord()
   {
      _impl = new HLAfixedRecordImplementation();
   }

   // PS: Deep copy assumed
   HLAfixedRecord::HLAfixedRecord(
         HLAfixedRecord const & rhs)
   {
      _impl = new HLAfixedRecordImplementation();
      size_t length = rhs._impl->elements.size();
      for (size_t i = 0; i < length; i++) {
         DataElement * src = rhs._impl->elements[i].second;
         DataElement * clone = src->clone().release();
         _impl->elements.emplace_back(true, clone);
      }
   }

   HLAfixedRecord::~HLAfixedRecord()
   {
      delete _impl;
   }


   // Returns a new copy of the DataElement
   // PS: Deep copy assumed
   unique_ptr<DataElement> HLAfixedRecord::clone() const
   {
      return make_unique_derived<DataElement, HLAfixedRecord>(*this);
   }

   // Returns the number of elements in this fixed array.
   size_t HLAfixedRecord::size() const
   {
      return _impl->elements.size();
   }

   // Return true if given element is same type as this; otherwise, false.
   bool HLAfixedRecord::isSameTypeAs(const DataElement & other) const
   {
      return typeid(*this) == typeid(other);
   }

   // Return true if given element is same type as the indexed element;
   // otherwise, false.
   bool HLAfixedRecord::hasElementSameTypeAs(
         size_t index,
         DataElement const & inData) const
   {
      return _impl->elements[index].second->isSameTypeAs(inData);
   }

   // Adds a copy of the dataElement instance to this fixed array all array elements
   // must have same type
#if (RTI_HLA_VERSION >= 2024)
   HLAfixedRecord &
#else
   void
#endif
   HLAfixedRecord::appendElement(
         const DataElement & dataElement)
   {
      _impl->elements.emplace_back(true, dataElement.clone().release());
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Adds a pointer to a dataElement instance to this fixed array all array elements
   // must have same type
#if (RTI_HLA_VERSION >= 2024)
   HLAfixedRecord &
#else
   void
#endif
   HLAfixedRecord::appendElementPointer(
         DataElement * dataElement)
   {
      _impl->elements.emplace_back(false, dataElement);
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Returns a reference to the element at the specified index.
   const DataElement & HLAfixedRecord::get(size_t index) const RTI_THROW(EncoderException)
   {
      return *(_impl->elements[index]).second;
   }

   DataElement const & HLAfixedRecord::operator[](size_t index) const RTI_THROW(EncoderException)
   {
      return *(_impl->elements[index]).second;
   }

   // Sets the element at the given index to a copy of the given element instance
   // Element must match prototype.

#if (RTI_HLA_VERSION >= 2024)
   HLAfixedRecord &
#else
   void
#endif
   HLAfixedRecord::set(
         size_t index,
         const DataElement & dataElement) RTI_THROW(EncoderException)
   {
      if (hasElementSameTypeAs(index, dataElement)) {
         if (_impl->elements.at(index).first) {
            delete _impl->elements.at(index).second;
         }
         _impl->elements.at(index) = std::pair<bool, DataElement *>(true, dataElement.clone().release());
      } else {
         throw EncoderException(L"Non-matching type");
      }
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Sets the element at the given index to a copy of the given element instance
   // Element must match prototype.
#if (RTI_HLA_VERSION >= 2024)
   HLAfixedRecord &
#else
   void
#endif
   HLAfixedRecord::setElementPointer(
         size_t index,
         DataElement * dataElement) RTI_THROW(EncoderException)
   {
      if (hasElementSameTypeAs(index, *dataElement)) {
         if (_impl->elements.at(index).first) {
            delete _impl->elements.at(index).second;
         }
         _impl->elements.at(index) = std::pair<bool, DataElement *>(false, dataElement);
      } else {
         throw EncoderException(L"Non-matching type");
      }
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Returns the octet boundary of this element.
   unsigned int HLAfixedRecord::getOctetBoundary() const
   {
      unsigned int boundary = 1;
      for (auto & element : _impl->elements) {
         DataElement * dataElement = element.second;
         unsigned int b = dataElement->getOctetBoundary();
         if (b > boundary) {
            boundary = b;
         }
      }
      return boundary;
   }

   // Encodes this element into a new VariableLengthData
   VariableLengthData HLAfixedRecord::encode() const RTI_THROW(EncoderException)
   {
      VariableLengthData res;
      encode(res);
      return res;
   }

   // Encodes this element into an existing VariableLengthData
   void HLAfixedRecord::encode(
         VariableLengthData & inData) const RTI_THROW(EncoderException)
   {
      size_t length = getEncodedLength();
      if (length == 0) {
         inData.setData(nullptr, 0);
      } else {
         std::vector<Octet> wrapper;
         encodeInto(wrapper);
         inData.setData(&wrapper[0], length);
      }
   }

   // Encode this element and append it to a buffer
   void HLAfixedRecord::encodeInto(
         std::vector <Octet> & buffer) const RTI_THROW(EncoderException)
   {
      Encoder::insertBytes(buffer, Encoder::paddingSizeInBytes(buffer.size(), getOctetBoundary()));
      for (auto & element : _impl->elements) {
         element.second->encodeInto(buffer);
      }
   }

   // Returns the size in bytes of this element's encoding.
   size_t HLAfixedRecord::getEncodedLength() const RTI_THROW(EncoderException)
   {
      size_t size = 0;
      for (auto & element : _impl->elements) {
         DataElement * dataElement = element.second;
         while ((size % dataElement->getOctetBoundary()) != 0) {
            size += 1;
         }
         size += dataElement->getEncodedLength();
      }
      return size;
   }

   // Decodes this element from the ByteWrapper.
#if (RTI_HLA_VERSION >= 2024)
   HLAfixedRecord &
#else
   void
#endif
   HLAfixedRecord::decode(
         VariableLengthData const & inData) RTI_THROW(EncoderException)
   {
      std::vector<Octet> buffer((char *) inData.data(), (char *) inData.data() + inData.size());
      decodeFrom(buffer, 0);
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Decodes this element starting at the index in the provided buffer
   size_t HLAfixedRecord::decodeFrom(
         std::vector <Octet> const & b,
         size_t startIndex) RTI_THROW(EncoderException)
   {
      size_t index = startIndex;
      for (auto & i : _impl->elements) {
         DataElement * element = i.second;
         while (index % element->getOctetBoundary() != 0) {
            index++; // Align
         }
         index = element->decodeFrom(b, index);
      }
      return index;
   }

} // RTI_NAMESPACE

// NOLINTEND(hicpp-exception-baseclass)
