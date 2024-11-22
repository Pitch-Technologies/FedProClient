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

#include "Handle.h"

#include <RTI/Handle.h>
#include <RTI/RangeBounds.h>

#include <map>
#include <string>

#define DECLARE_HANDLE_HELPERS(HandleKind)                                    \
   template<>                                                                 \
   HandleKind make_handle<HandleKind>(std::string && encodedHandle);          \
   std::string serialize(const HandleKind & handle);                          \
   const HandleImplementation * getImplementation(const HandleKind & handle); \
   HandleImplementation * getImplementation(HandleKind & handle);

namespace RTI_NAMESPACE
{
   /**
    * Base handle implementation.
    *
    * Stores a byte sequence of arbitrary length using std::string.
    * Actual handle implementations may derive from this class
    * to store additional information.
    */
   class HandleImplementation
   {
   public:

      HandleImplementation();

      explicit HandleImplementation(std::string && encodedData);

      virtual ~HandleImplementation();

      void setData(std::string && encodedData);

      void setData(
            const void * encodedData,
            size_t encodedDataLength);

      const std::string & getData() const;

      /**
       * Encode the handle and place the result into a buffer.
       *
       * @throw RTI_NAMESPACE::CouldNotEncode if bufferSize is insufficient.
       */
      size_t encode(
            void * buffer,
            size_t bufferSize) const;

      size_t length() const;

      bool isValid() const;

      long hash() const;

      bool operator==(const HandleImplementation & rhs) const
      {
         return _encodedHandle == rhs._encodedHandle;
      }

      bool operator!=(const HandleImplementation & rhs) const
      {
         return _encodedHandle != rhs._encodedHandle;
      }

      bool operator<(const HandleImplementation & rhs) const
      {
         return _encodedHandle < rhs._encodedHandle;
      }

   protected:
      std::string _encodedHandle;

   };

   template<class Handle>
   Handle make_handle(std::string && encodedHandle);

   // Handle helper classes are implemented by invoking the macro above.
   DECLARE_HANDLE_HELPERS(FederateHandle)

   DECLARE_HANDLE_HELPERS(ObjectClassHandle)

   DECLARE_HANDLE_HELPERS(InteractionClassHandle)

   DECLARE_HANDLE_HELPERS(ObjectInstanceHandle)

   DECLARE_HANDLE_HELPERS(AttributeHandle)

   DECLARE_HANDLE_HELPERS(ParameterHandle)

   DECLARE_HANDLE_HELPERS(DimensionHandle)

   DECLARE_HANDLE_HELPERS(MessageRetractionHandle)

   DECLARE_HANDLE_HELPERS(RegionHandle)

   DECLARE_HANDLE_HELPERS(TransportationTypeHandle)
}
