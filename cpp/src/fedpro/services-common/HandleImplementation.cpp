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

#include "HandleImplementation.h"

#include "RegionHandleImplementation.h"
#include "RTIcompat.h"

#include <RTI/Handle.h>
#include <RTI/VariableLengthData.h>

#include <cstring>
#include <iostream>
#include <sstream>


#define DECLARE_HANDLE_IMPLEMENTATION(HandleKind)                 \
class HandleKind##Implementation : public HandleImplementation    \
{                                                                 \
public:                                                           \
   HandleKind##Implementation() = default;                        \
   HandleKind##Implementation(                                    \
      const HandleKind##Implementation &) = default;              \
   HandleKind##Implementation(                                    \
      HandleKind##Implementation &&) = default;                   \
                                                                  \
   HandleKind##Implementation& operator=(                         \
      const HandleKind##Implementation &) = default;              \
   HandleKind##Implementation& operator=(                         \
      HandleKind##Implementation &&) = default;                   \
};


#define IMPLEMENT_HANDLE_CLASS(HandleKind)                        \
   /* Constructs an invalid handle                           */   \
   HandleKind::HandleKind () :                                    \
      _impl(new HandleKind##Implementation) {                     \
   }                                                              \
                                                                  \
   HandleKind::HandleKind(HandleKind##Implementation* impl)  :    \
      _impl(new HandleKind##Implementation(*impl)) {              \
   }                                                              \
                                                                  \
   HandleKind::~HandleKind () noexcept {                          \
      delete _impl;                                               \
   }                                                              \
                                                                  \
   HandleKind::HandleKind(HandleKind const & rhs) :               \
      HandleKind(rhs._impl) {                                     \
   }                                                              \
                                                                  \
   HandleKind &                                                   \
   HandleKind::operator= (HandleKind const & rhs) {               \
      if (this != &rhs) {                                         \
         *_impl = *rhs._impl;                                     \
      }                                                           \
      return *this;                                               \
   }                                                              \
                                                                  \
   /* Indicates whether this handle is valid                 */   \
   bool                                                           \
   HandleKind::isValid () const {                                 \
      return _impl->isValid();                                    \
   }                                                              \
                                                                  \
   /* All invalid handles are equivalent                     */   \
   bool                                                           \
   HandleKind::operator== (                                       \
   HandleKind const & rhs) const {                                \
      return *_impl == *rhs._impl;                                \
   }                                                              \
                                                                  \
   bool                                                           \
   HandleKind::operator!= (                                       \
   HandleKind const & rhs) const {                                \
      return *_impl != *rhs._impl;                                \
   }                                                              \
                                                                  \
   bool                                                           \
   HandleKind::operator< (                                        \
   HandleKind const & rhs) const {                                \
      return *_impl < *rhs._impl;                                 \
   }                                                              \
                                                                  \
   /* Generate a hash value for use in storing handles in a  */   \
   /* in a hash table.                                       */   \
   /* Note: The hash value may not be unique across two      */   \
   /* separate handle values but it will be equal given two  */   \
   /* separate instances of the same handle value.           */   \
   /* H1 == H2 implies H1.hash() == H2.hash()                */   \
   /* H1 != H2 does not imply H1.hash() != H2.hash()         */   \
   long                                                           \
   HandleKind::hash () const {                                    \
      return _impl->hash();                                       \
   }                                                              \
                                                                  \
   /* Generate an encoded value that can be used to send     */   \
   /* handles to other federates in updates or interactions. */   \
   VariableLengthData                                             \
   HandleKind::encode () const {                                  \
      const std::string & encodedData = _impl->getData();         \
      return {encodedData.data(), encodedData.size()};            \
   }                                                              \
                                                                  \
   /* Alternate encode for directly filling a buffer         */   \
   size_t                                                         \
   HandleKind::encodedLength () const {                           \
      return _impl->length();                                     \
   }                                                              \
                                                                  \
   size_t                                                         \
   HandleKind::encode (                                           \
      void* buffer,                                               \
      size_t bufferSize) const                                    \
      RTI_THROW(CouldNotEncode)                                   \
   {                                                              \
      return _impl->encode(buffer, bufferSize);                   \
   }                                                              \
                                                                  \
   std::wstring                                                   \
   HandleKind::toString () const {                                \
      std::wstringstream res;                                     \
      res << *_impl;                                              \
      return res.str();                                           \
   }                                                              \
                                                                  \
                                                                  \
   const HandleKind##Implementation*                              \
   HandleKind::getImplementation () const {                       \
      return _impl;                                               \
   }                                                              \
                                                                  \
   HandleKind##Implementation*                                    \
   HandleKind::getImplementation () {                             \
      return _impl;                                               \
   }                                                              \
                                                                  \
                                                                  \
   HandleKind::HandleKind (                                       \
   VariableLengthData const & encodedValue) :                     \
      HandleKind() {                                              \
      _impl->setData(encodedValue.data(), encodedValue.size());   \
   }                                                              \
                                                                  \
   /* Output operator for Handles                               */\
   std::wostream &                                                \
   operator<< (                                                   \
      std::wostream &str,                                         \
      HandleKind const &handle) {                                 \
      const HandleImplementation *impl = getImplementation(handle); \
      str << L"HandleKind<" << *impl <<L">";                      \
      return str;                                                 \
   }                                                              \
                                                                  \
   class HandleKind##Friend {                                     \
   public:                                                        \
      using Impl = HandleKind##Implementation;                    \
      static HandleKind make_handle(Impl * handleImpl) {          \
         return HandleKind(handleImpl);                           \
      }                                                           \
      static HandleKind make_handle(std::string && encodedData) { \
         HandleKind handle;                                       \
         auto handleImpl = handle.getImplementation();            \
         handleImpl->setData(std::move(encodedData));             \
         return handle;                                           \
      }                                                           \
      static const Impl * getImplementation(const HandleKind & handle) \
      {                                                           \
         return handle.getImplementation();                       \
      }                                                           \
      static Impl * getImplementation(HandleKind & handle)        \
      {                                                           \
         return handle.getImplementation();                       \
      }                                                           \
   };                                                             \
                                                                  \
   template<>                                                     \
   HandleKind make_handle<HandleKind>(std::string && buffer)      \
   {                                                              \
       return HandleKind##Friend::make_handle(std::move(buffer)); \
   }                                                              \
                                                                  \
   std::string serialize(const HandleKind & handle) {             \
      const HandleImplementation * impl = getImplementation(handle); \
      return impl->getData();                                     \
   }                                                              \
                                                                  \
   const HandleImplementation * getImplementation(const HandleKind & handle) \
   {                                                              \
      return HandleKind##Friend::getImplementation(handle);       \
   }                                                              \
                                                                  \
   HandleImplementation * getImplementation(HandleKind & handle)  \
   {                                                              \
       return HandleKind##Friend::getImplementation(handle);      \
   }


