# Federate Protocol C++ client

## <a name="logging"></a>Logging

The C++ Federate protocol client library uses the header-only open source library [spdlog](https://github.com/gabime/spdlog) for writing log messages.
The library currently supports writing log messages to console and rotating files.
To configure the C++ client logger, the settings that have the prefix `log.` and that are specified in the settings section above may be used.
Log severity level may be specified for each log output destination separately using some of these settings.
The available severity levels are, from least verbose to most, `off`, `critical`, `error`, `warning`, `info`, `debug`, and `trace`.
The severity level `trace` is set to not be available for release builds, but that can be changed by assigning another value for the macro `SPDLOG_ACTIVE_LEVEL` and rebuilding the library.

## <a name="building"></a>Build instructions

This section provides instructions to build the C++ Federate Protocol client library and its dependencies.

Clone *Protocol Buffers* and the *Federate Protocol client library* repositories in a directory, side by side, as described below.

### Pre-requirements ###

Building the library requires *CMake* and *Protocol Buffers*. 

We suggest using *Protocol Buffers* version 3.21.x for compatibility with Visual Studio 2015. Note version 3.22.0 and above requires Visual Studio 2017 or later.

#### Obtain Protocol Buffers

The following command clones the *Protocol Buffers* repository in a new `protobuf` subdirectory.

```
git clone --depth=1 --branch=v3.21.12 https://github.com/protocolbuffers/protobuf.git
cd protobuf
```

Define the following environment variables:
* `CMAKE_PREFIX_PATH` to `$HOME/cmake-prefix"` (for Unix) or `%USERPROFILE%/cmake-prefix` (for Windows)
* `FEDPRO_CLIENT_REPO_PATH` to the absolute path where the FedPro client repository was cloned or downloaded.

To make these variables persist:
* For Linux, add lines with the syntax `export <VARIABLE_NAME>=<value>` to `/etc/profile.d/user.sh`
* For MacOS, add lines with the syntax `export <VARIABLE_NAME>=<value>` to `/Users/<user_home>/.zshrc`
* For Windows, open the Start menu, search for and open "Edit environment variables for your account", and then add the variables with their values.

The following CMake commands should be executed at the root of the cloned protobuf directory.

#### Build Protocol Buffers with Visual Studio 2022, V140 toolset, 64 bit

* Configure both Release and Debug builds

  ```
  cmake -B out/vc140-amd64/Debug --toolchain %FEDPRO_CLIENT_REPO_PATH%/cpp/cmake/toolchains/windows_amd64_msvc.cmake -DCMAKE_BUILD_TYPE=Debug -DCMAKE_INSTALL_PREFIX=%CMAKE_PREFIX_PATH%/protobuf-vc140-amd64 -Dprotobuf_MSVC_STATIC_RUNTIME=OFF -Dprotobuf_BUILD_TESTS=OFF
  cmake -B out/vc140-amd64/Release --toolchain %FEDPRO_CLIENT_REPO_PATH%/cpp/cmake/toolchains/windows_amd64_msvc.cmake -DCMAKE_BUILD_TYPE=Release -DCMAKE_INSTALL_PREFIX=%CMAKE_PREFIX_PATH%/protobuf-vc140-amd64 -Dprotobuf_MSVC_STATIC_RUNTIME=OFF -Dprotobuf_BUILD_TESTS=OFF
  ```

* Build and install the Debug configuration

  ```
  cmake --build out/vc140-amd64/Debug/ --config Debug
  cmake --install out/vc140-amd64/Debug/ --config Debug
  ```

* Build and install the Release configuration

  ```
  cmake --build out/vc140-amd64/Release/ --config Release
  cmake --install out/vc140-amd64/Release/ --config Release
  ```

#### Build Protocol Buffers with GCC 11, Ninja on Ubuntu Linux, amd64/x86_64

* Make sure Ninja is installed on your Ubuntu. To install it, run:

  ```
  sudo apt update
  sudo apt install ninja-build
  ```
  
* Configure both Release and Debug builds

  ```
  cmake -B out/gcc11-amd64/Debug --toolchain $FEDPRO_CLIENT_REPO_PATH/cpp/cmake/toolchains/linux_amd64_gcc.cmake -DCMAKE_BUILD_TYPE=Debug -DCMAKE_INSTALL_PREFIX=$CMAKE_PREFIX_PATH/protobuf-gcc11-amd64 -DCMAKE_POSITION_INDEPENDENT_CODE=ON -Dprotobuf_BUILD_TESTS=OFF
  cmake -B out/gcc11-amd64/Release --toolchain $FEDPRO_CLIENT_REPO_PATH/cpp/cmake/toolchains/linux_amd64_gcc.cmake -DCMAKE_BUILD_TYPE=Release -DCMAKE_INSTALL_PREFIX=$CMAKE_PREFIX_PATH/protobuf-gcc11-amd64 -DCMAKE_POSITION_INDEPENDENT_CODE=ON -Dprotobuf_BUILD_TESTS=OFF
  ```

* Build and install the Debug configuration

  ```
  cmake --build out/gcc11-amd64/Debug/ --config Debug
  cmake --install out/gcc11-amd64/Debug/ --config Debug
  ```

* Build and install the Release configuration

  ```
  cmake --build out/gcc11-amd64/Release/ --config Release
  cmake --install out/gcc11-amd64/Release/ --config Release
  ```

#### Build Protocol Buffers with GCC 11, Ninja on Ubuntu Linux, arm64/aarch64

* Make sure Ninja is installed on your Ubuntu. To install it, run:

  ```
  sudo apt update
  sudo apt install ninja-build
  ```

* Configure both Release and Debug builds

  ```
  cmake -B out/gcc11-arm64/Debug --toolchain $FEDPRO_CLIENT_REPO_PATH/cpp/cmake/toolchains/linux_arm64_gcc.cmake -DCMAKE_BUILD_TYPE=Debug -DCMAKE_INSTALL_PREFIX=$CMAKE_PREFIX_PATH/protobuf-gcc11-arm64 -DCMAKE_POSITION_INDEPENDENT_CODE=ON -Dprotobuf_BUILD_TESTS=OFF 
  cmake -B out/gcc11-arm64/Release --toolchain $FEDPRO_CLIENT_REPO_PATH/cpp/cmake/toolchains/linux_arm64_gcc.cmake -DCMAKE_BUILD_TYPE=Release -DCMAKE_INSTALL_PREFIX=$CMAKE_PREFIX_PATH/protobuf-gcc11-arm64 -DCMAKE_POSITION_INDEPENDENT_CODE=ON -Dprotobuf_BUILD_TESTS=OFF
  ```

* Build and install the Debug configuration

  ```
  cmake --build out/gcc11-arm64/Debug/ --config Debug
  cmake --install out/gcc11-arm64/Debug/ --config Debug
  ```

* Build and install the Release configuration

  ```
  cmake --build out/gcc11-arm64/Release/ --config Release
  cmake --install out/gcc11-arm64/Release/ --config Release
  ```

#### Build Protocol Buffers with CLang, Ninja on macOS, 64 bit

* Make sure Ninja is installed on your Mac. You can install it through the package manager [Homebrew](https://brew.sh/).

* Configure both Release and Debug builds

specify your path instead of "<your_path>"

  ```
  cmake -B out/clang-universal2/Debug --toolchain $FEDPRO_CLIENT_REPO_PATH/cpp/cmake/toolchains/macos_amd64_arm64_clang.cmake -DCMAKE_BUILD_TYPE=Debug -DCMAKE_INSTALL_PREFIX=$CMAKE_PREFIX_PATH/protobuf-clang-universal2 -DCMAKE_POSITION_INDEPENDENT_CODE=ON -Dprotobuf_BUILD_TESTS=OFF
  cmake -B out/clang-universal2/Release --toolchain $FEDPRO_CLIENT_REPO_PATH/cpp/cmake/toolchains/macos_amd64_arm64_clang.cmake -DCMAKE_BUILD_TYPE=Release -DCMAKE_INSTALL_PREFIX=$CMAKE_PREFIX_PATH/protobuf-clang-universal2 -DCMAKE_POSITION_INDEPENDENT_CODE=ON -Dprotobuf_BUILD_TESTS=OFF
  ```

* Build and install the Debug configuration

  ```
  cmake --build out/clang-universal2/Debug/ --config Debug
  cmake --install out/clang-universal2/Debug/ --config Debug
  ```
  
* Build and install the Release configuration

  ```
  cmake --build out/clang-universal2/Release/ --config Release
  cmake --install out/clang-universal2/Release/ --config Release
  ```

### Build the Federate Protocol C++ client library

Execute the following instructions in the `cpp` directory to build the client library and the accompanying chat samples.

Make sure a `Debug` configuration is installed for *Protobuf Buffers* when building the client with a `Debug` profile,
and a corresponding `Release` configuration when building with a `Release` profile.

#### Build with latest Visual Studio toolset installed

* Configure

  ```
  cmake --preset vc-amd64
  ```

* Build

  ```
  cmake --build --preset vc-amd64-debug
  cmake --build --preset vc-amd64-release
  ```

* install

  ```
  cmake --install ./out/build/vc-amd64 --config Debug
  cmake --install ./out/build/vc-amd64 --config Release

  ```

#### Build with latest GCC installed, Ninja

* Configure

  ```
  cmake --preset gcc-amd64
  ```

* Build

  ```
  cmake --build --preset gcc-amd64-debug
  cmake --build --preset gcc-amd64-release
  ```

* install

  ```
  cmake --install ./out/build/gcc-amd64 --config Debug
  cmake --install ./out/build/gcc-amd64 --config Release

  ```

#### Build with latest CLang toolset installed

* Configure

  ```
  cmake --preset clang-universal2
  ```

* Build

  ```
  cmake --build --preset clang-universal2-debug
  cmake --build --preset clang-universal2-release
  ```

* install

  ```
  cmake --install ./out/build/clang-universal2 --config Debug
  cmake --install ./out/build/clang-universal2 --config Release

  ```

### Build and run the chat samples

* Follow the above instructions to build both the samples and the client library. 
* Change your working directory to the sample build directory `cpp/build/<config>/<sample_name>/<config>`. 

  Example:
  ```
  cd  build/vc140-amd64-release/sample/Release
  ```
* Run the sample executable. 
* (Optional) Run the sample executable multiple times, simultaneously, to chat between federates. 

### Build static libraries

By default, the client library is built as a shared library. If you want to build static libraries instead, you can turn off the `FEDPRO_SHARED_LIBS` option in the CMake configure step. For example, if building with the latest Visual Studio toolset installed, run

```
cmake --preset vc-amd64 -DFEDPRO_SHARED_LIBS=OFF
```

The build and install commands remain the same as before.

## Components ##

### Transport component ###

TODO describe component

### Session component ###

TODO describe component

### Services component ###

The services components implements the standard HLA C++ API using Federate Protocol.

Directories:

* `services-common/ `

  Contains common source files shared by HLA4 and HLAEvolved. The overwhelming majority of code is shared and located in this directory.
* `services-hla4/`

  Contains HLA4-specific source files.

Macros:

* `RTI_NAMESPACE`

  Standard HLA API namespace, such as `rti1516_2025`. This macro definition allows writing common source files and reusing them for multiple HLA API versions.

* `RTI_HLA_VERSION`

  A numerical value representing the standard HLA API version number. This macro definition allows placing version-specific sections code in shared source files.

## API reference documentation

Generated doxygen documentation of the Federate Protocol client is checked in this repository.
I can be replaced through regeneration.
The documentation resides in the `doxygen`/ directory, found in the root of this repository.
To view the documentation, open the file [html/index.html](doxygen/html/index.html) in a browser.

### Generation configuration

The file `Doxyfile` is a configuration file for the doxygen project.
There are plenty of options and the following has been modified to for this particular project:
- PROJECT_NAME           = Federate Protocol Client
- INPUT                  = include
- OUTPUT_DIRECTORY       = doxygen
- RECURSIVE              = YES
- EXCLUDE                = src/fedpro/protobuf src/fedpro/protobuf/generated
- GENERATE_LATEX         = NO

No documentation for protobuf components is or is supposed to be generated.

### Documentation regeneration

To regenerate HTML documentation, first make sure you are in the directory in which the `Doxyfile` is located.
Then run the command `[<path_to_installed_doxygen_app>/]doxygen Doxyfile`, alternatively `doxygen Doxyfile` if the path for the doxygen application is assigned to the system PATH variable.
Documentation should now have been generated in the directory assigned to the OUTPUT_DIRECTORY key in the `Doxyfile`.
