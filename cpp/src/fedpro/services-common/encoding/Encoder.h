/***********************************************************************
  Copyright (C) 2023 Pitch Technologies AB

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 **********************************************************************/

#pragma once

#include <RTI/encoding/EncodingExceptions.h>
#include <RTI/encoding/EncodingConfig.h>

#include <cstring>
#include <limits>
#include <stdexcept>

class Encoder
{

public:

   using Octet = RTI_NAMESPACE::Octet;
   using OctetPair = RTI_NAMESPACE::OctetPair;
   using Integer16 = RTI_NAMESPACE::Integer16;
   using Integer32 = RTI_NAMESPACE::Integer32;
   using Integer64 = RTI_NAMESPACE::Integer64;

#if (RTI_HLA_VERSION >= 2025)
   using UnsignedInteger16 = RTI_NAMESPACE::UnsignedInteger16;
   using UnsignedInteger32 = RTI_NAMESPACE::UnsignedInteger32;
   using UnsignedInteger64 = RTI_NAMESPACE::UnsignedInteger64;
#endif

//-------------------------------------------------------------------------
   static void encodeHLAASCIIchar(char value, const void *buffer)
   {
      *(char *) buffer = value;
   }

   static void *encodeHLAASCIIchar(char value, int &len)
   {
      len = 1;
      char *buffer = new char[len];
      *buffer = value;
      return buffer;
   }

   static char decodeHLAASCIIchar(const void *buffer, const size_t)
   {
      return *(char *) buffer;
   }

   static unsigned int sizeofHLAASCIIchar(char)
   {
      return 1;
   }

   static unsigned int boundaryofHLAASCIIchar()
   {
      return 1;
   }

//-------------------------------------------------------------------------
   static void encodeHLAboolean(bool value, const void *buffer)
   {
      encodeHLAinteger32BE(value ? 1 : 0, buffer);
   }

   static void *encodeHLAboolean(bool value, int &len)
   {
      return encodeHLAinteger32BE(value ? 1 : 0, len);
   }

   static bool decodeHLAboolean(const void *buffer, const size_t remaining)
   {
      return decodeHLAinteger32BE(buffer, remaining) == 1;
   }

   static unsigned int sizeofHLAboolean(bool)
   {
      return 4;
   }

   static unsigned int boundaryofHLAboolean()
   {
      return 4;
   }

//-------------------------------------------------------------------------
   static void encodeHLAbyte(Octet value, const void *buffer)
   {
      *(Octet *) buffer = value;
   }

   static void *encodeHLAbyte(Octet value, int &len)
   {
      len = 1;
      auto *buffer = (Octet *) new char[len];
      *buffer = value;
      return buffer;
   }

   static Octet decodeHLAbyte(const void *buffer, const size_t)
   {
      return *(Octet *) buffer;
   }

   static unsigned int sizeofHLAbyte(Octet)
   {
      return 1;
   }

   static unsigned int boundaryofHLAbyte()
   {
      return 1;
   }

//-------------------------------------------------------------------------
   static void encodeHLAoctet(Octet value, const void *buffer)
   {
      *(Octet *) buffer = value;
   }

   static void *encodeHLAoctet(Octet value, int &len)
   {
      len = 1;
      auto *buffer = (Octet *) new char[len];
      *buffer = value;
      return buffer;
   }

   static Octet decodeHLAoctet(const void *buffer, const size_t)
   {
      return *(Octet *) buffer;
   }

   static unsigned int sizeofHLAoctet(Octet)
   {
      return 1;
   }

   static unsigned int boundaryofHLAoctet()
   {
      return 1;
   }

//-------------------------------------------------------------------------
   static void encodeHLAoctetPairBE(OctetPair value, const void *buffer)
   {
      ((char *) buffer)[0] = value.first;
      ((char *) buffer)[1] = value.second;
   }

   static void *encodeHLAoctetPairBE(OctetPair value, int &len)
   {
      len = 2;
      auto *buffer = (OctetPair *) new char[len];
      encodeHLAoctetPairBE(value, buffer);
      return buffer;
   }

   static OctetPair decodeHLAoctetPairBE(const void *buffer, const size_t)
   {
      OctetPair res;
      res.first = ((char *) buffer)[0];
      res.second = ((char *) buffer)[1];
      return res;
   }

