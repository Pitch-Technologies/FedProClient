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

#include <RTI/encoding/HLAvariantRecord.h>
#include <RTI/VariableLengthData.h>

#include <memory>
#include <map>
#include <typeinfo>

using namespace std;

namespace RTI_NAMESPACE
{

   bool operator<(
         const VariableLengthData & v0,
         const VariableLengthData & v1)
   {
      char * c0 = (char *) v0.data();
      char * c1 = (char *) v1.data();
      return std::lexicographical_compare(c0, c0 + v0.size(), c1, c1 + v1.size());
   }

   class HLAvariantRecordImplementation
   {
   public:
      HLAvariantRecordImplementation() = default;

      map <Integer64, pair<bool, DataElement *>> elements;
      std::unique_ptr<DataElement> discriminant;

      ~HLAvariantRecordImplementation()
      {
         map<Integer64, pair<bool, DataElement *> >::const_iterator i = elements.begin();
         while (i != elements.end()) {
            if (i->second.first) {
               DataElement * element = i->second.second;
               delete element;
            }
            i++;
         }
      }

      void setVariant(
            const DataElement & discriminant,
            bool del,
            DataElement * valuePtr)
      {
         auto iter = elements.find(discriminant.hash());
         if (iter != elements.end()) {
            DataElement * prototype = iter->second.second;
            if (prototype->isSameTypeAs(*valuePtr)) {
               if (iter->second.first) {
                  delete prototype;
               }
            } else {
               throw EncoderException(L"Non-matching type");
            }
         }
         elements[discriminant.hash()] = pair<bool, DataElement *>(del, valuePtr);
      }

   };

   HLAvariantRecord::HLAvariantRecord(DataElement const & discriminantPrototype)
   {
      _impl = new HLAvariantRecordImplementation();
      _impl->discriminant = discriminantPrototype.clone();
   }


   // PS: Deep copy assumed
   HLAvariantRecord::HLAvariantRecord(
         HLAvariantRecord const & rhs)
   {
      _impl = new HLAvariantRecordImplementation();
      DataElement * discriminant = rhs._impl->discriminant.get();
      _impl->discriminant = discriminant ? discriminant->clone() : std::unique_ptr<DataElement>();
      map<Integer64, pair<bool, DataElement *> >::const_iterator i = rhs._impl->elements.begin();
      while (i != rhs._impl->elements.end()) {
         _impl->elements[i->first] = pair<bool, DataElement *>(true, i->second.second->clone().release());
         i++;
      }
   }

   HLAvariantRecord::~HLAvariantRecord()
   {
      delete _impl;
   }

   // Returns a new copy of the DataElement
   // PS: Deep copy assumed
   RTI_UNIQUE_PTR<DataElement> HLAvariantRecord::clone() const
   {
      return make_unique_derived<DataElement, HLAvariantRecord>(*this);
   }

   bool HLAvariantRecord::isSameTypeAs(const DataElement & inData) const
   {
      return typeid(*this) == typeid(inData);
   }

   bool HLAvariantRecord::isSameTypeAs(
         DataElement const & discriminant,
         DataElement const & inData) const RTI_THROW(EncoderException)
   {
      map<Integer64, pair<bool, DataElement *> >::const_iterator iter = _impl->elements.find(discriminant.hash());
      if (iter == _impl->elements.end()) {
         return false;
      }
      return iter->second.second->isSameTypeAs(inData);
   }

   // Return true if given element matches prototype of this array.
   bool HLAvariantRecord::hasMatchingDiscriminantTypeAs(DataElement const & dataElement) const
   {
      return _impl->discriminant->isSameTypeAs(dataElement);
   }

