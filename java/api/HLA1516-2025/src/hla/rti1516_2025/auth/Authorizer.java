/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_2025.auth;

public interface Authorizer {

   /**
    * Called to authorize the connect service call
    */
   AuthorizationResult authorizeRtiOperation(Credentials credentials);

   /**
    * Called to authorize the create and destroy federation service calls
    */
   AuthorizationResult authorizeFederationOperation(Credentials credentials, String federationName);

   /**
    * Called to authorize the join federation service call
    */
   AuthorizationResult authorizeFederateOperation(Credentials credentials,
                                                  String federationName,
                                                  String federateName,
                                                  String federateType);

   /**
    * Return the name of the authorizer implementation
    */
   String getName();
}
