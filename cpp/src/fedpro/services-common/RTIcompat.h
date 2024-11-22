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

#include <RTI/SpecificConfig.h>

#include <memory>

namespace RTI_NAMESPACE
{
#if (RTI_HLA_VERSION >= 2024)
   template<class T>
   using unique_ptr = std::unique_ptr<T>;

   using std::make_unique;

   /**
    * @brief Helper function to create a std::unique_ptr<BaseType> containing a new
    * DerivedType object.
    * This function uses the adapter pattern, returns std::auto_ptr or std::unique_ptr
    * depending on the value of RTI_HLA_VERSION.
    */
   template<class BaseType, class DerivedType, class... Args>
   inline std::unique_ptr<BaseType> make_unique_derived(Args && ... args)
   {
      return std::make_unique<DerivedType>(std::forward<Args>(args)...);
   }
#else
   template<class T>
   using unique_ptr = std::auto_ptr<T>;

   /**
    * @brief Helper function to create a std::auto_ptr containing a new Type object.
    * This function uses the adapter pattern, returns std::auto_ptr or std::unique_ptr
    * depending on the value of RTI_HLA_VERSION.
    */
   template<class Type, class... Args>
   inline unique_ptr<Type> make_unique(Args && ... args)
   {
      return unique_ptr<Type>(new Type(std::forward<Args>(args)...));
   }

   /**
    * @brief Helper function to create a std::auto_ptr<BaseType> containing a new
    * DerivedType object.
    * This function uses the adapter pattern, returns std::auto_ptr or std::unique_ptr
    * depending on the value of RTI_HLA_VERSION.
    */
   template<class BaseType, class DerivedType, class... Args>
   inline unique_ptr<BaseType> make_unique_derived(Args && ... args)
   {
      return unique_ptr<BaseType>(new DerivedType(std::forward<Args>(args)...));
   }
#endif
} // RTI_NAMESPACE

#define RTI_UNIQUE_PTR RTI_NAMESPACE::unique_ptr

#ifndef RTI_NOEXCEPT
// Macro RTI_NOEXCEPT is provided for compatibility purpose.
// This may be used for sharing code with older RTI versions,
// but should not be used for new developments.
#define RTI_NOEXCEPT noexcept
#endif

#ifndef RTI_THROW
// Macro RTI_THROW is provided for compatibility purpose.
// This may be used for sharing code with older RTI versions,
// but should not be used for new developments.
#define RTI_THROW(...) noexcept(false)
#endif

// Macro RTI_IF_HLA_VERSION_2024_OR_LATER allows adding an extra qualifier for HLA 4 and above only.
// For instance, if a return type is const in HLA 4, and non-const in HLA Evolved:
// RTI_IF_HLA_VERSION_2024_OR_LATER(const) int multiply(int a, int b)
#if (RTI_HLA_VERSION >= 2024)
#define RTI_IF_HLA_VERSION_2024_OR_LATER(CODE) CODE
#else
#define RTI_IF_HLA_VERSION_2024_OR_LATER(CODE)
#endif
