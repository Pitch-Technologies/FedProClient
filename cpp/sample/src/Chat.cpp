/*
 *  Copyright (C) 2023 Pitch Technologies AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <RTI/encoding/BasicDataElements.h>
#include <RTI/NullFederateAmbassador.h>
#include <RTI/RTI1516.h>
#include <RTI/RTIambassadorFactory.h>
#include <RTI/time/HLAinteger64TimeFactory.h>

#ifdef _WIN32

#include <fcntl.h>
#include <io.h>

#endif

#include <atomic>
#include <condition_variable>
#include <iostream>
#include <map>
#include <mutex>
#include <string>


using std::string;
using std::wstring;
using namespace rti1516_2025;

std::mutex mutex;
std::condition_variable reservationCompleted;


void string2wstring(
      wstring & dest,
      const string & src)
{
   dest.resize(src.size());
   for (string::size_type i = 0; i < src.size(); i++) {
      dest[i] = static_cast<unsigned char>(src[i]);
   }
}

/**
 * @breif HLA 4 Chat sample for Federate Protocol.
 */
class ChatFederateHla4 : public NullFederateAmbassador
{
private:
   std::unique_ptr<RTIambassador> _rtiAmbassador;
   InteractionClassHandle _messageInteractionClass;
   ParameterHandle _paramText;
   ParameterHandle _paramSender;
   ObjectClassHandle _participantObjectClass;
   ObjectInstanceHandle _myObjectInstance;
   AttributeHandle _attrName;
   AttributeHandleSet _attributes;
   InteractionClassHandleSet _interactions;
   ParameterHandleValueMap _parameterValues;
   AttributeHandleValueMap _attributeValues;

   wstring _username;
   wstring _message;

   std::atomic<bool> _reservationSucceeded{false};
   std::atomic<bool> _reservationComplete{false};
   const wstring FEDERATION_NAME{L"ChatRoom"};

public:

   class Participant {
   private:
      wstring _name;
      ObjectInstanceHandle _objectInstance;
   public:
      explicit Participant(wstring const & name, ObjectInstanceHandle const & objectInstance) {
         _name = name;
         _objectInstance = objectInstance;
      }

      Participant() = default;

      ObjectInstanceHandle getObjectInstanceHandle() {
         return _objectInstance;
      }

      wstring toString() {
         return _name;
      }
   };

   std::map<ObjectInstanceHandle, Participant> _knownObjects;
   std::map<wstring, Participant> _knownParticipants;

