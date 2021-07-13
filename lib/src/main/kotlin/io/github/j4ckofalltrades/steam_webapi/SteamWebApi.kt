package io.github.j4ckofalltrades.steam_webapi

import io.github.j4ckofalltrades.steam_webapi.apps.ISteamAppsWrapper
import io.github.j4ckofalltrades.steam_webapi.news.ISteamNewsWrapper
import io.github.j4ckofalltrades.steam_webapi.user.ISteamUserWrapper
import io.github.j4ckofalltrades.steam_webapi.util.ISteamWebApiUtilWrapper

typealias WebApiKey = String
typealias AppId = Long
typealias SteamId = String

/**
 * Wrapper around the [Steam WebAPI](https://partner.steamgames.com/doc/webapi) endpoints, and is accessed via the
 * provided [webApikey].
 */
class SteamWebApi constructor(private val webApikey: WebApiKey) {

    private val appsApi: ISteamAppsWrapper by lazy {
        ISteamAppsWrapper()
    }

    private val newsApi: ISteamNewsWrapper by lazy {
        ISteamNewsWrapper()
    }

    private val steamWebApiUtil: ISteamWebApiUtilWrapper by lazy {
        ISteamWebApiUtilWrapper()
    }

    private val userApi: ISteamUserWrapper by lazy {
        ISteamUserWrapper(webApikey)
    }

    fun appsApi(): ISteamAppsWrapper {
        return this.appsApi
    }

    fun newsApi(): ISteamNewsWrapper {
        return this.newsApi
    }

    fun steamWebApiUtil(): ISteamWebApiUtilWrapper {
        return this.steamWebApiUtil
    }

    fun userApi(): ISteamUserWrapper {
        return this.userApi
    }
}
