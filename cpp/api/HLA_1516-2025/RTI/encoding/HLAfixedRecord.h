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
   class HLAfixedRecordImplementation;

   // Interface for the HLAfixedRecord complex data element
   class RTI_EXPORT HLAfixedRecord : public rti1516_2025::DataElement
   {
   public:

      // Default Constructor
      HLAfixedRecord ();

      // Copy Constructor
      HLAfixedRecord (
         HLAfixedRecord const & rhs);

      // Destructor
      ~HLAfixedRecord () override;

      // Return a new copy of the DataElement
      std::unique_ptr<DataElement> clone () const override;

      // Encode this element into a new VariableLengthData
       /** @throws EncoderException */
      VariableLengthData encode () const override;

      // Encode this element into an existing VariableLengthData
       /** @throws EncoderException */
      void encode (
         VariableLengthData& inData) const override;

      // Encode this element and append it to a buffer
       /** @throws EncoderException */
      void encodeInto (
         std::vector<Octet>& buffer) const override;

      // Decode this element from the RTI's VariableLengthData.
       /** @throws EncoderException */
      HLAfixedRecord& decode (
         VariableLengthData const & inData) override;

      // Decode this element starting at the index in the provided buffer
       /** @throws EncoderException */
      size_t decodeFrom (
         std::vector<Octet> const & buffer,
         size_t index) override;

      // Return the size in bytes of this record's encoding.
       /** @throws EncoderException */
      size_t getEncodedLength () const override;

      // Return the octet boundary of this element.
      unsigned int getOctetBoundary () const override;

      // Return true if given element is same type as this; otherwise, false.
      bool isSameTypeAs(
         DataElement const& inData ) const override;

      // Return true if given element is same type as the indexed element;
      // otherwise, false.
      virtual bool hasElementSameTypeAs(size_t index,
         DataElement const& inData ) const;

      // Return the number of elements in this fixed record.
      virtual size_t size () const;

      // Append a copy of the dataElement instance to this fixed record.
      virtual HLAfixedRecord& appendElement (const DataElement& dataElement);

      // Append the dataElement instance to this fixed record.
      // Null pointer results in an exception.
      virtual HLAfixedRecord& appendElementPointer (DataElement* dataElement);

      // Sets the element at the given index to a copy of the given element instance
      // Element must match prototype of existing element at this index.
       /** @throws EncoderException */
      virtual HLAfixedRecord& set (size_t index,
         const DataElement& dataElement);

      // Sets the element at the given index to the given element instance
      // Element must match prototype of existing element at this index.
      // Null pointer results in an exception.
       /** @throws EncoderException */
      virtual HLAfixedRecord& setElementPointer (size_t index, DataElement* dataElement);

      // Return a const reference to the element at the specified index.
      // Must use set to change element.
       /** @throws EncoderException */
      virtual const DataElement& get (
         size_t index) const;

      // Return a const reference to the element instance at the specified index.
      // Must use set to change element.
       /** @throws EncoderException */
      DataElement const& operator [](size_t index) const;

      // Assignment Operator not allowed
      HLAfixedRecord& operator=(
         HLAfixedRecord const & rhs) = delete;

   protected:

      HLAfixedRecordImplementation* _impl;
   };
}



