#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

namespace rti1516_2025
{
   class LogicalTime;
   class LogicalTimeInterval;
   class VariableLengthData;
}

#include <RTI/SpecificConfig.h>
#include <RTI/Exception.h>
#include <RTI/time/LogicalTimeFactory.h>
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

namespace rti1516_2025
{
   class RTI_EXPORT HLAlogicalTimeFactoryFactory
   {
   public:

      // Provides a factory for the standard logical time types HLAfloat64Time
      // and HLAinteger64Time. The RTI reference time library's
      // LogicalTimeFactoryFactory should just forward requests to here.
      static std::unique_ptr< LogicalTimeFactory > makeLogicalTimeFactory (
         std::wstring const & implementationName);
   };
}
