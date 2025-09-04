#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

#include <utility>
#include <vector>


namespace rti1516_2025
{
   // Platform Specific Typedefs
#if defined(_WIN32)
   typedef char      Integer8;
   typedef short     Integer16;
   typedef int       Integer32;
   typedef _int64    Integer64;
   typedef unsigned char      UnsignedInteger8;
   typedef unsigned short     UnsignedInteger16;
   typedef unsigned int       UnsignedInteger32;
   typedef unsigned _int64    UnsignedInteger64;
#else
#if defined(RTI_USE_64BIT_LONGS)
   typedef char      Integer8;
   typedef short     Integer16;
   typedef int       Integer32;
   typedef long      Integer64;
   typedef unsigned char      UnsignedInteger8;
   typedef unsigned short     UnsignedInteger16;
   typedef unsigned int       UnsignedInteger32;
   typedef unsigned _int64    UnsignedInteger64;
#else
   typedef char      Integer8;
   typedef short     Integer16;
   typedef int       Integer32;
   typedef long long Integer64;
   typedef unsigned char      UnsignedInteger8;
   typedef unsigned short     UnsignedInteger16;
   typedef unsigned int       UnsignedInteger32;
   typedef unsigned long long UnsignedInteger64;
#endif
#endif

   typedef Integer8  Octet;
   typedef std::pair< Octet, Octet > OctetPair;
}




