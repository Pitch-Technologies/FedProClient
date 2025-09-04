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


# msvc.cmake

# We have to pass FEDPRO_VC_TOOLSET through the environment because toolchain
# files shall not rely on the variable cache. This has to do with CMake creating
# other CMake projects internally during project configuration for probing
# system capabilities. Variables in the cache do not reach these subprojects;
# environment variables do.
if (DEFINED ENV{FEDPRO_VC_TOOLSET})
   set(exact "EXACT")
else ()
   if (FEDPRO_REQUIRE_VC_TOOLSET)
      message(FATAL_ERROR
            " FEDPRO_VC_TOOLSET environment variable not defined.\n"
            " This variable controls which version of Visual Studio will be used for the build.\n"
            " Make sure you are using one of the presets defined in CMakePresets.json or that you are setting up the environment correctly.\n"
            " Example:\n"
            "     FEDPRO_VC_TOOLSET=140\n"
      )
   endif ()
   set(exact)
endif ()

# If FEDPRO_VC_TOOLSET is not defined, we are just calling FindVcToolset with
# no version. If it is, we are passing that in and asking for an exact match.
# This find_package command is deliberately not specified inside any function,
# since it needs to affect the global CMake scope.
find_package(VcToolset $ENV{FEDPRO_VC_TOOLSET} ${exact} REQUIRED)


# Function enable_vc_toolset:
# Runs vcvarsall.bat and loads the MSVC environment variables into the CMake context.
function(enable_vc_toolset _architecture)

   # CMake doesn't seem to set the VS_PLATFORM_TOOLSET variable when we set the
   # compilers from a toolchain.
   set(CMAKE_VS_PLATFORM_TOOLSET "v${VcToolset_VERSION}" PARENT_SCOPE)

   execute_process(COMMAND
         ${VcToolset_VCVARS_WRAPPER} set
         OUTPUT_VARIABLE env_dump OUTPUT_STRIP_TRAILING_WHITESPACE)

   # 1. Escape troublesome chars
   string(REPLACE ";" "__semicolon__" env_dump "${env_dump}")
   string(REPLACE "\\" "__backslash__" env_dump "${env_dump}")
   string(REPLACE "\"" "__doublequote__" env_dump "${env_dump}")

   # 2. Multi-line => one line
   string(REGEX REPLACE "[\r\n]+" ";" env_dump "${env_dump}")

   # 3. Keep only lines looking like xx=yy
   list(FILTER env_dump INCLUDE REGEX ".+=.+")

   # 4. Set captured environment variables right here
   foreach (key_value ${env_dump})
      string(REPLACE "=" ";" key_value_as_list ${key_value})
      list(GET key_value_as_list 0 key)
      list(GET key_value_as_list 1 value)

      string(REPLACE "__semicolon__" "\;" key "${key}")
      string(REPLACE "__backslash__" "\\" key "${key}")
      string(REPLACE "__doublequote__" "\"" key "${key}")

      string(REPLACE "__semicolon__" ";" value "${value}")
      string(REPLACE "__backslash__" "\\" value "${value}")
      string(REPLACE "__doublequote__" "\"" value "${value}")

      set(ENV{${key}} "${value}")
   endforeach ()

   # 5. Adjust CMake environment to find msvc headers & resolve libraries
   cmake_path(APPEND WindowsSDK_dir "$ENV{WindowsSdkDir}" "bin" "$ENV{WindowsSDKVersion}" "${_architecture}")
   if (IS_DIRECTORY ${WindowsSDK_dir})
      set(ENV{PATH} "$ENV{PATH};${WindowsSDK_dir}")
   endif ()

endfunction()
