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

import hla.rti1516_202X.MessageRetractionHandle;
import hla.rti1516_202X.RTIambassador;
import hla.rti1516_202X.encoding.ByteWrapper;
import hla.rti1516_202X.encoding.DecoderException;
import hla.rti1516_202X.encoding.EncoderException;
import hla.rti1516_202X.encoding.HLAmessageRetractionHandle;
import hla.rti1516_202X.exceptions.CouldNotDecode;
import hla.rti1516_202X.exceptions.FederateNotExecutionMember;
import hla.rti1516_202X.exceptions.NotConnected;
import hla.rti1516_202X.exceptions.RTIinternalError;

import java.util.Objects;

class OmtHLAmessageRetractionHandle extends AbstractDataElement implements HLAmessageRetractionHandle {
   private final RTIambassador _rtiAmbassador;

   private MessageRetractionHandle _messageRetractionHandle;

   OmtHLAmessageRetractionHandle(RTIambassador rtiAmbassador)
   {
      _rtiAmbassador = rtiAmbassador;
      _messageRetractionHandle = null;
   }

   OmtHLAmessageRetractionHandle(
         RTIambassador rtiAmbassador,
         MessageRetractionHandle messageRetractionHandle)
   {
      _rtiAmbassador = rtiAmbassador;
      _messageRetractionHandle = messageRetractionHandle;
   }

   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      if (_messageRetractionHandle == null) {
         throw new EncoderException("Unable to encode empty HLAmessageRetractionHandle");
      }
      try {
         byteWrapper.align(4);
         byte[] elements = new byte[_messageRetractionHandle.encodedLength()];
         _messageRetractionHandle.encode(elements, 0);
         byteWrapper.putInt(elements.length);
         byteWrapper.put(elements);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new EncoderException("Failed to encode HLAmessageRetractionHandle");
      }
   }

   public HLAmessageRetractionHandle decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(4);
         int encodedLength = byteWrapper.getInt();
         byteWrapper.verify(encodedLength);
         byte[] buf = new byte[encodedLength];
         byteWrapper.get(buf);
         _messageRetractionHandle = _rtiAmbassador.getMessageRetractionHandleFactory().decode(buf, 0);
      } catch (ArrayIndexOutOfBoundsException | NotConnected | RTIinternalError | FederateNotExecutionMember |
               CouldNotDecode e) {
         throw new DecoderException("Failed to decode HLAmessageRetractionHandle", e);
      }
      return this;
   }

   @Override
   public HLAmessageRetractionHandle decode(byte[] bytes)
   throws DecoderException
   {
      decode(new ByteWrapper(bytes));
      return this;
   }

   public int getEncodedLength()
   {
      return 4 + _messageRetractionHandle.encodedLength();
   }

   public int getOctetBoundary()
   {
      return 4;
   }

   public MessageRetractionHandle getValue()
   {
      return _messageRetractionHandle;
   }

   public HLAmessageRetractionHandle setValue(MessageRetractionHandle value)
   {
      _messageRetractionHandle = value;
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

      final OmtHLAmessageRetractionHandle omtHLAhandle = (OmtHLAmessageRetractionHandle) o;

      if (!Objects.equals(_messageRetractionHandle, omtHLAhandle._messageRetractionHandle)) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return (_messageRetractionHandle != null ? _messageRetractionHandle.hashCode() : 0);
   }

   @Override
   public String toString()
   {
      return "HLAmessageRetractionHandle<" + _messageRetractionHandle + ">";
   }
}
