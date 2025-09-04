#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

// Purpose: This file contains definitions that are used to isolate
// platform-specific elements of the API. It is not implementation-specific.

//
// The following macros control import and export of library symbols:
//
// STATIC_RTI - Shall be defined when building librti as a static library
//     or when building a federate that wants to link to it statically.
// STATIC_FEDTIME - Shall be defined when building libfedtime as a static library
//     or when building a federate that wants to link to it statically.
// STATIC_AUTHORIZER - Shall be defined when building libauthorizer as a static library
//     or when building a federate that wants to link to it statically.
//
// For internal RTI development on Windows only:
//
// BUILDING_RTI - Shall be defined when compiling librti as a DLL.
// BUILDING_FEDTIME - Shall be defined when building libfedtime as a DLL.
// BUILDING_AUTHORIZER - Shall be defined when building libauthorizer as a DLL.
//

#if defined(_MSC_VER) && defined(RTI_DISABLE_WARNINGS)
// disable warnings about a "dllexport" class using a regular class
   #pragma warning(disable: 4251)
#endif

#if defined(STATIC_RTI)
   #define RTI_EXPORT
#elif defined(_WIN32) || defined(__CYGWIN__)
   #if defined(BUILDING_RTI)
      #define RTI_EXPORT __declspec(dllexport)
   #else
      #define RTI_EXPORT __declspec(dllimport)
   #endif
#else
   #define RTI_EXPORT __attribute__((visibility ("default")))
#endif

#if defined(STATIC_FEDTIME)
   #define RTI_EXPORT_FEDTIME
#elif defined(_WIN32) || defined(__CYGWIN__)
   #if defined(BUILDING_FEDTIME)
      #define RTI_EXPORT_FEDTIME __declspec(dllexport)
   #else
      #define RTI_EXPORT_FEDTIME __declspec(dllimport)
   #endif
#else
   #define RTI_EXPORT_FEDTIME __attribute__((visibility ("default")))
#endif

#if defined(STATIC_AUTHORIZER)
   #define RTI_EXPORT_AUTHORIZER
#elif defined(_WIN32) || defined(__CYGWIN__)
   #if defined(BUILDING_AUTHORIZER)
      #define RTI_EXPORT_AUTHORIZER __declspec(dllexport)
   #else
      #define RTI_EXPORT_AUTHORIZER __declspec(dllimport)
   #endif
#else
   #define RTI_EXPORT_AUTHORIZER __attribute__((visibility ("default")))
#endif

