#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

#include <RTI/time/LogicalTimeFactory.h>
#include <RTI/encoding/EncodingConfig.h>

// Defines interface for HLAinteger64TimeFactory which presents an integer-based
// time /interval representation in the range 0 - 2^63-1.

namespace rti1516_2025
{
   const std::wstring HLAinteger64TimeName(L"HLAinteger64Time");

   class HLAinteger64Time;
   class HLAinteger64Interval;

   class RTI_EXPORT HLAinteger64TimeFactory : public rti1516_2025::LogicalTimeFactory
   {
   public:
      HLAinteger64TimeFactory ();

      ~HLAinteger64TimeFactory ()
         noexcept override;

      // Return a LogicalTime with the given value
      /** @throws InternalError */
      virtual std::unique_ptr< HLAinteger64Time > makeLogicalTime (
          Integer64 value);

      // Return a LogicalTime with a value of "initial"
      /** @throws InternalError */
      std::unique_ptr< LogicalTime > makeInitial() override;

      // Return a LogicalTime with a value of "final"
      /** @throws InternalError */
      std::unique_ptr< LogicalTime > makeFinal() override;

      // Return a LogicalTimeInterval with the given value
      /** @throws InternalError */
      virtual std::unique_ptr< HLAinteger64Interval > makeLogicalTimeInterval (
         Integer64 value);

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



