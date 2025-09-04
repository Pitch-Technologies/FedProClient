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
   class HLAextendableVariantRecordImplementation;

   // Interface for the HLAextendableVariantRecord complex data element
   class RTI_EXPORT HLAextendableVariantRecord : public rti1516_2025::DataElement
   {
   public:

      // Constructor which accepts a prototype element for discriminants.
      // A clone of the given element acts to validate the discriminant type.
      explicit HLAextendableVariantRecord (
         DataElement const& discriminantPrototype);

      // Copy Constructor
      HLAextendableVariantRecord (
         HLAextendableVariantRecord const & rhs);

      // Destructor
      ~HLAextendableVariantRecord () override;

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
      HLAextendableVariantRecord& decode (
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

      // Return true if given element is same type as this; otherwise, false.
      bool isSameTypeAs(
         DataElement const& inData ) const override;

      // Return true if given element is same type as specified variant; otherwise, false.
      virtual bool isSameTypeAs(DataElement const& discriminant, 
         DataElement const& inData ) const;

      // Return true if given element matches prototype of this array.
      virtual bool hasMatchingDiscriminantTypeAs(DataElement const& dataElement ) const;

      // Add a new discriminant/variant pair: adds a mapping between the given
      // unique discriminant and a copy of the value element.
      // When encoding, the last discriminant specified (either by adding or setDescriminant)
      // determines the value to be encoded.
      // When decoding, the encoded discriminant will determine which variant is
      // used. The getDescriminant call indicates the variant data element returned
      // by getValue.
      // Discriminants must match prototype
       /** @throws EncoderException */
      virtual HLAextendableVariantRecord& addVariant (
         const DataElement& discriminant,
         const DataElement& valuePrototype);

      // Add a new discriminant/variant pair: adds a mapping between the given
      // unique discriminant and the given value element.
      // When encoding, the last discriminant specified (either by adding or
      // setVariant, or setDescriminant) determines the value to be encoded.
      // When decoding, the encoded discriminant will determine which variant is
      // used. The getDescriminant call indicates the variant data element
      // returned by getValue.
      // Discriminants must match prototype
      // Caller is responsible for ensuring that the external memory is
      // valid for the lifetime of this object or until the variant for the
      // given discriminant acquires new memory through setVariantPointer.
      // Null pointer results in an exception.
       /** @throws EncoderException */
      virtual HLAextendableVariantRecord& addVariantPointer (
         const DataElement& discriminant,
         DataElement* valuePtr);

      // Set the current value of the discriminant (specifies the type of the value)
      // Discriminants must match prototype
       /** @throws EncoderException */
      virtual HLAextendableVariantRecord& setDiscriminant (
         const DataElement& discriminant);

      // Sets the variant with the given discriminant to a copy of the given value
      // Discriminant must match prototype and value must match its variant
       /** @throws EncoderException */
      virtual HLAextendableVariantRecord& setVariant (
         const DataElement& discriminant,
         DataElement const& value);

      // Sets the variant with the given discriminant to the given value
      // Discriminant must match prototype and value must match its variant
      // Caller is responsible for ensuring that the external memory is
      // valid for the lifetime of this object or until the variant for the
      // given discriminant acquires new memory through this call.
      // Null pointer results in an exception.
       /** @throws EncoderException */
      virtual HLAextendableVariantRecord& setVariantPointer (
         const DataElement& discriminant,
         DataElement* valuePtr);

      // Return a reference to the discriminant element
      virtual const DataElement& getDiscriminant () const;

      // Return a reference to the variant element.
      // Exception thrown if encoded discriminant is not mapped to a value.
       /** @throws EncoderException */
      virtual const DataElement& getVariant() const;

      // Default constructor not allowed
      HLAextendableVariantRecord () = delete;

      // Assignment Operator not allowed
      HLAextendableVariantRecord& operator=(
         HLAextendableVariantRecord const & rhs) = delete;

   protected:

      HLAextendableVariantRecordImplementation* _impl;
   };

   template<class T> class HLAextendableVariantRecordT : public rti1516_2025::HLAextendableVariantRecord
   {
   public:
      // Constructor
      HLAextendableVariantRecordT() : HLAextendableVariantRecord(T())
      {
      }

      // Copy Constructor
      // Copied elements use internal memory
      HLAextendableVariantRecordT(
         HLAextendableVariantRecordT<T> const & rhs) :
         HLAextendableVariantRecord(rhs)
      {
      }

      // Destructor
      ~HLAextendableVariantRecordT() override = default;

      // Return a new copy of the variant record
      std::unique_ptr<DataElement> clone() const override
      {
         return std::make_unique<HLAextendableVariantRecordT>(*this);
      }

      // Return true if given element is same type as this; otherwise, false.
      bool isSameTypeAs(
         DataElement const& inData) const override
      {
         return typeid(*this) == typeid(inData);
      }

      // Return true if given element is same type as specified variant; otherwise, false.
      virtual bool isSameTypeAs(T const& discriminant,
         DataElement const& inData) const
      {
         return HLAextendableVariantRecord::isSameTypeAs(discriminant, inData);
      }

      // Add a new discriminant/variant pair: adds a mapping between the given
      // unique discriminant and a copy of the value element.
      // When encoding, the last discriminant specified (either by adding or setDescriminant)
      // determines the value to be encoded.
      // When decoding, the encoded discriminant will determine which variant is
      // used. The getDescriminant call indicates the variant data element returned
      // by getValue.
      virtual HLAextendableVariantRecordT<T>& addVariant(
         const T& discriminant,
         const DataElement& valuePrototype)
      {
         HLAextendableVariantRecord::addVariant(discriminant, valuePrototype);
         return *this;
      }

      // Add a new discriminant/variant pair: adds a mapping between the given
      // unique discriminant and the given value element.
      // When encoding, the last discriminant specified (either by adding or
      // setVariant, or setDescriminant) determines the value to be encoded.
      // When decoding, the encoded discriminant will determine which variant is
      // used. The getDescriminant call indicates the variant data element
      // returned by getValue.
      // Caller is responsible for ensuring that the external memory is
      // valid for the lifetime of this object or until the variant for the
      // given discriminant acquires new memory through setVariantPointer.
      // Null pointer results in an exception.
      virtual HLAextendableVariantRecordT<T>& addVariantPointer(
         const T& discriminant,
         DataElement* valuePtr)
      {
         HLAextendableVariantRecord::addVariantPointer(discriminant, valuePtr);
         return *this;
      }

      // Set the current value of the discriminant (specifies the type of the value)
      virtual HLAextendableVariantRecordT<T>& setDiscriminant(
         const T& discriminant)
      {
         HLAextendableVariantRecord::setDiscriminant(discriminant);
         return *this;
      }

      // Sets the variant with the given discriminant to a copy of the given value
      // Value must match specified variant
      virtual HLAextendableVariantRecordT<T>& setVariant(
         const T& discriminant,
         DataElement const& value)
      {
         HLAextendableVariantRecord::setVariant(discriminant, value);
         return *this;
      }

      // Sets the variant with the given discriminant to the given value
      // Value must match specified variant
      // Caller is responsible for ensuring that the external memory is
      // valid for the lifetime of this object or until the variant for the
      // given discriminant acquires new memory through this call.
      // Null pointer results in an exception.
      virtual HLAextendableVariantRecordT<T>& setVariantPointer(
         const T& discriminant,
         DataElement* valuePtr)
      {
         HLAextendableVariantRecord::setVariantPointer(discriminant, valuePtr);
         return *this;
      }

      // Return a reference to the discriminant element
      const T& getDiscriminant() const override
      {
         return dynamic_cast<const T &>(HLAextendableVariantRecord::getDiscriminant());
      }

      // Assignment Operator not allowed
      HLAextendableVariantRecordT<T>& operator=(
         HLAextendableVariantRecordT<T> const & rhs) = delete;
   };

}



