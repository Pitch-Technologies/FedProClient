#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

#include <string>

#include <RTI/SpecificConfig.h>

namespace rti1516_202X
{
   class RTI_EXPORT RtiConfiguration
   {
   public:
      static RtiConfiguration createConfiguration();

      RtiConfiguration& withConfigurationName(std::wstring const & configurationName);

      RtiConfiguration& withRtiAddress(std::wstring const & rtiAddress);

      RtiConfiguration& withAdditionalSettings(std::wstring const & additionalSettings);

   public:
      const std::wstring& configurationName() const;

      const std::wstring& rtiAddress() const;

      const std::wstring& additionalSettings() const;

   private:
      std::wstring _configurationName;
      std::wstring _rtiAddress;
      std::wstring _additionalSettings;
   };

}


