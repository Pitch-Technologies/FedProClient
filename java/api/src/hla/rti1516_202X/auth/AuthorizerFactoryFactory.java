/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

//File: AuthorizerFactoryFactory.java

package hla.rti1516_202X.auth;

import java.util.ServiceLoader;

public class AuthorizerFactoryFactory {
   public static AuthorizerFactory getAuthorizerFactory(String authorizerName)
   {
      ServiceLoader<AuthorizerFactory> loader = ServiceLoader.load(AuthorizerFactory.class);
      for (AuthorizerFactory authorizerFactory : loader) {
         if (authorizerFactory.getName().equals(authorizerName)) {
            return authorizerFactory;
         }
      }
      return null;
   }
}
