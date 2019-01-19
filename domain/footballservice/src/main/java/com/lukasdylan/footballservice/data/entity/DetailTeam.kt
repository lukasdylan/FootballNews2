package com.lukasdylan.footballservice.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class DetailTeam(
    @PrimaryKey
    @Json(name = "idTeam")
    @ColumnInfo(name = "id_team")
    val idTeam: String = "",

    @Json(name = "strSport")
    @ColumnInfo(name = "sport_type")
    val sportType: String? = null,

    @Json(name = "strTeam")
    @ColumnInfo(name = "team_name")
    val teamName: String? = null,

    @Json(name = "strLeague")
    @ColumnInfo(name = "league_name")
    val leagueName: String? = null,

    @Json(name = "intFormedYear")
    @ColumnInfo(name = "formed_year")
    val formedYear: String? = null,

    @Json(name = "strManager")
    @ColumnInfo(name = "team_manager")
    val teamManager: String? = null,

    @Json(name = "strStadium")
    @ColumnInfo(name = "team_stadium")
    val teamStadium: String? = null,
    @Json(name = "strTeamBadge")
    val teamBadge: String? = null,
    @Json(name = "strStadiumThumb")
    val teamStadiumImage: String? = null,
    @Json(name = "strStadiumDescription")
    val teamStadiumDesc: String? = null,
    @Json(name = "strDescriptionEN")
    val teamDesc: String? = null,
    @Json(name = "strStadiumLocation")
    val teamStadiumLocation: String? = null,
    @Json(name = "intStadiumCapacity")
    val teamStadiumCapacity: String? = null,
    @Json(name = "strWebsite")
    val teamWebsite: String? = null,
    @Json(name = "strFacebook")
    val teamFacebookPage: String? = null,
    @Json(name = "strTwitter")
    val teamTwitterPage: String? = null,
    @Json(name = "strInstagram")
    val teamInstagramPage: String? = null,
    @Json(name = "strTeamFanart1")
    val teamFanArt1: String? = null,
    @Json(name = "strTeamFanart2")
    val teamFanArt2: String? = null,
    @Json(name = "strTeamFanart3")
    val teamFanArt3: String? = null,
    @Json(name = "strTeamFanart4")
    val teamFanArt4: String? = null
) : Parcelable