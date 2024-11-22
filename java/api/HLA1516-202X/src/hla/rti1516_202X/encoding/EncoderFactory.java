/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_202X.encoding;

import hla.rti1516_202X.*;
import hla.rti1516_202X.time.LogicalTime;
import hla.rti1516_202X.time.LogicalTimeInterval;

/**
 * Factory for the various HLA data types.
 */
public interface EncoderFactory {
   HLAASCIIchar createHLAASCIIchar();

   HLAASCIIchar createHLAASCIIchar(byte b);

   HLAASCIIstring createHLAASCIIstring();

   HLAASCIIstring createHLAASCIIstring(String s);

   HLAboolean createHLAboolean();

   HLAboolean createHLAboolean(boolean b);

   HLAbyte createHLAbyte();

   HLAbyte createHLAbyte(byte b);

   <T extends DataElement> HLAvariantRecord<T> createHLAvariantRecord(T discriminant);

   <T extends DataElement> HLAextendableVariantRecord<T> createHLAextendableVariantRecord(T discriminant);

   HLAfixedRecord createHLAfixedRecord();

   <T extends DataElement> HLAfixedArray<T> createHLAfixedArray(DataElementFactory<T> factory, int size);

   <T extends DataElement> HLAfixedArray<T> createHLAfixedArray(T... elements);

   HLAfloat32BE createHLAfloat32BE();

   HLAfloat32BE createHLAfloat32BE(float f);

   HLAfloat32LE createHLAfloat32LE();

   HLAfloat32LE createHLAfloat32LE(float f);

   HLAfloat64BE createHLAfloat64BE();

   HLAfloat64BE createHLAfloat64BE(double d);

   HLAfloat64LE createHLAfloat64LE();

   HLAfloat64LE createHLAfloat64LE(double d);

   HLAinteger16BE createHLAinteger16BE();

   HLAinteger16BE createHLAinteger16BE(short s);

   HLAinteger16LE createHLAinteger16LE();

   HLAinteger16LE createHLAinteger16LE(short s);

   HLAinteger32BE createHLAinteger32BE();

   HLAinteger32BE createHLAinteger32BE(int i);

   HLAinteger32LE createHLAinteger32LE();

   HLAinteger32LE createHLAinteger32LE(int i);

   HLAinteger64BE createHLAinteger64BE();

   HLAinteger64BE createHLAinteger64BE(long l);

   HLAinteger64LE createHLAinteger64LE();

   HLAinteger64LE createHLAinteger64LE(long l);

   HLAunsignedInteger16BE createHLAunsignedInteger16BE();

   HLAunsignedInteger16BE createHLAunsignedInteger16BE(short s);

   HLAunsignedInteger16LE createHLAunsignedInteger16LE();

   HLAunsignedInteger16LE createHLAunsignedInteger16LE(short s);

   HLAunsignedInteger32BE createHLAunsignedInteger32BE();

   HLAunsignedInteger32BE createHLAunsignedInteger32BE(int i);

   HLAunsignedInteger32LE createHLAunsignedInteger32LE();

   HLAunsignedInteger32LE createHLAunsignedInteger32LE(int i);

   HLAunsignedInteger64BE createHLAunsignedInteger64BE();

   HLAunsignedInteger64BE createHLAunsignedInteger64BE(long l);

   HLAunsignedInteger64LE createHLAunsignedInteger64LE();

   HLAunsignedInteger64LE createHLAunsignedInteger64LE(long l);

   HLAoctet createHLAoctet();

   HLAoctet createHLAoctet(byte b);

   HLAoctetPairBE createHLAoctetPairBE();

   HLAoctetPairBE createHLAoctetPairBE(short s);

   HLAoctetPairLE createHLAoctetPairLE();

   HLAoctetPairLE createHLAoctetPairLE(short s);

   HLAopaqueData createHLAopaqueData();

   HLAopaqueData createHLAopaqueData(byte[] b);

   HLAunicodeChar createHLAunicodeChar();

   HLAunicodeChar createHLAunicodeChar(short c);

   HLAunicodeString createHLAunicodeString();

   HLAunicodeString createHLAunicodeString(String s);

   <T extends DataElement> HLAvariableArray<T> createHLAvariableArray(DataElementFactory<T> factory, T... elements);

   HLAfederateHandle createHLAfederateHandle(RTIambassador rtiAmbassador);

   HLAfederateHandle createHLAfederateHandle(RTIambassador rtiAmbassador, FederateHandle federateHandle);

   HLAobjectClassHandle createHLAobjectClassHandle(RTIambassador rtiAmbassador);

   HLAobjectClassHandle createHLAobjectClassHandle(RTIambassador rtiAmbassador, ObjectClassHandle objectClassHandle);

   HLAinteractionClassHandle createHLAinteractionClassHandle(RTIambassador rtiAmbassador);

   HLAinteractionClassHandle createHLAinteractionClassHandle(RTIambassador rtiAmbassador,
                                                             InteractionClassHandle interactionClassHandle);

   HLAobjectInstanceHandle createHLAobjectInstanceHandle(RTIambassador rtiAmbassador);

   HLAobjectInstanceHandle createHLAobjectInstanceHandle(RTIambassador rtiAmbassador,
                                                         ObjectInstanceHandle objectInstanceHandle);

   HLAattributeHandle createHLAattributeHandle(RTIambassador rtiAmbassador);

   HLAattributeHandle createHLAattributeHandle(RTIambassador rtiAmbassador, AttributeHandle attributeHandle);

   HLAparameterHandle createHLAparameterHandle(RTIambassador rtiAmbassador);

   HLAparameterHandle createHLAparameterHandle(RTIambassador rtiAmbassador, ParameterHandle parameterHandle);

   HLAdimensionHandle createHLAdimensionHandle(RTIambassador rtiAmbassador);

   HLAdimensionHandle createHLAdimensionHandle(RTIambassador rtiAmbassador, DimensionHandle dimensionHandle);

   HLAmessageRetractionHandle createHLAmessageRetractionHandle(RTIambassador rtiAmbassador);

   HLAmessageRetractionHandle createHLAmessageRetractionHandle(RTIambassador rtiAmbassador,
                                                               MessageRetractionHandle messageRetractionHandle);

   HLAregionHandle createHLAregionHandle(RTIambassador rtiAmbassador);

   HLAregionHandle createHLAregionHandle(RTIambassador rtiAmbassador, RegionHandle regionHandle);

   <T extends LogicalTime<T, U>, U extends LogicalTimeInterval<U>> HLAlogicalTime<T, U> createHLAlogicalTime(RTIambassador rtiAmbassador);

   <T extends LogicalTime<T, U>, U extends LogicalTimeInterval<U>> HLAlogicalTime<T, U> createHLAlogicalTime(RTIambassador rtiAmbassador, T logicalTime);

   <T extends LogicalTime<T, U>, U extends LogicalTimeInterval<U>> HLAlogicalTimeInterval<T, U> createHLAlogicalTimeInterval(RTIambassador rtiAmbassador);

   <T extends LogicalTime<T, U>, U extends LogicalTimeInterval<U>> HLAlogicalTimeInterval<T, U> createHLAlogicalTimeInterval(RTIambassador rtiAmbassador, U logicalTimeInterval);
}