   static unsigned int sizeofHLAoctetPairBE(OctetPair)
   {
      return 2;
   }

   static unsigned int boundaryofHLAoctetPairBE()
   {
      return 2;
   }

//-------------------------------------------------------------------------
   static void encodeHLAoctetPairLE(OctetPair value, const void *buffer)
   {
      ((char *) buffer)[0] = value.second;
      ((char *) buffer)[1] = value.first;
   }

   static void *encodeHLAoctetPairLE(OctetPair value, int &len)
   {
      len = 2;
      auto *buffer = (OctetPair *) new char[len];
      encodeHLAoctetPairLE(value, buffer);
      return buffer;
   }

   static OctetPair decodeHLAoctetPairLE(const void *buffer, const size_t)
   {
      OctetPair res;
      res.second = ((char *) buffer)[0];
      res.first = ((char *) buffer)[1];
      return res;
   }

   static unsigned int sizeofHLAoctetPairLE(OctetPair)
   {
      return 2;
   }

   static unsigned int boundaryofHLAoctetPairLE()
   {
      return 2;
   }

//-------------------------------------------------------------------------
   static void encodeHLAinteger16LE(Integer16 value, const void *buffer)
   {
      ((char *) buffer)[0] = static_cast<char>(value & 0x00ff);
      ((char *) buffer)[1] = static_cast<char>((value & 0xff00) >> 8);
   }

   static void *encodeHLAinteger16LE(Integer16 value, int &len)
   {
      len = 2;
      auto *buffer = (Integer16 *) new char[len];
      encodeHLAinteger16LE(value, buffer);
      return buffer;
   }

   static Integer16 decodeHLAinteger16LE(const void *buffer, const size_t)
   {
      Integer16 res = 0;
      res |= (((Integer16) ((char *) buffer)[0]) & 0xff) << 0;
      res |= (((Integer16) ((char *) buffer)[1]) & 0xff) << 8;
      return res;
   }

   static unsigned int sizeofHLAinteger16LE(Integer16)
   {
      return 2;
   }

   static unsigned int boundaryofHLAinteger16LE()
   {
      return 2;
   }

//-------------------------------------------------------------------------
   static void encodeHLAinteger16BE(Integer16 value, const void *buffer)
   {
      ((char *) buffer)[0] = static_cast<char>((value & 0xff00) >> 8);
      ((char *) buffer)[1] = static_cast<char>((value & 0xff) >> 0);
   }

   static void *encodeHLAinteger16BE(Integer16 value, int &len)
   {
      len = 2;
      auto *buffer = (Integer16 *) new char[len];
      encodeHLAinteger16BE(value, buffer);
      return buffer;
   }

   static Integer16 decodeHLAinteger16BE(const void *buffer, const size_t)
   {
      Integer16 res = 0;
      res |= (((Integer16) ((char *) buffer)[0]) & 0xff) << 8;
      res |= (((Integer16) ((char *) buffer)[1]) & 0xff) << 0;
      return res;
   }

   static unsigned int sizeofHLAinteger16BE(Integer16)
   {
      return 2;
   }

   static unsigned int boundaryofHLAinteger16BE()
   {
      return 2;
   }

//-------------------------------------------------------------------------
   static void encodeHLAinteger32LE(Integer32 value, const void *buffer)
   {
      ((char *) buffer)[0] = static_cast<char>(value & 0x00ff);
      ((char *) buffer)[1] = static_cast<char>((value & 0xff00) >> 8);
      ((char *) buffer)[2] = static_cast<char>((value & 0xff0000) >> 16);
      ((char *) buffer)[3] = static_cast<char>((value & 0xff000000) >> 24);
   }

   static void *encodeHLAinteger32LE(Integer32 value, int &len)
   {
      len = 4;
      auto *buffer = (Integer32 *) new char[len];
      encodeHLAinteger32LE(value, buffer);
      return buffer;
   }

   static Integer32 decodeHLAinteger32LE(const void *buffer, const size_t)
   {
      Integer32 res = 0;
      res |= (((Integer32) ((char *) buffer)[0]) & 0xff) << 0;
      res |= (((Integer32) ((char *) buffer)[1]) & 0xff) << 8;
      res |= (((Integer32) ((char *) buffer)[2]) & 0xff) << 16;
      res |= (((Integer32) ((char *) buffer)[3]) & 0xff) << 24;
      return res;
   }

