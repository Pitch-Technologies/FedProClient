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

#include <type_traits>

/**
 * Defines a concept for use with std::if_enable_t that requires a given expression to be valid.
 */
#define FEDPRO_CONCEPT_REQUIRES_VALID_EXPRESSION(                             \
   CONCEPT_NAME, CONCEPT_TYPE, CONCEPT_EXPRESSION)                            \
                                                                              \
/* Template indicate failure to meet requirement with std::false_type */      \
template<typename CONCEPT_TYPE, typename = void>                              \
struct CONCEPT_NAME : std::false_type                                         \
{                                                                             \
};                                                                            \
                                                                              \
/* Specialization indicate a type meet requirement with std::true_type */     \
template<typename CONCEPT_TYPE>                                               \
struct CONCEPT_NAME<CONCEPT_TYPE, void_t<decltype(CONCEPT_EXPRESSION)>> : std::true_type \
{                                                                             \
};

namespace FedPro
{
   /**
    * Maps a sequence of any types to the type void.
    * Available as std::void_t in C++17.
    */
   template< class... >
   using void_t = void;

   /**
    * Concept of response Message with a result.
    * Imply the Message type has a result() method.
    */
   FEDPRO_CONCEPT_REQUIRES_VALID_EXPRESSION(
         is_response,
         Message,
         std::declval<Message>().result());

   /**
    * Concept of response Message with an allocated result type.
    * Imply the Message type has a release_result() method.
    */
   FEDPRO_CONCEPT_REQUIRES_VALID_EXPRESSION(
         is_response_allocated,
         Message,
         std::declval<Message>().release_result() != nullptr);
}
