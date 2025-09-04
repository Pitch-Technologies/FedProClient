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

   class RTI_EXPORT HLAauthorizerFactoryFactory
   {
   public:
      // Provides a factory for the standard authorizer type
      // HLAauthorizer. The RTI authorizer library's
      // AuthorizerFactoryFactory should just forward requests to here.
      // If the supplied implementation name does not match any name
      // supported by the library, then a nullptr is returned.
      static std::unique_ptr<AuthorizerFactory> getAuthorizerFactory(
            std::wstring const & authorizerName);
   };
}


