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
 * Interface for the HLA data type HLAfixedArray.
 */
public interface HLAfixedArray<T extends DataElement> extends DataElement, Iterable<T> {

   /**
    * Returns the number of elements in this fixed array.
    *
    * @return the number of elements in this fixed array
    */
   int size();

   /**
    * Returns the element at the specified <code>index</code>.
    *
    * @param index index of element to get
    *
    * @return the element at the specified <code>index</code>
    */
   T get(int index);

   /**
    * Returns an iterator for the elements in this fixed array.
    *
    * @return an iterator for the elements in this fixed array
    */
   Iterator<T> iterator();

   /**
    * Decodes this element from the ByteWrapper.
    *
    * @param byteWrapper source for the decoding of this element
    * @throws DecoderException if the element can not be decoded
    */
   @Override
   HLAfixedArray<T> decode(ByteWrapper byteWrapper)
      throws
      DecoderException;

   /**
    * Decodes this element from the byte array.
    *
    * @param bytes source for the decoding of this element
    * @throws DecoderException if the element can not be decoded
    */
   @Override
   HLAfixedArray<T> decode(byte[] bytes)
      throws
      DecoderException;
}
