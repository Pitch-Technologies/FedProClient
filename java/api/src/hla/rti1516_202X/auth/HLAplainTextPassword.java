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
   public static final String HLA_PLAIN_TEXT_PASSWORD_TYPE = "HLAplainTextPassword";

   public HLAplainTextPassword(String password)
   {
      super(HLA_PLAIN_TEXT_PASSWORD_TYPE, encode(password));
   }

   public HLAplainTextPassword(byte[] data)
   {
      super(HLA_PLAIN_TEXT_PASSWORD_TYPE, data);
   }

   @SuppressWarnings("PointlessBitwiseExpression")
   private static byte[] encode(String password)
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

   @SuppressWarnings("PointlessBitwiseExpression")
   public String decode()
   {
      try {
         byte[] passwordBytes = getData();
         ByteWrapper byteWrapper = new ByteWrapper(passwordBytes);
         byteWrapper.align(4);
         int length = byteWrapper.getInt();
         byteWrapper.verify(length * 2);
         char[] chars = new char[length];
         for (int i = 0; i < length; i++) {
            int hi = byteWrapper.get();
            int lo = byteWrapper.get();
            chars[i] = (char) ((hi << 8) + (lo << 0));
         }
         return new String(chars);
      } catch (ArrayIndexOutOfBoundsException e) {
         throw new RuntimeException("Failed to decode HLAunicodeString", e);
      }
   }
}
