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


cmake_minimum_required(VERSION 3.19)
project(fedpro_client LANGUAGES CXX)
set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_EXTENSIONS OFF)
set(CMAKE_POSITION_INDEPENDENT_CODE ON)

find_package(Protobuf 3.21.0...<3.22.0 CONFIG)
if (NOT Protobuf_FOUND)
   message(STATUS "protobuf not found, skipping ${CMAKE_CURRENT_SOURCE_DIR}")
   return()
endif ()

if (UNIX)
   # Uncomment for extra warnings
   #   set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wall -Wextra")
   #   if (CMAKE_CXX_COMPILER_ID STREQUAL "GNU" AND CMAKE_CXX_COMPILER_VERSION VERSION_GREATER_EQUAL 9.0)
   #      set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -Wredundant-move -Wpessimizing-move")
   #   endif ()
endif ()

get_filename_component(HLA_1516_2010_FEDPRO_INCLUDE "api/HLA_1516-2010" ABSOLUTE)
get_filename_component(HLA_1516_202X_FEDPRO_INCLUDE "api/HLA_1516-202X" ABSOLUTE)

# Adds library source files
add_subdirectory(src)
target_include_directories(fedpro_librti1516_4 PRIVATE third_party)

# Chat Samples
add_subdirectory(sample)
add_subdirectory(sample_evolved)

# Tests
add_subdirectory(test)
add_subdirectory(third_party/googletest googletest)

# CMake Install steps - moves all the files to the 'dist' directory

install(TARGETS fedpro_librti1516_4 DESTINATION "$<CONFIG>/lib")
install(TARGETS fedpro_libfedtime1516_4 DESTINATION "$<CONFIG>/lib")

install(TARGETS fedpro_librti1516e DESTINATION "$<CONFIG>/lib")
install(TARGETS fedpro_libfedtime1516e DESTINATION "$<CONFIG>/lib")

set(SAMPLE_PATH_4 "$<CONFIG>/samples/chat-cpp-hla4-fedpro")
install(TARGETS chat-cpp-hla4-fedpro DESTINATION ${SAMPLE_PATH_4})
install(FILES "sample/Chat-evolved.xml" DESTINATION ${SAMPLE_PATH_4})
install(FILES "sample/Chat.cpp" DESTINATION "${SAMPLE_PATH_4}/src")

set(SAMPLE_PATH_E "$<CONFIG>/samples/chat-cpp-1516e-fedpro")
install(TARGETS chat-cpp-1516e-fedpro DESTINATION ${SAMPLE_PATH_E})
install(FILES "sample_evolved/Chat-evolved.xml" DESTINATION ${SAMPLE_PATH_E})
install(FILES "sample_evolved/Chat.cpp" DESTINATION "${SAMPLE_PATH_E}/src")
