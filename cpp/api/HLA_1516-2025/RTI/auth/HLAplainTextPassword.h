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
   const std::wstring HLAplainTextPasswordType(L"HLAplainTextPassword");

   class RTI_EXPORT HLAplainTextPassword : public Credentials
   {
   public:
      explicit HLAplainTextPassword(
            std::wstring const & password);

      explicit HLAplainTextPassword(
            VariableLengthData const & encodedPassword);

      ~HLAplainTextPassword() noexcept override;

      HLAplainTextPassword(
            HLAplainTextPassword const & rhs);

      HLAplainTextPassword & operator=(
            HLAplainTextPassword const & rhs);

      std::wstring decode();

   private:
      VariableLengthData encode(std::wstring const & basicString);
   };
}
