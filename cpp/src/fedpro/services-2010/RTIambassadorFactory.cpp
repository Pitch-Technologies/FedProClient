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

#include "RTIambassadorClientAdapter.h"

#include <RTI/RTIambassadorFactory.h>

#include <memory>

namespace RTI_NAMESPACE
{

   RTIambassadorFactory::RTIambassadorFactory() = default;

   RTIambassadorFactory::~RTIambassadorFactory() RTI_NOEXCEPT = default;

   unique_ptr<RTIambassador> RTIambassadorFactory::createRTIambassador() RTI_THROW(RTIinternalError)
   {
      return make_unique_derived<RTIambassador, RTIambassadorClientAdapter>();
   }

   std::wstring rtiName()
   {
      return L"Federate Protocol";
   }

   std::wstring rtiVersion()
   {
      return L"2.1";
   }

}
