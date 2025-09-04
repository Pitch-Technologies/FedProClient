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

#if (RTI_HLA_VERSION >= 2025)

#include "Encoder.h"

#include <RTI/encoding/HLAextendableVariantRecord.h>
#include <RTI/VariableLengthData.h>

#include <memory>
#include <map>
#include <typeinfo>

static int const ELEMENT_SIZE_OCTET_BOUNDARY = 4;
using namespace std;

namespace RTI_NAMESPACE
{

   bool operator<(
         const VariableLengthData & v0,
         const VariableLengthData & v1);

   class HLAextendableVariantRecordImplementation
   {
   public:
      HLAextendableVariantRecordImplementation() = default;

      map <Integer64, pair<bool, DataElement *>> elements;
      std::unique_ptr<DataElement> discriminant;

      ~HLAextendableVariantRecordImplementation()
      {
         auto i = elements.begin();
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
         Integer64 discriminantHash = discriminant.hash();
         auto iter = elements.find(discriminantHash);
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
         elements[discriminantHash] = pair<bool, DataElement *>(del, valuePtr);
      }

   };


   HLAextendableVariantRecord::HLAextendableVariantRecord(DataElement const & discriminantPrototype)
   {
      _impl = new HLAextendableVariantRecordImplementation();
      _impl->discriminant = discriminantPrototype.clone();
   }

   // PS: Deep copy assumed
   HLAextendableVariantRecord::HLAextendableVariantRecord(
         HLAextendableVariantRecord const & rhs)
   {
      _impl = new HLAextendableVariantRecordImplementation();
      DataElement * discriminant = rhs._impl->discriminant.get();
      _impl->discriminant = discriminant ? discriminant->clone() : std::unique_ptr<DataElement>();
      map<Integer64, pair<bool, DataElement *> >::const_iterator i = rhs._impl->elements.begin();
      while (i != rhs._impl->elements.end()) {
         _impl->elements[i->first] = pair<bool, DataElement *>(true, i->second.second->clone().release());
         i++;
      }
   }

   HLAextendableVariantRecord::~HLAextendableVariantRecord()
   {
      delete _impl;
   }

   // Returns a new copy of the DataElement
   // PS: Deep copy assumed
   std::unique_ptr<DataElement> HLAextendableVariantRecord::clone() const
   {
      return std::make_unique<HLAextendableVariantRecord>(*this);
   }

   bool HLAextendableVariantRecord::isSameTypeAs(const rti1516_2025::DataElement & inData) const
   {
      return typeid(*this) == typeid(inData);
   }

   bool HLAextendableVariantRecord::isSameTypeAs(
         DataElement const & discriminant,
         DataElement const & inData) const
   {
      map<Integer64, pair<bool, DataElement *> >::const_iterator iter = _impl->elements.find(discriminant.hash());
      if (iter == _impl->elements.end()) {
         return false;
      }
      return iter->second.second->isSameTypeAs(inData);
   }

   // Return true if given element matches prototype of this array.
   bool HLAextendableVariantRecord::hasMatchingDiscriminantTypeAs(DataElement const & dataElement) const
   {
      return _impl->discriminant->isSameTypeAs(dataElement);
   }

   // Adds a copy of the dataElement instance to this fixed array all array elements
   // must have same type
   HLAextendableVariantRecord & HLAextendableVariantRecord::addVariant(
         const DataElement & discriminant,
         const DataElement & valuePrototype)
   {
      _impl->elements[discriminant.hash()] = pair<bool, DataElement *>(true, valuePrototype.clone().release());
      return *this;
   }

   // Adds a pointer to a dataElement instance to this fixed array all array elements
   // must have same type
   // Ownership is transferred
   HLAextendableVariantRecord & HLAextendableVariantRecord::addVariantPointer(
         const DataElement & discriminant,
         DataElement * valuePtr)
   {
      _impl->elements[discriminant.hash()] = pair<bool, DataElement *>(false, valuePtr);
      return *this;
   }

   // Sets the variant with the given discriminant to a copy of the given value
   // Discriminant must match prototype and value must match its variant
   HLAextendableVariantRecord & HLAextendableVariantRecord::setVariant(
         const DataElement & discriminant,
         DataElement const & value)
   {
      _impl->setVariant(discriminant, true, value.clone().release());
      return *this;
   }

   // Sets the variant with the given discriminant to the given value
   // Discriminant must match prototype and value must match its variant
   HLAextendableVariantRecord & HLAextendableVariantRecord::setVariantPointer(
         const DataElement & discriminant,
         DataElement * valuePtr)
   {
      _impl->setVariant(discriminant, false, valuePtr);
      return *this;
   }

