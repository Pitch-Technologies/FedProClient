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

import hla.rti1516_2025.RTIambassador;
import hla.rti1516_2025.TransportationTypeHandle;
import hla.rti1516_2025.encoding.ByteWrapper;
import hla.rti1516_2025.encoding.DecoderException;
import hla.rti1516_2025.encoding.EncoderException;
import hla.rti1516_2025.encoding.HLAtransportationTypeHandle;
import hla.rti1516_2025.exceptions.CouldNotDecode;
import hla.rti1516_2025.exceptions.FederateNotExecutionMember;
import hla.rti1516_2025.exceptions.NotConnected;
import hla.rti1516_2025.exceptions.RTIinternalError;

import java.util.Objects;

class OmtHLAtransportationTypeHandle extends AbstractDataElement implements HLAtransportationTypeHandle {
   private final RTIambassador _rtiAmbassador;

   private TransportationTypeHandle _transportationTypeHandle;

   OmtHLAtransportationTypeHandle(RTIambassador rtiAmbassador)
   {
      _rtiAmbassador = rtiAmbassador;
      _transportationTypeHandle = null;
   }

   OmtHLAtransportationTypeHandle(
         RTIambassador rtiAmbassador,
         TransportationTypeHandle transportationTypeHandle)
   {
      _rtiAmbassador = rtiAmbassador;
      _transportationTypeHandle = transportationTypeHandle;
   }

   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      if (_transportationTypeHandle == null) {
         throw new EncoderException("Unable to encode empty HLAtransportationTypeHandle");
      }
      try {
         byteWrapper.align(4);
         byte[] elements = new byte[_transportationTypeHandle.encodedLength()];
         _transportationTypeHandle.encode(elements, 0);
         byteWrapper.putInt(elements.length);
         byteWrapper.put(elements);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAtransportationTypeHandle");
      }
   }

   public HLAtransportationTypeHandle decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(4);
         int encodedLength = byteWrapper.getInt();
         byteWrapper.verify(encodedLength);
         byte[] buf = new byte[encodedLength];
         byteWrapper.get(buf);
         _transportationTypeHandle = _rtiAmbassador.getTransportationTypeHandleFactory().decode(buf, 0);
      } catch (ArrayIndexOutOfBoundsException | NotConnected | RTIinternalError | FederateNotExecutionMember |
               CouldNotDecode e) {
         throw new DecoderException("Failed to decode HLAtransportationTypeHandle", e);
      }
      return this;
   }

   @Override
   public HLAtransportationTypeHandle decode(byte[] bytes)
   throws DecoderException
   {
      decode(new ByteWrapper(bytes));
      return this;
   }

   public int getEncodedLength()
   {
      return 4 + _transportationTypeHandle.encodedLength();
   }

   public int getOctetBoundary()
   {
      return 4;
   }

   public TransportationTypeHandle getValue()
   {
      return _transportationTypeHandle;
   }

   public HLAtransportationTypeHandle setValue(TransportationTypeHandle value)
   {
      _transportationTypeHandle = value;
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

      final OmtHLAtransportationTypeHandle omtHLAhandle = (OmtHLAtransportationTypeHandle) o;

      if (!Objects.equals(_transportationTypeHandle, omtHLAhandle._transportationTypeHandle)) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return (_transportationTypeHandle != null ? _transportationTypeHandle.hashCode() : 0);
   }

   @Override
   public String toString()
   {
      return "HLAtransportationTypeHandle<" + _transportationTypeHandle + ">";
   }
}
