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
#include <RTI/auth/Credentials.h>
#include <string>

namespace rti1516_2025
{
   const std::wstring HLAnoCredentialsType(L"HLAnoCredentials");

   class RTI_EXPORT HLAnoCredentials : public Credentials
   {
   public:
      HLAnoCredentials ()
            : Credentials(HLAnoCredentialsType, VariableLengthData())
      {
      }

      ~HLAnoCredentials ()
         noexcept override = default;

      HLAnoCredentials (
         HLAnoCredentials const & rhs) = default;

      HLAnoCredentials & operator= (
         HLAnoCredentials const & rhs) = default;
   };
}

