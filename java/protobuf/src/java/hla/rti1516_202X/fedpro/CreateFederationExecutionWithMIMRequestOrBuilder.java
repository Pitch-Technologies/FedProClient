// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RTIambassador.proto

package hla.rti1516_202X.fedpro;

public interface CreateFederationExecutionWithMIMRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:rti1516_202X.fedpro.CreateFederationExecutionWithMIMRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string federationName = 1;</code>
   * @return The federationName.
   */
  java.lang.String getFederationName();
  /**
   * <code>string federationName = 1;</code>
   * @return The bytes for federationName.
   */
  com.google.protobuf.ByteString
      getFederationNameBytes();

  /**
   * <code>.rti1516_202X.fedpro.FomModuleSet fomModules = 2;</code>
   * @return Whether the fomModules field is set.
   */
  boolean hasFomModules();
  /**
   * <code>.rti1516_202X.fedpro.FomModuleSet fomModules = 2;</code>
   * @return The fomModules.
   */
  hla.rti1516_202X.fedpro.FomModuleSet getFomModules();
  /**
   * <code>.rti1516_202X.fedpro.FomModuleSet fomModules = 2;</code>
   */
  hla.rti1516_202X.fedpro.FomModuleSetOrBuilder getFomModulesOrBuilder();

  /**
   * <code>.rti1516_202X.fedpro.FomModule mimModule = 3;</code>
   * @return Whether the mimModule field is set.
   */
  boolean hasMimModule();
  /**
   * <code>.rti1516_202X.fedpro.FomModule mimModule = 3;</code>
   * @return The mimModule.
   */
  hla.rti1516_202X.fedpro.FomModule getMimModule();
  /**
   * <code>.rti1516_202X.fedpro.FomModule mimModule = 3;</code>
   */
  hla.rti1516_202X.fedpro.FomModuleOrBuilder getMimModuleOrBuilder();
}
