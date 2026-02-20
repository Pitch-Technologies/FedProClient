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

// Silence clang-tidy issues reported for gtest macros.
// NOLINTBEGIN(cppcoreguidelines-avoid-non-const-global-variables)
// Silence clang-tidy issues reported for standard HLA exception.
// NOLINTBEGIN(hicpp-exception-baseclass)

#if (RTI_HLA_VERSION >= 2025)
#include <RTI/auth/AuthorizationResult.h>
#include <RTI/auth/Authorizer.h>
#include <RTI/auth/AuthorizerFactory.h>
#include <RTI/auth/HLAauthorizerFactoryFactory.h>
#endif
#include <RTI/encoding/BasicDataElements.h>
#if (RTI_HLA_VERSION >= 2025)
#include <RTI/encoding/HLAextendableVariantRecord.h>
#endif
#include <RTI/encoding/HLAfixedArray.h>
#include <RTI/encoding/HLAfixedRecord.h>
#if (RTI_HLA_VERSION >= 2025)
#include <RTI/encoding/HLAlogicalTime.h>
#include <RTI/encoding/HLAlogicalTimeInterval.h>
#endif
#include <RTI/encoding/HLAopaqueData.h>
#include <RTI/encoding/HLAvariableArray.h>
#include <RTI/encoding/HLAvariantRecord.h>
#include <RTI/time/HLAfloat64Interval.h>
#include <RTI/time/HLAfloat64Time.h>
#include <RTI/time/HLAfloat64TimeFactory.h>
#include <RTI/time/HLAinteger64Interval.h>
#include <RTI/time/HLAinteger64Time.h>
#include <RTI/time/HLAinteger64TimeFactory.h>
#include <RTI/NullFederateAmbassador.h>
#include <RTI/RTI1516.h>

#include <gtest/gtest.h>

#include <algorithm>
#include <fstream>

class TestLibRti : public ::testing::Test
{
};

#define LINK_CLASS_COPY_CONSTRUCTOR(CLASS)         \
{                                                  \
   CLASS * pointer{};                              \
   if ((std::min)(pointer, pointer)) {             \
      CLASS{*pointer};                             \
   }                                               \
}                                                  \
static_assert(true, "Require semicolon after macro")

#define LINK_EXCEPTION_COPY_CONSTRUCTOR(CLASS)     \
{                                                  \
   CLASS * pointer{};                              \
   if ((std::min)(pointer, pointer)) {             \
      throw CLASS{*pointer};                       \
   }                                               \
}                                                  \
static_assert(true, "Require semicolon after macro")

#define LINK_CLASS_DESTRUCTOR(CLASS)               \
{                                                  \
   CLASS * pointer{};                              \
   if ((std::min)(pointer, pointer)) {             \
      std::unique_ptr<CLASS>{pointer};             \
   }                                               \
}                                                  \
static_assert(true, "Require semicolon after macro")

#define LINK_CLASS_WIDE_STREAM_OPERATOR(CLASS)     \
{                                                  \
   CLASS * pointer{};                              \
   if ((std::min)(pointer, pointer)) {             \
      std::wofstream stream;                       \
      stream << *pointer;                          \
   }                                               \
}                                                  \
static_assert(true, "Require semicolon after macro")

// Begin a block of non-reachable code to exercise
// linking with the given class.
#define LINK_ONLY_BLOCK(TYPE, POINTER_NAME)                                                      \
if (TYPE * POINTER_NAME = (std::min)(static_cast<TYPE *>(nullptr), static_cast<TYPE *>(nullptr)))

