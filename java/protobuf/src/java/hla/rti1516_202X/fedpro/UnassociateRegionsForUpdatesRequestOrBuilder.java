// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RTIambassador.proto

package hla.rti1516_202X.fedpro;

public interface UnassociateRegionsForUpdatesRequestOrBuilder extends
    // @@protoc_insertion_point(interface_extends:rti1516_202X.fedpro.UnassociateRegionsForUpdatesRequest)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
   * @return Whether the objectInstance field is set.
   */
  boolean hasObjectInstance();
  /**
   * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
   * @return The objectInstance.
   */
  hla.rti1516_202X.fedpro.ObjectInstanceHandle getObjectInstance();
  /**
   * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
   */
  hla.rti1516_202X.fedpro.ObjectInstanceHandleOrBuilder getObjectInstanceOrBuilder();

  /**
   * <code>.rti1516_202X.fedpro.AttributeSetRegionSetPairList attributesAndRegions = 2;</code>
   * @return Whether the attributesAndRegions field is set.
   */
  boolean hasAttributesAndRegions();
  /**
   * <code>.rti1516_202X.fedpro.AttributeSetRegionSetPairList attributesAndRegions = 2;</code>
   * @return The attributesAndRegions.
   */
  hla.rti1516_202X.fedpro.AttributeSetRegionSetPairList getAttributesAndRegions();
  /**
   * <code>.rti1516_202X.fedpro.AttributeSetRegionSetPairList attributesAndRegions = 2;</code>
   */
  hla.rti1516_202X.fedpro.AttributeSetRegionSetPairListOrBuilder getAttributesAndRegionsOrBuilder();
}