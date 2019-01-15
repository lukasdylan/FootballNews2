package com.lukasdylan.footballservice.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class DetailMatch(

    @PrimaryKey
    @Json(name = "idEvent")
    @ColumnInfo(name = "id_event")
    val idEvent: String = "",

    @Json(name = "idLeague")
    @ColumnInfo(name = "id_league")
    val idLeague: String? = null,

    @Json(name = "strSport")
    @ColumnInfo(name = "sport_type")
    val sportType: String? = null,

    @Json(name = "strEvent")
    @ColumnInfo(name = "match_name")
    val matchName: String? = null,

    @Json(name = "strLeague")
    @ColumnInfo(name = "league_name")
    val leagueName: String? = null,

    @Json(name = "strDescriptionEN")
    @ColumnInfo(name = "match_description")
    val matchDescription: String? = null,

    @Json(name = "idHomeTeam")
    @ColumnInfo(name = "home_team_id")
    val homeTeamId: String? = null,

    @Json(name = "idAwayTeam")
    @ColumnInfo(name = "away_team_id")
    val awayTeamId: String? = null,

    @Json(name = "strHomeTeam")
    @ColumnInfo(name = "home_team_name")
    val homeTeamName: String? = null,

    @Json(name = "strAwayTeam")
    @ColumnInfo(name = "away_team_name")
    val awayTeamName: String? = null,

    @Json(name = "intHomeScore")
    @ColumnInfo(name = "home_team_score")
    val homeTeamScore: String? = null,

    @Json(name = "intAwayScore")
    @ColumnInfo(name = "away_team_score")
    val awayTeamScore: String? = null,

    @Json(name = "strHomeGoalDetails")
    @ColumnInfo(name = "home_goal_details")
    val homeGoalDetails: String? = null,

    @Json(name = "strAwayGoalDetails")
    @ColumnInfo(name = "away_goal_details")
    val awayGoalDetails: String? = null,

    @Json(name = "strHomeRedCards")
    @ColumnInfo(name = "home_red_cards")
    val homeRedCards: String? = null,

    @Json(name = "strAwayRedCards")
    @ColumnInfo(name = "away_red_cards")
    val awayRedCards: String? = null,

    @Json(name = "strHomeYellowCards")
    @ColumnInfo(name = "home_yellow_cards")
    val homeYellowCards: String? = null,

    @Json(name = "strAwayYellowCards")
    @ColumnInfo(name = "away_yellow_cards")
    val awayYellowCards: String? = null,

    @Json(name = "strHomeLineupGoalkeeper")
    @ColumnInfo(name = "home_goal_keeper")
    val homeGoalKeeper: String? = null,

    @Json(name = "strAwayLineupGoalkeeper")
    @ColumnInfo(name = "away_goal_keeper")
    val awayGoalKeeper: String? = null,

    @Json(name = "strHomeLineupDefense")
    @ColumnInfo(name = "home_defenders")
    val homeDefenders: String? = null,

    @Json(name = "strAwayLineupDefense")
    @ColumnInfo(name = "away_defenders")
    val awayDefenders: String? = null,

    @Json(name = "strHomeLineupMidfield")
    @ColumnInfo(name = "home_midfielders")
    val homeMidfielders: String? = null,

    @Json(name = "strAwayLineupMidfield")
    @ColumnInfo(name = "away_midfielders")
    val awayMidfielders: String? = null,

    @Json(name = "strHomeLineupForward")
    @ColumnInfo(name = "home_forwarders")
    val homeForwarders: String? = null,

    @Json(name = "strAwayLineupForward")
    @ColumnInfo(name = "away_forwarders")
    val awayForwarders: String? = null,

    @Json(name = "strHomeLineupSubstitutes")
    @ColumnInfo(name = "home_substitutes")
    val homeSubstitutes: String? = null,

    @Json(name = "strAwayLineupSubstitutes")
    @ColumnInfo(name = "away_substitutes")
    val awaySubstitutes: String? = null,

    @Json(name = "dateEvent")
    @ColumnInfo(name = "date_event")
    val dateEvent: String? = null,

    @Json(name = "strDate")
    @ColumnInfo(name = "date")
    val date: String? = null,

    @Json(name = "strTime")
    @ColumnInfo(name = "time")
    val time: String? = null
) : Parcelable