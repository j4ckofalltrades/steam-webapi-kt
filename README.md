![steam-webapi-kt](https://res.cloudinary.com/j4ckofalltrades/image/upload/v1697278939/foss/gh-social-icons/steam-webapi-kt_g9la3j.png)

[![Maven Central](https://img.shields.io/maven-central/v/io.github.j4ckofalltrades/steam-webapi-kt.svg?label=Maven%20Central)](https://search.maven.org/artifact/io.github.j4ckofalltrades/steam-webapi-kt)
[![JitPack](https://jitpack.io/v/j4ckofalltrades/steam-webapi-kt.svg)](https://jitpack.io/#j4ckofalltrades/steam-webapi-kt)

[![KDoc](https://img.shields.io/badge/kdoc-1.2.2-green)](https://j4ckofalltrades.github.io/steam-webapi-kt)
[![javadoc](https://javadoc.io/badge2/io.github.j4ckofalltrades/steam-webapi-kt/javadoc.svg)](https://javadoc.io/doc/io.github.j4ckofalltrades/steam-webapi-kt)
[![codecov](https://codecov.io/gh/j4ckofalltrades/steam-webapi-kt/branch/main/graph/badge.svg?token=2IDBVWIE7T)](https://codecov.io/gh/j4ckofalltrades/steam-webapi-kt)

Steam WebAPI wrapper written in Kotlin and Ktor.

## Installation

### Gradle Kotlin DSL

```kotlin
dependencies {
    implementation("com.github.j4ckofalltrades:steam-webapi-kt:1.2.2")
}
```

### Gradle Groovy DSL

```groovy
dependencies {
    implementation "com.github.j4ckofalltrades:steam-webapi-kt:1.2.2"
}
```

### Apache Maven

```xml
<dependency>
    <groupId>com.github.j4ckofalltrades</groupId>
    <artifactId>steam-webapi-kt</artifactId>
    <version>1.2.2</version>
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
    implementation("com.github.j4ckofalltrades:steam-webapi-kt:1.2.2")
}
```

### Installing via GitHub Packages

1. Authenticate to GitHub Packages. For more information, see ["Authenticating to GitHub Packages"](https://docs.github.com/en/packages/working-with-a-github-packages-registry/working-with-the-gradle-registry#authenticating-to-github-packages).

2. Add the package as a dependency.

```kotlin
dependencies {
    implementation("com.github.j4ckofalltrades:steam-webapi-kt:1.2.2")
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

## Stats

![Alt](https://repobeats.axiom.co/api/embed/0de183cc9f2d5d764a1ba379aae44cbd811bee73.svg "Repobeats analytics image")
