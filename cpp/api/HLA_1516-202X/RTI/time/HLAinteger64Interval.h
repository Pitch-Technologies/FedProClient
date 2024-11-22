#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

#include <RTI/time/LogicalTimeInterval.h>
#include <RTI/encoding/EncodingConfig.h>

// Defines interface for HLAinteger64Time which presents an integer-based
// interval representation in the range 0 - 2^63-1. The encoded representation
// is HLAinteger64BE (signed, 64-bit, big-endian).

namespace rti1516_202X
{
   class HLAinteger64IntervalImpl;

   class RTI_EXPORT HLAinteger64Interval : public rti1516_202X::LogicalTimeInterval
   {
   public:

      // Constructors
      HLAinteger64Interval ();

      HLAinteger64Interval (
         HLAinteger64Interval const & rhs);

      HLAinteger64Interval (
         rti1516_202X::LogicalTimeInterval const & rhs);

      HLAinteger64Interval (
         Integer64);

      // Destructor
      ~HLAinteger64Interval()
         noexcept override;

      // Basic accessors/mutators

      void setZero () override;

      bool isZero () const override;

      void setEpsilon () override;

      bool isEpsilon () const override;

      // Operators

      /** @throws InvalidLogicalTimeInterval */
      rti1516_202X::HLAinteger64Interval& operator= (
         rti1516_202X::LogicalTimeInterval const & value) override;

       /** @throws InvalidLogicalTimeInterval */
      rti1516_202X::LogicalTimeInterval& operator+= (
          rti1516_202X::LogicalTimeInterval const & addend) override;

       /** @throws InvalidLogicalTimeInterval */
      rti1516_202X::LogicalTimeInterval& operator-= (
          rti1516_202X::LogicalTimeInterval const & subtrahend) override;

       /** @throws InvalidLogicalTimeInterval */
      bool operator> (
          rti1516_202X::LogicalTimeInterval const & value) const override;

       /** @throws InvalidLogicalTimeInterval */
      bool operator< (
          rti1516_202X::LogicalTimeInterval const & value) const override;

       /** @throws InvalidLogicalTimeInterval */
      bool operator== (
          rti1516_202X::LogicalTimeInterval const & value) const override;

       /** @throws InvalidLogicalTimeInterval */
      bool operator>= (
          rti1516_202X::LogicalTimeInterval const & value) const override;

      /** @throws InvalidLogicalTimeInterval */
      bool operator<= (
         rti1516_202X::LogicalTimeInterval const & value) const override;

      // Set self to the difference between two LogicalTimes
      /** @throws IllegalTimeArithmetic
           @throws InvalidLogicalTimeInterval */
      void setToDifference (
         rti1516_202X::LogicalTime const & minuend,
         rti1516_202X::LogicalTime const& subtrahend) override;

      // Generates an encoded value that can be used to send
      // LogicalTimeIntervals to other federates in updates or interactions
      /** @throws CouldNotEncode */
      rti1516_202X::VariableLengthData encode () const override;

      // Alternate encode for directly filling a buffer
      // Return the length of encoded data
      /** @throws CouldNotEncode */
      size_t encode (
         void* buffer,
         size_t bufferSize) const override;

      // The length of the encoded data
      size_t encodedLength () const override;

      // Decode encodedValue into self
      /** @throws InternalError
          @throws CouldNotDecode */
      void decode (
         rti1516_202X::VariableLengthData const & encodedValue) override;

      // Decode encodedValue into self
      // Alternate decode that reads directly from a buffer
      /** @throws InternalError
          @throws CouldNotDecode */
      void decode (
         const void* buffer,
         size_t bufferSize) override;

      // Diagnostic string representation of time
      std::wstring toString () const override;

      // Return the name of the Implementation, as needed by
      // createFederationExecution.
      std::wstring implementationName () const override;

   public:

      //-----------------------------------------------------------------
      // Implementation functions
      //-----------------------------------------------------------------

      virtual Integer64 getInterval () const;

      virtual void setInterval (
         Integer64 value);

      //-----------------------------------------------------------------
      // Implementation operators
      //-----------------------------------------------------------------
      virtual HLAinteger64Interval& operator= (
         const HLAinteger64Interval& value);

      operator Integer64 () const;

   private:

      //-----------------------------------------------------------------
      // Pointer to internal Implementation
      //-----------------------------------------------------------------
      HLAinteger64IntervalImpl* _impl;
   };
}



