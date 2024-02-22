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
 * Set of these records returned by (4.25) reportFederationExecutions
 */
public final class FederationExecutionInformation
   implements Serializable {

   public FederationExecutionInformation(String federationName, String logicalTimeImplementationName)
   {
      this.federationName = federationName;
      this.logicalTimeImplementationName = logicalTimeImplementationName;
   }

   public final String federationName;
   public final String logicalTimeImplementationName;

   public boolean equals(Object o)
   {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }

      FederationExecutionInformation that = (FederationExecutionInformation) o;

      if (!federationName.equals(that.federationName)) {
         return false;
      }
      if (!logicalTimeImplementationName.equals(that.logicalTimeImplementationName)) {
         return false;
      }

      return true;
   }

   public int hashCode()
   {
      int result;
      result = federationName.hashCode();
      result = 31 * result + logicalTimeImplementationName.hashCode();
      return result;
   }
}
