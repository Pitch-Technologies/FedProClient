// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: datatypes.proto

package hla.rti1516_202X.fedpro;

/**
 * Protobuf type {@code rti1516_202X.fedpro.AttributeHandleValue}
 */
public final class AttributeHandleValue extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:rti1516_202X.fedpro.AttributeHandleValue)
    AttributeHandleValueOrBuilder {
private static final long serialVersionUID = 0L;
  // Use AttributeHandleValue.newBuilder() to construct.
  private AttributeHandleValue(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private AttributeHandleValue() {
    value_ = com.google.protobuf.ByteString.EMPTY;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new AttributeHandleValue();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_AttributeHandleValue_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_AttributeHandleValue_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            hla.rti1516_202X.fedpro.AttributeHandleValue.class, hla.rti1516_202X.fedpro.AttributeHandleValue.Builder.class);
  }

  public static final int ATTRIBUTEHANDLE_FIELD_NUMBER = 1;
  private hla.rti1516_202X.fedpro.AttributeHandle attributeHandle_;
  /**
   * <code>.rti1516_202X.fedpro.AttributeHandle attributeHandle = 1;</code>
   * @return Whether the attributeHandle field is set.
   */
  @java.lang.Override
  public boolean hasAttributeHandle() {
    return attributeHandle_ != null;
  }
  /**
   * <code>.rti1516_202X.fedpro.AttributeHandle attributeHandle = 1;</code>
   * @return The attributeHandle.
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.AttributeHandle getAttributeHandle() {
    return attributeHandle_ == null ? hla.rti1516_202X.fedpro.AttributeHandle.getDefaultInstance() : attributeHandle_;
  }
  /**
   * <code>.rti1516_202X.fedpro.AttributeHandle attributeHandle = 1;</code>
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.AttributeHandleOrBuilder getAttributeHandleOrBuilder() {
    return getAttributeHandle();
  }

  public static final int VALUE_FIELD_NUMBER = 2;
  private com.google.protobuf.ByteString value_;
  /**
   * <code>bytes value = 2;</code>
   * @return The value.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getValue() {
    return value_;
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
    if (attributeHandle_ != null) {
      output.writeMessage(1, getAttributeHandle());
    }
    if (!value_.isEmpty()) {
      output.writeBytes(2, value_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (attributeHandle_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getAttributeHandle());
    }
    if (!value_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(2, value_);
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
    if (!(obj instanceof hla.rti1516_202X.fedpro.AttributeHandleValue)) {
      return super.equals(obj);
    }
    hla.rti1516_202X.fedpro.AttributeHandleValue other = (hla.rti1516_202X.fedpro.AttributeHandleValue) obj;

    if (hasAttributeHandle() != other.hasAttributeHandle()) return false;
    if (hasAttributeHandle()) {
      if (!getAttributeHandle()
          .equals(other.getAttributeHandle())) return false;
    }
    if (!getValue()
        .equals(other.getValue())) return false;
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
    if (hasAttributeHandle()) {
      hash = (37 * hash) + ATTRIBUTEHANDLE_FIELD_NUMBER;
      hash = (53 * hash) + getAttributeHandle().hashCode();
    }
    hash = (37 * hash) + VALUE_FIELD_NUMBER;
    hash = (53 * hash) + getValue().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static hla.rti1516_202X.fedpro.AttributeHandleValue parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.AttributeHandleValue parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.AttributeHandleValue parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.AttributeHandleValue parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.AttributeHandleValue parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.AttributeHandleValue parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.AttributeHandleValue parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.AttributeHandleValue parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.AttributeHandleValue parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.AttributeHandleValue parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.AttributeHandleValue parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.AttributeHandleValue parseFrom(
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
  public static Builder newBuilder(hla.rti1516_202X.fedpro.AttributeHandleValue prototype) {
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
   * Protobuf type {@code rti1516_202X.fedpro.AttributeHandleValue}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:rti1516_202X.fedpro.AttributeHandleValue)
      hla.rti1516_202X.fedpro.AttributeHandleValueOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_AttributeHandleValue_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_AttributeHandleValue_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              hla.rti1516_202X.fedpro.AttributeHandleValue.class, hla.rti1516_202X.fedpro.AttributeHandleValue.Builder.class);
    }

    // Construct using hla.rti1516_202X.fedpro.AttributeHandleValue.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (attributeHandleBuilder_ == null) {
        attributeHandle_ = null;
      } else {
        attributeHandle_ = null;
        attributeHandleBuilder_ = null;
      }
      value_ = com.google.protobuf.ByteString.EMPTY;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_AttributeHandleValue_descriptor;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.AttributeHandleValue getDefaultInstanceForType() {
      return hla.rti1516_202X.fedpro.AttributeHandleValue.getDefaultInstance();
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.AttributeHandleValue build() {
      hla.rti1516_202X.fedpro.AttributeHandleValue result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.AttributeHandleValue buildPartial() {
      hla.rti1516_202X.fedpro.AttributeHandleValue result = new hla.rti1516_202X.fedpro.AttributeHandleValue(this);
      if (attributeHandleBuilder_ == null) {
        result.attributeHandle_ = attributeHandle_;
      } else {
        result.attributeHandle_ = attributeHandleBuilder_.build();
      }
      result.value_ = value_;
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
      if (other instanceof hla.rti1516_202X.fedpro.AttributeHandleValue) {
        return mergeFrom((hla.rti1516_202X.fedpro.AttributeHandleValue)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(hla.rti1516_202X.fedpro.AttributeHandleValue other) {
      if (other == hla.rti1516_202X.fedpro.AttributeHandleValue.getDefaultInstance()) return this;
      if (other.hasAttributeHandle()) {
        mergeAttributeHandle(other.getAttributeHandle());
      }
      if (other.getValue() != com.google.protobuf.ByteString.EMPTY) {
        setValue(other.getValue());
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
                  getAttributeHandleFieldBuilder().getBuilder(),
                  extensionRegistry);

              break;
            } // case 10
            case 18: {
              value_ = input.readBytes();

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

    private hla.rti1516_202X.fedpro.AttributeHandle attributeHandle_;
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.AttributeHandle, hla.rti1516_202X.fedpro.AttributeHandle.Builder, hla.rti1516_202X.fedpro.AttributeHandleOrBuilder> attributeHandleBuilder_;
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandle attributeHandle = 1;</code>
     * @return Whether the attributeHandle field is set.
     */
    public boolean hasAttributeHandle() {
      return attributeHandleBuilder_ != null || attributeHandle_ != null;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandle attributeHandle = 1;</code>
     * @return The attributeHandle.
     */
    public hla.rti1516_202X.fedpro.AttributeHandle getAttributeHandle() {
      if (attributeHandleBuilder_ == null) {
        return attributeHandle_ == null ? hla.rti1516_202X.fedpro.AttributeHandle.getDefaultInstance() : attributeHandle_;
      } else {
        return attributeHandleBuilder_.getMessage();
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandle attributeHandle = 1;</code>
     */
    public Builder setAttributeHandle(hla.rti1516_202X.fedpro.AttributeHandle value) {
      if (attributeHandleBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        attributeHandle_ = value;
        onChanged();
      } else {
        attributeHandleBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandle attributeHandle = 1;</code>
     */
    public Builder setAttributeHandle(
        hla.rti1516_202X.fedpro.AttributeHandle.Builder builderForValue) {
      if (attributeHandleBuilder_ == null) {
        attributeHandle_ = builderForValue.build();
        onChanged();
      } else {
        attributeHandleBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandle attributeHandle = 1;</code>
     */
    public Builder mergeAttributeHandle(hla.rti1516_202X.fedpro.AttributeHandle value) {
      if (attributeHandleBuilder_ == null) {
        if (attributeHandle_ != null) {
          attributeHandle_ =
            hla.rti1516_202X.fedpro.AttributeHandle.newBuilder(attributeHandle_).mergeFrom(value).buildPartial();
        } else {
          attributeHandle_ = value;
        }
        onChanged();
      } else {
        attributeHandleBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandle attributeHandle = 1;</code>
     */
    public Builder clearAttributeHandle() {
      if (attributeHandleBuilder_ == null) {
        attributeHandle_ = null;
        onChanged();
      } else {
        attributeHandle_ = null;
        attributeHandleBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandle attributeHandle = 1;</code>
     */
    public hla.rti1516_202X.fedpro.AttributeHandle.Builder getAttributeHandleBuilder() {
      
      onChanged();
      return getAttributeHandleFieldBuilder().getBuilder();
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandle attributeHandle = 1;</code>
     */
    public hla.rti1516_202X.fedpro.AttributeHandleOrBuilder getAttributeHandleOrBuilder() {
      if (attributeHandleBuilder_ != null) {
        return attributeHandleBuilder_.getMessageOrBuilder();
      } else {
        return attributeHandle_ == null ?
            hla.rti1516_202X.fedpro.AttributeHandle.getDefaultInstance() : attributeHandle_;
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandle attributeHandle = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.AttributeHandle, hla.rti1516_202X.fedpro.AttributeHandle.Builder, hla.rti1516_202X.fedpro.AttributeHandleOrBuilder> 
        getAttributeHandleFieldBuilder() {
      if (attributeHandleBuilder_ == null) {
        attributeHandleBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            hla.rti1516_202X.fedpro.AttributeHandle, hla.rti1516_202X.fedpro.AttributeHandle.Builder, hla.rti1516_202X.fedpro.AttributeHandleOrBuilder>(
                getAttributeHandle(),
                getParentForChildren(),
                isClean());
        attributeHandle_ = null;
      }
      return attributeHandleBuilder_;
    }

    private com.google.protobuf.ByteString value_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes value = 2;</code>
     * @return The value.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getValue() {
      return value_;
    }
    /**
     * <code>bytes value = 2;</code>
     * @param value The value to set.
     * @return This builder for chaining.
     */
    public Builder setValue(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      value_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bytes value = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearValue() {
      
      value_ = getDefaultInstance().getValue();
      onChanged();
      return this;
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


    // @@protoc_insertion_point(builder_scope:rti1516_202X.fedpro.AttributeHandleValue)
  }

  // @@protoc_insertion_point(class_scope:rti1516_202X.fedpro.AttributeHandleValue)
  private static final hla.rti1516_202X.fedpro.AttributeHandleValue DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new hla.rti1516_202X.fedpro.AttributeHandleValue();
  }

  public static hla.rti1516_202X.fedpro.AttributeHandleValue getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AttributeHandleValue>
      PARSER = new com.google.protobuf.AbstractParser<AttributeHandleValue>() {
    @java.lang.Override
    public AttributeHandleValue parsePartialFrom(
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

  public static com.google.protobuf.Parser<AttributeHandleValue> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<AttributeHandleValue> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public hla.rti1516_202X.fedpro.AttributeHandleValue getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

