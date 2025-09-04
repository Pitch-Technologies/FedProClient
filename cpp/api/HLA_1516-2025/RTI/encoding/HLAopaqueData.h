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

namespace rti1516_2025
{
   // Forward Declarations
   class VariableLengthData;
   class HLAopaqueDataImplementation;

   // Interface for the HLAopaqueData basic data element
   class RTI_EXPORT HLAopaqueData : public rti1516_2025::DataElement
   {
   public:

      // Constructor: Default
      // Uses internal memory
      HLAopaqueData ();

      // Constructor: Initial Value
      // Uses internal memory
      HLAopaqueData (
         const Octet* inData,
         size_t dataSize);

      // Constructor: Use external memory with buffer and data of given lengths.
      // This instance changes or reflects changes to contents of external memory.
      // Changes to external memory are reflected in subsequent encodings.
      // Changes to encoder (i.e., set or decode) are reflected in external memory.
      // Caller is responsible for ensuring that the external memory is
      // valid for the lifetime of this object or until this object acquires
      // new memory through setDataPointer.
      // Buffer length indicates size of memory; data length indicates size of
      // data stored in memory.
      // Exception is thrown for null memory or zero buffer size.
       /** @throws EncoderException */
      HLAopaqueData (
         Octet** inData,
         size_t bufferSize,
         size_t dataSize);

      // Constructor: Copy
      // Uses internal memory
      HLAopaqueData (
         HLAopaqueData const & rhs);

      // Caller is free to delete rhs.
      ~HLAopaqueData() override;

      // Return a new copy of the DataElement
      std::unique_ptr<DataElement> clone () const override;

      // Return the encoding of this element in a VariableLengthData
       /** @throws EncoderException */
      VariableLengthData encode () const override;

      // Encode this element into an existing VariableLengthData
       /** @throws EncoderException */
      void encode (
         VariableLengthData& inData) const override;

      // Encode this element and append it to a buffer.
       /** @throws EncoderException */
      void encodeInto (
         std::vector<Octet>& buffer) const override;

      // Decode this element from the RTI's VariableLengthData.
       /** @throws EncoderException */
      HLAopaqueData& decode (
         VariableLengthData const & inData) override;

      // Decode this element starting at the index in the provided buffer
       /** @throws EncoderException */
      size_t decodeFrom (
         std::vector<Octet> const & buffer,
         size_t index) override;

      // Return the size in bytes of this element's encoding.
       /** @throws EncoderException */
      size_t getEncodedLength () const override;

      // Return the octet boundary of this element.
      unsigned int getOctetBoundary () const override;

      // Return the length of the contained buffer
      virtual size_t bufferLength () const;

      // Return the length of the data stored in the buffer
      virtual size_t dataLength () const;

      // Change memory to use given external memory
      // Changes to this instance will be reflected in external memory
      // Caller is responsible for ensuring that the data that is
      // pointed to is valid for the lifetime of this object, or past
      // the next time this object is given new data.
      // Buffer length indicates size of memory; data length indicates size of
      // data stored in memory.
      // Exception is thrown for null memory or zero buffer size.
       /** @throws EncoderException */
      virtual HLAopaqueData& setDataPointer (
         Octet** inData,
         size_t bufferSize,
         size_t dataSize);

      // Set the data to be encoded.
      virtual HLAopaqueData& set(
         const Octet* inData,
         size_t dataSize);

      // Return a reference to the contained array
      virtual const Octet* get () const;

      // Assignment Operator not allowed
      HLAopaqueData& operator=(
         HLAopaqueData const & rhs) = delete;

   protected:

      HLAopaqueDataImplementation* _impl;
   };
}



