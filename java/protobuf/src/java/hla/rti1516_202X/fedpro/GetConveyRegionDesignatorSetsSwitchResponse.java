// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: RTIambassador.proto

package hla.rti1516_202X.fedpro;

/**
 * Protobuf type {@code rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse}
 */
public final class GetConveyRegionDesignatorSetsSwitchResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse)
    GetConveyRegionDesignatorSetsSwitchResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use GetConveyRegionDesignatorSetsSwitchResponse.newBuilder() to construct.
  private GetConveyRegionDesignatorSetsSwitchResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private GetConveyRegionDesignatorSetsSwitchResponse() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new GetConveyRegionDesignatorSetsSwitchResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_GetConveyRegionDesignatorSetsSwitchResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_GetConveyRegionDesignatorSetsSwitchResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse.class, hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse.Builder.class);
  }

  public static final int RESULT_FIELD_NUMBER = 1;
  private boolean result_;
  /**
   * <code>bool result = 1;</code>
   * @return The result.
   */
  @java.lang.Override
  public boolean getResult() {
    return result_;
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
    if (result_ != false) {
      output.writeBool(1, result_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (result_ != false) {
      size += com.google.protobuf.CodedOutputStream
        .computeBoolSize(1, result_);
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
    if (!(obj instanceof hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse)) {
      return super.equals(obj);
    }
    hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse other = (hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse) obj;

    if (getResult()
        != other.getResult()) return false;
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
    hash = (37 * hash) + RESULT_FIELD_NUMBER;
    hash = (53 * hash) + com.google.protobuf.Internal.hashBoolean(
        getResult());
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse parseFrom(
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
  public static Builder newBuilder(hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse prototype) {
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
   * Protobuf type {@code rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse)
      hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_GetConveyRegionDesignatorSetsSwitchResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_GetConveyRegionDesignatorSetsSwitchResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse.class, hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse.Builder.class);
    }

    // Construct using hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      result_ = false;

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return hla.rti1516_202X.fedpro.RTIambassador.internal_static_rti1516_202X_fedpro_GetConveyRegionDesignatorSetsSwitchResponse_descriptor;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse getDefaultInstanceForType() {
      return hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse.getDefaultInstance();
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse build() {
      hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse buildPartial() {
      hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse result = new hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse(this);
      result.result_ = result_;
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
      if (other instanceof hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse) {
        return mergeFrom((hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse other) {
      if (other == hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse.getDefaultInstance()) return this;
      if (other.getResult() != false) {
        setResult(other.getResult());
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
            case 8: {
              result_ = input.readBool();

              break;
            } // case 8
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

    private boolean result_ ;
    /**
     * <code>bool result = 1;</code>
     * @return The result.
     */
    @java.lang.Override
    public boolean getResult() {
      return result_;
    }
    /**
     * <code>bool result = 1;</code>
     * @param value The result to set.
     * @return This builder for chaining.
     */
    public Builder setResult(boolean value) {
      
      result_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>bool result = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearResult() {
      
      result_ = false;
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


    // @@protoc_insertion_point(builder_scope:rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse)
  }

  // @@protoc_insertion_point(class_scope:rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse)
  private static final hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse();
  }

  public static hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<GetConveyRegionDesignatorSetsSwitchResponse>
      PARSER = new com.google.protobuf.AbstractParser<GetConveyRegionDesignatorSetsSwitchResponse>() {
    @java.lang.Override
    public GetConveyRegionDesignatorSetsSwitchResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<GetConveyRegionDesignatorSetsSwitchResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<GetConveyRegionDesignatorSetsSwitchResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public hla.rti1516_202X.fedpro.GetConveyRegionDesignatorSetsSwitchResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

