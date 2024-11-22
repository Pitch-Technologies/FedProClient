#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

namespace rti1516_202X
{
   class LogicalTime;
   class LogicalTimeInterval;
   class VariableLengthData;
}

#include <RTI/SpecificConfig.h>
#include <RTI/Exception.h>
#include <memory>
#include <string>

// LogicalTimeFactory is used by the RTI to construct instances of classes
// derived from LogicalTime and LogicalTimeInterval.  A federation is responsible
// for providing a fedtime library that includes one or more subclasses
// of LogicalTime and LogicalTimeInterval, one or more subclasses of LogicalTimeFactory
// (which is used to create instances of those LogicalTime and LogicalTimeInterval
// subclasses), and a single implementation of
// LogicalTimeFactoryFactory::makeLogicalTimeFactory.  This static function should
// choose a LogicalTimeFactory based on the string identifier passed as an argument,
// and return an instance of that kind of factory.  The RTI will call this function to
// obtain a LogicalTimeFactory for a federation, and then will use that factory to create
// any instances of LogicalTime or LogicalTimeInterval that it needs.

// All RTIs shall implement a reference time library with time types named HLAinteger64Time
// and HLAfloat64Time.  The interfaces for these types shall be found in the time subdirectory.

namespace rti1516_202X
{
   class RTI_EXPORT LogicalTimeFactory
   {
   public:
      virtual ~LogicalTimeFactory ()
         noexcept = 0;

      // Return a LogicalTime with a value of "initial"
      /**
       * @throws InternalError
       */
      virtual std::unique_ptr< LogicalTime > makeInitial() = 0;

      // Return a LogicalTime with a value of "final"
      /**
       * @throws InternalError
       */
      virtual std::unique_ptr< LogicalTime > makeFinal() = 0;

      // Return a LogicalTimeInterval with a value of "zero"
      /**
       * @throws InternalError
       */
      virtual std::unique_ptr< LogicalTimeInterval > makeZero() = 0;

      // Return a LogicalTimeInterval with a value of "epsilon"
      /**
       * @throws InternalError
       */
      virtual std::unique_ptr< LogicalTimeInterval > makeEpsilon() = 0;

      // LogicalTime decode from an encoded LogicalTime
      /**
       * @throws InternalError
       * @throws CouldNotDecode
       */
      virtual std::unique_ptr< LogicalTime > decodeLogicalTime (
         VariableLengthData const & encodedLogicalTime) = 0;

      // Alternate LogicalTime decode that reads directly from a buffer
      /**
       * @throws InternalError
       * @throws CouldNotDecode
       */
      virtual std::unique_ptr< LogicalTime > decodeLogicalTime (
         const void* buffer,
         size_t bufferSize) = 0;

      // LogicalTimeInterval decode from an encoded LogicalTimeInterval
      /**
       * @throws InternalError
       * @throws CouldNotDecode
       */
      virtual std::unique_ptr< LogicalTimeInterval > decodeLogicalTimeInterval (
         VariableLengthData const & encodedValue) = 0;

      // Alternate LogicalTimeInterval decode that reads directly from a buffer
      /**
       * @throws InternalError
       * @throws CouldNotDecode
       */
      virtual std::unique_ptr< LogicalTimeInterval > decodeLogicalTimeInterval (
         const void* buffer,
         size_t bufferSize) = 0;

      // Return the name of the logical time implementation
      virtual std::wstring getName () const = 0;
   };
}

