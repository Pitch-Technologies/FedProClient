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

namespace rti1516_2025
{
   class AuthorizerFactory;

   // The AuthorizerFactoryFactory implementation resides in a separate
   // library named libauthorizer. The libauthorizer library may provide custom
   // authorizers.
   class RTI_EXPORT_AUTHORIZER AuthorizerFactoryFactory
   {
   public:
      virtual ~AuthorizerFactoryFactory() = default;

      // The name is used to choose among several AuthorizerFactories that might
      // be present in the authorizer library. The name shall come from the RTI
      // Runtime Initialization Data.
      // If the supplied implementation name does not match any name
      // supported by the library, then the call shall be delegated to
      // HLAauthorizerFactoryFactory::getAuthorizerFactory.
      static std::unique_ptr<AuthorizerFactory> getAuthorizerFactory(
            std::wstring const & authorizerName);
   };
}


