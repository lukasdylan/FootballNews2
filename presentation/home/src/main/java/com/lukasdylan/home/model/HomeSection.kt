package com.lukasdylan.home.model

enum class HomeSectionType {
    TRENDING_LEAGUE_NEWS, STANDINGS, PREV_MATCH, NEXT_MATCH
}

data class HomeSection(
    val type: HomeSectionType,
    val data: List<*>,
    val isLoading: Boolean
)