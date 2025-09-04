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

#if (RTI_HLA_VERSION >= 2025)
#include <RTI/time/LogicalTime.h>
#else
#include <RTI/LogicalTime.h>
#endif
#include <RTI/time/HLAinteger64Interval.h>
#include <RTI/time/HLAinteger64Time.h>
#include <RTI/time/HLAinteger64TimeFactory.h>

namespace RTI_NAMESPACE
{
   HLAinteger64TimeFactory::HLAinteger64TimeFactory() = default;

   HLAinteger64TimeFactory::~HLAinteger64TimeFactory() RTI_NOEXCEPT = default;

   RTI_UNIQUE_PTR<HLAinteger64Time> HLAinteger64TimeFactory::makeLogicalTime(
         Integer64 value) RTI_THROW(InternalError)
   {
      return RTI_UNIQUE_PTR<HLAinteger64Time>{new HLAinteger64Time(value)};
   }

   RTI_UNIQUE_PTR<LogicalTime> HLAinteger64TimeFactory::makeInitial() RTI_THROW(InternalError)
   {
      RTI_UNIQUE_PTR<LogicalTime> initialTime{new HLAinteger64Time};
      initialTime->setInitial();
      return initialTime;
   }

   RTI_UNIQUE_PTR<LogicalTime> HLAinteger64TimeFactory::makeFinal() RTI_THROW(InternalError)
   {
      RTI_UNIQUE_PTR<LogicalTime> finalTime{new HLAinteger64Time};
      finalTime->setFinal();
      return finalTime;
   }

   RTI_UNIQUE_PTR<HLAinteger64Interval> HLAinteger64TimeFactory::makeLogicalTimeInterval(
         Integer64 value) RTI_THROW(InternalError)
   {
      return RTI_UNIQUE_PTR<HLAinteger64Interval>{new HLAinteger64Interval(value)};
   }

   RTI_UNIQUE_PTR<LogicalTimeInterval> HLAinteger64TimeFactory::makeZero() RTI_THROW(InternalError)
   {
      return RTI_UNIQUE_PTR<LogicalTimeInterval>{new HLAinteger64Interval()};
   }

   RTI_UNIQUE_PTR<LogicalTimeInterval> HLAinteger64TimeFactory::makeEpsilon() RTI_THROW(InternalError)
   {
      return RTI_UNIQUE_PTR<LogicalTimeInterval>{new HLAinteger64Interval(1)};
   }

   RTI_UNIQUE_PTR<LogicalTime> HLAinteger64TimeFactory::decodeLogicalTime(
         VariableLengthData const & encodedLogicalTime) RTI_THROW(InternalError, CouldNotDecode)
   {
      RTI_UNIQUE_PTR<LogicalTime> time{new HLAinteger64Time()};
      time->decode(encodedLogicalTime);
      return time;
   }

   RTI_UNIQUE_PTR<LogicalTime> HLAinteger64TimeFactory::decodeLogicalTime(
         RTI_IF_HLA_VERSION_2025_OR_LATER(const) void * buffer,
         size_t bufferSize) RTI_THROW(InternalError, CouldNotDecode)
   {
      RTI_UNIQUE_PTR<LogicalTime> time{new HLAinteger64Time()};
      time->decode(buffer, bufferSize);
      return time;
   }

   RTI_UNIQUE_PTR<LogicalTimeInterval> HLAinteger64TimeFactory::decodeLogicalTimeInterval(
         VariableLengthData const & encodedValue) RTI_THROW(InternalError, CouldNotDecode)
   {
      RTI_UNIQUE_PTR<LogicalTimeInterval> timeInterval{new HLAinteger64Interval()};
      timeInterval->decode(encodedValue);
      return timeInterval;
   }

   RTI_UNIQUE_PTR<LogicalTimeInterval> HLAinteger64TimeFactory::decodeLogicalTimeInterval(
         RTI_IF_HLA_VERSION_2025_OR_LATER(const) void * buffer,
         size_t bufferSize) RTI_THROW(InternalError, CouldNotDecode)
   {
      RTI_UNIQUE_PTR<LogicalTimeInterval> timeInterval{new HLAinteger64Interval()};
      timeInterval->decode(buffer, bufferSize);
      return timeInterval;
   }

   std::wstring HLAinteger64TimeFactory::getName() const
   {
      return HLAinteger64TimeName;
   }
} // RTI_NAMESPACE