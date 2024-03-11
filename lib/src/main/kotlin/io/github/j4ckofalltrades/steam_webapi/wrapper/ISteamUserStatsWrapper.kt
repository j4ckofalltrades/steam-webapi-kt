package io.github.j4ckofalltrades.steam_webapi.wrapper

import io.github.j4ckofalltrades.steam_webapi.core.AppId
import io.github.j4ckofalltrades.steam_webapi.core.SteamId
import io.github.j4ckofalltrades.steam_webapi.core.WebApiClient
import io.github.j4ckofalltrades.steam_webapi.core.WebApiKey
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal const val GET_GLOBAL_ACHIEVEMENT_PERCENTAGES_FOR_APP =
    "/ISteamUserStats/GetGlobalAchievementPercentagesForApp/v2"
internal const val GET_GLOBAL_STATS_FOR_GAME = "/ISteamUserStats/GetGlobalStatsForGame/v1"
internal const val GET_NUMBER_OF_CURRENT_PLAYERS = "/ISteamUserStats/GetNumberOfCurrentPlayers/v1"
internal const val GET_PLAYER_ACHIEVEMENTS = "/ISteamUserStats/GetPlayerAchievements/v1"
internal const val GET_SCHEMA_FOR_GAME = "/ISteamUserStats/GetSchemaForGame/v2"
internal const val GET_USER_STATS_FOR_GAME = "/ISteamUserStats/GetUserStatsForGame/v2"

@Serializable
enum class FriendRelationship(val value: String) {
    @SerialName("all")
    ALL("all"),

    @SerialName("friend")
    FRIEND("friend"),
}

@Serializable
data class FriendListWrapper(
    @SerialName("friendslist") val friendsList: FriendList,
)

@Serializable
data class FriendList(val friends: List<Friend>)

/**
 * @property steamId[SteamId] The user's 64-bit ID.
 * @property relationship[FriendRelationship] Role in relation to the given [steamId].
 * @property friendSince[Long] A unix timestamp of when the friend was added to the list.
 *
 */
@Serializable
data class Friend(
    @SerialName("steamid")
    val steamId: SteamId,
    val relationship: FriendRelationship,
    @SerialName("friend_since")
    val friendSince: Long,
)

@Serializable
data class PlayerBanList(val players: List<PlayerBan>)

/**
 * @property steamId[SteamId] A string containing the player's 64 bit ID.
 * @property isCommunityBanned[Boolean] Indicates whether the player is banned from Community.
 * @property isVACBanned[Boolean] Indicates whether the player has VAC bans on record.
 * @property numberOfGameBans[Int] Number of bans in games.
 * @property numberOfVACBans[Int] Number of VAC bans.
 * @property daysSinceLastBan[Int] Days since last ban.
 * @property economyBan[String] String containing the player's ban status in the economy. If the player has no bans on
 *           record the string will be "none", if the player is on probation it will say "probation", and so forth.
 */
@Serializable
data class PlayerBan(
    @SerialName("SteamId")
    val steamId: SteamId,
    @SerialName("CommunityBanned")
    val isCommunityBanned: Boolean,
    @SerialName("VACBanned")
    val isVACBanned: Boolean,
    @SerialName("NumberOfGameBans")
    val numberOfGameBans: Int,
    @SerialName("NumberOfVACBans")
    val numberOfVACBans: Int,
    @SerialName("DaysSinceLastBan")
    val daysSinceLastBan: Int,
    @SerialName("EconomyBan")
    val economyBan: String,
)

@Serializable
data class PlayerSummaryListWrapper(val response: PlayerSummaryList)

@Serializable
data class PlayerSummaryList(val players: List<PlayerSummary>)

/**
 * @property steamId[SteamId] The user's 64-bit ID.
 * @property communityVisibilityState[Int] An integer that describes the access setting of the profile.
 *           1 - Private, 2 - Friends only, 3 - Friends of Friends, 4 - Users Only, 5 Public.
 * @property profileState[Int] If set to 1 the user has configured the profile.
 * @property personaName[String] User's display name.
 * @property profileUrl[String] The URL to the user's Steam Community profile.
 * @property avatar[String] A 32x32 image.
 * @property avatarMedium[String] A 64x64 image.
 * @property avatarFull[String] A 184x184 image.
 * @property avatarHash[String] Avatar identifier.
 * @property personaState[Int] The user's status.
 *           0 - Offline, 1 - Online, 2 - Busy, 3 - Away, 4 - Snooze, 5 - looking to trade, 6 - looking to play.
 * @property commentPermission[String] (Optional) If present the profile allows public comments.
 * @property realName[String] (Optional) The user's real name.
 * @property primaryClanId[String] (Optional) The 64-bit ID of the user's primary group.
 * @property timeCreated[Long] (Optional) A unix timestamp of the date the profile was created.
 * @property locCountryCode[String] (Optional) ISO 3166 code of where the user is located.
 * @property locStateCode[String] (Optional) Variable length code representing the state the user is located in.
 * @property locCityId[Int] (Optional) An integer ID internal to Steam representing the user's city.
 * @property gameId[Long] (Optional) If the user is in game this will be set to its app ID as a string.
 * @property gameExtraInfo[String] (Optional) The title of the game.
 * @property gameServerIp[String] (Optional) The server URL given as an IP address and port number separated by a colon,
 *           this will not be present or set to "0.0.0.0:0" if none is available.
 */
