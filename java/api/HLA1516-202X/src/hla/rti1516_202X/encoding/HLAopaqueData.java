/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_202X.encoding;

import java.util.Iterator;

/**
 * Interface for the HLA data type HLAopaqueData.
 */
public interface HLAopaqueData extends DataElement, Iterable<Byte> {

   /**
    * Returns the number of bytes in this array.
    *
    * @return the number of bytes in this array.
    */
   int size();

   /**
    * Returns the <code>byte</code> at the specified position in this array.
    *
    * @param index index of <code>byte</code> to return
    *
    * @return <code>byte</code> at the specified index
    */
   byte get(int index);

   /**
    * Returns an iterator over the bytes in this array in a proper sequence.
    *
    * @return an iterator over the bytes in this array in a proper sequence
    */
   Iterator<Byte> iterator();

   /**
    * Returns the byte[] value of this element.
    *
    * @return byte[] value
    */
   byte[] getValue();

   /**
    * Sets the byte[] value of this element.
    *
    * @param value new value
    */
   HLAopaqueData setValue(byte[] value);

   /**
    * Decodes this element from the ByteWrapper.
    *
    * @param byteWrapper source for the decoding of this element
    * @throws DecoderException if the element can not be decoded
    */
   @Override
   HLAopaqueData decode(ByteWrapper byteWrapper)
      throws
      DecoderException;

   /**
    * Decodes this element from the byte array.
    *
    * @param bytes source for the decoding of this element
    * @throws DecoderException if the element can not be decoded
    */
   @Override
   HLAopaqueData decode(byte[] bytes)
      throws
      DecoderException;
}
