# Federate Protocol Java client

_fedproclient-java_ is a Federate Protocol Java client library.
It aims to implement the HLA4 wire protocol from the IEEE 1516-2025 specification.

<!-- TODO: Finish merging with ../README.md and ../docs -->

## Logging

For both the frontend and the backend, Java Util Logging is used.
The library supports writing log messages to console and files, for example.

To configure the logging of the java client library during development, edit the properties file ```tools/FedProClient.logging```.
If that file doesn't exist already, make a copy of `tools/FedProClient.logging.template` and rename it to ```tools/FedProClient.logging```.
Note that ```tools/FedProClient.logging``` will not be added to the git repository.
In the properties file, the log severity level can be set for the java client library by changing the value of `se.pitch.oss.fedpro.level` to the desired level.
The available severity levels are, from least verbose to most, `OFF`, `SEVERE`, `WARNING`, `INFO`, `CONFIG`, `FINE`, `FINER`, `FINEST`, and `ALL`.

## <a name="building"></a>Build instructions

To build the Federate Protocol Java Client and its dependencies using Gradle, run `gradlew build` in `java/`.

Gradle requires JVM 17 or later. 

## Running the Chat federate

To build and run the sample Chat federate using Gradle, run `gradlew runSample --console=plain` in `java/`