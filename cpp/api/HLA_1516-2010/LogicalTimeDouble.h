/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2010".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
 **********************************************************************/
/***********************************************************************
   IEEE 1516.1 High Level Architecture Interface Specification C++ API
   File: RTI/time/LogicalTimeDouble.h
***********************************************************************/

#ifndef RTI_LogicalTimeDouble_H_
#define RTI_LogicalTimeDouble_H_

#include <RTI/LogicalTime.h>
#include <RTI/encoding/EncodingConfig.h>
#include "LogicalTimeDoubleInterval.h"

namespace rti1516e
{
   class LogicalTimeDoubleImpl;

   class RTI_EXPORT LogicalTimeDouble : public rti1516e::LogicalTime
   {
   public:
      // Constructors
      LogicalTimeDouble ();

      LogicalTimeDouble (
         double const & value);

      LogicalTimeDouble (
         rti1516e::LogicalTime const & value);

      LogicalTimeDouble (
         LogicalTimeDouble const & value);

      // Destructor
      virtual ~LogicalTimeDouble ()
         throw ();

      // Basic accessors/mutators
      virtual void setInitial ();

      virtual bool isInitial () const;

      virtual void setFinal ();

      virtual bool isFinal () const;

      // Assignment
      virtual rti1516e::LogicalTime& operator= (
         rti1516e::LogicalTime const & value)
         throw (rti1516e::InvalidLogicalTime);

      // Operators

      virtual rti1516e::LogicalTime& operator+= (
         rti1516e::LogicalTimeInterval const & addend)
         throw (rti1516e::IllegalTimeArithmetic,
                rti1516e::InvalidLogicalTimeInterval);

      virtual rti1516e::LogicalTime& operator-= (
         rti1516e::LogicalTimeInterval const & subtrahend)
         throw (rti1516e::IllegalTimeArithmetic,
                rti1516e::InvalidLogicalTimeInterval);

      virtual bool operator> (
         rti1516e::LogicalTime const & value) const
         throw (rti1516e::InvalidLogicalTime);

      virtual bool operator< (
         rti1516e::LogicalTime const & value) const
         throw (rti1516e::InvalidLogicalTime);

      virtual bool operator== (
         rti1516e::LogicalTime const & value) const
         throw (rti1516e::InvalidLogicalTime);

      virtual bool operator>= (
         rti1516e::LogicalTime const & value) const
         throw (rti1516e::InvalidLogicalTime);

      virtual bool operator<= (
         rti1516e::LogicalTime const & value) const
         throw (rti1516e::InvalidLogicalTime);

      // Generates an encoded value that can be used to send
      // LogicalTimes to other federates in updates or interactions
      virtual rti1516e::VariableLengthData encode() const;

      // Alternate encode for directly filling a buffer
      virtual size_t encode (
         void* buffer,
         size_t bufferSize) const
         throw (rti1516e::CouldNotEncode);

      // The length of the encoded data
      virtual size_t encodedLength () const;

      // Decode VariableLengthData into self
      virtual void decode (
         rti1516e::VariableLengthData const & VariableLengthData)
         throw (rti1516e::InternalError,
                rti1516e::CouldNotDecode);

      // Alternate decode that reads directly from a buffer
      virtual void decode (
         void* buffer,
         size_t bufferSize)
         throw (rti1516e::InternalError,
                rti1516e::CouldNotDecode);

      // Diagnostic string representation of time
      virtual std::wstring toString () const;

      // Return the name of the Implementation, as needed by
      // createFederationExecution.
      virtual std::wstring implementationName() const;

   public:
      //-----------------------------------------------------------------
      // Implementation methods
      //-----------------------------------------------------------------
      virtual double getTime () const;

      virtual void setTime (
         double value);

      //-----------------------------------------------------------------
      // Implementation operators
      //-----------------------------------------------------------------
      virtual LogicalTimeDouble& operator= (
         const LogicalTimeDouble&)
         throw (rti1516e::InvalidLogicalTime);

      operator double() const;

   private:

      //-----------------------------------------------------------------
      // Interval implementation must have access to time implementation
      // in order to calculate difference
      //-----------------------------------------------------------------
      friend class LogicalTimeDoubleInterval;

      //-----------------------------------------------------------------
      // Implementation-specific
      //-----------------------------------------------------------------
      Integer64 _time;

      long getSeconds() const;

      int getMicros() const;
   };
}

#endif // RTI_LogicalTimeDouble_H_

