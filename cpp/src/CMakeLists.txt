#  Copyright (C) 2023 Pitch Technologies AB
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#  http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.


include(../cmake/FedProTargetHelpers.cmake)

set(FEDPRO_SOURCES_COMMON
      # API
      ../include/FedPro.h
      ../include/fedpro/Aliases.h
      ../include/fedpro/Config.h
      ../include/fedpro/EofException.h
      ../include/fedpro/Logger.h
      ../include/fedpro/PersistentSession.h
      ../include/fedpro/ProgressiveDelayResumeStrategy.h
      ../include/fedpro/Properties.h
      ../include/fedpro/ResumeStrategy.h
      ../include/fedpro/Session.h
      ../include/fedpro/Settings.h
      ../include/fedpro/SimpleResumeStrategy.h
      ../include/fedpro/Socket.h
      ../include/fedpro/Transport.h
      # Common
      fedpro/FedPro.cpp
      fedpro/FedProException.cpp
      fedpro/utility/bit.h
      fedpro/utility/exceptions/InterruptedException.cpp
      fedpro/utility/final.h
      fedpro/utility/InterruptibleCondition.cpp
      fedpro/utility/InterruptibleCondition.h
      fedpro/utility/FixedRateExecutor.h
      fedpro/utility/FixedRateExecutor.cpp
      fedpro/utility/Logger.cpp
      fedpro/utility/LoggerInitializer.cpp
      fedpro/utility/LoggerInitializer.h
      fedpro/utility/MovingStats.h
      fedpro/utility/MovingStats.cpp
      fedpro/utility/MovingStatsNoOp.h
      fedpro/utility/NannyLock.cpp
      fedpro/utility/NannyLock.h
      fedpro/utility/Properties.cpp
      fedpro/utility/StandAloneMovingStats.h
      fedpro/utility/StandAloneMovingStats.cpp
      fedpro/utility/StatsPrettyPrint.cpp
      fedpro/utility/StatsPrettyPrint.h
      fedpro/utility/StringUtil.cpp
      fedpro/utility/StringUtil.h
      fedpro/utility/string_view.h
      # Session
      fedpro/session/AtomicSequenceNumber.cpp
      fedpro/session/AtomicSequenceNumber.h
      fedpro/session/buffers/BufferReader.h
      fedpro/session/buffers/BufferPeeker.h
      fedpro/session/buffers/CircularBuffer.h
      fedpro/session/buffers/GenericBuffer.h
      fedpro/session/buffers/HistoryBuffer.cpp
      fedpro/session/buffers/HistoryBuffer.h
      fedpro/session/buffers/RateLimitedBuffer.h
      fedpro/session/buffers/UnboundedBuffer.h
      fedpro/session/exceptions/BadMessage.cpp
      fedpro/session/exceptions/MessageQueueFull.cpp
      fedpro/session/exceptions/SessionAlreadyTerminated.cpp
      fedpro/session/exceptions/SessionIllegalState.cpp
      fedpro/session/exceptions/SessionLost.cpp
      fedpro/session/flowcontrol/ExponentialRateLimiter.cpp
      fedpro/session/flowcontrol/ExponentialRateLimiter.h
      fedpro/session/flowcontrol/NullRateLimiter.cpp
      fedpro/session/flowcontrol/NullRateLimiter.h
      fedpro/session/flowcontrol/RateLimiter.h
      fedpro/session/LogUtil.cpp
      fedpro/session/LogUtil.h
      fedpro/session/MessageWriter.cpp
      fedpro/session/MessageWriter.h
      fedpro/session/MessageType.cpp
      fedpro/session/MessageType.h
      fedpro/session/msg/ByteInfo.h
      fedpro/session/msg/ByteReader.cpp
      fedpro/session/msg/ByteReader.h
      fedpro/session/msg/ByteWriter.h
      fedpro/session/msg/EncodableMessage.cpp
      fedpro/session/msg/EncodableMessage.h
      fedpro/session/msg/EncodedMessage.cpp
      fedpro/session/msg/EncodedMessage.h
      fedpro/session/msg/HeartbeatResponseMessage.cpp
      fedpro/session/msg/HeartbeatResponseMessage.h
      fedpro/session/msg/HlaCallbackRequestMessage.cpp
      fedpro/session/msg/HlaCallbackRequestMessage.h
      fedpro/session/msg/HlaCallbackResponseMessage.cpp
      fedpro/session/msg/HlaCallbackResponseMessage.h
      fedpro/session/msg/HlaCallRequestMessage.cpp
      fedpro/session/msg/HlaCallRequestMessage.h
      fedpro/session/msg/HlaCallResponseMessage.cpp
      fedpro/session/msg/HlaCallResponseMessage.h
      fedpro/session/msg/MessageHeader.cpp
      fedpro/session/msg/MessageHeader.h
      fedpro/session/msg/NewSessionMessage.cpp
      fedpro/session/msg/NewSessionMessage.h
      fedpro/session/msg/NewSessionStatusMessage.cpp
      fedpro/session/msg/NewSessionStatusMessage.h
      fedpro/session/msg/QueueableMessage.cpp
      fedpro/session/msg/QueueableMessage.h
      fedpro/session/msg/ResumeRequestMessage.cpp
      fedpro/session/msg/ResumeRequestMessage.h
      fedpro/session/msg/ResumeStatusMessage.cpp
      fedpro/session/msg/ResumeStatusMessage.h
      fedpro/session/PersistentSessionImpl.cpp
      fedpro/session/PersistentSessionImpl.h
      fedpro/session/ProgressiveDelayResumeStrategy.cpp
      fedpro/session/SequenceNumber.cpp
      fedpro/session/SequenceNumber.h
      fedpro/session/SessionImpl.cpp
      fedpro/session/SessionImpl.h
      fedpro/session/SessionSettings.h
      fedpro/session/SessionUtils.h
      fedpro/session/SimpleResumeStrategy.cpp
      fedpro/session/SocketWriter.cpp
      fedpro/session/SocketWriter.h
      fedpro/session/TimeoutTimer.cpp
      fedpro/session/TimeoutTimer.h
      fedpro/session/ThreadPool.cpp
      fedpro/session/ThreadPool.h
      # Transport
      fedpro/transport/exceptions/EofException.cpp
      fedpro/transport/Socket.cpp
      fedpro/transport/TcpSocket.cpp
      fedpro/transport/TcpSocket.h
      fedpro/transport/TcpTransport.cpp
      fedpro/transport/TcpTransport.h
      fedpro/transport/TlsTransport.cpp
      fedpro/transport/TlsTransport.h
      fedpro/transport/TransportBase.cpp
      fedpro/transport/TransportImplBase.h
      fedpro/transport/TransportSettings.h
      fedpro/transport/WebsocketSecureTransport.cpp
      fedpro/transport/WebsocketSecureTransport.h
      fedpro/transport/WebsocketTransport.cpp
      fedpro/transport/WebsocketTransport.h
      # Protobuf-generated
      fedpro/protobuf/generated/datatypes.pb.cc
      fedpro/protobuf/generated/FederateAmbassador.pb.cc
      fedpro/protobuf/generated/RTIambassador.pb.cc
      # Services
      fedpro/services-common/ClientConverter.cpp
      fedpro/services-common/ClientConverter.h
      fedpro/services-common/encoding/BasicDataElements.cpp
      fedpro/services-common/encoding/Encoder.h
      fedpro/services-common/encoding/EncoderException.cpp
      fedpro/services-common/encoding/DataElement.cpp
      fedpro/services-common/encoding/HLAextendableVariantRecord.cpp
      fedpro/services-common/encoding/HLAfixedArray.cpp
      fedpro/services-common/encoding/HLAfixedRecord.cpp
      fedpro/services-common/encoding/HLAlogicalTime.cpp
      fedpro/services-common/encoding/HLAlogicalTimeInterval.cpp
      fedpro/services-common/encoding/HLAopaqueData.cpp
      fedpro/services-common/encoding/HLAvariableArray.cpp
      fedpro/services-common/encoding/HLAvariantRecord.cpp
      fedpro/services-common/Exception.cpp
      fedpro/services-common/FederateAmbassador.cpp
      fedpro/services-common/FederateAmbassadorDispatcher.h
      fedpro/services-common/FederateAmbassadorDispatcher.cpp
      fedpro/services-common/FomModule.cpp
      fedpro/services-common/FomModule.h
      fedpro/services-common/FomModuleLoader.cpp
      fedpro/services-common/FomModuleLoader.h
      fedpro/services-common/FomModuleSet.cpp
      fedpro/services-common/FomModuleSet.h
      fedpro/services-common/Handle.h
      fedpro/services-common/HandleImplementation.cpp
      fedpro/services-common/HandleImplementation.h
      fedpro/services-common/JoinResult.cpp
      fedpro/services-common/JoinResult.h
      fedpro/services-common/LogicalTimeFactory.cpp
      fedpro/services-common/LogicalTimeFactoryFactory.cpp
      fedpro/services-common/NullFederateAmbassador.cpp
      fedpro/services-common/protobuf_util.h
      fedpro/services-common/RangeBounds.cpp
      fedpro/services-common/RegionHandleImplementation.cpp
      fedpro/services-common/RegionHandleImplementation.h
      fedpro/services-common/RTIambassador.cpp
      fedpro/services-common/RTIambassadorClient.cpp
      fedpro/services-common/RTIambassadorClient.h
      fedpro/services-common/RTIambassadorClient_connect_callbacks.cpp
      fedpro/services-common/RTIambassadorClientGenericBase.cpp
      fedpro/services-common/RTIambassadorClientGenericBase.h
      fedpro/services-common/RTIcompat.h
      fedpro/services-common/RtiConfiguration.cpp
      fedpro/services-common/RtiConfiguration.h
      fedpro/services-common/RTITypedefs.cpp
      fedpro/services-common/RTITypedefs.h
      fedpro/services-common/ServiceSettings.h
      fedpro/services-common/SettingsParser.cpp
      fedpro/services-common/SettingsParser.h
      fedpro/services-common/time/HLAfloat64Interval.cpp
      fedpro/services-common/time/HLAfloat64Time.cpp
      fedpro/services-common/time/HLAfloat64TimeFactory.cpp
      fedpro/services-common/time/HLAinteger64Interval.cpp
      fedpro/services-common/time/HLAinteger64Time.cpp
      fedpro/services-common/time/HLAinteger64TimeFactory.cpp
      fedpro/services-common/time/HLAlogicalTimeFactoryFactory.cpp
      fedpro/services-common/time/LogicalTime.cpp
      fedpro/services-common/time/LogicalTimeInterval.cpp
      fedpro/services-common/VariableLengthData.cpp
      fedpro/services-common/RTITypedefs.h
)

