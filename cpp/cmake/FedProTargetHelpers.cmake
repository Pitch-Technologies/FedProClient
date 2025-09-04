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
# Helper macros for building the Federate Protocol C++ client library.
#

include(TestBigEndian)

#
# Returns the name and version of the current compiler following the naming
# conventions of the HLA Library Nomenclature document.
#
function(hla_get_compiler_id out_compiler out_version)
   set(${out_compiler} PARENT_SCOPE)
   set(${out_version} PARENT_SCOPE)

   string(REGEX MATCH "^[0-9]+" _compiler_version_major ${CMAKE_CXX_COMPILER_VERSION})

   if(CMAKE_CXX_COMPILER_ID STREQUAL "MSVC")
      set(${out_compiler} vc PARENT_SCOPE)
      set(${out_version} ${MSVC_TOOLSET_VERSION} PARENT_SCOPE)

   elseif(CMAKE_CXX_COMPILER_ID MATCHES "Clang")  # There's Clang and AppleClang
      set(${out_compiler} clang PARENT_SCOPE)
      set(${out_version} ${_compiler_version_major} PARENT_SCOPE)

   elseif(CMAKE_CXX_COMPILER_ID STREQUAL "GNU")
      set(${out_compiler} gcc PARENT_SCOPE)
      set(${out_version} ${_compiler_version_major} PARENT_SCOPE)

   else()
      string(TOLOWER "${CMAKE_CXX_COMPILER_ID}" _compiler_id_lower)
      set(${out_compiler} ${_compiler_id_lower} PARENT_SCOPE)
      set(${out_version} ${_compiler_version_major} PARENT_SCOPE)

   endif()
endfunction()

function(get_current_output_dir _var)
   # The following determines the default target output directory.
   if (CMAKE_CONFIGURATION_TYPES)
      # The path of the output directory contains the name of the configuration used (e.g., "Release" or "Debug").
      set(${_var} "${CMAKE_CURRENT_BINARY_DIR}/$<CONFIG>" PARENT_SCOPE)
   else ()
      # The path of the output directory does not contain the name of the configuration used.
      set(${_var} "${CMAKE_CURRENT_BINARY_DIR}" PARENT_SCOPE)
   endif ()
endfunction()

function(hla_get_target_suffix _rti_hla_version output_suffix)
   string(TOLOWER "${CMAKE_SYSTEM_PROCESSOR}" _processor_lower)
   if (${_rti_hla_version} LESS 2025)
      if (_processor_lower MATCHES "^(x86_64|amd64|x64)$")
         set(${output_suffix} "64" PARENT_SCOPE)
      else ()
         set(${output_suffix} "" PARENT_SCOPE)
      endif ()
   else () # _rti_hla_version >= 2025
      if (_processor_lower MATCHES "^(x86_64|amd64|x64)$")
         set(arch_suffix "")
      else ()
         set(arch_suffix "_32")
      endif ()
      hla_get_compiler_id(_compiler _compiler_version)
      set(${output_suffix} "${_compiler}${_compiler_version}${arch_suffix}" PARENT_SCOPE)
   endif ()
endfunction()

function(add_copy_file_to_binary_dir _target _file_src_path)
   # Some files need to be placed in the same directory as where the executable is placed.
   # The following determines the path of this directory.
   get_current_output_dir(_output_path)
   get_filename_component(FILENAME ${_file_src_path} NAME)
   set(fedpro_file_copy_target ${_target}_copy_${FILENAME})
   add_custom_target(${fedpro_file_copy_target}
         COMMAND ${CMAKE_COMMAND} -E copy_if_different "${_file_src_path}" "${_output_path}/${FILENAME}"
         DEPENDS "${_file_src_path}"
         BYPRODUCTS "${_output_path}/${FILENAME}"
         COMMAND_EXPAND_LISTS
         COMMENT "Copying FILE ${FILENAME} to ${_output_path}"
   )
   add_dependencies(${_target} ${fedpro_file_copy_target})
endfunction()

