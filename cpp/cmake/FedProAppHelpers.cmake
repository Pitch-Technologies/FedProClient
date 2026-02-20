#  Copyright (C) 2025-2026 Pitch Technologies AB
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
# Helper functions for building and running Federate Protocol C++ applications.
#

include(${CMAKE_CURRENT_LIST_DIR}/FedProTargetHelpers.cmake)

#
# Returns an identifier for the C++ Application binary interface (ABI)
#
function(get_cpp_abi_id _abi_id)

   hla_get_compiler_id(compiler_id compiler_version)
   if (CMAKE_SYSTEM_PROCESSOR MATCHES "(x86_64|amd64|x64)" AND NOT APPLE)
      set(${_abi_id} "${compiler_id}${compiler_version}_64" PARENT_SCOPE)
   elseif (CMAKE_SYSTEM_PROCESSOR MATCHES "(x86|i386)$")
      set(${_abi_id} "${compiler_id}${compiler_version}_32" PARENT_SCOPE)
   else ()
      set(${_abi_id} "${compiler_id}${compiler_version}" PARENT_SCOPE)
   endif()

endfunction()
