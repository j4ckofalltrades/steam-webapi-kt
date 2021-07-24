package io.github.j4ckofalltrades.steam_webapi.types

import io.github.j4ckofalltrades.steam_webapi.core.SteamId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class FriendRelationship(val value: String) {
    @SerialName("all")
    ALL("all"),

    @SerialName("friend")
    FRIEND("friend"),
}

@Serializable
data class FriendListWrapper(@SerialName("friendslist") val friendsList: FriendList)

@Serializable
data class FriendList(val friends: List<Friend>)

/**
 * @property steamId[SteamId] The user's 64 bit ID.
 * @property relationship[FriendRelationship] Role in relation to the given [steamId].
 * @property friendSince[Long] A unix timestamp of when the friend was added to the list.
 *
 */
@Serializable
data class Friend(
    @SerialName("steamid")
    val steamId: SteamId,
    val relationship: FriendRelationship,
    @SerialName("friend_since")
    val friendSince: Long,
)

@Serializable
data class PlayerBanList(val players: List<PlayerBan>)

/**
 * @property steamId[SteamId] A string containing the player's 64 bit ID.
 * @property isCommunityBanned[Boolean] Indicates whether or not the player is banned from Community.
 * @property isVACBanned[Boolean] Indicates whether or not the player has VAC bans on record.
 * @property numberOfGameBans[Int] Number of bans in games.
 * @property numberOfVACBans[Int] Number of VAC bans.
 * @property daysSinceLastBan[Int] Days since last ban.
 * @property economyBan[String] String containing the player's ban status in the economy. If the player has no bans on
 *           record the string will be "none", if the player is on probation it will say "probation", and so forth.
 */
@Serializable
data class PlayerBan(
    @SerialName("SteamId")
    val steamId: SteamId,
    @SerialName("CommunityBanned")
    val isCommunityBanned: Boolean,
    @SerialName("VACBanned")
    val isVACBanned: Boolean,
    @SerialName("NumberOfGameBans")
    val numberOfGameBans: Int,
    @SerialName("NumberOfVACBans")
    val numberOfVACBans: Int,
    @SerialName("DaysSinceLastBan")
    val daysSinceLastBan: Int,
    @SerialName("EconomyBan")
    val economyBan: String,
)

@Serializable
data class PlayerSummaryListWrapper(val response: PlayerSummaryList)

@Serializable
data class PlayerSummaryList(val players: List<PlayerSummary>)

/**
 * @property steamId[SteamId] The user's 64 bit ID.
 * @property communityVisibilityState[Int] An integer that describes the access setting of the profile.
 *           1 - Private, 2 - Friends only, 3 - Friends of Friends, 4 - Users Only, 5 Public.
 * @property profileState[Int] If set to 1 the user has configured the profile.
 * @property personaName[String] User's display name.
 * @property profileUrl[String] The URL to the user's Steam Community profile.
 * @property avatar[String] A 32x32 image.
 * @property avatarMedium[String] A 64x64 image.
 * @property avatarFull[String] A 184x184 image.
 * @property avatarHash[String] Avatar identifier.
 * @property personaState[Int] The user's status.
 *           0 - Offline, 1 - Online, 2 - Busy, 3 - Away, 4 - Snooze, 5 - looking to trade, 6 - looking to play.
 * @property commentPermission[String] (Optional) If present the profile allows public comments.
 * @property realName[String] (Optional) The user's real name.
 * @property primaryClanId[String] (Optional) The 64 bit ID of the user's primary group.
 * @property timeCreated[Long] (Optional) A unix timestamp of the date the profile was created.
 * @property locCountryCode[String] (Optional) ISO 3166 code of where the user is located.
 * @property locStateCode[String] (Optional) Variable length code representing the state the user is located in.
 * @property locCityId[Int] (Optional) An integer ID internal to Steam representing the user's city.
 * @property gameId[Long] (Optional) If the user is in game this will be set to it's app ID as a string.
 * @property gameExtraInfo[String] (Optional) The title of the game.
 * @property gameServerIp[String] (Optional) The server URL given as an IP address and port number separated by a colon,
 *           this will not be present or set to "0.0.0.0:0" if none is available.
 */
@Serializable
data class PlayerSummary(
    @SerialName("steamid")
    val steamId: SteamId,
    @SerialName("personaname")
    val personaName: String,
    @SerialName("profileurl")
    val profileUrl: String,
    val avatar: String,
    @SerialName("avatarmedium")
    val avatarMedium: String,
    @SerialName("avatarfull")
    val avatarFull: String,
    @SerialName("avatarhash")
    val avatarHash: String,
    @SerialName("personastate")
    val personaState: Int,
    @SerialName("communityvisibilitystate")
    val communityVisibilityState: Int,
    @SerialName("profilestate")
    val profileState: Int,
    @SerialName("commentpermission")
    val commentPermission: String? = null,
    @SerialName("realname")
    val realName: String? = null,
    @SerialName("primaryclanid")
    val primaryClanId: String? = null,
    @SerialName("timecreated")
    val timeCreated: Long? = null,
    @SerialName("gameid")
    val gameId: Long? = null,
    @SerialName("gameserverip")
    val gameServerIp: String? = null,
    @SerialName("gameextrainfo")
    val gameExtraInfo: String? = null,
    @SerialName("loccityid")
    val locCityId: Int? = null,
    @SerialName("loccountrycode")
    val locCountryCode: String? = null,
    @SerialName("locstatecode")
    val locStateCode: String? = null,
    @SerialName("personastateflags")
    val personaStateFlags: Int? = null,
)

@Serializable
data class UserGroupListWrapper(val response: UserGroupList)

/**
 * @property success[Boolean] Result status of the call.
 * @property groups[List] List of groups the user subscribes to.
 */
@Serializable
data class UserGroupList(
    val success: Boolean,
    val groups: List<UserGroup>,
)

/**
 * @property gid[String] 64 bit ID number of group.
 */
@Serializable
data class UserGroup(val gid: String)

@Serializable
data class VanityURLResponseWrapper(val response: VanityURLResponse)

/**
 * @property success[Int] The status of the request. 1 if successful, 42 if there was no match.
 * @property steamId[SteamId] (Optional) The 64 bit Steam ID the vanity URL resolves to. Not returned on resolution
 *           failures.
 * @property message[String] (Optional) The message associated with the request status. Currently only used on
 *           resolution failures.
 */
@Serializable
data class VanityURLResponse(
    val success: Int,
    @SerialName("steamid")
    val steamId: SteamId? = null,
    val message: String? = null,
)
