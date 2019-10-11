package com.example.jsondeserializationprototype.immutablesmodels

import org.immutables.gson.Gson
import org.immutables.value.Value
import org.joda.time.DateTime
import java.time.LocalDate

/**
 * ImmutablesSportsDataApiResponse
 * https://statsapi.mlb.com/api/v1/schedule?hydrate=decisions,probablePitcher,broadcasts(all),
 *      game(content(all)),linescore(matchup,runners,positions),stats,person,team,
 *      flags&sportId=1%2C51&date=09%2F02%2F2018
// */
@Gson.TypeAdapters
@Value.Immutable
data class ImmutablesSportsDataApiResponse(val date: LocalDate, val dates: List<Day>, val totalGames: Int) {

    @Value.Immutable
    data class Day(
        val totalGames: Int, // 14
        val games: List<Game>
    )

    @Value.Immutable
    data class Game(
        val gamePk: Int,
        val link: String,
        val gameType: String, //"R"
        val gameDate: String, //2018-09-14T23:05:00Z"
        var resumeDate: DateTime? = null, //2018-09-14T23:05:00Z"
        var resumedFrom: DateTime? = null, //2018-09-14T23:05:00Z"
        val status: Status,
        val teams: Teams,
        val venue: Venue,
        val content: Content,
        val isTie: Boolean,
        val doubleHeader: String,
        val gamedayType: String, //"P"
        val tiebreaker: String,
        val gameNumber: Int, //1
        val calendarEventID: String, //14-531605-2018-09-14
        val seasonDisplay: String, //2018
        val dayNight: String, //"night"
        val scheduledInnings: Int, //9
        val gamesInSeries: Int, //3
        val seriesGameNumber: Int, //1
        val seriesDescription: String, //Regular Season
        val description: String, //World Series Game 1
        val recordSource: String, //S
        val ifNecessary: String, //"N"
        val ifNecessaryDescription: String, //Normal Game
        val linescore: LineScore?, //Line Score of the game
        val decisions: Decisions?, //Decisions
        val flags: Flags
    )

    @Value.Immutable
    data class Flags(val noHitter: Boolean, val perfectGame: Boolean)

    @Value.Immutable
    data class Status(
        val abstractGameCode: String,
        val statusCode: String,
        val codedGameState: String
    )

    @Value.Immutable
    data class Teams(val away: TeamStatus, val home: TeamStatus)
    @Value.Immutable
    data class TeamStatus(
        val score: Int,
        val team: Team,
        val leagueRecord: TeamLeagueRecord,
        val probablePitcher: Player?,
        val splitSquad: Boolean
    )

    @Value.Immutable
    data class Team(
        val id: Int,
        val name: String,
        val teamCode: String,
        val fileCode: String,
        val abbreviation: String,
        val teamName: String,
        val locationName: String,
        val shortName: String,
        val springLeague: League?,
        val springLeagueRank: Int?,
        val division: Division
    )

    @Value.Immutable
    data class TeamLeagueRecord(val wins: Int, val losses: Int, val pct: Float)
    @Value.Immutable
    data class Venue(val id: Int, val name: String, val link: String)
    @Value.Immutable
    data class Content(
        val link: String,
        val media: Media?,
        val highlights: ImmutablesGameHighlightResponse.Highlights?
    )

    @Value.Immutable
    data class Media(
        val epg: List<Epg>?,
        val epgAlternate: List<Epg>,
        val enhancedGame: Boolean,
        val freeGame: Boolean
    )

    @Value.Immutable
    data class Epg (val title: String, val items: List<Items>)

    @Value.Immutable
    data class Items(
        val id: Int,
        val contentId: String,
        val mediaId: String,
        val mediaState: String,
        val mediaFeedType: String,
        val mediaFeedSubType: String,
        val callLetters: String,
        val foxAuthRequired: Boolean,
        val tbsAuthRequired: Boolean,
        val espnAuthRequired: Boolean,
        val fs1AuthRequired: Boolean,
        val mlbnAuthRequired: Boolean,
        val freeGame: Boolean
    )

    @Value.Immutable
    data class VideoEpgItem(
        val id: String,
        val contentId: String,
        val mediaId: String,
        val mediaState: String, //Enum candidate
        val mediaFeedType: String, //Enum candidate HOME|AWAY
        val mediaFeedSubType: String,
        val callLetters: String,
        val foxAuthRequired: Boolean,
        val tbsAuthRequired: Boolean,
        val espnAuthRequired: Boolean,
        val fs1AuthRequired: Boolean,
        val mlbnAuthRequired: Boolean,
        val freeGame: Boolean
    )

