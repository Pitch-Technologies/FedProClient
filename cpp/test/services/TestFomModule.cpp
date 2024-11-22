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

#include <services-common/FomModule.h>
#include <services-common/FomModuleLoader.h>

#include <gtest/gtest.h>

using namespace FedPro;

class TestFomModule : public ::testing::Test
{
};

TEST_F(TestFomModule, copy)
{
   const FomModule constUrlModule(L"https://example.invalid/foms/RPR-Base_v2.0.xml");
   FomModule copiedModule{constUrlModule};
   EXPECT_EQ(constUrlModule, copiedModule);
   copiedModule = constUrlModule;
   EXPECT_EQ(constUrlModule, copiedModule);
}

TEST_F(TestFomModule, move_constructor)
{
   auto rprBaseUrl = L"https://example.invalid/foms/RPR-Base_v2.0.xml";
   FomModule urlModule(rprBaseUrl);
   FomModule otherModule{std::move(urlModule)};
   EXPECT_EQ(otherModule.getUrl(), rprBaseUrl);
}

TEST_F(TestFomModule, move_copy)
{
   auto rprBaseUrl = L"https://example.invalid/foms/RPR-Base_v2.0.xml";
   FomModule urlModule(rprBaseUrl);
   FomModule otherModule(L"https://example.invalid/forms/Multiverse-Base.xml");
   otherModule = urlModule;
   EXPECT_EQ(otherModule.getUrl(), rprBaseUrl);
}

class TestFomModuleLoader : public ::testing::Test
{
protected:
   FomModuleLoader _moduleLoader;
};

TEST_F(TestFomModuleLoader, getFomModuleSet)
{
   std::vector<std::wstring> moduleNameVec{L"test.xml"};
   FomModuleSet moduleSet = _moduleLoader.getFomModuleSet(moduleNameVec);
   EXPECT_EQ(moduleSet.size(), 1);
}

TEST_F(TestFomModuleLoader, getFomModule)
{
   FomModule fomModule = _moduleLoader.getFomModule(L"file://./MockModule.xml");
   EXPECT_EQ(fomModule.getType(), FomModule::Type::FILE);
   EXPECT_EQ(fomModule.getFileName(), L"./MockModule.xml");
   EXPECT_FALSE(fomModule.getFileContent().empty());

   fomModule = _moduleLoader.getFomModule(L"https://example.invalid/foms/RPR-Base_v2.0.xml");
   EXPECT_EQ(fomModule.getType(), FomModule::Type::URL);
   EXPECT_EQ(fomModule.getUrl(), L"https://example.invalid/foms/RPR-Base_v2.0.xml");
   EXPECT_FALSE(fomModule.getUrl().empty());
}

TEST_F(TestFomModuleLoader, getFomModuleFromFile)
{
   std::stringstream mockModuleStream;
   mockModuleStream << R"(<?xml version="1.0" encoding="UTF-8" standalone="yes"?>)" << std::endl;
   mockModuleStream << "<objectModel />" << std::endl;

   FomModule fomModule = _moduleLoader.getFomModuleFromFile(L"MockModule.xml", mockModuleStream);
   EXPECT_EQ(fomModule.getType(), FomModule::Type::FILE);
   EXPECT_EQ(fomModule.getFileName(), L"MockModule.xml");
   EXPECT_FALSE(fomModule.getFileContent().empty());
}

TEST_F(TestFomModuleLoader, getFomModuleFromURI)
{
   FomModule fomModule =_moduleLoader.getFomModuleFromURI(L"https://example.invalid/foms/RPR-Base_v2.0.xml");
   EXPECT_EQ(fomModule.getType(), FomModule::Type::URL);
   EXPECT_EQ(fomModule.getUrl(), L"https://example.invalid/foms/RPR-Base_v2.0.xml");
   EXPECT_FALSE(fomModule.getUrl().empty());
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)