import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

plugins {
    kotlin("jvm") version "1.5.20"
    kotlin("plugin.serialization") version "1.5.20"
    id("org.jlleitschuh.gradle.ktlint") version "10.1.0"
    id("org.jetbrains.dokka") version "1.5.0"
    `java-library`
    `maven-publish`
    signing
    jacoco
}

group = "io.github.j4ckofalltrades"
version = "1.0.0"

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

tasks.register("installGitHook", Copy::class) {
    from("../hooks/pre-commit")
    from("../hooks/pre-push")
    into("../.git/hooks")
}

tasks.compileKotlin {
    dependsOn("installGitHook")
}

tasks.jar {
    manifest {
        attributes(
            mapOf(
                "Implementation-Title" to rootProject.name,
                "Implementation-Version" to project.version
            )
        )
    }
    archiveBaseName.set("steam-webapi-kt")
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
                            "/tree/main/lib/src/main/kotlin"
                    )
                )
                remoteLineSuffix.set("#L")
            }
        }
    }
}

tasks {
    val sourcesJar by registering(Jar::class) {
        dependsOn(JavaPlugin.CLASSES_TASK_NAME)
        archiveClassifier.set("sources")
        archiveBaseName.set("steam-webapi-kt")
        from(sourceSets["main"].allSource)
    }

    val javadocJar by registering(Jar::class) {
        dependsOn("dokkaJavadoc")
        archiveClassifier.set("javadoc")
        archiveBaseName.set("steam-webapi-kt")
        from("docs")
    }

    artifacts {
        archives(sourcesJar)
        archives(javadocJar)
        archives(jar)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            groupId = project.group.toString()
            artifactId = rootProject.name
            version = project.version.toString()

            from(components["java"])
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])

            pom {
                name.set("steam-webapi-kt")
                description.set("Steam WebAPI wrapper in Kotlin and Ktor")
                url.set("https://github.com/j4ckofalltrades/steam-webapi-kt")
                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("j4ckofalltrades")
                        name.set("Jordan Duabe")
                        email.set("me@jduabe.dev")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/j4ckofalltrades/steam-webapi-kt.git")
                    developerConnection.set("scm:git:ssh://github.com/j4ckofalltrades/steam-webapi-kt.git")
                    url.set("https://github.com/j4ckofalltrades/steam-webapi-kt")
                }
            }
        }
    }
    repositories {
        maven {
            name = "OSSRH"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/j4ckofalltrades/steam-webapi-kt")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

signing {
    setRequired({
        gradle.taskGraph.hasTask("publishMavenJavaToOSSRHRepository")
    })

    val signingKey = System.getenv("SIGNING_KEY")
    val signingPassword = System.getenv("SIGNING_PASSWORD")
    useInMemoryPgpKeys(signingKey, signingPassword)
    sign(publishing.publications["mavenJava"])
}

jacoco {
    toolVersion = "0.8.7"
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.outputLocation.set(layout.buildDirectory.dir("jacocoHtml"))
    }
    classDirectories.setFrom(
        sourceSets.main.get().output.asFileTree.matching {
            exclude("**/types/*", "**/core/*")
        }
    )
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
}
