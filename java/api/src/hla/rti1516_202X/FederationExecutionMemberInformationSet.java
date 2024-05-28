/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

//File: FederateHandleSet.java

package hla.rti1516_202X;

import java.io.Serializable;
import java.util.Set;

/**
 * All Set operations are required, none are optional.
 * add() and remove() should throw IllegalArgumentException if the argument is not
 * a FederationExecutionMemberInformation.
 * addAll(), removeAll() and retainAll() should throw IllegalArgumentException if
 * the argument is not a FederationExecutionMemberInformationSet.
 */

public interface FederationExecutionMemberInformationSet
   extends Set<FederationExecutionMemberInformation>, Serializable, Cloneable {
}

//end FederationExecutionMemberInformationSet

