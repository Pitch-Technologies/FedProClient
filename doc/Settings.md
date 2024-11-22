## Settings

## How to provide settings

Client libraries are configurable by passing settings through the `connect()` method of the `RTIambassador` class,
through environment variables, and through Java System Properties.

If the value for a certain setting is specified in more than one way,
the value which is set through the most prioritized way will be used.
The order of precedence is, from least to most prioritized:

* Implementation defaults,
* Environment variable `FEDPRO_CLIENT_SETTINGS`,
* System property with prefix `FedPro.` (Java only),
* `RTIambassador.connect()` parameter (local settings designator string in HLA Evolved, and `RtiConfiguration` instance in (HLA 4),

* Environment variable `FEDPRO_CLIENT_OVERRIDES`,
* System property with prefix `fedProOverrides.` (Java only).

### RtiConfiguration syntax (HLA 4)

For HLA{nbsp}4 federates,
one way to provide settings is to provide them through a `RtiConfiguration` object
and then pass through to the `connect()` method of the `RTIambassador` class.
When doing so, the following applies:

* All settings may be separated by either commas or line breaks
* Both RTI-specific settings, and Federate Protocol client-specific settings may be provided through the additional settings field of the `RtiConfiguration` object
* All Federate Protocol client setting names must be prefixed with `FedPro.`
* The address for the Federate Protocol server to connect to may be specified through the RTI address field of the `RtiConfiguration` object

C++ Example:
```
std::wstring additionalSettings{L"LRC.skipVersionCheck=false\n"
                                 "FedPro.connect.timeout=10,FedPro.FED_TIMEOUT_HEART=100\n"
                                 "crcHost=rti.test.local\n"
                                 "FedPro.messageQueue.outgoing.limitedRate=false"};

RtiConfiguration rtiConfiguration{RtiConfiguration::createConfiguration().withRtiAddress(L"192.168.1.20:15164").withAdditionalSettings(additionalSettings)};

_rtiAmbassador->connect(*this, HLA_IMMEDIATE, rtiConfiguration);
```

### Local Settings Designator syntax (HLA Evolved)

For HLA Evolved federates, for compatibility purpose,
the following applies for the `localSettingsDesignator` parameter of the `connect()` method of the `RTIambassador` class:

* All settings may be separated by either commas or line breaks
* There may be both RTI-specific settings and Federate Protocol client-specific settings provided within it
* All Federate Protocol client setting names must be prefixed with `FedPro.`
* Exception: The setting 'crcAddress' is interpreted as the address of the FedPro server, not as an LRC setting.
  This minimizes the changes required for existing Evolved federates when transitioning from a classic CRC connection to a Federate Protocol server connection.

Example:
```
crcHost=localhost
FedPro.FED_TIMEOUT_HEART=200
LRC.skipVersionCheck=false
FedPro.connect.hostname=fedproserver.example,FedPro.connect.port=12345
```

### Environment variable syntax

Environment variables `FEDPRO_CLIENT_SETTINGS` or `FEDPRO_CLIENT_OVERRIDES` should contain a list of desired settings as key-value pairs in the following format:

`<environmentVariable>=<setting1>=<value1>,<setting2>=<value2>,...`

Example:

```FEDPRO_CLIENT_SETTINGS=FED_INT_HEART=30,connect.hostname=fedproserver.test```

## List of settings and defaults

Note: Settings are case-sensitive.

### Common settings

* `FED_INT_HEART`

  Heartbeat interval (seconds). 

  Default: 600 seconds

* `FED_TIMEOUT_HEART`

  Missing RTI message timeout (seconds). 

  Default: 180 seconds

* `FED_TIMEOUT_RECONNECT`

  Reconnect timeout (seconds). 

  Default: 600 seconds
* `asyncUpdates`

  Set to true to ignore error responses for sent HLA updates, allowing non-blocking HLA update calls. True or false. 

  Default: "false"

* `connect.hostname`

  Server hostname to use. 

  Default: *localhost*

* `connect.maxRetryAttempts`

  Number of attempts for the client to retry to connect to the server if the first attempt fails. 

  Default: *0*

* `connect.port` 

  Server port to use. 

  Default: Depends on the protocol in use

* `connect.protocol`

  The network protocol to use to connect to the server. Can be one of `tcp`, `tls`, `websocket`, or `websocketsecure`.

  Default: *tcp*

* `connect.timeout`

  Connection timeout (seconds) for session (re)start. 

  Default: 5 seconds

* `messageQueue.size`

  Message history size, as number of messages. 

  Default: 2000

* `messageQueue.outgoing.limitedRate`

  Use rate limiter for outgoing messages.

  Default: *false*

### C++-specific Settings

* `log.console.level`

  Severity level of log messages written to the console. See all levels [here](../cpp/README.md#Logging).

  Default: *warn*

* `log.rotatingFile.level`

  Severity level of log messages written to rotating log files. See all levels [here](../cpp/README.md#Logging).

  Default: *off*
* `log.rotatingFile.path`

  File path to where rotating logs will be written.

  Default: *<working_dir>/FedProClientCppLogs/FedProClientCpp.log*

### Java-specific Settings

* `keystore.algorithm`

  The name of the standard algorithm to use. 

  Default: *SunX509*

* `keystore.password.path`

  Path to a file that contains the password to the keystore. 

  Default: None

* `keystore.path`

  Path to a keystore file. 

  Default: None

* `keystore.type`

  Type of keystore. 

  Default: *JKS*

* `keystore.useDefault`

  Use Java default keystore. 

  Default: *false*

* `tls.mode`

  Level of security, *SERVER_AUTH* or *ENCRYPTED*. 

  Default: *SERVER_AUTH*

* `tls.sniHostname`

  Set to provide a Server Name Indication (SNI) to the server. 

  Default: None
