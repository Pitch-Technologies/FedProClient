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

#if (RTI_HLA_VERSION >= 2024)
#include <RTI/time/HLAlogicalTimeFactoryFactory.h>
#include <RTI/time/LogicalTimeFactory.h>
#else
#include <RTI/LogicalTimeFactory.h>
#endif
#include <RTI/time/HLAfloat64TimeFactory.h>
#include <RTI/time/HLAinteger64TimeFactory.h>

namespace RTI_NAMESPACE
{
   unique_ptr<LogicalTimeFactory> HLAlogicalTimeFactoryFactory::makeLogicalTimeFactory(
         std::wstring const & implementationName)
   {
      if (implementationName.empty() || implementationName == HLAfloat64TimeName) {
         return make_unique_derived<LogicalTimeFactory, HLAfloat64TimeFactory>();
      } else if (implementationName == HLAinteger64TimeName) {
         return make_unique_derived<LogicalTimeFactory, HLAinteger64TimeFactory>();
      } else {
         return unique_ptr<LogicalTimeFactory>{nullptr};
      }
   }
} // RTI_NAMESPACE