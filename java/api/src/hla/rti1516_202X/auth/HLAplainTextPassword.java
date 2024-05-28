/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

//File: HLAplainTextPassword.java

package hla.rti1516_202X.auth;

import hla.rti1516_202X.encoding.ByteWrapper;

public class HLAplainTextPassword extends Credentials {
   public static final String HLAplainTextPasswordType = "HLAplainTextPassword";

   public HLAplainTextPassword(String password)
   {
      super("HLAplainTextPassword", encodePassword(password));
   }

   @SuppressWarnings("PointlessBitwiseExpression")
   private static byte[] encodePassword(String password)
   {
      try {
         int len = password.length();
         ByteWrapper byteWrapper = new ByteWrapper(4 + len * 2);
         byteWrapper.align(4);
         byteWrapper.putInt(len);
         for (int i = 0; i < len; i++) {
            int v = password.charAt(i);
            byteWrapper.put((v >>> 8) & 0xFF);
            byteWrapper.put((v >>> 0) & 0xFF);
         }
         return byteWrapper.array();
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new RuntimeException("Failed to encode HLAunicodeString");
      }
   }
}
