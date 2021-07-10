package io.github.j4ckofalltrades.steam_webapi

import io.github.j4ckofalltrades.steam_webapi.WebApiClient.defaultHttpClient
import io.github.j4ckofalltrades.steam_webapi.WebApiClient.webApiBaseUrl
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.http.URLBuilder
import io.ktor.http.Url
import io.ktor.http.takeFrom

typealias WebApiKey = String

/**
 * Provides the default HTTP client used to call the Steam WebAPI.
 *
 * @property webApiBaseUrl[Url] Steam WebAPI base url.
 * @property defaultHttpClient[HttpClient] HTTP client configured with the CIO engine and JSON
 *           serialization/deserialization using kotlinx.serialization.
 */
object WebApiClient {

    private val webApiBaseUrl = Url("https://api.steampowered.com")
    private val defaultHttpClient = HttpClient(CIO) {
        defaultRequest {
            url.takeFrom(
                URLBuilder().takeFrom(webApiBaseUrl)
                    .apply {
                        encodedPath += url.encodedPath
                    }
            )
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
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
