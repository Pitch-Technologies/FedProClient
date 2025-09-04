/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_2025;

import hla.rti1516_2025.exceptions.CouldNotDecode;
import hla.rti1516_2025.exceptions.FederateNotExecutionMember;
import hla.rti1516_2025.exceptions.NotConnected;
import hla.rti1516_2025.exceptions.RTIinternalError;

import java.io.Serializable;

/**
 * The factory is used only (outside RTI) to create DimensionHandle
 * received as an attribute value or parameter value.
 */

public interface DimensionHandleFactory extends Serializable {
   DimensionHandle decode(byte[] buffer, int offset)
      throws CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError;
}
