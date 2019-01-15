package com.lukasdylan.football.model

enum class TeamInfoType {
    TEAM_INFO, STADIUM_INFO, PLAYER_INFO, MANAGER_INFO, NEWS_INFO
}

data class TeamInfoDataModel(
    val type: TeamInfoType,
    val data: List<*>,
    val isLoading: Boolean
)