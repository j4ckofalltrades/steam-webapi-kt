package io.github.j4ckofalltrades.steam_webapi.util

import io.github.j4ckofalltrades.steam_webapi.WebApiClient.defaultConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.headersOf
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ISteamWebApiUtilWrapperTest {

    private lateinit var steamWebApiUtil: ISteamWebApiUtilWrapper

    @BeforeTest
    private fun setup() {
        val webApiClientMock = HttpClient(MockEngine) {
            defaultConfig()
            engine {
                addHandler {
                    val responseHeaders =
                        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                    when (it.url.encodedPath) {
                        GET_SERVER_INFO -> {
                            respond(SERVER_INFO_JSON, headers = responseHeaders)
                        }
                        GET_SUPPORTED_API_LIST -> {
                            respond(SUPPORTED_API_JSON, headers = responseHeaders)
                        }
                        else -> error("Unhandled ${it.url.encodedPath}")
                    }
                }
            }
        }

        steamWebApiUtil = ISteamWebApiUtilWrapper(webApiClient = webApiClientMock)
    }

    @Test
    fun getServerInfo(): Unit = runBlocking {
        assertEquals(
            Json.decodeFromString(SERVER_INFO_JSON),
            steamWebApiUtil.getServerInfo()
        )
    }

    @Test
    fun getSupportedApiList(): Unit = runBlocking {
        assertEquals(
            Json.decodeFromString(SUPPORTED_API_JSON),
            steamWebApiUtil.getSupportedApiList()
        )
    }
}

private const val SERVER_INFO_JSON = """
    {
        "servertime": 1625396869,
        "servertimestring": "Sun Jul  4 04:07:49 2021"
    }
"""

private const val SUPPORTED_API_JSON = """
    {
        "apilist": {
            "interfaces": [{
                "name": "ISteamWebAPIUtil",
                "methods": [{
                    "name": "GetServerInfo",
                    "version": 1,
                    "httpmethod": "GET",
                    "parameters": []
                },
                {
                    "name": "GetSupportedAPIList",
                    "version": 1,
                    "httpmethod": "GET",
                    "parameters": [{
                        "name": "key",
                        "type": "string",
                        "optional": true,
                        "description": "access key"
                    }]
                }]
            }]
        }
    }
"""
