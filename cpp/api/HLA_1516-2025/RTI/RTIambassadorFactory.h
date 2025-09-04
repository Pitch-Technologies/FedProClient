#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

namespace rti1516_2025
{
   class RTIambassador;
}

#include <RTI/SpecificConfig.h>
#include <RTI/Exception.h>
#include <memory>

namespace rti1516_2025
{
   class RTI_EXPORT RTIambassadorFactory
   {
   public:
      RTIambassadorFactory();

      virtual ~RTIambassadorFactory()
         noexcept;

      // 10.35
       /**
        * @throws RTIinternalError
        */
      std::unique_ptr< RTIambassador > createRTIambassador ();
   };
}