# RTI API library target
add_library(fedpro_librti1516e
      SHARED
      ${FEDPRO_SOURCES_COMMON}
      # HLA Evolved-specific files
      fedpro/services-2010/FederateAmbassadorAdapter.cpp
      fedpro/services-2010/FederateAmbassadorAdapter.h
      fedpro/services-2010/RTIambassadorFactory.cpp
      fedpro/services-2010/RTIambassadorClientAdapter.cpp
      fedpro/services-2010/RTIambassadorClientAdapter.h
)

if (APPLE)
   # lib-suffix shouldn't be provided for mac as it shouldn't end with "64"
   set(HLA_EVOLVED_LIB_SUFFIX "")
else ()
   hla_get_target_suffix(2010 HLA_EVOLVED_LIB_SUFFIX)
endif()

# Define the actual library prefix and name
set_target_properties(fedpro_librti1516e PROPERTIES PREFIX "")
set_target_properties(fedpro_librti1516e PROPERTIES OUTPUT_NAME librti1516e${HLA_EVOLVED_LIB_SUFFIX})

# Specify compiler and linker flags
target_compile_options(fedpro_librti1516e PRIVATE ${FEDPRO_CXX_FLAGS_EVOLVED})

# Define the RTI library target
fedpro_set_librti_target_options(fedpro_librti1516e rti1516e 2010 ${HLA_1516_2010_FEDPRO_INCLUDE})


