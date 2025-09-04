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

#include "services-common/RTIcompat.h"
#include "utility/bit.h"

#include <RTI/time/HLAinteger64Time.h>

#include <cstring>
#include <limits>
#include <sstream>

namespace RTI_NAMESPACE
{
   static const Integer64 INITIAL_VALUE = 0;
   static const Integer64 FINAL_VALUE = std::numeric_limits<Integer64>::max();

   class HLAinteger64TimeImpl
   {

   public:
      Integer64 _time;
   };

   HLAinteger64Time::HLAinteger64Time()
   {
      _impl = new HLAinteger64TimeImpl();
      _impl->_time = 0;
   }

   HLAinteger64Time::HLAinteger64Time(
         Integer64 value)
   {
      _impl = new HLAinteger64TimeImpl();
      _impl->_time = value;

   }

   HLAinteger64Time::HLAinteger64Time(
         LogicalTime const & value)
   {
      _impl = new HLAinteger64TimeImpl();
      _impl->_time = dynamic_cast<const HLAinteger64Time &>(value)._impl->_time;

   }

   HLAinteger64Time::HLAinteger64Time(
         HLAinteger64Time const & value)
   {
      _impl = new HLAinteger64TimeImpl();
      _impl->_time = value._impl->_time;
   }

   HLAinteger64Time::~HLAinteger64Time() noexcept
   {
      delete _impl;
   }

   void HLAinteger64Time::setInitial()
   {
      _impl->_time = INITIAL_VALUE;
   }

   bool HLAinteger64Time::isInitial() const
   {
      return _impl->_time == INITIAL_VALUE;
   }

   void HLAinteger64Time::setFinal()
   {
      _impl->_time = FINAL_VALUE;
   }

   bool HLAinteger64Time::isFinal() const
   {
      return _impl->_time == FINAL_VALUE;
   }

#if (RTI_HLA_VERSION >= 2025)
   HLAinteger64Time &
#else
   LogicalTime &
#endif
   HLAinteger64Time::operator=(
         LogicalTime const & value) RTI_THROW(InvalidLogicalTime)
   {
      if (this == &value) {
         return *this;
      }
      _impl->_time = dynamic_cast<const HLAinteger64Time &>(value)._impl->_time;
      return *this;
   }

   LogicalTime & HLAinteger64Time::operator+=(
         LogicalTimeInterval const & addend) RTI_THROW(IllegalTimeArithmetic, InvalidLogicalTimeInterval)
   {
      // This cast will throw a C++ exception with description "Bad dynamic_cast!"
      // if the addend is of the wrong type
      _impl->_time += dynamic_cast<const HLAinteger64Interval &>(addend).getInterval();
      return *this;
   }

   LogicalTime & HLAinteger64Time::operator-=(
         LogicalTimeInterval const & subtrahend) RTI_THROW(IllegalTimeArithmetic, InvalidLogicalTimeInterval)
   {
      _impl->_time -= dynamic_cast<const HLAinteger64Interval &>(subtrahend).getInterval();
      return *this;

   }

   bool HLAinteger64Time::operator>(
         LogicalTime const & value) const RTI_THROW(InvalidLogicalTime)
   {
      return _impl->_time > dynamic_cast<const HLAinteger64Time &>(value)._impl->_time;
   }


   bool HLAinteger64Time::operator<(
         LogicalTime const & value) const RTI_THROW(InvalidLogicalTime)
   {
      return _impl->_time < dynamic_cast<const HLAinteger64Time &>(value)._impl->_time;

   }

   bool HLAinteger64Time::operator==(
         LogicalTime const & value) const RTI_THROW(InvalidLogicalTime)
   {
      auto other = dynamic_cast<const HLAinteger64Time *>(&value);
      if (other == nullptr) {
         return false;
      }
      return _impl->_time == other->_impl->_time;

   }

   bool HLAinteger64Time::operator>=(
         LogicalTime const & value) const RTI_THROW(InvalidLogicalTime)
   {
      return _impl->_time >= dynamic_cast<const HLAinteger64Time &>(value)._impl->_time;
   }

   bool HLAinteger64Time::operator<=(
         LogicalTime const & value) const RTI_THROW(InvalidLogicalTime)
   {
      return _impl->_time <= dynamic_cast<const HLAinteger64Time &>(value)._impl->_time;
   }

   VariableLengthData HLAinteger64Time::encode() const
   {
      auto bigTime = FedPro::nativeToBigEndian<Integer64>(_impl->_time);
      VariableLengthData vld(&bigTime, sizeof(bigTime));
      return vld;
   }

   size_t HLAinteger64Time::encode(
         void * buffer,
         size_t bufferSize) const RTI_THROW(CouldNotEncode)
   {
      if (encodedLength() > bufferSize) {
         throw CouldNotEncode(L"Buffer too small!");
      }

      auto bigTime = FedPro::nativeToBigEndian<Integer64>(_impl->_time);
      memcpy(buffer, &bigTime, sizeof(bigTime));
      return sizeof(bigTime);
   }

   size_t HLAinteger64Time::encodedLength() const
   {
      return sizeof(Integer64);
   }

   void HLAinteger64Time::decode(
         VariableLengthData const & VariableLengthData) RTI_THROW(InternalError, CouldNotDecode)
   {
      if (encodedLength() > VariableLengthData.size()) {
         throw CouldNotDecode(L"Buffer too small!");
      }

      _impl->_time = FedPro::bigEndianToNative<Integer64>(VariableLengthData.data(), VariableLengthData.size());
   }

   void HLAinteger64Time::decode(
         RTI_IF_HLA_VERSION_2025_OR_LATER(const) void * buffer,
         size_t bufferSize) RTI_THROW(InternalError, CouldNotDecode)
   {
      if (encodedLength() > bufferSize) {
         throw CouldNotDecode(L"Buffer too small!");
      }

      _impl->_time = FedPro::bigEndianToNative<Integer64>(buffer, bufferSize);
   }

   std::wstring HLAinteger64Time::toString() const
   {
      std::wstringstream res;
      res << L"HLAinteger64Time<" << _impl->_time << L">";
      return res.str();
   }

   std::wstring HLAinteger64Time::implementationName() const
   {
      return L"HLAinteger64Time";
   }

   Integer64 HLAinteger64Time::getTime() const
   {
      return _impl->_time;
   }

   void HLAinteger64Time::setTime(
         Integer64 value)
   {
      _impl->_time = value;
   }

   HLAinteger64Time & HLAinteger64Time::operator=(
         const HLAinteger64Time & rhs) RTI_THROW(InvalidLogicalTime)
   {
      if (this == &rhs) {
         return *this;
      }
      _impl->_time = rhs._impl->_time;
      return *this;
   }

   HLAinteger64Time::operator Integer64() const
   {
      return _impl->_time;
   }

} // RTI_NAMESPACE

