package com.lukasdylan.footballservice.data.response

import androidx.annotation.Keep
import com.lukasdylan.footballservice.data.entity.DetailTeam
import com.squareup.moshi.Json

@Keep
data class DetailTeamResponse(
    @Json(name = "teams") val listTeam: List<DetailTeam> = arrayListOf()
)