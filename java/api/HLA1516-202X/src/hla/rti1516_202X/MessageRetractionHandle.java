/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_202X;

import java.io.Serializable;

/**
 * The user can do nothing with these but employ them as keys.
 * Implementers should provide equals, hashCode and toString
 * rather than settling for the defaults.
 */
public interface MessageRetractionHandle extends Serializable {

   /**
    * @return true if this refers to the same Message as other handle
    */
   boolean equals(Object otherMRHandle);

   /**
    * @return int. All instances that refer to the same Message should return the
    *         same hashcode.
    */
   int hashCode();

   int encodedLength();

   void encode(byte[] buffer, int offset);

   String toString();
}

//end MessageRetractionHandle


