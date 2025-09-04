#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-2025".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

#include <RTI/encoding/DataElement.h>
#include <RTI/encoding/EncodingConfig.h>
#include <RTI/SpecificConfig.h>
#include <string>

// The following macro is used to define each of the encoding helper
// classes for basic C++ datatypes, e.g. HLAinteger16LE, HLAunicodeString,
// etc. Each kind of encoding helper contains the same set of operators
// and functions, but each is a separate class for type safety.
// Encoder uses either internal memory for value of native type or
// uses a supplied references to external memory of the native type.
// When used with a reference, the encoding and decoding is performed using
// the referenced instance of the native type.
#define DEFINE_ENCODING_HELPER_CLASS(EncodableDataType, SimpleDataType)       \
                                                                              \
/* Forward declaration for the RTI-internal class used to implement        */ \
/* a specific kind of encoding helper                                      */ \
class EncodableDataType##Implementation;                                      \
                                                                              \
class RTI_EXPORT EncodableDataType : public rti1516_2025::DataElement         \
{                                                                             \
public:                                                                       \
                                                                              \
   /* Constructor: Default                                                 */ \
   /* Uses internal memory.                                                */ \
   EncodableDataType();                                                       \
                                                                              \
   /* Constructor: Initial Value                                           */ \
   /* Uses internal memory.                                                */ \
   explicit EncodableDataType (                                               \
      SimpleDataType const & inData);                                         \
                                                                              \
   /* Constructor: External memory                                         */ \
   /* This instance changes or is changed by contents of external memory.  */ \
   /* Caller is responsible for ensuring that the external memory is       */ \
   /* valid for the lifetime of this object or until this object acquires  */ \
   /* new memory through setDataPointer.                                   */ \
   /* A null value will construct instance to use internal memory.         */ \
   explicit EncodableDataType (                                               \
      SimpleDataType* inData);                                                \
                                                                              \
   /* Constructor: Copy                                                    */ \
   /* Uses internal memory.                                                */ \
   EncodableDataType (                                                        \
      EncodableDataType const & rhs);                                         \
                                                                              \
   /* Destructor                                                           */ \
   virtual ~EncodableDataType ();                                             \
                                                                              \
   /* Assignment Operator                                                  */ \
   /* Uses existing memory of this instance.                               */ \
   EncodableDataType& operator=(                                              \
      EncodableDataType const & rhs);                                         \
                                                                              \
   /* Return a new copy of the DataElement                                 */ \
   /* Copy uses internal memory.                                           */ \
   virtual std::unique_ptr<DataElement> clone () const;                         \
                                                                              \
   /* Encode this element into a new VariableLengthData                    */ \
   /** @throws EncoderException */                                            \
   virtual VariableLengthData encode () const;                                \
                                                                              \
   /* Encode this element into an existing VariableLengthData              */ \
   /** @throws EncoderException */                                            \
   virtual void encode (                                                      \
      VariableLengthData& inData) const;                                      \
                                                                              \
   /* Encode this element and append it to a buffer                        */ \
   /** @throws EncoderException */                                            \
   virtual void encodeInto (                                                  \
      std::vector<Octet>& buffer) const;                                      \
                                                                              \
   /* Decode this element from the RTI's VariableLengthData.               */ \
   /** @throws EncoderException */                                            \
   virtual EncodableDataType& decode (                                        \
      VariableLengthData const & inData);                                     \
                                                                              \
   /* Decode this element starting at the index in the provided buffer     */ \
   /* Return the index immediately after the decoded data.                 */ \
   /** @throws EncoderException */                                            \
   virtual size_t decodeFrom (                                                \
      std::vector<Octet> const & buffer,                                      \
      size_t index);                                                          \
                                                                              \
   /* Return the size in bytes of this element's encoding.                 */ \
   /** @throws EncoderException */                                            \
   virtual size_t getEncodedLength () const;                                  \
                                                                              \
   /* Return the octet boundary of this element.                           */ \
   virtual unsigned int getOctetBoundary () const;                            \
                                                                              \
   /* Return a hash of the encoded data                                    */ \
   /* Provides mechanism to map DataElement discriminants to variants      */ \
   /* in VariantRecord.                                                    */ \
   virtual Integer64 hash() const;                                            \
                                                                              \
   /* Change this instance to use supplied external memory.                */ \
   /* Caller is responsible for ensuring that the external memory is       */ \
   /* valid for the lifetime of this object or until this object acquires  */ \
   /* new memory through this call.                                        */ \
   /* Null pointer results in an exception.                                */ \
   /** @throws EncoderException */                                            \
   virtual void setDataPointer (                                              \
      SimpleDataType* inData);                                                \
                                                                              \
   /* Set the value to be encoded.                                         */ \
   /* If this element uses external memory, the memory will be modified.   */ \
   virtual EncodableDataType & set (                                          \
      SimpleDataType inData);                                                 \
                                                                              \
   /* Get the value from encoded data.                                     */ \
   virtual SimpleDataType get () const;                                       \
                                                                              \
   /* Assignment of the value to be encoded data.                          */ \
   /* If this element uses external memory, the memory will be modified.   */ \
   EncodableDataType & operator= (                                            \
      SimpleDataType rhs);                                                    \
                                                                              \
   /* Conversion operator to SimpleDataType                                */ \
   /* Return value from encoded data.                                      */ \
   operator SimpleDataType () const;                                          \
                                                                              \
protected:                                                                    \
                                                                              \
   EncodableDataType##Implementation* _impl;                                  \
};