TEST_F(TestLibRti, linkAllClasses)
{
   // Link with methods and operators to verify that this RTI library
   // implements all relevant classes.

   using namespace RTI_NAMESPACE;

   LINK_EXCEPTION_COPY_CONSTRUCTOR(AlreadyConnected);
   LINK_CLASS_DESTRUCTOR(AlreadyConnected);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AlreadyConnected);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AsynchronousDeliveryAlreadyDisabled);
   LINK_CLASS_DESTRUCTOR(AsynchronousDeliveryAlreadyDisabled);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AsynchronousDeliveryAlreadyDisabled);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AsynchronousDeliveryAlreadyEnabled);
   LINK_CLASS_DESTRUCTOR(AsynchronousDeliveryAlreadyEnabled);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AsynchronousDeliveryAlreadyEnabled);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AttributeAcquisitionWasNotRequested);
   LINK_CLASS_DESTRUCTOR(AttributeAcquisitionWasNotRequested);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AttributeAcquisitionWasNotRequested);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AttributeAlreadyBeingAcquired);
   LINK_CLASS_DESTRUCTOR(AttributeAlreadyBeingAcquired);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AttributeAlreadyBeingAcquired);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AttributeAlreadyBeingChanged);
   LINK_CLASS_DESTRUCTOR(AttributeAlreadyBeingChanged);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AttributeAlreadyBeingChanged);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AttributeAlreadyBeingDivested);
   LINK_CLASS_DESTRUCTOR(AttributeAlreadyBeingDivested);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AttributeAlreadyBeingDivested);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AttributeAlreadyOwned);
   LINK_CLASS_DESTRUCTOR(AttributeAlreadyOwned);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AttributeAlreadyOwned);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AttributeDivestitureWasNotRequested);
   LINK_CLASS_DESTRUCTOR(AttributeDivestitureWasNotRequested);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AttributeDivestitureWasNotRequested);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AttributeNotDefined);
   LINK_CLASS_DESTRUCTOR(AttributeNotDefined);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AttributeNotDefined);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AttributeNotOwned);
   LINK_CLASS_DESTRUCTOR(AttributeNotOwned);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AttributeNotOwned);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AttributeNotPublished);
   LINK_CLASS_DESTRUCTOR(AttributeNotPublished);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AttributeNotPublished);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AttributeRelevanceAdvisorySwitchIsOff);
   LINK_CLASS_DESTRUCTOR(AttributeRelevanceAdvisorySwitchIsOff);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AttributeRelevanceAdvisorySwitchIsOff);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AttributeRelevanceAdvisorySwitchIsOn);
   LINK_CLASS_DESTRUCTOR(AttributeRelevanceAdvisorySwitchIsOn);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AttributeRelevanceAdvisorySwitchIsOn);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AttributeScopeAdvisorySwitchIsOff);
   LINK_CLASS_DESTRUCTOR(AttributeScopeAdvisorySwitchIsOff);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AttributeScopeAdvisorySwitchIsOff);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(AttributeScopeAdvisorySwitchIsOn);
   LINK_CLASS_DESTRUCTOR(AttributeScopeAdvisorySwitchIsOn);
   LINK_CLASS_WIDE_STREAM_OPERATOR(AttributeScopeAdvisorySwitchIsOn);

   // HLA 4 Authorization APIs
#if (RTI_HLA_VERSION >= 2025)
   LINK_CLASS_COPY_CONSTRUCTOR(AuthorizationResult);
   LINK_CLASS_DESTRUCTOR(AuthorizationResult);
   LINK_CLASS_DESTRUCTOR(Authorizer);
   LINK_CLASS_DESTRUCTOR(AuthorizerFactory);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAauthorizerFactoryFactory);
   LINK_CLASS_DESTRUCTOR(HLAauthorizerFactoryFactory);
   LINK_CLASS_COPY_CONSTRUCTOR(Credentials);
   LINK_CLASS_DESTRUCTOR(Credentials);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAnoCredentials);
   LINK_CLASS_DESTRUCTOR(HLAnoCredentials);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAplainTextPassword);
   LINK_CLASS_DESTRUCTOR(HLAplainTextPassword);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidCredentials);
   LINK_CLASS_DESTRUCTOR(InvalidCredentials);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidCredentials);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(Unauthorized);
   LINK_CLASS_DESTRUCTOR(Unauthorized);
   LINK_CLASS_WIDE_STREAM_OPERATOR(Unauthorized);
#endif
   LINK_EXCEPTION_COPY_CONSTRUCTOR(CallNotAllowedFromWithinCallback);
   LINK_CLASS_DESTRUCTOR(CallNotAllowedFromWithinCallback);
   LINK_CLASS_WIDE_STREAM_OPERATOR(CallNotAllowedFromWithinCallback);
#if (RTI_HLA_VERSION >= 2025)
   LINK_CLASS_COPY_CONSTRUCTOR(ConfigurationResult);
   LINK_CLASS_DESTRUCTOR(ConfigurationResult);
#endif
   LINK_EXCEPTION_COPY_CONSTRUCTOR(ConnectionFailed);
   LINK_CLASS_DESTRUCTOR(ConnectionFailed);
   LINK_CLASS_WIDE_STREAM_OPERATOR(ConnectionFailed);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(CouldNotCreateLogicalTimeFactory);
   LINK_CLASS_DESTRUCTOR(CouldNotCreateLogicalTimeFactory);
   LINK_CLASS_WIDE_STREAM_OPERATOR(CouldNotCreateLogicalTimeFactory);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(CouldNotDecode);
   LINK_CLASS_DESTRUCTOR(CouldNotDecode);
   LINK_CLASS_WIDE_STREAM_OPERATOR(CouldNotDecode);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(CouldNotEncode);
   LINK_CLASS_DESTRUCTOR(CouldNotEncode);
   LINK_CLASS_WIDE_STREAM_OPERATOR(CouldNotEncode);
