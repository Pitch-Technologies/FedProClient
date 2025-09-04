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

#include "Encoder.h"
#include "services-common/RTIcompat.h"

#include <RTI/encoding/BasicDataElements.h>
#include <RTI/VariableLengthData.h>

#include <functional>

#if (RTI_HLA_VERSION >= 2025)
#define DECLARE_ENCODING_SET_METHOD(EncodableDataType, SimpleDataType)                        \
EncodableDataType & EncodableDataType::set(SimpleDataType rhs)                                \
{                                                                                             \
   _impl->setValue(rhs);                                                                      \
   return *this;                                                                              \
}
#define DECLARE_ENCODING_DECODE_METHOD(EncodableDataType, SimpleDataType)                     \
EncodableDataType & EncodableDataType::decode(VariableLengthData const & inData)              \
{                                                                                             \
   if (inData.size() < getOctetBoundary()) { /* octet boundary == encoded length for fixed length types, variable length types must use remaining in decodeXXXX */  \
      throw EncoderException(L"Index is out of bounds");                                      \
   }                                                                                          \
   if (_impl->extData) {                                                                      \
      *(_impl->extData) = Encoder::decode##EncodableDataType(inData.data(), inData.size());   \
   } else {                                                                                   \
      _impl->setValue(Encoder::decode##EncodableDataType(inData.data(), inData.size()));      \
   }                                                                                          \
   return *this;                                                                              \
}
#else
#define DECLARE_ENCODING_SET_METHOD(EncodableDataType, SimpleDataType)                        \
void EncodableDataType::set(SimpleDataType rhs)                                               \
{                                                                                             \
   _impl->setValue(rhs);                                                                      \
}
#define DECLARE_ENCODING_DECODE_METHOD(EncodableDataType, SimpleDataType)                     \
/* Decodes this element from the ByteWrapper.                           */                    \
void EncodableDataType::decode(VariableLengthData const & inData)                             \
    RTI_THROW(EncoderException)                                                               \
{                                                                                             \
   if (inData.size() < getOctetBoundary()) { /* octet boundary == encoded length for fixed length types, variable length types must use remaining in decodeXXXX */  \
      throw EncoderException(L"Index is out of bounds");                                      \
   }                                                                                          \
   if (_impl->extData) {                                                                      \
      *(_impl->extData) = Encoder::decode##EncodableDataType(inData.data(), inData.size());   \
   } else {                                                                                   \
      _impl->setValue(Encoder::decode##EncodableDataType(inData.data(), inData.size()));      \
   }                                                                                          \
}
#endif

#define DECLARE_ENCODING_HELPER_CLASS(EncodableDataType, SimpleDataType, initialValue)        \
class EncodableDataType##Implementation {                                                     \
public:                                                                                       \
   EncodableDataType##Implementation()                                                        \
      : internData{initialValue}                                                              \
   {                                                                                          \
   }                                                                                          \
   SimpleDataType * extData{nullptr};                                                         \
   SimpleDataType internData;                                                                 \
                                                                                              \
   SimpleDataType getValue() {                                                                \
      if (extData) {                                                                          \
         return *extData;                                                                     \
      } else {                                                                                \
         return internData;                                                                   \
      }                                                                                       \
   }                                                                                          \
                                                                                              \
   SimpleDataType * getValuePtr() {                                                           \
      if (extData) {                                                                          \
         return extData;                                                                      \
      } else {                                                                                \
         return &internData;                                                                  \
      }                                                                                       \
   }                                                                                          \
                                                                                              \
   void setValue(const SimpleDataType &value) {                                               \
      extData = nullptr;                                                                      \
      internData = value;                                                                     \
   }                                                                                          \
};                                                                                            \
                                                                                              \
                                                                                              \
/* Default Constructor                                                  */                    \
EncodableDataType::EncodableDataType()                                                        \
{                                                                                             \
   _impl = new EncodableDataType##Implementation();                                           \
}                                                                                             \
                                                                                              \
/* Construct with initial value                                         */                    \
/* Uses internal storage, caller is free to delete inData               */                    \
EncodableDataType::EncodableDataType (                                                        \
                     SimpleDataType const & inData)                                           \
{                                                                                             \
   _impl = new EncodableDataType##Implementation();                                           \
   _impl->setValue(inData);                                                                   \
}                                                                                             \
                                                                                              \
/* Construct with pointer to local storage                              */                    \
/* Changes to this instance will be reflected in local storage          */                    \
/* Caller is responsible for ensuring that the data that is             */                    \
/* pointed to is valid for the lifetime of this object, or past         */                    \
/* the next time this object is given new data.                         */                    \
/* A null value will revert instance to use internal storage.           */                    \
EncodableDataType::EncodableDataType (                                                        \
                     SimpleDataType* inData)                                                  \
{                                                                                             \
   _impl = new EncodableDataType##Implementation();                                           \
   _impl->extData = inData;                                                                   \
}                                                                                             \
                                                                                              \
/* Copy uses internal storage, caller is free to delete rhs             */                    \
EncodableDataType::EncodableDataType (                                                        \
                     EncodableDataType const & rhs)                                           \
{                                                                                             \
   _impl = new EncodableDataType##Implementation();                                           \
   _impl->setValue(rhs._impl->getValue());                                                    \
}                                                                                             \
                                                                                              \
EncodableDataType::~EncodableDataType ()                                                      \
{                                                                                             \
   delete _impl;                                                                              \
}                                                                                             \
                                                                                              \
EncodableDataType & EncodableDataType::operator= (                                            \
                     EncodableDataType const & rhs)                                           \
{                                                                                             \
   if (this == &rhs) {                                                                        \
      return *this;                                                                           \
   }                                                                                          \
   _impl->setValue(rhs._impl->getValue());                                                    \
   return *this;                                                                              \
}                                                                                             \
                                                                                              \
/* Caller is free to delete rhs.                                        */                    \
/* Assignment is made to data pointer if set.                           */                    \
EncodableDataType & EncodableDataType::operator= (                                            \
                              SimpleDataType rhs)                                             \
{                                                                                             \
   _impl->setValue(rhs);                                                                      \
   return *this;                                                                              \
}                                                                                             \
                                                                                              \
DECLARE_ENCODING_SET_METHOD(EncodableDataType, SimpleDataType)                                \
                                                                                              \
/* Returns a new copy of the DataElement                                */                    \
RTI_UNIQUE_PTR<DataElement> EncodableDataType::clone () const                                 \
{                                                                                             \
   return make_unique_derived<DataElement, EncodableDataType>(_impl->getValue());             \
}                                                                                             \
                                                                                              \
/* Change the pointer to storage                                        */                    \
/* Changes to this instance will be reflected in pointed to storage     */                    \
/* Caller is responsible for ensuring that the data that is             */                    \
/* pointed to is valid for the lifetime of this object, or past         */                    \
/* the next time this object is given new data.                         */                    \
/* A null value will revert instance to use internal storage.           */                    \
void EncodableDataType::setDataPointer(SimpleDataType* inData)                                \
   RTI_THROW(EncoderException)                                                                \
{                                                                                             \
   _impl->extData = inData;                                                                   \
}                                                                                             \
                                                                                              \
/* Conversion operator to SimpleDataType                           */                         \
EncodableDataType::operator SimpleDataType () const                                           \
{                                                                                             \
   return _impl->getValue();                                                                  \
}                                                                                             \
                                                                                              \
SimpleDataType EncodableDataType::get() const                                                 \
{                                                                                             \
   return _impl->getValue();                                                                  \
}                                                                                             \
                                                                                              \
/* Returns the octet boundary of this element.                          */                    \
unsigned int EncodableDataType::getOctetBoundary () const                                     \
{                                                                                             \
   return Encoder::boundaryof##EncodableDataType();                                           \
}                                                                                             \
                                                                                              \
/* Encodes this element into a new VariableLengthData                   */                    \
VariableLengthData EncodableDataType::encode() const                                          \
   RTI_THROW(EncoderException)                                                                \
{                                                                                             \
   VariableLengthData res;                                                                    \
   int len;                                                                                   \
   void * buf = Encoder::encode##EncodableDataType(_impl->getValue(), len);                   \
   res.setData(buf,len);                                                                      \
   delete [] (char *)buf;                                                                     \
   return res;                                                                                \
}                                                                                             \
                                                                                              \
/* Encodes this element into an existing VariableLengthData             */                    \
void                                                                                          \
EncodableDataType::encode(VariableLengthData& inData) const                                   \
   RTI_THROW(EncoderException)                                                                \
{                                                                                             \
   int len;                                                                                   \
   void * buf = Encoder::encode##EncodableDataType(_impl->getValue(), len);                   \
   inData.setData(buf,len);                                                                   \
   delete [] (char *)buf;                                                                     \
}                                                                                             \
                                                                                              \
/* Encode this element and append it to a buffer                        */                    \
void                                                                                          \
EncodableDataType::encodeInto(std::vector<Octet>& buffer) const                               \
   RTI_THROW(EncoderException)                                                                \
{                                                                                             \
   /* buffer.align(getOctetBoundary());*/                                                     \
   size_t size = buffer.size();                                                               \
   size_t paddingSize = Encoder::paddingSizeInBytes(size, getOctetBoundary());                \
   size += paddingSize;                                                                       \
   buffer.resize(size + getEncodedLength());                                                  \
   Encoder::encode##EncodableDataType(_impl->getValue(), &buffer[size]);                      \
}                                                                                             \
                                                                                              \
/* Returns the size in bytes of this element's encoding.                */                    \
size_t EncodableDataType::getEncodedLength () const                                           \
   RTI_THROW(EncoderException)                                                                \
{                                                                                             \
   return Encoder::sizeof##EncodableDataType(_impl->getValue());                              \
}                                                                                             \
                                                                                              \
/* Decodes this element from the ByteWrapper.                           */                    \
DECLARE_ENCODING_DECODE_METHOD(EncodableDataType, SimpleDataType)                             \
                                                                                              \
/* Decodes this element starting at the index in the provided buffer    */                    \
size_t                                                                                        \
EncodableDataType::decodeFrom (                                                               \
                    std::vector<Octet> const & buffer,                                        \
                    size_t index)                                                             \
                    RTI_THROW(EncoderException)                                               \
{                                                                                             \
   if (index >= buffer.size()) {                                                              \
      throw EncoderException(std::wstring(L"Index is out of bounds"));                        \
   }                                                                                          \
   const char *data = &buffer.at(index);                                                      \
   size_t remaining = buffer.size() - index;                                                  \
   if (remaining < getOctetBoundary()) { /* octet boundary == encoded length for fixed length types, variable length types must use remaining in decodeXXXX */  \
      throw EncoderException(L"Index is out of bounds");                                      \
   }                                                                                          \
   if (_impl->extData) {                                                                      \
      *(_impl->extData) = Encoder::decode##EncodableDataType(data, remaining);                \
   } else {                                                                                   \
      _impl->setValue(Encoder::decode##EncodableDataType(data, remaining));                   \
   }                                                                                          \
   return index + getEncodedLength();                                                         \
}                                                                                             \
                                                                                              \
Integer64 EncodableDataType::hash() const                                                     \
{                                                                                             \
   return std::hash<SimpleDataType>{}(_impl->getValue());                                     \
}

namespace std {
   template <>
   struct hash<RTI_NAMESPACE::OctetPair>
   {
      size_t operator()(const RTI_NAMESPACE::OctetPair & octetPair) const
      {
         std::hash<uint16_t> hasher;
         return hasher((octetPair.first << 8) + octetPair.second);
      }
   };
}

namespace RTI_NAMESPACE
{
   DECLARE_ENCODING_HELPER_CLASS(HLAASCIIchar, char, 0)
   DECLARE_ENCODING_HELPER_CLASS(HLAASCIIstring, std::string, {})
   DECLARE_ENCODING_HELPER_CLASS(HLAboolean, bool, false)
   DECLARE_ENCODING_HELPER_CLASS(HLAbyte, Octet, 0)
   DECLARE_ENCODING_HELPER_CLASS(HLAfloat32BE, float, 0.0f)
   DECLARE_ENCODING_HELPER_CLASS(HLAfloat32LE, float, 0.0f)
   DECLARE_ENCODING_HELPER_CLASS(HLAfloat64BE, double, 0.0f)
   DECLARE_ENCODING_HELPER_CLASS(HLAfloat64LE, double, 0.0f)
   DECLARE_ENCODING_HELPER_CLASS(HLAinteger16LE, Integer16, 0)
   DECLARE_ENCODING_HELPER_CLASS(HLAinteger16BE, Integer16, 0)
   DECLARE_ENCODING_HELPER_CLASS(HLAinteger32BE, Integer32, 0)
   DECLARE_ENCODING_HELPER_CLASS(HLAinteger32LE, Integer32, 0)
   DECLARE_ENCODING_HELPER_CLASS(HLAinteger64BE, Integer64, 0)
   DECLARE_ENCODING_HELPER_CLASS(HLAinteger64LE, Integer64, 0)
#if (RTI_HLA_VERSION >= 2025)
   DECLARE_ENCODING_HELPER_CLASS(HLAunsignedInteger16LE, UnsignedInteger16, 0)
   DECLARE_ENCODING_HELPER_CLASS(HLAunsignedInteger16BE, UnsignedInteger16, 0)
   DECLARE_ENCODING_HELPER_CLASS(HLAunsignedInteger32BE, UnsignedInteger32, 0)
   DECLARE_ENCODING_HELPER_CLASS(HLAunsignedInteger32LE, UnsignedInteger32, 0)
   DECLARE_ENCODING_HELPER_CLASS(HLAunsignedInteger64BE, UnsignedInteger64, 0)
   DECLARE_ENCODING_HELPER_CLASS(HLAunsignedInteger64LE, UnsignedInteger64, 0)
#endif
   DECLARE_ENCODING_HELPER_CLASS(HLAoctet, Octet, 0)
   DECLARE_ENCODING_HELPER_CLASS(HLAoctetPairBE, OctetPair, OctetPair(0, 0))
   DECLARE_ENCODING_HELPER_CLASS(HLAoctetPairLE, OctetPair, OctetPair(0, 0))
   DECLARE_ENCODING_HELPER_CLASS(HLAunicodeChar, wchar_t, 0)
   DECLARE_ENCODING_HELPER_CLASS(HLAunicodeString, std::wstring, std::wstring{})
} // RTI_NAMESPACE
