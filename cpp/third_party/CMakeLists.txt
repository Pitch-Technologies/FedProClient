cmake_minimum_required(VERSION 3.14)

include(FetchContent)
FetchContent_Declare(
   googletest
   SOURCE_DIR ${CMAKE_CURRENT_SOURCE_DIR}/googletest
   BINARY_DIR ${CMAKE_CURRENT_BINARY_DIR}/googletest
   # Builds the googlemock subproject
   CMAKE_ARGS -DBUILD_GMOCK:BOOL=ON
   # Disable installation of googletest
   CMAKE_ARGS -DINSTALL_GTEST:BOOL=OFF
   # This seems necessary to link unit tests correctly in Windows, otherwise we get
   # the following kind of errors:
   # LNK2038: mismatch detected for 'RuntimeLibrary': value 'MTd_StaticDebug' doesn't match value 'MDd_DynamicDebug'
   CMAKE_ARGS -Dgtest_force_shared_crt:BOOL=ON
)
FetchContent_MakeAvailable(googletest)
