package io.github.j4ckofalltrades.steam_webapi.types

import io.github.j4ckofalltrades.steam_webapi.core.AppId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
