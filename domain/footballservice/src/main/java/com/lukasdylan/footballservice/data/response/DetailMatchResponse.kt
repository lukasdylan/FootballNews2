package com.lukasdylan.footballservice.data.response

import com.lukasdylan.footballservice.data.entity.DetailMatch
import com.squareup.moshi.Json

data class DetailMatchResponse(
    @Json(name = "events") val listEvent: List<DetailMatch> = arrayListOf()
)