/*
 *  Copyright (C) 2025 Pitch Technologies AB
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

plugins {
    `java-library`
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(libs.protobuf.java)
    testImplementation(libs.junit)
    testImplementation(project(":api:HLA1516-2010"))
    testImplementation(project(":api:HLA1516-2025"))
    testImplementation(project(":client"))
    testImplementation(project(":client_evolved"))
}

sourceSets {
    test {
        java {
            setSrcDirs(listOf("src"))
        }
    }
}

java {
    withSourcesJar()
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

tasks.named<Test>("test") {
    testLogging {
        events("passed")
    }
}