   static unsigned int sizeofHLAinteger32LE(Integer32)
   {
      return 4;
   }

   static unsigned int boundaryofHLAinteger32LE()
   {
      return 4;
   }

//-------------------------------------------------------------------------
   static void encodeHLAinteger32BE(Integer32 value, const void *buffer)
   {
      ((char *) buffer)[0] = static_cast<char>((value & 0xff000000) >> 24);
      ((char *) buffer)[1] = static_cast<char>((value & 0xff0000) >> 16);
      ((char *) buffer)[2] = static_cast<char>((value & 0xff00) >> 8);
      ((char *) buffer)[3] = static_cast<char>((value & 0xff) >> 0);
   }

   static void *encodeHLAinteger32BE(Integer32 value, int &len)
   {
      len = 4;
      auto *buffer = (Integer32 *) new char[len];
      encodeHLAinteger32BE(value, buffer);
      return buffer;
   }

   static Integer32 decodeHLAinteger32BE(const void *buffer, const size_t)
   {
      Integer32 res = 0;
      res |= (((Integer32) ((char *) buffer)[0]) & 0xff) << 24;
      res |= (((Integer32) ((char *) buffer)[1]) & 0xff) << 16;
      res |= (((Integer32) ((char *) buffer)[2]) & 0xff) << 8;
      res |= (((Integer32) ((char *) buffer)[3]) & 0xff) << 0;
      return res;
   }

   static unsigned int sizeofHLAinteger32BE(Integer32)
   {
      return 4;
   }

   static unsigned int boundaryofHLAinteger32BE()
   {
      return 4;
   }

//-------------------------------------------------------------------------
   static void encodeHLAinteger64LE(Integer64 value, const void *buffer)
   {
      ((char *) buffer)[0] = (char) (value & 0x00ff);
      ((char *) buffer)[1] = (char) ((value >> 8) & 0xff);
      ((char *) buffer)[2] = (char) ((value >> 16) & 0xff);
      ((char *) buffer)[3] = (char) ((value >> 24) & 0xff);
      ((char *) buffer)[4] = (char) ((value >> 32) & 0xff);
      ((char *) buffer)[5] = (char) ((value >> 40) & 0xff);
      ((char *) buffer)[6] = (char) ((value >> 48) & 0xff);
      ((char *) buffer)[7] = (char) ((value >> 56) & 0xff);
   }

   static void *encodeHLAinteger64LE(Integer64 value, int &len)
   {
      len = 8;
      auto *buffer = (Integer64 *) new char[len];
      encodeHLAinteger64LE(value, buffer);
      return buffer;
   }

   static Integer64 decodeHLAinteger64LE(const void *buffer, const size_t)
   {
      Integer64 res = 0;
      res |= (((Integer64) ((char *) buffer)[0]) & 0xff) << 0;
      res |= (((Integer64) ((char *) buffer)[1]) & 0xff) << 8;
      res |= (((Integer64) ((char *) buffer)[2]) & 0xff) << 16;
      res |= (((Integer64) ((char *) buffer)[3]) & 0xff) << 24;
      res |= (((Integer64) ((char *) buffer)[4]) & 0xff) << 32;
      res |= (((Integer64) ((char *) buffer)[5]) & 0xff) << 40;
      res |= (((Integer64) ((char *) buffer)[6]) & 0xff) << 48;
      res |= (((Integer64) ((char *) buffer)[7]) & 0xff) << 56;
      return res;
   }

   static unsigned int sizeofHLAinteger64LE(Integer64)
   {
      return 8;
   }

   static unsigned int boundaryofHLAinteger64LE()
   {
      return 8;
   }

//-------------------------------------------------------------------------
   static void encodeHLAinteger64BE(Integer64 value, const void *buffer)
   {
      ((char *) buffer)[0] = (char) ((value >> 56) & 0xff);
      ((char *) buffer)[1] = (char) ((value >> 48) & 0xff);
      ((char *) buffer)[2] = (char) ((value >> 40) & 0xff);
      ((char *) buffer)[3] = (char) ((value >> 32) & 0xff);
      ((char *) buffer)[4] = (char) ((value >> 24) & 0xff);
      ((char *) buffer)[5] = (char) ((value >> 16) & 0xff);
      ((char *) buffer)[6] = (char) ((value >> 8) & 0xff);
      ((char *) buffer)[7] = (char) ((value >> 0) & 0xff);
   }

