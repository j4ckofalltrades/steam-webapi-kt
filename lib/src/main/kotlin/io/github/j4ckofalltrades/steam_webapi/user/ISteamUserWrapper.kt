package io.github.j4ckofalltrades.steam_webapi.user

import io.github.j4ckofalltrades.steam_webapi.SteamId
import io.github.j4ckofalltrades.steam_webapi.WebApiClient
import io.github.j4ckofalltrades.steam_webapi.WebApiKey
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
enum class FriendRelationship(val value: String) {
    @SerialName("all")
    ALL("all"),

    @SerialName("friend")
    FRIEND("friend"),
}

@Serializable
data class FriendListWrapper(val friendslist: FriendList)

@Serializable
data class FriendList(val friends: List<Friend>)

/**
 * @property steamid[SteamId] The user's 64 bit ID.
 * @property relationship[FriendRelationship] Role in relation to the given steamid.
 * @property friendSince[Long] A unix timestamp of when the friend was added to the list.
 *
 */
@Serializable
data class Friend(
    val steamid: SteamId,
    val relationship: FriendRelationship,
    @SerialName("friend_since")
    val friendSince: Long,
)

@Serializable
data class PlayerBanList(val players: List<PlayerBan>)

/**
 * @property SteamId[SteamId] A string containing the player's 64 bit ID.
 * @property CommunityBanned[Boolean] Indicates whether or not the player is banned from Community.
 * @property VACBanned[Boolean] Indicates whether or not the player has VAC bans on record.
 * @property NumberOfGameBans[Int] Number of bans in games.
 * @property NumberOfVACBans[Int] Number of VAC bans.
 * @property DaysSinceLastBan[Int] Days since last ban.
 * @property EconomyBan[String] String containing the player's ban status in the economy. If the player has no bans on
 *           record the string will be "none", if the player is on probation it will say "probation", and so forth.
 */
@Serializable
data class PlayerBan(
    val SteamId: SteamId,
    val CommunityBanned: Boolean,
    val VACBanned: Boolean,
    val NumberOfGameBans: Int,
    val NumberOfVACBans: Int,
    val DaysSinceLastBan: Int,
    val EconomyBan: String,
)

@Serializable
data class PlayerSummaryListWrapper(val response: PlayerSummaryList)

@Serializable
data class PlayerSummaryList(val players: List<PlayerSummary>)

/**
 * @property steamid[SteamId] The user's 64 bit ID.
 * @property communityvisibilitystate[Int] An integer that describes the access setting of the profile.
 *           1 - Private, 2 - Friends only, 3 - Friends of Friends, 4 - Users Only, 5 Public.
 * @property profilestate[Int] If set to 1 the user has configured the profile.
 * @property personaname[String] User's display name.
 * @property profileurl[String] The URL to the user's Steam Community profile.
 * @property avatar[String] A 32x32 image.
 * @property avatarmedium[String] A 64x64 image.
 * @property avatarfull[String] A 184x184 image.
 * @property avatarhash[String] Avatar identifier.
 * @property personastate[Int] The user's status.
 *           0 - Offline, 1 - Online, 2 - Busy, 3 - Away, 4 - Snooze, 5 - looking to trade, 6 - looking to play.
 * @property commentpermission[String] (Optional) If present the profile allows public comments.
 * @property realname[String] (Optional) The user's real name.
 * @property primaryclanid[String] (Optional) The 64 bit ID of the user's primary group.
 * @property timecreated[Long] (Optional) A unix timestamp of the date the profile was created.
 * @property loccountrycode[String] (Optional) ISO 3166 code of where the user is located.
 * @property locstatecode[String] (Optional) Variable length code representing the state the user is located in.
 * @property loccityid[Int] (Optional) An integer ID internal to Steam representing the user's city.
 * @property gameid[Long] (Optional) If the user is in game this will be set to it's app ID as a string.
 * @property gameextrainfo[String] (Optional) The title of the game.
 * @property gameserverip[String] (Optional) The server URL given as an IP address and port number separated by a colon,
 *           this will not be present or set to "0.0.0.0:0" if none is available.
 */
@Serializable
data class PlayerSummary(
    val steamid: SteamId,
    val personaname: String,
    val profileurl: String,
    val avatar: String,
    val avatarmedium: String,
    val avatarfull: String,
    val avatarhash: String,
    val personastate: Int,
    val communityvisibilitystate: Int,
    val profilestate: Int,
    val commentpermission: String? = null,
    val realname: String? = null,
    val primaryclanid: String? = null,
    val timecreated: Long? = null,
    val gameid: Long? = null,
    val gameserverip: String? = null,
    val gameextrainfo: String? = null,
    val loccityid: Int? = null,
    val loccountrycode: String? = null,
    val locstatecode: String? = null,
    val personastateflags: Int? = null,
)

