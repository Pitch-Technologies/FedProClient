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

import hla.rti1516_202X.InteractionClassHandle;
import hla.rti1516_202X.RTIambassador;
import hla.rti1516_202X.encoding.ByteWrapper;
import hla.rti1516_202X.encoding.DecoderException;
import hla.rti1516_202X.encoding.EncoderException;
import hla.rti1516_202X.encoding.HLAinteractionClassHandle;
import hla.rti1516_202X.exceptions.CouldNotDecode;
import hla.rti1516_202X.exceptions.FederateNotExecutionMember;
import hla.rti1516_202X.exceptions.NotConnected;
import hla.rti1516_202X.exceptions.RTIinternalError;

import java.util.Objects;

class OmtHLAinteractionClassHandle extends AbstractDataElement implements HLAinteractionClassHandle {
   private final RTIambassador _rtiAmbassador;

   private InteractionClassHandle _interactionClassHandle;

   OmtHLAinteractionClassHandle(RTIambassador rtiAmbassador)
   {
      _rtiAmbassador = rtiAmbassador;
      _interactionClassHandle = null;
   }

   OmtHLAinteractionClassHandle(
         RTIambassador rtiAmbassador,
         InteractionClassHandle interactionClassHandle)
   {
      _rtiAmbassador = rtiAmbassador;
      _interactionClassHandle = interactionClassHandle;
   }

   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      if (_interactionClassHandle == null) {
         throw new EncoderException("Unable to encode empty HLAinteractionClassHandle");
      }
      try {
         byteWrapper.align(4);
         byte[] elements = new byte[_interactionClassHandle.encodedLength()];
         _interactionClassHandle.encode(elements, 0);
         byteWrapper.putInt(elements.length);
         byteWrapper.put(elements);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAinteractionClassHandle");
      }
   }

   public OmtHLAinteractionClassHandle decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(4);
         int encodedLength = byteWrapper.getInt();
         byteWrapper.verify(encodedLength);
         byte[] buf = new byte[encodedLength];
         byteWrapper.get(buf);
         _interactionClassHandle = _rtiAmbassador.getInteractionClassHandleFactory().decode(buf, 0);
      } catch (ArrayIndexOutOfBoundsException | NotConnected | RTIinternalError | FederateNotExecutionMember |
               CouldNotDecode e) {
         throw new DecoderException("Failed to decode HLAinteractionClassHandle", e);
      }
      return this;
   }

   @Override
   public HLAinteractionClassHandle decode(byte[] bytes)
   throws DecoderException
   {
      decode(new ByteWrapper(bytes));
      return this;
   }

   public int getEncodedLength()
   {
      return 4 + _interactionClassHandle.encodedLength();
   }

   public int getOctetBoundary()
   {
      return 4;
   }

   public InteractionClassHandle getValue()
   {
      return _interactionClassHandle;
   }

   public HLAinteractionClassHandle setValue(InteractionClassHandle value)
   {
      _interactionClassHandle = value;
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

      final OmtHLAinteractionClassHandle omtHLAhandle = (OmtHLAinteractionClassHandle) o;

      if (!Objects.equals(_interactionClassHandle, omtHLAhandle._interactionClassHandle)) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return (_interactionClassHandle != null ? _interactionClassHandle.hashCode() : 0);
   }

   @Override
   public String toString()
   {
      return "HLAinteractionClassHandle<" + _interactionClassHandle + ">";
   }
}