#if (RTI_HLA_VERSION >= 2025)
   LINK_EXCEPTION_COPY_CONSTRUCTOR(CouldNotOpenFOM);
   LINK_CLASS_DESTRUCTOR(CouldNotOpenFOM);
   LINK_CLASS_WIDE_STREAM_OPERATOR(CouldNotOpenFOM);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(ErrorReadingFOM);
   LINK_CLASS_DESTRUCTOR(ErrorReadingFOM);
   LINK_CLASS_WIDE_STREAM_OPERATOR(ErrorReadingFOM);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InconsistentFOM);
   LINK_CLASS_DESTRUCTOR(InconsistentFOM);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InconsistentFOM);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidFOM);
   LINK_CLASS_DESTRUCTOR(InvalidFOM);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidFOM);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidMIM);
   LINK_CLASS_DESTRUCTOR(InvalidMIM);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidMIM);
#else
   LINK_EXCEPTION_COPY_CONSTRUCTOR(CouldNotOpenFDD);
   LINK_CLASS_DESTRUCTOR(CouldNotOpenFDD);
   LINK_CLASS_WIDE_STREAM_OPERATOR(CouldNotOpenFDD);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(ErrorReadingFDD);
   LINK_CLASS_DESTRUCTOR(ErrorReadingFDD);
   LINK_CLASS_WIDE_STREAM_OPERATOR(ErrorReadingFDD);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InconsistentFDD);
   LINK_CLASS_DESTRUCTOR(InconsistentFDD);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InconsistentFDD);
#endif
   LINK_EXCEPTION_COPY_CONSTRUCTOR(CouldNotOpenMIM);
   LINK_CLASS_DESTRUCTOR(CouldNotOpenMIM);
   LINK_CLASS_WIDE_STREAM_OPERATOR(CouldNotOpenMIM);
   LINK_CLASS_DESTRUCTOR(DataElement);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(DeletePrivilegeNotHeld);
   LINK_CLASS_DESTRUCTOR(DeletePrivilegeNotHeld);
   LINK_CLASS_WIDE_STREAM_OPERATOR(DeletePrivilegeNotHeld);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(DesignatorIsHLAstandardMIM);
   LINK_CLASS_DESTRUCTOR(DesignatorIsHLAstandardMIM);
   LINK_CLASS_WIDE_STREAM_OPERATOR(DesignatorIsHLAstandardMIM);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(EncoderException);
   LINK_CLASS_DESTRUCTOR(EncoderException);
   LINK_CLASS_WIDE_STREAM_OPERATOR(EncoderException);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(ErrorReadingMIM);
   LINK_CLASS_DESTRUCTOR(ErrorReadingMIM);
   LINK_CLASS_WIDE_STREAM_OPERATOR(ErrorReadingMIM);
   LINK_CLASS_DESTRUCTOR(Exception);
   LINK_CLASS_WIDE_STREAM_OPERATOR(Exception);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(FederateAlreadyExecutionMember);
   LINK_CLASS_DESTRUCTOR(FederateAlreadyExecutionMember);
   LINK_CLASS_WIDE_STREAM_OPERATOR(FederateAlreadyExecutionMember);
   LINK_CLASS_DESTRUCTOR(FederateAmbassador);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(FederateHandleNotKnown);
   LINK_CLASS_DESTRUCTOR(FederateHandleNotKnown);
   LINK_CLASS_WIDE_STREAM_OPERATOR(FederateHandleNotKnown);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(FederateHasNotBegunSave);
   LINK_CLASS_DESTRUCTOR(FederateHasNotBegunSave);
   LINK_CLASS_WIDE_STREAM_OPERATOR(FederateHasNotBegunSave);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(FederateInternalError);
   LINK_CLASS_DESTRUCTOR(FederateInternalError);
   LINK_CLASS_WIDE_STREAM_OPERATOR(FederateInternalError);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(FederateIsExecutionMember);
   LINK_CLASS_DESTRUCTOR(FederateIsExecutionMember);
   LINK_CLASS_WIDE_STREAM_OPERATOR(FederateIsExecutionMember);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(FederateNameAlreadyInUse);
   LINK_CLASS_DESTRUCTOR(FederateNameAlreadyInUse);
   LINK_CLASS_WIDE_STREAM_OPERATOR(FederateNameAlreadyInUse);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(FederateNotExecutionMember);
   LINK_CLASS_DESTRUCTOR(FederateNotExecutionMember);
   LINK_CLASS_WIDE_STREAM_OPERATOR(FederateNotExecutionMember);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(FederateOwnsAttributes);
   LINK_CLASS_DESTRUCTOR(FederateOwnsAttributes);
   LINK_CLASS_WIDE_STREAM_OPERATOR(FederateOwnsAttributes);
   LINK_CLASS_COPY_CONSTRUCTOR(FederateRestoreStatus);
   LINK_CLASS_DESTRUCTOR(FederateRestoreStatus);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(FederateServiceInvocationsAreBeingReportedViaMOM);
   LINK_CLASS_DESTRUCTOR(FederateServiceInvocationsAreBeingReportedViaMOM);
   LINK_CLASS_WIDE_STREAM_OPERATOR(FederateServiceInvocationsAreBeingReportedViaMOM);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(FederateUnableToUseTime);
   LINK_CLASS_DESTRUCTOR(FederateUnableToUseTime);
   LINK_CLASS_WIDE_STREAM_OPERATOR(FederateUnableToUseTime);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(FederatesCurrentlyJoined);
   LINK_CLASS_DESTRUCTOR(FederatesCurrentlyJoined);
   LINK_CLASS_WIDE_STREAM_OPERATOR(FederatesCurrentlyJoined);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(FederationExecutionAlreadyExists);
   LINK_CLASS_DESTRUCTOR(FederationExecutionAlreadyExists);
   LINK_CLASS_WIDE_STREAM_OPERATOR(FederationExecutionAlreadyExists);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(FederationExecutionDoesNotExist);
   LINK_CLASS_DESTRUCTOR(FederationExecutionDoesNotExist);
   LINK_CLASS_WIDE_STREAM_OPERATOR(FederationExecutionDoesNotExist);
   LINK_CLASS_COPY_CONSTRUCTOR(FederationExecutionInformation);
   LINK_CLASS_DESTRUCTOR(FederationExecutionInformation);
