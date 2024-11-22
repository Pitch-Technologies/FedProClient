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

#if (RTI_HLA_VERSION < 2024)
namespace RTI_NAMESPACE
{
   // Define TransportationTypeHandle for internal use.
   // Federate Protocol requires type TransportationTypeHandle but the earlier HLA APIs do not define it.
   DEFINE_HANDLE_CLASS(TransportationTypeHandle)
}
#endif