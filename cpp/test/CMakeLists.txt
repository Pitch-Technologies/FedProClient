#  Copyright (C) 2023 Pitch Technologies AB
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#  http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.


find_package(Protobuf 3.21.0...<3.22.0 CONFIG)
if (NOT Protobuf_FOUND)
   message(STATUS "protobuf not found, skipping ${CMAKE_CURRENT_SOURCE_DIR}")
   return()
endif()

# Create an executable and link it with the target project
add_executable(gtest_fedpro_2010
      # Evolved-specific services tests
      services-2010/TestRTIambassadorClientEvolved.cpp
      # Statically link librti dependency
      $<TARGET_OBJECTS:fedpro_librti1516e>
)

fedpro_set_target_compile_options(gtest_fedpro_2010 ${HLA_1516_2010_FEDPRO_INCLUDE})

# Create an executable and link it with the target project
add_executable(gtest_fedpro_2024
      # FedPro client tests initializer
      StaticTestsInitializer.cpp
      StaticTestsInitializer.h
      # Helpers
      utility/LocalRateLimiter.cpp
      utility/LocalRateLimiter.h
      utility/MemoryTransport.cpp
      utility/MemoryTransport.h
      utility/MemorySocket.cpp
      utility/MemorySocket.h
      utility/PacketFactory.cpp
      utility/PacketFactory.h
      # Utility tests
      utility/TestBit.cpp
      utility/TestConcurrentHashMap.cpp
      utility/TestInterruptibleCondition.cpp
      utility/TestLoggerInitializer.cpp
      utility/TestStringUtil.cpp
      utility/TestString_view.cpp
      utility/TestThreadPool.cpp
      utility/TestTimeoutTimer.cpp
      # Transport tests
      transport/TestTcpSocket.cpp
      # Session tests
      session/TestAtomicSequenceNumber.cpp
      session/TestByteWriter.cpp
      session/TestCircularBuffer.cpp
      session/TestHistoryBuffer.cpp
      session/TestRateLimitedBuffer.cpp
      session/TestRoundRobinBuffer.cpp
      session/TestSequenceNumber.cpp
      session/TestSession.cpp
      # Services tests
      services/TestClientConverter.h
      services/TestClientConverter_Handles.cpp
      services/TestClientConverter_Objects.cpp
      services/TestClientConverter_Objects.h
      services/TestClientConverter_Responses.cpp
      services/TestClientConverter_strings_enums.cpp
      services/TestClientGenericBase.cpp
      services/TestExtractAndRemoveClientSettings.cpp
      services/TestFomModule.cpp
      services/TestLibRti.cpp
      services/TestSettingsParser.cpp
      services/type_traits_util.cpp
      # HLA4-specific services tests
      services-2024/TestRTIAmbassador.cpp
      services-2024/TestRTIAmbassadorClientHla4.cpp
      # Statically link librti dependency
      $<TARGET_OBJECTS:fedpro_librti1516_4>
)

fedpro_set_target_compile_options(gtest_fedpro_2024 ${HLA_1516_202X_FEDPRO_INCLUDE})

target_include_directories(gtest_fedpro_2010
      BEFORE PRIVATE
      .
)
target_include_directories(gtest_fedpro_2024
      BEFORE PRIVATE
      .
)

target_compile_definitions(gtest_fedpro_2010
      PRIVATE
      RTI_DISABLE_WARNINGS
      RTI_NAMESPACE=rti1516e
      RTI_HLA_VERSION=2010
      STATIC_RTI # Statically link librti dependency
)

target_compile_definitions(gtest_fedpro_2024
      PRIVATE
      RTI_DISABLE_WARNINGS
      RTI_NAMESPACE=rti1516_202X
      RTI_HLA_VERSION=2024
      STATIC_RTI # Statically link librti dependency
)

target_link_libraries(gtest_fedpro_2010
      PRIVATE
      protobuf::libprotobuf
      gtest_main
)
target_link_libraries(gtest_fedpro_2024
      PRIVATE
      protobuf::libprotobuf
      gtest_main
)

if (MSVC)
   #set_target_properties(gtest_fedpro PROPERTIES LINK_FLAGS "/ignore:4099")
   # Silence warning caused by third_party/google/protobuf/arena_impl.h(305).
   target_compile_definitions(gtest_fedpro_2010 PRIVATE _SCL_SECURE_NO_WARNINGS)
   target_compile_definitions(gtest_fedpro_2024 PRIVATE _SCL_SECURE_NO_WARNINGS)
endif ()

include(GoogleTest)
# Increase DISCOVERY_TIMEOUT above default (5 sec) due to slow program execution in some environment.
#gtest_discover_tests(gtest_fedpro_2010 PROPERTIES DISCOVERY_TIMEOUT 30)
#gtest_discover_tests(gtest_fedpro_2024 PROPERTIES DISCOVERY_TIMEOUT 30)

# Copy the Mock Module file to the determined output path after the build process is complete.
add_copy_file_to_binary_dir(
      gtest_fedpro_2024
      "${CMAKE_CURRENT_SOURCE_DIR}/MockModule.xml"
)