#if (RTI_HLA_VERSION >= 2025)
   LINK_CLASS_COPY_CONSTRUCTOR(FederationExecutionMemberInformation);
   LINK_CLASS_DESTRUCTOR(FederationExecutionMemberInformation);
#endif
   LINK_CLASS_COPY_CONSTRUCTOR(HLAASCIIchar);
   LINK_CLASS_DESTRUCTOR(HLAASCIIchar);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAASCIIchar);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAASCIIstring);
   LINK_CLASS_DESTRUCTOR(HLAASCIIstring);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAboolean);
   LINK_CLASS_DESTRUCTOR(HLAboolean);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAboolean);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAbyte);
   LINK_CLASS_DESTRUCTOR(HLAbyte);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAbyte);
#if (RTI_HLA_VERSION >= 2025)
   LINK_CLASS_COPY_CONSTRUCTOR(HLAextendableVariantRecord);
   LINK_CLASS_DESTRUCTOR(HLAextendableVariantRecord);
#endif
   LINK_CLASS_COPY_CONSTRUCTOR(HLAfixedArray);
   LINK_CLASS_DESTRUCTOR(HLAfixedArray);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAfixedRecord);
   LINK_CLASS_DESTRUCTOR(HLAfixedRecord);
   LINK_ONLY_BLOCK(HLAfixedRecord, pointer) {
      // Subscript operator
      auto & ignoredRef = (*pointer)[0];
   }
   LINK_CLASS_COPY_CONSTRUCTOR(HLAfloat32BE);
   LINK_CLASS_DESTRUCTOR(HLAfloat32BE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAfloat32BE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAfloat32LE);
   LINK_CLASS_DESTRUCTOR(HLAfloat32LE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAfloat32LE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAfloat64BE);
   LINK_CLASS_DESTRUCTOR(HLAfloat64BE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAfloat64BE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAfloat64Interval);
   LINK_CLASS_DESTRUCTOR(HLAfloat64Interval);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAfloat64Interval);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAfloat64LE);
   LINK_CLASS_DESTRUCTOR(HLAfloat64LE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAfloat64LE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAfloat64Time);
   LINK_CLASS_DESTRUCTOR(HLAfloat64Time);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAfloat64Time);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAfloat64TimeFactory);
   LINK_CLASS_DESTRUCTOR(HLAfloat64TimeFactory);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAinteger16BE);
   LINK_CLASS_DESTRUCTOR(HLAinteger16BE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAinteger16BE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAinteger16LE);
   LINK_CLASS_DESTRUCTOR(HLAinteger16LE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAinteger16LE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAinteger32BE);
   LINK_CLASS_DESTRUCTOR(HLAinteger32BE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAinteger32BE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAinteger32LE);
   LINK_CLASS_DESTRUCTOR(HLAinteger32LE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAinteger32LE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAinteger64BE);
   LINK_CLASS_DESTRUCTOR(HLAinteger64BE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAinteger64BE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAinteger64Interval);
   LINK_CLASS_DESTRUCTOR(HLAinteger64Interval);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAinteger64Interval);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAinteger64LE);
   LINK_CLASS_DESTRUCTOR(HLAinteger64LE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAinteger64LE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAinteger64Time);
   LINK_CLASS_DESTRUCTOR(HLAinteger64Time);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAinteger64Time);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAinteger64TimeFactory);
   LINK_CLASS_DESTRUCTOR(HLAinteger64TimeFactory);
