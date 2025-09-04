#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

// The classes associated with authorization allow a federation to provide their
// own implementation of authorization. The federation is responsible to inherit
// from the abstract classes declared below.

#include <RTI/SpecificConfig.h>
#include <RTI/auth/AuthorizationResult.h>
#include <RTI/auth/HLAnoCredentials.h>
#include <string>

namespace rti1516_2025
{
   class Credentials;

   class RTI_EXPORT Authorizer
   {
   public:
      virtual ~Authorizer() noexcept = default;

      // Called to authorize the connect service call
      virtual AuthorizationResult authorizeRtiOperation (
         Credentials const & credentials) = 0;

      // Called to authorize the create and destroy federation service calls
      virtual AuthorizationResult authorizeFederationOperation (
         Credentials const & credentials,
         std::wstring const & federationName) = 0;

      // Called to authorize the join federation service call
      virtual AuthorizationResult authorizeFederateOperation (
         Credentials const & credentials,
         std::wstring const & federationName,
         std::wstring const & federateName,
         std::wstring const & federateType) = 0;

      // Return the name of the authorizer implementation
      virtual std::wstring getName () const = 0;
   };
}


