#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

#include <RTI/SpecificConfig.h>
#include <string>

namespace rti1516_202X
{
   class RTI_EXPORT AuthorizationResult
   {
   public:
      enum Code {
         AUTHORIZED, UNAUTHORIZED, INVALID_CREDENTIALS, AUTHORIZATION_ERROR
      };

      explicit AuthorizationResult(
         Code code, std::wstring const & message = L"")
         : _code(code), _message(message)
      {
      }

      virtual ~AuthorizationResult ()
         noexcept = default;

      AuthorizationResult (
         AuthorizationResult const & rhs) = default;

      Code getCode() const
      {
         return _code;
      };

      std::wstring const & getMessage() const
      {
         return _message;
      }

   private:
      Code _code;
      std::wstring _message;
   };
}


