# steam-webapi-kt

Steam Web API wrapper in Kotlin and Ktor.

## Installation

Add the JitPack repository to your build file and add the dependency.

### Gradle (Kotlin)

```kotlin
repositories {
    maven(url="https://jitpack.io")
}

dependencies {
    implementation("com.github.j4ckofalltrades:steam-webapi-kt:1.0.0")
}
```

### Gradle (Groovy)

```groovy
repositories {
    maven { url "https://jitpack.io" }
}

dependencies {
    implementation "com.github.j4ckofalltrades:steam-webapi-kt:1.0.0"
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
        <groupId>com.github.User</groupId>
        <artifactId>Repo</artifactId>
        <version>Tag</version>
    </dependency>
</project>
```

## Usage

**This requires a Steam Web API Key, you can get one at https://steamcommunity.com/dev/apikey**

You can use either the provided `SteamWebApi` wrapper

```kotlin
import io.github.j4ckofalltrades.steam_webapi.SteamWebApi

val steamWebApi = SteamWebApi("web_api_key")
steamWebApi.userApi().getServerInfo()
```

or with a specific interface e.g. `ISteamUserWrapper`

```kotlin
import io.github.j4ckofalltrades.steam_webapi.util.ISteamUserWrapper

val userApi = new ISteamUserWrapper("web_api_key")
userApi.getPlayerSummaries(listOf("steam_ids"))
```

## Docs

For more detailed documentation see https://j4ckofalltrades.github.io/steam-webapi-kt.