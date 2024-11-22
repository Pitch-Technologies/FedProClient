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

#include <cstring>
#include <memory>

#include <RTI/VariableLengthData.h>

namespace RTI_NAMESPACE
{
   class VariableLengthDataImplementation
   {

   public:
      VariableLengthDataImplementation()
      {
         len = 0;
         data = nullptr;
         deleteFunction = nullptr;
         isMemoryOwner = false;
      }

      // Caller is free to delete inData after the call
      VariableLengthDataImplementation(void const *inData,
                                       size_t inSize)
      {
         data = new char[inSize];
         deleteFunction = nullptr;
         isMemoryOwner = true;
         memcpy(data, inData, inSize);
         len = inSize;
      }

      ~VariableLengthDataImplementation()
      {
         if (isMemoryOwner) {
            if (deleteFunction == nullptr) {
               delete[] (char *) data;
            } else {
               deleteFunction(data);
            }
         }
      }

      void *data;
      size_t len;
      VariableLengthDataDeleteFunction deleteFunction;
      bool isMemoryOwner;
   };

   VariableLengthData::VariableLengthData()
   {
      _impl = new VariableLengthDataImplementation();
   }

// Caller is free to delete inData after the call
   VariableLengthData::VariableLengthData(
         void const *inData,
         size_t inSize)
   {
      _impl = new VariableLengthDataImplementation(inData, inSize);
   }

// Caller is free to delete rhs after the call
   VariableLengthData::VariableLengthData(
         VariableLengthData const &rhs)
   {
      _impl = new VariableLengthDataImplementation(rhs._impl->data, rhs._impl->len);
   }

   VariableLengthData::~VariableLengthData()
   {
      delete _impl;
   }

// Caller is free to delete rhs after the call
// This instance will revert to internal storage as a result of assignment.
   VariableLengthData &
   VariableLengthData::operator=(
         VariableLengthData const &rhs)
   {
      if (this == &rhs) {
         return *this;
      }
      delete _impl;
      _impl = new VariableLengthDataImplementation(rhs._impl->data, rhs._impl->len);
      return *this;
   }

// This pointer should not be expected to be valid past the
// lifetime of this object, or past the next time this object
// is given new data
   void const *
   VariableLengthData::data() const
   {
      return _impl->data;
   }

   size_t
   VariableLengthData::size() const
   {
      return _impl->len;
   }

// Caller is free to delete inData after the call
   void
   VariableLengthData::setData(
         void const *inData,
         size_t inSize)
   {
      delete _impl;
      _impl = new VariableLengthDataImplementation(inData, inSize);
   }

// Caller is responsible for ensuring that the data that is
// pointed to is valid for the lifetime of this object, or past
// the next time this object is given new data.
   void
   VariableLengthData::setDataPointer(
         void *inData,
         size_t inSize)
   {
      delete _impl;
      _impl = new VariableLengthDataImplementation();
      _impl->data = inData;
      _impl->len = inSize;
   }

// Caller gives up ownership of inData to this object.
// This object assumes the responsibility of deleting inData
// when it is no longer needed.
// The allocation of inData is assumed to have been through an array
// alloctor (e.g., char* data = new char[20]. If the data was allocated
// in some other fashion, a deletion function must be supplied.
   void
   VariableLengthData::takeDataPointer(
         void *inData,
         size_t inSize,
         VariableLengthDataDeleteFunction func)
   {
      delete _impl;
      _impl = new VariableLengthDataImplementation();
      _impl->isMemoryOwner = true;
      _impl->deleteFunction = func;
      _impl->data = inData;
      _impl->len = inSize;
   }
}

