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

#pragma once

#include <RTI/RtiConfiguration.h>
#include <RTI/RangeBounds.h>

namespace RTI_NAMESPACE
{
   bool operator==(
         const RTI_NAMESPACE::RtiConfiguration & lhs,
         const RTI_NAMESPACE::RtiConfiguration & rhs)
   {
      return lhs.configurationName() == rhs.configurationName() && lhs.rtiAddress() == rhs.rtiAddress() &&
             lhs.additionalSettings() == rhs.additionalSettings();
   }

   bool operator==(
         const RTI_NAMESPACE::RangeBounds & lhs,
         const RTI_NAMESPACE::RangeBounds & rhs)
   {
      return lhs.getLowerBound() == rhs.getLowerBound() && lhs.getUpperBound() == rhs.getUpperBound();
   }
}