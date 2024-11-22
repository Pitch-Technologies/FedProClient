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

#include <RTI/Handle.h>

#include <string>

namespace FedPro
{
   class JoinResult
   {
   public:
      JoinResult();

      JoinResult(
            const RTI_NAMESPACE::FederateHandle & federateHandle,
            std::wstring logicalTimeImplementationName);

      constexpr const RTI_NAMESPACE::FederateHandle & getFederateHandle() const
      {
         return _federateHandle;
      }

      constexpr const std::wstring & getLogicalTimeImplementationName() const
      {
         return _logicalTimeImplementationName;
      }

   private:

      RTI_NAMESPACE::FederateHandle _federateHandle;
      std::wstring _logicalTimeImplementationName;

   };
}
