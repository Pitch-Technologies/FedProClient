/*
 *  Copyright (C) 2022 Pitch Technologies AB
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package se.pitch.oss.fedpro.client.hla;

import org.junit.Test;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

public class TestClientConverter {

   @Test
   public void getIntValue_With_DifferentSizeArrays()
   {
      // The last four entries lose data as we're only looking at the last four bytes of provided data.
      int[] expected = {
            129,              // 0x81
            32769,            // 0x80 0x01
            8388609,          // 0x80 0x00 0x01
            -2147483647,      // 0x80 0x00 0x00 0x01
            1,                // 0x80 0x00 0x00 0x00 0x01
            1,                // 0x80 0x00 0x00 0x00 0x00 0x01
            1,                // 0x80 0x00 0x00 0x00 0x00 0x00 0x01
            1,                // 0x80 0x00 0x00 0x00 0x00 0x00 0x00 0x01
      };

      for (int byteArraySize = 1; byteArraySize < 9; byteArraySize++) {
         byte[] bytes = new byte[byteArraySize];    // initialized with zeroes for all bits
         bytes[0] = (byte) 0x80;                      // set first bit to 1
         bytes[byteArraySize - 1] |= (byte) 0x01;   // set last bit to 1

         ClientConverter.HandleImpl handleImpl = new ClientConverter.HandleImpl(bytes);
         assertEquals(expected[byteArraySize - 1], handleImpl.getIntValue());
      }
   }

   @Test(expected = RuntimeException.class)
   public void getIntValue_Throws_WhenByteArraySize_Is_Zero()
   {
      // Given
      ClientConverter.HandleImpl handleImpl = new ClientConverter.HandleImpl(new byte[0]);

      // Then throw
      handleImpl.getIntValue();
   }

   @Test
   public void getIntValue_encodes_all_bits()
   {
      // Given
      byte[] fourBytesArray = ones(4);
      byte[] eightBytesArray = ones(8);

      ClientConverter.HandleImpl fourBytes = new ClientConverter.HandleImpl(fourBytesArray);
      ClientConverter.HandleImpl eightBytes = new ClientConverter.HandleImpl(eightBytesArray);

      // Then
      assertEquals(fourBytes.getIntValue(), eightBytes.getIntValue());
   }

   private byte[] ones(int arraySize)
   {
      byte[] ones = new byte[arraySize];
      Arrays.fill(ones, (byte) 0xFF);
      return ones;
   }

}