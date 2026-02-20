## Settings

## How to provide settings

Client libraries are configurable by passing settings through the `connect()` method of the `RTIambassador` class, through environment variables, and through Java system properties.

The order of precedence of the different methods is as follows:

1. System property with prefix `FedProOverrides.` (Java only)
2. Environment variable `FEDPRO_CLIENT_OVERRIDES`
3. `RTIambassador.connect()` parameter (local settings designator string in HLA Evolved, and `RtiConfiguration` instance in HLA 4)
4. System property with prefix `FedPro.` (Java only)
5. Environment variable `FEDPRO_CLIENT_SETTINGS`
6. Default values

### RtiConfiguration syntax (HLA 4)

Federates using the HLA 4 API may provide settings in an `RtiConfiguration` object passed to the `connect()` method of the `RTIambassador` class.
When doing so, the following applies:

* Both regular LRC settings and Federate Protocol client settings may be provided through the `additionalSettings` field of the `RtiConfiguration` object.
* LRC settings provided this way will only apply if the Federate Protocol server is configured to trust them.
* All settings may be separated by either commas or line breaks.
* All Federate Protocol client setting names must be prefixed with `FedPro.`.
* The address for the Federate Protocol server to connect to may be specified through the RTI address field of the `RtiConfiguration` object.

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

Federates using the HLA Evolved API may provide settings in the `localSettingsDesignator` string passed to the `connect()` method of the `RTIambassador` class.
When doing so, the following applies:

* The `localSettingsDesignator` may contain both regular LRC settings and Federate Protocol client settings.
* LRC settings provided this way will only apply if the Federate Protocol Server is configured to trust them.
* All settings may be separated by either commas or line breaks.
* All Federate Protocol client setting names must be prefixed with `FedPro.`.
* Exception: The setting `crcAddress` is interpreted as the address of the FedPro server, not as an LRC setting.
  This minimizes the changes required for existing Evolved federates when transitioning from a classic CRC connection to a Federate Protocol server connection.

Example:
```
crcHost=localhost
FedPro.FED_TIMEOUT_HEART=200
LRC.skipVersionCheck=false
FedPro.connect.hostname=fedproserver.example,FedPro.connect.port=12345
```

### Environment variable syntax

Environment variables `FEDPRO_CLIENT_SETTINGS` and `FEDPRO_CLIENT_OVERRIDES` may contain a list of desired settings as key-value pairs in the following format:

`<environmentVariable>=<setting1>=<value1>,<setting2>=<value2>,...`

Example:

```FEDPRO_CLIENT_SETTINGS=FED_INT_HEART=30,connect.hostname=fedproserver.test```

## List of settings and defaults

The table below shows each setting and which of the two clients that supports it.
Settings are case-sensitive.

| Setting | Description | Default value | Java | C++
|---|---|---|---|---|
| `FED_INT_HEART` | Heartbeat interval (seconds). | 60 seconds | Yes | Yes
| `FED_TIMEOUT_HEART` | Missing RTI message timeout. (seconds). | 180 seconds | Yes | Yes
| `FED_TIMEOUT_RECONNECT` | Reconnect timeout (seconds). | 600 seconds | Yes | Yes
| `asyncUpdates` | Set to true to ignore error responses for sent HLA updates, allowing non-blocking HLA update calls. True or false. | "false" | Yes | Yes
| `connect.hostname` | Server hostname to use. | "localhost" | Yes | Yes
| `connect.maxRetryAttempts` | Number of attempts for the client to retry to connect to the server if the first attempt fails. | "0" | Yes | Yes
| `connect.port` | Server port to use. | Depends on the protocol used. | Yes | Yes
| `connect.protocol` | The network protocol to use to connect to the server. Valid values are "tcp", "tls", "websocket", or "websocketsecure" | "tcp" | Yes | Yes
| `connect.timeout` | Connection timeout (seconds) for session (re)start. | 5 seconds | Yes | Yes
| `log.console.level` | Severity level of log messages written to the console. See the section on the C++ client for more info. | "warn" | | Yes
| `log.rotatingFile.level` | Severity level of log messages written to rotating log files. | "off" | | Yes
| `log.rotatingFile.path` | File path to where rotating logs will be written. | In <work_dir>/FedProClientCppLogs | | Yes
| `log.stats` | Set to true to have the client print statistics to log scope INFO at an interval set by `log.stats.interval`. | "false" | Yes | Yes
| `log.stats.interval` | The interval, in seconds, at which statistics are logged when `log.stats` is set to true. | 60 | Yes | Yes
| `keystore.algorithm` | The name of the standard algorithm to use. | "SunX509" | Yes |
| `keystore.password.path` | Path to a file that contains the password to the keystore. | None | Yes |
| `keystore.path` | Path to a keystore file. If none is provided, the client will use Java's default keystore. | None | Yes |
| `keystore.type` | Type of keystore. | "JKS" | Yes |
| `messageQueue.size` | Message history size, as number of messages. | 2000 messages | Yes | Yes
| `messageQueue.outgoing.limitedRate` | Use rate limiter for outgoing messages. | "false" | Yes | Yes
| `API.version` | Sends the specified value to the RTI by prepending `API.version=[value]` to the `RtiConfiguration` additional settings field. | "IEEE 1516-2010" for the Evolved adapter. | Yes | Yes
| `tls.mode` | Level of security, "SERVER_AUTH" or "ENCRYPTED". | "SERVER_AUTH" | Yes |
| `tls.sniHostname` | Set to provide a Server Name Indication (SNI) to the server. | None | Yes |

Note that the Federate Protocol client settings do not affect timeout behavior on the Federate Protocol server side.