   void run(
         int argc,
         char * argv[])
   {
      wstring rtiHost = L"localhost";
      std::vector<wstring> FOMmoduleUrls;

      FOMmoduleUrls.emplace_back(L"Chat-hla4.xml");

      try {
         if (argc > 1) {
            string2wstring(rtiHost, argv[1]);
         } else {
            std::wcout << L"Enter the Federate Protocol server address, such as" << std::endl;
            std::wcout << L"'localhost', 'localhost:15164', '192.168.1.62'" << std::endl;
            std::wcout << L"If no value is provided, defaults will be used." << std::endl;
            std::wcout << std::endl;
            std::wcout << L"[localhost]: ";
            std::wcout.flush();
            std::getline(std::wcin, rtiHost);
            if (std::wcin.bad()) {
               std::wcerr << L"Unrecoverable input error while reading hostname." << std::endl;
               rtiHost.clear();
               return;
            }

            if (rtiHost.empty()) {
               rtiHost = L"localhost";
            }
         }
         auto rtiAmbassadorFactory = std::make_unique<RTIambassadorFactory>();
         _rtiAmbassador = rtiAmbassadorFactory->createRTIambassador();
      } catch (const RTIinternalError &) {
         std::wcout << std::endl << L"Unable to create RTI ambassador" << std::endl;
         return;
      }

#ifdef _WIN32
      // Make sure that stdin and stdout are in text mode. Initialization of
      // the Java Virtual Machine (done by pRTI) has a side-effect which is
      // to set stdin and stdout to binary mode.
      _setmode(_fileno(stdout), _O_TEXT);
      _setmode(_fileno(stderr), _O_TEXT);
      _setmode(_fileno(stdin), _O_TEXT);
#endif

      try {
         RtiConfiguration rtiConfiguration = RtiConfiguration::createConfiguration().withRtiAddress(rtiHost);
         _rtiAmbassador->connect(*this, HLA_IMMEDIATE, rtiConfiguration);

         try {
            _rtiAmbassador->destroyFederationExecution(FEDERATION_NAME);
         } catch (const FederatesCurrentlyJoined &) {
         } catch (const FederationExecutionDoesNotExist &) {
         } catch (const RTIinternalError &) {
         }

         try {
            _rtiAmbassador->createFederationExecution(FEDERATION_NAME, FOMmoduleUrls);
         } catch (const FederationExecutionAlreadyExists &) {
         }

         FederateHandle federateHandle = _rtiAmbassador->joinFederationExecution(
               L"Chat", FEDERATION_NAME, FOMmoduleUrls);


         _messageInteractionClass = _rtiAmbassador->getInteractionClassHandle(L"Communication");
         _paramText = _rtiAmbassador->getParameterHandle(_messageInteractionClass, L"Message");
         _paramSender = _rtiAmbassador->getParameterHandle(_messageInteractionClass, L"Sender");
         _participantObjectClass = _rtiAmbassador->getObjectClassHandle(L"Participant");
         _attrName = _rtiAmbassador->getAttributeHandle(_participantObjectClass, L"Name");
         _attributes.insert(_attrName);

         _interactions.insert(_messageInteractionClass);

         _rtiAmbassador->subscribeObjectClassDirectedInteractions(_participantObjectClass, _interactions);
         _rtiAmbassador->publishObjectClassDirectedInteractions(_participantObjectClass, _interactions);
         _rtiAmbassador->subscribeInteractionClass(_messageInteractionClass);
         _rtiAmbassador->publishInteractionClass(_messageInteractionClass);
         _rtiAmbassador->subscribeObjectClassAttributes(_participantObjectClass , _attributes);
         _rtiAmbassador->publishObjectClassAttributes(_participantObjectClass , _attributes);

         _reservationSucceeded = false;
         do {
            std::wcout << L"Enter your name: ";
            std::wcout.flush();
            std::getline(std::wcin, _username);
            if (std::wcin.bad()) {
               std::wcerr << L"Unrecoverable input error while reading username." << std::endl;
               _username.clear();
               return;
            }

            try {
               _reservationComplete = false;
               _rtiAmbassador->reserveObjectInstanceName(_username);

               std::unique_lock<std::mutex> lock(mutex);
               reservationCompleted.wait(lock, [this] { return _reservationComplete.load(); });
               lock.unlock();

               if (!_reservationSucceeded) {
                  std::wcout << L"Name already taken, try again.\n";
               }
            } catch (const IllegalName &) {
               std::wcout << L"Illegal name. Try again.\n";
            } catch (const Exception &) {
               std::wcout << L"RTI exception when reserving name: \n";
               return;
            }
         } while (!_reservationSucceeded);

         HLAunicodeString unicodeUserName{_username};
         _myObjectInstance = _rtiAmbassador->registerObjectInstance(_participantObjectClass, _username);
         _attributeValues[_attrName] = unicodeUserName.encode();
         _rtiAmbassador->updateAttributeValues(_myObjectInstance, _attributeValues, VariableLengthData{});

         std::wcout << L"Type messages you want to send.\nTo send a private message, type \"<Participant Name>: <Message>\"\nTo exit, type . <ENTER>" << std::endl;
         while (true) {
            wstring wmsg;
            std::wcout << L"> ";
            std::wcout.flush();
            std::getline(std::wcin, wmsg);

            if (std::wcin.eof() || wmsg == L".") {
               break;
            }

            Participant * recipient = nullptr;

            // Find the position of the first ": "
            size_t delimiterPos = wmsg.find(L": ");
            if (delimiterPos != std::wstring::npos) {
               std::wstring recipientName = wmsg.substr(0, delimiterPos);

               // Check if the recipient exists in the known participants
               auto it = _knownParticipants.find(recipientName);
               if (it != _knownParticipants.end()) {
                  recipient = &it->second;
               } else {
                  std::wcout << L"Unknown receiver of private message \"" << recipientName << L"\"" << std::endl;
                  continue;
               }

               wmsg = wmsg.substr(delimiterPos + 2);
            }

            HLAunicodeString unicodeMessage{wmsg};
            _parameterValues[_paramText] = unicodeMessage.encode();
            _parameterValues[_paramSender] = unicodeUserName.encode();

            if (recipient == nullptr) {
               _rtiAmbassador->sendInteraction(_messageInteractionClass, _parameterValues, VariableLengthData());
            } else {
               _rtiAmbassador->sendDirectedInteraction(_messageInteractionClass,
                                                       recipient->getObjectInstanceHandle(),
                                                       _parameterValues,
                                                       VariableLengthData());
            }
         }

         _rtiAmbassador->resignFederationExecution(CANCEL_THEN_DELETE_THEN_DIVEST);
         try {
            _rtiAmbassador->destroyFederationExecution(FEDERATION_NAME);
         } catch (const FederatesCurrentlyJoined &) {
         }
         _rtiAmbassador->disconnect();
      } catch (const CouldNotOpenFOM & fdde) {
         std::wcerr << fdde.what() << std::endl;
         std::wcerr.flush();
      } catch (const Exception & e) {
         std::wcerr << e.what() << std::endl;
         std::wcerr.flush();
      } catch (const std::exception & e2) {
         std::wcerr << e2.what() << std::endl;
         std::wcerr.flush();
      }
      std::wcout << std::endl << L"Press <ENTER> to shutdown" << std::endl;
      wstring emptymsg;
      std::getline(std::wcin, emptymsg);
   }

