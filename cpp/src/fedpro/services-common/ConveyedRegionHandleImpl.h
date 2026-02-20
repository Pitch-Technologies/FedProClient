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

#include "HandleImplementation.h"

#include <RTI/Handle.h>
#include <RTI/RangeBounds.h>
#include <RTI/Typedefs.h>

namespace RTI_NAMESPACE
{
   class VariableLengthData;

   /**
    * ConveyedRegionHandleImpl deviate from the default implementation
    * in order to store information for ConveyedRegion.
    */
   class ConveyedRegionHandleImpl : public RegionHandleImplementation
   {
   public:

      HandleImplementation * clone() const override;

      void addRange(
            DimensionHandle && dimensionHandle,
            RangeBounds && rangeBounds);

      RangeBounds getRangeBounds(const DimensionHandle & dimensionHandle) const;

      /**
       * Dimensions specified for this region.
       */
      DimensionHandleSet getDimensionHandleSet() const;

   private:

      std::map<DimensionHandle, RangeBounds> _rangeMap;

   };
}
