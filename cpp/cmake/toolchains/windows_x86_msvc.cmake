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

# Good posts on peculiarities of building on Windows with CMake:
# - https://stackoverflow.com/questions/28350214/how-to-build-x86-and-or-x64-on-windows-from-command-line-with-cmake
# - https://blog.feabhas.com/2021/09/cmake-part-4-windows-10-host/CMake_Command_Line
# - https://stackoverflow.com/questions/31262342/cmake-g-ninja-on-windows-specify-x64

set(CMAKE_SYSTEM_NAME Windows)
set(CMAKE_SYSTEM_PROCESSOR i386)
if (NOT DEFINED CMAKE_C_COMPILER)
   set(CMAKE_C_COMPILER cl.exe)
endif ()
if (NOT DEFINED CMAKE_CXX_COMPILER)
   set(CMAKE_CXX_COMPILER cl.exe)
endif ()

# Set flags that are compiler specific
set(CXX_FLAGS_FEDPRO_EVOLVED
      /W3
      # /WX # Uncomment if you want to treat warnings as errors
)
set(FEDPRO_CXX_FLAGS_HLA4
      /W3
      # /WX # Uncomment if you want to treat warnings as errors
)



# Load MSVC environment
include(${CMAKE_CURRENT_LIST_DIR}/msvc.cmake)

# Set environment variables to enable MSVC
enable_vc_toolset("x86")

# Adjust CMake environment to find msvc headers & resolve libraries
if (CMAKE_GENERATOR MATCHES "Visual Studio")

   # The Visual Studio generator automatically find paths (include, lib), given a toolset version.
   # Use option CACHE to workaround cmake issue #19409 https://gitlab.kitware.com/cmake/cmake/-/issues/19409
   set(CMAKE_GENERATOR_PLATFORM "Win32" CACHE INTERNAL "")
   if (DEFINED ENV{FEDPRO_VC_TOOLSET})
      set(CMAKE_GENERATOR_TOOLSET "v$ENV{FEDPRO_VC_TOOLSET},host=x86")
   else ()
      # If no toolset is specified, use v140
      set(CMAKE_GENERATOR_TOOLSET "v140,host=x86")
   endif ()

else ()
   # For other generators, explicitly set paths
   set(CMAKE_C_STANDARD_INCLUDE_DIRECTORIES $ENV{INCLUDE})
   set(CMAKE_CXX_STANDARD_INCLUDE_DIRECTORIES $ENV{INCLUDE})
   link_directories($ENV{LIB})
endif ()
