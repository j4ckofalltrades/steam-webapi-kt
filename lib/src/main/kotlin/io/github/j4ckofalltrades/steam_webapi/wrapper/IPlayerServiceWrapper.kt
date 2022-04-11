package io.github.j4ckofalltrades.steam_webapi.wrapper

import io.github.j4ckofalltrades.steam_webapi.core.AppId
import io.github.j4ckofalltrades.steam_webapi.core.SteamId
import io.github.j4ckofalltrades.steam_webapi.core.WebApiClient
import io.github.j4ckofalltrades.steam_webapi.core.WebApiKey
import io.github.j4ckofalltrades.steam_webapi.types.GetOwnedGamesParams
import io.github.j4ckofalltrades.steam_webapi.types.OwnedGamesWrapper
import io.github.j4ckofalltrades.steam_webapi.types.PlayerBadgeProgress
import io.github.j4ckofalltrades.steam_webapi.types.PlayerBadgesWrapper
import io.github.j4ckofalltrades.steam_webapi.types.RecentlyPlayedGamesWrapper
import io.github.j4ckofalltrades.steam_webapi.types.SharedGameDetailsWrapper
import io.github.j4ckofalltrades.steam_webapi.types.SteamLevelWrapper
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal const val GET_RECENTLY_PLAYED_GAMES = "/IPlayerService/GetRecentlyPlayedGames/v1"
internal const val GET_OWNED_GAMES = "/IPlayerService/GetOwnedGames/v1"
internal const val GET_STEAM_LEVEL = "/IPlayerService/GetSteamLevel/v1"
internal const val GET_BADGES = "/IPlayerService/GetBadges/v1"
internal const val GET_COMMUNITY_BADGE_PROGRESS = "/IPlayerService/GetCommunityBadgeProgress/v1"
internal const val IS_PLAYING_SHARED_GAME = "/IPlayerService/IsPlayingSharedGame/v1"

/**
 * Wrapper for the [IPlayerService](https://partner.steamgames.com/doc/webapi/IPlayerService) endpoint which additional
 * methods for interacting with Steam users.
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
    suspend fun getRecentlyPlayedGames(steamId: SteamId, count: Int? = null): RecentlyPlayedGamesWrapper =
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
    suspend fun getOwnedGames(steamId: SteamId, request: GetOwnedGamesParams? = null): OwnedGamesWrapper =
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
    suspend fun getCommunityBadgeProgress(steamId: SteamId, badge: Int? = null): PlayerBadgeProgress =
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
    suspend fun isPlayingSharedGame(steamId: SteamId, appIdPlaying: AppId): SharedGameDetailsWrapper =
        webApiClient.get(IS_PLAYING_SHARED_GAME) {
            parameter("key", webApiKey)
            parameter("steamid", steamId)
            parameter("appid_playing", appIdPlaying)
        }
            .body()
}