#if (RTI_HLA_VERSION >= 2025)
   LINK_CLASS_COPY_CONSTRUCTOR(HLAlogicalTime);
   LINK_CLASS_DESTRUCTOR(HLAlogicalTime);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAlogicalTimeFactoryFactory);
   LINK_CLASS_DESTRUCTOR(HLAlogicalTimeFactoryFactory);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAlogicalTimeInterval);
   LINK_CLASS_DESTRUCTOR(HLAlogicalTimeInterval);
#endif
   LINK_CLASS_COPY_CONSTRUCTOR(HLAoctet);
   LINK_CLASS_DESTRUCTOR(HLAoctet);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAoctet);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAoctetPairBE);
   LINK_CLASS_DESTRUCTOR(HLAoctetPairBE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAoctetPairLE);
   LINK_CLASS_DESTRUCTOR(HLAoctetPairLE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAopaqueData);
   LINK_CLASS_DESTRUCTOR(HLAopaqueData);
   LINK_ONLY_BLOCK(HLAopaqueData, pointer) {
      // Conversion function
#if (RTI_HLA_VERSION < 2025)
      const Octet * ignored1 = *pointer;
#endif
      const Octet * ignored2 = pointer->get();
   }
   LINK_CLASS_COPY_CONSTRUCTOR(HLAunicodeChar);
   LINK_CLASS_DESTRUCTOR(HLAunicodeChar);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAunicodeChar);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAunicodeString);
   LINK_CLASS_DESTRUCTOR(HLAunicodeString);
#if (RTI_HLA_VERSION >= 2025)
   LINK_CLASS_COPY_CONSTRUCTOR(HLAunsignedInteger16BE);
   LINK_CLASS_DESTRUCTOR(HLAunsignedInteger16BE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAunsignedInteger16BE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAunsignedInteger16LE);
   LINK_CLASS_DESTRUCTOR(HLAunsignedInteger16LE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAunsignedInteger16LE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAunsignedInteger32BE);
   LINK_CLASS_DESTRUCTOR(HLAunsignedInteger32BE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAunsignedInteger32BE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAunsignedInteger32LE);
   LINK_CLASS_DESTRUCTOR(HLAunsignedInteger32LE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAunsignedInteger32LE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAunsignedInteger64BE);
   LINK_CLASS_DESTRUCTOR(HLAunsignedInteger64BE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAunsignedInteger64BE);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAunsignedInteger64LE);
   LINK_CLASS_DESTRUCTOR(HLAunsignedInteger64LE);
   LINK_CLASS_WIDE_STREAM_OPERATOR(HLAunsignedInteger64LE);