   HLAextendableVariantRecord & HLAextendableVariantRecord::setDiscriminant(
         const DataElement & discriminant)
   {
      _impl->discriminant = discriminant.clone();
      return *this;
   }

   // Returns a reference to the discriminant element
   const DataElement & HLAextendableVariantRecord::getDiscriminant() const
   {
      return *(_impl->discriminant);
   }

   // Returns a reference to the value element
   const DataElement & HLAextendableVariantRecord::getVariant() const
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
         // In the standard, octet boundary is hardcoded to 8.
         return 8;
      }
   }

   // Returns the octet boundary of this element.
   unsigned int HLAextendableVariantRecord::getOctetBoundary() const
   {
      // In the standard, octet boundary is hardcoded to 8.
      return 8;
   }

   // Encodes this element into a new VariableLengthData
   VariableLengthData HLAextendableVariantRecord::encode() const
   {
      VariableLengthData res;
      encode(res);
      return res;
   }

   // Encodes this element into an existing VariableLengthData
   void HLAextendableVariantRecord::encode(
         VariableLengthData & inData) const
   {
      size_t length = getEncodedLength();
      vector<Octet> wrapper;
      encodeInto(wrapper);
      inData.setData(&wrapper[0], length);
   }

   // Encode this element and append it to a buffer
   void HLAextendableVariantRecord::encodeInto(
         vector <Octet> & buffer) const
   {
      map<Integer64, pair<bool, DataElement *> >::const_iterator
            iter = _impl->elements.find(_impl->discriminant->hash());
      if (iter != _impl->elements.end()) {
         Encoder::insertBytes(buffer, Encoder::paddingSizeInBytes(buffer.size(), getOctetBoundary()));
         _impl->discriminant->encodeInto(buffer);  // Discriminant
         // Align for element size
         Encoder::insertBytes(buffer, Encoder::paddingSizeInBytes(buffer.size(), ELEMENT_SIZE_OCTET_BOUNDARY));
         DataElement * variant = iter->second.second;
         Encoder::appendInt(buffer, (Integer32) variant->getEncodedLength()); // Element size
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
   size_t HLAextendableVariantRecord::getEncodedLength() const
   {
      size_t size = _impl->discriminant->getEncodedLength();
      while ((size % ELEMENT_SIZE_OCTET_BOUNDARY) != 0) {
         size += 1;
      }
      size += 4;  // Element size
      map<Integer64, pair<bool, DataElement *> >::const_iterator
            iter = _impl->elements.find(_impl->discriminant->hash());
      if (iter != _impl->elements.end()) {
         // Pad to variant octet boundary
         int octetBoundary = getVariantOctetBoundary(_impl->elements);
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
   HLAextendableVariantRecord & HLAextendableVariantRecord::decode(
         VariableLengthData const & inData)
   {
      vector<Octet> buffer((char *) inData.data(), (char *) inData.data() + inData.size());
      decodeFrom(buffer, 0);
      return *this;
   }

   // Decodes this element starting at the index in the provided buffer
   size_t HLAextendableVariantRecord::decodeFrom(
         vector <Octet> const & buffer,
         size_t startIndex)
   {
      size_t index = startIndex;
      // Skip to discriminant octet boundary
      index += Encoder::paddingSizeInBytes(index, _impl->discriminant->getOctetBoundary());
      // Decode discriminant
      index = _impl->discriminant->decodeFrom(buffer, index);
      // Skip to octet boundary for elementSize
      index += Encoder::paddingSizeInBytes(index, ELEMENT_SIZE_OCTET_BOUNDARY);
      // Decode element size
      if (index + 4 > buffer.size()) {
         throw EncoderException(L"Array index out of bounds");
      }
      size_t elementSize = Encoder::decodeInt(buffer, index);
      index += 4;
      map<Integer64, pair<bool, DataElement *> >::const_iterator
            iter = _impl->elements.find(_impl->discriminant->hash());
      if (iter != _impl->elements.end()) {
         DataElement & variantValue = *(iter->second.second);
         // Skip to variant octet boundary
         index += Encoder::paddingSizeInBytes(index, getVariantOctetBoundary(_impl->elements));
         // Decode variant
         index = variantValue.decodeFrom(buffer, index);
      } else {
         // Skip unknown variant
         index += elementSize;
      }
      return index;
   }

} // RTI_NAMESPACE

#endif // RTI_HLA_VERSION

// NOLINTEND(hicpp-exception-baseclass)
