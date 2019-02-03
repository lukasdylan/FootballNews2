package com.lukasdylan.footballservice.data.response

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Standings(
    @Json(name = "name") val name: String? = null,
    @Json(name = "teamid") val teamId: String? = null,
    @Json(name = "played") val played: Int = 0,
    @Json(name = "win") val win: Int = 0,
    @Json(name = "draw") val draw: Int = 0,
    @Json(name = "loss") val loss: Int = 0,
    @Json(name = "total") val total: Int = 0
) : Parcelable

@Keep
@Parcelize
data class StandingResponse(
    @Json(name = "table") val standingList: List<Standings> = mutableListOf()
) : Parcelable