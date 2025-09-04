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

#include "utility/bit.h"
#include "../RTIcompat.h"

#include <RTI/time/HLAfloat64Interval.h>
#include <RTI/time/HLAfloat64Time.h>

#include <cstring>
#include <sstream>

namespace RTI_NAMESPACE
{
   static constexpr const double EPSILON_VALUE = 2.2250738585072014e-308;

   class HLAfloat64IntervalImpl
   {
   public:
      double _time{0};
   };

   HLAfloat64Interval::HLAfloat64Interval()
   {
      _impl = new HLAfloat64IntervalImpl();
      _impl->_time = 0;
   }

   HLAfloat64Interval::HLAfloat64Interval(
         double value)
   {
      _impl = new HLAfloat64IntervalImpl();
      _impl->_time = value;

   }

   HLAfloat64Interval::HLAfloat64Interval(LogicalTimeInterval const & value)
   {
      _impl = new HLAfloat64IntervalImpl();
      _impl->_time = dynamic_cast<const HLAfloat64Interval &>(value)._impl->_time;
   }

   HLAfloat64Interval::HLAfloat64Interval(HLAfloat64Interval const & value)
   {
      _impl = new HLAfloat64IntervalImpl();
      _impl->_time = value._impl->_time;
   }

   HLAfloat64Interval::~HLAfloat64Interval() RTI_NOEXCEPT
   {
      delete _impl;
   }

   void HLAfloat64Interval::setZero()
   {
      _impl->_time = 0;
   }

   bool HLAfloat64Interval::isZero() const
   {
      return _impl->_time == 0;
   }

   void HLAfloat64Interval::setEpsilon()
   {
      _impl->_time = EPSILON_VALUE;
   }

   bool HLAfloat64Interval::isEpsilon() const
   {
      return _impl->_time == EPSILON_VALUE;
   }

#if (RTI_HLA_VERSION >= 2025)
   HLAfloat64Interval &
#else
   LogicalTimeInterval &
#endif
   HLAfloat64Interval::operator=(LogicalTimeInterval const & value) RTI_THROW(
         InvalidLogicalTimeInterval)
   {
      _impl->_time = dynamic_cast<const HLAfloat64Interval &>(value)._impl->_time;
      return *this;
   }

   LogicalTimeInterval & HLAfloat64Interval::operator+=(LogicalTimeInterval const & addend) RTI_THROW(
         IllegalTimeArithmetic,
         InvalidLogicalTimeInterval)
   {
      _impl->_time += dynamic_cast<const HLAfloat64Interval &>(addend).getInterval();
      return *this;
   }

   LogicalTimeInterval & HLAfloat64Interval::operator-=(LogicalTimeInterval const & subtrahend) RTI_THROW(
         IllegalTimeArithmetic,
         InvalidLogicalTimeInterval)
   {
      _impl->_time -= dynamic_cast<const HLAfloat64Interval &>(subtrahend).getInterval();
      return *this;

   }

   bool HLAfloat64Interval::operator>(LogicalTimeInterval const & value) const RTI_THROW(InvalidLogicalTimeInterval)
   {
      return _impl->_time > dynamic_cast<const HLAfloat64Interval &>(value)._impl->_time;
   }


   bool HLAfloat64Interval::operator<(LogicalTimeInterval const & value) const RTI_THROW(InvalidLogicalTimeInterval)
   {
      return _impl->_time < dynamic_cast<const HLAfloat64Interval &>(value)._impl->_time;

   }

   bool HLAfloat64Interval::operator==(LogicalTimeInterval const & value) const RTI_THROW(InvalidLogicalTimeInterval)
   {
      return _impl->_time == dynamic_cast<const HLAfloat64Interval &>(value)._impl->_time;

   }

   bool HLAfloat64Interval::operator>=(LogicalTimeInterval const & value) const RTI_THROW(InvalidLogicalTimeInterval)
   {
      return _impl->_time >= dynamic_cast<const HLAfloat64Interval &>(value)._impl->_time;
   }

   bool HLAfloat64Interval::operator<=(LogicalTimeInterval const & value) const RTI_THROW(InvalidLogicalTimeInterval)
   {
      return _impl->_time <= dynamic_cast<const HLAfloat64Interval &>(value)._impl->_time;
   }


   void HLAfloat64Interval::setToDifference(
         LogicalTime const & minuend,
         LogicalTime const & subtrahend) RTI_THROW(IllegalTimeArithmetic,
                                                    InvalidLogicalTime)
   {

      _impl->_time = dynamic_cast<const HLAfloat64Time &>(minuend).getTime() -
                     dynamic_cast<const HLAfloat64Time &>(subtrahend).getTime();
   }


   VariableLengthData HLAfloat64Interval::encode() const
   {
      static_assert(sizeof(double) == 64/8, "Use double for 64-bit float encoding");
      auto bigTime = FedPro::nativeToBigEndian<double>(_impl->_time);
      VariableLengthData vld(&bigTime, sizeof(bigTime));
      return vld;

   }

   size_t HLAfloat64Interval::encode(
         void * buffer,
         size_t bufferSize) const RTI_THROW(CouldNotEncode)
   {
      if (encodedLength() > bufferSize) {
         throw CouldNotDecode(L"Buffer too small!");
      }

      auto bigTime = FedPro::nativeToBigEndian<double>(_impl->_time);
      memcpy(buffer, &bigTime, sizeof(bigTime));
      return sizeof(bigTime);
   }

   size_t HLAfloat64Interval::encodedLength() const
   {
      static_assert(sizeof(double) == 64/8, "Use double for 64-bit float encoding");
      return sizeof(double);
   }

   void HLAfloat64Interval::decode(
         VariableLengthData const & VariableLengthData) RTI_THROW(InternalError, CouldNotDecode)
   {
      if (encodedLength() > VariableLengthData.size()) {
         throw CouldNotDecode(L"Buffer too small!");
      }

      _impl->_time = FedPro::bigEndianToNative<double>(VariableLengthData.data(), VariableLengthData.size());
   }

   void HLAfloat64Interval::decode(
         RTI_IF_HLA_VERSION_2025_OR_LATER(const) void * buffer,
         size_t bufferSize) RTI_THROW(InternalError, CouldNotDecode)
   {
      if (encodedLength() > bufferSize) {
         throw CouldNotDecode(L"Buffer too small!");
      }

      _impl->_time = FedPro::bigEndianToNative<double>(buffer, bufferSize);
   }

   std::wstring HLAfloat64Interval::toString() const
   {
      std::wstringstream res;
      res << L"HLAfloat64Interval<" << _impl->_time << L">";
      return res.str();
   }

   std::wstring HLAfloat64Interval::implementationName() const
   {
      return L"HLAfloat64Time";
   }

   double HLAfloat64Interval::getInterval() const
   {
      return _impl->_time;
   }

   void HLAfloat64Interval::setInterval(
         double value)
   {
      _impl->_time = value;
   }

   HLAfloat64Interval & HLAfloat64Interval::operator=(const HLAfloat64Interval & rhs) RTI_THROW(
         InvalidLogicalTimeInterval)
   {
      _impl->_time = rhs._impl->_time;
      return *this;
   }

   HLAfloat64Interval::operator double() const
   {
      return _impl->_time;
   }

} // RTI_NAMESPACE