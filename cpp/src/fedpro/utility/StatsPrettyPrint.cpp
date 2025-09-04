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

#include "StatsPrettyPrint.h"

#include <iomanip>

namespace FedPro
{

   namespace StatsPrettyPrint
   {

      std::ostream & titles(std::ostream & stream)
      {
         stream << "    Average        Max        Min      Total   All time";
         return stream;
      }

      std::ostream & titlesPerSecond(std::ostream & stream)
      {
         stream << "  Average/s      Max/s      Min/s      Total   All time";
         return stream;
      }

      std::ostream & padStat(std::ostream & stream)
      {
         // Preserve format flags that may be affected by the printing of characters.
         stream << "           ";
         return stream;
      }

      std::ostream & setFormat(std::ostream & stream)
      {
         stream << std::setfill(' ') << std::setw(11) << std::setprecision(2);
         return stream;
      }

      std::ostream & operator<<(std::ostream & stream, MovingStats::Stats & stat)
      {
         stream << setFormat << stat._averageBucket << setFormat << stat._maxBucket << setFormat << stat._minBucket
                << setFormat << stat._sum << setFormat << stat._historicTotal;
         return stream;
      }

   } // StatsPrettyPrint

} // FedPro