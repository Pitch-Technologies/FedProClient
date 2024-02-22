/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_202X.encoding;

import hla.rti1516_202X.ParameterHandle;

/**
 * Interface for the HLA data type HLAparameterHandle.
 */
public interface HLAparameterHandle extends DataElement {

   /**
    * Returns the ParameterHandle value of this element.
    *
    * @return ParameterHandle value
    */
   ParameterHandle getValue();

   /**
    * Sets the ParameterHandle value of this element.
    *
    * @param value new value
    */
   HLAparameterHandle setValue(ParameterHandle value);

   /**
    * Decodes this element from the ByteWrapper.
    *
    * @param byteWrapper source for the decoding of this element
    * @throws DecoderException if the element can not be decoded
    */
   @Override
   HLAparameterHandle decode(ByteWrapper byteWrapper)
      throws
      DecoderException;

   /**
    * Decodes this element from the byte array.
    *
    * @param bytes source for the decoding of this element
    * @throws DecoderException if the element can not be decoded
    */
   @Override
   HLAparameterHandle decode(byte[] bytes)
      throws
      DecoderException;
}
