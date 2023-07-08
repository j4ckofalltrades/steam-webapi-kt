package io.github.j4ckofalltrades.steam_webapi.wrapper

import io.github.j4ckofalltrades.steam_webapi.core.WebApiClient.defaultConfig
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

internal class ISteamUserStatsWrapperTest {

    private val steamId = "123"
    private val appId = 789L
    private val lang = "en"
    private val json = Json { ignoreUnknownKeys = true }

    private lateinit var userStatsApiClient: ISteamUserStatsWrapper

    @BeforeTest
    fun setup() {
        val webApiClientMock = HttpClient(MockEngine) {
            defaultConfig()
            engine {
                addHandler {
                    val responseHeaders =
                        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                    when (it.url.encodedPath) {
                        GET_GLOBAL_ACHIEVEMENT_PERCENTAGES_FOR_APP -> {
                            respond(GLOBAL_ACHIEVEMENTS_JSON, headers = responseHeaders)
                        }

                        GET_GLOBAL_STATS_FOR_GAME -> {
                            respond(GLOBAL_STATS_FOR_GAME_JSON, headers = responseHeaders)
                        }

                        GET_NUMBER_OF_CURRENT_PLAYERS -> {
                            respond(CURRENT_PLAYERS_JSON, headers = responseHeaders)
                        }

                        GET_PLAYER_ACHIEVEMENTS -> {
                            respond(PLAYER_ACHIEVEMENTS_JSON, headers = responseHeaders)
                        }

                        GET_SCHEMA_FOR_GAME -> {
                            respond(GAME_SCHEMA_JSON, headers = responseHeaders)
                        }

                        GET_USER_STATS_FOR_GAME -> {
                            respond(GAME_USER_STATS_JSON, headers = responseHeaders)
                        }

                        else -> error("Unhandled ${it.url.encodedPath}")
                    }
                }
            }
        }

        userStatsApiClient = ISteamUserStatsWrapper(webApiKey = "123", webApiClient = webApiClientMock)
    }

    @Test
    fun getGlobalAchievementPercentagesForApp() = runBlocking {
        assertEquals(
            json.decodeFromString(GLOBAL_ACHIEVEMENTS_JSON),
            userStatsApiClient.getGlobalAchievementPercentagesForApp(appId)
        )
    }

    @Test
    fun getGlobalStatsForGame() = runBlocking {
        assertEquals(
            json.decodeFromString(GLOBAL_STATS_FOR_GAME_JSON),
            userStatsApiClient.getGlobalStatsForGame(
                appId = appId,
                count = 10,
                stats = listOf()
            )
        )

        assertEquals(
            json.decodeFromString(GLOBAL_STATS_FOR_GAME_JSON),
            userStatsApiClient.getGlobalStatsForGame(
                appId = appId,
                count = 10,
                stats = listOf("stat_name_0", "stat_name_1")
            )
        )
    }

    @Test
    fun getUserStatsForGame() = runBlocking {
        assertEquals(
            json.decodeFromString(GAME_USER_STATS_JSON),
            userStatsApiClient.getUserStatsForGame(steamId = steamId, appId = appId)
        )
    }

    @Test
    fun getNumberOfConcurrentPlayers() = runBlocking {
        assertEquals(
            json.decodeFromString(CURRENT_PLAYERS_JSON),
            userStatsApiClient.getNumberOfConcurrentPlayers(appId)
        )
    }

    @Test
    fun getPlayerAchievements() = runBlocking {
        assertEquals(
            json.decodeFromString(PLAYER_ACHIEVEMENTS_JSON),
            userStatsApiClient.getPlayerAchievements(steamId = steamId, appId = appId)
        )

        assertEquals(
            json.decodeFromString(PLAYER_ACHIEVEMENTS_JSON),
            userStatsApiClient.getPlayerAchievements(steamId = steamId, appId = appId, lang = lang)
        )
    }

    @Test
    fun getSchemaForGame() = runBlocking {
        assertEquals(
            json.decodeFromString(GAME_SCHEMA_JSON),
            userStatsApiClient.getSchemaForGame(appId)
        )

        assertEquals(
            json.decodeFromString(GAME_SCHEMA_JSON),
            userStatsApiClient.getSchemaForGame(appId = appId, lang = lang)
        )
    }
}

private const val CURRENT_PLAYERS_JSON = """
{
    "response": {
        "player_count": 573500,
        "result": 1
    }
}
"""

private const val GAME_SCHEMA_JSON = """
{
    "game": {
        "gameName": "[STAGING] DotA 2",
        "gameVersion": "11",
        "availableGameStats": {
            "stats": [
                {
                    "name": "DOTA_SHOW_FULL_UI",
                    "defaultvalue": 0,
                    "displayName": ""
                }
            ],
            "achievements": []
        }
    }
}
"""

private const val GAME_USER_STATS_JSON = """
{
    "playerstats": {
        "steamID": "123456789010",
        "gameName": "Fall Guys",
        "achievements": [
            {
                "name": "ACH_GRAB_PLAYER",
                "achieved": 1
            },
            {
                "name": "ACH_QUALIFY_1_ROUND",
                "achieved": 1
            }
        ]
    }
}
"""

private const val GLOBAL_ACHIEVEMENTS_JSON = """
{
    "achievementpercentages": {
        "achievements": [
            {
                "name": "CHARMED",
                "percent": 75
            },
            {
                "name": "FK_DEFEAT",
                "percent": 68.9000015258789063
            },
            {
                "name": "HORNET_1",
                "percent": 59.9000015258789063
            },
            {
                "name": "STAG_STATION_HALF",
                "percent": 45.7000007629394531
            },
            {
                "name": "PROTECTED",
                "percent": 44.2000007629394531
            }
        ]
    }
}
"""

private const val GLOBAL_STATS_FOR_GAME_JSON = """
{
    "response": {
        "result": 1,
        "globalstats": [
            {
                "stat_name_0": {
                    "total": 123
                },
                "stat_name_1": {
                    "total": 345
                }
            }
        ]
    }
}
"""

private const val PLAYER_ACHIEVEMENTS_JSON = """
{
    "playerstats": {
        "steamID": "12345678910",
        "gameName": "Hollow Knight",
        "achievements": [
            {
                "apiname": "CHARMED",
                "achieved": 0,
                "unlocktime": 0
            },
            {
                "apiname": "ENCHANTED",
                "achieved": 0,
                "unlocktime": 0
            },
            {
                "apiname": "BLESSED",
                "achieved": 0,
                "unlocktime": 0
            }
        ],
        "success": true
    }
}
"""
