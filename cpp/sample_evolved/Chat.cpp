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


#include <RTI/encoding/BasicDataElements.h>
#include <RTI/NullFederateAmbassador.h>
#include <RTI/RTI1516.h>
#include <RTI/RTIambassadorFactory.h>
#include <RTI/time/HLAinteger64Interval.h>
#include <RTI/time/HLAinteger64Time.h>
#include <RTI/time/HLAinteger64TimeFactory.h>

#ifdef _WIN32

#include <fcntl.h>
#include <io.h>

#endif

#include <condition_variable>
#include <iostream>
#include <map>
#include <mutex>
#include <string>


using std::string;
using std::wstring;
using namespace rti1516e;

std::mutex _mutex;
std::condition_variable _threshold_cv;


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
 * @breif HLA Evolved Chat sample for Federate Protocol.
 */
class ChatFederate : public NullFederateAmbassador
{
private:
   std::auto_ptr<RTIambassador> _rtiAmbassador;
   InteractionClassHandle _iMessageId;
   ParameterHandle _pTextId;
   ParameterHandle _pSenderId;
   ObjectClassHandle _oParticipantId;
   ObjectInstanceHandle _iParticipantHdl;
   AttributeHandle _aNameId;
   AttributeHandleSet _aHandleSet;
   ParameterHandleValueMap _pHandleValueMap;
   AttributeHandleValueMap _aHandleValueMap;

   wstring _username;
   wstring _message;

   bool _reservationSucceeded;
   bool _reservationComplete;
   const wstring FEDERATION_NAME{L"ChatRoom"};

public:

   class Participant
   {
   private:
      wstring _name;
   public:
      Participant(wstring name)
      {
         _name = name;
      }

      Participant()
      {
      }

      wstring toString()
      {
         return _name;
      }
   };

   std::map<ObjectInstanceHandle, Participant> _knownObjects;

   ~ChatFederate() throw()
   {
      // Empty
   }

   void run(
         int argc,
         char * argv[])
   {
      wstring rtiHost = L"localhost";
      std::vector<wstring> FOMmoduleUrls;

      FOMmoduleUrls.push_back(L"Chat-evolved.xml");
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
         auto rtiAmbassadorFactory = new RTIambassadorFactory{};
         _rtiAmbassador = rtiAmbassadorFactory->createRTIambassador();
      } catch (const RTIinternalError & ignored) {
         std::wcout << std::endl << L"Unable to create RTI ambassador" << std::endl;
         return;
      }

