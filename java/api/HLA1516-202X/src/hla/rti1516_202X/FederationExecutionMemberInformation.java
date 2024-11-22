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
 * Set of these records returned by (4.25) reportFederationExecutionMembers
 */
public final class FederationExecutionMemberInformation
   implements Serializable {

   public FederationExecutionMemberInformation(String federateName, String federateType)
   {
      this.federateName = federateName;
      this.federateType = federateType;
   }

   public final String federateName;
   public final String federateType;

   public boolean equals(Object o)
   {
      if (this == o) {
         return true;
      }
      if (o == null || getClass() != o.getClass()) {
         return false;
      }

      FederationExecutionMemberInformation that = (FederationExecutionMemberInformation) o;

      if (!federateName.equals(that.federateName)) {
         return false;
      }
      if (!federateType.equals(that.federateType)) {
         return false;
      }

      return true;
   }

   public int hashCode()
   {
      int result;
      result = federateName.hashCode();
      result = 31 * result + federateType.hashCode();
      return result;
   }
}
