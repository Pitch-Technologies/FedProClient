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

import hla.rti1516_202X.ObjectInstanceHandle;
import hla.rti1516_202X.RTIambassador;
import hla.rti1516_202X.encoding.ByteWrapper;
import hla.rti1516_202X.encoding.DecoderException;
import hla.rti1516_202X.encoding.EncoderException;
import hla.rti1516_202X.encoding.HLAobjectInstanceHandle;
import hla.rti1516_202X.exceptions.CouldNotDecode;
import hla.rti1516_202X.exceptions.FederateNotExecutionMember;
import hla.rti1516_202X.exceptions.NotConnected;
import hla.rti1516_202X.exceptions.RTIinternalError;

import java.util.Objects;

class OmtHLAobjectInstanceHandle extends AbstractDataElement implements HLAobjectInstanceHandle {
   private final RTIambassador _rtiAmbassador;

   private ObjectInstanceHandle _objectInstanceHandle;

   OmtHLAobjectInstanceHandle(RTIambassador rtiAmbassador)
   {
      _rtiAmbassador = rtiAmbassador;
      _objectInstanceHandle = null;
   }

   OmtHLAobjectInstanceHandle(
         RTIambassador rtiAmbassador,
         ObjectInstanceHandle objectInstanceHandle)
   {
      _rtiAmbassador = rtiAmbassador;
      _objectInstanceHandle = objectInstanceHandle;
   }

   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      if (_objectInstanceHandle == null) {
         throw new EncoderException("Unable to encode empty HLAobjectInstanceHandle");
      }
      try {
         byteWrapper.align(4);
         byte[] elements = new byte[_objectInstanceHandle.encodedLength()];
         _objectInstanceHandle.encode(elements, 0);
         byteWrapper.putInt(elements.length);
         byteWrapper.put(elements);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAobjectInstanceHandle");
      }
   }

   public OmtHLAobjectInstanceHandle decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(4);
         int encodedLength = byteWrapper.getInt();
         byteWrapper.verify(encodedLength);
         byte[] buf = new byte[encodedLength];
         byteWrapper.get(buf);
         _objectInstanceHandle = _rtiAmbassador.getObjectInstanceHandleFactory().decode(buf, 0);
      } catch (ArrayIndexOutOfBoundsException | NotConnected | RTIinternalError | FederateNotExecutionMember |
               CouldNotDecode e) {
         throw new DecoderException("Failed to decode HLAobjectInstanceHandle", e);
      }
      return this;
   }

   @Override
   public HLAobjectInstanceHandle decode(byte[] bytes)
   throws DecoderException
   {
      decode(new ByteWrapper(bytes));
      return this;
   }

   public int getEncodedLength()
   {
      return 4 + _objectInstanceHandle.encodedLength();
   }

   public int getOctetBoundary()
   {
      return 4;
   }

   public ObjectInstanceHandle getValue()
   {
      return _objectInstanceHandle;
   }

   public HLAobjectInstanceHandle setValue(ObjectInstanceHandle value)
   {
      _objectInstanceHandle = value;
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

      final OmtHLAobjectInstanceHandle omtHLAhandle = (OmtHLAobjectInstanceHandle) o;

      if (!Objects.equals(_objectInstanceHandle, omtHLAhandle._objectInstanceHandle)) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return (_objectInstanceHandle != null ? _objectInstanceHandle.hashCode() : 0);
   }

   @Override
   public String toString()
   {
      return "HLAobjectInstanceHandle<" + _objectInstanceHandle + ">";
   }
}
