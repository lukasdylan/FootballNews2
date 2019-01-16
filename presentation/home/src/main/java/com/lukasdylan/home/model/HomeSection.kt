package com.lukasdylan.home.model

enum class HomeSectionType {
    TRENDING_LEAGUE_NEWS, PREV_MATCH, NEXT_MATCH, STANDINGS
}

data class HomeSection(
    val type: HomeSectionType,
    val data: List<*>,
    val isLoading: Boolean
)