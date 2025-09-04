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

#include <fedpro/Aliases.h>

#include <functional>
#include <memory>

namespace FedPro
{
   class ThreadPool
   {
   public:

      explicit ThreadPool(uint32_t numberOfThreads);

      /**
       * @brief Destroy a ThreadPool, but do not wait for job termination.
       */
      ~ThreadPool();

      void start();

      void queueJob(const std::function<void()> & job);

      void clearJobs();

      /**
       * @Brief Instruct threads to terminate, but do not wait for job termination.
       */
      void shutdown(bool processRemainingJobs);

      bool isShutdown() const;

      /**
       * @Brief Wait for all threads to terminate, following a call to shutdown().
       */
      void waitTermination();

      /**
       * @Brief Wait for all threads to terminate, following a call to shutdown().
       */
      bool waitTermination(FedProDuration timeout);

      bool isTerminated() const;

   private:
      class State;

      std::shared_ptr<State> _state;
   };

} // FedPro
