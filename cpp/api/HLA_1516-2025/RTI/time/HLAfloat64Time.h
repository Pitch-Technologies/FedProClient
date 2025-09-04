#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

#include <RTI/time/LogicalTime.h>
#include <RTI/time/HLAfloat64Interval.h>

namespace rti1516_2025
{
   class HLAfloat64TimeImpl;

   class RTI_EXPORT HLAfloat64Time : public rti1516_2025::LogicalTime
   {
   public:
      // Constructors
      HLAfloat64Time ();

      HLAfloat64Time (
         double const & value);

      HLAfloat64Time (
         rti1516_2025::LogicalTime const & value);

      HLAfloat64Time (
         HLAfloat64Time const & value);

      // Destructor
      ~HLAfloat64Time ()
         noexcept override;

      // Basic accessors/mutators
      void setInitial () override;

      bool isInitial () const override;

      void setFinal () override;

      bool isFinal () const override;

      // Assignment
      /** @throws InvalidLogicalTime */
      rti1516_2025::HLAfloat64Time& operator= (
         rti1516_2025::LogicalTime const & rhs) override;

      // Operators

      /** @throws InvalidLogicalTimeInterval */
      rti1516_2025::LogicalTime& operator+= (
         rti1516_2025::LogicalTimeInterval const & addend) override;

      /** @throws InvalidLogicalTimeInterval */
      rti1516_2025::LogicalTime& operator-= (
         rti1516_2025::LogicalTimeInterval const & subtrahend) override;

      /** @throws InvalidLogicalTime */
      bool operator> (
         rti1516_2025::LogicalTime const & value) const override;

      /** @throws InvalidLogicalTime */
      bool operator< (
         rti1516_2025::LogicalTime const & value) const override;

      /** @throws InvalidLogicalTime */
      bool operator== (
         rti1516_2025::LogicalTime const & value) const override;

      /** @throws InvalidLogicalTime */
      bool operator>= (
         rti1516_2025::LogicalTime const & value) const override;

      /** @throws InvalidLogicalTime */
      bool operator<= (
         rti1516_2025::LogicalTime const & value) const override;

      // Generates an encoded value that can be used to send
      // LogicalTimes to other federates in updates or interactions
      rti1516_2025::VariableLengthData encode() const override;

      // Alternate encode for directly filling a buffer
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
         rti1516_2025::VariableLengthData const & VariableLengthData) override;

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
      std::wstring implementationName() const override;

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
      virtual HLAfloat64Time& operator= (
         const HLAfloat64Time&);

      operator double() const;

   private:

      //-----------------------------------------------------------------
      // Interval implementation must have access to time implementation
      // in order to calculate difference
      //-----------------------------------------------------------------
      friend class HLAfloat64Interval;

      //-----------------------------------------------------------------
      // Pointer to internal implementation
      //-----------------------------------------------------------------
      HLAfloat64TimeImpl* _impl;
   };
}



