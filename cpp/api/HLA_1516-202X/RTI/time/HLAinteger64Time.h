#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

#include <RTI/time/LogicalTime.h>
#include <RTI/time/HLAinteger64Interval.h>

// Defines interface for HLAinteger64Time which presents an integer-based
// time representation in the range 0 - 2^63-1. The encoded representation
// is HLAinteger64BE (signed, 64-bit, big-endian).

namespace rti1516_202X
{
   class HLAinteger64TimeImpl;

   class RTI_EXPORT HLAinteger64Time : public rti1516_202X::LogicalTime
   {
   public:
      // Constructors
      HLAinteger64Time ();
      HLAinteger64Time (
         Integer64 value);
      HLAinteger64Time (
         rti1516_202X::LogicalTime const & value);
      HLAinteger64Time (
         HLAinteger64Time const & value);

      // Destructor
      ~HLAinteger64Time ()
         noexcept override;

      // Basic accessors/mutators
      void setInitial () override;

      bool isInitial () const override;

      void setFinal () override;

      bool isFinal () const override;

      // Assignment
      /** @throws InvalidLogicalTime */
      rti1516_202X::HLAinteger64Time& operator= (
         rti1516_202X::LogicalTime const & value) override;

      // Operators

      /** @throws InvalidLogicalTimeInterval */
      rti1516_202X::LogicalTime& operator+= (
         rti1516_202X::LogicalTimeInterval const & addend) override;

      /** @throws InvalidLogicalTimeInterval */
      rti1516_202X::LogicalTime& operator-= (
         rti1516_202X::LogicalTimeInterval const & subtrahend) override;

      /** @throws InvalidLogicalTime */
      bool operator> (
         rti1516_202X::LogicalTime const & value) const override;

      /** @throws InvalidLogicalTime */
      bool operator< (
         rti1516_202X::LogicalTime const & value) const override;

      /** @throws InvalidLogicalTime */
      bool operator== (
         rti1516_202X::LogicalTime const & value) const override;

      /** @throws InvalidLogicalTime */
      bool operator>= (
         rti1516_202X::LogicalTime const & value) const override;

      /** @throws InvalidLogicalTime */
      bool operator<= (
         rti1516_202X::LogicalTime const & value) const override;

      // Generates an encoded value that can be used to send
      // LogicalTimes to other federates in updates or interactions
      rti1516_202X::VariableLengthData encode () const override;

      // Alternate encode for directly filling a buffer
      // Return the length of the encoded data
      /** @throws CouldNotEncode */
      size_t encode (
         void* buffer,
         size_t bufferSize) const override;

      // The length of the encoded data
      size_t encodedLength () const override;

      // Decode VariableLengthData into self
      /** @throws InternalError
          @throws CouldNotDecode */
      void decode (
         rti1516_202X::VariableLengthData const & VariableLengthData) override;

      // Alternate decode that reads directly from a buffer
      /** @throws InternalError
          @throws CouldNotDecode */
      void decode (
         const void* buffer,
         size_t bufferSize) override;

      // Diagnostic string representation of time
      std::wstring toString () const override;

      // Return the name of the implementation, as needed by
      // createFederationExecution.
      std::wstring implementationName () const override;


   public:
      //-----------------------------------------------------------------
      // Implementation functions
      //-----------------------------------------------------------------
      virtual Integer64 getTime () const;

      virtual void setTime (
         Integer64 value);

      //-----------------------------------------------------------------
      // Implementation operators
      //-----------------------------------------------------------------
      virtual HLAinteger64Time& operator= (
         const HLAinteger64Time&);

      operator Integer64 () const;

   private:

      //-----------------------------------------------------------------
      // Interval implementation must have access to time implementation
      // in order to calculate difference
      //-----------------------------------------------------------------
      friend class HLAinteger64Interval;;

      //-----------------------------------------------------------------
      // Pointer to internal implementation
      //-----------------------------------------------------------------
      HLAinteger64TimeImpl* _impl;
   };
}




