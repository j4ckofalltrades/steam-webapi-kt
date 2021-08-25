package io.github.j4ckofalltrades.steam_webapi.wrapper

import io.github.j4ckofalltrades.steam_webapi.core.WebApiClient.defaultConfig
import io.github.j4ckofalltrades.steam_webapi.types.GetOwnedGamesParams
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

@kotlinx.serialization.ExperimentalSerializationApi
internal class IPlayerServiceWrapperTest {

    private val steamId = "123"
    private val json = Json { ignoreUnknownKeys = true }

    private lateinit var playerServiceClient: IPlayerServiceWrapper

    @BeforeTest
    private fun setup() {
        val webApiClientMock = HttpClient(MockEngine) {
            defaultConfig()
            engine {
                addHandler {
                    val responseHeaders =
                        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                    when (it.url.encodedPath) {
                        GET_RECENTLY_PLAYED_GAMES -> {
                            respond(RECENTLY_PLAYED_GAMES_JSON, headers = responseHeaders)
                        }
                        GET_OWNED_GAMES -> {
                            respond(OWNED_GAMES_JSON, headers = responseHeaders)
                        }
                        GET_STEAM_LEVEL -> {
                            respond(PLAYER_LEVEL_JSON, headers = responseHeaders)
                        }
                        GET_BADGES -> {
                            respond(PLAYER_BADGES_JSON, headers = responseHeaders)
                        }
                        GET_COMMUNITY_BADGE_PROGRESS -> {
                            respond(PLAYER_BADGE_PROGRESS_JSON, headers = responseHeaders)
                        }
                        IS_PLAYING_SHARED_GAME -> {
                            respond(PLAYING_SHARED_GAME_JSON, headers = responseHeaders)
                        }
                        else -> error("Unhandled ${it.url.encodedPath}")
                    }
                }
            }
        }

        playerServiceClient = IPlayerServiceWrapper(webApiKey = "123", webApiClient = webApiClientMock)
    }

    @Test
    fun getRecentlyPlayedGames() = runBlocking {
        assertEquals(
            json.decodeFromString(RECENTLY_PLAYED_GAMES_JSON),
            playerServiceClient.getRecentlyPlayedGames(steamId)
        )

        assertEquals(
            json.decodeFromString(RECENTLY_PLAYED_GAMES_JSON),
            playerServiceClient.getRecentlyPlayedGames(steamId = steamId, count = 10)
        )
    }

    @Test
    fun getOwnedGames() = runBlocking {
        assertEquals(
            json.decodeFromString(OWNED_GAMES_JSON),
            playerServiceClient.getOwnedGames(steamId)
        )

        assertEquals(
            json.decodeFromString(OWNED_GAMES_JSON),
            playerServiceClient.getOwnedGames(
                steamId = steamId,
                request = GetOwnedGamesParams(
                    includeAppInfo = true,
                    includePlayedFreeGames = true,
                    appIdsFilter = listOf(440, 570)
                )
            )
        )

        assertEquals(
            json.decodeFromString(OWNED_GAMES_JSON),
            playerServiceClient.getOwnedGames(
                steamId = steamId,
                request = GetOwnedGamesParams(
                    includeAppInfo = false,
                    includePlayedFreeGames = false,
                )
            )
        )

        assertEquals(
            json.decodeFromString(OWNED_GAMES_JSON),
            playerServiceClient.getOwnedGames(
                steamId = steamId,
                request = GetOwnedGamesParams(
                    appIdsFilter = listOf()
                )
            )
        )
    }

    @Test
    fun getSteamLevel() = runBlocking {
        assertEquals(
            json.decodeFromString(PLAYER_LEVEL_JSON),
            playerServiceClient.getSteamLevel(steamId)
        )
    }

    @Test
    fun getBadges() = runBlocking {
        assertEquals(
            json.decodeFromString(PLAYER_BADGES_JSON),
            playerServiceClient.getBadges(steamId)
        )
    }

    @Test
    fun getCommunityBadgeProgress() = runBlocking {
        assertEquals(
            json.decodeFromString(PLAYER_BADGE_PROGRESS_JSON),
            playerServiceClient.getCommunityBadgeProgress(steamId)
        )

        assertEquals(
            json.decodeFromString(PLAYER_BADGE_PROGRESS_JSON),
            playerServiceClient.getCommunityBadgeProgress(steamId = steamId, badge = 123)
        )
    }

    @Test
    fun isPlayingSharedGame() = runBlocking {
        assertEquals(
            json.decodeFromString(PLAYING_SHARED_GAME_JSON),
            playerServiceClient.isPlayingSharedGame(steamId = steamId, appIdPlaying = 123)
        )
    }
}

private const val OWNED_GAMES_JSON = """
{
    "response": {
        "game_count": 1,
        "games": [{
            "appid": 1,
            "playtime_forever": 10,
            "playtime_windows_forever": 10,
            "playtime_mac_forever": 0,
            "playtime_linux_forever": 0
        }]
    }
}
"""

private const val PLAYER_BADGE_PROGRESS_JSON = """
{
    "response": {
        "quests": [{
            "questid": 115,
            "completed": true
        },
        {
            "questid": 128,
            "completed": true
        }]
    }
}
"""

private const val PLAYER_BADGES_JSON = """
{
    "response": {
        "badges": [{
            "badgeid": 13,
            "level": 127,
            "completion_time": 1622373519,
            "xp": 356,
            "scarcity": 7098987
        }],
        "player_xp": 706,
        "player_level": 7,
        "player_xp_needed_to_level_up": 94,
        "player_xp_needed_current_level": 700
    }
}
"""

private const val PLAYER_LEVEL_JSON = """
{
    "response": {
        "player_level": 7
    }
}
"""

private const val PLAYING_SHARED_GAME_JSON = """
{
    "response": {
        "lender_steamid": "0"
    }
}
"""

private const val RECENTLY_PLAYED_GAMES_JSON = """
{
    "response": {
        "total_count": 1,
        "games": [{
            "appid": 570,
            "name": "Dota 2",
            "playtime_2weeks": 224,
            "playtime_forever": 78765,
            "img_icon_url": "0bbb630d63262dd66d2fdd0f7d37e8661a410075",
            "img_logo_url": "d4f836839254be08d8e9dd333ecc9a01782c26d2",
            "playtime_windows_forever": 37652,
            "playtime_mac_forever": 0,
            "playtime_linux_forever": 6315
        }]
    }
}
"""
