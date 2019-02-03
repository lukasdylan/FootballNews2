package com.lukasdylan.footballservice.data.response

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Player(
    @Json(name = "idPlayer")
    val idPlayer: String? = null,
    @Json(name = "strPlayer")
    val playerName: String? = null,
    @Json(name = "strNationality")
    val playerNationality: String? = null,
    @Json(name = "dateBorn")
    val playerDateBorn: String? = null,
    @Json(name = "dateSigned")
    val playerDateJoin: String? = null,
    @Json(name = "strDescriptionEN")
    val playerDesc: String? = null,
    @Json(name = "strCutout")
    val playerIconUrl: String? = null,
    @Json(name = "strPosition")
    val playerPosition: String? = null,
    @Json(name = "strWeight")
    val playerWeight: String? = null,
    @Json(name = "strHeight")
    val playerHeight: String? = null,
    @Json(name = "strFanart1")
    val playerFanArtUrl: String? = null
) : Parcelable

@Keep
data class PlayerResponse(
    @Json(name = "player") val playerList: List<Player> = mutableListOf()
)