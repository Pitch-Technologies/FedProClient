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

#include "RegionHandleImplementation.h"

#include <RTI/Exception.h>

namespace RTI_NAMESPACE
{
   void RegionHandleImplementation::addRange(
         DimensionHandle && dimensionHandle,
         RangeBounds && rangeBounds)
   {
      _rangeMap.emplace(std::move(dimensionHandle), std::move(rangeBounds));
   }

   RangeBounds RegionHandleImplementation::getRangeBounds(const DimensionHandle & dimensionHandle) const
   {
      auto it = _rangeMap.find(dimensionHandle);
      if (it != _rangeMap.end()) {
         return it->second;
      } else {
         throw RegionDoesNotContainSpecifiedDimension(L"No RangeBounds for this DimensionHandle");
      }
   }

   DimensionHandleSet RegionHandleImplementation::getDimensionHandleSet() const
   {
      DimensionHandleSet dimensionHandleSet;
      for (const auto & keyValuePair : _rangeMap) {
         dimensionHandleSet.emplace(keyValuePair.first);
      }
      return dimensionHandleSet;
   }
}
