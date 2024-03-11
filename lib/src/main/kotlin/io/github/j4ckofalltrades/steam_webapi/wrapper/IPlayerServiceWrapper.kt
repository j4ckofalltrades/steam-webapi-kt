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

internal const val GET_RECENTLY_PLAYED_GAMES = "/IPlayerService/GetRecentlyPlayedGames/v1"
internal const val GET_OWNED_GAMES = "/IPlayerService/GetOwnedGames/v1"
internal const val GET_STEAM_LEVEL = "/IPlayerService/GetSteamLevel/v1"
internal const val GET_BADGES = "/IPlayerService/GetBadges/v1"
internal const val GET_COMMUNITY_BADGE_PROGRESS = "/IPlayerService/GetCommunityBadgeProgress/v1"
internal const val IS_PLAYING_SHARED_GAME = "/IPlayerService/IsPlayingSharedGame/v1"

/**
 * @property includeAppInfo[Boolean] (Optional) Whether to include additional details of apps - name and images.
 *           Defaults to false.
 * @property includePlayedFreeGames[Boolean] (Optional) Whether to list free-to-play games in the results.
 *           Defaults to false.
 * @property appIdsFilter[List] (Optional) Restricts results to the appids passed here. This is an array and should be
 *           passed like "appids_filter[0]=440&appids_filter[1]=570".
 */
data class GetOwnedGamesParams(
    val includeAppInfo: Boolean? = null,
    val includePlayedFreeGames: Boolean? = null,
    val appIdsFilter: List<Long>? = null,
)

@Serializable
data class OwnedGamesWrapper(val response: OwnedGames)

@Serializable
data class OwnedGames(
    @SerialName("game_count")
    val gameCount: Long,
    val games: List<Game>,
)

/**
 * @property appId[AppId] An integer containing the program's ID.
 * @property playtimeForever[Int] An integer of the player's total playtime, denoted in minutes.
 * @property playtimeWindowsForever[Int] An integer of the player's total playtime on Windows, denoted in minutes.
 * @property playtimeMACForever[Int] An integer of the player's total playtime on macOS, denoted in minutes.
 * @property playtimeLinuxForever[Int] An integer of the player's total playtime on Linux, denoted in minutes.
 */
@Serializable
data class Game(
    @SerialName("appid")
    val appId: Long,
    @SerialName("playtime_forever")
    val playtimeForever: Long,
    @SerialName("playtime_windows_forever")
    val playtimeWindowsForever: Long,
    @SerialName("playtime_mac_forever")
    val playtimeMACForever: Long,
    @SerialName("playtime_linux_forever")
    val playtimeLinuxForever: Long,
)

@Serializable
data class PlayerBadgesWrapper(val response: PlayerBadges)

@Serializable
data class PlayerBadges(
    val badges: List<Badge>,
    @SerialName("player_xp")
    val playerXP: Long,
    @SerialName("player_level")
    val playerLevel: Long,
    @SerialName("player_xp_needed_to_level_up")
    val playerXPNeededToLevelUp: Long,
    @SerialName("player_xp_needed_current_level")
    val playerXPNeededCurrentLevel: Long,
)

/**
 * @property badgeId[String] Badge ID. Currently no official badge schema is available.
 * @property completionTime[Long] Unix timestamp of when the steam user acquired the badge.
 * @property xp[Long] The experience this badge is worth, contributing toward the steam account's player_xp.
 * @property scarcity[Long] The amount of people who has this badge.
 * @property appId[AppId] (Optional) Provided if the badge relates to an app (trading cards).
 * @property communityItemId[Int] (Optional) Provided if the badge relates to an app (trading cards); the value doesn't
 *           seem to be an item in the steam accounts backpack, however the value minus 1 seems to be the item ID for
 *           the emoticon granted for crafting this badge, and the value minus 2 seems to be the background granted.
 * @property borderColor[String] (Optional) Provided if the badge relates to an app (trading cards).
 */
@Serializable
data class Badge(
    @SerialName("badgeid")
    val badgeId: Long,
    val level: Long,
    @SerialName("completion_time")
    val completionTime: Long,
    val xp: Long,
    val scarcity: Long,
    @SerialName("appid")
    val appId: AppId? = null,
    @SerialName("communityitemid")
    val communityItemId: Int? = null,
    @SerialName("border_color")
    val borderColor: String? = null,
)

@Serializable
data class PlayerBadgeProgress(val response: QuestList)

/**
 * @property quests[List] Array of quests (actions required to unlock a badge).
 */
@Serializable
data class QuestList(val quests: List<Quest>)

/**
 * @property questId[Long] Quest ID.
 * @property completed[Boolean] Whether the Steam account has completed this quest.
 */
@Serializable
data class Quest(
    @SerialName("questid")
    val questId: Long,
    val completed: Boolean,
)

@Serializable
data class SteamLevelWrapper(val response: SteamLevel)

/**
 * @property playerLevel[Int] The Steam level of the player.
 */
@Serializable
data class SteamLevel(
    @SerialName("player_level") val playerLevel: Int,
)

@Serializable
data class SharedGameDetailsWrapper(val response: SharedGameDetails)

/**
 * @property lenderSteamId[SteamId] Owner of shared game.
 */
@Serializable
data class SharedGameDetails(
    @SerialName("lender_steamid") val lenderSteamId: SteamId,
)

@Serializable
data class RecentlyPlayedGamesWrapper(val response: RecentlyPlayedGames)

@Serializable
data class RecentlyPlayedGames(
    @SerialName("total_count")
    val totalCount: Long,
    val games: List<RecentlyPlayedGame>,
)

