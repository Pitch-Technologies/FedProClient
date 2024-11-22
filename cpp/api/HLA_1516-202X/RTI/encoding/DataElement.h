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
#include <RTI/encoding/EncodingConfig.h>
#include <RTI/encoding/EncodingExceptions.h>
#include <memory>


namespace rti1516_202X
{
   // Forward Declarations
   class VariableLengthData;

   // Interface provided by all HLA data elements.
   class RTI_EXPORT DataElement
   {
   public:

      // Destructor
      virtual ~DataElement () = 0;

      // Return a new copy of the DataElement
      virtual std::unique_ptr<DataElement> clone () const = 0;

      // Encode this element into a new VariableLengthData
       /** @throws EncoderException */
      virtual VariableLengthData encode () const = 0;

      // Encode this element into an existing VariableLengthData
       /** @throws EncoderException */
      virtual void encode (
         VariableLengthData& inData) const = 0;

      // Encode this element and append it to a buffer
       /** @throws EncoderException */
      virtual void encodeInto (
         std::vector<Octet>& buffer) const = 0;

      // Decode this element from the RTI's VariableLengthData.
       /** @throws EncoderException */
      virtual DataElement& decode (
         VariableLengthData const & inData) = 0;

      // Decode this element starting at the index in the provided buffer
       /** @throws EncoderException */
      virtual size_t decodeFrom (
         std::vector<Octet> const & buffer,
         size_t index) = 0;

      // Return the size in bytes of this element's encoding.
       /** @throws EncoderException */
      virtual size_t getEncodedLength () const = 0;

      // Return the octet boundary of this element.
      virtual unsigned int getOctetBoundary () const = 0;

      // Return true if given element is same type as this; otherwise, false.
      virtual bool isSameTypeAs(
         DataElement const& inData ) const;

      // Return a hash of the encoded data
      // Provides mechanism to map DataElement discriminants to variants
      // in VariantRecord.
      virtual Integer64 hash() const;

   };
}



