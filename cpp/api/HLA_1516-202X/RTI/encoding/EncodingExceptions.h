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
#include <RTI/Exception.h>
#include <RTI/encoding/EncodingConfig.h>

namespace rti1516_202X
{

   class RTI_EXPORT EncoderException : public Exception
   {
   public:
      explicit EncoderException (std::wstring const & message)
         noexcept;

      std::wstring what () const
         noexcept override;

      std::wstring name() const noexcept override;

   private:
      std::wstring _msg;
   };
}




