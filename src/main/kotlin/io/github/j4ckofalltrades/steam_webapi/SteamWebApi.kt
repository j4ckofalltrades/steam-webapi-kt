package io.github.j4ckofalltrades.steam_webapi

import io.github.j4ckofalltrades.steam_webapi.util.ISteamWebApiUtilWrapper

/**
 * Wrapper around the [Steam WebAPI](https://partner.steamgames.com/doc/webapi) endpoints.
 *
 * @property webApikey[WebApiKey] Steam WebAPI key.
 * @constructor Steam WebAPI wrapper.
 */
class SteamWebApi constructor(private val webApikey: WebApiKey) {

    private val steamWebApiUtil: ISteamWebApiUtilWrapper by lazy {
        ISteamWebApiUtilWrapper()
    }

    fun steamWebApiUtil(): ISteamWebApiUtilWrapper {
        return this.steamWebApiUtil
    }
}
