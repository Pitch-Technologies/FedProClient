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

package se.pitch.oss.fedpro.client_evolved.hla;

public class FomModule {
   public enum Type {
      FILE,
      COMPRESSED,
      URL
   }

   private Type _type;
   private String _fileName;
   private byte[] _fileContent;
   private byte[] _compressedModule;
   private String _url;

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

   public void setFileNameAndContent(String name, byte[] content)
   {
      _type = Type.FILE;
      _fileName = name;
      _fileContent = content;
   }

   public byte[] getCompressedModule()
   {
      return _compressedModule;
   }

   public void setCompressedModule(byte[] compressedModule)
   {
      _type = Type.COMPRESSED;
      _compressedModule = compressedModule;
   }

   public String getUrl()
   {
      return _url;
   }

   public void setUrl(String url)
   {
      _type = Type.URL;
      _url = url;
   }
}
