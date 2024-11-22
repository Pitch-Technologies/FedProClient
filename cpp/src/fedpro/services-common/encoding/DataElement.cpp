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

#include <RTI/encoding/DataElement.h>

#include <typeinfo>

namespace RTI_NAMESPACE
{
   DataElement::~DataElement() = default;

   bool DataElement::isSameTypeAs(const DataElement &inData) const
   {
      return typeid(*this) == typeid(inData);
   }

   Integer64 DataElement::hash() const
   {
      return 1;
   }

} // RTI_NAMESPACE

