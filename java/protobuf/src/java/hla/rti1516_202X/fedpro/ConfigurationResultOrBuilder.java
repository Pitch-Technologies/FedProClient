// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: datatypes.proto

package hla.rti1516_202X.fedpro;

public interface ConfigurationResultOrBuilder extends
    // @@protoc_insertion_point(interface_extends:rti1516_202X.fedpro.ConfigurationResult)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>bool configurationUsed = 1;</code>
   * @return The configurationUsed.
   */
  boolean getConfigurationUsed();

  /**
   * <code>bool addressUsed = 2;</code>
   * @return The addressUsed.
   */
  boolean getAddressUsed();

  /**
   * <code>.rti1516_202X.fedpro.AdditionalSettingsResultCode additionalSettingsResultCode = 3;</code>
   * @return The enum numeric value on the wire for additionalSettingsResultCode.
   */
  int getAdditionalSettingsResultCodeValue();
  /**
   * <code>.rti1516_202X.fedpro.AdditionalSettingsResultCode additionalSettingsResultCode = 3;</code>
   * @return The additionalSettingsResultCode.
   */
  hla.rti1516_202X.fedpro.AdditionalSettingsResultCode getAdditionalSettingsResultCode();

  /**
   * <code>string message = 4;</code>
   * @return The message.
   */
  java.lang.String getMessage();
  /**
   * <code>string message = 4;</code>
   * @return The bytes for message.
   */
  com.google.protobuf.ByteString
      getMessageBytes();
}
