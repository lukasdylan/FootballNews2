package com.lukasdylan.footballservice.data.response

import androidx.annotation.Keep
import com.lukasdylan.footballservice.data.entity.DetailMatch
import com.squareup.moshi.Json

@Keep
data class DetailMatchResponse(
    @Json(name = "events") val listEvent: List<DetailMatch> = arrayListOf()
)