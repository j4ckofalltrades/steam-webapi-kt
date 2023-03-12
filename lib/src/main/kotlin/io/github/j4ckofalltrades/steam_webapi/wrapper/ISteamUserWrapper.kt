package io.github.j4ckofalltrades.steam_webapi.wrapper

import io.github.j4ckofalltrades.steam_webapi.core.SteamId
import io.github.j4ckofalltrades.steam_webapi.core.WebApiClient
import io.github.j4ckofalltrades.steam_webapi.core.WebApiKey
import io.github.j4ckofalltrades.steam_webapi.types.FriendListWrapper
import io.github.j4ckofalltrades.steam_webapi.types.FriendRelationship
import io.github.j4ckofalltrades.steam_webapi.types.PlayerBanList
import io.github.j4ckofalltrades.steam_webapi.types.PlayerSummaryListWrapper
import io.github.j4ckofalltrades.steam_webapi.types.UserGroupListWrapper
import io.github.j4ckofalltrades.steam_webapi.types.VanityURLResponseWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal const val GET_PLAYER_SUMMARIES = "/ISteamUser/GetPlayerSummaries/v2"
internal const val GET_FRIEND_LIST = "/ISteamUser/GetFriendList/v1"
internal const val GET_PLAYER_BANS = "/ISteamUser/GetPlayerBans/v1"
internal const val GET_USER_GROUP_LIST = "/ISteamUser/GetUserGroupList/v1"
internal const val RESOLVE_VANITY_URL = "/ISteamUser/ResolveVanityURL/v1"

/**
 * Wrapper for the [ISteamUser](https://partner.steamgames.com/doc/webapi/ISteamUser) endpoint which contains methods
 * relating to the Steam users.
 */
class ISteamUserWrapper(
    val webApiKey: WebApiKey,
    val webApiClient: HttpClient = WebApiClient.default(),
) {

    /**
     * User friend list.
     *
     * @param steamId[SteamId] The 64-bit ID of the user to retrieve a list for.
     * @param friendRelationship[FriendRelationship] Filter by a given role. Possible options are *all* (All roles),
     *        *friend*.
     * */
    suspend fun getFriendList(steamId: SteamId, friendRelationship: FriendRelationship): FriendListWrapper =
        webApiClient.get(GET_FRIEND_LIST) {
            parameter("key", webApiKey)
            parameter("steamid", steamId)
            parameter("relationship", friendRelationship.value)
        }
            .body()

    /**
     * Player ban/probation status.
     *
     * @param steamIds[List] Comma-delimited list of steam IDs.
     */
    suspend fun getPlayerBans(steamIds: List<SteamId>): PlayerBanList =
        webApiClient.get(GET_PLAYER_BANS) {
            parameter("key", webApiKey)
            parameter("steamids", Json.encodeToString(steamIds))
        }
            .body()

    /**
     * User profile data.
     *
     * @param steamIds[List] Comma-delimited list of steam IDs.
     */
    suspend fun getPlayerSummaries(steamIds: List<SteamId>): PlayerSummaryListWrapper =
        webApiClient.get(GET_PLAYER_SUMMARIES) {
            parameter("key", webApiKey)
            parameter("steamids", Json.encodeToString(steamIds))
        }
            .body()

    /**
     * Lists Group ID(s) linked with 64-bit ID.
     *
     * @param steamId[SteamId] The 64-bit ID of the user.
     */
    suspend fun getUserGroupList(steamId: SteamId): UserGroupListWrapper =
        webApiClient.get(GET_USER_GROUP_LIST) {
            parameter("key", webApiKey)
            parameter("steamid", steamId)
        }
            .body()

    /**
     * Resolve vanity URL parts to a 64-bit ID.
     *
     * @param vanityUrl[String] The user's vanity URL that you would like to retrieve a steam ID for,
     *        e.g. http://steamcommunity.com/id/gabelogannewell would use "gabelogannewell".
     */
    suspend fun resolveVanityURL(vanityUrl: String): VanityURLResponseWrapper =
        webApiClient.get(RESOLVE_VANITY_URL) {
            parameter("key", webApiKey)
            parameter("vanityurl", vanityUrl)
        }
            .body()
}
