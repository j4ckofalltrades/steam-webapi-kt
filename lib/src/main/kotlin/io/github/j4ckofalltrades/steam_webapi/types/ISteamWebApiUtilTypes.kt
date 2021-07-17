package io.github.j4ckofalltrades.steam_webapi.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @property serverTime[Int] Unix timestamp of WebAPI server.
 * @property serverTimeString[String] Time string of WebAPI server.
 */
@Serializable
data class ServerInfo(
    @SerialName("servertime")
    val serverTime: Int,
    @SerialName("servertimestring")
    val serverTimeString: String,
)

@Serializable
data class SupportedAPI(@SerialName("apilist") val apiList: ApiList)

@Serializable
data class ApiList(val interfaces: List<Interface>)

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
 * @property httpMethod[String] Allowed HTTP method for method (GET, POST).
 * @property parameters[List] List of parameters.
 * @property description[String] (Optional) API documentation of method.
 */
@Serializable
data class Method(
    val name: String,
    val version: Int,
    @SerialName("httpmethod")
    val httpMethod: String,
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