@Serializable
data class UserGroupListWrapper(val response: UserGroupList)

/**
 * @property success[Boolean] Result status of the call.
 * @property groups[List] List of groups the user subscribes to.
 */
@Serializable
data class UserGroupList(
    val success: Boolean,
    val groups: List<UserGroup>,
)

/**
 * @property gid[String] 64 bit ID number of group.
 */
@Serializable
data class UserGroup(val gid: String)

@Serializable
data class VanityURLResponseWrapper(val response: VanityURLResponse)

/**
 * @property success[Int] The status of the request. 1 if successful, 42 if there was no match.
 * @property steamid[SteamId] (Optional) The 64 bit Steam ID the vanity URL resolves to. Not returned on resolution
 *           failures.
 * @property message[String] (Optional) The message associated with the request status. Currently only used on
 *           resolution failures.
 */
@Serializable
data class VanityURLResponse(
    val success: Int,
    val steamid: SteamId? = null,
    val message: String? = null,
)

internal const val GET_PLAYER_SUMMARIES = "/ISteamUser/GetPlayerSummaries/v2"
internal const val GET_FRIEND_LIST = "/ISteamUser/GetFriendList/v1"
internal const val GET_PLAYER_BANS = "/ISteamUser/GetPlayerBans/v1"
internal const val GET_USER_GROUP_LIST = "/ISteamUser/GetUserGroupList/v1"
internal const val RESOLVE_VANITY_URL = "/ISteamUser/ResolveVanityURL/v1"

/**
 * Wrapper for the [ISteamUser](https://partner.steamgames.com/doc/webapi/ISteamUser) endpoint which contains methods
 * relating to the Steam users.
 */
class ISteamUserWrapper constructor(
    private val webApiKey: WebApiKey,
    private val webApiClient: HttpClient = WebApiClient.default(),
) {

    /**
     * User friend list.
     *
     * @param steamId[SteamId] The 64 bit ID of the user to retrieve a list for.
     * @param friendRelationship[FriendRelationship] Filter by a given role. Possible options are *all* (All roles),
     *        *friend*.
     * */
    suspend fun getFriendList(steamId: SteamId, friendRelationship: FriendRelationship): FriendListWrapper {
        return webApiClient.get(path = GET_FRIEND_LIST) {
            parameter("key", webApiKey)
            parameter("steamid", steamId)
            parameter("relationship", friendRelationship.value)
        }
    }

    /**
     * Player ban/probation status.
     *
     * @param steamIds[List] Comma-delimited list of steam IDs.
     */
    suspend fun getPlayerBans(steamIds: List<SteamId>): PlayerBanList {
        return webApiClient.get(path = GET_PLAYER_BANS) {
            parameter("key", webApiKey)
            parameter("steamids", Json.encodeToString(steamIds))
        }
    }

    /**
     * User profile data.
     *
     * @param steamIds[List] Comma-delimited list of steam IDs.
     */
    suspend fun getPlayerSummaries(steamIds: List<SteamId>): PlayerSummaryListWrapper {
        return webApiClient.get(path = GET_PLAYER_SUMMARIES) {
            parameter("key", webApiKey)
            parameter("steamids", Json.encodeToString(steamIds))
        }
    }

    /**
     * Lists Group ID(s) linked with 64 bit ID.
     *
     * @param steamId[SteamId] The 64 bit ID of the user.
     */
    suspend fun getUserGroupList(steamId: SteamId): UserGroupListWrapper {
        return webApiClient.get(path = GET_USER_GROUP_LIST) {
            parameter("key", webApiKey)
            parameter("steamid", steamId)
        }
    }

    /**
     * Resolve vanity URL parts to a 64 bit ID.
     *
     * @param vanityUrl[String] The user's vanity URL that you would like to retrieve a steam ID for,
     *        e.g. http://steamcommunity.com/id/gabelogannewell would use "gabelogannewell".
     */
    suspend fun resolveVanityURL(vanityUrl: String): VanityURLResponseWrapper {
        return webApiClient.get(path = RESOLVE_VANITY_URL) {
            parameter("key", webApiKey)
            parameter("vanityurl", vanityUrl)
        }
    }
}
