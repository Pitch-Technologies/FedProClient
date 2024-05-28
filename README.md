# Federate Protocol Client (compatible with Pitch pRTI 5.5.10)

This branch contains the source code of a Java Federate Protocol library that is compatible with the Federate Protocol preview implementation shipped with pRTI 5.5.10 from Pitch Technologies.
You should only be using this branch as a reference if you need to work with pRTI 5.5.10.
The `main` branch of this repository contains the latest code, an implementation that is closer to the final Federate Protocol specification from IEEE.

## Building
The Federate Protocol Client and its dependencies can be build using the ant-target `dist.all` present in `build.xml`.

### Regenerating build-targets
Regenerating the build-targets has to be done when module dependencies has changed.
For the java build, the plugin 'Ant Build Generation' in IntelliJ should be used, with the settings 'Generate single-file ant build' selected, and 'Enable UI forms compilations' disabled.

## Samples

### AsyncChat
`AsyncChat` is a basic sample that uses asynchronous versions of `updateAttributeValues()` and `sendInteraction()`.

### AsyncChatAdvanced
`AsyncChatAdvanced` uses asynchronous versions of HLA services whenever possible, and makes use of many more methods of `CompletableFuture` than `AsyncChat` does. For a good primer on `CompletableFuture`, see this article: https://liakh-aliaksandr.medium.com/asynchronous-programming-in-java-with-completablefuture-47ab86458aab
