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

#include <RTI/encoding/HLAvariableArray.h>
#include <RTI/VariableLengthData.h>

#include <memory>
#include <typeinfo>
#include <vector>

using namespace std;

namespace RTI_NAMESPACE
{
   class HLAvariableArrayImplementation
   {
   public:
      HLAvariableArrayImplementation() = default;

      explicit HLAvariableArrayImplementation(const DataElement & prototype)
            : _prototype(prototype.clone())
      {
      }

      ~HLAvariableArrayImplementation()
      {
         for (auto & element : elements) {
            if (element.first) {
               delete element.second;
            }
         }
      }

      void resize(size_t newSize)
      {
         if (newSize < elements.size()) {
            // Delete unwanted elements
            for (size_t i = newSize; i < elements.size(); i++) {
               if (elements[i].first) {
                  delete elements[i].second;
               }
            }
            while (newSize < elements.size()) {
               elements.resize(newSize);
            }
         } else if (newSize > elements.size()) {
            while (newSize > elements.size()) {
               elements.push_back(pair<bool, DataElement *>(true, _prototype->clone().release()));
            }
         } else {
            //size is already equal
         }
      }

      vector <pair<bool, DataElement *>> elements;
      std::unique_ptr<DataElement> _prototype;
   };

   // Default constructor
   HLAvariableArray::HLAvariableArray()
   {
      _impl = new HLAvariableArrayImplementation();
   }

   HLAvariableArray::HLAvariableArray(
         const DataElement & dataElement)
   {
      _impl = new HLAvariableArrayImplementation(dataElement);
   }

   HLAvariableArray::HLAvariableArray(
         HLAvariableArray const & rhs)
   {
      _impl = new HLAvariableArrayImplementation(*(rhs._impl->_prototype));
      size_t length = rhs._impl->elements.size();
      for (size_t i = 0; i < length; i++) {
         DataElement * p = rhs[i].clone().release();
         _impl->elements.push_back(pair<bool, DataElement *>(true, p));
      }
   }

   HLAvariableArray::~HLAvariableArray()
   {
      delete _impl;
   }

   // Returns a new copy of the DataElement
   // PS: Deep copy assumed
   RTI_UNIQUE_PTR<DataElement> HLAvariableArray::clone() const
   {
      return make_unique_derived<DataElement, HLAvariableArray>(*this);
   }

   // Returns the number of elements in this variable array.
   size_t HLAvariableArray::size() const
   {
      return _impl->elements.size();
   }

   // Adds a copy of the dataElement instance to this variable array all array elements
   // must have same type
#if (RTI_HLA_VERSION >= 2024)
   HLAvariableArray &
#else
   void
#endif
   HLAvariableArray::addElement(
         const DataElement & dataElement) RTI_THROW(EncoderException)
   {
      _impl->elements.push_back(pair<bool, DataElement *>(true, dataElement.clone().release()));
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Adds a copy of the dataElement instance to this variable array all array elements
   // must have same type
#if (RTI_HLA_VERSION >= 2024)
   HLAvariableArray &
#else
   void
#endif
   HLAvariableArray::addElementPointer(
         DataElement * dataElement) RTI_THROW(EncoderException)
   {
      _impl->elements.push_back(pair<bool, DataElement *>(false, dataElement));
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   bool HLAvariableArray::isSameTypeAs(const DataElement & inData) const
   {
      return typeid(*this) == typeid(inData);
   }

   bool HLAvariableArray::hasPrototypeSameTypeAs(const DataElement & dataElement) const
   {
      return _impl->_prototype->isSameTypeAs(dataElement);
   }

   // Returns a reference to the element at the specified index.
   const DataElement & HLAvariableArray::get(
         size_t index) const RTI_THROW(EncoderException)
   {
      return *(_impl->elements[index].second);
   }

#if (RTI_HLA_VERSION >= 2024)
   HLAvariableArray &
#else
   void
#endif
   HLAvariableArray::set(
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
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

#if (RTI_HLA_VERSION >= 2024)
   HLAvariableArray &
#else
   void
#endif
   HLAvariableArray::setElementPointer(
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
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   DataElement const & HLAvariableArray::operator[](size_t index) const RTI_THROW(EncoderException)
   {
      if (index >= _impl->elements.size()) {
         throw EncoderException(L"Array index out of bounds");
      }
      return get(index);
   }

   // Returns the octet boundary of this element.
   unsigned int HLAvariableArray::getOctetBoundary() const
   {
      unsigned int boundary = 4;
      for (auto & element : _impl->elements) {
         DataElement * dataElement = element.second;
         unsigned int b = dataElement->getOctetBoundary();
         if (b > boundary) {
            boundary = b;
         }
      }

      if (_impl->elements.empty()) {
         unsigned int b = _impl->_prototype->getOctetBoundary();
         if (b > boundary) {
            boundary = b;
         }
      }

      return boundary;
   }

   // Encodes this element into a new VariableLengthData
   VariableLengthData HLAvariableArray::encode() const RTI_THROW(EncoderException)
   {
      VariableLengthData res;
      encode(res);
      return res;
   }

   // Encodes this element into an existing VariableLengthData
   void HLAvariableArray::encode(
         VariableLengthData & inData) const RTI_THROW(EncoderException)
   {
      size_t length = getEncodedLength();
      vector<Octet> wrapper;
      encodeInto(wrapper);
      inData.setData(&wrapper[0], length);
   }

   // Encode this element and append it to a buffer
   void HLAvariableArray::encodeInto(
         vector <Octet> & buffer) const RTI_THROW(EncoderException)
   {
      Encoder::insertBytes(buffer, Encoder::paddingSizeInBytes(buffer.size(), getOctetBoundary()));
      Encoder::appendInt(buffer, (Integer32) _impl->elements.size());
      for (auto & element : _impl->elements) {
         element.second->encodeInto(buffer);
      }
   }

   // Returns the size in bytes of this element's encoding.
   size_t HLAvariableArray::getEncodedLength() const RTI_THROW(EncoderException)
   {
      size_t size = 0;
      size += 4; // element count
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
   HLAvariableArray &
#else
   void
#endif
   HLAvariableArray::decode(
         VariableLengthData const & inData) RTI_THROW(EncoderException)
   {
      vector<Octet> buffer((char *) inData.data(), (char *) inData.data() + inData.size());
      decodeFrom(buffer, 0);
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Decodes this element starting at the index in the provided buffer
   size_t HLAvariableArray::decodeFrom(
         vector <Octet> const & b,
         size_t startIndex) RTI_THROW(EncoderException)
   {
      size_t index = startIndex;
      index += Encoder::paddingSizeInBytes(index, 4);
      if (index + 4 > b.size()) {
         throw EncoderException(L"HLAvariableArray cannot decode (indata too short)");
      }
      size_t elementCount = Encoder::decodeInt(b, index);
      index += 4;
      if (elementCount > b.size()) {
         throw EncoderException(L"HLAvariableArray cannot decode (indata too short)");
      }
      if (index + elementCount > b.size()) {
         throw EncoderException(L"HLAvariableArray cannot decode (indata too short)");
      }
      _impl->resize(elementCount);
      for (size_t i = 0; i < elementCount; i++) {
         DataElement * element = _impl->elements[i].second;
         index += Encoder::paddingSizeInBytes(index, element->getOctetBoundary());
         index = element->decodeFrom(b, index);
      }
      return index;
   }

} // RTI_NAMESPACE

// NOLINTEND(hicpp-exception-baseclass)
