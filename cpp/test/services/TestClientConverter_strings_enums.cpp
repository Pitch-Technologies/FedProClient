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

#include "TestClientConverter.h"

#include <algorithm>

using namespace FedPro;

class TestClientConverter_strings_enums : public TestClientConverter
{
protected:

   // Analyze enumerations values from a protobuf EnumDescriptor.
   // Store original values and converted values as member variables.
   template<typename FedProEnumT>
   void analyze_enum(const google::protobuf::EnumDescriptor * enumDescriptor);

   // Test conversion of unique enumerations values
   // produces a set of unique converted enumerations values.
   void test_enum_values_unique()
   {
      // Ensure the original enum contains unique values
      size_t valueCount = _fedproEnumValues.size();
      EXPECT_GT(valueCount, 0);
      auto lastFedproVal = std::unique(_fedproEnumValues.begin(), _fedproEnumValues.end());
      _fedproEnumValues.erase(lastFedproVal, _fedproEnumValues.end());
      EXPECT_EQ(_fedproEnumValues.size(), valueCount);

      // Ensure the conversion produce unique values
      EXPECT_EQ(_rtiEnumValues.size(), valueCount);
      auto lastRtiVal = std::unique(_rtiEnumValues.begin(), _rtiEnumValues.end());
      _rtiEnumValues.erase(lastRtiVal, _rtiEnumValues.end());
      EXPECT_EQ(_rtiEnumValues.size(), valueCount);
   }

   // Test conversion of invalid enumeration values.
   template<typename FedProEnumT>
   void test_enum_invalid_value_conversion()
   {
      EXPECT_THROW(_clientConverter.convertToHla(static_cast<FedProEnumT>(-1)), std::invalid_argument);
      EXPECT_THROW(_clientConverter.convertToHla(static_cast<FedProEnumT>(_fedproEnumMaxValue + 1)), std::invalid_argument);
   }

   // Test back-and-forth conversion of enumeration values.
   template<typename FedProEnumT>
   void test_enum_back_and_forth_conversion()
   {
      size_t valueCount = _fedproEnumValues.size();
      EXPECT_GT(valueCount, 0);
      for (size_t i = 0; i < valueCount; ++i) {
         auto fedproValue = static_cast<FedProEnumT>(_fedproEnumValues[i]);
         auto riValue = _clientConverter.convertToHla(fedproValue);
         auto fedproValueBis = _clientConverter.convertFromHla(riValue);
         EXPECT_EQ(fedproValue, fedproValueBis);
      }
   }

protected:

   // FedPro enumerations values collected by analyze_enum.
   std::vector<int> _fedproEnumValues;
   // Maximum enumeration value computed by analyze_enum.
   int _fedproEnumMaxValue{-1};
   // RTI Enumerations values collected by analyze_enum.
   std::vector<int> _rtiEnumValues;

};

TEST_F(TestClientConverter_strings_enums, string_convertStringFromHla)
{
   EXPECT_EQ(_clientConverter.convertStringFromHla(L"hello"), "hello");
   EXPECT_EQ(_clientConverter.convertStringFromHla(L"Bj\x00F6rn"), "Bj\xC3\xB6rn");
}

TEST_F(TestClientConverter_strings_enums, string_convertToHla)
{
   EXPECT_EQ(_clientConverter.convertToHla("hello"), L"hello");
   EXPECT_EQ(_clientConverter.convertToHla("Bj\xC3\xB6rn"), L"Bj\x00F6rn");
}

TEST_F(TestClientConverter_strings_enums, string_convertFromHla)
{
   const std::wstring messageWideString(L"hello");
   std::unique_ptr<std::string> messagePtr(
         _clientConverter.convertFromHla(messageWideString));
   EXPECT_EQ(messageWideString.size(), messagePtr->size());
   EXPECT_EQ(messageWideString, _clientConverter.convertToHla(*messagePtr));
}