@Serializable
data class PlayerSummary(
    @SerialName("steamid")
    val steamId: SteamId,
    @SerialName("personaname")
    val personaName: String,
    @SerialName("profileurl")
    val profileUrl: String,
    val avatar: String,
    @SerialName("avatarmedium")
    val avatarMedium: String,
    @SerialName("avatarfull")
    val avatarFull: String,
    @SerialName("avatarhash")
    val avatarHash: String,
    @SerialName("personastate")
    val personaState: Int,
    @SerialName("communityvisibilitystate")
    val communityVisibilityState: Int,
    @SerialName("profilestate")
    val profileState: Int,
    @SerialName("commentpermission")
    val commentPermission: String? = null,
    @SerialName("realname")
    val realName: String? = null,
    @SerialName("primaryclanid")
    val primaryClanId: String? = null,
    @SerialName("timecreated")
    val timeCreated: Long? = null,
    @SerialName("gameid")
    val gameId: Long? = null,
    @SerialName("gameserverip")
    val gameServerIp: String? = null,
    @SerialName("gameextrainfo")
    val gameExtraInfo: String? = null,
    @SerialName("loccityid")
    val locCityId: Int? = null,
    @SerialName("loccountrycode")
    val locCountryCode: String? = null,
    @SerialName("locstatecode")
    val locStateCode: String? = null,
    @SerialName("personastateflags")
    val personaStateFlags: Int? = null,
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
 * @property steamId[SteamId] (Optional) The 64-bit Steam ID the vanity URL resolves to. Not returned on resolution
 *           failures.
 * @property message[String] (Optional) The message associated with the request status. Currently only used on
 *           resolution failures.
 */
@Serializable
data class VanityURLResponse(
    val success: Int,
    @SerialName("steamid")
    val steamId: SteamId? = null,
    val message: String? = null,
)

/**
 * Wrapper for the [ISteamUserStats](https://partner.steamgames.com/doc/webapi/ISteamUserStats) endpoint which contains
 * methods used to access information about users.
 */
class ISteamUserStatsWrapper(
    private val webApiKey: WebApiKey,
    val webApiClient: HttpClient = WebApiClient.default(),
) {
    /**
     * Retrieves the global achievement percentages for the specified app.
     *
     * @param appId[AppId] GameID to retrieve the achievement percentages for. This can be the ID of any Steamworks game
     * with achievements available.
     */
    suspend fun getGlobalAchievementPercentagesForApp(appId: AppId): GlobalAchievementPercentages =
        webApiClient.get(GET_GLOBAL_ACHIEVEMENT_PERCENTAGES_FOR_APP) {
            parameter("appid", appId)
        }
            .body()

    /**
     * Gets the total number of players currently active in the specified app on Steam.
     *
     * @param appId[AppId] AppID that we're getting user count for.
     */
    suspend fun getNumberOfConcurrentPlayers(appId: AppId): CurrentPlayerCountWrapper =
        webApiClient.get(GET_NUMBER_OF_CURRENT_PLAYERS) {
            parameter("appid", appId)
        }
            .body()

    /**
     * Gets the list of achievements the specified user has unlocked in an app.
     *
     * @param steamId[SteamId] SteamID of user.
     * @param appId[AppId] AppID to get achievements for.
     * @param lang[String] (Optional) Language to return strings for.
     */
    suspend fun getPlayerAchievements(
        steamId: SteamId,
        appId: AppId,
        lang: String? = null,
    ): PlayerStatsWrapper =
        webApiClient.get(GET_PLAYER_ACHIEVEMENTS) {
            parameter("steamid", steamId)
            parameter("appid", appId)
            lang?.let { parameter("l", lang) }
        }
            .body()

    /**
     * Gets the complete list of stats and achievements for the specified game.
     *
     * @param appId[AppId] AppId of the game.
     * @param lang[String] Localized language to return (english, french, etc.).
     */
    suspend fun getSchemaForGame(
        appId: AppId,
        lang: String? = null,
    ): GameSchemaWrapper =
        webApiClient.get(GET_SCHEMA_FOR_GAME) {
            parameter("key", webApiKey)
            parameter("appid", appId)
            lang?.let { parameter("l", lang) }
        }
            .body()

    /**
     * Gets the list of stats that the specified user has set in an app.
     *
     * @param steamId[SteamId] SteamId of user.
     * @param appId[AppId] AppId of game.
     */
    suspend fun getUserStatsForGame(
        steamId: SteamId,
        appId: AppId,
    ): GameUserStatsWrapper =
        webApiClient.get(GET_USER_STATS_FOR_GAME) {
            parameter("key", webApiKey)
            parameter("steamid", steamId)
            parameter("appid", appId)
        }
            .body()

    /**
     * Retrieves the global stats percentages for the specified app.
     *
     * @param appId[AppId] AppID that we're getting global stats for.
     * @param count[Int] Number of stats get data for.
     * @param stats[List] Names of stat to get data for.
     */
    suspend fun getGlobalStatsForGame(
        appId: AppId,
        count: Int,
        stats: List<String>,
    ): GlobalStatsForGameWrapper =
        webApiClient.get(GET_GLOBAL_STATS_FOR_GAME) {
            parameter("appid", appId)
            parameter("count", count)
            for (i in stats.indices) {
                parameter("name${stats[i]}", stats[i])
            }
        }
            .body()
}
