// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RTIambassador.proto

package hla.rti1516_202X.fedpro;

public interface RegisterFederationSynchronizationPointWithSetRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:rti1516_202X.fedpro.RegisterFederationSynchronizationPointWithSetRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string synchronizationPointLabel = 1;</code>
   * @return The synchronizationPointLabel.
   */
  java.lang.String getSynchronizationPointLabel();
  /**
   * <code>string synchronizationPointLabel = 1;</code>
   * @return The bytes for synchronizationPointLabel.
   */
  com.google.protobuf.ByteString
      getSynchronizationPointLabelBytes();

  /**
   * <code>bytes userSuppliedTag = 2;</code>
   * @return The userSuppliedTag.
   */
  com.google.protobuf.ByteString getUserSuppliedTag();

  /**
   * <code>.rti1516_202X.fedpro.FederateHandleSet synchronizationSet = 3;</code>
   * @return Whether the synchronizationSet field is set.
   */
  boolean hasSynchronizationSet();
  /**
   * <code>.rti1516_202X.fedpro.FederateHandleSet synchronizationSet = 3;</code>
   * @return The synchronizationSet.
   */
  hla.rti1516_202X.fedpro.FederateHandleSet getSynchronizationSet();
  /**
   * <code>.rti1516_202X.fedpro.FederateHandleSet synchronizationSet = 3;</code>
   */
  hla.rti1516_202X.fedpro.FederateHandleSetOrBuilder getSynchronizationSetOrBuilder();
}
