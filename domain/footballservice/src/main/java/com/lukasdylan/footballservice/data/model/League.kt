package com.lukasdylan.footballservice.data.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

const val PREMIER_LEAGUE_ID = 4328
const val SERIE_A_ID = 4332
const val LA_LIGA_ID = 4335
const val PREMIER_LEAGUE_NAME = "English Premier League"
const val LA_LIGA_NAME = "Spanish La Liga"
const val SERIE_A_NAME = "Italian Serie A"

@Keep
@Parcelize
data class League(
    val leagueId: String,
    val leagueName: String
) : Parcelable {
    companion object {
        val defaultLeague = League(PREMIER_LEAGUE_ID.toString(), PREMIER_LEAGUE_NAME)

        val leagueList = listOf(
            League(PREMIER_LEAGUE_ID.toString(), PREMIER_LEAGUE_NAME),
            League(SERIE_A_ID.toString(), SERIE_A_NAME),
            League(LA_LIGA_ID.toString(), LA_LIGA_NAME)
        )
    }
}