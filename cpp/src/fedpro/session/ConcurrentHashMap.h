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

#include <functional>
#include <memory>
#include <mutex>
#include <unordered_map>

namespace FedPro
{
   template<typename K, typename V>
   class ConcurrentHashMap
   {
   public:

      typedef size_t size_type;

      ConcurrentHashMap() = default;

      void moveAndInsert(
            const K & key,
            V && value)
      {
         std::lock_guard<std::mutex> lock(_mapMutex);
         _map[key] = std::move(value);
      }

      std::unique_ptr<V> get(
            const K & key)
      {
         std::lock_guard<std::mutex> lock(_mapMutex);
         auto it = _map.find(key);
         if (it == _map.end()) {
            return nullptr;
         }
         return std::make_unique<V>(it->second);
      }

      std::unique_ptr<V> remove(const K & keyToRemove)
      {
         std::lock_guard<std::mutex> lock(_mapMutex);
         auto it = _map.find(keyToRemove);
         if (it == _map.end()) {
            return nullptr;
         }

         std::unique_ptr<V> removedElement = std::make_unique<V>(std::move(it->second));
         _map.erase(it);
         return removedElement;
      }

      void swap(std::unordered_map<K, V> & other)
      {
         std::lock_guard<std::mutex> lock(_mapMutex);
         _map.swap(other);
      }

      void clear()
      {
         std::lock_guard<std::mutex> lock(_mapMutex);
         _map.clear();
      }

      size_type size()
      {
         std::lock_guard<std::mutex> lock (_mapMutex);
         return _map.size();
      }

   private:
      std::unordered_map<K, V> _map;
      std::mutex _mapMutex;
   };

} // FedPro
