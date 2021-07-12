package io.github.j4ckofalltrades.steam_webapi.apps

import io.github.j4ckofalltrades.steam_webapi.AppId
import io.github.j4ckofalltrades.steam_webapi.WebApiClient
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal const val GET_APP_LIST = "/ISteamApps/GetAppList/v2"
internal const val UP_TO_DATE_CHECK = "/ISteamApps/UpToDateCheck/v1"

@Serializable
data class AppListWrapper(@SerialName("applist") val appList: AppList)

@Serializable
data class AppList(val apps: List<App>)

/**
 * @property appid[AppId] An integer containing the program's ID.
 * @property name[String] A string containing the program's publicly facing title.
 */
@Serializable
data class App(
    val appid: AppId,
    val name: String,
)

@Serializable
data class UpToDateCheckWrapper(val response: UpToDateCheck)

/**
 * @property success[Boolean] Boolean indicating if request was successful.
 * @property isUpToDate[Boolean] Boolean indicating if the given version number is the most current version.
 * @property isListableVersion[Boolean] Boolean indicating if the given version can be listed in public changelogs.
 * @property requiredVersion[Int] (Optional) Integer of the most current version of the app available.
 * @property message[String] (Optional) A string giving the status message if applicable.
 */
@Serializable
data class UpToDateCheck(
    val success: Boolean,
    @SerialName("up_to_date")
    val isUpToDate: Boolean,
    @SerialName("version_is_listable")
    val isListableVersion: Boolean,
    @SerialName("required_version")
    val requiredVersion: Int? = null,
    val message: String? = null,
)

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
