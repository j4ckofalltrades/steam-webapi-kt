package io.github.j4ckofalltrades.steam_webapi.wrapper

import io.github.j4ckofalltrades.steam_webapi.core.AppId
import io.github.j4ckofalltrades.steam_webapi.core.WebApiClient
import io.github.j4ckofalltrades.steam_webapi.types.AppNewsWrapper
import io.github.j4ckofalltrades.steam_webapi.types.NewsForAppParams
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal const val GET_NEWS_FOR_APP = "/ISteamNews/GetNewsForApp/v2"

/**
 * Wrapper for the [ISteamNews](https://partner.steamgames.com/doc/webapi/ISteamNews) endpoint which contains methods
 * relating to the Steam news.
 */
class ISteamNewsWrapper constructor(private val webApiClient: HttpClient = WebApiClient.default()) {

    /**
     * Get the news for the specified app.
     *
     * @param appId[AppId] AppID to retrieve news for.
     * @param params[NewsForAppParams] (Optional) Additional request parameters.
     */
    suspend fun getNewsForApp(appId: AppId, params: NewsForAppParams): AppNewsWrapper =
        webApiClient.get(path = GET_NEWS_FOR_APP) {
            parameter("appid", appId)
            parameter("count", params.count)
            params.maxLength?.let { parameter("maxlength", params.maxLength) }
            params.endDate?.let { parameter("enddate", params.endDate) }
            params.feeds?.let { parameter("feeds", params.feeds) }
        }
}
