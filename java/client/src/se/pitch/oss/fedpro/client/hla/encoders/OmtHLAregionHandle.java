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

package se.pitch.oss.fedpro.client.hla.encoders;

import hla.rti1516_202X.RTIambassador;
import hla.rti1516_202X.RegionHandle;
import hla.rti1516_202X.encoding.ByteWrapper;
import hla.rti1516_202X.encoding.DecoderException;
import hla.rti1516_202X.encoding.EncoderException;
import hla.rti1516_202X.encoding.HLAregionHandle;
import hla.rti1516_202X.exceptions.CouldNotDecode;
import hla.rti1516_202X.exceptions.FederateNotExecutionMember;
import hla.rti1516_202X.exceptions.NotConnected;
import hla.rti1516_202X.exceptions.RTIinternalError;

import java.util.Objects;

class OmtHLAregionHandle extends AbstractDataElement implements HLAregionHandle {
   private final RTIambassador _rtiAmbassador;

   private RegionHandle _regionHandle;

   OmtHLAregionHandle(RTIambassador rtiAmbassador)
   {
      _rtiAmbassador = rtiAmbassador;
      _regionHandle = null;
   }

   OmtHLAregionHandle(
         RTIambassador rtiAmbassador,
         RegionHandle regionHandle)
   {
      _rtiAmbassador = rtiAmbassador;
      _regionHandle = regionHandle;
   }

   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      if (_regionHandle == null) {
         throw new EncoderException("Unable to encode empty HLAregionHandle");
      }
      try {
         byteWrapper.align(4);
         byte[] elements = new byte[_regionHandle.encodedLength()];
         _regionHandle.encode(elements, 0);
         byteWrapper.putInt(elements.length);
         byteWrapper.put(elements);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAregionHandle");
      }
   }

   public OmtHLAregionHandle decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(4);
         int encodedLength = byteWrapper.getInt();
         byteWrapper.verify(encodedLength);
         byte[] buf = new byte[encodedLength];
         byteWrapper.get(buf);
         _regionHandle = _rtiAmbassador.getRegionHandleFactory().decode(buf, 0);
      } catch (ArrayIndexOutOfBoundsException | NotConnected | RTIinternalError | FederateNotExecutionMember |
               CouldNotDecode e) {
         throw new DecoderException("Failed to decode HLAregionHandle", e);
      }
      return this;
   }

   @Override
   public HLAregionHandle decode(byte[] bytes)
   throws DecoderException
   {
      decode(new ByteWrapper(bytes));
      return this;
   }

   public int getEncodedLength()
   {
      return 4 + _regionHandle.encodedLength();
   }

   public int getOctetBoundary()
   {
      return 4;
   }

   public RegionHandle getValue()
   {
      return _regionHandle;
   }

   public HLAregionHandle setValue(RegionHandle value)
   {
      _regionHandle = value;
      return this;
   }

   /**
    * @noinspection RedundantIfStatement
    */
   @Override
   public boolean equals(Object o)
   {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }

      final OmtHLAregionHandle omtHLAhandle = (OmtHLAregionHandle) o;

      if (!Objects.equals(_regionHandle, omtHLAhandle._regionHandle)) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return (_regionHandle != null ? _regionHandle.hashCode() : 0);
   }

   @Override
   public String toString()
   {
      return "HLAregionHandle<" + _regionHandle + ">";
   }
}
