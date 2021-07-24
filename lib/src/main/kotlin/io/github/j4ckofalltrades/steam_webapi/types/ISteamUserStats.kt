package io.github.j4ckofalltrades.steam_webapi.types

import io.github.j4ckofalltrades.steam_webapi.core.SteamId
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class CurrentPlayerCountWrapper(
    val response: CurrentPlayerCount,
)

/**
 * @property playerCount[Long] Total number of currently active players for the specified app.
 * @property result[Int] The status of the request. 1 if successful, 42 if there was no match.
 */
@Serializable
data class CurrentPlayerCount(
    @SerialName("player_count")
    val playerCount: Long,
    val result: Int,
)

@Serializable
data class GameSchemaWrapper(
    val game: GameSchema,
)

/**
 * @property gameName[String] Steam internal (non-localized) name of game.
 * @property gameVersion[String] Steam release version number currently live on Steam.
 * @property availableGameStats[List] List of available achievements and stats for the game.
 */
@Serializable
data class GameSchema(
    val gameName: String,
    val gameVersion: String,
    val availableGameStats: GameSchemaStats,
)

@Serializable
data class GameSchemaStats(
    val achievements: List<GameSchemaAchievement>,
    val stats: List<GameSchemaStat>,
)

/**
 * @property name[String] API name of stat.
 * @property defaultValue[Int] Default value of stat.
 * @property displayName[String] Developer provided name of stat.
 */
@Serializable
data class GameSchemaStat(
    val name: String,
    @SerialName("defaultvalue")
    val defaultValue: Int,
    val displayName: String,
)

/**
 * @property name[String] API Name of achievement.
 * @property defaultValue[Int] Always 0 (player's default state is unachieved).
 * @property displayName[String] Display title string of achievement.
 * @property hidden[Int] If achievement is hidden to the user before earning achievement, value is 1. 0 if public.
 * @property description[String] Display description string of achievement.
 * @property icon[String] Absolute URL of earned achievement icon art.
 * @property iconGray[String] Absolute URL of un-earned achievement icon art.
 */
@Serializable
data class GameSchemaAchievement(
    val name: String,
    @SerialName("defaultvalue")
    val defaultValue: Int,
    val displayName: String,
    val hidden: Int,
    val description: String,
    val icon: String,
    @SerialName("icongray")
    val iconGray: String,
)

@Serializable
data class GameUserStatsWrapper(
    val playerstats: GameUserStats,
)

/**
 * @property steamId[SteamId] SteamId of user.
 * @property gameName[String] String containing the game title.
 * @property achievements[List] List of game achievements the user has unlocked.
 */
@Serializable
data class GameUserStats(
    @SerialName("steamID")
    val steamId: SteamId,
    val gameName: String,
    val achievements: List<GameUserAchievement>,
)

/**
 * @property name[String] String containing the ID of the achievement.
 * @property achieved[Int] Integer to be used as a boolean value indicating whether or not the achievement has been unlocked
 *           by the user.
 */
@Serializable
data class GameUserAchievement(
    val name: String,
    val achieved: Int,
)

@Serializable
data class GlobalStatsForGameWrapper(
    val response: GlobalStatsForGame,
)

/**
 * @property result[Int] Result code.
 * @property stats[JsonElement] Array of global game statistics.
 */
@Serializable
data class GlobalStatsForGame(
    val result: Int,
    @SerialName("globalstats")
    val stats: JsonElement,
)

/**
 * @property globalAchievements[List] List of achievements and percentage of players that have unlocked said achievement.
 */
@Serializable
data class GlobalAchievementPercentages(
    @SerialName("achievementpercentages")
    val globalAchievements: GlobalAchievementList,
)

@Serializable
data class GlobalAchievementList(
    val achievements: List<GlobalAchievement>,
)

/**
 * @property name[String] The name of the achievement as an unlocalized token.
 * @property percent[Double] Percentage of player population that has unlocked the achievement given as a double.
 */
@Serializable
data class GlobalAchievement(
    val name: String,
    val percent: Double,
)

@Serializable
data class PlayerStatsWrapper(
    val playerstats: PlayerStats,
)

/**
 * @property steamId[SteamId] The 64 bit ID of the user.
 * @property gameName[String] String containing the game title.
 * @property achievements[List] List of achievements unlocked by the user.
 */
@Serializable
data class PlayerStats(
    @SerialName("steamID")
    val steamId: SteamId,
    val gameName: String,
    val achievements: List<PlayerAchievement>,
    val success: Boolean,
)

/**
 * @property apiName[String] String containing the ID of the achievement.
 * @property achieved[Int] Integer to be used as a boolean value indicating whether or not the achievement has been
 *           unlocked by the user. 1 means the achievement has been unlocked, 0 if otherwise.
 * @property unlockTime[Long] A Unix timestamp of the date when the achievement was unlocked.
 */
@Serializable
data class PlayerAchievement(
    @SerialName("apiname")
    val apiName: String,
    val achieved: Int,
    @SerialName("unlocktime")
    val unlockTime: Long,
)
