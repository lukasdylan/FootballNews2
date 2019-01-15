package com.lukasdylan.newsservice.data

object NewsSources {
    const val BBC_SPORT = "bbc-sport"
    const val ESPN = "espn"
    const val FOUR_FOUR_TWO = "four-four-two"
    const val FOX_SPORTS = "fox-sports"
    const val THE_SPORT_BIBLE = "the-sport-bible"

    val defaultSources = listOf(BBC_SPORT, ESPN, FOUR_FOUR_TWO, FOX_SPORTS, THE_SPORT_BIBLE).joinToString(",")
}