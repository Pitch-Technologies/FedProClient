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

#include <RTI/encoding/EncodingExceptions.h>

namespace RTI_NAMESPACE
{

#define RTI_EXCEPTION_2010(A)             \
   A::A (                                 \
         std::wstring const & message)    \
       noexcept : _msg(message) {         \
   }                                      \
   std::wstring                           \
     A::what () const                     \
     noexcept { return _msg;};

#if (RTI_HLA_VERSION < 2024)
#define RTI_EXCEPTION(A)                  \
   RTI_EXCEPTION_2010(A)
#else
   #define RTI_EXCEPTION(A)                  \
   RTI_EXCEPTION_2010(A)                  \
   std::wstring                           \
     A::name () const                     \
     noexcept { return L"" #A L"";};
#endif

   RTI_EXCEPTION(EncoderException)

} // RTI_NAMESPACE
