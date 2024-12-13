/*
 * The IEEE hereby grants a general, royalty-free license to copy, distribute,
 * display and make derivative works from this material, for all purposes,
 * provided that any use of the material contains the following
 * attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
 * Should you require additional information, contact the Manager, Standards
 * Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 */

package hla.rti1516_202X;

import hla.rti1516_202X.time.LogicalTime;

import java.io.Serializable;

/**
 * Record returned by (8.16) queryLBTS and (8.18) queryMinimumNextEventTime
 */

public final class TimeQueryReturn
   implements Serializable {
   public TimeQueryReturn(boolean tiv, LogicalTime<?, ?> lt)
   {
      timeIsValid = tiv;
      time = lt;
   }

   public final boolean timeIsValid;
   public final LogicalTime<?, ?> time;

   public boolean equals(Object other)
   {
      if (this == other) {
         return true;
      }
      if (!(other instanceof TimeQueryReturn)) {
         return false;
      }

      final TimeQueryReturn timeQueryReturn = (TimeQueryReturn) other;

      if (!timeIsValid && !timeQueryReturn.timeIsValid) {
         return true;
      } else if (timeIsValid && timeQueryReturn.timeIsValid) {
         return time.equals(timeQueryReturn.time);
      } else {
         return false;
      }
   }

   public int hashCode()
   {
      return (timeIsValid ? time.hashCode() : 7);
   }

   public String toString()
   {
      return "" + timeIsValid + " " + time;
   }
}

//end TimeQueryReturn
