// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RTIambassador.proto

package hla.rti1516_202X.fedpro;

/**
 * Protobuf type {@code rti1516_202X.fedpro.ConfirmDivestitureRequest}
 */
public final class ConfirmDivestitureRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:rti1516_202X.fedpro.ConfirmDivestitureRequest)
    ConfirmDivestitureRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ConfirmDivestitureRequest.newBuilder() to construct.
  private ConfirmDivestitureRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ConfirmDivestitureRequest() {
    userSuppliedTag_ = com.google.protobuf.ByteString.EMPTY;
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ConfirmDivestitureRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_ConfirmDivestitureRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_ConfirmDivestitureRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            hla.rti1516_202X.fedpro.ConfirmDivestitureRequest.class, hla.rti1516_202X.fedpro.ConfirmDivestitureRequest.Builder.class);
  }

  public static final int OBJECTINSTANCE_FIELD_NUMBER = 1;
  private hla.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance_;
  /**
   * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
   * @return Whether the objectInstance field is set.
   */
  @java.lang.Override
  public boolean hasObjectInstance() {
    return objectInstance_ != null;
  }
  /**
   * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
   * @return The objectInstance.
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.ObjectInstanceHandle getObjectInstance() {
    return objectInstance_ == null ? hla.rti1516_202X.fedpro.ObjectInstanceHandle.getDefaultInstance() : objectInstance_;
  }
  /**
   * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.ObjectInstanceHandleOrBuilder getObjectInstanceOrBuilder() {
    return getObjectInstance();
  }

  public static final int CONFIRMEDATTRIBUTES_FIELD_NUMBER = 2;
  private hla.rti1516_202X.fedpro.AttributeHandleSet confirmedAttributes_;
  /**
   * <code>.rti1516_202X.fedpro.AttributeHandleSet confirmedAttributes = 2;</code>
   * @return Whether the confirmedAttributes field is set.
   */
  @java.lang.Override
  public boolean hasConfirmedAttributes() {
    return confirmedAttributes_ != null;
  }
  /**
   * <code>.rti1516_202X.fedpro.AttributeHandleSet confirmedAttributes = 2;</code>
   * @return The confirmedAttributes.
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.AttributeHandleSet getConfirmedAttributes() {
    return confirmedAttributes_ == null ? hla.rti1516_202X.fedpro.AttributeHandleSet.getDefaultInstance() : confirmedAttributes_;
  }
  /**
   * <code>.rti1516_202X.fedpro.AttributeHandleSet confirmedAttributes = 2;</code>
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.AttributeHandleSetOrBuilder getConfirmedAttributesOrBuilder() {
    return getConfirmedAttributes();
  }

  public static final int USERSUPPLIEDTAG_FIELD_NUMBER = 3;
  private com.google.protobuf.ByteString userSuppliedTag_;
  /**
   * <code>bytes userSuppliedTag = 3;</code>
   * @return The userSuppliedTag.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString getUserSuppliedTag() {
    return userSuppliedTag_;
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
    if (objectInstance_ != null) {
      output.writeMessage(1, getObjectInstance());
    }
    if (confirmedAttributes_ != null) {
      output.writeMessage(2, getConfirmedAttributes());
    }
    if (!userSuppliedTag_.isEmpty()) {
      output.writeBytes(3, userSuppliedTag_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (objectInstance_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getObjectInstance());
    }
    if (confirmedAttributes_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, getConfirmedAttributes());
    }
    if (!userSuppliedTag_.isEmpty()) {
      size += com.google.protobuf.CodedOutputStream
        .computeBytesSize(3, userSuppliedTag_);
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
    if (!(obj instanceof hla.rti1516_202X.fedpro.ConfirmDivestitureRequest)) {
      return super.equals(obj);
    }
    hla.rti1516_202X.fedpro.ConfirmDivestitureRequest other = (hla.rti1516_202X.fedpro.ConfirmDivestitureRequest) obj;

    if (hasObjectInstance() != other.hasObjectInstance()) return false;
    if (hasObjectInstance()) {
      if (!getObjectInstance()
          .equals(other.getObjectInstance())) return false;
    }
    if (hasConfirmedAttributes() != other.hasConfirmedAttributes()) return false;
    if (hasConfirmedAttributes()) {
      if (!getConfirmedAttributes()
          .equals(other.getConfirmedAttributes())) return false;
    }
    if (!getUserSuppliedTag()
        .equals(other.getUserSuppliedTag())) return false;
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
    if (hasObjectInstance()) {
      hash = (37 * hash) + OBJECTINSTANCE_FIELD_NUMBER;
      hash = (53 * hash) + getObjectInstance().hashCode();
    }
    if (hasConfirmedAttributes()) {
      hash = (37 * hash) + CONFIRMEDATTRIBUTES_FIELD_NUMBER;
      hash = (53 * hash) + getConfirmedAttributes().hashCode();
    }
    hash = (37 * hash) + USERSUPPLIEDTAG_FIELD_NUMBER;
    hash = (53 * hash) + getUserSuppliedTag().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static hla.rti1516_202X.fedpro.ConfirmDivestitureRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.ConfirmDivestitureRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ConfirmDivestitureRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.ConfirmDivestitureRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ConfirmDivestitureRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.ConfirmDivestitureRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ConfirmDivestitureRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.ConfirmDivestitureRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ConfirmDivestitureRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.ConfirmDivestitureRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ConfirmDivestitureRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.ConfirmDivestitureRequest parseFrom(
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
  public static Builder newBuilder(hla.rti1516_202X.fedpro.ConfirmDivestitureRequest prototype) {
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
   * Protobuf type {@code rti1516_202X.fedpro.ConfirmDivestitureRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:rti1516_202X.fedpro.ConfirmDivestitureRequest)
      hla.rti1516_202X.fedpro.ConfirmDivestitureRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_ConfirmDivestitureRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_ConfirmDivestitureRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              hla.rti1516_202X.fedpro.ConfirmDivestitureRequest.class, hla.rti1516_202X.fedpro.ConfirmDivestitureRequest.Builder.class);
    }

    // Construct using hla.rti1516_202X.fedpro.ConfirmDivestitureRequest.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (objectInstanceBuilder_ == null) {
        objectInstance_ = null;
      } else {
        objectInstance_ = null;
        objectInstanceBuilder_ = null;
      }
      if (confirmedAttributesBuilder_ == null) {
        confirmedAttributes_ = null;
      } else {
        confirmedAttributes_ = null;
        confirmedAttributesBuilder_ = null;
      }
      userSuppliedTag_ = com.google.protobuf.ByteString.EMPTY;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_ConfirmDivestitureRequest_descriptor;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.ConfirmDivestitureRequest getDefaultInstanceForType() {
      return hla.rti1516_202X.fedpro.ConfirmDivestitureRequest.getDefaultInstance();
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.ConfirmDivestitureRequest build() {
      hla.rti1516_202X.fedpro.ConfirmDivestitureRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.ConfirmDivestitureRequest buildPartial() {
      hla.rti1516_202X.fedpro.ConfirmDivestitureRequest result = new hla.rti1516_202X.fedpro.ConfirmDivestitureRequest(this);
      if (objectInstanceBuilder_ == null) {
        result.objectInstance_ = objectInstance_;
      } else {
        result.objectInstance_ = objectInstanceBuilder_.build();
      }
      if (confirmedAttributesBuilder_ == null) {
        result.confirmedAttributes_ = confirmedAttributes_;
      } else {
        result.confirmedAttributes_ = confirmedAttributesBuilder_.build();
      }
      result.userSuppliedTag_ = userSuppliedTag_;
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
      if (other instanceof hla.rti1516_202X.fedpro.ConfirmDivestitureRequest) {
        return mergeFrom((hla.rti1516_202X.fedpro.ConfirmDivestitureRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(hla.rti1516_202X.fedpro.ConfirmDivestitureRequest other) {
      if (other == hla.rti1516_202X.fedpro.ConfirmDivestitureRequest.getDefaultInstance()) return this;
      if (other.hasObjectInstance()) {
        mergeObjectInstance(other.getObjectInstance());
      }
      if (other.hasConfirmedAttributes()) {
        mergeConfirmedAttributes(other.getConfirmedAttributes());
      }
      if (other.getUserSuppliedTag() != com.google.protobuf.ByteString.EMPTY) {
        setUserSuppliedTag(other.getUserSuppliedTag());
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
                  getObjectInstanceFieldBuilder().getBuilder(),
                  extensionRegistry);

              break;
            } // case 10
            case 18: {
              input.readMessage(
                  getConfirmedAttributesFieldBuilder().getBuilder(),
                  extensionRegistry);

              break;
            } // case 18
            case 26: {
              userSuppliedTag_ = input.readBytes();

              break;
            } // case 26
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

    private hla.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance_;
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.ObjectInstanceHandle, hla.rti1516_202X.fedpro.ObjectInstanceHandle.Builder, hla.rti1516_202X.fedpro.ObjectInstanceHandleOrBuilder> objectInstanceBuilder_;
    /**
     * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
     * @return Whether the objectInstance field is set.
     */
    public boolean hasObjectInstance() {
      return objectInstanceBuilder_ != null || objectInstance_ != null;
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
     * @return The objectInstance.
     */
    public hla.rti1516_202X.fedpro.ObjectInstanceHandle getObjectInstance() {
      if (objectInstanceBuilder_ == null) {
        return objectInstance_ == null ? hla.rti1516_202X.fedpro.ObjectInstanceHandle.getDefaultInstance() : objectInstance_;
      } else {
        return objectInstanceBuilder_.getMessage();
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
     */
    public Builder setObjectInstance(hla.rti1516_202X.fedpro.ObjectInstanceHandle value) {
      if (objectInstanceBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        objectInstance_ = value;
        onChanged();
      } else {
        objectInstanceBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
     */
    public Builder setObjectInstance(
        hla.rti1516_202X.fedpro.ObjectInstanceHandle.Builder builderForValue) {
      if (objectInstanceBuilder_ == null) {
        objectInstance_ = builderForValue.build();
        onChanged();
      } else {
        objectInstanceBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
     */
    public Builder mergeObjectInstance(hla.rti1516_202X.fedpro.ObjectInstanceHandle value) {
      if (objectInstanceBuilder_ == null) {
        if (objectInstance_ != null) {
          objectInstance_ =
            hla.rti1516_202X.fedpro.ObjectInstanceHandle.newBuilder(objectInstance_).mergeFrom(value).buildPartial();
        } else {
          objectInstance_ = value;
        }
        onChanged();
      } else {
        objectInstanceBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
     */
    public Builder clearObjectInstance() {
      if (objectInstanceBuilder_ == null) {
        objectInstance_ = null;
        onChanged();
      } else {
        objectInstance_ = null;
        objectInstanceBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
     */
    public hla.rti1516_202X.fedpro.ObjectInstanceHandle.Builder getObjectInstanceBuilder() {
      
      onChanged();
      return getObjectInstanceFieldBuilder().getBuilder();
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
     */
    public hla.rti1516_202X.fedpro.ObjectInstanceHandleOrBuilder getObjectInstanceOrBuilder() {
      if (objectInstanceBuilder_ != null) {
        return objectInstanceBuilder_.getMessageOrBuilder();
      } else {
        return objectInstance_ == null ?
            hla.rti1516_202X.fedpro.ObjectInstanceHandle.getDefaultInstance() : objectInstance_;
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.ObjectInstanceHandle objectInstance = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.ObjectInstanceHandle, hla.rti1516_202X.fedpro.ObjectInstanceHandle.Builder, hla.rti1516_202X.fedpro.ObjectInstanceHandleOrBuilder> 
        getObjectInstanceFieldBuilder() {
      if (objectInstanceBuilder_ == null) {
        objectInstanceBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            hla.rti1516_202X.fedpro.ObjectInstanceHandle, hla.rti1516_202X.fedpro.ObjectInstanceHandle.Builder, hla.rti1516_202X.fedpro.ObjectInstanceHandleOrBuilder>(
                getObjectInstance(),
                getParentForChildren(),
                isClean());
        objectInstance_ = null;
      }
      return objectInstanceBuilder_;
    }

    private hla.rti1516_202X.fedpro.AttributeHandleSet confirmedAttributes_;
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.AttributeHandleSet, hla.rti1516_202X.fedpro.AttributeHandleSet.Builder, hla.rti1516_202X.fedpro.AttributeHandleSetOrBuilder> confirmedAttributesBuilder_;
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet confirmedAttributes = 2;</code>
     * @return Whether the confirmedAttributes field is set.
     */
    public boolean hasConfirmedAttributes() {
      return confirmedAttributesBuilder_ != null || confirmedAttributes_ != null;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet confirmedAttributes = 2;</code>
     * @return The confirmedAttributes.
     */
    public hla.rti1516_202X.fedpro.AttributeHandleSet getConfirmedAttributes() {
      if (confirmedAttributesBuilder_ == null) {
        return confirmedAttributes_ == null ? hla.rti1516_202X.fedpro.AttributeHandleSet.getDefaultInstance() : confirmedAttributes_;
      } else {
        return confirmedAttributesBuilder_.getMessage();
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet confirmedAttributes = 2;</code>
     */
    public Builder setConfirmedAttributes(hla.rti1516_202X.fedpro.AttributeHandleSet value) {
      if (confirmedAttributesBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        confirmedAttributes_ = value;
        onChanged();
      } else {
        confirmedAttributesBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet confirmedAttributes = 2;</code>
     */
    public Builder setConfirmedAttributes(
        hla.rti1516_202X.fedpro.AttributeHandleSet.Builder builderForValue) {
      if (confirmedAttributesBuilder_ == null) {
        confirmedAttributes_ = builderForValue.build();
        onChanged();
      } else {
        confirmedAttributesBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet confirmedAttributes = 2;</code>
     */
    public Builder mergeConfirmedAttributes(hla.rti1516_202X.fedpro.AttributeHandleSet value) {
      if (confirmedAttributesBuilder_ == null) {
        if (confirmedAttributes_ != null) {
          confirmedAttributes_ =
            hla.rti1516_202X.fedpro.AttributeHandleSet.newBuilder(confirmedAttributes_).mergeFrom(value).buildPartial();
        } else {
          confirmedAttributes_ = value;
        }
        onChanged();
      } else {
        confirmedAttributesBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet confirmedAttributes = 2;</code>
     */
    public Builder clearConfirmedAttributes() {
      if (confirmedAttributesBuilder_ == null) {
        confirmedAttributes_ = null;
        onChanged();
      } else {
        confirmedAttributes_ = null;
        confirmedAttributesBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet confirmedAttributes = 2;</code>
     */
    public hla.rti1516_202X.fedpro.AttributeHandleSet.Builder getConfirmedAttributesBuilder() {
      
      onChanged();
      return getConfirmedAttributesFieldBuilder().getBuilder();
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet confirmedAttributes = 2;</code>
     */
    public hla.rti1516_202X.fedpro.AttributeHandleSetOrBuilder getConfirmedAttributesOrBuilder() {
      if (confirmedAttributesBuilder_ != null) {
        return confirmedAttributesBuilder_.getMessageOrBuilder();
      } else {
        return confirmedAttributes_ == null ?
            hla.rti1516_202X.fedpro.AttributeHandleSet.getDefaultInstance() : confirmedAttributes_;
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.AttributeHandleSet confirmedAttributes = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.AttributeHandleSet, hla.rti1516_202X.fedpro.AttributeHandleSet.Builder, hla.rti1516_202X.fedpro.AttributeHandleSetOrBuilder> 
        getConfirmedAttributesFieldBuilder() {
      if (confirmedAttributesBuilder_ == null) {
        confirmedAttributesBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            hla.rti1516_202X.fedpro.AttributeHandleSet, hla.rti1516_202X.fedpro.AttributeHandleSet.Builder, hla.rti1516_202X.fedpro.AttributeHandleSetOrBuilder>(
                getConfirmedAttributes(),
                getParentForChildren(),
                isClean());
        confirmedAttributes_ = null;
      }
      return confirmedAttributesBuilder_;
    }

    private com.google.protobuf.ByteString userSuppliedTag_ = com.google.protobuf.ByteString.EMPTY;
    /**
     * <code>bytes userSuppliedTag = 3;</code>
     * @return The userSuppliedTag.
     */
    @java.lang.Override
    public com.google.protobuf.ByteString getUserSuppliedTag() {
      return userSuppliedTag_;
    }
    /**
     * <code>bytes userSuppliedTag = 3;</code>
     * @param value The userSuppliedTag to set.
     * @return This builder for chaining.
     */
    public Builder setUserSuppliedTag(com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      userSuppliedTag_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bytes userSuppliedTag = 3;</code>
     * @return This builder for chaining.
     */
    public Builder clearUserSuppliedTag() {
      
      userSuppliedTag_ = getDefaultInstance().getUserSuppliedTag();
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


    // @@protoc_insertion_point(builder_scope:rti1516_202X.fedpro.ConfirmDivestitureRequest)
  }

  // @@protoc_insertion_point(class_scope:rti1516_202X.fedpro.ConfirmDivestitureRequest)
  private static final hla.rti1516_202X.fedpro.ConfirmDivestitureRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new hla.rti1516_202X.fedpro.ConfirmDivestitureRequest();
  }

  public static hla.rti1516_202X.fedpro.ConfirmDivestitureRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ConfirmDivestitureRequest>
      PARSER = new com.google.protobuf.AbstractParser<ConfirmDivestitureRequest>() {
    @java.lang.Override
    public ConfirmDivestitureRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<ConfirmDivestitureRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ConfirmDivestitureRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public hla.rti1516_202X.fedpro.ConfirmDivestitureRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

