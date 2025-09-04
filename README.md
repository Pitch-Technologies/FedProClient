# Federate Protocol Client

_FedProClient_ is a set of Federate Protocol client libraries for C++ and Java.
It aims to implement the HLA 4 Federate Protocol from the IEEE 1516-2025 specification.

Both the C++ and Java client libraries have two versions,
which implement the HLA Evolved standard API (IEEE 1516-2010) and the HLA 4 standard API (IEEE 1516-2025), respectively.

## Usage

HLA 4 applications that use the Federate Protocol client may specify the Federate Protocol server address using the method `RtiConfiguration.withRtiAddress`,
with the syntax described in the [Settings documentation](doc/Settings.md). 

### Samples
Sample federates for Java and C++ clients are provided to facilitate getting started with the Federate Protocol.

#### Chat
`Chat` is a basic synchronous sample.
Both HLA 4 and HLA Evolved versions of it are provided.
The samples prompt users to specify the FedPro server address in the format `[<server_address>[:<server_port>]]`.
If values for address and/or port are left out,
the samples will use the default values `localhost` as hostname and `15164` as port.


#### AsyncChat
`AsyncChat` is a basic HLA 4 sample that uses asynchronous versions of `updateAttributeValues()` and `sendInteraction()`.
<!-- TODO: Currently only available for the java client. -->

#### AsyncChatAdvanced
`AsyncChatAdvanced` uses asynchronous versions of HLA 4 services whenever possible, and makes use of many more methods of `CompletableFuture` than `AsyncChat` does.
For a good primer on `CompletableFuture`, see this article: https://liakh-aliaksandr.medium.com/asynchronous-programming-in-java-with-completablefuture-47ab86458aab
<!-- TODO: Currently only available for the java client. -->


### Existing C++ Federates

The client library is ABI compatible with existing RTI local components (LRC) for HLA Evolved and HLA 4,
and may act as a drop-in replacement, allowing use of Federate Protocol with an existing federate.

Building the library produces two shared libraries within directory `out/TARGET_COMPILER/Debug/cpp/Debug` or `out/TARGET_COMPILER/Release/cpp/Release`.
Library filenames follow the usual naming convention for RTI local components.

Example of library names generated using Visual Studio's Release configuration:
* ```out/vc140-amd64/Release/cpp/Release/libfedtime1516e64.dll```
* ```out/vc140-amd64/Release/cpp/Release/librti1516e64.dll```

To use the client with an existing federate application:
* Build the client library,
* Locate the relevant library flavor (Debug or Release, HLA Evolved or 4),
* Copy the library files in the same directory as the federate executable.

## Further reading

* [Build instructions for Java](java/README.md#building)
* [Build instructions for C++](cpp/README.md#building)
* [Components](doc/Components.md)
* [Settings](doc/Settings.md)
