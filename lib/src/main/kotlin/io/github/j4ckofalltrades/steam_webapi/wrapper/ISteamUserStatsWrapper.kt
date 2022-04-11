package io.github.j4ckofalltrades.steam_webapi.wrapper

import io.github.j4ckofalltrades.steam_webapi.core.AppId
import io.github.j4ckofalltrades.steam_webapi.core.SteamId
import io.github.j4ckofalltrades.steam_webapi.core.WebApiClient
import io.github.j4ckofalltrades.steam_webapi.core.WebApiKey
import io.github.j4ckofalltrades.steam_webapi.types.CurrentPlayerCountWrapper
import io.github.j4ckofalltrades.steam_webapi.types.GameSchemaWrapper
import io.github.j4ckofalltrades.steam_webapi.types.GameUserStatsWrapper
import io.github.j4ckofalltrades.steam_webapi.types.GlobalAchievementPercentages
import io.github.j4ckofalltrades.steam_webapi.types.GlobalStatsForGameWrapper
import io.github.j4ckofalltrades.steam_webapi.types.PlayerStatsWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal const val GET_GLOBAL_ACHIEVEMENT_PERCENTAGES_FOR_APP =
    "/ISteamUserStats/GetGlobalAchievementPercentagesForApp/v2"
internal const val GET_GLOBAL_STATS_FOR_GAME = "/ISteamUserStats/GetGlobalStatsForGame/v1"
internal const val GET_NUMBER_OF_CURRENT_PLAYERS = "/ISteamUserStats/GetNumberOfCurrentPlayers/v1"
internal const val GET_PLAYER_ACHIEVEMENTS = "/ISteamUserStats/GetPlayerAchievements/v1"
internal const val GET_SCHEMA_FOR_GAME = "/ISteamUserStats/GetSchemaForGame/v2"
internal const val GET_USER_STATS_FOR_GAME = "/ISteamUserStats/GetUserStatsForGame/v2"

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
    suspend fun getPlayerAchievements(steamId: SteamId, appId: AppId, lang: String? = null): PlayerStatsWrapper =
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
    suspend fun getSchemaForGame(appId: AppId, lang: String? = null): GameSchemaWrapper =
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
    suspend fun getUserStatsForGame(steamId: SteamId, appId: AppId): GameUserStatsWrapper =
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
    suspend fun getGlobalStatsForGame(appId: AppId, count: Int, stats: List<String>): GlobalStatsForGameWrapper =
        webApiClient.get(GET_GLOBAL_STATS_FOR_GAME) {
            parameter("appid", appId)
            parameter("count", count)
            for (i in stats.indices) {
                parameter("name${stats[i]}", stats[i])
            }
        }
            .body()
}
