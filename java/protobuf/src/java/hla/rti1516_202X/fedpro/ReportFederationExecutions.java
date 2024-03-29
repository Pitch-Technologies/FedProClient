// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: FederateAmbassador.proto

package hla.rti1516_202X.fedpro;

/**
 * Protobuf type {@code rti1516_202X.fedpro.ReportFederationExecutions}
 */
public final class ReportFederationExecutions extends
    com.google.protobuf.GeneratedMessageV3 implements
    // @@protoc_insertion_point(message_implements:rti1516_202X.fedpro.ReportFederationExecutions)
    ReportFederationExecutionsOrBuilder {
private static final long serialVersionUID = 0L;
  // Use ReportFederationExecutions.newBuilder() to construct.
  private ReportFederationExecutions(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
    super(builder);
  }
  private ReportFederationExecutions() {
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new ReportFederationExecutions();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return hla.rti1516_202X.fedpro.FederateAmbassador.internal_static_rti1516_202X_fedpro_ReportFederationExecutions_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return hla.rti1516_202X.fedpro.FederateAmbassador.internal_static_rti1516_202X_fedpro_ReportFederationExecutions_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            hla.rti1516_202X.fedpro.ReportFederationExecutions.class, hla.rti1516_202X.fedpro.ReportFederationExecutions.Builder.class);
  }

  public static final int REPORT_FIELD_NUMBER = 1;
  private hla.rti1516_202X.fedpro.FederationExecutionInformationSet report_;
  /**
   * <code>.rti1516_202X.fedpro.FederationExecutionInformationSet report = 1;</code>
   * @return Whether the report field is set.
   */
  @java.lang.Override
  public boolean hasReport() {
    return report_ != null;
  }
  /**
   * <code>.rti1516_202X.fedpro.FederationExecutionInformationSet report = 1;</code>
   * @return The report.
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.FederationExecutionInformationSet getReport() {
    return report_ == null ? hla.rti1516_202X.fedpro.FederationExecutionInformationSet.getDefaultInstance() : report_;
  }
  /**
   * <code>.rti1516_202X.fedpro.FederationExecutionInformationSet report = 1;</code>
   */
  @java.lang.Override
  public hla.rti1516_202X.fedpro.FederationExecutionInformationSetOrBuilder getReportOrBuilder() {
    return getReport();
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
    if (report_ != null) {
      output.writeMessage(1, getReport());
    }
    getUnknownFields().writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    if (report_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, getReport());
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
    if (!(obj instanceof hla.rti1516_202X.fedpro.ReportFederationExecutions)) {
      return super.equals(obj);
    }
    hla.rti1516_202X.fedpro.ReportFederationExecutions other = (hla.rti1516_202X.fedpro.ReportFederationExecutions) obj;

    if (hasReport() != other.hasReport()) return false;
    if (hasReport()) {
      if (!getReport()
          .equals(other.getReport())) return false;
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
    if (hasReport()) {
      hash = (37 * hash) + REPORT_FIELD_NUMBER;
      hash = (53 * hash) + getReport().hashCode();
    }
    hash = (29 * hash) + getUnknownFields().hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static hla.rti1516_202X.fedpro.ReportFederationExecutions parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.ReportFederationExecutions parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ReportFederationExecutions parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.ReportFederationExecutions parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ReportFederationExecutions parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static hla.rti1516_202X.fedpro.ReportFederationExecutions parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ReportFederationExecutions parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.ReportFederationExecutions parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ReportFederationExecutions parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.ReportFederationExecutions parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static hla.rti1516_202X.fedpro.ReportFederationExecutions parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static hla.rti1516_202X.fedpro.ReportFederationExecutions parseFrom(
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
  public static Builder newBuilder(hla.rti1516_202X.fedpro.ReportFederationExecutions prototype) {
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
   * Protobuf type {@code rti1516_202X.fedpro.ReportFederationExecutions}
   */
  public static final class Builder extends
      com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
      // @@protoc_insertion_point(builder_implements:rti1516_202X.fedpro.ReportFederationExecutions)
      hla.rti1516_202X.fedpro.ReportFederationExecutionsOrBuilder {
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return hla.rti1516_202X.fedpro.FederateAmbassador.internal_static_rti1516_202X_fedpro_ReportFederationExecutions_descriptor;
    }

    @java.lang.Override
    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return hla.rti1516_202X.fedpro.FederateAmbassador.internal_static_rti1516_202X_fedpro_ReportFederationExecutions_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              hla.rti1516_202X.fedpro.ReportFederationExecutions.class, hla.rti1516_202X.fedpro.ReportFederationExecutions.Builder.class);
    }

    // Construct using hla.rti1516_202X.fedpro.ReportFederationExecutions.newBuilder()
    private Builder() {

    }

    private Builder(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      super(parent);

    }
    @java.lang.Override
    public Builder clear() {
      super.clear();
      if (reportBuilder_ == null) {
        report_ = null;
      } else {
        report_ = null;
        reportBuilder_ = null;
      }
      return this;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.Descriptor
        getDescriptorForType() {
      return hla.rti1516_202X.fedpro.FederateAmbassador.internal_static_rti1516_202X_fedpro_ReportFederationExecutions_descriptor;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.ReportFederationExecutions getDefaultInstanceForType() {
      return hla.rti1516_202X.fedpro.ReportFederationExecutions.getDefaultInstance();
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.ReportFederationExecutions build() {
      hla.rti1516_202X.fedpro.ReportFederationExecutions result = buildPartial();
      if (!result.isInitialized()) {
        throw newUninitializedMessageException(result);
      }
      return result;
    }

    @java.lang.Override
    public hla.rti1516_202X.fedpro.ReportFederationExecutions buildPartial() {
      hla.rti1516_202X.fedpro.ReportFederationExecutions result = new hla.rti1516_202X.fedpro.ReportFederationExecutions(this);
      if (reportBuilder_ == null) {
        result.report_ = report_;
      } else {
        result.report_ = reportBuilder_.build();
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
      if (other instanceof hla.rti1516_202X.fedpro.ReportFederationExecutions) {
        return mergeFrom((hla.rti1516_202X.fedpro.ReportFederationExecutions)other);
      } else {
        super.mergeFrom(other);
        return this;
      }
    }

    public Builder mergeFrom(hla.rti1516_202X.fedpro.ReportFederationExecutions other) {
      if (other == hla.rti1516_202X.fedpro.ReportFederationExecutions.getDefaultInstance()) return this;
      if (other.hasReport()) {
        mergeReport(other.getReport());
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
                  getReportFieldBuilder().getBuilder(),
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

    private hla.rti1516_202X.fedpro.FederationExecutionInformationSet report_;
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.FederationExecutionInformationSet, hla.rti1516_202X.fedpro.FederationExecutionInformationSet.Builder, hla.rti1516_202X.fedpro.FederationExecutionInformationSetOrBuilder> reportBuilder_;
    /**
     * <code>.rti1516_202X.fedpro.FederationExecutionInformationSet report = 1;</code>
     * @return Whether the report field is set.
     */
    public boolean hasReport() {
      return reportBuilder_ != null || report_ != null;
    }
    /**
     * <code>.rti1516_202X.fedpro.FederationExecutionInformationSet report = 1;</code>
     * @return The report.
     */
    public hla.rti1516_202X.fedpro.FederationExecutionInformationSet getReport() {
      if (reportBuilder_ == null) {
        return report_ == null ? hla.rti1516_202X.fedpro.FederationExecutionInformationSet.getDefaultInstance() : report_;
      } else {
        return reportBuilder_.getMessage();
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.FederationExecutionInformationSet report = 1;</code>
     */
    public Builder setReport(hla.rti1516_202X.fedpro.FederationExecutionInformationSet value) {
      if (reportBuilder_ == null) {
        if (value == null) {
          throw new NullPointerException();
        }
        report_ = value;
        onChanged();
      } else {
        reportBuilder_.setMessage(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.FederationExecutionInformationSet report = 1;</code>
     */
    public Builder setReport(
        hla.rti1516_202X.fedpro.FederationExecutionInformationSet.Builder builderForValue) {
      if (reportBuilder_ == null) {
        report_ = builderForValue.build();
        onChanged();
      } else {
        reportBuilder_.setMessage(builderForValue.build());
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.FederationExecutionInformationSet report = 1;</code>
     */
    public Builder mergeReport(hla.rti1516_202X.fedpro.FederationExecutionInformationSet value) {
      if (reportBuilder_ == null) {
        if (report_ != null) {
          report_ =
            hla.rti1516_202X.fedpro.FederationExecutionInformationSet.newBuilder(report_).mergeFrom(value).buildPartial();
        } else {
          report_ = value;
        }
        onChanged();
      } else {
        reportBuilder_.mergeFrom(value);
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.FederationExecutionInformationSet report = 1;</code>
     */
    public Builder clearReport() {
      if (reportBuilder_ == null) {
        report_ = null;
        onChanged();
      } else {
        report_ = null;
        reportBuilder_ = null;
      }

      return this;
    }
    /**
     * <code>.rti1516_202X.fedpro.FederationExecutionInformationSet report = 1;</code>
     */
    public hla.rti1516_202X.fedpro.FederationExecutionInformationSet.Builder getReportBuilder() {
      
      onChanged();
      return getReportFieldBuilder().getBuilder();
    }
    /**
     * <code>.rti1516_202X.fedpro.FederationExecutionInformationSet report = 1;</code>
     */
    public hla.rti1516_202X.fedpro.FederationExecutionInformationSetOrBuilder getReportOrBuilder() {
      if (reportBuilder_ != null) {
        return reportBuilder_.getMessageOrBuilder();
      } else {
        return report_ == null ?
            hla.rti1516_202X.fedpro.FederationExecutionInformationSet.getDefaultInstance() : report_;
      }
    }
    /**
     * <code>.rti1516_202X.fedpro.FederationExecutionInformationSet report = 1;</code>
     */
    private com.google.protobuf.SingleFieldBuilderV3<
        hla.rti1516_202X.fedpro.FederationExecutionInformationSet, hla.rti1516_202X.fedpro.FederationExecutionInformationSet.Builder, hla.rti1516_202X.fedpro.FederationExecutionInformationSetOrBuilder> 
        getReportFieldBuilder() {
      if (reportBuilder_ == null) {
        reportBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
            hla.rti1516_202X.fedpro.FederationExecutionInformationSet, hla.rti1516_202X.fedpro.FederationExecutionInformationSet.Builder, hla.rti1516_202X.fedpro.FederationExecutionInformationSetOrBuilder>(
                getReport(),
                getParentForChildren(),
                isClean());
        report_ = null;
      }
      return reportBuilder_;
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


    // @@protoc_insertion_point(builder_scope:rti1516_202X.fedpro.ReportFederationExecutions)
  }

  // @@protoc_insertion_point(class_scope:rti1516_202X.fedpro.ReportFederationExecutions)
  private static final hla.rti1516_202X.fedpro.ReportFederationExecutions DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new hla.rti1516_202X.fedpro.ReportFederationExecutions();
  }

  public static hla.rti1516_202X.fedpro.ReportFederationExecutions getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<ReportFederationExecutions>
      PARSER = new com.google.protobuf.AbstractParser<ReportFederationExecutions>() {
    @java.lang.Override
    public ReportFederationExecutions parsePartialFrom(
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

  public static com.google.protobuf.Parser<ReportFederationExecutions> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<ReportFederationExecutions> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public hla.rti1516_202X.fedpro.ReportFederationExecutions getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

