#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

// The classes associated with logical time allow a federation to provide
// their own representation for logical time and logical time interval. The
// federation is responsible to inherit from the abstract classes declared
// below. The encoded time classes are used to hold the arbitrary bit
// representation of the logical time and logical time intervals.

namespace rti1516_2025
{
   class LogicalTime;
}

#include <RTI/SpecificConfig.h>
#include <RTI/Exception.h>
#include <string>
#include <ostream>
#include <RTI/VariableLengthData.h>

namespace rti1516_2025
{
   class RTI_EXPORT LogicalTimeInterval
   {
   public:
      // Destructor
      virtual ~LogicalTimeInterval ()
         noexcept = default;

      // Basic accessors/mutators

      virtual void setZero () = 0;

      virtual bool isZero () const = 0;

      virtual void setEpsilon () = 0;

      virtual bool isEpsilon () const = 0;

      // Operators

      /**
       * @throws InvalidLogicalTimeInterval
       */
      virtual LogicalTimeInterval & operator= (
         LogicalTimeInterval const & value) = 0;

      /**
       * @throws InvalidLogicalTimeInterval
       */
      virtual LogicalTimeInterval & operator+= (
         LogicalTimeInterval const & addend) = 0;

      /**
       * @throws InvalidLogicalTimeInterval
       */
      virtual LogicalTimeInterval & operator-= (
         LogicalTimeInterval const & subtrahend) = 0;

      /**
       * @throws InvalidLogicalTimeInterval
       */
      virtual bool operator> (
         LogicalTimeInterval const & value) const = 0;

      /**
       * @throws InvalidLogicalTimeInterval
       */
      virtual bool operator< (
         LogicalTimeInterval const & value) const = 0;

      /**
       * @throws InvalidLogicalTimeInterval
       */
      virtual bool operator== (
         LogicalTimeInterval const & value) const = 0;

      /**
       * @throws InvalidLogicalTimeInterval
       */
      virtual bool operator>= (
         LogicalTimeInterval const & value) const = 0;

      /**
       * @throws InvalidLogicalTimeInterval
       */
      virtual bool operator<= (
         LogicalTimeInterval const & value) const = 0;

      /**
       * Set self to the difference between two LogicalTimes
       * @throws IllegalTimeArithmetic,
           InvalidLogicalTime
       */
      virtual void setToDifference (
         LogicalTime const & minuend,
         LogicalTime const& subtrahend) = 0;

      // Generates an encoded value that can be used to send
      // LogicalTimeIntervals to other federates in updates or interactions
      virtual VariableLengthData encode () const = 0;

      /**
       * Alternate encode for directly filling a buffer
       * @throws CouldNotEncode
       */
      virtual size_t encode (
         void* buffer,
         size_t bufferSize) const = 0;

      // The length of the encoded data
      virtual size_t encodedLength () const = 0;

      /**
       * Decode encodedValue into self
       * @throws InternalError
       * @throws CouldNotDecode
       */
      virtual void decode (
         VariableLengthData const & encodedValue) = 0;

      /**
       * Alternate decode that reads directly from a buffer
       * @throws InternalError
       * @throws CouldNotDecode
       */
      virtual void decode (
         const void* buffer,
         size_t bufferSize) = 0;

      // Diagnostic string representation of time interval
      virtual std::wstring toString () const = 0;

      // Return the name of the implementation, as needed by
      // createFederationExecution.
      virtual std::wstring implementationName () const = 0;
   };

   // Output operator for LogicalTimeInterval
   std::wostream RTI_EXPORT & operator << (
      std::wostream &,
      LogicalTimeInterval const &);
}