# Logical time library
add_library(fedpro_libfedtime1516e
      SHARED
      # Services
      fedpro/services-common/LogicalTimeFactoryFactory.cpp
)
target_include_directories(fedpro_libfedtime1516e PRIVATE "${HLA_1516_2010_INCLUDE}")
target_compile_definitions(fedpro_libfedtime1516e
      PRIVATE
      BUILDING_FEDTIME # Export fedtime symbols, including LogicalTimeFactoryFactory
      RTI_DISABLE_WARNINGS
      RTI_NAMESPACE=rti1516e # Select RTI namespace
      RTI_HLA_VERSION=2010
)

# Define the actual library prefix and name
set_target_properties(fedpro_libfedtime1516e PROPERTIES PREFIX "")
set_target_properties(fedpro_libfedtime1516e PROPERTIES OUTPUT_NAME libfedtime1516e${HLA_EVOLVED_LIB_SUFFIX})

target_link_libraries(fedpro_libfedtime1516e
      PRIVATE
      fedpro_librti1516e
)

# RTI API library target
add_library(fedpro_librti1516_4
      SHARED
      # Common
      ${FEDPRO_SOURCES_COMMON}
      # HLA4-specific files
      fedpro/services-hla4/HLAnoCredentials.cpp
      fedpro/services-hla4/HLAplainTextPassword.cpp
      fedpro/services-hla4/RTIambassadorFactory.cpp
      fedpro/services-hla4/RTIambassadorClientAdapter.cpp
      fedpro/services-hla4/RTIambassadorClientAdapter.h
)

