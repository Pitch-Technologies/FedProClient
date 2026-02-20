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
    id("com.vanniktech.maven.publish") version "0.34.0"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.protobuf.java)
    implementation(libs.java.websocket)
    implementation(libs.jcip.annotations)
    implementation(libs.bundles.slf4j)
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

mavenPublishing {
    coordinates("se.pitch.oss.fedpro", "session", libs.versions.fed.pro.client.get())

    publishToMavenCentral()
    signAllPublications()

    pom {
        name.set("Federate Protocol Java Client Session")
        description.set("Provides the session layer for Federate Protocol")
        url.set("https://github.com/Pitch-Technologies/FedProClient")


        licenses {
            license {
                name.set("The Apache License, Version 2.0")
                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        scm {
            connection.set("scm:git:https://github.com/Pitch-Technologies/FedProClient.git")
            developerConnection.set("scm:git:ssh://git@github.com:Pitch-Technologies/FedProClient.git")
            url.set("https://github.com/Pitch-Technologies/FedProClient")
        }
        developers {
            developer {
                name.set("Pitch Technologies")
                organizationUrl.set("https://pitchtechnologies.com/")
            }
        }
    }
}
