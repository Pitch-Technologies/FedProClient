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

#include <session/buffers/CircularBuffer.h>

#include <gtest/gtest.h>

#include <thread>

class TestCircularBuffer : public ::testing::Test
{
protected:
   static FedPro::CircularBuffer<int>::Predicate getElementFinder(int element)
   {
      return [element](const int & value) {
         // Your logic here, for example:
         return element == value;
      };
   }
};


TEST_F(TestCircularBuffer, oldestAvailableValueIsReturned_When_PeekIsCalled)
{
   // Given
   FedPro::CircularBuffer<int> buf{3};
   buf.insert(0);
   buf.insert(1);

   // When
   int returnValue = *buf.peek();

   // Then
   ASSERT_EQ(0, returnValue);
}

TEST_F(TestCircularBuffer, peekReturnsOldestAvailableValue_When_OldestHasBeenOverwritten)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};
   buf.insert(0);
   buf.insert(1);

   // When
   buf.insert(2);

   // Then
   ASSERT_EQ(1, *buf.peek());
}


TEST_F(TestCircularBuffer, peekReturnsNull_WhenbufferIsEmpty)
{
   // Given
   FedPro::CircularBuffer<int> buf{1};

   // Then
   ASSERT_EQ(nullptr, buf.peek());
}


TEST_F(TestCircularBuffer, oldestValueIsReturned_When_PeekOldestIsCalled)
{
   // Given
   FedPro::CircularBuffer<int> buf{3};
   buf.insert(0);
   buf.insert(1);

   // When
   int oldestValue = *buf.peekOldest();

   // Then
   ASSERT_EQ(0, oldestValue);
}


TEST_F(TestCircularBuffer, peekOldestStillReturnsOldestValue_When_PollHasBeenCalled)
{
   // Given
   FedPro::CircularBuffer<int> buf{3};
   buf.insert(0);
   buf.insert(1);

   // When
   buf.poll();

   // Then
   ASSERT_EQ(0, *buf.peekOldest());
}


TEST_F(TestCircularBuffer, peekOldestReturnsOldestValue_When_OldestHasBeenOverwritten)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};
   buf.insert(0);
   buf.insert(1);

   // When
   buf.insert(2);

   // Then
   ASSERT_EQ(1, *buf.peekOldest());
}


TEST_F(TestCircularBuffer, peekOldestReturnsNull_WhenbufferIsEmpty)
{
   // Given
   FedPro::CircularBuffer<int> buf{1};

   // Then
   ASSERT_EQ(nullptr, buf.peekOldest());
}


TEST_F(TestCircularBuffer, oldestAvailableValueIsReturned_When_PollIsCalled)
{
   // Given
   FedPro::CircularBuffer<int> buf{3};
   buf.insert(0);
   buf.insert(1);

   // When
   int returnValue = *buf.poll();

   // Then
   ASSERT_EQ(0, returnValue);
}


TEST_F(TestCircularBuffer, oldestAvailableValueGetsUnavailable_When_PollIsCalled)
{
   // Given
   FedPro::CircularBuffer<int> buf{3};
   buf.insert(0);
   buf.insert(1);

   // When
   buf.poll();

   // Then
   ASSERT_EQ(1, buf.size());
   ASSERT_EQ(1, *buf.peek());
}


TEST_F(TestCircularBuffer, pollReturnsOldestAvailableValue_When_OldestHasBeenOverwritten)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};
   buf.insert(0);
   buf.insert(1);

   // When
   buf.insert(2);

   // Then
   ASSERT_EQ(1, *buf.poll());
}


TEST_F(TestCircularBuffer, pollReturnsNull_WhenbufferIsEmpty)
{
   // Given
   FedPro::CircularBuffer<int> buf{1};

   // Then
   ASSERT_EQ(nullptr, buf.poll());
}


TEST_F(TestCircularBuffer, newestValueIsReturned_When_PeekNewestIsCalled)
{
   // Given
   FedPro::CircularBuffer<int> buf{3};
   buf.insert(0);
   buf.insert(1);

   // When
   int newestValue = *buf.peekNewest();

   // Then
   ASSERT_EQ(1, newestValue);
}


TEST_F(TestCircularBuffer, peekNewestReturnsNull_WhenbufferIsEmpty)
{
   // Given
   FedPro::CircularBuffer<int> buf{1};

   // Then
   ASSERT_EQ(buf.peekNewest(), nullptr);
}


