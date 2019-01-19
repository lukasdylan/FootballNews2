package com.lukasdylan.footballservice.data.response

import com.lukasdylan.footballservice.data.entity.DetailTeam
import com.squareup.moshi.Json

data class DetailTeamResponse(
    @Json(name = "teams") val listTeam: List<DetailTeam> = arrayListOf()
)