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
get_cpp_abi_id(abi_id)

# Create a chat sample federate executable.
add_executable(chat-cpp-hla4-fedpro src/Chat.cpp)

# Link against a fedpro client library which exposes the HLA 4 C++ API.
target_link_libraries(chat-cpp-hla4-fedpro PRIVATE fedpro_librti1516_4)

# Signed binaries on macOS ignore all environment variables starting with DYLD.
# Here, we hardcode the run-path (RPATH) for the sample to relative locations in the
# installed layout.
set_target_properties(chat-cpp-hla4-fedpro
      PROPERTIES
      OUTPUT_NAME chat-cpp-hla4-fedpro_${abi_id}
      INSTALL_RPATH "${RPATH_BASE}/../../fedpro/lib;${RPATH_BASE}/../../lib"
)

# Copy the xml file to the determined output path after the build process is complete.
add_copy_file_to_binary_dir(
      chat-cpp-hla4-fedpro
      "${CMAKE_CURRENT_SOURCE_DIR}/Chat-hla4.xml"
)

if (WIN32)
   # Windows does not have rpath. To be able to run the executable, the runtime libraries must be in the same directory.
   # This custom command will copy the rti1516 library to the target directory after each incremental compilation.
   add_copy_target_to_binary_dir(
         chat-cpp-hla4-fedpro
         fedpro_librti1516_4
   )
endif ()

set(sample_path_hla4 "$<CONFIG>/samples/chat-cpp-hla4-fedpro")
install(
      TARGETS chat-cpp-hla4-fedpro
      DESTINATION ${sample_path_hla4}
      COMPONENT fedproclient_samples OPTIONAL EXCLUDE_FROM_ALL
)
install(
      FILES "Chat-hla4.xml"
      DESTINATION ${sample_path_hla4}
      COMPONENT fedproclient_samples OPTIONAL EXCLUDE_FROM_ALL
)
install(
      FILES "src/Chat.cpp"
      DESTINATION "${sample_path_hla4}/src"
      COMPONENT fedproclient_samples OPTIONAL EXCLUDE_FROM_ALL
)
install(
      FILES "Readme.md"
      DESTINATION "${sample_path_hla4}"
      COMPONENT fedproclient_samples OPTIONAL EXCLUDE_FROM_ALL
)