TEST_F(TestCircularBuffer, oldestAvailableValueIsReturned_When_WaitAndPollIsCalled)
{
   // Given
   FedPro::CircularBuffer<int> buf{3};
   buf.insert(0);
   buf.insert(1);

   // When
   int returnValue = *buf.waitAndPoll();

   // Then
   ASSERT_EQ(0, returnValue);
}


TEST_F(TestCircularBuffer, oldestAvailableValueGetsUnavailable_When_WaitAndPollIsCalled)
{
   // Given
   FedPro::CircularBuffer<int> buf{3};
   buf.insert(0);
   buf.insert(1);

   // When
   buf.waitAndPoll();

   // Then
   ASSERT_EQ(1, buf.size());
}


TEST_F(TestCircularBuffer, waitAndPollReturnsOldestAvailableValue_When_OldestHasBeenOverwritten)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};
   buf.insert(0);
   buf.insert(1);

   // When
   buf.insert(2);

   // Then
   ASSERT_EQ(1, *buf.waitAndPoll());
}


TEST_F(TestCircularBuffer, waitAndPollPolls_When_FirstValueIsInserted)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};

   std::thread myThread(
         [&buf]() {
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
            buf.insert(5);
         });

   // Then
   ASSERT_EQ(5, *buf.waitAndPoll());

   if (myThread.joinable()) {
      myThread.join();
   }
}


TEST_F(TestCircularBuffer, oldestAvailableValueIsReturned_When_WaitAndPeekIsCalled)
{
   // Given
   FedPro::CircularBuffer<int> buf{3};
   buf.insert(0);
   buf.insert(1);

   // When
   int returnValue = *buf.waitAndPeek();

   // Then
   ASSERT_EQ(0, returnValue);
}


TEST_F(TestCircularBuffer, waitAndPeekReturnsOldestAvailableValue_When_OldestHasBeenOverwritten)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};
   buf.insert(0);
   buf.insert(1);

   // When
   buf.insert(2);

   // Then
   ASSERT_EQ(1, *buf.waitAndPeek());
}


TEST_F(TestCircularBuffer, waitAndPeekPeeks_When_FirstValueIsInserted)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};

   // When
   std::thread myThread(
         [&buf]() {
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
            buf.insert(5);
         });

   // Then
   ASSERT_EQ(5, *buf.waitAndPeek());

   if (myThread.joinable()) {
      myThread.join();
   }
}


TEST_F(TestCircularBuffer, sizeReturnsZero_WhenbufferIsEmpty)
{
   // Given
   FedPro::CircularBuffer<int> buf{4};

   // Then
   ASSERT_EQ(0, buf.size());
}


TEST_F(TestCircularBuffer, sizeReturnsTwo_When_TwoElementsHaveBeenInserted)
{
   // Given
   FedPro::CircularBuffer<int> buf{4};

   // When
   buf.insert(0);
   buf.insert(1);

   // Then
   ASSERT_EQ(2, buf.size());
}


TEST_F(TestCircularBuffer, sizeIsReducedByOne_When_PollIsCalled)
{
   // Given
   FedPro::CircularBuffer<int> buf{4};
   buf.insert(0);
   buf.insert(1);

   // When
   buf.poll();

   // Then
   ASSERT_EQ(1, buf.size());
}


TEST_F(TestCircularBuffer, sizeIsUnchanged_When_InsertingMoreElementsThanBufferCapacity)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};
   buf.insert(0);
   buf.insert(1);

   // When
   buf.insert(3);

   // Then
   ASSERT_EQ(2, buf.size());
}


TEST_F(TestCircularBuffer, isEmptyReturnsTrue_WhenbufferIsEmpty)
{
   // Given
   FedPro::CircularBuffer<int> buf{1};

   // Then
   ASSERT_TRUE(buf.isEmpty());

}


TEST_F(TestCircularBuffer, isEmptyReturnsFalse_WhenbufferIsNotEmpty)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};

   // When
   buf.insert(0);

   // Then
   ASSERT_FALSE(buf.isEmpty());
}


TEST_F(TestCircularBuffer, isEmptyReturnsFalse_WhenbufferIsFull)
{
   // Given
   FedPro::CircularBuffer<int> buf{1};

   // When
   buf.insert(0);

   // Then
   ASSERT_FALSE(buf.isEmpty());
}


TEST_F(TestCircularBuffer, isFullReturnsTrue_WhenbufferIsFull)
{
   // Given
   FedPro::CircularBuffer<int> buf{1};

   // When
   buf.insert(0);

   // Then
   ASSERT_TRUE(buf.isFull());
}


