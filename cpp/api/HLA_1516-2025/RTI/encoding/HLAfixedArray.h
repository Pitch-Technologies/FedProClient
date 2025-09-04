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
   class HLAfixedArrayImplementation;

   // Interface for the HLAfixedArray complex data element
   class RTI_EXPORT HLAfixedArray : public rti1516_2025::DataElement
   {
   public:
      // Constructor which accepts a prototype element and size
      // that specifies the type and number of elements to be stored in the array.
      // A clone of the given element works as a prototype.
      explicit HLAfixedArray (
         const DataElement& protoType,
         size_t length );

      // Copy Constructor
      // Copied elements use internal memory
      HLAfixedArray (
         HLAfixedArray const & rhs);

      // Destructor
      ~HLAfixedArray() override;

      // Return a new copy of the array
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
      HLAfixedArray& decode (
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

      // Return true if given element matches prototype of this array.
      virtual bool hasPrototypeSameTypeAs(DataElement const& dataElement ) const;

      // Return the number of elements in this fixed array.
      virtual size_t size () const;

      // Sets the element at the given index to a copy of the given element instance
      // Element must match prototype.
      // If indexed element uses external memory, the memory will be modified.
       /** @throws EncoderException */
      virtual HLAfixedArray& set (size_t index,
         const DataElement& dataElement);

      // Sets the element at the given index to the given element instance
      // Element must match prototype.
      // Null pointer results in an exception.
      // Caller is responsible for ensuring that the external memory is
      // valid for the lifetime of this object or until the indexed element
      // acquires new memory through this call.
       /** @throws EncoderException */
      virtual HLAfixedArray& setElementPointer (size_t index,
         DataElement* dataElement);

      // Return a reference to the element instance at the specified index.
      // Access of indexed element that has not been set will set that index
      // with a clone of prototype and return it.
      // Must use set to change element.
       /** @throws EncoderException */
      virtual const DataElement& get (
         size_t index) const;

      // Return a const reference to the element instance at the specified index.
      // Access of indexed element that has not been set will set that index
      // with a clone of prototype and return it.
      // Must use set to change element.
       /** @throws EncoderException */
      virtual DataElement const& operator [](size_t index) const;

   private:

      // Default Constructor not allowed
      HLAfixedArray ();

      // Assignment Operator not allowed
      HLAfixedArray& operator=(
         HLAfixedArray const & rhs);

   protected:

      HLAfixedArrayImplementation* _impl;
   };

   template<class T> class HLAfixedArrayT : public rti1516_2025::HLAfixedArray
   {
   public:
      // Constructor which accepts a size that specifies
      // the number of elements to be stored in the array.
      explicit HLAfixedArrayT(
         size_t length) : HLAfixedArray(T(), length)
      {
      }

      // Copy Constructor
      // Copied elements use internal memory
      HLAfixedArrayT(
         HLAfixedArrayT<T> const & rhs) :
         HLAfixedArray(rhs)
      {
      }

      // Destructor
      ~HLAfixedArrayT() override = default;

      // Return a new copy of the array
      std::unique_ptr<DataElement> clone() const override
      {
         return std::make_unique<HLAfixedArrayT>(*this);
      }

      // Return true if given element is same type as this; otherwise, false.
      bool isSameTypeAs(
         DataElement const& inData) const override
      {
         return typeid(*this) == typeid(inData);
      }

      // Sets the element at the given index to a copy of the given element instance.
      // If indexed element uses external memory, the memory will be modified.
      virtual HLAfixedArrayT<T>& set(size_t index,
         const T& dataElement)
      {
         HLAfixedArray::set(index, dataElement);
         return *this;
      }

      // Sets the element at the given index to the given element instance.
      // Null pointer results in an exception.
      // Caller is responsible for ensuring that the external memory is
      // valid for the lifetime of this object or until the indexed element
      // acquires new memory through this call.
      virtual HLAfixedArrayT<T>& setElementPointer(size_t index,
         T* dataElement)
      {
         HLAfixedArray::setElementPointer(index, dataElement);
         return *this;
      }

      // Return a reference to the element instance at the specified index.
      // Access of indexed element that has not been set will set that index
      // with a default constructed element and return it.
      // Must use set to change element.
      const T& get(
         size_t index) const override
      {
         return dynamic_cast<const T &>(HLAfixedArray::get(index));
      }

      // Return a const reference to the element instance at the specified index.
      // Access of indexed element that has not been set will set that index
      // with a default constructed element and return it.
      // Must use set to change element.
      T const& operator [](size_t index) const override
      {
         return dynamic_cast<T const &>(HLAfixedArray::operator[](index));
      }

      // Assignment Operator not allowed
      HLAfixedArrayT<T>& operator=(
         HLAfixedArrayT<T> const& rhs) = delete;
   };
}



