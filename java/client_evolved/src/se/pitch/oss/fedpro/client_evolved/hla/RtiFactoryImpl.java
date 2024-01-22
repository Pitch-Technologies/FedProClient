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

import hla.rti1516e.RTIambassador;
import hla.rti1516e.RtiFactory;
import hla.rti1516e.encoding.EncoderFactory;
import hla.rti1516e.exceptions.RTIinternalError;
import se.pitch.oss.fedpro.client_evolved.hla.encoders.OmtEncoderFactory;

/**
 * Implementation of factory class.
 */
public class RtiFactoryImpl implements RtiFactory {
   public RTIambassador getRtiAmbassador()
   throws RTIinternalError
   {
      return new RTIambassadorClientAdapter();
   }

   public EncoderFactory getEncoderFactory()
   throws RTIinternalError
   {
      return OmtEncoderFactory.getInstance();
   }

   public String rtiName()
   {
      return "Federate Protocol";
   }

   public String rtiVersion()
   {
      return "1.0";
   }
}
