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

// Silence clang-tidy issues reported for standard HLA exception usage.
// NOLINTBEGIN(hicpp-exception-baseclass)

#include "Encoder.h"
#include "services-common/RTIcompat.h"

#include <RTI/encoding/HLAfixedArray.h>
#include <RTI/VariableLengthData.h>
#include <RTI/encoding/EncodingConfig.h>

#include <memory>
#include <typeinfo>
#include <vector>

using namespace std;

namespace RTI_NAMESPACE
{
   class HLAfixedArrayImplementation
   {
   public:
      HLAfixedArrayImplementation()
            : elements(0)
      {
      }

      vector <pair<bool, DataElement *>> elements;

      ~HLAfixedArrayImplementation()
      {
         for (auto & element : elements) {
            if (element.first) {
               delete element.second;
            }
         }
      }
   };

   // Default constructor
   HLAfixedArray::HLAfixedArray()
   {
      _impl = new HLAfixedArrayImplementation();
   }

   // Constructor which initializes the array with copies of a prototype element
   HLAfixedArray::HLAfixedArray(
         const DataElement & dataElement,
         size_t length)
   {
      _impl = new HLAfixedArrayImplementation();
      for (size_t i = 0; i < length; i++) {
         DataElement * p = dataElement.clone().release();
         _impl->elements.emplace_back(true, p);
      }
   }

   // PS: Deep copy assumed
   HLAfixedArray::HLAfixedArray(
         HLAfixedArray const & rhs)
   {
      _impl = new HLAfixedArrayImplementation();
      size_t length = (Integer32) rhs._impl->elements.size();
      for (size_t i = 0; i < length; i++) {
         DataElement * p = rhs[i].clone().release();
         _impl->elements.emplace_back(true, p);
      }
   }

   HLAfixedArray::~HLAfixedArray()
   {
      delete _impl;
   }

   HLAfixedArray & HLAfixedArray::operator=(
         HLAfixedArray const & rhs)
   {
      if (this == &rhs) {
         return *this;
      }
      auto * impl = new HLAfixedArrayImplementation();
      size_t length = (Integer32) rhs._impl->elements.size();
      for (size_t i = 0; i < length; i++) {
         DataElement * p = rhs[i].clone().release();
         impl->elements.emplace_back(true, p);
      }
      delete _impl;
      _impl = impl;

      return *this;
   }

   // Returns a new copy of the DataElement
   // PS: Deep copy assumed
   unique_ptr<DataElement> HLAfixedArray::clone() const
   {
      return make_unique_derived<DataElement, HLAfixedArray>(*this);
   }

   // Returns the number of elements in this fixed array.
   size_t HLAfixedArray::size() const
   {
      return _impl->elements.size();
   }

   // Return true if given element is same type as this; otherwise, false.
   bool HLAfixedArray::isSameTypeAs(const DataElement & other) const
   {
      return typeid(*this) == typeid(other);
   }

   // Return true if given element is same type as this; otherwise, false.
   bool HLAfixedArray::hasPrototypeSameTypeAs(DataElement const & dataElement) const
   {
      return _impl->elements[0].second->isSameTypeAs(dataElement);
   }


   // Sets the element at the given index to a copy of the given element instance
   // Element must match prototype.
#if (RTI_HLA_VERSION >= 2025)
   HLAfixedArray &
#else
   void
#endif
   HLAfixedArray::set(
         size_t index,
         const DataElement & dataElement) RTI_THROW(EncoderException)
   {
      if (index >= _impl->elements.size()) {
         throw EncoderException(L"Array index out of bounds");
      }
      if (!hasPrototypeSameTypeAs(dataElement)) {
         throw EncoderException(L"Element doesn't match prototype");
      }
      if (_impl->elements.at(index).first) {
         delete _impl->elements.at(index).second;
      }
      _impl->elements.at(index) = pair<bool, DataElement *>(true, dataElement.clone().release());
#if (RTI_HLA_VERSION >= 2025)
      return *this;
#endif
   }

