/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_202X.encoding;

/**
 * Interface for the HLA data type HLAextendableVariantRecord.
 * Methods encode and getEncodedLength will throw EncoderException
 * if no variant is associated with the active discriminant.
 */
public interface HLAextendableVariantRecord<T extends DataElement> extends DataElement {
   /**
    * Associates the data element for a specified discriminant.
    *
    * @param discriminant discriminant to associate data element with
    * @param dataElement  data element to associate the discriminant with
    */
   HLAextendableVariantRecord<T> setVariant(T discriminant, DataElement dataElement);

   /**
    * Sets the active discriminant.
    *
    * @param discriminant active discriminant
    */
   HLAextendableVariantRecord<T> setDiscriminant(T discriminant);

   /**
    * Returns the active discriminant.
    *
    * @return the active discriminant
    */
   T getDiscriminant();

   /**
    * Returns element associated with the active discriminant.
    * @throws EncoderException if no element is associated with
    *     the active discriminant.
    *
    * @return value
    */
   DataElement getValue()
      throws
      EncoderException;

   /**
    * Decodes this element from the ByteWrapper.
    *
    * @param byteWrapper source for the decoding of this element
    * @throws DecoderException if the element can not be decoded
    */
   @Override
   HLAextendableVariantRecord<T> decode(ByteWrapper byteWrapper)
      throws
      DecoderException;

   /**
    * Decodes this element from the byte array.
    *
    * @param bytes source for the decoding of this element
    * @throws DecoderException if the element can not be decoded
    */
   @Override
   HLAextendableVariantRecord<T> decode(byte[] bytes)
      throws
      DecoderException;

   /**
    * Compares the specified object with this HLAextendableVariantRecord for equality.
    * Returns <tt>true</tt> if the given object is also a HLAextendableVariantRecord
    * and the two HLAextendableVariantRecords have the same active discriminant and
    * the same active variant.
    *
    * @param o object to be compared for equality with this object
    * @return <tt>true</tt> if the specified object is equal to this object
    */
   boolean equals(Object o);
}
