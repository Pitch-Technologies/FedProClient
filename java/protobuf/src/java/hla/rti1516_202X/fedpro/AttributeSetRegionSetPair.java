// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: datatypes.proto

package hla.rti1516_202X.fedpro;

/**
 * Protobuf type {@code rti1516_202X.fedpro.AttributeSetRegionSetPair}
 */
public final class AttributeSetRegionSetPair extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:rti1516_202X.fedpro.AttributeSetRegionSetPair)
    AttributeSetRegionSetPairOrBuilder {
private static final long serialVersionUID = 0L;
  // Use AttributeSetRegionSetPair.newBuilder() to construct.
  private AttributeSetRegionSetPair(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private AttributeSetRegionSetPair() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new AttributeSetRegionSetPair();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_AttributeSetRegionSetPair_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_AttributeSetRegionSetPair_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            hla.rti1516_202X.fedpro.AttributeSetRegionSetPair.class, hla.rti1516_202X.fedpro.AttributeSetRegionSetPair.Builder.class);
  }

  public static final int ATTRIBUTESET_FIELD_NUMBER = 1;
  private hla.rti1516_202X.fedpro.AttributeHandleSet attributeSet_;
  /**
   * <code>.rti1516_202X.fedpro.AttributeHandleSet attributeSet = 1;</code>
   * @return Whether the attributeSet field is set.
   */
  @java.lang.Override
  public boolean hasAttributeSet() {
    return attributeSet_ != null;
  }
  /**
   * <code>.rti1516_202X.fedpro.AttributeHandleSet attributeSet = 1;</code>
   * @return The attributeSet.
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.AttributeHandleSet getAttributeSet() {
    return attributeSet_ == null ? hla.rti1516_202X.fedpro.AttributeHandleSet.getDefaultInstance() : attributeSet_;
  }
  /**
   * <code>.rti1516_202X.fedpro.AttributeHandleSet attributeSet = 1;</code>
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.AttributeHandleSetOrBuilder getAttributeSetOrBuilder() {
    return getAttributeSet();
  }

  public static final int REGIONSET_FIELD_NUMBER = 2;
  private hla.rti1516_202X.fedpro.RegionHandleSet regionSet_;
  /**
   * <code>.rti1516_202X.fedpro.RegionHandleSet regionSet = 2;</code>
   * @return Whether the regionSet field is set.
   */
  @java.lang.Override
  public boolean hasRegionSet() {
    return regionSet_ != null;
  }
  /**
   * <code>.rti1516_202X.fedpro.RegionHandleSet regionSet = 2;</code>
   * @return The regionSet.
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.RegionHandleSet getRegionSet() {
    return regionSet_ == null ? hla.rti1516_202X.fedpro.RegionHandleSet.getDefaultInstance() : regionSet_;
  }
  /**
   * <code>.rti1516_202X.fedpro.RegionHandleSet regionSet = 2;</code>
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.RegionHandleSetOrBuilder getRegionSetOrBuilder() {
    return getRegionSet();
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    if (attributeSet_ != null) {
      output.writeMessage(1, getAttributeSet());
    }
    if (regionSet_ != null) {
      output.writeMessage(2, getRegionSet());
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (attributeSet_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getAttributeSet());
    }
    if (regionSet_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getRegionSet());
    }
    size += getUnknownFields().getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof hla.rti1516_202X.fedpro.AttributeSetRegionSetPair)) {
      return super.equals(obj);
    }
    hla.rti1516_202X.fedpro.AttributeSetRegionSetPair other = (hla.rti1516_202X.fedpro.AttributeSetRegionSetPair) obj;

    if (hasAttributeSet() != other.hasAttributeSet()) return false;
    if (hasAttributeSet()) {
      if (!getAttributeSet()
          .equals(other.getAttributeSet())) return false;
    }
    if (hasRegionSet() != other.hasRegionSet()) return false;
    if (hasRegionSet()) {
      if (!getRegionSet()
          .equals(other.getRegionSet())) return false;
    }
    if (!getUnknownFields().equals(other.getUnknownFields())) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (hasAttributeSet()) {
      hash = (37 * hash) + ATTRIBUTESET_FIELD_NUMBER;
      hash = (53 * hash) + getAttributeSet().hashCode();
    }
    if (hasRegionSet()) {
      hash = (37 * hash) + REGIONSET_FIELD_NUMBER;
      hash = (53 * hash) + getRegionSet().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static hla.rti1516_202X.fedpro.AttributeSetRegionSetPair parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.AttributeSetRegionSetPair parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.AttributeSetRegionSetPair parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.AttributeSetRegionSetPair parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.AttributeSetRegionSetPair parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.AttributeSetRegionSetPair parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.AttributeSetRegionSetPair parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.AttributeSetRegionSetPair parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.AttributeSetRegionSetPair parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.AttributeSetRegionSetPair parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.AttributeSetRegionSetPair parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.AttributeSetRegionSetPair parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }

  @java.lang.Override
  public Builder newBuilderForType() { return newBuilder(); }
  public static Builder newBuilder() {
    return DEFAULT_INSTANCE.toBuilder();
  }
  public static Builder newBuilder(hla.rti1516_202X.fedpro.AttributeSetRegionSetPair prototype) {
    return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
  }
  @java.lang.Override
  public Builder toBuilder() {
    return this == DEFAULT_INSTANCE
        ? new Builder() : new Builder().mergeFrom(this);
  }

  @java.lang.Override
  protected Builder newBuilderForType(
      com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
    Builder builder = new Builder(parent);
    return builder;
  }
  /**
   * Protobuf type {@code rti1516_202X.fedpro.AttributeSetRegionSetPair}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:rti1516_202X.fedpro.AttributeSetRegionSetPair)
      hla.rti1516_202X.fedpro.AttributeSetRegionSetPairOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_AttributeSetRegionSetPair_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_AttributeSetRegionSetPair_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              hla.rti1516_202X.fedpro.AttributeSetRegionSetPair.class, hla.rti1516_202X.fedpro.AttributeSetRegionSetPair.Builder.class);
    }

    // Construct using hla.rti1516_202X.fedpro.AttributeSetRegionSetPair.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (attributeSetBuilder_ == null) {
        attributeSet_ = null;
      } else {
        attributeSet_ = null;
        attributeSetBuilder_ = null;
      }
      if (regionSetBuilder_ == null) {
        regionSet_ = null;
      } else {
        regionSet_ = null;
        regionSetBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_AttributeSetRegionSetPair_descriptor;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.AttributeSetRegionSetPair getDefaultInstanceForType() {
      return hla.rti1516_202X.fedpro.AttributeSetRegionSetPair.getDefaultInstance();
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.AttributeSetRegionSetPair build() {
      hla.rti1516_202X.fedpro.AttributeSetRegionSetPair result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.AttributeSetRegionSetPair buildPartial() {
      hla.rti1516_202X.fedpro.AttributeSetRegionSetPair result = new hla.rti1516_202X.fedpro.AttributeSetRegionSetPair(this);
      if (attributeSetBuilder_ == null) {
        result.attributeSet_ = attributeSet_;
      } else {
        result.attributeSet_ = attributeSetBuilder_.build();
      }
      if (regionSetBuilder_ == null) {
        result.regionSet_ = regionSet_;
      } else {
        result.regionSet_ = regionSetBuilder_.build();
      }
      onBuilt();
      return result;
    }

    @java.lang.Override
    public Builder clone() {
      return super.clone();
    }
    @java.lang.Override
    public Builder setField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.setField(field, value);
    }
    @java.lang.Override
    public Builder clearField(
        com.google.protobuf.Descriptors.FieldDescriptor field) {
      return super.clearField(field);
    }
    @java.lang.Override
    public Builder clearOneof(
        com.google.protobuf.Descriptors.OneofDescriptor oneof) {
      return super.clearOneof(oneof);
    }
    @java.lang.Override
    public Builder setRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        int index, java.lang.Object value) {
      return super.setRepeatedField(field, index, value);
    }
    @java.lang.Override
    public Builder addRepeatedField(
        com.google.protobuf.Descriptors.FieldDescriptor field,
        java.lang.Object value) {
      return super.addRepeatedField(field, value);
    }
    @java.lang.Override
    public Builder mergeFrom(com.google.protobuf.Message other) {
      if (other instanceof hla.rti1516_202X.fedpro.AttributeSetRegionSetPair) {
        return mergeFrom((hla.rti1516_202X.fedpro.AttributeSetRegionSetPair)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(hla.rti1516_202X.fedpro.AttributeSetRegionSetPair other) {
      if (other == hla.rti1516_202X.fedpro.AttributeSetRegionSetPair.getDefaultInstance()) return this;
      if (other.hasAttributeSet()) {
        mergeAttributeSet(other.getAttributeSet());
      }
      if (other.hasRegionSet()) {
        mergeRegionSet(other.getRegionSet());
      }
      this.mergeUnknownFields(other.getUnknownFields());
      onChanged();
      return this;
    }

    @java.lang.Override
    public final boolean isInitialized() {
      return true;
    }

    @java.lang.Override
    public Builder mergeFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            case 10: {
              input.readMessage(
                  getAttributeSetFieldBuilder().getBuilder(),
                  extensionRegistry);

              break;
            } // case 10
            case 18: {
              input.readMessage(
                  getRegionSetFieldBuilder().getBuilder(),
                  extensionRegistry);

              break;
            } // case 18
            default: {
              if (!super.parseUnknownField(input, extensionRegistry, tag)) {
                done = true; // was an endgroup tag
              }
              break;
            } // default:
          } // switch (tag)
        } // while (!done)
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.unwrapIOException();
      } finally {
        onChanged();
      } // finally
      return this;
    }

    private hla.rti1516_202X.fedpro.AttributeHandleSet attributeSet_;
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.AttributeHandleSet, hla.rti1516_202X.fedpro.AttributeHandleSet.Builder, hla.rti1516_202X.fedpro.AttributeHandleSetOrBuilder> attributeSetBuilder_;
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet attributeSet = 1;</code>
     * @return Whether the attributeSet field is set.
     */
    public boolean hasAttributeSet() {
      return attributeSetBuilder_ != null || attributeSet_ != null;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet attributeSet = 1;</code>
     * @return The attributeSet.
     */
    public hla.rti1516_202X.fedpro.AttributeHandleSet getAttributeSet() {
      if (attributeSetBuilder_ == null) {
        return attributeSet_ == null ? hla.rti1516_202X.fedpro.AttributeHandleSet.getDefaultInstance() : attributeSet_;
      } else {
        return attributeSetBuilder_.getMessage();
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet attributeSet = 1;</code>
     */
    public Builder setAttributeSet(hla.rti1516_202X.fedpro.AttributeHandleSet value) {
      if (attributeSetBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        attributeSet_ = value;
        onChanged();
      } else {
        attributeSetBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet attributeSet = 1;</code>
     */
    public Builder setAttributeSet(
        hla.rti1516_202X.fedpro.AttributeHandleSet.Builder builderForValue) {
      if (attributeSetBuilder_ == null) {
        attributeSet_ = builderForValue.build();
        onChanged();
      } else {
        attributeSetBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet attributeSet = 1;</code>
     */
    public Builder mergeAttributeSet(hla.rti1516_202X.fedpro.AttributeHandleSet value) {
      if (attributeSetBuilder_ == null) {
        if (attributeSet_ != null) {
          attributeSet_ =
            hla.rti1516_202X.fedpro.AttributeHandleSet.newBuilder(attributeSet_).mergeFrom(value).buildPartial();
        } else {
          attributeSet_ = value;
        }
        onChanged();
      } else {
        attributeSetBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet attributeSet = 1;</code>
     */
    public Builder clearAttributeSet() {
      if (attributeSetBuilder_ == null) {
        attributeSet_ = null;
        onChanged();
      } else {
        attributeSet_ = null;
        attributeSetBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet attributeSet = 1;</code>
     */
    public hla.rti1516_202X.fedpro.AttributeHandleSet.Builder getAttributeSetBuilder() {
      
      onChanged();
      return getAttributeSetFieldBuilder().getBuilder();
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet attributeSet = 1;</code>
     */
    public hla.rti1516_202X.fedpro.AttributeHandleSetOrBuilder getAttributeSetOrBuilder() {
      if (attributeSetBuilder_ != null) {
        return attributeSetBuilder_.getMessageOrBuilder();
      } else {
        return attributeSet_ == null ?
            hla.rti1516_202X.fedpro.AttributeHandleSet.getDefaultInstance() : attributeSet_;
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet attributeSet = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.AttributeHandleSet, hla.rti1516_202X.fedpro.AttributeHandleSet.Builder, hla.rti1516_202X.fedpro.AttributeHandleSetOrBuilder> 
        getAttributeSetFieldBuilder() {
      if (attributeSetBuilder_ == null) {
        attributeSetBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            hla.rti1516_202X.fedpro.AttributeHandleSet, hla.rti1516_202X.fedpro.AttributeHandleSet.Builder, hla.rti1516_202X.fedpro.AttributeHandleSetOrBuilder>(
                getAttributeSet(),
                getParentForChildren(),
                isClean());
        attributeSet_ = null;
      }
      return attributeSetBuilder_;
    }

    private hla.rti1516_202X.fedpro.RegionHandleSet regionSet_;
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.RegionHandleSet, hla.rti1516_202X.fedpro.RegionHandleSet.Builder, hla.rti1516_202X.fedpro.RegionHandleSetOrBuilder> regionSetBuilder_;
    /**
     * <code>.rti1516_202X.fedpro.RegionHandleSet regionSet = 2;</code>
     * @return Whether the regionSet field is set.
     */
    public boolean hasRegionSet() {
      return regionSetBuilder_ != null || regionSet_ != null;
    }
    /**
     * <code>.rti1516_202X.fedpro.RegionHandleSet regionSet = 2;</code>
     * @return The regionSet.
     */
    public hla.rti1516_202X.fedpro.RegionHandleSet getRegionSet() {
      if (regionSetBuilder_ == null) {
        return regionSet_ == null ? hla.rti1516_202X.fedpro.RegionHandleSet.getDefaultInstance() : regionSet_;
      } else {
        return regionSetBuilder_.getMessage();
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.RegionHandleSet regionSet = 2;</code>
     */
    public Builder setRegionSet(hla.rti1516_202X.fedpro.RegionHandleSet value) {
      if (regionSetBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        regionSet_ = value;
        onChanged();
      } else {
        regionSetBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.RegionHandleSet regionSet = 2;</code>
     */
    public Builder setRegionSet(
        hla.rti1516_202X.fedpro.RegionHandleSet.Builder builderForValue) {
      if (regionSetBuilder_ == null) {
        regionSet_ = builderForValue.build();
        onChanged();
      } else {
        regionSetBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.RegionHandleSet regionSet = 2;</code>
     */
    public Builder mergeRegionSet(hla.rti1516_202X.fedpro.RegionHandleSet value) {
      if (regionSetBuilder_ == null) {
        if (regionSet_ != null) {
          regionSet_ =
            hla.rti1516_202X.fedpro.RegionHandleSet.newBuilder(regionSet_).mergeFrom(value).buildPartial();
        } else {
          regionSet_ = value;
        }
        onChanged();
      } else {
        regionSetBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.RegionHandleSet regionSet = 2;</code>
     */
    public Builder clearRegionSet() {
      if (regionSetBuilder_ == null) {
        regionSet_ = null;
        onChanged();
      } else {
        regionSet_ = null;
        regionSetBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.RegionHandleSet regionSet = 2;</code>
     */
    public hla.rti1516_202X.fedpro.RegionHandleSet.Builder getRegionSetBuilder() {
      
      onChanged();
      return getRegionSetFieldBuilder().getBuilder();
    }
    /**
     * <code>.rti1516_202X.fedpro.RegionHandleSet regionSet = 2;</code>
     */
    public hla.rti1516_202X.fedpro.RegionHandleSetOrBuilder getRegionSetOrBuilder() {
      if (regionSetBuilder_ != null) {
        return regionSetBuilder_.getMessageOrBuilder();
      } else {
        return regionSet_ == null ?
            hla.rti1516_202X.fedpro.RegionHandleSet.getDefaultInstance() : regionSet_;
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.RegionHandleSet regionSet = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.RegionHandleSet, hla.rti1516_202X.fedpro.RegionHandleSet.Builder, hla.rti1516_202X.fedpro.RegionHandleSetOrBuilder> 
        getRegionSetFieldBuilder() {
      if (regionSetBuilder_ == null) {
        regionSetBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            hla.rti1516_202X.fedpro.RegionHandleSet, hla.rti1516_202X.fedpro.RegionHandleSet.Builder, hla.rti1516_202X.fedpro.RegionHandleSetOrBuilder>(
                getRegionSet(),
                getParentForChildren(),
                isClean());
        regionSet_ = null;
      }
      return regionSetBuilder_;
    }
    @java.lang.Override
    public final Builder setUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.setUnknownFields(unknownFields);
    }

    @java.lang.Override
    public final Builder mergeUnknownFields(
        final com.google.protobuf.UnknownFieldSet unknownFields) {
      return super.mergeUnknownFields(unknownFields);
    }


    // @@protoc_insertion_point(builder_scope:rti1516_202X.fedpro.AttributeSetRegionSetPair)
  }

  // @@protoc_insertion_point(class_scope:rti1516_202X.fedpro.AttributeSetRegionSetPair)
  private static final hla.rti1516_202X.fedpro.AttributeSetRegionSetPair DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new hla.rti1516_202X.fedpro.AttributeSetRegionSetPair();
  }

  public static hla.rti1516_202X.fedpro.AttributeSetRegionSetPair getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AttributeSetRegionSetPair>
      PARSER = new com.google.protobuf.AbstractParser<AttributeSetRegionSetPair>() {
    @java.lang.Override
    public AttributeSetRegionSetPair parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      Builder builder = newBuilder();
      try {
        builder.mergeFrom(input, extensionRegistry);
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(builder.buildPartial());
      } catch (com.google.protobuf.UninitializedMessageException e) {
        throw e.asInvalidProtocolBufferException().setUnfinishedMessage(builder.buildPartial());
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(e)
            .setUnfinishedMessage(builder.buildPartial());
      }
      return builder.buildPartial();
    }
  };

  public static com.google.protobuf.Parser<AttributeSetRegionSetPair> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<AttributeSetRegionSetPair> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public hla.rti1516_202X.fedpro.AttributeSetRegionSetPair getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
