package io.github.j4ckofalltrades.steam_webapi.wrapper

import io.github.j4ckofalltrades.steam_webapi.core.WebApiClient.defaultConfig
import io.github.j4ckofalltrades.steam_webapi.types.NewsForAppParams
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.headersOf
import io.ktor.util.date.getTimeMillis
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ISteamNewsWrapperTest {

    private val json = Json { ignoreUnknownKeys = true }

    private lateinit var newsApi: ISteamNewsWrapper

    @BeforeTest
    fun setup() {
        val webApiClientMock = HttpClient(MockEngine) {
            defaultConfig()
            engine {
                addHandler {
                    val responseHeaders =
                        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                    when (it.url.encodedPath) {
                        GET_NEWS_FOR_APP -> {
                            respond(NEWS_FOR_APP_JSON, headers = responseHeaders)
                        }
                        else -> error("Unhandled ${it.url.encodedPath}")
                    }
                }
            }
        }

        newsApi = ISteamNewsWrapper(webApiClient = webApiClientMock)
    }

    @Test
    fun getNewsForApp() = runBlocking {
        assertEquals(
            json.decodeFromString(NEWS_FOR_APP_JSON),
            newsApi.getNewsForApp(appId = 570)
        )
    }

    @Test
    fun getNewsForAppWithParams() = runBlocking {
        assertEquals(
            json.decodeFromString(NEWS_FOR_APP_JSON),
            newsApi.getNewsForApp(
                appId = 570,
                params = NewsForAppParams(
                    maxLength = 5,
                    endDate = getTimeMillis().toInt(),
                    count = 10,
                    feeds = "default"
                )
            )
        )
    }
}

private const val NEWS_FOR_APP_JSON = """
    {
        "appnews": {
            "appid": 570,
            "newsitems": [{
                "gid": "4032392655093925419",
                "title": "Dota 2 update for 2 July 2021",
                "url": "https://steamstore-a.akamaihd.net/news/externalpost/SteamDB/4032392655093925419",
                "is_external_url": true,
                "author": "SteamDB",
                "contents": "news",
                "feedlabel": "SteamDB",
                "date": 1625254529,
                "feedname": "SteamDB",
                "feed_type": 0,
                "appid": 570
            }]
        }
    }
"""
