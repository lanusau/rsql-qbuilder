import org.gradle.api.JavaVersion.VERSION_11

plugins {
    java
}

group = "com.conversantmedia.mpub"

java {
    sourceCompatibility = VERSION_11
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

// Get more details on unchecked warnings
tasks.withType<JavaCompile> {
    options.compilerArgs.addAll(arrayOf("-parameters", "-Xlint:unchecked", "-Xlint:deprecation"))
}
