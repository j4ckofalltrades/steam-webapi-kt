import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

plugins {
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("org.jetbrains.dokka") version "1.6.10"
    `java-library`
    `maven-publish`
    signing
    jacoco
}

group = "io.github.j4ckofalltrades"
version = "1.0.2"

var kotlinVersion = "1.6.10"
var ktorVersion = "1.6.5"

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

// code coverage
jacoco {
    toolVersion = "0.8.7"
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
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

// git hooks
tasks.register("installGitHook", Copy::class) {
    from("../hooks/pre-commit")
    from("../hooks/pre-push")
    into("../.git/hooks")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
    dependsOn("installGitHook")
    kotlinOptions {
        jvmTarget = "17"
    }
}

// packaging
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

// docs
tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets {
        named("main") {
            outputDirectory.set(file("docs"))
            moduleName.set("steam_webapi")
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
