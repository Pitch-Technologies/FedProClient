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

#include <RTI/time/HLAinteger64Interval.h>
#include <RTI/time/HLAinteger64Time.h>

#include <cstring>
#include <limits>
#include <stdexcept>
#include <sstream>

namespace RTI_NAMESPACE
{
//TODO: Check for and throw IllegalTimeArithmetic, InvalidLogicalTimeInterval and InvalidLogicalTime.

   class HLAinteger64IntervalImpl
   {
   public:
      Integer64 _time;
   };

   HLAinteger64Interval::HLAinteger64Interval()
   {
      _impl = new HLAinteger64IntervalImpl();
      _impl->_time = 0;
   }

   HLAinteger64Interval::HLAinteger64Interval(
         Integer64 value)
   {
      _impl = new HLAinteger64IntervalImpl();
      _impl->_time = value;

   }

   HLAinteger64Interval::HLAinteger64Interval(
         LogicalTimeInterval const & value)
   {
      _impl = new HLAinteger64IntervalImpl();
      _impl->_time = dynamic_cast<const HLAinteger64Interval &>(value)._impl->_time;

   }

   HLAinteger64Interval::HLAinteger64Interval(
         HLAinteger64Interval const & value)
   {
      _impl = new HLAinteger64IntervalImpl();
      _impl->_time = value._impl->_time;
   }

   HLAinteger64Interval::~HLAinteger64Interval() RTI_NOEXCEPT
   {
      delete _impl;
   }

   void HLAinteger64Interval::setZero()
   {
      _impl->_time = 0;
   }

   bool HLAinteger64Interval::isZero() const
   {
      return _impl->_time == 0;
   }

   void HLAinteger64Interval::setEpsilon()
   {
      _impl->_time = 1;
   }

   bool HLAinteger64Interval::isEpsilon() const
   {
      return _impl->_time == 1;
   }

#if (RTI_HLA_VERSION >= 2024)
   HLAinteger64Interval &
#else
   LogicalTimeInterval &
#endif
   HLAinteger64Interval::operator=(
         LogicalTimeInterval const & value) RTI_THROW(InvalidLogicalTimeInterval)
   {
      if (this == &value) {
         return *this;
      }
      _impl->_time = dynamic_cast<const HLAinteger64Interval &>(value)._impl->_time;
      return *this;
   }

   LogicalTimeInterval & HLAinteger64Interval::operator+=(
         LogicalTimeInterval const & addend) RTI_THROW(IllegalTimeArithmetic, InvalidLogicalTimeInterval)
   {
      _impl->_time += dynamic_cast<const HLAinteger64Interval &>(addend).getInterval();
      return *this;
   }

   LogicalTimeInterval & HLAinteger64Interval::operator-=(
         LogicalTimeInterval const & subtrahend) RTI_THROW(IllegalTimeArithmetic, InvalidLogicalTimeInterval)
   {
      _impl->_time -= dynamic_cast<const HLAinteger64Interval &>(subtrahend).getInterval();
      return *this;

   }

   bool HLAinteger64Interval::operator>(
         LogicalTimeInterval const & value) const RTI_THROW(InvalidLogicalTimeInterval)
   {
      return _impl->_time > dynamic_cast<const HLAinteger64Interval &>(value)._impl->_time;
   }


   bool HLAinteger64Interval::operator<(
         LogicalTimeInterval const & value) const RTI_THROW(InvalidLogicalTimeInterval)
   {
      return _impl->_time < dynamic_cast<const HLAinteger64Interval &>(value)._impl->_time;

   }

   bool HLAinteger64Interval::operator==(
         LogicalTimeInterval const & value) const RTI_THROW(InvalidLogicalTimeInterval)
   {
      auto other = dynamic_cast<const HLAinteger64Interval *>(&value);
      if (other == nullptr) {
         return false;
      }
      return _impl->_time == other->_impl->_time;

   }

   bool HLAinteger64Interval::operator>=(
         LogicalTimeInterval const & value) const RTI_THROW(InvalidLogicalTimeInterval)
   {
      return _impl->_time >= dynamic_cast<const HLAinteger64Interval &>(value)._impl->_time;
   }

   bool HLAinteger64Interval::operator<=(
         LogicalTimeInterval const & value) const RTI_THROW(InvalidLogicalTimeInterval)
   {
      return _impl->_time <= dynamic_cast<const HLAinteger64Interval &>(value)._impl->_time;
   }

   void HLAinteger64Interval::setToDifference(
         LogicalTime const & minuend,
         LogicalTime const & subtrahend) RTI_THROW(IllegalTimeArithmetic, InvalidLogicalTime)
   {

      _impl->_time = dynamic_cast<const HLAinteger64Time &>(minuend).getTime() -
                     dynamic_cast<const HLAinteger64Time &>(subtrahend).getTime();
   }

   VariableLengthData HLAinteger64Interval::encode() const
   {
      auto bigTime = FedPro::nativeToBigEndian<Integer64>(_impl->_time);
      VariableLengthData vld(&bigTime, sizeof(bigTime));
      return vld;

   }

   size_t HLAinteger64Interval::encode(
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

   size_t HLAinteger64Interval::encodedLength() const
   {
      return sizeof(Integer64);
   }

   void HLAinteger64Interval::decode(
         VariableLengthData const & VariableLengthData) RTI_THROW(InternalError, CouldNotDecode)
   {
      if (encodedLength() > VariableLengthData.size()) {
         throw CouldNotDecode(L"Buffer too small!");
      }

      _impl->_time = FedPro::bigEndianToNative<Integer64>(VariableLengthData.data(), VariableLengthData.size());
   }

   void HLAinteger64Interval::decode(
         RTI_IF_HLA_VERSION_2024_OR_LATER(const) void * buffer,
         size_t bufferSize) RTI_THROW(InternalError, CouldNotDecode)
   {
      if (encodedLength() > bufferSize) {
         throw CouldNotDecode(L"Buffer too small!");
      }

      _impl->_time = FedPro::bigEndianToNative<Integer64>(buffer, bufferSize);
   }

   std::wstring HLAinteger64Interval::toString() const
   {
      std::wstringstream res;
      res << L"HLAinteger64Interval<" << _impl->_time << L">";
      return res.str();
   }

   std::wstring HLAinteger64Interval::implementationName() const
   {
      return L"HLAinteger64Time";
   }

   Integer64 HLAinteger64Interval::getInterval() const
   {
      return _impl->_time;
   }

   void HLAinteger64Interval::setInterval(
         Integer64 value)
   {
      _impl->_time = value;
   }

   HLAinteger64Interval & HLAinteger64Interval::operator=(
         const HLAinteger64Interval & rhs) RTI_THROW(InvalidLogicalTimeInterval)
   {
      if (this == &rhs) {
         return *this;
      }
      _impl->_time = rhs._impl->_time;
      return *this;
   }

   HLAinteger64Interval::operator Integer64() const
   {
      return _impl->_time;
   }

} // RTI_NAMESPACE
