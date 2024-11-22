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
#include <RTI/VariableLengthData.h>
#include <string>

namespace rti1516_202X
{
   class RTI_EXPORT Credentials
   {
   public:
      Credentials(
            std::wstring const & type,
            VariableLengthData const & data) : _type(type), _data(data)
      {
      }

      virtual ~Credentials() noexcept = default;

      Credentials(
            Credentials const & rhs) = default;

      /**
       * Returns the type of these credentials. Predefined types are:
       * <li>HLAplainTextPassword</li>
       * <li>HLAnoCredentials</li>
       *
       * User-defined authorizer libraries can support new types of credentials.
       * \return type of these credentials
       */
      std::wstring const & getType() const
      {
         return _type;
      }

      VariableLengthData const & getData() const
      {
         return _data;
      }

   private:
      std::wstring _type;
      VariableLengthData _data;
   };
}
