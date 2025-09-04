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

#include <RTI/encoding/EncodingConfig.h>
#include <RTI/time/HLAfloat64Time.h>

#include <cstring>
#include <sstream>

namespace RTI_NAMESPACE
{
   //TODO: Check for and throw IllegalTimeArithmetic, InvalidLogicalTimeInterval and InvalidLogicalTime.

   static const double INITIAL_VALUE = 0;
   static const double FINAL_VALUE = 1.7976931348623157e+308;

   class HLAfloat64TimeImpl
   {
   public:
      double _time;
   };

   namespace
   {
      double inc(double d)
      {
         Integer64 i = *(Integer64 *) &d;
         i++;
         return *(double *) &i;
      }

      double dec(double d)
      {
         Integer64 i = *(Integer64 *) &d;
         i--;
         return *(double *) &i;
      }
   }

   using namespace RTI_NAMESPACE;

   HLAfloat64Time::HLAfloat64Time()
   {
      _impl = new HLAfloat64TimeImpl();
      _impl->_time = 0;
   }

   HLAfloat64Time::HLAfloat64Time(double const & value)
   {
      _impl = new HLAfloat64TimeImpl();
      _impl->_time = value;

   }

   HLAfloat64Time::HLAfloat64Time(LogicalTime const & value)
   {
      _impl = new HLAfloat64TimeImpl();
      _impl->_time = dynamic_cast<const HLAfloat64Time &>(value)._impl->_time;

   }

   HLAfloat64Time::HLAfloat64Time(HLAfloat64Time const & value)
   {
      _impl = new HLAfloat64TimeImpl();
      _impl->_time = value._impl->_time;
   }

   HLAfloat64Time::~HLAfloat64Time() noexcept
   {
      delete _impl;
   }

   void HLAfloat64Time::setInitial()
   {
      _impl->_time = INITIAL_VALUE;
   }

   bool HLAfloat64Time::isInitial() const
   {
      return _impl->_time == INITIAL_VALUE;
   }

   void HLAfloat64Time::setFinal()
   {
      _impl->_time = FINAL_VALUE;
   }

   bool HLAfloat64Time::isFinal() const
   {
      return _impl->_time == FINAL_VALUE;
   }

#if (RTI_HLA_VERSION >= 2025)
   HLAfloat64Time & HLAfloat64Time::operator=(
         LogicalTime const & rhs)
#else
   LogicalTime & HLAfloat64Time::operator=(
         LogicalTime const & rhs) RTI_THROW(InvalidLogicalTime)
#endif
   {
      if (this == &rhs) {
         return *this;
      }
      _impl->_time = dynamic_cast<const HLAfloat64Time &>(rhs)._impl->_time;
      return *this;
   }

   LogicalTime & HLAfloat64Time::operator+=(
         LogicalTimeInterval const & addend) RTI_THROW(IllegalTimeArithmetic,
                                                        InvalidLogicalTimeInterval)
   {
      const auto & x = dynamic_cast<const HLAfloat64Interval &>(addend);
      double newValue = _impl->_time + x.getInterval();
      if (newValue == _impl->_time && x.getInterval() != 0) {
         newValue = inc(_impl->_time);
      }
      _impl->_time = newValue;
      return *this;
   }

   LogicalTime & HLAfloat64Time::operator-=(
         LogicalTimeInterval const & subtrahend) RTI_THROW(IllegalTimeArithmetic,
                                                            InvalidLogicalTimeInterval)
   {
      const auto & x = dynamic_cast<const HLAfloat64Interval &>(subtrahend);
      double newValue = _impl->_time - x.getInterval();
      if (newValue == _impl->_time && x.getInterval() != 0) {
         newValue = dec(_impl->_time);
      }
      _impl->_time = newValue;
      return *this;
   }

