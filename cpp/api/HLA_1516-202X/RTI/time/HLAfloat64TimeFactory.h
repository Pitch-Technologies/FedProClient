#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

#include <RTI/time/LogicalTimeFactory.h>

// Defines interface for HLAfloat64TimeFactory which presents a
// floating point-based time/interval representation in the range 0 - 2^63-1.

namespace rti1516_202X
{
   const std::wstring HLAfloat64TimeName(L"HLAfloat64Time");

   class HLAfloat64Time;
   class HLAfloat64Interval;

   class RTI_EXPORT HLAfloat64TimeFactory : public rti1516_202X::LogicalTimeFactory
   {
   public:
      HLAfloat64TimeFactory ();

      ~HLAfloat64TimeFactory ()
         noexcept override;

      // Return a LogicalTime with the given value
      /** @throws InternalError */
      virtual std::unique_ptr< HLAfloat64Time > makeLogicalTime (
         double value);

      // Return a LogicalTime with a value of "initial"
      /** @throws InternalError */
      std::unique_ptr< LogicalTime > makeInitial() override;

      // Return a LogicalTime with a value of "final"
      /** @throws InternalError */
      std::unique_ptr< LogicalTime > makeFinal() override;

      // Return a LogicalTimeInterval with the given value
      /** @throws InternalError */
      virtual std::unique_ptr< HLAfloat64Interval > makeLogicalTimeInterval (
         double value);

      // Return a LogicalTimeInterval with a value of "zero"
      /** @throws InternalError */
      std::unique_ptr< LogicalTimeInterval > makeZero() override;

      // Return a LogicalTimeInterval with a value of "epsilon"
      /** @throws InternalError */
      std::unique_ptr< LogicalTimeInterval > makeEpsilon() override;

      // LogicalTime decode from an encoded LogicalTime
      /** @throws InternalError
          @throws CouldNotDecode */
      std::unique_ptr< LogicalTime > decodeLogicalTime (
         VariableLengthData const & encodedLogicalTime) override;

      // Alternate LogicalTime decode that reads directly from a buffer
      /** @throws InternalError
          @throws CouldNotDecode */
      std::unique_ptr< LogicalTime > decodeLogicalTime (
         const void* buffer,
         size_t bufferSize) override;

      // LogicalTimeInterval decode from an encoded LogicalTimeInterval
      /** @throws InternalError
          @throws CouldNotDecode */
      std::unique_ptr< LogicalTimeInterval > decodeLogicalTimeInterval (
         VariableLengthData const & encodedValue) override;

      // Alternate LogicalTimeInterval decode that reads directly from a buffer
      /** @throws InternalError
          @throws CouldNotDecode */
      std::unique_ptr< LogicalTimeInterval > decodeLogicalTimeInterval (
         const void* buffer,
         size_t bufferSize) override;

      std::wstring getName () const override;
   };
}