namespace RTI_NAMESPACE
{
   HandleImplementation::HandleImplementation() = default;

   HandleImplementation::HandleImplementation(std::string && encodedData) : _encodedHandle(encodedData)
   {
   }

   HandleImplementation::~HandleImplementation() = default;

   void HandleImplementation::setData(std::string && encodedData)
   {
      _encodedHandle = std::move(encodedData);
   }

   void HandleImplementation::setData(
         const void * encodedData,
         size_t encodedDataLength)
   {
      const char * encodedDataBytes = reinterpret_cast<const char *>(encodedData);
      _encodedHandle.assign(encodedDataBytes, encodedDataLength);
   }

   const std::string & HandleImplementation::getData() const
   {
      return _encodedHandle;
   }

   size_t HandleImplementation::encode(
         void * buffer,
         size_t bufferSize) const
   {
      const size_t encodedDataLength = length();
      if (bufferSize < encodedDataLength) {
         throw RTI_NAMESPACE::CouldNotEncode(
               L"Could not encode a Handle of length " + std::to_wstring(encodedDataLength)
               + L" into a buffer of length " + std::to_wstring(bufferSize));
      }

      memcpy(buffer, _encodedHandle.data(), encodedDataLength);
      return encodedDataLength;
   }

   size_t HandleImplementation::length() const
   {
      return _encodedHandle.length();
   }

   bool HandleImplementation::isValid() const
   {
      return !_encodedHandle.empty();
   }

   long HandleImplementation::hash () const
   {
      std::size_t fullHash = std::hash<std::string>{}(_encodedHandle);
      // This cast may discard bits. This is unavoidable to satisfy RTI API.
      return static_cast<long>(fullHash);
   }

   std::ostream& operator<<(std::ostream& os, const HandleImplementation& handleImpl)
   {
      os << std::hex << handleImpl.getData();
      return os;
   }

   std::wostream& operator<<(std::wostream& os, const HandleImplementation& handleImpl)
   {
      for (auto c : handleImpl.getData()){
         os << std::hex << c;
      }
      return os;
   }

   // Handle classes are implemented by invoking the macro above.

   DECLARE_HANDLE_IMPLEMENTATION(FederateHandle)
   IMPLEMENT_HANDLE_CLASS(FederateHandle)

   DECLARE_HANDLE_IMPLEMENTATION(ObjectClassHandle)
   IMPLEMENT_HANDLE_CLASS(ObjectClassHandle)

   DECLARE_HANDLE_IMPLEMENTATION(InteractionClassHandle)
   IMPLEMENT_HANDLE_CLASS(InteractionClassHandle)

   DECLARE_HANDLE_IMPLEMENTATION(ObjectInstanceHandle)
   IMPLEMENT_HANDLE_CLASS(ObjectInstanceHandle)

   DECLARE_HANDLE_IMPLEMENTATION(AttributeHandle)
   IMPLEMENT_HANDLE_CLASS(AttributeHandle)

   DECLARE_HANDLE_IMPLEMENTATION(ParameterHandle)
   IMPLEMENT_HANDLE_CLASS(ParameterHandle)

   DECLARE_HANDLE_IMPLEMENTATION(DimensionHandle)
   IMPLEMENT_HANDLE_CLASS(DimensionHandle)

   DECLARE_HANDLE_IMPLEMENTATION(MessageRetractionHandle)
   IMPLEMENT_HANDLE_CLASS(MessageRetractionHandle)

   // RegionHandleImplementation is located in RegionHandleImplementation.cpp.h
   IMPLEMENT_HANDLE_CLASS(RegionHandle)

   DECLARE_HANDLE_IMPLEMENTATION(TransportationTypeHandle)
   IMPLEMENT_HANDLE_CLASS(TransportationTypeHandle)
}