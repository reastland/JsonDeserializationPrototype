package com.example.jsondeserializationprototype.kotlinserializationmodels

import kotlinx.serialization.*
import org.joda.time.DateTime
import java.time.LocalDate
import java.util.*

/**
 * ImmutablesSportsDataApiResponse
 * https://statsapi.mlb.com/api/v1/schedule?hydrate=decisions,probablePitcher,broadcasts(all),
 *      game(content(all)),linescore(matchup,runners,positions),stats,person,team,
 *      flags&sportId=1%2C51&date=09%2F02%2F2018
 */
@Serializable
data class KotlinSerializationSportsDataApiResponse(
    @ContextualSerialization val date: LocalDate = LocalDate.of(2019,1,1),
    val dates: List<Day> = listOf(),
    val totalGames: Int = 0) {

    @Serializable
    data class Day(
        val totalGames: Int = 0,
        val games: List<Game> = listOf()
    )

    @Serializable
    data class Game(
        val gamePk: Int = 0,
        val link: String = "",
//        @ContextualSerialization val gameType: DateTime, // requires explicit serialization
//        val gameDate: DateTime, // requires explicit serialization
//        var resumeDate: DateTime? = null, // requires explicit serialization
//        var resumedFrom: DateTime? = null, // requires explicit serialization
        val status: Status = Status(),
        val teams: Teams = Teams(),
        val venue: Venue = Venue(),
        val content: Content = Content(),
        val isTie: Boolean = false,
        val doubleHeader: String = "",
        val gamedayType: String = "", //"P"
        val tiebreaker: String = "",
        val gameNumber: Int = 0, //1
        val calendarEventID: String = "", //14-531605-2018-09-14
        val seasonDisplay: String = "", //2018
        val dayNight: String = "", //"night"
        val scheduledInnings: Int = 0, //9
        val gamesInSeries: Int = 0, //3
        val seriesGameNumber: Int = 0, //1
        val seriesDescription: String = "", //Regular Season
        val description: String = "", //World Series Game 1
        val recordSource: String = "", //S
        val ifNecessary: String = "", //"N"
        val ifNecessaryDescription: String = "", //Normal Game
        val linescore: LineScore? = LineScore(), //Line Score of the game
        val decisions: Decisions? = Decisions(), //Decisions
        val flags: Flags = Flags()
    )

    @Serializable
    data class Flags(val noHitter: Boolean = false, val perfectGame: Boolean = false)

    @Serializable
    data class Status(
        val abstractGameCode: String = "",
        val statusCode: String = "",
        val codedGameState: String = ""
    )

    @Serializable
    data class Teams(val away: TeamStatus = TeamStatus(), val home: TeamStatus = TeamStatus())

    @Serializable
    data class TeamStatus(
        val score: Int = 0,
        val team: Team = Team(),
        val leagueRecord: TeamLeagueRecord = TeamLeagueRecord(),
        val probablePitcher: Player? = Player(),
        val splitSquad: Boolean = false
    )

    @Serializable
    data class Team(
        val id: Int = 0,
        val name: String = "",
        val teamCode: String = "",
        val fileCode: String = "",
        val abbreviation: String = "",
        val teamName: String = "",
        val locationName: String = "",
        val shortName: String = "",
        val springLeague: League? = League(),
        val springLeagueRank: Int = 0,
        val division: Division = Division()
    )


    @Serializable data class TeamLeagueRecord(val wins: Int = 0, val losses: Int = 0, val pct: Float = 0f)
    @Serializable data class Venue(val id: Int = 0, val name: String = "", val link: String = "")
    @Serializable data class Content(
        val link: String = "",
        val media: Media? = Media(),
        val highlights: KotlinSerializationGameHighlightResponse.Highlights? = KotlinSerializationGameHighlightResponse.Highlights()
    )

    @Serializable
    data class Media(
        val epg: List<Epg>? = listOf(),
        val epgAlternate: List<Epg> = listOf(),
        val enhancedGame: Boolean = false,
        val freeGame: Boolean = false
    )

    @Serializable
    data class Epg (val title: String = "", val items: List<Items>)

    @Serializable
    data class Items(
        val id: Int = 0,
        val contentId: String = "",
        val mediaId: String = "",
        val mediaState: String = "",
        val mediaFeedType: String = "",
        val mediaFeedSubType: String = "",
        val callLetters: String = "",
        val foxAuthRequired: Boolean = false,
        val tbsAuthRequired: Boolean = false,
        val espnAuthRequired: Boolean = false,
        val fs1AuthRequired: Boolean = false,
        val mlbnAuthRequired: Boolean = false,
        val freeGame: Boolean = false
    )

    @Serializable
    data class VideoEpgItem(
        val id: String = "",
        val contentId: String = "",
        val mediaId: String = "",
        val mediaState: String = "", //Enum candidate
        val mediaFeedType: String = "", //Enum candidate HOME|AWAY
        val mediaFeedSubType: String = "",
        val callLetters: String = "",
        val foxAuthRequired: Boolean = false,
        val tbsAuthRequired: Boolean = false,
        val espnAuthRequired: Boolean = false,
        val fs1AuthRequired: Boolean = false,
        val mlbnAuthRequired: Boolean = false,
        val freeGame: Boolean = false
    )

    @Serializable
    data class MlbtvAudioEpgItem(
        val id: String = "",
        val type: String = "",
        val mediaFeedType: String = "",
        val description: String = "",
        val renditionName: String = "",
        val language: String
    )

    @Serializable
    data class AudioEpgItem(
        val id: String = "",
        val contentId: String = "",
        val mediaId: String = "",
        val mediaState: String = "",
        val type: String = "", //AWAY|HOME
        val mediaFeedSubType: String = "", //3 digit number
        val callLetters: String = "",
        val language: String
    )

    @Serializable
    data class LineScore(
        val currentInning: Int? = 0, // Current Inning
        val currentInningOrdinal: String? = "", //"9th"
        val inningState: String? = "", //Top, Middle, Bottom, End (enumeration)
        val inningHalf: String? = "", //Top, Bottom
        val isTopInning: Boolean? = false, //true/false
        val innings: List<Inning>? = listOf(),
        val teams: LineScoreTeams = LineScoreTeams(),
        val balls: Int? = 0,
        val strikes: Int? = 0,
        val outs: Int? = 0,
        val offense: Offense? = Offense(),
        val defense: Defense? = Defense()
    )

    @Serializable data class LineScoreTeams(val home: LineScoreTeam = LineScoreTeam(), val away: LineScoreTeam = LineScoreTeam())
    @Serializable data class LineScoreTeam(val runs: Int = 0, val hits: Int = 0, val errors: Int = 0, val leftOnBase: Int = 0)
    @Serializable data class Inning(
        val num: Int = 0,
        val ordinalNum: String = "",
        val home: LineScoreTeam,
        val away: LineScoreTeam
    )

    @Serializable
    data class Offense(
        val batter: Player? = Player(),
        val first: Player? = Player(),
        val second: Player? = Player(),
        val third: Player? = Player()
    )

    @Serializable
    data class Defense(
        val pitcher: Player? = Player(),
        val catcher: Player? = Player(),
        val first: Player? = Player(),
        val second: Player? = Player(),
        val third: Player? = Player(),
        val shortstop: Player? = Player(),
        val left: Player? = Player(),
        val center: Player? = Player(),
        val right: Player? = Player()
    )

    @Serializable
    data class Decisions(val winner: Player? = Player(), val loser: Player? = Player(), val save: Player? = Player())

    @Serializable
    data class Player(
        val id: Int = 0,
        val fullName: String? = "",
        val link: String? = "",
        val firstName: String? = "",
        val lastName: String? = "",
        var nameFirstLast: String? = "",
        val boxscoreName: String? = "",
        val batSide: PlayerSide? = PlayerSide(),
        val pitchHand: PlayerSide? = PlayerSide(),
        val stats: List<StatType>? = listOf(),
        val primaryNumber: String? = "",
        val birthDate: String? = "",
        val currentAge: Int = 0,
        val birthCity: String? = "",
        val birthStateProvince: String? = "",
        val birthCountry: String? = "",
        val height: String? = "",
        val weight: Int = 0,
        val active: Boolean = false,
        val primaryPosition: Position? = Position(),
        val useName: String? = "",
        val middleName: String? = "",
        val draftYear: Int = 0,
        val initLastName: String? = "",
        val allPositions: List<Position>? = listOf()
    )

    @Serializable
    data class PlayerSide(val code: String = "", val description: String = "")

    @Serializable
    data class Position(
        val code: String = "",
        val name: String = "",
        val type: String = "",
        val abbreviation: String = ""
    )

    @Serializable
    data class StatType(
        val type: SimpleDisplayName?,
        val group: SimpleDisplayName?,
        val stats: PlayerStats?
    )

    @Serializable
    data class SimpleDisplayName(val displayName: String?)

    @Serializable
    data class PlayerStats(
        val flyOuts: String? = "",
        val doubles: String? = "",
        val triples: String? = "",
        val strikeOuts: String? = "",
        val hitByPitch: String? = "",
        val rbi: String? = "",
        val caughtStealing: String? = "",
        val stolenBases: String? = "",
        val atBats: String? = "",
        val leftOnBase: String? = "",
        val sacBunts: String? = "",
        val sacFlies: String? = "",
        val avg: String? = "",
        val obp: String? = "",
        val slg: String? = "",
        val ops: String? = "",
        val inningsPitched: String? = "",
        val earnedRuns: String? = "",
        val battersFaced: String? = "",
        val outs: String? = "",
        val pitchesThrown: String? = "",
        val balls: String? = "",
        val strikes: String? = "",
        val era: String? = "",
        val wins: String? = "",
        val losses: String? = "",
        val saves: String? = "",
        val holds: String? = "",
        val blownSaves: String? = "",
        val runs: String? = "",
        val homeRuns: String? = "",
        val baseOnBalls: String? = "",
        val hits: String? = "",
        val note: String? = ""
    )

    @Serializable
    data class League(
        val id: Int = 0,
        val name: String = ""
    )

    /**
     * Data class containing info about a given division with a DivisionStandingsRecord.
     *
     * @param id integer corresponding to the division id constants, i.e. "200" for American League West,
     * "201" for American League East and so on. The full list of division ids can be found at
     * https://statsapi.mlb.com/api/v1/divisions?sportId=1
     */
    @Serializable
    data class Division(
        val id: Int = 0,
        val name: String = ""
    )
}
