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


# This is the internal build script for the sample.

# Get useful system information used for naming the chat sample executable.
include(../cmake/FedProAppHelpers.cmake)
get_cpp_abi_id(cpp_api_id)

# Create a chat sample federate executable.
add_executable(chat-cpp-1516e-fedpro src/Chat.cpp)

# Link against a fedpro client library which exposes the standard HLA Evolved C++ API.
target_link_libraries(chat-cpp-1516e-fedpro PRIVATE fedpro_librti1516e)

# Signed binaries on macOS ignore all environment variables starting with DYLD.
# Here, we hardcode the run-path (RPATH) for the sample to relative locations in the
# installed layout.
set_target_properties(chat-cpp-1516e-fedpro
      PROPERTIES
      OUTPUT_NAME chat-cpp-1516e-fedpro_${cpp_api_id}
      INSTALL_RPATH "${RPATH_BASE}/../../fedpro/lib/${cpp_api_id};${RPATH_BASE}/../../lib"
)

# Specify compiler and linker flags
target_compile_options(chat-cpp-1516e-fedpro PRIVATE ${FEDPRO_CXX_FLAGS_EVOLVED})

# Some files need to be placed in the same directory as where the executable is placed.
# The following determines the path of this directory.
if (CMAKE_CONFIGURATION_TYPES)
   # The path of the output directory contains the name of the configuration used (e.g., "Release" or "Debug").
   set(OUTPUT_PATH "${CMAKE_CURRENT_BINARY_DIR}/$<CONFIG>")
else ()
   # The path of the output directory does not contain the name of the configuration used.
   set(OUTPUT_PATH "${CMAKE_CURRENT_BINARY_DIR}")
endif ()

# Copy the xml file to the determined output path after the build process is complete.
add_copy_file_to_binary_dir(
      chat-cpp-1516e-fedpro
      "${CMAKE_CURRENT_SOURCE_DIR}/Chat-evolved.xml"
)

if (WIN32)
   # Windows does not have rpath. To be able to run the executable, the runtime libraries must be in the same directory.
   # This custom command will copy the rti1516 library to the target directory after each incremental compilation.
   add_copy_target_to_binary_dir(
         chat-cpp-1516e-fedpro
         fedpro_librti1516e
   )
endif ()

set(sample_path_1516e "$<CONFIG>/samples/chat-cpp-1516e-fedpro")
install(
      TARGETS chat-cpp-1516e-fedpro
      DESTINATION ${sample_path_1516e}
      COMPONENT fedproclient_samples OPTIONAL EXCLUDE_FROM_ALL
)
install(
      FILES "Chat-evolved.xml"
      DESTINATION ${sample_path_1516e}
      COMPONENT fedproclient_samples OPTIONAL EXCLUDE_FROM_ALL
)
install(
      FILES "src/Chat.cpp"
      DESTINATION "${sample_path_1516e}/src"
      COMPONENT fedproclient_samples OPTIONAL EXCLUDE_FROM_ALL
)
install(
      FILES "Readme.md"
      DESTINATION "${sample_path_1516e}"
      COMPONENT fedproclient_samples OPTIONAL EXCLUDE_FROM_ALL
)
