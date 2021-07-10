import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

plugins {
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.serialization") version "1.5.20"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    id("org.jetbrains.dokka") version "1.5.0"
}

group = "io.github.j4ckofalltrades"
version = "0.1.0"

var kotlinVersion = "1.5.20"
var ktorVersion = "1.6.1"

repositories {
    mavenCentral()
    maven(url = "https://dl.bintray.com/kotlin/dokka")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-serialization:$ktorVersion")

    // testing
    testImplementation("io.ktor:ktor-client-mock:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlinVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets {
        named("main") {
            outputDirectory.set(file("docs"))
            moduleName.set("steam-webapi")
            includes.from("Module.md")
            displayName.set("JVM")
            platform.set(org.jetbrains.dokka.Platform.jvm)
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(
                    URL(
                        "https://github.com/j4ckofalltrades/steam-webapi-kt" +
                            "/tree/main/src/main/kotlin"
                    )
                )
                remoteLineSuffix.set("#L")
            }
        }
    }
}
