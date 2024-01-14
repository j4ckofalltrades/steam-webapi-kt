package io.github.j4ckofalltrades.steam_webapi.wrapper

import io.github.j4ckofalltrades.steam_webapi.core.WebApiClient.defaultConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.headersOf
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ISteamAppsWrapperTest {

    private val json = Json { ignoreUnknownKeys = true }

    private lateinit var appsApiClient: ISteamAppsWrapper

    @BeforeTest
    fun setup() {
        val webApiClientMock = HttpClient(MockEngine) {
            defaultConfig()
            engine {
                addHandler {
                    val responseHeaders =
                        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                    when (it.url.encodedPath) {
                        GET_APP_LIST -> {
                            respond(APP_LIST_JSON, headers = responseHeaders)
                        }

                        UP_TO_DATE_CHECK -> {
                            respond(UP_TO_DATE_CHECK_JSON, headers = responseHeaders)
                        }

                        else -> error("Unhandled ${it.url.encodedPath}")
                    }
                }
            }
        }

        appsApiClient = ISteamAppsWrapper(webApiClient = webApiClientMock)
    }

    @Test
    fun getAppList() = runBlocking {
        assertEquals(
            json.decodeFromString(APP_LIST_JSON),
            appsApiClient.getAppList()
        )
    }

    @Test
    fun upToDateCheck() = runBlocking {
        assertEquals(
            json.decodeFromString(UP_TO_DATE_CHECK_JSON),
            appsApiClient.upToDateCheck(appId = 570, version = "7.29d")
        )
    }
}

private const val APP_LIST_JSON = """
    {
        "applist": {
            "apps": [{
                "appid": 570,
                "name": "DotA 2"
            }]
        }
    }
"""

private const val UP_TO_DATE_CHECK_JSON = """
    {
        "response": {
            "success": true,
            "up_to_date": true,
            "version_is_listable": true
        }
    }
"""
