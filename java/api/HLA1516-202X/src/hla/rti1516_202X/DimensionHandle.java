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
 * Type-safe handle for a dimension. Generally these are created by the
 * RTI and passed to the user.
 */

public interface DimensionHandle extends Serializable {

   /**
    * @return true if this refers to the same dimension as other handle
    */
   boolean equals(Object otherDimensionHandle);

   /**
    * @return int. All instances that refer to the same dimension should return the
    *         same hascode.
    */
   int hashCode();

   int encodedLength();

   void encode(byte[] buffer, int offset);

   String toString();

}

//end DimensionHandle
