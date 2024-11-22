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

// Silence clang-tidy issues reported for gtest macros.
// NOLINTBEGIN(cppcoreguidelines-avoid-non-const-global-variables)

#include <utility/bit.h>

#include <gtest/gtest.h>

#include <string>

using namespace FedPro;

class TestBit_util : public ::testing::Test
{
};

TEST_F(TestBit_util, byteswap_integers)
{
   int mirrorInteger{0x00444400};
   EXPECT_EQ(mirrorInteger, byteswap(mirrorInteger));

   int one{0x01};
   int oneSwapped{0x01000000};
   EXPECT_EQ(oneSwapped, byteswap(one));
}

TEST_F(TestBit_util, byteswap_double)
{
   uint64_t zero{0x0000000000000000u};
   double zeroBytesDouble{reinterpret_cast<double &>(zero)};
   EXPECT_EQ(zeroBytesDouble, byteswap(zeroBytesDouble));

   uint64_t one{0x0000000000000001u};
   double oneAsDouble{reinterpret_cast<double &>(one)};
   uint64_t oneSwapped{0x0100000000000000u};
   double oneSwappedAsDouble{reinterpret_cast<double &>(oneSwapped)};
   EXPECT_EQ(oneSwappedAsDouble, byteswap(oneAsDouble));
}

TEST_F(TestBit_util, byteswap_buffer)
{
   std::string emptyByteSequence;
   FedPro::byteswap((uint8_t *)(emptyByteSequence.data()), emptyByteSequence.length());
   EXPECT_EQ(emptyByteSequence, std::string());

   std::string evenLengthByteSequence("drawer");
   std::string swappedEvenLengthByteSequence("reward");
   FedPro::byteswap((uint8_t *)(swappedEvenLengthByteSequence.data()), swappedEvenLengthByteSequence.length());
   EXPECT_EQ(evenLengthByteSequence, swappedEvenLengthByteSequence);

   std::string oddLengthByteSequence("drawer ");
   std::string swappedOddLengthByteSequence(" reward");
   FedPro::byteswap((uint8_t *)(swappedOddLengthByteSequence.data()), swappedOddLengthByteSequence.length());
   EXPECT_EQ(oddLengthByteSequence, swappedOddLengthByteSequence);
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)