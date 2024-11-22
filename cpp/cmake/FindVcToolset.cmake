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


#
# FindVcToolset.cmake
#
# Locates an installed Visual Studio version that can provide the requested
# toolset version.
#
# The version specified when calling find_package() controls which version of
# Visual Studio we will search for. If no version is specified, the most modern
# compiler available is selected.
#
#    find_package(VcToolset 140 REQUIRED)
#
# CMAKE_SYSTEM_PROCESSOR controls which architecture we will build for.
#
# Sets the following variables:
#
#   VcToolset_FOUND          - TRUE if we found what we were looking for.
#   VcToolset_ROOT_DIR       - Path to the Visual Studio installation.
#   VcToolset_VCVARS_FILE    - Path to the suitable vcvars.bat script.
#   VcToolset_VCVARS_WRAPPER - A wrapper script that inserts a call to the
#                              vcvars script before subsequent commands.
#   VcToolset_VERSION        - The toolset version that was found.
#


# HELPER FUNCTIONS (Only called internally within this file) ___________________

function(get_msvs_version_from_toolset out_msvs_version toolset_version)
   # See https://en.wikipedia.org/wiki/Microsoft_Visual_C%2B%2B#Internal_version_numbering

   if (toolset_version LESS_EQUAL "140")  # VS 6.0 - VS 2015
      # Discard the last figure
      # toolset = 80 => Visual Studio version = 8
      string(REGEX REPLACE "[01]$" "" msvs_version ${toolset_version})
   elseif (toolset_version EQUAL 141)  # VS 2017
      set(msvs_version "15")
   elseif (toolset_version EQUAL 142)  # VS 2019
      set(msvs_version "16")
   elseif (toolset_version EQUAL 143)  # VS 2022
      set(msvs_version "17")
   else ()
      message(FATAL_ERROR "Toolset [${toolset_version}] is not supported.")
   endif ()

   set(${out_msvs_version} ${msvs_version} PARENT_SCOPE)
endfunction()


function(get_vc_toolset_from_msvs_version out_toolset_version msvs_version)
   if (msvs_version VERSION_LESS "15.0")  # VS 6.0 - VS 2015
      set(toolset "${msvs_version}0")
   elseif (msvs_version VERSION_LESS "16.0")  # VS 2017
      set(toolset "141")
   elseif (msvs_version VERSION_LESS "17.0")  # VS 2019
      set(toolset "142")
   elseif (msvs_version VERSION_LESS "18.0")  # VS 2022
      set(toolset "143")
   else ()
      message(FATAL_ERROR "Visual Studio version [${msvs_version}] is not supported.")
   endif ()

   set(${out_toolset_version} ${toolset} PARENT_SCOPE)
endfunction()


function(get_msvs_version_at_path out_version msvs_path)
   execute_process(
         COMMAND
         vswhere -nologo -path ${msvs_path} -property installationVersion
         WORKING_DIRECTORY
         "$ENV{ProgramFiles\(x86\)}\\Microsoft Visual Studio\\Installer"
         OUTPUT_VARIABLE
         msvs_version
         RESULT_VARIABLE
         result
         OUTPUT_STRIP_TRAILING_WHITESPACE
   )

   if (NOT result EQUAL 0)
      message(WARNING "vswhere was unable to determine the version of Visual Studio installed at ${msvs_path}")
   endif ()

   set(${out_version} ${msvs_version} PARENT_SCOPE)
endfunction()


function(find_path_to_msvs msvs_version out_path)
   if (msvs_version STREQUAL "latest")
      set(args "-latest")
   else ()
      math(EXPR next_version "${msvs_version} + 1")
      set(args "-version;[${msvs_version},${next_version})")
   endif ()

   execute_process(
         COMMAND
         vswhere -nologo ${args} -legacy -property installationPath
         WORKING_DIRECTORY
         "$ENV{ProgramFiles\(x86\)}\\Microsoft Visual Studio\\Installer"
         OUTPUT_VARIABLE
         msvs_path
         RESULT_VARIABLE
         result
         OUTPUT_STRIP_TRAILING_WHITESPACE
   )

   if (NOT result EQUAL 0)
      message(WARNING "vswhere returned ${_result} when trying to find MSVS version ${msvs_version}")
   endif ()

   set(${out_path} ${msvs_path} PARENT_SCOPE)
endfunction()


# Generates a .bat file that executes vcvars to set the MSVS environment before
# running the given arguments.
function(generate_vcvars_wrapper _vcvars _arch)
   set(_in "${CMAKE_BINARY_DIR}/${CMAKE_FILES_DIRECTORY}/vcvars_wrapper.bat.in")
   file(WRITE ${_in} "call \"${_vcvars}\" ${_arch}
%*
")

   get_filename_component(_basename ${_vcvars} NAME_WE)
   set(_out "${CMAKE_BINARY_DIR}/${CMAKE_FILES_DIRECTORY}/${_basename}_wrapper.bat")
   configure_file(${_in} ${_out} @ONLY)

   set(VcToolset_VCVARS_WRAPPER ${_out} PARENT_SCOPE)
endfunction()


# MAIN SCRIPT __________________________________________________________________

if (CMAKE_SYSTEM_PROCESSOR MATCHES "(x86_64|amd64|x64)")
   set(_msvs_arch amd64)
else ()
   set(_msvs_arch x86)
endif ()

if (VcToolset_FIND_VERSION)
   if (NOT VcToolset_FIND_VERSION_EXACT)
      message(FATAL_ERROR "VcToolset requires EXACT to be specified when calling find_package(VcToolset) with a version number.")
   endif ()
   get_msvs_version_from_toolset(_msvs_version "${VcToolset_FIND_VERSION}")
else ()
   message(STATUS "No VC toolset version specified, using the latest version available")
   set(_msvs_version "latest")
endif ()

find_path_to_msvs(${_msvs_version} _msvs_path)

if (_msvs_path)
   set(VcToolset_ROOT_DIR ${_msvs_path})
   if (NOT DEFINED VcToolset_FIND_VERSION)
      # For older versions of Visual Studio, there is a risk that vswhere will
      # not be able to identify the VS version that is installed at a given
      # path. For this reason, we only try to read it when the user accepts
      # any version, case in which we do need to state what we have chosen.
      get_msvs_version_at_path(_msvs_version ${_msvs_path})
   endif ()
else ()
   set(VcToolset_ROOT_DIR "VcToolset_ROOT_DIR-NOTFOUND")
endif ()

get_vc_toolset_from_msvs_version(VcToolset_VERSION ${_msvs_version})

find_program(VcToolset_VCVARS_FILE
      NAMES vcvarsall.bat
      HINTS ${_msvs_path}
      PATH_SUFFIXES
      VC
      VC/bin
      VC/Auxiliary/Build
      DOC "Visual Studio environment configuration script"
)

if (VcToolset_VCVARS_FILE)
   generate_vcvars_wrapper(${VcToolset_VCVARS_FILE} ${_msvs_arch})
endif ()

unset(_msvs_arch)
unset(_msvs_path)
unset(_msvs_version)

include(FindPackageHandleStandardArgs)
find_package_handle_standard_args(VcToolset
      REQUIRED_VARS
      VcToolset_ROOT_DIR
      VcToolset_VCVARS_FILE
)
