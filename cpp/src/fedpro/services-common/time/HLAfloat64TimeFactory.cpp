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

#include "../RTIcompat.h"

#if (RTI_HLA_VERSION >= 2025)
#include <RTI/time/LogicalTime.h>
#else
#include <RTI/LogicalTime.h>
#endif
#include <RTI/time/HLAfloat64Interval.h>
#include <RTI/time/HLAfloat64Time.h>
#include <RTI/time/HLAfloat64TimeFactory.h>

namespace RTI_NAMESPACE
{
   HLAfloat64TimeFactory::HLAfloat64TimeFactory() = default;

   HLAfloat64TimeFactory::~HLAfloat64TimeFactory() noexcept = default;

   RTI_UNIQUE_PTR<HLAfloat64Time> HLAfloat64TimeFactory::makeLogicalTime(
         double value) RTI_THROW(InternalError)
   {
      return RTI_UNIQUE_PTR<HLAfloat64Time>{new HLAfloat64Time(value)};
   }

   RTI_UNIQUE_PTR<LogicalTime> HLAfloat64TimeFactory::makeInitial() RTI_THROW(InternalError)
   {
      RTI_UNIQUE_PTR<LogicalTime> initialTime{new HLAfloat64Time};
      initialTime->setInitial();
      return initialTime;
   }

   RTI_UNIQUE_PTR<LogicalTime> HLAfloat64TimeFactory::makeFinal() RTI_THROW(InternalError)
   {
      RTI_UNIQUE_PTR<LogicalTime> finalTime{new HLAfloat64Time};
      finalTime->setFinal();
      return finalTime;
   }

   RTI_UNIQUE_PTR<HLAfloat64Interval> HLAfloat64TimeFactory::makeLogicalTimeInterval(
         double value) RTI_THROW(InternalError)
   {
      return RTI_UNIQUE_PTR<HLAfloat64Interval>{new HLAfloat64Interval(value)};
   }

   RTI_UNIQUE_PTR<LogicalTimeInterval> HLAfloat64TimeFactory::makeZero() RTI_THROW(InternalError)
   {
      return RTI_UNIQUE_PTR<LogicalTimeInterval>{new HLAfloat64Interval()};
   }

   RTI_UNIQUE_PTR<LogicalTimeInterval> HLAfloat64TimeFactory::makeEpsilon() RTI_THROW(InternalError)
   {
      return RTI_UNIQUE_PTR<LogicalTimeInterval>{new HLAfloat64Interval(1)};
   }

   RTI_UNIQUE_PTR<LogicalTime> HLAfloat64TimeFactory::decodeLogicalTime(
         VariableLengthData const & encodedLogicalTime) RTI_THROW(InternalError, CouldNotDecode)
   {
      RTI_UNIQUE_PTR<LogicalTime> time{new HLAfloat64Time()};
      time->decode(encodedLogicalTime);
      return time;
   }

   RTI_UNIQUE_PTR<LogicalTime> HLAfloat64TimeFactory::decodeLogicalTime(
         RTI_IF_HLA_VERSION_2025_OR_LATER(const) void * buffer,
         size_t bufferSize) RTI_THROW(InternalError, CouldNotDecode)
   {
      RTI_UNIQUE_PTR<LogicalTime> time{new HLAfloat64Time()};
      time->decode(buffer, bufferSize);
      return time;
   }

   RTI_UNIQUE_PTR<LogicalTimeInterval> HLAfloat64TimeFactory::decodeLogicalTimeInterval(
         VariableLengthData const & encodedValue) RTI_THROW(InternalError, CouldNotDecode)
   {
      RTI_UNIQUE_PTR<LogicalTimeInterval> timeInterval{new HLAfloat64Interval()};
      timeInterval->decode(encodedValue);
      return timeInterval;
   }

   RTI_UNIQUE_PTR<LogicalTimeInterval> HLAfloat64TimeFactory::decodeLogicalTimeInterval(
         RTI_IF_HLA_VERSION_2025_OR_LATER(const) void * buffer,
         size_t bufferSize) RTI_THROW(InternalError, CouldNotDecode)
   {
      RTI_UNIQUE_PTR<LogicalTimeInterval> timeInterval{new HLAfloat64Interval()};
      timeInterval->decode(buffer, bufferSize);
      return timeInterval;
   }

   std::wstring HLAfloat64TimeFactory::getName() const
   {
      return HLAfloat64TimeName;
   }
} // RTI_NAMESPACE