   void printMessage(
         InteractionClassHandle const & interactionClass,
         ParameterHandleValueMap const & parameterValues,
         bool directedInteraction)
   {
      if (interactionClass == _messageInteractionClass) {
         HLAunicodeString message;
         HLAunicodeString sender;

         for (auto i = parameterValues.begin(); i != parameterValues.end(); ++i) {
            ParameterHandle const & handle = i->first;
            VariableLengthData const & value = i->second;
            if (handle == _paramText) {
               message.decode(value);
            } else if (handle == _paramSender) {
               sender.decode(value);
            }
         }
         if (directedInteraction) {
            std::wcout << L"Private message from ";
         }
         std::wcout << wstring(sender) << L": " << wstring(message) << std::endl;
      }
   }
   void receiveDirectedInteraction(
         InteractionClassHandle const & interactionClass,
         ObjectInstanceHandle const & objectInstance,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate) override
   {
      printMessage(interactionClass, parameterValues, true);
   }

   void receiveInteraction(
         InteractionClassHandle const & interactionClass,
         ParameterHandleValueMap const & parameterValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate,
         const RegionHandleSet * optionalSentRegions) override
   {
      printMessage(interactionClass, parameterValues, false);
   }

   void reflectAttributeValues(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleValueMap const & attributeValues,
         VariableLengthData const & userSuppliedTag,
         TransportationTypeHandle const & transportationType,
         FederateHandle const & producingFederate,
         RegionHandleSet const * optionalSentRegions) override
   {
      HLAunicodeString name;
      name.decode(attributeValues.find(_attrName)->second);

      if (_knownObjects.count(objectInstance) == 0) {
         Participant member{(wstring) name, objectInstance};
         std::wcout << L"[ " << member.toString() << L" has joined the chat ]" << std::endl;
         std::wcout << L"> ";
         _knownObjects[objectInstance] = member;
         _knownParticipants[member.toString()] = member;
      }
   }

   void objectInstanceNameReservationSucceeded(
         wstring const & objectInstanceName) override
   {
      std::lock_guard<std::mutex> guard(mutex);
      _reservationComplete = true;
      _reservationSucceeded = true;
      reservationCompleted.notify_all();
   }

   void objectInstanceNameReservationFailed(
         wstring const & objectInstanceName) override
   {
      std::lock_guard<std::mutex> guard(mutex);
      _reservationComplete = true;
      _reservationSucceeded = false;
      reservationCompleted.notify_all();
   }

   void removeObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         VariableLengthData const & userSuppliedTag,
         FederateHandle const & producingFederate) override
   {
      if (_knownObjects.count(objectInstance)) {
         auto iter = _knownObjects.find(objectInstance);
         Participant member{iter->second};
         _knownObjects.erase(objectInstance);
         _knownParticipants.erase(member.toString());
         std::wcout << L"[ " << member.toString() << L" has left the chat ]" << std::endl;
         std::wcout << L"> ";
      }
   }

   void provideAttributeValueUpdate(
         ObjectInstanceHandle const & objectInstance,
         AttributeHandleSet const & attributes,
         VariableLengthData const & userSuppliedTag) override
   {
      if (objectInstance == _myObjectInstance && attributes.count(_attrName) != 0) {
         HLAunicodeString unicodeUserName{_username};
         AttributeHandleValueMap attributeValues;
         attributeValues[_attrName] = unicodeUserName.encode();
         _rtiAmbassador->updateAttributeValues(_myObjectInstance, attributeValues, VariableLengthData{});
      }
   }

   void discoverObjectInstance(
         ObjectInstanceHandle const & objectInstance,
         ObjectClassHandle const & objectClassHandle,
         wstring const & objectInstanceName,
         FederateHandle const & producingFederate) override
   {
      _rtiAmbassador->requestAttributeValueUpdate(objectInstance, _attributes, VariableLengthData());
   }
};


int main(
      int argc,
      char * argv[])
{
   ChatFederateHla4 chatFederate;
   chatFederate.run(argc, argv);
   return 0;
}
