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

#include <RTI/encoding/HLAopaqueData.h>
#include <RTI/VariableLengthData.h>

#include <cstring>

namespace RTI_NAMESPACE
{
   static char EMPTY_ARRAY[] = {0x00};

   class HLAopaqueDataImplementation
   {
   public:
      HLAopaqueDataImplementation()
            : buffer_len(0),
              data_len(0),
              data(nullptr),
              isMemoryOwner(true)
      {
      }

      // Caller is free to delete inData after the call
      HLAopaqueDataImplementation(
            void const * inData,
            size_t inSize)
            : buffer_len(inSize),
              data_len(inSize),
              data(new char[inSize]),
              isMemoryOwner(true)
      {
         memcpy(data, inData, inSize);
      }

      // Construct with pointer to storage
      // Changes to this instance will be reflected in local storage
      // Caller is responsible for ensuring that the data that is
      // pointed to is valid for the lifetime of this object, or past
      // the next time this object is given new data.
      // A null value will revert instance to use internal storage
      HLAopaqueDataImplementation(
            Octet ** inData,
            size_t bufferSize,
            size_t dataSize)
            : buffer_len(bufferSize),
              data_len(dataSize),
              data(*inData),
              isMemoryOwner(false)
      {
      }

      ~HLAopaqueDataImplementation()
      {
         if (isMemoryOwner) {
            delete[] (char *) data;
         }
      }

      void * data;
      size_t buffer_len;
      size_t data_len;
      bool isMemoryOwner;
   };

   // Default Constructor
   HLAopaqueData::HLAopaqueData()
         : _impl(new HLAopaqueDataImplementation())
   {
   }

   // Construct from a simple array
   HLAopaqueData::HLAopaqueData(
         const Octet * inDataBuffer,
         size_t bufferSize)
         : _impl(new HLAopaqueDataImplementation(inDataBuffer, bufferSize))
   {
   }

   // Construct with pointer to storage
   // Changes to this instance will be reflected in local storage
   // Caller is responsible for ensuring that the data that is
   // pointed to is valid for the lifetime of this object, or past
   // the next time this object is given new data.
   // A null value will revert instance to use internal storage
   HLAopaqueData::HLAopaqueData(
         Octet ** inData,
         size_t bufferSize,
         size_t dataSize) RTI_THROW(EncoderException)
         : _impl(new HLAopaqueDataImplementation(inData, bufferSize, dataSize))
   {
   }

   // Copy uses internal storage, caller is free to delete rhs
   HLAopaqueData::HLAopaqueData(
         HLAopaqueData const & rhs)
         : _impl(new HLAopaqueDataImplementation(rhs._impl->data, rhs._impl->data_len))
   {
   }

   // Caller is free to delete rhs.
   HLAopaqueData::~HLAopaqueData()
   {
      delete _impl;
   }

   // Returns a new copy of the DataElement
   RTI_UNIQUE_PTR<DataElement> HLAopaqueData::clone() const
   {
      return make_unique_derived<DataElement, HLAopaqueData>(*this);
   }

   // Change the pointer to storage
   // Changes to this instance will be reflected in local storage
   // Caller is responsible for ensuring that the data that is
   // pointed to is valid for the lifetime of this object, or past
   // the next time this object is given new data.
   // A null value will revert instance to use internal storage
#if (RTI_HLA_VERSION >= 2024)
   HLAopaqueData &
#else
   void
#endif
   HLAopaqueData::setDataPointer(
         Octet ** inDataBuffer,
         size_t bufferSize,
         size_t dataSize) RTI_THROW(EncoderException)
   {
      delete _impl;
      _impl = new HLAopaqueDataImplementation(inDataBuffer, bufferSize, dataSize);
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Returns the length of the contained array
   size_t HLAopaqueData::bufferLength() const
   {
      return _impl->buffer_len;
   }

   // Returns the length of the contained array
   size_t HLAopaqueData::dataLength() const
   {
      return _impl->data_len;
   }

   // Returns a pointer to the contained array
   const Octet * HLAopaqueData::get() const
   {
      return static_cast<const Octet *>(_impl->data);
   }

#if (RTI_HLA_VERSION >= 2024)
   HLAopaqueData &
#else
   void
#endif
   HLAopaqueData::set(
         const Octet * inData,
         size_t dataSize)
   {
      delete _impl;
      _impl = new HLAopaqueDataImplementation(inData, dataSize);
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Returns the octet boundary of this element.
   unsigned int HLAopaqueData::getOctetBoundary() const
   {
      return 4;
   }

   // Encodes this element into a new VariableLengthData
   VariableLengthData HLAopaqueData::encode() const RTI_THROW(EncoderException)
   {
      VariableLengthData res;
      encode(res);
      return res;
   }

   // Encodes this element into an existing VariableLengthData
   void HLAopaqueData::encode(
         VariableLengthData & inData) const RTI_THROW(EncoderException)
   {
      std::vector<Octet> buffer;
      encodeInto(buffer);
      inData.setData(&buffer[0], getEncodedLength());
   }

   // Encode this element and append it to a buffer
   void HLAopaqueData::encodeInto(std::vector<Octet> & buffer) const RTI_THROW(EncoderException)
   {
      Encoder::insertBytes(buffer, Encoder::paddingSizeInBytes(buffer.size(), getOctetBoundary()));
      Encoder::appendInt(buffer, (Integer32) _impl->data_len);
      Encoder::appendBytes(buffer, (char *) _impl->data, _impl->data_len);
   }

   // Returns the size in bytes of this element's encoding.
   size_t HLAopaqueData::getEncodedLength() const RTI_THROW(EncoderException)
   {
      return 4 + _impl->data_len;
   }

   // Decodes this element from the ByteWrapper.
#if (RTI_HLA_VERSION >= 2024)
   HLAopaqueData &
#else
   void
#endif
   HLAopaqueData::decode(
         VariableLengthData const & inData) RTI_THROW(EncoderException)
   {
      std::vector<Octet> buffer((char *) inData.data(), (char *) inData.data() + inData.size());
      decodeFrom(buffer, 0);
#if (RTI_HLA_VERSION >= 2024)
      return *this;
#endif
   }

   // Decodes this element starting at the index in the provided buffer
   size_t HLAopaqueData::decodeFrom(
         std::vector<Octet> const & buffer,
         size_t index) RTI_THROW(EncoderException)
   {
      if (index + 4 > buffer.size()) {
         throw EncoderException(L"HLAopaqueData cannot decode (indata too short)");
      }
      size_t len = Encoder::decodeInt(buffer, index);
      index += 4;
      if (len > buffer.size()) {
         // Check before adding index since len 0xffffffff will wrap around and fail the test
         throw EncoderException(L"Array index out of bounds");
      }
      if (index + len > buffer.size()) {
         throw EncoderException(L"Array index out of bounds");
      }
      if (_impl->isMemoryOwner) {
         delete _impl;
         if (len == 0) {
            // Prevent attempt to reference data beyond buffer
            _impl = new HLAopaqueDataImplementation(EMPTY_ARRAY, len);
         } else {
            _impl = new HLAopaqueDataImplementation(&buffer[index], len);
         }
      } else {
         // Decode into external memory
         if (len > _impl->buffer_len) {
            throw EncoderException(L"Insufficient external storage");
         }
         if (len != 0) {
            memcpy(_impl->data, &buffer[index], len);
         }
         _impl->data_len = len;
      }
      index += len;
      return index;
   }

} // RTI_NAMESPACE

// NOLINTEND(hicpp-exception-baseclass)