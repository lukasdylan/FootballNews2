package com.lukasdylan.footballservice.data.response

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DetailTeam(
    @Json(name = "idTeam")
    val idTeam: String? = null,
    @Json(name = "strSport")
    val sportType: String? = null,
    @Json(name = "strTeam")
    val teamName: String? = null,
    @Json(name = "intFormedYear")
    val formedYear: String? = null,
    @Json(name = "strManager")
    val teamManager: String? = null,
    @Json(name = "strStadium")
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
    @Json(name = "strTeamFanart1")
    val teamFanArt1: String? = null,
    @Json(name = "strTeamFanart2")
    val teamFanArt2: String? = null,
    @Json(name = "strTeamFanart3")
    val teamFanArt3: String? = null,
    @Json(name = "strTeamFanart4")
    val teamFanArt4: String? = null
) : Parcelable

data class DetailTeamResponse(
    @Json(name = "teams") val listTeam: List<DetailTeam> = arrayListOf()
)