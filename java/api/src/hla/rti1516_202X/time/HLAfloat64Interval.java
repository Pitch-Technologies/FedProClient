/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_202X.time;

import hla.rti1516_202X.exceptions.IllegalTimeArithmetic;
import hla.rti1516_202X.exceptions.InvalidLogicalTimeInterval;
import hla.rti1516_202X.exceptions.CouldNotEncode;

/**
 * Interface for the interval part of the standardized time type HLAfloat64Time. 
 */
public interface HLAfloat64Interval extends LogicalTimeInterval<HLAfloat64Interval> {
   boolean isZero();

   boolean isEpsilon();

   HLAfloat64Interval add(HLAfloat64Interval addend)
      throws
      IllegalTimeArithmetic,
      InvalidLogicalTimeInterval;

   HLAfloat64Interval subtract(HLAfloat64Interval subtrahend)
      throws
      IllegalTimeArithmetic,
      InvalidLogicalTimeInterval;

   int compareTo(HLAfloat64Interval other);

   int encodedLength();

   void encode(byte[] buffer, int offset)
      throws CouldNotEncode;

   double getValue();
}