namespace rti1516_2025
{
   // Forward Declarations
   class VariableLengthData;

   // All of the RTI API's Basic DataType Encoding Helper classes are
   // defined by invoking the macro above.
   DEFINE_ENCODING_HELPER_CLASS( HLAASCIIchar, char )
   DEFINE_ENCODING_HELPER_CLASS( HLAASCIIstring, std::string )
   DEFINE_ENCODING_HELPER_CLASS( HLAboolean, bool )
   DEFINE_ENCODING_HELPER_CLASS( HLAbyte, Octet )
   DEFINE_ENCODING_HELPER_CLASS( HLAfloat32BE, float )
   DEFINE_ENCODING_HELPER_CLASS( HLAfloat32LE, float )
   DEFINE_ENCODING_HELPER_CLASS( HLAfloat64BE, double )
   DEFINE_ENCODING_HELPER_CLASS( HLAfloat64LE, double )
   DEFINE_ENCODING_HELPER_CLASS( HLAinteger16LE, Integer16 )
   DEFINE_ENCODING_HELPER_CLASS( HLAinteger16BE, Integer16 )
   DEFINE_ENCODING_HELPER_CLASS( HLAinteger32BE, Integer32 )
   DEFINE_ENCODING_HELPER_CLASS( HLAinteger32LE, Integer32 )
   DEFINE_ENCODING_HELPER_CLASS( HLAinteger64BE, Integer64 )
   DEFINE_ENCODING_HELPER_CLASS( HLAinteger64LE, Integer64 )
   DEFINE_ENCODING_HELPER_CLASS( HLAunsignedInteger16LE, UnsignedInteger16 )
   DEFINE_ENCODING_HELPER_CLASS( HLAunsignedInteger16BE, UnsignedInteger16 )
   DEFINE_ENCODING_HELPER_CLASS( HLAunsignedInteger32BE, UnsignedInteger32 )
   DEFINE_ENCODING_HELPER_CLASS( HLAunsignedInteger32LE, UnsignedInteger32 )
   DEFINE_ENCODING_HELPER_CLASS( HLAunsignedInteger64BE, UnsignedInteger64 )
   DEFINE_ENCODING_HELPER_CLASS( HLAunsignedInteger64LE, UnsignedInteger64 )
   DEFINE_ENCODING_HELPER_CLASS( HLAoctet, Octet )
   DEFINE_ENCODING_HELPER_CLASS( HLAoctetPairBE, OctetPair )
   DEFINE_ENCODING_HELPER_CLASS( HLAoctetPairLE, OctetPair )
   DEFINE_ENCODING_HELPER_CLASS( HLAunicodeChar, wchar_t )
   DEFINE_ENCODING_HELPER_CLASS( HLAunicodeString, std::wstring )

}



