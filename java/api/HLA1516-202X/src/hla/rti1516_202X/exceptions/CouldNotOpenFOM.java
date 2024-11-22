/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_202X.exceptions;


/**
 * Public exception class CouldNotOpenFOM
 */
public final class CouldNotOpenFOM extends RTIexception {
   public CouldNotOpenFOM(String msg)
   {
      super(msg);
   }

   public CouldNotOpenFOM(String message, Throwable cause)
   {
      super(message, cause);
   }
}