   static void *encodeHLAinteger64BE(Integer64 value, int &len)
   {
      len = 8;
      auto *buffer = (Integer64 *) new char[len];
      encodeHLAinteger64BE(value, buffer);
      return buffer;
   }

   static Integer64 decodeHLAinteger64BE(const void *buffer, const size_t)
   {
      Integer64 res = 0;
      res |= (((Integer64) ((char *) buffer)[0]) & 0xff) << 56;
      res |= (((Integer64) ((char *) buffer)[1]) & 0xff) << 48;
      res |= (((Integer64) ((char *) buffer)[2]) & 0xff) << 40;
      res |= (((Integer64) ((char *) buffer)[3]) & 0xff) << 32;
      res |= (((Integer64) ((char *) buffer)[4]) & 0xff) << 24;
      res |= (((Integer64) ((char *) buffer)[5]) & 0xff) << 16;
      res |= (((Integer64) ((char *) buffer)[6]) & 0xff) << 8;
      res |= (((Integer64) ((char *) buffer)[7]) & 0xff) << 0;
      return res;
   }

   static unsigned int sizeofHLAinteger64BE(Integer64)
   {
      return 8;
   }

   static unsigned int boundaryofHLAinteger64BE()
   {
      return 8;
   }

#if (RTI_HLA_VERSION >= 2025)
//-------------------------------------------------------------------------
   static void encodeHLAunsignedInteger16LE(UnsignedInteger16 value, const void *buffer)
   {
      ((char *) buffer)[0] = static_cast<char>(value & 0x00ff);
      ((char *) buffer)[1] = static_cast<char>((value & 0xff00) >> 8);
   }

   static void *encodeHLAunsignedInteger16LE(UnsignedInteger16 value, int &len)
   {
      len = 2;
      auto *buffer = (UnsignedInteger16 *) new char[len];
      encodeHLAunsignedInteger16LE(value, buffer);
      return buffer;
   }

   static UnsignedInteger16 decodeHLAunsignedInteger16LE(const void *buffer, const size_t)
   {
      UnsignedInteger16 res = 0;
      res |= (((UnsignedInteger16) ((char *) buffer)[0]) & 0xff) << 0;
      res |= (((UnsignedInteger16) ((char *) buffer)[1]) & 0xff) << 8;
      return res;
   }

   static unsigned int sizeofHLAunsignedInteger16LE(UnsignedInteger16)
   {
      return 2;
   }

   static unsigned int boundaryofHLAunsignedInteger16LE()
   {
      return 2;
   }

   //-------------------------------------------------------------------------
   static void encodeHLAunsignedInteger16BE(UnsignedInteger16 value, const void *buffer)
   {
      ((char *) buffer)[0] = static_cast<char>((value & 0xff00) >> 8);
      ((char *) buffer)[1] = static_cast<char>((value & 0xff) >> 0);
   }

   static void *encodeHLAunsignedInteger16BE(UnsignedInteger16 value, int &len)
   {
      len = 2;
      auto *buffer = (UnsignedInteger16 *) new char[len];
      encodeHLAunsignedInteger16BE(value, buffer);
      return buffer;
   }

   static UnsignedInteger16 decodeHLAunsignedInteger16BE(const void *buffer, const size_t)
   {
      UnsignedInteger16 res = 0;
      res |= (((UnsignedInteger16) ((char *) buffer)[0]) & 0xff) << 8;
      res |= (((UnsignedInteger16) ((char *) buffer)[1]) & 0xff) << 0;
      return res;
   }

   static unsigned int sizeofHLAunsignedInteger16BE(UnsignedInteger16)
   {
      return 2;
   }

   static unsigned int boundaryofHLAunsignedInteger16BE()
   {
      return 2;
   }

