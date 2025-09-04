#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

#include <RTI/SpecificConfig.h>
#include <memory>
#include <string>

// AuthorizerFactory is used by the RTI to construct instances of classes
// derived from Authorizer.
// An authorizer library includes one or more subclasses of Authorizer,
// one or more subclasses of AuthorizerFactory (which is used to create
// instances of those Authorizer subclasses), and a single implementation
// of AuthorizerFactoryFactory::makeAuthorizerFactory.
// This static function should choose a AuthorizerFactory based on the string
// identifier passed as an argument, and return an instance of that kind of
// factory.  The RTI will call this function to obtain an AuthorizerFactory,
// and then will use that factory to create any instances of Authorizer that
// it needs.

// All RTIs shall implement a reference authorizer library with an authorizer
// named HLAauthorizer. The interfaces for these types shall
// be found in the auth subdirectory.

namespace rti1516_2025
{
   class Authorizer;

   const std::wstring HLAauthorizerName(L"HLAauthorizer");

   class RTI_EXPORT AuthorizerFactory
   {
   public:
      virtual ~AuthorizerFactory() noexcept = default;

      // Return an Authorizer
      virtual std::unique_ptr<Authorizer> getAuthorizer() = 0;

      // Return the name of the authorizer implementation
      virtual std::wstring getName() const = 0;
   };
}


