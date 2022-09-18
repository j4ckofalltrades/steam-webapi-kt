# steam-webapi-kt

[![Maven Central](https://img.shields.io/maven-central/v/io.github.j4ckofalltrades/steam-webapi-kt.svg?label=Maven%20Central)](https://search.maven.org/artifact/io.github.j4ckofalltrades/steam-webapi-kt)
[![JitPack](https://jitpack.io/v/j4ckofalltrades/steam-webapi-kt.svg)](https://jitpack.io/#j4ckofalltrades/steam-webapi-kt)
[![Kotlin](https://img.shields.io/badge/kotlin-1.7.0-blueviolet)](https://kotlinlang.org)
[![Ktor](https://img.shields.io/badge/ktor-2.0.2-blue)](https://ktor.io)

[![KDoc](https://img.shields.io/badge/kdoc-1.2.1-green)](https://j4ckofalltrades.github.io/steam-webapi-kt)
[![javadoc](https://javadoc.io/badge2/io.github.j4ckofalltrades/steam-webapi-kt/javadoc.svg)](https://javadoc.io/doc/io.github.j4ckofalltrades/steam-webapi-kt)
[![codecov](https://codecov.io/gh/j4ckofalltrades/steam-webapi-kt/branch/main/graph/badge.svg?token=2IDBVWIE7T)](https://codecov.io/gh/j4ckofalltrades/steam-webapi-kt)

Steam WebAPI wrapper in Kotlin and Ktor.

## Installation

### Gradle Kotlin DSL

```kotlin
dependencies {
    implementation("com.github.j4ckofalltrades:steam-webapi-kt:1.2.1")
}
```

### Gradle Groovy DSL

```groovy
dependencies {
    implementation "com.github.j4ckofalltrades:steam-webapi-kt:1.2.1"
}
```

### Apache Maven

```xml
<dependency>
    <groupId>com.github.j4ckofalltrades</groupId>
    <artifactId>steam-webapi-kt</artifactId>
    <version>1.2.1</version>
</dependency>
```

### Installing from the JitPack repository

Add the JitPack repository to the list of repositories, and then add the package as a dependency.

Sample using Kotlin DSL:

```kotlin
repositories {
    maven(url="https://jitpack.io")
}

dependencies {
    implementation("com.github.j4ckofalltrades:steam-webapi-kt:1.2.1")
}
```

### Installing via GitHub Packages

1. Authenticate to GitHub Packages. For more information, see ["Authenticating to GitHub Packages"](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#authenticating-to-github-packages).

2. Add the package as a dependency.

```kotlin
dependencies {
    implementation("com.github.j4ckofalltrades:steam-webapi-kt:1.2.1")
}
```

3. (Optional) If using Gradle, add the `maven` plugin to your build file.

```kotlin
plugins {
    `maven`
}
```

## Usage

**This requires a Steam WebAPI Key, you can get one at https://steamcommunity.com/dev/apikey**

You can use either the provided `SteamWebApi` wrapper

```kotlin
import io.github.j4ckofalltrades.steam_webapi.core.SteamWebApi

val steamWebApi = SteamWebApi("web_api_key")
steamWebApi.userApi().getPlayerSummaries(listOf("steam_ids"))
```

or with a specific interface e.g. `ISteamUserWrapper`

```kotlin
import io.github.j4ckofalltrades.steam_webapi.wrapper.ISteamUserWrapper

val steamUserApi = new ISteamUserWrapper("web_api_key")
steamUserApi.getPlayerSummaries(listOf("steam_ids"))
```

## Docs

For more detailed documentation see:

- [KDoc](https://j4ckofalltrades.github.io/steam-webapi-kt)

- [Javadoc](https://javadoc.io/doc/io.github.j4ckofalltrades/steam-webapi-kt/latest)

## Stats

![Alt](https://repobeats.axiom.co/api/embed/0de183cc9f2d5d764a1ba379aae44cbd811bee73.svg "Repobeats analytics image")
