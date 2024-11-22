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

#include <condition_variable>
#include <functional>
#include <mutex>
#include <queue>
#include <thread>

namespace FedPro
{
   class ThreadPool
   {
   public:

      explicit ThreadPool(uint32_t numberOfThreads);

      ~ThreadPool();

      void start();

      void queueJob(const std::function<void()> & job);

      void clearJobs();

      void stop(bool processRemainingJobs);

   private:
      void threadLoop();

      bool _stop{false};
      bool _processJobsOnStop{false};
      std::mutex _queueMutex;
      std::condition_variable _condition;
      std::vector<std::thread> _threads;
      std::queue<std::function<void()>> _jobs;
      uint32_t _numberOfThreads;
   };

} // FedPro
