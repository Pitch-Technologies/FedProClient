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
    implementation(libs.bundles.slf4j)
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("HLA1516-2025/src"))
        }
    }
}

java {
    withSourcesJar()
}

mavenPublishing {
    coordinates("se.pitch.oss.fedpro", "hla4-api", libs.versions.fed.pro.client.get())

    pom {
        name.set("HLA 4 API")
        description.set("IEEE 1516-2025 High Level Architecture (HLA) API")
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

