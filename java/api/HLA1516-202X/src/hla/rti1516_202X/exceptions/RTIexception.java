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
 * Superclass of all exceptions thrown by the RTI.
 * All RTI exceptions must be caught or specified.
 */
public class RTIexception extends Exception {
   public RTIexception(String msg)
   {
      super(msg);
   }

   public RTIexception(String message, Throwable cause)
   {
      super(message, cause);
   }

   public String name()
   {
      return getClass().getSimpleName();
   }
}
