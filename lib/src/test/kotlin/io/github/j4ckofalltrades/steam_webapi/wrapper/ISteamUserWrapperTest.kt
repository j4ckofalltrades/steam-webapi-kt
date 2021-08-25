package io.github.j4ckofalltrades.steam_webapi.wrapper

import io.github.j4ckofalltrades.steam_webapi.core.WebApiClient.defaultConfig
import io.github.j4ckofalltrades.steam_webapi.types.FriendRelationship
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
internal class ISteamUserWrapperTest {

    private val steamId = "123"
    private val json = Json { ignoreUnknownKeys = true }

    private lateinit var userApiClient: ISteamUserWrapper

    @BeforeTest
    private fun setup() {
        val webApiClientMock = HttpClient(MockEngine) {
            defaultConfig()
            engine {
                addHandler {
                    val responseHeaders =
                        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                    when (it.url.encodedPath) {
                        GET_FRIEND_LIST -> {
                            respond(FRIEND_LIST_JSON, headers = responseHeaders)
                        }
                        GET_PLAYER_BANS -> {
                            respond(PLAYER_BANS_JSON, headers = responseHeaders)
                        }
                        GET_PLAYER_SUMMARIES -> {
                            respond(PLAYER_SUMMARIES_JSON, headers = responseHeaders)
                        }
                        GET_USER_GROUP_LIST -> {
                            respond(USER_GROUP_LIST_JSON, headers = responseHeaders)
                        }
                        RESOLVE_VANITY_URL -> {
                            respond(VANITY_URL_JSON, headers = responseHeaders)
                        }
                        else -> error("Unhandled ${it.url.encodedPath}")
                    }
                }
            }
        }

        userApiClient = ISteamUserWrapper(webApiKey = "123", webApiClient = webApiClientMock)
    }

    @Test
    fun getFriendList(): Unit = runBlocking {
        assertEquals(
            json.decodeFromString(FRIEND_LIST_JSON),
            userApiClient.getFriendList(steamId = steamId, friendRelationship = FriendRelationship.FRIEND)
        )

        assertEquals(
            json.decodeFromString(FRIEND_LIST_JSON),
            userApiClient.getFriendList(steamId = steamId, friendRelationship = FriendRelationship.ALL)
        )
    }

    @Test
    fun getPlayerBans(): Unit = runBlocking {
        assertEquals(
            json.decodeFromString(PLAYER_BANS_JSON),
            userApiClient.getPlayerBans(listOf(steamId))
        )
    }

    @Test
    fun getPlayerSummaries(): Unit = runBlocking {
        assertEquals(
            json.decodeFromString(PLAYER_SUMMARIES_JSON),
            userApiClient.getPlayerSummaries(listOf(steamId))
        )
    }

    @Test
    fun getUserGroupList(): Unit = runBlocking {
        assertEquals(
            json.decodeFromString(USER_GROUP_LIST_JSON),
            userApiClient.getUserGroupList(steamId)
        )
    }

    @Test
    fun resolveVanityURL(): Unit = runBlocking {
        assertEquals(
            json.decodeFromString(VANITY_URL_JSON),
            userApiClient.resolveVanityURL("https://steamcommunity.com/id/gabelogannewell")
        )
    }
}

private const val FRIEND_LIST_JSON = """
   {
        "friendslist": {
            "friends": [{
                "steamid": "76561197960265740",
                "relationship": "friend",
                "friend_since": 0
            },
            {
                "steamid": "76561197960265744",
                "relationship": "friend",
                "friend_since": 1585508613
            }]
        }
   } 
"""

private const val PLAYER_BANS_JSON = """
   {
       "players": [{
           "SteamId": "76561197960435530",
           "CommunityBanned": false,
           "VACBanned": false,
           "NumberOfVACBans": 0,
           "DaysSinceLastBan": 0,
           "NumberOfGameBans": 0,
           "EconomyBan": "none"
       }]
   }
"""

private const val PLAYER_SUMMARIES_JSON = """
    {
        "response": {
            "players": [{
                "steamid": "76561197960435530",
                "communityvisibilitystate": 3,
                "profilestate": 1,
                "personaname": "Robin",
                "profileurl": "https://steamcommunity.com/id/robinwalker/",
                "avatar": "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/f1/f1dd60a188883caf82d0cbfccfe6aba0af1732d4.jpg",
                "avatarmedium": "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/f1/f1dd60a188883caf82d0cbfccfe6aba0af1732d4_medium.jpg",
                "avatarfull": "https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/f1/f1dd60a188883caf82d0cbfccfe6aba0af1732d4_full.jpg",
                "avatarhash": "f1dd60a188883caf82d0cbfccfe6aba0af1732d4",
                "personastate": 0,
                "realname": "Robin Walker",
                "primaryclanid": "103582791429521412",
                "timecreated": 1063407589,
                "personastateflags": 0,
                "loccountrycode": "US",
                "locstatecode": "WA",
                "loccityid": 3961
            }]
        }
    } 
"""

private const val USER_GROUP_LIST_JSON = """
    {
        "response": {
            "success": true,
            "groups": [{
                "gid": "4"
            }]
        }
    }
"""

private const val VANITY_URL_JSON = """
    {
        "response": {
            "steamid": "76561197960435530",
            "success": 1
    }
}
"""
