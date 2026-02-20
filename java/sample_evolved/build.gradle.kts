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
    mavenLocal()
}

dependencies {
    // Use the following to fetch the client from the local project build
    implementation(project(":client_evolved"))
    api(project(":api:HLA1516-2010"))
    // Use the following to fetch the client from maven
    //implementation("se.pitch.oss.fedpro:client-api-evolved:2.1.0-SNAPSHOT")
    //api("se.pitch.oss.fedpro:hlaevolved-api:2.1.0-SNAPSHOT")
}

sourceSets {
    main {
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

tasks.register<JavaExec>("runSampleEvolved") {
    classpath = files(tasks.jar) + configurations.runtimeClasspath.get()
    mainClass = "se.pitch.oss.chat1516e.Chat"
    standardInput = System.`in`
    workingDir = File(".")
}
