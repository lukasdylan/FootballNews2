package com.lukasdylan.footballservice.data.entity

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Keep
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
    @ColumnInfo(name = "team_badge")
    val teamBadge: String? = null,

    @Json(name = "strStadiumThumb")
    @ColumnInfo(name = "team_stadium_image")
    val teamStadiumImage: String? = null,

    @Json(name = "strStadiumDescription")
    @ColumnInfo(name = "team_stadium_desc")
    val teamStadiumDesc: String? = null,

    @Json(name = "strDescriptionEN")
    @ColumnInfo(name = "team_desc")
    val teamDesc: String? = null,

    @Json(name = "strStadiumLocation")
    @ColumnInfo(name = "team_stadium_location")
    val teamStadiumLocation: String? = null,

    @Json(name = "intStadiumCapacity")
    @ColumnInfo(name = "team_stadium_capacity")
    val teamStadiumCapacity: String? = null,

    @Json(name = "strWebsite")
    @ColumnInfo(name = "team_website")
    val teamWebsite: String? = null,

    @Json(name = "strFacebook")
    @ColumnInfo(name = "team_facebook_page")
    val teamFacebookPage: String? = null,

    @Json(name = "strTwitter")
    @ColumnInfo(name = "team_twitter_page")
    val teamTwitterPage: String? = null,

    @Json(name = "strInstagram")
    @ColumnInfo(name = "team_instagram_page")
    val teamInstagramPage: String? = null,

    @Json(name = "strTeamFanart1")
    @ColumnInfo(name = "team_fan_art_1")
    val teamFanArt1: String? = null,

    @Json(name = "strTeamFanart2")
    @ColumnInfo(name = "team_fan_art_2")
    val teamFanArt2: String? = null,

    @Json(name = "strTeamFanart3")
    @ColumnInfo(name = "team_fan_art_3")
    val teamFanArt3: String? = null,

    @Json(name = "strTeamFanart4")
    @ColumnInfo(name = "team_fan_art_4")
    val teamFanArt4: String? = null
) : Parcelable