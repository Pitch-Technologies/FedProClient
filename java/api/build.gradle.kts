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
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

mavenPublishing {
    coordinates("se.pitch.oss.fedpro", "hla4-api", libs.versions.fed.pro.client.get())

    publishToMavenCentral()
    signAllPublications()

    pom {
        name.set("HLA 4 API")
        description.set("IEEE 1516-2025 High Level Architecture (HLA) API")
        url.set("https://github.com/Pitch-Technologies/FedProClient")

        licenses {
            license {
                name.set("IEEE 1516.1-2025 Permission Notice")
                url.set("https://github.com/Pitch-Technologies/FedProClient/blob/main/java/api/HLA1516-2025/src/hla/rti1516_2025/AdditionalSettingsResultCode.java")
                distribution.set("repo")
                comments.set("See notice in the source file header")
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

