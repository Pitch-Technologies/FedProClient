/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

//File: HLAnoCredentials.java

package hla.rti1516_202X.auth;

public class HLAnoCredentials extends Credentials {

   public static final String HLA_NO_CREDENTIALS_TYPE = "HLAnoCredentials";

   public HLAnoCredentials()
   {
      super(HLA_NO_CREDENTIALS_TYPE, new byte[0]);
   }
}
