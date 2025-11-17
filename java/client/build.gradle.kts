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
    api(project(":java:api"))
    api(project(":java:protobuf"))
    api(project(":java:session"))
    api(project(":java:client_common"))
}

sourceSets {
    main {
        java {
            setSrcDirs(listOf("src","gensrc"))
        }
    }
}

tasks.jar {
    from(file("resources/META-INF/services")) {
        into("META-INF/services")
    }
}

java {
    withSourcesJar()
}

mavenPublishing {
    coordinates("se.pitch.oss.fedpro", "client", libs.versions.fed.pro.client.get())

    pom {
        name.set("Federate Protocol Java Client Protobuf")
        description.set("Provides the Federate Protocol for HLA 4")
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