   bool HLAfloat64Time::operator>(
         LogicalTime const & value) const RTI_THROW(InvalidLogicalTime)
   {
      return _impl->_time > dynamic_cast<const HLAfloat64Time &>(value)._impl->_time;
   }


   bool HLAfloat64Time::operator<(
         LogicalTime const & value) const RTI_THROW(InvalidLogicalTime)
   {
      return _impl->_time < dynamic_cast<const HLAfloat64Time &>(value)._impl->_time;

   }

   bool HLAfloat64Time::operator==(
         LogicalTime const & value) const RTI_THROW(InvalidLogicalTime)
   {
      auto other = dynamic_cast<const HLAfloat64Time *>(&value);
      if (other == nullptr) {
         return false;
      }
      return _impl->_time == other->_impl->_time;
   }

   bool HLAfloat64Time::operator>=(
         LogicalTime const & value) const RTI_THROW(InvalidLogicalTime)
   {
      return _impl->_time >= dynamic_cast<const HLAfloat64Time &>(value)._impl->_time;
   }

   bool HLAfloat64Time::operator<=(
         LogicalTime const & value) const RTI_THROW(InvalidLogicalTime)
   {
      return _impl->_time <= dynamic_cast<const HLAfloat64Time &>(value)._impl->_time;
   }

   VariableLengthData HLAfloat64Time::encode() const
   {
      static_assert(sizeof(double) == 64/8, "Use double for 64-bit float encoding");
      auto bigTime = FedPro::nativeToBigEndian<double>(_impl->_time);
      VariableLengthData vld(&bigTime, sizeof(bigTime));
      return vld;
   }

   size_t HLAfloat64Time::encode(
         void * buffer,
         size_t bufferSize) const RTI_THROW(CouldNotEncode)
   {
      if (encodedLength() > bufferSize) {
         throw CouldNotEncode(L"Buffer too small!");
      }

      auto bigTime = FedPro::nativeToBigEndian<double>(_impl->_time);
      memcpy(buffer, &bigTime, sizeof(bigTime));
      return sizeof(bigTime);
   }

   size_t HLAfloat64Time::encodedLength() const
   {
      static_assert(sizeof(double) == 64/8, "Use double for 64-bit float encoding");
      return sizeof(double);
   }

   void HLAfloat64Time::decode(
         VariableLengthData const & VariableLengthData) RTI_THROW(InternalError, CouldNotDecode)
   {
      if (encodedLength() > VariableLengthData.size()) {
         throw CouldNotDecode(L"Buffer too small!");
      }

      _impl->_time = FedPro::bigEndianToNative<double>(VariableLengthData.data(), VariableLengthData.size());
   }

   void HLAfloat64Time::decode(
         RTI_IF_HLA_VERSION_2025_OR_LATER(const) void * buffer,
         size_t bufferSize) RTI_THROW(InternalError, CouldNotDecode)
   {
      if (encodedLength() > bufferSize) {
         throw CouldNotDecode(L"Buffer too small!");
      }

      _impl->_time = FedPro::bigEndianToNative<double>(buffer, bufferSize);
   }

   std::wstring HLAfloat64Time::toString() const
   {
      std::wstringstream res;
      res << L"HLAfloat64Time<" << _impl->_time << L">";
      return res.str();
   }

   std::wstring HLAfloat64Time::implementationName() const
   {
      return L"HLAfloat64Time";
   }

   double HLAfloat64Time::getTime() const
   {
      return _impl->_time;
   }

   void HLAfloat64Time::setTime(
         double value)
   {
      _impl->_time = value;
   }

   HLAfloat64Time & HLAfloat64Time::operator=(
         const HLAfloat64Time & rhs) RTI_THROW(InvalidLogicalTime)
   {
      if (this == &rhs) {
         return *this;
      }
      _impl->_time = rhs._impl->_time;
      return *this;
   }

   HLAfloat64Time::operator double() const
   {
      return _impl->_time;
   }

} // RTI_NAMESPACE