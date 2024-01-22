/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

//File: Credentials.java

package hla.rti1516_202X.auth;

import java.io.Serializable;

public class Credentials implements Serializable {
   private final String _type;
   private final byte[] _data;

   public Credentials(String type, byte[] data)
   {
      _type = type;
      _data = data;
   }

   /**
    * Returns the type of these credentials. Predefined types are:
    * <li>HLAplainTextPassword</li>
    * <li>HLAnoCredentials</li>
    * User-defined authorizer plugins can support new types of credentials.
    * @return type of these credentials
    */
   public String getType() {
      return _type;
   }

   public byte[] getData() {
      return _data;
   }
}
