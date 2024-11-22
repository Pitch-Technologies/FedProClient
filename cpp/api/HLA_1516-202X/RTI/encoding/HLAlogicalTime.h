#pragma once
/***********************************************************************
   The IEEE hereby grants a general, royalty-free license to copy, distribute,
   display and make derivative works from this material, for all purposes,
   provided that any use of the material contains the following
   attribution: "Reprinted with permission from IEEE 1516.1(TM)-202X".
   Should you require additional information, contact the Manager, Standards
   Intellectual Property, IEEE Standards Association (stds-ipr@ieee.org).
***********************************************************************/

#include <RTI/encoding/DataElement.h>
#include <RTI/encoding/EncodingConfig.h>
#include <RTI/SpecificConfig.h>

namespace rti1516_202X
{
   // Forward Declarations
    class LogicalTime;
    class VariableLengthData;
    class HLAlogicalTimeImplementation;
    class RTIambassador;

    // Interface for the HLAlogicalTime complex data element
    class RTI_EXPORT HLAlogicalTime : public rti1516_202X::DataElement
    {
    public:
        // Constructor.
        explicit HLAlogicalTime(
            const RTIambassador* ambassador);

        // Constructor which accepts initial value
        explicit HLAlogicalTime(
            const RTIambassador* ambassador,
            const LogicalTime& logicalTime);

        // Copy Constructor
        // Copied elements use internal memory
        HLAlogicalTime(
            HLAlogicalTime const & rhs);

        // Destructor
        ~HLAlogicalTime() override;

        // Return a new copy of the array
        std::unique_ptr<DataElement> clone() const override;

        // Encode this element into a new VariableLengthData
        VariableLengthData encode() const override;

        // Encode this element into an existing VariableLengthData
        void encode(
            VariableLengthData& inData) const override;

        // Encode this element and append it to a buffer
        void encodeInto(
            std::vector<Octet>& buffer) const override;

        // Decode this element from the RTI's VariableLengthData.
        HLAlogicalTime& decode(
            VariableLengthData const & inData) override;

        // Decode this element starting at the index in the provided buffer
        size_t decodeFrom(
            std::vector<Octet> const & buffer,
            size_t index) override;

        // Return the size in bytes of this element's encoding.
        size_t getEncodedLength() const override;

        // Return the octet boundary of this element.
        unsigned int getOctetBoundary() const override;

        // Return true if given element is same type as this; otherwise, false.
        bool isSameTypeAs(
            DataElement const& inData) const override;

        // Sets the value to be encoded
        virtual HLAlogicalTime& set(const LogicalTime& value);

        // Gets the value from encoded data
        virtual void get(LogicalTime& time);

        // Default Constructor not allowed
        HLAlogicalTime() = delete;

        // Assignment Operator not allowed
        HLAlogicalTime& operator=(
            HLAlogicalTime const & rhs) = delete;

    protected:

        HLAlogicalTimeImplementation* _impl;
    };
}



