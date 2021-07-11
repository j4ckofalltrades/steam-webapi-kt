package io.github.j4ckofalltrades.steam_webapi.util

import io.github.j4ckofalltrades.steam_webapi.WebApiClient
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.serialization.Serializable

/**
 * @property servertime[Int] Unix timestamp of WebAPI server.
 * @property servertimestring[String] Time string of WebAPI server.
 */
@Serializable
data class ServerInfo(
    val servertime: Int,
    val servertimestring: String,
)

@Serializable
data class SupportedAPI(
    val apilist: ApiList,
)

@Serializable
data class ApiList(
    val interfaces: List<Interface>,
)

/**
 * @property name[String] Name of the interface.
 * @property methods[List] List of available methods for the interface.
 */
@Serializable
data class Interface(
    val name: String,
    val methods: List<Method>,
)

/**
 * @property name[String] Name of method.
 * @property version[Int] Version of method.
 * @property httpmethod[String] Allowed HTTP method for method (GET, POST).
 * @property parameters[List] List of parameters.
 * @property description[String] (Optional) API documentation of method.
 */
@Serializable
data class Method(
    val name: String,
    val version: Int,
    val httpmethod: String,
    val parameters: List<Parameter>,
    val description: String? = null,
)

/**
 * @property name[String] Name of parameter.
 * @property type[String] Expected type of value.
 * @property optional[Boolean] Is input optional for method.
 * @property description[String] (Optional) API documentation of parameter.
 */
@Serializable
data class Parameter(
    val name: String,
    val type: String,
    val optional: Boolean,
    val description: String? = null,
)

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
    suspend fun getServerInfo(): ServerInfo {
        return webApiClient.get(path = GET_SERVER_INFO)
    }

    /**
     * Gets the list of supported API calls.
     */
    suspend fun getSupportedApiList(): SupportedAPI {
        return webApiClient.get(path = GET_SUPPORTED_API_LIST)
    }
}
