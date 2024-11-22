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

#if (RTI_HLA_VERSION >= 2024)

#include "Encoder.h"

#include <memory>
#include <vector>
#include <typeinfo>

#include <RTI/encoding/EncodingConfig.h>
#include <RTI/encoding/HLAlogicalTime.h>
#include <RTI/RTIambassador.h>
#include <RTI/time/LogicalTime.h>
#include <RTI/time/LogicalTimeFactory.h>
#include <RTI/VariableLengthData.h>

using namespace std;

namespace RTI_NAMESPACE
{
   class HLAlogicalTimeImplementation
   {
   public:
      explicit HLAlogicalTimeImplementation(const RTIambassador * ambassador)
            : _ambassador(ambassador)
      {
         _theTime = _ambassador->getTimeFactory()->makeInitial();
      }

      unique_ptr <LogicalTime> _theTime;
      const RTIambassador * _ambassador;

      ~HLAlogicalTimeImplementation() = default;
   };

   HLAlogicalTime::HLAlogicalTime(HLAlogicalTime const & rhs)
   {
      _impl = new HLAlogicalTimeImplementation(rhs._impl->_ambassador);
      *_impl->_theTime = *rhs._impl->_theTime;
   }

   HLAlogicalTime::HLAlogicalTime(const RTIambassador * ambassador)
   {
      _impl = new HLAlogicalTimeImplementation(ambassador);
   }

   HLAlogicalTime::HLAlogicalTime(
         const RTIambassador * ambassador,
         const LogicalTime & logicalTime)
   {
      _impl = new HLAlogicalTimeImplementation(ambassador);
      *_impl->_theTime = logicalTime;
   }

   HLAlogicalTime & HLAlogicalTime::set(const LogicalTime & value)
   {
      *_impl->_theTime = value;
      return *this;
   }

   void HLAlogicalTime::get(LogicalTime & theTime)
   {
      theTime = *_impl->_theTime;
   }

   HLAlogicalTime::~HLAlogicalTime()
   {
      delete _impl;
   }

   // Returns a new copy of the DataElement
   // PS: Deep copy assumed
   std::unique_ptr<DataElement> HLAlogicalTime::clone() const
   {
      return std::make_unique<HLAlogicalTime>(*this);
   }

   // Return true if given element is same type as this; otherwise, false.
   bool HLAlogicalTime::isSameTypeAs(const DataElement & other) const
   {
      return typeid(*this) == typeid(other);
   }

   // Returns the octet boundary of this element.
   unsigned int HLAlogicalTime::getOctetBoundary() const
   {
      unsigned int boundary = 4;
      return boundary;
   }

   // Encodes this element into a new VariableLengthData
   VariableLengthData HLAlogicalTime::encode() const
   {
      VariableLengthData res;
      encode(res);
      return res;
   }

   // Encodes this element into an existing VariableLengthData
   void HLAlogicalTime::encode(
         VariableLengthData & inData) const
   {
      size_t length = getEncodedLength();
      vector<Octet> wrapper;
      encodeInto(wrapper);
      inData.setData(&wrapper[0], length);
   }

   // Encode this element and append it to a buffer
   void HLAlogicalTime::encodeInto(
         vector <Octet> & buffer) const
   {
      Encoder::insertBytes(buffer, Encoder::paddingSizeInBytes(buffer.size(), getOctetBoundary()));
      Encoder::appendInt(buffer, (Integer32) _impl->_theTime->encodedLength());
      VariableLengthData encodedTime = _impl->_theTime->encode();
      Encoder::appendBytes(buffer, static_cast<char const *>(encodedTime.data()), encodedTime.size());
   }

   // Returns the size in bytes of this element's encoding.
   size_t HLAlogicalTime::getEncodedLength() const
   {
      return 4 + _impl->_theTime->encodedLength();
   }

   // Decodes this element from the ByteWrapper.
   HLAlogicalTime & HLAlogicalTime::decode(
         VariableLengthData const & inData)
   {
      vector<Octet> buffer((char *) inData.data(), (char *) inData.data() + inData.size());
      decodeFrom(buffer, 0);
      return *this;
   }

   // Decodes this element starting at the index in the provided buffer
   size_t HLAlogicalTime::decodeFrom(
         vector <Octet> const & buffer,
         size_t startIndex)
   {
      size_t index = startIndex;
      if (index + 4 > buffer.size()) {
         throw EncoderException(L"HLAlogicalTime cannot decode (indata too short)");
      }
      size_t len = Encoder::decodeInt(buffer, index);
      index += 4;
      if (len > buffer.size()) {
         // Check before adding index since len 0xffffffff will wrap around and fail the test
         throw EncoderException(L"HLAlogicalTime cannot decode (indata too short)");
      }
      if (index + len > buffer.size()) {
         throw EncoderException(L"HLAlogicalTime cannot decode (indata too short)");
      }
      _impl->_theTime->decode((void *) &buffer[index], len);
      index += len;
      return index;
   }

} // RTI_NAMESPACE

#endif // RTI_HLA_VERSION

// NOLINTEND(hicpp-exception-baseclass)