    @Value.Immutable
    data class MlbtvAudioEpgItem(
        val id: String,
        val type: String,
        val mediaFeedType: String,
        val description: String,
        val renditionName: String,
        val language: String
    )

    @Value.Immutable
    data class AudioEpgItem(
        val id: String,
        val contentId: String,
        val mediaId: String,
        val mediaState: String,
        val type: String, //AWAY|HOME
        val mediaFeedSubType: String, //3 digit number
        val callLetters: String,
        val language: String
    )

    @Value.Immutable
    data class LineScore(
        val currentInning: Int?, // Current Inning
        val currentInningOrdinal: String?, //"9th"
        val inningState: String?, //Top, Middle, Bottom, End (enumeration)
        val inningHalf: String?, //Top, Bottom
        val isTopInning: Boolean?, //true/false
        val innings: List<Inning>?,
        val teams: LineScoreTeams,
        val balls: Int?,
        val strikes: Int?,
        val outs: Int?,
        val offense: Offense?,
        val defense: Defense?
    )

    @Value.Immutable
    data class LineScoreTeams(val home: LineScoreTeam, val away: LineScoreTeam)
    @Value.Immutable
    data class LineScoreTeam(val runs: Int, val hits: Int, val errors: Int, val leftOnBase: Int)
    @Value.Immutable
    data class Inning(
        val num: Int,
        val ordinalNum: String,
        val home: LineScoreTeam,
        val away: LineScoreTeam
    )

    @Value.Immutable
    data class Offense(
        val batter: Player?,
        val first: Player?,
        val second: Player?,
        val third: Player?
    )

    @Value.Immutable
    data class Defense(
        val pitcher: Player?,
        val catcher: Player?,
        val first: Player?,
        val second: Player?,
        val third: Player?,
        val shortstop: Player?,
        val left: Player?,
        val center: Player?,
        val right: Player?
    )

    @Value.Immutable
    data class Decisions(val winner: Player?, val loser: Player?, val save: Player?)

    @Value.Immutable
    data class Player(
        val id: Int,
        val fullName: String?,
        val link: String?,
        val firstName: String?,
        val lastName: String?,
        var nameFirstLast: String?,
        val boxscoreName: String?,
        val batSide: PlayerSide?,
        val pitchHand: PlayerSide?,
        val stats: List<StatType>?,
        val primaryNumber: String?,
        val birthDate: String?,
        val currentAge: Int,
        val birthCity: String?,
        val birthStateProvince: String?,
        val birthCountry: String?,
        val height: String?,
        val weight: Int,
        val active: Boolean,
        val primaryPosition: Position?,
        val useName: String?,
        val middleName: String?,
        val draftYear: Int,
        val initLastName: String?,
        val allPositions: List<Position>?
    )

    @Value.Immutable
    data class PlayerSide(val code: String, val description: String)

    @Value.Immutable
    data class Position(
        val code: String,
        val name: String,
        val type: String,
        val abbreviation: String
    )

    @Value.Immutable
    data class StatType(
        val type: SimpleDisplayName?,
        val group: SimpleDisplayName?,
        val stats: PlayerStats?
    )

    @Value.Immutable
    data class SimpleDisplayName(val displayName: String?)

    @Value.Immutable
    data class PlayerStats(
        val flyOuts: String?,
        val doubles: String?,
        val triples: String?,
        val strikeOuts: String?,
        val hitByPitch: String?,
        val rbi: String?,
        val caughtStealing: String?,
        val stolenBases: String?,
        val atBats: String?,
        val leftOnBase: String?,
        val sacBunts: String?,
        val sacFlies: String?,
        val avg: String?,
        val obp: String?,
        val slg: String?,
        val ops: String?,
        val inningsPitched: String?,
        val earnedRuns: String?,
        val battersFaced: String?,
        val outs: String?,
        val pitchesThrown: String?,
        val balls: String?,
        val strikes: String?,
        val era: String?,
        val wins: String?,
        val losses: String?,
        val saves: String?,
        val holds: String?,
        val blownSaves: String?,
        val runs: String?,
        val homeRuns: String?,
        val baseOnBalls: String?,
        val hits: String?,
        val note: String?
    )

    @Value.Immutable
    data class League(
        val id: Int,
        val name: String
    )

    /**
     * Data class containing info about a given division with a DivisionStandingsRecord.
     *
     * @param id integer corresponding to the division id constants, i.e. "200" for American League West,
     * "201" for American League East and so on. The full list of division ids can be found at
     * https://statsapi.mlb.com/api/v1/divisions?sportId=1
     */
    @Value.Immutable
    data class Division(
        val id: Int,
        val name: String
    )
}
