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

#pragma once

#include <fedpro/Config.h>

#include <exception>
#include <string>

namespace FedPro
{
   /**
    * @brief Base class for custom exceptions in the FedPro application.
    */
   class FedProException : public std::exception
   {
   public:

      FEDPRO_EXPORT explicit FedProException(std::string message) noexcept;

      /**
       * @brief Returns the exception message.
       * @return A const char pointer to the exception message.
       */
      FEDPRO_EXPORT const char * what() const noexcept override;

      /**
       * @brief Returns the raw name of the exception.
       * @return A char pointer to the exception name.
       */
      FEDPRO_EXPORT const char * getName() const noexcept;

   private:
      std::string _message;
   };

   /**
    * @brief Session layer exception that indicates that a received message is invalid.
    */
   class BadMessage : public FedProException
   {
   public:
      FEDPRO_EXPORT explicit BadMessage(std::string message);
   };

   /**
    * @brief Service layer exception thrown when the current thread is interrupted by
    * another one.
    */
   class InterruptedException : public FedProException
   {
   public:
      FEDPRO_EXPORT explicit InterruptedException(std::string message);
   };

   /**
    * @brief Session layer exception that may be thrown when failing to insert a message
    * into a queue with no free allocated space left.
    */
   class MessageQueueFull : public FedProException
   {
   public:
      FEDPRO_EXPORT explicit MessageQueueFull(std::string message);
   };

   /**
    * @brief Session layer exception thrown when the state prohibits the intended
    * operation.
    */
   class SessionIllegalState : public FedProException
   {
   public:
      FEDPRO_EXPORT explicit SessionIllegalState(std::string message);
   };

   /**
    * @brief Session layer exception thrown when calling a member function but the session
    * has terminated.
    */
   class SessionAlreadyTerminated : public SessionIllegalState
   {
   public:
      FEDPRO_EXPORT explicit SessionAlreadyTerminated(std::string message);
   };

   /**
    * @brief Session layer exception indicating that the session cannot continue for some
    * reason. The session is unusable once this exception is thrown.
    */
   class SessionLost : public FedProException
   {
   public:
      FEDPRO_EXPORT explicit SessionLost(std::string message);
   };

} // FedPro