function(add_copy_target_to_binary_dir _target _src_target)
   # Some files need to be placed in the same directory as where the executable is placed.
   # The following determines the path of this directory.
   get_current_output_dir(_output_path)
   set(fedpro_file_copy_target ${_target}_copy_${_src_target})
   add_custom_target(${fedpro_file_copy_target}
         COMMAND ${CMAKE_COMMAND} -E copy_if_different "$<TARGET_FILE:${_src_target}>" "${_output_path}"
         DEPENDS ${_src_target}
         COMMAND_EXPAND_LISTS
         COMMENT "Copying TARGET ${_src_target} to ${_output_path}"
   )
   add_dependencies(${_target} ${fedpro_file_copy_target})
endfunction()


function(fedpro_set_target_compile_options _target _rti_include_path)

   if (CMAKE_CXX_BYTE_ORDER STREQUAL "")
      TEST_BIG_ENDIAN(IS_BIG_ENDIAN)
      if (IS_BIG_ENDIAN)
         set(CMAKE_CXX_BYTE_ORDER "BIG_ENDIAN")
      else ()
         set(CMAKE_CXX_BYTE_ORDER "LITTLE_ENDIAN")
      endif ()
   endif ()

   # TODO Use std::endian when moving to C++20
   if (CMAKE_CXX_BYTE_ORDER STREQUAL "BIG_ENDIAN")
      target_compile_definitions(${_target} PUBLIC BYTE_ORDER_IS_BIG)
   elseif (CMAKE_CXX_BYTE_ORDER STREQUAL "LITTLE_ENDIAN")
      target_compile_definitions(${_target} PUBLIC BYTE_ORDER_IS_LITTLE)
   endif ()

   # The level of the least severe log messages that will be possible to log.
   if (CMAKE_BUILD_TYPE STREQUAL "Release")
      target_compile_definitions(${_target} PRIVATE SPDLOG_ACTIVE_LEVEL=SPDLOG_LEVEL_DEBUG)
   else ()
      # if CMAKE_BUILD_TYPE not defined, make all levels available
      target_compile_definitions(${_target} PRIVATE SPDLOG_ACTIVE_LEVEL=SPDLOG_LEVEL_TRACE)
   endif ()

   # Define include search directories for this target
   target_include_directories(${_target}
         PRIVATE
         ${CMAKE_CURRENT_FUNCTION_LIST_DIR}/../src
         ${CMAKE_CURRENT_FUNCTION_LIST_DIR}/../src/fedpro
         ${CMAKE_CURRENT_FUNCTION_LIST_DIR}/../include
   )
   # Add include directory as SYSTEM to silence warnings in third party headers.
   target_include_directories(${_target}
         SYSTEM PRIVATE
         ${CMAKE_CURRENT_FUNCTION_LIST_DIR}/../third_party
         ${_rti_include_path}
   )

endfunction()


function(fedpro_set_librti_target_options _target _rti_namespace _rti_hla_version _rti_include_path)
   fedpro_set_target_compile_options(${_target} ${_rti_include_path})

   # Define macros
   target_compile_definitions(${_target}
         PRIVATE
         BUILD_SHARED_LIBS=ON
         BUILDING_RTI # Export RTI symbols, including RTIambassador symbols.
         BUILDING_FEDPRO # Export FedPro symbols
         STATIC_FEDTIME # Statically link LogicalTimeFactoryFactory to avoid circular librti-libfedtime dependency
         RTI_DISABLE_WARNINGS
         RTI_NAMESPACE=${_rti_namespace} # Select RTI namespace
         RTI_HLA_VERSION=${_rti_hla_version}
   )

   if (WIN32)
      # This setting is required to build CppSockets on windows.
      target_compile_definitions(${_target}
            PRIVATE
            UNICODE
            _UNICODE
      )
   endif ()

   if (MSVC)
      # Required for protobuf headers processing
      target_compile_options(${_target} PRIVATE /bigobj)
      # Silence warning caused by third_party/google/protobuf/arena_impl.h(305).
      target_compile_definitions(${_target} PRIVATE _SCL_SECURE_NO_WARNINGS)
   endif ()

   # Define interface include directories for this target
   target_include_directories(${_target}
         INTERFACE
         ${CMAKE_CURRENT_FUNCTION_LIST_DIR}/../include
   )
   # Add RTI include directory as SYSTEM to silence depreciation warnings in third party headers.
   target_include_directories(${_target}
         SYSTEM INTERFACE
         ${_rti_include_path}
   )

   target_link_libraries(${_target}
         PRIVATE
         protobuf::libprotobuf
   )

endfunction()