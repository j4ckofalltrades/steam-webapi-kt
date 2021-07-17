package io.github.j4ckofalltrades.steam_webapi.apps

import io.github.j4ckofalltrades.steam_webapi.AppId
import io.github.j4ckofalltrades.steam_webapi.WebApiClient
import io.github.j4ckofalltrades.steam_webapi.types.AppListWrapper
import io.github.j4ckofalltrades.steam_webapi.types.UpToDateCheckWrapper
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal const val GET_APP_LIST = "/ISteamApps/GetAppList/v2"
internal const val UP_TO_DATE_CHECK = "/ISteamApps/UpToDateCheck/v1"

/**
 * Wrapper for the [ISteamApps](https://partner.steamgames.com/doc/webapi/ISteamApps) endpoint which contains methods
 * relating to the Steam apps.
 */
class ISteamAppsWrapper constructor(private val webApiClient: HttpClient = WebApiClient.default()) {

    /**
     * Full list of every publicly facing program in the store/library.
     */
    suspend fun getAppList(): AppListWrapper = webApiClient.get(path = GET_APP_LIST)

    /**
     * Check if a given app version is the most current available.
     *
     * @param appId[App] AppID of game.
     * @param version[String] The installed version of the game.
     */
    suspend fun upToDateCheck(appId: AppId, version: String): UpToDateCheckWrapper =
        webApiClient.get(path = UP_TO_DATE_CHECK) {
            parameter("appid", appId)
            parameter("version", version)
        }
}