   //-------------------------------------------------------------------------
   static void encodeHLAunsignedInteger32LE(UnsignedInteger32 value, const void *buffer)
   {
      ((char *) buffer)[0] = static_cast<char>(value & 0x00ff);
      ((char *) buffer)[1] = static_cast<char>((value & 0xff00) >> 8);
      ((char *) buffer)[2] = static_cast<char>((value & 0xff0000) >> 16);
      ((char *) buffer)[3] = static_cast<char>((value & 0xff000000) >> 24);
   }

   static void *encodeHLAunsignedInteger32LE(UnsignedInteger32 value, int &len)
   {
      len = 4;
      auto *buffer = (UnsignedInteger32 *) new char[len];
      encodeHLAunsignedInteger32LE(value, buffer);
      return buffer;
   }

   static UnsignedInteger32 decodeHLAunsignedInteger32LE(const void *buffer, const size_t)
   {
      UnsignedInteger32 res = 0;
      res |= (((UnsignedInteger32) ((char *) buffer)[0]) & 0xff) << 0;
      res |= (((UnsignedInteger32) ((char *) buffer)[1]) & 0xff) << 8;
      res |= (((UnsignedInteger32) ((char *) buffer)[2]) & 0xff) << 16;
      res |= (((UnsignedInteger32) ((char *) buffer)[3]) & 0xff) << 24;
      return res;
   }

   static unsigned int sizeofHLAunsignedInteger32LE(UnsignedInteger32)
   {
      return 4;
   }

   static unsigned int boundaryofHLAunsignedInteger32LE()
   {
      return 4;
   }

   //-------------------------------------------------------------------------
   static void encodeHLAunsignedInteger32BE(UnsignedInteger32 value, const void *buffer)
   {
      ((char *) buffer)[0] = static_cast<char>((value & 0xff000000) >> 24);
      ((char *) buffer)[1] = static_cast<char>((value & 0xff0000) >> 16);
      ((char *) buffer)[2] = static_cast<char>((value & 0xff00) >> 8);
      ((char *) buffer)[3] = static_cast<char>((value & 0xff) >> 0);
   }

   static void *encodeHLAunsignedInteger32BE(UnsignedInteger32 value, int &len)
   {
      len = 4;
      auto *buffer = (UnsignedInteger32 *) new char[len];
      encodeHLAunsignedInteger32BE(value, buffer);
      return buffer;
   }

   static UnsignedInteger32 decodeHLAunsignedInteger32BE(const void *buffer, const size_t)
   {
      UnsignedInteger32 res = 0;
      res |= (((UnsignedInteger32) ((char *) buffer)[0]) & 0xff) << 24;
      res |= (((UnsignedInteger32) ((char *) buffer)[1]) & 0xff) << 16;
      res |= (((UnsignedInteger32) ((char *) buffer)[2]) & 0xff) << 8;
      res |= (((UnsignedInteger32) ((char *) buffer)[3]) & 0xff) << 0;
      return res;
   }

   static unsigned int sizeofHLAunsignedInteger32BE(UnsignedInteger32)
   {
      return 4;
   }

   static unsigned int boundaryofHLAunsignedInteger32BE()
   {
      return 4;
   }

   //-------------------------------------------------------------------------
   static void encodeHLAunsignedInteger64LE(UnsignedInteger64 value, const void *buffer)
   {
      ((char *) buffer)[0] = (char) (value & 0x00ff);
      ((char *) buffer)[1] = (char) ((value >> 8) & 0xff);
      ((char *) buffer)[2] = (char) ((value >> 16) & 0xff);
      ((char *) buffer)[3] = (char) ((value >> 24) & 0xff);
      ((char *) buffer)[4] = (char) ((value >> 32) & 0xff);
      ((char *) buffer)[5] = (char) ((value >> 40) & 0xff);
      ((char *) buffer)[6] = (char) ((value >> 48) & 0xff);
      ((char *) buffer)[7] = (char) ((value >> 56) & 0xff);
   }

   static void *encodeHLAunsignedInteger64LE(UnsignedInteger64 value, int &len)
   {
      len = 8;
      auto *buffer = (UnsignedInteger64 *)
      new char[len];
      encodeHLAunsignedInteger64LE(value, buffer);
      return buffer;
   }