#endif
   LINK_CLASS_COPY_CONSTRUCTOR(HLAvariableArray);
   LINK_CLASS_DESTRUCTOR(HLAvariableArray);
   LINK_CLASS_COPY_CONSTRUCTOR(HLAvariantRecord);
   LINK_CLASS_DESTRUCTOR(HLAvariantRecord);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(IllegalName);
   LINK_CLASS_DESTRUCTOR(IllegalName);
   LINK_CLASS_WIDE_STREAM_OPERATOR(IllegalName);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(IllegalTimeArithmetic);
   LINK_CLASS_DESTRUCTOR(IllegalTimeArithmetic);
   LINK_CLASS_WIDE_STREAM_OPERATOR(IllegalTimeArithmetic);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InTimeAdvancingState);
   LINK_CLASS_DESTRUCTOR(InTimeAdvancingState);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InTimeAdvancingState);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InteractionClassAlreadyBeingChanged);
   LINK_CLASS_DESTRUCTOR(InteractionClassAlreadyBeingChanged);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InteractionClassAlreadyBeingChanged);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InteractionClassNotDefined);
   LINK_CLASS_DESTRUCTOR(InteractionClassNotDefined);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InteractionClassNotDefined);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InteractionClassNotPublished);
   LINK_CLASS_DESTRUCTOR(InteractionClassNotPublished);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InteractionClassNotPublished);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InteractionParameterNotDefined);
   LINK_CLASS_DESTRUCTOR(InteractionParameterNotDefined);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InteractionParameterNotDefined);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InteractionRelevanceAdvisorySwitchIsOff);
   LINK_CLASS_DESTRUCTOR(InteractionRelevanceAdvisorySwitchIsOff);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InteractionRelevanceAdvisorySwitchIsOff);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InteractionRelevanceAdvisorySwitchIsOn);
   LINK_CLASS_DESTRUCTOR(InteractionRelevanceAdvisorySwitchIsOn);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InteractionRelevanceAdvisorySwitchIsOn);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InternalError);
   LINK_CLASS_DESTRUCTOR(InternalError);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InternalError);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidAttributeHandle);
   LINK_CLASS_DESTRUCTOR(InvalidAttributeHandle);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidAttributeHandle);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidDimensionHandle);
   LINK_CLASS_DESTRUCTOR(InvalidDimensionHandle);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidDimensionHandle);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidFederateHandle);
   LINK_CLASS_DESTRUCTOR(InvalidFederateHandle);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidFederateHandle);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidInteractionClassHandle);
   LINK_CLASS_DESTRUCTOR(InvalidInteractionClassHandle);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidInteractionClassHandle);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidLogicalTime);
   LINK_CLASS_DESTRUCTOR(InvalidLogicalTime);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidLogicalTime);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidLogicalTimeInterval);
   LINK_CLASS_DESTRUCTOR(InvalidLogicalTimeInterval);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidLogicalTimeInterval);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidLookahead);
   LINK_CLASS_DESTRUCTOR(InvalidLookahead);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidLookahead);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidMessageRetractionHandle);
   LINK_CLASS_DESTRUCTOR(InvalidMessageRetractionHandle);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidMessageRetractionHandle);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidObjectClassHandle);
   LINK_CLASS_DESTRUCTOR(InvalidObjectClassHandle);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidObjectClassHandle);
#if (RTI_HLA_VERSION >= 2025)
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidObjectInstanceHandle);
   LINK_CLASS_DESTRUCTOR(InvalidObjectInstanceHandle);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidObjectInstanceHandle);
#endif
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidOrderName);
   LINK_CLASS_DESTRUCTOR(InvalidOrderName);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidOrderName);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidOrderType);
   LINK_CLASS_DESTRUCTOR(InvalidOrderType);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidOrderType);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidParameterHandle);
   LINK_CLASS_DESTRUCTOR(InvalidParameterHandle);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidParameterHandle);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidRangeBound);
   LINK_CLASS_DESTRUCTOR(InvalidRangeBound);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidRangeBound);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidRegion);
   LINK_CLASS_DESTRUCTOR(InvalidRegion);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidRegion);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidRegionContext);
   LINK_CLASS_DESTRUCTOR(InvalidRegionContext);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidRegionContext);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidResignAction);
   LINK_CLASS_DESTRUCTOR(InvalidResignAction);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidResignAction);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidServiceGroup);
   LINK_CLASS_DESTRUCTOR(InvalidServiceGroup);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidServiceGroup);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidTransportationName);
   LINK_CLASS_DESTRUCTOR(InvalidTransportationName);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidTransportationName);
#if (RTI_HLA_VERSION >= 2025)
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidTransportationTypeHandle);
   LINK_CLASS_DESTRUCTOR(InvalidTransportationTypeHandle);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidTransportationTypeHandle);
