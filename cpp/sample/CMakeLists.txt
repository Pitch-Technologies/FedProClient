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
include(../cmake/FedProTargetHelpers.cmake)
hla_get_target_suffix(2024 HLA_2024_SUFFIX)

# Create a chat sample federate executable.
add_executable(chat-cpp-hla4-fedpro Chat.cpp)

# Link the executable with the specified HLA library.
target_link_libraries(chat-cpp-hla4-fedpro PRIVATE fedpro_librti1516_4)

# Signed binaries on macOS ignore all environment variables starting with DYLD.
# Here, we hardcode the run-path (RPATH) for the sample to relative locations in the
# installed layout.
set_target_properties(chat-cpp-hla4-fedpro
      PROPERTIES
      OUTPUT_NAME chat-cpp-hla4-fedpro_${HLA_2024_SUFFIX}
      INSTALL_RPATH "${RPATH_BASE}/../../fedpro/lib;${RPATH_BASE}/../../lib"
)

# Copy the xml file to the determined output path after the build process is complete.
add_copy_file_to_binary_dir(
      chat-cpp-hla4-fedpro
      "${CMAKE_CURRENT_SOURCE_DIR}/Chat-evolved.xml"
)

if (WIN32)
   # Windows does not have rpath. To be able to run the executable, the runtime libraries must be in the same directory.
   # This custom command will copy the rti1516 library to the target directory after each incremental compilation.
   add_copy_target_to_binary_dir(
         chat-cpp-hla4-fedpro
         fedpro_librti1516_4
   )
endif ()
