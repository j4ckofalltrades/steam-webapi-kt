package io.github.j4ckofalltrades.steam_webapi.types

import io.github.j4ckofalltrades.steam_webapi.core.AppId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
