// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RTIambassador.proto

package hla.rti1516_202X.fedpro;

/**
 * Protobuf type {@code rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest}
 */
public final class GetAvailableDimensionsForObjectClassRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest)
    GetAvailableDimensionsForObjectClassRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use GetAvailableDimensionsForObjectClassRequest.newBuilder() to construct.
  private GetAvailableDimensionsForObjectClassRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private GetAvailableDimensionsForObjectClassRequest() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new GetAvailableDimensionsForObjectClassRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_GetAvailableDimensionsForObjectClassRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_GetAvailableDimensionsForObjectClassRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest.class, hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest.Builder.class);
  }

  public static final int OBJECTCLASS_FIELD_NUMBER = 1;
  private hla.rti1516_202X.fedpro.ObjectClassHandle objectClass_;
  /**
   * <code>.rti1516_202X.fedpro.ObjectClassHandle objectClass = 1;</code>
   * @return Whether the objectClass field is set.
   */
  @java.lang.Override
  public boolean hasObjectClass() {
    return objectClass_ != null;
  }
  /**
   * <code>.rti1516_202X.fedpro.ObjectClassHandle objectClass = 1;</code>
   * @return The objectClass.
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.ObjectClassHandle getObjectClass() {
    return objectClass_ == null ? hla.rti1516_202X.fedpro.ObjectClassHandle.getDefaultInstance() : objectClass_;
  }
  /**
   * <code>.rti1516_202X.fedpro.ObjectClassHandle objectClass = 1;</code>
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.ObjectClassHandleOrBuilder getObjectClassOrBuilder() {
    return getObjectClass();
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
    if (objectClass_ != null) {
      output.writeMessage(1, getObjectClass());
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (objectClass_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getObjectClass());
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
    if (!(obj instanceof hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest)) {
      return super.equals(obj);
    }
    hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest other = (hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest) obj;

    if (hasObjectClass() != other.hasObjectClass()) return false;
    if (hasObjectClass()) {
      if (!getObjectClass()
          .equals(other.getObjectClass())) return false;
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
    if (hasObjectClass()) {
      hash = (37 * hash) + OBJECTCLASS_FIELD_NUMBER;
      hash = (53 * hash) + getObjectClass().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest parseFrom(
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
  public static Builder newBuilder(hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest prototype) {
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
   * Protobuf type {@code rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest)
      hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_GetAvailableDimensionsForObjectClassRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_GetAvailableDimensionsForObjectClassRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest.class, hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest.Builder.class);
    }

    // Construct using hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (objectClassBuilder_ == null) {
        objectClass_ = null;
      } else {
        objectClass_ = null;
        objectClassBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_GetAvailableDimensionsForObjectClassRequest_descriptor;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest getDefaultInstanceForType() {
      return hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest.getDefaultInstance();
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest build() {
      hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest buildPartial() {
      hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest result = new hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest(this);
      if (objectClassBuilder_ == null) {
        result.objectClass_ = objectClass_;
      } else {
        result.objectClass_ = objectClassBuilder_.build();
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
      if (other instanceof hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest) {
        return mergeFrom((hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest other) {
      if (other == hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest.getDefaultInstance()) return this;
      if (other.hasObjectClass()) {
        mergeObjectClass(other.getObjectClass());
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
                  getObjectClassFieldBuilder().getBuilder(),
                  extensionRegistry);

              break;
            } // case 10
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

    private hla.rti1516_202X.fedpro.ObjectClassHandle objectClass_;
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.ObjectClassHandle, hla.rti1516_202X.fedpro.ObjectClassHandle.Builder, hla.rti1516_202X.fedpro.ObjectClassHandleOrBuilder> objectClassBuilder_;
    /**
     * <code>.rti1516_202X.fedpro.ObjectClassHandle objectClass = 1;</code>
     * @return Whether the objectClass field is set.
     */
    public boolean hasObjectClass() {
      return objectClassBuilder_ != null || objectClass_ != null;
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectClassHandle objectClass = 1;</code>
     * @return The objectClass.
     */
    public hla.rti1516_202X.fedpro.ObjectClassHandle getObjectClass() {
      if (objectClassBuilder_ == null) {
        return objectClass_ == null ? hla.rti1516_202X.fedpro.ObjectClassHandle.getDefaultInstance() : objectClass_;
      } else {
        return objectClassBuilder_.getMessage();
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectClassHandle objectClass = 1;</code>
     */
    public Builder setObjectClass(hla.rti1516_202X.fedpro.ObjectClassHandle value) {
      if (objectClassBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        objectClass_ = value;
        onChanged();
      } else {
        objectClassBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectClassHandle objectClass = 1;</code>
     */
    public Builder setObjectClass(
        hla.rti1516_202X.fedpro.ObjectClassHandle.Builder builderForValue) {
      if (objectClassBuilder_ == null) {
        objectClass_ = builderForValue.build();
        onChanged();
      } else {
        objectClassBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectClassHandle objectClass = 1;</code>
     */
    public Builder mergeObjectClass(hla.rti1516_202X.fedpro.ObjectClassHandle value) {
      if (objectClassBuilder_ == null) {
        if (objectClass_ != null) {
          objectClass_ =
            hla.rti1516_202X.fedpro.ObjectClassHandle.newBuilder(objectClass_).mergeFrom(value).buildPartial();
        } else {
          objectClass_ = value;
        }
        onChanged();
      } else {
        objectClassBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectClassHandle objectClass = 1;</code>
     */
    public Builder clearObjectClass() {
      if (objectClassBuilder_ == null) {
        objectClass_ = null;
        onChanged();
      } else {
        objectClass_ = null;
        objectClassBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectClassHandle objectClass = 1;</code>
     */
    public hla.rti1516_202X.fedpro.ObjectClassHandle.Builder getObjectClassBuilder() {
      
      onChanged();
      return getObjectClassFieldBuilder().getBuilder();
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectClassHandle objectClass = 1;</code>
     */
    public hla.rti1516_202X.fedpro.ObjectClassHandleOrBuilder getObjectClassOrBuilder() {
      if (objectClassBuilder_ != null) {
        return objectClassBuilder_.getMessageOrBuilder();
      } else {
        return objectClass_ == null ?
            hla.rti1516_202X.fedpro.ObjectClassHandle.getDefaultInstance() : objectClass_;
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectClassHandle objectClass = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.ObjectClassHandle, hla.rti1516_202X.fedpro.ObjectClassHandle.Builder, hla.rti1516_202X.fedpro.ObjectClassHandleOrBuilder> 
        getObjectClassFieldBuilder() {
      if (objectClassBuilder_ == null) {
        objectClassBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            hla.rti1516_202X.fedpro.ObjectClassHandle, hla.rti1516_202X.fedpro.ObjectClassHandle.Builder, hla.rti1516_202X.fedpro.ObjectClassHandleOrBuilder>(
                getObjectClass(),
                getParentForChildren(),
                isClean());
        objectClass_ = null;
      }
      return objectClassBuilder_;
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


    // @@protoc_insertion_point(builder_scope:rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest)
  }

  // @@protoc_insertion_point(class_scope:rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest)
  private static final hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest();
  }

  public static hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<GetAvailableDimensionsForObjectClassRequest>
      PARSER = new com.google.protobuf.AbstractParser<GetAvailableDimensionsForObjectClassRequest>() {
    @java.lang.Override
    public GetAvailableDimensionsForObjectClassRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<GetAvailableDimensionsForObjectClassRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<GetAvailableDimensionsForObjectClassRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public hla.rti1516_202X.fedpro.GetAvailableDimensionsForObjectClassRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