/**
 * @property appId[AppId] An integer containing the program's ID.
 * @property name[String] A string containing the program's publicly facing title.
 * @property playtime2Weeks[Int] An integer of the player's playtime in the past 2 weeks, denoted in minutes.
 * @property playtimeForever[Int] An integer of the player's total playtime, denoted in minutes.
 * @property imgIconURL[String] The program icon's file name, accessible at:
 *           http://media.steampowered.com/steamcommunity/public/images/apps/APPID/IMG_ICON_URL.jpg, replacing "APPID"
 *           and "IMG_ICON_URL" as necessary.
 * @property imgLogoURL[String] The program logo's file name, accessible at:
 *           http://media.steampowered.com/steamcommunity/public/images/apps/APPID/IMG_LOGO_URL.jpg, replacing "APPID"
 *           and "IMG_ICON_URL" as necessary.
 * @property playtimeWindowsForever[Int] An integer of the player's total playtime on Windows, denoted in minutes.
 * @property playtimeMACForever[Int] An integer of the player's total playtime on macOS, denoted in minutes.
 * @property playtimeLinuxForever[Int] An integer of the player's total playtime on Linux, denoted in minutes.
 */
@Serializable
data class RecentlyPlayedGame(
    @SerialName("appid")
    val appId: Long,
    val name: String,
    @SerialName("playtime_2weeks")
    val playtime2Weeks: Int,
    @SerialName("playtime_forever")
    val playtimeForever: Int,
    @SerialName("img_icon_url")
    val imgIconURL: String,
    @SerialName("img_logo_url")
    val imgLogoURL: String,
    @SerialName("playtime_windows_forever")
    val playtimeWindowsForever: Long,
    @SerialName("playtime_mac_forever")
    val playtimeMACForever: Long,
    @SerialName("playtime_linux_forever")
    val playtimeLinuxForever: Long,
)

/**
 * Wrapper for the [IPlayerService](https://partner.steamgames.com/doc/webapi/IPlayerService) endpoint which contains
 * additional methods for interacting with Steam users.
 */
class IPlayerServiceWrapper(
    private val webApiKey: WebApiKey,
    val webApiClient: HttpClient = WebApiClient.default(),
) {
    /**
     * Gets information about a player's recently played games.
     *
     * @param steamId[SteamId] The player we're asking about.
     * @param count The number of games to return (0/unset: all).
     */
    suspend fun getRecentlyPlayedGames(
        steamId: SteamId,
        count: Int? = null,
    ): RecentlyPlayedGamesWrapper =
        webApiClient.get(GET_RECENTLY_PLAYED_GAMES) {
            parameter("key", webApiKey)
            parameter("steamid", steamId)
            count?.let { parameter("count", count) }
        }
            .body()

    /**
     * Return a list of games owned by the player.
     *
     * @param steamId[SteamId] The player we're asking about.
     * @param request[GetOwnedGamesParams] (Optional) Additional request parameters.
     */
    suspend fun getOwnedGames(
        steamId: SteamId,
        request: GetOwnedGamesParams? = null,
    ): OwnedGamesWrapper =
        webApiClient.get(GET_OWNED_GAMES) {
            parameter("key", webApiKey)
            parameter("steamid", steamId)
            request?.includeAppInfo?.let { parameter("include_appinfo", request.includeAppInfo) }
            request?.includePlayedFreeGames?.let {
                parameter("include_played_free_games", request.includePlayedFreeGames)
            }
            request?.appIdsFilter?.let {
                for (i in request.appIdsFilter.indices) {
                    parameter("appids_filter[${request.appIdsFilter[i]}]", request.appIdsFilter[i])
                }
            }
        }
            .body()

    /**
     * Returns the Steam Level of a user.
     *
     * @param steamId[SteamId] The player we're asking about.
     */
    suspend fun getSteamLevel(steamId: SteamId): SteamLevelWrapper =
        webApiClient.get(GET_STEAM_LEVEL) {
            parameter("key", webApiKey)
            parameter("steamid", steamId)
        }
            .body()

    /**
     * Gets badges that are owned by a specific user.
     *
     * @param steamId[SteamId] The player we're asking about.
     */
    suspend fun getBadges(steamId: SteamId): PlayerBadgesWrapper =
        webApiClient.get(GET_BADGES) {
            parameter("key", webApiKey)
            parameter("steamid", steamId)
        }
            .body()

    /**
     * Gets all the quests needed to get the specified badge, and which are completed.
     *
     * @param steamId[SteamId] The player we're asking about.
     * @param badge[Int] The badge we're asking about.
     */
    suspend fun getCommunityBadgeProgress(
        steamId: SteamId,
        badge: Int? = null,
    ): PlayerBadgeProgress =
        webApiClient.get(GET_COMMUNITY_BADGE_PROGRESS) {
            parameter("key", webApiKey)
            parameter("steamid", steamId)
            badge?.let { parameter("badgeid", badge) }
        }
            .body()

    /**
     * Returns valid lender SteamID if game currently played is borrowed.
     *
     * @param steamId[SteamId] The player we're asking about.
     * @param appIdPlaying[AppId] The game player is currently playing.
     */
    suspend fun isPlayingSharedGame(
        steamId: SteamId,
        appIdPlaying: AppId,
    ): SharedGameDetailsWrapper =
        webApiClient.get(IS_PLAYING_SHARED_GAME) {
            parameter("key", webApiKey)
            parameter("steamid", steamId)
            parameter("appid_playing", appIdPlaying)
        }
            .body()
}
