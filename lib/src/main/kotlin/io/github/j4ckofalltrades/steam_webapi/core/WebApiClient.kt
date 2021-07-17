package io.github.j4ckofalltrades.steam_webapi.core

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.takeFrom
import kotlinx.serialization.json.Json

/**
 * Provides the default HTTP client configured with the Steam WebAPI base url.
 */
object WebApiClient {

    private val webApiBaseUrl = Url("https://api.steampowered.com")
    private val defaultHttpClient = HttpClient(CIO) {
        defaultConfig()
        defaultRequest {
            url.takeFrom(
                URLBuilder().takeFrom(webApiBaseUrl)
                    .apply {
                        encodedPath += url.encodedPath
                    }
            )
        }
    }

    fun HttpClientConfig<*>.defaultConfig() {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    fun default(): HttpClient {
        return defaultHttpClient
    }
}
