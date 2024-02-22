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

package se.pitch.oss.fedpro.client_evolved.hla.encoders;

import hla.rti1516e.encoding.*;

public class OmtEncoderFactory implements EncoderFactory {
   private static final OmtEncoderFactory OMT_FACTORY = new OmtEncoderFactory();

   private OmtEncoderFactory()
   {
   }

   public static OmtEncoderFactory getInstance()
   {
      return OMT_FACTORY;
   }

   public HLAinteger32BE createHLAinteger32BE()
   {
      return new OmtHLAinteger32BE();
   }

   public HLAinteger32BE createHLAinteger32BE(int myNumber)
   {
      return new OmtHLAinteger32BE(myNumber);
   }

   public HLAinteger32LE createHLAinteger32LE()
   {
      return new OmtHLAinteger32LE();
   }

   public HLAinteger32LE createHLAinteger32LE(int i)
   {
      return new OmtHLAinteger32LE(i);
   }

   public HLAinteger64BE createHLAinteger64BE()
   {
      return new OmtHLAinteger64BE();
   }

   public HLAinteger64BE createHLAinteger64BE(long l)
   {
      return new OmtHLAinteger64BE(l);
   }

   public HLAinteger64LE createHLAinteger64LE()
   {
      return new OmtHLAinteger64LE();
   }

   public HLAinteger64LE createHLAinteger64LE(long l)
   {
      return new OmtHLAinteger64LE(l);
   }

   public HLAoctet createHLAoctet()
   {
      return new OmtHLAoctet();
   }

   public HLAoctet createHLAoctet(byte b)
   {
      return new OmtHLAoctet(b);
   }

   public HLAoctetPairBE createHLAoctetPairBE()
   {
      return new OmtHLAoctetPairBE();
   }

   public HLAoctetPairBE createHLAoctetPairBE(short s)
   {
      return new OmtHLAoctetPairBE(s);
   }

   public HLAoctetPairLE createHLAoctetPairLE()
   {
      return new OmtHLAoctetPairLE();
   }

   public HLAoctetPairLE createHLAoctetPairLE(short s)
   {
      return new OmtHLAoctetPairLE(s);
   }

   public HLAopaqueData createHLAopaqueData()
   {
      return new OmtHLAopaqueData();
   }

   public HLAopaqueData createHLAopaqueData(byte[] b)
   {
      return new OmtHLAopaqueData(b);
   }

   public HLAunicodeChar createHLAunicodeChar()
   {
      return new OmtHLAunicodeChar();
   }

   public HLAunicodeChar createHLAunicodeChar(short c)
   {
      return new OmtHLAunicodeChar(c);
   }

   public HLAunicodeString createHLAunicodeString()
   {
      return new OmtHLAunicodeString();
   }

   public HLAunicodeString createHLAunicodeString(String s)
   {
      return new OmtHLAunicodeString(s);
   }

   public HLAASCIIchar createHLAASCIIchar()
   {
      return new OmtHLAASCIIchar();
   }

   public HLAASCIIchar createHLAASCIIchar(byte b)
   {
      return new OmtHLAASCIIchar(b);
   }

   public HLAASCIIstring createHLAASCIIstring()
   {
      return new OmtHLAASCIIstring();
   }

   public HLAASCIIstring createHLAASCIIstring(String s)
   {
      return new OmtHLAASCIIstring(s);
   }

   public HLAboolean createHLAboolean()
   {
      return new OmtHLAboolean();
   }

   public HLAboolean createHLAboolean(boolean b)
   {
      return new OmtHLAboolean(b);
   }

   public HLAbyte createHLAbyte()
   {
      return new OmtHLAbyte();
   }

   public HLAbyte createHLAbyte(byte b)
   {
      return new OmtHLAbyte(b);
   }

   public <T extends DataElement> HLAvariantRecord<T> createHLAvariantRecord(T discriminant)
   {
      return new OmtHLAvariantRecord<>(discriminant);
   }

   public HLAfixedRecord createHLAfixedRecord()
   {
      return new OmtHLAfixedRecord();
   }

   public <T extends DataElement> HLAfixedArray<T> createHLAfixedArray(DataElementFactory<T> factory, int size)
   {
      return new OmtHLAfixedArray<>(factory, size);
   }

   public <T extends DataElement> HLAfixedArray<T> createHLAfixedArray(T... elements)
   {
      return new OmtHLAfixedArray<>(elements);
   }

   public HLAfloat32BE createHLAfloat32BE()
   {
      return new OmtHLAfloat32BE();
   }

   public HLAfloat32BE createHLAfloat32BE(float f)
   {
      return new OmtHLAfloat32BE(f);
   }

   public HLAfloat32LE createHLAfloat32LE()
   {
      return new OmtHLAfloat32LE();
   }

   public HLAfloat32LE createHLAfloat32LE(float f)
   {
      return new OmtHLAfloat32LE(f);
   }

   public HLAfloat64BE createHLAfloat64BE()
   {
      return new OmtHLAfloat64BE();
   }

   public HLAfloat64BE createHLAfloat64BE(double d)
   {
      return new OmtHLAfloat64BE(d);
   }

   public HLAfloat64LE createHLAfloat64LE()
   {
      return new OmtHLAfloat64LE();
   }

   public HLAfloat64LE createHLAfloat64LE(double d)
   {
      return new OmtHLAfloat64LE(d);
   }

   public HLAinteger16BE createHLAinteger16BE()
   {
      return new OmtHLAinteger16BE();
   }

   public HLAinteger16BE createHLAinteger16BE(short s)
   {
      return new OmtHLAinteger16BE(s);
   }

   public HLAinteger16LE createHLAinteger16LE()
   {
      return new OmtHLAinteger16LE();
   }

   public HLAinteger16LE createHLAinteger16LE(short s)
   {
      return new OmtHLAinteger16LE(s);
   }

   public <T extends DataElement> HLAvariableArray<T> createHLAvariableArray(
         DataElementFactory<T> factory,
         T... elements)
   {
      return new OmtHLAvariableArray<>(factory, elements);
   }
}
