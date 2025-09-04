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

#include <RTI/Exception.h>

#include <iostream>
#include <typeinfo>

namespace RTI_NAMESPACE
{

   Exception::Exception() = default;

   Exception::Exception(Exception const &) = default;

   Exception & Exception::operator=(Exception const &) = default;

#if (RTI_HLA_VERSION >= 2025)
   Exception::~Exception() noexcept = default;
#else
   Exception::~Exception() = default;
#endif

   std::wostream RTI_EXPORT & operator<<(
         std::wostream & stream,
         Exception const & exception)
   {
      stream << typeid(exception).name() << ": " << exception.what();
      return stream;
   }

#define RTI_EXCEPTION_2010(A)             \
   A::A (                                 \
         std::wstring const & message)    \
       noexcept : _msg(message) {         \
   }                                      \
   std::wstring                           \
     A::what () const                     \
     noexcept { return _msg;};

#if (RTI_HLA_VERSION < 2025)
#define RTI_EXCEPTION(A)                  \
   RTI_EXCEPTION_2010(A)
#else
#define RTI_EXCEPTION(A)                  \
   RTI_EXCEPTION_2010(A)                  \
   std::wstring                           \
     A::name () const                     \
     noexcept { return L"" #A L"";};
#endif


#if (RTI_HLA_VERSION < 2025)
   RTI_EXCEPTION(AttributeAcquisitionWasNotCanceled)
   RTI_EXCEPTION(AttributeNotRecognized)
   RTI_EXCEPTION(AttributeNotSubscribed)
   RTI_EXCEPTION(BadInitializationParameter)
   RTI_EXCEPTION(CouldNotDiscover)
   RTI_EXCEPTION(CouldNotInitiateRestore)
   RTI_EXCEPTION(CouldNotOpenFDD)
   RTI_EXCEPTION(ErrorReadingFDD)
   RTI_EXCEPTION(InconsistentFDD)
   RTI_EXCEPTION(InteractionClassNotRecognized)
   RTI_EXCEPTION(InteractionClassNotSubscribed)
   RTI_EXCEPTION(InteractionParameterNotRecognized)
   RTI_EXCEPTION(InvalidLocalSettingsDesignator)
   RTI_EXCEPTION(InvalidTransportationType)
   RTI_EXCEPTION(JoinedFederateIsNotInTimeAdvancingState)
   RTI_EXCEPTION(NoRequestToEnableTimeConstrainedWasPending)
   RTI_EXCEPTION(NoRequestToEnableTimeRegulationWasPending)
   RTI_EXCEPTION(NoFederateWillingToAcquireAttribute)
   RTI_EXCEPTION(ObjectClassNotKnown)
   RTI_EXCEPTION(SpecifiedSaveLabelDoesNotExist)
   RTI_EXCEPTION(UnableToPerformSave)
   RTI_EXCEPTION(UnknownName)
#else
   RTI_EXCEPTION(CouldNotOpenFOM)
   RTI_EXCEPTION(ErrorReadingFOM)
   RTI_EXCEPTION(InconsistentFOM)
   RTI_EXCEPTION(InvalidCredentials)
   RTI_EXCEPTION(InvalidMIM)
   RTI_EXCEPTION(InvalidFOM)
   RTI_EXCEPTION(InvalidObjectInstanceHandle)
   RTI_EXCEPTION(InvalidTransportationTypeHandle)
   RTI_EXCEPTION(ReportServiceInvocationsAreSubscribed)
   RTI_EXCEPTION(Unauthorized)
#endif

RTI_EXCEPTION(AlreadyConnected)

RTI_EXCEPTION(AsynchronousDeliveryAlreadyDisabled)

RTI_EXCEPTION(AsynchronousDeliveryAlreadyEnabled)

RTI_EXCEPTION(AttributeAcquisitionWasNotRequested)

RTI_EXCEPTION(AttributeAlreadyBeingAcquired)

RTI_EXCEPTION(AttributeAlreadyBeingChanged)

RTI_EXCEPTION(AttributeAlreadyBeingDivested)

RTI_EXCEPTION(AttributeAlreadyOwned)

RTI_EXCEPTION(AttributeDivestitureWasNotRequested)

RTI_EXCEPTION(AttributeNotDefined)

RTI_EXCEPTION(AttributeNotOwned)

RTI_EXCEPTION(AttributeNotPublished)

RTI_EXCEPTION(AttributeRelevanceAdvisorySwitchIsOff)

RTI_EXCEPTION(AttributeRelevanceAdvisorySwitchIsOn)

RTI_EXCEPTION(AttributeScopeAdvisorySwitchIsOff)

RTI_EXCEPTION(AttributeScopeAdvisorySwitchIsOn)

RTI_EXCEPTION(CallNotAllowedFromWithinCallback)

RTI_EXCEPTION(ConnectionFailed)

RTI_EXCEPTION(CouldNotCreateLogicalTimeFactory)

RTI_EXCEPTION(CouldNotDecode)

RTI_EXCEPTION(CouldNotEncode)

RTI_EXCEPTION(CouldNotOpenMIM)

RTI_EXCEPTION(DeletePrivilegeNotHeld)

RTI_EXCEPTION(DesignatorIsHLAstandardMIM)

RTI_EXCEPTION(RequestForTimeConstrainedPending)

RTI_EXCEPTION(RequestForTimeRegulationPending)

RTI_EXCEPTION(ErrorReadingMIM)

RTI_EXCEPTION(FederateAlreadyExecutionMember)

RTI_EXCEPTION(FederateHandleNotKnown)

RTI_EXCEPTION(FederateHasNotBegunSave)

RTI_EXCEPTION(FederateInternalError)

RTI_EXCEPTION(FederateIsExecutionMember)

RTI_EXCEPTION(FederateNameAlreadyInUse)

RTI_EXCEPTION(FederateNotExecutionMember)

RTI_EXCEPTION(FederateOwnsAttributes)

RTI_EXCEPTION(FederateServiceInvocationsAreBeingReportedViaMOM)

RTI_EXCEPTION(FederateUnableToUseTime)

RTI_EXCEPTION(FederatesCurrentlyJoined)

RTI_EXCEPTION(FederationExecutionAlreadyExists)

RTI_EXCEPTION(FederationExecutionDoesNotExist)

RTI_EXCEPTION(IllegalName)

RTI_EXCEPTION(IllegalTimeArithmetic)

RTI_EXCEPTION(InteractionClassAlreadyBeingChanged)

RTI_EXCEPTION(InteractionClassNotDefined)

RTI_EXCEPTION(InteractionClassNotPublished)

RTI_EXCEPTION(InteractionParameterNotDefined)

RTI_EXCEPTION(InteractionRelevanceAdvisorySwitchIsOff)

RTI_EXCEPTION(InteractionRelevanceAdvisorySwitchIsOn)

RTI_EXCEPTION(InTimeAdvancingState)

RTI_EXCEPTION(InvalidAttributeHandle)

RTI_EXCEPTION(InvalidDimensionHandle)

RTI_EXCEPTION(InvalidFederateHandle)

RTI_EXCEPTION(InvalidInteractionClassHandle)

RTI_EXCEPTION(InvalidLogicalTime)

RTI_EXCEPTION(InvalidLogicalTimeInterval)

RTI_EXCEPTION(InvalidLookahead)

RTI_EXCEPTION(InvalidObjectClassHandle)

RTI_EXCEPTION(InvalidOrderName)

RTI_EXCEPTION(InvalidOrderType)

RTI_EXCEPTION(InvalidParameterHandle)

RTI_EXCEPTION(InvalidRangeBound)

RTI_EXCEPTION(InvalidRegion)

RTI_EXCEPTION(InvalidResignAction)

RTI_EXCEPTION(InvalidRegionContext)

RTI_EXCEPTION(InvalidMessageRetractionHandle)

RTI_EXCEPTION(InvalidServiceGroup)

RTI_EXCEPTION(InvalidTransportationName)

RTI_EXCEPTION(InvalidUpdateRateDesignator)

RTI_EXCEPTION(LogicalTimeAlreadyPassed)

RTI_EXCEPTION(MessageCanNoLongerBeRetracted)

RTI_EXCEPTION(NameNotFound)

RTI_EXCEPTION(NameSetWasEmpty)

RTI_EXCEPTION(NoAcquisitionPending)

RTI_EXCEPTION(NotConnected)

RTI_EXCEPTION(ObjectClassNotDefined)

RTI_EXCEPTION(ObjectClassNotPublished)

RTI_EXCEPTION(ObjectClassRelevanceAdvisorySwitchIsOff)

RTI_EXCEPTION(ObjectClassRelevanceAdvisorySwitchIsOn)

RTI_EXCEPTION(ObjectInstanceNameInUse)

RTI_EXCEPTION(ObjectInstanceNameNotReserved)

RTI_EXCEPTION(ObjectInstanceNotKnown)

RTI_EXCEPTION(OwnershipAcquisitionPending)

RTI_EXCEPTION(RTIinternalError)

RTI_EXCEPTION(RegionDoesNotContainSpecifiedDimension)

RTI_EXCEPTION(RegionInUseForUpdateOrSubscription)

RTI_EXCEPTION(RegionNotCreatedByThisFederate)

RTI_EXCEPTION(RestoreInProgress)

RTI_EXCEPTION(RestoreNotInProgress)

RTI_EXCEPTION(RestoreNotRequested)

RTI_EXCEPTION(SaveInProgress)

RTI_EXCEPTION(SaveNotInProgress)

RTI_EXCEPTION(SaveNotInitiated)

RTI_EXCEPTION(SynchronizationPointLabelNotAnnounced)

RTI_EXCEPTION(TimeConstrainedAlreadyEnabled)

RTI_EXCEPTION(TimeConstrainedIsNotEnabled)

RTI_EXCEPTION(TimeRegulationAlreadyEnabled)

RTI_EXCEPTION(TimeRegulationIsNotEnabled)

RTI_EXCEPTION(UnsupportedCallbackModel)

RTI_EXCEPTION(InternalError)

}