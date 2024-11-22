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

#include <session/msg/ByteWriter.h>

#include <gtest/gtest.h>

using namespace FedPro;

class TestByteWriter : public ::testing::Test
{
protected:

   template<typename Type>
   void testWriteAtPosition(const std::initializer_list<Type> & values, size_t startPos)
   {
      // When
      ByteSequence outputBuffer(startPos + sizeof(Type), '\0');
      for (Type x : values) {
         // Given
         ByteWriter::write(outputBuffer, startPos, x);
         Type decoded_x{};
         memcpy(&decoded_x, outputBuffer.data() + startPos, sizeof(decoded_x));
         if (FedPro::endian::native != FedPro::endian::big) {
            decoded_x = byteswap(decoded_x);
         }
         // Then
         EXPECT_EQ(x, decoded_x);
      }
   }
};

TEST_F(TestByteWriter, write_int8_at_0)
{
   // When
   const size_t startPos{0};
   // Given
   std::initializer_list<int8_t> values = {-1, 0, 1, 2, 0x7E, 0x7F};
   // Then
   testWriteAtPosition(values, startPos);
}

TEST_F(TestByteWriter, write_int8_at_4)
{
   // When
   const size_t startPos{4};
   // Given
   std::initializer_list<int8_t> values = {-1, 0, 1, 2, 0x7E, 0x7F};
   // Then
   testWriteAtPosition(values, startPos);
}

TEST_F(TestByteWriter, write_uint32_at_0)
{
   // When
   const size_t startPos{0};
   // Given
   std::initializer_list<uint32_t> values = {0u, 1u, 2u, 0xFEu, 0xFFu, 0xFFFFFFFEu, 0xFFFFFFFFu};
   // Then
   testWriteAtPosition(values, startPos);
}

TEST_F(TestByteWriter, write_uint32_at_4)
{
   // When
   const size_t startPos{4};
   // Given
   std::initializer_list<uint32_t> values = {0u, 1u, 2u, 0xFEu, 0xFFu, 0xFFFFFFFEu, 0xFFFFFFFFu};
   // Then
   testWriteAtPosition(values, startPos);
}

TEST_F(TestByteWriter, write_uint32_at_20)
{
   // When the start position is the same as the position of FedPro's MessageType field in MessageHeader
   const size_t startPos{20};
   // Given values with the same type as FedPro's MessageType field (uint32_t)
   std::initializer_list<uint32_t> values = {0u, 1u, 2u, 0xFEu, 0xFFu, 0xFFFFFFFEu, 0xFFFFFFFFu};
   // Then
   testWriteAtPosition(values, startPos);
}

TEST_F(TestByteWriter, write_uint64_at_0)
{
// When
   const size_t startPos{0};
// Given
   std::initializer_list<uint64_t> values = {0u, 1u, 0xFFFFFFFFFFFFFFEu, 0xFFFFFFFFFFFFFFEu};
// Then
   testWriteAtPosition(values, startPos);
}

TEST_F(TestByteWriter, write_uint64_at_3)
{
// When
   const size_t startPos{3};
// Given
   std::initializer_list<uint64_t> values = {0u, 1u, 0xFFFFFFFFFFFFFFEu, 0xFFFFFFFFFFFFFFEu};
// Then
   testWriteAtPosition(values, startPos);
}
