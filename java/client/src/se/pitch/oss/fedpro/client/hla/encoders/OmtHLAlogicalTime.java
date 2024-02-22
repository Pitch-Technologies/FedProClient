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

import hla.rti1516_202X.time.LogicalTime;
import hla.rti1516_202X.time.LogicalTimeFactory;
import hla.rti1516_202X.time.LogicalTimeInterval;
import hla.rti1516_202X.RTIambassador;
import hla.rti1516_202X.encoding.ByteWrapper;
import hla.rti1516_202X.encoding.DecoderException;
import hla.rti1516_202X.encoding.EncoderException;
import hla.rti1516_202X.encoding.HLAlogicalTime;
import hla.rti1516_202X.exceptions.CouldNotDecode;
import hla.rti1516_202X.exceptions.CouldNotEncode;
import hla.rti1516_202X.exceptions.FederateNotExecutionMember;
import hla.rti1516_202X.exceptions.NotConnected;

import java.util.Objects;

class OmtHLAlogicalTime<T extends LogicalTime<T, U>, U extends LogicalTimeInterval<U>> extends AbstractDataElement implements HLAlogicalTime<T, U> {
   private final RTIambassador _rtiAmbassador;

   private T _logicalTime;

   OmtHLAlogicalTime(RTIambassador rtiAmbassador)
   {
      _rtiAmbassador = rtiAmbassador;
      _logicalTime = null;
   }

   OmtHLAlogicalTime(
         RTIambassador rtiAmbassador,
         T logicalTime)
   {
      _rtiAmbassador = rtiAmbassador;
      _logicalTime = logicalTime;
   }

   public void encode(ByteWrapper byteWrapper)
   throws EncoderException
   {
      if (_logicalTime == null) {
         throw new EncoderException("Unable to encode empty HLAlogicalTime");
      }
      try {
         byteWrapper.align(4);
         byte[] elements = new byte[_logicalTime.encodedLength()];
         _logicalTime.encode(elements, 0);
         byteWrapper.putInt(elements.length);
         byteWrapper.put(elements);
      } catch (ArrayIndexOutOfBoundsException | CouldNotEncode e) {
         throw new EncoderException("Failed to encode HLAlogicalTime");
      }
   }

   public OmtHLAlogicalTime<T, U> decode(ByteWrapper byteWrapper)
   throws DecoderException
   {
      try {
         byteWrapper.align(4);
         int encodedLength = byteWrapper.getInt();
         byteWrapper.verify(encodedLength);
         byte[] buf = new byte[encodedLength];
         byteWrapper.get(buf);
         LogicalTimeFactory<T, U> timeFactory = (LogicalTimeFactory<T, U>) _rtiAmbassador.getTimeFactory();
         _logicalTime = timeFactory.decodeTime(buf, 0);
      } catch (ArrayIndexOutOfBoundsException | NotConnected | FederateNotExecutionMember | CouldNotDecode e) {
         throw new DecoderException("Failed to decode HLAlogicalTime", e);
      }
      return this;
   }

   @Override
   public HLAlogicalTime<T, U> decode(byte[] bytes)
   throws DecoderException
   {
      decode(new ByteWrapper(bytes));
      return this;
   }

   public int getEncodedLength()
   {
      return 4 + _logicalTime.encodedLength();
   }

   public int getOctetBoundary()
   {
      return 4;
   }

   public T getValue()
   {
      return _logicalTime;
   }

   public HLAlogicalTime<T, U> setValue(T value)
   {
      _logicalTime = value;
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

      final OmtHLAlogicalTime<?, ?> omtHLAlogicalTime = (OmtHLAlogicalTime<?, ?>) o;

      if (!Objects.equals(_logicalTime, omtHLAlogicalTime._logicalTime)) {
         return false;
      }

      return true;
   }

   @Override
   public int hashCode()
   {
      return (_logicalTime != null ? _logicalTime.hashCode() : 0);
   }

   @Override
   public String toString()
   {
      return "HLAlogicalTime<" + _logicalTime + ">";
   }
}