   // Adds a copy of the dataElement instance to this fixed array all array elements
   // must have same type
#if (RTI_HLA_VERSION >= 2024)
   HLAvariantRecord &
#else
   void
#endif
   HLAvariantRecord::addVariant(
         const DataElement & discriminant,
         const DataElement & valuePrototype) RTI_THROW(EncoderException)
   {
      _impl->elements[discriminant.hash()] = pair<bool, DataElement *>(true, valuePrototype.clone().release());
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Adds a pointer to a dataElement instance to this fixed array all array elements
   // must have same type
   // Ownership is transferred
#if (RTI_HLA_VERSION >= 2024)
   HLAvariantRecord &
#else
   void
#endif
   HLAvariantRecord::addVariantPointer(
         const DataElement & discriminant,
         DataElement * valuePtr) RTI_THROW(EncoderException)
   {
      _impl->elements[discriminant.hash()] = pair<bool, DataElement *>(false, valuePtr);
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Sets the variant with the given discriminant to a copy of the given value
   // Discriminant must match prototype and value must match its variant
#if (RTI_HLA_VERSION >= 2024)
   HLAvariantRecord &
#else
   void
#endif
   HLAvariantRecord::setVariant(
         const DataElement & discriminant,
         DataElement const & value) RTI_THROW(EncoderException)
   {
      _impl->setVariant(discriminant, true, value.clone().release());
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Sets the variant with the given discriminant to the given value
   // Discriminant must match prototype and value must match its variant
#if (RTI_HLA_VERSION >= 2024)
   HLAvariantRecord &
#else
   void
#endif
   HLAvariantRecord::setVariantPointer(
         const DataElement & discriminant,
         DataElement * valuePtr) RTI_THROW(EncoderException)
   {
      _impl->setVariant(discriminant, false, valuePtr);
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

#if (RTI_HLA_VERSION >= 2024)
   HLAvariantRecord &
#else
   void
#endif
   HLAvariantRecord::setDiscriminant(
         const DataElement & discriminant) RTI_THROW(EncoderException)
   {
      _impl->discriminant = discriminant.clone();
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Returns a reference to the discriminant element
   const DataElement & HLAvariantRecord::getDiscriminant() const
   {
      return *(_impl->discriminant);
   }

   // Returns a reference to the value element
   const DataElement & HLAvariantRecord::getVariant() const RTI_THROW(EncoderException)
   {
      map<Integer64, pair<bool, DataElement *> >::const_iterator
            iter = _impl->elements.find(_impl->discriminant->hash());
      if (iter == _impl->elements.end()) {
         throw EncoderException(L"Discriminant is not mapped to a value");
      }
      return *(iter->second.second);
   }

   namespace
   {
      unsigned int getVariantOctetBoundary(map <Integer64, pair<bool, DataElement *>> & elements)
      {
         unsigned int boundary = 0;
         map<Integer64, pair<bool, DataElement *> >::const_iterator i = elements.begin();
         while (i != elements.end()) {
            DataElement * element = i->second.second;
            unsigned int b = element->getOctetBoundary();
            if (b > boundary) {
               boundary = b;
            }
            i++;
         }
         return boundary;
      }
   }

   // Returns the octet boundary of this element.
   unsigned int HLAvariantRecord::getOctetBoundary() const
   {
      unsigned int discriminantBoundary = _impl->discriminant->getOctetBoundary();
      unsigned int variantBoundary = getVariantOctetBoundary(_impl->elements);
      if (discriminantBoundary > variantBoundary) {
         return discriminantBoundary;
      } else {
         return variantBoundary;
      }
   }

   // Encodes this element into a new VariableLengthData
   VariableLengthData HLAvariantRecord::encode() const RTI_THROW(EncoderException)
   {
      VariableLengthData res;
      encode(res);
      return res;
   }

   // Encodes this element into an existing VariableLengthData
   void HLAvariantRecord::encode(
         VariableLengthData & inData) const RTI_THROW(EncoderException)
   {
      size_t length = getEncodedLength();
      vector<Octet> wrapper;
      encodeInto(wrapper);
      inData.setData(&wrapper[0], length);
   }

   // Encode this element and append it to a buffer
   void HLAvariantRecord::encodeInto(
         vector <Octet> & buffer) const RTI_THROW(EncoderException)
   {
      map<Integer64, pair<bool, DataElement *> >::const_iterator
            iter = _impl->elements.find(_impl->discriminant->hash());
      if (iter != _impl->elements.end()) {
         size_t ob = getOctetBoundary();
         size_t bs = buffer.size();
         size_t padding = Encoder::paddingSizeInBytes(bs, ob);
         Encoder::insertBytes(buffer, padding);
         _impl->discriminant->encodeInto(buffer);
         DataElement * variant = iter->second.second;
         // Align for variant
         Encoder::insertBytes(
               buffer,
               Encoder::paddingSizeInBytes(buffer.size(), getVariantOctetBoundary(_impl->elements)));
         variant->encodeInto(buffer);
      } else {
         throw EncoderException(L"Discriminant is not mapped to a value");
      }
   }

   // Returns the size in bytes of this element's encoding.
   size_t HLAvariantRecord::getEncodedLength() const RTI_THROW(EncoderException)
   {
      size_t size = _impl->discriminant->getEncodedLength();
      map<Integer64, pair<bool, DataElement *> >::const_iterator
            iter = _impl->elements.find(_impl->discriminant->hash());
      if (iter != _impl->elements.end()) {
         // Pad to variant octet boundary
         unsigned int octetBoundary = getVariantOctetBoundary(_impl->elements);
         while ((size % octetBoundary) != 0) {
            size += 1;
         }
         // Add size of selected element
         size += iter->second.second->getEncodedLength();
      } else {
         throw EncoderException(L"Discriminant is not mapped to a value");
      }
      return size;
   }

   // Decodes this element from the ByteWrapper.
#if (RTI_HLA_VERSION >= 2024)
   HLAvariantRecord &
#else
   void
#endif
   HLAvariantRecord::decode(
         VariableLengthData const & inData) RTI_THROW(EncoderException)
   {
      vector<Octet> buffer((char *) inData.data(), (char *) inData.data() + inData.size());
      decodeFrom(buffer, 0);
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Decodes this element starting at the index in the provided buffer
   size_t HLAvariantRecord::decodeFrom(
         vector <Octet> const & buffer,
         size_t startIndex) RTI_THROW(EncoderException)
   {
      size_t index = startIndex;
      // Skip to discriminant octet boundary
      index += Encoder::paddingSizeInBytes(index, _impl->discriminant->getOctetBoundary());
      // Decode discriminant
      index = _impl->discriminant->decodeFrom(buffer, index);
      map<Integer64, pair<bool, DataElement *> >::const_iterator
            iter = _impl->elements.find(_impl->discriminant->hash());
      if (iter != _impl->elements.end()) {
         DataElement & variantValue = *(iter->second.second);
         // Skip to variant octet boundary
         index += Encoder::paddingSizeInBytes(index, getVariantOctetBoundary(_impl->elements));
         // Decode variant
         index = variantValue.decodeFrom(buffer, index);
      } else {
         throw EncoderException(L"Discriminant is not mapped to a value");
      }
      return index;
   }

} // RTI_NAMESPACE

// NOLINTEND(hicpp-exception-baseclass)
