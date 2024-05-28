/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_202X.encoding;

import hla.rti1516_202X.MessageRetractionHandle;

/**
 * Interface for the HLA data type HLAmessageRetractionHandle.
 */
public interface HLAmessageRetractionHandle extends DataElement {

   /**
    * Returns the MessageRetractionHandle value of this element.
    *
    * @return MessageRetractionHandle value
    */
   MessageRetractionHandle getValue();

   /**
    * Sets the MessageRetractionHandle value of this element.
    *
    * @param value new value
    */
   HLAmessageRetractionHandle setValue(MessageRetractionHandle value);

   /**
    * Decodes this element from the ByteWrapper.
    *
    * @param byteWrapper source for the decoding of this element
    * @throws DecoderException if the element can not be decoded
    */
   @Override
   HLAmessageRetractionHandle decode(ByteWrapper byteWrapper)
      throws
      DecoderException;

   /**
    * Decodes this element from the byte array.
    *
    * @param bytes source for the decoding of this element
    * @throws DecoderException if the element can not be decoded
    */
   @Override
   HLAmessageRetractionHandle decode(byte[] bytes)
      throws
      DecoderException;
}