hla_get_target_suffix(2025 HLA_4_LIB_SUFFIX)

# Define the actual library prefix and name
set_target_properties(fedpro_librti1516_4 PROPERTIES PREFIX "")
set_target_properties(fedpro_librti1516_4 PROPERTIES OUTPUT_NAME librti1516_2025${HLA_4_LIB_SUFFIX})

# Specify compiler and linker flags
target_compile_options(fedpro_librti1516_4 PRIVATE ${FEDPRO_CXX_FLAGS_HLA4})

# Define the RTI library target
fedpro_set_librti_target_options(fedpro_librti1516_4 rti1516_2025 2025 ${HLA_1516_2025_FEDPRO_INCLUDE})

# Logical time library
add_library(fedpro_libfedtime1516_4
      SHARED
      # Services
      fedpro/services-common/LogicalTimeFactoryFactory.cpp
)

# Define include search directories for this target
target_include_directories(fedpro_libfedtime1516_4 PRIVATE "${HLA_1516_2025_INCLUDE}")

# Define macros
target_compile_definitions(fedpro_libfedtime1516_4
      PRIVATE
      BUILDING_FEDTIME # Export fedtime symbols, including LogicalTimeFactoryFactory
      RTI_DISABLE_WARNINGS
      RTI_NAMESPACE=rti1516_2025 # Select RTI namespace
      RTI_HLA_VERSION=2025
)

# Define the actual library name
set_target_properties(fedpro_libfedtime1516_4 PROPERTIES PREFIX "")
set_target_properties(fedpro_libfedtime1516_4 PROPERTIES OUTPUT_NAME libfedtime1516_2025${HLA_4_LIB_SUFFIX})

target_link_libraries(fedpro_libfedtime1516_4
      PRIVATE
      fedpro_librti1516_4
)
