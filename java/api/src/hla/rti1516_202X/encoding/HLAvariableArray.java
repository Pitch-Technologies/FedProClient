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
 * Interface for the HLA data type HLAvariableArray.
 */
public interface HLAvariableArray<T extends DataElement> extends DataElement, Iterable<T> {

   /**
    * Adds an element to this variable array.
    *
    * @param dataElement element to add
    */
   HLAvariableArray<T> addElement(T dataElement);

   /**
    * Returns the number of elements in this variable array.
    *
    * @return the number of elements in this variable array
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
    * Returns an iterator for the elements in this variable array.
    *
    * @return an iterator for the elements in this variable array
    */
   Iterator<T> iterator();

   /**
    * Resize the variable array to the <code>newSize</code>.
    * Uses the <code>DataElementFactory</code> if new elements needs to be added.
    *
    * @param newSize the new size
    */
   HLAvariableArray<T> resize(int newSize);

   /**
    * Decodes this element from the ByteWrapper.
    *
    * @param byteWrapper source for the decoding of this element
    * @throws DecoderException if the element can not be decoded
    */
   @Override
   HLAvariableArray<T> decode(ByteWrapper byteWrapper)
      throws
      DecoderException;

   /**
    * Decodes this element from the byte array.
    *
    * @param bytes source for the decoding of this element
    * @throws DecoderException if the element can not be decoded
    */
   @Override
   HLAvariableArray<T> decode(byte[] bytes)
      throws
      DecoderException;
}
