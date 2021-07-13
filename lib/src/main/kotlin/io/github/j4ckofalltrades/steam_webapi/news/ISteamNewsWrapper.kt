package io.github.j4ckofalltrades.steam_webapi.news

import io.github.j4ckofalltrades.steam_webapi.AppId
import io.github.j4ckofalltrades.steam_webapi.WebApiClient
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal const val GET_NEWS_FOR_APP = "/ISteamNews/GetNewsForApp/v2"

/**
 * @property maxLength[String] Maximum length for the content to return, if this is 0 the full content is returned, if
 *           it's less then a blurb is generated to fit.
 * @property endDate[Int] (Optional) Retrieve posts earlier than this date (unix epoch timestamp).
 * @property count[Int] (Optional) Number of posts to retrieve (default 20).
 * @property feeds[Int] (Optional) Comma-separated list of feed names to return news for.
 */
data class NewsForAppParams(
    val maxLength: Int? = null,
    val endDate: Int? = null,
    val count: Int = 20,
    val feeds: String? = null,
)

@Serializable
data class AppNewsWrapper(@SerialName("appnews") val appNews: AppNews)

/**
 * @property appId[AppId] AppID to retrieve news for.
 * @property newsItems[List] A list of objects describing each news item.
 */
@Serializable
data class AppNews(
    @SerialName("appid")
    val appId: AppId,
    @SerialName("newsitems")
    val newsItems: List<NewsItem>,
)

/**
 * @property gid[String] The unique identifier of the news item.
 * @property title[String] Title of the news item.
 * @property url[String] Permanent link to the item
 * @property isExternalUrl[Boolean] true if the url given links to an external website. false if it links to the Steam
 *           store.
 * @property author[String] The author of the news item.
 * @property contents[String] The article body with a length equal to the given length with an appended ellipsis if it is
 *           exceeded.
 * @property feedLabel[Long] The category label of the news item.
 * @property date A unix timestamp of the date the item was posted.
 * @property feedName[String] An internal tag that describes the source of the news item.
 * @property appId[String] AppID where the news item belong to.
 */
@Serializable
data class NewsItem(
    val gid: String,
    val title: String,
    val url: String,
    @SerialName("is_external_url")
    val isExternalUrl: Boolean,
    val author: String,
    val contents: String,
    @SerialName("feedlabel")
    val feedLabel: String,
    val date: Long,
    @SerialName("feedname")
    val feedName: String,
    @SerialName("feed_type")
    val feedType: Int,
    @SerialName("appid")
    val appId: AppId,
)

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
