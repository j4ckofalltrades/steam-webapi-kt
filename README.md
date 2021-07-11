# steam-webapi-kt

[![](https://jitpack.io/v/j4ckofalltrades/steam-webapi-kt.svg)](https://jitpack.io/#j4ckofalltrades/steam-webapi-kt)
[![](https://img.shields.io/badge/kotlin-1.5.20-blueviolet)](https://kotlinlang.org)
[![](https://img.shields.io/badge/ktor-1.6.1-blue)](https://ktor.io)

Steam WebAPI wrapper in Kotlin and Ktor.

## Installation

Add the JitPack repository, and the dependency to your build file.

### Gradle (Kotlin)

```kotlin
repositories {
    maven(url="https://jitpack.io")
}

dependencies {
    implementation("com.github.j4ckofalltrades:steam-webapi-kt:0.2.0")
}
```

### Gradle (Groovy)

```groovy
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation "com.github.j4ckofalltrades:steam-webapi-kt:0.2.0"
}
```

### Maven

```xml
<project>
    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>

    <dependency>
        <groupId>com.github.j4ckofalltrades</groupId>
        <artifactId>steam-webapi-kt</artifactId>
        <version>0.2.0</version>
    </dependency>
</project>
```

## Usage

**This requires a Steam Web API Key, you can get one at https://steamcommunity.com/dev/apikey**

You can use either the provided `SteamWebApi` wrapper

```kotlin
import io.github.j4ckofalltrades.steam_webapi.SteamWebApi

val steamWebApi = SteamWebApi("web_api_key")
steamWebApi.userApi().getPlayerSummaries(listOf("steam_ids"))
```

or with a specific interface e.g. `ISteamUserWrapper`

```kotlin
import io.github.j4ckofalltrades.steam_webapi.util.ISteamUserWrapper

val steamUserApi = new ISteamUserWrapper("web_api_key")
steamUserApi.getPlayerSummaries(listOf("steam_ids"))
```

## Docs

For more detailed documentation see https://j4ckofalltrades.github.io/steam-webapi-kt.