      try {
         const wstring localSettingsDesignator{L"crcAddress=" + rtiHost};
         _rtiAmbassador->connect(*this, HLA_IMMEDIATE, localSettingsDesignator);

         try {
            _rtiAmbassador->destroyFederationExecution(FEDERATION_NAME);
         } catch (const FederatesCurrentlyJoined & ignored) {
         } catch (const FederationExecutionDoesNotExist & ignored) {
         } catch (const RTIinternalError & ignored) {
         }

         try {
            _rtiAmbassador->createFederationExecutionWithMIM(FEDERATION_NAME, FOMmoduleUrls, L"");
         } catch (const FederationExecutionAlreadyExists & ignored) {
         }

         FederateHandle federateHandle = _rtiAmbassador->joinFederationExecution(
               L"Chat", FEDERATION_NAME, FOMmoduleUrls);

         _iMessageId = _rtiAmbassador->getInteractionClassHandle(L"Communication");
         _pTextId = _rtiAmbassador->getParameterHandle(_iMessageId, L"Message");
         _pSenderId = _rtiAmbassador->getParameterHandle(_iMessageId, L"Sender");
         _oParticipantId = _rtiAmbassador->getObjectClassHandle(L"Participant");
         _aNameId = _rtiAmbassador->getAttributeHandle(_oParticipantId, L"Name");
         _aHandleSet.insert(_aNameId);

         _rtiAmbassador->subscribeInteractionClass(_iMessageId);
         _rtiAmbassador->publishInteractionClass(_iMessageId);
         _rtiAmbassador->subscribeObjectClassAttributes(_oParticipantId, _aHandleSet);
         _rtiAmbassador->publishObjectClassAttributes(_oParticipantId, _aHandleSet);

#ifdef _WIN32
         // Make sure that stdin and stdout are in text mode. Initialization of
         // the Java Virtual Machine (done by pRTI) has a side-effect which is
         // to set stdin and stdout to binary mode.
         _setmode(_fileno(stdout), _O_TEXT);
         _setmode(_fileno(stderr), _O_TEXT);
         _setmode(_fileno(stdin), _O_TEXT);
#endif

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

               std::unique_lock<std::mutex> lock(_mutex);
               _threshold_cv.wait(lock, [this] { return _reservationComplete; });
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
         _iParticipantHdl = _rtiAmbassador->registerObjectInstance(_oParticipantId, _username);
         _aHandleValueMap[_aNameId] = unicodeUserName.encode();
         _rtiAmbassador->updateAttributeValues(_iParticipantHdl, _aHandleValueMap, VariableLengthData{});

         std::wcout << L"Type messages you want to send. To exit, type . <ENTER>" << std::endl;
         while (true) {
            wstring wmsg;
            std::wcout << L"> ";
            std::wcout.flush();
            std::getline(std::wcin, wmsg);

            if (wmsg == L".") {
               break;
            }

            HLAunicodeString unicodeMessage{wmsg};
            _pHandleValueMap[_pTextId] = unicodeMessage.encode();
            _pHandleValueMap[_pSenderId] = unicodeUserName.encode();
            _rtiAmbassador->sendInteraction(_iMessageId, _pHandleValueMap, VariableLengthData{});
         }

         _rtiAmbassador->resignFederationExecution(CANCEL_THEN_DELETE_THEN_DIVEST);
         try {
            _rtiAmbassador->destroyFederationExecution(FEDERATION_NAME);
         } catch (const FederatesCurrentlyJoined &) {
         }
         _rtiAmbassador->disconnect();
      } catch (const CouldNotOpenFDD & fdde) {
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

   virtual void receiveInteraction(
         InteractionClassHandle theInteraction,
         ParameterHandleValueMap const & theParameterValues,
         VariableLengthData const & theUserSuppliedTag,
         OrderType sentOrder,
         TransportationType theType,
         SupplementalReceiveInfo theReceiveInfo) throw(FederateInternalError)
   {
      if (theInteraction == _iMessageId) {
         HLAunicodeString message;
         HLAunicodeString sender;

         for (auto i = theParameterValues.begin(); i != theParameterValues.end(); ++i) {
            ParameterHandle const & handle = i->first;
            VariableLengthData const & value = i->second;
            if (handle == _pTextId) {
               message.decode(value);
            } else if (handle == _pSenderId) {
               sender.decode(value);
            }
         }
         std::wcout << wstring{sender} << L": " << wstring{message} << std::endl;
      }
   }

   virtual void reflectAttributeValues(
         ObjectInstanceHandle theObject,
         AttributeHandleValueMap const & theAttributeValues,
         VariableLengthData const & theUserSuppliedTag,
         OrderType sentOrder,
         TransportationType theType,
         SupplementalReflectInfo theReflectInfo) throw(FederateInternalError)
   {
      HLAunicodeString name;
      name.decode(theAttributeValues.find(_aNameId)->second);

      if (_knownObjects.count(theObject) == 0) {
         Participant member{(wstring) name};
         std::wcout << L"[ " << member.toString() << L" has joined the chat ]" << std::endl;
         std::wcout << L"> ";
         _knownObjects[theObject] = member;
      }
   }

   virtual void objectInstanceNameReservationSucceeded(
         wstring const & theObjectInstanceName) throw(FederateInternalError)
   {
      std::lock_guard<std::mutex> guard(_mutex);
      _reservationComplete = true;
      _reservationSucceeded = true;
      _threshold_cv.notify_all();
   }

   virtual void objectInstanceNameReservationFailed(
         wstring const & theObjectInstanceName) throw(FederateInternalError)
   {
      std::lock_guard<std::mutex> guard(_mutex);
      _reservationComplete = true;
      _reservationSucceeded = false;
      _threshold_cv.notify_all();
   }

   virtual void removeObjectInstance(
         ObjectInstanceHandle const & theObject,
         VariableLengthData const & theUserSuppliedTag,
         OrderType const & sentOrder)
   {
      if (_knownObjects.count(theObject)) {
         auto iter = _knownObjects.find(theObject);
         Participant member{iter->second};
         _knownObjects.erase(theObject);
         std::wcout << L"[ " << member.toString() << L" has left the chat ]" << std::endl;
         std::wcout << L"> ";
      }
   }

   virtual void provideAttributeValueUpdate(
         ObjectInstanceHandle theObject,
         AttributeHandleSet const & theAttributes,
         VariableLengthData const & theUserSuppliedTag) throw(FederateInternalError)
   {
      if (theObject == _iParticipantHdl && theAttributes.count(_aNameId) != 0) {
         HLAunicodeString unicodeUserName{_username};
         AttributeHandleValueMap attributeValues;
         attributeValues[_aNameId] = unicodeUserName.encode();
         _rtiAmbassador->updateAttributeValues(_iParticipantHdl, attributeValues, VariableLengthData{});
      }
   }
};


int main(
      int argc,
      char * argv[])
{
   ChatFederate chatFederate;
   chatFederate.run(argc, argv);
   return 0;
}
