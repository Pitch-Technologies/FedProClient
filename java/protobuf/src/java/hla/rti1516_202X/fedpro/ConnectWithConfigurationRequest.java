// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RTIambassador.proto

package hla.rti1516_202X.fedpro;

/**
 * Protobuf type {@code rti1516_202X.fedpro.ConnectWithConfigurationRequest}
 */
public final class ConnectWithConfigurationRequest extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:rti1516_202X.fedpro.ConnectWithConfigurationRequest)
    ConnectWithConfigurationRequestOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ConnectWithConfigurationRequest.newBuilder() to construct.
  private ConnectWithConfigurationRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ConnectWithConfigurationRequest() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ConnectWithConfigurationRequest();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_ConnectWithConfigurationRequest_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_ConnectWithConfigurationRequest_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest.class, hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest.Builder.class);
  }

  public static final int RTICONFIGURATION_FIELD_NUMBER = 1;
  private hla.rti1516_202X.fedpro.RtiConfiguration rtiConfiguration_;
  /**
   * <code>.rti1516_202X.fedpro.RtiConfiguration rtiConfiguration = 1;</code>
   * @return Whether the rtiConfiguration field is set.
   */
  @java.lang.Override
  public boolean hasRtiConfiguration() {
    return rtiConfiguration_ != null;
  }
  /**
   * <code>.rti1516_202X.fedpro.RtiConfiguration rtiConfiguration = 1;</code>
   * @return The rtiConfiguration.
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.RtiConfiguration getRtiConfiguration() {
    return rtiConfiguration_ == null ? hla.rti1516_202X.fedpro.RtiConfiguration.getDefaultInstance() : rtiConfiguration_;
  }
  /**
   * <code>.rti1516_202X.fedpro.RtiConfiguration rtiConfiguration = 1;</code>
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.RtiConfigurationOrBuilder getRtiConfigurationOrBuilder() {
    return getRtiConfiguration();
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
    if (rtiConfiguration_ != null) {
      output.writeMessage(1, getRtiConfiguration());
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (rtiConfiguration_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getRtiConfiguration());
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
    if (!(obj instanceof hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest)) {
      return super.equals(obj);
    }
    hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest other = (hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest) obj;

    if (hasRtiConfiguration() != other.hasRtiConfiguration()) return false;
    if (hasRtiConfiguration()) {
      if (!getRtiConfiguration()
          .equals(other.getRtiConfiguration())) return false;
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
    if (hasRtiConfiguration()) {
      hash = (37 * hash) + RTICONFIGURATION_FIELD_NUMBER;
      hash = (53 * hash) + getRtiConfiguration().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest parseFrom(
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
  public static Builder newBuilder(hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest prototype) {
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
   * Protobuf type {@code rti1516_202X.fedpro.ConnectWithConfigurationRequest}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:rti1516_202X.fedpro.ConnectWithConfigurationRequest)
      hla.rti1516_202X.fedpro.ConnectWithConfigurationRequestOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_ConnectWithConfigurationRequest_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_ConnectWithConfigurationRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest.class, hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest.Builder.class);
    }

    // Construct using hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (rtiConfigurationBuilder_ == null) {
        rtiConfiguration_ = null;
      } else {
        rtiConfiguration_ = null;
        rtiConfigurationBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_ConnectWithConfigurationRequest_descriptor;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest getDefaultInstanceForType() {
      return hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest.getDefaultInstance();
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest build() {
      hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest buildPartial() {
      hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest result = new hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest(this);
      if (rtiConfigurationBuilder_ == null) {
        result.rtiConfiguration_ = rtiConfiguration_;
      } else {
        result.rtiConfiguration_ = rtiConfigurationBuilder_.build();
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
      if (other instanceof hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest) {
        return mergeFrom((hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest other) {
      if (other == hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest.getDefaultInstance()) return this;
      if (other.hasRtiConfiguration()) {
        mergeRtiConfiguration(other.getRtiConfiguration());
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
                  getRtiConfigurationFieldBuilder().getBuilder(),
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

    private hla.rti1516_202X.fedpro.RtiConfiguration rtiConfiguration_;
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.RtiConfiguration, hla.rti1516_202X.fedpro.RtiConfiguration.Builder, hla.rti1516_202X.fedpro.RtiConfigurationOrBuilder> rtiConfigurationBuilder_;
    /**
     * <code>.rti1516_202X.fedpro.RtiConfiguration rtiConfiguration = 1;</code>
     * @return Whether the rtiConfiguration field is set.
     */
    public boolean hasRtiConfiguration() {
      return rtiConfigurationBuilder_ != null || rtiConfiguration_ != null;
    }
    /**
     * <code>.rti1516_202X.fedpro.RtiConfiguration rtiConfiguration = 1;</code>
     * @return The rtiConfiguration.
     */
    public hla.rti1516_202X.fedpro.RtiConfiguration getRtiConfiguration() {
      if (rtiConfigurationBuilder_ == null) {
        return rtiConfiguration_ == null ? hla.rti1516_202X.fedpro.RtiConfiguration.getDefaultInstance() : rtiConfiguration_;
      } else {
        return rtiConfigurationBuilder_.getMessage();
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.RtiConfiguration rtiConfiguration = 1;</code>
     */
    public Builder setRtiConfiguration(hla.rti1516_202X.fedpro.RtiConfiguration value) {
      if (rtiConfigurationBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        rtiConfiguration_ = value;
        onChanged();
      } else {
        rtiConfigurationBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.RtiConfiguration rtiConfiguration = 1;</code>
     */
    public Builder setRtiConfiguration(
        hla.rti1516_202X.fedpro.RtiConfiguration.Builder builderForValue) {
      if (rtiConfigurationBuilder_ == null) {
        rtiConfiguration_ = builderForValue.build();
        onChanged();
      } else {
        rtiConfigurationBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.RtiConfiguration rtiConfiguration = 1;</code>
     */
    public Builder mergeRtiConfiguration(hla.rti1516_202X.fedpro.RtiConfiguration value) {
      if (rtiConfigurationBuilder_ == null) {
        if (rtiConfiguration_ != null) {
          rtiConfiguration_ =
            hla.rti1516_202X.fedpro.RtiConfiguration.newBuilder(rtiConfiguration_).mergeFrom(value).buildPartial();
        } else {
          rtiConfiguration_ = value;
        }
        onChanged();
      } else {
        rtiConfigurationBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.RtiConfiguration rtiConfiguration = 1;</code>
     */
    public Builder clearRtiConfiguration() {
      if (rtiConfigurationBuilder_ == null) {
        rtiConfiguration_ = null;
        onChanged();
      } else {
        rtiConfiguration_ = null;
        rtiConfigurationBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.RtiConfiguration rtiConfiguration = 1;</code>
     */
    public hla.rti1516_202X.fedpro.RtiConfiguration.Builder getRtiConfigurationBuilder() {
      
      onChanged();
      return getRtiConfigurationFieldBuilder().getBuilder();
    }
    /**
     * <code>.rti1516_202X.fedpro.RtiConfiguration rtiConfiguration = 1;</code>
     */
    public hla.rti1516_202X.fedpro.RtiConfigurationOrBuilder getRtiConfigurationOrBuilder() {
      if (rtiConfigurationBuilder_ != null) {
        return rtiConfigurationBuilder_.getMessageOrBuilder();
      } else {
        return rtiConfiguration_ == null ?
            hla.rti1516_202X.fedpro.RtiConfiguration.getDefaultInstance() : rtiConfiguration_;
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.RtiConfiguration rtiConfiguration = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.RtiConfiguration, hla.rti1516_202X.fedpro.RtiConfiguration.Builder, hla.rti1516_202X.fedpro.RtiConfigurationOrBuilder> 
        getRtiConfigurationFieldBuilder() {
      if (rtiConfigurationBuilder_ == null) {
        rtiConfigurationBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            hla.rti1516_202X.fedpro.RtiConfiguration, hla.rti1516_202X.fedpro.RtiConfiguration.Builder, hla.rti1516_202X.fedpro.RtiConfigurationOrBuilder>(
                getRtiConfiguration(),
                getParentForChildren(),
                isClean());
        rtiConfiguration_ = null;
      }
      return rtiConfigurationBuilder_;
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


    // @@protoc_insertion_point(builder_scope:rti1516_202X.fedpro.ConnectWithConfigurationRequest)
  }

  // @@protoc_insertion_point(class_scope:rti1516_202X.fedpro.ConnectWithConfigurationRequest)
  private static final hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest();
  }

  public static hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ConnectWithConfigurationRequest>
      PARSER = new com.google.protobuf.AbstractParser<ConnectWithConfigurationRequest>() {
    @java.lang.Override
    public ConnectWithConfigurationRequest parsePartialFrom(
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

  public static com.google.protobuf.Parser<ConnectWithConfigurationRequest> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ConnectWithConfigurationRequest> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public hla.rti1516_202X.fedpro.ConnectWithConfigurationRequest getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

