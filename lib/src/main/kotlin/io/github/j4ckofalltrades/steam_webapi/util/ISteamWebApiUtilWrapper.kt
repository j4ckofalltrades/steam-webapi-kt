package io.github.j4ckofalltrades.steam_webapi.util

import io.github.j4ckofalltrades.steam_webapi.WebApiClient
import io.github.j4ckofalltrades.steam_webapi.types.ServerInfo
import io.github.j4ckofalltrades.steam_webapi.types.SupportedAPI
import io.ktor.client.HttpClient
import io.ktor.client.request.get

internal const val GET_SERVER_INFO = "/ISteamWebAPIUtil/GetServerInfo/v1"
internal const val GET_SUPPORTED_API_LIST = "/ISteamWebAPIUtil/GetSupportedAPIList/v1"

/**
 * Wrapper for the [ISteamWebApiUtil](https://partner.steamgames.com/doc/webapi/ISteamWebAPIUtil) endpoint which
 * contains methods relating to the Steam WebAPI itself.
 */
class ISteamWebApiUtilWrapper constructor(private val webApiClient: HttpClient = WebApiClient.default()) {

    /**
     * Returns WebAPI server time & checks server status.
     */
    suspend fun getServerInfo(): ServerInfo = webApiClient.get(path = GET_SERVER_INFO)

    /**
     * Gets the list of supported API calls.
     */
    suspend fun getSupportedApiList(): SupportedAPI = webApiClient.get(path = GET_SUPPORTED_API_LIST)
}
