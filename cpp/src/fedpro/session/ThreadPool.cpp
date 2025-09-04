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

#include <atomic>
#include <condition_variable>
#include <mutex>
#include <queue>
#include <stdexcept>
#include <thread>

namespace FedPro
{
   class ThreadPool::State {
   public:

      explicit State(uint32_t numberOfThreads)
            : _numberOfThreads{numberOfThreads}
      {
      }

      void start(const std::shared_ptr<State> & stateRef);

      void queueJob(const std::function<void()> & job);

      void clearJobs();

      void shutdownIfRunning(bool processRemainingJobs);

      void shutdown(bool processRemainingJobs);

      bool isShutdown() const;

      void waitTermination();

      bool waitTermination(FedProDuration timeout);

      bool isTerminated() const;

   private:

      void threadLoop();

      void terminate();

      void waitTermination(std::unique_lock<std::mutex> & lock); // REQUIRES(_queueMutex)

      bool waitTermination(std::unique_lock<std::mutex> & lock, FedProDuration timeout); // REQUIRES(_queueMutex)

      bool _shutdown{false}; // GUARDED_BY(_queueMutex)
      bool _processJobsOnStop{false}; // GUARDED_BY(_queueMutex)
      std::atomic<uint32_t> _activeThreadCount{0};

      mutable std::mutex _queueMutex;
      std::condition_variable _condition;

      uint32_t _numberOfThreads; // GUARDED_BY(_queueMutex)
      std::vector<std::thread> _threads; // GUARDED_BY(_queueMutex)
      std::queue<std::function<void()>> _jobs; // GUARDED_BY(_queueMutex)
   };

   ThreadPool::ThreadPool(uint32_t numberOfThreads)
         : _state{std::make_shared<State>(numberOfThreads)}
   {
   }

   ThreadPool::~ThreadPool()
   {
      _state->shutdownIfRunning(false);
   }

   void ThreadPool::start()
   {
      // Pass a shared_ref<State> to the State to allow object lifetime management.
      _state->start(_state);
   }

   void ThreadPool::State::start(const std::shared_ptr<State> & stateRef)
   {
      std::unique_lock<std::mutex> lock(_queueMutex);
      if (!_shutdown && !_threads.empty()) {
         throw std::logic_error("Cannot start while running");
      }
      // Ensure the pool is inactive, potentially waiting for threads to stop.
      waitTermination(lock);
      _shutdown = false;
      _processJobsOnStop = false;
      for (uint32_t i = 0; i < _numberOfThreads; i++) {
         // Pass a shared_ref<ThreadPool::State> to each thread, to ensure the State lifetime
         // extends until the last thread goes away.
         _threads.emplace_back(&ThreadPool::State::threadLoop, stateRef);
      }
      _activeThreadCount = _numberOfThreads;
   }

   void ThreadPool::queueJob(const std::function<void()> & job)
   {
      _state->queueJob(job);
   }

   void ThreadPool::State::queueJob(const std::function<void()> & job)
   {
      {
         std::unique_lock<std::mutex> lock(_queueMutex);
         _jobs.push(job);
      }
      _condition.notify_one();
   }

   void ThreadPool::clearJobs()
   {
      _state->clearJobs();
   }

   void ThreadPool::State::clearJobs()
   {
      std::queue<std::function<void()>> empty;
      std::lock_guard<std::mutex> lock(_queueMutex);
      std::swap(_jobs, empty);
   }

   void ThreadPool::State::shutdownIfRunning(bool processRemainingJobs)
   {
      std::lock_guard<std::mutex> lock(_queueMutex);
      if (!_shutdown)  {
         _shutdown = true;
         _processJobsOnStop = processRemainingJobs;
         _condition.notify_all();
      }
   }

   void ThreadPool::shutdown(bool processRemainingJobs)
   {
      _state->shutdown(processRemainingJobs);
   }

   void ThreadPool::State::shutdown(bool processRemainingJobs)
   {
      std::lock_guard<std::mutex> lock(_queueMutex);
      _shutdown = true;
      _processJobsOnStop = processRemainingJobs;
      _condition.notify_all();
   }

   bool ThreadPool::isShutdown() const
   {
      return _state->isShutdown();
   }

   bool ThreadPool::State::isShutdown() const
   {
      std::lock_guard<std::mutex> lock(_queueMutex);
      return _shutdown;
   }

   void ThreadPool::waitTermination()
   {
      _state->waitTermination();
   }

   void ThreadPool::State::waitTermination()
   {
      std::unique_lock<std::mutex> lock(_queueMutex);
      waitTermination(lock);
   }

   bool ThreadPool::waitTermination(FedProDuration timeout)
   {
      return _state->waitTermination(timeout);
   }

   bool ThreadPool::State::waitTermination(FedProDuration timeout)
   {
      std::unique_lock<std::mutex> lock(_queueMutex);
      return waitTermination(lock, timeout);
   }

   bool ThreadPool::isTerminated() const
   {
      return _state->isTerminated();
   }

   bool ThreadPool::State::isTerminated() const
   {
      std::lock_guard<std::mutex> lock(_queueMutex);
      return (_threads.empty() || _activeThreadCount == 0);
   }

   // Private methods

   void ThreadPool::State::terminate()
   {
      std::unique_lock<std::mutex> lock(_queueMutex);
      while (_activeThreadCount > 0) {
         _condition.wait(lock);
      }
      std::thread::id thisThreadId = std::this_thread::get_id();
      for (std::thread & activeThread : _threads) {
         if (activeThread.get_id() == thisThreadId) {
            activeThread.detach();
         } else if (activeThread.joinable()) {
            activeThread.join();
         }
      }
      _threads.clear();
      _shutdown = false;
      _condition.notify_all();
   }

   void ThreadPool::State::waitTermination(std::unique_lock<std::mutex> & lock)
   {
      while (!_threads.empty() && _activeThreadCount > 0) {
         _condition.wait(lock);
      }
   }

   bool ThreadPool::State::waitTermination(std::unique_lock<std::mutex> & lock, FedProDuration timeout)
   {
      auto timeoutPoint = std::chrono::steady_clock::now() + timeout;
      return _condition.wait_until(
            lock,
            timeoutPoint,
            [this]() { return _threads.empty() || _activeThreadCount == 0; });
   }

   // Every thread in the pool runs this loop
   void ThreadPool::State::threadLoop()
   {
      while (true) {
         std::function<void()> job;
         {
            std::unique_lock<std::mutex> lock(_queueMutex);
            _condition.wait(
                  lock, [this] {
                     return !_jobs.empty() || _shutdown;
                  });
            if (_shutdown && (!_processJobsOnStop || _jobs.empty())) {
               break;
            }
            job = _jobs.front();
            _jobs.pop();
         }
         try {
            job();
         } catch (...) {
            uint32_t activeThreadCount = _activeThreadCount.fetch_sub(1) - 1;
            _condition.notify_all();
            if (activeThreadCount == 0) {
               // The last thread terminate and cleanup the State
               terminate();
            }
            throw;
         }
      }
      uint32_t activeThreadCount = _activeThreadCount.fetch_sub(1) - 1;
      _condition.notify_all();
      if (activeThreadCount == 0) {
         // The last thread terminate and cleanup the State
         terminate();
      }
   }

} // FedPro