   static UnsignedInteger64 decodeHLAunsignedInteger64LE(const void *buffer, const size_t)
   {
      UnsignedInteger64 res = 0;
      res |= (((UnsignedInteger64)((char *) buffer)[0]) & 0xff) << 0;
      res |= (((UnsignedInteger64)((char *) buffer)[1]) & 0xff) << 8;
      res |= (((UnsignedInteger64)((char *) buffer)[2]) & 0xff) << 16;
      res |= (((UnsignedInteger64)((char *) buffer)[3]) & 0xff) << 24;
      res |= (((UnsignedInteger64)((char *) buffer)[4]) & 0xff) << 32;
      res |= (((UnsignedInteger64)((char *) buffer)[5]) & 0xff) << 40;
      res |= (((UnsignedInteger64)((char *) buffer)[6]) & 0xff) << 48;
      res |= (((UnsignedInteger64)((char *) buffer)[7]) & 0xff) << 56;
      return res;
   }

   static unsigned int sizeofHLAunsignedInteger64LE(UnsignedInteger64)
   {
      return 8;
   }

   static unsigned int boundaryofHLAunsignedInteger64LE()
   {
      return 8;
   }

//-------------------------------------------------------------------------
   static void encodeHLAunsignedInteger64BE(UnsignedInteger64 value, const void *buffer)
   {
      ((char *) buffer)[0] = (char) ((value >> 56) & 0xff);
      ((char *) buffer)[1] = (char) ((value >> 48) & 0xff);
      ((char *) buffer)[2] = (char) ((value >> 40) & 0xff);
      ((char *) buffer)[3] = (char) ((value >> 32) & 0xff);
      ((char *) buffer)[4] = (char) ((value >> 24) & 0xff);
      ((char *) buffer)[5] = (char) ((value >> 16) & 0xff);
      ((char *) buffer)[6] = (char) ((value >> 8) & 0xff);
      ((char *) buffer)[7] = (char) ((value >> 0) & 0xff);
   }

   static void *encodeHLAunsignedInteger64BE(UnsignedInteger64 value, int &len)
   {
      len = 8;
      auto *buffer = (UnsignedInteger64 *)
      new char[len];
      encodeHLAunsignedInteger64BE(value, buffer);
      return buffer;
   }

   static UnsignedInteger64 decodeHLAunsignedInteger64BE(const void *buffer, const size_t)
   {
      UnsignedInteger64 res = 0;
      res |= (((UnsignedInteger64)((char *) buffer)[0]) & 0xff) << 56;
      res |= (((UnsignedInteger64)((char *) buffer)[1]) & 0xff) << 48;
      res |= (((UnsignedInteger64)((char *) buffer)[2]) & 0xff) << 40;
      res |= (((UnsignedInteger64)((char *) buffer)[3]) & 0xff) << 32;
      res |= (((UnsignedInteger64)((char *) buffer)[4]) & 0xff) << 24;
      res |= (((UnsignedInteger64)((char *) buffer)[5]) & 0xff) << 16;
      res |= (((UnsignedInteger64)((char *) buffer)[6]) & 0xff) << 8;
      res |= (((UnsignedInteger64)((char *) buffer)[7]) & 0xff) << 0;
      return res;
   }

   static unsigned int sizeofHLAunsignedInteger64BE(UnsignedInteger64)
   {
      return 8;
   }

   static unsigned int boundaryofHLAunsignedInteger64BE()
   {
      return 8;
   }
#endif // (RTI_HLA_VERSION >= 2025)

//-------------------------------------------------------------------------
   static void encodeHLAfloat32LE(float value, const void *buffer)
   {
      Integer32 intValue;
      memcpy(&intValue, &value, 4);
      encodeHLAinteger32LE(intValue, buffer);
   }

   static void *encodeHLAfloat32LE(float value, int &len)
   {
      len = 4;
      auto *buffer = (float *) new char[len];
      encodeHLAfloat32LE(value, buffer);
      return buffer;
   }

   static float decodeHLAfloat32LE(const void *buffer, const size_t remaining)
   {
      Integer32 res = decodeHLAinteger32LE(buffer, remaining);
      float floatRes;
      memcpy(&floatRes, &res, 4);
      return floatRes;
   }

   static unsigned int sizeofHLAfloat32LE(float)
   {
      return 4;
   }