#endif
   LINK_EXCEPTION_COPY_CONSTRUCTOR(InvalidUpdateRateDesignator);
   LINK_CLASS_DESTRUCTOR(InvalidUpdateRateDesignator);
   LINK_CLASS_WIDE_STREAM_OPERATOR(InvalidUpdateRateDesignator);
   LINK_CLASS_WIDE_STREAM_OPERATOR(LogicalTime);
   LINK_CLASS_DESTRUCTOR(LogicalTime);
   LINK_CLASS_WIDE_STREAM_OPERATOR(LogicalTime);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(LogicalTimeAlreadyPassed);
   LINK_CLASS_DESTRUCTOR(LogicalTimeAlreadyPassed);
   LINK_CLASS_WIDE_STREAM_OPERATOR(LogicalTimeAlreadyPassed);
   LINK_CLASS_DESTRUCTOR(LogicalTimeFactory);
   LINK_CLASS_COPY_CONSTRUCTOR(LogicalTimeFactoryFactory);
   LINK_CLASS_DESTRUCTOR(LogicalTimeFactoryFactory);
   LINK_CLASS_WIDE_STREAM_OPERATOR(LogicalTimeInterval);
   LINK_CLASS_DESTRUCTOR(LogicalTimeInterval);
   LINK_CLASS_WIDE_STREAM_OPERATOR(LogicalTimeInterval);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(MessageCanNoLongerBeRetracted);
   LINK_CLASS_DESTRUCTOR(MessageCanNoLongerBeRetracted);
   LINK_CLASS_WIDE_STREAM_OPERATOR(MessageCanNoLongerBeRetracted);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(NameNotFound);
   LINK_CLASS_DESTRUCTOR(NameNotFound);
   LINK_CLASS_WIDE_STREAM_OPERATOR(NameNotFound);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(NameSetWasEmpty);
   LINK_CLASS_DESTRUCTOR(NameSetWasEmpty);
   LINK_CLASS_WIDE_STREAM_OPERATOR(NameSetWasEmpty);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(NoAcquisitionPending);
   LINK_CLASS_DESTRUCTOR(NoAcquisitionPending);
   LINK_CLASS_WIDE_STREAM_OPERATOR(NoAcquisitionPending);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(NotConnected);
   LINK_CLASS_DESTRUCTOR(NotConnected);
   LINK_CLASS_WIDE_STREAM_OPERATOR(NotConnected);
   LINK_CLASS_COPY_CONSTRUCTOR(NullFederateAmbassador);
   LINK_CLASS_DESTRUCTOR(NullFederateAmbassador);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(ObjectClassNotDefined);
   LINK_CLASS_DESTRUCTOR(ObjectClassNotDefined);
   LINK_CLASS_WIDE_STREAM_OPERATOR(ObjectClassNotDefined);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(ObjectClassNotPublished);
   LINK_CLASS_DESTRUCTOR(ObjectClassNotPublished);
   LINK_CLASS_WIDE_STREAM_OPERATOR(ObjectClassNotPublished);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(ObjectClassRelevanceAdvisorySwitchIsOff);
   LINK_CLASS_DESTRUCTOR(ObjectClassRelevanceAdvisorySwitchIsOff);
   LINK_CLASS_WIDE_STREAM_OPERATOR(ObjectClassRelevanceAdvisorySwitchIsOff);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(ObjectClassRelevanceAdvisorySwitchIsOn);
   LINK_CLASS_DESTRUCTOR(ObjectClassRelevanceAdvisorySwitchIsOn);
   LINK_CLASS_WIDE_STREAM_OPERATOR(ObjectClassRelevanceAdvisorySwitchIsOn);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(ObjectInstanceNameInUse);
   LINK_CLASS_DESTRUCTOR(ObjectInstanceNameInUse);
   LINK_CLASS_WIDE_STREAM_OPERATOR(ObjectInstanceNameInUse);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(ObjectInstanceNameNotReserved);
   LINK_CLASS_DESTRUCTOR(ObjectInstanceNameNotReserved);
   LINK_CLASS_WIDE_STREAM_OPERATOR(ObjectInstanceNameNotReserved);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(ObjectInstanceNotKnown);
   LINK_CLASS_DESTRUCTOR(ObjectInstanceNotKnown);
   LINK_CLASS_WIDE_STREAM_OPERATOR(ObjectInstanceNotKnown);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(OwnershipAcquisitionPending);
   LINK_CLASS_DESTRUCTOR(OwnershipAcquisitionPending);
   LINK_CLASS_WIDE_STREAM_OPERATOR(OwnershipAcquisitionPending);
   LINK_CLASS_DESTRUCTOR(RTIambassador);
   LINK_CLASS_COPY_CONSTRUCTOR(RTIambassadorFactory);
   LINK_CLASS_DESTRUCTOR(RTIambassadorFactory);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(RTIinternalError);
   LINK_CLASS_DESTRUCTOR(RTIinternalError);
   LINK_CLASS_WIDE_STREAM_OPERATOR(RTIinternalError);
   LINK_CLASS_COPY_CONSTRUCTOR(RangeBounds);
   LINK_CLASS_DESTRUCTOR(RangeBounds);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(RegionDoesNotContainSpecifiedDimension);
   LINK_CLASS_DESTRUCTOR(RegionDoesNotContainSpecifiedDimension);
   LINK_CLASS_WIDE_STREAM_OPERATOR(RegionDoesNotContainSpecifiedDimension);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(RegionInUseForUpdateOrSubscription);
   LINK_CLASS_DESTRUCTOR(RegionInUseForUpdateOrSubscription);
   LINK_CLASS_WIDE_STREAM_OPERATOR(RegionInUseForUpdateOrSubscription);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(RegionNotCreatedByThisFederate);
   LINK_CLASS_DESTRUCTOR(RegionNotCreatedByThisFederate);
   LINK_CLASS_WIDE_STREAM_OPERATOR(RegionNotCreatedByThisFederate);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(RequestForTimeConstrainedPending);
   LINK_CLASS_DESTRUCTOR(RequestForTimeConstrainedPending);
   LINK_CLASS_WIDE_STREAM_OPERATOR(RequestForTimeConstrainedPending);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(RequestForTimeRegulationPending);
   LINK_CLASS_DESTRUCTOR(RequestForTimeRegulationPending);
   LINK_CLASS_WIDE_STREAM_OPERATOR(RequestForTimeRegulationPending);
