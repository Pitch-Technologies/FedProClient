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

// Silence clang-tidy issues reported for gtest macros.
// NOLINTBEGIN(cppcoreguidelines-avoid-non-const-global-variables)

#include <transport/TcpSocket.h>

#include <gtest/gtest.h>

#include <future>

using namespace FedPro;

class TestTcpSocket : public ::testing::Test
{
};

TEST_F(TestTcpSocket, send_recv_large_message)
{
   // WHEN
   // Message size doesn't fit in 16 bits
   const size_t messageSize = 20000;

   TcpSocket serverSocket("localhost", 10495);
   ASSERT_TRUE(serverSocket.listen());

   auto future = std::async([&serverSocket, messageSize](){
      // Wait for a client connection.

      auto clientAcceptSocket = serverSocket.accept();

      // Receive message.
      // This may require multiple recv calls, since recv' size parameter is only an upper bound.

      std::string readMessage(messageSize, '0');
      char *recvBuffer = const_cast<char *>(readMessage.data());
      uint32_t recvBufferSize = readMessage.size();
      int readBytesTotal{0};
      while (readBytesTotal < messageSize) {
         int readBytes = clientAcceptSocket->recv(recvBuffer, messageSize - readBytesTotal);
         readBytesTotal += readBytes;
         recvBuffer += readBytes;
         recvBufferSize -= readBytes;
      }
      EXPECT_EQ(readBytesTotal, messageSize);
      return readMessage;
   });

   std::string message(messageSize, '0');
   for (int i = 0; i < message.size(); ++i)
   {
      if (((i/26) % 2) == 0)
         message[i] = static_cast<char>('a' + (i % 26));
      else
         message[i] = static_cast<char>('A' + (i % 26));
   }

   // Connect and send a large message.
   TcpSocket clientSocket("localhost", 10495);
   ASSERT_TRUE(clientSocket.connect());
   ASSERT_TRUE(clientSocket.send(message.c_str(), message.length()));

   ASSERT_EQ(message, future.get());
}

// NOLINTEND(cppcoreguidelines-avoid-non-const-global-variables)
