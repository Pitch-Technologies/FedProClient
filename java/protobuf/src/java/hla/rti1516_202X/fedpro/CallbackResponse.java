// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: FederateAmbassador.proto

package hla.rti1516_202X.fedpro;

/**
 * Protobuf type {@code rti1516_202X.fedpro.CallbackResponse}
 */
public final class CallbackResponse extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:rti1516_202X.fedpro.CallbackResponse)
    CallbackResponseOrBuilder {
private static final long serialVersionUID = 0L;
  // Use CallbackResponse.newBuilder() to construct.
  private CallbackResponse(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private CallbackResponse() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new CallbackResponse();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return hla.rti1516_202X.fedpro.FederateAmbassador.internal_static_rti1516_202X_fedpro_CallbackResponse_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return hla.rti1516_202X.fedpro.FederateAmbassador.internal_static_rti1516_202X_fedpro_CallbackResponse_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            hla.rti1516_202X.fedpro.CallbackResponse.class, hla.rti1516_202X.fedpro.CallbackResponse.Builder.class);
  }

  private int callbackResponseCase_ = 0;
  private java.lang.Object callbackResponse_;
  public enum CallbackResponseCase
      implements com.google.protobuf.Internal.EnumLite,
          com.google.protobuf.AbstractMessage.InternalOneOfEnum {
    CALLBACKSUCCEEDED(1),
    CALLBACKFAILED(2),
    CALLBACKRESPONSE_NOT_SET(0);
    private final int value;
    private CallbackResponseCase(int value) {
      this.value = value;
    }
    /**
     * @param value The number of the enum to look for.
     * @return The enum associated with the given number.
     * @deprecated Use {@link #forNumber(int)} instead.
     */
    @java.lang.Deprecated
    public static CallbackResponseCase valueOf(int value) {
      return forNumber(value);
    }

    public static CallbackResponseCase forNumber(int value) {
      switch (value) {
        case 1: return CALLBACKSUCCEEDED;
        case 2: return CALLBACKFAILED;
        case 0: return CALLBACKRESPONSE_NOT_SET;
        default: return null;
      }
    }
    public int getNumber() {
      return this.value;
    }
  };

  public CallbackResponseCase
  getCallbackResponseCase() {
    return CallbackResponseCase.forNumber(
        callbackResponseCase_);
  }

  public static final int CALLBACKSUCCEEDED_FIELD_NUMBER = 1;
  /**
   * <code>.rti1516_202X.fedpro.CallbackSucceeded callbackSucceeded = 1;</code>
   * @return Whether the callbackSucceeded field is set.
   */
  @java.lang.Override
  public boolean hasCallbackSucceeded() {
    return callbackResponseCase_ == 1;
  }
  /**
   * <code>.rti1516_202X.fedpro.CallbackSucceeded callbackSucceeded = 1;</code>
   * @return The callbackSucceeded.
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.CallbackSucceeded getCallbackSucceeded() {
    if (callbackResponseCase_ == 1) {
       return (hla.rti1516_202X.fedpro.CallbackSucceeded) callbackResponse_;
    }
    return hla.rti1516_202X.fedpro.CallbackSucceeded.getDefaultInstance();
  }
  /**
   * <code>.rti1516_202X.fedpro.CallbackSucceeded callbackSucceeded = 1;</code>
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.CallbackSucceededOrBuilder getCallbackSucceededOrBuilder() {
    if (callbackResponseCase_ == 1) {
       return (hla.rti1516_202X.fedpro.CallbackSucceeded) callbackResponse_;
    }
    return hla.rti1516_202X.fedpro.CallbackSucceeded.getDefaultInstance();
  }

  public static final int CALLBACKFAILED_FIELD_NUMBER = 2;
  /**
   * <code>.rti1516_202X.fedpro.ExceptionData callbackFailed = 2;</code>
   * @return Whether the callbackFailed field is set.
   */
  @java.lang.Override
  public boolean hasCallbackFailed() {
    return callbackResponseCase_ == 2;
  }
  /**
   * <code>.rti1516_202X.fedpro.ExceptionData callbackFailed = 2;</code>
   * @return The callbackFailed.
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.ExceptionData getCallbackFailed() {
    if (callbackResponseCase_ == 2) {
       return (hla.rti1516_202X.fedpro.ExceptionData) callbackResponse_;
    }
    return hla.rti1516_202X.fedpro.ExceptionData.getDefaultInstance();
  }
  /**
   * <code>.rti1516_202X.fedpro.ExceptionData callbackFailed = 2;</code>
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.ExceptionDataOrBuilder getCallbackFailedOrBuilder() {
    if (callbackResponseCase_ == 2) {
       return (hla.rti1516_202X.fedpro.ExceptionData) callbackResponse_;
    }
    return hla.rti1516_202X.fedpro.ExceptionData.getDefaultInstance();
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
    if (callbackResponseCase_ == 1) {
      output.writeMessage(1, (hla.rti1516_202X.fedpro.CallbackSucceeded) callbackResponse_);
    }
    if (callbackResponseCase_ == 2) {
      output.writeMessage(2, (hla.rti1516_202X.fedpro.ExceptionData) callbackResponse_);
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (callbackResponseCase_ == 1) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, (hla.rti1516_202X.fedpro.CallbackSucceeded) callbackResponse_);
    }
    if (callbackResponseCase_ == 2) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, (hla.rti1516_202X.fedpro.ExceptionData) callbackResponse_);
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
    if (!(obj instanceof hla.rti1516_202X.fedpro.CallbackResponse)) {
      return super.equals(obj);
    }
    hla.rti1516_202X.fedpro.CallbackResponse other = (hla.rti1516_202X.fedpro.CallbackResponse) obj;

    if (!getCallbackResponseCase().equals(other.getCallbackResponseCase())) return false;
    switch (callbackResponseCase_) {
      case 1:
        if (!getCallbackSucceeded()
            .equals(other.getCallbackSucceeded())) return false;
        break;
      case 2:
        if (!getCallbackFailed()
            .equals(other.getCallbackFailed())) return false;
        break;
      case 0:
      default:
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
    switch (callbackResponseCase_) {
      case 1:
        hash = (37 * hash) + CALLBACKSUCCEEDED_FIELD_NUMBER;
        hash = (53 * hash) + getCallbackSucceeded().hashCode();
        break;
      case 2:
        hash = (37 * hash) + CALLBACKFAILED_FIELD_NUMBER;
        hash = (53 * hash) + getCallbackFailed().hashCode();
        break;
      case 0:
      default:
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static hla.rti1516_202X.fedpro.CallbackResponse parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.CallbackResponse parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.CallbackResponse parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.CallbackResponse parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.CallbackResponse parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.CallbackResponse parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.CallbackResponse parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.CallbackResponse parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.CallbackResponse parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.CallbackResponse parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.CallbackResponse parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.CallbackResponse parseFrom(
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
  public static Builder newBuilder(hla.rti1516_202X.fedpro.CallbackResponse prototype) {
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
   * Protobuf type {@code rti1516_202X.fedpro.CallbackResponse}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:rti1516_202X.fedpro.CallbackResponse)
      hla.rti1516_202X.fedpro.CallbackResponseOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return hla.rti1516_202X.fedpro.FederateAmbassador.internal_static_rti1516_202X_fedpro_CallbackResponse_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return hla.rti1516_202X.fedpro.FederateAmbassador.internal_static_rti1516_202X_fedpro_CallbackResponse_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              hla.rti1516_202X.fedpro.CallbackResponse.class, hla.rti1516_202X.fedpro.CallbackResponse.Builder.class);
    }

    // Construct using hla.rti1516_202X.fedpro.CallbackResponse.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (callbackSucceededBuilder_ != null) {
        callbackSucceededBuilder_.clear();
      }
      if (callbackFailedBuilder_ != null) {
        callbackFailedBuilder_.clear();
      }
      callbackResponseCase_ = 0;
      callbackResponse_ = null;
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return hla.rti1516_202X.fedpro.FederateAmbassador.internal_static_rti1516_202X_fedpro_CallbackResponse_descriptor;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.CallbackResponse getDefaultInstanceForType() {
      return hla.rti1516_202X.fedpro.CallbackResponse.getDefaultInstance();
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.CallbackResponse build() {
      hla.rti1516_202X.fedpro.CallbackResponse result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.CallbackResponse buildPartial() {
      hla.rti1516_202X.fedpro.CallbackResponse result = new hla.rti1516_202X.fedpro.CallbackResponse(this);
      if (callbackResponseCase_ == 1) {
        if (callbackSucceededBuilder_ == null) {
          result.callbackResponse_ = callbackResponse_;
        } else {
          result.callbackResponse_ = callbackSucceededBuilder_.build();
        }
      }
      if (callbackResponseCase_ == 2) {
        if (callbackFailedBuilder_ == null) {
          result.callbackResponse_ = callbackResponse_;
        } else {
          result.callbackResponse_ = callbackFailedBuilder_.build();
        }
      }
      result.callbackResponseCase_ = callbackResponseCase_;
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
      if (other instanceof hla.rti1516_202X.fedpro.CallbackResponse) {
        return mergeFrom((hla.rti1516_202X.fedpro.CallbackResponse)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(hla.rti1516_202X.fedpro.CallbackResponse other) {
      if (other == hla.rti1516_202X.fedpro.CallbackResponse.getDefaultInstance()) return this;
      switch (other.getCallbackResponseCase()) {
        case CALLBACKSUCCEEDED: {
          mergeCallbackSucceeded(other.getCallbackSucceeded());
          break;
        }
        case CALLBACKFAILED: {
          mergeCallbackFailed(other.getCallbackFailed());
          break;
        }
        case CALLBACKRESPONSE_NOT_SET: {
          break;
        }
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
                  getCallbackSucceededFieldBuilder().getBuilder(),
                  extensionRegistry);
              callbackResponseCase_ = 1;
              break;
            } // case 10
            case 18: {
              input.readMessage(
                  getCallbackFailedFieldBuilder().getBuilder(),
                  extensionRegistry);
              callbackResponseCase_ = 2;
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
    private int callbackResponseCase_ = 0;
    private java.lang.Object callbackResponse_;
    public CallbackResponseCase
        getCallbackResponseCase() {
      return CallbackResponseCase.forNumber(
          callbackResponseCase_);
    }

    public Builder clearCallbackResponse() {
      callbackResponseCase_ = 0;
      callbackResponse_ = null;
      onChanged();
      return this;
    }


    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.CallbackSucceeded, hla.rti1516_202X.fedpro.CallbackSucceeded.Builder, hla.rti1516_202X.fedpro.CallbackSucceededOrBuilder> callbackSucceededBuilder_;
    /**
     * <code>.rti1516_202X.fedpro.CallbackSucceeded callbackSucceeded = 1;</code>
     * @return Whether the callbackSucceeded field is set.
     */
    @java.lang.Override
    public boolean hasCallbackSucceeded() {
      return callbackResponseCase_ == 1;
    }
    /**
     * <code>.rti1516_202X.fedpro.CallbackSucceeded callbackSucceeded = 1;</code>
     * @return The callbackSucceeded.
     */
    @java.lang.Override
    public hla.rti1516_202X.fedpro.CallbackSucceeded getCallbackSucceeded() {
      if (callbackSucceededBuilder_ == null) {
        if (callbackResponseCase_ == 1) {
          return (hla.rti1516_202X.fedpro.CallbackSucceeded) callbackResponse_;
        }
        return hla.rti1516_202X.fedpro.CallbackSucceeded.getDefaultInstance();
      } else {
        if (callbackResponseCase_ == 1) {
          return callbackSucceededBuilder_.getMessage();
        }
        return hla.rti1516_202X.fedpro.CallbackSucceeded.getDefaultInstance();
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.CallbackSucceeded callbackSucceeded = 1;</code>
     */
    public Builder setCallbackSucceeded(hla.rti1516_202X.fedpro.CallbackSucceeded value) {
      if (callbackSucceededBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        callbackResponse_ = value;
        onChanged();
      } else {
        callbackSucceededBuilder_.setMessage(value);
      }
      callbackResponseCase_ = 1;
      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.CallbackSucceeded callbackSucceeded = 1;</code>
     */
    public Builder setCallbackSucceeded(
        hla.rti1516_202X.fedpro.CallbackSucceeded.Builder builderForValue) {
      if (callbackSucceededBuilder_ == null) {
        callbackResponse_ = builderForValue.build();
        onChanged();
      } else {
        callbackSucceededBuilder_.setMessage(builderForValue.build());
      }
      callbackResponseCase_ = 1;
      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.CallbackSucceeded callbackSucceeded = 1;</code>
     */
    public Builder mergeCallbackSucceeded(hla.rti1516_202X.fedpro.CallbackSucceeded value) {
      if (callbackSucceededBuilder_ == null) {
        if (callbackResponseCase_ == 1 &&
            callbackResponse_ != hla.rti1516_202X.fedpro.CallbackSucceeded.getDefaultInstance()) {
          callbackResponse_ = hla.rti1516_202X.fedpro.CallbackSucceeded.newBuilder((hla.rti1516_202X.fedpro.CallbackSucceeded) callbackResponse_)
              .mergeFrom(value).buildPartial();
        } else {
          callbackResponse_ = value;
        }
        onChanged();
      } else {
        if (callbackResponseCase_ == 1) {
          callbackSucceededBuilder_.mergeFrom(value);
        } else {
          callbackSucceededBuilder_.setMessage(value);
        }
      }
      callbackResponseCase_ = 1;
      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.CallbackSucceeded callbackSucceeded = 1;</code>
     */
    public Builder clearCallbackSucceeded() {
      if (callbackSucceededBuilder_ == null) {
        if (callbackResponseCase_ == 1) {
          callbackResponseCase_ = 0;
          callbackResponse_ = null;
          onChanged();
        }
      } else {
        if (callbackResponseCase_ == 1) {
          callbackResponseCase_ = 0;
          callbackResponse_ = null;
        }
        callbackSucceededBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.CallbackSucceeded callbackSucceeded = 1;</code>
     */
    public hla.rti1516_202X.fedpro.CallbackSucceeded.Builder getCallbackSucceededBuilder() {
      return getCallbackSucceededFieldBuilder().getBuilder();
    }
    /**
     * <code>.rti1516_202X.fedpro.CallbackSucceeded callbackSucceeded = 1;</code>
     */
    @java.lang.Override
    public hla.rti1516_202X.fedpro.CallbackSucceededOrBuilder getCallbackSucceededOrBuilder() {
      if ((callbackResponseCase_ == 1) && (callbackSucceededBuilder_ != null)) {
        return callbackSucceededBuilder_.getMessageOrBuilder();
      } else {
        if (callbackResponseCase_ == 1) {
          return (hla.rti1516_202X.fedpro.CallbackSucceeded) callbackResponse_;
        }
        return hla.rti1516_202X.fedpro.CallbackSucceeded.getDefaultInstance();
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.CallbackSucceeded callbackSucceeded = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.CallbackSucceeded, hla.rti1516_202X.fedpro.CallbackSucceeded.Builder, hla.rti1516_202X.fedpro.CallbackSucceededOrBuilder> 
        getCallbackSucceededFieldBuilder() {
      if (callbackSucceededBuilder_ == null) {
        if (!(callbackResponseCase_ == 1)) {
          callbackResponse_ = hla.rti1516_202X.fedpro.CallbackSucceeded.getDefaultInstance();
        }
        callbackSucceededBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            hla.rti1516_202X.fedpro.CallbackSucceeded, hla.rti1516_202X.fedpro.CallbackSucceeded.Builder, hla.rti1516_202X.fedpro.CallbackSucceededOrBuilder>(
                (hla.rti1516_202X.fedpro.CallbackSucceeded) callbackResponse_,
                getParentForChildren(),
                isClean());
        callbackResponse_ = null;
      }
      callbackResponseCase_ = 1;
      onChanged();;
      return callbackSucceededBuilder_;
    }

    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.ExceptionData, hla.rti1516_202X.fedpro.ExceptionData.Builder, hla.rti1516_202X.fedpro.ExceptionDataOrBuilder> callbackFailedBuilder_;
    /**
     * <code>.rti1516_202X.fedpro.ExceptionData callbackFailed = 2;</code>
     * @return Whether the callbackFailed field is set.
     */
    @java.lang.Override
    public boolean hasCallbackFailed() {
      return callbackResponseCase_ == 2;
    }
    /**
     * <code>.rti1516_202X.fedpro.ExceptionData callbackFailed = 2;</code>
     * @return The callbackFailed.
     */
    @java.lang.Override
    public hla.rti1516_202X.fedpro.ExceptionData getCallbackFailed() {
      if (callbackFailedBuilder_ == null) {
        if (callbackResponseCase_ == 2) {
          return (hla.rti1516_202X.fedpro.ExceptionData) callbackResponse_;
        }
        return hla.rti1516_202X.fedpro.ExceptionData.getDefaultInstance();
      } else {
        if (callbackResponseCase_ == 2) {
          return callbackFailedBuilder_.getMessage();
        }
        return hla.rti1516_202X.fedpro.ExceptionData.getDefaultInstance();
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.ExceptionData callbackFailed = 2;</code>
     */
    public Builder setCallbackFailed(hla.rti1516_202X.fedpro.ExceptionData value) {
      if (callbackFailedBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        callbackResponse_ = value;
        onChanged();
      } else {
        callbackFailedBuilder_.setMessage(value);
      }
      callbackResponseCase_ = 2;
      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.ExceptionData callbackFailed = 2;</code>
     */
    public Builder setCallbackFailed(
        hla.rti1516_202X.fedpro.ExceptionData.Builder builderForValue) {
      if (callbackFailedBuilder_ == null) {
        callbackResponse_ = builderForValue.build();
        onChanged();
      } else {
        callbackFailedBuilder_.setMessage(builderForValue.build());
      }
      callbackResponseCase_ = 2;
      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.ExceptionData callbackFailed = 2;</code>
     */
    public Builder mergeCallbackFailed(hla.rti1516_202X.fedpro.ExceptionData value) {
      if (callbackFailedBuilder_ == null) {
        if (callbackResponseCase_ == 2 &&
            callbackResponse_ != hla.rti1516_202X.fedpro.ExceptionData.getDefaultInstance()) {
          callbackResponse_ = hla.rti1516_202X.fedpro.ExceptionData.newBuilder((hla.rti1516_202X.fedpro.ExceptionData) callbackResponse_)
              .mergeFrom(value).buildPartial();
        } else {
          callbackResponse_ = value;
        }
        onChanged();
      } else {
        if (callbackResponseCase_ == 2) {
          callbackFailedBuilder_.mergeFrom(value);
        } else {
          callbackFailedBuilder_.setMessage(value);
        }
      }
      callbackResponseCase_ = 2;
      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.ExceptionData callbackFailed = 2;</code>
     */
    public Builder clearCallbackFailed() {
      if (callbackFailedBuilder_ == null) {
        if (callbackResponseCase_ == 2) {
          callbackResponseCase_ = 0;
          callbackResponse_ = null;
          onChanged();
        }
      } else {
        if (callbackResponseCase_ == 2) {
          callbackResponseCase_ = 0;
          callbackResponse_ = null;
        }
        callbackFailedBuilder_.clear();
      }
      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.ExceptionData callbackFailed = 2;</code>
     */
    public hla.rti1516_202X.fedpro.ExceptionData.Builder getCallbackFailedBuilder() {
      return getCallbackFailedFieldBuilder().getBuilder();
    }
    /**
     * <code>.rti1516_202X.fedpro.ExceptionData callbackFailed = 2;</code>
     */
    @java.lang.Override
    public hla.rti1516_202X.fedpro.ExceptionDataOrBuilder getCallbackFailedOrBuilder() {
      if ((callbackResponseCase_ == 2) && (callbackFailedBuilder_ != null)) {
        return callbackFailedBuilder_.getMessageOrBuilder();
      } else {
        if (callbackResponseCase_ == 2) {
          return (hla.rti1516_202X.fedpro.ExceptionData) callbackResponse_;
        }
        return hla.rti1516_202X.fedpro.ExceptionData.getDefaultInstance();
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.ExceptionData callbackFailed = 2;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.ExceptionData, hla.rti1516_202X.fedpro.ExceptionData.Builder, hla.rti1516_202X.fedpro.ExceptionDataOrBuilder> 
        getCallbackFailedFieldBuilder() {
      if (callbackFailedBuilder_ == null) {
        if (!(callbackResponseCase_ == 2)) {
          callbackResponse_ = hla.rti1516_202X.fedpro.ExceptionData.getDefaultInstance();
        }
        callbackFailedBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            hla.rti1516_202X.fedpro.ExceptionData, hla.rti1516_202X.fedpro.ExceptionData.Builder, hla.rti1516_202X.fedpro.ExceptionDataOrBuilder>(
                (hla.rti1516_202X.fedpro.ExceptionData) callbackResponse_,
                getParentForChildren(),
                isClean());
        callbackResponse_ = null;
      }
      callbackResponseCase_ = 2;
      onChanged();;
      return callbackFailedBuilder_;
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


    // @@protoc_insertion_point(builder_scope:rti1516_202X.fedpro.CallbackResponse)
  }

  // @@protoc_insertion_point(class_scope:rti1516_202X.fedpro.CallbackResponse)
  private static final hla.rti1516_202X.fedpro.CallbackResponse DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new hla.rti1516_202X.fedpro.CallbackResponse();
  }

  public static hla.rti1516_202X.fedpro.CallbackResponse getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<CallbackResponse>
      PARSER = new com.google.protobuf.AbstractParser<CallbackResponse>() {
    @java.lang.Override
    public CallbackResponse parsePartialFrom(
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

  public static com.google.protobuf.Parser<CallbackResponse> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<CallbackResponse> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public hla.rti1516_202X.fedpro.CallbackResponse getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}
