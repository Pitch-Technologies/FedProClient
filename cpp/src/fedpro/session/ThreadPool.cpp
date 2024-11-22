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

#include "ThreadPool.h"

#include <stdexcept>

namespace FedPro
{

   ThreadPool::ThreadPool(uint32_t numberOfThreads)
         : _queueMutex{},
           _condition{},
           _threads{},
           _jobs{},
           _numberOfThreads{numberOfThreads}
   {
   }

   ThreadPool::~ThreadPool()
   {
      stop(false);
   }

   void ThreadPool::start()
   {
      if (!_threads.empty()) {
         throw std::logic_error("Cannot start while running");
      }
      std::unique_lock<std::mutex> lock(_queueMutex);
      _stop = false;
      _processJobsOnStop = false;
      for (uint32_t i = 0; i < _numberOfThreads; i++) {
         _threads.emplace_back(&ThreadPool::threadLoop, this);
      }
   }

   void ThreadPool::queueJob(const std::function<void()> & job)
   {
      {
         std::unique_lock<std::mutex> lock(_queueMutex);
         _jobs.push(job);
      }
      _condition.notify_one();
   }

   void ThreadPool::clearJobs()
   {
      std::queue<std::function<void()>> empty;
      std::lock_guard<std::mutex> lock(_queueMutex);
      std::swap(_jobs, empty);
   }

   void ThreadPool::stop(bool processRemainingJobs)
   {
      {
         std::lock_guard<std::mutex> lock(_queueMutex);
         _stop = true;
         _processJobsOnStop = processRemainingJobs;
         _condition.notify_all();
      }
      for (std::thread & activeThread : _threads) {
         if (activeThread.joinable()) {
            activeThread.join();
         }
      }
      _threads.clear();
   }

   // Private methods

   // Every thread in the pool runs this loop
   void ThreadPool::threadLoop()
   {
      while (true) {
         std::function<void()> job;
         {
            std::unique_lock<std::mutex> lock(_queueMutex);
            _condition.wait(
                  lock, [this] {
                     return !_jobs.empty() || _stop;
                  });
            if (_stop && (!_processJobsOnStop || _jobs.empty())) {
               return;
            }
            job = _jobs.front();
            _jobs.pop();
         }
         job();
      }
   }

} // FedPro