TEST_F(TestCircularBuffer, isFullReturnsFalse_WhenbufferIsNotFull)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};

   // When
   buf.insert(0);

   // Then
   ASSERT_FALSE(buf.isFull());
}


TEST_F(TestCircularBuffer, isFullReturnsFalse_WhenbufferIsEmpty)
{
   // Given
   FedPro::CircularBuffer<int> buf{1};

   // Then
   ASSERT_FALSE(buf.isFull());
}


TEST_F(TestCircularBuffer, stateIsCorrect_When_RewindingByOneIndex)
{
   // Given
   FedPro::CircularBuffer<int> buf{4};
   buf.insert(0);
   buf.insert(1);
   buf.insert(2);
   buf.poll();

   // When
   buf.rewindTo(getElementFinder(0));

   // Then
   ASSERT_EQ(3, buf.size());
   ASSERT_EQ(0, *buf.peek());
}


TEST_F(TestCircularBuffer, stateIsCorrect_When_RewindingToFirstElement)
{
   // Given
   FedPro::CircularBuffer<int> buf{3};
   buf.insert(0);
   buf.insert(1);
   buf.insert(2);
   buf.poll();
   buf.poll();
   buf.poll();

   // When
   int element = 0;
   buf.rewindTo(getElementFinder(element));

   // Then
   ASSERT_EQ(3, buf.size());
   ASSERT_EQ(0, *buf.peek());
}


TEST_F(TestCircularBuffer, stateIsCorrect_When_RewindingByOneMultipleTimes)
{
   // Given
   FedPro::CircularBuffer<int> buf{3};
   buf.insert(0);
   buf.insert(1);
   buf.insert(2);
   buf.poll();
   buf.poll();
   buf.poll();
   buf.insert(3);
   buf.poll();
   buf.insert(4);
   buf.insert(5);
   buf.insert(6);
   buf.poll();
   buf.poll();
   buf.poll();

   // Future reads: []
   // Past reads: [4, 5, 6] (6 is the last read)

   int data[] = {6, 5, 4};

   for (int i = 0; i < sizeof(data) / sizeof(data[0]); i++) {
      int rewindTo = data[i];

      // When
      buf.rewindTo(getElementFinder(rewindTo));
      // Then
      ASSERT_EQ(i + 1, buf.size());
      ASSERT_EQ(rewindTo, *buf.peek());
   }
}


TEST_F(TestCircularBuffer, stateIsCorrect_When_RewindingTheEntireBufferMultipleTimes)
{
   // Given
   FedPro::CircularBuffer<int> buf{3};
   buf.insert(0);
   buf.insert(1);
   buf.insert(2);
   buf.poll();
   buf.poll();
   buf.poll();
   buf.insert(3);
   buf.poll();
   buf.insert(4);
   buf.insert(5);
   buf.insert(6);
   buf.poll();
   buf.poll();
   buf.poll();
   int element = 4;
   buf.rewindTo(getElementFinder(element));
   buf.poll();
   buf.poll();
   buf.poll();

   // When
   element = 4;
   buf.rewindTo(getElementFinder(element));

   // Then
   ASSERT_EQ(4, *buf.poll());
   ASSERT_EQ(2, buf.size());
   ASSERT_EQ(5, *buf.peek());
}


TEST_F(TestCircularBuffer, rewindsToFirstElement_When_RewindingToFirstElementBeforeWraparound)
{
   // Given
   FedPro::CircularBuffer<int> buf{3};
   buf.insert(0);
   buf.insert(1);
   buf.poll();
   buf.poll();

   // When
   int element = 0;
   buf.rewindTo(getElementFinder(element));

   // Then
   ASSERT_EQ(0, *buf.peek());
}


TEST_F(TestCircularBuffer, rewindsToNonFirstElement_When_RewindingToNonFirstElementBeforeWraparound)
{
   // Given
   FedPro::CircularBuffer<int> buf{3};
   buf.insert(0);
   buf.insert(1);
   buf.insert(2);
   buf.poll();
   buf.poll();

   // When
   int element = 1;
   buf.rewindTo(getElementFinder(element));

   // Then
   ASSERT_EQ(1, *buf.peek());
}


TEST_F(TestCircularBuffer, rewindThrows_When_RewindingToToANonPresentValue)
{
   // Given
   FedPro::CircularBuffer<int> buf{4};

   // When
   int element = 3;
   EXPECT_THROW(buf.rewindTo(getElementFinder(element)), std::invalid_argument);

   // Then throw.
}

