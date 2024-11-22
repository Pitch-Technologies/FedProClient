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

#include "services-common/encoding/Encoder.h"

#include <RTI/auth/HLAplainTextPassword.h>

namespace RTI_NAMESPACE {

   HLAplainTextPassword::HLAplainTextPassword(
         std::wstring const & password) : Credentials(HLAplainTextPasswordType, encode(password))
   {
   }

   HLAplainTextPassword::HLAplainTextPassword(
         VariableLengthData const & encodedPassword) : Credentials(HLAplainTextPasswordType, encodedPassword)
   {
   }

   HLAplainTextPassword::~HLAplainTextPassword() noexcept = default;

   HLAplainTextPassword::HLAplainTextPassword(HLAplainTextPassword const & rhs) = default;

   HLAplainTextPassword & HLAplainTextPassword::operator=(HLAplainTextPassword const & rhs) = default;


   VariableLengthData HLAplainTextPassword::encode(std::wstring const & password)
   {
      unsigned int size = Encoder::sizeofHLAunicodeString(password);
      auto buffer = new uint8_t[size];
      Encoder::encodeHLAunicodeString(password, buffer);
      VariableLengthData res;
      res.takeDataPointer(buffer, size);
      return res;
   }

   std::wstring HLAplainTextPassword::decode()
   {
      VariableLengthData const & data = getData();
      return Encoder::decodeHLAunicodeString(data.data(), data.size());
   }
} // RTI_NAMESPACE
