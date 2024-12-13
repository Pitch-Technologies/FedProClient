/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_202X;

import hla.rti1516_202X.exceptions.CouldNotDecode;
import hla.rti1516_202X.exceptions.FederateNotExecutionMember;
import hla.rti1516_202X.exceptions.NotConnected;
import hla.rti1516_202X.exceptions.RTIinternalError;

import java.io.Serializable;

/**
 * The factory is used only (outside RTI) to create ParameterHandle
 * received as an attribute value or parameter value.
 */

public interface MessageRetractionHandleFactory extends Serializable {
   MessageRetractionHandle decode(byte[] buffer, int offset)
      throws CouldNotDecode, FederateNotExecutionMember, NotConnected, RTIinternalError;
}
