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

sourceSets {
    main {
        java {
            setSrcDirs(listOf("../../client_evolved/src/hla"))
        }
    }
}
java {
    withSourcesJar()
    toolchain {
        languageVersion = JavaLanguageVersion.of(8)
    }
}

mavenPublishing {
    coordinates("se.pitch.oss.fedpro", "hlaevolved-api", libs.versions.fed.pro.client.get())

    publishToMavenCentral()
    signAllPublications()

    pom {
        name.set("HLA Evolved API")
        description.set("IEEE 1516-2010 High Level Architecture (HLA) API")
        url.set("https://github.com/Pitch-Technologies/FedProClient")

        licenses {
            license {
                name.set("IEEE 1516.1-2010 Permission Notice")
                url.set("https://github.com/Pitch-Technologies/FedProClient/blob/main/doc/licenses/IEEE_1516-2010.txt")
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

