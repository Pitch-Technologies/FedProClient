// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: datatypes.proto

package hla.rti1516_202X.fedpro;

/**
 * Protobuf type {@code rti1516_202X.fedpro.FederationExecutionInformation}
 */
public final class FederationExecutionInformation extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:rti1516_202X.fedpro.FederationExecutionInformation)
    FederationExecutionInformationOrBuilder {
private static final long serialVersionUID = 0L;
  // Use FederationExecutionInformation.newBuilder() to construct.
  private FederationExecutionInformation(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private FederationExecutionInformation() {
    federationExecutionName_ = "";
    logicalTimeImplementationName_ = "";
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new FederationExecutionInformation();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_FederationExecutionInformation_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_FederationExecutionInformation_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            hla.rti1516_202X.fedpro.FederationExecutionInformation.class, hla.rti1516_202X.fedpro.FederationExecutionInformation.Builder.class);
  }

  public static final int FEDERATIONEXECUTIONNAME_FIELD_NUMBER = 1;
  private volatile java.lang.Object federationExecutionName_;
  /**
   * <code>string federationExecutionName = 1;</code>
   * @return The federationExecutionName.
   */
  @java.lang.Override
  public java.lang.String getFederationExecutionName() {
    java.lang.Object ref = federationExecutionName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      federationExecutionName_ = s;
      return s;
    }
  }
  /**
   * <code>string federationExecutionName = 1;</code>
   * @return The bytes for federationExecutionName.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getFederationExecutionNameBytes() {
    java.lang.Object ref = federationExecutionName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      federationExecutionName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
  }

  public static final int LOGICALTIMEIMPLEMENTATIONNAME_FIELD_NUMBER = 2;
  private volatile java.lang.Object logicalTimeImplementationName_;
  /**
   * <code>string logicalTimeImplementationName = 2;</code>
   * @return The logicalTimeImplementationName.
   */
  @java.lang.Override
  public java.lang.String getLogicalTimeImplementationName() {
    java.lang.Object ref = logicalTimeImplementationName_;
    if (ref instanceof java.lang.String) {
      return (java.lang.String) ref;
    } else {
      com.google.protobuf.ByteString bs = 
          (com.google.protobuf.ByteString) ref;
      java.lang.String s = bs.toStringUtf8();
      logicalTimeImplementationName_ = s;
      return s;
    }
  }
  /**
   * <code>string logicalTimeImplementationName = 2;</code>
   * @return The bytes for logicalTimeImplementationName.
   */
  @java.lang.Override
  public com.google.protobuf.ByteString
      getLogicalTimeImplementationNameBytes() {
    java.lang.Object ref = logicalTimeImplementationName_;
    if (ref instanceof java.lang.String) {
      com.google.protobuf.ByteString b = 
          com.google.protobuf.ByteString.copyFromUtf8(
              (java.lang.String) ref);
      logicalTimeImplementationName_ = b;
      return b;
    } else {
      return (com.google.protobuf.ByteString) ref;
    }
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
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(federationExecutionName_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 1, federationExecutionName_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(logicalTimeImplementationName_)) {
      com.google.protobuf.GeneratedMessageV3.writeString(output, 2, logicalTimeImplementationName_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(federationExecutionName_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, federationExecutionName_);
    }
    if (!com.google.protobuf.GeneratedMessageV3.isStringEmpty(logicalTimeImplementationName_)) {
      size += com.google.protobuf.GeneratedMessageV3.computeStringSize(2, logicalTimeImplementationName_);
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
    if (!(obj instanceof hla.rti1516_202X.fedpro.FederationExecutionInformation)) {
      return super.equals(obj);
    }
    hla.rti1516_202X.fedpro.FederationExecutionInformation other = (hla.rti1516_202X.fedpro.FederationExecutionInformation) obj;

    if (!getFederationExecutionName()
        .equals(other.getFederationExecutionName())) return false;
    if (!getLogicalTimeImplementationName()
        .equals(other.getLogicalTimeImplementationName())) return false;
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
    hash = (37 * hash) + FEDERATIONEXECUTIONNAME_FIELD_NUMBER;
    hash = (53 * hash) + getFederationExecutionName().hashCode();
    hash = (37 * hash) + LOGICALTIMEIMPLEMENTATIONNAME_FIELD_NUMBER;
    hash = (53 * hash) + getLogicalTimeImplementationName().hashCode();
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static hla.rti1516_202X.fedpro.FederationExecutionInformation parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.FederationExecutionInformation parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.FederationExecutionInformation parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.FederationExecutionInformation parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.FederationExecutionInformation parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.FederationExecutionInformation parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.FederationExecutionInformation parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.FederationExecutionInformation parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.FederationExecutionInformation parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.FederationExecutionInformation parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.FederationExecutionInformation parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.FederationExecutionInformation parseFrom(
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
  public static Builder newBuilder(hla.rti1516_202X.fedpro.FederationExecutionInformation prototype) {
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
   * Protobuf type {@code rti1516_202X.fedpro.FederationExecutionInformation}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:rti1516_202X.fedpro.FederationExecutionInformation)
      hla.rti1516_202X.fedpro.FederationExecutionInformationOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_FederationExecutionInformation_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_FederationExecutionInformation_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              hla.rti1516_202X.fedpro.FederationExecutionInformation.class, hla.rti1516_202X.fedpro.FederationExecutionInformation.Builder.class);
    }

    // Construct using hla.rti1516_202X.fedpro.FederationExecutionInformation.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      federationExecutionName_ = "";

      logicalTimeImplementationName_ = "";

      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return hla.rti1516_202X.fedpro.Datatypes.internal_static_rti1516_202X_fedpro_FederationExecutionInformation_descriptor;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.FederationExecutionInformation getDefaultInstanceForType() {
      return hla.rti1516_202X.fedpro.FederationExecutionInformation.getDefaultInstance();
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.FederationExecutionInformation build() {
      hla.rti1516_202X.fedpro.FederationExecutionInformation result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.FederationExecutionInformation buildPartial() {
      hla.rti1516_202X.fedpro.FederationExecutionInformation result = new hla.rti1516_202X.fedpro.FederationExecutionInformation(this);
      result.federationExecutionName_ = federationExecutionName_;
      result.logicalTimeImplementationName_ = logicalTimeImplementationName_;
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
      if (other instanceof hla.rti1516_202X.fedpro.FederationExecutionInformation) {
        return mergeFrom((hla.rti1516_202X.fedpro.FederationExecutionInformation)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(hla.rti1516_202X.fedpro.FederationExecutionInformation other) {
      if (other == hla.rti1516_202X.fedpro.FederationExecutionInformation.getDefaultInstance()) return this;
      if (!other.getFederationExecutionName().isEmpty()) {
        federationExecutionName_ = other.federationExecutionName_;
        onChanged();
      }
      if (!other.getLogicalTimeImplementationName().isEmpty()) {
        logicalTimeImplementationName_ = other.logicalTimeImplementationName_;
        onChanged();
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
              federationExecutionName_ = input.readStringRequireUtf8();

              break;
            } // case 10
            case 18: {
              logicalTimeImplementationName_ = input.readStringRequireUtf8();

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

    private java.lang.Object federationExecutionName_ = "";
    /**
     * <code>string federationExecutionName = 1;</code>
     * @return The federationExecutionName.
     */
    public java.lang.String getFederationExecutionName() {
      java.lang.Object ref = federationExecutionName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        federationExecutionName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string federationExecutionName = 1;</code>
     * @return The bytes for federationExecutionName.
     */
    public com.google.protobuf.ByteString
        getFederationExecutionNameBytes() {
      java.lang.Object ref = federationExecutionName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        federationExecutionName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string federationExecutionName = 1;</code>
     * @param value The federationExecutionName to set.
     * @return This builder for chaining.
     */
    public Builder setFederationExecutionName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      federationExecutionName_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string federationExecutionName = 1;</code>
     * @return This builder for chaining.
     */
    public Builder clearFederationExecutionName() {
      
      federationExecutionName_ = getDefaultInstance().getFederationExecutionName();
      onChanged();
      return this;
    }
    /**
     * <code>string federationExecutionName = 1;</code>
     * @param value The bytes for federationExecutionName to set.
     * @return This builder for chaining.
     */
    public Builder setFederationExecutionNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      federationExecutionName_ = value;
      onChanged();
      return this;
    }

    private java.lang.Object logicalTimeImplementationName_ = "";
    /**
     * <code>string logicalTimeImplementationName = 2;</code>
     * @return The logicalTimeImplementationName.
     */
    public java.lang.String getLogicalTimeImplementationName() {
      java.lang.Object ref = logicalTimeImplementationName_;
      if (!(ref instanceof java.lang.String)) {
        com.google.protobuf.ByteString bs =
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        logicalTimeImplementationName_ = s;
        return s;
      } else {
        return (java.lang.String) ref;
      }
    }
    /**
     * <code>string logicalTimeImplementationName = 2;</code>
     * @return The bytes for logicalTimeImplementationName.
     */
    public com.google.protobuf.ByteString
        getLogicalTimeImplementationNameBytes() {
      java.lang.Object ref = logicalTimeImplementationName_;
      if (ref instanceof String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        logicalTimeImplementationName_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }
    /**
     * <code>string logicalTimeImplementationName = 2;</code>
     * @param value The logicalTimeImplementationName to set.
     * @return This builder for chaining.
     */
    public Builder setLogicalTimeImplementationName(
        java.lang.String value) {
      if (value == null) {
    throw new NullPointerException();
  }
  
      logicalTimeImplementationName_ = value;
      onChanged();
      return this;
    }
    /**
     * <code>string logicalTimeImplementationName = 2;</code>
     * @return This builder for chaining.
     */
    public Builder clearLogicalTimeImplementationName() {
      
      logicalTimeImplementationName_ = getDefaultInstance().getLogicalTimeImplementationName();
      onChanged();
      return this;
    }
    /**
     * <code>string logicalTimeImplementationName = 2;</code>
     * @param value The bytes for logicalTimeImplementationName to set.
     * @return This builder for chaining.
     */
    public Builder setLogicalTimeImplementationNameBytes(
        com.google.protobuf.ByteString value) {
      if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
      
      logicalTimeImplementationName_ = value;
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


    // @@protoc_insertion_point(builder_scope:rti1516_202X.fedpro.FederationExecutionInformation)
  }

  // @@protoc_insertion_point(class_scope:rti1516_202X.fedpro.FederationExecutionInformation)
  private static final hla.rti1516_202X.fedpro.FederationExecutionInformation DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new hla.rti1516_202X.fedpro.FederationExecutionInformation();
  }

  public static hla.rti1516_202X.fedpro.FederationExecutionInformation getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<FederationExecutionInformation>
      PARSER = new com.google.protobuf.AbstractParser<FederationExecutionInformation>() {
    @java.lang.Override
    public FederationExecutionInformation parsePartialFrom(
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

  public static com.google.protobuf.Parser<FederationExecutionInformation> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<FederationExecutionInformation> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public hla.rti1516_202X.fedpro.FederationExecutionInformation getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