TEST_F(TestCircularBuffer, rewindThrows_When_RewindingToToPresentNonReadValueBeforeWraparound)
{
   // Given
   FedPro::CircularBuffer<int> buf{4};
   buf.insert(3);

   // When
   int element = 3;
   EXPECT_THROW(buf.rewindTo(getElementFinder(element)), std::invalid_argument);

   // Then throw.
}


TEST_F(TestCircularBuffer, rewindThrows_When_RewindingToToPresentNonReadValueAfterWraparound)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};
   buf.insert(0);
   buf.insert(1);
   buf.poll();
   buf.poll();
   buf.insert(2);
   buf.insert(3);
   buf.poll();
   buf.poll();

   // When
   int element = 6;
   EXPECT_THROW(buf.rewindTo(getElementFinder(element)), std::invalid_argument);

   // Then throw.
}


TEST_F(TestCircularBuffer, rewindsAllValues_When_RewindingToPastZero)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};
   buf.insert(0);
   buf.poll();
   buf.insert(1);
   buf.poll();
   buf.insert(2);
   buf.poll();

   // When
   buf.rewindTo(getElementFinder(1));

   // Then
   ASSERT_EQ(2, buf.size());
   ASSERT_EQ(1, *buf.peek());
}


TEST_F(TestCircularBuffer, wrapAroundHappens_When_RewindingToPastZero)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};
   buf.insert(0);
   buf.poll();
   buf.insert(1);
   buf.poll();
   buf.insert(2);
   buf.poll();

   // When
   int element = 1;
   buf.rewindTo(getElementFinder(element));

   // Then
   ASSERT_EQ(1, *buf.peek());
}


TEST_F(TestCircularBuffer, waitUntilEmptyDoesNotThrow_When_PollHappensAfter100ms)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};
   buf.insert(0);
   buf.insert(1);

   std::thread myThread(
         [&buf]() {
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
            buf.poll();
            buf.poll();
         });

   // When
   buf.waitUntilEmpty(FedPro::FedProDuration(0));

   // Then do not throw.
   if (myThread.joinable()) {
      myThread.join();
   }
}


TEST_F(TestCircularBuffer, waitUntilEmptyDoesNotWait_WhenbufferIsEmpty)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};

   // Then
   buf.waitUntilEmpty(FedPro::FedProDuration(0));
}


TEST_F(TestCircularBuffer, waitUntilEmptyTimesOut_WhenbufferDoesNotGetEmpty)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};
   buf.insert(0);

   // When
   bool result = buf.waitUntilEmpty(FedPro::FedProDuration(100));

   // Then
   ASSERT_FALSE(result);
}


TEST_F(TestCircularBuffer, rewindToFirstDoesNotThrow_When_BufferIsEmpty)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};

   // When
   buf.rewindToFirst();

   // Then do not throw
}


TEST_F(TestCircularBuffer, rewindToFirstWorks_When_RewindingFullBufferToFirstMessage)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};
   buf.insert(3);
   buf.insert(2);
   buf.poll();
   buf.poll();

   // When
   buf.rewindToFirst();

   // Then
   ASSERT_EQ(3, *buf.poll());
   ASSERT_EQ(2, *buf.poll());
   ASSERT_EQ(0, buf.size());
}


TEST_F(TestCircularBuffer, rewindToFirstWorks_When_RewindingHalfFullBufferToFirstMessage)
{
   // Given
   FedPro::CircularBuffer<int> buf{4};
   buf.insert(3);
   buf.insert(2);
   buf.poll();
   buf.poll();

   // When
   buf.rewindToFirst();

   // Then
   ASSERT_EQ(3, *buf.poll());
   ASSERT_EQ(2, *buf.poll());
   ASSERT_EQ(0, buf.size());
}


TEST_F(TestCircularBuffer, rewindToFirstWorks_When_UnreadMessagesExists)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};
   buf.insert(3);
   buf.insert(2);
   buf.poll();

   // When
   buf.rewindToFirst();

   // Then
   ASSERT_EQ(3, *buf.poll());
   ASSERT_EQ(2, *buf.poll());
   ASSERT_EQ(0, buf.size());
}


TEST_F(TestCircularBuffer, rewindToFirstTHrows_When_BufferHasWrappedCompletely)
{
   // Given
   FedPro::CircularBuffer<int> buf{2};
   buf.insert(3);
   buf.insert(2);
   buf.poll();
   buf.insert(1);

   // When
   ASSERT_THROW(buf.rewindToFirst(), std::invalid_argument);
}
