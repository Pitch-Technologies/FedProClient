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


include(${CMAKE_CURRENT_LIST_DIR}/common.cmake)

set(CMAKE_SYSTEM_NAME Linux)
set(CMAKE_SYSTEM_PROCESSOR arm64)
if (NOT DEFINED CMAKE_C_COMPILER)
   set(CMAKE_C_COMPILER gcc)
endif ()
if (NOT DEFINED CMAKE_CXX_COMPILER)
   set(CMAKE_CXX_COMPILER g++)
endif ()

set(RPATH_BASE $ORIGIN)

# Explicitly tell gcc to build for the arm64/aarch64 architecture
set(CMAKE_CXX_FLAGS "-march=armv8-a ${CMAKE_CXX_FLAGS}")

# Set flags that are compiler specific
set(FEDPRO_CXX_FLAGS_EVOLVED
      -Wno-deprecated-declarations # Suppress warnings about deprecated declarations
)
set(FEDPRO_CXX_FLAGS_HLA4
      -Werror # Treats all warnings as errors
)
