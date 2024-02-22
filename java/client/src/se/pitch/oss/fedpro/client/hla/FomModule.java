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

public class FomModule {
   public enum Type {
      FILE,
      COMPRESSED,
      URL
   }

   private final Type _type;
   private final String _fileName;
   private final byte[] _fileContent;
   private final byte[] _compressedModule;
   private final String _url;

   public FomModule(
         String name,
         byte[] content)
   {
      _type = Type.FILE;
      _fileName = name;
      _fileContent = content;
      _compressedModule = null;
      _url = null;
   }

   public FomModule(byte[] compressedModule)
   {
      _type = Type.COMPRESSED;
      _compressedModule = compressedModule;
      _fileName = null;
      _fileContent = null;
      _url = null;
   }

   public FomModule(String url)
   {
      _type = Type.URL;
      _url = url;
      _fileName = null;
      _fileContent = null;
      _compressedModule = null;
   }

   public Type getType()
   {
      return _type;
   }

   public String getFileName()
   {
      return _fileName;
   }

   public byte[] getFileContent()
   {
      return _fileContent;
   }

   public byte[] getCompressedModule()
   {
      return _compressedModule;
   }

   public String getUrl()
   {
      return _url;
   }
}
