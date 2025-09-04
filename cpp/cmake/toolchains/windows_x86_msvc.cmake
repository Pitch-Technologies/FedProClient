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

set(FEDPRO_CXX_FLAGS_EVOLVED
      # Sets the level of Microsoft Visual C++ (MSVC) messages to warning level 3
      /W3
      # C4275: An exported class was derived from a class that wasn't exported.
      # It is safe to ignore since we require building libraries and applications using the same compiler and settings.
      /wd4275
      # C4800: A value is implicitly converted into type bool.
      # Silence this warning coming from protobuf-generated code.
      /wd4800
      # C4996: Use a function, class member, variable, or typedef that's marked deprecated.
      # Silence this warning since the HLA Evolved API includes std::auto_ptr and others marked deprecated.
      /wd4996
)
set(FEDPRO_CXX_FLAGS_HLA4
      # Sets the level of Microsoft Visual C++ (MSVC) messages to warning level 3
      /W3
      # C4275: An exported class was derived from a class that wasn't exported.
      # It is safe to ignore since we require building libraries and applications using the same compiler and settings.
      /wd4275
      # C4996: Use a function, class member, variable, or typedef that's marked deprecated.
      # Silence this warning since the HLA Evolved API includes std::auto_ptr and others marked deprecated.
      /wd4800
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