   static unsigned int boundaryofHLAfloat32LE()
   {
      return 4;
   }

//-------------------------------------------------------------------------
   static void encodeHLAfloat32BE(float value, const void *buffer)
   {
      Integer32 intValue;
      memcpy(&intValue, &value, 4);
      encodeHLAinteger32BE(intValue, buffer);
   }

   static void *encodeHLAfloat32BE(float value, int &len)
   {
      len = 4;
      auto *buffer = (float *) new char[len];
      encodeHLAfloat32BE(value, buffer);
      return buffer;
   }

   static float decodeHLAfloat32BE(const void *buffer, const size_t remaining)
   {
      Integer32 res = decodeHLAinteger32BE(buffer, remaining);
      float floatRes;
      memcpy(&floatRes, &res, 4);
      return floatRes;
   }

   static unsigned int sizeofHLAfloat32BE(float)
   {
      return 4;
   }

   static unsigned int boundaryofHLAfloat32BE()
   {
      return 4;
   }

//-------------------------------------------------------------------------
   static void encodeHLAfloat64LE(double value, const void *buffer)
   {
      Integer64 longValue;
      memcpy(&longValue, &value, 8);
      encodeHLAinteger64LE(longValue, buffer);
   }

   static void *encodeHLAfloat64LE(double value, int &len)
   {
      len = 8;
      auto *buffer = (double *) new char[len];
      encodeHLAfloat64LE(value, buffer);
      return buffer;
   }

   static double decodeHLAfloat64LE(const void *buffer, const size_t remaining)
   {
      Integer64 res = decodeHLAinteger64LE(buffer, remaining);
      double doubleRes;
      memcpy(&doubleRes, &res, 8);
      return doubleRes;
   }

   static unsigned int sizeofHLAfloat64LE(double)
   {
      return 8;
   }

   static unsigned int boundaryofHLAfloat64LE()
   {
      return 8;
   }

//-------------------------------------------------------------------------
   static void encodeHLAfloat64BE(double value, const void *buffer)
   {
      Integer64 longValue;
      memcpy(&longValue, &value, 8);
      encodeHLAinteger64BE(longValue, buffer);
   }

   static void *encodeHLAfloat64BE(double value, int &len)
   {
      len = 8;
      auto *buffer = (double *) new char[len];
      encodeHLAfloat64BE(value, buffer);
      return buffer;
   }

   static double decodeHLAfloat64BE(const void *buffer, const size_t remaining)
   {
      Integer64 res = decodeHLAinteger64BE(buffer, remaining);
      double doubleRes;
      memcpy(&doubleRes, &res, 8);
      return doubleRes;
   }

   static unsigned int sizeofHLAfloat64BE(double)
   {
      return 8;
   }

   static unsigned int boundaryofHLAfloat64BE()
   {
      return 8;
   }

//-------------------------------------------------------------------------
   static void encodeHLAASCIIstring(const std::string &value, const void *buffer)
   {
      encodeHLAinteger32BE(static_cast<Integer32>(value.length()), buffer);
      memcpy((char *) buffer + 4, value.c_str(), value.length());
   }

   static void *encodeHLAASCIIstring(const std::string &value, int &len)
   {
      len = 4 + static_cast<int>(value.length());
      char *buffer = new char[len];
      encodeHLAASCIIstring(value, buffer);
      return buffer;
   }

   static std::string decodeHLAASCIIstring(const void *buffer, const size_t remaining)
   {
      const char * pos = reinterpret_cast<const char *>(buffer);
      size_t len = decodeHLAinteger32BE(pos, remaining);
      if (len > std::numeric_limits<size_t>::max() - 4) {
         // (4 + len) would overflow
         throw EncoderException(L"Array index out of bounds");
      }
      if (4 + len > remaining) {
         throw EncoderException(L"Array index out of bounds");
      }

      pos += 4;
      try {
         return std::string(pos, len);
      } catch (const std::length_error &) {
         throw EncoderException(L"String size exceeds maximum");
      }
   }

   static unsigned int sizeofHLAASCIIstring(const std::string &value)
   {
      return static_cast<unsigned int>(value.length()) + 4;
   }

   static unsigned int boundaryofHLAASCIIstring()
   {
      return 4;
   }

//-------------------------------------------------------------------------
   static void encodeHLAunicodeChar(wchar_t value, const void *buffer)
   {
      ((char *) buffer)[0] = static_cast<char>((value & 0xff00) >> 8);
      ((char *) buffer)[1] = static_cast<char>(value & 0x00ff);
   }

