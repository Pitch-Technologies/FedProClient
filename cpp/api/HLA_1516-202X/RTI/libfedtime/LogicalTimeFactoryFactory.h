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
#include <RTI/time/LogicalTimeFactory.h>
#include <memory>
#include <string>

namespace rti1516_202X
{
   class RTI_EXPORT_FEDTIME LogicalTimeFactoryFactory
   {
   public:

      // The name is used to choose among several LogicalTimeFactories that might
      // be present in the fedtime library.  Each federation chooses its
      // implementation by passing the appropriate name to createFederationExecution.
      // If the supplied name is the empty string, a default LogicalTimeFactory is
      // returned.  If the supplied implementation name does not match any name
      // supported by the library, then a NULL pointer is returned.
      static std::unique_ptr<LogicalTimeFactory> makeLogicalTimeFactory(
            std::wstring const & implementationName);
   };
}


