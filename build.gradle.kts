import org.gradle.api.JavaVersion.VERSION_11

plugins {
    java
    `maven-publish`
    id("net.researchgate.release") version "2.8.1"
}

group = "com.conversantmedia.mpub"

java {
    sourceCompatibility = VERSION_11
    withSourcesJar()
}

enum class RepositoryDefinitions(val url: String) {
    PUBLIC    ("https://vault.cnvrmedia.net/nexus/content/groups/public"),
    SNAPSHOTS ("https://vault.cnvrmedia.net/nexus/content/repositories/snapshots"),
    RELEASES  ("https://vault.cnvrmedia.net/nexus/content/repositories/releases")
}

repositories {
    maven { url = uri(RepositoryDefinitions.PUBLIC.url) }
    maven { url = uri(RepositoryDefinitions.RELEASES.url) }
    maven { url = uri(RepositoryDefinitions.SNAPSHOTS.url) }
}

enum class Versions(val text: String) {
    JETBRAINS_ANNOTATIONS ( "21.0.1"),
    LOMBOK                ( "1.18.20"),
    JUNIT                 ("5.8.2")
}

enum class Libs(val text: String) {
    JETBRAINS_ANNOTATIONS ( "org.jetbrains:annotations:${Versions.JETBRAINS_ANNOTATIONS.text}"),
    LOMBOK                ( "org.projectlombok:lombok:${Versions.LOMBOK.text}"),
    JUNIT                 ("org.junit.jupiter:junit-jupiter:${Versions.JUNIT.text}")
}

dependencies {

    compileOnly(Libs.LOMBOK.text)
    compileOnly(Libs.JETBRAINS_ANNOTATIONS.text)

    annotationProcessor(Libs.LOMBOK.text)

    testCompileOnly(Libs.LOMBOK.text)
    testCompileOnly(Libs.JETBRAINS_ANNOTATIONS.text)

    testAnnotationProcessor(Libs.LOMBOK.text)

    testImplementation(Libs.JUNIT.text)

}

tasks.test {
    useJUnitPlatform()
}

// Setup release plugin to also run publish task
release {
    buildTasks = listOf("build","publish")
}

// Setup Maven publication
publishing {
    publications {
        create<MavenPublication>("maven") {

            from(components["java"])

            pom {
                name.set("rSQL query builder")
                description.set("Query builder for rSQL")
                developers {
                    developer {
                        id.set("laianusa")
                        name.set("Laimonas Anusauskas")
                        email.set("laianusa@publicisgroupe.net")
                    }
                }
                scm {
                    url.set("https://git.cnvrmedia.net/projects/MPUB/repos/rsql-qbuilder/browse")
                }
            }
        }
    }

    repositories {
        maven {
            credentials {
                username = property("cnvrNexusUsername").toString()
                password = property("cnvrNexusPassword").toString()
            }

            // Where artifact will be published to depends on whether its a snapshot or not
            if (".*-SNAPSHOT".toRegex().matches(version.toString())) {
                url = uri(RepositoryDefinitions.SNAPSHOTS.url)
            } else {
                url = uri(RepositoryDefinitions.RELEASES.url)
            }
        }
    }
}

// Get more details on unchecked warnings
tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(arrayOf("-parameters", "-Xlint:unchecked", "-Xlint:deprecation"))
}