#if (RTI_HLA_VERSION >= 2025)
   LINK_EXCEPTION_COPY_CONSTRUCTOR(ReportServiceInvocationsAreSubscribed);
   LINK_CLASS_DESTRUCTOR(ReportServiceInvocationsAreSubscribed);
   LINK_CLASS_WIDE_STREAM_OPERATOR(ReportServiceInvocationsAreSubscribed);
#endif
   LINK_EXCEPTION_COPY_CONSTRUCTOR(RestoreInProgress);
   LINK_CLASS_DESTRUCTOR(RestoreInProgress);
   LINK_CLASS_WIDE_STREAM_OPERATOR(RestoreInProgress);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(RestoreNotInProgress);
   LINK_CLASS_DESTRUCTOR(RestoreNotInProgress);
   LINK_CLASS_WIDE_STREAM_OPERATOR(RestoreNotInProgress);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(RestoreNotRequested);
   LINK_CLASS_DESTRUCTOR(RestoreNotRequested);
   LINK_CLASS_WIDE_STREAM_OPERATOR(RestoreNotRequested);
#if (RTI_HLA_VERSION >= 2025)
   LINK_CLASS_COPY_CONSTRUCTOR(RtiConfiguration);
   LINK_CLASS_DESTRUCTOR(RtiConfiguration);
#endif
   LINK_EXCEPTION_COPY_CONSTRUCTOR(SaveInProgress);
   LINK_CLASS_DESTRUCTOR(SaveInProgress);
   LINK_CLASS_WIDE_STREAM_OPERATOR(SaveInProgress);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(SaveNotInProgress);
   LINK_CLASS_DESTRUCTOR(SaveNotInProgress);
   LINK_CLASS_WIDE_STREAM_OPERATOR(SaveNotInProgress);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(SaveNotInitiated);
   LINK_CLASS_DESTRUCTOR(SaveNotInitiated);
   LINK_CLASS_WIDE_STREAM_OPERATOR(SaveNotInitiated);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(SynchronizationPointLabelNotAnnounced);
   LINK_CLASS_DESTRUCTOR(SynchronizationPointLabelNotAnnounced);
   LINK_CLASS_WIDE_STREAM_OPERATOR(SynchronizationPointLabelNotAnnounced);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(TimeConstrainedAlreadyEnabled);
   LINK_CLASS_DESTRUCTOR(TimeConstrainedAlreadyEnabled);
   LINK_CLASS_WIDE_STREAM_OPERATOR(TimeConstrainedAlreadyEnabled);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(TimeConstrainedIsNotEnabled);
   LINK_CLASS_DESTRUCTOR(TimeConstrainedIsNotEnabled);
   LINK_CLASS_WIDE_STREAM_OPERATOR(TimeConstrainedIsNotEnabled);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(TimeRegulationAlreadyEnabled);
   LINK_CLASS_DESTRUCTOR(TimeRegulationAlreadyEnabled);
   LINK_CLASS_WIDE_STREAM_OPERATOR(TimeRegulationAlreadyEnabled);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(TimeRegulationIsNotEnabled);
   LINK_CLASS_DESTRUCTOR(TimeRegulationIsNotEnabled);
   LINK_CLASS_WIDE_STREAM_OPERATOR(TimeRegulationIsNotEnabled);
   LINK_EXCEPTION_COPY_CONSTRUCTOR(UnsupportedCallbackModel);
   LINK_CLASS_DESTRUCTOR(UnsupportedCallbackModel);
   LINK_CLASS_WIDE_STREAM_OPERATOR(UnsupportedCallbackModel);
   LINK_CLASS_COPY_CONSTRUCTOR(VariableLengthData);
   LINK_CLASS_DESTRUCTOR(VariableLengthData);
}

// NOLINTEND(hicpp-exception-baseclass)
// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)