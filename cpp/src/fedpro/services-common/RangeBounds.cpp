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

#include "RTIcompat.h"

#include <RTI/RangeBounds.h>

namespace RTI_NAMESPACE
{

   RangeBounds::RangeBounds() :
      _lowerBound{0},
      _upperBound{0}
   {
   }

   RangeBounds::RangeBounds(
         unsigned long lowerBound,
         unsigned long upperBound)
   {
      _lowerBound = lowerBound;
      _upperBound = upperBound;
   }

   RangeBounds::~RangeBounds() RTI_NOEXCEPT = default;

   RangeBounds::RangeBounds(
         RangeBounds const & rhs) = default;

   RangeBounds & RangeBounds::operator=(
         RangeBounds const & rhs) = default;

   unsigned long RangeBounds::getLowerBound() const
   {
      return _lowerBound;
   }

   unsigned long RangeBounds::getUpperBound() const
   {
      return _upperBound;
   }

   void RangeBounds::setLowerBound(
         unsigned long lowerBound)
   {
      _lowerBound = lowerBound;
   }

   void RangeBounds::setUpperBound(
         unsigned long upperBound)
   {
      _upperBound = upperBound;
   }

} // RTI_NAMESPACE