   static void *encodeHLAunicodeChar(wchar_t value, int &len)
   {
      len = 2;
      auto *buffer = (wchar_t *) new char[len];
      encodeHLAunicodeChar(value, buffer);
      return buffer;
   }

   static wchar_t decodeHLAunicodeChar(const void *buffer, const size_t)
   {
      wchar_t res = 0;
      res |= (((wchar_t) ((char *) buffer)[0]) & 0xff) << 8;
      res |= (((wchar_t) ((char *) buffer)[1]) & 0xff) << 0;
      return res;
   }

   static unsigned int sizeofHLAunicodeChar(wchar_t)
   {
      return 2;
   }

   static unsigned int boundaryofHLAunicodeChar()
   {
      return 2;
   }

//-------------------------------------------------------------------------
   static void encodeHLAunicodeString(const std::wstring & value, const void *buffer)
   {
      char *pos = (char *) buffer;
      encodeHLAinteger32BE(static_cast<Integer32>(value.length()), pos);
      pos += 4;
      for (wchar_t i: value) {
         encodeHLAunicodeChar(i, pos);
         pos += 2;
      }
   }

   static void *encodeHLAunicodeString(const std::wstring & value, int &len)
   {
      len = 4 + static_cast<int>(value.length()) * 2;
      char *buffer = new char[len];
      encodeHLAunicodeString(value, buffer);
      return buffer;
   }

   static std::wstring decodeHLAunicodeString(const void *buffer, size_t remaining)
   {
      char *pos = (char *) buffer;
      size_t len = decodeHLAinteger32BE(buffer, remaining);
      if (len > (std::numeric_limits<size_t>::max() - 4) / 2) {
         // (4 + len * 2) would overflow
         throw EncoderException(L"Array index out of bounds");
      }
      if (4 + len * 2 > remaining) {
         throw EncoderException(L"Array index out of bounds");
      }

      pos += 4;
      remaining -= 4;
      std::wstring result;
      for (size_t i = 0; i < len; i++) {
         result += decodeHLAunicodeChar(pos, remaining);
         pos += 2;
         remaining -= 2;
      }
      return result;
   }

   static unsigned int sizeofHLAunicodeString(const std::wstring &value)
   {
      return static_cast<unsigned int>(value.length()) * 2 + 4;
   }

   static unsigned int boundaryofHLAunicodeString()
   {
      return 4;
   }

   static void appendInt(std::vector<Octet> &buffer, Integer32 value)
   {
      buffer.push_back((char) ((value >> 24) & 0xFF));
      buffer.push_back((char) ((value >> 16) & 0xFF));
      buffer.push_back((char) ((value >> 8) & 0xFF));
      buffer.push_back((char) ((value >> 0) & 0xFF));
   }

   static Integer32 decodeInt(std::vector<Octet> const &buffer, size_t index)
   {
      auto p2 = (unsigned char *) &buffer[index];

      Integer32 value = p2[0] << 24 | p2[1] << 16 | p2[2] << 8 | p2[3];
      return value;
   }

   static inline void appendBytes(std::vector<Octet> &wrapper, const char *bytes, size_t length)
   {
      if (length != 0) {
         // Resize the buffer and then write to it (this is efficient if caller
         // has already reserved the space - otherwise buffer may re-alloc)
         size_t currentBufferSize = wrapper.size();
         wrapper.resize(currentBufferSize + length);
         memcpy(&wrapper[currentBufferSize], bytes, length);
      }
   }

   static inline void insertBytes(std::vector<Octet> &wrapper, size_t length)
   {
      // Resize the buffer and then write to it (this is efficient if caller
      // has already reserved the space - otherwise buffer may re-alloc)
      size_t currentBufferSize = wrapper.size();
      wrapper.resize(currentBufferSize + length);
   }

   static inline size_t paddingSizeInBytes(size_t pos, size_t octetBoundary)
   {
      size_t alignedPos = pos;
      while ((alignedPos % octetBoundary) != 0) {
         alignedPos++;
      }
      return alignedPos - pos;
   }

private:

   typedef RTI_NAMESPACE::EncoderException EncoderException;
};