   // Sets the element at the given index to the given element instance
   // Caller maintains lifetime of element
#if (RTI_HLA_VERSION >= 2025)
   HLAfixedArray &
#else
   void
#endif
   HLAfixedArray::setElementPointer(
         size_t index,
         DataElement * dataElement) RTI_THROW(EncoderException)
   {
      if (index >= _impl->elements.size()) {
         throw EncoderException(L"Array index out of bounds");
      }
      if (!hasPrototypeSameTypeAs(*dataElement)) {
         throw EncoderException(L"Element doesn't match prototype");
      }
      if (_impl->elements.at(index).first) {
         delete _impl->elements.at(index).second;
      }
      _impl->elements.at(index) = pair<bool, DataElement *>(false, dataElement);
#if (RTI_HLA_VERSION >= 2025)
      return *this;
#endif
   }

   // Returns a reference to the element at the specified index.
   const DataElement & HLAfixedArray::get(
         size_t index) const RTI_THROW(EncoderException)
   {
      if (index >= _impl->elements.size()) {
         throw EncoderException(L"Array index out of bounds");
      }
      return *(_impl->elements[index].second);
   }

   const DataElement & HLAfixedArray::operator[](
         size_t index) const RTI_THROW(EncoderException)
   {
      if (index >= _impl->elements.size()) {
         throw EncoderException(L"Array index out of bounds");
      }
      return *(_impl->elements[index].second);
   }

   // Returns the octet boundary of this element.
   unsigned int HLAfixedArray::getOctetBoundary() const
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
   VariableLengthData HLAfixedArray::encode() const RTI_THROW(EncoderException)
   {
      VariableLengthData res;
      encode(res);
      return res;
   }

   // Encodes this element into an existing VariableLengthData
   void HLAfixedArray::encode(
         VariableLengthData & inData) const RTI_THROW(EncoderException)
   {
      size_t length = getEncodedLength();
      vector<Octet> wrapper;
      encodeInto(wrapper);
      inData.setData(&wrapper[0], length);

   }

   // Encode this element and append it to a buffer
   void HLAfixedArray::encodeInto(
         vector <Octet> & buffer) const RTI_THROW(EncoderException)
   {
      Encoder::insertBytes(buffer, Encoder::paddingSizeInBytes(buffer.size(), getOctetBoundary()));      \
   for (auto & element : _impl->elements) {
         element.second->encodeInto(buffer);
      }
   }

   // Returns the size in bytes of this element's encoding.
   size_t HLAfixedArray::getEncodedLength() const RTI_THROW(EncoderException)
   {
      size_t size = 0;
      for (auto & element : _impl->elements) {
         DataElement * dataElement = element.second;
         size_t octetBoundary = dataElement->getOctetBoundary();
         while ((size % octetBoundary) != 0) {
            size++;
         }
         size += dataElement->getEncodedLength();
      }
      return size;
   }

   // Decodes this element from the ByteWrapper.

#if (RTI_HLA_VERSION >= 2025)
   HLAfixedArray &
#else
   void
#endif
   HLAfixedArray::decode(
         VariableLengthData const & inData) RTI_THROW(EncoderException)
   {
      vector<Octet> buffer((char *) inData.data(), (char *) inData.data() + inData.size());
      decodeFrom(buffer, 0);
#if (RTI_HLA_VERSION >= 2025)
      return *this;
#endif
   }

   // Decodes this element starting at the index in the provided buffer
   size_t HLAfixedArray::decodeFrom(
         vector <Octet> const & b,
         size_t startIndex) RTI_THROW(EncoderException)
   {
      size_t index = startIndex;
      for (auto & i : _impl->elements) {
         DataElement * element = i.second;
         size_t octetBoundary = element->getOctetBoundary();
         while (index % octetBoundary != 0) {
            index++; // Align
         }
         index = element->decodeFrom(b, index);
      }
      return index;
   }

} // RTI_NAMESPACE

// NOLINTEND(hicpp-exception-baseclass)
