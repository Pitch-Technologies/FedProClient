// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: datatypes.proto

package hla.rti1516_202X.fedpro;

public interface JoinResultOrBuilder extends
    // @@protoc_insertion_point(interface_extends:rti1516_202X.fedpro.JoinResult)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.rti1516_202X.fedpro.FederateHandle federateHandle = 1;</code>
   * @return Whether the federateHandle field is set.
   */
  boolean hasFederateHandle();
  /**
   * <code>.rti1516_202X.fedpro.FederateHandle federateHandle = 1;</code>
   * @return The federateHandle.
   */
  hla.rti1516_202X.fedpro.FederateHandle getFederateHandle();
  /**
   * <code>.rti1516_202X.fedpro.FederateHandle federateHandle = 1;</code>
   */
  hla.rti1516_202X.fedpro.FederateHandleOrBuilder getFederateHandleOrBuilder();

  /**
   * <code>string logicalTimeImplementationName = 2;</code>
   * @return The logicalTimeImplementationName.
   */
  java.lang.String getLogicalTimeImplementationName();
  /**
   * <code>string logicalTimeImplementationName = 2;</code>
   * @return The bytes for logicalTimeImplementationName.
   */
  com.google.protobuf.ByteString
      getLogicalTimeImplementationNameBytes();
}
