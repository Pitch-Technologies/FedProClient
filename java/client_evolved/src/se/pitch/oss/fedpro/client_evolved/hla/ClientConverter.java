/*
 *  Copyright (C) 2022 Pitch Technologies AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.pitch.oss.fedpro.client_evolved.hla;

import com.google.protobuf.ByteString;
import com.google.protobuf.ProtocolStringList;
import hla.rti1516_202X.fedpro.*;
import hla.rti1516e.*;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.AttributeSetRegionSetPairList;
import hla.rti1516e.DimensionHandle;
import hla.rti1516e.DimensionHandleSet;
import hla.rti1516e.FederateAmbassador;
import hla.rti1516e.FederateHandle;
import hla.rti1516e.FederateHandleSaveStatusPair;
import hla.rti1516e.FederateHandleSet;
import hla.rti1516e.FederateRestoreStatus;
import hla.rti1516e.FederationExecutionInformation;
import hla.rti1516e.FederationExecutionInformationSet;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.MessageRetractionHandle;
import hla.rti1516e.MessageRetractionReturn;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.ParameterHandle;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.RangeBounds;
import hla.rti1516e.RegionHandle;
import hla.rti1516e.RegionHandleSet;
import hla.rti1516e.ResignAction;
import hla.rti1516e.RestoreFailureReason;
import hla.rti1516e.RestoreStatus;
import hla.rti1516e.SaveFailureReason;
import hla.rti1516e.SaveStatus;
import hla.rti1516e.ServiceGroup;
import hla.rti1516e.SynchronizationPointFailureReason;
import hla.rti1516e.TimeQueryReturn;
import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.encoding.ByteWrapper;
import hla.rti1516e.exceptions.*;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.*;

public class ClientConverter {
   private LogicalTimeFactory _timeFactory;

   public Credentials.Builder convertFromHla(hla.rti1516_202X.auth.Credentials credentials)
   {
      Credentials.Builder credentialsBuilder = Credentials.newBuilder();
      credentialsBuilder.setType(credentials.getType());
      credentialsBuilder.setData(convertFromHla(credentials.getData()));
      return credentialsBuilder;
   }

   private static class HandleImpl implements Serializable {
      protected final byte[] _encodedHandle;

      protected int getIntValue()
      {
         // Decodes an arbitrary length byte array to an int, optimized for 4 && 8 bytes.
         // If more than 4 bytes are provided, only the last 4 are converted.
         // If less than 4 bytes are provided, assumes the highest value bytes are 0.
         // Throws RuntimeException when 0 bytes are provided.
         if (_encodedHandle.length == 4) {
            return ByteBuffer.wrap(_encodedHandle).getInt();
         } else if (_encodedHandle.length == 8) {
            return (int) ByteBuffer.wrap(_encodedHandle).getLong();
         } else if (_encodedHandle.length > 4) {
            return ByteBuffer.wrap(_encodedHandle, _encodedHandle.length - 4, 4).getInt();
         } else if (_encodedHandle.length > 0) {
            int result = 0;
            for (byte b : _encodedHandle) {
               result <<= 8;
               result |= (b & 0xFF);
            }
            return result;
         } else {
            throw new RuntimeException("Cannot convert a 0-length byte array to an integer.");
         }
      }

      public HandleImpl(byte[] encodedHandle)
      {
         _encodedHandle = encodedHandle;
      }

      protected HandleImpl(byte[] encodedHandle, int offset)
      {
         if (offset == 0) {
            _encodedHandle = encodedHandle;
         } else {
            _encodedHandle = new byte[encodedHandle.length - offset];
            System.arraycopy(encodedHandle, offset, _encodedHandle, 0, _encodedHandle.length);
         }
      }

      protected int encodedLength()
      {
         return _encodedHandle.length;
      }

      protected void encode(byte[] buffer, int offset)
      {
         System.arraycopy(_encodedHandle, 0, buffer, offset, _encodedHandle.length);
      }

      public boolean equals(Object o)
      {
         if (this == o) {
            return true;
         }
         if (o == null || getClass() != o.getClass()) {
            return false;
         }

         HandleImpl handle = (HandleImpl) o;
         return Arrays.equals(_encodedHandle, handle._encodedHandle);
      }

      public int hashCode()
      {
         return Arrays.hashCode(_encodedHandle);
      }

      public String toString()
      {
         String name = getClass().getInterfaces()[0].getSimpleName();
         if (_encodedHandle.length == 4) {
            return name + "<" + ByteBuffer.wrap(_encodedHandle).getInt() + ">";
         } else if (_encodedHandle.length == 8) {
            return name + "<" + ByteBuffer.wrap(_encodedHandle).getLong() + ">";
         } else {
            return name + Arrays.toString(_encodedHandle);
         }
      }
   }

   private static class FederateHandleImpl extends HandleImpl implements FederateHandle {
      public FederateHandleImpl(byte[] encodedHandle)
      {
         super(encodedHandle);
      }

      public FederateHandleImpl(byte[] buffer, int offset)
      {
         super(buffer, offset);
      }

      public int encodedLength()
      {
         return super.encodedLength();
      }

      public void encode(byte[] buffer, int offset)
      {
         super.encode(buffer, offset);
      }
   }

   private static class DimensionHandleImpl extends HandleImpl implements DimensionHandle {
      public DimensionHandleImpl(byte[] encodedHandle)
      {
         super(encodedHandle);
      }

      public DimensionHandleImpl(byte[] buffer, int offset)
      {
         super(buffer, offset);
      }

      public int encodedLength()
      {
         return super.encodedLength();
      }

      public void encode(byte[] buffer, int offset)
      {
         super.encode(buffer, offset);
      }
   }

   private static class RegionHandleImpl extends HandleImpl implements RegionHandle {
      public RegionHandleImpl(byte[] encodedHandle)
      {
         super(encodedHandle);
      }

      public RegionHandleImpl(byte[] buffer, int offset)
      {
         super(buffer, offset);
      }

      public int encodedLength()
      {
         return super.encodedLength();
      }

      public void encode(byte[] buffer, int offset)
      {
         super.encode(buffer, offset);
      }
   }

   static class ConveyedRegionHandleImpl implements RegionHandle {
      private final Map<DimensionHandle, RangeBounds> _rangeMap = new HashMap<>();

      public ConveyedRegionHandleImpl()
      {
      }

      void addRange(DimensionHandle dimensionHandle, long lower, long upper)
      {
         _rangeMap.put(dimensionHandle, new RangeBounds(lower, upper));
      }

      RangeBounds getRangeBounds(DimensionHandle dimensionHandle)
      {
         return _rangeMap.get(dimensionHandle);
      }

      /**
       * Dimensions specified for this region.
       */
      DimensionHandleSetImpl getDimensionHandleSet()
      {
         DimensionHandleSetImpl dimensionHandleSet = new DimensionHandleSetImpl();
         dimensionHandleSet.addAll(_rangeMap.keySet());
         return dimensionHandleSet;
      }

      public int encodedLength()
      {
         throw new UnsupportedOperationException("Conveyed Region cannnot be encoded");
      }

      public void encode(byte[] buffer, int offset)
      {
         throw new UnsupportedOperationException("Conveyed Region cannnot be encoded");
      }
   }

   private static class ObjectClassHandleImpl extends HandleImpl implements ObjectClassHandle {
      public ObjectClassHandleImpl(byte[] encodedHandle)
      {
         super(encodedHandle);
      }

      public ObjectClassHandleImpl(byte[] buffer, int offset)
      {
         super(buffer, offset);
      }

      public int encodedLength()
      {
         return super.encodedLength();
      }

      public void encode(byte[] buffer, int offset)
      {
         super.encode(buffer, offset);
      }
   }

   public static ObjectClassHandle createObjectClassHandle(byte[] encodedHandle)
   {
      return new ObjectClassHandleImpl(encodedHandle);
   }

   private static class ObjectInstanceHandleImpl extends HandleImpl implements ObjectInstanceHandle {
      public ObjectInstanceHandleImpl(byte[] encodedHandle)
      {
         super(encodedHandle);
      }

      public ObjectInstanceHandleImpl(byte[] buffer, int offset)
      {
         super(buffer, offset);
      }

      public int encodedLength()
      {
         return super.encodedLength();
      }

      public void encode(byte[] buffer, int offset)
      {
         super.encode(buffer, offset);
      }
   }

   public static ObjectInstanceHandle createObjectInstanceHandle(byte[] encodedHandle)
   {
      return new ObjectInstanceHandleImpl(encodedHandle);
   }

   private static class AttributeHandleImpl extends HandleImpl implements AttributeHandle {
      public AttributeHandleImpl(byte[] encodedHandle)
      {
         super(encodedHandle);
      }

      public AttributeHandleImpl(byte[] encodedHandle, int offset)
      {
         super(encodedHandle, offset);
      }

      public int encodedLength()
      {
         return super.encodedLength();
      }

      public void encode(byte[] buffer, int offset)
      {
         super.encode(buffer, offset);
      }
   }

   private static class InteractionClassHandleImpl extends HandleImpl implements InteractionClassHandle {
      public InteractionClassHandleImpl(byte[] encodedHandle)
      {
         super(encodedHandle);
      }

      public InteractionClassHandleImpl(byte[] buffer, int offset)
      {
         super(buffer, offset);
      }

      public int encodedLength()
      {
         return super.encodedLength();
      }

      public void encode(byte[] buffer, int offset)
      {
         super.encode(buffer, offset);
      }
   }

   static class ParameterHandleImpl extends HandleImpl implements ParameterHandle {
      public ParameterHandleImpl(byte[] encodedHandle)
      {
         super(encodedHandle);
      }

      public ParameterHandleImpl(byte[] buffer, int offset)
      {
         super(buffer, offset);
      }

      public int encodedLength()
      {
         return super.encodedLength();
      }

      public void encode(byte[] buffer, int offset)
      {
         super.encode(buffer, offset);
      }
   }

   private static class TransportationTypeHandleImpl extends HandleImpl implements TransportationTypeHandle {
      private static final int TRANSPORT_ID_RELIABLE = 1;
      private static final int TRANSPORT_ID_BEST_EFFORT = 2;

      public TransportationTypeHandleImpl(byte[] encodedHandle)
      {
         super(encodedHandle);
      }

      private TransportationTypeHandleImpl(byte[] buffer, int offset)
      {
         super(buffer, offset);
      }

      public int encodedLength()
      {
         return super.encodedLength();
      }

      public void encode(byte[] buffer, int offset)
      {
         super.encode(buffer, offset);
      }

      public String toString()
      {
         int intValue = getIntValue();
         switch (intValue) {
            case TRANSPORT_ID_RELIABLE:
               return "HLA_RELIABLE";
            case TRANSPORT_ID_BEST_EFFORT:
               return "HLA_BEST_EFFORT";
            default:
               return "TransportationType<" + intValue + ">";
         }
      }

      protected Object readResolve()
      {
         try {
            return MyTransportationTypeHandleFactory.resolve(this);
         } catch (CouldNotDecode couldNotDecode) {
            return null;
         }
      }
   }

   public static class MessageRetractionHandleImpl extends HandleImpl implements MessageRetractionHandle {
      private Integer id = null;

      public MessageRetractionHandleImpl(byte[] encodedHandle)
      {
         super(encodedHandle);
      }

      public MessageRetractionHandleImpl(byte[] buffer, int offset)
      {
         super(buffer, offset);
      }

      @Override
      public boolean equals(Object o)
      {
         if (this == o) {
            return true;
         }
         if (o == null || getClass() != o.getClass()) {
            return false;
         }

         HandleImpl handle = (HandleImpl) o;
         return getId() == getId(handle._encodedHandle);
      }

      private int getId()
      {
         if (id == null) {
            id = getId(_encodedHandle);
         }
         return id;
      }

      private static int getId(byte[] data)
      {
         hla.rti1516_202X.encoding.ByteWrapper byteWrapper = new hla.rti1516_202X.encoding.ByteWrapper(data);
         byteWrapper.getInt();
         return byteWrapper.getInt();
      }

      public int encodedLength()
      {
         return super.encodedLength();
      }

      public void encode(byte[] buffer, int offset)
      {
         super.encode(buffer, offset);
      }

      @Override
      public String toString()
      {
         return "messageRetraction<" + getId() + ">";
      }
   }

   public void setTimeFactory(LogicalTimeFactory timeFactory)
   {
      _timeFactory = timeFactory;
   }

   public JoinResult convertToHla(hla.rti1516_202X.fedpro.JoinResult result)
   throws
         RTIinternalError
   {
      return new JoinResult(
            convertToHla(result.getFederateHandle()),
            convertToHla(result.getLogicalTimeImplementationName()));
   }

   public hla.rti1516_202X.fedpro.RtiConfiguration.Builder convertFromHla(hla.rti1516_202X.RtiConfiguration rtiConfiguration)
   {
      hla.rti1516_202X.fedpro.RtiConfiguration.Builder builder = hla.rti1516_202X.fedpro.RtiConfiguration.newBuilder();
      builder.setConfigurationName(rtiConfiguration.configurationName());
      builder.setRtiAddress(rtiConfiguration.rtiAddress());
      builder.setAdditionalSettings(rtiConfiguration.additionalSettings());
      return builder;
   }

   public hla.rti1516_202X.fedpro.FomModule convertFromHla(se.pitch.oss.fedpro.client_evolved.hla.FomModule fomModule)
   {
      hla.rti1516_202X.fedpro.FomModule.Builder builder = hla.rti1516_202X.fedpro.FomModule.newBuilder();
      switch (fomModule.getType()) {
         case FILE:
            builder.setFile(hla.rti1516_202X.fedpro.FileFomModule.newBuilder()
                  .setName(fomModule.getFileName())
                  .setContent(ByteString.copyFrom(fomModule.getFileContent())));
            break;
         case COMPRESSED:
            builder.setCompressedModule(ByteString.copyFrom(fomModule.getCompressedModule()));
            break;
         case URL:
            builder.setUrl(fomModule.getUrl());
            break;
      }
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.FomModuleSet convertFromHla(FomModuleSet fomModules)
   {
      hla.rti1516_202X.fedpro.FomModuleSet.Builder result = hla.rti1516_202X.fedpro.FomModuleSet.newBuilder();
      for (se.pitch.oss.fedpro.client_evolved.hla.FomModule fomModule : fomModules) {
         result.addFomModule(convertFromHla(fomModule));
      }
      return result.build();
   }

   public hla.rti1516_202X.fedpro.FederateHandle convertFromHla(FederateHandle result)
   {
      hla.rti1516_202X.fedpro.FederateHandle.Builder builder = hla.rti1516_202X.fedpro.FederateHandle.newBuilder();
      byte[] buf = new byte[result.encodedLength()];
      result.encode(buf, 0);
      builder.setData(ByteString.copyFrom(buf));
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.InteractionClassHandle convertFromHla(hla.rti1516e.InteractionClassHandle result)
   {
      hla.rti1516_202X.fedpro.InteractionClassHandle.Builder builder =
            hla.rti1516_202X.fedpro.InteractionClassHandle.newBuilder();
      byte[] buf = new byte[result.encodedLength()];
      result.encode(buf, 0);
      builder.setData(ByteString.copyFrom(buf));
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.ParameterHandle convertFromHla(ParameterHandle result)
   {
      hla.rti1516_202X.fedpro.ParameterHandle.Builder builder = hla.rti1516_202X.fedpro.ParameterHandle.newBuilder();
      byte[] buf = new byte[result.encodedLength()];
      result.encode(buf, 0);
      builder.setData(ByteString.copyFrom(buf));
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.ObjectClassHandle convertFromHla(hla.rti1516e.ObjectClassHandle result)
   {
      hla.rti1516_202X.fedpro.ObjectClassHandle.Builder builder = hla.rti1516_202X.fedpro.ObjectClassHandle.newBuilder();
      byte[] buf = new byte[result.encodedLength()];
      result.encode(buf, 0);
      builder.setData(ByteString.copyFrom(buf));
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.ObjectInstanceHandle convertFromHla(hla.rti1516e.ObjectInstanceHandle result)
   {
      hla.rti1516_202X.fedpro.ObjectInstanceHandle.Builder builder =
            hla.rti1516_202X.fedpro.ObjectInstanceHandle.newBuilder();
      byte[] buf = new byte[result.encodedLength()];
      result.encode(buf, 0);
      builder.setData(ByteString.copyFrom(buf));
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.AttributeHandle convertFromHla(AttributeHandle result)
   {
      hla.rti1516_202X.fedpro.AttributeHandle.Builder builder = hla.rti1516_202X.fedpro.AttributeHandle.newBuilder();
      byte[] buf = new byte[result.encodedLength()];
      result.encode(buf, 0);
      builder.setData(ByteString.copyFrom(buf));
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.DimensionHandle convertFromHla(DimensionHandle result)
   {
      hla.rti1516_202X.fedpro.DimensionHandle.Builder builder = hla.rti1516_202X.fedpro.DimensionHandle.newBuilder();
      byte[] buf = new byte[result.encodedLength()];
      result.encode(buf, 0);
      builder.setData(ByteString.copyFrom(buf));
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.RegionHandle convertFromHla(RegionHandle result)
   {
      hla.rti1516_202X.fedpro.RegionHandle.Builder builder = hla.rti1516_202X.fedpro.RegionHandle.newBuilder();
      RegionHandleImpl impl = (RegionHandleImpl) result;
      byte[] buf = new byte[impl.encodedLength()];
      impl.encode(buf, 0);
      builder.setData(ByteString.copyFrom(buf));
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.AttributeHandleSet convertFromHla(AttributeHandleSet result)
   throws
         AttributeNotDefined
   {
      if (result == null) {
         throw new AttributeNotDefined("null");
      }
      hla.rti1516_202X.fedpro.AttributeHandleSet.Builder builder = hla.rti1516_202X.fedpro.AttributeHandleSet.newBuilder();
      for (AttributeHandle attributeHandle : result) {
         builder.addAttributeHandle(convertFromHla(attributeHandle));
      }
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.AttributeHandleSet convertFromHlaToSet(AttributeHandle result)
   throws
         AttributeNotDefined
   {
      if (result == null) {
         throw new AttributeNotDefined("null");
      }
      hla.rti1516_202X.fedpro.AttributeHandleSet.Builder builder = hla.rti1516_202X.fedpro.AttributeHandleSet.newBuilder();
      builder.addAttributeHandle(convertFromHla(result));
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.MessageRetractionReturn convertFromHla(MessageRetractionReturn result)
   {
      return hla.rti1516_202X.fedpro.MessageRetractionReturn.getDefaultInstance();
   }

   public hla.rti1516_202X.fedpro.TransportationTypeHandle convertFromHla(hla.rti1516e.TransportationTypeHandle result)
   {
      hla.rti1516_202X.fedpro.TransportationTypeHandle.Builder builder =
            hla.rti1516_202X.fedpro.TransportationTypeHandle.newBuilder();
      byte[] buf = new byte[result.encodedLength()];
      result.encode(buf, 0);
      builder.setData(ByteString.copyFrom(buf));
      return builder.build();
   }

   public boolean convertFromHla(boolean b)
   {
      return b;
   }

   public String convertFromHla(String s)
   {
      // Protobuf doesn't accept null values
      return Objects.requireNonNullElse(s, "");
   }

   public double convertFromHla(double d)
   {
      return d;
   }

   public long convertFromHla(long l)
   {
      return l;
   }

   public hla.rti1516_202X.fedpro.LogicalTime convertFromHla(hla.rti1516e.LogicalTime logicalTime)
   throws
         RTIinternalError
   {
      try {
         hla.rti1516_202X.fedpro.LogicalTime.Builder builder = hla.rti1516_202X.fedpro.LogicalTime.newBuilder();
         byte[] buf = new byte[logicalTime.encodedLength()];
         logicalTime.encode(buf, 0);
         builder.setData(ByteString.copyFrom(buf));
         return builder.build();
      } catch (CouldNotEncode e) {
         throw new RTIinternalError("Error in conversion", e);
      }
   }

   public hla.rti1516_202X.fedpro.LogicalTimeInterval convertFromHla(hla.rti1516e.LogicalTimeInterval logicalTimeInterval)
   throws
         RTIinternalError
   {
      try {
         hla.rti1516_202X.fedpro.LogicalTimeInterval.Builder builder =
               hla.rti1516_202X.fedpro.LogicalTimeInterval.newBuilder();
         byte[] buf = new byte[logicalTimeInterval.encodedLength()];
         logicalTimeInterval.encode(buf, 0);
         builder.setData(ByteString.copyFrom(buf));
         return builder.build();
      } catch (CouldNotEncode e) {
         throw new RTIinternalError("Error in conversion", e);
      }
   }

   public String convertToHla(String s)
   {
      return s;
   }

   public boolean convertToHla(boolean s)
   {
      return s;
   }

   public byte[] convertToHla(ByteString byteString)
   {
      return byteString.toByteArray();
   }

   public hla.rti1516e.InteractionClassHandle convertToHla(hla.rti1516_202X.fedpro.InteractionClassHandle theInteraction)
   throws
         RTIinternalError
   {
      return new InteractionClassHandleImpl(theInteraction.getData().toByteArray());
   }

   public hla.rti1516e.ObjectClassHandle convertToHla(hla.rti1516_202X.fedpro.ObjectClassHandle objectClassHandle)
   throws
         RTIinternalError
   {
      return new ObjectClassHandleImpl(objectClassHandle.getData().toByteArray());
   }

   public AttributeHandle convertToHla(hla.rti1516_202X.fedpro.AttributeHandle attributeHandle)
   throws
         RTIinternalError
   {
      return new AttributeHandleImpl(attributeHandle.getData().toByteArray());
   }

   public hla.rti1516e.ObjectInstanceHandle convertToHla(hla.rti1516_202X.fedpro.ObjectInstanceHandle objectInstanceHandle)
   throws
         RTIinternalError
   {
      return new ObjectInstanceHandleImpl(objectInstanceHandle.getData().toByteArray());
   }

   public ParameterHandle convertToHla(hla.rti1516_202X.fedpro.ParameterHandle parameterHandle)
   throws
         RTIinternalError
   {
      return new ParameterHandleImpl(parameterHandle.getData().toByteArray());
   }

   public FederateHandle convertToHla(hla.rti1516_202X.fedpro.FederateHandle federateHandle)
   throws
         RTIinternalError
   {
      byte[] encodedHandle = federateHandle.getData().toByteArray();
      if (encodedHandle.length == 0) {
         return null;
      }
      return new FederateHandleImpl(encodedHandle);
   }

   public DimensionHandle convertToHla(hla.rti1516_202X.fedpro.DimensionHandle dimensionHandle)
   throws
         RTIinternalError
   {
      return new DimensionHandleImpl(dimensionHandle.getData().toByteArray());
   }

   public RegionHandle convertToHla(hla.rti1516_202X.fedpro.RegionHandle regionHandle)
   {
      return new RegionHandleImpl(regionHandle.getData().toByteArray());
   }

   public FederateHandleSet convertToHla(hla.rti1516_202X.fedpro.FederateHandleSet federateHandleSet)
   throws
         RTIinternalError
   {
      FederateHandleSet handleSet = getFederateHandleSetFactory().create();
      for (hla.rti1516_202X.fedpro.FederateHandle federateHandle : federateHandleSet.getFederateHandleList()) {
         handleSet.add(convertToHla(federateHandle));
      }
      return handleSet;
   }

   public AttributeHandleSet convertToHla(hla.rti1516_202X.fedpro.AttributeHandleSet attributeHandleSet)
   throws
         RTIinternalError
   {
      AttributeHandleSet handleSet = getAttributeHandleSetFactory().create();
      for (hla.rti1516_202X.fedpro.AttributeHandle attributeHandle : attributeHandleSet.getAttributeHandleList()) {
         handleSet.add(convertToHla(attributeHandle));
      }
      return handleSet;
   }

   public DimensionHandleSet convertToHla(hla.rti1516_202X.fedpro.DimensionHandleSet dimensionHandleSet)
   throws
         RTIinternalError
   {
      try {
         DimensionHandleSet handleSet = getDimensionHandleSetFactory().create();
         for (hla.rti1516_202X.fedpro.DimensionHandle dimensionHandle : dimensionHandleSet.getDimensionHandleList()) {
            handleSet.add(convertToHla(dimensionHandle));
         }
         return handleSet;
      } catch (FederateNotExecutionMember | NotConnected e) {
         throw new RTIinternalError("Error decoding message");
      }
   }

   public RegionHandleSet convertToHla(hla.rti1516_202X.fedpro.RegionHandleSet regionHandleSet)
   throws
         RTIinternalError
   {
      try {
         RegionHandleSet handleSet = getRegionHandleSetFactory().create();
         for (hla.rti1516_202X.fedpro.RegionHandle regionHandle : regionHandleSet.getRegionHandleList()) {
            handleSet.add(convertToHla(regionHandle));
         }
         return handleSet;
      } catch (FederateNotExecutionMember | NotConnected e) {
         throw new RTIinternalError("Error decoding message");
      }
   }

   public AttributeHandleValueMap convertToHla(hla.rti1516_202X.fedpro.AttributeHandleValueMap attributeHandleValueMap)
   throws
         RTIinternalError
   {
      AttributeHandleValueMap result =
            getAttributeHandleValueMapFactory().create(attributeHandleValueMap.getAttributeHandleValueCount());
      for (AttributeHandleValue attributeHandleValue : attributeHandleValueMap.getAttributeHandleValueList()) {
         result.put(
               convertToHla(attributeHandleValue.getAttributeHandle()),
               convertToHla(attributeHandleValue.getValue()));
      }
      return result;
   }

   public ParameterHandleValueMap convertToHla(hla.rti1516_202X.fedpro.ParameterHandleValueMap parameterHandleValueMap)
   throws
         RTIinternalError
   {
      try {
         ParameterHandleValueMap result =
               getParameterHandleValueMapFactory().create(parameterHandleValueMap.getParameterHandleValueCount());
         for (hla.rti1516_202X.fedpro.ParameterHandleValue ParameterHandleValue : parameterHandleValueMap.getParameterHandleValueList()) {
            result.put(
                  convertToHla(ParameterHandleValue.getParameterHandle()),
                  convertToHla(ParameterHandleValue.getValue()));
         }
         return result;
      } catch (FederateNotExecutionMember | NotConnected e) {
         throw new RTIinternalError("Error decoding message");
      }
   }

   public hla.rti1516e.TransportationTypeHandle convertToHla(hla.rti1516_202X.fedpro.TransportationTypeHandle transportationTypeHandle)
   throws
         RTIinternalError
   {
      try {
         return getTransportationTypeHandleFactory().decode(transportationTypeHandle.getData().toByteArray(), 0);
      } catch (CouldNotDecode | FederateNotExecutionMember | NotConnected e) {
         throw new RTIinternalError("Error in conversion", e);
      }
   }

   public hla.rti1516e.LogicalTime convertToHla(hla.rti1516_202X.fedpro.LogicalTime logicalTime)
   throws
         RTIinternalError
   {
      try {
         return _timeFactory.decodeTime(logicalTime.getData().toByteArray(), 0);
      } catch (CouldNotDecode e) {
         throw new RTIinternalError("Error in conversion", e);
      }
   }

   public hla.rti1516e.LogicalTimeInterval convertToHla(hla.rti1516_202X.fedpro.LogicalTimeInterval logicalTimeInterval)
   throws
         RTIinternalError
   {
      try {
         return _timeFactory.decodeInterval(logicalTimeInterval.getData().toByteArray(), 0);
      } catch (CouldNotDecode e) {
         throw new RTIinternalError("Error in conversion", e);
      }
   }

   public OrderType convertToHla(hla.rti1516_202X.fedpro.OrderType orderType)
   {
      switch (orderType) {
         case RECEIVE:
            return OrderType.RECEIVE;
         case TIMESTAMP:
            return OrderType.TIMESTAMP;
         case UNRECOGNIZED:
            return OrderType.RECEIVE;
      }
      return null;
   }

   public hla.rti1516_202X.fedpro.OrderType convertFromHla(OrderType orderType)
   {
      switch (orderType) {
         case RECEIVE:
            return hla.rti1516_202X.fedpro.OrderType.RECEIVE;
         case TIMESTAMP:
            return hla.rti1516_202X.fedpro.OrderType.TIMESTAMP;
         default:
            return hla.rti1516_202X.fedpro.OrderType.RECEIVE;
      }
   }

   public MessageRetractionHandle convertToHla(hla.rti1516_202X.fedpro.MessageRetractionHandle retractionHandle)
   {
      return new MessageRetractionHandleImpl(retractionHandle.getData().toByteArray());
   }

   public ResignAction convertToHla(hla.rti1516_202X.fedpro.ResignAction resignAction)
   {
      switch (resignAction) {
         case UNCONDITIONALLY_DIVEST_ATTRIBUTES:
            return ResignAction.UNCONDITIONALLY_DIVEST_ATTRIBUTES;
         case DELETE_OBJECTS:
            return ResignAction.DELETE_OBJECTS;
         case CANCEL_PENDING_OWNERSHIP_ACQUISITIONS:
            return ResignAction.CANCEL_PENDING_OWNERSHIP_ACQUISITIONS;
         case DELETE_OBJECTS_THEN_DIVEST:
            return ResignAction.DELETE_OBJECTS_THEN_DIVEST;
         case CANCEL_THEN_DELETE_THEN_DIVEST:
            return ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST;
         case NO_ACTION:
            return ResignAction.NO_ACTION;
         case UNRECOGNIZED:
            return ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST;
      }
      return null;
   }

   public hla.rti1516_202X.fedpro.ResignAction convertFromHla(
         ResignAction resignAction)
   {
      switch (resignAction) {
         case UNCONDITIONALLY_DIVEST_ATTRIBUTES:
            return hla.rti1516_202X.fedpro.ResignAction.UNCONDITIONALLY_DIVEST_ATTRIBUTES;
         case DELETE_OBJECTS:
            return hla.rti1516_202X.fedpro.ResignAction.DELETE_OBJECTS;
         case CANCEL_PENDING_OWNERSHIP_ACQUISITIONS:
            return hla.rti1516_202X.fedpro.ResignAction.CANCEL_PENDING_OWNERSHIP_ACQUISITIONS;
         case DELETE_OBJECTS_THEN_DIVEST:
            return hla.rti1516_202X.fedpro.ResignAction.DELETE_OBJECTS_THEN_DIVEST;
         case CANCEL_THEN_DELETE_THEN_DIVEST:
            return hla.rti1516_202X.fedpro.ResignAction.CANCEL_THEN_DELETE_THEN_DIVEST;
         case NO_ACTION:
            return hla.rti1516_202X.fedpro.ResignAction.NO_ACTION;
      }
      return hla.rti1516_202X.fedpro.ResignAction.UNRECOGNIZED;
   }

   public Iterable<String> convertFromHla(Set<String> stringSet)
   {
      return stringSet;
   }

   public ByteString convertFromHla(byte[] buf)
   {
      return buf == null ? ByteString.EMPTY : ByteString.copyFrom(buf);
   }

   public hla.rti1516_202X.fedpro.FederateHandleSet convertFromHla(FederateHandleSet federateHandleSet)
   {
      hla.rti1516_202X.fedpro.FederateHandleSet.Builder builder = hla.rti1516_202X.fedpro.FederateHandleSet.newBuilder();
      for (FederateHandle federateHandle : federateHandleSet) {
         builder.addFederateHandle(convertFromHla(federateHandle));
      }
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.AttributeHandleValueMap convertFromHla(AttributeHandleValueMap attributeHandleValueMap)
   {
      hla.rti1516_202X.fedpro.AttributeHandleValueMap.Builder builder =
            hla.rti1516_202X.fedpro.AttributeHandleValueMap.newBuilder();
      for (Map.Entry<AttributeHandle, byte[]> attributeHandleEntry : attributeHandleValueMap.entrySet()) {
         AttributeHandleValue.Builder handleValueBuilder = AttributeHandleValue.newBuilder();
         handleValueBuilder.setAttributeHandle(convertFromHla(attributeHandleEntry.getKey()));
         handleValueBuilder.setValue(convertFromHla(attributeHandleEntry.getValue()));
         builder.addAttributeHandleValue(handleValueBuilder);
      }
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.ParameterHandleValueMap convertFromHla(ParameterHandleValueMap parameterHandleValueMap)
   {
      hla.rti1516_202X.fedpro.ParameterHandleValueMap.Builder builder =
            hla.rti1516_202X.fedpro.ParameterHandleValueMap.newBuilder();
      for (Map.Entry<ParameterHandle, byte[]> ParameterHandleEntry : parameterHandleValueMap.entrySet()) {
         ParameterHandleValue.Builder handleValueBuilder = ParameterHandleValue.newBuilder();
         handleValueBuilder.setParameterHandle(convertFromHla(ParameterHandleEntry.getKey()));
         handleValueBuilder.setValue(convertFromHla(ParameterHandleEntry.getValue()));
         builder.addParameterHandleValue(handleValueBuilder);
      }
      return builder.build();
   }

   public MessageRetractionReturn convertToHla(hla.rti1516_202X.fedpro.MessageRetractionReturn result)
   {
      boolean retractionHandleIsValid = result.getRetractionHandleIsValid();
      MessageRetractionHandle mrh = retractionHandleIsValid ? convertToHla(result.getMessageRetractionHandle()) : null;
      return new MessageRetractionReturn(convertToHla(retractionHandleIsValid), mrh);
   }

   public hla.rti1516e.TimeQueryReturn convertToHla(hla.rti1516_202X.fedpro.TimeQueryReturn result)
   throws
         RTIinternalError
   {
      return new TimeQueryReturn(
            result.getLogicalTimeIsValid(),
            result.hasLogicalTime() ? convertToHla(result.getLogicalTime()) : null);
   }

   public hla.rti1516_202X.fedpro.MessageRetractionHandle convertFromHla(MessageRetractionHandle messageRetractionHandle)
   {
      hla.rti1516_202X.fedpro.MessageRetractionHandle.Builder builder =
            hla.rti1516_202X.fedpro.MessageRetractionHandle.newBuilder();
      MessageRetractionHandleImpl impl = (MessageRetractionHandleImpl) messageRetractionHandle;
      byte[] buf = new byte[impl.encodedLength()];
      impl.encode(buf, 0);
      builder.setData(ByteString.copyFrom(buf));
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.DimensionHandleSet convertFromHla(
         DimensionHandleSet dimensions)
   throws
         InvalidDimensionHandle
   {
      if (dimensions == null) {
         throw new InvalidDimensionHandle("null");
      }
      hla.rti1516_202X.fedpro.DimensionHandleSet.Builder builder = hla.rti1516_202X.fedpro.DimensionHandleSet.newBuilder();
      for (DimensionHandle dimensionHandle : dimensions) {
         builder.addDimensionHandle(convertFromHla(dimensionHandle));
      }
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.RegionHandleSet convertFromHla(RegionHandleSet regions)
   throws
         InvalidRegion
   {
      if (regions == null) {
         throw new InvalidRegion("null");
      }
      hla.rti1516_202X.fedpro.RegionHandleSet.Builder builder = hla.rti1516_202X.fedpro.RegionHandleSet.newBuilder();
      for (RegionHandle regionHandle : regions) {
         builder.addRegionHandle(convertFromHla(regionHandle));
      }
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.AttributeSetRegionSetPairList convertFromHla(
         AttributeSetRegionSetPairList attributesAndRegions)
   throws
         InvalidRegion,
         AttributeNotDefined
   {
      if (attributesAndRegions == null) {
         throw new AttributeNotDefined("null");
      }
      hla.rti1516_202X.fedpro.AttributeSetRegionSetPairList.Builder builder =
            hla.rti1516_202X.fedpro.AttributeSetRegionSetPairList.newBuilder();
      for (AttributeRegionAssociation attributesAndRegion : attributesAndRegions) {
         AttributeSetRegionSetPair.Builder pairBuilder = AttributeSetRegionSetPair.newBuilder();
         pairBuilder.setAttributeSet(convertFromHla(attributesAndRegion.ahset));
         pairBuilder.setRegionSet(convertFromHla(attributesAndRegion.rhset));
         builder.addAttributeSetRegionSetPair(pairBuilder);
      }
      return builder.build();
   }

   public hla.rti1516_202X.fedpro.ServiceGroup convertFromHla(ServiceGroup group)
   throws
         InvalidServiceGroup
   {
      if (group == null) {
         throw new InvalidServiceGroup("null");
      }
      switch (group) {
         case FEDERATION_MANAGEMENT:
            return hla.rti1516_202X.fedpro.ServiceGroup.FEDERATION_MANAGEMENT;
         case DECLARATION_MANAGEMENT:
            return hla.rti1516_202X.fedpro.ServiceGroup.DECLARATION_MANAGEMENT;
         case OBJECT_MANAGEMENT:
            return hla.rti1516_202X.fedpro.ServiceGroup.OBJECT_MANAGEMENT;
         case OWNERSHIP_MANAGEMENT:
            return hla.rti1516_202X.fedpro.ServiceGroup.OWNERSHIP_MANAGEMENT;
         case TIME_MANAGEMENT:
            return hla.rti1516_202X.fedpro.ServiceGroup.TIME_MANAGEMENT;
         case DATA_DISTRIBUTION_MANAGEMENT:
            return hla.rti1516_202X.fedpro.ServiceGroup.DATA_DISTRIBUTION_MANAGEMENT;
         case SUPPORT_SERVICES:
            return hla.rti1516_202X.fedpro.ServiceGroup.SUPPORT_SERVICES;
      }
      return hla.rti1516_202X.fedpro.ServiceGroup.UNRECOGNIZED;
   }

   public long convertToHla(long result)
   {
      return result;
   }

   public double convertToHla(double result)
   {
      return result;
   }

   public hla.rti1516e.RangeBounds convertToHla(hla.rti1516_202X.fedpro.RangeBounds result)
   {
      return new hla.rti1516e.RangeBounds(
            Integer.toUnsignedLong(result.getLower()),
            Integer.toUnsignedLong(result.getUpper()));
   }

   public hla.rti1516_202X.fedpro.RangeBounds convertFromHla(hla.rti1516e.RangeBounds bounds)
   throws
         InvalidRangeBound
   {
      if (bounds == null) {
         throw new InvalidRangeBound("null");
      }
      hla.rti1516_202X.fedpro.RangeBounds.Builder builder = hla.rti1516_202X.fedpro.RangeBounds.newBuilder();
      builder.setLower((int) bounds.lower);
      builder.setUpper((int) bounds.upper);
      return builder.build();
   }

   public FederateAmbassador.SupplementalReceiveInfo convertToHla(SupplementalReceiveInfo receiveInfo)
   throws
         RTIinternalError
   {
      return new SupplementalReceiveInfoImpl(
            convertToHla(receiveInfo.getProducingFederate()),
            convertToHla(receiveInfo.getConveyedRegions()));
   }

   private RegionHandleSet convertToHla(ConveyedRegionSet conveyedRegions)
   throws
         RTIinternalError
   {
      RegionHandleSet handleSet = new RegionHandleSetImpl();
      for (ConveyedRegion conveyedRegion : conveyedRegions.getConveyedRegionsList()) {
         ConveyedRegionHandleImpl region = new ConveyedRegionHandleImpl();
         for (DimensionAndRange dimensionAndRange : conveyedRegion.getDimensionAndRangeList()) {
            region.addRange(
                  convertToHla(dimensionAndRange.getDimensionHandle()),
                  Integer.toUnsignedLong(dimensionAndRange.getRangeBounds().getLower()),
                  Integer.toUnsignedLong(dimensionAndRange.getRangeBounds().getUpper()));
         }
         handleSet.add(region);
      }
      return handleSet;
   }

   private static class SupplementalReceiveInfoImpl implements FederateAmbassador.SupplementalReceiveInfo {
      private final FederateHandle _producingFederate;
      private final RegionHandleSet _sentRegions;

      public SupplementalReceiveInfoImpl(FederateHandle producingFederate, RegionHandleSet sentRegions)
      {
         _producingFederate = producingFederate;
         _sentRegions = sentRegions;
      }

      public boolean hasProducingFederate()
      {
         return _producingFederate != null;
      }

      public FederateHandle getProducingFederate()
      {
         return _producingFederate;
      }

      public boolean hasSentRegions()
      {
         return _sentRegions != null;
      }

      public RegionHandleSet getSentRegions()
      {
         return _sentRegions;
      }
   }

   public AttributeHandleFactory getAttributeHandleFactory()
   {
      return (buffer, offset) -> {
         if (buffer.length - offset == 0) {
            throw new CouldNotDecode("empty array");
         }
         return new AttributeHandleImpl(buffer, offset);
      };
   }

   public AttributeHandleSetFactory getAttributeHandleSetFactory()
   {
      return AttributeHandleSetImpl::new;
   }

   public AttributeHandleValueMapFactory getAttributeHandleValueMapFactory()
   {
      return AttributeHandleValueMapImpl::new;
   }

   public AttributeSetRegionSetPairListFactory getAttributeSetRegionSetPairListFactory()
   {
      return capacity -> new AttributeSetRegionSetPairListImpl();
   }

   public DimensionHandleFactory getDimensionHandleFactory()
   {
      return (buffer, offset) -> {
         if (buffer.length - offset == 0) {
            throw new CouldNotDecode("empty array");
         }
         return new DimensionHandleImpl(buffer, offset);
      };
   }

   public DimensionHandleSetFactory getDimensionHandleSetFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return DimensionHandleSetImpl::new;
   }

   public FederateHandleFactory getFederateHandleFactory()
   {
      return (buffer, offset) -> {
         if (buffer.length - offset == 0) {
            throw new CouldNotDecode("empty array");
         }
         return new FederateHandleImpl(buffer, offset);
      };
   }

   public FederateHandleSetFactory getFederateHandleSetFactory()
   {
      return FederateHandleSetImpl::new;
   }

   public InteractionClassHandleFactory getInteractionClassHandleFactory()
   {
      return (buffer, offset) -> {
         if (buffer.length - offset == 0) {
            throw new CouldNotDecode("empty array");
         }
         return new InteractionClassHandleImpl(buffer, offset);
      };
   }

   public ObjectClassHandleFactory getObjectClassHandleFactory()
   {
      return (buffer, offset) -> {
         if (buffer.length - offset == 0) {
            throw new CouldNotDecode("empty array");
         }
         return new ObjectClassHandleImpl(buffer, offset);
      };
   }

   public ObjectInstanceHandleFactory getObjectInstanceHandleFactory()
   {
      return (buffer, offset) -> {
         if (buffer.length - offset == 0) {
            throw new CouldNotDecode("empty array");
         }
         return new ObjectInstanceHandleImpl(buffer, offset);
      };
   }

   public ParameterHandleFactory getParameterHandleFactory()
   {
      return (buffer, offset) -> {
         if (buffer.length - offset == 0) {
            throw new CouldNotDecode("empty array");
         }
         return new ParameterHandleImpl(buffer, offset);
      };
   }

   public ParameterHandleValueMapFactory getParameterHandleValueMapFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return ParameterHandleValueMapImpl::new;
   }

   public SynchronizationPointFailureReason convertToHla(hla.rti1516_202X.fedpro.SynchronizationPointFailureReason reason)
   {
      switch (reason) {
         case SYNCHRONIZATION_POINT_LABEL_NOT_UNIQUE:
            return SynchronizationPointFailureReason.SYNCHRONIZATION_POINT_LABEL_NOT_UNIQUE;
         case SYNCHRONIZATION_SET_MEMBER_NOT_JOINED:
            return SynchronizationPointFailureReason.SYNCHRONIZATION_SET_MEMBER_NOT_JOINED;
         case UNRECOGNIZED:
            // FIXME What is the default? Or should we throw an excxeption?
            return SynchronizationPointFailureReason.SYNCHRONIZATION_POINT_LABEL_NOT_UNIQUE;
      }
      return null;
   }

   public SaveFailureReason convertToHla(hla.rti1516_202X.fedpro.SaveFailureReason reason)
   {
      switch (reason) {
         case RTI_UNABLE_TO_SAVE:
            return SaveFailureReason.RTI_UNABLE_TO_SAVE;
         case FEDERATE_REPORTED_FAILURE_DURING_SAVE:
            return SaveFailureReason.FEDERATE_REPORTED_FAILURE_DURING_SAVE;
         case FEDERATE_RESIGNED_DURING_SAVE:
            return SaveFailureReason.FEDERATE_RESIGNED_DURING_SAVE;
         case RTI_DETECTED_FAILURE_DURING_SAVE:
            return SaveFailureReason.RTI_DETECTED_FAILURE_DURING_SAVE;
         case SAVE_TIME_CANNOT_BE_HONORED:
            return SaveFailureReason.SAVE_TIME_CANNOT_BE_HONORED;
         case SAVE_ABORTED:
            return SaveFailureReason.SAVE_ABORTED;
         case UNRECOGNIZED:
            return SaveFailureReason.RTI_UNABLE_TO_SAVE;
      }
      return null;
   }

   public RestoreFailureReason convertToHla(hla.rti1516_202X.fedpro.RestoreFailureReason reason)
   {
      switch (reason) {
         case RTI_UNABLE_TO_RESTORE:
            return RestoreFailureReason.RTI_UNABLE_TO_RESTORE;
         case FEDERATE_REPORTED_FAILURE_DURING_RESTORE:
            return RestoreFailureReason.FEDERATE_REPORTED_FAILURE_DURING_RESTORE;
         case FEDERATE_RESIGNED_DURING_RESTORE:
            return RestoreFailureReason.FEDERATE_RESIGNED_DURING_RESTORE;
         case RTI_DETECTED_FAILURE_DURING_RESTORE:
            return RestoreFailureReason.RTI_DETECTED_FAILURE_DURING_RESTORE;
         case RESTORE_ABORTED:
            return RestoreFailureReason.RESTORE_ABORTED;
         case UNRECOGNIZED:
            return RestoreFailureReason.RTI_UNABLE_TO_RESTORE;
      }
      return null;
   }

   public FederationExecutionInformationSet convertToHla(hla.rti1516_202X.fedpro.FederationExecutionInformationSet theFederationExecutionInformationSet)
   {
      FederationExecutionInformationSet informationSet = new FederationExecutionInformationSetImpl();
      for (hla.rti1516_202X.fedpro.FederationExecutionInformation info : theFederationExecutionInformationSet.getFederationExecutionInformationList()) {
         informationSet.add(convertToHla(info));
      }
      return informationSet;
   }

   public Set<String> convertToHla(ProtocolStringList stringList)
   {
      return new HashSet<>(stringList);
   }

   public FederateAmbassador.SupplementalReflectInfo convertToHla(
         ConveyedRegionSet conveyedRegions,
         hla.rti1516_202X.fedpro.FederateHandle producingFederate)
   throws
         RTIinternalError
   {
      return new SupplementalReflectInfoImpl(
            convertToHla(producingFederate), convertToHla(conveyedRegions)
      );
   }

   public FederateAmbassador.SupplementalReceiveInfo convertToHlaSupplementalReceiveInfo(
         ConveyedRegionSet conveyedRegions,
         hla.rti1516_202X.fedpro.FederateHandle producingFederate)
   throws
         RTIinternalError
   {
      return new SupplementalReceiveInfoImpl(
            convertToHla(producingFederate),
            convertToHla(conveyedRegions));
   }

   public FederateAmbassador.SupplementalRemoveInfo convertToHlaSupplementalRemoveInfo(FederateHandle handle)
   {
      return new MySupplementalRemoveInfo(handle);
   }

   public FederateRestoreStatus[] convertToHla(FederateRestoreStatusArray federateRestoreStatusArray)
   throws
         RTIinternalError
   {
      FederateRestoreStatus[] federateRestoreStatuses =
            new FederateRestoreStatus[federateRestoreStatusArray.getFederateRestoreStatusCount()];
      int i = 0;
      for (hla.rti1516_202X.fedpro.FederateRestoreStatus federateRestoreStatus : federateRestoreStatusArray.getFederateRestoreStatusList()) {
         federateRestoreStatuses[i] = new FederateRestoreStatus(
               convertToHla(federateRestoreStatus.getPreRestoreHandle()),
               convertToHla(federateRestoreStatus.getPostRestoreHandle()),
               convertToHla(federateRestoreStatus.getRestoreStatus()));
         i++;
      }
      return federateRestoreStatuses;
   }

   public FederateHandleSaveStatusPair[] convertToHla(FederateHandleSaveStatusPairArray federateHandleSaveStatusPairArray)
   throws
         RTIinternalError
   {
      FederateHandleSaveStatusPair[] federateHandleSaveStatusPairs =
            new FederateHandleSaveStatusPair[federateHandleSaveStatusPairArray.getFederateHandleSaveStatusPairCount()];
      int i = 0;
      for (hla.rti1516_202X.fedpro.FederateHandleSaveStatusPair saveStatusPair : federateHandleSaveStatusPairArray.getFederateHandleSaveStatusPairList()) {
         federateHandleSaveStatusPairs[i] = new FederateHandleSaveStatusPair(
               convertToHla(saveStatusPair.getFederateHandle()),
               convertToHla(saveStatusPair.getSaveStatus()));
         i++;
      }
      return federateHandleSaveStatusPairs;
   }

   private RestoreStatus convertToHla(hla.rti1516_202X.fedpro.RestoreStatus restoreStatus)
   {
      switch (restoreStatus) {
         case NO_RESTORE_IN_PROGRESS:
            return RestoreStatus.NO_RESTORE_IN_PROGRESS;
         case FEDERATE_RESTORE_REQUEST_PENDING:
            return RestoreStatus.FEDERATE_RESTORE_REQUEST_PENDING;
         case FEDERATE_WAITING_FOR_RESTORE_TO_BEGIN:
            return RestoreStatus.FEDERATE_WAITING_FOR_RESTORE_TO_BEGIN;
         case FEDERATE_PREPARED_TO_RESTORE:
            return RestoreStatus.FEDERATE_RESTORING;
         case FEDERATE_RESTORING:
            return RestoreStatus.FEDERATE_RESTORING;
         case FEDERATE_WAITING_FOR_FEDERATION_TO_RESTORE:
            return RestoreStatus.FEDERATE_WAITING_FOR_FEDERATION_TO_RESTORE;
         case UNRECOGNIZED:
            return RestoreStatus.NO_RESTORE_IN_PROGRESS;
      }
      return null;
   }

   private SaveStatus convertToHla(hla.rti1516_202X.fedpro.SaveStatus saveStatus)
   {
      switch (saveStatus) {
         case NO_SAVE_IN_PROGRESS:
            return SaveStatus.NO_SAVE_IN_PROGRESS;
         case FEDERATE_INSTRUCTED_TO_SAVE:
            return SaveStatus.FEDERATE_INSTRUCTED_TO_SAVE;
         case FEDERATE_SAVING:
            return SaveStatus.FEDERATE_SAVING;
         case FEDERATE_WAITING_FOR_FEDERATION_TO_SAVE:
            return SaveStatus.FEDERATE_WAITING_FOR_FEDERATION_TO_SAVE;
         case UNRECOGNIZED:
            return SaveStatus.NO_SAVE_IN_PROGRESS;
      }
      return null;
   }

   private static class SupplementalReflectInfoImpl implements FederateAmbassador.SupplementalReflectInfo {
      private final FederateHandle _producingFederate;
      private final RegionHandleSet _sentRegions;

      public SupplementalReflectInfoImpl(FederateHandle producingFederate, RegionHandleSet sentRegions)
      {
         _producingFederate = producingFederate;
         _sentRegions = sentRegions;
      }

      public boolean hasProducingFederate()
      {
         return _producingFederate != null;
      }

      public boolean hasSentRegions()
      {
         return _sentRegions != null;
      }

      public FederateHandle getProducingFederate()
      {
         return _producingFederate;
      }

      public RegionHandleSet getSentRegions()
      {
         return _sentRegions;
      }

      public String toString()
      {
         return "{" + _producingFederate + ", " + _sentRegions + '}';
      }
   }

   private static class MySupplementalRemoveInfo implements FederateAmbassador.SupplementalRemoveInfo {
      private final FederateHandle _producingFederate;

      private MySupplementalRemoveInfo(FederateHandle producingFederate)
      {
         _producingFederate = producingFederate;
      }

      public boolean hasProducingFederate()
      {
         return _producingFederate != null;
      }

      public FederateHandle getProducingFederate()
      {
         return _producingFederate;
      }

      public String toString()
      {
         return "{" + _producingFederate + '}';
      }
   }

   private FederationExecutionInformation convertToHla(hla.rti1516_202X.fedpro.FederationExecutionInformation info)
   {
      return new FederationExecutionInformation(
            info.getFederationExecutionName(),
            info.getLogicalTimeImplementationName());
   }

   private static class FederationExecutionInformationSetImpl extends HashSet<FederationExecutionInformation> implements FederationExecutionInformationSet {
      public Object clone()
      {
         return super.clone();
      }
   }

   public RegionHandleSetFactory getRegionHandleSetFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return RegionHandleSetImpl::new;
   }

   private static class MyTransportationTypeHandleFactory implements TransportationTypeHandleFactory {
      private static final TransportationTypeHandleImpl HLA_RELIABLE =
            new TransportationTypeHandleImpl(new byte[] {0, 0, 0, 1});
      private static final TransportationTypeHandleImpl HLA_BEST_EFFORT =
            new TransportationTypeHandleImpl(new byte[] {0, 0, 0, 2});

      public TransportationTypeHandle decode(
            byte[] buffer,
            int offset)
      throws CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError
      {
         if (buffer.length - offset == 0) {
            throw new CouldNotDecode("empty array");
         }
         return new TransportationTypeHandleImpl(buffer, offset);
      }

      private static TransportationTypeHandleImpl resolve(TransportationTypeHandleImpl handle)
      throws CouldNotDecode
      {
         if (handle.equals(HLA_BEST_EFFORT)) {
            return HLA_BEST_EFFORT;
         } else if (handle.equals(HLA_RELIABLE)) {
            return HLA_RELIABLE;
         } else {
            throw new CouldNotDecode("Unknown transport type: " + handle);
         }
      }

      public TransportationTypeHandle getHLAdefaultReliable()
      throws RTIinternalError
      {
         return HLA_RELIABLE;
      }

      public TransportationTypeHandle getHLAdefaultBestEffort()
      throws RTIinternalError
      {
         return HLA_BEST_EFFORT;
      }
   }

   public TransportationTypeHandleFactory getTransportationTypeHandleFactory()
   throws
         FederateNotExecutionMember,
         NotConnected
   {
      return new MyTransportationTypeHandleFactory();
   }

   private static class AttributeHandleSetImpl extends HashSet<AttributeHandle> implements AttributeHandleSet {
      public AttributeHandleSet clone()
      {
         return (AttributeHandleSet) super.clone();
      }
   }

   private static class AttributeHandleValueMapImpl extends HashMap<AttributeHandle, byte[]> implements AttributeHandleValueMap {
      public AttributeHandleValueMapImpl(int capacity)
      {
         super(capacity);
      }

      public ByteWrapper getValueReference(AttributeHandle key)
      {
         byte[] value = get(key);
         if (value == null) {
            return null;
         }
         return new ByteWrapper(value);
      }

      public ByteWrapper getValueReference(AttributeHandle key, ByteWrapper byteWrapper)
      {
         byte[] value = get(key);
         if (value == null) {
            byteWrapper.reassign(new byte[0], 0, 0);
         } else {
            byteWrapper.reassign(value, 0, value.length);
         }
         return byteWrapper;
      }

      public boolean equals(Object o)
      {
         if (o == this) {
            return true;
         }

         if (!(o instanceof AttributeHandleValueMap)) {
            return false;
         }
         AttributeHandleValueMap t = (AttributeHandleValueMap) o;
         if (t.size() != size()) {
            return false;
         }

         for (Entry<AttributeHandle, byte[]> e : entrySet()) {
            Object key = e.getKey();
            byte[] value = e.getValue();
            if (value == null) {
               if (!(t.get(key) == null && t.containsKey(key))) {
                  return false;
               }
            } else {
               if (!Arrays.equals(value, t.get(key))) {
                  return false;
               }
            }
         }
         return true;
      }
   }

   private static class ParameterHandleValueMapImpl extends HashMap<ParameterHandle, byte[]> implements ParameterHandleValueMap {
      public ParameterHandleValueMapImpl(int capacity)
      {
         super(capacity);
      }

      public ByteWrapper getValueReference(ParameterHandle key)
      {
         byte[] value = get(key);
         if (value == null) {
            return null;
         }
         return new ByteWrapper(value);
      }

      public ByteWrapper getValueReference(ParameterHandle key, ByteWrapper byteWrapper)
      {
         byte[] value = get(key);
         if (value == null) {
            byteWrapper.reassign(new byte[0], 0, 0);
         } else {
            byteWrapper.reassign(value, 0, value.length);
         }
         return byteWrapper;
      }

      public boolean equals(Object o)
      {
         if (o == this) {
            return true;
         }

         if (!(o instanceof ParameterHandleValueMap)) {
            return false;
         }
         ParameterHandleValueMap t = (ParameterHandleValueMap) o;
         if (t.size() != size()) {
            return false;
         }

         for (Entry<ParameterHandle, byte[]> e : entrySet()) {
            Object key = e.getKey();
            byte[] value = e.getValue();
            if (value == null) {
               if (!(t.get(key) == null && t.containsKey(key))) {
                  return false;
               }
            } else {
               if (!Arrays.equals(value, t.get(key))) {
                  return false;
               }
            }
         }
         return true;
      }
   }

   private static class FederateHandleSetImpl extends HashSet<FederateHandle> implements FederateHandleSet {
      public FederateHandleSet clone()
      {
         return (FederateHandleSet) super.clone();
      }
   }

   private static class DimensionHandleSetImpl extends HashSet<DimensionHandle> implements DimensionHandleSet {
      public DimensionHandleSet clone()
      {
         return (DimensionHandleSet) super.clone();
      }
   }

   private static class RegionHandleSetImpl extends HashSet<RegionHandle> implements RegionHandleSet {
      public RegionHandleSet clone()
      {
         return (RegionHandleSet) super.clone();
      }
   }

   private static class AttributeSetRegionSetPairListImpl extends ArrayList<AttributeRegionAssociation> implements AttributeSetRegionSetPairList {
      public AttributeSetRegionSetPairList clone()
      {
         return (AttributeSetRegionSetPairList) super.clone();
      }
   }
}
