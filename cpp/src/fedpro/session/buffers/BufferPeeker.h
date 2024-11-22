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

#include <memory>

#include <type_traits>

namespace FedPro
{
   // This default template provides no peek capability,
   // but peek type and implementation may change though template specialization.
   template<typename E, typename EnableType = void>
   class BufferPeeker
   {
   public:

      using peek_type = void;

      static constexpr peek_type createPeek()
      {
      }

      static constexpr peek_type createPeek(const E &)
      {
      }
   };


   // Template class specialization that provides peek capability if type E is copy-constructible.
   template<typename E>
   class BufferPeeker<E, typename std::enable_if<std::is_copy_constructible<E>::value>::type>
   {
   public:

      using peek_type = std::unique_ptr<E>;

      static peek_type createPeek()
      {
         return {};
      }

      static peek_type createPeek(const E & element)
      {
         return std::make_unique<E>(element);
      }
   };

} // FedPro
