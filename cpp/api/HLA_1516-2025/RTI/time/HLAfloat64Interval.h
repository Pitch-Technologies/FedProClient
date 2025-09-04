#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

#include <RTI/time/LogicalTimeInterval.h>

namespace rti1516_2025
{
   class HLAfloat64IntervalImpl;

   class RTI_EXPORT HLAfloat64Interval : public rti1516_2025::LogicalTimeInterval
   {
   public:

      // Constructors
      HLAfloat64Interval ();

      HLAfloat64Interval (
         double);

      HLAfloat64Interval (
         rti1516_2025::LogicalTimeInterval const &);

      HLAfloat64Interval (
         const HLAfloat64Interval&);

      // Destructor
      ~HLAfloat64Interval ()
         noexcept override;

      // Basic accessors/mutators

      void setZero () override;

      bool isZero () const override;

      void setEpsilon () override;

      bool isEpsilon () const override;

      // Operators

      /** @throws InvalidLogicalTimeInterval */
      rti1516_2025::HLAfloat64Interval& operator= (
         rti1516_2025::LogicalTimeInterval const & value) override;

       /** @throws InvalidLogicalTimeInterval */
      rti1516_2025::LogicalTimeInterval& operator+= (
          rti1516_2025::LogicalTimeInterval const & addend) override;

       /** @throws InvalidLogicalTimeInterval */
      rti1516_2025::LogicalTimeInterval& operator-= (
          rti1516_2025::LogicalTimeInterval const & subtrahend) override;

       /** @throws InvalidLogicalTimeInterval */
      bool operator> (
          rti1516_2025::LogicalTimeInterval const & value) const override;

       /** @throws InvalidLogicalTimeInterval */
      bool operator< (
          rti1516_2025::LogicalTimeInterval const & value) const override;

       /** @throws InvalidLogicalTimeInterval */
      bool operator== (
          rti1516_2025::LogicalTimeInterval const & value) const override;

       /** @throws InvalidLogicalTimeInterval */
      bool operator>= (
          rti1516_2025::LogicalTimeInterval const & value) const override;

      /** @throws InvalidLogicalTimeInterval */
      bool operator<= (
         rti1516_2025::LogicalTimeInterval const & value) const override;

      // Set self to the difference between two LogicalTimes
      /** @throws IllegalTimeArithmetic
           @throws InvalidLogicalTimeInterval */
      void setToDifference (
         rti1516_2025::LogicalTime const & minuend,
         rti1516_2025::LogicalTime const& subtrahend) override;

      // Generates an encoded value that can be used to send
      // LogicalTimeIntervals to other federates in updates or interactions
      /** @throws CouldNotEncode */
      rti1516_2025::VariableLengthData encode () const override;

      // Alternate encode for directly filling a buffer
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
         rti1516_2025::VariableLengthData const & encodedValue) override;

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
      std::wstring implementationName() const override;

   public:
      //-----------------------------------------------------------------
      // Implementation functions
      //-----------------------------------------------------------------

      virtual double getInterval () const;

      virtual void setInterval (
         double value);

      //-----------------------------------------------------------------
      // Implementation operators
      //-----------------------------------------------------------------
      virtual HLAfloat64Interval& operator= (
         const HLAfloat64Interval& value);

      operator double () const;


   private:

      //-----------------------------------------------------------------
      // Pointer to internal implementation
      //-----------------------------------------------------------------
      HLAfloat64IntervalImpl* _impl;
   };
}