TEST_F(TestClientConverter_strings_enums, VariableLengthData)
{
   // A sequence of null bytes and control characters for testing string and buffer conversion.
   constexpr static const char controlChars[] = "\x00\x01\x02\x03\x04\x05\x06\x07\b\x0E\x0F\x10\x11\x12\x13\x14\x15"
                                                "\x16\x17\x18\x19\x1A\x1B\x1C\x1D\x1E\x1F\x7F\x80\x81\x82\x83\x84"
                                                "\x86\x87\x88\x89\x8A\x8B\x8C\x8D\x8E\x8F\x90\x91\x92\x93\x94\x95"
                                                "\x96\x97\x98\x99\x9A\x9B\x9C\x9D\x9E\x9F\xAD\x00\x00\x00\x00\x00";

   const std::string messageString(controlChars, sizeof(controlChars));
   EXPECT_EQ(messageString.size(), sizeof(controlChars));

   const RTI_NAMESPACE::VariableLengthData messageArray(
         _clientConverter.convertToHlaByteArray(messageString));
   EXPECT_EQ(messageArray.size(), messageString.size());
   EXPECT_TRUE(memcmp(messageArray.data(), messageString.data(), messageArray.size()) == 0);

   const std::string mesageStringBis(_clientConverter.convertFromHla(messageArray));
   EXPECT_EQ(messageArray.size(), mesageStringBis.size());
   EXPECT_TRUE(memcmp(messageArray.data(), mesageStringBis.data(), messageArray.size()) == 0);
}

#define ENUMERATION_TO_HLA_TEST_F(Fixture, EnumType)                 \
TEST_F(Fixture, EnumType)                                            \
{                                                                    \
   analyze_enum<rti1516_202X::fedpro::EnumType>(                     \
      rti1516_202X::fedpro::EnumType##_descriptor());                \
                                                                     \
   test_enum_values_unique();                                        \
   test_enum_invalid_value_conversion<rti1516_202X::fedpro::EnumType>(); \
}

#define ENUMERATION_TO_FROM_HLA_TEST_F(Fixture, EnumType)            \
TEST_F(Fixture, EnumType)                                            \
{                                                                    \
   analyze_enum<rti1516_202X::fedpro::EnumType>(                     \
      rti1516_202X::fedpro::EnumType##_descriptor());                \
                                                                     \
   test_enum_values_unique();                                        \
   test_enum_invalid_value_conversion<rti1516_202X::fedpro::EnumType>(); \
   test_enum_back_and_forth_conversion<rti1516_202X::fedpro::EnumType>(); \
}

ENUMERATION_TO_HLA_TEST_F(TestClientConverter_strings_enums, AdditionalSettingsResultCode)

ENUMERATION_TO_FROM_HLA_TEST_F(TestClientConverter_strings_enums, OrderType)

ENUMERATION_TO_FROM_HLA_TEST_F(TestClientConverter_strings_enums, ResignAction)

ENUMERATION_TO_FROM_HLA_TEST_F(TestClientConverter_strings_enums, RestoreFailureReason)

ENUMERATION_TO_HLA_TEST_F(TestClientConverter_strings_enums, RestoreStatus)

ENUMERATION_TO_FROM_HLA_TEST_F(TestClientConverter_strings_enums, SaveFailureReason)

ENUMERATION_TO_HLA_TEST_F(TestClientConverter_strings_enums, SaveStatus)

ENUMERATION_TO_FROM_HLA_TEST_F(TestClientConverter_strings_enums, ServiceGroup)

ENUMERATION_TO_HLA_TEST_F(TestClientConverter_strings_enums, SynchronizationPointFailureReason)


template<typename FedProEnumT>
void TestClientConverter_strings_enums::analyze_enum(const google::protobuf::EnumDescriptor * enumDescriptor)
{
   // Reset

   _fedproEnumValues.clear();
   _fedproEnumMaxValue = -1;
   _rtiEnumValues.clear();

   // Analyze

   const int valueCount = enumDescriptor->value_count();
   _fedproEnumValues.reserve(valueCount);
   _rtiEnumValues.reserve(valueCount);
   for (int i = 0; i < valueCount; ++i) {
      auto valueDescriptor = enumDescriptor->value(i);
      auto fedproValue = static_cast<FedProEnumT>(valueDescriptor->number());
      _fedproEnumValues.push_back(fedproValue);
      _fedproEnumMaxValue = std::max<int>(_fedproEnumMaxValue, fedproValue);
      auto rtiValue = _clientConverter.convertToHla(fedproValue);
      _rtiEnumValues.push_back(rtiValue);
   }
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)